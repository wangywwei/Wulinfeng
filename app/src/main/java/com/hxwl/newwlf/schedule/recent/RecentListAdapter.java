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

import com.hxwl.newwlf.home.home.intent.DuizhengVideoActivity;
import com.hxwl.newwlf.modlebean.RecentJinqiBean;
import com.hxwl.newwlf.modlebean.SaichengHuifaBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.VideoDetailActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/25.
 */

public class RecentListAdapter extends RecyclerView.Adapter<RecentListAdapter.ViewHolder> {

    private ArrayList<SaichengHuifaBean.DataBean.AgainstInfoListBean> list;
    private Context context;

    public RecentListAdapter(ArrayList<SaichengHuifaBean.DataBean.AgainstInfoListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_lisr_item, parent, false);
        RecyclerView.LayoutParams params=new  RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder viewHolder=new ViewHolder(view);


        return viewHolder;
    }

    public interface OnItemclickLinter{
        public void onItemClicj(int position);
    }

    private OnItemclickLinter onItemclickLinter;

    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (list.get(position).getWinWay()){
            case "1":
                holder.recent_name2.setText("KO");
                break;
            case "2":
                holder.recent_name2.setText("TKO");
                break;
            case "3":
                holder.recent_name2.setText("判定");
                break;
        }

        switch (list.get(position).getResult()){
            case 1:
                holder.recent_name.setText(list.get(position).getRedPlayerName());
                break;
            case 2:
                holder.recent_name.setText(list.get(position).getBluePlayerName());
                break;
            case 3:

                break;

        }

        holder.recent_content.setText(list.get(position).getAgainstName());

        final String againstId=list.get(position).getAgainstId()+"";

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(DuizhengVideoActivity.getIntent(context,againstId));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView recent_bofang;
        public TextView recent_name;
        public TextView recent_name2;
        public TextView recent_content;
        public ViewHolder(View itemView) {
            super(itemView);
            this.recent_bofang = (ImageView) itemView.findViewById(R.id.recent_bofang);
            this.recent_name = (TextView) itemView.findViewById(R.id.recent_name);
            this.recent_name2 = (TextView) itemView.findViewById(R.id.recent_name2);
            this.recent_content = (TextView) itemView.findViewById(R.id.recent_content);
        }
    }
}
