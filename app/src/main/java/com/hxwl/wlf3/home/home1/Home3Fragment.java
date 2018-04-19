package com.hxwl.wlf3.home.home1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.Recommend.RecommendBanner;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.remenhot.RemenHotLayout;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;


public class Home3Fragment extends BaseFragment {
    private View view;
    private XRecyclerView home3_xrecyclerview;
    private Home3Adapter home3Adapter;
    private ArrayList<Home3Bean.DataBean.SchedulesBean> datalist=new ArrayList<>();



    private RecommendBanner recommendBanner;
    private RemenHotLayout remenHotLayout;
    private LiveLayout liveLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home3, null);
            initView(view);
            initData();
            loding();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.rgb_BA2B2C), 0);
        initView(view);
        return view;
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
                                Home3Bean  bean = gson.fromJson(response,Home3Bean.class);

                                try {
                                    if (bean.getCode().equals("1000")){
                                        try {
                                            recommendBanner.setBean(bean.getData());
                                        }catch (Exception o){
                                        }
                                        try {
                                            remenHotLayout.setBean(bean.getData());
                                        }catch (Exception o){

                                        }
                                        try {
                                            liveLayout.setBean(bean.getData());
                                        }catch (Exception o){

                                        }
                                        datalist.clear();
                                        datalist.addAll(bean.getData().getSchedules());
                                        List<Home3Bean.DataBean.SchedulesBean> schedules = bean.getData().getSchedules();


                                        home3Adapter.notifyDataSetChanged();
                                    }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                        UIUtils.showToast(bean.getMessage());
                                        startActivity(LoginActivity.getIntent(getActivity()));
                                        getActivity().finish();
                                    }


                                }catch (Exception e){
                                    e.printStackTrace();
                                }



                            }catch (Exception e){
                                Log.e("TTTAAADD","");
                            }




                        }
                    }
                });

    }



    private void initData() {
        //推荐banner
        recommendBanner = new RecommendBanner(getActivity());
        home3_xrecyclerview.addHeaderView(recommendBanner);

        remenHotLayout = new RemenHotLayout(getActivity());
        home3_xrecyclerview.addHeaderView(remenHotLayout);


        liveLayout =new LiveLayout(getActivity());
        home3_xrecyclerview.addHeaderView(liveLayout);



    }

    private void initView(View view) {
        home3_xrecyclerview = (XRecyclerView) view.findViewById(R.id.home3_xrecyclerview);
        try {
            home3Adapter = new Home3Adapter(getActivity(),datalist);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            home3_xrecyclerview.setLayoutManager(layoutManager);
            home3_xrecyclerview.setNestedScrollingEnabled(false);
            home3_xrecyclerview.setAdapter(home3Adapter);
        }catch (Exception e){

        }




        home3_xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loding();
                home3_xrecyclerview.refreshComplete();//刷新完成
            }

            @Override
            public void onLoadMore() {
                home3_xrecyclerview.refreshComplete();//刷新完成
            }
        });
    }
}
