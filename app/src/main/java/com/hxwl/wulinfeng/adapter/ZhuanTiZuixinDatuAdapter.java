package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.ZhuanTiDetailBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import static com.hxwl.wulinfeng.R.id.iv_big_img;
import static com.hxwl.wulinfeng.R.id.iv_video_flg;
import static com.hxwl.wulinfeng.R.id.rl_item;
import static com.hxwl.wulinfeng.R.id.rl_video_flg;
import static com.hxwl.wulinfeng.R.id.tv_title1;

/**
 * Created by Allen on 2017/6/28.
 */
public class ZhuanTiZuixinDatuAdapter extends DelegateAdapter.Adapter<ZhuanTiZuixinDatuAdapter.MyViewHolder>{
    private Activity activity ;
    private ZhuanTiDetailBean dataBean ;
    private LayoutHelper mLayoutHelper ;
    public ZhuanTiZuixinDatuAdapter(Activity activity, LinearLayoutHelper linearLayoutHelper) {
        this.activity = activity ;
        this.mLayoutHelper = linearLayoutHelper ;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }
    public void setData(ZhuanTiDetailBean info){
        if(info == null){
            dataBean = null ;
            notifyDataSetChanged();
            return ;
        }
        dataBean = null ;
        dataBean = info ;
        notifyDataSetChanged();
    }

    @Override
    public ZhuanTiZuixinDatuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity ,R.layout.item_zhuanti2,null);
        ZhuanTiZuixinDatuAdapter.MyViewHolder viewHolder = new ZhuanTiZuixinDatuAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ZhuanTiZuixinDatuAdapter.MyViewHolder holder, final int position) {

    }
    public ZhuanTiZuixinDatuAdapter.onItemClickListener onItemClickListener;
    public void setOnItemClickListener(ZhuanTiZuixinDatuAdapter.onItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public  interface onItemClickListener{
        void onImageClick(View view, int position);

    }

    @Override
    public int getItemCount() {
        return dataBean == null ? 0 : 1;
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
}
