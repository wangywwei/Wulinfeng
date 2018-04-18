package com.hxwl.wulinfeng.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.common.utils.HncNotifier;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.LiveDetailActivity;
import com.hxwl.wulinfeng.activity.LoginforCodeActivity;
import com.hxwl.wulinfeng.adapter.SaiChengAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.HomeBannerImageBean;
import com.hxwl.wulinfeng.bean.SaiChengBannereBean;
import com.hxwl.wulinfeng.bean.SaiChengType;
import com.hxwl.wulinfeng.bean.SaichengBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.util.json.GsonUtil;
import com.tendcloud.tenddata.TCAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Allen on 2017/5/26.
 * 资讯fragment界面
 */

public class SaiChengQuanFragment extends BaseFragment {
    private Activity activity;
    private ListView list_view;
    private View saicheng_frament;
    private RelativeLayout ll_pb;
    private SaiChengType bean;
    private CommonSwipeRefreshLayout common_layout;
    private int page = 1;
    private boolean isRefresh;
    private String num;
    private List<SaichengBean> listData = new ArrayList<>();

    private List<SaiChengBannereBean> data2EntityList = new ArrayList<>();

    private String type;
    private Banner banner;
    private SaiChengAdapter saiChengAdapter;
    private View header;
    private EmptyViewHelper emptyViewHelper;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public void setType(SaiChengType bean) {
        this.bean = bean;
        this.type = bean.getName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (saicheng_frament == null) {
            saicheng_frament = inflater.inflate(R.layout.saicheng_frament, container, false);
            initView();
            initData(true);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) saicheng_frament.getParent();
            if (parent != null) {
                parent.removeView(saicheng_frament);
            }
        }
        AppUtils.setTitle(getActivity());
        return saicheng_frament;
    }

    private void initData(boolean b) {
        getZixunData(page, b);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SaichengBean dataBean = null;
            if (v instanceof RelativeLayout) {
                dataBean = (SaichengBean) v.getTag();
            } else {
                dataBean = (SaichengBean) v.findViewById(R.id.rl_click).getTag();
            }
            if (dataBean == null) {
                return;
            }
            Intent intent = null;
            String state = dataBean.getState();//0不可用 1未开始 2进行中 3 已结束
            if ("0".equals(state)) {
                ToastUtils.showToast(getActivity(), "类型不可用。");
            } else if ("1".equals(state)) {//预约
                if ("empty".equals(dataBean.getYuyue_state())) {
                    String start_time = dataBean.getStart_time();
                    long value = Long.parseLong(start_time) * 1000;
                    long value2 = System.currentTimeMillis();
                    if (value <= value2) {
                        intent = new Intent(getActivity(), HuiGuDetailActivity.class);
                        intent.putExtra("saichengId", dataBean.getId());
                        intent.putExtra("saishiId", dataBean.getSaishi_id());
                        intent.putExtra("name", dataBean.getName());
                        intent.putExtra("pageType", "bisaishipin");
                        startActivity(intent);
                    } else {
                        postYuYueState(dataBean);
                    }
                } else if ("0".equals(dataBean.getYuyue_state())) {
                    postUnYuYueState(dataBean);
                } else {
                    ToastUtils.showToast(getActivity(), "已经预约过了");
                }

            } else if ("2".equals(state)) {//直播
                intent = new Intent(getActivity(), LiveDetailActivity.class);
                intent.putExtra("saichengId", dataBean.getId());
                intent.putExtra("saishiId", dataBean.getSaishi_id());
                intent.putExtra("name", dataBean.getName());
                startActivity(intent);
            } else if ("3".equals(state)) {//回放
                intent = new Intent(getActivity(), HuiGuDetailActivity.class);
                intent.putExtra("saichengId", dataBean.getId());
                intent.putExtra("saishiId", dataBean.getSaishi_id());
                intent.putExtra("name", dataBean.getName());
                intent.putExtra("pageType", "bisaishipin");
                startActivity(intent);
            } else {
                ToastUtils.showToast(getActivity(), "未识别类型。");
            }
        }
    };

    //提交取消预约
    private void postUnYuYueState(final SaichengBean dataBean) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    dataBean.setYuyue_state("empty");
                    saiChengAdapter.notifyDataSetChanged();
                    SPUtils.clearYuYueInfo(getActivity(), dataBean);
                } else {
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("saichengId", dataBean.getId());
        map.put("uid", MakerApplication.instance.getUid());
        map.put("method", NetUrlUtils.wulin_quxiaoyuyuetongzhi);
        tasker.execute(map);
    }

    /**
     * 提交预约
     *
     * @param dataBean
     */
    private void postYuYueState(final SaichengBean dataBean) {
        AppUtils.setEvent(getActivity(), "Appointment", "点击预约");
        String uid = MakerApplication.instance.getUid();
        String loginKey = MakerApplication.instance.getLoginKey();
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)) {
            startActivity(new Intent(getActivity(), LoginforCodeActivity.class));
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    initTiXing(dataBean);
                } else {
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("saichengId", dataBean.getId());
        map.put("device", "2");
        map.put("uid", MakerApplication.instance.getUid());
        map.put("method", NetUrlUtils.wulin_yuyuetongzhi);
        tasker.execute(map);

    }

    /**
     * 添加提醒
     *
     * @param bean
     */
    private void initTiXing(final SaichengBean bean) {

        ToastUtils.diyToast(getActivity(), "直播开始前10分钟提醒！");
        bean.setYuyue_state("0");
        saiChengAdapter.notifyDataSetChanged();
        MakerApplication.instance.makeSaicheng(getActivity(), bean);

        if (listData != null) {
            String start_time = bean.getStart_time();
            long value = Long.parseLong(start_time) * 1000;
            long value2 = System.currentTimeMillis();
            if (value <= value2) {
                SPUtils.clearYuYueInfo(getActivity(), bean);
            } else {
                HncNotifier.getHncNotifier().shownotifyItemNote(getActivity(), bean);
            }
        }
    }

    public void getZixunData(int page, boolean isShowPro) {
        if (!SystemHelper.isConnected(activity)) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), isShowPro, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if (isRefresh) {//是加载更多
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(), SaichengBean.class));
                    } else {//刷新或是第一次进来
                        listData.addAll(JSON.parseArray(result.getData(), SaichengBean.class));
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    saiChengAdapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    emptyViewHelper.setType(1);
                }
            }
        }, "method=" + NetUrlUtils.zsaiCheng_listdata + page + bean.getId() + MakerApplication.instance.getUid());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page + "");
        map.put("num", num);
        map.put("uid", MakerApplication.instance.getUid());
        map.put("loginKey", MakerApplication.instance.getLoginKey());
        if (bean != null) {
            map.put("saishiId", bean.getId());
        }
        map.put("method", NetUrlUtils.zsaiCheng_listdata);
        tasker.execute(map);
    }

    private void initView() {
        list_view = (ListView) saicheng_frament.findViewById(R.id.list_view);
        header = LayoutInflater.from(getActivity()).inflate(R.layout.item_banner_saicheng, (ViewGroup) saicheng_frament.findViewById(android.R.id.content), false);
        list_view.addHeaderView(header);

        ll_pb = (RelativeLayout) saicheng_frament.findViewById(R.id.ll_pb);
        common_layout = (CommonSwipeRefreshLayout) saicheng_frament.findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                getZixunData(page, true);
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                getZixunData(page, true);
            }
        });
        saiChengAdapter = new SaiChengAdapter(getActivity(), listData, onClickListener);
        list_view.setAdapter(saiChengAdapter);
        emptyViewHelper = new EmptyViewHelper(list_view, (FrameLayout) saicheng_frament.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initHeadData();
                initData(true);
            }
        });
