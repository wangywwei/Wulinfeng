package com.hxwl.wulinfeng.bean;

import java.io.Serializable;

/**
 * Created by Allen on 2017/6/8.
 */
public class HuiFuBean implements Serializable{
    private String id;

    private String type;

    private String zhu_id;

    private String from_uid;

    private String to_uid;

    private String msg;

    private String time;

    private String from_nickname;

    private String to_nickname;

    public String getTo_nickname() {
        return to_nickname;
    }

    public void setTo_nickname(String to_nickname) {
        this.to_nickname = to_nickname;
    }

    private String from_head_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZhu_id() {
        return zhu_id;
    }

    public void setZhu_id(String zhu_id) {
        this.zhu_id = zhu_id;
    }

    public String getFrom_uid() {
        return from_uid;
    }

    public void setFrom_uid(String from_uid) {
        this.from_uid = from_uid;
    }

    public String getTo_uid() {
        return to_uid;
    }

    public void setTo_uid(String to_uid) {
        this.to_uid = to_uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom_nickname() {
        return from_nickname;
    }

    public void setFrom_nickname(String from_nickname) {
        this.from_nickname = from_nickname;
    }

    public String getFrom_head_url() {
        return from_head_url;
    }

    public void setFrom_head_url(String from_head_url) {
        this.from_head_url = from_head_url;
    }
    @Override
    public String toString() {
        return "HuiFuBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", zhu_id='" + zhu_id + '\'' +
                ", from_uid='" + from_uid + '\'' +
                ", to_uid='" + to_uid + '\'' +
                ", msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", from_nickname='" + from_nickname + '\'' +
                ", from_head_url='" + from_head_url + '\'' +
                '}';
    }
}
