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
import com.hxwl.wulinfeng.activity.ZhuanTiDetailActivity;
import com.hxwl.wulinfeng.bean.WQShiPinBean;
import com.hxwl.wulinfeng.bean.ZhuanTiDetailBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;


/**
 * Created by Allen on 2017/6/28.
 */
public class ZhuanTiDatuAdapter extends DelegateAdapter.Adapter<ZhuanTiDatuAdapter.MyViewHolder>{
    private Activity activity ;
    private ZhuanTiDetailBean dataBean ;
    private LayoutHelper mLayoutHelper ;
    public ZhuanTiDatuAdapter(Activity activity, LinearLayoutHelper linearLayoutHelper) {
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
    public ZhuanTiDatuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity ,R.layout.zhuanti_head,null);
        ZhuanTiDatuAdapter.MyViewHolder viewHolder = new ZhuanTiDatuAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ZhuanTiDatuAdapter.MyViewHolder holder, final int position) {
        holder.rl_item.setTag(dataBean);
        holder.title.setText(dataBean.getTitle());
        ImageUtils.getPic(dataBean.getFengmiantu() !=null ? dataBean.getFengmiantu().trim() : "" , holder.iv_fengmiantu ,activity);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onImageClick(holder.rl_item, position);
                }
            }
        });
        String type = dataBean.getType_();
        if("video".equals(type)){
            holder.flag.setVisibility(View.VISIBLE);
            holder.flag.setText("视频");
        }else if("tuji".equals(type)){
            holder.flag.setVisibility(View.VISIBLE);
            holder.flag.setText("图集");
        }else{//news
            holder.flag.setVisibility(View.GONE);
        }
    }
    public ZhuanTiDatuAdapter.onItemClickListener onItemClickListener;
    public void setOnItemClickListener(ZhuanTiDatuAdapter.onItemClickListener onItemClickListener){
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

        public TextView flag;//类型
        public TextView title;//日期时间
        public ImageView iv_fengmiantu;//资讯图片
        public RelativeLayout rl_item;

        public MyViewHolder(View convertView) {
            super(convertView);
            flag = ((TextView) convertView
                    .findViewById(R.id.flag));

            title = ((TextView) convertView
                    .findViewById(R.id.title));

            iv_fengmiantu = ((ImageView) convertView
                    .findViewById(R.id.iv_fengmiantu));

            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
        }
    }
}
