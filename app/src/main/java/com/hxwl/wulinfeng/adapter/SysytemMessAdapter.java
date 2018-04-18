package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.SystemMessageActivity;
import com.hxwl.wulinfeng.bean.MessageBean;
import com.hxwl.wulinfeng.bean.SystemMessageBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

/**
 * Created by Allen on 2017/6/14.
 */
public class SysytemMessAdapter extends BaseAdapter{
    private Activity activity ;
    private List<SystemMessageBean> listData ;
    public SysytemMessAdapter(Activity activity, List<SystemMessageBean> listData) {
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
        SystemMessageBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_systemmess,
                    null);
            holder = new ViewHolder();
            holder.tv_flag = ((TextView) convertView
                    .findViewById(R.id.tv_flag));

            holder.tv_title = ((TextView) convertView
                    .findViewById(R.id.tv_title));

            holder.tv_msg = ((TextView) convertView
                    .findViewById(R.id.tv_msg));

            holder.tv_time = ((TextView) convertView
                    .findViewById(R.id.tv_time));

            holder.tv_jump2det = ((TextView) convertView
                    .findViewById(R.id.tv_jump2det));

            holder.iv_img = ((ImageView) convertView
                    .findViewById(R.id.iv_img));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(dataBean.getTitle());
        holder.tv_title.setTag(dataBean);
        holder.tv_msg.setText(dataBean.getContents());
        holder.tv_time.setText(TimeUtiles.getTimeeForTuiJ(dataBean.getTime()));
        if(TextUtils.isEmpty(dataBean.getImg())){
            holder.iv_img.setVisibility(View.GONE);
        }else{
            holder.iv_img.setVisibility(View.VISIBLE);
            ImageUtils.getPic(dataBean.getImg() ,holder.iv_img ,activity);
        }


        return convertView;
    }
    class ViewHolder{
        public TextView tv_flag;
        public TextView tv_title;
        public TextView tv_msg;
        public TextView tv_time;
        public TextView tv_jump2det;
        public ImageView iv_img;
    }
}
