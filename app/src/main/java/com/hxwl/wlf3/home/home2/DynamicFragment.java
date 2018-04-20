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
import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.bean.GuideBean;
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
 *
 * 赛事动态
 *
 * Created by Administrator on 2018/4/19.
 */

public class DynamicFragment extends BaseFragment {
    private View view;
    private XRecyclerView dongtao_xrecyclerview;
    private ArrayList<DynamicBean.DataBean> datalist=new ArrayList<>();
    private DynamicActivity dyuamicAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.dongtai_item, null);
            initView(view);
            initData();
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

        OkHttpUtils.get()
                .url(URLS.SCHEDULE_DYNAMIC)
                .addParams("userId", MakerApplication.instance.getUid())
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
                                DynamicBean bean = gson.fromJson(response, DynamicBean.class);
                                try {
                                    if (bean.getCode().equals("1000")){
                                        datalist.clear();
                                        datalist.addAll(bean.getData());
                                        dyuamicAdapter.notifyDataSetChanged();
                                    }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                        UIUtils.showToast(bean.getMessage());
                                        startActivity(LoginActivity.getIntent(getActivity()));
                                    }
                                }catch (Exception e){
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    }
                });



    }

    private void initView(View view) {

        dongtao_xrecyclerview = (XRecyclerView) view.findViewById(R.id.dongtao_xrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        dongtao_xrecyclerview.setLayoutManager(linearLayoutManager);
        try{
            dyuamicAdapter = new DynamicActivity(getContext(),datalist);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            dongtao_xrecyclerview.setLayoutManager(layoutManager);
            dongtao_xrecyclerview.setNestedScrollingEnabled(false);
            dongtao_xrecyclerview.setAdapter(dyuamicAdapter);

        }catch (Exception e){
        }


    }
}