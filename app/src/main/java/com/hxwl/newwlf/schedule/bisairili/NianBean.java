package com.hxwl.newwlf.schedule.bisairili;

import com.hxwl.wulinfeng.bean.NianFenYueBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 */

public class NianBean {


    /**
     * code : 1000
     * message : 获取赛程日历年月
     * data : [{"year":"2017","monthList":["11","12"]},{"year":"2018","monthList":["01"]}]
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
         * year : 2017
         * monthList : ["11","12"]
         */
        private boolean select;
        private boolean yueselect;

        public boolean isYueselect() {
            return yueselect;
        }

        public void setYueselect(boolean yueselect) {
            this.yueselect = yueselect;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        private String year;
        private List<String> monthList;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<String> getMonthList() {
            return monthList;
        }

        public void setMonthList(List<String> monthList) {
            this.monthList = monthList;
        }
    }
}
