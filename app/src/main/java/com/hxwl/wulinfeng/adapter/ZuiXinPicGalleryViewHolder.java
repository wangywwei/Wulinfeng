package com.hxwl.wulinfeng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;

/**
 * Created by Allen on 2017/5/26.
 * 最新图集R引用
 */

public class ZuiXinPicGalleryViewHolder extends RecyclerView.ViewHolder {
    public ImageView iv_news_img ;
    public ImageView view_line ;
    public TextView tv_title ;
    public TextView tv_time ;
    public TextView tv_kanguo ;
    public TextView tv_count ;
    public RelativeLayout rl_img ;
    public ZuiXinPicGalleryViewHolder(View itemView) {
        super(itemView);
        iv_news_img = (ImageView)itemView.findViewById(R.id.iv_news_img);
        view_line = (ImageView)itemView.findViewById(R.id.view_line);
        tv_title = (TextView)itemView.findViewById(R.id.tv_title);
        tv_time = (TextView)itemView.findViewById(R.id.tv_time);
        tv_kanguo = (TextView)itemView.findViewById(R.id.tv_kanguo);
        tv_count = (TextView) itemView.findViewById(R.id.tv_count);
        rl_img = (RelativeLayout) itemView.findViewById(R.id.rl_img);

    }
}
