package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/22.
 * 往期视频bean
 */
public class WangQiSPBean {
    private List<WQShiPinBean> huifang ;

    public List<WQShiPinBean> getHuifang() {
        return huifang;
    }

    public void setHuifang(List<WQShiPinBean> huifang) {
        this.huifang = huifang;
    }

    @Override
    public String toString() {
        return "WangQiSPBean{" +
                "huifang=" + huifang +
                '}';
    }
}
