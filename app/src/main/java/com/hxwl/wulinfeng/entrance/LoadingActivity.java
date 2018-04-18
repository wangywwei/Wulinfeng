package com.hxwl.wulinfeng.entrance;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.common.utils.ThreadPoolUtils;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HomeActivity;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.AdvertisementBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.util.config.SystemDir;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

import static com.hxwl.wulinfeng.R.id.iv_loadingimage;
import static com.hxwl.wulinfeng.R.style.dialog;

/**
 * loading页面，检查是否有网络
 * @author Allen
 */
public class LoadingActivity extends BaseActivity {

	private View view;
	private ImageView iv_loadingimage;

	//倒计时
	private LinearLayout llyt_jishi;
	//数字
	private TextView tv_time;
	int time = 4;
	//刷新角标
	private static final int REFRESH_JIAOBIAO = 0;
	//跳转主页面
	private static final int TIAOZHUAN_MAIN = 1;
	//开启倒计时
	Timer timer = new Timer();
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case REFRESH_JIAOBIAO:
					tv_time.setText(time+"");
					break;
				case TIAOZHUAN_MAIN:
					if(timer != null){
						timer.cancel();
						timer = null;
					}
					if (MakerApplication.instance.getLoginState().equals(MakerApplication.LOGIN)){
						startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
						LoadingActivity.this.finish();
					}else {
						startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
						LoadingActivity.this.finish();
					}

					break;
			}

		}
	};
	private TextView tv_tiaoguo;
	private LinearLayout llyt_jishi1;
	private TextView tv_time1;

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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
		view = View.inflate(this, R.layout.activity_loading, null);
		setContentView(view);

		// 开发时调用，建议上线前关闭，以免影响性能
//		StatService.setDebugOn(false);
		StatService.start(this);
