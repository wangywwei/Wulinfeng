package com.lecloud.skin.ui.impl;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.common.utils.DensityUtil;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.DuizhenEntity;
import com.hxwl.wulinfeng.bean.InteractionData;
import com.hxwl.wulinfeng.socket.DanMuDataListener;
import com.hxwl.wulinfeng.socket.HeiXiongSocketClient;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.lecloud.skin.ui.ILetv;
import com.lecloud.skin.ui.ILetvSwitchMachineListener;
import com.lecloud.skin.ui.base.IBaseLiveSeekBar;
import com.lecloud.skin.ui.utils.ReUtils;

import com.lecloud.skin.ui.utils.ScreenUtils;
import com.lecloud.skin.ui.view.V4HudongView;
import com.lecloud.skin.ui.view.V4LargeLiveMediaControllerNew;
import com.lecloud.skin.ui.view.V4SmallLiveMediaControllerNew;
import com.lecloud.skin.ui.view.V4TopTitleView;
import com.yasic.bubbleview.BubbleView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;



public class LetvLiveUICon extends BaseLetvLiveUICon implements DanMuDataListener {

    private LinearLayout ll_danmu;
    private LinearLayout ll_edit_msg;
    private boolean isShow;
    private Dialog dialog = null;
    private EditText et_keywored;
    private TextView tv_send;
    private String model;
    private HashMap<String, String> hm;
    private String userId;
    private String loginKey;
    private int connectTimes = 0;
    //弹幕lastid
    private String danmuLastid = "";
    //互动lastid
    private String hudongLastid = "";
    //红方出拳次数
    private int redPlayerQuanTimes = 0;
    //蓝方出拳次数
    private int bluePlayerQuanTimes = 0;
    //红方出腿次数
    private int redPlayerTuiTimes = 0;
    //蓝方出腿次数
    private int bluePlayerTuiTimes = 0;
    //红方出膝次数
    private int redPlayerXiTimes = 0;
    //蓝方出膝次数
    private int bluePlayerXiTimes = 0;
//    private V4HudongView mV4hudongView;
    private String BluehudongCount;
    private String redhudongCount;
//    private BubbleView heart_layout1;
//    private BubbleView heart_layout2;
    private List<InteractionData> huDongDatas = new ArrayList<>();
    private TimerTask timerTask;
    private boolean ismysendmsg = false; //false不是我自己发送的，true是我自己发送的
    private String uid;
    private String msg;
    private Exception e;
    private int isinitdanmu = 0;
    private String duizhenId;
    private int blbucount;
    private int redcount;
    private TextView tv_textcount;

    public LetvLiveUICon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LetvLiveUICon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetvLiveUICon(Context context) {
        super(context);
    }

    protected boolean showDanmaku;
    //弹幕View
    protected DanmakuView danmakuView;
    protected DanmakuContext danmakuContext;
    protected Context mContext;
    private Random mRandom = new Random();
    private Timer mTimer = new Timer();
    private Timer animationTimer = new Timer();
//    private int[] redquans = new int[]{R.drawable.red_fist1, R.drawable.red_fist2, R.drawable.red_fist3, R.drawable.red_fist4, R.drawable.red_fist5,R.drawable.red_fist6,R.drawable.red_fist7,R.drawable.red_fist8,R.drawable.red_fist9,R.drawable.red_fist10};
//    private int[] bluequans = new int[]{R.drawable.blue_fist1, R.drawable.blue_fist2, R.drawable.blue_fist3, R.drawable.blue_fist4, R.drawable.blue_fist5,R.drawable.blue_fist6,R.drawable.blue_fist7,R.drawable.blue_fist8,R.drawable.blue_fist9,R.drawable.blue_fist10};
//    private int[] redlegs = new int[]{R.drawable.red_leg1, R.drawable.red_leg2, R.drawable.red_leg3, R.drawable.red_leg6, R.drawable.red_leg7, R.drawable.red_leg8, R.drawable.red_leg9, R.drawable.red_leg10};
//    private int[] bluelegs = new int[]{R.drawable.blue_leg1, R.drawable.blue_leg2, R.drawable.blue_leg3, R.drawable.blue_leg6, R.drawable.blue_leg7, R.drawable.blue_leg8, R.drawable.blue_leg9, R.drawable.blue_leg10};
//    private int[] redknees = new int[]{R.drawable.red_knee1, R.drawable.red_knee2, R.drawable.red_knee3,R.drawable.red_knee4,R.drawable.red_knee5,R.drawable.red_knee6,R.drawable.red_knee7,R.drawable.red_knee8};
//    private int[] blueknees = new int[]{R.drawable.blue_knee1, R.drawable.blue_knee2, R.drawable.blue_knee3,R.drawable.blue_knee4,R.drawable.blue_knee5,R.drawable.blue_knee6,R.drawable.blue_knee7,R.drawable.blue_knee8};
    private HeiXiongSocketClient heiXiongSocketClient;
    private List<Drawable> drawableList = new ArrayList<>();
    private List<Drawable> redonClickdrawableList1 = new ArrayList<>();
    private List<Drawable> redonClickdrawableList2 = new ArrayList<>();
    private List<Drawable> redonClickdrawableList3 = new ArrayList<>();
    private List<Drawable> blueonClickdrawableList1 = new ArrayList<>();
    private List<Drawable> blueonClickdrawableList2 = new ArrayList<>();
    private List<Drawable> blueonClickdrawableList3 = new ArrayList<>();
    private List<Drawable> onClickdrawableList = new ArrayList<>();

