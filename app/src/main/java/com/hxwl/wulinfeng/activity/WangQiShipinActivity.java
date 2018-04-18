package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.fastjson.JSON;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.DatuAdapter;
import com.hxwl.wulinfeng.adapter.NianFenListAdapter;
import com.hxwl.wulinfeng.adapter.WangQiSPGridAdapter;
import com.hxwl.wulinfeng.adapter.YueFenListAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.BiSaiShiPinBean;
import com.hxwl.wulinfeng.bean.Month;
import com.hxwl.wulinfeng.bean.NianFenYueBean;
import com.hxwl.wulinfeng.bean.WQShiPinBean;
import com.hxwl.wulinfeng.bean.WangQiSPBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.NianFenComp;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.util.YueFenComp;
import com.hxwl.wulinfeng.view.GridViewWithHeaderAndFooter;
import com.hxwl.wulinfeng.view.MyGrideview;
import com.hxwl.wulinfeng.view.XCRecyclerView;
import com.hxwl.wulinfeng.view.XRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

/**
 * Created by Allen on 2017/6/22.
 * 往期视频界面
 */
public class WangQiShipinActivity extends BaseActivity {

    private RecyclerView rv_years;
    private RecyclerView rv_month;
    private RecyclerView rv_video;
    private String saishiId;
    private NianFenListAdapter nianFenListAdapter;
    private YueFenListAdapter yueFenListAdapter;
    private List<NianFenYueBean> listData = new ArrayList<>();
    private List<Month> yueFenList = new ArrayList<>();

    private int page = 1;
    private boolean isRefresh;
    private String yearMonth;
    private String DatuYearMonth;

