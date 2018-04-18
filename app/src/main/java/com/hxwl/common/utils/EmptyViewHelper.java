package com.hxwl.common.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxwl.wulinfeng.R;


/**
 * Created by Allen on 2017/8/8.
 */

public class EmptyViewHelper {
    private View mListView;
    private View emptyView;
    private Context mContext;
    private String mEmptyText;
    private int type = 0; //0表示是数据加载中  1表示加载失败网络异常 2表示没有内容
    private FrameLayout parent;

    public EmptyViewHelper(View listView, FrameLayout parent, int type) {
        mListView = listView;
        mContext = listView.getContext();
        this.parent = parent;
        this.type = type;
        if (0 == type) {
            initEmptyView();
        } else {
        }
    }

    public EmptyViewHelper(View listView, FrameLayout parent) {
        mListView = listView;
        mContext = listView.getContext();
        this.parent = parent;
        if (0 == type) {
            initEmptyView();
        } else {
        }
    }

    public EmptyViewHelper(View listView, String text, FrameLayout parent) {
        mListView = listView;
        mContext = listView.getContext();
        mEmptyText = text;
        this.parent = parent;
        if (0 == type) {
            initEmptyView();
        } else {
        }
    }

    public void setType(int type) {
        this.type = type;
        notifiView();
    }

    private void initEmptyView() {
        emptyView = View.inflate(mContext, R.layout.progress_dialog2, null);

        TextView tv_reconnection = (TextView) emptyView.findViewById(R.id.tv_reconnection);
        ImageView imageView = (ImageView) emptyView.findViewById(R.id.dialog_pg);
        TextView progress_dialog_msgTextView = (TextView) emptyView.findViewById(R.id.progress_dialog_msgTextView);
        if (0 == type) {
            imageView.setImageResource(R.drawable.progress_backgroud);
            AnimationDrawable drawable = (AnimationDrawable) imageView.getDrawable();
            drawable.start();
//            Glide.with(mContext)
//                    .load(R.drawable.loadinggif)
//                    .asGif()
//                    .into(imageView);
            progress_dialog_msgTextView.setText("稍等片刻...");
            tv_reconnection.setVisibility(View.GONE);
        } else if(1 == type){
            imageView.setImageResource(R.drawable.yichang);
            tv_reconnection.setVisibility(View.VISIBLE);
            tv_reconnection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callBackListener != null){
                        callBackListener.OnClickButton(v);
                    }
                }
            });
            progress_dialog_msgTextView.setText("网络异常，请刷新重试");
        } else if(2 == type){
            imageView.setVisibility(View.INVISIBLE);
            tv_reconnection.setVisibility(View.INVISIBLE);
            progress_dialog_msgTextView.setText("未查询到");
        } else if(3 == type) {
            imageView.setVisibility(View.INVISIBLE);
            tv_reconnection.setVisibility(View.INVISIBLE);
            progress_dialog_msgTextView.setText("您还没有发布动态");
        } else{
            imageView.setImageResource(R.drawable.yichang);
            tv_reconnection.setVisibility(View.VISIBLE);
            tv_reconnection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callBackListener != null){
                        callBackListener.OnClickButton(v);
                    }
                }
            });
            progress_dialog_msgTextView.setText("数据异常，请刷新重试");
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER_HORIZONTAL);
        parent.addView(emptyView, lp);
        initView();
    }

    private void notifiView() {
        parent.removeView(emptyView);
        initEmptyView();
    }

    private void initView() {
        if (mListView instanceof AbsListView) {
            ((AbsListView) mListView).setEmptyView(emptyView);
        } else if (mListView instanceof EmptyRecyclerView) {
            ((EmptyRecyclerView) mListView).setEmptyView(emptyView);
        } else {

        }
    }

    public static interface OnClickCallBackListener {
        public void OnClickButton(View v);
    }

    private OnClickCallBackListener callBackListener;            //声明接口对象

    public void setCallBackListener(OnClickCallBackListener callBackListener) {
       this.callBackListener = callBackListener;
    }

}
