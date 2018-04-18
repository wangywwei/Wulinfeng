package com.hxwl.newwlf.home.home.Recommend;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class GodRecyclerAdapter extends RecyclerView.Adapter<GodRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HomeRecommendBean.DataBean.GodListBean> list;


    public GodRecyclerAdapter(Context context, ArrayList<HomeRecommendBean.DataBean.GodListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GodRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommen_god_item, null);
        ViewHolder viewHolder = new ViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GodRecyclerAdapter.ViewHolder holder, final int position) {
        GlidUtils.setGrid2(context, URLS.IMG+list.get(position).getHeadImg(),holder.god_touxiang);
        holder.god_title.setText(list.get(position).getContent());
        holder.god_name.setText(list.get(position).getNickName());
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView god_touxiang;
        TextView god_name;
        TextView god_title;
        public ViewHolder(View itemView) {
            super(itemView);
            god_touxiang= (ImageView) itemView.findViewById(R.id.god_touxiang);
            god_name= (TextView) itemView.findViewById(R.id.god_name);
            god_title= (TextView) itemView.findViewById(R.id.god_title);
        }
    }

    private OnItemclickLinter onItemclickLinter;

    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }

    public interface OnItemclickLinter{
        public void onItemClicj(int position);
    }

}
