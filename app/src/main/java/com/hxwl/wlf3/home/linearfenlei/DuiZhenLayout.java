package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxwl.common.customview.GridSpacingItemDecoration;
import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.LiveAdapter;
import com.hxwl.wulinfeng.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */
/*
* 对阵的Layout
* */
public class DuiZhenLayout extends LinearLayout {
    private Context context;
    private View view;
    private DuiZhenAdapter beiyongadapter;
    private Home3Bean.DataBean.SchedulesBean dataBean;
    private XRecyclerView duizhen_xrecycler;
    private TextView duizhen_xinxi;
    private TextView duizhen_shijian;
    private ArrayList<Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean> arrayList = new ArrayList();
    private RelativeLayout duizhen_relative;
    private HuoDongLayout huoDongLayout;

    private DynamicBean.DataBean datasqBean;
    public void setxqBean(final DynamicBean.DataBean bean) {
        this.datasqBean = bean;
        try {
            String eventTime = datasqBean.getEvent().getEventTime();
            duizhen_shijian.setText(eventTime);
        }catch (Exception e){
        }
        try {
            String title = datasqBean.getEvent().getTitle();
            duizhen_xinxi.setText(title);
        }catch (Exception e){
        }
//        arrayList.clear();
//        arrayList.add(datasqBean.getEvent().getAgainstListBean());
//        beiyongadapter.notifyDataSetChanged();





/*
* 活动
* */

//        try {
//            List<DynamicBean.DataBean.ActivityListBean> activityList = datasqBean;
//            if(null == activityList || activityList.size() ==0 ){
//                //为空的情况
//                return;
//            }else{
//                duizhen_relative.removeAllViews();//清空布局
//                huoDongLayout = new HuoDongLayout(context);
//                duizhen_relative.addView(huoDongLayout);
//                huoDongLayout.setBean(dataBean.getActivityList().get(0));
//            }
//        }catch (Exception e){

//        }



    }






    public void setBean(final Home3Bean.DataBean.SchedulesBean bean) {
        this.dataBean = bean;
        try {
            String eventTime = dataBean.getEvent().getEventTime();
            duizhen_shijian.setText(eventTime);
        }catch (Exception e){
        }
        try {
            String title = dataBean.getEvent().getTitle();
            duizhen_xinxi.setText(title);
        }catch (Exception e){
        }
        arrayList.clear();
        arrayList.add(dataBean.getEvent().getAgainstListBean());
        beiyongadapter.notifyDataSetChanged();





/*
* 活动
* */

        try {
            List<Home3Bean.DataBean.SchedulesBean.ActivityListBean> activityList = dataBean.getActivityList();
            if(null == activityList || activityList.size() ==0 ){
                //为空的情况
                return;
            }else{
                duizhen_relative.removeAllViews();//清空布局
                huoDongLayout = new HuoDongLayout(context);
                duizhen_relative.addView(huoDongLayout);
                huoDongLayout.setBean(dataBean.getActivityList().get(0));
            }
        }catch (Exception e){

        }



    }





    public DuiZhenLayout(Context context) {
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
            beiyongadapter = new DuiZhenAdapter(context, arrayList);//对阵的适配器
            duizhen_xrecycler.setAdapter(beiyongadapter);
        }catch (Exception e){
        }

    }

}
