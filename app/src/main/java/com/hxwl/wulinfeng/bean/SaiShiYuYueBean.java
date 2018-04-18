package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/14.
 * 赛事预约bean
 */
public class SaiShiYuYueBean {
    private String id;

    private String uid;

    private String saicheng_id;

    private String state;

    private Saicheng_info saicheng_info;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSaicheng_id() {
        return saicheng_id;
    }

    public void setSaicheng_id(String saicheng_id) {
        this.saicheng_id = saicheng_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Saicheng_info getSaicheng_info() {
        return saicheng_info;
    }

    public void setSaicheng_info(Saicheng_info saicheng_info) {
        this.saicheng_info = saicheng_info;
    }

    @Override
    public String toString() {
        return "SaiShiYuYueBean{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", saicheng_id='" + saicheng_id + '\'' +
                ", state='" + state + '\'' +
                ", saicheng_info=" + saicheng_info +
                '}';
    }
}
