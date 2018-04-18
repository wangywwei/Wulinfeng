package com.hxwl.wulinfeng;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.hxwl.common.dbdaoutils.dbutils.GreenDaoUtils;
import com.hxwl.common.errorlog.CrashHandler;
import com.hxwl.common.errorlog.TheAppCrashHandler;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.LoginBean;
import com.hxwl.newwlf.modlebean.TuiSongBean;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.base.BaseFragmentActivity;
import com.hxwl.wulinfeng.bean.LoginUser;
import com.hxwl.wulinfeng.bean.SaichengBean;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.config.SystemDir;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.ext.group.TIMGroupAssistantListener;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.tendcloud.tenddata.TCAgent;

import java.io.File;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import okhttp3.OkHttpClient;


/**
 *  武林风的主application 集成保证没有方法数量的限制
 */
public class MakerApplication extends MultiDexApplication {
    /**
     * 上下文
     */
    public static BaseFragmentActivity activity;
    public static MakerApplication instance;
    public static String WLF_Flag = "wlf";
    public static String PRO_NAME = "com.hxwl.wulinfeng";

    public TheAppCrashHandler m_CrashHandler;
    public static OkHttpClient okHttpClient;

    public static IWXAPI api;

    private void regToWx(){
        api= WXAPIFactory.createWXAPI(this,Constants.APP_ID);
        api.registerApp(Constants.APP_ID);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        regToWx();
        initIM();//腾讯IM初始化
        //设置信鸽的相关内容
        setXgContent();

        ShareSDK.initSDK(this);
        //初始化数据库
        initDatabase();
        //初始化创建文件夹
        initcreatFile();


        //错误日志捕捉
        m_CrashHandler = TheAppCrashHandler.getInstance();
        m_CrashHandler.init(getApplicationContext());
        CrashHandler.getInstance().init(this);
        initTencentX5();

        TCAgent.LOG_ON=true;
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        TCAgent.init(this);
        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
        TCAgent.setReportUncaughtExceptions(true);


    }





    private String tag="TAG";
    private void initIM() {
        //初始化SDK基本配置
        TIMSdkConfig config = new TIMSdkConfig(1400068524)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

//初始化SDK
        TIMManager.getInstance().init(getApplicationContext(), config);
//基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置群组资料拉取字段
//                .setGroupSettings(initGroupSettings())
                //设置资料关系链拉取字段
//                .setFriendshipSettings(initFriendshipSettings())
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        Log.i(tag, "onForceOffline");
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新userSig重新登录SDK
                        Log.i(tag, "onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        Log.i(tag, "onConnected");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        Log.i(tag, "onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        Log.i(tag, "onWifiNeedAuth");
                    }
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        Log.i(tag, "onGroupTipsEvent, type: " + elem.getTipsType());
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(tag, "onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        Log.i(tag, "onRefreshConversation, conversation size: " + conversations.size());
                    }
                });

//消息扩展用户配置
        userConfig = new TIMUserConfigMsgExt(userConfig)
                //禁用消息存储
                .enableStorage(false)
                //开启消息已读回执
                .enableReadReceipt(true);

        //资料关系链扩展用户配置
        userConfig = new TIMUserConfigSnsExt(userConfig)
                //开启资料关系链本地存储
                .enableFriendshipStorage(true)
                //设置关系链变更事件监听器
                .setFriendshipProxyListener(new TIMFriendshipProxyListener() {
                    @Override
                    public void OnAddFriends(List<TIMUserProfile> users) {
                        Log.i(tag, "OnAddFriends");
                    }

                    @Override
                    public void OnDelFriends(List<String> identifiers) {
                        Log.i(tag, "OnDelFriends");
                    }

                    @Override
                    public void OnFriendProfileUpdate(List<TIMUserProfile> profiles) {
                        Log.i(tag, "OnFriendProfileUpdate");
                    }

                    @Override
                    public void OnAddFriendReqs(List<TIMSNSChangeInfo> reqs) {
                        Log.i(tag, "OnAddFriendReqs");
                    }
                });

//群组管理扩展用户配置
        userConfig = new TIMUserConfigGroupExt(userConfig)
                //开启群组资料本地存储
                .enableGroupStorage(true)
                //设置群组资料变更事件监听器
                .setGroupAssistantListener(new TIMGroupAssistantListener() {
                    @Override
                    public void onMemberJoin(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        Log.i(tag, "onMemberJoin");
                    }

                    @Override
                    public void onMemberQuit(String groupId, List<String> members) {
                        Log.i(tag, "onMemberQuit");
                    }

                    @Override
                    public void onMemberUpdate(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        Log.i(tag, "onMemberUpdate");
                    }

                    @Override
                    public void onGroupAdd(TIMGroupCacheInfo groupCacheInfo) {
                        Log.i(tag, "onGroupAdd");
                    }

                    @Override
                    public void onGroupDelete(String groupId) {
                        Log.i(tag, "onGroupDelete");
                    }

                    @Override
                    public void onGroupUpdate(TIMGroupCacheInfo groupCacheInfo) {
                        Log.i(tag, "onGroupUpdate");
                    }
                });

