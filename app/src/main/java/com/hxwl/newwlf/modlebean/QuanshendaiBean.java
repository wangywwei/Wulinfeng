package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 */

public class QuanshendaiBean {

    /**
     * code : 1000
     * message : 省份代理信息
     * data : [{"weightName":"草量级|低于115磅（52kg）","playerName":"二龙","playerId":2,"headImage":"2018012917423296886415.jpg","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"3龙","playerId":3,"headImage":"20181112.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"4龙","playerId":4,"headImage":"20181112.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"5龙","playerId":5,"headImage":"20181112.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"6龙","playerId":6,"headImage":"20181112.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"7龙","playerId":7,"headImage":"20181112.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"8龙","playerId":8,"headImage":"20181112.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"9龙","playerId":9,"headImage":"20181112.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"10龙","playerId":10,"headImage":"20181113.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"11龙","playerId":11,"headImage":"20181113.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"12龙","playerId":12,"headImage":"20181113.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"13龙","playerId":13,"headImage":"20181113.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"14龙","playerId":14,"headImage":"20181113.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"15龙","playerId":15,"headImage":"20181113.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"16龙","playerId":16,"headImage":"20181113.png","weightLevel":68},{"weightName":"草量级|低于115磅（52kg）","playerName":"一龙","playerId":1,"headImage":"20181112.png","weightLevel":68}]
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
         * weightName : 草量级|低于115磅（52kg）
         * playerName : 二龙
         * playerId : 2
         * headImage : 2018012917423296886415.jpg
         * weightLevel : 68
         */

        private String weightName;
        private String playerName;
        private int playerId;
        private String headImage;
        private int weightLevel;

        public String getWeightName() {
            return weightName;
        }

        public void setWeightName(String weightName) {
            this.weightName = weightName;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public int getPlayerId() {
            return playerId;
        }

        public void setPlayerId(int playerId) {
            this.playerId = playerId;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public int getWeightLevel() {
            return weightLevel;
        }

        public void setWeightLevel(int weightLevel) {
            this.weightLevel = weightLevel;
        }
    }
}
