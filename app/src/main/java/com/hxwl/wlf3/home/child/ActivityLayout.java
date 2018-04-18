package com.hxwl.wlf3.home.child;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hxwl.wulinfeng.R;

/**
 * Created by Administrator on 2018/4/13.
 */
/*
* 活动显示列表
* */
public class ActivityLayout  extends LinearLayout {

    private Context context;
    private View view;

//    public void setBean(final Home3Bean.DataBean bean) {
//        this.dataBean=bean;
//        list.clear();
//        list.addAll(dataBean.getActivities());
//        huodongAdapter.notifyDataSetChanged();
//    }

//    public Saicheng1Layout(Context context) {
//        super(context);
//    }

    public ActivityLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(final Context context) {

        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.activity_item, this);

    }

    public ActivityLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ActivityLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}