package com.hxwl.wlf3.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class DuiZenXiangBean {


    /**
     * code : 1000
     * message : 请求成功
     * data : {"againstList":[{"redClub":"高大拳搏击俱乐部","redName":"一龙","blueClub":"","blueName":"龙二","id":1,"redHeadImg":"58105df91aa36.jpg","blueHeadImg":"57c9341a3e792.jpg"},{"redClub":"","redName":"迈克\u2022泰森","blueClub":"","blueName":"付庆南","id":2,"redHeadImg":"57c9225a8ab77.jpg","blueHeadImg":"582a9c945b82d.jpg"}],"eventAddress":"北京市朝阳区区工人体育馆","author":"搏击大数据","eventTime":"04.04/星期三","showType":6,"id":8,"label":"备赛","title":"对阵信息"}
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
         * againstList : [{"redClub":"高大拳搏击俱乐部","redName":"一龙","blueClub":"","blueName":"龙二","id":1,"redHeadImg":"58105df91aa36.jpg","blueHeadImg":"57c9341a3e792.jpg"},{"redClub":"","redName":"迈克\u2022泰森","blueClub":"","blueName":"付庆南","id":2,"redHeadImg":"57c9225a8ab77.jpg","blueHeadImg":"582a9c945b82d.jpg"}]
         * eventAddress : 北京市朝阳区区工人体育馆
         * author : 搏击大数据
         * eventTime : 04.04/星期三
         * showType : 6
         * id : 8
         * label : 备赛
         * title : 对阵信息
         */

        private String eventAddress;
        private String author;
        private String eventTime;
        private int showType;
        private int id;
        private String label;
        private String title;
        private List<AgainstListBean> againstList;

        public String getEventAddress() {
            return eventAddress;
        }

        public void setEventAddress(String eventAddress) {
            this.eventAddress = eventAddress;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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

        public List<AgainstListBean> getAgainstList() {
            return againstList;
        }

        public void setAgainstList(List<AgainstListBean> againstList) {
            this.againstList = againstList;
        }

        public static class AgainstListBean {
            /**
             * redClub : 高大拳搏击俱乐部
             * redName : 一龙
             * blueClub :
             * blueName : 龙二
             * id : 1
             * redHeadImg : 58105df91aa36.jpg
             * blueHeadImg : 57c9341a3e792.jpg
             */

            private String redClub;
            private String redName;
            private String blueClub;
            private String blueName;
            private int id;
            private String redHeadImg;
            private String blueHeadImg;

            public String getRedClub() {
                return redClub;
            }

            public void setRedClub(String redClub) {
                this.redClub = redClub;
            }

            public String getRedName() {
                return redName;
            }

            public void setRedName(String redName) {
                this.redName = redName;
            }

            public String getBlueClub() {
                return blueClub;
            }

            public void setBlueClub(String blueClub) {
                this.blueClub = blueClub;
            }

            public String getBlueName() {
                return blueName;
            }

            public void setBlueName(String blueName) {
                this.blueName = blueName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRedHeadImg() {
                return redHeadImg;
            }

            public void setRedHeadImg(String redHeadImg) {
                this.redHeadImg = redHeadImg;
            }

            public String getBlueHeadImg() {
                return blueHeadImg;
            }

            public void setBlueHeadImg(String blueHeadImg) {
                this.blueHeadImg = blueHeadImg;
            }
        }
    }
}
