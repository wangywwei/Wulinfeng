package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxwl.newwlf.modlebean.QuanshouWeiBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/18.
 */

public class FollowFuAdapter extends BaseAdapter {
    private ArrayList<QuanshouWeiBean.DataBean> list;
    private Context context;

    public FollowFuAdapter(ArrayList<QuanshouWeiBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private int selectedPosition = 0;// 选中的位置

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.follow_fu_item, null);
            v = new ViewHolder(convertView);
            convertView.setTag(v);

        }else {
            v= (ViewHolder) convertView.getTag();
        }
            v.follow_list_name.setText(list.get(position).getName());

        if (selectedPosition == position) {
            v.follow_list_name.setSelected(true);
            v.follow_list_name.setPressed(true);
            v.follow_list_name.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            v.follow_list_name.setSelected(false);
            v.follow_list_name.setPressed(false);
            v.follow_list_name.setBackgroundColor(Color.TRANSPARENT);

        }

        return convertView;
    }


    class ViewHolder {
        public View rootView;
        public TextView follow_list_name;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.follow_list_name = (TextView) rootView.findViewById(R.id.follow_list_name);
        }

    }
}