    ILetvSwitchMachineListener machineListener;

    protected BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    public HeiXiongSocketClient getHeiXiongSocketClient() {
        if (heiXiongSocketClient == null) {
            heiXiongSocketClient = new HeiXiongSocketClient();
        }
        return heiXiongSocketClient;
    }

    @Override
    public void setMachineListener(ILetvSwitchMachineListener machineListener) {
        this.machineListener = machineListener;
    }

    @Override
    protected void init(Context context) {
        super.init(context);
//		UI 分层
        mContext = context;
//        ((LiveDetailActivity) mContext).fragment1.setDanMuDataListener(this);
        model = android.os.Build.MODEL;
        //登录信息
        userId = (String) SPUtils.get(mContext, Constants.USER_ID, "-1");
        loginKey = (String) SPUtils.get(mContext, Constants.USER_LOGIN_KEY, "-1");

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rlSkin = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.letv_skin_v4_skin_live, null);
        addView(rlSkin, params);
        videoLock = (ImageView) findViewById(ReUtils.getId(context, "iv_video_lock"));

        videoLock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lockFlag = !lockFlag;
                if (machineListener != null) {
                    machineListener.showSwitchMachineBtn(lockFlag);
                }
                if (lockFlag) {
                    hide();
                    videoLock.setImageResource(R.drawable.screen_lock_drawable);
                } else {
                    show();
                    videoLock.setImageResource(R.drawable.screen_unlock_drawable);
                }
            }
        });

        danmakuView = (DanmakuView) findViewById(R.id.danmakuview);
        ll_danmu = (LinearLayout) findViewById(R.id.ll_danmu);
        //视频下部view
        mLargeMediaController = (V4LargeLiveMediaControllerNew) findViewById(R.id.v4_large_media_controller);
        //视频上层标题view
        mV4TopTitleView = (V4TopTitleView) findViewById(R.id.v4_letv_skin_v4_top_layout);
        //中间互动的view
//        mV4hudongView = (V4HudongView) findViewById(R.id.v4_letv_skin_v4_hudong_layout);
//        heart_layout1 = (BubbleView) findViewById(heart_layout1);
//        heart_layout2 = (BubbleView) findViewById(heart_layout2);
        //发送弹幕的输入框
        ll_edit_msg = (LinearLayout) findViewById(R.id.ll_edit_msg);
        mSmallMediaController = (V4SmallLiveMediaControllerNew) findViewById(R.id.v4_small_media_controller);
        mV4TopTitleView.setmILetv(new ILetv() {
            @Override
            public void isOpenDanmu(boolean isOpen) {
                if (danmakuView == null) {
                    return;
                }

                if (isOpen) {
//                    initDanmu();

                } else {
                    danmakuView.stop();
                }
            }

            @Override
            public void isOpenHudong(boolean isOpen) {
//                if (isOpen) {
//                    mV4hudongView.setVisibility(VISIBLE);
//                    heart_layout1.setVisibility(VISIBLE);
//                    heart_layout2.setVisibility(VISIBLE);
//                } else {
//                    mV4hudongView.setVisibility(GONE);
//                    heart_layout1.setVisibility(GONE);
//                    heart_layout2.setVisibility(GONE);
//                }
            }
        });


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
//                heart_layout1.startAnimation(UIUtils.dip2px(mContext,60), heart_layout1.getHeight(), 1);
////                sendHudong("[::redPlayerQuan::]");
//
//            }

//            @Override
//            public void onClicklistenerLeftTwo(View view) {
//                int leg = redlegs[mRandom.nextInt(redlegs.length)];
////                heart_layout1.addHeart(randomColor(),leg,leg);
//                redonClickdrawableList2.clear();
//                redonClickdrawableList2.add(getResources().getDrawable(leg));
//                heart_layout1.setDrawableList(redonClickdrawableList2);
//                heart_layout1.startAnimation(UIUtils.dip2px(mContext,60), heart_layout1.getHeight(), 1);
//
////                sendHudong("[::redPlayerTui::]");
//            }

