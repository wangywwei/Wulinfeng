package com.hxwl.newwlf.wulin.arts;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.GenHuatiBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.library.flowlayout.FlowLayoutManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;


public class HuatiFragment extends BaseFragment {
    private View view;
    private TextView huati_title;
    private RecyclerView huati_gridview;
    private TextView huati_title2;
    private XRecyclerView huati_recycler;
    private HuatiGridAdapter huatiGridAdapter;
    private ArrayList<GenHuatiBean.DataBean.HotTopicListBean> list=new ArrayList<>();
    private ArrayList<GenHuatiBean.DataBean.TopicListBean> addlist=new ArrayList<>();
    private HuatiRecycAdapter huatiRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_huati, null);
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
    private int pageNumber=1;
    private void initData() {
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_TOPICLIST)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("pageNumber",pageNumber+"")
                .addParams("pageSize","10")
                .build().execute(new StringCallback() {
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
                        GenHuatiBean bean = gson.fromJson(response, GenHuatiBean.class);
                        if (bean.getCode().equals("1000")){
                            if (bean.getData().getHotTopicList()!=null&&bean.getData().getHotTopicList().size()>0){
                                list.addAll(bean.getData().getHotTopicList());
                                huatiGridAdapter.notifyDataSetChanged();
                            }
                            if (bean.getData().getTopicList()!=null&&bean.getData().getTopicList().size()>0){
                                addlist.addAll(bean.getData().getTopicList());
                                huatiRecyclerAdapter.notifyDataSetChanged();
                            }

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
        huati_title = (TextView) view.findViewById(R.id.huati_title);
        huati_gridview = (RecyclerView) view.findViewById(R.id.huati_gridview);
        huati_title2 = (TextView) view.findViewById(R.id.huati_title2);
        huati_recycler = (XRecyclerView) view.findViewById(R.id.huati_recycler);

        huatiGridAdapter=new HuatiGridAdapter(list,getActivity());
        huati_gridview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        huati_gridview.setAdapter(huatiGridAdapter);

        huatiGridAdapter.setOnItemclickLinter(new HuatiGridAdapter.OnItemclickLinter() {
            @Override
            public void onItemClicj(int view) {
                startActivity(HuatiXQActivity.getIntent(getActivity(),
                        list.get(view).getId()+"",
                        list.get(view).getTopicName(),
                        list.get(view).getContent(),
                        list.get(view).getImage(),
                        list.get(view).getJoinNum()+""));
            }
        });



        //流式布局
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        huati_recycler.setLayoutManager(flowLayoutManager);
        huatiRecyclerAdapter = new HuatiRecycAdapter(addlist,getActivity());
        huati_recycler.setAdapter(huatiRecyclerAdapter);

        huatiRecyclerAdapter.setOnItemclickLinter(new HuatiRecycAdapter.OnItemclickLinter() {
            @Override
            public void onItemClicj(int position) {
                startActivity(HuatiXQActivity.getIntent(getActivity(),
                        addlist.get(position).getId()+"",
                        addlist.get(position).getTopicName(),
                        addlist.get(position).getContent(),
                        addlist.get(position).getImage(),
                        addlist.get(position).getJoinNum()+""));
            }
        });



    }

}
