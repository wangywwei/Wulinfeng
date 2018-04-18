package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.MessageActivity;
import com.hxwl.wulinfeng.bean.MessageBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

/**
 * Created by Allen on 2017/6/8.
 */
public class MessageAdapter extends BaseAdapter{
    private Activity activity ;
    private List<MessageBean> listData ;
    public MessageAdapter(MessageActivity messageActivity, List<MessageBean> listData) {
        this.activity = messageActivity ;
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
        MessageBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_message,
                    null);
            holder = new ViewHolder();
            holder.txt_user_name = ((TextView) convertView
                    .findViewById(R.id.txt_user_name));

            holder.tv_state = ((TextView) convertView
                    .findViewById(R.id.tv_state));

            holder.tv_time = ((TextView) convertView
                    .findViewById(R.id.tv_time));

            holder.text = ((TextView) convertView
                    .findViewById(R.id.text));

            holder.user_head = ((ImageView) convertView
                    .findViewById(R.id.user_head));

            holder.img = ((ImageView) convertView
                    .findViewById(R.id.img));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txt_user_name.setText(dataBean.getMsg_info().getNickname());
        String type = dataBean.getType() ;
        if("1".equals(type)){//zan
            holder.tv_state.setText("赞了你的发布");
        }else{
            holder.tv_state.setText("评论了你："+dataBean.getMsg_info().getMsg());
        }
        holder.txt_user_name.setTag(dataBean);

        if(dataBean.getZhu_info().getImg() != null && dataBean.getZhu_info().getImg().size()>0){
            holder.img.setVisibility(View.VISIBLE);
            holder.text.setVisibility(View.GONE);
            ImageUtils.getPic(dataBean.getZhu_info().getImg().get(0),holder.img,activity);
        }else{
            holder.img.setVisibility(View.GONE);
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setText(dataBean.getZhu_info().getContents());
        }
        ImageUtils.getCirclePic(dataBean.getMsg_info().getHead_url(),holder.user_head,activity);
        holder.tv_time.setText(dataBean.getMsg_info().getTime() == null ? "" : TimeUtiles.getTimeed_(dataBean.getMsg_info().getTime()));

        return convertView;
    }
    class ViewHolder{
        public ImageView user_head;//头像
        public ImageView img;//
        public TextView txt_user_name;
        public TextView tv_state;//姓名
        public TextView tv_time;//日期时间
        public TextView text;//日期时间
    }
}
