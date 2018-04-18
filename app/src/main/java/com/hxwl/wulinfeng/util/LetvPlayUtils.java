package com.hxwl.wulinfeng.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lecloud.sdk.constant.PlayerEvent;
import com.lecloud.sdk.constant.PlayerParams;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.lecloud.sdk.videoview.VideoViewListener;
import com.lecloud.skin.videoview.vod.UIVodVideoView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 功能:乐视播放的工具类
 * 作者：zjn on 2017/3/16 14:31
 */

public class LetvPlayUtils {

    private IMediaDataVideoView videoView;
    private LetvPlayUtils(){

    }

    private static volatile LetvPlayUtils instance;

    public static LetvPlayUtils getInstance(){
        if(instance == null){
            synchronized (LetvPlayUtils.class){
                if(instance == null){
                    instance = new LetvPlayUtils();
                }
            }
        }
        return instance;
    }


    /**
     * 乐视点播
     */
    public void letvVodPlay(HashMap<String, String> hashMap, RelativeLayout videoContainer,Activity ctx) {
        videoContainer.removeAllViews();
        videoView = new UIVodVideoView(ctx);
        videoView.setVideoViewListener(videoViewListener);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.width = width;
        int height = width * 9 / 16;
        params.height = height;
        videoContainer.addView((View) videoView, params);
        if (videoView != null) {
            videoView.resetPlayer();
            try {
                Bundle mBundle = new Bundle();
                mBundle.putInt(PlayerParams.KEY_PLAY_MODE, PlayerParams.VALUE_PLAYER_VOD);
                mBundle.putString(PlayerParams.KEY_PLAY_UUID, hashMap.get("uu"));
                mBundle.putString(PlayerParams.KEY_PLAY_VUID, hashMap.get("vu"));
                mBundle.putString(
                        PlayerParams.KEY_PLAY_PU, hashMap.get("pu"));
                videoView.setDataSource(mBundle);
                videoView.onResume();
            } catch (Exception e) {

            }
        }
    }
    /**
     * 乐视点播
     */
    public IMediaDataVideoView letvVodPlayAndView(HashMap<String, String> hashMap, RelativeLayout videoContainer, Activity ctx) {
        videoContainer.removeAllViews();
        if(videoView != null){
            videoView.stopAndRelease();
            videoView.onDestroy();
            videoView = null;
        }
        videoView = new UIVodVideoView(ctx);
        videoView.setVideoViewListener(videoViewListener);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.width = width;
        int height = width * 9 / 16;
        params.height = height;
        videoContainer.addView((View) videoView, params);
        if (videoView != null) {
            videoView.resetPlayer();
            try {
                Bundle mBundle = new Bundle();
                mBundle.putInt(PlayerParams.KEY_PLAY_MODE, PlayerParams.VALUE_PLAYER_VOD);
                mBundle.putString(PlayerParams.KEY_PLAY_UUID, hashMap.get("uu"));
                mBundle.putString(PlayerParams.KEY_PLAY_VUID, hashMap.get("vu"));
                mBundle.putString(
                        PlayerParams.KEY_PLAY_PU, hashMap.get("pu"));
                videoView.setDataSource(mBundle);
                videoView.onResume();
            } catch (Exception e) {

            }
        }
        return videoView;
    }

    public void letvVodPlay(String url,RelativeLayout videoContainer,Activity ctx) {
        videoContainer.removeAllViews();
        videoView = new UIVodVideoView(ctx);
        videoView.setVideoViewListener(videoViewListener);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        params.width = width;
        int height = width * 9 / 16;
        params.height = height;
        videoContainer.addView((View) videoView, params);
        if (videoView != null) {
            videoView.resetPlayer();
            try {
                videoView.setDataSource(url);
                videoView.onResume();
            } catch (Exception e) {

            }
        }
    }

    VideoViewListener videoViewListener = new VideoViewListener() {
        @Override
        public void onStateResult(int event, Bundle bundle) {
            handlePlayerEvent(event, bundle);// 处理播放器事件
            handlePlayerEvent(event, bundle);// 处理播放器事件
            handleLiveEvent(event, bundle);// 处理直播类事件,如果是点播，则这些事件不会回调
        }

        @Override
        public String onGetVideoRateList(LinkedHashMap<String, String> linkedHashMap) {
            for (Map.Entry<String, String> rates : linkedHashMap.entrySet()) {
                if (rates.getValue().equals("高清")) {
                    return rates.getKey();
                }
            }
            return "";
        }
    };

    /**
     * 处理播放器本身事件，具体事件可以参见IPlayer类
     */
    private void handlePlayerEvent(int state, Bundle bundle) {
        switch (state) {
            case PlayerEvent.PLAY_VIDEOSIZE_CHANGED:
                /**
                 * 获取到视频的宽高的时候，此时可以通过视频的宽高计算出比例，进而设置视频view的显示大小。
                 * 如果不按照视频的比例进行显示的话，(以surfaceView为例子)内容会填充整个surfaceView。
                 * 意味着你的surfaceView显示的内容有可能是拉伸的
                 */
                break;

            case PlayerEvent.PLAY_PREPARED:
                // 播放器准备完成，此刻调用start()就可以进行播放了
                if (videoView != null) {
                    videoView.onStart();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 处理直播类事件
     */
    private void handleLiveEvent(int state, Bundle bundle) {
    }
}
