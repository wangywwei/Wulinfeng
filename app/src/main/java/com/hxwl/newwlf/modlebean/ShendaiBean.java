package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ShendaiBean {

    /**
     * code : 1000
     * message : 省份代理列表
     * data : [{"logoImage":"5a2102aed276.jpg","agentName":"河南代理","agentId":1},{"logoImage":"7878336666.png","agentName":"北京代理","agentId":2}]
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
         * logoImage : 5a2102aed276.jpg
         * agentName : 河南代理
         * agentId : 1
         */

        private String logoImage;
        private String agentName;
        private int agentId;

        public String getLogoImage() {
            return logoImage;
        }

        public void setLogoImage(String logoImage) {
            this.logoImage = logoImage;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }
    }
}
