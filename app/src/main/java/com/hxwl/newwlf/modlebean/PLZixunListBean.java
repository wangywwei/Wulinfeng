package com.hxwl.newwlf.modlebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/10.
 */

public class PLZixunListBean implements Serializable{

    /**
     * code : 1000
     * message : 资讯评论列表成功
     * data : [{"content":"从头越，苍山如海，残阳如血。","favourNum":20,"replyList":[{"id":5,"pid":1,"newsId":1,"userId":2,"userName":"def","referUserId":3,"referUserName":"sdfsdf","userHeaderImage":null,"content":"走马观花","state":1,"favourNum":46777,"replyNum":235677,"isGodComment":2,"updateTime":1518144449000},{"id":6,"pid":1,"newsId":1,"userId":3,"userName":"sdfsdf","referUserId":4,"referUserName":"俏江南","userHeaderImage":null,"content":"故乡","state":1,"favourNum":366,"replyNum":422,"isGodComment":1,"updateTime":1518144485000}],"headImg":"20181112.png","commentTime":"2天前","updateTime":1518073674000,"userId":1,"userName":"abc","commentId":1,"replyNum":20},{"content":"long long ago","replyList":[],"headImg":"20181112.png","commentTime":"2天前","updateTime":1518073674000,"userId":1,"userName":"abc","commentId":8}]
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
         * content : 从头越，苍山如海，残阳如血。
         * favourNum : 20
         * replyList : [{"id":5,"pid":1,"newsId":1,"userId":2,"userName":"def","referUserId":3,"referUserName":"sdfsdf","userHeaderImage":null,"content":"走马观花","state":1,"favourNum":46777,"replyNum":235677,"isGodComment":2,"updateTime":1518144449000},{"id":6,"pid":1,"newsId":1,"userId":3,"userName":"sdfsdf","referUserId":4,"referUserName":"俏江南","userHeaderImage":null,"content":"故乡","state":1,"favourNum":366,"replyNum":422,"isGodComment":1,"updateTime":1518144485000}]
         * headImg : 20181112.png
         * commentTime : 2天前
         * updateTime : 1518073674000
         * userId : 1
         * userName : abc
         * commentId : 1
         * replyNum : 20
         */
        private int userIsFavour;
        private String newsId;
        private String title;
        private String newsType;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNewsType() {
            return newsType;
        }

        public void setNewsType(String newsType) {
            this.newsType = newsType;
        }

        public String getNewsId() {
            return newsId;
        }

        public void setNewsId(String newsId) {
            this.newsId = newsId;
        }

        public int getUserIsFavour() {
            return userIsFavour;
        }

        public void setUserIsFavour(int userIsFavour) {
            this.userIsFavour = userIsFavour;
        }

        private String content;
        private int favourNum;
        private String headImg;
        private String commentTime;
        private long updateTime;
        private String userId;
        private int isGodComment;

        public int getIsGodComment() {
            return isGodComment;
        }

        public void setIsGodComment(int isGodComment) {
            this.isGodComment = isGodComment;
        }

        private String userName;
        private String commentId;
        private int replyNum;
        private List<ReplyListBean> replyList;

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

        public static class ReplyListBean implements Serializable{
            /**
             * id : 5
             * pid : 1
             * newsId : 1
             * userId : 2
             * userName : def
             * referUserId : 3
             * referUserName : sdfsdf
             * userHeaderImage : null
             * content : 走马观花
             * state : 1
             * favourNum : 46777
             * replyNum : 235677
             * isGodComment : 2
             * updateTime : 1518144449000
             */
            private String id;
            private String pid;
            private String newsId;
            private String userId;
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

            public String getNewsId() {
                return newsId;
            }

            public void setNewsId(String newsId) {
                this.newsId = newsId;
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
