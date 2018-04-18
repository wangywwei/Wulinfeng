package com.hxwl.wulinfeng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.MyFankuiBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.MyFankuiAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/20.
 * 我的反馈详情页面
 */
public class MyFanKuiActivity extends BaseActivity {

    private ListView lv_listview;
    private CommonSwipeRefreshLayout common_layout;
    private List<MyFankuiBean.DataBean> listData = new ArrayList<>() ;

    private int page = 1 ;
    private boolean isRefresh;
    private MyFankuiAdapter fankuiMessAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfankui_activity);
        AppUtils.setTitle(MyFanKuiActivity.this);
        initView();
        initData(page);
    }

    private void initData(int page) {
        if (!SystemHelper.isConnected(MyFanKuiActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                MyFanKuiActivity.this, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    if(isRefresh){//刷新
//                        listData.clear();
//                        listData.addAll(JSON.parseArray(result.getData(),MyFanKuiBean.class)) ;
//                    }else{//加载更多
//                        listData.addAll(JSON.parseArray(result.getData(),MyFanKuiBean.class)) ;
//                    }
//                    common_layout.setRefreshing(false);
//                    common_layout.setLoadMore(false);
//                    fankuiMessAdapter.notifyDataSetChanged();
//                }else if(result != null && result.getStatus().equals("empty")){
//                    common_layout.setRefreshing(false);
//                    common_layout.setLoadMore(false);
//                    UIUtils.showToast("没有更多了");
//                }else{
//                    common_layout.setRefreshing(false);
//                    common_layout.setLoadMore(false);
////                    UIUtils.showToast(result.getMsg());
//                }
//            }
//        },"method="+NetUrlUtils.wo_myfankuideta+page+MakerApplication.instance().getUid());
//        HashMap<String, Object> map= new HashMap<String, Object>();
//        map.put("uid", MakerApplication.instance().getUid());
//        map.put("loginKey", MakerApplication.instance().getLoginKey());
//        map.put("method", NetUrlUtils.wo_myfankuideta);
//        tasker.execute(map);

        OkHttpUtils.post()
                .url(URLS.FEEDBACK_FEEDBACKLIST)
                .addParams("pageNumber", page+"")
                .addParams("pageSize", "10")
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                MyFankuiBean bean = gson.fromJson(response, MyFankuiBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if(isRefresh){//刷新
                                        listData.clear();
                                        listData.addAll(bean.getData()) ;
                                    }else{//加载更多
                                        listData.addAll(bean.getData()) ;
                                    }
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    fankuiMessAdapter.notifyDataSetChanged();
                                }else {
                                    UIUtils.showToast(bean.getMessage());
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

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        if(user_icon != null && user_icon instanceof ImageView){
            findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        RelativeLayout rl_empty = (RelativeLayout) findViewById(R.id.rl_empty);
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        lv_listview.setEmptyView(rl_empty);
        fankuiMessAdapter = new MyFankuiAdapter(MyFanKuiActivity.this ,listData);
        lv_listview.setAdapter(fankuiMessAdapter);
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
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "我的反馈");
        TCAgent.onPageStart(this, "我的反馈");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"我的反馈");
        TCAgent.onPageEnd(this,"我的反馈");
    }
}
