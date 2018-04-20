package com.hxwl.wlf3.home.home2;

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
import com.hxwl.wlf3.home.linearfenlei.Home3SaiChengAdapter;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/20.
 */

public class Home3SaiCheng1Adapter extends RecyclerView.Adapter<Home3SaiCheng1Adapter.ViewHolder> {
    private Context context;
    private ArrayList<DynamicBean.DataBean.EventBean> list;
    private int result;

    public Home3SaiCheng1Adapter(Context context, ArrayList<DynamicBean.DataBean.EventBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public Home3SaiCheng1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saishi_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        Home3SaiCheng1Adapter.ViewHolder viewHolder = new Home3SaiCheng1Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Home3SaiCheng1Adapter.ViewHolder holder, final int position) {


        try {
            result = list.get(position).getId();
        } catch (Exception e) {
        }

        try {
            if (result == 1) {
                holder.home3saishi_img.setImageResource(R.drawable.bofangbiaoqian1);
            } else {
                holder.home3saishi_img.setImageResource(R.drawable.bofangbiaoqian2);
            }
        } catch (Exception e) {
        }

        try {
//            holder.home3saishi_name.setText(list.get(position).getBlueName());
            if (result == 1) {
                holder.home3saishi_name.setBackgroundResource(R.drawable.zixunbiaoqian1);
                holder.home3saishi_name.setTextColor(Color.parseColor("#C52720"));
            } else {
                holder.home3saishi_name.setBackgroundResource(R.drawable.zixunbiaoqian2);
                holder.home3saishi_name.setTextColor(Color.parseColor("#0074FF"));
            }
        } catch (Exception e) {
        }

        try {
//            holder.home3saishi_name1.setText(list.get(position).getRedName());
            if (result == 1) {
                holder.home3saishi_name1.setBackgroundResource(R.drawable.zixunbiaoqian1);
                holder.home3saishi_name1.setTextColor(Color.parseColor("#C52720"));
            } else {
                holder.home3saishi_name1.setBackgroundResource(R.drawable.zixunbiaoqian2);
                holder.home3saishi_name1.setTextColor(Color.parseColor("#0074FF"));
            }
        } catch (Exception e) {
        }


        try {
//            holder.home3saishi_content.setText(list.get(position).getRedClub());
        } catch (Exception e) {
        }


        try {
            holder.home3saishi_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了这个条目" + position, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView home3saishi_img;
        public TextView home3saishi_name, home3saishi_name1, home3saishi_content;
        private LinearLayout home3saishi_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            this.home3saishi_img = (ImageView) itemView.findViewById(R.id.home3saishi_img);
            this.home3saishi_name = (TextView) itemView.findViewById(R.id.home3saishi_name);
            this.home3saishi_name1 = (TextView) itemView.findViewById(R.id.home3saishi_name1);
            this.home3saishi_content = (TextView) itemView.findViewById(R.id.home3saishi_content);
            this.home3saishi_layout = (LinearLayout) itemView.findViewById(R.id.home3saishi_layout);
        }
    }
}
