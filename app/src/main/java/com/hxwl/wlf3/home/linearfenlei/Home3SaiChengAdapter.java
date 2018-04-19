package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.graphics.Color;
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
* 赛程的适配器
* */
public class Home3SaiChengAdapter extends RecyclerView.Adapter<Home3SaiChengAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean> list;
    private int result;

    public Home3SaiChengAdapter(Context context, ArrayList<Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public Home3SaiChengAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saishi_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        Home3SaiChengAdapter.ViewHolder viewHolder = new Home3SaiChengAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Home3SaiChengAdapter.ViewHolder holder, final int position) {


        try {
            result = list.get(position).getId();
        } catch (Exception e) {
        }

        try {
            if (result == 1) {
                holder.home3saishi_img.setImageResource(R.drawable.bofangbiaoqian1);
            } else {
                holder.home3saishi_img.setImageResource(R.drawable.bofangbiaoqian2);
            }
        } catch (Exception e) {
        }

        try {
            holder.home3saishi_name.setText(list.get(position).getBlueName());
            if (result == 1) {
                holder.home3saishi_name.setBackgroundResource(R.drawable.zixunbiaoqian1);
                holder.home3saishi_name.setTextColor(Color.parseColor("#C52720"));
            } else {
                holder.home3saishi_name.setBackgroundResource(R.drawable.zixunbiaoqian2);
                holder.home3saishi_name.setTextColor(Color.parseColor("#0074FF"));
            }
        } catch (Exception e) {
        }

/*

*
* */
        try {
            holder.home3saishi_name1.setText(list.get(position).getRedName());
            if (result == 1) {
                holder.home3saishi_name1.setBackgroundResource(R.drawable.zixunbiaoqian1);
                holder.home3saishi_name1.setTextColor(Color.parseColor("#C52720"));
            } else {
                holder.home3saishi_name1.setBackgroundResource(R.drawable.zixunbiaoqian2);
                holder.home3saishi_name1.setTextColor(Color.parseColor("#0074FF"));
            }
        } catch (Exception e) {
        }


        try {
            holder.home3saishi_content.setText(list.get(position).getRedClub());


        } catch (Exception e) {

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView home3saishi_img;
        public TextView home3saishi_name, home3saishi_name1, home3saishi_content;

        public ViewHolder(View itemView) {
            super(itemView);

            this.home3saishi_img = (ImageView) itemView.findViewById(R.id.home3saishi_img);
            this.home3saishi_name = (TextView) itemView.findViewById(R.id.home3saishi_name);
            this.home3saishi_name1 = (TextView) itemView.findViewById(R.id.home3saishi_name1);
            this.home3saishi_content = (TextView) itemView.findViewById(R.id.home3saishi_content);


        }
    }
}
