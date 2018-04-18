package com.hxwl.common.tencentplay.tencentCloud;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.common.utils.DensityUtil;
import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.LiveDetailActivity;
import com.hxwl.wulinfeng.bean.SocketLoginBean;
import com.hxwl.wulinfeng.socket.DanMuDataListener;
import com.hxwl.wulinfeng.socket.HeiXiongSocketClient;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.SPUtils;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yasic.bubbleview.BubbleView;

import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

import static com.hxwl.common.tencentplay.tencentCloud.TXMediaManager.CURRENT_PLAYING_URL;

/**
 * 重写方法 腾讯播放器的方法类（基类）
 * Created by Allen on 2017/8/23.
 */

public abstract class TXCloudViewViewA extends FrameLayout implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener, ITXLivePlayListener, DanMuDataListener {

    public static boolean SAVE_PROGRESS = true;

    public ImageView ivHudong;
    protected DanmakuView danmakuView;
    protected DanmakuContext danmakuContext;
    protected Context context;
    public TXMediaManager instance;
    protected BubbleView heart_layout1;
    protected BubbleView heart_layout2;
    protected LinearLayout ll_edit_msg;
    protected V4HudongView mV4hudongView;

    protected LinearLayout ll_danmu;
    private Dialog dialog;
    private TextView tv_send;
    private TextView tv_textcount;
    private EditText et_keywored;
    protected RelativeLayout rl_hudong;
    protected RelativeLayout rl_chongbo;
    //错误提示布局 文本框
    protected RelativeLayout rl_error_hint;

    protected TextView tv_error;
    protected RelativeLayout iv_chongbo;

    private int error_count = 0;
    private ImageView iv_backgroud;

    public TXCloudViewViewA(Context context) {
        super(context);
        this.context = context;
        if (instance == null) {
            instance = TXMediaManager.instance(context);
        }
        init(context);
    }

    public TXCloudViewViewA(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        if (instance == null) {
            instance = TXMediaManager.instance(context);
        }
        init(context);
    }

    public abstract int getLayoutId();

    //基本的参数
    public int currentState = -1;
    public int currentScreen = -1;
    public boolean loop = false;
    public Map<String, String> headData;

    public ImageView startButton;
    public SeekBar progressBar;
    public ImageView fullscreenButton;

    public ImageView ivDanmu;//弹幕
    protected boolean showDanmaku;
    //分享
    private ImageView ivShare;

    public TextView currentTimeTextView, totalTimeTextView;
    public ViewGroup textureViewContainer;
    public ViewGroup topContainer, bottomContainer;

    protected int mScreenWidth;
    protected int mScreenHeight;
    protected AudioManager mAudioManager;
    protected Handler mHandler;
    protected ProgressTimerTask mProgressTimerTask;

    public static int FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
    public static int NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

    //回调参数的实现
    public String url = "";
    public Object[] objects = null;
    public int seekToInAdvance = 0;

    private ILetv mILetv;
    //弹幕是否打开，默认打开
    protected boolean isOpenDanmu = true;
    //互动是否打开，默认打开
    protected boolean isOpenHudong = true;
    protected BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    public static final int CURRENT_STATE_NORMAL = 0;
    public static final int CURRENT_STATE_PREPARING = 1;
    public static final int CURRENT_STATE_PLAYING = 2;
    public static final int CURRENT_STATE_PLAYING_BUFFERING_START = 3;
    public static final int CURRENT_STATE_PAUSE = 5;
    public static final int CURRENT_STATE_AUTO_COMPLETE = 6;
    public static final int CURRENT_STATE_ERROR = 7;

    public static final int SCREEN_LAYOUT_NORMAL = 0;
    public static final int SCREEN_LAYOUT_LIST = 1;
    public static final int SCREEN_WINDOW_FULLSCREEN = 2;
    public static final int SCREEN_WINDOW_TINY = 3;

    public static boolean ACTION_BAR_EXIST = true;
    public static boolean TOOL_BAR_EXIST = true;

    protected float mDownX;
    protected float mDownY;
    protected boolean mChangeVolume;
    protected boolean mChangePosition;
    protected boolean mChangeBrightness;
    protected int mGestureDownPosition;
    protected int mGestureDownVolume;
    protected float mGestureDownBrightness;
    protected int mSeekTimePosition;

    //时间 bean对象
    protected static TXUserAction TX_USER_EVENT; //储存用户的基本操作
    protected static Timer UPDATE_PROGRESS_TIMER;

    public static boolean WIFI_TIP_DIALOG_SHOWED = false;

    private Random mRandom = new Random();
    private Timer mTimer = new Timer();
    private Timer animationTimer = new Timer();
    private int[] redquans = new int[]{R.drawable.red_fist1, R.drawable.red_fist2, R.drawable.red_fist3, R.drawable.red_fist4, R.drawable.red_fist5, R.drawable.red_fist6, R.drawable.red_fist7, R.drawable.red_fist8, R.drawable.red_fist9, R.drawable.red_fist10};
    private int[] bluequans = new int[]{R.drawable.blue_fist1, R.drawable.blue_fist2, R.drawable.blue_fist3, R.drawable.blue_fist4, R.drawable.blue_fist5, R.drawable.blue_fist6, R.drawable.blue_fist7, R.drawable.blue_fist8, R.drawable.blue_fist9, R.drawable.blue_fist10};
    private int[] redlegs = new int[]{R.drawable.red_leg1, R.drawable.red_leg2, R.drawable.red_leg3, R.drawable.red_leg6, R.drawable.red_leg7, R.drawable.red_leg8, R.drawable.red_leg9, R.drawable.red_leg10};
    private int[] bluelegs = new int[]{R.drawable.blue_leg1, R.drawable.blue_leg2, R.drawable.blue_leg3, R.drawable.blue_leg6, R.drawable.blue_leg7, R.drawable.blue_leg8, R.drawable.blue_leg9, R.drawable.blue_leg10};
    private int[] redknees = new int[]{R.drawable.red_knee1, R.drawable.red_knee2, R.drawable.red_knee3, R.drawable.red_knee4, R.drawable.red_knee5, R.drawable.red_knee6, R.drawable.red_knee7, R.drawable.red_knee8};
    private int[] blueknees = new int[]{R.drawable.blue_knee1, R.drawable.blue_knee2, R.drawable.blue_knee3, R.drawable.blue_knee4, R.drawable.blue_knee5, R.drawable.blue_knee6, R.drawable.blue_knee7, R.drawable.blue_knee8};
    private HeiXiongSocketClient heiXiongSocketClient;
    private List<Drawable> drawableList = new ArrayList<>();
    private List<Drawable> redonClickdrawableList1 = new ArrayList<>();
    private List<Drawable> redonClickdrawableList2 = new ArrayList<>();
    private List<Drawable> redonClickdrawableList3 = new ArrayList<>();
    private List<Drawable> blueonClickdrawableList1 = new ArrayList<>();
    private List<Drawable> blueonClickdrawableList2 = new ArrayList<>();
    private List<Drawable> blueonClickdrawableList3 = new ArrayList<>();
    private List<Drawable> onClickdrawableList = new ArrayList<>();
    private String model;
    private String userId;
    private String loginKey;

    public HeiXiongSocketClient getHeiXiongSocketClient() {
        if (heiXiongSocketClient == null) {
            heiXiongSocketClient = new HeiXiongSocketClient();
        }
        return heiXiongSocketClient;
    }

