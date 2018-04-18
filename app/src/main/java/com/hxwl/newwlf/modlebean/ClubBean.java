package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ClubBean {

    /**
     * code : 1000
     * message : 俱乐部列表
     * data : [{"id":2,"clubName":"飞狼俱乐部","clubLogo":"5a03f819df918.jpg","backgroundImage":null,"desc":"飞阳搏击俱乐部","rank":2,"updateTime":1516611918000},{"id":1,"clubName":"飞虎俱乐部","clubLogo":"5a2102aed276.jpg","backgroundImage":null,"desc":"青岛鑫江国际搏击俱乐部","rank":1,"updateTime":1516611916000}]
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

    public static class DataBean {
        /**
         * id : 2
         * clubName : 飞狼俱乐部
         * clubLogo : 5a03f819df918.jpg
         * backgroundImage : null
         * desc : 飞阳搏击俱乐部
         * rank : 2
         * updateTime : 1516611918000
         */

        private int id;
        private String clubName;
        private String clubLogo;
        private Object backgroundImage;
        private String desc;
        private int rank;
        private long updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClubName() {
            return clubName;
        }

        public void setClubName(String clubName) {
            this.clubName = clubName;
        }

        public String getClubLogo() {
            return clubLogo;
        }

        public void setClubLogo(String clubLogo) {
            this.clubLogo = clubLogo;
        }

        public Object getBackgroundImage() {
            return backgroundImage;
        }

        public void setBackgroundImage(Object backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
