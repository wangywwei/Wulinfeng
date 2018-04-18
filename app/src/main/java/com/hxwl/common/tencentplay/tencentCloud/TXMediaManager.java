package com.hxwl.common.tencentplay.tencentCloud;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.internal.FastBlur;

import static android.view.View.GONE;
import static com.tencent.rtmp.TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
import static com.tencent.rtmp.TXLiveConstants.RENDER_ROTATION_PORTRAIT;
import static com.tencent.rtmp.TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
import static com.tencent.rtmp.TXLivePlayer.PLAY_TYPE_VOD_HLS;
import static com.tencent.rtmp.TXLivePlayer.PLAY_TYPE_VOD_MP4;

/**
 * <p>统一管理MediaPlayer的地方,只有一个mediaPlayer实例，那么不会有多个视频同时播放，也节省资源。</p>
 * <p>Unified management MediaPlayer place, there is only one MediaPlayer instance, then there will be no more video broadcast at the same time, also save resources.</p>
 * Created by Nathen
 * On 2015/11/30 15:39
 */
public class TXMediaManager {
    public static String TAG = "JieCaoVideoPlayer";
    private static TXMediaManager TXMediaManager;
    public TXCloudVideoView textureView;

    public TXLivePlayConfig mPlayConfig;
    public TXLivePlayer mLivePlayer;
    public TXCloudViewViewImp mPlayerView1;
    public static SurfaceTexture savedSurfaceTexture;

    public static String CURRENT_PLAYING_URL;
    public static boolean CURRENT_PLING_LOOP;
    public static Map<String, String> MAP_HEADER_DATA;
    public int currentVideoWidth = 0;
    public int currentVideoHeight = 0;

    public static final int HANDLER_PREPARE = 0;
    public static final int HANDLER_RELEASE = 2;
    HandlerThread mMediaHandlerThread;
    MediaHandler mMediaHandler;
    Handler mainThreadHandler;
    private Surface mSurface;
    private int mActivityType;
    public static final int ACTIVITY_TYPE_PUBLISH = 1;
    public static final int ACTIVITY_TYPE_LIVE_PLAY = 2;//直播
    public static final int ACTIVITY_TYPE_VOD_PLAY = 3;//点播
    public static final int ACTIVITY_TYPE_LINK_MIC = 4;
    private List<TXLivePlayer> playList = new ArrayList<>();
    public int mCurrentRenderRotation;

    public int urlType = -1;

    public static Context ctx;
    public int result;

    public static TXMediaManager instance(Context ctx2) {
        if (TXMediaManager == null) {
            TXMediaManager = new TXMediaManager(ctx2);
        }
        ctx = ctx2;
        return TXMediaManager;
    }

    public void setActivityType(int mActivityType) {
        this.mActivityType = mActivityType;
    }

    public int getActivityType() {
        return mActivityType;
    }

    public Drawable draw;
    private Bitmap bit ;
    public void setBackImg(Bitmap bit) {
        this.bit = bit ;
        this.draw = new BitmapDrawable(ctx.getResources(), bit);
    }

    public Drawable getBackImg() {
        if (this.draw == null) {
            Resources resources = ctx.getResources();
            Drawable drawable = resources.getDrawable(R.drawable.icon_pic_default);
            Bitmap bm = com.hxwl.wulinfeng.view.FastBlur.toBlur(BitmapUtils.drawableToBitmap(drawable),0);
            return BitmapUtils.bitmap2Drawable(bm);
        }
        return this.draw;
    }

    public Bitmap getBackImgBit() {
        if (this.bit == null) {
            return null ;
        }
        return this.bit;
    }

