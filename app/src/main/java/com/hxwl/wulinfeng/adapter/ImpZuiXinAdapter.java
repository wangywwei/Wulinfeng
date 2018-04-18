package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.BiSaiShiPinActivity;
import com.hxwl.wulinfeng.activity.HomeActivity;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.LiveDetailActivity;
import com.hxwl.wulinfeng.activity.TuJiDetailsActivity;
import com.hxwl.wulinfeng.activity.VideoDetailActivity;
import com.hxwl.wulinfeng.activity.WebViewCurrencyActivity;
import com.hxwl.wulinfeng.activity.ZhuanTiDetailActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.hxwl.wulinfeng.bean.AllHomeHunHeBean;
import com.hxwl.wulinfeng.bean.AllZhuanTiBean;
import com.hxwl.wulinfeng.bean.BannerListBean;
import com.hxwl.wulinfeng.bean.HomeBannerImageBean;
import com.hxwl.wulinfeng.bean.HomeHunHeBean;
import com.hxwl.wulinfeng.bean.HomeTuijianZhiboBean;
import com.hxwl.wulinfeng.bean.HomeZhuanTiBean;
import com.hxwl.wulinfeng.bean.HomeZuixinHuifangBean;
import com.hxwl.wulinfeng.bean.MainPageTuiJianBean;
import com.hxwl.wulinfeng.bean.SaichengBean;
import com.hxwl.wulinfeng.bean.ShouyeBanner;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.NetWorkUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.util.json.GsonUtil;
import com.tendcloud.tenddata.TCAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.id;
import static android.R.string.no;
import static android.media.CamcorderProfile.get;
import static com.alibaba.fastjson.JSON.parseObject;
import static com.hxwl.wulinfeng.R.id.common_layout;
import static com.hxwl.wulinfeng.R.id.view_line;

/**
 * Created by Allen on 2017/5/26.
 * 最新栏的实现类
 */

public class ImpZuiXinAdapter extends BaseZuiXinAdapter {

    //    private ArrayList<ShouyeBanner.Data2Entity> bannerData = new ArrayList<>();
    //头条数据
    private List<SaichengBean> tuijianZhiboBeen = new ArrayList<>();
    private List<HomeZuixinHuifangBean> huifangBean = new ArrayList<>();
    private List<HomeZhuanTiBean> zhuantiBean = new ArrayList<>();
    private List<AllHomeHunHeBean> tuijianHunHeBean = new ArrayList<>();
    private MainPageTuiJianBean mainPageTuiJianBean;
    //上下文对象
    private Context m_Context;
    private Activity activity;
    private int titleCount;
    private List<HomeBannerImageBean> data2EntityList = new ArrayList<>();
    private ShouyeBanner bannerBean;

    private VideoListener videoListener;
    private String tuijianNum;



//    //专题列表
//    private List<MainPageTuiJianBean.DataBean.ZhuantiBean> zhuantiBeanList;
//    //新闻列表
//    private List<MainPageTuiJianBean.DataBean.NewsBean> newsBeanList;

    public void setVideoListener(VideoListener videoListener) {
        this.videoListener = videoListener;
    }

    public ImpZuiXinAdapter(Context context, Activity activity) {
        this.m_Context = context;
        this.activity = activity;
    }

