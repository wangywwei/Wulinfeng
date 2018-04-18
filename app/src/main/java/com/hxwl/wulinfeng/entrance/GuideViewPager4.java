package com.hxwl.wulinfeng.entrance;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HomeActivity;


public class GuideViewPager4 extends BasePager {

	private Activity context;

	public GuideViewPager4(Activity context) {
		super(context);
		this.context = context;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public View initView() {
		View view = View.inflate(this.context, R.layout.guide_viewpager4, null);
		// 渐变展示启动屏
		ImageView iv_onclick = (ImageView) view.findViewById(R.id.iv_loadingimage);
		iv_onclick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				context.getSharedPreferences("UserMessage", context.MODE_PRIVATE).edit().putBoolean("isFirstUse", false).commit();
				if (MakerApplication.instance.getLoginState().equals(MakerApplication.LOGIN)){
					context.startActivity(new Intent(context, HomeActivity.class));
					context.finish();
				}else {
					context.startActivity(new Intent(context, LoginActivity.class));
					context.finish();
				}
			}
		});

		return view;
	}

}
