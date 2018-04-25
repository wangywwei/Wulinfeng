package com.hxwl.wlf3.bean3;

import java.io.Serializable;
import java.util.List;

public class Pinlin3Bean {

    /**
     * code : 1000
     * message : 评论列表
     * data : [{"targetId":1,"nickName":"用户(18612697861)","pid":0,"replyList":[{"targetId":1,"referUserId":2,"nickName":"用户(15910549092)","pid":68,"replyList":null,"type":1,"commentTime":"2018-04-25 09:40","userId":14,"content":"2222222","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":70,"referUserNickName":"用户(18612697861)"},{"targetId":1,"referUserId":2,"nickName":"用户(15910549092)","pid":68,"replyList":null,"type":1,"commentTime":"2018-04-25 09:39","userId":14,"content":"2222222","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":69,"referUserNickName":"用户(18612697861)"}],"type":1,"commentTime":"2018-04-24 06:03","userId":2,"content":"Conding天使马晓培","favourList":[],"favourNum":1,"isFavour":0,"replyNum":2,"isGodComment":0,"id":68},{"targetId":1,"nickName":"用户(15910549092)","pid":0,"replyList":null,"type":1,"commentTime":"2018-04-24 05:34","userId":14,"content":"2222222","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":54},{"targetId":1,"nickName":"用户(15910549092)","pid":0,"replyList":null,"type":1,"commentTime":"2018-04-24 05:34","userId":14,"content":"2222222","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":53},{"targetId":1,"nickName":"用户(15910549092)","pid":0,"replyList":null,"type":1,"commentTime":"2018-04-24 05:33","userId":14,"content":"2222222","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":51},{"targetId":1,"nickName":"用户(15910549092)","pid":0,"replyList":null,"type":1,"commentTime":"2018-04-24 05:31","userId":14,"content":"2222222","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":50},{"targetId":1,"nickName":"用户(15910549092)","pid":0,"replyList":null,"type":1,"commentTime":"2018-04-24 05:30","userId":14,"content":"12313","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":49},{"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":0,"replyList":null,"type":1,"commentTime":"2018-04-23 11:01","userId":1,"content":"Conding天使1","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":46},{"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":0,"replyList":null,"type":1,"commentTime":"2018-04-20 06:24","userId":1,"content":"Conding天使1","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":43},{"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":0,"replyList":null,"type":1,"commentTime":"2018-04-20 05:25","userId":1,"content":"Conding天使1","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":42},{"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":0,"replyList":[{"targetId":1,"nickName":"用户(18612697861)","pid":1,"replyList":null,"type":1,"commentTime":"2018-04-25 11:03","userId":2,"content":"回复王毅伟referUserId不传","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":71},{"targetId":1,"referUserId":1,"nickName":"用户(18612697861)","pid":1,"replyList":null,"type":1,"commentTime":"2018-04-23 11:04","userId":2,"content":"2回复Conding天使","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":48,"referUserNickName":"用户(18101221140)"},{"targetId":1,"referUserId":1,"nickName":"用户(18612697861)","pid":1,"replyList":null,"type":1,"commentTime":"2018-04-23 11:02","userId":2,"content":"2回复Conding天使","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":47,"referUserNickName":"用户(18101221140)"},{"targetId":1,"referUserId":1,"nickName":"用户(18612697861)","pid":1,"replyList":null,"type":1,"commentTime":"2018-04-20 06:25","userId":2,"content":"2回复Conding天使","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":45,"referUserNickName":"用户(18101221140)"},{"targetId":1,"referUserId":1,"nickName":"用户(18612697861)","pid":1,"replyList":null,"type":1,"commentTime":"2018-04-20 06:24","userId":2,"content":"2回复Conding天使","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":44,"referUserNickName":"用户(18101221140)"}],"type":1,"commentTime":"2018-04-20 03:54","userId":1,"content":"Conding天使1","favourList":[],"favourNum":0,"isFavour":1,"replyNum":0,"isGodComment":1,"id":1}]
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

    public static class DataBean implements Serializable {
        /**
         * targetId : 1
         * nickName : 用户(18612697861)
         * pid : 0
         * replyList : [{"targetId":1,"referUserId":2,"nickName":"用户(15910549092)","pid":68,"replyList":null,"type":1,"commentTime":"2018-04-25 09:40","userId":14,"content":"2222222","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":70,"referUserNickName":"用户(18612697861)"},{"targetId":1,"referUserId":2,"nickName":"用户(15910549092)","pid":68,"replyList":null,"type":1,"commentTime":"2018-04-25 09:39","userId":14,"content":"2222222","favourList":[],"favourNum":0,"isFavour":0,"replyNum":0,"isGodComment":0,"id":69,"referUserNickName":"用户(18612697861)"}]
         * type : 1
         * commentTime : 2018-04-24 06:03
         * userId : 2
         * content : Conding天使马晓培
         * favourList : []
         * favourNum : 1
         * isFavour : 0
         * replyNum : 2
         * isGodComment : 0
         * id : 68
         * headImg : 2018031618300758506267.jpg
         */

