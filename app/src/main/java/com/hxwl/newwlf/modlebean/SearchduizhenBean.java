package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/2.
 */

public class SearchduizhenBean {

    /**
     * code : 1000
     * message : 请求成功
     * data : [{"remark":"good","id":"2","publicityImage":"","againstName":"三龙对阵四龙"},{"remark":"很好nnnnnnnnnnnnnnnnnnnnnnnnnnnn","id":"1","publicityImage":"","againstName":"一龙对阵二龙"},{"remark":"22222","id":"4","publicityImage":"","againstName":"七龙对阵8龙"},{"remark":"很好bbbbbbbbbbbbbbbbbbbbbbbbb","id":"18","publicityImage":"","againstName":"f"},{"remark":"很好nnnbbbbbbb","id":"17","publicityImage":"","againstName":"e"},{"remark":"很好bnnnnnnnnnnnnnnnnnnnnnnnn","id":"19","publicityImage":"","againstName":"g"},{"remark":"很好bnnnnnnnnnnnnnnnnnnnnnnnn","id":"20","publicityImage":"","againstName":"h"},{"remark":"4444","id":"5","publicityImage":"","againstName":"j"},{"remark":"4444","id":"11","publicityImage":"","againstName":"d"},{"remark":"4444","id":"8","publicityImage":"","againstName":"a"}]
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
         * remark : good
         * id : 2
         * publicityImage :
         * againstName : 三龙对阵四龙
         */

        private String remark;
        private String id;
        private String publicityImage;
        private String againstName;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPublicityImage() {
            return publicityImage;
        }

        public void setPublicityImage(String publicityImage) {
            this.publicityImage = publicityImage;
        }

        public String getAgainstName() {
            return againstName;
        }

        public void setAgainstName(String againstName) {
            this.againstName = againstName;
        }
    }
}
