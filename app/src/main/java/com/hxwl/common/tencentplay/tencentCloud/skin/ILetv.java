package com.hxwl.common.tencentplay.tencentCloud.skin;

/**
 * 功能:弹幕实现回调
 * 作者：zjn on 2017/3/3 15:18
 */

public interface ILetv {

    //是否开启弹幕 true 为打开 false 为关闭
    void isOpenDanmu(boolean isOpen);
    //是否开启互动 true 为打开 false 为关闭
    void isOpenHudong(boolean isOpen);
}
