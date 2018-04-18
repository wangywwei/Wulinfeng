package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.BiSaiShiPinAdapter;
import com.hxwl.wulinfeng.adapter.WuZhanAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.BiSaiShiPinBean;
import com.hxwl.wulinfeng.bean.SaiShiYuYueBean;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.bean.WuZhanLMBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.MyListView;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hxwl.wulinfeng.R.id.common_layout;

/**
 * Created by Allen on 2017/6/14.
 * 发现上方布局点击进入
 */
public class BiSaiShiPinActivity extends BaseActivity{
    private ListView lv_listview;
    private List<BiSaiShiPinBean> listData = new ArrayList<>() ;

    private int page = 1 ;
    private boolean isRefresh;
    private BiSaiShiPinAdapter biSaiShiPinAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bisaishipin_activity);
        AppUtils.setTitle(BiSaiShiPinActivity.this);
        initView();
        initData();
    }
    //faxian_bisaishipin
    private void initData() {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                BiSaiShiPinActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if(isRefresh){
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(),BiSaiShiPinBean.class)) ;
                    }else{//刷新或是第一次进来
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(),BiSaiShiPinBean.class)) ;
                    }
                    biSaiShiPinAdapter.notifyDataSetChanged();
                }else if(result != null && result.getStatus().equals("empty")){

                }else{
                }
            }
        },"method="+NetUrlUtils.faxian_bisaishipin+page);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("method", NetUrlUtils.faxian_bisaishipin);
        tasker.execute(params);
    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BiSaiShiPinBean dataBean = null ;
                if(view instanceof TextView){
                    dataBean = (BiSaiShiPinBean)view.getTag();
                }else{
                    dataBean = (BiSaiShiPinBean)view.findViewById(R.id.tv_title).getTag();
                }
                if(dataBean == null){
                    return ;
                }
                Intent intent = new Intent(BiSaiShiPinActivity.this,WangQiShipinActivity.class);
                intent.putExtra("saishiId",dataBean.getId()) ;
                startActivity(intent);
            }
        });
        biSaiShiPinAdapter = new BiSaiShiPinAdapter(BiSaiShiPinActivity.this ,listData);
        lv_listview.setAdapter(biSaiShiPinAdapter);
//        common_layout = (CommonSwipeRefreshLayout) findViewById(R.id.common_layout);
//        common_layout.setTargetScrollWithLayout(true);
//        common_layout.setPullRefreshEnabled(true);
//        common_layout.setLoadingMoreEnabled(true);
//        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                page = 1 ;
//                isRefresh = true;
//                initData();
//            }
//
//            @Override
//            public void onLoadMore() {
//                page ++ ;
//                isRefresh = false;
//                initData();
//            }
//        });
    }
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "比赛视频");
        TCAgent.onPageStart(this, "比赛视频");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"比赛视频");
        TCAgent.onPageEnd(this,"比赛视频");
    }
}
