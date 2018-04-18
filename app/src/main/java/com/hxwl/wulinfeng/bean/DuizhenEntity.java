package com.hxwl.wulinfeng.bean;


import java.io.Serializable;
import java.util.List;

/**
 *功能：对阵数据
 */
public  class DuizhenEntity implements Serializable{
    private String duizhen_id;
    private String title;
    private String title2;
    private String title3;
    private String vs_order;
    private String red_player_id;
    private String blue_player_id;
    private String state;
    private String vs_res;
    private String red_peilv;
    private String blue_peilv;
    private String red_player_name;
    private String red_player_photo;
    private String red_win_times;
    private String red_lose_times;
    private String red_ko_times;
    private String red_ping_times;
    private String red_shuangyue_times;
    private String red_player_guoqi_img;
    private String blue_player_name;
    private String blue_player_photo;
    private String blue_win_times;
    private String blue_lose_times;
    private String blue_ko_times;
    private String blue_ping_times;
    private String blue_shuangyue_times;
    private String blue_player_guoqi_img;
    private String bet_renshu;
    private String label;
    private String enable_bet;
    private boolean isSelect = false;

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    /**
     * bet_id : 474
     * uid : 39
     * gold : 1135
     * time : 1475574973
     * nickname : 关关
     * head_url : http://api.hxboji.com/upload/head/2016_10_04/oj5g4v3JylCN-JWBDEjBP8C8bpO8.jpg
     */

    private List<BetTopEntity> bet_top;

    public void setDuizhen_id(String duizhen_id) {
        this.duizhen_id = duizhen_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVs_order(String vs_order) {
        this.vs_order = vs_order;
    }
    public void setEnable_bet(String enable_bet) {
        this.enable_bet = enable_bet;
    }

    public void setRed_player_id(String red_player_id) {
        this.red_player_id = red_player_id;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public void setBlue_player_id(String blue_player_id) {
        this.blue_player_id = blue_player_id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setVs_res(String vs_res) {
        this.vs_res = vs_res;
    }

    public void setRed_peilv(String red_peilv) {
        this.red_peilv = red_peilv;
    }

    public void setBlue_peilv(String blue_peilv) {
        this.blue_peilv = blue_peilv;
    }

    public void setRed_player_name(String red_player_name) {
        this.red_player_name = red_player_name;
    }

    public void setRed_player_photo(String red_player_photo) {
        this.red_player_photo = red_player_photo;
    }

    public void setRed_win_times(String red_win_times) {
        this.red_win_times = red_win_times;
    }

    public void setRed_lose_times(String red_lose_times) {
        this.red_lose_times = red_lose_times;
    }

    public void setRed_ko_times(String red_ko_times) {
        this.red_ko_times = red_ko_times;
    }

    public void setRed_ping_times(String red_ping_times) {
        this.red_ping_times = red_ping_times;
    }

    public void setRed_shuangyue_times(String red_shuangyue_times) {
        this.red_shuangyue_times = red_shuangyue_times;
    }

    public void setRed_player_guoqi_img(String red_player_guoqi_img) {
        this.red_player_guoqi_img = red_player_guoqi_img;
    }

    public void setBlue_player_name(String blue_player_name) {
        this.blue_player_name = blue_player_name;
    }

    public void setBlue_player_photo(String blue_player_photo) {
        this.blue_player_photo = blue_player_photo;
    }

    public void setBlue_win_times(String blue_win_times) {
        this.blue_win_times = blue_win_times;
    }

    public void setBlue_lose_times(String blue_lose_times) {
        this.blue_lose_times = blue_lose_times;
    }

    public void setBlue_ko_times(String blue_ko_times) {
        this.blue_ko_times = blue_ko_times;
    }

    public void setBlue_ping_times(String blue_ping_times) {
        this.blue_ping_times = blue_ping_times;
    }

    public void setBlue_shuangyue_times(String blue_shuangyue_times) {
        this.blue_shuangyue_times = blue_shuangyue_times;
    }

    public void setBlue_player_guoqi_img(String blue_player_guoqi_img) {
        this.blue_player_guoqi_img = blue_player_guoqi_img;
    }

    public void setBet_renshu(String bet_renshu) {
        this.bet_renshu = bet_renshu;
    }

    public void setBet_top(List<BetTopEntity> bet_top) {
        this.bet_top = bet_top;
    }

    public String getDuizhen_id() {
        return duizhen_id;
    }

    public String getTitle() {
        return title;
    }

    public String getVs_order() {
        return vs_order;
    }

    public String getRed_player_id() {
        return red_player_id;
    }

    public String getBlue_player_id() {
        return blue_player_id;
    }

    public String getState() {
        return state;
    }

    public String getVs_res() {
        return vs_res;
    }
    public String getEnable_bet() {
        return enable_bet;
    }

    public String getRed_peilv() {
        return red_peilv;
    }

    public String getBlue_peilv() {
        return blue_peilv;
    }

    public String getRed_player_name() {
        return red_player_name;
    }

    public String getRed_player_photo() {
        return red_player_photo;
    }

    public String getRed_win_times() {
        return red_win_times;
    }

    public String getRed_lose_times() {
        return red_lose_times;
    }

    public String getRed_ko_times() {
        return red_ko_times;
    }

    public String getRed_ping_times() {
        return red_ping_times;
    }

    public String getRed_shuangyue_times() {
        return red_shuangyue_times;
    }

    public String getRed_player_guoqi_img() {
        return red_player_guoqi_img;
    }

    public String getBlue_player_name() {
        return blue_player_name;
    }

    public String getBlue_player_photo() {
        return blue_player_photo;
    }

    public String getBlue_win_times() {
        return blue_win_times;
    }

    public String getBlue_lose_times() {
        return blue_lose_times;
    }

    public String getBlue_ko_times() {
        return blue_ko_times;
    }

    public String getBlue_ping_times() {
        return blue_ping_times;
    }

    public String getBlue_shuangyue_times() {
        return blue_shuangyue_times;
    }

    public String getBlue_player_guoqi_img() {
        return blue_player_guoqi_img;
    }

    public String getBet_renshu() {
        return bet_renshu;
    }
    public String getLabel() {
        return label;
    }
    public List<BetTopEntity> getBet_top() {
        return bet_top;
    }

    public static class BetTopEntity implements Serializable {
        private String bet_id;
        private String uid;
        private String gold;
        private String time;
        private String nickname;
        private String head_url;

        public void setBet_id(String bet_id) {
            this.bet_id = bet_id;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setHead_url(String head_url) {
            this.head_url = head_url;
        }

        public String getBet_id() {
            return bet_id;
        }

        public String getUid() {
            return uid;
        }

        public String getGold() {
            return gold;
        }

        public String getTime() {
            return time;
        }

        public String getNickname() {
            return nickname;
        }

        public String getHead_url() {
            return head_url;
        }
    }




}
