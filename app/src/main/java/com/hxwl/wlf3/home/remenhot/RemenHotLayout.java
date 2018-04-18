package com.hxwl.wlf3.home.remenhot;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.common.customview.GridSpacingItemDecoration;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;
/*
* 更多
* */
public class RemenHotLayout extends LinearLayout implements View.OnClickListener {
    private Context context;
    private View view;
    private TextView genduohuodong;
    private RecyclerView huodong_grid;
    private HuodongAdapter huodongAdapter;
//    private ArrayList<Home3Bean.DataBean.ActivitiesBean> list=new ArrayList<>();

    private ArrayList<Home3Bean.DataBean.ActivitiesBean> list=new ArrayList<>();



    private Home3Bean.DataBean dataBean;
    public void setBean(final Home3Bean.DataBean bean) {
        this.dataBean=bean;
        list.clear();
        list.addAll(dataBean.getActivities());
        huodongAdapter.notifyDataSetChanged();
    }

    public RemenHotLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(final Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.remenhotlayout, this);
        genduohuodong= (TextView) view.findViewById(R.id.genduohuodong);
        genduohuodong.setOnClickListener(this);
        huodong_grid= (RecyclerView) view.findViewById(R.id.huodong_grid);
        huodong_grid.setLayoutManager(new GridLayoutManager(context, 2));

        int spanCount = 2;//跟布局里面的spanCount属性是一致的
        int spacing = 16;//每一个矩形的间距
        boolean includeEdge = false;//如果设置成false那边缘地带就没有间距s
        //设置每个item间距
        huodong_grid.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));


        huodongAdapter=new HuodongAdapter(context,list);
        huodong_grid.setAdapter(huodongAdapter);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.genduohuodong:
                context.startActivity(GenDuoActivity.getIntent(context));
                break;
        }
    }
}
