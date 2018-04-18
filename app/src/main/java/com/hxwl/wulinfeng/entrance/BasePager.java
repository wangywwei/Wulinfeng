package com.hxwl.wulinfeng.entrance;

import android.app.Activity;
import android.view.View;

/**
 * viewpager的基类
 * 
 * @author Allen
 * 
 */
public abstract class BasePager {
	public Activity context;
	public BasePager(Activity paramActivity) {
		this.context = paramActivity;
	}

	public abstract void initData();

	public abstract View initView();

	public boolean isInitView() {
		return false;
	};

}
