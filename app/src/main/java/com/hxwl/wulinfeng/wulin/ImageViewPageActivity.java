package com.hxwl.wulinfeng.wulin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hxwl.common.utils.FileUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.config.SystemDir;
import com.hxwl.wulinfeng.view.TouchImageView;
import com.jaeger.library.StatusBarUtil;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 查看多个图片
 * @author Allen
 *
 */
public class ImageViewPageActivity extends BaseActivity {

	private ViewPager viewPager;
	private int currentPosition;
	private MyPageAdapter adapter;
	private ImageView[] dots;
	private int imagewsize;
	private JSONArray jsonArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_image_viewpager);
		StatusBarUtil.setColor(ImageViewPageActivity.this,getResources().getColor(R.color.text_black),0);
		super.onCreate(savedInstanceState);
		initView();
	}

	public void initView() {
		ImageView iv_downpic = (ImageView) findViewById(R.id.iv_downpic);
		iv_downpic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url_2 = jsonArray.get(viewPager.getCurrentItem()).toString();
				String fileName2 = url_2.substring(url_2.lastIndexOf("/") + 1);
				final File file2 = new File(SystemDir.DIR_DOWN_IMAGE + "/" + fileName2);
				if (!file2.exists()) {
					List<String> picUrlList = new ArrayList<>();
					picUrlList.add(url_2);
					File appDir = new File(SystemDir.DIR_DOWN_IMAGE);
					if (!appDir.exists()) {
						appDir.mkdirs();
					}
					final File currentFile = new File(appDir, fileName2);
					if (currentFile.exists()) {
						ToastUtils.showToast(ImageViewPageActivity.this,"图片已存在");
						return ;
					}
					Glide.with(ImageViewPageActivity.this)
							.load(url_2)
							.asBitmap()
							.into(new SimpleTarget<Bitmap>() {
								@Override
								public void onResourceReady(Bitmap bitmap1, GlideAnimation<? super Bitmap> glideAnimation) {
									if (bitmap1 != null) {
										// 在这里执行图片保存方法
										String filePath = currentFile.getPath();
										BitmapUtils.saveBitToCanmer(bitmap1, filePath, 60);

										Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
										Uri uri = Uri.fromFile(file2);
										intent.setData(uri);
										sendBroadcast(intent) ;

										ToastUtils.showToast(ImageViewPageActivity.this,"图片保存至"+filePath);
									}
								}
							});
				}
			}
		});
		viewPager = (ViewPager) this.findViewById(R.id.viewpagerimage);
		currentPosition = getIntent().getIntExtra("position", 0);

		String jsonString =getIntent().getStringExtra("jsonArray");
		jsonArray = JSONArray.parseArray(jsonString);
		if(0 == currentPosition){
			String url = getIntent().getStringExtra("url");
			if(!TextUtils.isEmpty(url)){
				for(int i = 0; i< jsonArray.size() ; i++){
					if(url.equals(jsonArray.get(i))){
						currentPosition = i ;
					}
				}
			}
		}
		imagewsize= jsonArray.size();
		if(imagewsize>1){
			initDots(jsonArray.size());
		}
		viewPager.setOnPageChangeListener(pageChangeListener);
		adapter = new MyPageAdapter(jsonArray);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentPosition);
	}
	
	private void initDots(int imageSize){
		LinearLayout ll=(LinearLayout)this.findViewById(R.id.ll_point);
		dots=new ImageView[imageSize];
		for(int i=0;i<imageSize;i++){
			if(dots[i] != null){
				dots[i]=(ImageView)ll.getChildAt(i);
				dots[i].setEnabled(false);
				dots[i].setVisibility(View.VISIBLE);
			}
		}
		if(dots[currentPosition] != null){
			dots[currentPosition].setEnabled(true);
		}
	}
	
	private void setCurrentDot(int position){
		if(position<0||position>imagewsize||position==currentPosition){
			return;
		}if(dots[position] != null)dots[position].setEnabled(true);
		if(dots[currentPosition]!=null)dots[currentPosition].setEnabled(false);
		currentPosition=position;
	}
	
	
	class MyPageAdapter extends PagerAdapter {

		private JSONArray jsonArray;
		private ArrayList<TouchImageView> mViews = new ArrayList<TouchImageView>();

		public MyPageAdapter(JSONArray jsonArray) {
			this.jsonArray = jsonArray;
			getAllImages();
		}

		public void getAllImages() {
			TouchImageView imageView;
			for (int i = 0; i < jsonArray.size(); i++) {
				imageView = new TouchImageView(
						ImageViewPageActivity.this);
				if(i==currentPosition){
					String imagePath = jsonArray.get(i).toString();
					downlaodImage(imagePath,imageView,i);
					imageView.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					mViews.add(imageView);
				}else{
					String imagePath = jsonArray.get(i).toString();
					downlaodImage(imagePath,imageView,i);
					imageView.setLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					mViews.add(imageView);
				}
				imageView.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						String url_2 = jsonArray.get(viewPager.getCurrentItem()).toString();
						String fileName2 = url_2.substring(url_2.lastIndexOf("/") + 1);
						final File file2 = new File(SystemDir.DIR_DOWN_IMAGE);
						if (!file2.exists()) {
							List<String> picUrlList = new ArrayList<>();
							picUrlList.add(url_2);
							File appDir = new File(SystemDir.DIR_DOWN_IMAGE);
							if (!appDir.exists()) {
								appDir.mkdirs();
							}
							final File currentFile = new File(appDir, fileName2);
							if (currentFile.exists()) {
								return false;
							}
							Glide.with(ImageViewPageActivity.this)
									.load(url_2)
									.asBitmap()
									.into(new SimpleTarget<Bitmap>() {
										@Override
										public void onResourceReady(Bitmap bitmap1, GlideAnimation<? super Bitmap> glideAnimation) {
											if (bitmap1 != null) {
												// 在这里执行图片保存方法
												String filePath = currentFile.getPath();
												BitmapUtils.saveBitToCanmer(bitmap1, filePath, 60);

												Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
												Uri uri = Uri.fromFile(file2);
												intent.setData(uri);
												sendBroadcast(intent) ;

												ToastUtils.showToast(ImageViewPageActivity.this,"图片保存至"+filePath);
											}
										}
									});
						}
						return false;
					}
				});
			}
		}
		private void downlaodImage(String url,final TouchImageView imageView,final int position){
			ImageUtils.getPic(url,imageView,ImageViewPageActivity.this);
		}
		
		
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		@Override
		public Object instantiateItem(View container, final int position) {
			TouchImageView iv = mViews.get(position);
			((ViewPager) container).addView(iv);
			iv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish() ;
				}
			});
			return iv;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			if (mViews.size() >= position + 1)
			{
				((ViewPager) container).removeView(mViews.get(position));
			}
		}
		
		@Override
		public int getCount() {
			return jsonArray.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			setCurrentDot(arg0);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
			
		}
	};
	
}
