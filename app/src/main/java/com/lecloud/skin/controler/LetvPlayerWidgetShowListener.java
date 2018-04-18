package com.lecloud.skin.controler;

/**
 * 功能:乐视播放器上层控件展示监听
 * 作者：zjn on 2017/2/28 15:52
 */
public interface LetvPlayerWidgetShowListener {
    //直播互动按钮的展示与隐藏
    void liveInteractionWidgetShow();
    void liveInteractionWidgetHide();
    //弹幕按钮的展示与隐藏
    void danMukuWidgetShow();
    void danMukuWidgetHide();
    //弹幕输入框按钮的展示与隐藏
    void danMuEditWidgetShow();
    void danMuEditWidgetHide();
    //分享按钮的展示与隐藏
    void vedioShareWidgetShow();
    void vedioShareWidgetHide();
}
