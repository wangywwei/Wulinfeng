package com.hxwl.wulinfeng.bean;


import android.graphics.drawable.Drawable;

import com.yasic.bubbleview.BubbleView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class InteractionData {

    public int type;//0表示红方  1表示蓝方
    public int imageid;
    public BubbleView heartLayout;
    public int color;
    public List<Drawable> drawable;

    public InteractionData(BubbleView heartLayout, List<Drawable> drawable, int type) {
        this.heartLayout = heartLayout;
        this.drawable = drawable;
        this.type=type;
    }

    public List<Drawable> getDrawable() {
        return drawable;
    }

    public void setDrawable(List<Drawable> drawable) {
        this.drawable = drawable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public BubbleView getHeartLayout() {
        return heartLayout;
    }

    public void setHeartLayout(BubbleView heartLayout) {
        this.heartLayout = heartLayout;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public InteractionData(int imageid, BubbleView heartLayout) {
        this.imageid = imageid;
        this.heartLayout = heartLayout;
    }
}
