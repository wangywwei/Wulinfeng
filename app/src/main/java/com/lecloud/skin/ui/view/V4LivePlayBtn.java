package com.lecloud.skin.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import com.hxwl.wulinfeng.R;
import com.lecloud.skin.ui.base.BasePlayBtn;

/**
 * 播放按钮
 */
public  class V4LivePlayBtn extends BasePlayBtn {
	
	public V4LivePlayBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public V4LivePlayBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

	public V4LivePlayBtn(Context context) {
		super(context);
	}

	@Override
	protected int getPauseResId() {
		return R.drawable.video_start;
	}

	@Override
	protected int getPlayResId() {
		return R.drawable.video_stop;
	}

	
   
}
