package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class DongTaiHuifuBean {

    /**
     * code : 1000
     * message : 动态评论回复列表
     * data : {"userIsFavourMessage":2,"commentNum":7,"favourNum":0,"commentList":[{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"66","state":null,"updateTime":1519887797000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"555","state":null,"updateTime":1519887795000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"44","state":null,"updateTime":1519887792000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"333","state":null,"updateTime":1519887788000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"122","state":null,"updateTime":1519887786000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"11111","state":null,"updateTime":1519887784000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"1111","state":null,"updateTime":1519887781000}]}
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
         * userIsFavourMessage : 2
         * commentNum : 7
         * favourNum : 0
         * commentList : [{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"66","state":null,"updateTime":1519887797000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"555","state":null,"updateTime":1519887795000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"44","state":null,"updateTime":1519887792000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"333","state":null,"updateTime":1519887788000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"122","state":null,"updateTime":1519887786000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"11111","state":null,"updateTime":1519887784000},{"id":null,"messageId":75,"userId":23,"userName":"用户(18101221140)","referUserId":null,"referUserName":null,"content":"1111","state":null,"updateTime":1519887781000}]
         */

        private int userIsFavourMessage;
        private int commentNum;
        private int favourNum;
        private List<CommentListBean> commentList;

        public int getUserIsFavourMessage() {
            return userIsFavourMessage;
        }

        public void setUserIsFavourMessage(int userIsFavourMessage) {
            this.userIsFavourMessage = userIsFavourMessage;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getFavourNum() {
            return favourNum;
        }

        public void setFavourNum(int favourNum) {
            this.favourNum = favourNum;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class CommentListBean {
            /**
             * id : null
             * messageId : 75
             * userId : 23
             * userName : 用户(18101221140)
             * referUserId : null
             * referUserName : null
             * content : 66
             * state : null
             * updateTime : 1519887797000
             */

            private String id;
            private String messageId;
            private String userId;
            private String userName;
            private String referUserId;
            private String referUserName;
            private String content;
            private int state;
            private long updateTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMessageId() {
                return messageId;
            }

            public void setMessageId(String messageId) {
                this.messageId = messageId;
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

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
