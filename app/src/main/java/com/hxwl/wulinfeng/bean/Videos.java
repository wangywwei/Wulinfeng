package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/5/31.
 */
public class Videos {
    private String id;

    private String type;

    private String contents;

    private String url;

    private String video_list_id;

    private String is_show;

    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideo_list_id() {
        return video_list_id;
    }

    public void setVideo_list_id(String video_list_id) {
        this.video_list_id = video_list_id;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Videos{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", contents='" + contents + '\'' +
                ", url='" + url + '\'' +
                ", video_list_id='" + video_list_id + '\'' +
                ", is_show='" + is_show + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
