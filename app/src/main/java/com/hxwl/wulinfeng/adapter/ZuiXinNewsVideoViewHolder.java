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
 * 最新混合布局的视频R引用
 */

public class ZuiXinNewsVideoViewHolder extends RecyclerView.ViewHolder {
    public ImageView view_line;
    public ImageView iv_video_bg;
    public TextView tv_title;
    public ImageView user_icon;
    public TextView tv_time;
    public TextView tv_label;

    public TextView tv_pinglun;
    public TextView tv_dianzan;
    public LinearLayout ll_item;
    //空白区域
    public LinearLayout llyt_empty_area;
    //开始按钮
    public ImageView iv_start;
    //播放背景图片容器
    public RelativeLayout rlyt_video_bg;
    //播放容器
    public RelativeLayout videoContainer;
    //标题
    public RelativeLayout llyt_title;
    public View ic_net_warn;
    public Button btn1;

    public ZuiXinNewsVideoViewHolder(View view) {
        super(view);
        view_line = (ImageView) view.findViewById(R.id.view_line);
        iv_video_bg = (ImageView) view.findViewById(R.id.iv_video_bg);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_label = (TextView) view.findViewById(R.id.tv_label);
        user_icon = (ImageView) view.findViewById(R.id.user_icon);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
        tv_pinglun = (TextView) view.findViewById(R.id.tv_pinglun);
        tv_dianzan = (TextView) view.findViewById(R.id.tv_dianzan);
        ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
        llyt_empty_area = (LinearLayout) view.findViewById(R.id.llyt_empty_area);
        llyt_title = (RelativeLayout) view.findViewById(R.id.llyt_title);
        iv_start = (ImageView) view.findViewById(R.id.iv_start);
        rlyt_video_bg = (RelativeLayout) view.findViewById(R.id.rlyt_video_bg);
        videoContainer = (RelativeLayout) view.findViewById(R.id.videoContainer);
        ic_net_warn =  view.findViewById(R.id.ic_net_warn);
        btn1 = (Button) ic_net_warn.findViewById(R.id.btn1);
    }
}