    /**
     * 腾讯点播 -- 直播
     *
     * @param url
     * @param urlType
     * @param title
     * @return
     */
    public View TXVodPlayAndView(final String url, RelativeLayout videoContainer2, int urlType, String title) {
        if (urlType == -1) {
            this.urlType = getCheckCode(url);
        } else {
            if (getActivityType() == ACTIVITY_TYPE_VOD_PLAY) {
                if (urlType == 3) {//mp4
                    this.urlType = PLAY_TYPE_VOD_MP4;
                } else if (urlType == 4) {//flv
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
                } else if (urlType == 5) {//hls (m3u8)
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                } else if (urlType == 2) {//hls (rtmp)
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                }
            } else if (getActivityType() == ACTIVITY_TYPE_LIVE_PLAY) {
                if (urlType == 5) {//HLS
                    this.urlType = PLAY_TYPE_VOD_HLS;
                } else if (urlType == 2) {//RTMP
                    this.urlType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if (urlType == 4) {//FLV
                    this.urlType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                }
            }
        }
        if (mPlayConfig == null) {
            mPlayConfig = new TXLivePlayConfig();
        }
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (textureView != null) {
            textureView.onDestroy();
            textureView = null;
        }
        if (mPlayerView1 != null) {
            mPlayerView1.removeAllViews();
            mPlayerView1 = null;
        }
        mLivePlayer = new TXLivePlayer(ctx);
        mPlayerView1 = new TXCloudViewViewImp(ctx);
        mLivePlayer.enableHardwareDecode(false);
        mLivePlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);
//        playList.add(mLivePlayer);
        mCurrentRenderRotation = RENDER_ROTATION_PORTRAIT;
        mLivePlayer.setRenderRotation(RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);

        mLivePlayer.setConfig(mPlayConfig);
        mPlayerView1.setAllControlsVisible(GONE, GONE, View.VISIBLE, GONE, View.VISIBLE, View.VISIBLE, GONE);
        mPlayerView1.tinyBackImageView.setVisibility(GONE);

        mPlayerView1.setUp(url, TXCloudViewViewA.SCREEN_LAYOUT_LIST, title);
        mPlayerView1.seekToInAdvance = 0;
        mPlayerView1.startVideo();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.width = width;
        int height = width * 9 / 16;
        params.height = height;
        if (mPlayerView1 != null && mPlayerView1.getParent() != null) {
            ((ViewGroup) mPlayerView1.getParent()).removeView(mPlayerView1);
        }
        videoContainer2.removeAllViews();
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        videoContainer2.addView((View) mPlayerView1, params);
        videoContainer2.refreshDrawableState();
        return mPlayerView1;
    }

