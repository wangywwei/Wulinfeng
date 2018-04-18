package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.ClubListBean;
import com.hxwl.wulinfeng.bean.PlayListBean;

import java.util.List;


/**
 * Created by Administrator on 2016/9/27.
 */
public class ClubRecordAdapter extends BaseAdapter{
    private final Activity context;
    private ViewHolder vh1;
    private List<ClubListBean> playListBean;

    public ClubRecordAdapter(Activity activity, List<ClubListBean> playListBean) {
        this.context=activity;
        this.playListBean = playListBean ;
    }

    @Override
    public int getCount() {
        return playListBean.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            vh1 = new ViewHolder();
            convertView = View.inflate(context, R.layout.cansai_record_club_item, null);
            vh1.tv_bisai = (TextView) convertView.findViewById(R.id.tv_bisai);
            vh1.tv_shijian = (TextView) convertView.findViewById(R.id.tv_shijian);
            vh1.tv_duishou = (TextView) convertView.findViewById(R.id.tv_duishou);
            vh1.tv_shengfu = (TextView) convertView.findViewById(R.id.tv_shengfu);
            vh1.tv_qixiaxuanshou = (TextView) convertView.findViewById(R.id.tv_qixiaxuanshou);
            convertView.setTag(vh1);
        } else{
            vh1 = (ViewHolder) convertView.getTag();
        }
        vh1.tv_bisai.setText(playListBean.get(i).getSaishi_name());
        vh1.tv_shijian.setText(playListBean.get(i).getStart_time_format());
        vh1.tv_duishou.setText(playListBean.get(i).getDuishou_name());
        vh1.tv_shengfu.setText(playListBean.get(i).getSaiguo());
        vh1.tv_qixiaxuanshou.setText(playListBean.get(i).getQixia_name());
        return convertView;
    }
    private static class ViewHolder {

        public TextView tv_bisai;
        public TextView tv_shijian;
        public TextView tv_duishou;
        public TextView tv_shengfu;
        public TextView tv_qixiaxuanshou;
    }
}

