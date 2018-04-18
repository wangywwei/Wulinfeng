package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by w1565 on 2018/2/28.
 */

public class GenduoHuifuBean {

    /**
     * code : 1000
     * message : 一级评论回复列表
     * data : {"userIsFavour":2,"favourNum":0,"replyList":[{"id":147,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802111000},{"id":146,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802109000},{"id":145,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802105000},{"id":144,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"1111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802103000},{"id":143,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"11","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802100000},{"id":142,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802096000},{"id":141,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802093000}],"favourList":[],"replyNum":7}
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
         * userIsFavour : 2
         * favourNum : 0
         * replyList : [{"id":147,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802111000},{"id":146,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802109000},{"id":145,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802105000},{"id":144,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"1111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802103000},{"id":143,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"11","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802100000},{"id":142,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802096000},{"id":141,"pid":140,"newsId":39,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"userHeaderImage":null,"content":"111","state":null,"favourNum":0,"replyNum":null,"isGodComment":null,"updateTime":1519802093000}]
         * favourList : []
         * replyNum : 7
         */

        private int userIsFavour;
        private int favourNum;
        private int replyNum;
        private List<ReplyListBean> replyList;
        private List<?> favourList;

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

        public List<?> getFavourList() {
            return favourList;
        }

        public void setFavourList(List<?> favourList) {
            this.favourList = favourList;
        }

        public static class ReplyListBean {
            /**
             * id : 147
             * pid : 140
             * newsId : 39
             * userId : 23
             * userName : 用户(18101221140)
             * referUserId : null
             * referUserName : null
             * userHeaderImage : null
             * content : 111
             * state : null
             * favourNum : 0
             * replyNum : null
             * isGodComment : null
             * updateTime : 1519802111000
             */

            private int id;
            private int pid;
            private int newsId;
            private int userId;
            private String userName;
            private String referUserId;
            private String referUserName;
            private String userHeaderImage;
            private String content;
            private int state;
            private int favourNum;
            private int replyNum;
            private int isGodComment;
            private long updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getNewsId() {
                return newsId;
            }

            public void setNewsId(int newsId) {
                this.newsId = newsId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
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

            public String getUserHeaderImage() {
                return userHeaderImage;
            }

            public void setUserHeaderImage(String userHeaderImage) {
                this.userHeaderImage = userHeaderImage;
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

            public void setState(int  state) {
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

            public int getIsGodComment() {
                return isGodComment;
            }

            public void setIsGodComment(int isGodComment) {
                this.isGodComment = isGodComment;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
