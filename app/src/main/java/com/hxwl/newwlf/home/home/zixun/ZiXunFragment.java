package com.hxwl.newwlf.home.home.zixun;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.ZixunqushiBean;
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

import okhttp3.Call;

/*
* 关注——资讯
* */
@SuppressLint("ValidFragment")
public class ZiXunFragment extends BaseFragment {
    private View view;
    private XRecyclerView information_recycle;
    private TextView no_information;
    private ZixunQushiAdapter informationAdapter;
    private ArrayList<ZixunqushiBean.DataBean> list=new ArrayList<>();
    private int pageNumber=1;
    private String columnId;
    private boolean isRefresh=true;//分页判断
    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

//    public ZiXunFragment(String columnId) {
//        this.columnId = columnId;
//    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_information, null);
            initView(view);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.shouye_tab), 0);


        return view;

    }

    private void initData() {
        if (StringUtils.isBlank(columnId)){
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_NEWSLIST)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("columnId",columnId)
                .addParams("pageNumber",pageNumber+"")
                .addParams("pageSize",10+"")
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
                                    if (list==null){
                                        list=new ArrayList<>();
                                    }
                                    if (isRefresh){
                                        list.clear();
                                        list.addAll(bean.getData());
                                    }else {
                                        list.addAll(bean.getData());
                                    }

                                    informationAdapter.notifyDataSetChanged();

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
        information_recycle = (XRecyclerView) view.findViewById(R.id.information_recycle);
        no_information= (TextView) view.findViewById(R.id.no_information);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        information_recycle.setLayoutManager(linearLayoutManager);
        informationAdapter=new ZixunQushiAdapter(list,getActivity());
        information_recycle.setAdapter(informationAdapter);

        information_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                information_recycle.refreshComplete();//刷新完成
                isRefresh=true;
                pageNumber=1;
                initData();

            }

            @Override
            public void onLoadMore() {
                information_recycle.refreshComplete();//刷新完成
                pageNumber++;
                isRefresh=false;
                initData();

            }
        });
    }
}
