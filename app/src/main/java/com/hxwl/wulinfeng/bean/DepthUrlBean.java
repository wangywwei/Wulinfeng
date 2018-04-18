package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * 功能:深度链接接收对象
 * 作者：zjn on 2017/2/14 13:49
 */

public class DepthUrlBean {

    /**
     * classname : com.hxwl.blackbears.HaoZixunDetailActivity
     * params : [{"key":"id","value":"232","type":"0"}]
     */

    private String classname;
    private List<ParamsBean> params;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public List<ParamsBean> getParams() {
        return params;
    }

    public void setParams(List<ParamsBean> params) {
        this.params = params;
    }
}
