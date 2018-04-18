package com.hxwl.wulinfeng.entrance;

import com.hxwl.common.utils.LazyViewPager;
import com.hxwl.wulinfeng.base.BaseActivity;


import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.hxwl.wulinfeng.R;

/**
 * 引导页
 * @author tianbx
 */
public class GuideActivity extends BaseActivity {
	
	public static List<BasePager> list = new ArrayList<BasePager>();//存放viewpager
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
//		getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
		setSwipeBackEnable(false); //设置本页是不是能左滑返回
		initview();
	}
	//真正的沉浸式状态栏
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(hasFocus && Build.VERSION.SDK_INT >= 19){
			View decorView = getWindow().getDecorView();
			decorView.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	private void initview() {
		boolean isFirstUse = getSharedPreferences("UserMessage", MODE_PRIVATE).getBoolean("isFirstUse", true);
		if (isFirstUse) {
			list.add(new GuideViewPager1(GuideActivity.this));
			list.add(new GuideViewPager2(GuideActivity.this));
			list.add(new GuideViewPager3(GuideActivity.this));
			list.add(new GuideViewPager4(GuideActivity.this));

			LazyViewPager viewpager = (LazyViewPager) findViewById(R.id.viewpager);
			
			Mypageradapter mypageradapter = new Mypageradapter();
			viewpager.setAdapter(mypageradapter);
			
			viewpager.setCurrentItem(0);
			viewpager.setOffscreenPageLimit(1);// 预加载
		}else {
			Intent intent = new Intent(GuideActivity.this, LoadingActivity.class);
			startActivity(intent);
			finish();
		}
		
		
	}
class Mypageradapter extends PagerAdapter{
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			// TODO Auto-generated method stub
			return view == obj;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager currentPager = list.get(position);
			View initView = currentPager.initView();
			currentPager.initData();// 初始化数据
			container.addView(initView);
			return initView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}
	
	
	
}
