package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.ZhuanTiBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/2/26.
 */

public class ZixunLinlayout extends LinearLayout {
    private Context context;
    private RecyclerView zixun_xrecycler;
    private QuanbuZixunAdapter2 adapter;
    private ArrayList<ZhuanTiBean.DataBean> list = new ArrayList<>();
    public ZixunLinlayout(Context context) {
        super(context);
        initView(context);
        initData();
    }

    private void initData() {
        OkHttpUtils.post()
                .url(URLS.NEWS_SUBJECTLIST)
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
                                ZhuanTiBean bean = gson.fromJson(response, ZhuanTiBean.class);
                                if (bean.getCode().equals("1000")) {
                                    list.addAll(bean.getData());
                                    adapter.notifyDataSetChanged();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    context.startActivity(LoginActivity.getIntent(context));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.informationlayout, this);
        zixun_xrecycler= (RecyclerView) view.findViewById(R.id.zixun_xrecycler);
        adapter = new QuanbuZixunAdapter2(list,context );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        zixun_xrecycler.setLayoutManager(linearLayoutManager);
        zixun_xrecycler.setAdapter(adapter);




    }


}
