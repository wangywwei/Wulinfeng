package com.hxwl.wulinfeng.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */
public class ShouyeBanner implements Serializable{

    /**
     * status : ok
     * data : ["https://m.heixiongboji.com/asset/images/lunbotu/58743e3bb7f4c.jpg","http://m.heixiongboji.com/asset/images/lunbotu/5879ed49797ab.jpg","https://m.heixiongboji.com/asset/images/lunbotu/5875ce2086b62.jpg","https://m.heixiongboji.com/asset/images/lunbotu/58802c96a73d0.jpg"]
     * data2 : [{"id":"62","type":"6","img":"https://m.heixiongboji.com/asset/images/lunbotu/58743e3bb7f4c.jpg","url":"http://m.heixiongboji.com/index.php/Bet/index","title":"","sort":"1","is_show":"1","time":"1484013115","redirect_about_id":"0","redirect_bankuai":"0"},{"id":"65","type":"6","img":"http://m.heixiongboji.com/asset/images/lunbotu/5879ed49797ab.jpg","url":"http://m.heixiongboji.com/index.php/Zhibo/video/saishi_id/13/saicheng_id/433","title":"武林风功夫盛典","sort":"999","is_show":"1","time":"1484793145","redirect_about_id":"0","redirect_bankuai":"0"},{"id":"68","type":"6","img":"https://m.heixiongboji.com/asset/images/lunbotu/5875ce2086b62.jpg","url":"http://m.heixiongboji.com/index.php/Zhibo/video/saishi_id/14/saicheng_id/459","title":"","sort":"999","is_show":"1","time":"1484793100","redirect_about_id":"0","redirect_bankuai":"0"},{"id":"69","type":"6","img":"https://m.heixiongboji.com/asset/images/lunbotu/58802c96a73d0.jpg","url":"http://m.heixiongboji.com/index.php/Zhibo/video/saishi_id/17/saicheng_id/361","title":"昆仑决三亚总决赛","sort":"999","is_show":"1","time":"1484795030","redirect_about_id":"0","redirect_bankuai":"11"}]
     */

    private String status;
    private List<String> data;
    /**
     * id : 62
     * type : 6
     * img : https://m.heixiongboji.com/asset/images/lunbotu/58743e3bb7f4c.jpg
     * url : http://m.heixiongboji.com/index.php/Bet/index
     * title :
     * sort : 1
     * is_show : 1
     * time : 1484013115
     * redirect_about_id : 0
     * redirect_bankuai : 0
     */

    private List<Data2Entity> data2;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public void setData2(List<Data2Entity> data2) {
        this.data2 = data2;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getData() {
        return data;
    }

    public List<Data2Entity> getData2() {
        return data2;
    }

    public static class Data2Entity implements Serializable{
        private String id;
        private String type;
        private String img;
        private String url;
        private String title;
        private String sort;
        private String is_show;
        private String time;
        private String redirect_about_id;
        private String redirect_bankuai;
        private String label;
        private String android_url;

        public String getAndroid_url() {
            return android_url;
        }

        public void setAndroid_url(String android_url) {
            this.android_url = android_url;
        }
        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setRedirect_about_id(String redirect_about_id) {
            this.redirect_about_id = redirect_about_id;
        }

        public void setRedirect_bankuai(String redirect_bankuai) {
            this.redirect_bankuai = redirect_bankuai;
        }

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getImg() {
            return img;
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }

        public String getSort() {
            return sort;
        }

        public String getIs_show() {
            return is_show;
        }

        public String getTime() {
            return time;
        }

        public String getRedirect_about_id() {
            return redirect_about_id;
        }

        public String getRedirect_bankuai() {
            return redirect_bankuai;
        }
    }
}
