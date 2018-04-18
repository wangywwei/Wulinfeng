package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/14.
 * 武战联盟bean
 */
public class WuZhanLMBean {
    private String id;

    private String name;

    private String icon;

    private String addr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "WuZhanLMBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }
}
