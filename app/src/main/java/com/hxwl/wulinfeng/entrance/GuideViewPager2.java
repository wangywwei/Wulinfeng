package com.hxwl.wulinfeng.entrance;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hxwl.wulinfeng.R;


public class GuideViewPager2 extends BasePager {

	public GuideViewPager2(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override 
	public View initView() {
		View view = View.inflate(this.context, R.layout.guide_viewpager2, null);
		//渐变展示启动屏
		return view;
	}

}
