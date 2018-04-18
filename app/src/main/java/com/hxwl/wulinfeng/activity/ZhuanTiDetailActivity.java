package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.hxwl.wulinfeng.adapter.WangQiSPGridAdapter;
import com.hxwl.wulinfeng.adapter.ZhuanTiDatuAdapter;
import com.hxwl.wulinfeng.adapter.ZhuanTiGridAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.TuJiListDatilsBean;
import com.hxwl.wulinfeng.bean.WQShiPinBean;
import com.hxwl.wulinfeng.bean.ZhuanTiDetailBean;
import com.hxwl.wulinfeng.bean.ZhuanTiDetailPackBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/6/28.
 * 专题详情界面(更多专题)
 */
public class ZhuanTiDetailActivity extends BaseActivity {

    private String zhuantiId;//专题总编号
    private String name;
    private String lastNewsId;//加载编号
    private List<ZhuanTiDetailBean> listData = new ArrayList<>();
    private int page = 1;
    public boolean isRefresh = true;

    private CommonSwipeRefreshLayout common_layout;
    private RecyclerView rv_video;
    //动态添加的adapter；
    List<DelegateAdapter.Adapter> dynamicAdapterList;
    private DelegateAdapter delegateAdapter;
    private ZhuanTiDatuAdapter datuAdapter;
    private ZhuanTiDetailBean headBean;
    private ZhuanTiGridAdapter zhuanTiAdapter;
    private ZhuanTiDetailPackBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuantidetail_activity);
        AppUtils.setTitle(ZhuanTiDetailActivity.this);
        zhuantiId = getIntent().getStringExtra("zhuantiId");
        name = getIntent().getStringExtra("name");
        initView();
        initData(page);
    }

    private void initData(final int page) { //zhuanti_detail
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                ZhuanTiDetailActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    bean = JSON.parseObject(result.getData(), ZhuanTiDetailPackBean.class);
                    lastNewsId = bean.getLastNewsId();
                    if (isRefresh) {
                        listData.clear();
                        listData.addAll(bean.getNews());
                    } else {
                        listData.addAll(bean.getNews());
                    }

                    if (1 == page) {
                        headBean = listData.get(0);
                        listData.remove(0);
                        datuAdapter.setData(headBean);
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    zhuanTiAdapter.setData(listData);
                } else if (result != null && result.getStatus().equals("empty")) {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    UIUtils.showToast("没有更多了...");
                } else {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    UIUtils.showToast("服务器异常");
                }
            }
        }, "method=" + NetUrlUtils.zhuanti_detail + page + zhuantiId );
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("zhuantiId", zhuantiId);
        map.put("page", page);
        map.put("lastNewsId", lastNewsId);
        map.put("method", NetUrlUtils.zhuanti_detail);
        tasker.execute(map);

//        Map<String, Object> map = new HashMap<>();
//        map.put("zhuantiId", zhuantiId);
//        map.put("page", page);
//        map.put("lastNewsId", lastNewsId);
//        try {
//            AppClient.okhttp_post_Asyn(NetUrlUtils.zhuanti_detail, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    ZhuanTiDetailActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            common_layout.setRefreshing(false);
//                            common_layout.setLoadMore(false);
//                            UIUtils.showToast("服务器异常");
//                        }
//                    });
//                }
//
//                @Override
//                public void success(ResultPacket result) {
//                    if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                        bean = JSON.parseObject(result.getData(), ZhuanTiDetailPackBean.class);
//                        lastNewsId = bean.getLastNewsId();
//                        if (isRefresh) {
//                            listData.clear();
//                            listData.addAll(bean.getNews());
//                        } else {
//                            listData.addAll(bean.getNews());
//                        }
//
//                        ZhuanTiDetailActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (1 == page) {
//                                    headBean = listData.get(0);
//                                    listData.remove(0);
//                                    datuAdapter.setData(headBean);
//                                }
//                                common_layout.setRefreshing(false);
//                                common_layout.setLoadMore(false);
//                                zhuanTiAdapter.setData(listData);
//                            }
//                        });
//                    } else if (result.getStatus() != null && result.getStatus().equals("empty")) {
//                        ZhuanTiDetailActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                common_layout.setRefreshing(false);
//                                common_layout.setLoadMore(false);
//                                UIUtils.showToast("没有更多了...");
//                            }
//                        });
//
//                    }
//                }
//            });
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(name);
        rv_video = (RecyclerView) findViewById(R.id.rv_video);
        VirtualLayoutManager vm = new VirtualLayoutManager(ZhuanTiDetailActivity.this);
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
        datuAdapter = new ZhuanTiDatuAdapter(ZhuanTiDetailActivity.this, new LinearLayoutHelper());
        datuAdapter.setOnItemClickListener(new ZhuanTiDatuAdapter.onItemClickListener() {
            @Override
            public void onImageClick(View view, int position) {
                ZhuanTiDetailBean dataBean = null;
                if (view instanceof RelativeLayout) {
                    dataBean = (ZhuanTiDetailBean) view.getTag();
                } else {
                    dataBean = (ZhuanTiDetailBean) view.findViewById(R.id.rl_item).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                //TODO
                String type = dataBean.getType_();
                Intent intent = null;
                if ("video".equals(type)) {
                    intent = new Intent(ZhuanTiDetailActivity.this, VideoDetailActivity.class);
                    intent.putExtra("id", dataBean.getId());
                    startActivity(intent);
                } else if ("tuji".equals(type)) {
                    intent = new Intent(ZhuanTiDetailActivity.this, TuJiDetailsActivity.class);
                    intent.putExtra("id", dataBean.getId());
                    startActivity(intent);
                } else if ("news".equals(type)) {//news
                    intent = new Intent(ZhuanTiDetailActivity.this, ZiXunDetailsActivity.class);
                    intent.putExtra("id", dataBean.getId());
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });
        dynamicAdapterList.add(datuAdapter);
        //gridviewadapter
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setVGap(0);
        gridLayoutHelper.setAutoExpand(false);
        zhuanTiAdapter = new ZhuanTiGridAdapter(ZhuanTiDetailActivity.this, gridLayoutHelper);
        zhuanTiAdapter.setOnItemClickListener(new ZhuanTiGridAdapter.onItemClickListener() {
            @Override
            public void myOnItemClickListener(View view, int position) {
                ZhuanTiDetailBean dataBean = null;
                if (view instanceof RelativeLayout) {
                    dataBean = (ZhuanTiDetailBean) view.getTag();
                } else {
                    dataBean = (ZhuanTiDetailBean) view.findViewById(R.id.rl_item).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                //TODO
                String type = dataBean.getType_();
                Intent intent = null;
                if ("video".equals(type)) {
                  intent = new Intent(ZhuanTiDetailActivity.this, VideoDetailActivity.class);
                    intent.putExtra("id", dataBean.getId());
                    startActivity(intent);
                } else if ("tuji".equals(type)) {
                    intent = new Intent(ZhuanTiDetailActivity.this, TuJiDetailsActivity.class);
                    intent.putExtra("id", dataBean.getId());
                    startActivity(intent);
                } else if ("news".equals(type)) {//news
                    intent = new Intent(ZhuanTiDetailActivity.this, ZiXunDetailsActivity.class);
                    intent.putExtra("id", dataBean.getId());
                    startActivity(intent);
                } else {
                    return;
                }
            }
        });
        dynamicAdapterList.add(zhuanTiAdapter);
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
}
