package com.hxwl.newwlf.modlebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class BisairiliVideoBean {

    /**
     * code : 1000
     * message : 通过赛程id获取对阵列表
     * data : [{"scheduleId":2,"scheduleName":"互相要地有物相成地10","videoUrl":"http://1252859940.vod2.myqcloud.com/9e9d4b34vodtransgzp1252859940/baaf41f24564972819136991234/v.f20.mp4","videoLength":22,"videoName":"三龙对阵四龙","publicityImg":"2018012301.png"},{"scheduleId":1,"scheduleName":"要地地相辅相成要9","videoUrl":"http://1252859940.vod2.myqcloud.com/9e9d4b34vodtransgzp1252859940/669535554564972819135678344/v.f20.mp4","videoLength":100,"videoName":"一龙对阵二龙","publicityImg":"2018012202.png"}]
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

    public static class DataBean implements Serializable{
        /**
         * scheduleId : 2
         * scheduleName : 互相要地有物相成地10
         * videoUrl : http://1252859940.vod2.myqcloud.com/9e9d4b34vodtransgzp1252859940/baaf41f24564972819136991234/v.f20.mp4
         * videoLength : 22
         * videoName : 三龙对阵四龙
         * publicityImg : 2018012301.png
         */

        private int scheduleId;
        private String scheduleName;
        private String videoUrl;
        private int videoLength;
        private String videoName;
        private String publicityImg;

        public int getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(int scheduleId) {
            this.scheduleId = scheduleId;
        }

        public String getScheduleName() {
            return scheduleName;
        }

        public void setScheduleName(String scheduleName) {
            this.scheduleName = scheduleName;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public int getVideoLength() {
            return videoLength;
        }

        public void setVideoLength(int videoLength) {
            this.videoLength = videoLength;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public String getPublicityImg() {
            return publicityImg;
        }

        public void setPublicityImg(String publicityImg) {
            this.publicityImg = publicityImg;
        }
    }
}
