package com.hxwl.wlf3.home.home2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.DuizhengXQBean;
import com.hxwl.wlf3.bean.DuiZenXiangBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.Home3Adapter;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;



import com.hxwl.wulinfeng.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class DZDetailsActivity extends AppCompatActivity {

    private XRecyclerView dzd_xrecyclerview;

    private ArrayList<DuiZenXiangBean.DataBean> clear=new ArrayList<>();
    private XiangQingAdapter xiangQingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzdetails);
        initView();


        try {
//            home3Adapter = new Home3Adapter(getActivity(),datalist);

            xiangQingAdapter = new XiangQingAdapter(this,clear);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            dzd_xrecyclerview.setLayoutManager(layoutManager);
            dzd_xrecyclerview.setNestedScrollingEnabled(false);
            dzd_xrecyclerview.setAdapter(xiangQingAdapter);
        }catch (Exception e){}



    }

    private void initView() {
        dzd_xrecyclerview = (XRecyclerView) findViewById(R.id.dzd_xrecyclerview);
    }


    private void loding() {
        OkHttpUtils.post()
                .url(URLS.HOME_HOME)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                        Log.e("TTT",call+"==============="+e+"----------------"+id);
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();

                            try {
                                DuiZenXiangBean bean = gson.fromJson(response,DuiZenXiangBean.class);

                                try {
                                    if (bean.getCode().equals("1000")){

                                        clear.clear();
                                        clear.add(bean.getData());
                                        xiangQingAdapter.notifyDataSetChanged();
                                    }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                        UIUtils.showToast(bean.getMessage());
                                        startActivity(LoginActivity.getIntent(DZDetailsActivity.this));
                                     finish();
                                    }


                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }catch (Exception e){
                            }

                        }
                    }
                });

    }

}
