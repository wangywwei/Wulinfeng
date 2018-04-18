package com.hxwl.newwlf.modlebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class LeavingBean implements Serializable{

    /**
     * code : 1000
     * message : 选手评论列表
     * data : [{"content":"sfdgfdgdf","userIsFavour":1,"favourNum":1,"replyList":[{"id":16,"playerId":2,"userId":9,"userName":"回眸","commentPid":15,"referUserId":null,"referUserName":null,"content":"㡑","state":null,"favourNum":null,"replyNum":null,"updateTime":1519555322000}],"headImg":"20181112.png","commentTime":"2天前","updateTime":1519383420000,"userId":1,"userName":"abc","favourList":[{"headImg":"2018022418473002863982.jpg","nickName":"回眸","userId":9}],"commentId":15,"replyNum":1},{"content":"63254254","userIsFavour":2,"favourNum":0,"replyList":[],"headImg":"20181112.png","commentTime":"2天前","updateTime":1519383150000,"userId":1,"userName":"abc","favourList":[],"commentId":14,"replyNum":0},{"content":"bvcbcv","userIsFavour":1,"favourNum":1,"replyList":[{"id":10,"playerId":2,"userId":1,"userName":"abc","commentPid":9,"referUserId":null,"referUserName":null,"content":"sdsdf","state":null,"favourNum":null,"replyNum":null,"updateTime":1519358129000}],"headImg":"20181112.png","commentTime":"2天前","updateTime":1519358122000,"userId":1,"userName":"abc","favourList":[{"headImg":"2018022418473002863982.jpg","nickName":"回眸","userId":9}],"commentId":9,"replyNum":1},{"content":"dsadasd","userIsFavour":2,"favourNum":0,"replyList":[],"headImg":"20181112.png","commentTime":"2天前","updateTime":1519356788000,"userId":1,"userName":"abc","favourList":[],"commentId":8,"replyNum":0}]
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
         * content : sfdgfdgdf
         * userIsFavour : 1
         * favourNum : 1
         * replyList : [{"id":16,"playerId":2,"userId":9,"userName":"回眸","commentPid":15,"referUserId":null,"referUserName":null,"content":"㡑","state":null,"favourNum":null,"replyNum":null,"updateTime":1519555322000}]
         * headImg : 20181112.png
         * commentTime : 2天前
         * updateTime : 1519383420000
         * userId : 1
         * userName : abc
         * favourList : [{"headImg":"2018022418473002863982.jpg","nickName":"回眸","userId":9}]
         * commentId : 15
         * replyNum : 1
         */

        private String content;
        private int userIsFavour;
        private int favourNum;
        private String headImg;
        private String commentTime;
        private long updateTime;
        private String userId;
        private String userName;
        private String commentId;
        private int replyNum;
        private List<ReplyListBean> replyList;
        private List<FavourListBean> favourList;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUserIsFavour() {
            return userIsFavour;
        }

        public void setUserIsFavour(int userIsFavour) {
            this.userIsFavour = userIsFavour;
        }

        public int getFavourNum() {
            return favourNum;
        }

        public void setFavourNum(int favourNum) {
            this.favourNum = favourNum;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public int getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(int replyNum) {
            this.replyNum = replyNum;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public List<FavourListBean> getFavourList() {
            return favourList;
        }

        public void setFavourList(List<FavourListBean> favourList) {
            this.favourList = favourList;
        }

        public static class ReplyListBean implements Serializable{
            /**
             * id : 16
             * playerId : 2
             * userId : 9
             * userName : 回眸
             * commentPid : 15
             * referUserId : null
             * referUserName : null
             * content : 㡑
             * state : null
             * favourNum : null
             * replyNum : null
             * updateTime : 1519555322000
             */

            private String id;
            private String playerId;
            private String userId;
            private String userName;
            private String commentPid;
            private String referUserId;
            private String referUserName;
            private String content;
            private int state;
            private int favourNum;
            private int replyNum;
            private long updateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPlayerId() {
                return playerId;
            }

            public void setPlayerId(String playerId) {
                this.playerId = playerId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getCommentPid() {
                return commentPid;
            }

            public void setCommentPid(String commentPid) {
                this.commentPid = commentPid;
            }

            public String getReferUserId() {
                return referUserId;
            }

            public void setReferUserId(String referUserId) {
                this.referUserId = referUserId;
            }

            public String getReferUserName() {
                return referUserName;
            }

            public void setReferUserName(String referUserName) {
                this.referUserName = referUserName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getFavourNum() {
                return favourNum;
            }

            public void setFavourNum(int favourNum) {
                this.favourNum = favourNum;
            }

            public int getReplyNum() {
                return replyNum;
            }

            public void setReplyNum(int replyNum) {
                this.replyNum = replyNum;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }
        }

        public static class FavourListBean {
            /**
             * headImg : 2018022418473002863982.jpg
             * nickName : 回眸
             * userId : 9
             */

            private String headImg;
            private String nickName;
            private String userId;

            public String getHeadImg() {
                return headImg;
            }

            public void setHeadImg(String headImg) {
                this.headImg = headImg;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
