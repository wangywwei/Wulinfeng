package com.hxwl.common.xsocket.udp.client.bean;

import com.hxwl.common.xsocket.tcp.client.bean.TargetInfo;
import com.hxwl.common.xsocket.tcp.client.bean.TcpMsg;

/**
 */
public class UdpMsg extends TcpMsg {

    public UdpMsg(byte[] data, TargetInfo target, MsgType type) {
        super(data, target, type);
    }

    public UdpMsg(String data, TargetInfo target, MsgType type) {
        super(data, target, type);
    }

    public UdpMsg(int id) {
        super(id);
    }
}
