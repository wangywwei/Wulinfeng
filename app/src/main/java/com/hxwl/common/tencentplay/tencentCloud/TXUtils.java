package com.hxwl.common.tencentplay.tencentCloud;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;

import java.util.Formatter;
import java.util.Locale;

/**
 * 腾讯播放器的工具类
 * Created by Allen on 2017/8/23.
 */

public class TXUtils {
    public static String stringForTime(int timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
    //是否wifi连接
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
    //获取当前activity
    public static Activity scanForActivity(Context context) {
        if (context == null) return null;

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }
    //获取Activity对象
    public static AppCompatActivity getAppCompActivity(Context context) {
        if (context == null) return null;
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return getAppCompActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    //保存进度
    //TODO  要修改 sp的名称
    public static void saveProgress(Context context, String url, int progress) {
        if (!TXCloudViewViewA.SAVE_PROGRESS) return;
        SharedPreferences spn = context.getSharedPreferences("JCVD_PROGRESS",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spn.edit();
        editor.putInt(url, progress);
        editor.apply();
    }
    //得到播放进度
    public static int getSavedProgress(Context context, String url) {
        if (!TXCloudViewViewA.SAVE_PROGRESS) return 0;
        SharedPreferences spn;
        spn = context.getSharedPreferences("JCVD_PROGRESS",
                Context.MODE_PRIVATE);
        return spn.getInt(url, 0);
    }

    //清楚播放进度
    public static void clearSavedProgress(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            SharedPreferences spn = context.getSharedPreferences("JCVD_PROGRESS",
                    Context.MODE_PRIVATE);
            spn.edit().clear().apply();
        } else {
            SharedPreferences spn = context.getSharedPreferences("JCVD_PROGRESS",
                    Context.MODE_PRIVATE);
            spn.edit().putInt(url, 0).apply();
        }
    }
}
