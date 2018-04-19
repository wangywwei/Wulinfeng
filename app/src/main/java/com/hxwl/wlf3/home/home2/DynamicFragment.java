package com.hxwl.wlf3.home.home2;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.follow.PlayAdapter;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.QuanshouduizhengBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/4/19.
 */

public class DynamicFragment extends BaseFragment {
    private View view;
    private XRecyclerView play_xrecycler;
    private PlayAdapter adapter;
    private ArrayList<QuanshouduizhengBean.DataBean> list=new ArrayList<>();
    private int pageNumber=1;
    private String agentId;

    private boolean isRefresh=true;
    public void setAgentId(String agentId) {
        this.agentId=agentId;
        initData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.dynamic_item, null);
            initView(view);

        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        return view;
    }

    private void initData() {
        if (StringUtils.isBlank(agentId)){
            return;
        }
//        OkHttpUtils.post()
//                .url(URLS.SCHEDULE_GETPLAYERAGAINSTLIST)
//                .addParams("playerId",agentId)
//                .addParams("token", MakerApplication.instance.getToken())
//                .addParams("pageNumber",pageNumber+"")
//                .addParams("pageSize","10")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        UIUtils.showToast("服务器异常");
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        JsonValidator jsonValidator = new JsonValidator();
//                        if (jsonValidator.validate(response)){
//                            Gson gson = new Gson();
//                            try {
//                                QuanshouduizhengBean bean = gson.fromJson(response, QuanshouduizhengBean.class);
//                                if (bean.getCode().equals("1000")){
//                                    if (isRefresh){
//                                        list.clear();
//                                        list.addAll(bean.getData());
//                                    }else {
//                                        list.addAll(bean.getData());
//                                    }
//                                    adapter.notifyDataSetChanged();
//
//                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
//                                    UIUtils.showToast(bean.getMessage());
//                                    startActivity(LoginActivity.getIntent(getActivity()));
//
//                                }
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                    }
//                });



    }

    private void initView(View view) {
       /* play_xrecycler = (XRecyclerView) view.findViewById(R.id.play_xrecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        play_xrecycler.setLayoutManager(linearLayoutManager);

        adapter=new PlayAdapter(list,getActivity());
        play_xrecycler.setAdapter(adapter);

        play_xrecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNumber=1;
                isRefresh=true;
                initData();
                play_xrecycler.refreshComplete();//刷新完成
            }

            @Override
            public void onLoadMore() {
                isRefresh=false;
                pageNumber++;
                initData();
                play_xrecycler.refreshComplete();//刷新完成
            }
        });*/


    }
}