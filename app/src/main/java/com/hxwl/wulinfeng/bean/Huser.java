package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/20.
 */
public class Huser {
    private String nickname;

    private String head_url;

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
        return "Huser{" +
                "nickname='" + nickname + '\'' +
                ", head_url='" + head_url + '\'' +
                '}';
    }
}
