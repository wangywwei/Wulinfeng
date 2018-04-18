package com.hxwl.wulinfeng.bean;

import java.util.List;

/**
 * Created by Allen on 2017/6/8.
 *  武林最新帖子
 */
public class WuLinZuiXinBean {
    private String id;

    private String uid;

    private String contents;

    private String images;

    private String letv_video_id;

    private String letv_vu;

    private String huifu_times;

    private String zan_times;

    private String time;

    private String nickname;

    private String head_url;

    private List<String> img ;

    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private String qcloud_fileid ;
    private String qcloud_video_url ;
    private String qcloud_video_img ;

    public String getQcloud_fileid() {
        return qcloud_fileid;
    }

    public void setQcloud_fileid(String qcloud_fileid) {
        this.qcloud_fileid = qcloud_fileid;
    }

    public String getQcloud_video_url() {
        return qcloud_video_url;
    }

    public void setQcloud_video_url(String qcloud_video_url) {
        this.qcloud_video_url = qcloud_video_url;
    }

    public String getQcloud_video_img() {
        return qcloud_video_img;
    }

    public void setQcloud_video_img(String qcloud_video_img) {
        this.qcloud_video_img = qcloud_video_img;
    }

    private int is_guanzhu;
    private String is_zan ; //0 没有点过赞  1 点过赞

    public String getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(String is_zan) {
        this.is_zan = is_zan;
    }

    private List<ZanBean> zan;
    private List<HuiFuBean> huifu;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getLetv_video_id() {
        return letv_video_id;
    }

    public void setLetv_video_id(String letv_video_id) {
        this.letv_video_id = letv_video_id;
    }

    public String getLetv_vu() {
        return letv_vu;
    }

    public void setLetv_vu(String letv_vu) {
        this.letv_vu = letv_vu;
    }

    public String getHuifu_times() {
        return huifu_times;
    }

    public void setHuifu_times(String huifu_times) {
        this.huifu_times = huifu_times;
    }

    public String getZan_times() {
        return zan_times;
    }

    public void setZan_times(String zan_times) {
        this.zan_times = zan_times;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public int getIs_guanzhu() {
        return is_guanzhu;
    }

    public void setIs_guanzhu(int is_guanzhu) {
        this.is_guanzhu = is_guanzhu;
    }

    public List<ZanBean> getZan() {
        return zan;
    }

    public void setZan(List<ZanBean> zan) {
        this.zan = zan;
    }

    public List<HuiFuBean> getHuifu() {
        return huifu;
    }

    public void setHuifu(List<HuiFuBean> huifu) {
        this.huifu = huifu;
    }

    @Override
    public String toString() {
        return "WuLinZuiXinBean{" +
                "id='" + id + '\'' +
                ", uid='" + uid + '\'' +
                ", contents='" + contents + '\'' +
                ", images='" + images + '\'' +
                ", letv_video_id='" + letv_video_id + '\'' +
                ", letv_vu='" + letv_vu + '\'' +
                ", huifu_times='" + huifu_times + '\'' +
                ", zan_times='" + zan_times + '\'' +
                ", time='" + time + '\'' +
                ", nickname='" + nickname + '\'' +
                ", head_url='" + head_url + '\'' +
                ", img=" + img +
                ", qcloud_fileid='" + qcloud_fileid + '\'' +
                ", qcloud_video_url='" + qcloud_video_url + '\'' +
                ", qcloud_video_img='" + qcloud_video_img + '\'' +
                ", is_guanzhu=" + is_guanzhu +
                ", is_zan='" + is_zan + '\'' +
                ", zan=" + zan +
                ", huifu=" + huifu +
                '}';
    }
}
