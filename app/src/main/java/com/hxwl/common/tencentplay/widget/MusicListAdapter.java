package com.hxwl.common.tencentplay.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.hxwl.wulinfeng.R;

import java.util.List;

/**
 * 功能:
 * 作者：zjn on 2017/8/10 17:14
 */

public class MusicListAdapter extends BaseAdapter {
    private Context mContext;
    List<TCAudioControl.MediaEntity> mData = null;
    private LayoutInflater mInflater;
    MusicListAdapter(LayoutInflater inflater, List<TCAudioControl.MediaEntity> list){
        mInflater = inflater;
        mData = list;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MusicListView.ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.audio_ctrl_music_item,null);
            holder = new MusicListView.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.xml_music_item_name);
            holder.duration = (TextView) convertView.findViewById(R.id.xml_music_item_duration);
            holder.selected = (ImageView) convertView.findViewById(R.id.music_item_selected);
            convertView.setTag(holder);
        }
        else{
            holder = (MusicListView.ViewHolder)convertView.getTag();
        }
        holder.name.setText(mData.get(position).title);
        holder.duration.setText(mData.get(position).durationStr);
        holder.selected.setVisibility(mData.get(position).state == 1 ? View.VISIBLE : View.GONE);
        return convertView;
    }
}
