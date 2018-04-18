package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/13.
 */
public class Msg_info {
    private String zhu_id;

    private String uid;

    private String msg;

    private String type;

    private String time;

    private String nickname;

    private String head_url;

    public String getZhu_id() {
        return zhu_id;
    }

    public void setZhu_id(String zhu_id) {
        this.zhu_id = zhu_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    @Override
    public String toString() {
        return "Msg_info{" +
                "zhu_id='" + zhu_id + '\'' +
                ", uid='" + uid + '\'' +
                ", msg='" + msg + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_url='" + head_url + '\'' +
                '}';
    }
}
