package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.BiSaiShiPinActivity;
import com.hxwl.wulinfeng.activity.LoginforCodeActivity;
import com.hxwl.wulinfeng.activity.TuJiHuiFuDetailsActivity;
import com.hxwl.wulinfeng.activity.VideoHuiFuDetailsActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.hxwl.wulinfeng.bean.HuiFuBean;
import com.hxwl.wulinfeng.bean.TuJiListDatilsBean;
import com.hxwl.wulinfeng.bean.VideoListDatilsBean;
import com.hxwl.wulinfeng.bean.ZanBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.OnClickEventUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.wulin.ZiXunDetilsAdapter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/6/29.
 */
public class VideoDetilsAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private String id;
    //数据
    private List<VideoListDatilsBean> listData;
    private View.OnClickListener replyToCommentListener;// 评论监听
    private final static String SECCLICK = "点赞成功";
    private final static String FAILCLICK = "取消点赞";
    public static int replay_to_comment = 1000;
    private LayoutInflater inflater;

    // 剪切板 控制器
    private ClipboardManager mClipboard = null;

    public VideoDetilsAdapter(Activity activity, List<VideoListDatilsBean> listData, View.OnClickListener replyToCommentListener, String id) {
        this.context = activity;
        this.listData = listData;
        this.id = id;
        this.replyToCommentListener = replyToCommentListener;
        mClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listData.get(groupPosition).getHuifu() != null) {
            return listData.get(groupPosition).getHuifu().size() > 6 ? 6 : listData.get(groupPosition).getHuifu().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listData.get(groupPosition).getHuifu().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 分组getview
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final VideoDetilsAdapter.ViewHolder holder;
        final VideoListDatilsBean itemNotes = listData.get(groupPosition);
        if (convertView == null) {
            holder = new VideoDetilsAdapter.ViewHolder();
            convertView = View.inflate(context, R.layout.zixun_pinglun_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.user_icon);
            holder.iv_line = (ImageView) convertView.findViewById(R.id.iv_line);
            holder.nickName = (TextView) convertView.findViewById(R.id.user_name);
            holder.time = (TextView) convertView.findViewById(R.id.fatie_time);
            holder.tv_zancount = (TextView) convertView.findViewById(R.id.tv_zancount);
            holder.msg = (TextView) convertView.findViewById(R.id.pinglun_content);
            holder.iv_zan = (ImageView) convertView.findViewById(R.id.iv_zan);
            holder.btn_comment_reply = (ImageView) convertView.findViewById(R.id.btn_comment_reply);

            convertView.setTag(holder);
        } else {
            holder = (VideoDetilsAdapter.ViewHolder) convertView.getTag();
        }
        if(groupPosition == 0){
            holder.iv_line.setVisibility(View.GONE);
        }else{
            holder.iv_line.setVisibility(View.VISIBLE);
        }

        holder.nickName.setText(itemNotes.getNickname());
        holder.tv_zancount.setText(itemNotes.getZan_times());
        if (itemNotes.getTime() != null && !itemNotes.getTime().isEmpty()) {
            holder.time.setText(TimeUtiles.getTimeeForTuiJ(itemNotes.getTime()));
        } else {
//            viewHolder.time.setText(bean.getTime());
        }
        if (itemNotes.getMsg() != null && !StringUtils.isEmpty(itemNotes.getMsg())) {
            holder.msg.setVisibility(View.VISIBLE);
            holder.msg.setText(itemNotes.getMsg());
        } else {
            holder.msg.setVisibility(View.GONE);
        }
        ImageUtils.getCirclePic(itemNotes.getHead_url(), holder.imageView, context);
        holder.iv_zan.setTag(itemNotes);

        if ("1".equals(itemNotes.getIs_zan())) {//没有点过
            holder.tv_zancount.setTextColor(context.getResources().getColor(R.color.shouye_tab));
            holder.iv_zan.setImageResource(R.drawable.yizan_icon);
        } else {//点过赞
            holder.tv_zancount.setTextColor(context.getResources().getColor(R.color.text_zan));
            holder.iv_zan.setImageResource(R.drawable.zan_icon);
        }
        holder.iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OnClickEventUtils.isFastClick()){
                    return;
                }
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    context.startActivity(new Intent(context,LoginforCodeActivity.class));
                    return ;
                }
                String is_zan = itemNotes.getIs_zan();
                if (TextUtils.isEmpty(is_zan)) {//没有点过 holder.iv_zan.setImageResource(R.drawable.zanblue_icon);
                    AppUtils.setEvent(context,"VedioLike","视频-点赞");
                    dianZan(holder.iv_zan, itemNotes);
                } else {//点过赞
                    ToastUtils.showToast(context, "已经点过赞了");
                }
            }

            //点赞
            public void dianZan(final View view, final VideoListDatilsBean itemNotes) {
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    context.startActivity(new Intent(context,LoginforCodeActivity.class));
                    return ;
                }

                JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                        context, true, new ExcuteJSONObjectUpdate() {
                    @Override
                    public void excute(ResultPacket result) {
                        if (result != null && result.getStatus().equals("ok")) {
                            itemNotes.setIs_zan("1");
                            holder.tv_zancount.setText(Integer.parseInt(TextUtils.isEmpty(itemNotes.getZan_times())?("0"):(itemNotes.getZan_times()))+1+"") ;
                            itemNotes.setZan_times(Integer.parseInt(TextUtils.isEmpty(itemNotes.getZan_times())?"0":itemNotes.getZan_times())+1+"");
                            holder.tv_zancount.setTextColor(context.getResources().getColor(R.color.shouye_tab));
                            if (view instanceof ImageView) {
                                ((ImageView) view).setImageResource(R.drawable.yizan_icon);
                            } else {
                                ((ImageView) view.findViewById(R.id.iv_zan)).setImageResource(R.drawable.yizan_icon);
                            }
                            ZanBean bean = new ZanBean();
                            bean.setNickname(MakerApplication.instance().getUsername());
                            bean.setHead_url(MakerApplication.instance().getHeadPic());
                            bean.setUid(MakerApplication.instance().getUid());
                        } else if (result != null && result.getStatus().equals("empty")) {

                        } else {
                            UIUtils.showToast("服务器异常");
                        }
                    }
                });
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", id);
                map.put("type", "33");
                map.put("gentieId", itemNotes.getId());
                map.put("uid", MakerApplication.instance().getUid());
                map.put("loginKey", MakerApplication.instance().getLoginKey());
                map.put("method", NetUrlUtils.zixun_gentiezan);
                tasker.execute(map);

            }
        });
        //把数据背在 holder.nickName身上
        holder.nickName.setTag(itemNotes);

        holder.btn_comment_reply.setTag(itemNotes);
        holder.btn_comment_reply.setOnClickListener(replyToCommentListener);
        return convertView;
    }

    /**
     * 儿子getview
     */
    @Override
    public View getChildView(final int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final HuiFuBean noteComment = listData.get(groupPosition)
                .getHuifu().get(childPosition);
        VideoDetilsAdapter.ViewHolder holder = new VideoDetilsAdapter.ViewHolder();
        convertView = View.inflate(context, R.layout.item_zixun_comment_reply,
                null);
        //回复的内容
        holder.tv_comment_reply_text = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_text);
        //用户名字
        holder.tv_comment_reply_writer = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_writer);

        holder.iv_jiange = (ImageView) convertView
                .findViewById(R.id.iv_jiange);
        //c查看全部按钮textview
        holder.tv_all = (TextView) convertView
                .findViewById(R.id.tv_all);
        if (!isLastChild) {
            holder.tv_all.setVisibility(View.GONE);
        } else {
            holder.tv_all.setVisibility(View.VISIBLE);
//			if(){//还有下一页
//
//			}else{
//				holder.tv_all.setVisibility(View.GONE);
//			}
            //点击查看全部
            if (listData.get(groupPosition).getHuifu().size() > 6) {
                holder.tv_all.setVisibility(View.VISIBLE);
            } else {
                holder.tv_all.setVisibility(View.GONE);
            }
            holder.tv_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//查看更多
                    Intent intent = new Intent(context, VideoHuiFuDetailsActivity.class);
                    intent.putExtra("bean", listData.get(groupPosition));
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            });
        }
        int start = noteComment.getFrom_nickname().length();
        int end = noteComment.getMsg().length();
        int content = noteComment.getTo_nickname() == null ? 0 : noteComment.getTo_nickname().length();
        String text;
        if (noteComment.getTo_nickname() == null) {
            holder.tv_comment_reply_writer.setText(noteComment.getFrom_nickname());
            holder.tv_comment_reply_text.setText(noteComment.getMsg());
        } else {
            text = noteComment.getFrom_nickname() + "回复" + noteComment.getTo_nickname() + ":" + noteComment.getMsg();
            SpannableStringBuilder ss = new SpannableStringBuilder(text);
            // 设置指定位置文字的背景颜色（将“回复”设置成灰色）
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#5A5A5A")),
                    start, start + 2, Spannable
                            .SPAN_EXCLUSIVE_INCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(Color.parseColor("#5A5A5A")),
                    start + 2 + content + 1, start + 3 + end + content, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.tv_comment_reply_writer.setText(ss);
            holder.tv_comment_reply_text.setText("");
        }
