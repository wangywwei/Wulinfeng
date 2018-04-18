//package com.hxwl.wulinfeng.service;
//
//import android.app.Activity;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.database.DatabaseErrorHandler;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import com.hxwl.wulinfeng.MainActivity;
//import com.hxwl.wulinfeng.R;
//import com.hxwl.wulinfeng.activity.HomeActivity;
//import com.hxwl.wulinfeng.activity.MyMartialActivity;
//import com.hxwl.wulinfeng.util.TimeUtiles;
//import com.hxwl.wulinfeng.util.ToastUtils;
//
//import java.util.Date;
//import java.util.Random;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.UUID;
//
//import static android.R.attr.delay;
//
///**
// * Created by Allen on 2017/7/3.
// * 预约提醒
// */
//
//public class AlarmService extends Service{
//    @Nullable
//    static Timer timer = null;
//
//    // 清除通知
//    public static void cleanAllNotification(Activity activity) {
//        NotificationManager mn = (NotificationManager) activity.getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
//        mn.cancelAll();
//        if (timer != null) {
//            timer.cancel();
//            timer = null;
//        }
//    }
//
//    // 添加通知
//    public static void addNotification(Context activity , String delayTime, String tickerText,
//                                       String contentTitle, String contentText) {
//
//    }
//
//    @Override
//    public void onCreate() {
//        Log.e("addNotification", "===========create=======");
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    private static final int notifyId_msg = 0;
//    @Override
//    public int onStartCommand(final Intent intent, int flags, int startId) {
//        String delay = intent.getStringExtra("delayTime");
//        long time = Long.parseLong(delay);
//        final long notiTime = Long.parseLong(delay)*1000 - 10000;
////        long time = System.currentTimeMillis()+5000;
//
//        Date date = new Date(notiTime);
//
//        if (null == timer) {
//            timer = new Timer();
//        }
//        timer.schedule(new TimerTask() {
//
//            @Override
//            public void run() {
//                NotificationManager mn = (NotificationManager) AlarmService.this
//                        .getSystemService(NOTIFICATION_SERVICE);
//                Notification.Builder builder = new Notification.Builder(
//                        AlarmService.this);
//
//                Intent notificationIntent = new Intent(AlarmService.this,
//                        MainActivity.class);// 点击跳转位置
//                PendingIntent contentIntent = PendingIntent.getActivity(
//                        AlarmService.this, new Random().nextInt(1000), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                builder.setContentIntent(contentIntent);
//                builder.setSmallIcon(R.drawable.wlflogo_icon);// 设置通知图标
//                builder.setTicker(intent.getStringExtra("tickerText")); // 测试通知栏标题
//                builder.setContentText(intent.getStringExtra("contentText")); // 下拉通知啦内容
//                builder.setContentTitle(intent.getStringExtra("contentTitle"));// 下拉通知栏标题
//                builder.setAutoCancel(true);// 点击弹出的通知后,让通知将自动取消
//                builder.setVibrate(new long[] {1000}); // 震动需要真机测试-延迟0秒震动2秒延迟1秒震动4秒
//                // builder.setSound(Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI,
//                // "5"));//获取Android多媒体库内的铃声
//                // builder.setSound(Uri.parse("file:///sdcard/xx/xx.mp3"))
//                // ;//自定义铃声
//                builder.setDefaults(Notification.DEFAULT_ALL);// 设置使用系统默认声音
//                // builder.addAction("图标", title, intent); //此处可设置点击后 打开某个页面
//                Notification notification = builder.build();
////                notification.flags = notification.FLAG_INSISTENT;// 声音无限循环
//                mn.notify(UUID.randomUUID().toString(), 1, notification);
//            }
//        }, date);
////        }, delay, period);
//
//        return START_NOT_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        Log.e("addNotification", "===========destroy=======");
//        super.onDestroy();
//    }
//}
