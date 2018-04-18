package com.hxwl.wulinfeng.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class MainPageTuiJianBean implements Serializable{


    private String status;
    private DataBean data;
    private ParamBean param;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public ParamBean getParam() {
        return param;
    }

    public void setParam(ParamBean param) {
        this.param = param;
    }

    public static class DataBean implements Serializable{


        private String lastNewsId;
        private List<ZhiboBean> zhibo;
        private List<ZhuantiBean> zhuanti;
        private List<DuizhenBean> duizhen;
        private List<NewsBean> news;

        public String getLastNewsId() {
            return lastNewsId;
        }

        public void setLastNewsId(String lastNewsId) {
            this.lastNewsId = lastNewsId;
        }

        public List<ZhiboBean> getZhibo() {
            return zhibo;
        }

        public void setZhibo(List<ZhiboBean> zhibo) {
            this.zhibo = zhibo;
        }

        public List<ZhuantiBean> getZhuanti() {
            return zhuanti;
        }

        public void setZhuanti(List<ZhuantiBean> zhuanti) {
            this.zhuanti = zhuanti;
        }

        public List<DuizhenBean> getDuizhen() {
            return duizhen;
        }

        public void setDuizhen(List<DuizhenBean> duizhen) {
            this.duizhen = duizhen;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class ZhiboBean implements Serializable{
            /**
             * zhibo_id : 73
             * saishi_id : 13
             * saicheng_id : 377
             * type : 1
             * title : 武林风2016-12-17
             * url : http://live.wasu.cn/exo/id/17659
             * yugao_url :
             * image : http://m.heixiongboji.com/upload/zhibo_images/2016_12_15/5852468b47518.jpg
             * start_time : 1481980500
             * state : 2
             * yugao_url_is_tiaozhuan : 0
             * is_tiaozhuan : 0
             * is_letv_biaozhun : 0
             * start_time_format : 2016-12-17 21:15
             * saishi_name : 武林风
             * saishi_fang_icon : http://m.heixiongboji.com/asset/images/saishi/57d8fa9fe4b42.jpg
             * saicheng : {"city":"郑州","saicheng_name":"武林风2016-12-17","changguan_id":"174","title":"武林风2016-12-17","changguan":{"id":"174","city":"河南","name":"河南卫视","addr":"河南"}}
             */

            private String zhibo_id;
            private String saishi_id;
            private String saicheng_id;
            private String type;
            private String title;
            private String url;
            private String yugao_url;
            private String image;
            private String start_time;
            private String state;
            private String yugao_url_is_tiaozhuan;
            private String is_tiaozhuan;
            private String is_letv_biaozhun;
            private String start_time_format;
            private String saishi_name;
            private String saishi_fang_icon;
            private SaichengBean saicheng;

            public String getZhibo_id() {
                return zhibo_id;
            }

            public void setZhibo_id(String zhibo_id) {
                this.zhibo_id = zhibo_id;
            }

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getYugao_url() {
                return yugao_url;
            }

            public void setYugao_url(String yugao_url) {
                this.yugao_url = yugao_url;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getYugao_url_is_tiaozhuan() {
                return yugao_url_is_tiaozhuan;
            }

            public void setYugao_url_is_tiaozhuan(String yugao_url_is_tiaozhuan) {
                this.yugao_url_is_tiaozhuan = yugao_url_is_tiaozhuan;
            }

            public String getIs_tiaozhuan() {
                return is_tiaozhuan;
            }

            public void setIs_tiaozhuan(String is_tiaozhuan) {
                this.is_tiaozhuan = is_tiaozhuan;
            }

            public String getIs_letv_biaozhun() {
                return is_letv_biaozhun;
            }

            public void setIs_letv_biaozhun(String is_letv_biaozhun) {
                this.is_letv_biaozhun = is_letv_biaozhun;
            }

            public String getStart_time_format() {
                return start_time_format;
            }

            public void setStart_time_format(String start_time_format) {
                this.start_time_format = start_time_format;
            }

            public String getSaishi_name() {
                return saishi_name;
            }

            public void setSaishi_name(String saishi_name) {
                this.saishi_name = saishi_name;
            }

            public String getSaishi_fang_icon() {
                return saishi_fang_icon;
            }

            public void setSaishi_fang_icon(String saishi_fang_icon) {
                this.saishi_fang_icon = saishi_fang_icon;
            }

            public SaichengBean getSaicheng() {
                return saicheng;
            }

            public void setSaicheng(SaichengBean saicheng) {
                this.saicheng = saicheng;
            }

            public static class SaichengBean implements Serializable{
                /**
                 * city : 郑州
                 * saicheng_name : 武林风2016-12-17
                 * changguan_id : 174
                 * title : 武林风2016-12-17
                 * changguan : {"id":"174","city":"河南","name":"河南卫视","addr":"河南"}
                 */

                private String city;
                private String saicheng_name;
                private String changguan_id;
                private String title;
                private ChangguanBean changguan;

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getSaicheng_name() {
                    return saicheng_name;
                }

                public void setSaicheng_name(String saicheng_name) {
                    this.saicheng_name = saicheng_name;
                }

                public String getChangguan_id() {
                    return changguan_id;
                }

                public void setChangguan_id(String changguan_id) {
                    this.changguan_id = changguan_id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public ChangguanBean getChangguan() {
                    return changguan;
                }

                public void setChangguan(ChangguanBean changguan) {
                    this.changguan = changguan;
                }

                public static class ChangguanBean implements Serializable{
                    /**
                     * id : 174
                     * city : 河南
                     * name : 河南卫视
                     * addr : 河南
                     */

                    private String id;
                    private String city;
                    private String name;
                    private String addr;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getCity() {
                        return city;
                    }

                    public void setCity(String city) {
                        this.city = city;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getAddr() {
                        return addr;
                    }

                    public void setAddr(String addr) {
                        this.addr = addr;
                    }
                }
            }
        }

        public static class ZhuantiBean implements Serializable{
            /**
             * id : 1
             * title : fdsaf
             * img : http://192.168.10.8:99/upload/hao/zhuanti/2016_12_23/585ce91601f08.jpg
             * time : 1481857179
             * is_show : 1
             * rid : 12
             */

            private String id;
            private String title;
            private String img;
            private String time;
            private String is_show;
            private String rid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getIs_show() {
                return is_show;
            }

            public void setIs_show(String is_show) {
                this.is_show = is_show;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }
        }

        public static class DuizhenBean implements Serializable{
            /**
             * id : 1994
             * saishi_id : 19
             * saicheng_id : 313
             * vs_order : 2
             * title : fdsaf
             * red_player_id : 588
             * blue_player_id : 600
             * vs_res : -1
             * state : 1
             * bet_renshu : 0
             * red_player_name : AHMED AMIR
             * red_player_photo : http://m.heixiongboji.com/asset/images/player_photo/57d21f9fa62aa.jpg
             * red_player_guoqi_img : http://m.heixiongboji.com/asset/images/guoqi/57cfadf5e46d4.png
             * blue_player_name : Jaouad EI Byari
             * blue_player_photo : http://m.heixiongboji.com/asset/images/player_photo/57d22439c9fcf.jpg
             * blue_player_guoqi_img : http://m.heixiongboji.com/asset/images/guoqi/57c552b7cbb07.jpg
             */

            private String id;
            private String saishi_id;
            private String saicheng_id;
            private String vs_order;
            private String title;
            private String red_player_id;
            private String blue_player_id;
            private String vs_res;
            private String state;
            private int bet_renshu;
            private String red_player_name;
            private String red_player_photo;
            private String red_player_guoqi_img;
            private String blue_player_name;
            private String blue_player_photo;
            private String blue_player_guoqi_img;
            private String label;
            public String getId() {
                return id;
            }
            public void setLabel(String label) {
                this.label = label;
            }
            public void setId(String id) {
                this.id = id;
            }

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

            public String getVs_order() {
                return vs_order;
            }

            public void setVs_order(String vs_order) {
                this.vs_order = vs_order;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getBet_renshu() {
                return bet_renshu;
            }

            public void setBet_renshu(int bet_renshu) {
                this.bet_renshu = bet_renshu;
            }

            public String getRed_player_name() {
                return red_player_name;
            }

            public void setRed_player_name(String red_player_name) {
                this.red_player_name = red_player_name;
            }

            public String getRed_player_photo() {
                return red_player_photo;
            }

            public void setRed_player_photo(String red_player_photo) {
                this.red_player_photo = red_player_photo;
            }

            public String getRed_player_guoqi_img() {
                return red_player_guoqi_img;
            }

            public void setRed_player_guoqi_img(String red_player_guoqi_img) {
                this.red_player_guoqi_img = red_player_guoqi_img;
            }

            public String getBlue_player_name() {
                return blue_player_name;
            }

            public void setBlue_player_name(String blue_player_name) {
                this.blue_player_name = blue_player_name;
            }

            public String getBlue_player_photo() {
                return blue_player_photo;
            }

            public void setBlue_player_photo(String blue_player_photo) {
                this.blue_player_photo = blue_player_photo;
            }
            public String getLabel() {
                return label;
            }
            public String getBlue_player_guoqi_img() {
                return blue_player_guoqi_img;
            }

            public void setBlue_player_guoqi_img(String blue_player_guoqi_img) {
                this.blue_player_guoqi_img = blue_player_guoqi_img;
            }
        }

        public static class NewsBean implements Serializable{
            /**
             * id : 1
             * type : 0
             * title : 1
             * contents : <p><br></p><p><span style="font-family: Arial, Helvetica, sans-serif; font-size: 14px;">???????????????</span></p><p><br><img src="http://m.heixiongboji.com/upload/bbs/2016_11_05/581d88e9a4ec1.blob" class="fr-dii fr-draggable"></p><p><br></p><p><img src="http://m.heixiongboji.com/upload/bbs/2016_11_05/581d885f7b8df.blob" class="fr-dii fr-draggable"></p><p><br></p><p><img src="http://m.heixiongboji.com/upload/bbs/2016_11_05/581d8832a6e9b.blob" class="fr-dii fr-draggable"></p><p><br></p>
             * hid : 2
             * label :
             * huifu_times : 7
             * open_times : 45891
             * zan_times : 1220
             * type_ : news
             * huser : {"hid":"2","nickname":"我是2号","head_url":"http://192.168.10.8:99/asset/images/hao_head/5858e699db231.jpg"}
             * format_time : 47年前
             * title_contents : ???????????????
             * img_contents : ["http://m.heixiongboji.com/upload/bbs/2016_11_05/581d88e9a4ec1.blob","http://m.heixiongboji.com/upload/bbs/2016_11_05/581d885f7b8df.blob","http://m.heixiongboji.com/upload/bbs/2016_11_05/581d8832a6e9b.blob"]
             * is_tuijian : 1
             * tuijian_start_time : 0
             * tuijian_end_time : 57599
             * is_show : 1
             * sort : 1
             * time : 1482722542
             * zhuanti_id : 0
             * img_count : 0
             * imgs : [{"id":"83","contents":"","url":"http://192.168.10.8:99/upload/hao/img_ku/2016_12_20_16/5858e63b44737.jpg","img_list_id":"6","is_show":"1","time":"1482221115"}]
             * img : http://192.168.10.8:99/upload/hao/video/2016_12_15/585263ccf2294.png
             * video_count : 0
             * videos : {"id":"12","contents":"","url":"http://www.runoob.com/try/demo_source/mov_bbb.mp4","video_list_id":"13","is_show":"1","time":"1482302028"}
             */

            private String id;
            private String type;
            private String title;
            private String contents;
            private String hid;
            private String label;
            private String huifu_times;
            private String open_times;
            private String zan_times;
            private String type_;
            private HuserBean huser;
            private String format_time;
            private String title_contents;
            private String is_tuijian;
            private String tuijian_start_time;
            private String tuijian_end_time;
            private String is_show;
            private String sort;
            private String time;
            private String zhuanti_id;
            private String img_count;
            private String img;
            private String video_count;
            private String fengmiantu;
            private VideosBean videos;
            private List<String> img_contents;
            private List<ImgsBean> imgs;
            private boolean isSelect = false;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public String getFengmiantu() {
                return fengmiantu;
            }

            public void setFengmiantu(String fengmiantu) {
                this.fengmiantu = fengmiantu;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContents() {
                return contents;
            }

            public void setContents(String contents) {
                this.contents = contents;
            }

            public String getHid() {
                return hid;
            }

            public void setHid(String hid) {
                this.hid = hid;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getHuifu_times() {
                return huifu_times;
            }

            public void setHuifu_times(String huifu_times) {
                this.huifu_times = huifu_times;
            }

            public String getOpen_times() {
                return open_times;
            }

            public void setOpen_times(String open_times) {
                this.open_times = open_times;
            }

            public String getZan_times() {
                return zan_times;
            }

            public void setZan_times(String zan_times) {
                this.zan_times = zan_times;
            }

            public String getType_() {
                return type_;
            }

            public void setType_(String type_) {
                this.type_ = type_;
            }

            public HuserBean getHuser() {
                return huser;
            }

            public void setHuser(HuserBean huser) {
                this.huser = huser;
            }

            public String getFormat_time() {
                return format_time;
            }

            public void setFormat_time(String format_time) {
                this.format_time = format_time;
            }

            public String getTitle_contents() {
                return title_contents;
            }

            public void setTitle_contents(String title_contents) {
                this.title_contents = title_contents;
            }

            public String getIs_tuijian() {
                return is_tuijian;
            }

            public void setIs_tuijian(String is_tuijian) {
                this.is_tuijian = is_tuijian;
            }

            public String getTuijian_start_time() {
                return tuijian_start_time;
            }

            public void setTuijian_start_time(String tuijian_start_time) {
                this.tuijian_start_time = tuijian_start_time;
            }

            public String getTuijian_end_time() {
                return tuijian_end_time;
            }

            public void setTuijian_end_time(String tuijian_end_time) {
                this.tuijian_end_time = tuijian_end_time;
            }

            public String getIs_show() {
                return is_show;
            }

            public void setIs_show(String is_show) {
                this.is_show = is_show;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getZhuanti_id() {
                return zhuanti_id;
            }

            public void setZhuanti_id(String zhuanti_id) {
                this.zhuanti_id = zhuanti_id;
            }

            public String getImg_count() {
                return img_count;
            }

            public void setImg_count(String img_count) {
                this.img_count = img_count;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getVideo_count() {
                return video_count;
            }

            public void setVideo_count(String video_count) {
                this.video_count = video_count;
            }

            public VideosBean getVideos() {
                return videos;
            }

            public void setVideos(VideosBean videos) {
                this.videos = videos;
            }

            public List<String> getImg_contents() {
                return img_contents;
            }

            public void setImg_contents(List<String> img_contents) {
                this.img_contents = img_contents;
            }

            public List<ImgsBean> getImgs() {
                return imgs;
            }

            public void setImgs(List<ImgsBean> imgs) {
                this.imgs = imgs;
            }

            public static class HuserBean implements Serializable{
                /**
                 * hid : 2
                 * nickname : 我是2号
                 * head_url : http://192.168.10.8:99/asset/images/hao_head/5858e699db231.jpg
                 */

                private String hid;
                private String nickname;
                private String head_url;

                public String getHid() {
                    return hid;
                }

                public void setHid(String hid) {
                    this.hid = hid;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getHead_url() {
                    return head_url;
                }

                public void setHead_url(String head_url) {
                    this.head_url = head_url;
                }
            }

            public static class VideosBean implements Serializable{
                /**
                 * id : 12
                 * contents :
                 * url : http://www.runoob.com/try/demo_source/mov_bbb.mp4
                 * video_list_id : 13
                 * is_show : 1
                 * time : 1482302028
                 */

                private String id;
                private String contents;
                private String url;
                private String video_list_id;
                private String is_show;
                private String time;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getContents() {
                    return contents;
                }

                public void setContents(String contents) {
                    this.contents = contents;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getVideo_list_id() {
                    return video_list_id;
                }

                public void setVideo_list_id(String video_list_id) {
                    this.video_list_id = video_list_id;
                }

                public String getIs_show() {
                    return is_show;
                }

                public void setIs_show(String is_show) {
                    this.is_show = is_show;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }

            public static class ImgsBean implements Serializable{
                /**
                 * id : 83
                 * contents :
                 * url : http://192.168.10.8:99/upload/hao/img_ku/2016_12_20_16/5858e63b44737.jpg
                 * img_list_id : 6
                 * is_show : 1
                 * time : 1482221115
                 */

                private String id;
                private String contents;
                private String url;
                private String img_list_id;
                private String is_show;
                private String time;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getContents() {
                    return contents;
                }

                public void setContents(String contents) {
                    this.contents = contents;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getImg_list_id() {
                    return img_list_id;
                }

                public void setImg_list_id(String img_list_id) {
                    this.img_list_id = img_list_id;
                }

                public String getIs_show() {
                    return is_show;
                }

                public void setIs_show(String is_show) {
                    this.is_show = is_show;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }
        }
    }

    public static class ParamBean implements Serializable{
        /**
         * _action_ : Hao
         * _method_ : tuijian
         */

        private String _action_;
        private String _method_;

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
    }
}
