package com.hxwl.wulinfeng.bean;

/**
 * Created by Allen on 2017/6/20.
 */
public class Zhanji {
    private String z_win_times;

    private String z_lose_times;

    private String z_ping_times;

    private String z_ko_times;

    private String z_shuangyue_times;

    public String getZ_shuangyue_times() {
        return z_shuangyue_times;
    }

    public void setZ_shuangyue_times(String z_shuangyue_times) {
        this.z_shuangyue_times = z_shuangyue_times;
    }

    public String getZ_win_times() {
        return z_win_times;
    }

    public void setZ_win_times(String z_win_times) {
        this.z_win_times = z_win_times;
    }

    public String getZ_lose_times() {
        return z_lose_times;
    }

    public void setZ_lose_times(String z_lose_times) {
        this.z_lose_times = z_lose_times;
    }

    public String getZ_ping_times() {
        return z_ping_times;
    }

    public void setZ_ping_times(String z_ping_times) {
        this.z_ping_times = z_ping_times;
    }

    public String getZ_ko_times() {
        return z_ko_times;
    }

    public void setZ_ko_times(String z_ko_times) {
        this.z_ko_times = z_ko_times;
    }

    @Override
    public String toString() {
        return "Zhanji{" +
                "z_win_times='" + z_win_times + '\'' +
                ", z_lose_times='" + z_lose_times + '\'' +
                ", z_ping_times='" + z_ping_times + '\'' +
                ", z_ko_times='" + z_ko_times + '\'' +
                '}';
    }
}
