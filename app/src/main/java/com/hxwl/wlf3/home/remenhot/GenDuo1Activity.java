package com.hxwl.wlf3.home.remenhot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.home.home2.gengduo.Genduo1Adapter;
import com.hxwl.wlf3.home.home2.gengduo.GengDuoBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.hxwl.wulinfeng.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;


import okhttp3.Call;
/*
* 进入更多界面
* */
public class GenDuo1Activity extends AppCompatActivity {
    private ImageView gengduo_fanhui;
    private TextView gengduo_processing;
    private TextView gengduo_end;
    private XRecyclerView gengduo_xrecycler1;
    private XRecyclerView gengduo_xrecycler2;

    private ArrayList<GengDuoBean.DataBean> dataBeans=new ArrayList<>();
    private int a;
    private Genduo1Adapter home3Adapter;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, GenDuo1Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_duo1);
        initView();
        a = 1;

        try {
            getevent();
        }catch (Exception e){}

        try {
        getdata();
        }catch (Exception e){}

        try {
            home3Adapter = new Genduo1Adapter(this,dataBeans);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            gengduo_xrecycler2.setLayoutManager(layoutManager);
            gengduo_xrecycler2.setNestedScrollingEnabled(false);
            gengduo_xrecycler2.setAdapter(home3Adapter);
        }catch (Exception e){}



        try {
            gengduo_xrecycler2.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
//            loding();
                    gengduo_xrecycler2.refreshComplete();//刷新完成
                }

                @Override
                public void onLoadMore() {
                    gengduo_xrecycler2.refreshComplete();//刷新完成
                }
            });
        }catch (Exception e){}


        try {
            home3Adapter = new Genduo1Adapter(this,dataBeans);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            gengduo_xrecycler1.setLayoutManager(layoutManager);
            gengduo_xrecycler1.setNestedScrollingEnabled(false);
            gengduo_xrecycler1.setAdapter(home3Adapter);
        }catch (Exception e){}

        try {
        gengduo_xrecycler1.setLoadingListener(new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
//            loding();
            gengduo_xrecycler1.refreshComplete();//刷新完成
        }

        @Override
        public void onLoadMore() {
            gengduo_xrecycler1.refreshComplete();//刷新完成
        }
    });
        }catch (Exception e){}


    }

    private void initView() {
        gengduo_fanhui = (ImageView) findViewById(R.id.gengduo_fanhui);
        gengduo_processing = (TextView) findViewById(R.id.gengduo_processing);
        gengduo_end = (TextView) findViewById(R.id.gengduo_end);
        gengduo_xrecycler1 = (XRecyclerView) findViewById(R.id.gengduo_xrecycler1);
        gengduo_xrecycler2 = (XRecyclerView) findViewById(R.id.gengduo_xrecycler2);
    }



    public void getevent() {

        gengduo_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gengduo_processing.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                a=1;

                try {
                    gengduo_processing.setTextColor(getResources().getColor(R.color.rgb_C52720));
                    gengduo_end.setTextColor(getResources().getColor(R.color.home_back));
                    gengduo_processing.setBackground(getResources().getDrawable(R.drawable.corners_bg11));
                    gengduo_end.setBackground(getResources().getDrawable(R.drawable.coxrners_bg2));

                }catch (Exception e){}

                try {
                    gengduo_xrecycler1.setVisibility(View.VISIBLE);
                    gengduo_xrecycler2.setVisibility(View.GONE);
                }catch (Exception e){}

            }
        });

        gengduo_end.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                a=2;
                try {
                    gengduo_processing.setTextColor(getResources().getColor(R.color.home_back));
                    gengduo_end.setTextColor(getResources().getColor(R.color.rgb_C52720));
                    gengduo_end.setBackground(getResources().getDrawable(R.drawable.coxrners_bg22));
                    gengduo_processing.setBackground(getResources().getDrawable(R.drawable.corners_bg1));

                }catch (Exception e){}

                try {
                    gengduo_xrecycler2.setVisibility(View.VISIBLE);
                    gengduo_xrecycler1.setVisibility(View.GONE);

                }catch (Exception e){}

            }
        });

    }

    public void getdata() {
        /*
        *   .addParams("userId", MakerApplication.instance.getUid())
        * */
        OkHttpUtils.post()
                .url(URLS.MORE)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("state", a+"")
                .addParams("pageSize", 5+"")
                .addParams("pageNumber", 1+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        UIUtils.showToast("服务器异常");
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();

                            try {
                                GengDuoBean bean = gson.fromJson(response,GengDuoBean.class);

                                try {
                                    if (bean.getCode().equals("1000")){


                                        dataBeans.clear();
                                        dataBeans.addAll(bean.getData());
                                        home3Adapter.notifyDataSetChanged();

                                    }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
//                                        UIUtils.showToast(bean.getMessage());
//                                        startActivity(LoginActivity.getIntent(getActivity()));

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
