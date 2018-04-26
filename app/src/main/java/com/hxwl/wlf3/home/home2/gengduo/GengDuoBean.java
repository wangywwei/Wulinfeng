package com.hxwl.wlf3.home.home2.gengduo;

import java.util.List;

/**
 * Created by Administrator on 2018/4/26.
 */

public class GengDuoBean {


    /**
     * code : 1000
     * message : 获取活动列表成功
     * data : [{"start_time":1524243600000,"image":"2018041811065126067555.jpg","create_time":1524020831000,"is_recommend":1,"name":"4.18活动","end_time":1524330000000,"id":20,"state":1,"schedule_id":2,"url":"https://www.baidu.com/","is_show":1},{"start_time":1524848400000,"image":"2018041718043757063107.jpg","update_time":1523959482000,"create_time":1523959415000,"is_recommend":1,"name":"活动3","end_time":1524934800000,"id":17,"state":1,"schedule_id":2,"url":"https://www.baidu.com/","is_show":1}]
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
         * start_time : 1524243600000
         * image : 2018041811065126067555.jpg
         * create_time : 1524020831000
         * is_recommend : 1
         * name : 4.18活动
         * end_time : 1524330000000
         * id : 20
         * state : 1
         * schedule_id : 2
         * url : https://www.baidu.com/
         * is_show : 1
         * update_time : 1523959482000
         */

        private long start_time;
        private String image;
        private long create_time;
        private int is_recommend;
        private String name;
        private long end_time;
        private int id;
        private int state;
        private int schedule_id;
        private String url;
        private int is_show;
        private long update_time;

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public int getIs_recommend() {
            return is_recommend;
        }

        public void setIs_recommend(int is_recommend) {
            this.is_recommend = is_recommend;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getSchedule_id() {
            return schedule_id;
        }

        public void setSchedule_id(int schedule_id) {
            this.schedule_id = schedule_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public long getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(long update_time) {
            this.update_time = update_time;
        }
    }
}

