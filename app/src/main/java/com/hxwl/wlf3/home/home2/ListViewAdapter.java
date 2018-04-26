package com.hxwl.wlf3.home.home2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.linearfenlei.HuoDongLayout;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/26.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Home3Bean.DataBean.SchedulesBean.ActivityListBean> array;

    public ListViewAdapter(Context context, ArrayList<Home3Bean.DataBean.SchedulesBean.ActivityListBean> array ) {
        this.context = context;
        this.array = array;

    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = View.inflate(context, R.layout.syhuodong_adapter_item, null);
            viewHolder.huodong_content = (TextView) convertView.findViewById(R.id.huodong_content);
            viewHolder.huodong_layout = (LinearLayout) convertView.findViewById(R.id.huodong_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            // TODO: 2017/10/20 添加请求出来的数据
        }

        String name = array.get(position).getName();


        viewHolder.huodong_content.setText(name);
        return convertView;
    }

    private static class ViewHolder {
        TextView huodong_content;
        LinearLayout huodong_layout;
    }
}
