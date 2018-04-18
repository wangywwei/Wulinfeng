package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.zixun.ZixunQushiAdapter;
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

public class ZhuanTiActivity extends BaseActivity implements View.OnClickListener {
    private String subjectId;
    private ImageView back;

    public static Intent getIntent(Context context, String subjectId) {
        Intent intent = new Intent(context, ZhuanTiActivity.class);
        intent.putExtra("subjectId", subjectId);
        return intent;
    }


    private XRecyclerView information_recycle;
    private TextView no_information;
    private ZixunQushiAdapter informationAdapter;
    private ArrayList<ZixunqushiBean.DataBean> list = new ArrayList<>();
    private int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuan_ti);
        AppUtils.setTitle(this);
        subjectId = getIntent().getStringExtra("subjectId");

        initView();
        initData();


    }

    private void initData() {
        if (StringUtils.isBlank(subjectId)) {
            return;
        }
        OkHttpUtils.post()
                .url(URLS.NEWS_SUBJECTNEWSLIST)
                .addParams("subjectId", subjectId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("pageNumber", pageNumber + "")
                .addParams("pageSize", 10 + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                ZixunqushiBean bean = gson.fromJson(response, ZixunqushiBean.class);
                                if (bean.getCode().equals("1000")) {
                                    list.addAll(bean.getData());
                                    informationAdapter.notifyDataSetChanged();
                                    if (list.size() <= 0) {
                                        no_information.setVisibility(View.VISIBLE);
                                        information_recycle.setVisibility(View.GONE);
                                    }

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(ZhuanTiActivity.this));
                                    ZhuanTiActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });


    }

    private void initView() {
        information_recycle = (XRecyclerView) findViewById(R.id.information_recycle);
        no_information = (TextView) findViewById(R.id.no_information);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        information_recycle.setLayoutManager(linearLayoutManager);
        informationAdapter = new ZixunQushiAdapter(list, this);
        information_recycle.setAdapter(informationAdapter);

        information_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                list.clear();
                pageNumber = 1;
                initData();
                information_recycle.refreshComplete();//刷新完成
            }

            @Override
            public void onLoadMore() {
                pageNumber++;
                initData();
                information_recycle.refreshComplete();//刷新完成
            }
        });
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
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
