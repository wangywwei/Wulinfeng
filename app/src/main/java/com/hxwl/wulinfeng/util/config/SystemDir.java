package com.hxwl.wulinfeng.util.config;

import android.os.Environment;

/**
 * 功能:sd卡目录
 */

public class SystemDir {
    /** SD卡根目录 */
    public static final String ROOTPATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    /**不随程序卸载的文件夹 */
    public static final String DIR_LONG_CACHE = ROOTPATH + "/wulinfeng";

    /** 更新文件 */
    public static final String DIR_UPDATE_APK = DIR_LONG_CACHE+"/update_file";
    /** 错误日志 */
    public static final String DIR_ERROR_MSG = DIR_LONG_CACHE+"/error_file";
    /** 缓存图片 */
    public static final String DIR_IMAGE = DIR_LONG_CACHE+"/image_file";
    /** 下载图片 */
    public static final String DIR_DOWN_IMAGE = DIR_LONG_CACHE+"/downimg_file";
    /** 花椒缓存 */
    public static final String DIR_HUAJIAO = DIR_LONG_CACHE+"/huajiao_file";
}
