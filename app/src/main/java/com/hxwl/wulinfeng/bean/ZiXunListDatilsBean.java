package com.hxwl.wulinfeng.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Allen on 2017/6/21.
 */
public class ZiXunListDatilsBean implements Serializable{
    private String id;

    private String news_id;

    private String msg;

    private String time;

    private String uid;


    private String nickname;

    private String head_url;

    private List<HuiFuBean> huifu ;

    private String zan_times ;

    private String is_zan ;


    public String getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(String is_zan) {
        this.is_zan = is_zan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getZan_times() {
        return zan_times;
    }

    public void setZan_times(String zan_times) {
        this.zan_times = zan_times;
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

    public List<HuiFuBean> getHuifu() {
        return huifu;
    }

    public void setHuifu(List<HuiFuBean> huifu) {
        this.huifu = huifu;
    }

    @Override
    public String toString() {
        return "ZiXunListDatilsBean{" +
                "id='" + id + '\'' +
                ", news_id='" + news_id + '\'' +
                ", msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", uid='" + uid + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_url='" + head_url + '\'' +
                ", huifu=" + huifu +
                '}';
    }
}
