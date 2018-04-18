package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.BiSaiShiPinActivity;
import com.hxwl.wulinfeng.bean.BiSaiShiPinBean;
import com.hxwl.wulinfeng.bean.WuZhanLMBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Allen on 2017/6/14.
 */
public class BiSaiShiPinAdapter extends BaseAdapter{
    private Activity activity ;
    private List<BiSaiShiPinBean> listData ;
    public BiSaiShiPinAdapter(Activity activity, List<BiSaiShiPinBean> listData) {
        this.activity = activity ;
        this.listData = listData ;
    }

    @Override
    public int getCount() {
        return listData != null ? listData.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int paramInt, View convertView, ViewGroup viewGroup) {
        BiSaiShiPinBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_bisaishipin,
                    null);
            holder = new ViewHolder();
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_title_time = (TextView) convertView.findViewById(R.id.tv_title_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(!StringUtils.isEmpty(dataBean.getIcon())){
            ImageUtils.getPic(dataBean.getIcon(),holder.iv_img,activity);
        }else if(!StringUtils.isEmpty(dataBean.getFang_icon())){
            ImageUtils.getPic(dataBean.getFang_icon(),holder.iv_img,activity);
        }else{
            holder.iv_img.setImageResource(R.drawable.wlf_deimg);
        }
        holder.tv_title.setText(dataBean.getName());
        holder.tv_title_time.setText(TimeUtiles.getTimeForDuizhen(dataBean.getLastVideoTime()));
        holder.tv_title.setTag(dataBean);
        return convertView;
    }

    class ViewHolder{
        public ImageView iv_img;
        public TextView tv_title;
        public TextView tv_title_time;
    }
}
