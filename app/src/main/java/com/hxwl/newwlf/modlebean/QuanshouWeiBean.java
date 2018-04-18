package com.hxwl.newwlf.modlebean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class QuanshouWeiBean  {

    /**
     * code : 1000
     * message : 获取量级列表
     * data : [{"id":67,"pid":9,"name":"未知","version":0},{"id":68,"pid":9,"name":"草量级|低于115磅（52kg）","version":0},{"id":69,"pid":9,"name":"蝇量级|116-125磅（52-57kg）","version":0},{"id":70,"pid":9,"name":"雏量级|126-135磅（57-61kg）","version":0},{"id":71,"pid":9,"name":"羽量级|136-145磅（61-66kg）","version":0},{"id":72,"pid":9,"name":"轻量级|146-155磅（66-70kg）","version":0},{"id":73,"pid":9,"name":"次中量级|156-170磅（70-77kg）","version":0},{"id":74,"pid":9,"name":"中量级|171-185磅（77-84kg）","version":0},{"id":75,"pid":9,"name":"轻重量级|186-205磅（84-93kg）","version":0},{"id":76,"pid":9,"name":"重量级|206-265磅（93-120kg）","version":0},{"id":77,"pid":9,"name":"超重量级|超过265磅（120kg）","version":0}]
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
         * id : 67
         * pid : 9
         * name : 未知
         * version : 0
         */

        private int id;
        private int pid;
        private String name;
        private int version;
        private boolean isChecked ;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
