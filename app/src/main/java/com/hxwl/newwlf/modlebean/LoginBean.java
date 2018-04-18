package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/1/26.
 */

public class LoginBean {

    /**
     * code : 1000
     * message : 验证成功
     * data : {"id":9,"phone":"18101221140","nickName":null,"headImg":null,"isDisable":null,"weixinUnionId":null,"signature":"eJxFkFFPgzAUhf8LrzOzLa0w39gwDgNxMsnYE6m0QCOUpquEzfjfV3HE1*-LzTnnfjvv8X5JlRKsoKZwNXMeHeDcTZiPSmhe0MpwbTEkhCAAZjtwfRK9tAIBSCByAfiXgnFpRCX*Dn0IIEIQ4lmfRG158nTcRG*bscvHHLOcRpdLHSbhy2s7pIegiRCPMdpl3haDIMx9V9VREySt3JXVeYHSxSrrvPE*a1TZ8nUq*6P3odehd3gmetvEQzKHsc9iWvhbBduODz5B*CaN6Pi0Da4wxrbkjdOy7L*kKcxZ8eklP1dHZFdm","updateTime":null,"createTime":1519401600000,"signTime":1519401600000}
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
         * id : 9
         * phone : 18101221140
         * nickName : null
         * headImg : null
         * isDisable : null
         * weixinUnionId : null
         * signature : eJxFkFFPgzAUhf8LrzOzLa0w39gwDgNxMsnYE6m0QCOUpquEzfjfV3HE1*-LzTnnfjvv8X5JlRKsoKZwNXMeHeDcTZiPSmhe0MpwbTEkhCAAZjtwfRK9tAIBSCByAfiXgnFpRCX*Dn0IIEIQ4lmfRG158nTcRG*bscvHHLOcRpdLHSbhy2s7pIegiRCPMdpl3haDIMx9V9VREySt3JXVeYHSxSrrvPE*a1TZ8nUq*6P3odehd3gmetvEQzKHsc9iWvhbBduODz5B*CaN6Pi0Da4wxrbkjdOy7L*kKcxZ8eklP1dHZFdm
         * updateTime : null
         * createTime : 1519401600000
         * signTime : 1519401600000
         */
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        private int id;
        private String phone;
        private String nickName;
        private String headImg;
        private String isDisable;
        private String weixinUnionId;
        private String signature;
        private String updateTime;
        private long createTime;
        private long signTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getIsDisable() {
            return isDisable;
        }

        public void setIsDisable(String isDisable) {
            this.isDisable = isDisable;
        }

        public String getWeixinUnionId() {
            return weixinUnionId;
        }

        public void setWeixinUnionId(String weixinUnionId) {
            this.weixinUnionId = weixinUnionId;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getSignTime() {
            return signTime;
        }

        public void setSignTime(long signTime) {
            this.signTime = signTime;
        }
    }
}
