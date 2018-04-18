package com.hxwl.newwlf.wulin.arts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxwl.newwlf.modlebean.HuatiListBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/26.
 */

public class HuatiRecyclerAdapter extends RecyclerView.Adapter<HuatiRecyclerAdapter.ViewHolder> {
    private ArrayList<HuatiListBean.DataBean> list;
    private Context context;


    public interface OnItemclickLinter{
        public void onItemClicj(int position);
    }

    private OnItemclickLinter onItemclickLinter;

    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }

    public HuatiRecyclerAdapter(ArrayList<HuatiListBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.huati_recycler_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.huati_title.setText(list.get(position).getTopicName());
        holder.huati_number.setText(list.get(position).getJoinNum()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onItemclickLinter){
                    onItemclickLinter.onItemClicj(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView huati_title;
        private TextView huati_number;
        public ViewHolder(View itemView) {
            super(itemView);
            huati_title= (TextView) itemView.findViewById(R.id.huati_title);
            huati_number= (TextView) itemView.findViewById(R.id.huati_number);
        }
    }
}
