package com.hxwl.wlf3.home.home2.chongfu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/20.
 */

public class DuiZhen1Adapter extends RecyclerView.Adapter<DuiZhen1Adapter.ViewHolder> {
    private Context context;
    private ArrayList<DynamicBean.DataBean.EventBean> list;

    public DuiZhen1Adapter(Context context, ArrayList<DynamicBean.DataBean.EventBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public DuiZhen1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.beiyong_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        DuiZhen1Adapter.ViewHolder viewHolder = new DuiZhen1Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DuiZhen1Adapter.ViewHolder holder, final int position) {

        try {
            String redName = list.get(0).getAgainstList().get(position).getRedName();
            holder.duizhen_name1.setText(redName);
        }catch (Exception e){
        }


        try {
            Glide.with(context).load(URLS.IMG+list.get(0).getAgainstList().get(position).getRedHeadImg() ).into(holder.duizhen_img1);
        }catch (Exception e){
        }

        try {
            holder.duizhen_address1.setText(list.get(position).getAgainstList().get(position).getRedClub());
        }catch (Exception e){
        }


        try {
            String blueName = list.get(0).getAgainstList().get(position).getBlueName();
            holder.duizhen_name2.setText(blueName);
        }catch (Exception e){
        }

        try {
            Glide.with(context).load(URLS.IMG+list.get(position).getAgainstList().get(position).getBlueHeadImg()).into(holder.duizhen_img2);
        }catch (Exception e){
        }



        try {
            holder.duizhen_address2.setText(list.get(position).getAgainstList().get(position).getBlueClub());
        }catch (Exception e){
        }

        try {
            holder.duizhen_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了第"+position+"个队列", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView duizhen_img1,duizhen_img2;
        public TextView duizhen_address1,duizhen_address2,
                duizhen_name1,duizhen_name2;
        public LinearLayout duizhen_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.duizhen_img1 = (ImageView) itemView.findViewById(R.id.duizhen_img1);
            this.duizhen_img2 = (ImageView) itemView.findViewById(R.id.duizhen_img2);
            this.duizhen_name1 = (TextView) itemView.findViewById(R.id.duizhen_name1);
            this.duizhen_name2 = (TextView) itemView.findViewById(R.id.duizhen_name2);
            this.duizhen_address1 = (TextView) itemView.findViewById(R.id.duizhen_address1);
            this.duizhen_address2 = (TextView) itemView.findViewById(R.id.duizhen_address2);
            this.duizhen_layout = (LinearLayout) itemView.findViewById(R.id.duizhen_layout);
        }
    }
}
