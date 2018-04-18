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
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.WQShiPinBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/6/26.
 */
public class SearchWangQiSPGridAdapter extends DelegateAdapter.Adapter<SearchWangQiSPGridAdapter.MyViewHolder>{
    private Activity activity ;
    private LayoutInflater mInflater;
    private List<WQShiPinBean> listDatas = new ArrayList<>();
    private LayoutHelper mLayoutHelper;
    public SearchWangQiSPGridAdapter(Activity activity, LayoutHelper mLayoutHelper) {
        this.activity = activity ;
        this.mLayoutHelper = mLayoutHelper ;
        if (mInflater == null) {
            mInflater = LayoutInflater.from(activity);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_wangqishipin,
                parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        WQShiPinBean dataBean = listDatas.get(position);
        holder.title.setText(dataBean.getTitle());
        ImageUtils.getPic(dataBean.getImg() !=null ? dataBean.getImg().trim() : "" , holder.iv_fengmiantu ,activity);
        holder.time.setText(dataBean.getZhibo_time() == null ? "" : TimeUtiles.getTimeed_(dataBean.getZhibo_time()));
        holder.rl_item.setTag(dataBean);
        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.myOnItemClickListener(holder.rl_item,position);
                }
            }
        });
    }

    public void setData(List<WQShiPinBean> listData){
        if(listDatas != null){
            listDatas.clear();
        }
        listDatas.addAll(listData) ;
        notifyDataSetChanged();
    }

    public void upData(List<WQShiPinBean> listData){
        if(listDatas != null){
            listDatas.addAll(listData) ;
        }else{
            listDatas = listData ;
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listDatas != null ?listDatas.size():0 ;
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
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
    public onItemClickListener onItemClickListener;
    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public  interface onItemClickListener{
        void myOnItemClickListener(View view, int position);

    }
}
