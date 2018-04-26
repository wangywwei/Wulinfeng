package com.hxwl.wlf3.home.xiangqing;

public class Shijian3Bean {

    /**
     * code : 1000
     * message : 请求成功
     * data : {"eventAddress":"北京市朝阳区区工人体育馆","videoUrl":"http://1252859940.vod2.myqcloud.com/ea75a88cvodgzp1252859940/8d42987a9031868223128214261/fpX7578dPIcA.mp4","author":"搏击大数据","intro":"这是视频类型","coverImage":"2018031318150239565528.jpg","eventTime":"04.04/星期三","showType":1,"id":1,"label":"对阵","title":"视频title","content":null}
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
         * eventAddress : 北京市朝阳区区工人体育馆
         * videoUrl : http://1252859940.vod2.myqcloud.com/ea75a88cvodgzp1252859940/8d42987a9031868223128214261/fpX7578dPIcA.mp4
         * author : 搏击大数据
         * intro : 这是视频类型
         * coverImage : 2018031318150239565528.jpg
         * eventTime : 04.04/星期三
         * showType : 1
         * id : 1
         * label : 对阵
         * title : 视频title
         * content : null
         */

        private String eventAddress;
        private String videoUrl;
        private String author;
        private String intro;
        private String coverImage;
        private String eventTime;
        private int showType;
        private int id;
        private String label;
        private String title;
        private String content;

        public String getEventAddress() {
            return eventAddress;
        }

        public void setEventAddress(String eventAddress) {
            this.eventAddress = eventAddress;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public int getShowType() {
            return showType;
        }

        public void setShowType(int showType) {
            this.showType = showType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
