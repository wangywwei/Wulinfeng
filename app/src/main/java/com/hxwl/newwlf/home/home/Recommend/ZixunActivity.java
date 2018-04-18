package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.ZixunqushiBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/*
* 全部资讯
* */
public class ZixunActivity extends BaseActivity implements View.OnClickListener {
    private ImageView back;
    private int pageNumber=1;
    private ZixunLinlayout zixunLinlayout;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ZixunActivity.class);
        return intent;
    }

    private XRecyclerView zixun_xrecycler;
    private QuanbuZixunAdapter adapter;
    private ArrayList<ZixunqushiBean.DataBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zixun);
        AppUtils.setTitle(this);
        initView();
        initData();


    }

    private void initData() {
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_NEWSLIST)
                .addParams("pageNumber",pageNumber+"")
                .addParams("pageSize",10+"")
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                ZixunqushiBean bean = gson.fromJson(response, ZixunqushiBean.class);
                                if (bean.getCode().equals("1000")){
                                    list.addAll(bean.getData());
                                    adapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(ZixunActivity.this));
                                    ZixunActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

    }


    private void initView() {
        zixun_xrecycler = (XRecyclerView) findViewById(R.id.zixun_xrecycler);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        adapter = new QuanbuZixunAdapter(list,this );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        zixun_xrecycler.setLayoutManager(linearLayoutManager);
        zixun_xrecycler.setAdapter(adapter);

        zixunLinlayout = new ZixunLinlayout(this);
        zixun_xrecycler.addHeaderView(zixunLinlayout);
        zixun_xrecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                zixun_xrecycler.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                zixun_xrecycler.refreshComplete();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
