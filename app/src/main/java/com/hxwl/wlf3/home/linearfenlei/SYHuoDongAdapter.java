package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.NormalWebviewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */
/*
* */
public class SYHuoDongAdapter extends RecyclerView.Adapter<SYHuoDongAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Home3Bean.DataBean.SchedulesBean> list;
private int ii;

    public SYHuoDongAdapter(Context context, ArrayList<Home3Bean.DataBean.SchedulesBean> list,int i) {
        this.context = context;
        this.list = list;
        this.ii=i;
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
        List<Home3Bean.DataBean.SchedulesBean.ActivityListBean> activityList = list.get(ii).getActivityList();

        try {

            holder.huodong_layout.setVisibility(View.VISIBLE);

            String name = list.get(ii).getActivityList().get(position).getName();

            holder.huodong_content.setText(name);

        }catch (Exception e){
        }

        try {
        holder.huodong_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NormalWebviewActivity.class);
                intent.putExtra("url",list.get(ii).getActivityList().get(position).getUrl());
                intent.putExtra("title","活动");
                context.startActivity(intent);




            }
        });
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView huodong_content;
        private LinearLayout huodong_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.huodong_content = (TextView) itemView.findViewById(R.id.huodong_content);
            this.huodong_layout = (LinearLayout) itemView.findViewById(R.id.huodong_layout);
        }
    }
}
