package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.BiSaiSPTypeAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.BiSaiSPTypeBean;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Allen on 2017/7/3.
 * 比赛集锦AND花絮界面
 */
public class BiSaiShiPinTypeActivity extends BaseActivity{

    private TextView tv_type;
    private String type;

    private ListView lv_listview;
    private List<BiSaiSPTypeBean> listData = new ArrayList<>() ;
    private CommonSwipeRefreshLayout common_layout ;
    private int page  = 1;
    private boolean isRefresh = true;
    private BiSaiSPTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bisaishipintype_activity);
        type = getIntent().getStringExtra("type");
        initView();
        initData(page);
    }

    private void initData(int page) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                BiSaiShiPinTypeActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if(isRefresh){
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(),BiSaiSPTypeBean.class)) ;
                    }else{
                        listData.addAll(JSON.parseArray(result.getData(),BiSaiSPTypeBean.class)) ;
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    adapter.notifyDataSetChanged();
                }else if(result != null && result.getStatus().equals("empty")){
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    UIUtils.showToast("没有更多了");
                }else{
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                }
            }
        },"method="+NetUrlUtils.video_jijinANDhuaxu+page);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("page", page);
        params.put("method", NetUrlUtils.video_jijinANDhuaxu);
        tasker.execute(params);

//        Map<String, Object> map = new HashMap<>();
//        map.put("type", type);
//        map.put("page", page);
//        try {
//            AppClient.okhttp_post_Asyn(NetUrlUtils.video_jijinANDhuaxu, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    BiSaiShiPinTypeActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            common_layout.setRefreshing(false);
//                            UIUtils.showToast("服务器异常");
//                        }
//                    });
//                }
//                @Override
//                public void success(ResultPacket result) {
//                    if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                        if(isRefresh){
//                            listData.clear();
//                            listData.addAll(JSON.parseArray(result.getData(),BiSaiSPTypeBean.class)) ;
//                        }else{
//                            listData.addAll(JSON.parseArray(result.getData(),BiSaiSPTypeBean.class)) ;
//                        }
//                        BiSaiShiPinTypeActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run(){
//                                common_layout.setRefreshing(false);
//                                common_layout.setLoadMore(false);
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                    } else if(result.getStatus() != null && result.getStatus().equals("empty")){
//                        BiSaiShiPinTypeActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                common_layout.setRefreshing(false);
//                                common_layout.setLoadMore(false);
//                                UIUtils.showToast("没有更多了");
//                            }
//                        });
//                    }else {
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
        tv_type = (TextView) findViewById(R.id.tv_type);
        if("1".equals(type)){
            tv_type.setText("集锦");
        }else{
            tv_type.setText("花絮");
        }
        common_layout = (CommonSwipeRefreshLayout)findViewById(R.id.common_layout);
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
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        adapter = new BiSaiSPTypeAdapter(BiSaiShiPinTypeActivity.this,listData);
        lv_listview.setAdapter(adapter);
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BiSaiSPTypeBean dataBean = null ;
                if(view instanceof TextView){
                    dataBean = (BiSaiSPTypeBean)view.getTag();
                }else{
                    dataBean = (BiSaiSPTypeBean)view.findViewById(R.id.tv_title).getTag();
                }
                if(dataBean == null) {return ;}
                Intent intent = new Intent(BiSaiShiPinTypeActivity.this ,VideoDetailActivity.class) ;
                intent.putExtra("id",dataBean.getId());
                startActivity(intent);
            }
        });
    }
}
