package com.hxwl.wlf3.home.home2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.DuizhengXQBean;
import com.hxwl.wlf3.bean.DuiZenXiangBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.Home3Adapter;
import com.hxwl.wlf3.home.remenhot.GenDuoActivity;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.hxwl.wulinfeng.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class DZDetailsActivity extends AppCompatActivity {

    private XRecyclerView dzd_xrecyclerview;

    private ArrayList<DuiZenXiangBean.DataBean> clear=new ArrayList<>();
    private XiangQingAdapter xiangQingAdapter;


//    private static String str1;

//    public static Intent getIntent(Context context,String str){
//        Intent intent=new Intent(context,DZDetailsActivity.class);
//        str1 = str;
//        return intent;
//    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dzdetails);

//        Toast.makeText(this, "-------"+str1, Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
//        String num1 = intent.getStringExtra("one");
        String num2 = intent.getStringExtra("two");

        Toast.makeText(this, "传值成功"+num2, Toast.LENGTH_SHORT).show();

//        initView();

//
//        try {
//
//            for (int i = 0; i < clear.size(); i++) {
//                ArrayList<DuiZenXiangBean.DataBean.AgainstListBean> againstList = new ArrayList<>();
//                againstList.addAll( clear.get(i).getAgainstList());
//
//
//                xiangQingAdapter = new XiangQingAdapter(this,againstList  );
//                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                dzd_xrecyclerview.setLayoutManager(layoutManager);
//                dzd_xrecyclerview.setNestedScrollingEnabled(false);
//                dzd_xrecyclerview.setAdapter(xiangQingAdapter);
//            }
//
//
//
//        }catch (Exception e){
//
//        }



    }

    private void initView() {
        dzd_xrecyclerview = (XRecyclerView) findViewById(R.id.dzd_xrecyclerview);
    }


    /*private void loding() {
        OkHttpUtils.post()
                .url(URLS.CONFRONTATIONDETAILS)
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

    }*/

}
