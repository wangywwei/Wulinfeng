package com.lecloud.skin.videoview.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.lecloud.sdk.constant.PlayerEvent;
import com.lecloud.sdk.constant.PlayerParams;
import com.lecloud.sdk.constant.StatusCode;
import com.lecloud.sdk.player.IMediaDataPlayer;
import com.lecloud.sdk.player.IPlayer;
import com.lecloud.sdk.utils.NetworkUtils;
import com.lecloud.sdk.videoview.mobile.MobileLiveVideoView;
import com.lecloud.skin.controler.LetvPlayerWidgetShowListener;
import com.lecloud.skin.ui.ILetvLiveUICon;
import com.lecloud.skin.ui.ILetvVodUICon;
import com.lecloud.skin.ui.LetvLiveUIListener;
import com.lecloud.skin.ui.impl.LetvLiveUICon;
import com.lecloud.skin.ui.utils.ScreenUtils;
import com.lecloud.skin.ui.view.VideoNoticeView;

/**
 * Created by gaolinhua on 16/7/27.
 */
public class UIMobileLiveVideoView extends MobileLiveVideoView implements
        LetvPlayerWidgetShowListener{
    protected ILetvLiveUICon letvLiveUICon;
    protected int width = -1;
    protected int height = -1;
    private boolean isSeeking = false;

    public UIMobileLiveVideoView(Context context,String customId) {
        super(context,customId);
        initUICon(context);
    }

    private void initUICon(final Context context) {
        letvLiveUICon = new LetvLiveUICon(context);
        letvLiveUICon.setSeekable(false);
        letvLiveUICon.setGravitySensor(false);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(letvLiveUICon.getView(), params);
        letvLiveUICon.setRePlayListener(new VideoNoticeView.IReplayListener() {

            @Override
            public Bundle getReportParams() {
                return ((IMediaDataPlayer) player).getReportParams();
            }

            @Override
            public void onRePlay() {
                player.retry();
            }
        });
        letvLiveUICon.setLetvLiveUIListener(new LetvLiveUIListener() {


            @Override
            public void setRequestedOrientation(int requestedOrientation) {
                if (context instanceof Activity) {
                    ((Activity) context).setRequestedOrientation(requestedOrientation);
                }
            }

            @Override
            public void resetPlay() {
                // LeLog.dPrint(TAG, "--------resetPlay");
            }

            @Override
            public void onSetDefination(int type) {
//            	((IMediaDataPlayer) player).setDataSourceByRate(medialists.get(type).getVtype());
            }

            @Override
            public void onSeekTo(float sec) {
//                long msec = (long) (sec * player.getDuration());
                if (player != null) {
                    player.seekTo((long) sec);
                }

            }

            @Override
            public void onClickPlay() {
                if(!NetworkUtils.hasConnect(context)){
                    Toast.makeText(context,"没有网络,不能播放..",Toast.LENGTH_SHORT).show();
                    return;
                }
                letvLiveUICon.setPlayState(!player.isPlaying());
                if (player.isPlaying()) {
                    releaseAudioFocus();
                    player.stop();
                    player.reset();
                    player.release();
                    letvLiveUICon.showController(false);
                    enablePanoGesture(false);
                } else {
                    player.retry();
                    letvLiveUICon.showController(true);
                    enablePanoGesture(false);
                }
            }

            @Override
            public void onUIEvent(int event, Bundle bundle) {
                // TODO Auto-generated method stub

            }

            @Override
            public int onSwitchPanoControllMode(int controllMode) {
                return switchControllMode(controllMode);
            }

            @Override
            public int onSwitchPanoDisplayMode(int displayMode) {
                return switchDisplayMode(displayMode);
            }

            @Override
            public void onProgressChanged(int progress) {
                // TODO Auto-generated method stub

            }

            @Override
            public void isOpenDanmu(boolean isOpen) {

            }

            @Override
            public void isOpenHudong(boolean isOpen) {

            }

            @Override
            public void onStartSeek() {
                // TODO Auto-generated method stub
                isSeeking = true;
            }

            @Override
            public void onEndSeek() {
                isSeeking  =false;
            }

        });
    }
    protected int switchControllMode(int interactiveMode) {
        return 0;
    }

    protected int switchDisplayMode(int displayMode) {
        return 0;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (ScreenUtils.getOrientation(getContext()) == Configuration.ORIENTATION_PORTRAIT) {
            letvLiveUICon.setRequestedOrientation(ILetvVodUICon.SCREEN_PORTRAIT, this);
        } else {
            letvLiveUICon.setRequestedOrientation(ILetvVodUICon.SCREEN_LANDSCAPE,this);
        }
        super.onConfigurationChanged(newConfig);
    }


    @Override
    protected void notifyPlayerEvent(int state, Bundle bundle) {
        super.notifyPlayerEvent(state, bundle);
        letvLiveUICon.processPlayerState(state,bundle);
        if(letvLiveUICon.showErrorTip()){
            letvLiveUICon.setPlayState(false);
        }
        switch (state) {
            case PlayerEvent.PLAY_PREPARED:
                if (NetworkUtils.hasConnect(context) && !letvLiveUICon.isShowLoading()) {
                    letvLiveUICon.showLoadingProgress();
                }
                break;
            case PlayerEvent.PLAY_ERROR: {
                int code = bundle.getInt(PlayerParams.KEY_RESULT_STATUS_CODE);
                letvLiveUICon.hideLoading();
                break;
            }
            case PlayerEvent.PLAY_COMPLETION:
                letvLiveUICon.setPlayState(false);
                break;
            case PlayerEvent.PLAY_INFO:
                int code = bundle.getInt(PlayerParams.KEY_RESULT_STATUS_CODE);
                switch (code) {
                    case StatusCode.PLAY_INFO_BUFFERING_START:// 500004
                        if (NetworkUtils.hasConnect(context) && !letvLiveUICon.isShowLoading()) {
                            letvLiveUICon.showLoadingProgress();
                        }
                        break;
                    case StatusCode.PLAY_INFO_BUFFERING_END:// 500005
                        letvLiveUICon.setPlayState(true);
                        letvLiveUICon.hideLoading();
                        break;
                    case StatusCode.PLAY_INFO_VIDEO_RENDERING_START:// 500006
                        letvLiveUICon.setPlayState(true);
                        letvLiveUICon.hideLoading();
                        break;
                    default:
                        break;
                }
                break;
            case PlayerEvent.PLAY_SEEK_COMPLETE: {//209
                isSeeking = false;
                break;
            }
            default:
                break;
        }
    }

    protected void enablePanoGesture(boolean enable) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isComplete() {
        //TODO
        return player != null && player.getStatus() == IPlayer.PLAYER_STATUS_EOS;
    }

    @Override
    public void onResume() {
        //横屏设置ILetvVodUICon.SCREEN_LANDSCAPE,竖屏设置ILetvVodUICon.SCREEN_PORTRAIT
        if(isFirstPlay){
            letvLiveUICon.setRequestedOrientation(ILetvVodUICon.SCREEN_PORTRAIT, this);
            isFirstPlay = false;
        }else{
            super.onResume();
        }
        letvLiveUICon.showController(true);
        enablePanoGesture(true);
    }

    @Override
    public void liveInteractionWidgetShow() {

    }

    @Override
    public void liveInteractionWidgetHide() {

    }

    @Override
    public void danMukuWidgetShow() {

    }

    @Override
    public void danMukuWidgetHide() {

    }

    @Override
    public void danMuEditWidgetShow() {

    }

    @Override
    public void danMuEditWidgetHide() {

    }

    @Override
    public void vedioShareWidgetShow() {

    }

    @Override
    public void vedioShareWidgetHide() {

    }

    public void setTitle(String title){
        letvLiveUICon.setTitle(title);
    }
}
