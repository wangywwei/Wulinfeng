package com.hxwl.wlf3.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class GuideBean {


    /**
     * code : 1000
     * message : 请求成功
     * data : [{"showType":"0","value":2,"key":"id"},{"showType":"0","value":"新春大赛北京赛程","key":"赛事名称"},{"showType":"0","value":"2018年04月15日15:59","key":"比赛时间"},{"showType":"1","value":"广州市番禺区英东体育馆","key":"比赛场馆"},{"showType":"1","value":"广州科尔海悦酒店","key":"运动员住宿"},{"showType":"0","value":"关枫","key":"主持人"},{"showType":"0","value":"方便，王洪祥","key":"解说"},{"showType":"0","value":"牛德旺，曹德旺，牛槽旺","key":"裁判"},{"showType":"0","value":"大美女，小美女","key":"举牌女郎"},{"showType":"0","value":"草量级\n蝇量级","key":"比赛级别"},{"showType":"0","value":"广州大象健康科技有限公司","key":"主办单位"},{"showType":"0","value":"广州市大象体育产业有限公司","key":"承办单位"},{"showType":"0","value":"小象运动智能共享跑步机","key":"协办单位"},{"showType":"0","value":"高大拳搏击俱乐部\n大东翔搏击俱乐部","key":"参赛单位"}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * showType : 0
         * value : 2
         * key : id
         */

        private String showType;
        private String value;
        private String key;

        public String getShowType() {
            return showType;
        }

        public void setShowType(String showType) {
            this.showType = showType;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
