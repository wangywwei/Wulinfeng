package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.SaiChengType;

import java.util.List;


/**
 * Created by Allen on 2017/6/27.
 */
public class SaiShiTypeAdapter extends BaseAdapter{
    private Activity activity;
    private List<SaiChengType> listData;
    public SaiShiTypeAdapter(Activity activity, List<SaiChengType> listData) {
        this.activity = activity ;
        this.listData = listData ;
    }

    @Override
    public int getCount() {
        if(listData != null){
            return listData.size();
        }
        return 0;
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
       if(paramInt >= listData.size() ){return null ;}
        SaiChengType bean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_saishitype,
                    null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(bean.getName());
        holder.text.setTag(bean);

        if(bean.isSelect()){
            holder.text.setBackgroundResource(R.drawable.kuangred);
            holder.text.setTextColor(activity.getResources().getColor(R.color.title_red));
        }else{
            holder.text.setBackgroundResource(R.drawable.send_biankuang2);
            holder.text.setTextColor(activity.getResources().getColor(R.color.title_black));
        }
        return convertView;
    }

    class ViewHolder{
        public TextView text;
    }
}
