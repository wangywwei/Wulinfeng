package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.MessageAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.MessageBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Allen on 2017/6/8.
 * 武林fragment页面点击右上角第一个按钮 消息按钮
 */
public class MessageActivity extends BaseActivity{

    private ListView lv_listview;
    private CommonSwipeRefreshLayout common_layout;
    private List<MessageBean> listData = new ArrayList<>() ;

    private int page = 1 ;
    private boolean isRefresh;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        AppUtils.setTitle(MessageActivity.this);
        initView();
        initData(page) ;
    }

    public void initView() {
        findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MessageBean dataBean = null ;
                if(view instanceof TextView){
                    dataBean = (MessageBean)view.getTag();
                }else{
                    dataBean = (MessageBean)view.findViewById(R.id.txt_user_name).getTag();
                }
                if(dataBean == null){
                    return ;
                }
                Intent intent = new Intent(MessageActivity.this,TieZiDetailsActivity.class);
                intent.putExtra("zhu_id",dataBean.getZhu_info().getId()) ;
                startActivity(intent);
            }
        });
        messageAdapter = new MessageAdapter(MessageActivity.this ,listData);
        lv_listview.setAdapter(messageAdapter);
        common_layout = (CommonSwipeRefreshLayout) findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1 ;
                isRefresh = true;
                initData(page);
            }

            @Override
            public void onLoadMore() {
                page ++ ;
                isRefresh = false;
                initData(page);
            }
        });

    }

    private void initData(int page) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                MessageActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if(isRefresh){//是加载更多
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(),MessageBean.class)) ;
                    }else{//刷新或是第一次进来
                        listData.addAll(JSON.parseArray(result.getData(),MessageBean.class)) ;
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    messageAdapter.notifyDataSetChanged();
                }else if(result != null && result.getStatus().equals("empty")){
                    UIUtils.showToast("没有更多了");
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                }else{
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                }
            }
        },"method="+NetUrlUtils.wulin_message+page+MakerApplication.instance.getUid());
        HashMap<String, Object> map= new HashMap<String, Object>();
        map.put("page" ,page+"");
        map.put("uid", MakerApplication.instance.getUid());
        map.put("loginKey", MakerApplication.instance.getLoginKey());
        map.put("method", NetUrlUtils.wulin_message);
        tasker.execute(map);

    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "消息");
        TCAgent.onPageStart(this, "消息");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"消息");
        TCAgent.onPageEnd(this,"消息");
    }
}