//将用户配置与通讯管理器进行绑定
        TIMManager.getInstance().setUserConfig(userConfig);

    }

    /**
     * 文件夹初始化创建   检查是不是已经创建了需要的文件夹
     */
    private void initcreatFile() {
        File file = new File(SystemDir.DIR_LONG_CACHE);
        if(!file.exists()){
            file.mkdirs();
        }
        File file1 = new File(SystemDir.DIR_ERROR_MSG);
        if(!file1.exists()){
            file1.mkdirs();
        }
        File file2 = new File(SystemDir.DIR_UPDATE_APK);
        if(!file2.mkdirs()){
            file2.mkdirs();
        }
    }
    /**
     * 功能:初始化数据库
     */
    private void initDatabase() {
        GreenDaoUtils.getSingleTon(getApplicationContext());
    }

    private String pushToken;
    public void setPushToken(String pushToken) {
        this.pushToken=pushToken;
        SPUtils.put(this, "pushToken",pushToken);
    }

    public String getPushToken() {
        if (StringUtils.isBlank(pushToken)){
            return (String) SPUtils.get(this, "pushToken","");
        }else {
            return pushToken;
        }

    }

    /**
     * 设置信鸽的相关内容 任务栏通知  信鸽
     */
    private void setXgContent() {
        // 在主进程设置信鸽相关的内容

        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
            //token在设备卸载重装的时候有可能会变
                Log.e("TPush", "注册成功，设备token为：" + data);
                setPushToken(data+"");
                Log.e("TPush", "设备token为：" + getPushToken());
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.e("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });
        XGPushManager.bindAccount(getApplicationContext(), "XINGE");
            // 为保证弹出通知前一定调用本方法，需要在application的onCreate注册
            // 收到通知时，会调用本回调函数。
            // 相当于这个回调会拦截在信鸽的弹出通知之前被截取
            // 一般上针对需要获取通知内容、标题，设置通知点击的跳转逻辑等等
            XGPushManager
                    .setNotifactionCallback(new XGPushNotifactionCallback() {

                        @Override
                        public void handleNotify(XGNotifaction xGNotifaction) {
                            Log.e("test", "处理信鸽通知：" + xGNotifaction);
                            // 获取标签、内容、自定义内容
                            String title = xGNotifaction.getTitle();
                            String content = xGNotifaction.getContent();
                            String customContent = xGNotifaction
                                    .getCustomContent();
                            // 其它的处理
                            // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
                            showNotification(title,content,customContent);
                        }
                    });

    }

    private void showNotification(String title, String content, String customContent) {

        JsonValidator jsonValidator = new JsonValidator();
        if (jsonValidator.validate(customContent)) {
            Gson gson = new Gson();
            try {
                TuiSongBean bean = gson.fromJson(customContent, TuiSongBean.class);
                Intent newIntent = new Intent(this, HuiGuDetailActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newIntent.putExtra("scheduleId",bean.getScheduleId()+"");
                newIntent.putExtra("reliao",1);
                int id = (int) (System.currentTimeMillis() / 1000);
                PendingIntent pendingIntent=PendingIntent.getActivity(this,id,newIntent,PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);
                manager.notify(id,builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 内核信息
     */
       private void  initTencentX5(){
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
//                Toast.makeText(getApplicationContext(),"arg0 = " + arg0,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }


    public static MakerApplication instance() {
        return instance;
    }


    /**
     * 登陆者信息
     */
    private String mobile;
    private String pwd;
    private String password;
    private String uid ;
    private String loginKey ;
    private String username ;
    private String head_pic ;


    public void setLoginState(Activity activity ,String loginstate){
        SPUtils.put(activity, Constants.LOGIN_STATE,loginstate);
    }
    public String getLoginState(){
        return (String) SPUtils.get(this, Constants.LOGIN_STATE,"");
    }

    public void setLoginWX(Activity activity ,String loginstate){
        SPUtils.put(activity,"LoginWX" ,loginstate);
    }
    public String getLoginWX(){
        return (String) SPUtils.get(this, "LoginWX","");
    }

    public static String LOGIN = "login" ;
    public static String LOGOUT = "logout" ;
    /**
     * 保存登录信息
     */
    public void saveLoginInfo(final LoginUser user , Activity activity) {
        if(user == null){
            return ;
        }
        this.uid = user.getUid();
        this.loginKey = user.getLoginKey();
        this.username = user.getNickname();
        this.head_pic = user.getHead_url();
        this.mobile = user.getMobile();
        this.pwd = user.getPwd();
        this.password = user.getPassword();

        //存储用户信息到本地
        SPUtils.put(activity, Constants.USER_ID,uid);
        SPUtils.put(activity,Constants.USER_LOGIN_KEY,loginKey);
        SPUtils.put(activity, Constants.USER_Name,user.getNickname());
        SPUtils.put(activity, Constants.USER_PHONE,user.getMobile());
        SPUtils.put(activity,Constants.USER_ICON,user.getHead_url());
        SPUtils.put(activity,Constants.USER_PWD,user.getPwd());
        SPUtils.put(activity,Constants.USER_PASSWORD,user.getPwd());
    }


    /**
     * 保存登录信息
     */
    private String id;//用户id
    private String phone;//手机
    private String nickName;//昵称
    private String headImg;//头像
    private String weixinUnionId;//微信unionid
    private String signature;
    private String token;

    public String getWeixinUnionId() {
        if(!TextUtils.isEmpty(weixinUnionId)){
            return weixinUnionId;
        }else{
            return (String) SPUtils.get(this, "weixinUnionId","");
        }
    }

    public String getToken() {
        if(!TextUtils.isEmpty(token)){
            return token;
        }else{
            return (String) SPUtils.get(this, "token","");
        }
    }

    public void setToken(String token) {
        if(!TextUtils.isEmpty(password)){
            this.token = token ;
            SPUtils.put(this, "token",token);
        }
    }

    public void saveLogin(final LoginBean.DataBean user , Activity activity) {
        if(user == null){
            return ;
        }
        this.token = user.getToken();
        this.id = user.getId()+"";
        this.phone = user.getPhone();
        this.nickName = user.getNickName();
        this.headImg = user.getHeadImg();
        this.weixinUnionId = user.getWeixinUnionId();
        this.signature = user.getSignature()+"";
        //存储用户信息到本地
        SPUtils.put(activity,"id",id);

        if (!StringUtils.isBlank(phone)){
            SPUtils.put(activity,"phone",phone);
        }
        if (StringUtils.isBlank(nickName)){
            nickName="用户"+phone;
        }
        SPUtils.put(activity,"token",token);
        SPUtils.put(activity,"nickName",nickName);
        SPUtils.put(activity,"headImg",URLS.IMG+headImg);
        SPUtils.put(activity,"weixinUnionId",weixinUnionId);
        SPUtils.put(activity,"signature",signature);
    }
    public String getHeadImg(){
        return (String) SPUtils.get(this, "headImg","");

    }

    public String getSignature(){
        if(!TextUtils.isEmpty(signature)){
            return signature;
        }else{
            return (String) SPUtils.get(this, "signature","");
        }
    }

    public String getNickName(){
        if(!TextUtils.isEmpty(nickName)){
            return nickName;
        }else{
            return (String) SPUtils.get(this, "nickName","");
        }
    }

    public void clearUserInfo(){
        try {
            SPUtils.deleteAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getPassWord(){
        if(!TextUtils.isEmpty(password)){
            return password;
        }else{
            return (String) SPUtils.get(this, Constants.USER_PASSWORD,"");
        }
    }
    public void setPassWord(String password){
        if(!TextUtils.isEmpty(password)){
            this.password = password ;
            SPUtils.put(this, Constants.USER_PASSWORD,password);
        }
    }
    public String getUid(){
        if(!TextUtils.isEmpty(id)){
            return id;
        }else{
            return (String) SPUtils.get(this, "id","");
        }
    }
    public String getUserid(){
        if(!TextUtils.isEmpty(id)){
            return id;
        }else{
            return (String) SPUtils.get(this, "id","");
        }
    }

    public void setHeadPic(String img){
        if(!TextUtils.isEmpty(img)){
            this.head_pic = img ;
            SPUtils.put(this, Constants.USER_ICON,URLS.IMG+img);
            SPUtils.put(this, "headImg", URLS.IMG+img);
        }
    }
    public String getHeadPic(){
        if(!TextUtils.isEmpty(headImg)){
            return headImg;
        }else{
            return (String) SPUtils.get(this, "headImg","");
        }
    }
    public String getLoginKey(){
        if(!TextUtils.isEmpty(loginKey)){
            return loginKey;
        }else{
            return (String) SPUtils.get(this, Constants.USER_LOGIN_KEY,"");
        }
    }
    public String getMobile(){
        if(!TextUtils.isEmpty(phone)){
            return phone;
        }else{
            return (String) SPUtils.get(this, "phone","");
        }
    }
    public String getUsername(){
        if(!TextUtils.isEmpty(nickName)){
            return nickName;
        }else{
            return (String) SPUtils.get(this, "nickName","");
        }
    }
    public String getPwd(){
        if(!TextUtils.isEmpty(pwd)){
            return pwd;
        }else{
            return (String) SPUtils.get(this, Constants.USER_PWD,"");
        }
    }
    public void setUserName(String name ,Activity activity){
        if(!TextUtils.isEmpty(name)){
            this.nickName = name ;
            SPUtils.put(activity, Constants.USER_Name,name);
            SPUtils.put(activity,"nickName",nickName);
        }
    }
    public void setPhone(String phone ,Activity activity){
        if(!TextUtils.isEmpty(phone)){
            this.phone = phone ;
            SPUtils.put(activity, Constants.USER_PHONE,phone);
            SPUtils.put(activity,"phone",phone);
        }
    }

    public void makeSaicheng(Context activity ,SaichengBean bean){
        if(bean == null){return ;}
//        ToastUtils.showToast(activity,bean.getStart_time());
        if(bean.getStart_time() == null){
            ToastUtils.showToast(activity,"开始时间为空");
            return;
        }
        SPUtils.putYuYueInfo(activity ,bean) ;//保存预约信息
    }

}


