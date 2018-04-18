package com.hxwl.newwlf.sousuo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxwl.newwlf.modlebean.SearchHuatiBean;
import com.hxwl.newwlf.wulin.arts.HuatiXQActivity;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/26.
 */

public class HuatiRecycAdapter2 extends RecyclerView.Adapter<HuatiRecycAdapter2.ViewHodle> {
    private Context context;
    private ArrayList<SearchHuatiBean.DataBean> list;


    public HuatiRecycAdapter2(Context context, ArrayList<SearchHuatiBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public HuatiRecycAdapter2.ViewHodle onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.huati_recycler_item, null);
        return  new ViewHodle(view);
    }

    @Override
    public void onBindViewHolder(HuatiRecycAdapter2.ViewHodle holder, int position) {
        Log.e("TAG", "position: "+position );
        Log.e("TAG", "onBindViewHolder: "+list.size() );
        final SearchHuatiBean.DataBean dataBean=list.get(position);
        if (dataBean!=null){
            holder.huati_title.setText(dataBean.getTopicName());
            holder.huati_number.setText(dataBean.getJoinNum() + "");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(HuatiXQActivity.getIntent(context,dataBean.getId(),
                        dataBean.getTopicName(),
                        dataBean.getContent(),
                        dataBean.getImage(),
                        dataBean.getJoinNum()+""));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size()+1:0;
    }

    class ViewHodle extends RecyclerView.ViewHolder {
        public TextView huati_title;
        public TextView huati_number;

        public ViewHodle(View itemView) {
            super(itemView);
            huati_title = (TextView) itemView.findViewById(R.id.huati_title);
            huati_number = (TextView) itemView.findViewById(R.id.huati_number);
        }
    }
}
