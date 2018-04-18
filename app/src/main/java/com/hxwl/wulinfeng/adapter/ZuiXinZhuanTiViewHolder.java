package com.hxwl.wulinfeng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;

/**
 * Created by Allen on 2017/5/26.
 * 最新专题R引用
 */

public class ZuiXinZhuanTiViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout rl_layout ;
    public RelativeLayout rl_layout1 ;
    public RelativeLayout rl_layout2 ;
    public RelativeLayout rl_layout3 ;
    public RelativeLayout rl_layout4 ;
    public RelativeLayout rl_clickmore ;

    public RelativeLayout rl_video_flg ;
    public RelativeLayout rl_video_flg1 ;
    public RelativeLayout rl_video_flg2 ;
    public RelativeLayout rl_video_flg3 ;
    public RelativeLayout rl_video_flg4 ;

    public LinearLayout ll_layout ;
    public LinearLayout ll_layout2 ;
    public TextView tv_title_name;

    public TextView tv_title1;
    public TextView tv_title2;
    public TextView tv_title3;
    public TextView tv_title4;
    public TextView tv_title5;

    public TextView tv_click_more;

    public ImageView iv_video_flg;
    public ImageView iv_video_flg1;
    public ImageView iv_video_flg2;
    public ImageView iv_video_flg3;
    public ImageView iv_video_flg4;

    public ImageView iv_big_img;
    public ImageView iv_item_img;
    public ImageView iv_item_img2;
    public ImageView iv_item_img3;
    public ImageView iv_item_img4;
    public ImageView view_line;
    public ImageView view_line_bottom;

    public ZuiXinZhuanTiViewHolder(View itemView) {
        super(itemView);
        rl_layout1 = (RelativeLayout)itemView.findViewById(R.id.rl_layout1);
        rl_layout2 = (RelativeLayout)itemView.findViewById(R.id.rl_layout2);
        rl_layout = (RelativeLayout)itemView.findViewById(R.id.rl_layout);
        rl_layout3 = (RelativeLayout)itemView.findViewById(R.id.rl_layout3);
        rl_layout4 = (RelativeLayout)itemView.findViewById(R.id.rl_layout4);
        rl_clickmore = (RelativeLayout)itemView.findViewById(R.id.rl_clickmore);

        rl_video_flg = (RelativeLayout)itemView.findViewById(R.id.rl_video_flg);
        rl_video_flg1 = (RelativeLayout)itemView.findViewById(R.id.rl_video_flg1);
        rl_video_flg2 = (RelativeLayout)itemView.findViewById(R.id.rl_video_flg2);
        rl_video_flg3 = (RelativeLayout)itemView.findViewById(R.id.rl_video_flg3);
        rl_video_flg4 = (RelativeLayout)itemView.findViewById(R.id.rl_video_flg4);

        ll_layout = (LinearLayout) itemView.findViewById(R.id.ll_layout);
        ll_layout2 = (LinearLayout) itemView.findViewById(R.id.ll_layout2);
        tv_title_name = (TextView) itemView.findViewById(R.id.tv_title_name);

        tv_title1 = (TextView) itemView.findViewById(R.id.tv_title1);
        tv_title2 = (TextView) itemView.findViewById(R.id.tv_title2);
        tv_title3 = (TextView) itemView.findViewById(R.id.tv_title3);
        tv_title4 = (TextView) itemView.findViewById(R.id.tv_title4);
        tv_title5 = (TextView) itemView.findViewById(R.id.tv_title5);

        tv_click_more = (TextView) itemView.findViewById(R.id.tv_click_more);

        view_line = (ImageView) itemView.findViewById(R.id.view_line);
        view_line_bottom = (ImageView) itemView.findViewById(R.id.view_line_bottom);

        iv_video_flg = (ImageView) itemView.findViewById(R.id.iv_video_flg);
        iv_video_flg1 = (ImageView) itemView.findViewById(R.id.iv_video_flg1);
        iv_video_flg2 = (ImageView) itemView.findViewById(R.id.iv_video_flg2);
        iv_video_flg3 = (ImageView) itemView.findViewById(R.id.iv_video_flg3);
        iv_video_flg4 = (ImageView) itemView.findViewById(R.id.iv_video_flg4);

        iv_big_img = (ImageView) itemView.findViewById(R.id.iv_big_img);
        iv_item_img = (ImageView) itemView.findViewById(R.id.iv_item_img);
        iv_item_img2 = (ImageView) itemView.findViewById(R.id.iv_item_img2);
        iv_item_img3 = (ImageView) itemView.findViewById(R.id.iv_item_img3);
        iv_item_img4 = (ImageView) itemView.findViewById(R.id.iv_item_img4);

    }
}
