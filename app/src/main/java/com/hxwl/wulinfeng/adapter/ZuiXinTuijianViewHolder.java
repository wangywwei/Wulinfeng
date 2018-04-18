package com.hxwl.wulinfeng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;

/**
 * Created by Allen on 2017/5/26.
 * 最新推荐R引用
 */

public class ZuiXinTuijianViewHolder extends RecyclerView.ViewHolder {
    public RecyclerView recyclerView;
    public TextView tv_num ;
    public RelativeLayout rl_goon ;
    public ZuiXinTuijianViewHolder(View itemView) {
        super(itemView);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.rlv_toutiao_tuijian);
        tv_num = (TextView) itemView.findViewById(R.id.tv_num);
        rl_goon = (RelativeLayout) itemView.findViewById(R.id.rl_goon);
    }
}
