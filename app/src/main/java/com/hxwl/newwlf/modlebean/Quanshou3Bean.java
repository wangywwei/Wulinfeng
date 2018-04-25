package com.hxwl.newwlf.modlebean;

import android.support.annotation.NonNull;

import java.util.List;

public class Quanshou3Bean {

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
         * weightLevel : 74
         * isAttention : 0
         * headImg : 592f905c6d965.jpg
         * playerName : 王洪祥
         * clubName : 高大拳搏击俱乐部
         * firstLetter : W
         * playerId : 1
         */

        private int weightLevel;
        private int isAttention;
        private String headImg;
        private String playerName;
        private String clubName;
        private String firstLetter;
        private String playerId;

        public int getWeightLevel() {
            return weightLevel;
        }

        public void setWeightLevel(int weightLevel) {
            this.weightLevel = weightLevel;
        }

        public int getIsAttention() {
            return isAttention;
        }

        public void setIsAttention(int isAttention) {
            this.isAttention = isAttention;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public String getClubName() {
            return clubName;
        }

        public void setClubName(String clubName) {
            this.clubName = clubName;
        }

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        @Override
        public int compareTo(@NonNull DataBean o) {
            return getFirstLetter().compareTo(o.getFirstLetter());
        }
    }
}
