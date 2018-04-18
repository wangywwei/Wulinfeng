package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.view.HoRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class TabRecyclerAdapter extends RecyclerView.Adapter<TabRecyclerAdapter.ViewHolde> {
    private Context context;
    private ArrayList<HomeRecommendBean.DataBean.PlayBackVideoListBean> list;

    public void setList(ArrayList<HomeRecommendBean.DataBean.PlayBackVideoListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }



    public TabRecyclerAdapter(Context context, ArrayList<HomeRecommendBean.DataBean.PlayBackVideoListBean> list) {
        this.context = context;
        if(list == null){
            list = new ArrayList<>();
        }else{
            this.list = list;
        }
    }

    @Override
    public TabRecyclerAdapter.ViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_recycler_item, null);
        HoRecyclerView.LayoutParams params =new HoRecyclerView.LayoutParams(
                HoRecyclerView.LayoutParams.MATCH_PARENT, HoRecyclerView.LayoutParams.WRAP_CONTENT);
        //view.setLayoutParams(params);
        ViewHolde viewHolder = new ViewHolde(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TabRecyclerAdapter.ViewHolde holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onItemclickLinter){
                    onItemclickLinter.onItemClicj(position);
                }
            }
        });
        Glide.with(context).load(URLS.IMG+list.get(position).getPublicityImage()).error(R.drawable.wlf_deimg).into(holder.id_index_gallery_item_image);
        holder.id_index_gallery_item_text.setText(list.get(position).getVideoName());

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class ViewHolde extends RecyclerView.ViewHolder{
        ImageView id_index_gallery_item_image;
        TextView id_index_gallery_item_text;
        public ViewHolde(View itemView) {
            super(itemView);
            id_index_gallery_item_image= (ImageView) itemView.findViewById(R.id.id_index_gallery_item_image);
            id_index_gallery_item_text= (TextView) itemView.findViewById(R.id.id_index_gallery_item_text);

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
