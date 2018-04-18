package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/8.
 */
public class ZanBean {
    private String uid ;
    private String nickname ;
    private String head_url ;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
        return "ZanBean{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_url='" + head_url + '\'' +
                '}';
    }
}
