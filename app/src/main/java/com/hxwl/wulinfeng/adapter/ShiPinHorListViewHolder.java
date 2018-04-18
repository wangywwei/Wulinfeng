package com.hxwl.wulinfeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.common.utils.DensityUtil;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.PreciseCompute;
import com.hxwl.wulinfeng.util.ScreenUtils;

import static com.hxwl.wulinfeng.R.id.iv_start;

/**
 * 功能:头条推荐直播资源引用
 */

public class ShiPinHorListViewHolder extends RecyclerView.ViewHolder {

    public ImageView iv_hor_pic;
    public ImageView iv_start;
    public TextView tv_title;
    public TextView tv_time;
    public LinearLayout ll_layout;

    public ShiPinHorListViewHolder(View itemView, Context ctx) {
        super(itemView);
        iv_hor_pic = (ImageView) itemView.findViewById(R.id.iv_hor_pic);
        iv_start = (ImageView) itemView.findViewById(R.id.iv_start);
        tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        ll_layout = (LinearLayout) itemView.findViewById(R.id.ll_layout);

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ll_layout
                .getLayoutParams();
//        int width = ScreenUtils.getScreenWidth(ctx);
        int width =(int) PreciseCompute.div(ScreenUtils.getScreenWidth(ctx),3);
        linearParams.width = width - DensityUtil.dip2px(ctx,0);
//        linearParams.width = width/3;
        linearParams.height = DensityUtil.dip2px(ctx,142);
        ll_layout.setLayoutParams(linearParams);

    }
}