//		StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START,1,false);

		setSwipeBackEnable(false); //设置本页是不是能左滑返回
		initView();
	}

	private void initView() {
		iv_loadingimage = (ImageView) view.findViewById(R.id.iv_loadingimage);
		llyt_jishi = (LinearLayout) view.findViewById(R.id.llyt_jishi);
		tv_tiaoguo = (TextView) view.findViewById(R.id.tv_tiaoguo);
		tv_time = (TextView) view.findViewById(R.id.tv_time);

		llyt_jishi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(timer != null){
					timer.cancel();
					timer = null;
				}
				if (MakerApplication.instance.getLoginState().equals(MakerApplication.LOGIN)){
					startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
					LoadingActivity.this.finish();
				}else {
					startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
					LoadingActivity.this.finish();
				}
			}
		});
		//渐变展示启动屏
		AlphaAnimation animation = new AlphaAnimation(1f,1f);
		animation.setDuration(1000);
		iv_loadingimage.startAnimation(animation);
		// 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
		animation.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation arg0) {
				redirectTo();

			}
			@Override
			public void onAnimationRepeat(Animation animation) {

			}
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}

		});

	}

	private void enterMainActivity() {
		ThreadPoolUtils.newInstance().execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
					final  String localUrl = (String) SPUtils.get(LoadingActivity.this, "adUrl", "");
					final  String isShowAd = (String) SPUtils.get(LoadingActivity.this, "isShowAd", "");
					//true"1".equals(isShowAd)
					if (true) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								try{
									Bitmap adbm = null;
									if (!StringUtils.isEmpty(localUrl)) {
										String fileName = localUrl.substring(localUrl.lastIndexOf("/") + 1);
										adbm = BitmapUtils.getEncryptBitmap(SystemDir.DIR_IMAGE + "/" + fileName);
									}
									if (adbm != null) {
										SPUtils.put(LoadingActivity.this, "isShowAd", "0");
										iv_loadingimage.setImageBitmap(adbm);
										llyt_jishi.setVisibility(View.VISIBLE);
										timer.schedule(new TimerTask() {
											@Override
											public void run() {
												if(time < 1){
													mHandler.sendEmptyMessage(TIAOZHUAN_MAIN);
													return;
												}
												mHandler.sendEmptyMessage(REFRESH_JIAOBIAO);
												time --;

											}
										}, 0,100);
									} else {
										if (MakerApplication.instance.getLoginState().equals(MakerApplication.LOGIN)){
											startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
											LoadingActivity.this.finish();
										}else {
											startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
											LoadingActivity.this.finish();
										}
									}
								}catch (Exception e){
									if (MakerApplication.instance.getLoginState().equals(MakerApplication.LOGIN)){
										startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
										LoadingActivity.this.finish();
									}else {
										startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
										LoadingActivity.this.finish();
									}
								}

							}
						});
					} else{
						if (MakerApplication.instance.getLoginState().equals(MakerApplication.LOGIN)){
							startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
							LoadingActivity.this.finish();
						}else {
							startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
							LoadingActivity.this.finish();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void startApp() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			enterMainActivity();
		} else {
			//获取位置权限
			RxPermissions rxPermissions = new RxPermissions(this);
			rxPermissions.request(android.Manifest.permission.ACCESS_COARSE_LOCATION
			)
					.subscribe(new Action1<Boolean>() {
						@Override
						public void call(Boolean aBoolean) {
							if(aBoolean){
								enterMainActivity();
							}else{
								ToastUtils.showToast(LoadingActivity.this,"武林风需要使用您的位置权限与电话权限");
							}

						}
					});
		}
	}
	 /**
     * 跳转到...
     */
	 // 要申请的权限
	 private String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private void redirectTo(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			// 检查该权限是否已经获取
			int i = ContextCompat.checkSelfPermission(this, permissions[0]);
			// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
			if (i != PackageManager.PERMISSION_GRANTED) {
				// 如果没有授予该权限，就去提示用户请求
				showDialogTipUserRequestPermission();
			}
		}
         //判断用户是否登录
		boolean isFirstUse = getSharedPreferences("UserMessage", MODE_PRIVATE).getBoolean("isFirstUse", true);
		if (SystemHelper.isConnected(LoadingActivity.this)) {
			if(isFirstUse){//有网络
				//第一次
				Intent intent = new Intent(this , GuideActivity.class);
				startActivity(intent);
				finish();
			}else{
				enterMainActivity();
			}
		}else{
			if(isFirstUse){
				UIUtils.showToast("请检查网络");
				//第一次
				Intent intent = new Intent(this , GuideActivity.class);
				startActivity(intent);
				finish();
			}else{
				if (MakerApplication.instance.getLoginState().equals(MakerApplication.LOGIN)){
					startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
					LoadingActivity.this.finish();
				}else {
					startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
					LoadingActivity.this.finish();
				}
			}
		}
    }
	// 提示用户该请求权限的弹出框
	private void showDialogTipUserRequestPermission() {

		new AlertDialog.Builder(this)
				.setTitle(alertTitle)
				.setMessage(getResources().getString(R.string.app_name) + "需要使用存储权限，您是否允许？" + getResources().getString(R.string.app_name))
				.setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startRequestPermission();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setCancelable(false).show();
	}

	// 开始提交请求权限
	private void startRequestPermission() {
		ActivityCompat.requestPermissions(this, permissions, 321);
	}

	// 用户权限 申请 的回调方法
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 321) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
					// 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
					boolean b = shouldShowRequestPermissionRationale(permissions[0]);
					if (!b) {
						// 用户还是想用我的 APP 的
						// 提示用户去应用设置界面手动开启权限
						showDialogTipUserGoToAppSettting();
					} else
						finish();
				} else {
					startApp();
					Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	// 提示用户去应用设置界面手动开启权限
	String alertTitle = "存储权限申请";
	private void showDialogTipUserGoToAppSettting() {
		dialog = new AlertDialog.Builder(this)
				.setTitle(alertTitle)
				.setMessage("请在-应用设置-权限-中，允许" + getResources().getString(R.string.app_name) + "使用存储权限来保存用户数据")
				.setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 跳转到应用设置界面
						goToAppSetting();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setCancelable(false).show();
	}

	// 跳转到当前应用的设置界面
	private void goToAppSetting() {
		Intent intent = new Intent();

		intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", getPackageName(), null);
		intent.setData(uri);

		startActivityForResult(intent, 123);
	}
	private AlertDialog dialog;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 123) {
			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				// 检查该权限是否已经获取
				int i = ContextCompat.checkSelfPermission(this, permissions[0]);
				// 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
				if (i != PackageManager.PERMISSION_GRANTED) {
					// 提示用户应该去应用设置界面手动开启权限
					showDialogTipUserGoToAppSettting();
				} else {
					if (dialog != null && dialog.isShowing()) {
						dialog.dismiss();
					}
					startApp();
					Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