    public void init(final Context context) {
        View.inflate(context, getLayoutId(), this);

        //登录信息
        userId = (String) SPUtils.get(context, Constants.USER_ID, "-1");
        loginKey = (String) SPUtils.get(context, Constants.USER_LOGIN_KEY, "-1");

        startButton = (ImageView) findViewById(R.id.start);
        fullscreenButton = (ImageView) findViewById(R.id.fullscreen);
        progressBar = (SeekBar) findViewById(R.id.bottom_seek_progress);
        currentTimeTextView = (TextView) findViewById(R.id.current);

        ivDanmu = (ImageView) findViewById(R.id.iv_danmu_btn);
        ivHudong = (ImageView) findViewById(R.id.iv_hudong);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        totalTimeTextView = (TextView) findViewById(R.id.total);
        bottomContainer = (ViewGroup) findViewById(R.id.layout_bottom);
        textureViewContainer = (ViewGroup) findViewById(R.id.surface_container);
        topContainer = (ViewGroup) findViewById(R.id.layout_top);
        ll_danmu = (LinearLayout) findViewById(R.id.ll_danmu);
        rl_hudong = (RelativeLayout) findViewById(R.id.rl_hudong);

        rl_error_hint = (RelativeLayout) findViewById(R.id.rl_error_hint);
        tv_error = (TextView) findViewById(R.id.tv_error);

        //中间互动的view
        mV4hudongView = (V4HudongView) findViewById(R.id.v4_letv_skin_v4_hudong_layout);
        heart_layout1 = (BubbleView) findViewById(R.id.heart_layout1);
        heart_layout2 = (BubbleView) findViewById(R.id.heart_layout2);
        //发送弹幕的输入框
        ll_edit_msg = (LinearLayout) findViewById(R.id.ll_edit_msg);
        //背景图片
        iv_backgroud = (ImageView) findViewById(R.id.iv_backgroud);

        rl_chongbo = (RelativeLayout) findViewById(R.id.rl_chongbo);
        iv_chongbo = (RelativeLayout) findViewById(R.id.iv_chongbo);

        rl_chongbo.setOnClickListener(this);
        iv_chongbo.setOnClickListener(this);
        startButton.setOnClickListener(this);
        fullscreenButton.setOnClickListener(this);
        progressBar.setOnSeekBarChangeListener(this);
        bottomContainer.setOnClickListener(this);
        textureViewContainer.setOnClickListener(this);
        ivShare.setOnClickListener(this);
        textureViewContainer.setOnTouchListener(this);
        ivHudong.setOnClickListener(this);
        ivDanmu.setOnClickListener(this);
        ll_edit_msg.setOnClickListener(this);

//        //设置背景图片 在段视频播放的时候用到
//        if (Build.VERSION.SDK_INT >= 16) {
//            if(instance.getBackImg() != null){
//                iv_backgroud.setImageBitmap(instance.getBackImgBit());
//            }
////            iv_backgroud.setBackground(instance.getBackImg());
//        }

        danmakuView = (DanmakuView) findViewById(R.id.danmakuview);
//        this.setmILetv(new ILetv() {
//            @Override
//            public void isOpenDanmu(boolean isOpen) {
//                if(danmakuView == null){
//                    return;
//                }
////                UIUtils.showToast("  www "+isOpen);
//                if(isOpen){
//                    initDanmu();
//
//                }else{
//                    danmakuView.stop();
//                }
//            }
//
//            @Override
//            public void isOpenHudong(boolean isOpen) {
//                mV4hudongView.setVisibility(GONE);
//                heart_layout1.setVisibility(GONE);
//                heart_layout2.setVisibility(GONE);
////                if (isOpen) {
////                    mV4hudongView.setVisibility(VISIBLE);
////                    heart_layout1.setVisibility(VISIBLE);
////                    heart_layout2.setVisibility(VISIBLE);
////                } else {
////                    mV4hudongView.setVisibility(GONE);
////                    heart_layout1.setVisibility(GONE);
////                    heart_layout2.setVisibility(GONE);
////                }
//            }
//        });

//        mV4hudongView.setOnMyClickListener(new V4HudongView.OnMyClicklistener() {
//
//            @Override
//            public void onClicklistenerLeftOne(View view) {
//                int quan = redquans[mRandom.nextInt(redquans.length)];
////                heart_layout1.addHeart(randomColor(),quan,quan);
//                redonClickdrawableList1.clear();
//                redonClickdrawableList1.add(getResources().getDrawable(quan));
//                heart_layout1.setDrawableList(redonClickdrawableList1);
////                heart_layout1.startAnimation(heart_layout1.getWidth(), heart_layout1.getHeight(), 1);
//
//                heart_layout1.startAnimation(UIUtils.dip2px(context,60), heart_layout1.getHeight(), 1);
//                sendHudong("[::redPlayerQuan::]");
//
//            }
//
//            @Override
//            public void onClicklistenerLeftTwo(View view) {
//                int leg = redlegs[mRandom.nextInt(redlegs.length)];
////                heart_layout1.addHeart(randomColor(),leg,leg);
//                redonClickdrawableList2.clear();
//                redonClickdrawableList2.add(getResources().getDrawable(leg));
//                heart_layout1.setDrawableList(redonClickdrawableList2);
//                heart_layout1.startAnimation(UIUtils.dip2px(context,60), heart_layout1.getHeight(), 1);
//
//                sendHudong("[::redPlayerTui::]");
//            }
//
//            @Override
//            public void onClicklistenerLeftThree(View view) {
//                int knee = redknees[mRandom.nextInt(redknees.length)];
////                heart_layout1.addHeart(randomColor(),knee,knee);
//                redonClickdrawableList3.clear();
//                redonClickdrawableList3.add(getResources().getDrawable(knee));
//                heart_layout1.setDrawableList(redonClickdrawableList3);
//                heart_layout1.startAnimation(UIUtils.dip2px(context,60), heart_layout1.getHeight(), 1);
//
//                sendHudong("[::redPlayerXi::]");
//            }
//
//            @Override
//            public void onClicklistenerRightOne(View view) {
//                int quan = bluequans[mRandom.nextInt(bluequans.length)];
////                heart_layout2.addHeart(randomColor(),quan,quan);
//                blueonClickdrawableList1.clear();
//                blueonClickdrawableList1.add(getResources().getDrawable(quan));
//                heart_layout2.setDrawableList(blueonClickdrawableList1);
//                heart_layout2.startAnimation(heart_layout2.getWidth(), heart_layout2.getHeight(), 1);
//                sendHudong("[::bluePlayerQuan::]");
//            }
//
//            @Override
//            public void onClicklistenerRightTwo(View view) {
//                int leg = bluelegs[mRandom.nextInt(bluelegs.length)];
////                heart_layout2.addHeart(randomColor(),leg,leg);
//                blueonClickdrawableList2.clear();
//                blueonClickdrawableList2.add(getResources().getDrawable(leg));
//                heart_layout2.setDrawableList(blueonClickdrawableList2);
//                heart_layout2.startAnimation(heart_layout2.getWidth(), heart_layout2.getHeight(), 1);
//                sendHudong("[::bluePlayerTui::]");
//            }
//
//            @Override
//            public void onClicklistenerRightThree(View view) {
//                int knee = blueknees[mRandom.nextInt(blueknees.length)];
////              heart_layout2.addHeart(randomColor(),knee,knee);
//                blueonClickdrawableList3.clear();
//                blueonClickdrawableList3.add(getResources().getDrawable(knee));
//                heart_layout2.setDrawableList(blueonClickdrawableList3);
//                heart_layout2.startAnimation(heart_layout2.getWidth(), heart_layout2.getHeight(), 1);
//                sendHudong("[::bluePlayerXi::]");
//            }
//
//        });

        mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mHandler = new Handler();

        NORMAL_ORIENTATION = context.getResources().getConfiguration().orientation;
    }

    //屏幕旋转
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        if (ScreenUtils.getOrientation(getContext()) == Configuration.ORIENTATION_LANDSCAPE) {
            mV4hudongView.setVisibility(GONE);
            heart_layout1.setVisibility(GONE);
            heart_layout2.setVisibility(GONE);
//            ll_edit_msg.setVisibility(VISIBLE);
//            initDanmu();
            if (context instanceof LiveDetailActivity) {
                if (instance.getActivityType() == TXMediaManager.ACTIVITY_TYPE_LIVE_PLAY) {//直播
                    ll_edit_msg.setVisibility(INVISIBLE);
                } else {
                    ll_edit_msg.setVisibility(GONE);
                }
//                doCurrentduizhen();
            }
        } else {
            mV4hudongView.setVisibility(GONE);
            heart_layout1.setVisibility(GONE);
            heart_layout2.setVisibility(GONE);
//            danmakuView.stop();
        }
    }

    /**
     * 获取当前对阵
     */
    private HashMap<String, String> hm;
    private String duizhenId;
    private SocketLoginBean socketLogin;
