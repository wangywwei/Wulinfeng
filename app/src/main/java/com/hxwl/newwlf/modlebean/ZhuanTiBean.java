package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class ZhuanTiBean  {

    /**
     * code : 1000
     * message : 专题列表
     * data : [{"subjectName":"牛人专题","updateTime":1519469970000,"subjectId":3},{"subjectName":"二龙专题","updateTime":1519444575000,"subjectId":2,"coverImage":"2018012516012081240820.png"},{"subjectName":"一龙专题","updateTime":1519444543000,"subjectId":1,"coverImage":"2018012516003692443151.png"}]
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
         * subjectName : 牛人专题
         * updateTime : 1519469970000
         * subjectId : 3
         * coverImage : 2018012516012081240820.png
         */

        private String subjectName;
        private long updateTime;
        private int subjectId;
        private String coverImage;
        private String viewsNum;

        public String getViewsNum() {
            return viewsNum;
        }

        public void setViewsNum(String viewsNum) {
            this.viewsNum = viewsNum;
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(int subjectId) {
            this.subjectId = subjectId;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }
    }
}
