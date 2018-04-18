package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/13.
 */
public class LevelBean {
    private String id ;
    private String name ;
    private boolean isChecked ;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
