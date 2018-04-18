package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.ClubBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.ImageUtils;

import java.util.List;

/**
 * Created by Allen on 2017/6/14.
 */
public class WuZhanAdapter extends BaseAdapter {
    private Activity activity;
    private List<ClubBean.DataBean> listData;

    public WuZhanAdapter(Activity activity, List<ClubBean.DataBean> listData) {
        this.activity = activity;
        this.listData = listData;
    }


    @Override
    public int getCount() {
        return listData != null ? listData.size() : 0;
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
    public View getView(int paramInt, View convertView, ViewGroup viewGroup) {
        ClubBean.DataBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_wuzhanlm,
                    null);
            holder = new ViewHolder();
            holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageUtils.getPic(URLS.IMG + dataBean.getClubLogo(), holder.iv_image, activity);
        holder.tv_title.setText(dataBean.getClubName());
        holder.tv_title.setTag(dataBean);
        return convertView;
    }

    class ViewHolder {
        public ImageView iv_image;
        public TextView tv_title;
    }
}
