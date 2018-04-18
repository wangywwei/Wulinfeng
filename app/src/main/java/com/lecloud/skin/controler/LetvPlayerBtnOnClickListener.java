package com.lecloud.skin.controler;

/**
 * 功能:乐视播放器上那一悬浮层按钮的点击事件
 * 作者：zjn on 2017/2/28 15:38
 */

public interface LetvPlayerBtnOnClickListener {
    /** 互动按钮 true 为打开，false 为关闭 */
    void openOrCloseInteraction(boolean isOpen);
    /** 弹幕开关 true 为打开，false 为关闭 */
    void openOrCloseDanmaku(boolean isOpen);
    /** 分享 */
    void letvShare();
    /** 码率设置，高清，超清，蓝光 */
    void rateTypeSet();
}
