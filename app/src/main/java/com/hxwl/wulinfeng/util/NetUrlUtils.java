package com.hxwl.wulinfeng.util;

import static android.R.attr.id;

/**
 * Created by Administrator on 2016/7/28.
 */
public class NetUrlUtils {
    public static String project = "wulinfeng";

    /**
     * 局域网测试
     */
//   public static String BaseUrl="http://192.168.10.8:8888/index.php/"; //发版记得修改Socket —— IP地址 : Constants类

//    /**
//     * 线上  尽量使用https 安全
//     */
//    public static String BaseUrl = "http://test.heixiongboji.com:8888/index.php/";

    public static String BaseUrl = "http://apiwlf.heixiongboji.com/index.php/";

    //直播详情
    public static String share_zhibo = "m.wlf.heixiongboji.com/index.php/Info/live/";
    //图集详情
    public static String share_tuji = "m.wlf.heixiongboji.com/index.php/Info/img/";
    //资讯详情
    public static String share_zixun = "m.wlf.heixiongboji.com/index.php/Info/zixun/";
    //视频详情
    public static String share_shipin = "m.wlf.heixiongboji.com/index.php/Info/video/";
    /**
     * 分享share图标
     */
    public static String share_icon = "";

    //banner图片
    public static String zuiXin_banner = "Home/getTopImg";
    //首页推荐直播
    public static String zuiXin_tuijianzhibo = "Home/getTuijianZhibo";
    //首页专题
    public static String zuiXin_zhuanti = "Home/getZhuanti";
    //首页回放
    public static String zuiXin_huifang = "Home/getZuixinHuifang";
    //首页混合列表
    public static String zuiXin_news = "Home/gethunhe";
    //资讯列表上方的banner
//    public static String zuiXin_news = BaseUrl+ "Home/gethunhe";
    //资讯列表
    public static String ziXun_news = "Home/news";
    //图集列表
    public static String ziXun_tuji = "Home/imgList";
    //视频列表 上边布局
    public static String ziXun_abo_shipin = "Home/GetTuijianVideoList";
    //视频列表 下边list布局
    public static String ziXun_bel_shipin = "Home/videoList";
    //赛程 -- tab赛事类型
    public static String saiCheng_type = "Saicheng/getSaishi";
    //赛程 -- banner上方
    public static String saiCheng_banner = "Saicheng/getTopImg";
    //赛程 -- 下边列表
    public static String zsaiCheng_listdata = "Saicheng/getSaichengList";
    //武林 界面 最新接口
    public static String wulin_list = "Wulin/wulinList";
    //我的武林
    public static String wodewulin_list = "Wulin/getWodeWulin";
    //我的武林分布获取秘钥接口
    public static String wulin_getSignature = "QCloud/getSignature";
    //我的武林分布视频成功后 给服务器响应
    public static String wulin_publisVideo= "Wulin/addVideo";
    //武林 界面 右上角 发帖 （没有图片的）
    public static String wulin_fatie = "Wulin/fatie";
    //武林 界面 右上角 发帖之后添加图片
//    public static String wulin_fatie_img = "Wulin/addImg";
    //武林 点赞
    public static String wulin_dianzan = "Wulin/zanZhutie";
    //武林 取消点赞
    public static String wulin_quxiaodianzan = "Wulin/zanZhutieQuxiao";
    //武林 回复
    public static String wulin_huifu = "Wulin/huifu";
    //武林 -- 单个头
    public static String wulin_tiezi_details = "Wulin/tieInfo";
    //武林 -- 单个帖子回复
    public static String wulin_tiezi_details_huifu = "Wulin/getHuifuList";
    //武林 -- 右上角第一个按钮 -- 我的消息
    public static String wulin_message = "Wulin/getWodehuifu";
    //选手接口
    public static String wulin_play = "Player/getPlayerList";
    //选手接口详情
    public static String wulin_play_detail = "Player/getPlayerInfo";
    //选手接口战绩
    public static String wulin_play_list = "Player/getPlayerDuizhen";
    //选手接口 -- 量级
    public static String wulin_level = "Player/weightLevel";
    //我--赛事预约
    public static String wo_saishiyuyue = "Saicheng/yuyueList";
    //我--系统消息
    public static String wo_message_system = "Message/messageList";
    //我--系统消息
    public static String isneed_relogin = "User/isRelogin";
    //是否有Live标志
    public static String ishave_Live = "Saicheng/checkLive";
    //发现 --活动位列表
    public static String faxian_huodong = "Sys/getHuodongwei";
    //发现-- 武战联盟
    public static String faxian_wuzhanlm = "Club/getClubList";
    //发现-- 点击上方布局 -- 比赛视频
    public static String faxian_bisaishipin = "Saicheng/getSaishi";
    //我- 登录-- 获取验证码
    public static String wo_getverificationcode = "User/sendSMS";
    //我- 登录-- 验证码登陆
    public static String wo_codeLogin = "User/mobileLogin";
    //我- 登录-- 密码登陆
    public static String wo_pwdLogin = "User/passwordLogin";
    //我- 登录-- 密码登陆
    public static String wo_setpwd = "User/setPassword";
    //我- 登录-- w忘记密码
    public static String wo_forgetpwd = "User/resetPassword";
    //我- 注册
    public static String wo_register = "User/mobileReg";
    //我- 检测是否已经注册
    public static String wo_cheakisreg = "User/checkMobile";
    //我- 常见问题
    public static String wo_changjianquestion = "Sys/changjianwentiList";
    //我- 我的问题反馈
    public static String wo_myfankui = "User/checkWodeFankuiNotReadNum";
    //我- 系统消息
    public static String message_system = "Message/getLastMessageId";
    //我- 提交反馈
    public static String wo_submitreturn = "User/fankui";
    //我- 我的反馈详情
    public static String wo_myfankuideta = "User/getWodeFankui";
    //我- 修改用户名
    public static String wo_changename = "User/editNickname";
    //我- 修改手机号
    public static String wo_changephone = "User/bindMobile";
    //俱乐部详情
    public static String club_details = "Club/getClubInfo";
    //俱乐部对阵详情
    public static String club_duizhendetails = "Club/getPlayerDuizhen";
    //资讯详情上部分
    public static String zixun_detail = "Home/newsInfo";
    //资讯详情下部分
    public static String zixun_tiezidetail = "Home/getNewsGentie";
    //跟帖赞
    public static String zixun_gentiezan = "Home/zanGentie";
    //跟帖回复
    public static String zixun_gentiehuifu = "Home/huifu";
    //更多回复 -- 资讯
    public static String zixun_gengduohuifu = "Home/getNewsHuifuList";
    //往期赛事视频 -- 月份
    public static String video_wangqi_year = "Video/getYearMonth";
    //往期赛事视频 -- 视频
    public static String video_wangqi = "Video/getHuifangList";
    //图集详情 -- 上方
    public static String tuji_tieziimgdetail = "Home/imgListInfo";
    //图集详情下部分
    public static String tuji_tiezidetail = "Home/getImgListGentie";
    //更多回复 -- 图集
    public static String tuji_gengduohuifu = "Home/getImgListHuifuList";
    //更多回复 -- 视频
    public static String video_gengduohuifu = "Home/getVideoListHuifuList";
    //热搜
    public static String re_search = "Search/getHotKeryword";
    //专题详情界面
    public static String zhuanti_detail = "Home/zhuantiInfo";
    //武林 未读回复 ！！！！
    public static String wulin_unreadhuifu = "Wulin/checkWodehuifu";
    //注册协议
    public static String zhuce_xieyi = "Sys/regXieyi?format=html";
    //s使用条款
    public static String shiyong_xieyi = "Sys/shiyongtiaokuan?format=html";
    //隐私政策
    public static String yinsi_zhengce = "Sys/yinsizhengce?format=html";
    //修改头像
//    public static String wo_xiugaitouxiang = "User/editHeadurl";
    //获取用户基本信息
    public static String wo_getuserinfo = "User/getUserInfo";
    //视频详情头 -- 首页
    public static String video_detail = "Home/videoListInfo";
    //视频详情list数据 -- 首页
    public static String video_detail_list = "Home/getVideoListGentie";
    //视频回放视频详情信息
    public static String video_getinfo = "Video/getVideoForSaichengId";
    //视频回放视频详情信息(热聊消息)
    public static String video_reliao = "Saicheng/getMessage";
    //视频回放视频详情信息(对阵)
    public static String video_duizhen = "Saicheng/getDuizhen";
    //视频花絮AND集锦界面
    public static String video_jijinANDhuaxu = "Home/getVideoForType";
    //发帖关键字
    public static String wulin_hint = "Wulin/fatieKeyword";
    //预约通知
    public static String wulin_yuyuetongzhi = "Saicheng/yuyueTongzhi";
    //取消预约通知
    public static String wulin_quxiaoyuyuetongzhi = "Saicheng/yuyueTongzhiQuxiao";
    //预约完成通知
    public static String saicheng_finshtongzhi = "Saicheng/yuyueTanchuTongzhi";
    //直播信息
    public static String saicheng_zhibo = "Zhibo/getZhiboForSaichengId";
    //检查应用版本
    public static String sys_update = "Sys/apkVersion";
    //常见问题
    public static String sys_changjianquest = "Sys/changjianwentiInfo";
    //活动图片
    public static String sys_splshImg = "Sys/splashImg";
    //活动图片
    public static String sys_huodongImg = "HuodongTanchuang/getList";

//    //banner图片
//    public static String zuiXin_banner = BaseUrl + "Home/getTopImg";
//    //首页推荐直播
//    public static String zuiXin_tuijianzhibo = BaseUrl + "Home/getTuijianZhibo";
//    //首页专题
//    public static String zuiXin_zhuanti = BaseUrl + "Home/getZhuanti";
//    //首页回放
//    public static String zuiXin_huifang = BaseUrl + "Home/getZuixinHuifang";
//    //首页混合列表
//    public static String zuiXin_news = BaseUrl + "Home/gethunhe";
//    //资讯列表上方的banner
////    public static String zuiXin_news = BaseUrl+ "Home/gethunhe";
//    //资讯列表
//    public static String ziXun_news = BaseUrl + "Home/news";
//    //图集列表
//    public static String ziXun_tuji = BaseUrl + "Home/imgList";
//    //视频列表 上边布局
//    public static String ziXun_abo_shipin = BaseUrl + "Home/GetTuijianVideoList";
//    //视频列表 下边list布局
//    public static String ziXun_bel_shipin = BaseUrl + "Home/videoList";
//    //赛程 -- tab赛事类型
//    public static String saiCheng_type = BaseUrl + "Saicheng/getSaishi";
//    //赛程 -- banner上方
//    public static String saiCheng_banner = BaseUrl + "Saicheng/getTopImg";
//    //赛程 -- 下边列表
//    public static String zsaiCheng_listdata = BaseUrl + "Saicheng/getSaichengList";
//    //武林 界面 最新接口
//    public static String wulin_list = BaseUrl + "Wulin/wulinList";
//    //武林 界面 右上角 发帖 （没有图片的）
//    public static String wulin_fatie = BaseUrl + "Wulin/fatie";
//    //武林 界面 右上角 发帖之后添加图片
    public static String wulin_fatie_img = BaseUrl + "Wulin/addImg";
//    //武林 点赞
//    public static String wulin_dianzan = BaseUrl + "Wulin/zanZhutie";
//    //武林 取消点赞
//    public static String wulin_quxiaodianzan = BaseUrl + "Wulin/zanZhutieQuxiao";
//    //武林 回复
//    public static String wulin_huifu = BaseUrl + "Wulin/huifu";
//    //武林 -- 单个头
//    public static String wulin_tiezi_details = BaseUrl + "Wulin/tieInfo";
//    //武林 -- 单个帖子回复
//    public static String wulin_tiezi_details_huifu = BaseUrl + "Wulin/getHuifuList";
//    //武林 -- 右上角第一个按钮 -- 我的消息
//    public static String wulin_message = BaseUrl + "Wulin/getWodehuifu";
//    //选手接口
//    public static String wulin_play = BaseUrl + "Player/getPlayerList";
//    //选手接口详情
//    public static String wulin_play_detail = BaseUrl + "Player/getPlayerInfo";
//    //选手接口战绩
//    public static String wulin_play_list = BaseUrl + "Player/getPlayerDuizhen";
//    //选手接口 -- 量级
//    public static String wulin_level = BaseUrl + "Player/weightLevel";
//    //我--赛事预约
//    public static String wo_saishiyuyue = BaseUrl + "Saicheng/yuyueList";
//    //我--系统消息
//    public static String wo_message_system = BaseUrl + "Message/messageList";
//    //发现 --活动位列表
//    public static String faxian_huodong = BaseUrl + "Sys/getHuodongwei";
//    //发现-- 武战联盟
//    public static String faxian_wuzhanlm = BaseUrl + "Club/getClubList";
//    //发现-- 点击上方布局 -- 比赛视频
//    public static String faxian_bisaishipin = BaseUrl + "Saicheng/getSaishi";
//    //我- 登陆-- 获取验证码
//    public static String wo_getverificationcode = BaseUrl + "User/sendSMS";
//    //我- 登陆-- 验证码登陆
//    public static String wo_codeLogin = BaseUrl + "User/mobileLogin";
//    //我- 登陆-- 密码登陆
//    public static String wo_pwdLogin = BaseUrl + "User/passwordLogin";
//    //我- 登陆-- 密码登陆
//    public static String wo_setpwd = BaseUrl + "User/setPassword";
//    //我- 登陆-- w忘记密码
//    public static String wo_forgetpwd = BaseUrl + "User/resetPassword";
//    //我- 注册
//    public static String wo_register = BaseUrl + "User/mobileReg";
//    //我- 检测是否已经注册
//    public static String wo_cheakisreg = BaseUrl + "User/checkMobile";
//    //我- 常见问题
//    public static String wo_changjianquestion = BaseUrl + "Sys/changjianwentiList";
//    //我- 我的问题反馈
//    public static String wo_myfankui = BaseUrl + "User/checkWodeFankuiNotReadNum";
//    //我- 提交反馈
//    public static String wo_submitreturn = BaseUrl + "User/fankui";
//    //我- 我的反馈详情
//    public static String wo_myfankuideta = BaseUrl + "User/getWodeFankui";
//    //我- 修改用户名
//    public static String wo_changename = BaseUrl + "User/editNickname";
//    //我- 修改手机号
//    public static String wo_changephone = BaseUrl + "User/bindMobile";
//    //俱乐部详情
//    public static String club_details = BaseUrl + "Club/getClubInfo";
//    //俱乐部对阵详情
//    public static String club_duizhendetails = BaseUrl + "Club/getPlayerDuizhen";
//    //资讯详情上部分
//    public static String zixun_detail = BaseUrl + "Home/newsInfo";
//    //资讯详情下部分
//    public static String zixun_tiezidetail = BaseUrl + "Home/getNewsGentie";
//    //跟帖赞
//    public static String zixun_gentiezan = BaseUrl + "Home/zanGentie";
//    //跟帖回复
//    public static String zixun_gentiehuifu = BaseUrl + "Home/huifu";
//    //更多回复 -- 资讯
//    public static String zixun_gengduohuifu = BaseUrl + "Home/getNewsHuifuList";
//    //往期赛事视频 -- 月份
//    public static String video_wangqi_year = BaseUrl + "Video/getYearMonth";
//    //往期赛事视频 -- 视频
//    public static String video_wangqi = BaseUrl + "Video/getHuifangList";
//    //图集详情 -- 上方
//    public static String tuji_tieziimgdetail = BaseUrl + "Home/imgListInfo";
//    //图集详情下部分
//    public static String tuji_tiezidetail = BaseUrl + "Home/getImgListGentie";
//    //更多回复 -- 图集
//    public static String tuji_gengduohuifu = BaseUrl + "Home/getImgListHuifuList";
//    //更多回复 -- 视频
//    public static String video_gengduohuifu = BaseUrl + "Home/getVideoListHuifuList";
//    //热搜
//    public static String re_search = BaseUrl + "Search/getHotKeryword";
//    //专题详情界面
//    public static String zhuanti_detail = BaseUrl + "Home/zhuantiInfo";
//    //武林 未读回复 ！！！！
//    public static String wulin_unreadhuifu = BaseUrl + "Wulin/checkWodehuifu";
//    //注册协议
//    public static String zhuce_xieyi = BaseUrl + "Sys/regXieyi?format=html";
//    //s使用条款
//    public static String shiyong_xieyi = BaseUrl + "Sys/shiyongtiaokuan?format=html";
//    //隐私政策
//    public static String yinsi_zhengce = BaseUrl + "Sys/yinsizhengce?format=html";
    //修改头像
    public static String wo_xiugaitouxiang = BaseUrl + "User/editHeadurl";
//    //获取用户基本信息
//    public static String wo_getuserinfo = BaseUrl + "User/getUserInfo";
//    //视频详情头 -- 首页
//    public static String video_detail = BaseUrl + "Home/videoListInfo";
//    //视频详情list数据 -- 首页
//    public static String video_detail_list = BaseUrl + "Home/getVideoListGentie";
//    //视频回放视频详情信息
//    public static String video_getinfo = BaseUrl + "Video/getVideoForSaichengId";
//    //视频回放视频详情信息(热聊消息)
//    public static String video_reliao = BaseUrl + "Saicheng/getMessage";
//    //视频回放视频详情信息(对阵)
//    public static String video_duizhen = BaseUrl + "Saicheng/getDuizhen";
//    //视频花絮AND集锦界面
//    public static String video_jijinANDhuaxu = BaseUrl + "Home/getVideoForType";
//    //发帖关键字
//    public static String wulin_hint = BaseUrl + "Wulin/fatieKeyword";
//    //预约通知
//    public static String wulin_yuyuetongzhi = BaseUrl + "Saicheng/yuyueTongzhi";
//    //预约完成通知
//    public static String saicheng_finshtongzhi = BaseUrl + "Saicheng/yuyueTanchuTongzhi";
//    //直播信息
//    public static String saicheng_zhibo = BaseUrl + "Zhibo/getZhiboForSaichengId";


