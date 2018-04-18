package com.hxwl.newwlf.schedule.recent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.hxwl.newwlf.modlebean.RecentJinqiBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.view.MyListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/29.
 */

public class RecentHeader2 extends LinearLayout {
    private Context context;
    private MyListView recent_listview;
    private ArrayList<RecentJinqiBean.DataBean.NotBeginScheduleListBean> list=new ArrayList<>();
    private RecentHeaderAdapter2 adapter;
    public void setList(ArrayList<RecentJinqiBean.DataBean.NotBeginScheduleListBean> list) {
        this.list = list;
        adapter=new RecentHeaderAdapter2(list,context);
        recent_listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public RecentHeader2(Context context) {
        super(context);
        initView(context);
    }


    private void initView(final Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.recentheader, this);
        recent_listview= (MyListView) view.findViewById(R.id.recent_listview);

        recent_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                context.startActivity(HuiGuDetailActivity.getIntent(context,list.get(position).getScheduleId()+"",0));
            }
        });




    }


}
