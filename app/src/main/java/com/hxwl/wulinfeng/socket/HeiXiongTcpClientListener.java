package com.hxwl.wulinfeng.socket;

/**
 * 功能:黑熊tcp客户端监听
 * 作者：zjn on 2017/3/23 12:56
 */

public interface HeiXiongTcpClientListener {
    void onConnected(boolean isConnect);

    void onReceive(String strMsg);

    void onValidationFail(String strFailMsg);
}
