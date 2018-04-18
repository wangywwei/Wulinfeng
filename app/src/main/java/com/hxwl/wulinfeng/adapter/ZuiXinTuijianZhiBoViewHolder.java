package com.hxwl.wulinfeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.common.utils.DensityUtil;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.PreciseCompute;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.nostra13.universalimageloader.utils.L;

/**
 * 功能:头条推荐直播资源引用
 */

public class ZuiXinTuijianZhiBoViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_zhibo_thumbnail;
    public ImageView iv_zhibo_state;
    public RelativeLayout llyt_toutiao_zhibo ,rl_width,rl_img;
    public RelativeLayout rl_click;
//    public RelativeLayout rl_layout_click;

    public TextView tv_time ;
    public TextView tv_title ;
    public TextView tv_title2 ;
    public TextView flag ;
    public ImageView flag2 ;
    public TextView iv_button ;
    public ImageView iv_shoupiao ;
    public ImageView iv_img ;
    public TextView flag_back;
    public View view_line;

    public ZuiXinTuijianZhiBoViewHolder(View itemView, Context ctx) {
        super(itemView);
        iv_zhibo_thumbnail = (ImageView) itemView.findViewById(R.id.iv_zhibo_thumbnail);
        llyt_toutiao_zhibo = (RelativeLayout) itemView.findViewById(R.id.llyt_toutiao_zhibo);
        rl_click = (RelativeLayout) itemView.findViewById(R.id.rl_click);
//        rl_layout_click = (RelativeLayout) itemView.findViewById(R.id.rl_layout_click);
        iv_zhibo_state = (ImageView) itemView.findViewById(R.id.iv_zhibo_state);

        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        tv_title2 = (TextView) itemView.findViewById(R.id.tv_title2);
        flag = (TextView) itemView.findViewById(R.id.flag);
        flag2 = (ImageView) itemView.findViewById(R.id.flag2);
        iv_button = (TextView) itemView.findViewById(R.id.iv_button);
        iv_shoupiao = (ImageView) itemView.findViewById(R.id.iv_shoupiao);
        iv_img = (ImageView) itemView.findViewById(R.id.iv_img);

        flag_back = (TextView) itemView.findViewById(R.id.flag_back);
        rl_width = (RelativeLayout) itemView.findViewById(R.id.rl_width);
        rl_img = (RelativeLayout) itemView.findViewById(R.id.rl_img);
        view_line = itemView.findViewById(R.id.view_line);

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) rl_width
                .getLayoutParams();
        int width = ScreenUtils.getScreenWidth(ctx);
        linearParams.width = width - DensityUtil.dip2px(ctx,30);
        linearParams.height = (int)ctx.getResources().getDimension(R.dimen.px_870) ;
        rl_width.setLayoutParams(linearParams);

    }
}
