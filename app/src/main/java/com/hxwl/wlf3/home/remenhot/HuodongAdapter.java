package com.hxwl.wlf3.home.remenhot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.NormalWebviewActivity;

import java.util.ArrayList;

public class HuodongAdapter extends RecyclerView.Adapter<HuodongAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Home3Bean.DataBean.ActivitiesBean> list;

    public HuodongAdapter(Context context, ArrayList<Home3Bean.DataBean.ActivitiesBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.huodong_grid, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GlidUtils.setGrid(context, URLS.IMG + list.get(position).getImage(), holder.huodong_img);
        holder.huodong_title.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NormalWebviewActivity.class);
                intent.putExtra("url",list.get(position).getUrl());
                intent.putExtra("title","活动");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView huodong_img;
        public TextView huodong_title;

        public ViewHolder(View itemView) {
            super(itemView);
            this.huodong_img = (ImageView) itemView.findViewById(R.id.huodong_img);
            this.huodong_title = (TextView) itemView.findViewById(R.id.huodong_title);
        }
    }
}
