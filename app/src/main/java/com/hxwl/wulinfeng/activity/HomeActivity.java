package com.hxwl.wulinfeng.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.common.downloadapk.CheckVersionRunnable;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.FileUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.common.utils.ThreadPoolUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.CheckVersionBean;
import com.hxwl.newwlf.modlebean.HomeRedBean;
import com.hxwl.newwlf.schedule.ScheduleFragment;
import com.hxwl.newwlf.wulin.ArtsFragment;
import com.hxwl.wlf3.home.home1.Home3Fragment;
import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragmentActivity;
import com.hxwl.wulinfeng.bean.VersionUpdateBean;
import com.hxwl.wulinfeng.fragment.FaXianFragment;
import com.hxwl.wulinfeng.fragment.SaiChengFragment;
import com.hxwl.wulinfeng.fragment.ShouYeFragment;
import com.hxwl.wulinfeng.fragment.WoFragment;
import com.hxwl.wulinfeng.fragment.WuLinFragment;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.util.config.SystemDir;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Allen on 2017/5/26.
 */
public class HomeActivity extends BaseFragmentActivity {
    //==============初始化=============
    private Bundle mSaveInstanceState;
    private String alertTitle = "存储权限申请";
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};//初始化权限 写内存权限
    private int PERMISS_BCODE = 321;
    private int SETTING_BCODE = 123;
    private AlertDialog dialog;//跳轉設置提醒
    //=========控件===============
    private RadioGroup main_radio;
    private int id; //下边tab的停留状态
    public String morenpage;
    private Button btn_shouye, btn_saicheng, btn_wuli, btn_faxian, btn_wo;
    private QBadgeView mMyDotView_shouye, mMyDotView_saicheng, mMyDotView_wuli, mMyDotView_faxian, mMyDotView_wo;
    //=========fragment===============
    private ShouYeFragment shouYeFragment;
    private SaiChengFragment saiChengFragment;
    private WuLinFragment wulinFragment;
    private FaXianFragment faxianFragment;
    private WoFragment woFragment;
    private SharedPreferences sp;
    private SharedPreferences bugSp;
    private VersionUpdateBean bean;
    private int systemCount;
    private int fankuicount;
    private String qiangzhiUpdate;
    private String downUrl;
    private boolean signatrure;
    private ImageView iv_live ,iv_point;
    private FragmentTransaction fragmentTransaction;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String position = intent.getStringExtra("signTab");
            if (!StringUtils.isBlank(position)){
                if (position.equals("saicheng")) {
                    main_radio.check(R.id.tab_saicheng);
                    if (scheduleFragment == null) {
                        scheduleFragment = new ScheduleFragment();
                    }
                    scheduleFragment.setTypeCurrent(1);

                }else if (position.equals("wulin")){
                    if (artsFragment == null) {
                        artsFragment = new ArtsFragment();
                    }
                    artsFragment.setTopic(false);
                    main_radio.check(R.id.tab_wulin);
                }
            }

        }
    }

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            main_radio.check(R.id.tab_wulin);
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (artsFragment == null) {
                artsFragment = new ArtsFragment();
                fragmentTransaction.add(R.id.fl_content, artsFragment);
            } else {
                fragmentTransaction.show(artsFragment);
            }
            artsFragment.setTopic(true);
            if(TXMediaManager.instance(HomeActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null)
            { TXMediaManager.instance(HomeActivity.this).videoDestroy();}
            fragmentTransaction.commit();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                showDialogTipUserRequestPermission();
            }
        }

        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("recommendtopic");
        registerReceiver(receiver,intentFilter);

        bugSp = getSharedPreferences("Bug_Log", MODE_PRIVATE);
        //=========发送错误报告===========
