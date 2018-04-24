package com.hxwl.newwlf;


/**
 * Created by Administrator on 2018/1/22.
 */

public class URLS {
    public static final String URL="http://api.heixiongboji.top/";
    //登录
    public static final String LOGIN=URL+"user/pwdLogin";
    //首页 / 首页菜单列表
    public static final String HOME_ONE=URL+"index/columnList";
    //首页 / 首页数据
    public static final String HOME_HOME=URL+"index/indexList";
    //首页 / 直播间信息
    public static final String HOME_LIVEINFO=URL+"schedule/getScheduleLiveInfo";
    //首页 / 赛程id获取对阵列表
    public static final String HOME_GETSCHEDULEAGAINSTLIST=URL+"schedule/getScheduleAgainstList";
    //首页 / 用户关注选手列表
    public static final String HOME_USERPLAYERATTENTIONLIST=URL+"player/userPlayerAttentionList";
    //首页 / 获取资讯列表根据选手id
    public static final String HOME_GETNEWSLISTBYPLAYERID=URL+"player/getNewsListByPlayerId";
    //首页详情页--赛事指南
    public static final String SCHEDULE_EVENTS=URL+"schedule/2";
    //首页详情页--赛事动态
    public static final String SCHEDULE_DYNAMIC=URL+"schedule/events/2";


    //赛程 / 直播中,未开始,回放列表
    public static final String SCHEDULE_SCHEDULELIST=URL+"schedule/getLivePlaybackScheduleList";
    //赛程 /直播中与未开始的赛程
    public static final String SCHEDULE_GETLIVESCHEDULELIST=URL+"schedule/getLiveScheduleList";
    //赛程 / 回放赛程列表
    public static final String SCHEDULE_GETPLAYBACKSCHEDULELIST=URL+"schedule/getPlaybackScheduleList";

    //赛程 / 获取重量级列表
    public static final String SCHEDULE_WEIGHTLEVELLIST=URL+"player/getWeightLevelList";
    //赛程 / 选手列表分页
    public static final String SCHEDULE_PLAYERLIST=URL+"player/getPlayerList";
    //赛程 / 获取赛程日历
    public static final String SCHEDULE_SCHEDULECALENDAR=URL+"schedule/getScheduleCalendar";
    //赛程 / 获取赛程日历年月
    public static final String SCHEDULE_SGETSCHEDULEDATELIST=URL+"schedule/getScheduleDateList";
    //赛程 / 俱乐部列表
    public static final String SCHEDULE_CULBR=URL+"club/clubList";
    //赛程 / 俱乐部信息
    public static final String SCHEDULE_CULBRINFO=URL+"club/clubInfo";
    //赛程 / 省份代理列表
    public static final String SCHEDULE_AGENTLIST=URL+"agent/agentList";
    //赛程 / 获取选手详情
    public static final String SCHEDULE_GETPLAYERINFO=URL+"player/getPlayerInfo";
    //赛程 / 获取选手对阵列表
    public static final String SCHEDULE_GETPLAYERAGAINSTLIST=URL+"player/getPlayerAgainstList";
    //赛程 / 获取省份代理详情
    public static final String SCHEDULE_AGENTINFO=URL+"agent/agentInfo";
    //赛程 / 获取省代选手列表
    public static final String SCHEDULE_AGENTPLAYERLIST=URL+"agent/agentPlayerList";
    //赛程 / 获取省代赛程回放
    public static final String SCHEDULE_AGENTSCHEDULELIST=URL+"agent/agentScheduleList";
    //资讯 / 省代资讯列表
    public static final String SCHEDULE_AGENTNEWSLIST=URL+"news/agentNewsList";


    //赛程 / 用户预约赛程
    public static final String SCHEDULE_USERSUBSCRIBE=URL+"subscribe/userSubscribe";
    //赛程 / 用户取消赛程预约
    public static final String SCHEDULE_USERCANCELSUBSCRIBE=URL+"subscribe/userCancelSubscribe";
    //赛程 / 用户关注选手
    public static final String SCHEDULE_USERPLAYERATTENTION=URL+"player/userPlayerAttention";
    //赛程 / 用户取消关注选手
    public static final String SCHEDULE_USERCANCELPLAYERATTENTION=URL+"player/userCancelPlayerAttention";
    //赛程 / 获取赛程回放信息
    public static final String SCHEDULE_GETSCHEDULEPLAYBACKINFO=URL+"schedule/getSchedulePlaybackInfo";



    //武林 / 话题列表
    public static final String SCHEDULE_TOPICLIST=URL+"topic/topicList";
    //武林 / 热门话题
    public static final String SCHEDULE_HOTTOPICLIST=URL+"topic/hotTopicList";
    //武林 / 发布图文动态
    public static final String SCHEDULE_PUBLISHIMAGESTEXT=URL+"dynamic/publishImagesText";
    //武林 / 发布视频
    public static final String SCHEDULE_PUBLISHVIDEOTEXT=URL+"dynamic/publishVideoText";
    //武林 / 获取osstoken
    public static final String SCHEDULE_GETOSSTOKEN=URL+"common/getOSSToken";
    //武林 /  动态列表
    public static final String SCHEDULE_DYNAMICLIST=URL+"dynamic/dynamicList";
    //武林 /  动态评论
    public static final String SCHEDULE_DYNAMICCOMMENT=URL+"dynamic/dynamicComment";
    //武林 /  赞动态
    public static final String SCHEDULE_ADDDYNAMICMESSAGEFAVOUR=URL+"favour/addFavour";
    //武林 /   取消动态赞
    public static final String SCHEDULE_CANCELDYNAMICMESSAGEFAVOUR=URL+"favour/cancelFavour";