//    private void doCurrentduizhen() {
//
//        hm = new HashMap<String, String>();
//        hm.put("saichengId", ((LiveDetailActivity) context).getSaichengId());
//        MakerApplication.okHttpUtilsPost.url(NetUrlUtils.GET_CURRENT_DUIZHEN)
//                .params(hm).build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Log.d("aaaa", "获取当前对阵:" + response);
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(response);
//                    String status = jsonObject.optString("status");
//                    if (status != null && status.equals("ok")) {
//                        JSONObject data = jsonObject.getJSONObject("data");
//                        String vs_order = data.getString("vs_order");
//                        duizhenId = data.getString("duizhen_id");
//                        List<DuizhenEntity> duizhenList = ((LiveDetailActivity) context).getDuizhen();
//                        addPlayerImg(duizhenList, Integer.valueOf(vs_order));
//
//                        //获取互动数据
//                        socketLogin = new SocketLoginBean();
//                        socketLogin.setDuizhenId(duizhenId + "");
//                        socketLogin.setSaichengId(((LiveDetailActivity) context).getSaichengId());
//                        socketLogin.setType("saichengDuizhenHudongData");
//                        String stringjson = new Gson().toJson(socketLogin);
//                        ((LiveDetailActivity) context).fragment1.getHeiXiongSocketClient().sendMsg(stringjson + "\\r\\n");
//
//                    } else {
//                        UIUtils.showToast("服务器连接异常");
//                    }
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//
//            }
//        });
//    }

    //赋值运动员的头像
//    private void addPlayerImg(List<DuizhenEntity> duizhenList, int vs_order) {
//        if (duizhenList != null && duizhenList.size() > 0) {
//            String bluephoto = duizhenList.get(vs_order - 1).getBlue_player_photo();
//            String redphoto = duizhenList.get(vs_order - 1).getRed_player_photo();
//            mV4hudongView.setBlueImg(bluephoto);
//            mV4hudongView.setRedImg(redphoto);
//        } else {
//            mV4hudongView.setDefultImg();
//        }
//    }

//    private void sendHudong(String s) {
//        socketLogin = new SocketLoginBean();
//        socketLogin.setMsg(s);
//        socketLogin.setType("sendHudong");
//        socketLogin.setSaichengId(((LiveDetailActivity) context).getSaichengId());
//        socketLogin.setDuizhenId(duizhenId + "");
//        String stringjson = new Gson().toJson(socketLogin);
//        ((LiveDetailActivity) context).fragment1.getHeiXiongSocketClient().sendMsg(stringjson + "\\r\\n");
//    }


    //初始化弹幕
    private int isinitdanmu = 0;
