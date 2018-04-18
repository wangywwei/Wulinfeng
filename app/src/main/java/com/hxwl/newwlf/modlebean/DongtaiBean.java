package com.hxwl.newwlf.modlebean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/2/6.
 */

public class DongtaiBean implements Serializable {

    /**
     * code : 1000
     * message : 动态列表
     * data : [{"messageUserId":null,"messageType":1,"messageUserName":"abc","headImage":"230482304.jpg","updateTime":"1天前","content":"22222222","imageList":"123123.jpg,23424.jpg","videoName":null,"favourNum":4,"commentNum":3,"favourList":[{"headImg":"230482304.jpg","nickName":"abc","userId":1},{"headImg":"230482304.jpg","nickName":"def","userId":2},{"headImg":"230482304.jpg","nickName":"def","userId":3},null],"commentList":[{"id":null,"messageId":1,"userId":3,"userName":"def","referUserId":1,"referUserName":"abc","content":"人生不止眼前的苟且，还有诗和远方","state":null,"updateTime":1517902109000},{"id":null,"messageId":1,"userId":2,"userName":"def","referUserId":null,"referUserName":null,"content":"草色烟光残照里，无言谁会凭栏意。","state":null,"updateTime":1517888983000},{"id":null,"messageId":1,"userId":1,"userName":"abc","referUserId":null,"referUserName":null,"content":"伫倚危楼风细细，望极春愁，黯黯生天际。","state":null,"updateTime":1517888966000}]}]
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
         * messageUserId : null
         * messageType : 1
         * messageUserName : abc
         * headImage : 230482304.jpg
         * updateTime : 1天前
         * content : 22222222
         * imageList : 123123.jpg,23424.jpg
         * videoName : null
         * favourNum : 4
         * commentNum : 3
         * favourList : [{"headImg":"230482304.jpg","nickName":"abc","userId":1},{"headImg":"230482304.jpg","nickName":"def","userId":2},{"headImg":"230482304.jpg","nickName":"def","userId":3},null]
         * commentList : [{"id":null,"messageId":1,"userId":3,"userName":"def","referUserId":1,"referUserName":"abc","content":"人生不止眼前的苟且，还有诗和远方","state":null,"updateTime":1517902109000},{"id":null,"messageId":1,"userId":2,"userName":"def","referUserId":null,"referUserName":null,"content":"草色烟光残照里，无言谁会凭栏意。","state":null,"updateTime":1517888983000},{"id":null,"messageId":1,"userId":1,"userName":"abc","referUserId":null,"referUserName":null,"content":"伫倚危楼风细细，望极春愁，黯黯生天际。","state":null,"updateTime":1517888966000}]
         */
        private boolean isSelect = false;
        private String videoPreviewImage;
        private int userIsFavourMessage;

        public int getUserIsFavourMessage() {
            return userIsFavourMessage;
        }

        public void setUserIsFavourMessage(int userIsFavourMessage) {
            this.userIsFavourMessage = userIsFavourMessage;
        }

        public String getVideoPreviewImage() {
            return videoPreviewImage;
        }

        public void setVideoPreviewImage(String videoPreviewImage) {
            this.videoPreviewImage = videoPreviewImage;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        private String messageId;

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        private String messageUserId;
        private int messageType;
        private String messageUserName;
        private String headImage;
        private String updateTime;
        private String content;
        private String imageList;
        private String videoName;
        private int favourNum;
        private int commentNum;
        private List<FavourListBean> favourList;
        private List<CommentListBean> commentList;

        public String getMessageUserId() {
            return messageUserId;
        }

        public void setMessageUserId(String messageUserId) {
            this.messageUserId = messageUserId;
        }

        public int getMessageType() {
            return messageType;
        }

        public void setMessageType(int messageType) {
            this.messageType = messageType;
        }

        public String getMessageUserName() {
            return messageUserName;
        }

        public void setMessageUserName(String messageUserName) {
            this.messageUserName = messageUserName;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImageList() {
            return imageList;
        }

        public void setImageList(String imageList) {
            this.imageList = imageList;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
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

        public List<FavourListBean> getFavourList() {
            return favourList;
        }

        public void setFavourList(List<FavourListBean> favourList) {
            this.favourList = favourList;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class FavourListBean implements Serializable {
            /**
             * headImg : 230482304.jpg
             * nickName : abc
             * userId : 1
             */

            private String nickName;
            private String userId;

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

        public static class CommentListBean implements Serializable {
            /**
             * id : null
             * messageId : 1
             * userId : 3
             * userName : def
             * referUserId : 1
             * referUserName : abc
             * content : 人生不止眼前的苟且，还有诗和远方
             * state : null
             * updateTime : 1517902109000
             */

            private String id;
            private String messageId;
            private String userId;
            private String userName;
            private String referUserId;
            private String referUserName;
            private String content;
            private String state;
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

            public String getState() {
                return state;
            }

            public void setState(String state) {
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
