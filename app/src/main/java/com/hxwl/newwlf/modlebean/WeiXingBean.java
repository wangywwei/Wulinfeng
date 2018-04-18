package com.hxwl.newwlf.modlebean;

/**
 * Created by Administrator on 2018/3/1.
 */

public class WeiXingBean {

    /**
     * access_token : 7_D5lKQFbGvaBXJ9uZ6al1LfjLamPzD8R_gXm_zXNsAY6iiNyxefQFKn679-IV6akxzHtEOcixYdNCn9l9V6Q0rj3eb1q75WZ5h8kfpJ6gskU
     * expires_in : 7200
     * refresh_token : 7_l2ycRttD7L9HCMMc3aRCdBDKF-RKNkjM3excvVh5pXErUobchpjRAnQZapa5Ob3XiPR50yo7FKMMaJsqJVtKxiiuUQvXa4v6KdZdWuPtEfA
     * openid : oCQzR1Kao6dKPksxrgSDweK0p1d0
     * scope : snsapi_userinfo
     * unionid : o5pIYwZkuYLNR3YdRpYeL3qJ6uTw
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;
    private String errcode;
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
