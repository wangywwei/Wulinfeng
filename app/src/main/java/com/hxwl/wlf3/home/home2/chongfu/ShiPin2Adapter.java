package com.hxwl.wlf3.home.home2.chongfu;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.ShiPin1Adapter;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/22.
 */

public class ShiPin2Adapter extends RecyclerView.Adapter<ShiPin2Adapter.ViewHolder> {
    private Context context;
    private ArrayList<DynamicBean.DataBean.EventBean.AgainstListBean> list;

    public ShiPin2Adapter(Context context, ArrayList<DynamicBean.DataBean.EventBean.AgainstListBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ShiPin2Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.huodong_adapter_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        ShiPin2Adapter.ViewHolder viewHolder = new ShiPin2Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShiPin2Adapter.ViewHolder holder, final int position) {

        try {

//            String name = list.get(position).get
//            holder.huodong_content.setText(name);

            int againstId = list.get(position).getAgainstId();
            if (againstId==1){

                holder.shipin1_img.setImageResource(R.drawable.bofangbiaoqian1);
                String winner = list.get(position).getWinner();
                holder.shipin1_name.setText(winner);
                String winWay = list.get(position).getWinWay();
                holder.shipin1_name1.setText(winWay);
                String againstName = list.get(position).getAgainstName();
                holder.shipin1_content.setText(againstName);

                holder.shipin1_name.setTextColor(Color.parseColor("#C52720"));
                holder.shipin1_name1.setTextColor(Color.parseColor("#C52720"));
                holder.shipin1_name.setBackgroundResource(R.drawable.zixunbiaoqian1);
                holder.shipin1_name1.setBackgroundResource(R.drawable.zixunbiaoqian1);


            }else  if (againstId==2){
                holder.shipin1_img.setImageResource(R.drawable.bofangbiaoqian2);
                String winner = list.get(position).getWinner();
                holder.shipin1_name.setText(winner);
                String winWay = list.get(position).getWinWay();
                holder.shipin1_name1.setText(winWay);
                String againstName = list.get(position).getAgainstName();
                holder.shipin1_content.setText(againstName);

                holder.shipin1_name.setTextColor(Color.parseColor("#0074FF"));
                holder.shipin1_name1.setTextColor(Color.parseColor("#0074FF"));
                holder.shipin1_name.setBackgroundResource(R.drawable.zixunbiaoqian2);
                holder.shipin1_name1.setBackgroundResource(R.drawable.zixunbiaoqian2);
            }

        }catch (Exception e){
        }

        try {
            holder.shipin1_layout.setOnClickListener(new View.OnClickListener() {
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

        public ImageView shipin1_img;
        public TextView shipin1_name,shipin1_name1,shipin1_content;
        public LinearLayout shipin1_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            shipin1_img = (ImageView) itemView.findViewById(R.id.shipin1_img);
            this.shipin1_name = (TextView) itemView.findViewById(R.id.shipin1_name);
            this.shipin1_name1 = (TextView) itemView.findViewById(R.id.shipin1_name1);
            this.shipin1_content = (TextView) itemView.findViewById(R.id.shipin1_content);
            shipin1_layout = (LinearLayout) itemView.findViewById(R.id.shipin1_layout);
        }
    }
}