//        AppManager.getAppManager().addActivity(HomeActivity.this);
       // this.mSaveInstanceState = savedInstanceState;
        if(!TextUtils.isEmpty(MakerApplication.instance().getUid()) && !TextUtils.isEmpty(MakerApplication.instance().getLoginKey())){
            checkLoginState();
        }


        initUnreadMsg();
        checkVersion();
        deleteLocalImgCache();
        getSplashUrl();
    }
    private void sendBug() {
        String buglog = bugSp.getString("buglog","");
        if(TextUtils.isEmpty(buglog)){
            return ;
        }
        returnInfoToService(buglog);
    }
    private void returnInfoToService(final String msg){
        if(TextUtils.isEmpty(msg)){
            return ;
        }
    }
    /**
     *功能:删除本地文件
     */
    private void deleteLocalImgCache() {
        String appVersionName = AppUtils.getVersionName(this);
        String tag = (String)SPUtils.get(this, appVersionName,"-1");
        if(StringUtils.isEmpty(tag) || "-1".equals(tag)){
            FileUtils.delAllFile(SystemDir.DIR_IMAGE);
            SPUtils.put(this,appVersionName,appVersionName);
        }
    }
    /**
     * 获取引导页图片URL
     */
    private void getSplashUrl() {
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                HomeActivity.this, false, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    AdvertisementBean Advertbean = JSON.parseObject(result.getData(), AdvertisementBean.class);
//                    String url_2 = Advertbean.getUrl_2();
//                    String fileName2 = url_2.substring(url_2.lastIndexOf("/")+1);
//                    File file2 = new File(SystemDir.DIR_IMAGE+"/"+fileName2+Constants.BITMAP_SUFFIX);
//                    if (!file2.exists()) {
//                        SPUtils.put(HomeActivity.this, "isShowAd", "1");
//                        SPUtils.put(HomeActivity.this, "adUrl", url_2);
//                        List<String> picUrlList = new ArrayList<>();
//                        picUrlList.add(url_2);
//                        //下载闪屏图片
//                        File appDir = new File(SystemDir.DIR_IMAGE);
//                        if (!appDir.exists()) {
//                            appDir.mkdirs();
//                        }
//                        final File currentFile = new File(appDir, fileName2);
//                        if(currentFile.exists()){
//                            return;
//                        }
//                        Glide.with(HomeActivity.this)
//                                .load(url_2)
//                                .asBitmap()
//                                .into(new SimpleTarget<Bitmap>() {
//                                    @Override
//                                    public void onResourceReady(Bitmap bitmap1, GlideAnimation<? super Bitmap> glideAnimation) {
//                                        if (bitmap1 != null){
//                                            // 在这里执行图片保存方法
//                                            String filePath = currentFile.getPath();
//                                            BitmapUtils.saveEncryptBitmapToSD(bitmap1,filePath,60);
//                                        }
//                                    }
//                                });
//                    }
//                }
//            }
//        });
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("method", NetUrlUtils.sys_splshImg);
//        tasker.execute(map);
    }


    private void shouPopuWin(String url ,final String H5 ,final String name) {
        /**
         * 测量控件大小
         */
        View view = View.inflate(HomeActivity.this, R.layout.popu_huodong,
                null);
        //取消按钮
        ImageView iv_huodong = (ImageView) view.findViewById(R.id.iv_huodong);
        ImageView iv_back = (ImageView) view.findViewById(R.id.iv_back);
        if(!TextUtils.isEmpty(url)){
            ImageUtils.getPic(url,iv_huodong,HomeActivity.this);
            iv_back.setVisibility(View.VISIBLE);
        }else{
            iv_back.setVisibility(View.GONE);
        }
        //分局type判断显示什么按钮
        final PopupWindow pop = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 获取点击view的坐标
        pop.showAsDropDown(view);
        // 透明度渐变
        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(300);
        AnimationSet as = new AnimationSet(true);
        as.addAnimation(aa);
        view.startAnimation(as);
        iv_huodong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.setEvent(HomeActivity.this,"EventPopups","活动弹窗");
                Intent intent = new Intent(HomeActivity.this,WebViewCurrencyActivity.class);
                intent.putExtra("url",H5);
                intent.putExtra("title",name);
                startActivity(intent);
                pop.dismiss();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
    }

    private void checkVersion() {
        String str = (String) SPUtils.get(this, Constants.IS_CHECK_VERSION, "0");
        if (str.equals("1")) {
            return;
        }
        // 检查该权限是否已经获取
        int i = ContextCompat.checkSelfPermission(this, permissions[0]);
        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
        if (i != PackageManager.PERMISSION_GRANTED) {
            //没有获取权限时，结束该方法
            return;
        }
        OkHttpUtils.post()
                .url(URLS.COMMON_SYSTEMINFO)
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                CheckVersionBean bean = gson.fromJson(response, CheckVersionBean.class);
                                if (bean.getCode().equals("1000")) {
                                    //服务器返回的版本
                                    String newVersion = bean.getData().getAndroidMaxVersion();
                                    //下载地址
                                    downUrl = bean.getData().getAndroidDownloadUrl();

                                    //海选赛报名URL
                                    SPUtils.put(HomeActivity.this, "competitionUrl",bean.getData().getCompetitionUrl());
                                    //商城URL
                                    SPUtils.put(HomeActivity.this, "shoppingUrl",bean.getData().getShoppingUrl());

                                    //强制更新的标志
                                    qiangzhiUpdate = bean.getData().getAndroidMinVersion();
                                    //本地的版本
                                    int versionCode = AppUtils.getVersionCode(HomeActivity.this);
                                    if (newVersion != null && versionCode < Integer.valueOf(newVersion)) { //大于本地版本
                                        //检查本地是否已下载
                                        //如果已下载,提示用户安装
                                        //启动通知，去下载
                                        ThreadPoolUtils.newInstance().execute(new CheckVersionRunnable(HomeActivity.this, bean.getData()));
                                    }

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(HomeActivity.this));
                                    HomeActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }
    //初始化小红点
    private void initUnreadMsg() {
        sp = getSharedPreferences("MSG", MODE_PRIVATE);
        initMyFankui();
        initSystemMsg();
        initCheckLive();
    }

    private void initCheckLive() {
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                HomeActivity.this, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(result.getData());
//                        signatrure = jsonObject.getBoolean("live");
//                        if(signatrure){
//                            iv_live.setVisibility(View.VISIBLE);
//                        }else{
//                            iv_live.setVisibility(View.INVISIBLE);
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("method", NetUrlUtils.ishave_Live);
//        tasker.execute(params);
    }

    //系统消息
    private void checkLoginState() {
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                HomeActivity.this, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//
//                }
//            }
//        });
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("uid", MakerApplication.instance().getUid());
//        params.put("loginKey", MakerApplication.instance().getLoginKey());
//        params.put("method", NetUrlUtils.isneed_relogin);
//        tasker.execute(params);
    }
    //系统消息
    private void initSystemMsg() {
//        String id = sp.getString("systemlastId", "");//暂时没用了
//        int lastId = sp.getInt("systemMessagelastId", 0);
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                HomeActivity.this, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    systemCount = result.getNewCount();
//                    sp.edit().putInt("systemMessageCount", systemCount).commit();
//                    sp.edit().putInt("systemMessagelastId", result.getLastId()).commit();
//                } else if (result != null && result.getStatus().equals("empty")) {
//                    sp.edit().putInt("systemMessageCount", 0).commit();
//                    sp.edit().putInt("systemMessagelastId", result.getLastId()).commit();
//                } else {
//
//                }
//                if(fankuicount > 0 || systemCount > 0){
//                    iv_point.setVisibility(View.GONE);
//                }else{
//                    iv_point.setVisibility(View.GONE);
//                }
////                mMyDotView_wo.setBadgeNumber(fankuicount+systemCount);
//            }
//        });
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("lastId", lastId);
//        params.put("method", NetUrlUtils.message_system);
//        tasker.execute(params);
    }
    //我的反馈未读
    private void initMyFankui() {
        OkHttpUtils.post()
                .url(URLS.USER_TIP)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
//                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                HomeRedBean bean = gson.fromJson(response, HomeRedBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if (bean.getData().isHasNew()){
                                        sp.edit().putInt("fankuicount",1).commit();
                                        fankuicount=1;
                                    }else {
                                        sp.edit().putInt("fankuicount",0).commit();
                                        fankuicount=0;
                                    }
                                    if(fankuicount > 0 || systemCount > 0){
                                        iv_point.setVisibility(View.VISIBLE);
                                    }else{
                                        iv_point.setVisibility(View.GONE);
                                    }
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(HomeActivity.this));
                                    HomeActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }
    @Override
    public int getLayoutResID() {
        return R.layout.activity_home;
    }
    @Override
    public void initView() {
        main_radio = (RadioGroup) findViewById(R.id.main_radio);
        //设置上方提醒数字
        btn_shouye = (Button) findViewById(R.id.btn_shouye);
        mMyDotView_shouye = new QBadgeView(this);
        mMyDotView_shouye.bindTarget(btn_shouye);//就是这里
        btn_saicheng = (Button) findViewById(R.id.btn_saicheng);
        mMyDotView_saicheng = new QBadgeView(this);
        mMyDotView_saicheng.bindTarget(btn_saicheng);//就是这里
        btn_wuli = (Button) findViewById(R.id.btn_wuli);
        mMyDotView_wuli = new QBadgeView(this);
        mMyDotView_wuli.bindTarget(btn_wuli);//就是这里
        btn_faxian = (Button) findViewById(R.id.btn_faxian);
        mMyDotView_faxian = new QBadgeView(this);
        mMyDotView_faxian.bindTarget(btn_faxian);//就是这里
        btn_wo = (Button) findViewById(R.id.btn_wo);
        mMyDotView_wo = new QBadgeView(this);
        mMyDotView_wo.bindTarget(btn_wo);//就是这里
//        mMyDotView_wo.setBadgeNumber(2);
        mMyDotView_wo.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
            }
        });

        iv_live = (ImageView)findViewById(R.id.iv_live);
        iv_point = (ImageView)findViewById(R.id.iv_point);
    }
    @Override
    public void initData() {

        fragmentTransaction = getSupportFragmentManager ().beginTransaction();
        homeFragment = new Home3Fragment();
        fragmentTransaction.add(R.id.fl_content,homeFragment).commit();
    }

    @Override
    public void initListener() {
        //radioButton装填改变监听
        main_radio.setOnCheckedChangeListener(new OnMyCheckedChangeListener());
//        if (mSaveInstanceState == null) {
//            //初始化首次展现位置
//            initGroupPosition();
//        }
        initGroupPosition();
    }
    private Home3Fragment homeFragment;
    private ScheduleFragment scheduleFragment;
    private ArtsFragment artsFragment;
    private void hide(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (scheduleFragment != null) {
            transaction.hide(scheduleFragment);
        }
        if (artsFragment != null) {
            transaction.hide(artsFragment);
        }
        if (woFragment != null) {
            transaction.hide(woFragment);
        }
    }
    private final class OnMyCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            hide(fragmentTransaction);
            switch (checkedId) {
                case R.id.tab_shouye:
                    if (homeFragment == null) {
                        homeFragment = new Home3Fragment();
                        fragmentTransaction.add(R.id.fl_content, homeFragment);
                    } else {
                        fragmentTransaction.show(homeFragment);
                    }
                    if(TXMediaManager.instance(HomeActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null)
                    { TXMediaManager.instance(HomeActivity.this).videoDestroy();}
                    break;
                case R.id.tab_saicheng:
                    if (homeFragment == null) {
                        homeFragment = new Home3Fragment();
                        fragmentTransaction.add(R.id.fl_content, homeFragment);
                    } else {
                        fragmentTransaction.show(homeFragment);
                    }

//                    if (scheduleFragment == null) {
//                        scheduleFragment = new ScheduleFragment();
//                        fragmentTransaction.add(R.id.fl_content, scheduleFragment);
//                    } else {
//                        fragmentTransaction.show(scheduleFragment);
//                    }
                    if(TXMediaManager.instance(HomeActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null)
                    { TXMediaManager.instance(HomeActivity.this).videoDestroy();}
                    break;
                case R.id.tab_wulin:
                    if (artsFragment == null) {
                        artsFragment = new ArtsFragment();
                        fragmentTransaction.add(R.id.fl_content, artsFragment);
                    } else {
                        fragmentTransaction.show(artsFragment);
                    }

                    if(TXMediaManager.instance(HomeActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null)
                    { TXMediaManager.instance(HomeActivity.this).videoDestroy();}
                    break;
                case R.id.tab_wo:
                    if (woFragment == null) {
                        woFragment = new WoFragment();
                        fragmentTransaction.add(R.id.fl_content, woFragment);
                    } else {
                        fragmentTransaction.show(woFragment);
                    }

                    if(TXMediaManager.instance(HomeActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null)
                    { TXMediaManager.instance(HomeActivity.this).videoDestroy();}
                    break;
                default:
                    break;
            }
            fragmentTransaction.commit();

        }
    }


    private void initGroupPosition() {
        id = getIntent().getIntExtra("fragId", -1);
        morenpage = getIntent().getStringExtra("morenfrag");
        if (id == 1) {
            main_radio.check(R.id.tab_shouye);//主页
        } else if (id == 2) {
            main_radio.check(R.id.tab_saicheng);//赛程
        } else if (id == 3) {
            main_radio.check(R.id.tab_wulin);//武林
        } else if (id == 4) {
            main_radio.check(R.id.tab_wo);//我的
        } else {
            main_radio.check(R.id.tab_shouye);//主页
        }
    }

    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle(alertTitle)
                .setMessage(getResources().getString(R.string.app_name) + "需要使用存储权限，您是否允许？" + getResources().getString(R.string.app_name))
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, PERMISS_BCODE);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISS_BCODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限
    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle(alertTitle)
                .setMessage("请在-应用设置-权限-中，允许" + getResources().getString(R.string.app_name) + "使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, SETTING_BCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING_BCODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(ScreenUtils.isLandScape(this)){
                TXMediaManager.instance(HomeActivity.this).mPlayerView1.backPress() ;
            }else{
                exitBy2Click(); //调用双击退出函数
            }
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;// 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            AppManager.getAppManager().finishAllActivity();
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver!=null){
            receiver=null;
        }
    }
}
