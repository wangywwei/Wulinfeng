package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.SaiShiAdapter;
import com.hxwl.wulinfeng.adapter.WuZhanAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.SaiShiYuYueBean;
import com.hxwl.wulinfeng.bean.WuZhanLMBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hxwl.wulinfeng.R.layout.frag_tuji;

/**
 * Created by Allen on 2017/6/14.
 * 武战联盟 界面  改名  俱乐部
 */
public class WuZhanLMActivity extends BaseActivity {
    private ListView lv_listview;
    private CommonSwipeRefreshLayout common_layout;
    private List<WuZhanLMBean> listData = new ArrayList<>();

    private int page = 1;
    private boolean isRefresh;
    private WuZhanAdapter wuZhanAdapter;
    private EmptyViewHelper emptyViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wuzhanlm_activity);
        AppUtils.setTitle(WuZhanLMActivity.this);
        initView();
        initData(page);
    }

    private void initData(int page) {
        if (!SystemHelper.isConnected(this)) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                WuZhanLMActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if (isRefresh) {//是加载更多
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(), WuZhanLMBean.class));
                    } else {//刷新或是第一次进来
                        listData.addAll(JSON.parseArray(result.getData(), WuZhanLMBean.class));
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    wuZhanAdapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                } else {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    emptyViewHelper.setType(1);
                }
            }
        }, "method=" + NetUrlUtils.faxian_wuzhanlm + page + MakerApplication.instance().getUid());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page + "");
        map.put("uid", MakerApplication.instance().getUid());
        map.put("loginKey", MakerApplication.instance().getLoginKey());
        map.put("method", NetUrlUtils.faxian_wuzhanlm);
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
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WuZhanLMBean dataBean = null;
                if (view instanceof TextView) {
                    dataBean = (WuZhanLMBean) view.getTag();
                } else {
                    dataBean = (WuZhanLMBean) view.findViewById(R.id.tv_title).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                Intent intent = new Intent(WuZhanLMActivity.this, ClubDetailsActivity.class);
                intent.putExtra("clubId", dataBean.getId());
                startActivity(intent);
            }
        });
//        wuZhanAdapter = new WuZhanAdapter(WuZhanLMActivity.this, listData);
        lv_listview.setAdapter(wuZhanAdapter);
        emptyViewHelper = new EmptyViewHelper(lv_listview,(FrameLayout)findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initData(page);
            }
        });

        common_layout = (CommonSwipeRefreshLayout) findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(false);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
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
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "武战联盟");
        TCAgent.onPageStart(this, "武战联盟");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"武战联盟");
        TCAgent.onPageEnd(this,"武战联盟");
    }
}