//            @Override
//            public void onClicklistenerLeftThree(View view) {
//                int knee = redknees[mRandom.nextInt(redknees.length)];
////                heart_layout1.addHeart(randomColor(),knee,knee);
//                redonClickdrawableList3.clear();
//                redonClickdrawableList3.add(getResources().getDrawable(knee));
//                heart_layout1.setDrawableList(redonClickdrawableList3);
//                heart_layout1.startAnimation(UIUtils.dip2px(mContext,60), heart_layout1.getHeight(), 1);
//
////                sendHudong("[::redPlayerXi::]");
//            }

//            @Override
//            public void onClicklistenerRightOne(View view) {
//                int quan = bluequans[mRandom.nextInt(bluequans.length)];
////                heart_layout2.addHeart(randomColor(),quan,quan);
//                blueonClickdrawableList1.clear();
//                blueonClickdrawableList1.add(getResources().getDrawable(quan));
//                heart_layout2.setDrawableList(blueonClickdrawableList1);
//                heart_layout2.startAnimation(heart_layout2.getWidth(), heart_layout2.getHeight(), 1);
////                sendHudong("[::bluePlayerQuan::]");
//            }

//            @Override
//            public void onClicklistenerRightTwo(View view) {
//                int leg = bluelegs[mRandom.nextInt(bluelegs.length)];
////                heart_layout2.addHeart(randomColor(),leg,leg);
//                blueonClickdrawableList2.clear();
//                blueonClickdrawableList2.add(getResources().getDrawable(leg));
//                heart_layout2.setDrawableList(blueonClickdrawableList2);
//                heart_layout2.startAnimation(heart_layout2.getWidth(), heart_layout2.getHeight(), 1);
////                sendHudong("[::bluePlayerTui::]");
//            }

//            @Override
//            public void onClicklistenerRightThree(View view) {
//                int knee = blueknees[mRandom.nextInt(blueknees.length)];
////              heart_layout2.addHeart(randomColor(),knee,knee);
//                blueonClickdrawableList3.clear();
//                blueonClickdrawableList3.add(getResources().getDrawable(knee));
//                heart_layout2.setDrawableList(blueonClickdrawableList3);
//                heart_layout2.startAnimation(heart_layout2.getWidth(), heart_layout2.getHeight(), 1);
////                sendHudong("[::bluePlayerXi::]");
//            }
//
//        });

    }

//    private void sendHudong(String s) {
//        SocketLoginBean socketLogin = new SocketLoginBean();
//        socketLogin.setMsg(s);
//        socketLogin.setType("sendHudong");
//        socketLogin.setSaichengId(((LiveDetailActivity) mContext).getSaichengId());
//        socketLogin.setDuizhenId(duizhenId + "");
//        String stringjson = new Gson().toJson(socketLogin);
//        ((LiveDetailActivity) mContext).fragment1.getHeiXiongSocketClient().sendMsg(stringjson + "\\r\\n");
//    }

//    private int randomColor() {
//        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
//    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        if (ScreenUtils.getOrientation(getContext()) == Configuration.ORIENTATION_LANDSCAPE) {
            videoLock.setVisibility(GONE);
            mLargeMediaController.setVisibility(VISIBLE);
//            mV4hudongView.setVisibility(VISIBLE);
//            heart_layout1.setVisibility(VISIBLE);
//            heart_layout2.setVisibility(VISIBLE);
            ll_edit_msg.setVisibility(VISIBLE);
//            initDanmu();
//            doCurrentduizhen();
        } else {
            mLargeMediaController.setVisibility(GONE);
//            mV4hudongView.setVisibility(GONE);
//            heart_layout1.setVisibility(GONE);
//            heart_layout2.setVisibility(GONE);
            danmakuView.stop();
        }
    }

    /**
     * 获取当前对阵
     */
//    private void doCurrentduizhen() {
//
//        hm = new HashMap<String, String>();
//        hm.put("saichengId", ((LiveDetailActivity) mContext).getSaichengId());
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
//                        List<DuizhenEntity> duizhenList = ((LiveDetailActivity) mContext).getDuizhen();
//                        addPlayerImg(duizhenList, Integer.valueOf(vs_order));
//
//                        //获取互动数据
//                        SocketLoginBean socketLogin = new SocketLoginBean();
//                        socketLogin.setDuizhenId(duizhenId + "");
//                        socketLogin.setSaichengId(((LiveDetailActivity) mContext).getSaichengId());
//                        socketLogin.setType("saichengDuizhenHudongData");
//                        String stringjson = new Gson().toJson(socketLogin);
//                        ((LiveDetailActivity) mContext).fragment1.getHeiXiongSocketClient().sendMsg(stringjson + "\\r\\n");
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

    /**
     * 初始化弹幕
     */
