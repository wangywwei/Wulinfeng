package com.hxwl.wlf3.home.home2;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.follow.PlayAdapter;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.QuanshouduizhengBean;
import com.hxwl.wlf3.bean.GuideBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.Home3Adapter;
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
 * 赛事指南
 *
 * Created by Administrator on 2018/4/19.
 */
//GuideActivity
public class GuideFragment extends BaseFragment {
    private View view;
    private ArrayList<QuanshouduizhengBean.DataBean> list=new ArrayList<>();
    private XRecyclerView guide_recycler;
    private ArrayList<GuideBean.DataBean> datalist=new ArrayList<>();
    private GuideActivity home3Adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.guide_item, null);
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
                .url(URLS.SCHEDULE_EVENTS)
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
                                GuideBean bean = gson.fromJson(response, GuideBean.class);
                                try {
                                    if (bean.getCode().equals("1000")){
                                        datalist.clear();
                                        datalist.addAll(bean.getData());
                                        home3Adapter.notifyDataSetChanged();
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

        guide_recycler = (XRecyclerView) view.findViewById(R.id.guide_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        guide_recycler.setLayoutManager(linearLayoutManager);
                try{
                    home3Adapter = new GuideActivity(getContext(),datalist);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    guide_recycler.setLayoutManager(layoutManager);
                    guide_recycler.setNestedScrollingEnabled(false);
                    guide_recycler.setAdapter(home3Adapter);

                }catch (Exception e){
                }


                try {
                    list.remove(0);
                    home3Adapter.notifyItemRemoved(0);
                    home3Adapter.notifyItemRangeChanged(0,list.size()-0);

                }catch (Exception e){

                }
    }
}