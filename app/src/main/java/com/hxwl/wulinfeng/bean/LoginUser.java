package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/19.
 * 登陆者信息
 */
public class LoginUser {
        private String unionid ;
        private String password ;//0表示有密码 1 表示没有密码
        private String pwd ;//真正的密码
        private String is_subscribe ;
        private String nickname ;
        private String subscribe_time ;
        private String mobile ;
        private String uid ;
        private String loginKey ;
        private String wechat_nickname ;
        private String head_url ;
        private String avastar ;
        private String openid ;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(String subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getWechat_nickname() {
        return wechat_nickname;
    }

    public void setWechat_nickname(String wechat_nickname) {
        this.wechat_nickname = wechat_nickname;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getAvastar() {
        return avastar;
    }

    public void setAvastar(String avastar) {
        this.avastar = avastar;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "unionid='" + unionid + '\'' +
                ", password='" + password + '\'' +
                ", is_subscribe='" + is_subscribe + '\'' +
                ", nickname='" + nickname + '\'' +
                ", subscribe_time='" + subscribe_time + '\'' +
                ", mobile='" + mobile + '\'' +
                ", uid='" + uid + '\'' +
                ", loginKey='" + loginKey + '\'' +
                ", wechat_nickname='" + wechat_nickname + '\'' +
                ", head_url='" + head_url + '\'' +
                ", avastar='" + avastar + '\'' +
                ", openid='" + openid + '\'' +
                '}';
    }
}
