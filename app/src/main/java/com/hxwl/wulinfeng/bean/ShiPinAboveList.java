package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/6.
 * 首页 -- 视频上边list数据
 */
public class ShiPinAboveList {
    private String id;

    private String title;

    private String img;

    private String open_times;

    private Videos videos;

    private boolean isPlay ;

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public String getOpen_times() {
        return open_times;
    }

    public void setOpen_times(String open_times) {
        this.open_times = open_times;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }
}
