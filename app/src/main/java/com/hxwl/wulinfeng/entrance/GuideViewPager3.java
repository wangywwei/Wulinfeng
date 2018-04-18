package com.hxwl.wulinfeng.entrance;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.hxwl.wulinfeng.R;


public class GuideViewPager3 extends BasePager {

	private Activity context;

	public GuideViewPager3(Activity context) {
		super(context);
		this.context = context;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public View initView() {
		View view = View.inflate(this.context, R.layout.guide_viewpager3, null);
		// 渐变展示启动屏
		return view;
	}

}
