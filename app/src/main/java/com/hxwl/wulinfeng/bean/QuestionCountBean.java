package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/20.
 * 问题数量bean
 */
public class QuestionCountBean {
    private int num ;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "QuestionCountBean{" +
                "num='" + num + '\'' +
                '}';
    }
}
