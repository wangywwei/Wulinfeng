package com.hxwl.wulinfeng.bean;

/**
 * Created by 郭陈晨 on 2016/10/16.
 */
public class ImageBean {

    /**
     * _action_ : Bbs
     * _method_ : fatie
     * contents : 啊啊啊啊啊
     * loginKey : 4ab494ee5ac3cb256c964fe503c0c5bb
     * page : 0
     * saishiId :
     * title : 啊啊啊啊
     * type : 1
     * uid : 15359
     */

    private ParamBean param;
    /**
     * param : {"_action_":"Bbs","_method_":"fatie","contents":"啊啊啊啊啊","loginKey":"4ab494ee5ac3cb256c964fe503c0c5bb","page":"0","saishiId":"","title":"啊啊啊啊","type":"1","uid":"15359"}
     * state : ok
     * zhuId : 835
     */

    private String status;
    private String zhuId;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ParamBean getParam() {
        return param;
    }

    public void setParam(ParamBean param) {
        this.param = param;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getZhuId() {
        return zhuId;
    }

    public void setZhuId(String zhuId) {
        this.zhuId = zhuId;
    }

    public static class ParamBean {
        private String _action_;
        private String _method_;
        private String contents;
        private String loginKey;
        private String page;
        private String saishiId;
        private String title;
        private String type;
        private String uid;

        public String get_action_() {
            return _action_;
        }

        public void set_action_(String _action_) {
            this._action_ = _action_;
        }

        public String get_method_() {
            return _method_;
        }

        public void set_method_(String _method_) {
            this._method_ = _method_;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getLoginKey() {
            return loginKey;
        }

        public void setLoginKey(String loginKey) {
            this.loginKey = loginKey;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getSaishiId() {
            return saishiId;
        }

        public void setSaishiId(String saishiId) {
            this.saishiId = saishiId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
