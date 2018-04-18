package com.lecloud.skin.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import com.hxwl.wulinfeng.R;
import com.lecloud.skin.ui.base.BaseChgScreenBtn;

/**
 * 全、半屏切换
 */
public class V4ChgScreenBtn extends BaseChgScreenBtn {

    public V4ChgScreenBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public V4ChgScreenBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public V4ChgScreenBtn(Context context) {
        super(context);
    }


	@Override
	protected int getZoomInResId() {
		return R.drawable.xiaoping;
	}

	@Override
	protected int getZoomOutResId() {
		return R.drawable.quanping;
	}

}
