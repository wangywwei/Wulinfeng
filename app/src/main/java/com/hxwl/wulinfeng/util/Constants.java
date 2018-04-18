package com.hxwl.wulinfeng.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;

import com.baidu.mobstat.CooperService;
import com.hxwl.common.utils.DeviceUtils;


/**
 * 常量类
 */
public class Constants {
    /**
     * SP 本地共享参数相关
     */
    public static final String LOGIN_STATE = "login_state";//登陆状态
    public static final String USER_ID = "id";//用户id
    public static final String USER_LOGIN_KEY = "user_login_key";//登录的标记
    public static final String biaoshi = "wlf";

    //APP_ID
    public static final String APP_ID="wx3584771d5848a5f7";
    public static final String APPSECRET="6fd1071fb98dbd22e5b97bb2bdc57f70";
    //关联微信和微信登录的区别标志
    public static String WX_biaoshi="1";
    //用户第一次登录的标记
    public static String FIRST_LOGIN="firstLogin";
    //用户的信息
    public static String USER_ICON="headImg";
    public static String USER_Name="nickName";
    public static String USER_PHONE="phone";
    public static String USER_PWD="USER_pwd";//真正的密码
    public static String USER_PASSWORD="USER_password";//标记是不是有密码
    public static String HERO_MONEY="HERO_money";
    public static String IS_CHECK_VERSION="isCheckVersion";//是否提醒升级
    //活动弹窗id
    public static String HUODONG_DIALOG_ID="huodong_id";
    //内存卡路径
    public static final String PUB_PATH= Environment.getExternalStorageDirectory().getAbsolutePath();
    //头像的存储路径
    @SuppressLint("SdCardPath")
    public static String USER_PHOTO_PATH=PUB_PATH+"/black/photo_temp/";
    //头像的名称
    public static final String USER_PHOTO_NAME="user_photo.jpg";
    //热聊的远程IP
    //线下
//    public static final String REMOTE_IP = "192.168.10.8";
    //线上
    public static final String REMOTE_IP = "test.heixiongboji.com";
//    public static final String REMOTE_IP = "heixiongboji.com";//发版正式环境用这个 ！！！！
//    public static final String REMOTE_IP = "apiwlf.heixiongboji.com";

    //socket端口号
    public static final String REMOTE_PORT =  "9500";
    //图片后缀
    public static final String BITMAP_SUFFIX =  ".hxbj";
    //花椒常量信息
    public static final String HUAJIAO_App_ID ="14325";
    public static final String HUAJIAO_Secret_Key  ="q3etPnlCe7Y4ZmXfwobmV5iJUOlQU6r5";
    public static final String HUAJIAO_channelID ="heixiongapp";
    public static final String HUAJIAO_ESKey ="47684B54684A3459A453A65717A52514";
    public static final String HUAJIAO_TAG ="heixiongbojimoren";
    //黑熊logo图片
    public static final String HEIXIONG_LOGO = "https://api.heixiongboji.com/heixionglogo.png";

    /**
     * 手机参数上传 用与系统封装
     * @param ctx
     * @return
     */
    public static String getPhoneParams(Context ctx){
        return "手机型号:" + DeviceUtils.getBrand() + DeviceUtils.getDeviceName() +",Android版本:"+DeviceUtils.getRelease()+",武林风版本:" + AppUtils.getVersionName(ctx);
    }

    /**
     * 手机渠道名称
     * @param ctx
     * @return
     */
    public static String getChannelName(Context ctx){
        CooperService service = new CooperService();
        String channelName = service.getAppChannel(ctx).trim();
        return channelName ;
    }
}
