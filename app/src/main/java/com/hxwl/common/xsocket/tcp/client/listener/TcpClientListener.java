package com.hxwl.common.xsocket.tcp.client.listener;


import com.hxwl.common.xsocket.tcp.client.XTcpClient;
import com.hxwl.common.xsocket.tcp.client.bean.TcpMsg;

/**
 */
public interface TcpClientListener {
    void onConnected(XTcpClient client);

    void onSended(XTcpClient client, TcpMsg tcpMsg);

    void onDisconnected(XTcpClient client, String msg, Exception e);

    void onReceive(XTcpClient client, TcpMsg tcpMsg);

    void onValidationFail(XTcpClient client, TcpMsg tcpMsg);
}
