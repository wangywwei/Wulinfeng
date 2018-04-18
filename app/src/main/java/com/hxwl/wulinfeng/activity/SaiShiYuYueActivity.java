package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.SaishiBean;
import com.hxwl.newwlf.wod.SaishiAdapter;
import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/14.
 * 赛事预约界面
 */
public class SaiShiYuYueActivity extends BaseActivity{

    private ListView lv_listview;
    private CommonSwipeRefreshLayout common_layout;
    private ArrayList<SaishiBean.DataBean> listData = new ArrayList<>() ;

    private int page = 1 ;
    private boolean isRefresh;
    private SaishiAdapter saiShiAdapter;
    private TextView tv_text1;
    private TextView tv_text2;
//    private EmptyViewHelper emptyViewHelper;
    private RelativeLayout rl_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saishiyuyue_activity);
        AppUtils.setTitle(SaiShiYuYueActivity.this);
        initView();
        initData(page);
    }
    private void initView() {
        findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_text1 = (TextView) findViewById(R.id.tv_text1);
        tv_text2 = (TextView) findViewById(R.id.tv_text2);
        tv_text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SaiShiYuYueActivity.this,HomeActivity.class);
                intent.putExtra("fragId",2);
                intent.putExtra("signTab","saicheng");
                AppManager.getAppManager().finishActivityforName("HomeActivity");
                startActivity(intent);
                finish();
            }
        });
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty);
        lv_listview = (ListView) findViewById(R.id.lv_listview);

        lv_listview.setEmptyView(rl_empty);

        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SaishiBean.DataBean dataBean = listData.get(i) ;
                if(dataBean == null){
                    return ;
                }
//                if (dataBean.getScheduleState()==2){
//                    startActivity(HuiGuDetailActivity.getIntent(SaiShiYuYueActivity.this,dataBean.getScheduleId()+"",1));
//                }else {
                    startActivity(HuiGuDetailActivity.getIntent(SaiShiYuYueActivity.this,dataBean.getScheduleId()+"",0));
//                }


            }
        });
        saiShiAdapter = new SaishiAdapter(listData,SaiShiYuYueActivity.this);
        lv_listview.setAdapter(saiShiAdapter);


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
                common_layout.setRefreshing(false);
                common_layout.setLoadMore(false);
//                page ++ ;
//                isRefresh = false;
//                initData(page);
            }
        });
    }
    private void initData(int page) {
        if (!SystemHelper.isConnected(this)) {
            UIUtils.showToast("请检查网络");
//            emptyViewHelper.setType(1);
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_GETUSERSUBSCRIBELIST)
                .addParams("userId",MakerApplication.instance.getUid())
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
                                SaishiBean saishiBean = gson.fromJson(response, SaishiBean.class);
                                if (saishiBean.getCode().equals("1000")) {
                                    if(isRefresh){//是加载更多
                                        listData.clear();
                                        listData.addAll(saishiBean.getData()) ;
                                    }else{//刷新或是第一次进来
                                        listData.addAll(saishiBean.getData()) ;
                                    }
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    saiShiAdapter.notifyDataSetChanged();
                                }else if (saishiBean.getCode().equals("2002")||saishiBean.getCode().equals("2003")){
                                    UIUtils.showToast(saishiBean.getMessage());
                                    startActivity(LoginActivity.getIntent(SaiShiYuYueActivity.this));
                                    SaiShiYuYueActivity.this.finish();
                                }else{
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
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "赛事预约");
        TCAgent.onPageStart(this, "赛事预约");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"赛事预约");
        TCAgent.onPageEnd(this,"赛事预约");
    }

}
