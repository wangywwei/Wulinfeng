package com.hxwl.newwlf.schedule.cooperation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.ShendaiBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/23.
 */

public class CooperationAdapter extends BaseAdapter {
    private ArrayList<ShendaiBean.DataBean> list;
    private Context context;

    public CooperationAdapter(ArrayList<ShendaiBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder v;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cooperation, null);
            v=new ViewHolder(convertView);
            convertView.setTag(v);
        }else {
            v= (ViewHolder) convertView.getTag();
        }
        if (list!=null&&list.size()>0){
            GlidUtils.setGrid2(context, URLS.IMG+list.get(position).getLogoImage(),v.cooperation_img);
            v.cooperation_name.setText(list.get(position).getAgentName());
        }

        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public ImageView cooperation_img;
        public TextView cooperation_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.cooperation_img = (ImageView) rootView.findViewById(R.id.cooperation_img);
            this.cooperation_name = (TextView) rootView.findViewById(R.id.cooperation_name);
        }

    }
}
