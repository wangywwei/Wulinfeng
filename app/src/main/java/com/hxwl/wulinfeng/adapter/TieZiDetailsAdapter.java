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

import com.hxwl.newwlf.modlebean.DongTaiHuifuBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.HuiFuBean;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.bean.TujiImgs;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;


/**
 * Created by Allen on 2017/6/12.
 * 帖子详情adapter
 */
public class TieZiDetailsAdapter extends BaseAdapter{
    private Activity context ;
    private List<DongTaiHuifuBean.DataBean.CommentListBean> listData ;
    public TieZiDetailsAdapter(Activity tieZiDetailsActivity, List<DongTaiHuifuBean.DataBean.CommentListBean> huiFuBeenList) {
        this.context = tieZiDetailsActivity ;
        this.listData = huiFuBeenList;
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
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
        DongTaiHuifuBean.DataBean.CommentListBean noteComment = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_details_huifu,null);
            holder = new ViewHolder();
            //回复的内容
            holder.tv_comment_reply_text = (TextView) convertView
                    .findViewById(R.id.tv_comment_reply_text);
            //用户名字
            holder.tv_comment_reply_writer = (TextView) convertView
                    .findViewById(R.id.tv_comment_reply_writer);
            holder.tv_comment_reply_writer2 = (TextView) convertView
                    .findViewById(R.id.tv_comment_reply_writer2);
            holder.tv_comment_reply_writer3 = (TextView) convertView
                    .findViewById(R.id.tv_comment_reply_writer3);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (noteComment.getReferUserName() == null) {
            holder.tv_comment_reply_writer.setText(Photos.stringPhoto(noteComment.getUserName())+":");
            holder.tv_comment_reply_writer2.setVisibility(View.GONE);
            holder.tv_comment_reply_writer3.setVisibility(View.GONE);
            holder.tv_comment_reply_text.setText(noteComment.getContent());
        } else {
            holder.tv_comment_reply_writer.setText(noteComment.getUserName());
            holder.tv_comment_reply_writer2.setVisibility(View.VISIBLE);
            holder.tv_comment_reply_writer3.setVisibility(View.VISIBLE);
            holder.tv_comment_reply_writer3.setText(Photos.stringPhoto(noteComment.getReferUserName())+":");

            holder.tv_comment_reply_text.setText(noteComment.getContent());
        }
        holder.tv_comment_reply_text.setTag(noteComment);

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
        private TextView tv_comment_reply_writer; // 评论者昵称
        private TextView tv_comment_reply_writer2; // 评论者昵称
        private TextView tv_comment_reply_writer3; // 评论者昵称
        private TextView tv_comment_reply_text; // 评论 内容
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