//		holder.tv_comment_reply_writer.setText(noteComment.getFrom_nickname());
        convertView.setTag(R.id.tag_first, groupPosition);
        convertView.setTag(R.id.tag_second, childPosition);
//		convertView.setOnClickListener(replyToCommentListener);
        if(isLastChild){
            holder.iv_jiange.setVisibility(View.VISIBLE);
        }else{
            holder.iv_jiange.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ViewHolder {
        ImageView imageView;
        ImageView iv_zan;
        ImageView iv_line ;
        TextView nickName;
        TextView time;
        TextView msg;
        ImageView btn_comment_reply; //二级评论按钮

        private TextView tv_comment_reply_writer; // 评论者昵称
        private TextView tv_comment_reply_text; // 评论 内容
        private TextView tv_all; // 查看全部的
        public RelativeLayout rl_relayout;
        public TextView tv_zancount;
        public ImageView iv_jiange;
    }

    public void setOnZanClicklistener(ZiXunDetilsAdapter.OnZanClicklistener onZanClicklistener) {
        this.onZanClicklistener = onZanClicklistener;
    }

    public ZiXunDetilsAdapter.OnZanClicklistener onZanClicklistener;

    public interface OnZanClicklistener {
        void onZanClicklistener(int position, View view);
    }
}
