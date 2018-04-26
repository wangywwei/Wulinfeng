package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home2.ListViewAdapter;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.view.MyListView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/18.
 */
/*
*
* 活动的界面布局，
* */
public class HuoDongLayout extends LinearLayout {
    private Context context;
    private View view;
    private ArrayList<Home3Bean.DataBean.SchedulesBean.ActivityListBean> dataBean;
    private ArrayList<Home3Bean.DataBean.SchedulesBean.ActivityListBean> arrayList = new ArrayList();
    private MyListView syhuodong_mylistview;
    private ListViewAdapter listViewAdapter;

    public void setBean(final ArrayList<Home3Bean.DataBean.SchedulesBean.ActivityListBean> bean) {
        this.dataBean = bean;
        arrayList.clear();
        arrayList.add(dataBean.get(0));

        listViewAdapter.notifyDataSetChanged();

    }

    public HuoDongLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.syhuodong_item, this);
        syhuodong_mylistview = (MyListView) view.findViewById(R.id.syhuodong_mylistview);

        listViewAdapter = new ListViewAdapter(context,arrayList);
        syhuodong_mylistview.setAdapter(listViewAdapter);
    }

}
