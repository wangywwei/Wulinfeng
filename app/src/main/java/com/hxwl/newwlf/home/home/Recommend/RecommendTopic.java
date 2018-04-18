package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.newwlf.modlebean.HotHuatiBean;
import com.hxwl.newwlf.modlebean.HuatiListBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.view.HoRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class RecommendTopic extends LinearLayout{

    private Context context;
    private TextView topic_loding;
    private HoRecyclerView topic_recycler;
    private TopicAdapter adapter;
    private ArrayList<HomeRecommendBean.DataBean.HotTopicBean> list=new ArrayList<>();

    private HomeRecommendBean.DataBean bean;
    public void setBean(HomeRecommendBean.DataBean bean) {
        this.bean = bean;
        list.clear();
        list.addAll(bean.getHotTopic());
        adapter.notifyDataSetChanged();

    }

    public RecommendTopic(Context context) {
        this(context, null);
    }

    public RecommendTopic(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecommendTopic(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(final Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.recommen_topic, this);
        topic_loding= (TextView) view.findViewById(R.id.topic_loding);
        topic_recycler= (HoRecyclerView) view.findViewById(R.id.topic_recycler);

        topic_loding.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction("recommendtopic");
                context.sendBroadcast(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topic_recycler.setLayoutManager(linearLayoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(topic_recycler);

        adapter=new TopicAdapter(context,list);
        topic_recycler.setAdapter(adapter);

    }

}