    //对阵 / 详情
    public static final String AGAINST_AGAINSTINFO=URL+"against/againstInfo";
    //对阵 / 评论
    public static final String AGAINST_AGAINSTCOMMENTLIST=URL+"against/againstCommentList";
    //对阵 / 评论对阵
    public static final String GAINST_AGAINSTCOMMENT=URL+"against/againstComment";
    //对阵 / 评论回复
    public static final String AGAINST_AGAINSTCOMMENTREPLY=URL+"against/againstCommentReply";

    //3.0 /评论列表
    public static final String COMMENTLIST=URL+"comment_favour/commentList";
    //3.0 /评论
    public static final String COMMENT=URL+"comment_favour/comment";
    //3.0 /回复评论
    public static final String COMMENTREPLY=URL+"comment_favour/commentReply";
    //3.0 /子评论列表
    public static final String COMMENTREPLYLIST=URL+"comment_favour/commentReplyList";
    //3.0 /赞
    public static final String ADDFAVOUR=URL+"comment_favour/addFavour";
    //3.0 /取消赞
    public static final String CANCELFAVOUR=URL+"comment_favour/cancelFavour";
    //3.0 /获取详情总赞数与总评论数
    public static final String GETNUM=URL+"comment_favour/getNum";

    //资讯 / 资讯列表
    public static final String SCHEDULE_NEWSLIST=URL+"news/newsList";
    //资讯 / 资讯详情
    public static final String SCHEDULE_NEWSINFO=URL+"news/newsInfo";
    //资讯 / 资讯评论
    public static final String NEWS_NEWSCOMMENTLIST=URL+"news/newsCommentList";
    //资讯 / 评论资讯
    public static final String NEWS_NEWSCOMMENT=URL+"news/newsComment";
    //资讯 / 资讯评论回复
    public static final String NEWS_NEWSCOMMENTREPLY=URL+"news/newsCommentReply";
    //首页 / 关注 /选手评论列表
    public static final String HOME_PLAYERCOMMENTLIST=URL+"player/playerCommentList";
    //首页 / 关注 /选手评论
    public static final String HOME_PLAYERCOMMENT=URL+"player/playerComment";
    //首页 / 关注 /选手评论
    public static final String HOME_PLAYERCOMMENTREPLY=URL+"player/playerCommentReply";
    //资讯 / 专题列表
    public static final String NEWS_SUBJECTLIST=URL+"news/subjectList";
    //资讯 / 专题资讯列表
    public static final String NEWS_SUBJECTNEWSLIST=URL+"news/subjectNewsList";
    //资讯 / 神评列表
    public static final String NEWS_GODCOMMENTLIST=URL+"news/godCommentList";
    //资讯 / 根据1级评论列出所有回复
    public static final String NEWS_COMMENTREPLYLIST=URL+"news/commentReplyList";
    //资讯 / 选手一级评论回复列表
    public static final String PLAYER_COMMENTREPLYLIST=URL+"player/commentReplyList";
    //武林 / 动态评论回复列表
    public static final String DYNAMIC_DYNAMICCOMMENTREPLYLIST=URL+"dynamic/dynamicCommentReplyList";
    //武林 / 动态评论回复列表
    public static final String AGAINST_COMMENTREPLYLIST=URL+"against/commentReplyList";


    //用户 / 获取验证码
    public static final String HOME_GETCODE=URL+"user/getCode";
    //用户 / 登录
    public static final String USER_LOGIN=URL+"user/login";
    //用户 / 更改个人信息
    public static final String USER_UPDATE=URL+"user/update";
    //用户 / 验证是否存在
    public static final String USER_ISEXIST=URL+"user/isExist";


    //个人中心 / 用户预约赛程列表
    public static final String SCHEDULE_GETUSERSUBSCRIBELIST=URL+"schedule/getUserSubscribeList";
    //个人中心 / 反馈列表
    public static final String FEEDBACK_FEEDBACKLIST=URL+"feedback/feedbackList";
    //个人中心 / 反馈信息
    public static final String SCHEDULE_ADDFEEDBACK=URL+"feedback/addFeedback";


    //搜索 / 选手搜索接口
    public static final String SEARCH_PLAYER=URL+"search/player";
    //搜索 / 热搜词列表
    public static final String SEARCH_HOT=URL+"search/hot";
    //搜索 / 赛程搜索
    public static final String SEARCH_SCHEDULE=URL+"search/schedule";
    //搜索 / 对阵搜索
    public static final String SEARCH_AGAINST=URL+"search/against";
    //搜索 / 话题搜索
    public static final String SEARCH_TOPIC=URL+"search/topic";
    //搜索 / 资讯搜索
    public static final String SEARCH_NEWS=URL+"search/news";

    //富文本静态资源 / 根据id或者title 查找富文本内容
    public static final String STATICSOURCE_GET=URL+"staticSource/get";


    //用户 / 常见问题接口
    public static final String HOTQUESTION_ALL=URL+"hotQuestion/all";
    //个人中心 / 新的信息
    public static final String USER_TIP=URL+"user/tip";
    //首页预约
    public static final String COMMON_USERSUBSCRIBESTATUS=URL+"common/userSubscribeStatus";






    //图片前缀
    public static final String IMG="http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/images/";
    //视频前缀
    public static final String VIDEO="http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/videos/";
    //HTML前缀
    public static final String HTML="http://heixiong-wlf.oss-cn-beijing.aliyuncs.com/html/";
    //微信
    public static final String URL_WX_BASE="https://api.weixin.qq.com/sns/oauth2/access_token";
    //微信
    public static final String URL_USERINFO="https://api.weixin.qq.com/sns/userinfo";


    //用户 / 系统信息
    public static final String COMMON_SYSTEMINFO=URL+"common/systemInfo";

}
