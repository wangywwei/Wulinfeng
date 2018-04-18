package com.hxwl.common.tencentplay.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Link on 2016/9/12.
 */
public class MusicListView extends ListView {
    private Context mContext;
    List<TCAudioControl.MediaEntity> mData = null;
    public void setData(List<TCAudioControl.MediaEntity> data){
        mData = data;
    }
    private BaseAdapter adapter;
    public BaseAdapter getAdapter(){
        return adapter;
    }
    public MusicListView(Context context){
        super(context);
        init(context);
    }
    public MusicListView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }
    private void init(Context context){
        mContext = context;
        this.setChoiceMode(CHOICE_MODE_SINGLE);

    }
    public void setupList(LayoutInflater inflater, List<TCAudioControl.MediaEntity> data){
        mData = data;
//        SimpleAdapter adapter = new SimpleAdapter(mContext,getData(),R.layout.audio_ctrl_music_item,
//                new String[]{"name","duration"},
//                new int[]{R.id.xml_music_item_name,R.id.xml_music_item_duration});
        adapter = new MusicListAdapter(inflater, data);
        setAdapter(adapter);
    }
    @Override
    public int getCount() {
        return mData.size();
    }
    static public class ViewHolder{
        ImageView selected;
        TextView name;
        TextView duration;
    }

}