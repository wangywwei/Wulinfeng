package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.SearchduizhenBean;
import com.hxwl.wulinfeng.R;

import java.util.List;

/**
 * Created by Allen on 2017/6/2.
 */
public class ShiPinSearchAdapter extends BaseAdapter {
    private List<SearchduizhenBean.DataBean> listData;
    private Activity activity;

    public ShiPinSearchAdapter(Activity activity, List<SearchduizhenBean.DataBean> listData) {
        this.activity = activity;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int paramInt, View view, ViewGroup viewGroup) {
        if (paramInt >= listData.size()) {
            return null;
        }
        final SearchduizhenBean.DataBean dataBean = listData.get(paramInt);
        final ViewHolder holder;
        if (view == null) {
            view = View.inflate(activity, R.layout.item_shipinsearch_video,
                    null);
            holder = new ViewHolder(view);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        try {
            //视频图片背景
            GlidUtils.setGrid(activity,URLS.IMG + dataBean.getPublicityImage(),holder.iv_video_bg);

            holder.tv_userName2.setText(dataBean.getRemark());
            holder.tv_userName.setText(dataBean.getAgainstName());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_userName2;
        public ImageView iv_video_bg;
        public ImageView iv_start;
        public TextView tv_userName;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_userName2 = (TextView) rootView.findViewById(R.id.tv_userName2);
            this.iv_video_bg = (ImageView) rootView.findViewById(R.id.iv_video_bg);
            this.iv_start = (ImageView) rootView.findViewById(R.id.iv_start);
            this.tv_userName = (TextView) rootView.findViewById(R.id.tv_userName);
        }

    }
}
