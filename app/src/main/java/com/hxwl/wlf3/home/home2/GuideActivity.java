package com.hxwl.wlf3.home.home2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxwl.common.tencentplay.utils.TCUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.GuideBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.Home3Adapter;
import com.hxwl.wlf3.home.linearfenlei.DuiZhenLayout;
import com.hxwl.wlf3.home.linearfenlei.PureTextLayout;
import com.hxwl.wlf3.home.linearfenlei.VideoListlayout;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 *
 * 赛事指南---适配器
 *
 * Created by Administrator on 2018/4/20.
 */

public class GuideActivity extends RecyclerView.Adapter<GuideActivity.ViewHolder> {
    private Context context;
    private ArrayList<GuideBean.DataBean> list6;

    public GuideActivity(Context context, ArrayList<GuideBean.DataBean> list) {
        this.context = context;
        this.list6 = list;
    }

    @Override
    public GuideActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.guide_activity, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        GuideActivity.ViewHolder viewHolder = new GuideActivity.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GuideActivity.ViewHolder holder, final int position) {
        try {
            String value1 = list6.get(position).getValue();
            if (value1.equals("2")){
                holder.guidea_lauoyt.setVisibility(View.GONE);//隐藏
            }else {
                holder.guidea_name.setText(list6.get(position).getKey());
                String showType = list6.get(position).getShowType();
                try {
                    if (showType.equals("0")) {
                        holder.guidea_content.setVisibility(View.VISIBLE);//显示
                        holder.guidea_layout.setVisibility(View.GONE);//隐藏
                        String value = list6.get(position).getValue();
                        holder.guidea_content.setText(value.replace("\\\n","\n"));
                    } else {
                        holder.guidea_layout.setVisibility(View.VISIBLE);//显示
                        holder.guidea_content.setVisibility(View.GONE);//隐藏
                        holder.guidea_weizhi.setText(list6.get(position).getValue());
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }

        try {
            holder.guidea_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了地图", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
        }


    }

    @Override
    public int getItemCount() {
        return list6.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView guidea_name;
        private TextView guidea_content;
        private LinearLayout guidea_layout,guidea_lauoyt;
        private TextView guidea_weizhi;

        public ViewHolder(View itemView) {
            super(itemView);
            guidea_name = (TextView) itemView.findViewById(R.id.guidea_name);
            guidea_content = (TextView) itemView.findViewById(R.id.guidea_content);
            guidea_layout = (LinearLayout) itemView.findViewById(R.id.guidea_layout);
            guidea_weizhi = (TextView) itemView.findViewById(R.id.guidea_weizhi);
            guidea_lauoyt= (LinearLayout) itemView.findViewById(R.id.guidea_lauoyt);
        }
    }
}
