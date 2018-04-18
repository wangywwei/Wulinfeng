package com.hxwl.newwlf.schedule.cooperation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.ShendaiBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;


public class CooperationFragment extends Fragment {
    private View view;
    private GridView cooperation_grid;
    private CooperationAdapter adapter;
    private ArrayList<ShendaiBean.DataBean> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_cooperation, null);
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
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_AGENTLIST)
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
                                ShendaiBean bean = gson.fromJson(response, ShendaiBean.class);
                                if (bean.getCode().equals("1000")){
                                    list.clear();
                                    list.addAll(bean.getData());
                                    adapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });

    }


    private void initView(View view) {
        cooperation_grid = (GridView) view.findViewById(R.id.cooperation_grid);

        adapter=new CooperationAdapter(list,getActivity());
        cooperation_grid.setAdapter(adapter);

        cooperation_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(AgentActivity.getIntent(getActivity(),list.get(position).getAgentId()+""));

            }
        });

    }
}