    /**
     * 赛事顶部图片
     */
    public static String saishiBannerUrl = BaseUrl + "Saishi/getTopImg";
    /**
     * 闪屏图片
     */
    public static String SPLASHIMG = BaseUrl + "Sys/splashImg";
    /**
     * 闪屏图片2
     */
    public static String SPLASHIMG2 = BaseUrl + "Sys/splashImgV2";
    /**
     * 热门赛事
     */
    public static String HotSaishiUrl = BaseUrl + "Saishi/hotItem";
    /**
     * 热门赛事详情
     */
    public static String SaishiDetailUrl = BaseUrl + "Saishi/getSaishiInfo";
    /**
     * 热门赛事列表
     */
    public static String SaishiListUrl = BaseUrl + "Saishi/getSaishiList";
    /**
     * 热门选手列表
     */
    public static String PlayerListUrl = BaseUrl + "Player/getPlayerList";
    /**
     * 选手列表里的拳种
     */
    public static String QuanZhognUrl = BaseUrl + "Player/getQuanzhongList";
    /**
     * 选手列表里的量级
     */
    public static String LijiUrl = BaseUrl + "Player/weightLevel";
    /**
     * 选手列表里的俱乐部
     */
    public static String ClubUrl = BaseUrl + "Club/getAllClubName";

