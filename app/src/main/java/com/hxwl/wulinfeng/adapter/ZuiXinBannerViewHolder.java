package com.hxwl.wulinfeng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hxwl.wulinfeng.R;
import com.youth.banner.Banner;

/**
 * Created by Allen on 2017/5/26.
 * 最新BannerR引用
 */

public class ZuiXinBannerViewHolder extends RecyclerView.ViewHolder {
    public Banner banner;
    public ZuiXinBannerViewHolder(View itemView) {
        super(itemView);
        banner = (Banner) itemView.findViewById(R.id.banner);
    }
}
