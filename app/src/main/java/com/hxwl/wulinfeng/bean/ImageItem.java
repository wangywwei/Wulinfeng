package com.hxwl.wulinfeng.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;


public class ImageItem implements Comparable<ImageItem>, Serializable, Parcelable {
    private String imagePath;//大图路径
    private int position;//照片所处的位置
    private int picId;//服务器返回的照片id
    private String imageTub;//缩略图路径
    private String picName;//照片部位名称

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public ImageItem(String imagePath, String imageTub) {
        this.imagePath = imagePath;
        this.imageTub = imageTub;
    }
    public ImageItem(String imagePath, String imageTub, String picName) {
        this.imagePath = imagePath;
        this.imageTub = imageTub;
        this.picName = picName;
    }

    public ImageItem(String imagePath, String imageTub, int position) {
        this.imageTub = imageTub;
        this.imagePath = imagePath;
        this.position = position;
    }
    public ImageItem(String picName) {
        this.picName = picName;
    }

    public ImageItem() {
    }

    public String getImageTub() {
        return imageTub;
    }

    public void setImageTub(String imageTub) {
        this.imageTub = imageTub;
    }

    public int getPosition() {
        return position;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public int compareTo(ImageItem another) {
        return 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imagePath);
        dest.writeInt(this.position);
        dest.writeInt(this.picId);
        dest.writeString(this.imageTub);
        dest.writeString(this.picName);
    }

    protected ImageItem(Parcel in) {
        this.imagePath = in.readString();
        this.position = in.readInt();
        this.picId = in.readInt();
        this.imageTub = in.readString();
        this.picName = in.readString();
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        @Override
        public ImageItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        @Override
        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
