package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hxwl.common.customview.GridSpacingItemDecoration;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class RecommendZiXun extends LinearLayout implements View.OnClickListener {
    private Context context;
    private RecyclerView zixun_gridview;
    private ZiXunAdapter adapter;
    private ArrayList<HomeRecommendBean.DataBean.HotNewsBean> list=new ArrayList<>();
    private TextView zixun_gengduo;
    public RecommendZiXun(Context context) {
        this(context,null);
    }
    private HomeRecommendBean.DataBean bean;

    public void setBean(HomeRecommendBean.DataBean bean) {
        this.bean = bean;
        list.clear();
        list.addAll(bean.getHotNews());
        adapter.notifyDataSetChanged();

    }

    public RecommendZiXun(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RecommendZiXun(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context=context;
        View view= LayoutInflater.from(context).inflate(R.layout.zixun_recommend,this);
        zixun_gridview= (RecyclerView) view.findViewById(R.id.zixun_gridview);
        zixun_gengduo= (TextView) view.findViewById(R.id.zixun_gengduo);

        adapter=new ZiXunAdapter(context,list);
        zixun_gridview.setLayoutManager(new GridLayoutManager(context, 2));

        int spanCount = 2;//跟布局里面的spanCount属性是一致的
        int spacing = 16;//每一个矩形的间距
        boolean includeEdge = false;//如果设置成false那边缘地带就没有间距s
        //设置每个item间距
        zixun_gridview.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        zixun_gridview.setAdapter(adapter);

        zixun_gengduo.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zixun_gengduo:
                context.startActivity(ZixunActivity.getIntent(context));
                break;
        }
    }
}