    /**
     * 腾讯点播 -- 直播
     *
     * @param url
     * @param urlType
     * @param title
     * @return
     */
    public View TXVodPlayAndView(final String url, RelativeLayout videoContainer2, int urlType, String title, int position) {
        if (urlType == -1) {
            this.urlType = getCheckCode(url);
        } else {
            if (getActivityType() == ACTIVITY_TYPE_VOD_PLAY) {
                if (urlType == 3) {//mp4
                    this.urlType = PLAY_TYPE_VOD_MP4;
                } else if (urlType == 4) {//flv
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
                } else if (urlType == 5) {//hls (m3u8)
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                } else if (urlType == 2) {//hls (rtmp)
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                }
            } else if (getActivityType() == ACTIVITY_TYPE_LIVE_PLAY) {
                if (urlType == 7) {//HLS
                    this.urlType = PLAY_TYPE_VOD_HLS;
                } else if (urlType == 8) {//RTMP
                    this.urlType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if (urlType == 9) {//FLV
                    this.urlType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                }
            }
        }
        if (mPlayConfig == null) {
            mPlayConfig = new TXLivePlayConfig();
        }
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (mPlayerView1 != null) {
            mPlayerView1.removeAllViews();
            mPlayerView1 = null;
        }
        mLivePlayer = new TXLivePlayer(ctx);
        mPlayerView1 = new TXCloudViewViewImp(ctx);
        mLivePlayer.enableHardwareDecode(false);
        mLivePlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);
        mCurrentRenderRotation = RENDER_ROTATION_PORTRAIT;

        mLivePlayer.setRenderRotation(RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);

        mLivePlayer.setConfig(mPlayConfig);
        mPlayerView1.setAllControlsVisible(GONE, GONE, View.VISIBLE, GONE, View.VISIBLE, View.VISIBLE, GONE);
        mPlayerView1.tinyBackImageView.setVisibility(GONE);
        mPlayerView1.setPosition(position);//绑定Position

        mPlayerView1.setUp(url, TXCloudViewViewA.SCREEN_LAYOUT_LIST, title);
        mPlayerView1.seekToInAdvance = 0;
        mPlayerView1.startVideo();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.width = width;
        int height = width * 9 / 16;
        params.height = height;
        if (mPlayerView1 != null && mPlayerView1.getParent() != null) {
            ((ViewGroup) mPlayerView1.getParent()).removeView(mPlayerView1);
        }
        videoContainer2.removeAllViews();
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        videoContainer2.addView((View) mPlayerView1, params);
        videoContainer2.refreshDrawableState();
        return mPlayerView1;
    }
    /**
     * 腾讯点播 -- 直播
     *
     * @param url
     * @param urlType
     * @param title
     * @return
     */
    public View TXVodPlayAndViewWL(final String url, RelativeLayout videoContainer2, int urlType, String title, int position) {
        if (urlType == -1) {
            this.urlType = getCheckCode(url);
        } else {
            if (getActivityType() == ACTIVITY_TYPE_VOD_PLAY) {
                if (urlType == 3) {//mp4
                    this.urlType = PLAY_TYPE_VOD_MP4;
                } else if (urlType == 4) {//flv
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
                } else if (urlType == 5) {//hls (m3u8)
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                } else if (urlType == 2) {//hls (rtmp)
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                }
            } else if (getActivityType() == ACTIVITY_TYPE_LIVE_PLAY) {
                if (urlType == 7) {//HLS
                    this.urlType = PLAY_TYPE_VOD_HLS;
                } else if (urlType == 8) {//RTMP
                    this.urlType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if (urlType == 9) {//FLV
                    this.urlType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                }
            }
        }
//        this.videoContainer = videoContainer2;
        if (mPlayConfig == null) {
            mPlayConfig = new TXLivePlayConfig();
        }
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
//        if (textureView != null) {
//            textureView.onDestroy();
//            textureView = null;
//        }
        if (mPlayerView1 != null) {
            mPlayerView1.removeAllViews();
            mPlayerView1 = null;
        }
        mLivePlayer = new TXLivePlayer(ctx);
        mPlayerView1 = new TXCloudViewViewImp(ctx);
        mLivePlayer.enableHardwareDecode(false);
        mLivePlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);
//        playList.add(mLivePlayer);
        mCurrentRenderRotation = RENDER_ROTATION_PORTRAIT;
//        if (getCheckCode(url) == -1) {
//            return null;
//        }

//        mLivePlayer.setPlayListener(this);
        mLivePlayer.setRenderRotation(RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);

        mLivePlayer.setConfig(mPlayConfig);
        mPlayerView1.setAllControlsVisible(GONE, GONE, View.VISIBLE, GONE, View.VISIBLE, View.VISIBLE, GONE);
        mPlayerView1.tinyBackImageView.setVisibility(GONE);
        mPlayerView1.setPosition(position);//绑定Position

        mPlayerView1.setUp(url, TXCloudViewViewA.SCREEN_LAYOUT_LIST, title);
        mPlayerView1.seekToInAdvance = 0;
        mPlayerView1.startVideo();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.width = width;
        int height = width * 9 / 16;
        params.height = height;
        if (mPlayerView1 != null && mPlayerView1.getParent() != null) {
            ((ViewGroup) mPlayerView1.getParent()).removeView(mPlayerView1);
        }
        videoContainer2.removeAllViews();
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        videoContainer2.addView((View) mPlayerView1, params);
        videoContainer2.refreshDrawableState();
        return mPlayerView1;
    }