//    private void initDanmu() {
//
//        if (mContext instanceof LiveDetailActivity) { //直播详情
//            //如果是直播详情的页面  只有在直播的状态下有弹幕，预告和未开始没有弹幕
//            if (!(((LiveDetailActivity) mContext).isdanmu)) {
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
////              generateSomeDanmaku(mContext);
////                doDanmu(1);
//
//                //添加弹幕
////                generateSomeDanmaku(mContext, msg,uid);
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
////        danmakuView.start();
//
//
//        //发送弹幕输入框
//        ll_edit_msg.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showEditDialog(mContext);
//            }
//        });
//    }

//    private boolean isSendDamMu = true;//true联网   false 不联网
//    private Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            //逻辑处理
//            if (isSendDamMu) {
////                doDanmu(1);
//            }
//            handler.removeMessages(0);
//            handler.sendEmptyMessageDelayed(0, 15000);//15秒后再次执行
//        }
//    };

//    private void doDanmu(final int type) {
//        //
//        redPlayerQuanTimes = mV4hudongView.getLeftCountOne();
//        redPlayerTuiTimes = mV4hudongView.getLeftCountTwo();
//        redPlayerXiTimes = mV4hudongView.getLeftCountThree();
//        bluePlayerQuanTimes = mV4hudongView.getRightCountOne();
//        bluePlayerTuiTimes = mV4hudongView.getRightCountTwo();
//        bluePlayerXiTimes = mV4hudongView.getRightCountThree();
//        if (type == 1) { //获取弹幕
//            hm = new HashMap<String, String>();
//            hm.put("saichengId", ((LiveDetailActivity) mContext).getSaichengId());
//            hm.put("biaoshi", "haha");
//            hm.put("uid", userId);
//            hm.put("loginKey", loginKey);
//            hm.put("duizhenId", duizhenId + "");
//            hm.put("danmuLastid", danmuLastid);
//            hm.put("hudongLastid", hudongLastid);
//            hm.put("redPlayerQuanTimes", redPlayerQuanTimes + "");
//            hm.put("bluePlayerQuanTimes", bluePlayerQuanTimes + "");
//            hm.put("redPlayerTuiTimes", redPlayerTuiTimes + "");
//            hm.put("bluePlayerTuiTimes", bluePlayerTuiTimes + "");
//            hm.put("redPlayerXiTimes", redPlayerXiTimes + "");
//            hm.put("bluePlayerXiTimes", bluePlayerXiTimes + "");
//        } else if (type == 2) { //发送弹幕
//            hm = new HashMap<String, String>();
//            hm.put("saichengId", ((LiveDetailActivity) mContext).getSaichengId());
//            hm.put("biaoshi", "haha");
//            hm.put("uid", userId);
//            hm.put("loginKey", loginKey);
//            hm.put("msg", et_keywored.getText().toString().trim());
//            hm.put("danmuLastid", danmuLastid);
//            hm.put("hudongLastid", hudongLastid);
//            hm.put("redPlayerQuanTimes", redPlayerQuanTimes + "");
//            hm.put("bluePlayerQuanTimes", bluePlayerQuanTimes + "");
//            hm.put("redPlayerTuiTimes", redPlayerTuiTimes + "");
//            hm.put("bluePlayerTuiTimes", bluePlayerTuiTimes + "");
//            hm.put("redPlayerXiTimes", redPlayerXiTimes + "");
//            hm.put("bluePlayerXiTimes", bluePlayerXiTimes + "");
//        }
//        Log.d("danmu","vvvvvvvv"+redPlayerQuanTimes+"      "+bluePlayerQuanTimes+"    "+redPlayerTuiTimes+"     "+bluePlayerTuiTimes+"    "+redPlayerXiTimes+"    "+bluePlayerXiTimes+"   ");
//
//        MakerApplication.okHttpUtilsPost.url(NetUrlUtils.LIVE_DANMU)
//                .params(hm)
//                .build().execute(new StringCallback() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                Log.d("danmu", "获取弹幕：" + response);
//                try {
//                    LiveDanmuBean mliveabean = new Gson().fromJson(response, LiveDanmuBean.class);
//                    if (mliveabean != null && mliveabean.getStatus() != null && mliveabean.getStatus().equals("ok")) {
//                        mV4hudongView.setLeftCountOne(0);
//                        mV4hudongView.setLeftCountTwo(0);
//                        mV4hudongView.setLeftCountThree(0);
//                        mV4hudongView.setRightCountOne(0);
//                        mV4hudongView.setRightCountTwo(0);
//                        mV4hudongView.setRightCountThree(0);
//
//
//                        duizhenId = mliveabean.getData().getDangqian_duizhen().getDuizhen_id();
//                        //弹幕lastid，以供用户点击发送弹幕请求接口用
//                        danmuLastid = mliveabean.getData().getDanmuLastid();
//                        //互动lastid
//                        hudongLastid = mliveabean.getData().getHudongLastid();
//
//                        List<LiveDanmuBean.DataBean.DanmuBean> danmuList = mliveabean.getData().getDanmu();
//
//                            //添加弹幕
////                            generateSomeDanmaku(mContext, danmuList);
//
//
//
////                       当前第几场
//                        int Vs_order = mliveabean.getData().getDangqian_duizhen().getVs_order();
//                        List<DuizhenEntity> duizhenList = ((LiveDetailActivity) mContext).getDuizhen();
//                        addPlayerImg(duizhenList, Vs_order);
//
//                        //运动员的互动量
//                        LiveDanmuBean.DataBean.HudongBean hudongBean = mliveabean.getData().getHudong();
//                        addPlayerHudongNum(hudongBean);
//                        handler.removeMessages(0);
//                        handler.sendEmptyMessageDelayed(0, 15000);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


    //赋值运动员的头像
    private void addPlayerImg(List<DuizhenEntity> duizhenList, int vs_order) {
        if (duizhenList != null && duizhenList.size() > 0) {
            String bluephoto = duizhenList.get(vs_order - 1).getBlue_player_photo();
            String redphoto = duizhenList.get(vs_order - 1).getRed_player_photo();
//            mV4hudongView.setBlueImg(bluephoto);
//            mV4hudongView.setRedImg(redphoto);
        } else {
//            mV4hudongView.setDefultImg();
        }
    }


    public int bluequantimes;
    public int bluetuitimes;
    public int bluexitimes;
    public int redquantimes;
    public int redtuitimes;
    public int redxitimes;

    //赋值双方的互动数量
    int k = 0;

    private void initData() {
        k = i;
        if (k != 0 && k < huDongDatas.size()) {
            return;
        }
        List<InteractionData> datas = new ArrayList();
        if (timerTask != null) {
            timerTask.cancel();
        }

//        if(k!=0&&k<huDongDatas.size()){
//            for(int i=k;i<huDongDatas.size();i++){
//                datas.add(huDongDatas.get(i));
//            }
//            huDongDatas.clear();
//            huDongDatas.addAll(datas);
//
//        }else{
//            huDongDatas.clear();
//        }

        huDongDatas.clear();
        Log.d("3333333", "k======" + k + "     huDongDatas===" + huDongDatas.size());
//        for (int i = 0; i < blbucount; i++) {
//            InteractionData interactionData = new InteractionData(blueimg[i % 11], heart_layout2);
//            huDongDatas.add(interactionData);
//        }
//        for (int i = 0; i < redcount; i++) {
//            InteractionData interactionData = new InteractionData(redimg[i % 11], heart_layout1);
//            huDongDatas.add(interactionData);
//        }
//        for (int i = 0; i < redquantimes; i++) {
//            InteractionData interactionData = new InteractionData(redquans[i % 5], heart_layout1);
//            huDongDatas.add(interactionData);
//        }
//        for (int i = 0; i < bluexitimes; i++) {
//            InteractionData interactionData = new InteractionData(blueknees[i % 3], heart_layout2);
//            huDongDatas.add(interactionData);
//        }
//
//        for (int i = 0; i < bluetuitimes; i++) {
//            InteractionData interactionData = new InteractionData(bluelegs[i % 3], heart_layout2);
//            huDongDatas.add(interactionData);
//        }
//        for (int i = 0; i < redtuitimes; i++) {
//            InteractionData interactionData = new InteractionData(redlegs[i % 3], heart_layout1);
//            huDongDatas.add(interactionData);
//        }

        Collections.shuffle(huDongDatas);
//        Log.d("3333333","huDongDatas="+huDongDatas.size());
        showHuDong();
    }

    int i = 0;

    private void showHuDong() {
        i = 0;
        //mHeartLayout.addHea
        // rt(randomColor());
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (i >= huDongDatas.size()) {
                    if (timerTask != null) {
                        timerTask.cancel();
                    }
                    return;
                }
                huDongDatas.get(i).getHeartLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        i++;
//                        k=i;
                        if (i >= huDongDatas.size()) {
                            if (timerTask != null) {
                                timerTask.cancel();
                            }
                            i = 0;
                            return;
                        }
//                        huDongDatas.get(i).getHeartLayout().addHeart(randomColor(), huDongDatas.get(i).getImageid(), huDongDatas.get(i).getImageid());
                        drawableList.clear();
                        drawableList.add(getResources().getDrawable(huDongDatas.get(i).getImageid()));
                        huDongDatas.get(i).getHeartLayout().setDrawableList(drawableList);
                        huDongDatas.get(i).getHeartLayout().startAnimation(huDongDatas.get(i).getHeartLayout().getWidth(), huDongDatas.get(i).getHeartLayout().getHeight(), 1);
                    }
                });
            }
        };
        mTimer.scheduleAtFixedRate(timerTask, 300, 200);
    }


    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku(final Context ctx, final String danmu, final String uid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                while (showDanmaku) {
//                    int time = new Random().nextInt(500);
//                    String content = "测试一条信息" + time;
//                    addDanmaku(ctx, content, false);
//                    try {
//                        Thread.sleep(time);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (danmu!=null){
//                    for (int i = 0; i < danmu.size(); i++) {
//                        String danmuMsg = danmu.get(i).getMsg();
//                        if (danmu.get(i).getUid()!=null && danmu.get(i).getUid().equals(userId)){
//                            addDanmaku(ctx, danmuMsg, false,true);
//                        }else{
//                            addDanmaku(ctx, danmuMsg, false,false);
//                        }
//                        try {
//                            Thread.sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }


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


    @Override
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mV4TopTitleView.setTitle(title);
        }
    }

    @Override
    public boolean performClick() {
        if (lockFlag) {
            return false;
        }
        if (rlSkin != null) {
            if (!mRlSkinHide) {
                hide();
            } else {
                show();
            }
            return false;
        }
        return super.performClick();
    }

    @Override
    protected void seekTo(int seekGap) {
        IBaseLiveSeekBar seekBar = ((V4LargeLiveMediaControllerNew) mLargeMediaController).getSeekbar();
        if (seekBar != null) {
            seekBar.startTrackingTouch();
            Log.i("zsn", "--------seekGap:" + seekGap);
            seekBar.setProgressGap(seekGap);
            seekBar.setSeekToTime(seekGap, true);
            if (mGestureControl.mSeekToPopWindow != null) {
                mGestureControl.mSeekToPopWindow.setProgress(seekBar.getSeekToTime());
            }
        }
        super.seekTo(seekGap);
    }

    @Override
    protected void touchUp() {
        IBaseLiveSeekBar seekBar = ((V4LargeLiveMediaControllerNew) mLargeMediaController).getSeekbar();
        if (seekBar != null) {
            seekBar.stopTrackingTouch();
        }
        super.touchUp();
    }

    /**
     * 同步进度条
     *
     * @param progress
     */
    @Override
    public void syncSeekProgress(int progress) {
        if (mLargeMediaController != null) {
            ((V4LargeLiveMediaControllerNew) mLargeMediaController).getSeekbar().setProgress(progress);
        }
        if (mSmallMediaController != null) {
            ((V4SmallLiveMediaControllerNew) mSmallMediaController).getSeekbar().setProgress(progress);
        }
    }

    //点击别的区域的时候,设置输入法选项