    /**
     * 所有俱乐部
     */
    public static String AllClubUrl = BaseUrl + "Club/getClubList";
    /**
     * 俱乐部详情
     */
    public static String ClubDetailUrl = BaseUrl + "Club/getClubInfo";
    /**
     * 选手详情
     */
    public static String PlayerDetailUrl = BaseUrl + "Player/getPlayerInfo";
    /**
     * 俱乐部拳手
     */
    public static String QuanshouUrl = BaseUrl + "Club/playerList";
    /**
     * 手机号登录
     */
    public static String PhoneLoginUrl = BaseUrl + "User/mobileLogin";
    /**
     * 发送手机验证码
     */
    public static String SendSmsUrl = BaseUrl + "User/sendSMS";
    /**
     * 是否重新登录验证
     */
    public static String ReLoginUrl = BaseUrl + "User/isRelogin";

    /**
     * 获取直播列表
     */
    public static String zhiboListUrl = BaseUrl + "Zhibo/zhiboList";
    /**
     * 获取回顾列表
     */
    public static String HuiguListUrl = BaseUrl + "Zhibo/huiguList";
    /**
     * 微信登录
     */
    public static String WXLoginUrl = BaseUrl + "User/weixinLogin";
    /**
     * 获取微信配置
     */
    public static String GetWXconfigUrl = BaseUrl + "User/getWechatConfig";
    /**
     * 绑定微信
     */
    public static String BangdingWXUrl = BaseUrl + "User/bindWexin";
    /**
     * 获取竞猜轮播图
     */
    public static String GuessbannerUrl = BaseUrl + "Bet/getTopImg";
    /**
     * 竞猜排行榜
     */
    public static String GuessPaihangUrl = BaseUrl + "Bet/getTopNum";

