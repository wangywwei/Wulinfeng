package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.MessageAdapter;
import com.hxwl.wulinfeng.adapter.SysytemMessAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.BannerListBean;
import com.hxwl.wulinfeng.bean.MessageBean;
import com.hxwl.wulinfeng.bean.SystemMessageBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/6/14.
 * 我-- 系统消息
 */
public class SystemMessageActivity extends BaseActivity {
    private ListView lv_listview;
    private CommonSwipeRefreshLayout common_layout;
    private List<SystemMessageBean> listData = new ArrayList<>();

    private int page = 1;
    private boolean isRefresh;
    private SysytemMessAdapter systemMessAdapter;

    private String lastId;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.systemmess_activity);
        AppUtils.setTitle(SystemMessageActivity.this);
        initView();
        initData();
    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RelativeLayout rl_empty = (RelativeLayout) findViewById(R.id.rl_empty);
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        lv_listview.setEmptyView(rl_empty);
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SystemMessageBean dataBean = null;
                if (view instanceof TextView) {
                    dataBean = (SystemMessageBean) view.getTag();
                } else {
                    dataBean = (SystemMessageBean) view.findViewById(R.id.tv_title).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                String url = dataBean.getTongyong_url();
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                AppUtils.doTiaozhuan(url, SystemMessageActivity.this);
//                BannerListBean info = JSON.parseObject(url,BannerListBean.class);
//                String jump_type =  info.getJump_type();
//                Intent intent = null ;
//                if("zixun".equals(jump_type)){
//                    intent = new Intent(SystemMessageActivity.this,ZiXunDetailsActivity.class);
//                    intent.putExtra("id",info.getId());
//                    startActivity(intent);
//                }else if("tuji".equals(jump_type)){
//                    intent = new Intent(SystemMessageActivity.this,TuJiDetailsActivity.class);
//                    intent.putExtra("id",info.getId());
//                     startActivity(intent);
//                }else if("video".equals(jump_type)){
//                    intent = new Intent(SystemMessageActivity.this,VideoDetailActivity.class);
//                    intent.putExtra("id",info.getId());
//                     startActivity(intent);
//                }else if("bisaishipin".equals(jump_type)){
//                    intent = new Intent(SystemMessageActivity.this,HuiGuDetailActivity.class);
//                    intent.putExtra("saichengId",info.getId());
//                    intent.putExtra("saishiId",info.getsaishiId());
//                    intent.putExtra("name",info.getTitle());
//                     startActivity(intent);
//                }else if("zhibo".equals(jump_type)){
//                    intent = new Intent(SystemMessageActivity.this,LiveDetailActivity.class);
//                    intent.putExtra("saichengId",info.getId());
//                    intent.putExtra("saishiId",info.getsaishiId());
//                    intent.putExtra("name",info.getTitle());
//                     startActivity(intent);
//                }else if("h5".equals(jump_type)){
//                    intent = new Intent(SystemMessageActivity.this,WebViewCurrencyActivity.class);
//                    intent.putExtra("url",info.getUrl());
//                    intent.putExtra("title",info.getTitle());
//                     startActivity(intent);
//                }else{
//                    ToastUtils.showToast(SystemMessageActivity.this, "没有数据...");
//                }

            }
        });
        systemMessAdapter = new SysytemMessAdapter(SystemMessageActivity.this, listData);
        lv_listview.setAdapter(systemMessAdapter);
        common_layout = (CommonSwipeRefreshLayout) findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(false);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                initData();
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initData();
            }
        });
    }

    private void initData() {
        sp = getSharedPreferences("MSG", MODE_PRIVATE);

        //系统消息
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                SystemMessageActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    listData.clear();
                    listData.addAll(JSON.parseArray(result.getData(), SystemMessageBean.class));
                    systemMessAdapter.notifyDataSetChanged();
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                } else if (result != null && result.getStatus().equals("empty")) {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    UIUtils.showToast("没有更多了");
                } else {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                }
            }
        });
        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("lastId", lastId);
        params.put("method", NetUrlUtils.wo_message_system);
        tasker.execute(params);
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "系统消息");
        TCAgent.onPageStart(this, "系统消息");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "系统消息");
        TCAgent.onPageEnd(this, "系统消息");
    }
}
