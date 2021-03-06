package com.hxwl.wlf3.home.home2.chongfu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/20.
 */

public class SYHuoDong1Adapter extends RecyclerView.Adapter<SYHuoDong1Adapter.ViewHolder> {
    private Context context;
    private ArrayList<DynamicBean.DataBean.ActivityListBean> list;

    public SYHuoDong1Adapter(Context context, ArrayList<DynamicBean.DataBean.ActivityListBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public SYHuoDong1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.syhuodong_adapter_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        SYHuoDong1Adapter.ViewHolder viewHolder = new SYHuoDong1Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SYHuoDong1Adapter.ViewHolder holder, final int position) {

        try {

            String name = list.get(position).getName();
            holder.huodong_content.setText(name);
        }catch (Exception e){
        }

        try {
            holder.huodong_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了活动", Toast.LENGTH_SHORT).show();
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