        private String targetId;
        private String nickName;
        private String pid;
        private int type;
        private String commentTime;
        private String userId;
        private String content;
        private int favourNum;
        private int isFavour;
        private int replyNum;
        private int isGodComment;
        private String id;
        private String headImg;
        private List<ReplyListBean> replyList;
        private List<?> favourList;

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getFavourNum() {
            return favourNum;
        }

        public void setFavourNum(int favourNum) {
            this.favourNum = favourNum;
        }

        public int getIsFavour() {
            return isFavour;
        }

        public void setIsFavour(int isFavour) {
            this.isFavour = isFavour;
        }

        public int getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(int replyNum) {
            this.replyNum = replyNum;
        }

        public int getIsGodComment() {
            return isGodComment;
        }

        public void setIsGodComment(int isGodComment) {
            this.isGodComment = isGodComment;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public List<ReplyListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<ReplyListBean> replyList) {
            this.replyList = replyList;
        }

        public List<?> getFavourList() {
            return favourList;
        }

        public void setFavourList(List<?> favourList) {
            this.favourList = favourList;
        }

        public static class ReplyListBean {
            /**
             * targetId : 1
             * referUserId : 2
             * nickName : 用户(15910549092)
             * pid : 68
             * replyList : null
             * type : 1
             * commentTime : 2018-04-25 09:40
             * userId : 14
             * content : 2222222
             * favourList : []
             * favourNum : 0
             * isFavour : 0
             * replyNum : 0
             * isGodComment : 0
             * id : 70
             * referUserNickName : 用户(18612697861)
             */

            private String targetId;
            private String referUserId;
            private String nickName;
            private String pid;
            private Object replyList;
            private int type;
            private String commentTime;
            private String userId;
            private String content;
            private int favourNum;
            private int isFavour;
            private int replyNum;
            private int isGodComment;
            private String id;
            private String referUserNickName;
            private List<?> favourList;

            public String getTargetId() {
                return targetId;
            }

            public void setTargetId(String targetId) {
                this.targetId = targetId;
            }

            public String getReferUserId() {
                return referUserId;
            }

            public void setReferUserId(String referUserId) {
                this.referUserId = referUserId;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public Object getReplyList() {
                return replyList;
            }

            public void setReplyList(Object replyList) {
                this.replyList = replyList;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getCommentTime() {
                return commentTime;
            }

            public void setCommentTime(String commentTime) {
                this.commentTime = commentTime;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getFavourNum() {
                return favourNum;
            }

            public void setFavourNum(int favourNum) {
                this.favourNum = favourNum;
            }

            public int getIsFavour() {
                return isFavour;
            }

            public void setIsFavour(int isFavour) {
                this.isFavour = isFavour;
            }

            public int getReplyNum() {
                return replyNum;
            }

            public void setReplyNum(int replyNum) {
                this.replyNum = replyNum;
            }

            public int getIsGodComment() {
                return isGodComment;
            }

            public void setIsGodComment(int isGodComment) {
                this.isGodComment = isGodComment;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getReferUserNickName() {
                return referUserNickName;
            }

            public void setReferUserNickName(String referUserNickName) {
                this.referUserNickName = referUserNickName;
            }

            public List<?> getFavourList() {
                return favourList;
            }

            public void setFavourList(List<?> favourList) {
                this.favourList = favourList;
            }
        }
    }
}
