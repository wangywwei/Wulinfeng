package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/18.
 */
/*
*
* 活动的界面适配器。目前还没有用
* */
public class SYHuoDongAdapter extends RecyclerView.Adapter<SYHuoDongAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Home3Bean.DataBean.SchedulesBean.ActivityListBean> list;

    public SYHuoDongAdapter(Context context, ArrayList<Home3Bean.DataBean.SchedulesBean.ActivityListBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.syhuodong_adapter_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {

            String name = list.get(position).getName();
            holder.huodong_content.setText(name);
        }catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView huodong_content;

        public ViewHolder(View itemView) {
            super(itemView);
            this.huodong_content = (TextView) itemView.findViewById(R.id.huodong_content);
        }
    }
}
