package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/2/2.
 */

public class VideosaichenBean {

    /**
     * code : 1000
     * message : 获取赛程回放信息
     * data : {"scheduleId":9,"scheduleName":"赛事2对阵35","videoUrl":"http://1252859940.vod2.myqcloud.com/9e9d4b34vodtransgzp1252859940/5439f7df4564972819138936678/v.f20.mp4","videoLength":22,"videoName":"飞雪连天"}
     */

    private String code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * scheduleId : 9
         * scheduleName : 赛事2对阵35
         * videoUrl : http://1252859940.vod2.myqcloud.com/9e9d4b34vodtransgzp1252859940/5439f7df4564972819138936678/v.f20.mp4
         * videoLength : 22
         * videoName : 飞雪连天
         */

        private int scheduleId;
        private String scheduleName;
        private String videoUrl;
        private int videoLength;
        private String videoName;

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
    }
}
