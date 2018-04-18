package com.hxwl.wlf3.home.home1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/16.
 */
/*
* 直播中的适配器
* */
public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Home3Bean.DataBean.LiveListBean> list;//记得改一下

    public LiveAdapter(Context context, ArrayList<Home3Bean.DataBean.LiveListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LiveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.live_adapter_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        LiveAdapter.ViewHolder viewHolder = new LiveAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LiveAdapter.ViewHolder holder, final int position) {
        holder.zhibo_name.setText(list.get(position).getScheduleName());
        holder.zhibo_linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "我点击了"+position+"条数据", Toast.LENGTH_SHORT).show();
            }
        });

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, NormalWebviewActivity.class);
//                intent.putExtra("url",list.get(position).getUrl());
//                intent.putExtra("title","活动");
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout zhibo_linearlayout;
        public TextView zhibo_name;
        public ViewHolder(View itemView) {
            super(itemView);
            this.zhibo_linearlayout = (LinearLayout) itemView.findViewById(R.id.zhibo_linearlayout);
            this.zhibo_name = (TextView) itemView.findViewById(R.id.zhibo_name);
        }
    }
}
