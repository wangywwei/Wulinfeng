package com.hxwl.wulinfeng.wulin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HomeActivity;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.photoView.PhotoView;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.FileUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.config.SystemDir;
import com.jaeger.library.StatusBarUtil;


import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static com.hxwl.wulinfeng.R.id.iv_downpic;

/**
 * 查看单个照片页面（全屏）
 * @author Allen
 *
 */
public class SeeHighDefinitionPictureActivity extends BaseActivity {

	private PhotoView touchImageView;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_highdefinition_picture);
		StatusBarUtil.setColor(SeeHighDefinitionPictureActivity.this,getResources().getColor(R.color.text_black),0);
		super.onCreate(savedInstanceState);
		touchImageView = (PhotoView)findViewById(R.id.mTouchImageView);
		Intent  intent=getIntent();
		final String url=intent.getStringExtra("highDefinitionUrl");
		if(StringUtils.isEmpty(url)){
			return ;
		}
		RelativeLayout rl_layout = (RelativeLayout) findViewById(R.id.rl_layout);
		rl_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		ImageView iv_downpic = (ImageView) findViewById(R.id.iv_downpic);
		iv_downpic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url_2 = url;
				String fileName2 = url_2.substring(url_2.lastIndexOf("/") + 1);
				final File file2 = new File(SystemDir.DIR_DOWN_IMAGE + "/" + fileName2 );
				if (!file2.exists()) {
					List<String> picUrlList = new ArrayList<>();
					picUrlList.add(url_2);
					File appDir = new File(SystemDir.DIR_DOWN_IMAGE);
					if (!appDir.exists()) {
						appDir.mkdirs();
					}
					final File currentFile = new File(appDir, fileName2);
					if (currentFile.exists()) {
						return;
					}
					Glide.with(SeeHighDefinitionPictureActivity.this)
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

										ToastUtils.showToast(SeeHighDefinitionPictureActivity.this,"图片保存至"+filePath);
									}
								}
							});
				}
			}
		});
		touchImageView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				String url_2 = url;
				String fileName2 = url_2.substring(url_2.lastIndexOf("/") + 1);
				final File file2 = new File(SystemDir.DIR_IMAGE);
				if (!file2.exists()) {
					List<String> picUrlList = new ArrayList<>();
					picUrlList.add(url_2);
					File appDir = new File(SystemDir.DIR_IMAGE);
					if (!appDir.exists()) {
						appDir.mkdirs();
					}
					final File currentFile = new File(appDir, fileName2);
					if (currentFile.exists()) {
						return false;
					}
					Glide.with(SeeHighDefinitionPictureActivity.this)
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


										ToastUtils.showToast(SeeHighDefinitionPictureActivity.this,"图片保存至"+filePath);
									}
								}
							});
				}
				return false;
			}
		});
		ImageUtils.getPic(url,touchImageView,SeeHighDefinitionPictureActivity.this);

//		final String fileName = FileUtils.getFileName(url);
//		String screenShotPath = getSdHighDefinitionImgPath(fileName);
//		final Bitmap bitmap = ImageUtils.getBitmapByPath(screenShotPath);
//		touchImageView.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View arg0) {
//				String filePath="";
//				String cameraPath = Environment.getExternalStorageDirectory()
//						+ File.separator + "DCIM" + File.separator + "camera";
//				File file = new File(cameraPath);
//				if (!file.exists()) {
//					file.mkdir();
//				}
//				String filename = url.substring(url
//						.lastIndexOf("/"));
//				filePath = cameraPath +File.separator+filename;
//				if(bitmap!=null){
//					ImageUtils.saveBitmapTOSd(filePath, bitmap);
//					ImageUtils.scanPhoto(SeeHighDefinitionPictureActivity.this,filePath);
//				}else{
//					ImageUtils.downLoadImagToSd(SeeHighDefinitionPictureActivity.this, url, filePath);
//				}
//				return false;
//			}
//		});
		
		touchImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
//		if(bitmap!=null){
//			touchImageView.setImageBitmap(bitmap);
//		}else{
//			AppClient.getImageForView(touchImageView ,url,SeeHighDefinitionPictureActivity.this);
//
//		}
	}

	public Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int) scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(
				BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}

	public static String getSdHighDefinitionImgPath(String fileName){
		String imgPath = "";
		Boolean sdCardExist = FileUtils.checkSaveLocationExists();
		if (sdCardExist) {
			imgPath = Environment.getExternalStorageDirectory()
					+ File.separator+ NetUrlUtils.project+File.separator;
		} else {
			imgPath = Environment.getRootDirectory() + File.separator+ NetUrlUtils.project +
					File.separator;
		}
		return imgPath;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc();
	}
}
