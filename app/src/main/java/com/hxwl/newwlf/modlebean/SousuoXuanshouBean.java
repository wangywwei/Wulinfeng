package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class SousuoXuanshouBean {

    /**
     * code : 1000
     * message : 请求成功
     * data : [{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181113.png","playerName":"14龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"},{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181112.png","playerName":"7龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"},{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181112.png","playerName":"6龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"},{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181112.png","playerName":"9龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"},{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181112.png","playerName":"4龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"},{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181112.png","playerName":"3龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"},{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181112.png","playerName":"8龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"},{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181113.png","playerName":"11龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"},{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181113.png","playerName":"13龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"},{"countryName":"中国","weight":"0","headImg":"http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181113.png","playerName":"10龙","countryImg":"http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg"}]
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
         * countryName : 中国
         * weight : 0
         * headImg : http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/20181113.png
         * playerName : 14龙
         * countryImg : http://m.heixiongboji.com/asset/images/guoqi/57c550fa68c67.jpg
         */
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        private String countryName;
        private String weight;
        private String headImg;
        private String playerName;
        private String countryImg;

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
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

        public String getCountryImg() {
            return countryImg;
        }

        public void setCountryImg(String countryImg) {
            this.countryImg = countryImg;
        }
    }
}
