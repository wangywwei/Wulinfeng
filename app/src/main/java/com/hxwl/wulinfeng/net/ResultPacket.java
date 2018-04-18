package com.hxwl.wulinfeng.net;

import java.io.Serializable;

/**
 * Created by Allen on 2017/5/27.
 * 后台返回数据最外层
 */

public class ResultPacket implements Serializable{
    private String code;
    private String message;
    private String status ;//表示是否成功返回  empty
    private String msg ;//返回消息“密码不正确”
    private String data ;//数据
    private String insertid ;//插入id
    private String zhuId ;//数据
    private String param ;
    private int lastId ;//系统消息 红点比对
    private String html ;
    private String lastid ;//群聊专用
    private String num ;
    private String time ;
    private int newCount ;

    public String getData() {
        return data;
    }

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setLastid(String lastid) {
        this.lastid = lastid;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public  int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public String getInsertid() {
        return insertid;
    }

    public void setInsertid(String insertid) {
        this.insertid = insertid;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getZhuId() {
        return zhuId;
    }

    public void setZhuId(String zhuId) {
        this.zhuId = zhuId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String setOnScrollListenergetData() {
        return data ;
    }

    public void setData(String data) {
        this.data = data;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultPacket{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getLastid() {
        return lastid;
    }
}
