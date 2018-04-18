package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/1/31.
 */

public class QuanshouxiangqingBean {

    /**
     * code : 1000
     * message : 获取选手信息
     * data : {"id":1,"clubId":1,"clubName":"飞虎俱乐部","agentId":1,"playerName":"一龙","sex":1,"birthday":1516605802000,"headImg":"20181112.png","weightLevel":68,"weightLevelName":"草量级|低于115磅（52kg）","height":178,"countryId":1,"countryName":null,"winNum":99,"failNum":88,"drawNum":1,"koNum":99,"absenceNum":2,"introduction":"一龙最牛一龙最牛一龙最牛一龙最牛一龙最牛一龙最牛一龙最牛一龙最牛","rank":1,"isHot":1,"publicityUrl":"92839487234.jpg","winPercent":null,"koPercent":null,"failPercent":null,"weight":null}
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
         * clubId : 1
         * clubName : 飞虎俱乐部
         * agentId : 1
         * playerName : 一龙
         * sex : 1
         * birthday : 1516605802000
         * headImg : 20181112.png
         * weightLevel : 68
         * weightLevelName : 草量级|低于115磅（52kg）
         * height : 178
         * countryId : 1
         * countryName : null
         * winNum : 99
         * failNum : 88
         * drawNum : 1
         * koNum : 99
         * absenceNum : 2
         * introduction : 一龙最牛一龙最牛一龙最牛一龙最牛一龙最牛一龙最牛一龙最牛一龙最牛
         * rank : 1
         * isHot : 1
         * publicityUrl : 92839487234.jpg
         * winPercent : null
         * koPercent : null
         * failPercent : null
         * weight : null
         */
        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
        private String userIsFollow;

        public String getUserIsFollow() {
            return userIsFollow;
        }

        public void setUserIsFollow(String userIsFollow) {
            this.userIsFollow = userIsFollow;
        }

        private int id;
        private int clubId;
        private String clubName;
        private int agentId;
        private String playerName;
        private int sex;
        private String birthday;
        private String headImg;
        private int weightLevel;
        private String weightLevelName;
        private int height;
        private int countryId;
        private String countryName;
        private int winNum;
        private int failNum;
        private int drawNum;
        private int koNum;
        private int absenceNum;
        private String introduction;
        private int rank;
        private int isHot;
        private String publicityUrl;
        private int winPercent;
        private int koPercent;
        private int failPercent;
        private int weight;
        private String countryImg;

        public String getCountryImg() {
            return countryImg;
        }

        public void setCountryImg(String countryImg) {
            this.countryImg = countryImg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getClubId() {
            return clubId;
        }

        public void setClubId(int clubId) {
            this.clubId = clubId;
        }

        public String getClubName() {
            return clubName;
        }

        public void setClubName(String clubName) {
            this.clubName = clubName;
        }

        public int getAgentId() {
            return agentId;
        }

        public void setAgentId(int agentId) {
            this.agentId = agentId;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getWeightLevel() {
            return weightLevel;
        }

        public void setWeightLevel(int weightLevel) {
            this.weightLevel = weightLevel;
        }

        public String getWeightLevelName() {
            return weightLevelName;
        }

        public void setWeightLevelName(String weightLevelName) {
            this.weightLevelName = weightLevelName;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getCountryId() {
            return countryId;
        }

        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public int getWinNum() {
            return winNum;
        }

        public void setWinNum(int winNum) {
            this.winNum = winNum;
        }

        public int getFailNum() {
            return failNum;
        }

        public void setFailNum(int failNum) {
            this.failNum = failNum;
        }

        public int getDrawNum() {
            return drawNum;
        }

        public void setDrawNum(int drawNum) {
            this.drawNum = drawNum;
        }

        public int getKoNum() {
            return koNum;
        }

        public void setKoNum(int koNum) {
            this.koNum = koNum;
        }

        public int getAbsenceNum() {
            return absenceNum;
        }

        public void setAbsenceNum(int absenceNum) {
            this.absenceNum = absenceNum;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getIsHot() {
            return isHot;
        }

        public void setIsHot(int isHot) {
            this.isHot = isHot;
        }

        public String getPublicityUrl() {
            return publicityUrl;
        }

        public void setPublicityUrl(String publicityUrl) {
            this.publicityUrl = publicityUrl;
        }

        public int getWinPercent() {
            return winPercent;
        }

        public void setWinPercent(int winPercent) {
            this.winPercent = winPercent;
        }

        public int getKoPercent() {
            return koPercent;
        }

        public void setKoPercent(int koPercent) {
            this.koPercent = koPercent;
        }

        public int getFailPercent() {
            return failPercent;
        }

        public void setFailPercent(int failPercent) {
            this.failPercent = failPercent;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
