package com.hxwl.newwlf.schedule.recent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.RecentJinqiBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.DateUtils;
import com.hxwl.wulinfeng.view.MyListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/29.
 */

public class RecentHeaderAdapter extends BaseAdapter {
    private ArrayList<RecentJinqiBean.DataBean.LiveScheduleListBean> list;
    private Context context;

    public RecentHeaderAdapter(ArrayList<RecentJinqiBean.DataBean.LiveScheduleListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recent_item, null);
            v=new ViewHolder(convertView);
            convertView.setTag(v);
        } else {
            v= (ViewHolder) convertView.getTag();
        }

        v.recent_list.setVisibility(View.GONE);
        RecentJinqiBean.DataBean.LiveScheduleListBean liveScheduleBean=list.get(position);
        v.recent_tiem.setText(DateUtils.timet(liveScheduleBean.getScheduleBeginTime()));
        v.recent_title.setText(liveScheduleBean.getCompetitionName());
        GlidUtils.setGrid(context, URLS.IMG+liveScheduleBean.getPublicityImg(),v.recent_img);
        v.recent_content.setText(liveScheduleBean.getScheduleName());
        v.recent_address.setText(liveScheduleBean.getHoldAddress());
        v.recent_zhuangtai.setVisibility(View.VISIBLE);
        v.recent_zhuangtai2.setVisibility(View.GONE);
        v.recent_zhuangtai3.setVisibility(View.GONE);
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView recent_tiem;
        public TextView recent_title;
        public ImageView recent_img;
        public TextView recent_content;
        public TextView recent_address;
        public ImageView recent_zhuangtai;
        public ImageView recent_zhuangtai2;
        public ImageView recent_zhuangtai3;
        public RecyclerView recent_list;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.recent_tiem = (TextView) rootView.findViewById(R.id.recent_tiem);
            this.recent_title = (TextView) rootView.findViewById(R.id.recent_title);
            this.recent_img = (ImageView) rootView.findViewById(R.id.recent_img);
            this.recent_content = (TextView) rootView.findViewById(R.id.recent_content);
            this.recent_address = (TextView) rootView.findViewById(R.id.recent_address);
            this.recent_zhuangtai = (ImageView) rootView.findViewById(R.id.recent_zhuangtai);
            this.recent_zhuangtai2 = (ImageView) rootView.findViewById(R.id.recent_zhuangtai2);
            this.recent_zhuangtai3 = (ImageView) rootView.findViewById(R.id.recent_zhuangtai3);
            this.recent_list = (RecyclerView) rootView.findViewById(R.id.recent_recycler);
        }

    }
}
