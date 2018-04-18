package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.ZhuanTiDetailBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/6/28.
 */
public class ZhuanTiMoreGridAdapter extends DelegateAdapter.Adapter<ZhuanTiMoreGridAdapter.MyViewHolder>{
    private Activity activity ;
    private LayoutInflater mInflater;
    private List<ZhuanTiDetailBean> listDatas = new ArrayList<>();
    private LayoutHelper mLayoutHelper;
    public ZhuanTiMoreGridAdapter(Activity activity, GridLayoutHelper gridLayoutHelper) {
        this.activity = activity ;
        this.mLayoutHelper = gridLayoutHelper ;
        if (mInflater == null) {
            mInflater = LayoutInflater.from(activity);
        }
    }

    public void setData(List<ZhuanTiDetailBean> listData){
        if(listDatas != null){
            listDatas.clear();
        }
        listDatas.addAll(listData) ;
        notifyDataSetChanged();
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public ZhuanTiMoreGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_zhuanti2,
                parent, false);
        ZhuanTiMoreGridAdapter.MyViewHolder viewHolder = new ZhuanTiMoreGridAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ZhuanTiMoreGridAdapter.MyViewHolder holder, final int position) {
        ZhuanTiDetailBean dataBean = listDatas.get(position);

    }

    @Override
    public int getItemCount() {
        return listDatas != null ?listDatas.size():0 ;
    }
    public static class MyViewHolder extends BaseRecyclerViewHolder {
        public RelativeLayout rl_video_flg;
        public TextView tv_title1;//日期时间
        public ImageView iv_video_flg;//资讯图片
        public ImageView iv_big_img;//资讯图片

        public MyViewHolder(View convertView) {
            super(convertView);
            rl_video_flg = ((RelativeLayout) convertView
                    .findViewById(R.id.rl_video_flg));

            tv_title1 = ((TextView) convertView
                    .findViewById(R.id.tv_title1));

            iv_video_flg = ((ImageView) convertView
                    .findViewById(R.id.iv_video_flg));

            iv_big_img = ((ImageView) convertView
                    .findViewById(R.id.iv_big_img));

        }
    }
    public ZhuanTiMoreGridAdapter.onItemClickListener onItemClickListener;
    public void setOnItemClickListener(ZhuanTiMoreGridAdapter.onItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public  interface onItemClickListener{
        void myOnItemClickListener(View view, int position);

    }
}