    /**
     * 获取所有赛事图片
     */
    public static String AllSaishiPicUrl = BaseUrl + "Saishi/getAllSaishiImgs";
    /**
     * 获取所有赛事名称
     */
    public static String AllSaishiNameUrl = BaseUrl + "Saishi/getAllSaishiNames";
    /**
     * 获取竞猜赛程
     */
    public static String GuessSaichengUrl = BaseUrl + "Bet/getBetSaicheng";
    /**
     * 获取用户信息
     */
    public static String GetUserInfoUrl = BaseUrl + "User/getUserInfo";

    /**
     * 下注
     */
    public static String XiazhuUrl = BaseUrl + "Bet/bet";
    /**
     * 盈利榜
     */
    public static String YingliUrl = BaseUrl + "Bet/getYingliTop";
    /**
     * 财富榜
     */
    public static String caifuUrl = BaseUrl + "Bet/getCaifuTop";
    /**
     * 竞猜榜
     */
    public static String guessBangUrl = BaseUrl + "Bet/getBetTop";
    /**
     * 签到
     */
    public static String QiandaoUrl = BaseUrl + "User/qiandao";
    /**
     * 检测签到
     */
    public static String IsQiandaoUrl = BaseUrl + "User/isQiandao";
    /**
     * 获取直播详情
     */
    public static String ZhiboInfoUrl = BaseUrl + "Zhibo/getZhiboInfo";
    /**
     * 获取拳王榜
     */
    public static String QuanwangbangUrl = BaseUrl + "Quanwangbang/index";
    /**
     * 获取竞猜记录
     */
    public static String GuessrecordUrl = BaseUrl + "Bet/betLog";
    /**
     * 获取热聊信息
     */
    public static String GetChatUrl = BaseUrl + "Bet/getMessage";
    /**
     * 发送热聊信息
     */
    public static String SendChatUrl = BaseUrl + "Bet/sendMessage";

