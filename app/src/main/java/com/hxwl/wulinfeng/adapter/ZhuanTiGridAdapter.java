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
import com.hxwl.wulinfeng.activity.ZhuanTiDetailActivity;
import com.hxwl.wulinfeng.bean.WQShiPinBean;
import com.hxwl.wulinfeng.bean.ZhuanTiDetailBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Allen on 2017/6/28.
 */
public class ZhuanTiGridAdapter extends DelegateAdapter.Adapter<ZhuanTiGridAdapter.MyViewHolder>{
    private Activity activity ;
    private LayoutInflater mInflater;
    private List<ZhuanTiDetailBean> listDatas = new ArrayList<>();
    private LayoutHelper mLayoutHelper;
    public ZhuanTiGridAdapter(Activity activity, GridLayoutHelper gridLayoutHelper) {
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
    public ZhuanTiGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_zhuanti,
                parent, false);
        ZhuanTiGridAdapter.MyViewHolder viewHolder = new ZhuanTiGridAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ZhuanTiGridAdapter.MyViewHolder holder,final int position) {
        ZhuanTiDetailBean dataBean = listDatas.get(position);
        holder.title.setText(dataBean.getTitle());
        ImageUtils.getPic(dataBean.getFengmiantu() !=null ? dataBean.getFengmiantu().trim() : "" , holder.iv_fengmiantu ,activity);
        holder.rl_item.setTag(dataBean);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.myOnItemClickListener(holder.rl_item,position);
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

    @Override
    public int getItemCount() {
        return listDatas != null ?listDatas.size():0 ;
    }
    public static class MyViewHolder extends BaseRecyclerViewHolder {

        public TextView title;//日期时间
        public TextView flag;//日期时间
        public ImageView iv_fengmiantu;//资讯图片
        public RelativeLayout rl_item;

        public MyViewHolder(View convertView) {
            super(convertView);

            title = ((TextView) convertView
                    .findViewById(R.id.title));

            flag = ((TextView) convertView
                    .findViewById(R.id.flag));


            iv_fengmiantu = ((ImageView) convertView
                    .findViewById(R.id.iv_fengmiantu));

            rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
        }
    }
    public ZhuanTiGridAdapter.onItemClickListener onItemClickListener;
    public void setOnItemClickListener(ZhuanTiGridAdapter.onItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public  interface onItemClickListener{
        void myOnItemClickListener(View view, int position);

    }
}
