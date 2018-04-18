package com.hxwl.wulinfeng.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hxwl.newwlf.modlebean.QuanshouWeiBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.LevelBean;

import java.util.List;


public class PopAdapter extends BaseAdapter {


    private final List<QuanshouWeiBean.DataBean> mList;
    private Context context;
    private int checkItemPosition = 0;

    public PopAdapter(List<QuanshouWeiBean.DataBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }
    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        Log.d("100","////"+mList.size());
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.pop_item,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position).getName());
        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.textView.setTextColor(context.getResources().getColor(R.color.shouye_tab));
                viewHolder.textView.setBackgroundColor(context.getResources().getColor(R.color.list_back));
//                viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.more_right), null);
            } else {
                viewHolder.textView.setTextColor(context.getResources().getColor(R.color.textColor_assist_666666));
                viewHolder.textView.setBackgroundColor(context.getResources().getColor(R.color.white));
//                viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
        viewHolder.textView.setTag(mList.get(position));
        return convertView;
    }

    public class ViewHolder{
        public TextView textView;
    }
}
