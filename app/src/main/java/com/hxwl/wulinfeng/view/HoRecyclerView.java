package com.hxwl.wulinfeng.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * Created by Administrator on 2018/1/22.
 */

public class HoRecyclerView extends RecyclerView {
    public HoRecyclerView(Context context) {
        this(context,null);
    }

    public HoRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HoRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewParent parent =this;

        while(!((parent = parent.getParent()) instanceof ViewPager)) ;// 循环查找viewPager

        parent.requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);
    }


}