    /**
     * 腾讯点播 -- 直播(短拍专用)
     *
     * @param url
     * @param urlType
     * @param title
     * @return
     */
    public View TXVodPlayForDP(final String url, RelativeLayout videoContainer2, int urlType, String title) {
        if (urlType == -1) {
            this.urlType = getCheckCode(url);
        } else {
            if (getActivityType() == ACTIVITY_TYPE_VOD_PLAY) {
                if (urlType == 3) {//mp4
                    this.urlType = PLAY_TYPE_VOD_MP4;
                } else if (urlType == 4) {//flv
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_FLV;
                } else if (urlType == 5) {//hls (m3u8)
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                } else if (urlType == 2) {//hls (rtmp)
                    this.urlType = TXLivePlayer.PLAY_TYPE_VOD_HLS;
                }
            } else if (getActivityType() == ACTIVITY_TYPE_LIVE_PLAY) {
                if (urlType == 7) {//HLS
                    this.urlType = PLAY_TYPE_VOD_HLS;
                } else if (urlType == 8) {//RTMP
                    this.urlType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP;
                } else if (urlType == 9) {//FLV
                    this.urlType = TXLivePlayer.PLAY_TYPE_LIVE_FLV;
                }
            }
        }
//        this.videoContainer = videoContainer2;
        if (mPlayConfig == null) {
            mPlayConfig = new TXLivePlayConfig();
        }
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
//        if (textureView != null) {
//            textureView.onDestroy();
//            textureView = null;
//        }
        if (mPlayerView1 != null) {
            mPlayerView1.removeAllViews();
            mPlayerView1 = null;
        }
        mLivePlayer = new TXLivePlayer(ctx);
        mPlayerView1 = new TXCloudViewViewImp(ctx);
        mLivePlayer.enableHardwareDecode(false);
        mLivePlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);
//        playList.add(mLivePlayer);
        mCurrentRenderRotation = RENDER_ROTATION_PORTRAIT;
//        if (getCheckCode(url) == -1) {
//            return null;
//        }

//        mLivePlayer.setPlayListener(this);
        mLivePlayer.setRenderRotation(RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setRenderMode(RENDER_MODE_ADJUST_RESOLUTION);

        mLivePlayer.setConfig(mPlayConfig);
        mPlayerView1.setAllControlsVisible(GONE, GONE, View.VISIBLE, GONE, View.VISIBLE, View.VISIBLE, GONE);
        mPlayerView1.tinyBackImageView.setVisibility(GONE);

        mPlayerView1.setUp(url, TXCloudViewViewA.SCREEN_LAYOUT_LIST, title);
        mPlayerView1.seekToInAdvance = 0;
        mPlayerView1.startVideo();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.width = width;
        int height = width;
//        int height = width * 9 / 16;
        params.height = height;
        if (mPlayerView1 != null && mPlayerView1.getParent() != null) {
            ((ViewGroup) mPlayerView1.getParent()).removeView(mPlayerView1);
        }
        videoContainer2.removeAllViews();
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        videoContainer2.addView((View) mPlayerView1, params);
        videoContainer2.refreshDrawableState();
        return mPlayerView1;
    }

    public TXMediaManager() {
        mMediaHandlerThread = new HandlerThread(TAG);
        mMediaHandlerThread.start();
        mMediaHandler = new MediaHandler((mMediaHandlerThread.getLooper()));
        mainThreadHandler = new Handler();
    }

    public TXMediaManager(Context ctx) {
        this.ctx = ctx;
        mMediaHandlerThread = new HandlerThread(TAG);
        mMediaHandlerThread.start();
        mMediaHandler = new MediaHandler((mMediaHandlerThread.getLooper()));
        mainThreadHandler = new Handler();
    }

    public Point getVideoSize() {
        if (currentVideoWidth != 0 && currentVideoHeight != 0) {
            return new Point(currentVideoWidth, currentVideoHeight);
        } else {
            return null;
        }
    }