    @Override
    public ZuiXinBannerViewHolder onCreateBannerViewHolder(ViewGroup parent, int position) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view1 = inflate.inflate(R.layout.item_banner_zuixin, null);
        return new ZuiXinBannerViewHolder(view1);
    }

    /**
     * banner纵向数量
     *
     * @return
     */
    @Override
    public int getBannerViewCount() {
        if (data2EntityList != null && data2EntityList.size() > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public void onBindBannerViewHolder(ZuiXinBannerViewHolder holder, int position) {
        dobanner(holder.banner);
        holder.banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                detalisCallBack.getDetalisData(TYPE_BANNER, TYPE_BANNER, position);
            }
        });
    }

    /**
     * 轮播图
     */
    private void dobanner(final Banner banner) {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        //设置图片集合
        banner.setImages(data2EntityList);
        List<String> titleList = getTitleList(data2EntityList);
        banner.setBannerTitles(titleList) ;

        List<String> lableList = getLableList(data2EntityList);
        banner.setBannerLables(lableList) ;
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        //                        banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //TODO
                HomeBannerImageBean dataBean = data2EntityList.get(position);
                String url = dataBean.getTongyong_url() ;
                AppUtils.doTiaozhuan(url ,activity);

            }
        });
    }

    private List<String> getTitleList(List<HomeBannerImageBean> list) {
        List<String> titleList = new ArrayList<>();
        if (list == null) {
            return titleList;
        }
        for (int i = 0; i < list.size(); i++) {
            titleList.add(list.get(i).getTitle());
        }
        return titleList;
    }
    private List<String> getLableList(List<HomeBannerImageBean> list) {
        List<String> titleList = new ArrayList<>();
        if (list == null) {
            return titleList;
        }
        for (int i = 0; i < list.size(); i++) {
            titleList.add(list.get(i).getLabel());
        }
        return titleList;
    }

    @Override
    public ZuiXinTuijianViewHolder onCreateZuiXinTuiJianViewHolder(ViewGroup parent, int position) {
        if (tuijianZhiboBeen != null && tuijianZhiboBeen.size() > 0) {
            LayoutInflater inflate = LayoutInflater.from(parent.getContext());
            View view1 = inflate.inflate(R.layout.item_tuijian_zuixin, null);
            return new ZuiXinTuijianViewHolder(view1);
        }
        return null;
    }

    @Override
    public int getZuiXinTuiJianViewCount() {
        if (tuijianZhiboBeen != null) {
            return (tuijianZhiboBeen.size()) >= 1 ? 1 : 0;
        }
        return 0;
    }

    @Override
    public void onBindZuiXinTuiJianViewHolder(ZuiXinTuijianViewHolder holder, int position) {
        ImpZuiXinTuiJianAdapter zuiXinTuiJianAdapter = new ImpZuiXinTuiJianAdapter(tuijianZhiboBeen, activity);
        LinearLayoutManager layout = new LinearLayoutManager(m_Context);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.tv_num.setText("最近有" + tuijianNum + "比赛可预约观看");
        holder.rl_goon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatService.onEvent(activity, "MoreLive", "更多直播", 1);
                TCAgent.onEvent(activity, "MoreLive", "更多直播");
                Intent intent = new Intent(activity, HomeActivity.class);
                intent.putExtra("fragId", 2);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        holder.recyclerView.setLayoutManager(layout);
        holder.recyclerView.setAdapter(zuiXinTuiJianAdapter);
    }

    /**
     * 回放布局的创建
     *
     * @return
     */
    @Override
    public int getZuiXinVedioViewCount() {
        return huifangBean.size();
    }

    @Override
    public ZuiXinVedioViewHolder onCreateZuiXinVedioViewHolder(ViewGroup parent, int position) {
        if (huifangBean != null && huifangBean.size() > 0) {
            LayoutInflater inflate = LayoutInflater.from(parent.getContext());
            View huifangView = inflate.inflate(R.layout.item_zuixin_huifang, null);
            return new ZuiXinVedioViewHolder(huifangView);
        }
        return null;
    }

    @Override
    public void onBindZuiXinVedioViewHolder(final ZuiXinVedioViewHolder holder, final int position) {
        if (position == 0) {
            holder.iv_view.setVisibility(View.VISIBLE);
            holder.tv_hint.setVisibility(View.VISIBLE);
        } else {
            holder.iv_view.setVisibility(View.GONE);
            holder.tv_hint.setVisibility(View.GONE);
        }
        if (position == huifangBean.size() - 1) {
            holder.rl_hint2.setVisibility(View.VISIBLE);
        } else {
            holder.rl_hint2.setVisibility(View.GONE);
        }
        final HomeZuixinHuifangBean newsBean = huifangBean.get(position);
        holder.tv_coutent.setText(newsBean.getTitle() + "");
        holder.tv_dianzan.setText(newsBean.getBofangliang() + "人看过");
        holder.tv_time.setText(TimeUtiles.getTimeed(newsBean.getZhibo_time()));

        holder.label2.setText(newsBean.getSaishi_name());
        ImageUtils.getPic(newsBean.getImg(), holder.pic_1, m_Context);

        holder.ic_net_warn.setVisibility(View.GONE);
        if (!newsBean.isSelect() || holder.videoContainer.getVisibility() == View.GONE) {
            holder.videoContainer.removeAllViews();
            holder.videoContainer.setVisibility(View.GONE);
            holder.ll_picbg.setVisibility(View.VISIBLE);
        } else {
            //true
            holder.ll_picbg.setVisibility(View.GONE);
            holder.videoContainer.setVisibility(View.VISIBLE);
        }
        holder.llyt_toutiao_vedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                detalisCallBack.getDetalisData(TYPE_VEDIO, TYPE_VEDIO, position);
            }
        });
        holder.iv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoListener != null) {
                    StatService.onEvent(m_Context, "VedioPlayTimes", "视频播放次数", 1);
                    TCAgent.onEvent(m_Context, "VedioPlayTimes", "视频播放次数");
                    if (!NetWorkUtils.isWifiConnected(m_Context)) {
                        holder.ic_net_warn.setVisibility(View.VISIBLE);
                    } else {
                        if(newsBean.getUrl_type().equals("1")){
                            holder.ll_picbg.setVisibility(View.GONE);
                            holder.videoContainer.setVisibility(View.VISIBLE);
                            videoListener.loadWebUrlForHG(newsBean.getUrl() ,position ,position ,holder.videoContainer);
                        }else{
                            //隐藏该容器，显示播放容器
                            holder.ll_picbg.setVisibility(View.GONE);
                            holder.videoContainer.setVisibility(View.VISIBLE);
                            videoListener.playVideoForHG(newsBean.getUrl(),
                                    holder.videoContainer, getBannerViewCount()+getZuiXinTuiJianViewCount()+position ,position ,newsBean.getTitle());
                        }
                    }
                }
            }
        });
        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatService.onEvent(m_Context, "VedioPlayTimes", "视频播放次数", 1);
                TCAgent.onEvent(m_Context, "VedioPlayTimes", "视频播放次数");
                holder.ic_net_warn.setVisibility(View.GONE);
                if(newsBean.getUrl_type().equals("1")){
                    holder.ll_picbg.setVisibility(View.GONE);
                    holder.videoContainer.setVisibility(View.VISIBLE);
                    videoListener.loadWebUrlForHG(newsBean.getUrl() ,position ,position ,holder.videoContainer);
                }else{
                    //隐藏该容器，显示播放容器
                    holder.ll_picbg.setVisibility(View.GONE);
                    holder.videoContainer.setVisibility(View.VISIBLE);
                    videoListener.playVideoForHG(newsBean.getUrl(),
                            holder.videoContainer, getBannerViewCount()+getZuiXinTuiJianViewCount()+position ,position ,newsBean.getTitle());
                }
            }
        });
        holder.rl_goon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatService.onEvent(activity, "MoreMatchvideo", "更多比赛视频", 1);
                TCAgent.onEvent(activity, "MoreMatchvideo", "更多比赛视频");
                Intent intent = new Intent(activity, BiSaiShiPinActivity.class);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public ZuiXinZhuanTiViewHolder onCreateZuiXinZhuanTiViewHolder(ViewGroup parent, int position) {
        if (zhuantiBean != null && !zhuantiBean.isEmpty()) {
            LayoutInflater inflate = LayoutInflater.from(parent.getContext());
            View view1 = inflate.inflate(R.layout.item_zhuanti_zuixin, null);
            return new ZuiXinZhuanTiViewHolder(view1);
        }
        return null;
    }

    @Override
    public int getZuiXinZhuanTiViewCount() {
        return zhuantiBean.size();
    }

    @Override
    public void onBindZuiXinZhuanTiViewHolder(ZuiXinZhuanTiViewHolder holder, int position) {
        if (zhuantiBean != null) {
            final HomeZhuanTiBean bean = zhuantiBean.get(position);
            holder.tv_title_name.setText(bean.getTitle());
            final List<AllZhuanTiBean> zhuanti = bean.getNews();

            if (position == 0) {
//                holder.view_line.setVisibility(View.VISIBLE); //view_line_bottom
                holder.view_line_bottom.setVisibility(View.GONE);
            } else if (position == zhuantiBean.size() - 1) {
                holder.view_line_bottom.setVisibility(View.VISIBLE);
//                holder.view_line.setVisibility(View.GONE);
            } else {
                holder.view_line_bottom.setVisibility(View.GONE);
//                holder.view_line.setVisibility(View.GONE);
            }
            switch (zhuanti.size()) {
                case 6:
                case 5:
                    ImageUtils.getPic(zhuanti.get(4).getImg() != null ? zhuanti.get(4).getImg().trim() : "", holder.iv_item_img4, m_Context);
                    holder.tv_title5.setText(zhuanti.get(4).getTitle());
                    holder.tv_title5.setTag(zhuanti.get(4));
                    if ("video".equals(zhuanti.get(4).getType_())) {
                        holder.iv_video_flg4.setVisibility(View.VISIBLE);
                        holder.rl_video_flg4.setVisibility(View.VISIBLE);
                    } else {
                        holder.iv_video_flg4.setVisibility(View.GONE);
                        holder.rl_video_flg4.setVisibility(View.GONE);
                    }
                case 4:
                    ImageUtils.getPic(zhuanti.get(3).getImg() != null ? zhuanti.get(2).getImg().trim() : "", holder.iv_item_img3, m_Context);
                    holder.tv_title4.setText(zhuanti.get(3).getTitle());
                    holder.tv_title4.setTag(zhuanti.get(3));
                    if ("video".equals(zhuanti.get(3).getType_())) {
                        holder.iv_video_flg3.setVisibility(View.VISIBLE);
                        holder.rl_video_flg3.setVisibility(View.VISIBLE);
                    } else {
                        holder.iv_video_flg3.setVisibility(View.GONE);
                        holder.rl_video_flg3.setVisibility(View.GONE);
                    }
                case 3:
                    ImageUtils.getPic(zhuanti.get(2).getImg() != null ? zhuanti.get(2).getImg().trim() : "", holder.iv_item_img2, m_Context);
                    holder.tv_title3.setText(zhuanti.get(2).getTitle());
                    holder.tv_title3.setTag(zhuanti.get(2));
                    if ("video".equals(zhuanti.get(2).getType_())) {
                        holder.iv_video_flg2.setVisibility(View.VISIBLE);
                        holder.rl_video_flg2.setVisibility(View.VISIBLE);
                    } else {
                        holder.iv_video_flg2.setVisibility(View.GONE);
                        holder.rl_video_flg2.setVisibility(View.GONE);
                    }
                case 2:
                    ImageUtils.getPic(zhuanti.get(1).getImg() != null ? zhuanti.get(1).getImg().trim() : "", holder.iv_item_img, m_Context);
                    holder.tv_title2.setText(zhuanti.get(1).getTitle());
                    holder.tv_title2.setTag(zhuanti.get(1));
                    if ("video".equals(zhuanti.get(1).getType_())) {
                        holder.rl_video_flg1.setVisibility(View.VISIBLE);
                        holder.iv_video_flg1.setVisibility(View.VISIBLE);
                    } else {
                        holder.rl_video_flg1.setVisibility(View.GONE);
                        holder.iv_video_flg1.setVisibility(View.GONE);
                    }
                case 1:
                    ImageUtils.getPic(zhuanti.get(0).getImg() != null ? zhuanti.get(0).getImg().trim() : "", holder.iv_big_img, m_Context);
                    holder.tv_title1.setText(zhuanti.get(0).getTitle());
                    holder.tv_title1.setTag(zhuanti.get(0));
                    if ("video".equals(zhuanti.get(0).getType_())) {
                        holder.iv_video_flg.setVisibility(View.VISIBLE);
                        holder.rl_video_flg.setVisibility(View.VISIBLE);
                    } else {
                        holder.iv_video_flg.setVisibility(View.GONE);
                        holder.rl_video_flg.setVisibility(View.GONE);
                    }
                    break;
                default:
                    break;
            }
            if (zhuanti.size() == 1) {
                holder.ll_layout.setVisibility(View.GONE);
                holder.ll_layout2.setVisibility(View.GONE);
            } else if (zhuanti.size() == 2) {
                holder.ll_layout2.setVisibility(View.GONE);

                holder.ll_layout.setVisibility(View.VISIBLE);
                holder.rl_layout1.setVisibility(View.VISIBLE);
                holder.rl_layout2.setVisibility(View.INVISIBLE);
            } else if (zhuanti.size() == 3) {
                holder.ll_layout2.setVisibility(View.GONE);

                holder.ll_layout.setVisibility(View.VISIBLE);
                holder.rl_layout1.setVisibility(View.VISIBLE);
                holder.rl_layout2.setVisibility(View.VISIBLE);
            } else if (zhuanti.size() == 4) {
                holder.rl_layout4.setVisibility(View.GONE);
                holder.rl_layout3.setVisibility(View.VISIBLE);
                holder.ll_layout2.setVisibility(View.VISIBLE);
                holder.ll_layout.setVisibility(View.VISIBLE);
                holder.rl_layout1.setVisibility(View.VISIBLE);
                holder.rl_layout2.setVisibility(View.VISIBLE);
            } else {
                holder.rl_layout4.setVisibility(View.VISIBLE);
                holder.rl_layout3.setVisibility(View.VISIBLE);
                holder.ll_layout2.setVisibility(View.VISIBLE);
                holder.ll_layout.setVisibility(View.VISIBLE);
                holder.rl_layout1.setVisibility(View.VISIBLE);
                holder.rl_layout2.setVisibility(View.VISIBLE);
            }
            holder.rl_clickmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatService.onEvent(activity, "MoreTopic", "更多专题", 1);
                    TCAgent.onEvent(activity, "MoreTopic", "更多专题");
                    Intent intent = new Intent(activity, ZhuanTiDetailActivity.class);
                    intent.putExtra("zhuantiId", bean.getId());
                    intent.putExtra("name", bean.getTitle());
                    activity.startActivity(intent);
                }
            });
            holder.rl_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AllZhuanTiBean bean;
                    if (v instanceof TextView) {
                        bean = (AllZhuanTiBean) v.getTag();
                    } else {
                        bean = (AllZhuanTiBean) v.findViewById(R.id.tv_title1).getTag();
                    }
                    if (bean == null) {
                        return;
                    }
                    String type_ = bean.getType_();
                    Intent intent = null;
                    if ("news".equals(type_)) {
                        intent = new Intent(activity, ZiXunDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("tuji".equals(type_)) {
                        intent = new Intent(activity, TuJiDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("video".equals(type_)) {
                        intent = new Intent(activity, VideoDetailActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else {
                        ToastUtils.showToast(activity, bean.getTitle() + "未知类型");
                    }
                }
            });
            holder.rl_layout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AllZhuanTiBean bean;
                    if (v instanceof TextView) {
                        bean = (AllZhuanTiBean) v.getTag();
                    } else {
                        bean = (AllZhuanTiBean) v.findViewById(R.id.tv_title2).getTag();
                    }
                    if (bean == null) {
                        return;
                    }
                    String type_ = bean.getType_();
                    Intent intent = null;
                    if ("news".equals(type_)) {
                        intent = new Intent(activity, ZiXunDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("tuji".equals(type_)) {
                        intent = new Intent(activity, TuJiDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("video".equals(type_)) {
                        intent = new Intent(activity, VideoDetailActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else {
                        ToastUtils.showToast(activity, bean.getTitle() + "未知类型");
                    }
                }
            });
            holder.rl_layout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AllZhuanTiBean bean;
                    if (v instanceof TextView) {
                        bean = (AllZhuanTiBean) v.getTag();
                    } else {
                        bean = (AllZhuanTiBean) v.findViewById(R.id.tv_title3).getTag();
                    }
                    if (bean == null) {
                        return;
                    }
                    String type_ = bean.getType_();
                    Intent intent = null;
                    if ("news".equals(type_)) {
                        intent = new Intent(activity, ZiXunDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("tuji".equals(type_)) {
                        intent = new Intent(activity, TuJiDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("video".equals(type_)) {
                        intent = new Intent(activity, VideoDetailActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else {
                        ToastUtils.showToast(activity, bean.getTitle() + "未知类型");
                    }
                }
            });
            holder.rl_layout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AllZhuanTiBean bean;
                    if (v instanceof TextView) {
                        bean = (AllZhuanTiBean) v.getTag();
                    } else {
                        bean = (AllZhuanTiBean) v.findViewById(R.id.tv_title4).getTag();
                    }
                    if (bean == null) {
                        return;
                    }
                    String type_ = bean.getType_();
                    Intent intent = null;
                    if ("news".equals(type_)) {
                        intent = new Intent(activity, ZiXunDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("tuji".equals(type_)) {
                        intent = new Intent(activity, TuJiDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("video".equals(type_)) {
                        intent = new Intent(activity, VideoDetailActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else {
                        ToastUtils.showToast(activity, bean.getTitle() + "未知类型");
                    }
                }
            });
            holder.rl_layout4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AllZhuanTiBean bean;
                    if (v instanceof TextView) {
                        bean = (AllZhuanTiBean) v.getTag();
                    } else {
                        bean = (AllZhuanTiBean) v.findViewById(R.id.tv_title5).getTag();
                    }
                    if (bean == null) {
                        return;
                    }
                    String type_ = bean.getType_();
                    Intent intent = null;
                    if ("news".equals(type_)) {
                        intent = new Intent(activity, ZiXunDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("tuji".equals(type_)) {
                        intent = new Intent(activity, TuJiDetailsActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else if ("video".equals(type_)) {
                        intent = new Intent(activity, VideoDetailActivity.class);
                        intent.putExtra("id", bean.getId());
                        activity.startActivity(intent);
                    } else {
                        ToastUtils.showToast(activity, bean.getTitle() + "未知类型");
                    }
                }
            });

        }
    }

    @Override
    public ZuiXinNewsViewHolder onCreateZuiXinNewsViewHolder(ViewGroup parent, int position) {
        if (tuijianHunHeBean != null && tuijianHunHeBean.size() > 0) {
            LayoutInflater inflate = LayoutInflater.from(parent.getContext());
            View view1 = inflate.inflate(R.layout.item_news_zuixin, null);
            return new ZuiXinNewsViewHolder(view1);
        }
        return null;
    }

    @Override
    public int getHunHeViewCount() {
        if (tuijianHunHeBean != null && !tuijianHunHeBean.isEmpty()) {
            return tuijianHunHeBean.size();
        }
        return 0;
    }

    @Override
    public void onBindZuiXinNewsViewHolder(ZuiXinNewsViewHolder holder, final int position) {
        try {
            if (tuijianHunHeBean != null && !tuijianHunHeBean.isEmpty()) {
                if (position == 0) {
                    holder.view_line.setVisibility(View.VISIBLE);
                } else {
                    holder.view_line.setVisibility(View.GONE);
                }
                final AllHomeHunHeBean hunHeBean = tuijianHunHeBean.get(position);
                holder.tv_title.setText(hunHeBean.getTitle() + "");
                holder.tv_kanguo.setText(hunHeBean.getOpen_times() + "人看过");
                holder.tv_time.setText(TimeUtiles.getTimeeForTuiJ(hunHeBean.getTime()));
                holder.setIsRecyclable(false);

                if (!StringUtils.isEmpty(hunHeBean.getFengmiantu())) {
                    ImageUtils.getPic(hunHeBean.getFengmiantu(), holder.iv_news_img, m_Context);
                } else if (!StringUtils.isEmpty(hunHeBean.getImg())) {
                    ImageUtils.getPic(hunHeBean.getImg(), holder.iv_news_img, m_Context);
                } else {
                    if (hunHeBean.getImg_contents() != null && !hunHeBean.getImg_contents().isEmpty()) {
                        for (int i = 0; i < hunHeBean.getImg_contents().size(); i++) {
                            if (!StringUtils.isEmpty(hunHeBean.getImg_contents().get(i))) {
                                ImageUtils.getPic(hunHeBean.getImg_contents().get(i), holder.iv_news_img, m_Context);
                                break;
                            }
                        }
                    }
                }
                holder.llyt_toutiao_news.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, ZiXunDetailsActivity.class);
                        intent.putExtra("id", hunHeBean.getId());
                        activity.startActivity(intent);
//                            detalisCallBack.getDetalisData(TYPE_NEWS, TYPE_NEWS, position);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ZuiXinPicGalleryViewHolder onCreateZuiXinPicGalleryViewHolder(ViewGroup parent, int position) {
        if (tuijianHunHeBean != null && tuijianHunHeBean.size() > 0) {
            LayoutInflater inflate = LayoutInflater.from(parent.getContext());
            View view1 = inflate.inflate(R.layout.item_picgallery_zuixin, null);
            return new ZuiXinPicGalleryViewHolder(view1);
        }
        return null;
    }

    @Override
    public void onBindZuiXinPicGalleryViewHolder(ZuiXinPicGalleryViewHolder holder, final int position) {
        if (tuijianHunHeBean != null && tuijianHunHeBean.size() > 0) {
            if (position == 0) {
                holder.view_line.setVisibility(View.VISIBLE);
            } else {
                holder.view_line.setVisibility(View.GONE);
            }
            final AllHomeHunHeBean newsBean = tuijianHunHeBean.get(position);
            holder.tv_title.setText(newsBean.getTitle() + "");
            holder.tv_kanguo.setText(newsBean.getOpen_times()+"次");
            if (newsBean.getTime() != null) {
                holder.tv_time.setText(TimeUtiles.getTimeeForTuiJ(newsBean.getTime()));
            }else{
                holder.tv_time.setText("");
            }
//                ImageUtils.getPic(newsBean.getImg(), holder.iv_news_img, m_Context);
            if (newsBean.getImgs() != null && !newsBean.getImgs().isEmpty()) {
                for (int i = 0; i < newsBean.getImgs().size(); i++) {
                    if (!StringUtils.isEmpty(newsBean.getImgs().get(i).getUrl())) {
                        ImageUtils.getPic(newsBean.getImgs().get(i).getUrl(), holder.iv_news_img, m_Context);
                        break;
                    }
                }
            }
            if (newsBean.getImgs() != null) {
                holder.tv_count.setText(newsBean.getImgs().size() + "");
            }

            holder.tv_count.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.rl_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, TuJiDetailsActivity.class);
                    intent.putExtra("id", newsBean.getId());
                    activity.startActivity(intent);
//                        detalisCallBack.getDetalisData(TYPE_TUJI, TYPE_TUJI, position);
                }
            });
        }
    }

    @Override
    public ZuiXinNewsVideoViewHolder onCreateZuiXinNewsVideoViewHolder(ViewGroup parent, int position) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View newsvideoView = inflate.inflate(R.layout.item_zuixin_video, null);
        return new ZuiXinNewsVideoViewHolder(newsvideoView);
    }

    @Override
    public void onBindZuiXinNewsVideoViewHolder(final ZuiXinNewsVideoViewHolder holder, final int position) {
        if (tuijianHunHeBean != null && tuijianHunHeBean.size() > 0) {
            if (position == 0) {
                holder.view_line.setVisibility(View.VISIBLE);
            } else {
                holder.view_line.setVisibility(View.GONE);
            }

            final AllHomeHunHeBean newsBean = tuijianHunHeBean.get(position);
            holder.tv_title.setText(newsBean.getTitle() + "");
            if (newsBean.getTime() != null) {
                holder.tv_time.setText(TimeUtiles.getTimeeForTuiJ(newsBean.getTime()));
            }
            if (newsBean.getImg() != null) {
                ImageUtils.getPic(newsBean.getImg(), holder.iv_video_bg, m_Context);
            } else {

            }
            if (TextUtils.isEmpty(newsBean.getType_name())) {
                holder.tv_label.setVisibility(View.INVISIBLE);
            } else {
                holder.tv_label.setVisibility(View.VISIBLE);
                holder.tv_label.setText(newsBean.getType_name());

            }
            holder.tv_dianzan.setText(newsBean.getOpen_times() + "人看过");

            //视频播放
            holder.ic_net_warn.setVisibility(View.GONE);
            if (!newsBean.isSelect() || holder.videoContainer.getVisibility() == View.GONE) {
                holder.videoContainer.removeAllViews();
                holder.videoContainer.setVisibility(View.GONE);
                holder.rlyt_video_bg.setVisibility(View.VISIBLE);
            } else {
                //true
                holder.rlyt_video_bg.setVisibility(View.GONE);
                holder.videoContainer.setVisibility(View.VISIBLE);
            }
            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, VideoDetailActivity.class);
                    intent.putExtra("id", newsBean.getId());
                    activity.startActivity(intent);
                }
            });
            holder.iv_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (videoListener != null) {
                        StatService.onEvent(m_Context, "VedioPlayTimes", "视频播放次数", 1);
                        TCAgent.onEvent(m_Context, "VedioPlayTimes", "视频播放次数");
                        if (!NetWorkUtils.isWifiConnected(m_Context)) {
                            holder.ic_net_warn.setVisibility(View.VISIBLE);
                        } else {
                            if(newsBean.getVideos().getType().equals("1")){
                                holder.rlyt_video_bg.setVisibility(View.GONE);
                                holder.videoContainer.setVisibility(View.VISIBLE);
                                videoListener.loadWebUrl(newsBean.getVideos().getUrl() ,position ,position ,holder.videoContainer);
                            }else{
                                //隐藏该容器，显示播放容器
                                holder.rlyt_video_bg.setVisibility(View.GONE);
                                holder.videoContainer.setVisibility(View.VISIBLE);
                                videoListener.playVideo(newsBean.getVideos().getUrl(),
                                        holder.videoContainer, getBannerViewCount()+getZuiXinTuiJianViewCount()+getZuiXinZhuanTiViewCount()+getZuiXinVedioViewCount()+position ,position ,newsBean.getTitle());
                            }
                        }
                    }
                }
            });
            holder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatService.onEvent(m_Context, "VedioPlayTimes", "视频播放次数", 1);
                    TCAgent.onEvent(m_Context, "VedioPlayTimes", "视频播放次数");
                    holder.ic_net_warn.setVisibility(View.GONE);
                    //隐藏该容器，显示播放容器
                    if(newsBean.getVideos().getType().equals("1")){
                        holder.rlyt_video_bg.setVisibility(View.GONE);
                        holder.videoContainer.setVisibility(View.VISIBLE);
                        videoListener.loadWebUrl(newsBean.getVideos().getUrl() ,position ,position ,holder.videoContainer);
                    }else{
                        //隐藏该容器，显示播放容器
                        holder.rlyt_video_bg.setVisibility(View.GONE);
                        holder.videoContainer.setVisibility(View.VISIBLE);
                        videoListener.playVideo(newsBean.getVideos().getUrl(),
                                holder.videoContainer, getBannerViewCount()+getZuiXinTuiJianViewCount()+getZuiXinZhuanTiViewCount()+getZuiXinVedioViewCount()+position ,position ,newsBean.getTitle());
                    }
                }
            });

        }
    }

    @Override
    public void setData(List<AllHomeHunHeBean> list) {
        if (list != null && list.size() > 0) {
            tuijianHunHeBean.clear();
            tuijianHunHeBean.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void setTuijianData(List<SaichengBean> list, String num) {
        this.tuijianNum = num;
        if (list != null && list.size() > 0) {
            tuijianZhiboBeen.clear();
            tuijianZhiboBeen.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void setBannerData(List<HomeBannerImageBean> data2EntityList2) {
        data2EntityList.clear();
        data2EntityList.addAll(data2EntityList2);
        notifyDataSetChanged();
    }

    @Override
    public void setHuifangData(List<HomeZuixinHuifangBean> list) {
        if (list != null && list.size() > 0) {
            huifangBean.clear();
            huifangBean.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void setZhuanTiData(List<HomeZhuanTiBean> list) {
        if (list != null && list.size() > 0) {
            zhuantiBean.clear();
            zhuantiBean.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public MainPageTuiJianBean getData() {
        return mainPageTuiJianBean;
    }

    @Override
    public void loadNextPageData(List<AllHomeHunHeBean> tuijianHunHeBean2) {
        if (tuijianHunHeBean == null) {
            return;
        }
        tuijianHunHeBean.addAll(tuijianHunHeBean2);
        notifyDataSetChanged();
    }

    @Override
    public List<AllHomeHunHeBean> getNewsData() {
        return tuijianHunHeBean;
    }

    @Override
    public List<HomeZuixinHuifangBean> getHuiFangData() {
        return huifangBean;
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
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    public interface VideoListener {
        //混合播放
        void playVideo(String url, RelativeLayout videoContainer, int position, int oldposition ,String title);
        //回顾播放
        void playVideoForHG(String url, RelativeLayout videoContainer, int position, int oldposition ,String title);

        //跳转详情页
        void openVideoDetails(String id);
        //混合播放
        void loadWebUrl(String url , int position, int oldposition ,RelativeLayout videoContainer);
        //回顾播放
        void loadWebUrlForHG(String url , int position, int oldposition ,RelativeLayout videoContainer);
    }
}
