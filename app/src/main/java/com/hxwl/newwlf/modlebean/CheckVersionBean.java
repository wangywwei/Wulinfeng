package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/3/11.
 */

public class CheckVersionBean {

    /**
     * code : 1000
     * message : 请求成功
     * data : {"id":1,"androidMaxVersion":"android _max_version","androidMinVersion":"1","iosMaxVersion":"1","iosMinVersion":"1","iosDownloadUrl":null,"androidDownloadUrl":null,"startImage":null,"startAnimation":"start_ animation","shoppingUrl":null,"competitionUrl":"http://haixuansaibaoming.heixiongboji.com/?from=groupmessage&isappinstalled=0"}
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
         * id : 1
         * androidMaxVersion : android _max_version
         * androidMinVersion : 1
         * iosMaxVersion : 1
         * iosMinVersion : 1
         * iosDownloadUrl : null
         * androidDownloadUrl : null
         * startImage : null
         * startAnimation : start_ animation
         * shoppingUrl : null
         * competitionUrl : http://haixuansaibaoming.heixiongboji.com/?from=groupmessage&isappinstalled=0
         */

        private int id;
        private String androidMaxVersion;
        private String androidMinVersion;
        private String iosMaxVersion;
        private String iosMinVersion;
        private String iosDownloadUrl;
        private String androidDownloadUrl;
        private Object startImage;
        private String startAnimation;
        private String shoppingUrl;
        private String competitionUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAndroidMaxVersion() {
            return androidMaxVersion;
        }

        public void setAndroidMaxVersion(String androidMaxVersion) {
            this.androidMaxVersion = androidMaxVersion;
        }

        public String getAndroidMinVersion() {
            return androidMinVersion;
        }

        public void setAndroidMinVersion(String androidMinVersion) {
            this.androidMinVersion = androidMinVersion;
        }

        public String getIosMaxVersion() {
            return iosMaxVersion;
        }

        public void setIosMaxVersion(String iosMaxVersion) {
            this.iosMaxVersion = iosMaxVersion;
        }

        public String getIosMinVersion() {
            return iosMinVersion;
        }

        public void setIosMinVersion(String iosMinVersion) {
            this.iosMinVersion = iosMinVersion;
        }

        public String getIosDownloadUrl() {
            return iosDownloadUrl;
        }

        public void setIosDownloadUrl(String iosDownloadUrl) {
            this.iosDownloadUrl = iosDownloadUrl;
        }

        public String getAndroidDownloadUrl() {
            return androidDownloadUrl;
        }

        public void setAndroidDownloadUrl(String androidDownloadUrl) {
            this.androidDownloadUrl = androidDownloadUrl;
        }

        public Object getStartImage() {
            return startImage;
        }

        public void setStartImage(Object startImage) {
            this.startImage = startImage;
        }

        public String getStartAnimation() {
            return startAnimation;
        }

        public void setStartAnimation(String startAnimation) {
            this.startAnimation = startAnimation;
        }

        public String getShoppingUrl() {
            return shoppingUrl;
        }

        public void setShoppingUrl(String shoppingUrl) {
            this.shoppingUrl = shoppingUrl;
        }

        public String getCompetitionUrl() {
            return competitionUrl;
        }

        public void setCompetitionUrl(String competitionUrl) {
            this.competitionUrl = competitionUrl;
        }
    }
}
