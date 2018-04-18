package com.hxwl.newwlf.home.home.intent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/22.
 */

public class GodsListAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private Context context;

    public GodsListAdapter(ArrayList<String> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.godslist_item, null);
            v=new ViewHolder(convertView);
            convertView.setTag(v);
        }else {
            v= (ViewHolder) convertView.getTag();
        }



        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView godslist_name1;
        public TextView godslist_huifu;
        public TextView godslist_name2;
        public TextView godslist_content;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.godslist_name1 = (TextView) rootView.findViewById(R.id.godslist_name1);
            this.godslist_huifu = (TextView) rootView.findViewById(R.id.godslist_huifu);
            this.godslist_name2 = (TextView) rootView.findViewById(R.id.godslist_name2);
            this.godslist_content = (TextView) rootView.findViewById(R.id.godslist_content);
        }

    }
}
