package com.hxwl.common.tencentplay.tencentCloud;

/**
 * 腾讯云播放器管理类
 * Created by Allen on 2017/8/23.
 */

public class TXCloudPlayerManager {
    //创建两个TXCloudVideoView对象 方便全屏
    public static TXCloudViewViewA FIRST_FLOOR_JCVD;
    public static TXCloudViewViewA SECOND_FLOOR_JCVD;
    //创建get/set方法
    public static void setFirstFloor(TXCloudViewViewA jcVideoPlayer) {
        FIRST_FLOOR_JCVD = jcVideoPlayer;
    }

    public static void setSecondFloor(TXCloudViewViewA jcVideoPlayer) {
        SECOND_FLOOR_JCVD = jcVideoPlayer;
    }

    public static TXCloudViewViewA getFirstFloor() {
        return FIRST_FLOOR_JCVD;
    }

    public static TXCloudViewViewA getSecondFloor() {
        return SECOND_FLOOR_JCVD;
    }
    //获取当前的 TXCloudVideoView对象
    public static TXCloudViewViewA getCurrentJcvd() {
        if (getSecondFloor() != null) {
            return getSecondFloor();
        }
        return getFirstFloor();
    }
    //释放掉两个对象
    public static void completeAll() {
        if (SECOND_FLOOR_JCVD != null) {
            SECOND_FLOOR_JCVD.onCompletion();
            SECOND_FLOOR_JCVD = null;
        }
        if (FIRST_FLOOR_JCVD != null) {
            FIRST_FLOOR_JCVD.onCompletion();
            FIRST_FLOOR_JCVD = null;
        }
    }

}
