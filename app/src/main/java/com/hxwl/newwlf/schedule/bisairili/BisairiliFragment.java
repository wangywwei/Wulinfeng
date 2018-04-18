package com.hxwl.newwlf.schedule.bisairili;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.google.gson.Gson;
import com.hxwl.common.customview.GridSpacingItemDecoration;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.BisairiliVideoBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.adapter.NianFenListAdapter;
import com.hxwl.wulinfeng.adapter.WangQiSPGridAdapter;
import com.hxwl.wulinfeng.adapter.YueFenListAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.Month;
import com.hxwl.wulinfeng.bean.WangQiSPBean;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;


public class BisairiliFragment extends BaseFragment {

    private View view;
    private RecyclerView rv_years;
    private RecyclerView rv_month;
    private RecyclerView rv_video;
    private String saishiId = "13";
    private NianFenListAdapter nianFenListAdapter;
    private YueFenListAdapter yueFenListAdapter;
    private List<NianBean.DataBean> listData = new ArrayList<>();
    private List<Month> yueFenList = new ArrayList<>();
    private int page = 1;
    private boolean isRefresh;
    private String yearMonth;
    private String DatuYearMonth;

    private List<BisairiliVideoBean.DataBean> listDatas = new ArrayList<>();
    private WangQiSPBean bean;
    private CommonSwipeRefreshLayout common_layout;
    //大图头布局
    private View header;
    private ImageView iv_fengmiantu;
    private TextView time;
    private TextView title;

    //    private WangQiSPGridAdapter wangQiSPGridAdapter;
    //动态添加的adapter；
    List<DelegateAdapter.Adapter> dynamicAdapterList;
    private DelegateAdapter delegateAdapter;
    private WangQiSPGridAdapter zhiboAdapter;

