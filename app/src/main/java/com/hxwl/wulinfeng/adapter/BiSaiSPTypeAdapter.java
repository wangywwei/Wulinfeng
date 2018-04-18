package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.BiSaiSPTypeBean;
import com.hxwl.wulinfeng.bean.WQShiPinBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

/**
 * Created by Allen on 2017/7/3.
 */
public class BiSaiSPTypeAdapter extends BaseAdapter{
    private Activity activity;
    private List<BiSaiSPTypeBean> listData;
    public BiSaiSPTypeAdapter(Activity activity, List<BiSaiSPTypeBean> listData) {
        this.activity = activity ;
        this.listData = listData ;
    }

    @Override
    public int getCount() {
        return listData != null ? listData.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position >= listData.size()){
            return null;
        }
        BiSaiSPTypeBean dataBean = listData.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_video_huaxu,null);
            holder = new ViewHolder();
            holder.tv_title = ((TextView) convertView
                    .findViewById(R.id.tv_title));

            holder.tv_time = ((TextView) convertView
                    .findViewById(R.id.tv_time));

            holder.tv_count = ((TextView) convertView
                    .findViewById(R.id.tv_count));

            holder.tv_seecount = ((TextView) convertView
                    .findViewById(R.id.tv_seecount));

            holder.iv_img = ((ImageView) convertView
                    .findViewById(R.id.iv_img));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setTag(dataBean);
        holder.tv_title.setText(dataBean.getTitle());
        ImageUtils.getPic(dataBean.getImg() !=null ? dataBean.getImg().trim() : "" , holder.iv_img ,activity);

        holder.tv_time.setText(dataBean.getTime() == null ? "" : TimeUtiles.getTimeeForTuiJ(dataBean.getTime()));
        holder.tv_seecount.setText(dataBean.getOpen_times()+"人看过");
        return convertView;
    }
    class ViewHolder{
        public TextView tv_title;
        public TextView tv_time;//姓名
        public TextView tv_count;//日期时间
        public TextView tv_seecount;//日期时间
        public ImageView iv_img;//资讯图片
    }
}
