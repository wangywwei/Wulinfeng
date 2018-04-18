package com.hxwl.wulinfeng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Allen on 2017/5/27.
 * 推荐页 是横向的 recycleview控件
 */

public abstract class BaseZuiXinTuiJianAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_ZHIBO = 0;//推荐直播
    //推荐对阵
    public static final int TYPE_DUIZHEN = 1;

    //创建对阵的viewHolder
    public abstract ZuiXinTuijianDuiZhenViewHolder onCreateDuiZhenViewHolder(ViewGroup parent, int position);

    //当前类型的item数目
    public abstract int getDuiZhenViewCount();

    //赋值方法
    public abstract void onBindDuiZhenViewHolder(ZuiXinTuijianDuiZhenViewHolder holder, int position);


    //创建直播的viewHolder
    public abstract ZuiXinTuijianZhiBoViewHolder onCreateZhiBoViewHolder(ViewGroup parent, int position);

    //当前类型的item数目
    public abstract int getZhiBoViewCount();

    //赋值方法
    public abstract void onBindZhiBoViewHolder(ZuiXinTuijianZhiBoViewHolder holder, int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return onCreateZhiBoViewHolder(parent, viewType);
            case 1:
                return onCreateDuiZhenViewHolder(parent, viewType);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int zhiBoViewCount = getZhiBoViewCount();
        int itemType = getItemViewType(position);
        onBindZhiBoViewHolder((ZuiXinTuijianZhiBoViewHolder) holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        int zhiBoViewCount = getZhiBoViewCount();
        if (position < zhiBoViewCount) {
            return TYPE_ZHIBO;
        }
        return TYPE_DUIZHEN;
    }

    @Override
    public int getItemCount() {
        return getZhiBoViewCount() + getDuiZhenViewCount();
    }

    //第三部分和第二部分的接口回调函数
    public interface TuiJianCallBack {
        void getDetalisData(int viewType, int position);
    }

    public TuiJianCallBack detalisCallBack;

    public void setDetalisCallBack(TuiJianCallBack detalisCallBack) {
        this.detalisCallBack = detalisCallBack;
    }
}
