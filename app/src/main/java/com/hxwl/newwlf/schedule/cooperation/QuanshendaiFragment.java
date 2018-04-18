package com.hxwl.newwlf.schedule.cooperation;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.QuanshendaiBean;
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


public class QuanshendaiFragment extends BaseFragment {
    private View view;
    private XRecyclerView quanshou_xrecycler;

    private QuanshouAdapter adapter;
    private ArrayList<QuanshendaiBean.DataBean> list=new ArrayList<>();
    private String agentId;
    public void setAgentId(String agentId) {
        this.agentId=agentId;
        initData();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (StringUtils.isBlank(agentId)){
            return;
        }
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_quanshou, container, false);
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
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_AGENTPLAYERLIST)
                .addParams("agentId",agentId)
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
                                QuanshendaiBean bean = gson.fromJson(response, QuanshendaiBean.class);
                                if (bean.getCode().equals("1000")){
                                    list.clear();
                                    list.addAll(bean.getData());
                                    adapter.notifyDataSetChanged();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });






    }


    private void initView(View view) {
        quanshou_xrecycler = (XRecyclerView) view.findViewById(R.id.quanshou_xrecycler);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        quanshou_xrecycler.setLayoutManager(manager);
        adapter=new QuanshouAdapter(list,getActivity());
        quanshou_xrecycler.setAdapter(adapter);
        quanshou_xrecycler.setPullRefreshEnabled(false);
        quanshou_xrecycler.setHasFixedSize(true);
        quanshou_xrecycler.setNestedScrollingEnabled(false);
        quanshou_xrecycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
                quanshou_xrecycler.refreshComplete();
            }

            @Override
            public void onLoadMore() {

                initData();
                quanshou_xrecycler.refreshComplete();
            }
        });
    }
}
