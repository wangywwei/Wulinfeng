package com.hxwl.wulinfeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.labelview.LabelView;
import com.hxwl.common.utils.DensityUtil;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.ScreenUtils;

/**
 * 功能:头条推荐对阵资源引用
 */

public class ZuiXinTuijianDuiZhenViewHolder extends RecyclerView.ViewHolder {

    public ImageView player_icon_left;
    public ImageView player_icon_right;
    public ImageView country_left;
    public ImageView country_right;
    public TextView changci;
    public TextView guessing;
    public TextView guessing_num;
    public LinearLayout title;
    public TextView tv_data;
    public TextView tv_time;
    public TextView playerA_name;
    public TextView playerB_name;
    public ImageView iv_vs;
    public LinearLayout llyt_toutiao_duizhen;
    public TextView tv_toutiao_duizhen_title;
    public LabelView lableView;
    public ImageView iv_sheng_right;
    public ImageView iv_sheng_left;

    public ZuiXinTuijianDuiZhenViewHolder(View convertView) {
        super(convertView);
    }
    public ZuiXinTuijianDuiZhenViewHolder(View convertView, Context ctx) {
        super(convertView);
        player_icon_left = (ImageView) convertView.findViewById(R.id.iv_player_icon_left);
        player_icon_right = (ImageView) convertView.findViewById(R.id.iv_player_icon_right);
        changci = (TextView) convertView.findViewById(R.id.changci);
        playerA_name = (TextView) convertView.findViewById(R.id.tv_playerA_name);
        playerB_name = (TextView) convertView.findViewById(R.id.tv_playerB_name);
        iv_vs = (ImageView) convertView.findViewById(R.id.iv_vs);
        guessing = (TextView) convertView.findViewById(R.id.guessing);
        guessing_num = (TextView) convertView.findViewById(R.id.tv_guessing_num);
        country_left = (ImageView) convertView.findViewById(R.id.iv_country_left);
        country_right = (ImageView) convertView.findViewById(R.id.iv_country_right);
        llyt_toutiao_duizhen = (LinearLayout) convertView.findViewById(R.id.llyt_toutiao_duizhen);
        tv_toutiao_duizhen_title = (TextView) convertView.findViewById(R.id.tv_toutiao_duizhen_title);

        iv_sheng_right = (ImageView) convertView.findViewById(R.id.iv_sheng_right);
        iv_sheng_left = (ImageView) convertView.findViewById(R.id.iv_sheng_left);

        lableView = (LabelView) convertView.findViewById(R.id.lableView);
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) llyt_toutiao_duizhen
                .getLayoutParams();
        int width = ScreenUtils.getScreenWidth(ctx) - DensityUtil.dip2px(ctx,20);
        linearParams.width = width - DensityUtil.dip2px(ctx,20);
        linearParams.height = DensityUtil.dip2px(ctx,160);
        llyt_toutiao_duizhen.setLayoutParams(linearParams);
    }
}