    /**
     * 获取社区轮播图
     */
    public static String SheQuLunBoUrl = BaseUrl + "Bbs/getTopImg";
    /**
     * 获取社区列表数据
     */
    public static String SheQuData = BaseUrl + "Bbs/getZhutie";
    /**
     * 发帖图片url
     */
    public static String Uploadimage = BaseUrl + "Bbs/addBbsImg";
    /**
     * 发帖
     */
    public static String upcontent = BaseUrl + "Bbs/fatie";
    /**
     * 获取帖子详情
     */
    public static String Detailurl = BaseUrl + "Bbs/zhutieInfo";//
    /**
     * 获取我的帖子
     */
    public static String Wodeurl = BaseUrl + "Bbs/getWodetiezi";//

    /**
     * 获取评论内容
     */
    public static String COMMENT_URL = BaseUrl + "Bbs/getGentie";//
    /**
     * 打赏请求
     */
    public static String DASHANG_URL = BaseUrl + "Bbs/zanZhutie";
    /**
     * 回复
     */
    public static String COUMENT_URL = BaseUrl + "Bbs/huifu";
    /**
     * 跟帖点赞//Bbs/zanGentie
     */
    public static String GEMTIEDIANZAN_URL = BaseUrl + "Bbs/zanGentie";
    /**
     * 反馈意见
     */
    public static String SuggestionUrl = BaseUrl + "User/fankui";
    /**
     * 选手分析
     */
    public static String PlayerFenxiUrl = BaseUrl + "Bet/playerFenxi";
    /**
     * 任务状态监测
     */
    public static String TaskjianceUrl = BaseUrl + "Renwu/renwuCheck";
    /**
     * 新用户首次登录领取金币
     */
    public static String First_LIGINUrl = BaseUrl + "Renwu/shouciDengluGetGold";
    /**
     * 当天竞猜两次获取金币
     */
    public static String todayguess_Url = BaseUrl + "Renwu/betGetGold";
    /**
     * 社区回复  当天回帖获取金币
     */
    public static String shequHuifuUrl = BaseUrl + "Renwu/huitieGetGold";
    /**
     * 参与热聊活动
     */
    public static String reliaoUrl = BaseUrl + "Renwu/reliaoGetGold";
    /**
     * 检测最新版本 版本升级强制改为https，其他接口暂时不变
     */
    public static String NewVersonUrl = BaseUrl + "Sys/apkVersion";
    /**
     *下载最新版本
     */
//    public static String LoadNewVersonUrl=BaseUrl+"Sys/ApkUpload";