//    private void initDanmu() {
//        if (context instanceof LiveDetailActivity) { //直播详情
//            //如果是直播详情的页面  只有在直播的状态下有弹幕，预告和未开始没有弹幕
//            if (!(((LiveDetailActivity) context).isdanmu)) {
//                //隐藏弹幕布局
//                ll_danmu.setVisibility(GONE);
//                return;
//            } else {
//                ll_danmu.setVisibility(VISIBLE);
//
//            }
//
//        } else {
//            //隐藏弹幕布局
//            ll_danmu.setVisibility(GONE);
//            return;
//        }
//        danmakuView.enableDanmakuDrawingCache(true);
//        danmakuView.setCallback(new DrawHandler.Callback() {
//            @Override
//            public void prepared() {
//                showDanmaku = true;
//                danmakuView.start();
//                isinitdanmu = 1;
////              generateSomeDanmaku(context);
////                doDanmu(1);
//
//                //添加弹幕
////                generateSomeDanmaku(context, msg,uid);
//            }
//
//            @Override
//            public void updateTimer(DanmakuTimer timer) {
//
//            }
//
//            @Override
//            public void danmakuShown(BaseDanmaku danmaku) {
//
//            }
//
//            @Override
//            public void drawingFinished() {
//
//            }
//        });
//        danmakuContext = DanmakuContext.create();
//        danmakuView.prepare(parser, danmakuContext);
//    }

    //点击别的区域的时候,设置输入法选项
    private boolean isShow;

    public void showEditDialog(final Context context) {
        isShow = true;
        if (dialog == null) {
            dialog = new Dialog(context, R.style.dialog);
        }
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.view_edittext, null);
        et_keywored = (EditText) view.findViewById(R.id.et_keywored);
        tv_send = (TextView) view.findViewById(R.id.tv_send);
        tv_textcount = (TextView) view.findViewById(R.id.tv_textcount);


        //设置展示范围
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().
                setAttributes(params);

        dialog.setContentView(view);
        dialog.show();

        et_keywored.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("s.toString()", s.toString());
                if (s.length() <= 40) {
                    tv_textcount.setText((40 - s.length()) + "");
                } else {
                    et_keywored.setText(s.subSequence(0, 40));
                    et_keywored.setSelection(40);
                    Toast.makeText(context, "字数已经超出限制", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        et_keywored.setOnFocusChangeListener(new OnFocusChangeListener() {
                                                 @Override
                                                 public void onFocusChange(View v, boolean hasFocus) {
                                                     if (hasFocus) {
                                                         et_keywored.post(new Runnable() {
                                                             @Override
                                                             public void run() {
                                                                 InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                 imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                                                                 //自动弹出
                                                                 try {
                                                                     Thread.sleep(40);
                                                                 } catch (InterruptedException e) {

                                                                 }
                                                             }
                                                         });
                                                     }

                                                 }
                                             }
        );

        //发送消息
        tv_send.setOnClickListener(new OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (!TextUtils.isEmpty(et_keywored.getText().toString().trim())) {
                                               Toast.makeText(context, "发送成功!", Toast.LENGTH_SHORT).show();
                                               socketLogin = new SocketLoginBean();
                                               socketLogin.setMsg(et_keywored.getText().toString().trim());
                                               socketLogin.setType("sendQunliao");
                                               String stringjson = new Gson().toJson(socketLogin);
                                               et_keywored.getText().clear();
                                               dialog.dismiss();
                                               hideKeyBoard();

                                           } else {
                                               Toast.makeText(context, "内容不能为空!", Toast.LENGTH_SHORT).show();
                                               return;
                                           }
                                       }
                                   }

        );

        //隐藏dialog的方法
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                       @Override
                                       public void onCancel(final DialogInterface dialog) {
                                           //小米手机
                                           if (model.contains("MI 3")) {
                                               et_keywored.post(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                       imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                                                       try {
                                                           Thread.sleep(100);
                                                       } catch (Exception e) {

                                                       }

                                                   }
                                               });
                                           } else {
                                               //普通手机
                                               InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                               imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                                           }
                                           isShow = false;
                                       }
                                   }
        );

        et_keywored.setFocusable(true);
    }

    //隐藏键盘和输入框
    public void hideKeyBoard() {
        isShow = false;
        if (model.contains("MI 3")) {
            //小米手机
            et_keywored.post(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }
            });
        } else {
            //普通手机
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku(final Context ctx, final String danmu, final String uid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (uid != null && uid.equals(userId) && danmu != null) {
                    addDanmaku(ctx, danmu, false, true);
                } else {
                    addDanmaku(ctx, danmu, false, false);
                }

            }
        }).start();
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    public void addDanmaku(Context ctx, String content, boolean withBorder, boolean ismysendmsg) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || danmakuView == null) {
            return;
        }
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = DensityUtil.sp2px(ctx, 20);
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }

        if (ismysendmsg) {
            danmaku.textColor = getResources().getColor(R.color.yellow_text);
        } else {
            danmaku.textColor = Color.WHITE;
        }

        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        //最多显示5行
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5);
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, false);
        danmakuContext.preventOverlapping(overlappingEnablePair);
        danmakuContext.setMaximumLines(maxLinesPair);
        danmakuView.addDanmaku(danmaku);
    }

    public ILetv getmILetv() {
        return mILetv;
    }

    public void setmILetv(ILetv mILetv) {
        this.mILetv = mILetv;
    }

    //更新播放参数的回调实现
    public void setUp(String url, int screen, Object... objects) {
        if (!TextUtils.isEmpty(this.url) && TextUtils.equals(this.url, url)) {
            return;
        }
        this.url = url;
        this.objects = objects;
        this.currentScreen = screen;
        this.headData = null;
        onStateNormal();
    }

    public void onStateNormal() {
        currentState = CURRENT_STATE_NORMAL;
        cancelProgressTimer();
//        if (isCurrentJcvd()) {//这个if是无法取代的，否则进入全屏的时候会releaseMediaPlayer
//            instance.videoDestroy();
//        }
    }


    //===============================================添加界面点击接口回调=======================================================================
    private OnVideoClickListener mOnVideoClickListener;

    public void setOnVideoClickListener(OnVideoClickListener listener) {
        mOnVideoClickListener = listener;
    }

    public boolean isCurrentJcvd() {//虽然看这个函数很不爽，但是干不掉
        return TXCloudPlayerManager.getCurrentJcvd() != null
                && TXCloudPlayerManager.getCurrentJcvd() == this;
    }

    //开始进度计时
    public void startProgressTimer() {
        cancelProgressTimer();
        UPDATE_PROGRESS_TIMER = new Timer();
        mProgressTimerTask = new ProgressTimerTask();
        UPDATE_PROGRESS_TIMER.schedule(mProgressTimerTask, 0, 300);
    }

    //结束进度计时
    public void cancelProgressTimer() {
        if (UPDATE_PROGRESS_TIMER != null) {
            UPDATE_PROGRESS_TIMER.cancel();
        }
        if (mProgressTimerTask != null) {
            mProgressTimerTask.cancel();
        }
    }

    private boolean mStartSeek = false;
    private int duration;//总时长
    private int progress;//当前播放时长
    private int videoHeight; //当前视频的高度
    private int videoWidth;//当前视频的宽度

    @Override
    public void onPlayEvent(int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {//开始播放
            if (TXCloudPlayerManager.getCurrentJcvd() != null) {
                TXCloudPlayerManager.getCurrentJcvd().onPrepared();
            }
//            stopLoadingAnimation();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {//开始播放
            if (mStartSeek) {
                return;
            }
            progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
            duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION);
            long curTS = System.currentTimeMillis();

            // 避免滑动进度条松开的瞬间可能出现滑动条瞬间跳到上一个位置
//            if (Math.abs(curTS - mTrackingTouchTS) < 500) {
//                return;
//            }
//            mTrackingTouchTS = curTS;

            if (progressBar != null) {
                progressBar.setProgress(progress);
            }
            if (currentTimeTextView != null) {
                currentTimeTextView.setText(String.format("%02d:%02d", progress / 60, progress % 60));
            }
            if (totalTimeTextView != null) {
                totalTimeTextView.setText(String.format("%02d:%02d", duration / 60, duration % 60));
            }
            if (progressBar != null) {
                progressBar.setMax(duration);
            }
            return;
        } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {//网络断连,且经多次重连抢救无效,可以放弃治疗,更多重试请自行重启播放(直播的时候三次重连失败 走这里 表示直播结束)
            if (TXCloudPlayerManager.getCurrentJcvd() != null) {
                TXCloudPlayerManager.getCurrentJcvd().onError();
            }
            //TODO
            if (TXMediaManager.instance(context).result != 0) {
                rl_error_hint.setVisibility(View.VISIBLE);
                tv_error.setText("播放地址出错了~~");
            } else {
                error_count++;
                if (error_count >= 3) {
                    if (TXMediaManager.instance(context).getActivityType() == TXMediaManager.ACTIVITY_TYPE_LIVE_PLAY) {
                        rl_error_hint.setVisibility(View.VISIBLE);
                        tv_error.setText("直播已经结束");
                    } else {
                        rl_error_hint.setVisibility(View.VISIBLE);
                        tv_error.setText("播放地址出错了~~");
                    }
                } else {
                    if (TXMediaManager.instance(context).getActivityType() == TXMediaManager.ACTIVITY_TYPE_LIVE_PLAY) {

                    } else {
                        rl_error_hint.setVisibility(View.VISIBLE);
                        tv_error.setText("播放地址出错了~~");
                    }
                }
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {//播放完成
            instance.mLivePlayer.stopPlay(false); //不清楚最后一帧
            rl_chongbo.setVisibility(View.VISIBLE);
            if (TXCloudPlayerManager.getCurrentJcvd() != null) {
                TXCloudPlayerManager.getCurrentJcvd().onAutoCompletionChongBo();
            }
//                    TXMediaManager.instance().stopplayer();
            boolean mVideoPlay = false;
            boolean mVideoPause = false;

            if (currentTimeTextView != null) {
                currentTimeTextView.setText("00:00");
            }
            if (progressBar != null) {
                progressBar.setProgress(0);
            }
//            startVideo();//循环播放
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_LOADING) {
            if (TXMediaManager.instance(context).result != 0) {
                if (TXCloudPlayerManager.getCurrentJcvd() != null) {
                    TXCloudPlayerManager.getCurrentJcvd().onError();
                }
                //TODO
                rl_error_hint.setVisibility(View.VISIBLE);
                tv_error.setText("播放地址出错了~~");
            }
        }

        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
//        appendEventLog(event, msg);
//        if (mScrollView.getVisibility() == View.VISIBLE) {
//            mLogViewEvent.setText(mLogMsg);
//            scroll2Bottom(mScrollView, mLogViewEvent);
//        }
        if (event < 0) {
//            Toast.makeText(getApplicationContext(), param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
//            stopLoadingAnimation();
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {
        if (videoHeight == 0 || videoWidth == 0) {
            videoHeight = bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT);
            videoWidth = bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH);
        }
    }

    private String uid;
    private String msg;
    //当前对阵id
    private String currentDuiZhenId;

    @Override
    public void onReceive(String strMsg) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(strMsg);
            String type = jsonObject.optString("type");
            String status = jsonObject.optString("status");

            //登录
            if (type != null && type.equals("relogin")) {
                getHeiXiongSocketClient().connect();
            } else if (type != null && type.equals("qunliao")) {  //热聊
                if (isinitdanmu == 1) { //已初始化弹幕成功
                    uid = jsonObject.optString("uid");
                    msg = jsonObject.optString("msg");
                    generateSomeDanmaku(context, msg, uid);
                }
            }
//            else if (type != null && type.equals("tongzhi_duizhen_c")) { //推送当前对阵状态
//                String state = jsonObject.optString("state");
//                if (state != null && state.equals("2")) {
//                    String vs_order = jsonObject.getString("vs_order");
//                    duizhenId = jsonObject.getString("duizhen_id");
//                    List duizhenList = ((LiveDetailActivity) context).getDuizhen();
//                    addPlayerImg(duizhenList, Integer.valueOf(vs_order));
//                    if(!currentDuiZhenId.equals(duizhenId)){
//                        currentDuiZhenId = duizhenId;
//                        //重置互动点赞数量
//                        mV4hudongView.setBlueHudongNum("0");
//                        mV4hudongView.setRedHudongNum("0");
//                    }
//                }
//                else if(state != null && state.equals("1")){
//                    //上一个对阵已结束
//                    String vs_order = jsonObject.getString("vs_order");
//                    duizhenId = jsonObject.getString("duizhen_id");
//                    List duizhenList = ((LiveDetailActivity) context).getDuizhen();
//                    addPlayerImg(duizhenList, Integer.valueOf(vs_order));
//                    if(!currentDuiZhenId.equals(duizhenId)){
//                        currentDuiZhenId = duizhenId;
//                        //重置互动点赞数量
//                        mV4hudongView.setBlueHudongNum("0");
//                        mV4hudongView.setRedHudongNum("0");
//                    }
//                }
//            }
//            else if (type != null && type.equals("saichengDuizhenHudongData")) { ////当前互动数据
//                String red_data = jsonObject.getString("red_data");
//                String blue_data = jsonObject.getString("blue_data");
//                currentDuiZhenId = jsonObject.getString("duizhenId");
//                Log.d("yyyeee",red_data+" haha   "+blue_data);
//                addPlayerHudongNum(red_data, blue_data);
//            }
//            else if (type != null && type.equals("hudong")) {
//                //互动
//                String hudongmsg = jsonObject.optString("msg");
//                String uid = jsonObject.optString("uid");
//                if (uid != null && !uid.equals(userId)) {
//                    if (hudongmsg != null && hudongmsg.equals("[::redPlayerQuan::]")) {
//                        int quan = redquans[mRandom.nextInt(redquans.length)];
//                        redonClickdrawableList1.clear();
//                        redonClickdrawableList1.add(getResources().getDrawable(quan));
//                        datas.add(new InteractionData(heart_layout1, redonClickdrawableList1,0));
//                        addredhudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::redPlayerTui::]")) {
//                        int leg = redlegs[mRandom.nextInt(redlegs.length)];
//                        redonClickdrawableList2.clear();
//                        redonClickdrawableList2.add(getResources().getDrawable(leg));
//                        datas.add(new InteractionData(heart_layout1, redonClickdrawableList2,0));
//                        addredhudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::redPlayerXi::]")) {
//                        int xi = redknees[mRandom.nextInt(redknees.length)];
//                        redonClickdrawableList3.clear();
//                        redonClickdrawableList3.add(getResources().getDrawable(xi));
//                        datas.add(new InteractionData(heart_layout1, redonClickdrawableList3,0));
//                        addredhudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::bluePlayerQuan::]")) {
//                        int quans = bluequans[mRandom.nextInt(bluequans.length)];
//                        blueonClickdrawableList1.clear();
//                        blueonClickdrawableList1.add(getResources().getDrawable(quans));
//                        datas.add(new InteractionData(heart_layout2, blueonClickdrawableList1,1));
//                        addbluehudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::bluePlayerTui::]")) {
//                        int legs = bluelegs[mRandom.nextInt(bluelegs.length)];
//                        blueonClickdrawableList2.clear();
//                        blueonClickdrawableList2.add(getResources().getDrawable(legs));
//                        datas.add(new InteractionData(heart_layout2, blueonClickdrawableList2,1));
//                        addbluehudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::bluePlayerXi::]")) {
//                        int knee = blueknees[mRandom.nextInt(blueknees.length)];
//                        blueonClickdrawableList3.clear();
//                        blueonClickdrawableList3.add(getResources().getDrawable(knee));
//                        datas.add(new InteractionData(heart_layout2, blueonClickdrawableList3,1));
//                        addbluehudongcount();
//                    }
//                }
//                if (!isFirstShowAnimation) {
//                    addanimation();
//                }
//                isFirstShowAnimation = true;
//            }
            //重新登录

//            OnClickEventUtils.isFastClick()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //赋值双方的互动数量
    private int blbucount;
    private int redcount;
    private String BluehudongCount;
    private String redhudongCount;
//    private void addPlayerHudongNum(String red, String blue) {
//        if (mV4hudongView.getBlueHudongNum() != null && !TextUtils.isEmpty(mV4hudongView.getBlueHudongNum())) {
//            BluehudongCount = "0";
//        } else {
//            BluehudongCount = "0";
//        }
//
//        if (mV4hudongView.getRedHudongNum() != null && !TextUtils.isEmpty(mV4hudongView.getRedHudongNum())) {
//            redhudongCount = "0";
//        } else {
//            redhudongCount = "0";
//        }
//        if (!BluehudongCount.equals("10W+")){ //不是10w
//            blbucount = Integer.parseInt(blue) + Integer.parseInt(BluehudongCount);
//            if (blbucount>=100000){
//                mV4hudongView.setBlueHudongNum("10W+");
//            }else{
//                mV4hudongView.setBlueHudongNum(blbucount+"");
//            }
//
//        }else{
//            mV4hudongView.setBlueHudongNum("10W+");
//        }
//
//        if (!redhudongCount.equals("10W+")){
//            redcount = Integer.parseInt(red) + Integer.parseInt(redhudongCount);
//            if (redcount>=100000){
//                mV4hudongView.setRedHudongNum("10W+");
//            }else {
//                mV4hudongView.setRedHudongNum(redcount + "");
//            }
//        }else{
//            mV4hudongView.setRedHudongNum("10W+");
//        }
//    }

    /**
     * 飘动画时添加红方互动数
     */
//    private void addredhudongcount() {
//        if (mV4hudongView.getRedHudongNum() != null && !TextUtils.isEmpty(mV4hudongView.getRedHudongNum())) {
//            redhudongCount = mV4hudongView.getRedHudongNum();
//        } else {
//            redhudongCount = "0";
//        }
//        redcount = 1 + Integer.parseInt(redhudongCount);
//        mV4hudongView.setRedHudongNum(redcount + "");
//    }
    /**
     * 飘动画时添加蓝方互动数
     */
//    private void addbluehudongcount() {
//        if (mV4hudongView.getBlueHudongNum() != null && !TextUtils.isEmpty(mV4hudongView.getBlueHudongNum())) {
//            BluehudongCount = mV4hudongView.getBlueHudongNum();
//        } else {
//            BluehudongCount = "0";
//        }
//        blbucount = 1 + Integer.parseInt(BluehudongCount);
//        mV4hudongView.setBlueHudongNum(blbucount + "");
//    }

//    private List<InteractionData> datas = new MyList();
    private boolean isFirstShowAnimation = false;
    private TimerTask animationTimerTask;
    int i = 0;
//    private void addanimation() {
//        animationTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                if (i < datas.size()) {
//                    datas.get(i).getHeartLayout().post(new Runnable() {
//                        @Override
//                        public void run() {
////                        mHeartLayout.addHeart(randomColor());
//                            if (i >= datas.size()) {
//                                i = 0;
//                                datas.clear();
//                                cancel();
//
//                                return;
//                            }
//
//                            if (datas.get(i).getType()==0){
//                                datas.get(i).getHeartLayout().setDrawableList(datas.get(i).getDrawable());
//                                datas.get(i).getHeartLayout().startAnimation(UIUtils.dip2px(context,60), datas.get(i).getHeartLayout().getHeight(), 1);
//                            }else{
//                                datas.get(i).getHeartLayout().setDrawableList(datas.get(i).getDrawable());
//                                datas.get(i).getHeartLayout().startAnimation(datas.get(i).getHeartLayout().getWidth(), datas.get(i).getHeartLayout().getHeight(), 1);
//                            }
//
//                            i++;
//
//                        }
//                    });
//                } else {
//
//                    i=0;
//                    datas.clear();
//                    animationTimerTask.cancel();
//                    animationTimerTask = null;
//                    isFirstShowAnimation = false;
//
//                }
//            }
//        };
//        animationTimer.scheduleAtFixedRate(animationTimerTask, 300, 200);
//
//    }

    //进度时间的异步延时加载
    public class ProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            if (currentState == CURRENT_STATE_PLAYING || currentState == CURRENT_STATE_PAUSE || currentState == CURRENT_STATE_PLAYING_BUFFERING_START) {
//                Log.v(TAG, "onProgressUpdate " + position + "/" + duration + " [" + this.hashCode() + "] ");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int position = getCurrentPositionWhenPlaying();
                        int duration = getDuration();
                        int progress = position * 100 / (duration == 0 ? 1 : duration);
                        setProgressAndText(progress, position, duration);
                    }
                });
            }
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.start) {
            //接口回调
            if (mOnVideoClickListener != null && currentState == -1) {
                mOnVideoClickListener.onVideoClickToStart();
            }
            if (currentState == CURRENT_STATE_NORMAL || currentState == CURRENT_STATE_ERROR) {
                if (TextUtils.isEmpty(url)) {
                    Toast.makeText(getContext(), "NO URL", Toast.LENGTH_SHORT).show();
                    return;
                }
                //不是本地的目录
                if (!url.startsWith("file") && !TXUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                    //不是wifi网络
                    showWifiDialog(TXUserActionImp.ON_CLICK_START_ICON);
                    return;
                }
                startVideo();
                onEvent(currentState != CURRENT_STATE_ERROR ? TXUserAction.ON_CLICK_START_ICON : TXUserAction.ON_CLICK_START_ERROR);
            } else if (currentState == CURRENT_STATE_PLAYING) { //正在播放 --去暂停
                onEvent(TXUserAction.ON_CLICK_PAUSE);
                instance.pauseplayer();//去暂停
                onStatePause();
            } else if (currentState == CURRENT_STATE_PAUSE) {//正在暂停-- 去播放
                onEvent(TXUserAction.ON_CLICK_RESUME);
                instance.resumeplayer();//去暂停;//去去播放
                onStatePlaying();
            } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {//结束了
                onEvent(TXUserAction.ON_CLICK_START_AUTO_COMPLETE);
                startVideo();
            }
        } else if (i == R.id.fullscreen) { //全屏按钮
            if (currentState == CURRENT_STATE_AUTO_COMPLETE) return; //结束了就直接return
            if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {//已经全屏了
                //quit fullscreen
                backPress();
            } else {//去全屏
                onEvent(TXUserAction.ON_ENTER_FULLSCREEN);
                startWindowFullscreen();
            }
        } else if (i == R.id.surface_container && currentState == CURRENT_STATE_ERROR) {//点击界面
            startVideo();
        } else if (i == R.id.iv_danmu_btn) {
            if (isOpenDanmu) {
                isOpenDanmu = false;
                //关闭弹幕
                ivDanmu.setBackgroundResource(R.drawable.danmu_off);
                mILetv.isOpenDanmu(false);
            } else {
                isOpenDanmu = true;
                //打开弹幕
                ivDanmu.setBackgroundResource(R.drawable.danmu_on);
                mILetv.isOpenDanmu(true);
            }
        } else if (i == R.id.iv_hudong) {
            if (isOpenHudong) {
                isOpenHudong = false;
                //关闭互动
                ivHudong.setBackgroundResource(R.drawable.hudong_off);
                mILetv.isOpenHudong(false);
            } else {
                isOpenHudong = true;
                //打开互动
                ivHudong.setBackgroundResource(R.drawable.hudong_on);
                mILetv.isOpenHudong(true);
            }
        } else if (i == R.id.ll_edit_msg) { //输入框
            showEditDialog(context);

        } else if (i == R.id.iv_share) {
            //TODO 分享

        } else if (i == R.id.iv_chongbo) {
            //TODO  重播
            rl_chongbo.setVisibility(View.GONE);
            startVideo();
        }else if (i == R.id.rl_chongbo) {

        }
    }


    /**
     * 全屏方法
     */
    //动态设置ID 不建议这样做
    public static final int FULLSCREEN_ID = 33797;
    public static final int TINY_ID = 33798;
    public static final int THRESHOLD = 80;

    public void startWindowFullscreen() {
        hideSupportActionBar(getContext());
        if (checkVideoAngle()) {

        } else {
            ((Activity) TXMediaManager.ctx).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

//        TXMediaManager.ctx.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ViewGroup vp = (ViewGroup) (TXUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View old = vp.findViewById(FULLSCREEN_ID); // 就是播放的控制器 就是播放器
        if (old != null) {
            vp.removeView(old);
        }
        textureViewContainer.removeView(instance.textureView);//移出这个记录播放器
        try {
            Constructor<TXCloudViewViewA> constructor = (Constructor<TXCloudViewViewA>) TXCloudViewViewA.this.getClass().getConstructor(Context.class);
            TXCloudViewViewA jcVideoPlayer = constructor.newInstance(getContext());
            jcVideoPlayer.setId(FULLSCREEN_ID);
            LayoutParams lp = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            vp.addView(jcVideoPlayer, lp);
            jcVideoPlayer.setUp(url, TXCloudViewViewImp.SCREEN_WINDOW_FULLSCREEN, objects);
            jcVideoPlayer.setState(currentState);
            jcVideoPlayer.addTextureView();
            TXCloudPlayerManager.setSecondFloor(jcVideoPlayer);
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPrepared() {
        if (currentState != CURRENT_STATE_PREPARING && currentState != CURRENT_STATE_PLAYING_BUFFERING_START)
            return;
        if (seekToInAdvance != 0) {
            instance.mLivePlayer.seek(seekToInAdvance);//自动滑动到
            seekToInAdvance = 0;
        } else {
            int position = TXUtils.getSavedProgress(getContext(), url);
            if (position != 0) {
                instance.mLivePlayer.seek(position);//自动滑动到
            }
        }
        startProgressTimer();
        onStatePlaying();
    }

    public static int BACKUP_PLAYING_BUFFERING_STATE = -1;

    public void onInfo(int what, int extra) {
        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
            if (currentState == CURRENT_STATE_PLAYING_BUFFERING_START) return;
            BACKUP_PLAYING_BUFFERING_STATE = currentState;
            onStatePlaybackBufferingStart();
        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
            if (BACKUP_PLAYING_BUFFERING_STATE != -1) {
                if (currentState == CURRENT_STATE_PLAYING_BUFFERING_START) {
                    setState(BACKUP_PLAYING_BUFFERING_STATE);
                }
                BACKUP_PLAYING_BUFFERING_STATE = -1;
            }
        }
    }

    public void onError() {
        onStateError();
        if (isCurrentJcvd()) {
            instance.releaseMediaPlayer();
        }
    }

    public int widthRatio = 0;
    public int heightRatio = 0;

    //测量
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN || currentScreen == SCREEN_WINDOW_TINY) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (widthRatio != 0 && heightRatio != 0) {
            int specWidth = MeasureSpec.getSize(widthMeasureSpec);
            int specHeight = (int) ((specWidth * (float) heightRatio) / widthRatio);
            setMeasuredDimension(specWidth, specHeight);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(specWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(specHeight, MeasureSpec.EXACTLY);
            getChildAt(0).measure(childWidthMeasureSpec, childHeightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void onAutoCompletion() {
        //加上这句，避免循环播放video的时候，内存不断飙升。
        Runtime.getRuntime().gc();
        onEvent(TXUserAction.ON_AUTO_COMPLETE);
        dismissVolumeDialog();
        dismissProgressDialog();
        dismissBrightnessDialog();
        cancelProgressTimer();
        onStateAutoComplete();

        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            backPress();
        }
        TXUtils.saveProgress(getContext(), url, 0);
    }

    public void onAutoCompletionChongBo() {
        //加上这句，避免循环播放video的时候，内存不断飙升。
        Runtime.getRuntime().gc();
        dismissVolumeDialog();
        dismissProgressDialog();
        dismissBrightnessDialog();
        cancelProgressTimer();
        onStateAutoCompleteChongBo();

        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            backPress();
        }
        TXUtils.saveProgress(getContext(), url, 0);
    }

    public static void releaseAllVideos() {
        TXCloudPlayerManager.completeAll();

    }

    public boolean checkVideoAngle() {
        if (videoHeight == 0 || videoWidth == 0) {
            return false;
        }
        if (videoHeight > videoWidth) {
            return true;
        } else {
            return false;
        }
    }

    public void onCompletion() {
        //save position
        if (currentState == CURRENT_STATE_PLAYING || currentState == CURRENT_STATE_PAUSE) {
            int position = getCurrentPositionWhenPlaying();
//            int duration = getDuration();
            TXUtils.saveProgress(getContext(), url, position);
        }
        cancelProgressTimer();
        onStateNormal();
        // 清理缓存变量
        textureViewContainer.removeView(instance.textureView);
        instance.currentVideoWidth = 0;
        instance.currentVideoHeight = 0;

        AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        ((Activity) TXMediaManager.ctx).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        clearFullscreenLayout();
        ((Activity) TXMediaManager.ctx).setRequestedOrientation(NORMAL_ORIENTATION);

        instance.textureView = null;
        TXMediaManager.savedSurfaceTexture = null;
    }

    protected boolean mTouchingProgressBar;

    public void clearFullscreenLayout() {
        ViewGroup vp = (ViewGroup) (TXUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View oldF = vp.findViewById(FULLSCREEN_ID);
        View oldT = vp.findViewById(TINY_ID);
        if (oldF != null) {
            vp.removeView(oldF);
        }
        if (oldT != null) {
            vp.removeView(oldT);
        }
        showSupportActionBar(getContext());
    }

    public void setProgressAndText(int progress, int position, int duration) {
        if (!mTouchingProgressBar) {
            if (progress != 0) progressBar.setProgress(this.progress);
        }
//        currentTimeTextView.setText(String.format("%02d:%02d", progress / 60, this.progress % 60));
//        totalTimeTextView.setText(String.format("%02d:%02d", progress / 60, this.duration % 60));
    }

    public void setBufferProgress(int bufferProgress) {
        if (bufferProgress != 0) progressBar.setSecondaryProgress(bufferProgress);
    }


    public void onVideoSizeChanged() {
        if (instance.textureView != null) {
//            TXMediaManager.textureView.setVideoSize(TXMediaManager.instance().getVideoSize());
        }
    }

    public int getCurrentPositionWhenPlaying() {
        int position = 0;
        if (instance.mLivePlayer == null)
            return position;//这行代码不应该在这，如果代码和逻辑万无一失的话，心头之恨呐
        if (currentState == CURRENT_STATE_PLAYING ||
                currentState == CURRENT_STATE_PAUSE ||
                currentState == CURRENT_STATE_PLAYING_BUFFERING_START) {
            try {
                position = progress;
//                position = TXMediaManager.instance().mediaPlayer.getCurrentPosition();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return position;
            }
        }
        return position;
    }

    public static final int FULL_SCREEN_NORMAL_DELAY = 300;
    public static long CLICK_QUIT_FULLSCREEN_TIME = 0;

    //退出全屏和小窗的方法
    public void playOnThisJcvd() {
        //1.清空全屏和小窗的jcvd
        currentState = TXCloudPlayerManager.getSecondFloor().currentState;
        clearFloatScreen();
        //2.在本jcvd上播放
        setState(currentState);
        addTextureView();
    }

    public void clearFloatScreen() {
        ((Activity) TXMediaManager.ctx).setRequestedOrientation(NORMAL_ORIENTATION);
        showSupportActionBar(getContext());
        TXCloudViewViewA currJcvd = TXCloudPlayerManager.getCurrentJcvd();
        if (currJcvd != null) {
            currJcvd.textureViewContainer.removeView(instance.textureView);//TODO 移出记录 View
            ViewGroup vp = (ViewGroup) (TXUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                    .findViewById(Window.ID_ANDROID_CONTENT);
            vp.removeView(currJcvd);
        }
        TXCloudPlayerManager.setSecondFloor(null);
    }

    //显示titleBar
    public static void showSupportActionBar(Context context) {
        if (ACTION_BAR_EXIST) {
            if (TXUtils.getAppCompActivity(context) != null) {
                ActionBar ab = TXUtils.getAppCompActivity(context).getSupportActionBar();
                if (ab != null) {
//                    ab.setShowHideAnimationEnabled(false);
                    ab.show();
                }
            }
        }
        if (TOOL_BAR_EXIST) {
            if (TXMediaManager.ctx != null && TXMediaManager.ctx instanceof Activity) {
                ((Activity) TXMediaManager.ctx).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
    }

    //隐藏titleBar
    public static void hideSupportActionBar(Context context) {
        if (ACTION_BAR_EXIST) {
            if (TXUtils.getAppCompActivity(context) != null) {
                ActionBar ab = TXUtils.getAppCompActivity(context).getSupportActionBar();
                if (ab != null) {
//                    ab.setShowHideAnimationEnabled(false);
                    ab.hide();
                }
            }
            if (TOOL_BAR_EXIST) {
                if (TXMediaManager.ctx != null && TXMediaManager.ctx instanceof Activity) {
                    ((Activity) TXMediaManager.ctx).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
            }
        }
    }

    public void setState(int state) {
        switch (state) {
            case CURRENT_STATE_NORMAL:
                onStateNormal();
                break;
            case CURRENT_STATE_PREPARING:
                onStatePreparing();
                break;
            case CURRENT_STATE_PLAYING:
                onStatePlaying();
                break;
            case CURRENT_STATE_PAUSE:
                onStatePause();
                break;
            case CURRENT_STATE_PLAYING_BUFFERING_START:
                onStatePlaybackBufferingStart();
                break;
            case CURRENT_STATE_ERROR:
                onStateError();
                break;
            case CURRENT_STATE_AUTO_COMPLETE:
                onStateAutoComplete();
                break;
        }
    }

    public void onStatePlaybackBufferingStart() {
        currentState = CURRENT_STATE_PLAYING_BUFFERING_START;
        startProgressTimer();
    }

    public void onStateError() {
        currentState = CURRENT_STATE_ERROR;
        cancelProgressTimer();
    }

    public void onStateAutoComplete() {
        currentState = CURRENT_STATE_AUTO_COMPLETE;
        cancelProgressTimer();
        progressBar.setProgress(100);
        currentTimeTextView.setText(totalTimeTextView.getText());
    }
    //最后一帧调用 测试 重播按钮的现实
    public void onStateAutoCompleteChongBo() {
        currentState = CURRENT_STATE_AUTO_COMPLETE;
    }

    //转回正常状态
    public static boolean backPress() {
        if ((System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME) < FULL_SCREEN_NORMAL_DELAY)
            return false;
        if (TXCloudPlayerManager.getSecondFloor() != null) {
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            TXCloudViewViewA jcVideoPlayer = TXCloudPlayerManager.getSecondFloor();
            jcVideoPlayer.onEvent(jcVideoPlayer.currentScreen == TXCloudViewViewImp.SCREEN_WINDOW_FULLSCREEN ?
                    TXUserAction.ON_QUIT_FULLSCREEN :
                    TXUserAction.ON_QUIT_TINYSCREEN);
            TXCloudPlayerManager.getFirstFloor().playOnThisJcvd();
            return true;
        } else if (TXCloudPlayerManager.getFirstFloor() != null &&
                (TXCloudPlayerManager.getFirstFloor().currentScreen == SCREEN_WINDOW_FULLSCREEN ||
                        TXCloudPlayerManager.getFirstFloor().currentScreen == SCREEN_WINDOW_TINY)) {//以前我总想把这两个判断写到一起，这分明是两个独立是逻辑
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            //直接退出全屏和小窗
            TXCloudPlayerManager.getCurrentJcvd().currentState = CURRENT_STATE_NORMAL;
            TXCloudPlayerManager.getFirstFloor().clearFloatScreen();
            TXCloudPlayerManager.setFirstFloor(null);
            return true;
        }
        return false;
    }


    public void startVideo() {
        //释放资源
        TXCloudPlayerManager.completeAll();
        rl_chongbo.setVisibility(View.GONE);

        initTextureView();
        addTextureView();
        AudioManager mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        ((Activity) TXMediaManager.ctx).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        CURRENT_PLAYING_URL = url;
        TXMediaManager.CURRENT_PLING_LOOP = loop;
        TXMediaManager.MAP_HEADER_DATA = headData;

        onStatePreparing();
        TXCloudPlayerManager.setFirstFloor(this);
        instance.prepare();
    }

    //准备工作
    public void onStatePreparing() {
        currentState = CURRENT_STATE_PREPARING;
        resetProgressAndTime();
    }

    //暂停播放
    public void onStatePause() {
        currentState = CURRENT_STATE_PAUSE;
        startProgressTimer();
    }

    //开始播放
    public void onStatePlaying() {
        currentState = CURRENT_STATE_PLAYING;
        startProgressTimer();
    }

    public void resetProgressAndTime() {
        progressBar.setProgress(0);
        progressBar.setSecondaryProgress(0);
        currentTimeTextView.setText(TXUtils.stringForTime(0));
        totalTimeTextView.setText(TXUtils.stringForTime(0));
    }

    public static AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    try {
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
            }
        }
    };

    public void initTextureView() {
        removeTextureView();
        instance.textureView = new TXCloudVideoView(getContext());
        instance.textureView.setBackgroundResource(R.color.black);
    }

    public void addTextureView() {
        LayoutParams layoutParams =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER);
        textureViewContainer.addView(instance.textureView, layoutParams);
        if (instance.mLivePlayer != null) {
            instance.mLivePlayer.setPlayListener(this);
        }
    }

    public static long lastAutoFullscreenTime = 0;

    //重力感应的时候调用的函数，
    public void autoFullscreen(float x) {
        if (isCurrentJcvd()
                && currentState == CURRENT_STATE_PLAYING
                && currentScreen != SCREEN_WINDOW_FULLSCREEN
                && currentScreen != SCREEN_WINDOW_TINY) {
            if (x > 0) {
                AppManager.getAppManager().currentActivity().setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                AppManager.getAppManager().currentActivity().setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
            onEvent(TXUserAction.ON_ENTER_FULLSCREEN);
            startWindowFullscreen();
        }
    }

    public void autoQuitFullscreen() {
        if ((System.currentTimeMillis() - lastAutoFullscreenTime) > 2000
                && isCurrentJcvd()
                && currentState == CURRENT_STATE_PLAYING
                && currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            lastAutoFullscreenTime = System.currentTimeMillis();
            backPress();
        }
    }

    public static class JCAutoFullscreenListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {//可以得到传感器实时测量出来的变化值
            final float x = event.values[SensorManager.DATA_X];
            float y = event.values[SensorManager.DATA_Y];
            float z = event.values[SensorManager.DATA_Z];
            //过滤掉用力过猛会有一个反向的大数值
            if (((x > -15 && x < -10) || (x < 15 && x > 10)) && Math.abs(y) < 1.5) {
                if ((System.currentTimeMillis() - lastAutoFullscreenTime) > 2000) {
                    if (TXCloudPlayerManager.getCurrentJcvd() != null) {
                        TXCloudPlayerManager.getCurrentJcvd().autoFullscreen(x);
                    }
                    lastAutoFullscreenTime = System.currentTimeMillis();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    public void removeTextureView() {
        TXMediaManager.savedSurfaceTexture = null;
        if (instance.textureView != null && instance.textureView.getParent() != null) {
            ((ViewGroup) instance.textureView.getParent()).removeView(instance.textureView);
        }
    }


    //用户操作
    public void onEvent(int type) {
        if (TX_USER_EVENT != null && isCurrentJcvd()) {
            TX_USER_EVENT.onEvent(type, url, currentScreen, objects);
        }
    }

    public void showWifiDialog(int event) {
    }

    public void dismissBrightnessDialog() {

    }

    public void dismissVolumeDialog() {

    }

    public void dismissProgressDialog() {

    }

    public void showProgressDialog(float deltaX,
                                   String seekTime, int seekTimePosition,
                                   String totalTime, int totalTimeDuration) {
    }


    public void showVolumeDialog(float deltaY, int volumePercent) {

    }


    public void showBrightnessDialog(int brightnessPercent) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        cancelProgressTimer();
        ViewParent vpdown = getParent();
        while (vpdown != null) {
            vpdown.requestDisallowInterceptTouchEvent(true);
            vpdown = vpdown.getParent();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        onEvent(TXUserAction.ON_SEEK_POSITION);
        startProgressTimer();
        ViewParent vpup = getParent();
        while (vpup != null) {
            vpup.requestDisallowInterceptTouchEvent(false);
            vpup = vpup.getParent();
        }
        if (currentState != CURRENT_STATE_PLAYING &&
                currentState != CURRENT_STATE_PAUSE) return;
        int time = seekBar.getProgress() * getDuration() / 100;
        instance.mLivePlayer.seek(seekBar.getProgress());
    }

    public int getDuration() {
        int duration = 0;
        if (instance.mLivePlayer == null || instance.mPlayerView1 == null) return duration;
        try {
            duration = this.duration;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return duration;
        }
        return duration;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int id = view.getId();
        if (id == R.id.surface_container) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTouchingProgressBar = true;

                    mDownX = x;
                    mDownY = y;
                    mChangeVolume = false;
                    mChangePosition = false;
                    mChangeBrightness = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float deltaX = x - mDownX;
                    float deltaY = y - mDownY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);
                    if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                        if (!mChangePosition && !mChangeVolume && !mChangeBrightness) {
                            if (absDeltaX > THRESHOLD || absDeltaY > THRESHOLD) {
                                cancelProgressTimer();
                                if (absDeltaX >= THRESHOLD) {
                                    // 全屏模式下的CURRENT_STATE_ERROR状态下,不响应进度拖动事件.
                                    if (currentState != CURRENT_STATE_ERROR) {
                                        mChangePosition = true;
                                        mGestureDownPosition = getCurrentPositionWhenPlaying();
                                    }
                                } else {
                                    //如果y轴滑动距离超过设置的处理范围，那么进行滑动事件处理
                                    if (mDownX < mScreenWidth * 0.5f) {//左侧改变亮度
                                        mChangeBrightness = true;
                                        WindowManager.LayoutParams lp = AppManager.getAppManager().currentActivity().getWindow().getAttributes();
                                        if (lp.screenBrightness < 0) {
                                            try {
                                                mGestureDownBrightness = Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                                            } catch (Settings.SettingNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            mGestureDownBrightness = lp.screenBrightness * 255;
                                        }
                                    } else {//右侧改变声音
                                        mChangeVolume = true;
                                        mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                    }
                                }
                            }
                        }
                    }
                    if (instance.getActivityType() == TXMediaManager.ACTIVITY_TYPE_VOD_PLAY) {
                        if (mChangePosition) {
                            int totalTimeDuration = getDuration();
                            mSeekTimePosition = (int) (mGestureDownPosition + deltaX * totalTimeDuration / mScreenWidth);
                            if (mSeekTimePosition > duration)
                                mSeekTimePosition = duration;
                            else if (mSeekTimePosition < 0) {
                                mSeekTimePosition = 0;
                            }
                            String seekTime = String.format("%02d:%02d", mSeekTimePosition / 60, mSeekTimePosition % 60);
                            String totalTime = String.format("%02d:%02d", duration / 60, duration % 60);
                            showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
                        }
                    }
                    if (mChangeVolume) {
                        deltaY = -deltaY;
                        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        int deltaV = (int) (max * deltaY * 3 / mScreenHeight);
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);
                        //dialog中显示百分比
                        int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / mScreenHeight);
                        showVolumeDialog(-deltaY, volumePercent);
                    }

                    if (mChangeBrightness) {
                        deltaY = -deltaY;
                        int deltaV = (int) (255 * deltaY * 3 / mScreenHeight);
                        WindowManager.LayoutParams params = AppManager.getAppManager().currentActivity().getWindow().getAttributes();
                        if (((mGestureDownBrightness + deltaV) / 255) >= 1) {//这和声音有区别，必须自己过滤一下负值
                            params.screenBrightness = 1;
                        } else if (((mGestureDownBrightness + deltaV) / 255) <= 0) {
                            params.screenBrightness = 0.01f;
                        } else {
                            params.screenBrightness = (mGestureDownBrightness + deltaV) / 255;
                        }
                        AppManager.getAppManager().currentActivity().getWindow().setAttributes(params);
                        //dialog中显示百分比
                        int brightnessPercent = (int) (mGestureDownBrightness * 100 / 255 + deltaY * 3 * 100 / mScreenHeight);
                        showBrightnessDialog(brightnessPercent);
//                        mDownY = y;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mTouchingProgressBar = false;
                    dismissProgressDialog();
                    dismissVolumeDialog();
                    dismissBrightnessDialog();
                    if (mChangePosition) {
                        onEvent(TXUserAction.ON_TOUCH_SCREEN_SEEK_POSITION);
                        instance.mLivePlayer.seek(mSeekTimePosition);
                        int duration = getDuration();
                        int progress = mSeekTimePosition * 100 / (duration == 0 ? 1 : duration);
                        progressBar.setProgress(progress);
                    }
                    if (mChangeVolume) {
                        onEvent(TXUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME);
                    }
                    startProgressTimer();
                    break;
            }
        }
        return false;
    }
}
