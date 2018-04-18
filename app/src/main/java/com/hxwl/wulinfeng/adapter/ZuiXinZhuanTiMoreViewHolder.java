package com.hxwl.wulinfeng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;

import static com.hxwl.wulinfeng.R.id.iv_big_img;
import static com.hxwl.wulinfeng.R.id.iv_item_img;
import static com.hxwl.wulinfeng.R.id.iv_item_img2;
import static com.hxwl.wulinfeng.R.id.iv_video_flg;
import static com.hxwl.wulinfeng.R.id.iv_video_flg1;
import static com.hxwl.wulinfeng.R.id.iv_video_flg2;
import static com.hxwl.wulinfeng.R.id.ll_layout;
import static com.hxwl.wulinfeng.R.id.rl_layout;
import static com.hxwl.wulinfeng.R.id.rl_layout1;
import static com.hxwl.wulinfeng.R.id.rl_layout2;
import static com.hxwl.wulinfeng.R.id.rl_video_flg;
import static com.hxwl.wulinfeng.R.id.rl_video_flg1;
import static com.hxwl.wulinfeng.R.id.rl_video_flg2;
import static com.hxwl.wulinfeng.R.id.tv_title1;
import static com.hxwl.wulinfeng.R.id.tv_title2;
import static com.hxwl.wulinfeng.R.id.tv_title3;

/**
 * Created by Allen on 2017/5/26.
 * 最新专题R引用
 */

public class ZuiXinZhuanTiMoreViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_title_name;
    public TextView tv_click_more;
    public RecyclerView rv_video ;

    public ZuiXinZhuanTiMoreViewHolder(View itemView) {
        super(itemView);
        tv_title_name = (TextView) itemView.findViewById(R.id.tv_title_name);
        tv_click_more = (TextView) itemView.findViewById(R.id.tv_click_more);

        rv_video = (RecyclerView)itemView.findViewById(R.id.rv_video);

    }
}
