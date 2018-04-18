package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ClubDetailsBean {

    /**
     * code : 1000
     * message : 俱乐部信息
     * data : {"id":2,"clubName":"飞狼俱乐部","clubLogo":"5a03f819df918.jpg","backgroundImage":null,"founder":"赵五","establishTime":1517297994000,"address":"每次你都是这样","rank":2,"mobile":"02088889999","startCoach":"美子州","startStudent":null,"honor":null,"intro":null,"venueGrade":null,"playerNumGrade":null,"playerStrengthGrade":null,"coachTeamGrade":"5","firstLetter":"e","isRecommend":2,"total":444,"winSum":88,"failSum":22,"koSum":2,"winPercent":2,"koPercent":66,"failPercent":22,"updateTime":1516611918000}
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
         * id : 2
         * clubName : 飞狼俱乐部
         * clubLogo : 5a03f819df918.jpg
         * backgroundImage : null
         * founder : 赵五
         * establishTime : 1517297994000
         * address : 每次你都是这样
         * rank : 2
         * mobile : 02088889999
         * startCoach : 美子州
         * startStudent : null
         * honor : null
         * intro : null
         * venueGrade : null
         * playerNumGrade : null
         * playerStrengthGrade : null
         * coachTeamGrade : 5
         * firstLetter : e
         * isRecommend : 2
         * total : 444
         * winSum : 88
         * failSum : 22
         * koSum : 2
         * winPercent : 2
         * koPercent : 66
         * failPercent : 22
         * updateTime : 1516611918000
         */

        private int id;
        private String clubName;
        private String clubLogo;
        private String backgroundImage;
        private String founder;
        private String establishTime;
        private String address;
        private int rank;
        private String mobile;
        private String startCoach;
        private String starStudent;
        private String honor;
        private String intro;
        private String venueGrade;
        private String playerNumGrade;
        private String playerStrengthGrade;
        private String coachTeamGrade;
        private String firstLetter;
        private int isRecommend;
        private int total;
        private int winSum;
        private int failSum;
        private int koSum;
        private int winPercent;
        private int koPercent;
        private int failPercent;
        private String updateTime;

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

        public String getBackgroundImage() {
            return backgroundImage;
        }

        public void setBackgroundImage(String backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        public String getFounder() {
            return founder;
        }

        public void setFounder(String founder) {
            this.founder = founder;
        }

        public String getEstablishTime() {
            return establishTime;
        }

        public void setEstablishTime(String establishTime) {
            this.establishTime = establishTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStartCoach() {
            return startCoach;
        }

        public void setStartCoach(String startCoach) {
            this.startCoach = startCoach;
        }

        public String getStartStudent() {
            return starStudent;
        }

        public void setStartStudent(String starStudent) {
            this.starStudent = starStudent;
        }

        public String getHonor() {
            return honor;
        }

        public void setHonor(String honor) {
            this.honor = honor;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getVenueGrade() {
            return venueGrade;
        }

        public void setVenueGrade(String venueGrade) {
            this.venueGrade = venueGrade;
        }

        public String getPlayerNumGrade() {
            return playerNumGrade;
        }

        public void setPlayerNumGrade(String playerNumGrade) {
            this.playerNumGrade = playerNumGrade;
        }

        public String getPlayerStrengthGrade() {
            return playerStrengthGrade;
        }

        public void setPlayerStrengthGrade(String playerStrengthGrade) {
            this.playerStrengthGrade = playerStrengthGrade;
        }

        public String getCoachTeamGrade() {
            return coachTeamGrade;
        }

        public void setCoachTeamGrade(String coachTeamGrade) {
            this.coachTeamGrade = coachTeamGrade;
        }

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getWinSum() {
            return winSum;
        }

        public void setWinSum(int winSum) {
            this.winSum = winSum;
        }

        public int getFailSum() {
            return failSum;
        }

        public void setFailSum(int failSum) {
            this.failSum = failSum;
        }

        public int getKoSum() {
            return koSum;
        }

        public void setKoSum(int koSum) {
            this.koSum = koSum;
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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