    /**
     * 礼品兑换轮播
     */
    public static String DuihuanBannerUrl = BaseUrl + "Duihuan/getTopImg";
    /**
     * 礼品兑换首页
     */
    public static String DuihuanUrl = BaseUrl + "Duihuan/index";
    /**
     * 获取夺宝列表
     */
    public static String DuobaoListUrl = BaseUrl + "Duihuan/duobaoList";
    /**
     * 获取今日兑换列表
     */
    public static String TodayDuihuanListUrl = BaseUrl + "Duihuan/jinribiduiList";
    /**
     * 获取普通兑换列表
     */
    public static String NormalDuihuanListUrl = BaseUrl + "Duihuan/putongduihuanList";

    /**
     * 获取夺宝详情
     */
    public static String DuobaoDetailsUrl = BaseUrl + "Duihuan/duobaoInfo";
    /**
     * 获取今日必兑详情
     */
    public static String TodaybiduiDetailsUrl = BaseUrl + "Duihuan/jinribiduiInfo";
    /**
     * 获取普通兑换详情
     */
    public static String NormalDuihuanDetailsUrl = BaseUrl + "Duihuan/putongInfo";
    /**
     * 参与夺宝
     */
    public static String CanyuDuobaoUrl = BaseUrl + "Duihuan/doDuobao";

