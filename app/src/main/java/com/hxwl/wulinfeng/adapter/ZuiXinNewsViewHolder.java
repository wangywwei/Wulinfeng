package com.hxwl.wulinfeng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.youth.banner.Banner;

/**
 * Created by Allen on 2017/5/26.
 * 最新新闻R引用
 */

public class ZuiXinNewsViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv_news_img ;
    public ImageView view_line ;
    public TextView tv_title ;
    public TextView tv_time ;
    public TextView tv_kanguo ;
    public LinearLayout llyt_toutiao_news ;

    public ZuiXinNewsViewHolder(View itemView) {
        super(itemView);
        iv_news_img = (ImageView)itemView.findViewById(R.id.iv_news_img);
        view_line = (ImageView)itemView.findViewById(R.id.view_line);
        tv_title = (TextView)itemView.findViewById(R.id.tv_title);
        tv_time = (TextView)itemView.findViewById(R.id.tv_time);
        tv_kanguo = (TextView)itemView.findViewById(R.id.tv_kanguo);
        llyt_toutiao_news = (LinearLayout) itemView.findViewById(R.id.llyt_toutiao_news);
    }
}
