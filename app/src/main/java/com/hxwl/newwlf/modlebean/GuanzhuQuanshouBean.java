package com.hxwl.newwlf.modlebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 */

public class GuanzhuQuanshouBean implements Serializable{

    /**
     * code : 1000
     * message : 用户关注选手列表
     * data : [{"playerName":"13龙","playerId":13,"playerHeadImage":"20181113.png"},{"playerName":"12龙","playerId":12,"playerHeadImage":"20181113.png"},{"playerName":"一龙","playerId":1,"playerHeadImage":"20181112.png"}]
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
         * playerName : 13龙
         * playerId : 13
         * playerHeadImage : 20181113.png
         */
        private boolean xuanzhong;

        public boolean isXuanzhong() {
            return xuanzhong;
        }

        public void setXuanzhong(boolean xuanzhong) {
            this.xuanzhong = xuanzhong;
        }

        private String playerName;
        private String playerId;
        private String playerHeadImage;
        private int userIsFollow=1;

        public int getUserIsFollow() {
            return userIsFollow;
        }

        public void setUserIsFollow(int userIsFollow) {
            this.userIsFollow = userIsFollow;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getPlayerHeadImage() {
            return playerHeadImage;
        }

        public void setPlayerHeadImage(String playerHeadImage) {
            this.playerHeadImage = playerHeadImage;
        }
    }
}