    @Override
    public void onResume() {
        super.onResume();
        isRefresh = true;
        initDataY();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.wangqisp_activity, container, false);
            initView(view);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        return view;
    }

    private void initDataY() { //初始化年月信息

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_SGETSCHEDULEDATELIST)
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                NianBean bean = gson.fromJson(response, NianBean.class);
                                if (bean.getCode().equals("1000")) {
                                    listData.clear();
                                    yueFenList.clear();
                                    listData.addAll(bean.getData());


                                    for (int i = 0; i < bean.getData().get(0).getMonthList().size(); i++) {
                                        Month month = new Month();
                                        month.setMonth(bean.getData().get(0).getMonthList().get(i));
                                        yueFenList.add(month);
                                    }
                                    listData.get(0).setSelect(true);
                                    yueFenList.get(0).setIsselect(true);
                                    nianFenListAdapter.notifyDataSetChanged();
                                    yueFenListAdapter.notifyDataSetChanged();


                                    yearMonth = bean.getData().get(0).getYear() + bean.getData().get(0).getMonthList().get(0);
                                    initData(page);
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }


                    }
                });


    }

    private void initView(View view) {
        ImageView user_icon = (ImageView) view.findViewById(R.id.user_icon);
        if (user_icon != null && user_icon instanceof ImageView) {
            view.findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        header = View.inflate(getActivity(), R.layout.wangqisp_head, null);
//        iv_fengmiantu = (ImageView) header.findViewById(R.id.iv_fengmiantu);
//        time = (TextView) header.findViewById(R.id.time);
//        title = (TextView) header.findViewById(R.id.title);
        rv_video = (RecyclerView) view.findViewById(R.id.rv_video);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_years = (RecyclerView) view.findViewById(R.id.rv_years);
        rv_years.setLayoutManager(linearLayoutManager);
        nianFenListAdapter = new NianFenListAdapter(getActivity(), listData, nianClickListener);
        rv_years.setAdapter(nianFenListAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_month = (RecyclerView) view.findViewById(R.id.rv_month);
        rv_month.setLayoutManager(linearLayoutManager2);
        yueFenListAdapter = new YueFenListAdapter(getActivity(), yueFenList, yueClickListener);
        rv_month.setAdapter(yueFenListAdapter);

        VirtualLayoutManager vm = new VirtualLayoutManager(getActivity());
        rv_video.setLayoutManager(vm);
        //设置回收复用池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        rv_video.setRecycledViewPool(recycledViewPool);
        recycledViewPool.setMaxRecycledViews(RecyclerView.VERTICAL, 10);
        rv_video.setItemAnimator(new DefaultItemAnimator());
        delegateAdapter = new DelegateAdapter(vm, false);
//        rv_video.addHeaderView(header);
        int spanCount = 2;//跟布局里面的spanCount属性是一致的
        int spacing = 16;//每一个矩形的间距
        boolean includeEdge = false;//如果设置成false那边缘地带就没有间距s
        //设置每个item间距
        rv_video.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        rv_video.setAdapter(delegateAdapter);
        dynamicAdapterList = new LinkedList<>();

        //gridviewadapter
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setVGap(0);
        gridLayoutHelper.setAutoExpand(false);
        zhiboAdapter = new WangQiSPGridAdapter(getActivity(), gridLayoutHelper);
        zhiboAdapter.setOnItemClickListener(new WangQiSPGridAdapter.onItemClickListener() {
            @Override
            public void myOnItemClickListener(View view, int position) {
                BisairiliVideoBean.DataBean dataBean = null;
                if (view instanceof RelativeLayout) {
                    dataBean = (BisairiliVideoBean.DataBean) view.getTag();
                } else {
                    dataBean = (BisairiliVideoBean.DataBean) view.findViewById(R.id.rl_item).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                //TODO  调视频详情

                startActivity(HuiGuDetailActivity.getIntent(getActivity(), dataBean.getScheduleId() + "", 0));

            }
        });
        dynamicAdapterList.add(zhiboAdapter);
        delegateAdapter.setAdapters(dynamicAdapterList);

        common_layout = (CommonSwipeRefreshLayout) view.findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                dynamicAdapterList.clear();
                page = 1;
                isRefresh = true;
                initData(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initData(page);
            }
        });

    }

    private void initData(final int page) {
        if (StringUtils.isBlank(yearMonth)) {
            common_layout.setRefreshing(false);
            common_layout.setLoadMore(false);
            return;
        }

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_SCHEDULECALENDAR)
                .addParams("scheduleMonth", yearMonth)
                .addParams("pageNumber", page + "")
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("pageSize", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                BisairiliVideoBean bean = gson.fromJson(response, BisairiliVideoBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if (isRefresh) {
                                        dynamicAdapterList.clear();
                                        listDatas.clear();
                                        listDatas.addAll(bean.getData());
                                    } else {
                                        listDatas.addAll(bean.getData());
                                    }
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    zhiboAdapter.setData(listDatas);
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }else {
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

    }

    private View.OnClickListener nianClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isRefresh=true;
            NianBean.DataBean bean = null;
            if (view instanceof TextView) {
                bean = (NianBean.DataBean) view.getTag();
            } else {
                bean = (NianBean.DataBean) view.findViewById(R.id.tv_nianfen).getTag();
            }
            if (bean == null) {
                return;
            }
            for (NianBean.DataBean info : listData) {
                info.setSelect(false);
            }
            yueFenList.clear();
            try {
                for (int i = 0; i < bean.getMonthList().size(); i++) {
                    Month month = new Month();
                    month.setMonth(bean.getMonthList().get(i));
                    yueFenList.add(month);
                }
            } catch (Exception o) {

            }
            yueFenList.get(0).setIsselect(true);
            bean.setSelect(true);
            nianFenListAdapter.notifyDataSetChanged();
            yueFenListAdapter.notifyDataSetChanged();


            //视频请求
            for (NianBean.DataBean data : listData) {
                if (data.getYear().equals(bean.getYear())) {//点的就是这一年
                    if (bean.getYear().equals("更早")) {
                        yearMonth = "gengzao";
                        break;
                    } else {
                        yearMonth = data.getYear() + data.getMonthList().get(0);
                        break;
                    }
                }
            }
            page = 1;
            isRefresh = true;
            initData(page);
        }
    };
    private View.OnClickListener yueClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isRefresh=true;
            Month yue = null;
            if (view instanceof TextView) {
                yue = (Month) view.getTag();
            } else {
                yue = (Month) view.findViewById(R.id.tv_nianfen).getTag();
            }
            if (yue == null) {
                return;
            }
            for (Month info : yueFenList) {
                info.setIsselect(false);
            }

            yue.setIsselect(true);
            yueFenListAdapter.notifyDataSetChanged();
            //视频请求
            for (NianBean.DataBean data : listData) {
                if (data.isSelect()) {
                    if (data.getYear().equals("更早")) {
                        yearMonth = "gengzao";
                        break;
                    } else {
                        yearMonth = data.getYear() + "" + yue.getMonth();
                        break;
                    }
                }
            }
            page = 1;
            isRefresh = true;
            initData(page);
        }
    };


}
