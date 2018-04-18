package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.modlebean.GenduoHuifuBean;
import com.hxwl.newwlf.modlebean.PLZixunListBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiFuDetailsActivity;
import com.hxwl.wulinfeng.bean.GengHuiFuBean;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.bean.TujiImgs;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

/**
 * Created by Allen on 2017/6/21.
 */
public class HuiFuAdapter extends BaseAdapter{
    private Activity activity ;
    private  List<GenduoHuifuBean.DataBean.ReplyListBean> listData ;
    public HuiFuAdapter(Activity activity, List<GenduoHuifuBean.DataBean.ReplyListBean> listData) {
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
    public View getView(final int paramInt, View convertView, ViewGroup viewGroup) {
        GenduoHuifuBean.DataBean.ReplyListBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_huifudetails_activity,null);
            holder = new ViewHolder();
            holder.tv_comment_reply_writer = (TextView) convertView.findViewById(R.id.tv_comment_reply_writer);
            holder.tv_comment_reply_writer2 = (TextView) convertView.findViewById(R.id.tv_comment_reply_writer2);
            holder.tv_comment_reply_writer3 = (TextView) convertView.findViewById(R.id.tv_comment_reply_writer3);
            holder.tv_comment_reply_text = (TextView) convertView.findViewById(R.id.tv_comment_reply_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (dataBean.getReferUserName() == null) {
            holder.tv_comment_reply_writer.setText(Photos.stringPhoto(dataBean.getUserName())+":");
            holder.tv_comment_reply_writer2.setVisibility(View.GONE);
            holder.tv_comment_reply_writer3.setVisibility(View.GONE);
            holder.tv_comment_reply_text.setText(dataBean.getContent());
        } else {
            holder.tv_comment_reply_writer.setText(Photos.stringPhoto(dataBean.getUserName()));
            holder.tv_comment_reply_writer2.setVisibility(View.VISIBLE);
            holder.tv_comment_reply_writer3.setVisibility(View.VISIBLE);
            holder.tv_comment_reply_writer3.setText(Photos.stringPhoto(dataBean.getReferUserName())+":");
            holder.tv_comment_reply_text.setText(dataBean.getContent());
        }
        holder.tv_comment_reply_writer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onPinlun1Clicklistener){
                    onPinlun1Clicklistener.onPinlun1Clicklistener(paramInt);
                }
            }
        });

        holder.tv_comment_reply_writer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onPinlun2Clicklistener){
                    onPinlun2Clicklistener.onPinlun2Clicklistener(paramInt);
                }
            }
        });


        return convertView;
    }
    class ViewHolder{
        TextView tv_comment_reply_writer;
        TextView tv_comment_reply_writer2;
        TextView tv_comment_reply_writer3;
        TextView tv_comment_reply_text;
    }

    public OnZanClicklistener onPinlun1Clicklistener;
    public OnZanClicklistener2 onPinlun2Clicklistener;

    public void setOnPinlun1Clicklistener(OnZanClicklistener onPinlun1Clicklistener) {
        this.onPinlun1Clicklistener = onPinlun1Clicklistener;
    }

    public void setOnPinlun2Clicklistener(OnZanClicklistener2 onPinlun2Clicklistener) {
        this.onPinlun2Clicklistener = onPinlun2Clicklistener;
    }

    public interface OnZanClicklistener {
        void onPinlun1Clicklistener(int groupPosition);

    }
    public interface OnZanClicklistener2 {
        void onPinlun2Clicklistener(int groupPosition);

    }
}
