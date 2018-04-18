package com.hxwl.wulinfeng.bean;

/**
 * 功能:深度链接的参数
 * 作者：zjn on 2017/2/14 14:09
 */

public class ParamsBean {

    /**
     * key : id
     * value : 232
     * type : 0为一级 1为二级 二级可以传对象
     */

    private String key;
    private String value;
    private String type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
