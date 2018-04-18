package com.hxwl.wulinfeng.socket;


import com.hxwl.common.utils.ContextHolder;
import com.hxwl.common.xsocket.tcp.client.TcpConnConfig;
import com.hxwl.common.xsocket.tcp.client.XTcpClient;
import com.hxwl.common.xsocket.tcp.client.bean.TargetInfo;
import com.hxwl.common.xsocket.tcp.client.bean.TcpMsg;
import com.hxwl.common.xsocket.tcp.client.helper.stickpackage.AbsStickPackageHelper;
import com.hxwl.common.xsocket.tcp.client.helper.stickpackage.HeiXiongHotChatStickPackageHelper;
import com.hxwl.common.xsocket.tcp.client.listener.TcpClientListener;
import com.hxwl.common.xsocket.utils.StringValidationUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.ToastUtils;

/**
 * 功能:黑熊socket连接客户端
 */
public class HeiXiongSocketClient implements TcpClientListener {
    final HeiXiongSocketClient self = this;

    private HeiXiongTcpClientListener heiXiongTcpClientListener;

    public HeiXiongSocketClient setHeiXiongTcpClientListener(HeiXiongTcpClientListener heiXiongTcpClientListener) {
        this.heiXiongTcpClientListener = heiXiongTcpClientListener;
        return self;
    }

    /* Public Methods */
    public void connect() {
        self.getLocalSocketClient().connect();
    }

    /* Properties */
    private XTcpClient xTcpClient;

    public XTcpClient getxTcpClient() {
        return xTcpClient;
    }

    public void setxTcpClient(XTcpClient xTcpClient) {
        this.xTcpClient = xTcpClient;
    }

    //初始化
    private XTcpClient getLocalSocketClient(){
        try{
            if (this.xTcpClient == null) {
                String str = "n";
                byte[] tail = str.getBytes("UTF-8");
                //粘包参数设置
                AbsStickPackageHelper stickHelper = new HeiXiongHotChatStickPackageHelper("\\r\\n".getBytes("UTF-8"));
                if (stickHelper == null) {
                    ToastUtils.showToast(ContextHolder.getContext(),"参数错误!");
                    return null;
                }
                String adress = Constants.REMOTE_IP+":"+Constants.REMOTE_PORT;
                String[] adresses = adress.split(":");
//                if (adresses.length == 2 && StringValidationUtils.validateRegex(adresses[0], StringValidationUtils.RegexIP)
//                        && StringValidationUtils.validateRegex(adresses[1], StringValidationUtils.RegexPort)) {
                if (adresses.length == 2 ) {
                    TargetInfo targetInfo = new TargetInfo(adresses[0], Integer.parseInt(adresses[1]));
                    xTcpClient = XTcpClient.getTcpClient(targetInfo);
                    xTcpClient.addTcpClientListener(this);
                    xTcpClient = XTcpClient.getTcpClient(targetInfo);
                    xTcpClient.config(new TcpConnConfig.Builder()
                            .setStickPackageHelper(stickHelper)//粘包
                            .setIsReconnect(true)//是否断开重连
                            .create());
                } else {
                    ToastUtils.showToast(ContextHolder.getContext(),"服务器地址异常!");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return xTcpClient;
    }

    //发送消息，按照数据接口传输
    public void sendMsg(String strMsg){
        if(xTcpClient != null){
            xTcpClient.sendMsg(strMsg);
        }
    }

    @Override
    public void onConnected(XTcpClient client) {
        heiXiongTcpClientListener.onConnected(client.isConnected());
    }

    @Override
    public void onSended(XTcpClient client, TcpMsg tcpMsg) {

    }

    @Override
    public void onDisconnected(XTcpClient client, String msg, Exception e) {
    }

    @Override
    public void onReceive(XTcpClient client, TcpMsg tcpMsg) {
        try{
            heiXiongTcpClientListener.onReceive(tcpMsg.getSourceDataString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onValidationFail(XTcpClient client, TcpMsg tcpMsg) {
        heiXiongTcpClientListener.onValidationFail(tcpMsg.getSourceDataString());
    }

    //断开tcp
    public void onDestory(){
        if (xTcpClient != null) {
            xTcpClient.removeTcpClientListener(this);
            xTcpClient.disconnect();//activity销毁时断开tcp连接
        }
    }

    //是否连接成功
    public boolean isConnected(){
        if(xTcpClient != null){
            return xTcpClient.isConnected();
        }
        return false;
    }
}