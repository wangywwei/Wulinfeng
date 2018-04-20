package com.hxwl.wlf3.home.home2;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.linearfenlei.DuiZhenAdapter;
import com.hxwl.wlf3.home.linearfenlei.HuoDongLayout;
import com.hxwl.wulinfeng.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public class DuiZhen1Layout extends LinearLayout {
    private Context context;
    private View view;
    private DuiZhen1Adapter beiyongadapter;
    private DynamicBean.DataBean dataBean;
    private XRecyclerView duizhen_xrecycler;
    private TextView duizhen_xinxi;
    private TextView duizhen_shijian;
    private ArrayList<DynamicBean.DataBean.EventBean> arrayList = new ArrayList();
    private RelativeLayout duizhen_relative;
    private HuoDong1Layout huoDongLayout;

    public void setBean(final DynamicBean.DataBean bean) {
        this.dataBean = bean;


        duizhen_shijian.setVisibility(View.GONE);

        try {
            String title = dataBean.getEvent().getTitle();
            duizhen_xinxi.setText(title);
        }catch (Exception e){
        }
        arrayList.clear();//getAgainstListBean
        arrayList.add(dataBean.getEvent());
        beiyongadapter.notifyDataSetChanged();


/*
* 活动
* */

        try {
            List<DynamicBean.DataBean.ActivityListBean> activityList = dataBean.getActivityList();
            if(null == activityList || activityList.size() ==0 ){
                //为空的情况
                return;
            }else{
                duizhen_relative.removeAllViews();//清空布局
                huoDongLayout = new HuoDong1Layout(context);
                duizhen_relative.addView(huoDongLayout);
                huoDongLayout.setBean(dataBean.getActivityList().get(0));
            }
        }catch (Exception e){

        }



    }


    public DuiZhen1Layout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.duizheng_item, this);
        duizhen_xrecycler = (XRecyclerView) view.findViewById(R.id.duizhen_xrecycler);
        duizhen_shijian = (TextView) view.findViewById(R.id.duizhen_shijian);
        duizhen_xinxi = (TextView) view.findViewById(R.id.duizhen_xinxi);

        duizhen_relative = (RelativeLayout) view.findViewById(R.id.duizhen_relative);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        duizhen_xrecycler.setLayoutManager(layoutManager);
        duizhen_xrecycler.setNestedScrollingEnabled(false);
        try {
            beiyongadapter = new DuiZhen1Adapter(context, arrayList);//对阵的适配器
            duizhen_xrecycler.setAdapter(beiyongadapter);
        }catch (Exception e){
        }

    }

}
