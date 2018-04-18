package com.hxwl.common.utils;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.LiveDetailActivity;
import com.hxwl.wulinfeng.bean.SaichengBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.ToastUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static android.content.Context.NOTIFICATION_SERVICE;

public class HncNotifier {

	private static HncNotifier instance;

	private NotificationManager notificationManager = null;
	private String appname = null;

	Ringtone ringtone = null;
	protected long lastNotifiyTime;
	protected AudioManager audioManager;
	protected Vibrator vibrator;

	private static final int notifyId_msg = 0;
	private static final int notifyId_tiren = 10;
	private static final int notifyId_itemnote = 1;
	private static final int notifyId_mult= 2;
	

	/** * 单一实例 */
	public static HncNotifier getHncNotifier() {
		if (instance == null) {
			instance = new HncNotifier();
		}
		return instance;
	}

	public HncNotifier() {
		Application appContext = MakerApplication.instance;
		notificationManager = (NotificationManager) appContext
				.getSystemService(NOTIFICATION_SERVICE);
		PackageManager packageManager = appContext.getPackageManager();
		appname = (String) packageManager.getApplicationLabel(appContext
				.getApplicationInfo());

		audioManager = (AudioManager) appContext
				.getSystemService(Context.AUDIO_SERVICE);
		vibrator = (Vibrator) appContext
				.getSystemService(Context.VIBRATOR_SERVICE);
	}
	/**
	 * 点击进入相应的心得界面
	 */
	static Timer timer = null;
	public void shownotifyItemNote(final Activity activity , final SaichengBean bean) {
		final MakerApplication appContext = MakerApplication.instance;
		String delay = bean.getStart_time();
		long time = Long.parseLong(delay);
		final long notiTime = Long.parseLong(delay)*1000 - 600000;
//        long time = System.currentTimeMillis()+5000;

		Date date = new Date(notiTime);

		if (null == timer) {
			timer = new Timer();
		}
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				NotificationManager mn = (NotificationManager) appContext
						.getSystemService(NOTIFICATION_SERVICE);
//				Notification.Builder builder = new Notification.Builder(
//						appContext);

				NotificationCompat.Builder builder = new NotificationCompat.Builder(
						appContext).setSmallIcon(appContext.getApplicationInfo().icon)
						.setWhen(System.currentTimeMillis()).setAutoCancel(true);

				Intent notificationIntent = new Intent(appContext,
						LiveDetailActivity.class);// 点击跳转位置
				if(TextUtils.isEmpty(bean.getSaicheng_id())){
					notificationIntent.putExtra("saichengId", bean.getId());
				}else{
					notificationIntent.putExtra("saichengId", bean.getSaicheng_id());
				}
				notificationIntent.putExtra("saishiId", bean.getSaishi_id());
				notificationIntent.putExtra("name", bean.getTitle());

				PendingIntent contentIntent = PendingIntent.getActivity(
						appContext, new Random().nextInt(1000), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				builder.setContentIntent(contentIntent);
				builder.setSmallIcon(R.drawable.wlflogo_icon);// 设置通知图标
				builder.setTicker("武林风"); // 测试通知栏标题
				builder.setContentText(bean.getName()); // 下拉通知啦内容
				builder.setContentTitle(bean.getTitle());// 下拉通知栏标题
				builder.setAutoCancel(true);// 点击弹出的通知后,让通知将自动取消
				builder.setVibrate(new long[] {1000}); // 震动需要真机测试-延迟0秒震动2秒延迟1秒震动4秒
				// builder.setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI,
				// "5"));//获取Android多媒体库内的铃声
				// builder.setSound(Uri.parse("file:///sdcard/xx/xx.mp3"))
				// ;//自定义铃声
				builder.setDefaults(Notification.DEFAULT_ALL);// 设置使用系统默认声音
				// builder.addAction("图标", title, intent); //此处可设置点击后 打开某个页面
				Notification notification = builder.build();
//                notification.flags = notification.FLAG_INSISTENT;// 声音无限循环
				mn.notify(UUID.randomUUID().toString(), 1, notification);
				Looper.prepare();
				ToastUtils.diyToast(activity,"直播马上开始了");
				Looper.loop();
				remindComplete(activity ,bean);
			}
		}, date);

