package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/30.
 */
public class ChatBean {
    private String id;
    private String saicheng_id;
    private String uid;
    private String msg;
    private String time;
    private String is_show;
    private String nickname;
    private String head_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaicheng_id() {
        return saicheng_id;
    }

    public void setSaicheng_id(String saicheng_id) {
        this.saicheng_id = saicheng_id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
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
        return "ChatBean{" +
                "id='" + id + '\'' +
                ", saicheng_id='" + saicheng_id + '\'' +
                ", uid='" + uid + '\'' +
                ", msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", is_show='" + is_show + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_url='" + head_url + '\'' +
                '}';
    }
}
