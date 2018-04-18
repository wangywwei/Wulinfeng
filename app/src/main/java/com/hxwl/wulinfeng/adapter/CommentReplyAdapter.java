package com.hxwl.wulinfeng.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hxwl.wulinfeng.R;

import java.util.List;

/**
 * 武林里边评论adapter
 */
public class CommentReplyAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private ViewHolder viewHolder;
	private OnClickListener replyToReplyListener;
	private int parentPosition = -1;
	private List<String> replyList;

	public CommentReplyAdapter(Context context, List<String> replyList,
							   int parentPosition, OnClickListener replyToReplyListener) {
		this.inflater = LayoutInflater.from(context);
		this.parentPosition = parentPosition;
		this.replyToReplyListener = replyToReplyListener;
		this.replyList = replyList;
	}

	@Override
	public int getCount() {
		return 3;
//		return replyList.size();
	}

//	public void clearList() {
//		this.replyList.clear();
//	}

//	public void updateList(List<Reply> replyList) {
//		this.replyList.addAll(replyList);
//	}

	@Override
	public Object getItem(int position) {
		return replyList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_comment_reply, null);
			viewHolder = new ViewHolder();
			//回复的内容
			viewHolder.tv_comment_reply_text = (TextView) convertView
					.findViewById(R.id.tv_comment_reply_text);
			//用户名字
			viewHolder.tv_comment_reply_writer = (TextView) convertView
					.findViewById(R.id.tv_comment_reply_writer);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();

		}
//        int start = replyList.get(position).getFrom_nickname().length();
//		int end = replyList.get(position).getMsg().length();
//		int content = replyList.get(position).getTo_nickname().length();
//			String text = replyList.get(position).getFrom_nickname() + "回复" + replyList.get(position).getTo_nickname()+":"+replyList.get(position).getMsg();
//			SpannableStringBuilder ss = new SpannableStringBuilder(text);
//			// 设置指定位置文字的背景颜色（将“回复”设置成灰色）
//			ss.setSpan(new ForegroundColorSpan(Color.parseColor("#5A5A5A")),
//					start, start + 2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//			ss.setSpan(new ForegroundColorSpan(Color.parseColor("#5A5A5A")),
//				start + 2+content+1, start + 3+end+content, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//			viewHolder.tv_comment_reply_writer.setText(ss);
////		}
//		// 记录发表回复时，对应的position,用于确定所回复的对象，如果是楼中楼，还需记录父节点的position
//		convertView.setTag(R.id.tag_first, parentPosition);
//		convertView.setTag(R.id.tag_second, position);
//		convertView.setOnClickListener(replyToReplyListener);
		return convertView;
	}

	public class ViewHolder {
		private TextView tv_comment_reply_writer; // 评论者昵称
		private TextView tv_comment_reply_text; // 评论 内容
	}
}
