package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class DuiZhengPLBean {

    /**
     * code : 1000
     * message : 对阵评论列表
     * data : [{"favourNum":2,"headImg":"","createTime":1521110843000,"userIsFavour":2,"replyNum":2,"commentId":1,"replyList":[{"id":3,"pid":1,"againstId":1,"userId":2,"userName":"用户(18612697861)","referUserId":3,"referUserName":"用户(18910527284)","content":"hello","state":1,"replyNum":0,"createTime":null},{"id":2,"pid":1,"againstId":1,"userId":2,"userName":"用户(18612697861)","referUserId":3,"referUserName":"用户(18910527284)","content":"hello","state":1,"replyNum":0,"createTime":null}],"userName":"回梦一眸","userId":1,"commentTime":"1小时前","content":"Conding天使","favourList":[{"nickName":"用户(18612697861)","userId":2},{"nickName":"用户(18910527284)","userId":3}]}]
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
         * favourNum : 2
         * headImg :
         * createTime : 1521110843000
         * userIsFavour : 2
         * replyNum : 2
         * commentId : 1
         * replyList : [{"id":3,"pid":1,"againstId":1,"userId":2,"userName":"用户(18612697861)","referUserId":3,"referUserName":"用户(18910527284)","content":"hello","state":1,"replyNum":0,"createTime":null},{"id":2,"pid":1,"againstId":1,"userId":2,"userName":"用户(18612697861)","referUserId":3,"referUserName":"用户(18910527284)","content":"hello","state":1,"replyNum":0,"createTime":null}]
         * userName : 回梦一眸
         * userId : 1
         * commentTime : 1小时前
         * content : Conding天使
         * favourList : [{"nickName":"用户(18612697861)","userId":2},{"nickName":"用户(18910527284)","userId":3}]
         */

        private int favourNum;
        private String headImg;
        private long createTime;
        private int userIsFavour;
        private int replyNum;
        private String commentId;
        private String userName;
        private String userId;
        private String commentTime;
        private String content;
        private List<ReplyListBean> replyList;
        private List<FavourListBean> favourList;

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

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getUserIsFavour() {
            return userIsFavour;
        }

        public void setUserIsFavour(int userIsFavour) {
            this.userIsFavour = userIsFavour;
        }

        public int getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(int replyNum) {
            this.replyNum = replyNum;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public static class ReplyListBean {
            /**
             * id : 3
             * pid : 1
             * againstId : 1
             * userId : 2
             * userName : 用户(18612697861)
             * referUserId : 3
             * referUserName : 用户(18910527284)
             * content : hello
             * state : 1
             * replyNum : 0
             * createTime : null
             */

            private String id;
            private String pid;
            private String againstId;
            private String userId;
            private String userName;
            private String referUserId;
            private String referUserName;
            private String content;
            private int state;
            private int replyNum;
            private Object createTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getAgainstId() {
                return againstId;
            }

            public void setAgainstId(String againstId) {
                this.againstId = againstId;
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

            public int getReplyNum() {
                return replyNum;
            }

            public void setReplyNum(int replyNum) {
                this.replyNum = replyNum;
            }

            public Object getCreateTime() {
                return createTime;
            }

            public void setCreateTime(Object createTime) {
                this.createTime = createTime;
            }
        }

        public static class FavourListBean {
            /**
             * nickName : 用户(18612697861)
             * userId : 2
             */

            private String nickName;
            private int userId;

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
