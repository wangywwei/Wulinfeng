package com.hxwl.wlf3.bean3;

import java.io.Serializable;
import java.util.List;

public class Pinlin3Bean {

    /**
     * code : 1000
     * message : 评论列表
     * data : [{"commentList":[{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":123,"type":1,"commentTime":"2018-04-26 03:18","userId":1,"content":"22222","favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":124}],"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 03:18","userId":1,"content":"1111","favourList":[],"favourNum":0,"commentNum":1,"isFavour":0,"isGodComment":0,"id":123},{"commentList":[{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":119,"type":1,"commentTime":"2018-04-26 03:17","userId":1,"content":"3432432","favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":122},{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":119,"type":1,"commentTime":"2018-04-26 03:08","userId":1,"content":"6454645","favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":121},{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":119,"type":1,"commentTime":"2018-04-26 03:07","userId":1,"content":"11111555555555555","favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":120}],"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 03:07","userId":1,"content":"11111555555555555","favourList":[{"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","userId":1}],"favourNum":1,"commentNum":3,"isFavour":1,"isGodComment":0,"id":119},{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 03:07","userId":1,"content":"11111","favourList":[],"favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":118},{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 02:57","userId":1,"content":"5555555","favourList":[],"favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":115},{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 02:56","userId":1,"content":"111111","favourList":[],"favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":114},{"commentList":[{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":112,"type":1,"commentTime":"2018-04-26 02:52","userId":1,"content":"3333333333333333333","favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":113}],"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 02:52","userId":1,"content":"3333333333333333333","favourList":[],"favourNum":0,"commentNum":1,"isFavour":0,"isGodComment":0,"id":112},{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 02:51","userId":1,"content":"222222222222222222","favourList":[],"favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":111},{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 02:51","userId":1,"content":"111111111111","favourList":[],"favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":110},{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 02:50","userId":1,"content":"111111111111","favourList":[],"favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":109},{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","type":1,"commentTime":"2018-04-26 02:50","userId":1,"content":"111111111111","favourList":[],"favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":108}]
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
         * commentList : [{"commentList":null,"targetId":1,"headImg":"2018031618300758506267.jpg","nickName":"用户(18101221140)","pid":123,"type":1,"commentTime":"2018-04-26 03:18","userId":1,"content":"22222","favourNum":0,"commentNum":0,"isFavour":0,"isGodComment":0,"id":124}]
         * targetId : 1
         * headImg : 2018031618300758506267.jpg
         * nickName : 用户(18101221140)
         * type : 1
         * commentTime : 2018-04-26 03:18
         * userId : 1
         * content : 1111
         * favourList : []
         * favourNum : 0
         * commentNum : 1
         * isFavour : 0
         * isGodComment : 0
         * id : 123
         */

        private String targetId;
        private String headImg;
        private String nickName;
        private int type;
        private String commentTime;
        private String userId;
        private String content;
        private int favourNum;
        private int commentNum;
        private int isFavour;
        private int isGodComment;
        private String id;
        private List<CommentListBean> commentList;
        private List<?> favourList;

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

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

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getIsFavour() {
            return isFavour;
        }

        public void setIsFavour(int isFavour) {
            this.isFavour = isFavour;
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

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public List<?> getFavourList() {
            return favourList;
        }

        public void setFavourList(List<?> favourList) {
            this.favourList = favourList;
        }

        public static class CommentListBean {
            /**
             * commentList : null
             * targetId : 1
             * headImg : 2018031618300758506267.jpg
             * nickName : 用户(18101221140)
             * pid : 123
             * type : 1
             * commentTime : 2018-04-26 03:18
             * userId : 1
             * content : 22222
             * favourNum : 0
             * commentNum : 0
             * isFavour : 0
             * isGodComment : 0
             * id : 124
             */

            private Object commentList;
            private String targetId;
            private String headImg;
            private String nickName;
            private String pid;
            private int type;
            private String commentTime;
            private String userId;
            private String content;
            private int favourNum;
            private int commentNum;
            private int isFavour;
            private int isGodComment;
            private String id;
            private String referUserNickName;
            private String referUserId;

            public String getReferUserNickName() {
                return referUserNickName;
            }

            public void setReferUserNickName(String referUserNickName) {
                this.referUserNickName = referUserNickName;
            }

            public String getReferUserId() {
                return referUserId;
            }

            public void setReferUserId(String referUserId) {
                this.referUserId = referUserId;
            }

            public Object getCommentList() {
                return commentList;
            }

            public void setCommentList(Object commentList) {
                this.commentList = commentList;
            }

            public String getTargetId() {
                return targetId;
            }

            public void setTargetId(String targetId) {
                this.targetId = targetId;
            }

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

            public int getCommentNum() {
                return commentNum;
            }

            public void setCommentNum(int commentNum) {
                this.commentNum = commentNum;
            }

            public int getIsFavour() {
                return isFavour;
            }

            public void setIsFavour(int isFavour) {
                this.isFavour = isFavour;
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
        }
    }
}
