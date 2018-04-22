package com.hxwl.wlf3.home.home1;

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

import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.linearfenlei.SYHuoDongAdapter;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/22.
 */

public class ShiPin1Adapter extends RecyclerView.Adapter<ShiPin1Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean> list;

    public ShiPin1Adapter(Context context, ArrayList<Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ShiPin1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.huodong_adapter_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        ShiPin1Adapter.ViewHolder viewHolder = new ShiPin1Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShiPin1Adapter.ViewHolder holder, final int position) {
        try {

            int againstId = list.get(position).getResult();
            String winner = list.get(position).getWinner();//winner
            Toast.makeText(context, ""+winner, Toast.LENGTH_SHORT).show();
            holder.shipin1_name.setText(winner);

            if (againstId==1){
                Toast.makeText(context, ""+againstId, Toast.LENGTH_SHORT).show();


                try {
                    holder.shipin1_img.setImageResource(R.drawable.bofangbiaoqian1);
                }catch (Exception e){}
                try {

                    Toast.makeText(context, "11111111111111====="+winner, Toast.LENGTH_SHORT).show();

                }catch (Exception e){}


                try {

                    String winWay = list.get(position).getWinWay();
                    holder.shipin1_name1.setText(winWay);
                }catch (Exception e){}
                try {

                    String againstName = list.get(position).getAgainstName();
                    holder.shipin1_content.setText(againstName);
                }catch (Exception e){}
                try {
                    holder.shipin1_name.setTextColor(Color.parseColor("#C52720"));
                    holder.shipin1_name1.setTextColor(Color.parseColor("#C52720"));
                }catch (Exception e){}
                try {
                    holder.shipin1_name.setBackgroundResource(R.drawable.zixunbiaoqian1);
                    holder.shipin1_name1.setBackgroundResource(R.drawable.zixunbiaoqian1);

                }catch (Exception e){}



            }else  if (againstId==2){

                try {
                    holder.shipin1_img.setImageResource(R.drawable.bofangbiaoqian2);
                }catch (Exception e){}
                try {
//                    String winner = list.get(position).getWinner();
//                    holder.shipin1_name.setText(winner);
                }catch (Exception e){}

                try {
                    String winWay = list.get(position).getWinWay();
                    holder.shipin1_name1.setText(winWay);
                }catch (Exception e){}
                try {

                    String againstName = list.get(position).getAgainstName();
                    holder.shipin1_content.setText(againstName);
                }catch (Exception e){}

                try {
                    holder.shipin1_name.setTextColor(Color.parseColor("#0074FF"));
                    holder.shipin1_name1.setTextColor(Color.parseColor("#0074FF"));
                }catch (Exception e){}


                try {

                    holder.shipin1_name.setBackgroundResource(R.drawable.zixunbiaoqian2);
                    holder.shipin1_name1.setBackgroundResource(R.drawable.zixunbiaoqian2);
                }catch (Exception e){}
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
