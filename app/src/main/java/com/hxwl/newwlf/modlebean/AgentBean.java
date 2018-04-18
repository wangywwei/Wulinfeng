package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/2/1.
 */

public class AgentBean {

    /**
     * code : 1000
     * message : 省份代理信息
     * data : {"id":1,"agentName":"河南代理","provinceId":16,"provinceName":"河南省","cityId":152,"districtId":1363,"address":"长江大道","mobile":"1876665533","backImage":"5a2102aed276.jpg","logoImage":"5a2102aed276.jpg","intro":"深圳市坦克武术馆是由KF1创始人，香港富德国际博击联盟总裁江富德先生大力支持而成立。学习项目多，重教学，师质力量雄厚。总教练郑岩自幼习武，多次拿冠军的他曾经走出国门精学泰拳，回国后为我市创办了第一家泰拳馆，至力于武术的弘扬和传授，深得武术界人士和体育总局领导的好评，并对期寄予深切的厚望。坦克武馆由深圳体育局认可并且唯一的泰拳培训机构。本健身中心教学宗旨：为每一个学员负责，让你来一次就有一次的收获。","rank":1,"updatetime":1517307969000}
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
         * agentName : 河南代理
         * provinceId : 16
         * provinceName : 河南省
         * cityId : 152
         * districtId : 1363
         * address : 长江大道
         * mobile : 1876665533
         * backImage : 5a2102aed276.jpg
         * logoImage : 5a2102aed276.jpg
         * intro : 深圳市坦克武术馆是由KF1创始人，香港富德国际博击联盟总裁江富德先生大力支持而成立。学习项目多，重教学，师质力量雄厚。总教练郑岩自幼习武，多次拿冠军的他曾经走出国门精学泰拳，回国后为我市创办了第一家泰拳馆，至力于武术的弘扬和传授，深得武术界人士和体育总局领导的好评，并对期寄予深切的厚望。坦克武馆由深圳体育局认可并且唯一的泰拳培训机构。本健身中心教学宗旨：为每一个学员负责，让你来一次就有一次的收获。
         * rank : 1
         * updatetime : 1517307969000
         */

        private int id;
        private String agentName;
        private int provinceId;
        private String provinceName;
        private int cityId;
        private int districtId;
        private String address;
        private String mobile;
        private String backImage;
        private String logoImage;
        private String intro;
        private int rank;
        private long updatetime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAgentName() {
            return agentName;
        }

        public void setAgentName(String agentName) {
            this.agentName = agentName;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(int provinceId) {
            this.provinceId = provinceId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public int getDistrictId() {
            return districtId;
        }

        public void setDistrictId(int districtId) {
            this.districtId = districtId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBackImage() {
            return backImage;
        }

        public void setBackImage(String backImage) {
            this.backImage = backImage;
        }

        public String getLogoImage() {
            return logoImage;
        }

        public void setLogoImage(String logoImage) {
            this.logoImage = logoImage;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public long getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(long updatetime) {
            this.updatetime = updatetime;
        }
    }
}