    /**
     * 参与普通兑换
     */
    public static String CanyuNormalDuihuanUrl = BaseUrl + "Duihuan/doPutongduihuan";
    /**
     * 参与今日必兑
     */
    public static String CanyuTodayBiduiUrl = BaseUrl + "Duihuan/doGaojiduihuan";
    /**
     * 兑换记录
     */
    public static String DuihuanRecordUrl = BaseUrl + "Duihuan/duihuanLog";
    /**
     * 夺宝记录
     */
    public static String DuobaoRecordUrl = BaseUrl + "Duihuan/duobaoLog";
    /**
     * 获取收货地址
     */
    public static String GetShouhuoAddressUrl = BaseUrl + "User/getAddr";
    /**
     * 领取兑换商品
     */
    public static String LingquDuihuangoodsUrl = BaseUrl + "Duihuan/lingquShangpin";
    /**
     * 增加收货地址
     */
    public static String AddAddressUrl = BaseUrl + "User/addAddr";
    /**
     * 删除收货地址
     */
    public static String DelectAddressUrl = BaseUrl + "User/delAddr";
    /**
     * 编辑收货地址
     */
    public static String EditAddressUrl = BaseUrl + "User/editAddr";
    /**
     * 打开转盘页面
     */
    public static String ZhuanpanUrl = BaseUrl + "Duihuan/zhuanpanPage";
    /**
     * 绑定手机号
     */
    public static String BindMobileUrl = BaseUrl + "User/bindMobile";
    /**
     * 获取用户的金币
     */
    public static String GetJinBIUrl = BaseUrl + "User/getGold";
    /**
     * 获取圈子列表
     */
    public static String QuanzilistUrl = BaseUrl + "Quanzi/getBankuai";
    /**
     * 获取圈子热帖
     */
    public static String ReTieUrl = BaseUrl + "Quanzi/retie";
    /**
     * 获取圈子热帖详情
     */
    public static String ReTieDetailUrl = BaseUrl + "Quanzi/zhutieInfo";
    /**
     * 获取圈子评论内容
     */
    public static String pinglunUrl = BaseUrl + "Quanzi/getGentie";
    /**
     * 赞主贴
     */
    public static String zanzhutieUrl = BaseUrl + "Quanzi/zanZhutie";
    /**
     * 赞跟帖
     */
    public static String zanGentieUrl = BaseUrl + "Quanzi/zanGentie";
    /**
     * 圈子回复
     */
    public static String QUANZI_HUIFU_Url = BaseUrl + "Quanzi/huifu";
    /**
     * 圈子轮播图
     */
    public static String QUANZI_BANNER_Url = BaseUrl + "Quanzi/getTopImg";
    /**
     *圈子板块列表
     */
//    public static String  QUANZI_List_Url=BaseUrl+"Quanzi/getBankuai";
    /**
     * 圈子主贴列表
     */
    public static String QUANZI_zhutieList_Url = BaseUrl + "Quanzi/getZhutie";
    /**
     * 圈子发帖
     */
    public static String QUANZI_FATIE_Url = BaseUrl + "Quanzi/fatie";
    /**
     * 圈子添加帖子图片
     */
    public static String QUANZI_add_pic = BaseUrl + "Quanzi/addQuanziImg";
    /**
     * 圈子获取我的帖子
     */
    public static String QUANZI_MyTiezi = BaseUrl + "Quanzi/getWodetiezi";
    /**
     * 俱乐部图集
     */
    public static String JULEBU_TUJI = BaseUrl + "Club/imgList";
    /**
     *
     */
    public static String JINGCAI = BaseUrl + "Bet/getDuizhenBetTop";
    /**
     *
     */
    public static String YINGLI = BaseUrl + "Bet/getDuizhenYingliTop";
    /*
    * 获取视频回顾
    * */
    public static String HUIGU = BaseUrl + "Zhibo/huiguVideoList";
    /*
    * 获取视频回顾详情
    *
    * */
    public static String HUIGUXiangQing = BaseUrl + "Zhibo/getHuiguInfo";
    /*
   * 首页轮播图
   *
   * */
    public static String HOME_PAGE_BANNER = BaseUrl + "Hao/getTopImg";
    /*
     * 获取公众号图集列表
     *
     * */
    public static String TUJI_List = BaseUrl + "Hao/imgList";
    /*
     * 获取公众号视频列表
     *
     * */
    public static String VIDEO_List = BaseUrl + "Hao/videoList";
    /*
     * 获取公众号资讯列表
     *
     * */
    public static String ZIXUN_List = BaseUrl + "Hao/news";
    /*
    * 获取公众号资讯列表详情
    *
    * */
    public static String ZIXUN_Detail = BaseUrl + "Hao/newsInfo";
    /*
   * 获取公众号资讯跟帖
   *
   * */
    public static String ZIXUN_GENTIE = BaseUrl + "Hao/getNewsGentie";
    /*
    * 号赞资讯/图集/视频集 主贴
    *
    * */
    public static String ZIXUN_ZANZHUTIE = BaseUrl + "Hao/zanZhutie";
    /*
     * 号赞资讯/图集/视频集 跟贴
     *
     * */
    public static String ZIXUN_ZANGENTIE = BaseUrl + "Hao/zanGentie";
    /*
         * 获取推荐首页
         *
         * */
    public static String TUIJIAN_Main = BaseUrl + "Hao/tuijian";
    /*
      * 号 资讯/图集/视频 回复
       *
        * */
    public static String ZIXUN_pinglun = BaseUrl + "Hao/huifu";
    /*
      * 获取公众号图集详情
      *
      * */
    public static String TUJI_Detail = BaseUrl + "Hao/imgListInfo";
    /*
   * 获取公众号图集跟帖
   *
   * */
    public static String TUJI_GENTIE = BaseUrl + "Hao/getImgListGentie";
    /*
    * 获取公众号视频跟帖
    *
    * */
    public static String VIDEO_GENTIE = BaseUrl + "Hao/getVideoListGentie";
    /*
   * 获取公众号列表头
   *
   * */
    public static String HAO_LIST = BaseUrl + "Hao/user";
    /*
   * 获取公众号混合列表
   *
   * */
    public static String HAO_HUNHELIST = BaseUrl + "Hao/getHaohunhe";
    /*
   * 获取专题详情列表
   *
   * */
    public static String ZHUANTI_LIST = BaseUrl + "Hao/zhuantiInfo";
    /*
   * 获取公众号信息
   *
   * */
    public static String GET_HUSER_INFO = BaseUrl + "Hao/getHuserInfo";
    /*
   * 首页推荐获取单个赛程
   *
   * */
    public static String GET_ONLY_SAICHENG = BaseUrl + "Hao/getBetSaicheng";
    /*
   * 对阵推荐弹窗
   *
   * */
    public static String DUIZHEN_TUIJIAN = BaseUrl + "Tuijian/duizhenTuijian";
    /*
   * 分享词随机
   *
   * */
    public static String SHARE_SJC = BaseUrl + "Huodong/shareWord";
    /*
   * 分享竞猜获取20金币，每个赛程一次
   *
   * */
    public static String SHARE_JCCG = BaseUrl + "Huodong/betShare";
    /*
   * 分享直播获取20金币，每个赛程一次
   *
   * */
    public static String SHARE_ZBCG = BaseUrl + "Huodong/zhiboShare";
    /*
   * 活动奖励金币数量
   *
   * */
    public static String GET_HDJB = BaseUrl + "Huodong/getHuodongnum";
    /*
   *竞猜规则
   *
   * */
    public static String GUESS_RELUS = BaseUrl + "Sys/betXieyi?format=html";
    /*
     *金币说明
     *
     */
    public static String JINBI_SHUOMING = BaseUrl + "Sys/goldXieyi?format=html";
    /**
     * 常见问题
     */
    public static String QUESTIONS = BaseUrl + "Sys/changjianwenti?format=html";
    /**
     * 注册协议
     */
    public static String ZHUCE_XIEYI = BaseUrl + "Sys/regXieyi?format=html";
    /**
     * 微信充值接口
     */
    public static String WECHAT_PAY = BaseUrl + "Chongzhi/weixin";
    /**
     * 获取账变记录
     */
    public static String GET_GOLD_LOG = BaseUrl + "Gold/goldLog";
    /**
     * 获取消息列表
     */
    public static String GET_MESSAGE_LIST = BaseUrl + "Message/messageList";
    /**
     * 获取消息列表2
     */
    public static String GET_MESSAGE_LIST_V2 = BaseUrl + "Message/messageListV2";
    /**
     * 获取赛事赛程
     */
    public static String GET_SAIAHI_SAICHENG = BaseUrl + "Saishi/getSaishiSaicheng";
    /**
     * 获取赛事视频
     */
    public static String GET_SAIAHI_VIDEO = BaseUrl + "Saishi/getSaishiVideo";
    /**
     * 获取回顾视频年份列表
     */
    public static String GET_NIANFEN_LIST = BaseUrl + "Zhibo/huiguVideoYearList";

