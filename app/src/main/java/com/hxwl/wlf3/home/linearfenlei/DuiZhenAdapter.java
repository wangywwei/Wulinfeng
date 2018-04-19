package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16.
 */
/*
* 对阵适配器
* */
public class DuiZhenAdapter extends RecyclerView.Adapter<DuiZhenAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean> list;

    public DuiZhenAdapter(Context context, ArrayList<Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public DuiZhenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.beiyong_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        DuiZhenAdapter.ViewHolder viewHolder = new DuiZhenAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DuiZhenAdapter.ViewHolder holder, final int position) {

        try {
            String blueName1 = list.get(position).getBlueName();
            holder.duizhen_name1.setText(blueName1);
        }catch (Exception e){
        }


        try {
            Glide.with(context).load(URLS.IMG+list.get(position).getBlueHeadImg() ).into(holder.duizhen_img1);
        }catch (Exception e){
        }

        try {
            String redName = list.get(position).getRedName();
            holder.duizhen_name2.setText(redName);
        }catch (Exception e){
        }

        try {
            Glide.with(context).load(URLS.IMG+list.get(position).getRedHeadImg() ).into(holder.duizhen_img2);
        }catch (Exception e){
        }

        try {
            holder.duizhen_address1.setText(list.get(position).getRedClub());
        }catch (Exception e){
        }

        try {
            holder.duizhen_address2.setText(list.get(position).getRedClub());
        }catch (Exception e){
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView duizhen_img1,duizhen_img2;
        public TextView duizhen_address1,duizhen_address2,
                duizhen_name1,duizhen_name2;

        public ViewHolder(View itemView) {
            super(itemView);
            this.duizhen_img1 = (ImageView) itemView.findViewById(R.id.duizhen_img1);
            this.duizhen_img2 = (ImageView) itemView.findViewById(R.id.duizhen_img2);
            this.duizhen_name1 = (TextView) itemView.findViewById(R.id.duizhen_name1);
            this.duizhen_name2 = (TextView) itemView.findViewById(R.id.duizhen_name2);
            this.duizhen_address1 = (TextView) itemView.findViewById(R.id.duizhen_address1);
            this.duizhen_address2 = (TextView) itemView.findViewById(R.id.duizhen_address2);

        }
    }
}
