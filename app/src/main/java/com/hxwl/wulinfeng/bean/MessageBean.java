package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/8.
 */
public class MessageBean {

    private String id;

    private String type;

    private String about_id;

    private String uid;

    private String is_read;

    private Msg_info msg_info;

    private Zhu_info zhu_info;

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

    public String getAbout_id() {
        return about_id;
    }

    public void setAbout_id(String about_id) {
        this.about_id = about_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }

    public Msg_info getMsg_info() {
        return msg_info;
    }

    public void setMsg_info(Msg_info msg_info) {
        this.msg_info = msg_info;
    }

    public Zhu_info getZhu_info() {
        return zhu_info;
    }

    public void setZhu_info(Zhu_info zhu_info) {
        this.zhu_info = zhu_info;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", about_id='" + about_id + '\'' +
                ", uid='" + uid + '\'' +
                ", is_read='" + is_read + '\'' +
                ", msg_info=" + msg_info +
                ", zhu_info=" + zhu_info +
                '}';
    }
}
