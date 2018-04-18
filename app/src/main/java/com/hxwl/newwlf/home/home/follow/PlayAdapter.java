package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.QuanshouduizhengBean;
import com.hxwl.newwlf.modlebean.SengdaiVideoBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.util.DateUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/25.
 */

public class PlayAdapter extends RecyclerView.Adapter<PlayAdapter.ViewHolder> {
    private ArrayList<QuanshouduizhengBean.DataBean> list;
    private Context context;


    public PlayAdapter(ArrayList<QuanshouduizhengBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.play_item, parent, false);
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.play_content.setText(list.get(position).getAgainstName());
        GlidUtils.setGrid(context, URLS.IMG+list.get(position).getPublicityImg(),holder.play_img);
        if (!StringUtils.isBlank(list.get(position).getWinPlayer())){
            holder.play_name.setVisibility(View.VISIBLE);
            holder.play_name2.setVisibility(View.VISIBLE);
            holder.play_name.setText(list.get(position).getWinPlayer());
            holder.play_name2.setText(list.get(position).getWinWay());
        }else {
            holder.play_name.setVisibility(View.GONE);
            holder.play_name2.setVisibility(View.GONE);
        }


        holder.play_title.setText(DateUtils.timeslashData(list.get(position).getBeginTime())+"  "+list.get(position).getScheduleName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(HuiGuDetailActivity.getIntent(context,list.get(position).getScheduleId()+"",2));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView play_title;
        private ImageView play_img;
        private ImageView play_bofang;
        private TextView play_name;
        private TextView play_name2;
        private TextView play_content;

        public ViewHolder(View itemView) {
            super(itemView);
            play_title= (TextView) itemView.findViewById(R.id.play_title);
            play_img= (ImageView) itemView.findViewById(R.id.play_img);
            play_bofang= (ImageView) itemView.findViewById(R.id.play_bofang);
            play_name= (TextView) itemView.findViewById(R.id.play_name);
            play_name2= (TextView) itemView.findViewById(R.id.play_name2);
            play_content= (TextView) itemView.findViewById(R.id.play_content);

        }
    }
}
