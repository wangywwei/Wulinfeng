package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.WuLinZuiXinBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/6/8.
 * 武林最新 最热 list的适配器
 */
public class WuLinZuiXinAdapter extends BaseAdapter{

    private List<WuLinZuiXinBean> zuixinListData ;
    private Activity activity ;
    ViewHolder viewHolder = null;
    //评论点击事件
    private View.OnClickListener replyToCommentListener;
    private View.OnClickListener replyToReplyListener;

    public WuLinZuiXinAdapter(List<WuLinZuiXinBean> zuixinListData, Activity activity ,View.OnClickListener replyToCommentListener,
                              View.OnClickListener replyToReplyListener) {
        this.activity = activity ;
        this.zuixinListData = zuixinListData ;
        this.replyToCommentListener = replyToCommentListener;
        this.replyToReplyListener = replyToReplyListener;
    }

    @Override
    public int getCount() {
        return 5 ;
//        return zuixinListData != null ?zuixinListData.size() : 0;
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
    public View getView(final int position, View view, ViewGroup viewGroup) {
//        WuLinZuiXinBean bean = zuixinListData.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = View.inflate(activity, R.layout.pinglun_item, null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.user_icon);
            viewHolder.nickName = (TextView) view.findViewById(R.id.user_name);
            viewHolder.time = (TextView) view.findViewById(R.id.fatie_time);
            viewHolder.msg = (TextView) view.findViewById(R.id.pinglun_content);
            viewHolder.iv_zan = (ImageView) view.findViewById(R.id.iv_zan);
            viewHolder.btn_comment_reply = (ImageView) view.findViewById(R.id.btn_comment_reply);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

//        viewHolder.tv_louceng.setText("第" + (position + 1) + "楼");
//        viewHolder.nickName.setText(bean.getNickname());
//
//        if (bean.getTime()!=null && bean.getTime().isEmpty()){
//            viewHolder.time.setText(TimeUtiles.getStandardDate(bean.getTime()));
//        }else{
////            viewHolder.time.setText(bean.getTime());
//        }
//
//        viewHolder.msg.setText(bean.getContents());
//        ImageUtils.getCirclePic(bean.getHead_url(), viewHolder.imageView, activity);
//
////        if (!bean.getZan().contains(position+"")){
////            viewHolder.iv_zan.setBackgroundResource(R.drawable.zanblue_icon);
////        }else{
////            viewHolder.iv_zan.setBackgroundResource(R.drawable.zan_icon);
////        }
//
//        viewHolder.iv_zan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onZanClicklistener != null) {
//                    onZanClicklistener.onZanClicklistener(position, viewHolder.iv_zan);
//                }
//            }
//        });

        //判断当前评论是否有回复
//        viewHolder.lv_user_comment_replys
//                .setAdapter(new CommentReplyAdapter(activity, new ArrayList<String>(), position,
//                        replyToReplyListener));
//        //记录点击回复按钮时对应的position,用于确定所回复的对象
//        viewHolder.btn_comment_reply.setTag(position);
//        viewHolder.btn_comment_reply.setOnClickListener(replyToCommentListener);
        return view;
    }
    public class ViewHolder {
        ImageView imageView;
        ImageView iv_zan;
        TextView nickName;
        TextView time;
        TextView msg;
        MyListView lv_user_comment_replys; //二级评论
        ImageView btn_comment_reply; //二级评论按钮
        TextView tv_louceng;
    }
    public void setOnZanClicklistener(OnZanClicklistener onZanClicklistener) {
        this.onZanClicklistener = onZanClicklistener;
    }

    public OnZanClicklistener onZanClicklistener;

    public interface OnZanClicklistener {
        void onZanClicklistener(int position, View view);
    }
}
