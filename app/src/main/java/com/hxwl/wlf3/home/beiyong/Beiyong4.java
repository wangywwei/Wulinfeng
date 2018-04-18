package com.hxwl.wlf3.home.beiyong;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.LiveAdapter;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16.
 */

public class Beiyong4 extends LinearLayout {

    private Context context;
    private View view;
    private ArrayList<Home3Bean.DataBean.ActivitiesBean> strings=new ArrayList<>();
    private LiveAdapter liveAdapter;
    private Home3Bean.DataBean dataBean;
    private RecyclerView home3_live_recycler;


    public void setBean(final Home3Bean.DataBean bean) {
        this.dataBean=bean;
        strings.clear();
        strings.addAll(dataBean.getActivities());
        liveAdapter.notifyDataSetChanged();
    }

    public Beiyong4(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.beiyong, this);

//        liveAdapter = new LiveAdapter(context,strings);
//        home3_live_recycler = (RecyclerView) view.findViewById(R.id.home3_live_recycler);
//        home3_live_recycler.setLayoutManager(new GridLayoutManager(context, 1));
//        int spanCount = 2;//跟布局里面的spanCount属性是一致的
//        int spacing = 16;//每一个矩形的间距
//        boolean includeEdge = false;//如果设置成false那边缘地带就没有间距s
//
//        //设置每个item间距
//        home3_live_recycler.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
//
//        liveAdapter=new LiveAdapter(context,strings);
//        home3_live_recycler.setAdapter(liveAdapter);
    }

}