    private List<WQShiPinBean> listDatas = new ArrayList<>();
    private WangQiSPBean bean;
    private CommonSwipeRefreshLayout common_layout;
    //大图头布局
    private View header;
    private ImageView iv_fengmiantu;
    private TextView time;
    private TextView title;
    private WQShiPinBean headBean; //头布局的数据
    //    private WangQiSPGridAdapter wangQiSPGridAdapter;
    //动态添加的adapter；
    List<DelegateAdapter.Adapter> dynamicAdapterList;
    private DelegateAdapter delegateAdapter;
    private WangQiSPGridAdapter zhiboAdapter;
    private DatuAdapter datuAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wangqisp_activity);
        AppUtils.setTitle(WangQiShipinActivity.this);
        saishiId = getIntent().getStringExtra("saishiId");
        initView();
        initDataY();
    }

    private void initData(final int page) {

    }

    private void initDataY() { //初始化年月信息
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                WangQiShipinActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    listData.clear();

                    NianFenYueBean gengzao = new NianFenYueBean();
                    gengzao.setYear("更早");
                    List<String> gengduoyue = new ArrayList<String>();
                    gengduoyue.add("更多");
                    gengzao.setMonth(gengduoyue);

                    listData.addAll(JSON.parseArray(result.getData(), NianFenYueBean.class));
                    Collections.sort(listData, new NianFenComp());
                    for (NianFenYueBean data : listData) {
                        Collections.sort(data.getMonth(), new YueFenComp());
                    }
                    listData.add(gengzao);
                    for (NianFenYueBean data : listData) {
                        if (data.getMonths() != null) {
                            data.getMonths().clear();
                        } else {
                            data.setMonths(new ArrayList<Month>());
                        }
                        for (String yue : data.getMonth()) {
                            Month month = new Month();
                            month.setMonth(yue);
                            data.getMonths().add(month);
                        }
                    }

                    if (listData != null && listData.size() > 0) {
                        listData.get(0).setIsselect(true);
                        if (listData.get(0).getMonths() != null && listData.get(0).getMonths().size() > 0) {
                            listData.get(0).getMonths().get(0).setIsselect(true);
                            yueFenList.addAll(listData.get(0).getMonths());
                        }
                    }
                    nianFenListAdapter.notifyDataSetChanged();
                    yueFenListAdapter.notifyDataSetChanged();
                    if (listData.size() > 0 && listData.get(0) != null && listData.get(0).getMonths().size() > 0) {
                        DatuYearMonth = listData.get(0).getYear() + "-" + listData.get(0).getMonths().get(0).getMonth();
                    }
                    for (NianFenYueBean data : listData) {
                        if (data.getYear().equals("更早")) {
                            yearMonth = "gengzao";
                            break;
                        }
                        if (data.getMonth() != null && data.getMonth().size() > 0) {
                            yearMonth = data.getYear() + "-" + data.getMonth().get(0);
//                            data.getMonths().get(0).setIsdatu(true);
                            break;
                        }
                    }
                    initData(page);
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {
                    UIUtils.showToast("服务器异常");
                }
            }
        }, "method=" + NetUrlUtils.video_wangqi_year + saishiId);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("saishiId", saishiId);
        map.put("method", NetUrlUtils.video_wangqi_year);
        tasker.execute(map);

    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        if (user_icon != null && user_icon instanceof ImageView) {
            findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        header = View.inflate(WangQiShipinActivity.this, R.layout.wangqisp_head, null);
//        iv_fengmiantu = (ImageView) header.findViewById(R.id.iv_fengmiantu);
//        time = (TextView) header.findViewById(R.id.time);
//        title = (TextView) header.findViewById(R.id.title);
        rv_video = (RecyclerView) findViewById(R.id.rv_video);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WangQiShipinActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_years = (RecyclerView) findViewById(R.id.rv_years);
        rv_years.setLayoutManager(linearLayoutManager);
//        nianFenListAdapter = new NianFenListAdapter(WangQiShipinActivity.this, listData, nianClickListener);
        rv_years.setAdapter(nianFenListAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(WangQiShipinActivity.this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_month = (RecyclerView) findViewById(R.id.rv_month);
        rv_month.setLayoutManager(linearLayoutManager2);
        yueFenListAdapter = new YueFenListAdapter(WangQiShipinActivity.this, yueFenList, yueClickListener);
        rv_month.setAdapter(yueFenListAdapter);

        VirtualLayoutManager vm = new VirtualLayoutManager(WangQiShipinActivity.this);
        rv_video.setLayoutManager(vm);
        //设置回收复用池
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        rv_video.setRecycledViewPool(recycledViewPool);
        recycledViewPool.setMaxRecycledViews(RecyclerView.VERTICAL, 10);
        rv_video.setItemAnimator(new DefaultItemAnimator());
        delegateAdapter = new DelegateAdapter(vm, false);
//        rv_video.addHeaderView(header);
        rv_video.setAdapter(delegateAdapter);
        dynamicAdapterList = new LinkedList<>();

        //大图adapter
        datuAdapter = new DatuAdapter(WangQiShipinActivity.this, new LinearLayoutHelper());
        datuAdapter.setOnItemClickListener(new DatuAdapter.onItemClickListener() {
            @Override
            public void onImageClick(View view, int position) {
                WQShiPinBean dataBean = null;
                if (view instanceof RelativeLayout) {
                    dataBean = (WQShiPinBean) view.getTag();
                } else {
                    dataBean = (WQShiPinBean) view.findViewById(R.id.rl_item).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                //TODO
                //TODO  调视频详情
                Intent intent = new Intent(WangQiShipinActivity.this, HuiGuDetailActivity.class);
                intent.putExtra("saichengId", dataBean.getSaicheng_id());
                intent.putExtra("saishiId", dataBean.getSaishi_id());
                intent.putExtra("name", dataBean.getName());
                intent.putExtra("pageType", "bisaishipin");
                startActivity(intent);
            }
        });
        dynamicAdapterList.add(datuAdapter);
        //gridviewadapter
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setVGap(0);
        gridLayoutHelper.setAutoExpand(false);
        zhiboAdapter = new WangQiSPGridAdapter(WangQiShipinActivity.this, gridLayoutHelper);
        zhiboAdapter.setOnItemClickListener(new WangQiSPGridAdapter.onItemClickListener() {
            @Override
            public void myOnItemClickListener(View view, int position) {
                WQShiPinBean dataBean = null;
                if (view instanceof RelativeLayout) {
                    dataBean = (WQShiPinBean) view.getTag();
                } else {
                    dataBean = (WQShiPinBean) view.findViewById(R.id.rl_item).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                //TODO  调视频详情
                Intent intent = new Intent(WangQiShipinActivity.this, HuiGuDetailActivity.class);
                intent.putExtra("bean", dataBean);
                intent.putExtra("pageType", "bisaishipin");
                startActivity(intent);

            }
        });
        dynamicAdapterList.add(zhiboAdapter);
        delegateAdapter.setAdapters(dynamicAdapterList);

        common_layout = (CommonSwipeRefreshLayout) findViewById(R.id.common_layout);
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

    private View.OnClickListener nianClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            NianFenYueBean bean = null;
            if (view instanceof TextView) {
                bean = (NianFenYueBean) view.getTag();
            } else {
                bean = (NianFenYueBean) view.findViewById(R.id.tv_nianfen).getTag();
            }
            if (bean == null) {
                return;
            }
            for (NianFenYueBean info : listData) {
                info.setIsselect(false);
            }
            bean.setIsselect(true);
            nianFenListAdapter.notifyDataSetChanged();
            yueFenList.clear();

            for (NianFenYueBean data : listData) {
                if (data.getMonths() != null) {
                    data.getMonths().clear();
                } else {
                    data.setMonths(new ArrayList<Month>());
                }
                for (String yue : data.getMonth()) {
                    Month month = new Month();
                    month.setMonth(yue);
                    data.getMonths().add(month);
                }
            }
            bean.getMonths().get(0).setIsselect(true);
            yueFenList.addAll(bean.getMonths());
            yueFenListAdapter.notifyDataSetChanged();
            //视频请求
            for (NianFenYueBean data : listData) {
                if (data.getYear().equals(bean.getYear())) {//点的就是这一年
                    if (bean.getYear().equals("更早")) {
                        yearMonth = "gengzao";
                        break;
                    } else {
                        yearMonth = data.getYear() + "-" + data.getMonth().get(0);
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
            Month yue = null;
            if (view instanceof TextView) {
                yue = (Month) view.getTag();
            } else {
                yue = (Month) view.findViewById(R.id.tv_nianfen).getTag();
            }
            if (yue == null) {
                return;
            }
            for (NianFenYueBean info : listData) {
                for (Month info2 : info.getMonths()) {
                    info2.setIsselect(false);
                }
            }
            yue.setIsselect(true);
            yueFenListAdapter.notifyDataSetChanged();
            //视频请求
            for (NianFenYueBean data : listData) {
                if (data.isselect()) {
                    if (data.getYear().equals("更早")) {
                        yearMonth = "gengzao";
                        break;
                    } else {
                        yearMonth = data.getYear() + "-" + yue.getMonth();
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
