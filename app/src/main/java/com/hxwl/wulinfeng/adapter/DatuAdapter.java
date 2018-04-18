package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.BisairiliVideoBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.WangQiShipinActivity;
import com.hxwl.wulinfeng.bean.WQShiPinBean;
import com.hxwl.wulinfeng.bean.WangQiSPBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

/**
 * Created by Allen on 2017/6/26.
 */
public class DatuAdapter extends DelegateAdapter.Adapter<DatuAdapter.MyViewHolder>{
        private Activity activity ;
        private BisairiliVideoBean.DataBean dataBean ;
        private LayoutHelper mLayoutHelper ;
    public DatuAdapter(Activity activity,LayoutHelper mLayoutHelper ) {
        this.activity = activity ;
        this.mLayoutHelper = mLayoutHelper ;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    public void setData(BisairiliVideoBean.DataBean info){
        if(info == null){
            dataBean = null ;
            notifyDataSetChanged();
            return ;
        }
        dataBean = null ;
        dataBean = info ;
        notifyDataSetChanged();
    }

    public void upData(BisairiliVideoBean.DataBean info){
        dataBean = null ;
        dataBean = info ;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity ,R.layout.wangqisp_head,null);
        DatuAdapter.MyViewHolder viewHolder = new DatuAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.rl_item.setTag(dataBean);
        holder.title.setText(dataBean.getScheduleName());
        ImageUtils.getPic(dataBean.getPublicityImg() !=null ? URLS.IMG+dataBean.getPublicityImg().trim() : "" , holder.iv_fengmiantu ,activity);
//        holder.time.setText(dataBean.getZhibo_time() == null ? "" : TimeUtiles.getTimeed_(dataBean.getZhibo_time()));
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onImageClick(holder.rl_item, position);
                }
            }
        });
    }
    public DatuAdapter.onItemClickListener onItemClickListener;
    public void setOnItemClickListener(DatuAdapter.onItemClickListener onItemClickListener){
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

        public TextView title;//日期时间
        public TextView time;//日期时间
        public ImageView iv_fengmiantu;//资讯图片
        public RelativeLayout rl_item;

        public MyViewHolder(View convertView) {
            super(convertView);
            time = ((TextView) convertView
                    .findViewById(R.id.time));

            title = ((TextView) convertView
                    .findViewById(R.id.title));

            iv_fengmiantu = ((ImageView) convertView
                    .findViewById(R.id.iv_fengmiantu));

            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
        }
    }
}