//        recycl_view.setDetalisCallBack(this);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SaichengBean dataBean = null;
                if (view instanceof RelativeLayout) {
                    dataBean = (SaichengBean) view.getTag();
                } else {
                    dataBean = (SaichengBean) view.findViewById(R.id.rl_click).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                Intent intent = null;
                String state = dataBean.getState();//0不可用 1未开始 2进行中 3 已结束
                if ("0".equals(state)) {
                    ToastUtils.showToast(getActivity(), "类型不可用。");
                } else if ("1".equals(state)) {//预约
                    String start_time = dataBean.getStart_time();
                    long value = Long.parseLong(start_time) * 1000;
                    long value2 = System.currentTimeMillis();
                    if (value <= value2) {
                        intent = new Intent(getActivity(), HuiGuDetailActivity.class);
                        intent.putExtra("saichengId", dataBean.getId());
                        intent.putExtra("saishiId", dataBean.getSaishi_id());
                        intent.putExtra("name", dataBean.getName());
                        intent.putExtra("pageType", "bisaishipin");
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), LiveDetailActivity.class);
                        intent.putExtra("saichengId", dataBean.getId());
                        intent.putExtra("saishiId", dataBean.getSaishi_id());
                        intent.putExtra("name", dataBean.getName());
                        startActivity(intent);
                    }
                } else if ("2".equals(state)) {//直播
                    intent = new Intent(getActivity(), LiveDetailActivity.class);
                    intent.putExtra("saichengId", dataBean.getId());
                    intent.putExtra("saishiId", dataBean.getSaishi_id());
                    intent.putExtra("name", dataBean.getName());
                    startActivity(intent);
                } else if ("3".equals(state)) {//回放
                    intent = new Intent(getActivity(), HuiGuDetailActivity.class);
                    intent.putExtra("saichengId", dataBean.getId());
                    intent.putExtra("saishiId", dataBean.getSaishi_id());
                    intent.putExtra("name", dataBean.getName());
                    intent.putExtra("pageType", "bisaishipin");
                    startActivity(intent);
                } else {
                    ToastUtils.showToast(getActivity(), "未识别类型。");
                }
            }
        });
        banner = (Banner) header.findViewById(R.id.banner);
        initHeadData();
    }

    private void initHeadData() {
        if (!SystemHelper.isConnected(activity)) {
            UIUtils.showToast("请检查网络");
            header.setVisibility(View.GONE);
            return  ;
        }
        if ("全部".equals(type)) {
            header.setVisibility(View.VISIBLE);
            banner.setVisibility(View.VISIBLE);
            saiChengAdapter.setIsShowLable(true);
            doBanner();
        } else {
            header.setVisibility(View.GONE);
            banner.setVisibility(View.GONE);
            saiChengAdapter.setIsShowLable(false);
        }
    }


    private void doBanner() {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    header.setVisibility(View.VISIBLE);
                    data2EntityList.clear();
                    //现在是显示一张图片
                    data2EntityList.addAll(JSON.parseArray(result.getData(), SaiChengBannereBean.class));
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                    banner.setIndicatorGravity(BannerConfig.RIGHT);
                    //设置图片加载器
                    banner.setImageLoader(new GlideImageLoader());
                    //设置图片集合
                    banner.setImages(data2EntityList);
                    //设置banner动画效果
                    banner.setBannerAnimation(Transformer.Default);
                    //设置自动轮播，默认为true
                    banner.isAutoPlay(true);
                    //设置轮播时间
                    banner.setDelayTime(3000);
                    //设置指示器位置（当BANNER模式中有指示器时）
//                        banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
                    //banner设置方法全部调用完毕时最后调用
                    banner.start();
                    banner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            //TODO 最新通用url跳转

                            SaiChengBannereBean dataBean = data2EntityList.get(position);
                            String url = dataBean.getTongyong_url() ;
                            AppUtils.doTiaozhuan(url ,getActivity());

//                            String jsonBean = dataBean.getAndroid_url();
//                            BannerListBean info = JSON.parseObject(jsonBean, BannerListBean.class);
//                            String jump_type = info.getJump_type();
//                            Intent intent = null;
//                            if ("zixun".equals(jump_type)) {
//                                intent = new Intent(activity, ZiXunDetailsActivity.class);
//                                intent.putExtra("id", info.getId());
//                                activity.startActivity(intent);
//                            } else if ("tuji".equals(jump_type)) {
//                                intent = new Intent(activity, TuJiDetailsActivity.class);
//                                intent.putExtra("id", info.getId());
//                                activity.startActivity(intent);
//                            } else if ("video".equals(jump_type)) {
//                                intent = new Intent(activity, VideoDetailActivity.class);
//                                intent.putExtra("id", info.getId());
//                                activity.startActivity(intent);
//                            } else if ("bisaishipin".equals(jump_type)) {
//                                intent = new Intent(activity, HuiGuDetailActivity.class);
//                                intent.putExtra("saichengId", info.getId());
//                                intent.putExtra("saishiId", info.getsaishiId());
//                                intent.putExtra("name", info.getTitle());
//                                activity.startActivity(intent);
//                            } else if ("zhibo".equals(jump_type)) {
//                                intent = new Intent(activity, LiveDetailActivity.class);
//                                intent.putExtra("saichengId", info.getId());
//                                intent.putExtra("saishiId", info.getsaishiId());
//                                intent.putExtra("name", info.getTitle());
//                                activity.startActivity(intent);
//                            } else if ("h5".equals(jump_type)) {
//                                intent = new Intent(activity, WebViewCurrencyActivity.class);
//                                intent.putExtra("url", info.getUrl());
//                                intent.putExtra("title", info.getTitle());
//                                activity.startActivity(intent);
//                            } else {
//                                ToastUtils.showToast(activity, "没有数据...");
//                            }
                        }
                    });
                } else if (result != null && result.getStatus().equals("empty")) {
                    list_view.removeHeaderView(header) ;
                    header.setVisibility(View.GONE);
                } else {
                    list_view.removeHeaderView(header) ;
                    header.setVisibility(View.GONE);
                }
            }
        }, "method=" + NetUrlUtils.saiCheng_banner);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("method", NetUrlUtils.saiCheng_banner);
        tasker.execute(map);

    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //Glide 加载图片简单用法
            HomeBannerImageBean pathbean = GsonUtil.GsonToBean(new Gson().toJson(path), HomeBannerImageBean.class);
            if (pathbean != null && pathbean.getImg() != null) {
                ImageUtils.getPic(pathbean.getImg(), imageView, context);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "赛程" + type);
        TCAgent.onPageStart(getActivity(), "赛程" + type);
        page = 1;
        isRefresh = true;
        initData(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(), "赛程" + type);
        TCAgent.onPageEnd(getActivity(), "赛程" + type);
    }
}
