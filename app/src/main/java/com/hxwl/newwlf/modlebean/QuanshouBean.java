package com.hxwl.newwlf.modlebean;

import android.support.annotation.NonNull;

import com.hxwl.newwlf.home.home.follow.PinYinUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class QuanshouBean {

    /**
     * code : 1000
     * message : 获取选手列表
     * data : [{"playerId":2,"playerImage":"2018012917423296886415.jpg","playerName":"二龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null},{"playerId":3,"playerImage":"20181112.png","playerName":"3龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null},{"playerId":4,"playerImage":"20181112.png","playerName":"4龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null},{"playerId":5,"playerImage":"20181112.png","playerName":"5龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null},{"playerId":6,"playerImage":"20181112.png","playerName":"6龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null},{"playerId":7,"playerImage":"20181112.png","playerName":"7龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null},{"playerId":8,"playerImage":"20181112.png","playerName":"8龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null},{"playerId":9,"playerImage":"20181112.png","playerName":"9龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null},{"playerId":10,"playerImage":"20181113.png","playerName":"10龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null},{"playerId":11,"playerImage":"20181113.png","playerName":"11龙","playerClub":"飞虎俱乐部","weightLevel":"草量级|低于115磅（52kg）","countryName":null,"flagImage":null,"height":null,"birthday":null,"weight":null,"introduction":null,"winNum":null,"koNum":null,"failNum":null,"sumSession":null,"winPercent":null,"failPercent":null,"koPercent":null}]
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

    public static class DataBean implements Comparable<DataBean>{
        /**
         * playerId : 2
         * playerImage : 2018012917423296886415.jpg
         * playerName : 二龙
         * playerClub : 飞虎俱乐部
         * weightLevel : 草量级|低于115磅（52kg）
         * countryName : null
         * flagImage : null
         * height : null
         * birthday : null
         * weight : null
         * introduction : null
         * winNum : null
         * koNum : null
         * failNum : null
         * sumSession : null
         * winPercent : null
         * failPercent : null
         * koPercent : null
         */
        private String mPinYin;

        public String getmPinYin() {
            return mPinYin;
        }

        public void setmPinYin(String mPinYin) {
            this.mPinYin = mPinYin;
        }

        private String playerId;
        private String playerImage;
        private String playerName;
        private String playerClub;
        private String weightLevel;
        private String countryName;
        private String flagImage;
        private String height;
        private String birthday;
        private String weight;
        private String introduction;
        private Object winNum;
        private Object koNum;
        private Object failNum;
        private Object sumSession;
        private Object winPercent;
        private Object failPercent;
        private Object koPercent;
        private int userIsFollow;

        public int getUserIsFollow() {
            return userIsFollow;
        }

        public void setUserIsFollow(int userIsFollow) {
            this.userIsFollow = userIsFollow;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getPlayerImage() {
            return playerImage;
        }

        public void setPlayerImage(String playerImage) {
            this.playerImage = playerImage;

        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
            setmPinYin(PinYinUtils.getPinYin(playerName));
        }

        public String getPlayerClub() {
            return playerClub;
        }

        public void setPlayerClub(String playerClub) {
            this.playerClub = playerClub;
        }

        public String getWeightLevel() {
            return weightLevel;
        }

        public void setWeightLevel(String weightLevel) {
            this.weightLevel = weightLevel;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getFlagImage() {
            return flagImage;
        }

        public void setFlagImage(String flagImage) {
            this.flagImage = flagImage;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public Object getWinNum() {
            return winNum;
        }

        public void setWinNum(Object winNum) {
            this.winNum = winNum;
        }

        public Object getKoNum() {
            return koNum;
        }

        public void setKoNum(Object koNum) {
            this.koNum = koNum;
        }

        public Object getFailNum() {
            return failNum;
        }

        public void setFailNum(Object failNum) {
            this.failNum = failNum;
        }

        public Object getSumSession() {
            return sumSession;
        }

        public void setSumSession(Object sumSession) {
            this.sumSession = sumSession;
        }

        public Object getWinPercent() {
            return winPercent;
        }

        public void setWinPercent(Object winPercent) {
            this.winPercent = winPercent;
        }

        public Object getFailPercent() {
            return failPercent;
        }

        public void setFailPercent(Object failPercent) {
            this.failPercent = failPercent;
        }

        public Object getKoPercent() {
            return koPercent;
        }

        public void setKoPercent(Object koPercent) {
            this.koPercent = koPercent;
        }

        @Override
        public int compareTo(@NonNull DataBean o) {
            return getmPinYin().compareTo(o.getmPinYin());
        }
    }
}
