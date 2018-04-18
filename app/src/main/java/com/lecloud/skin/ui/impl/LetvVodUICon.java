package com.lecloud.skin.ui.impl;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hxwl.common.utils.DensityUtil;
import com.hxwl.wulinfeng.R;
import com.lecloud.skin.ui.ILetv;
import com.lecloud.skin.ui.base.BasePlayerSeekBar;
import com.lecloud.skin.ui.utils.ReUtils;
import com.lecloud.skin.ui.utils.ScreenUtils;
import com.lecloud.skin.ui.utils.TimerUtils;
import com.lecloud.skin.ui.view.V4LargeMediaController;
import com.lecloud.skin.ui.view.V4SmallMediaController;
import com.lecloud.skin.ui.view.V4TopTitleView;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class LetvVodUICon extends BaseLetvVodUICon {


    private LinearLayout ll_danmu;

    public LetvVodUICon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LetvVodUICon(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetvVodUICon(Context context) {
        super(context);
    }
    protected boolean showDanmaku;
    //弹幕View
    protected DanmakuView danmakuView;
    protected DanmakuContext danmakuContext;
    protected Context mContext;

    protected BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    protected void init(final Context context) {
//		UI 分层
        super.init(context);
        mContext = context;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        rlSkin = (FrameLayout) LayoutInflater.from(getContext()).inflate(R.layout.letv_skin_v4_skin, null);
        addView(rlSkin, params);
        videoLock = (ImageView) findViewById(ReUtils.getId(context, "iv_video_lock"));
        videoLock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lockFlag = !lockFlag;
                if (lockFlag) {
                    hide();
                    videoLock.setImageResource(R.drawable.screen_lock_drawable);
                } else {
                    show();
                    videoLock.setImageResource(R.drawable.screen_unlock_drawable);
                }
            }
        });


        danmakuView = (DanmakuView)findViewById(R.id.danmakuview);
        mLargeMediaController = (V4LargeMediaController) findViewById(R.id.v4_large_media_controller);
        mV4TopTitleView = (V4TopTitleView) findViewById(R.id.v4_letv_skin_v4_top_layout);
        ll_danmu = (LinearLayout) findViewById(R.id.ll_danmu);
        mSmallMediaController = (V4SmallMediaController) findViewById(R.id.v4_small_media_controller);
        mV4TopTitleView.setmILetv(new ILetv() {
            @Override
            public void isOpenDanmu(boolean isOpen) {
                if(danmakuView == null){
                    return;
                }
//                UIUtils.showToast("  www "+isOpen);
                if(isOpen){
                   initDanmu();

                }else{
                    danmakuView.stop();
                }
            }

            @Override
            public void isOpenHudong(boolean isOpen) {

            }
        });


//        if (isFullScreen()){
//            initDanmu();
//        }else{
//            danmakuView.stop();
//        }
    }

//    @Override
//    public void setRequestedOrientation(int requestedOrientation, View view) {
//        super.setRequestedOrientation(requestedOrientation, view);
//        if (requestedOrientation == ILetvVodUICon.SCREEN_LANDSCAPE) {//全屏
//            initDanmu();
//        } else {//半屏
//            danmakuView.stop();
//        }
//    }
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        if (ScreenUtils.getOrientation(getContext()) == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("123rty","ffffrrrrr");
            initDanmu();
        }else{
            Log.d("123rty","ttt");
            danmakuView.stop();
        }
    }


    /**
     * 初始化弹幕
     */
    private void initDanmu() {

//        if (mContext instanceof HuiguDetailActivity){ //回顾详情
//            //隐藏弹幕布局
//            ll_danmu.setVisibility(GONE);
//            return;
//        }else if (mContext instanceof LiveDetailActivity){ //直播详情
//            //如果是直播详情的页面  只有在直播的状态下有弹幕，预告和未开始没有弹幕
//            if (!(((LiveDetailActivity) mContext).isdanmu)){
//                //隐藏弹幕布局
//                ll_danmu.setVisibility(GONE);
//                return;
//            }else{
//                ll_danmu.setVisibility(VISIBLE);
//
//            }
//
//        }
        danmakuView.enableDanmakuDrawingCache(true);
        Log.d("123rty","jgggggj");
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();
//                ToastUtils.showToast(mContext,"弹幕已经开始");
                Log.d("123rty","jjjjjjjjjjj");
                generateSomeDanmaku(mContext);
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);
//        danmakuView.start();

    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku(final Context ctx) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku) {
                    int time = new Random().nextInt(500);
                    String content = "测试一条信息" + time;
                    addDanmaku(ctx, content, false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
    public void addDanmaku(Context ctx, String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || danmakuView == null) {
            return;
        }
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = DensityUtil.sp2px(ctx, 20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
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
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mV4TopTitleView.setTitle(title);
        }
    }


    @Override
    protected void seekTo(int seekGap) {
        BasePlayerSeekBar seekBar = ((V4LargeMediaController) mLargeMediaController).getSeekbar();
        if (seekBar != null) {
            seekBar.startTrackingTouch();
            seekBar.setProgressGap(seekGap);
            if (mGestureControl.mSeekToPopWindow != null) {
                String progress = TimerUtils.stringForTime((int) (seekBar.getProgress() * duration / seekBar.getMax() / 1000));
                int times = (int) (duration / 1000);
                String duration = TimerUtils.stringForTime(times);
                mGestureControl.mSeekToPopWindow.setProgress(progress, duration);
            }
        }
        super.seekTo(seekGap);
    }

    @Override
    protected void touchUp() {
        BasePlayerSeekBar seekBar = ((V4LargeMediaController) mLargeMediaController).getSeekbar();
        if (seekBar != null) {
            seekBar.stopTrackingTouch();
        }
        super.touchUp();
    }

    public void isHideChgBtn(boolean isHideChgBtn){
        ((V4SmallMediaController)mSmallMediaController).setHideChgScreenBtn(isHideChgBtn);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
