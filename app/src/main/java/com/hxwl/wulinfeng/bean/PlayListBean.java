package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/15.
 */
public class PlayListBean {
    private String saishi_id;

    private String saicheng_id;

    private String red_player_id;

    private String blue_player_id;

    private String vs_res;

    private String saishi_name;

    private String start_time_format;

    private String duishou_name;

    private String saiguo;

    public String getSaishi_id() {
        return saishi_id;
    }

    public void setSaishi_id(String saishi_id) {
        this.saishi_id = saishi_id;
    }

    public String getSaicheng_id() {
        return saicheng_id;
    }

    public void setSaicheng_id(String saicheng_id) {
        this.saicheng_id = saicheng_id;
    }

    public String getRed_player_id() {
        return red_player_id;
    }

    public void setRed_player_id(String red_player_id) {
        this.red_player_id = red_player_id;
    }

    public String getBlue_player_id() {
        return blue_player_id;
    }

    public void setBlue_player_id(String blue_player_id) {
        this.blue_player_id = blue_player_id;
    }

    public String getVs_res() {
        return vs_res;
    }

    public void setVs_res(String vs_res) {
        this.vs_res = vs_res;
    }

    public String getSaishi_name() {
        return saishi_name;
    }

    public void setSaishi_name(String saishi_name) {
        this.saishi_name = saishi_name;
    }

    public String getStart_time_format() {
        return start_time_format;
    }

    public void setStart_time_format(String start_time_format) {
        this.start_time_format = start_time_format;
    }

    public String getDuishou_name() {
        return duishou_name;
    }

    public void setDuishou_name(String duishou_name) {
        this.duishou_name = duishou_name;
    }

    public String getSaiguo() {
        return saiguo;
    }

    public void setSaiguo(String saiguo) {
        this.saiguo = saiguo;
    }

    @Override
    public String toString() {
        return "PlayListBean{" +
                "saishi_id='" + saishi_id + '\'' +
                ", saicheng_id='" + saicheng_id + '\'' +
                ", red_player_id='" + red_player_id + '\'' +
                ", blue_player_id='" + blue_player_id + '\'' +
                ", vs_res='" + vs_res + '\'' +
                ", saishi_name='" + saishi_name + '\'' +
                ", start_time_format='" + start_time_format + '\'' +
                ", duishou_name='" + duishou_name + '\'' +
                ", saiguo='" + saiguo + '\'' +
                '}';
    }
}