    public class MediaHandler extends Handler {
        public MediaHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_PREPARE:
                    try {
                        currentVideoWidth = 0;
                        currentVideoHeight = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case HANDLER_RELEASE:
                    break;
            }
        }
    }

    //回复播放器 想着再list重现的时候调用
    public void resumeplayer() {
        if (mLivePlayer != null) {
            if (urlType == TXLivePlayer.PLAY_TYPE_VOD_FLV || urlType == TXLivePlayer.PLAY_TYPE_VOD_HLS ||
                    urlType == TXLivePlayer.PLAY_TYPE_VOD_MP4 || urlType == TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO
                    ) {
                mLivePlayer.resume();
            } else if (urlType == TXLivePlayer.PLAY_TYPE_LIVE_RTMP || urlType == TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC || urlType == TXLivePlayer.PLAY_TYPE_LIVE_FLV) {

                result = mLivePlayer.startPlay(CURRENT_PLAYING_URL, urlType); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
            }

        }

    }

    //
    public void pauseplayer() {
        if (mLivePlayer != null) {
            if (urlType == TXLivePlayer.PLAY_TYPE_VOD_FLV || urlType == TXLivePlayer.PLAY_TYPE_VOD_HLS ||
                    urlType == TXLivePlayer.PLAY_TYPE_VOD_MP4 || urlType == TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO
                    ) {
                mLivePlayer.pause();
            } else if (urlType == TXLivePlayer.PLAY_TYPE_LIVE_RTMP || urlType == TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC || urlType == TXLivePlayer.PLAY_TYPE_LIVE_FLV) {
                mLivePlayer.stopPlay(false);
            }

        }

    }

    //停止音乐播放器 想着在list划出屏幕的时候调用
    public void stopplayer() {
        if (mLivePlayer != null) {
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
    }

    public void videoDestroy() {
        if (mLivePlayer != null) {
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (TXCloudPlayerManager.getCurrentJcvd() != null) {
//            TXCloudPlayerManager.getCurrentJcvd().currentState = CURRENT_STATE_NORMAL;
            if (TXCloudPlayerManager.getFirstFloor() != null) {
                TXCloudPlayerManager.getFirstFloor().clearFloatScreen();
                TXCloudPlayerManager.setFirstFloor(null);
            }
            if (TXCloudPlayerManager.getSecondFloor() != null) {
                TXCloudPlayerManager.getSecondFloor().clearFloatScreen();
                TXCloudPlayerManager.setSecondFloor(null);
            }
        }
        if (textureView != null) {
            textureView.onDestroy();
            textureView = null;
        }
        if (mPlayerView1 != null) {
            mPlayerView1.removeAllViews();
            mPlayerView1 = null;
        }
        TXMediaManager = null;
        mPlayConfig = null;
    }


    public int getCheckCode(final String playUrl) {//检测是否可用地址 可用返回类型  不可用返回-1

        if (TextUtils.isEmpty(playUrl) || (!playUrl.startsWith("http://") && !playUrl.startsWith("https://") && !playUrl.startsWith("rtmp://") && !playUrl.startsWith("/"))) {
            return -1;
        }
        if (mActivityType == ACTIVITY_TYPE_LIVE_PLAY) {
            if (playUrl.startsWith("rtmp://")) {
                return PLAY_TYPE_LIVE_RTMP;
            } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".flv")) {
                return TXLivePlayer.PLAY_TYPE_LIVE_FLV;
            } else if (playUrl.contains(".m3u8")) {
                return TXLivePlayer.PLAY_TYPE_VOD_HLS;
            } else {
                return -1;
            }
        } else if (mActivityType == ACTIVITY_TYPE_VOD_PLAY) {
            if (playUrl.startsWith("http://") || playUrl.startsWith("https://")) {
                if (playUrl.contains(".flv")) {
                    return TXLivePlayer.PLAY_TYPE_VOD_FLV;
                } else if (playUrl.contains(".m3u8")) {
                    return TXLivePlayer.PLAY_TYPE_VOD_HLS;
                } else if (playUrl.toLowerCase().contains(".mp4")) {
                    return PLAY_TYPE_VOD_MP4;
                } else {
                    return -1;
                }
            } else if (playUrl.startsWith("/")) {
                if (playUrl.contains(".mp4") || playUrl.contains(".flv")) {
                    return TXLivePlayer.PLAY_TYPE_LOCAL_VIDEO;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /*
    *创建回调接口 移出屏幕监听
    */
    public static interface OnMoveOutBackListener {
        public void OnClickButton(View v);
    }

    private OnMoveOutBackListener callBackListener;            //声明接口对象

    public void setCallBackListener(OnMoveOutBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public void prepare() {
        releaseMediaPlayer();
        Message msg = new Message();
        msg.what = HANDLER_PREPARE;
        mMediaHandler.sendMessage(msg);
        currentVideoWidth = 0;
        currentVideoHeight = 0;

        if (urlType == -1) {
            return;
        }
        mLivePlayer.setPlayerView(textureView);
        mLivePlayer.setAutoPlay(true);
        // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        result = mLivePlayer.startPlay(CURRENT_PLAYING_URL, urlType);
        if (0 != result) {
            ToastUtils.showToast(ctx, "播放地址出错~~");
        }
    }

    public void releaseMediaPlayer() {
    }

}