    /**
     * 直播互动弹幕
     */
    public static String LIVE_DANMU = BaseUrl + "Zhibo/danmuHudong";
    /**
     * 获取公众号视频列表详情
     */
    public static String VIDEO_LIST_INFO = BaseUrl + "Hao/videoListInfo";

    /**
     * 获取赛程当前对阵
     */
    public static String GET_CURRENT_DUIZHEN = BaseUrl + "Zhibo/dangqianDuizhen";
    /**
     * 最近登录用户
     */
    public static String GET_LAST_LOGIN_USER = BaseUrl + "User/lastLoginUser";
    /**
     * 获取赛程竞猜排行前$num名
     */
    public static String GET_SAICHENG_BET_TOP = BaseUrl + "Bet/getSaichengBetTop";
    /**
     * 获取赛程盈利排行前$num名
     */
    public static String GET_SAICHENG_YINGLI_TOP = BaseUrl + "Bet/getSaichengYingliTop";
    /**
     * 获取礼品兑换屏蔽用户列表
     */
    public static String GET_PIBI_USER_LIST = BaseUrl + "Duihuan/unableUser";

    /**
     * 参与普通兑换 V2
     */
    public static String NORMAL_DUIHUAN = BaseUrl + "Duihuan/doPutongduihuanV2";
    /**
     * 参与今日必兑 V2
     */
    public static String TODAY_BIDUI = BaseUrl + "Duihuan/doGaojiduihuanV2";
    /**
     * 获取赔率
     */
    public static String GET_PEILV = BaseUrl + "Bet/getPeilv";
    /**
     * 修改用户昵称
     */
    public static String XIUGAI_NAME = BaseUrl + "User/editNickname";

    /**
     * 上传头像
     */
    public static String SHANGCHAUN_PIC = BaseUrl + "User/editHeadurl";
    /**
     * 检测圈子我的回复是否有未读
     */
    public static String HUIFU_WEIDU = BaseUrl + "Quanzi/checkWodehuifu";
    /**
     * 获取圈子我的回复
     */
    public static String GET_MYHUIFU = BaseUrl + "Quanzi/getWodehuifu";
    /**
     * 活动位列表
     * $device 1 ios,2 android 默认全部
     */
    public static String GET_HUODONGWEI_LIST = BaseUrl + "Sys/getHuodongwei";
    /**
     * 获取活动弹窗列表
     */
    public static String GET_HUODONG_LIST = BaseUrl + "HuodongTanchuang/getList";
    /**
     * 获取花椒直播配置
     */
    public static String GET_HUAJIAOZHIBO_CONFIG = BaseUrl + "HuajiaoZhibo/getConfig";
    /**
     * 获取花椒直播标签
     */
    public static String GET_HUAJIAOZHIBO_TAGS = BaseUrl + "HuajiaoZhibo/getTags";
    /**
     * 获取用户花椒直播token
     */
    public static String GET_HUAJIAOZHIBO_TOKEN = BaseUrl + "HuajiaoZhibo/getToken";
    /**
     * 获取花椒直播顶部图片
     */
    public static String GET_HUAJIAOZHIBO_TOPIMG = BaseUrl + "HuajiaoZhibo/getTopImg";
    /**
     * 获取花椒直播首页
     */
    public static String GET_HUAJIAOZHIBO_HOMEPAGE = BaseUrl + "HuajiaoZhibo/home";
    /**
     * 获取花椒回放
     */
    public static String GET_HUAJIAOZHIBO_HUIFANG = BaseUrl + "HuajiaoZhibo/huifang";
    /**
     * 花椒直播预告想看+1
     */
    public static String GET_HUAJIAOZHIBO_YUGAOXIANGKAN = BaseUrl + "HuajiaoZhibo/yugaoXiangkan";
    /**
     * 获取热门圈子板块列表
     */
    public static String GET_QUANZI_LIST = BaseUrl + "Quanzi/getHotBankuai";
    /**
     * 发起花椒直播通知服务器
     */
    public static String HUAJIAO_LIVE_NOTICE_STARTZHIBO = BaseUrl + "HuajiaoZhibo/startZhibo";
    /**
     * 关闭花椒直播通知服务器
     */
    public static String HUAJIAO_LIVE_NOTICE_ENDZHIBO = BaseUrl + "HuajiaoZhibo/endZhibo";
    /**
     * 活动奖励金币数量
     */
    public static String HUODONG_JINBI_NUM = BaseUrl + "Huodong/getHuodongnum";

}

