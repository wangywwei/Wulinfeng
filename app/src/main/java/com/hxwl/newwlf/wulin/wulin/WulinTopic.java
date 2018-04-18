package com.hxwl.newwlf.wulin.wulin;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HotHuatiBean;
import com.hxwl.newwlf.wulin.arts.HuatiXQActivity;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/1/12.
 */

public class WulinTopic extends LinearLayout{

    private Context context;
    private RecyclerView topic_recycler;
    private TopicAdapter adapter;
    private ArrayList<HotHuatiBean.DataBean> list=new ArrayList<>();

    public WulinTopic(Context context) {
        this(context, null);
    }

    public WulinTopic(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WulinTopic(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initData();
    }

    private void initData() {
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_HOTTOPICLIST)
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
                                HotHuatiBean bean = gson.fromJson(response, HotHuatiBean.class);
                                if (bean.getCode().equals("1000")){
                                    list.addAll(bean.getData());
                                    adapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    context.startActivity(LoginActivity.getIntent(context));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });

    }

    private void initView(final Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.wulin_topic, this);
        topic_recycler= (RecyclerView) view.findViewById(R.id.topic_recycler);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topic_recycler.setLayoutManager(linearLayoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(topic_recycler);

        adapter=new TopicAdapter(context,list);
        topic_recycler.setAdapter(adapter);

        adapter.setOnItemclickLinter(new TopicAdapter.OnItemclickLinter() {
            @Override
            public void onItemClicj(int view) {
                context.startActivity(HuatiXQActivity.getIntent(context,
                        list.get(view).getId()+"",
                        list.get(view).getTopicName(),
                        list.get(view).getContent(),
                        list.get(view).getImage(),
                        list.get(view).getJoinNum()+""));
            }
        });

    }

}
