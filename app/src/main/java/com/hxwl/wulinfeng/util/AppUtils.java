package com.hxwl.wulinfeng.util;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.hxwl.wulinfeng.MainActivity;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.LiveDetailActivity;
import com.hxwl.wulinfeng.activity.TuJiDetailsActivity;
import com.hxwl.wulinfeng.activity.VideoDetailActivity;
import com.hxwl.wulinfeng.activity.WebViewCurrencyActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.jaeger.library.StatusBarUtil;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * 跟App相关的辅助类
 */
public class AppUtils {


    private static String jinbi;

    private AppUtils()
    {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    public static void setTitle(Activity activity){
        StatusBarUtil.setColor(activity,activity.getResources().getColor(R.color.rgb_BA2B2C),0);
    }
    private static Intent intent;
    private static String[] paramshuzu;
    private static String userId;
    private static String loginkey;


    public static void doTiaozhuan(String url, Activity m_Context) {
        try {
//            userId = (String) SPUtils.get(m_Context.getApplicationContext(), Constants.USER_ID, "-1");
//            loginkey = (String) SPUtils.get(m_Context.getApplicationContext(), Constants.USER_LOGIN_KEY, "-1");
            if (url != null) {
                if (url.contains("http")) { //h5页面
                    Intent intent = new Intent(m_Context, WebViewCurrencyActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("title", "武林风");
                    m_Context.startActivity(intent);
                } else { // 1.有参数：url包含？字符   2.无参数：不包含？字符
                    if (url.contains("?")) {
                        String[] urlstr = url.split("\\?");
                        String classname = urlstr[0];
                        String paramsStr = urlstr[1];

                        if (classname != null && (classname.toLowerCase()).equals("wlf_zixunInfo".toLowerCase())) { //资讯详情页
                            intent = new Intent(m_Context, ZiXunDetailsActivity.class);
                            if (paramsStr != null && paramsStr.contains("&")) { //多个参数
                                paramshuzu = paramsStr.split("&");
                                for (int i = 0; i < paramshuzu.length; i++) {
                                    String[] cc = paramshuzu[i].split("=");
                                    String key = cc[0];
                                    String value = cc[1];
                                    intent.putExtra(key, value);
                                }
                            } else {
                                String[] cc = paramsStr.split("=");
                                String key = cc[0];
                                String value = cc[1];
                                intent.putExtra(key, value);
                            }
                            m_Context.startActivity(intent);
                        } else if (classname != null && (classname.toLowerCase()).equals("wlf_videoInfo".toLowerCase())) { //视频详情页
                            intent = new Intent(m_Context, VideoDetailActivity.class);
                            if (paramsStr != null && paramsStr.contains("&")) { //多个参数
                                paramshuzu = paramsStr.split("&");
                                for (int i = 0; i < paramshuzu.length; i++) {
                                    String[] cc = paramshuzu[i].split("=");
                                    String key = cc[0];
                                    String value = cc[1];
                                    intent.putExtra(key, value);
                                }
                            } else {
                                String[] cc = paramsStr.split("=");
                                String key = cc[0];
                                String value = cc[1];
                                intent.putExtra(key, value);
                            }
                            m_Context.startActivity(intent);
                        } else if (classname != null && (classname.toLowerCase()).equals("wlf_tujiInfo".toLowerCase())) { //图集详情页
                            intent = new Intent(m_Context, TuJiDetailsActivity.class);
                            if (paramsStr != null && paramsStr.contains("&")) { //多个参数
                                paramshuzu = paramsStr.split("&");
                                for (int i = 0; i < paramshuzu.length; i++) {
                                    String[] cc = paramshuzu[i].split("=");
                                    String key = cc[0];
                                    String value = cc[1];
                                    intent.putExtra(key, value);
                                }
                            } else {
                                String[] cc = paramsStr.split("=");
                                String key = cc[0];
                                String value = cc[1];
                                intent.putExtra(key, value);
                            }
                            m_Context.startActivity(intent);
                        } else if (classname != null && (classname.toLowerCase()).equals("wlf_zhiboInfo".toLowerCase())) { //比赛直播详情页
                            intent = new Intent(m_Context, LiveDetailActivity.class);
                            if (paramsStr != null && paramsStr.contains("&")) { //多个参数
                                paramshuzu = paramsStr.split("&");
                                for (int i = 0; i < paramshuzu.length; i++) {
                                    String[] cc = paramshuzu[i].split("=");
                                    String key = cc[0];
                                    String value = cc[1];
                                    intent.putExtra(key, value);
                                }
                            } else {
                                String[] cc = paramsStr.split("=");
                                String key = cc[0];
                                String value = cc[1];
                                intent.putExtra(key, value);
                            }
                            m_Context.startActivity(intent);
                        } else if (classname != null && (classname.toLowerCase()).equals("wlf_zhiboVideoInfo".toLowerCase())) { //回顾详情
                            intent = new Intent(m_Context, HuiGuDetailActivity.class);
                            if (paramsStr != null && paramsStr.contains("&")) { //多个参数
                                paramshuzu = paramsStr.split("&");
                                for (int i = 0; i < paramshuzu.length; i++) {
                                    String[] cc = paramshuzu[i].split("=");
                                    String key = cc[0];
                                    String value = cc[1];
                                    intent.putExtra(key, value);
                                }
                            } else {
                                String[] cc = paramsStr.split("=");
                                String key = cc[0];
                                String value = cc[1];
                                intent.putExtra(key, value);
                            }
                            m_Context.startActivity(intent);
                        }else{
                            ToastUtils.showToast(m_Context,"地址错误");
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     *
     * @param activity
     */
    public static void setEvent(Activity activity,String id ,String name){
        StatService.onEvent(activity,id,name,1);
        TCAgent.onEvent(activity,id,name);
    }

    public static void setPageStart(Activity activity,String name){
        StatService.onPageStart(activity, name);
        TCAgent.onPageStart(activity, name);
    }

    public static void setPageEnd(Activity activity,String name){
        StatService.onPageEnd(activity,name);
        TCAgent.onPageEnd(activity,name);
    }

    /**
     * 获得屏幕的宽度
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
		/*		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "0?(13|14|15|18|17)[0-9]{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }
    /**
     * 获得屏幕的高度
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();

        return height;
    }

    /**
     * 获取设备deviceid
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm =  null;
        try {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceid = tm.getDeviceId();
            if(deviceid == null || deviceid.length()==0){
                WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (manager != null) {
                    deviceid = manager.getConnectionInfo().getMacAddress();
                }
            }
            return deviceid;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            tm = null;
        }
        return null;
    }
    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * [获取应用程序版本号信息]
     *
     * @param context
     * @return 当前应用的版本号
     */
    public static int getVersionCode(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return 0;
    }


}
