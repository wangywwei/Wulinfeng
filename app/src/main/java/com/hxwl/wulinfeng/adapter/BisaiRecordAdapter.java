package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxwl.newwlf.modlebean.QuanshouduizhengBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.PlayDetailsActivity;
import com.hxwl.wulinfeng.bean.PlayListBean;
import com.hxwl.wulinfeng.util.DateUtils;

import java.util.List;


/**
 * Created by Administrator on 2016/9/27.
 */
public class BisaiRecordAdapter extends BaseAdapter{
    private final Activity context;
    private ViewHolder vh1;
    private List<QuanshouduizhengBean.DataBean> playListBean;

    public BisaiRecordAdapter(Activity activity, List<QuanshouduizhengBean.DataBean> playListBean) {
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
            convertView = View.inflate(context, R.layout.cansai_record_item, null);
            vh1.tv_bisai = (TextView) convertView.findViewById(R.id.tv_bisai);
            vh1.tv_shijian = (TextView) convertView.findViewById(R.id.tv_shijian);
            vh1.tv_duishou = (TextView) convertView.findViewById(R.id.tv_duishou);
            vh1.tv_shengfu = (TextView) convertView.findViewById(R.id.tv_shengfu);
            convertView.setTag(vh1);
        } else{
            vh1 = (ViewHolder) convertView.getTag();
        }
        vh1.tv_bisai.setText(playListBean.get(i).getScheduleName());
        vh1.tv_shijian.setText(DateUtils.timeslashData2(playListBean.get(i).getBeginTime()));
        vh1.tv_duishou.setText(playListBean.get(i).getAgainstPlayer());
        vh1.tv_shengfu.setText(playListBean.get(i).getAgainstResult());
        return convertView;
    }
    private static class ViewHolder {
        public TextView tv_bisai;
        public TextView tv_shijian;
        public TextView tv_duishou;
        public TextView tv_shengfu;
    }
}

