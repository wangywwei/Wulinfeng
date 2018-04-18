package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/22.
 */
public class NianFenYueBean {
    private String year;

    private List<String> monthList ;

    private List<Month> months ;

    public List<Month> getMonths() {
        return months;
    }

    public void setMonths(List<Month> months) {
        this.months = months;
    }

    private boolean isselect;

    public boolean isselect() {
        return isselect;
    }

    public void setIsselect(boolean isselect) {
        this.isselect = isselect;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<String> getMonth() {
        return monthList;
    }

    public void setMonth(List<String> month) {
        this.monthList = month;
    }

    @Override
    public String toString() {
        return "NianFenYueBean{" +
                "year='" + year + '\'' +
                ", month=" + monthList +
                '}';
    }
}
