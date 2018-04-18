package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/21.
 */
public class GengHuiFuBean {
    private String zhu_id ;

    public String getZhu_id() {
        return zhu_id;
    }

    public void setZhu_id(String zhu_id) {
        this.zhu_id = zhu_id;
    }

    public String getGen_id() {
        return gen_id;
    }

    public void setGen_id(String gen_id) {
        this.gen_id = gen_id;
    }

    private String gen_id;

    private String id;

    private String news_id;

    private String gentie_id;

    private String from_uid;

    private String to_uid;

    private String msg;

    private String time;

    private String from_nickname;

    private String from_head_url;

    private String to_nickname;

    private String to_head_url;

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

    public String getGentie_id() {
        return gentie_id;
    }

    public void setGentie_id(String gentie_id) {
        this.gentie_id = gentie_id;
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

    public String getTo_nickname() {
        return to_nickname;
    }

    public void setTo_nickname(String to_nickname) {
        this.to_nickname = to_nickname;
    }

    public String getTo_head_url() {
        return to_head_url;
    }

    public void setTo_head_url(String to_head_url) {
        this.to_head_url = to_head_url;
    }

    @Override
    public String toString() {
        return "GengHuiFuBean{" +
                "id='" + id + '\'' +
                ", news_id='" + news_id + '\'' +
                ", gentie_id='" + gentie_id + '\'' +
                ", from_uid='" + from_uid + '\'' +
                ", to_uid='" + to_uid + '\'' +
                ", msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", from_nickname='" + from_nickname + '\'' +
                ", from_head_url='" + from_head_url + '\'' +
                ", to_nickname='" + to_nickname + '\'' +
                ", to_head_url='" + to_head_url + '\'' +
                '}';
    }
}
