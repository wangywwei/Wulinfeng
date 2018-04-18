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
 * 回放视频R引用
 */

public class ZuiXinVedioViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_coutent;
    public TextView tv_time;
    public TextView tv_pinglun;
    public TextView tv_dianzan;
    public ImageView pic_1;
    public LinearLayout llyt_toutiao_vedio;
    public ImageView iv_start;
    public RelativeLayout videoContainer;
    public RelativeLayout ll_picbg;
    public RelativeLayout rl_hint2;
    public RelativeLayout rl_goon;
    public ImageView user_icon;
    public  View ic_net_warn;
    public Button btn1;
    public  TextView tv_hint;
    public  TextView tv_hint2;
    public  TextView label2;
    public  ImageView iv_view;

    public ZuiXinVedioViewHolder(View view) {
        super(view);
        tv_coutent = (TextView) itemView.findViewById(R.id.tv_coutent);
        tv_hint = (TextView) itemView.findViewById(R.id.tv_hint);
        tv_hint2 = (TextView) itemView.findViewById(R.id.tv_hint2);

        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        pic_1 = (ImageView) itemView.findViewById(R.id.pic_1);
        tv_pinglun = (TextView) itemView.findViewById(R.id.tv_pinglun);
        tv_dianzan = (TextView) itemView.findViewById(R.id.tv_dianzan);
        label2 = (TextView) itemView.findViewById(R.id.label2);
        llyt_toutiao_vedio = (LinearLayout)itemView.findViewById(R.id.llyt_toutiao_vedio);
        iv_start = (ImageView)itemView.findViewById(R.id.iv_start);
        videoContainer = (RelativeLayout)itemView.findViewById(R.id.videoContainer);
        ll_picbg = (RelativeLayout)itemView.findViewById(R.id.ll_picbg);
        rl_hint2 = (RelativeLayout)itemView.findViewById(R.id.rl_hint2);
        rl_goon = (RelativeLayout)itemView.findViewById(R.id.rl_goon);

        user_icon = (ImageView) itemView.findViewById(R.id.user_icon);
        ic_net_warn = (View) itemView.findViewById(R.id.ic_net_warn);
        btn1 = (Button) ic_net_warn.findViewById(R.id.btn1);
        iv_view = (ImageView) itemView.findViewById(R.id.iv_view);
    }
}