//    public void showEditDialog(final Context context) {
//        isShow = true;
//        if (dialog == null) {
//            dialog = new Dialog(context, R.style.dialog);
//        }
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View view = inflater.inflate(R.layout.view_edittext, null);
//        et_keywored = (EditText) view.findViewById(R.id.et_keywored);
//        tv_send = (TextView) view.findViewById(R.id.tv_send);
//        tv_textcount = (TextView) view.findViewById(R.id.tv_textcount);
//
//
//        //设置展示范围
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().
//                setAttributes(params);
//
//        dialog.setContentView(view);
//        dialog.show();
//
//        et_keywored.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e("s.toString()", s.toString());
//                if (s.length() <= 40) {
//                    tv_textcount.setText((40 - s.length()) + "");
//                } else {
//                    et_keywored.setText(s.subSequence(0, 40));
//                    et_keywored.setSelection(40);
//                    Toast.makeText(mContext, "字数已经超出限制", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });
//
//
//        et_keywored.setOnFocusChangeListener(new OnFocusChangeListener() {
//                                                 @Override
//                                                 public void onFocusChange(View v, boolean hasFocus) {
//                                                     if (hasFocus) {
//                                                         et_keywored.post(new Runnable() {
//                                                             @Override
//                                                             public void run() {
//                                                                 InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                                                 imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
//                                                                 //自动弹出
//                                                                 try {
//                                                                     Thread.sleep(40);
//                                                                 } catch (InterruptedException e) {
//
//                                                                 }
//                                                             }
//                                                         });
//                                                     }
//
//                                                 }
//                                             }
//        );
//
//        //发送消息
//        tv_send.setOnClickListener(new OnClickListener() {
//                                       @Override
//                                       public void onClick(View v) {
//                                           if (!TextUtils.isEmpty(et_keywored.getText().toString().trim())) {
//                                               Toast.makeText(mContext, "发送成功!", Toast.LENGTH_SHORT).show();
////                                               ismysendmsg=true;
////                                               //蓝方的点击次数
////                                               bluePlayerQuanTimes = mV4hudongView.getLeftCountOne();
////                                               bluePlayerTuiTimes = mV4hudongView.getLeftCountTwo();
////                                               bluePlayerXiTimes = mV4hudongView.getLeftCountThree();
////                                               //红方的点击次数
////                                               redPlayerQuanTimes = mV4hudongView.getRightCountOne();
////                                               redPlayerTuiTimes = mV4hudongView.getRightCountTwo();
////                                               redPlayerXiTimes = mV4hudongView.getRightCountThree();
////                                               //重置信息，重新发送handle
////                                               handler.removeMessages(0);
////                                               handler.sendEmptyMessageDelayed(0, 15000);//15秒后再次执行
////                                               doDanmu(2);
//                                               SocketLoginBean socketLogin = new SocketLoginBean();
//                                               socketLogin.setMsg(et_keywored.getText().toString().trim());
//                                               socketLogin.setType("sendReliao");
//                                               String stringjson = new Gson().toJson(socketLogin);
//                                               ((LiveDetailActivity) mContext).fragment1.getHeiXiongSocketClient().sendMsg(stringjson + "\\r\\n");
//                                               et_keywored.getText().clear();
//                                               dialog.dismiss();
//                                               hideKeyBoard();
//
//                                           } else {
//                                               Toast.makeText(mContext, "内容不能为空!", Toast.LENGTH_SHORT).show();
//                                               return;
//                                           }
//                                       }
//                                   }
//
//        );
//
//        //隐藏dialog的方法
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                       @Override
//                                       public void onCancel(final DialogInterface dialog) {
//                                           //小米手机
//                                           if (model.contains("MI 3")) {
//                                               et_keywored.post(new Runnable() {
//                                                   @Override
//                                                   public void run() {
//                                                       InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                                       imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
//                                                       try {
//                                                           Thread.sleep(100);
//                                                       } catch (Exception e) {
//
//                                                       }
//
//                                                   }
//                                               });
//                                           } else {
//                                               //普通手机
//                                               InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                               imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
//                                           }
//                                           isShow = false;
////                                           Log.e("Cancel", "per=" + per);
////                                           mVideoView.seekTo(per);
////                                           mVideoView.start();
////                                           mDanmakuView.resume();
//                                       }
//                                   }
//        );
//
//        et_keywored.setFocusable(true);
//    }


    //隐藏键盘和输入框
    public void hideKeyBoard() {
        isShow = false;
        if (model.contains("MI 3")) {
            //小米手机
            et_keywored.post(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }
            });
        } else {
            //普通手机
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

//    private Handler handler = new Handler() {
//        public void handleMessage(android.os.Message msg) {
//            connectTimes++;
//            if (connectTimes < 10 && ((LiveDetailActivity) mContext).fragment1.isLogin == 0) {
//                getHeiXiongSocketClient().connect();
//                handler.sendEmptyMessageDelayed(0, 5000);//15秒后再次执行
//            }
//
//        }
//    };

//    @Override
//    public void onReceive(String strMsg) {
//        Log.d("nnnmmmmzzz", "sssss" + strMsg);
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(strMsg);
//            String type = jsonObject.optString("type");
//            String status = jsonObject.optString("status");
//
//            //登录
//            if (type != null && type.equals("login") && status != null && status.equals("ok")) {
//                ((LiveDetailActivity) mContext).fragment1.isLogin = 1;
//            } else if (type != null && type.equals("login") && status != null && status.equals("error")) {
//                handler.sendEmptyMessageDelayed(0, 5000);
//            } else if (type != null && type.equals("reliao")) {  //热聊
//                if (isinitdanmu == 1) { //已初始化弹幕成功
//                    uid = jsonObject.optString("uid");
//                    msg = jsonObject.optString("msg");
//                    generateSomeDanmaku(mContext, msg, uid);
//                }
//            } else if (type != null && type.equals("tongzhi_duizhen_c")) { //推送当前对阵状态
//                String state = jsonObject.optString("state");
//                if (state != null && state.equals("2")) {
//                    String vs_order = jsonObject.getString("vs_order");
//                    duizhenId = jsonObject.getString("duizhen_id");
//                    List duizhenList = ((LiveDetailActivity) mContext).getDuizhen();
//                    addPlayerImg(duizhenList, Integer.valueOf(vs_order));
//                    //重置互动点赞数量
//                    mV4hudongView.setBlueHudongNum("0");
//                    mV4hudongView.setRedHudongNum("0");
//
//                }
//            } else if (type != null && type.equals("saichengDuizhenHudongData")) { ////校验互动数据
//                if (status != null && status.equals("ok")) {
//                    JSONObject daat = jsonObject.getJSONObject("data");
//                    String red_data = daat.getString("red_data");
//                    String blue_data = daat.getString("blue_data");
//                    Log.d("yyyeee",red_data+" haha   "+blue_data);
//                    addPlayerHudongNum(red_data, blue_data);
//                }
//            } else if (type != null && type.equals("hudong")) {
//                //互动
//                String hudongmsg = jsonObject.optString("msg");
//                String uid = jsonObject.optString("uid");
//                if (uid != null && !uid.equals(userId)) {
//                    if (hudongmsg != null && hudongmsg.equals("[::redPlayerQuan::]")) {
//                        int quan = redquans[mRandom.nextInt(redquans.length)];
//                        redonClickdrawableList1.clear();
//                        redonClickdrawableList1.add(getResources().getDrawable(quan));
////                    heart_layout1.setDrawableList(redonClickdrawableList1);
////                    heart_layout1.startAnimation(heart_layout1.getWidth(), heart_layout1.getHeight(), 1);
//                        datas.add(new InteractionData(heart_layout1, redonClickdrawableList1,0));
//                        addredhudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::redPlayerTui::]")) {
//                        int leg = redlegs[mRandom.nextInt(redlegs.length)];
////                    heart_layout1.setDrawableList(redonClickdrawableList2);
////                    heart_layout1.startAnimation(heart_layout1.getWidth(), heart_layout1.getHeight(), 1);
//                        redonClickdrawableList2.clear();
//                        redonClickdrawableList2.add(getResources().getDrawable(leg));
//                        datas.add(new InteractionData(heart_layout1, redonClickdrawableList2,0));
//                        addredhudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::redPlayerXi::]")) {
//                        int xi = redknees[mRandom.nextInt(redknees.length)];
//                        redonClickdrawableList3.clear();
//                        redonClickdrawableList3.add(getResources().getDrawable(xi));
////                    heart_layout1.setDrawableList(redonClickdrawableList3);
////                    heart_layout1.startAnimation(heart_layout1.getWidth(), heart_layout1.getHeight(), 1);
//                        datas.add(new InteractionData(heart_layout1, redonClickdrawableList3,0));
//                        addredhudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::bluePlayerQuan::]")) {
//                        int quans = bluequans[mRandom.nextInt(bluequans.length)];
//                        blueonClickdrawableList1.clear();
//                        blueonClickdrawableList1.add(getResources().getDrawable(quans));
////                    heart_layout2.setDrawableList(blueonClickdrawableList1);
////                    heart_layout2.startAnimation(heart_layout2.getWidth(), heart_layout2.getHeight(), 1);
//                        datas.add(new InteractionData(heart_layout2, blueonClickdrawableList1,1));
//                        addbluehudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::bluePlayerTui::]")) {
//                        int legs = bluelegs[mRandom.nextInt(bluelegs.length)];
//                        blueonClickdrawableList2.clear();
//                        blueonClickdrawableList2.add(getResources().getDrawable(legs));
////                    heart_layout2.setDrawableList(blueonClickdrawableList2);
////                    heart_layout2.startAnimation(heart_layout2.getWidth(), heart_layout2.getHeight(), 1);
//                        datas.add(new InteractionData(heart_layout2, blueonClickdrawableList2,1));
//                        addbluehudongcount();
//                    } else if (hudongmsg != null && hudongmsg.equals("[::bluePlayerXi::]")) {
//                        int knee = blueknees[mRandom.nextInt(blueknees.length)];
//                        blueonClickdrawableList3.clear();
//                        blueonClickdrawableList3.add(getResources().getDrawable(knee));
////                    heart_layout2.setDrawableList(blueonClickdrawableList3);
////                    heart_layout2.startAnimation(heart_layout2.getWidth(), heart_layout2.getHeight(), 1);
//                        datas.add(new InteractionData(heart_layout2, blueonClickdrawableList3,1));
//                        addbluehudongcount();
//                    }
//                }
//                if (!isFirstShowAnimation) {
//                    addanimation();
//                }
//                isFirstShowAnimation = true;
//            }
//            //重新登录
//            if (status != null && status.equals("relogin")) {
//                ((LiveDetailActivity) mContext).fragment1.socketLogin();
//            }
////            OnClickEventUtils.isFastClick()
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

//    private List<InteractionData> datas = new MyList();
    private boolean isFirstShowAnimation = false;
    private TimerTask animationTimerTask;

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
//                                datas.get(i).getHeartLayout().startAnimation(UIUtils.dip2px(mContext,60), datas.get(i).getHeartLayout().getHeight(), 1);
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

    public void isRate(boolean isRate){
        mLargeMediaController.isRate(isRate);
    }

    @Override
    public void onReceive(String strMsg) {

    }
}

