package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/20.
 * 我的反馈详情列表bean
 */
public class MyFanKuiBean {
    private String id;

    private String msg;

    private String time;

    private String huifu;

    private String is_read;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHuifu() {
        return huifu;
    }

    public void setHuifu(String huifu) {
        this.huifu = huifu;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    @Override
    public String toString() {
        return "MyFanKuiBean{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", time='" + time + '\'' +
                ", huifu='" + huifu + '\'' +
                ", is_read='" + is_read + '\'' +
                '}';
    }
}