//		String text = "" ;
//		if (StringUtils.isBlank(itemNotes.getContent())) {//如果是空
//			text = "[" + username + "]写：[ 图片 ]";
//		}else {
//			text = "[" + username + "]写：" + itemNotes.getContent();
//		}
//
//		// create and send notificaiton
//		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
//				appContext).setSmallIcon(appContext.getApplicationInfo().icon)
//				.setWhen(System.currentTimeMillis()).setAutoCancel(true);
//
//		String packageName = appContext.getApplicationInfo().packageName;
//		Intent msgIntent = appContext.getPackageManager()
//				.getLaunchIntentForPackage(packageName);
//		/**
//		 * 这里直接跳转
//		 */
//		msgIntent = new Intent(appContext, ItemDetailForExpandFragment.class);
//		msgIntent.putExtra("item_id", itemNotes.getItem_id());
//		msgIntent.putExtra("Sharing_id", itemNotes.getShareid());
//		PendingIntent pendingIntent = PendingIntent.getActivity(appContext,
//				new Random().nextInt(1000), msgIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//////		Intent msgIntent = appContext.getPackageManager()
//////				.getLaunchIntentForPackage(packageName);
//////		/**
//////		 * 这里直接跳转
//////		 */
//////			msgIntent = new Intent(appContext, ItemDetailForExpandFragment.class);
//////			msgIntent.putExtra("item_id", itemNotes.getItem_id());
//////			msgIntent.putExtra("Sharing_id", itemNotes.getShareid());
////			//设置点击通知栏的动作为启动另外一个广播
////	    Intent broadcastIntent = new Intent(appContext, NotificationReceiver.class);
////	    broadcastIntent.putExtra("item_id", itemNotes.getItem_id());
////	    broadcastIntent.putExtra("Sharing_id", itemNotes.getShareid());
//////		PendingIntent pendingIntent = PendingIntent.getActivity(appContext,
//////				notifyId_itemnote, msgIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
////		PendingIntent pendingIntent = PendingIntent.getBroadcast(appContext,
////				notifyId_itemnote, broadcastIntent, PendingIntent.FLAG_ONE_SHOT);
//		mBuilder.setContentTitle("[新心得]   " + appname);
//		mBuilder.setTicker(text);
//		mBuilder.setContentText(text);
//		mBuilder.setContentIntent(pendingIntent);
//		// mBuilder.setNumber(notificationNum);
//		Notification notification = mBuilder.build();
//		notificationManager.notify(UUID.randomUUID().toString(), notifyId_itemnote, notification);
	}

	private void remindComplete(final Activity activity, final SaichengBean bean) {
		JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
				activity, true, new ExcuteJSONObjectUpdate() {
			@Override
			public void excute(ResultPacket result) {
				if (result != null && result.getStatus().equals("ok")) {
					SPUtils.clearYuYueInfo(activity,bean);
				}else if(result != null && result.getStatus().equals("empty")){

				}else{

				}
			}
		});
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("saichengId", bean.getSaicheng_id());
		params.put("uid", MakerApplication.instance.getUid());
		params.put("method", "Saicheng/yuyueTanchuTongzhi");
		tasker.execute(params);

//		Map<String , Object> map = new HashMap<>();
//		map.put("saichengId" ,bean.getId());
//		map.put("uid", MakerApplication.instance().getUid());
//		try {
//			AppClient.okhttp_post_Asyn(NetUrlUtils.saicheng_finshtongzhi, map, new ICallback() {
//				@Override
//				public void error(IOException e) {
//				}
//				@Override
//				public void success(ResultPacket result) {
//					if(result.getStatus()!=null && result.getStatus().equals("ok")){
//						SPUtils.clearYuYueInfo(activity,bean);
////						activity.runOnUiThread(new Runnable() {
////							@Override
////							public void run() {
////								SPUtils.clearYuYueInfo(activity,bean);
////							}
////						});
//					}else{
//
//					}
//				}
//			});
//		} catch (Throwable throwable) {
//			throwable.printStackTrace();
//		}
	}
}
