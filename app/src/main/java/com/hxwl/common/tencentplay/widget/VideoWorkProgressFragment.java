package com.hxwl.common.tencentplay.widget;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;

/**
 * Created by hanszhli on 2017/6/5.
 */

public class VideoWorkProgressFragment extends DialogFragment {
    private View mContentView;
    private ImageView mIvStop;
    private NumberProgressBar mPbLoading;
    private int mProgress;
    private View.OnClickListener mListener;
    private TextView joiner_tv_msg;
    private String tv_msg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(R.style.ConfirmDialogStyle, R.style.customTheme);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mContentView = inflater.inflate(R.layout.layout_joiner_progress, null);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        mIvStop = (ImageView) mContentView.findViewById(R.id.joiner_iv_stop);
        joiner_tv_msg = (TextView) mContentView.findViewById(R.id.joiner_tv_msg);
        mPbLoading = (NumberProgressBar) mContentView.findViewById(R.id.joiner_pb_loading);
        mPbLoading.setMax(100);
        mPbLoading.setProgress(mProgress);
        mIvStop.setOnClickListener(mListener);
        if(!StringUtils.isEmpty(tv_msg)){
            joiner_tv_msg.setText(tv_msg);
        }
        return mContentView;
    }

    public void setJoiner_tv_msg(String joiner_tv_msg) {
        tv_msg = joiner_tv_msg;
    }

    /**
     * 设置停止按钮的监听
     *
     * @param listener
     */
    public void setOnClickStopListener(View.OnClickListener listener) {
        if (mIvStop == null) {
            mListener = listener;
        } else {
            mListener = listener;
            mIvStop.setOnClickListener(listener);
        }
    }

    /**
     * 设置进度条
     *
     * @param progress
     */
    public void setProgress(int progress) {
        if (mPbLoading == null) {
            mProgress = progress;
            return;
        }
        mPbLoading.setProgress(progress);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        //此处不使用用.show(...)的方式加载dialogfragment，避免IllegalStateException
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(this, tag);
        transaction.commit();
    }

    @Override
    public void dismiss() {
        // 和show对应
        if (getFragmentManager() != null && getFragmentManager().beginTransaction() != null && isAdded()) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(this);
            transaction.commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        getDialog().getWindow().setLayout( dm.widthPixels,  getDialog().getWindow().getAttributes().height );

    }
}
