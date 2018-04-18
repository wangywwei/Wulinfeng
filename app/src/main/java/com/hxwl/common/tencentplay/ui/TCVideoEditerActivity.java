package com.hxwl.common.tencentplay.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxwl.common.tencentplay.choose.TCVideoFileInfo;
import com.hxwl.common.tencentplay.editor.EditPannel;
import com.hxwl.common.tencentplay.editor.TCVideoEditView;
import com.hxwl.common.tencentplay.utils.TCConstants;
import com.hxwl.common.tencentplay.utils.TCUtils;
import com.hxwl.common.tencentplay.widget.VideoWorkProgressFragment;
import com.hxwl.newwlf.wulin.ViewFabuActivity;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.VideoShareActivity;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;
import com.tencent.ugc.TXVideoInfoReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UGC短视频裁剪
 */
public class TCVideoEditerActivity extends BaseActivity implements View.OnClickListener, TCVideoEditView.IOnRangeChangeListener, EditPannel.IOnEditCmdListener,
        TXVideoEditer.TXVideoGenerateListener, TXVideoInfoReader.OnSampleProgrocess, TXVideoEditer.TXVideoPreviewListener {

    private static final String TAG = TCVideoEditerActivity.class.getSimpleName();
    private final int STATE_NONE = 0;
    private final int STATE_RESUME = 1;
    private final int STATE_PAUSE = 2;
    private final int STATE_CUT = 3;

    private final int OP_PLAY = 0;
    private final int OP_PAUSE = 1;
    private final int OP_SEEK = 2;
    private final int OP_CUT = 3;
    private final int OP_CANCEL = 4;
    private int mCurrentState = STATE_NONE;

    private TextView mTvDone;
    private TextView mTvCurrent;
    private TextView mTvDuration;
    private ImageButton mBtnPlay;
    private FrameLayout mVideoView;
    private LinearLayout mLayoutEditer;
    private RelativeLayout rl_back ;
    private EditPannel mEditPannel;

    private TXVideoEditer mTXVideoEditer;
    private TCVideoFileInfo mTCVideoFileInfo;
    private TXVideoInfoReader mTXVideoInfoReader;

    private String mVideoOutputPath;
    private BackGroundHandler mHandler;
    private final int MSG_LOAD_VIDEO_INFO = 1000;
    private final int MSG_RET_VIDEO_INFO = 1001;
    private ProgressBar mLoadProgress;
    private TXVideoEditConstants.TXGenerateResult mResult;
    private int mCutVideoDuration;//裁剪的视频时长

    private VideoWorkProgressFragment mWorkProgressDialog;
    private Bitmap mWaterMarkLogo;

    private boolean mIsStopManually;//标记是否手动停止


    class BackGroundHandler extends Handler {

        public BackGroundHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_VIDEO_INFO:
                    TXVideoEditConstants.TXVideoInfo videoInfo = mTXVideoInfoReader.getVideoFileInfo(mTCVideoFileInfo.getFilePath());
                    if (videoInfo == null) {
                        mLoadProgress.setVisibility(View.GONE);

                        AlertDialog.Builder normalDialog = new AlertDialog.Builder(TCVideoEditerActivity.this, R.style.ConfirmDialogStyle);
                        normalDialog.setMessage("暂不支持Android 4.3以下的系统");
                        normalDialog.setCancelable(false);
                        normalDialog.setPositiveButton("知道了", null);
                        normalDialog.show();
                        return;
                    }
                    Message mainMsg = new Message();
                    mainMsg.what = MSG_RET_VIDEO_INFO;
                    mainMsg.obj = videoInfo;
                    mMainHandler.sendMessage(mainMsg);
                    break;
            }

        }
    }

    private TXVideoEditConstants.TXVideoInfo mTXVideoInfo;
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RET_VIDEO_INFO:
                    mTXVideoInfo = (TXVideoEditConstants.TXVideoInfo) msg.obj;

                    TXVideoEditConstants.TXPreviewParam param = new TXVideoEditConstants.TXPreviewParam();
                    param.videoView = mVideoView;
                    param.renderMode = TXVideoEditConstants.PREVIEW_RENDER_MODE_FILL_EDGE;
                    int ret = mTXVideoEditer.setVideoPath(mTCVideoFileInfo.getFilePath());
                    mTXVideoEditer.initWithPreview(param);
                    if (ret < 0) {
                        AlertDialog.Builder normalDialog = new AlertDialog.Builder(TCVideoEditerActivity.this, R.style.ConfirmDialogStyle);
                        normalDialog.setMessage("本机型暂不支持此视频格式");
                        normalDialog.setCancelable(false);
                        normalDialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        normalDialog.show();
                        return;
                    }

                    handleOp(OP_SEEK, 0, (int) mTXVideoInfo.duration);
                    mLoadProgress.setVisibility(View.GONE);
                    mTvDone.setClickable(true);
                    mBtnPlay.setClickable(true);

                    mEditPannel.setMediaFileInfo(mTXVideoInfo);
                    String duration = TCUtils.duration(mTXVideoInfo.duration);
                    String position = TCUtils.duration(0);

                    mTvCurrent.setText(position);
                    mTvDuration.setText(duration);
                    break;
            }
        }
    };
    private HandlerThread mHandlerThread;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.activity_video_editer);
        AppUtils.setTitle(TCVideoEditerActivity.this);
        initViews();
        initData();
    }

    @Override
    protected void onDestroy() {
        TelephonyManager tm = (TelephonyManager) this.getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(listener, PhoneStateListener.LISTEN_NONE);

        mHandlerThread.quit();
        handleOp(OP_CANCEL, 0, 0);

        mTXVideoInfoReader.cancel();
        mTXVideoEditer.setTXVideoPreviewListener(null);
        mTXVideoEditer.setVideoGenerateListener(null);
        super.onDestroy();
    }

    private void initViews() {

        mEditPannel = (EditPannel) findViewById(R.id.edit_pannel);
        mEditPannel.setRangeChangeListener(this);
        mEditPannel.setEditCmdListener(this);


        mTvCurrent = (TextView) findViewById(R.id.tv_current);
        mTvDuration = (TextView) findViewById(R.id.tv_duration);

        mVideoView = (FrameLayout) findViewById(R.id.video_view);

        mBtnPlay = (ImageButton) findViewById(R.id.btn_play);
        mBtnPlay.setOnClickListener(this);
        mBtnPlay.setClickable(false);

        mTvDone = (TextView) findViewById(R.id.btn_done);
        mTvDone.setOnClickListener(this);
        mTvDone.setClickable(false);

        rl_back = (RelativeLayout) findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);

        mLayoutEditer = (LinearLayout) findViewById(R.id.layout_editer);
        mLayoutEditer.setEnabled(true);


        mLoadProgress = (ProgressBar) findViewById(R.id.progress_load);
        initWorkProgressPopWin();
    }

    private void initWorkProgressPopWin() {
        if (mWorkProgressDialog == null) {
            mWorkProgressDialog = new VideoWorkProgressFragment();
            mWorkProgressDialog.setOnClickStopListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTvDone.setClickable(true);
                    mTvDone.setEnabled(true);
                    mWorkProgressDialog.dismiss();
                    Toast.makeText(TCVideoEditerActivity.this, "取消视频生成", Toast.LENGTH_SHORT).show();
                    mWorkProgressDialog.setProgress(0);
                    mCurrentState = STATE_NONE;
                    if (mTXVideoEditer != null) {
                        mTXVideoEditer.cancel();
                    }
                }
            });
        }
        mWorkProgressDialog.setProgress(0);
    }

    private synchronized boolean handleOp(int state, int startPlayTime, int endPlayTime) {
        switch (state) {
            case OP_PLAY:
                if (mCurrentState == STATE_NONE) {
                    mTXVideoEditer.startPlayFromTime(startPlayTime, endPlayTime);
                    mCurrentState = STATE_RESUME;
                    return true;
                } else if (mCurrentState == STATE_PAUSE) {
                    mTXVideoEditer.resumePlay();
                    mCurrentState = STATE_RESUME;
                    return true;
                }
                break;
            case OP_PAUSE:
                if (mCurrentState == STATE_RESUME) {
                    mTXVideoEditer.pausePlay();
                    mCurrentState = STATE_PAUSE;
                    return true;
                }
                break;
            case OP_SEEK:
                if (mCurrentState == STATE_CUT) {
                    return false;
                }
                if (mCurrentState == STATE_RESUME || mCurrentState == STATE_PAUSE) {
                    mTXVideoEditer.stopPlay();
                }
                mTXVideoEditer.startPlayFromTime(startPlayTime, endPlayTime);
                mCurrentState = STATE_RESUME;
                return true;
            case OP_CUT:
                if (mCurrentState == STATE_RESUME || mCurrentState == STATE_PAUSE) {
                    mTXVideoEditer.stopPlay();
                }
                startTranscode();
                mCurrentState = STATE_CUT;
                return true;
            case OP_CANCEL:
                if (mCurrentState == STATE_RESUME || mCurrentState == STATE_PAUSE) {
                    mTXVideoEditer.stopPlay();
                } else if (mCurrentState == STATE_CUT) {
                    mTXVideoEditer.cancel();
                }
                mCurrentState = STATE_NONE;
                return true;
        }
        return false;
    }

    private void initData() {
        mHandlerThread = new HandlerThread("LoadData");
        mHandlerThread.start();
        mHandler = new BackGroundHandler(mHandlerThread.getLooper());

        mTCVideoFileInfo = (TCVideoFileInfo) getIntent().getSerializableExtra(TCConstants.INTENT_KEY_SINGLE_CHOOSE);
        mTXVideoInfoReader = TXVideoInfoReader.getInstance();
        mTXVideoEditer = new TXVideoEditer(this);
        mTXVideoEditer.setTXVideoPreviewListener(this);

        //加载视频基本信息
        mHandler.sendEmptyMessage(MSG_LOAD_VIDEO_INFO);

        TelephonyManager tm = (TelephonyManager) this.getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        //加载缩略图
        mTXVideoInfoReader.getSampleImages(TCConstants.THUMB_COUNT, mTCVideoFileInfo.getFilePath(), this);

        mWaterMarkLogo = BitmapFactory.decodeResource(getResources(), R.drawable.wulinnull_icon);
    }

    private void createThumbFile() {
        AsyncTask<Void, String, String> task = new AsyncTask<Void, String, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                File outputVideo = new File(mVideoOutputPath);
                if (outputVideo == null || !outputVideo.exists())
                    return null;
                Bitmap bitmap = mTXVideoInfoReader.getSampleImage(0, mVideoOutputPath);
                if (bitmap == null)
                    return null;
                String mediaFileName = outputVideo.getAbsolutePath();
                if (mediaFileName.lastIndexOf(".") != -1) {
                    mediaFileName = mediaFileName.substring(0, mediaFileName.lastIndexOf("."));
                }
                String folder = Environment.getExternalStorageDirectory() + File.separator + TCConstants.DEFAULT_MEDIA_PACK_FOLDER + File.separator + mediaFileName;
                File appDir = new File(folder);
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }

                String fileName = "thumbnail" + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (mTCVideoFileInfo.getThumbPath() == null) {
                    mTCVideoFileInfo.setThumbPath(file.getAbsolutePath());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                //发布页面
//                startPreviewActivity(mResult);
                goFaBuVideo();
            }
        };
        task.execute();
    }

    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        if (mCurrentState == STATE_PAUSE && !mIsStopManually) {
            handleOp(OP_PLAY, mEditPannel.getSegmentFrom(), mEditPannel.getSegmentTo());
            mBtnPlay.setImageResource(mCurrentState == STATE_RESUME ? R.drawable.ic_pause : R.drawable.ic_play);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
        if (mCurrentState == STATE_NONE) {//说明是取消合成之后
            handleOp(OP_SEEK, 0, (int) mTXVideoInfo.duration);
            mBtnPlay.setImageResource(R.drawable.ic_pause);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
        if (mCurrentState == STATE_CUT) {
            handleOp(OP_CANCEL, 0, 0);
            if (mWorkProgressDialog != null && mWorkProgressDialog.isAdded()) {
                mWorkProgressDialog.dismiss();
            }
        } else {
            mIsStopManually = false;
            handleOp(OP_PAUSE, 0, 0);
            mBtnPlay.setImageResource(mCurrentState == STATE_RESUME ? R.drawable.ic_pause : R.drawable.ic_play);
        }
        mTvDone.setClickable(true);
        mTvDone.setEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                doTranscode();
//                mTXVideoEditer.quickGenerateVideo("/sdcard/7.mp4");
                break;
            case R.id.rl_back:
                mTXVideoInfoReader.cancel();
                handleOp(OP_CANCEL, 0, 0);
                mTXVideoEditer.setTXVideoPreviewListener(null);
                mTXVideoEditer.setVideoGenerateListener(null);
                finish();
                break;
            case R.id.btn_play:
                mIsStopManually = !mIsStopManually;
                playVideo();
                break;
        }
    }

    private void playVideo() {
        if (mCurrentState == STATE_RESUME) {
            handleOp(OP_PAUSE, 0, 0);
        } else {
            handleOp(OP_PLAY, mEditPannel.getSegmentFrom(), mEditPannel.getSegmentTo());
        }
        mBtnPlay.setImageResource(mCurrentState == STATE_RESUME ? R.drawable.ic_pause : R.drawable.ic_play);
    }

    private void doTranscode() {
        mTvDone.setEnabled(false);
        mTvDone.setClickable(false);

        mTXVideoInfoReader.cancel();
        mLayoutEditer.setEnabled(false);
        handleOp(OP_CUT, 0, 0);
    }

    private void startTranscode() {
        mBtnPlay.setImageResource(R.drawable.ic_play);
        mCutVideoDuration = mEditPannel.getSegmentTo() - mEditPannel.getSegmentFrom();
        mWorkProgressDialog.setProgress(0);
        mWorkProgressDialog.setCancelable(false);
        mWorkProgressDialog.show(getFragmentManager(), "progress_dialog");
        try {
            mTXVideoEditer.setCutFromTime(mEditPannel.getSegmentFrom(), mEditPannel.getSegmentTo());

            String outputPath = Environment.getExternalStorageDirectory() + File.separator + TCConstants.DEFAULT_MEDIA_PACK_FOLDER;
            File outputFolder = new File(outputPath);

            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }
            String current = String.valueOf(System.currentTimeMillis() / 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String time = sdf.format(new Date(Long.valueOf(current + "000")));
            String saveFileName = String.format("TXVideo_%s.mp4", time);
            mVideoOutputPath = outputFolder + "/" + saveFileName;
            mTXVideoEditer.setVideoGenerateListener(this);
            mTXVideoEditer.generateVideo(TXVideoEditConstants.VIDEO_COMPRESSED_720P, mVideoOutputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGenerateProgress(final float progress) {
        final int prog = (int) (progress * 100);
        mWorkProgressDialog.setProgress(prog);
    }

    @Override
    public void onGenerateComplete(TXVideoEditConstants.TXGenerateResult result) {
        if (result.retCode == TXVideoEditConstants.GENERATE_RESULT_OK) {
            if (mTXVideoInfo != null) {
                mResult = result;
                createThumbFile();
            }
        } else {
            final TXVideoEditConstants.TXGenerateResult ret = result;
            Toast.makeText(TCVideoEditerActivity.this, ret.descMsg, Toast.LENGTH_SHORT).show();
            mTvDone.setEnabled(true);
            mTvDone.setClickable(true);
        }
        mCurrentState = STATE_NONE;
    }

    private void startPreviewActivity(TXVideoEditConstants.TXGenerateResult result) {
        Intent intent = new Intent(getApplicationContext(), TCVideoPreviewActivity.class);
        intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_PLAY);
        intent.putExtra(TCConstants.VIDEO_RECORD_RESULT, result.retCode);
        intent.putExtra(TCConstants.VIDEO_RECORD_DESCMSG, result.descMsg);
        intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, mVideoOutputPath);
        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mTCVideoFileInfo.getThumbPath());
        intent.putExtra(TCConstants.VIDEO_RECORD_DURATION, mCutVideoDuration);
        startActivity(intent);
        finish();
    }

    //去发布视频
    private void goFaBuVideo() {
        Intent intent = new Intent(getApplicationContext(), ViewFabuActivity.class);
        intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, mVideoOutputPath);
        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mTCVideoFileInfo.getThumbPath());
        startActivity(intent);
        finish();
    }

    @Override
    public void sampleProcess(int number, Bitmap bitmap) {
        int num = number;
        Bitmap bmp = bitmap;
        mEditPannel.addBitmap(num, bmp);
        TXCLog.d(TAG, "number = " + number + ",bmp = " + bitmap);
    }


    @Override
    public void onPreviewProgress(final int time) {
//        Log.d(TAG, "onPreviewProgress time : " + time);
        if (mTvCurrent != null) {
            mTvCurrent.setText(TCUtils.duration(time / 1000));
        }
    }

    @Override
    public void onPreviewFinished() {
        TXCLog.d(TAG, "---------------onPreviewFinished-----------------");
        handleOp(OP_SEEK, mEditPannel.getSegmentFrom(), mEditPannel.getSegmentTo());
    }

    @Override
    public void onKeyDown() {
        mBtnPlay.setImageResource(R.drawable.ic_play);
        TXCLog.d(TAG, "onKeyDown");
        handleOp(OP_PAUSE, 0, 0);
    }

    @Override
    public void onKeyUp(int startTime, int endTime) {
        mBtnPlay.setImageResource(R.drawable.ic_pause);
        TXCLog.d(TAG, "onKeyUp");
        handleOp(OP_SEEK, mEditPannel.getSegmentFrom(), mEditPannel.getSegmentTo());
    }

    @Override
    public void onCmd(int cmd, EditPannel.EditParams params) {
        switch (cmd) {
            case EditPannel.CMD_SPEED:
//                mTXVideoEditer.setSpeed(params.mSpeedRate);
                break;
            case EditPannel.CMD_FILTER:
//                TXVideoEditConstants.TXRect rect = new TXVideoEditConstants.TXRect();
//                rect.x = 0.5f;
//                rect.y = 0.5f;
//                rect.width = 0.25f;
//                mTXVideoEditer.setWaterMark(mWaterMarkLogo, rect);
                mTXVideoEditer.setFilter(params.mFilterBmp);
                break;
        }
    }

    PhoneStateListener listener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:  //电话等待接听
                case TelephonyManager.CALL_STATE_OFFHOOK:  //电话接听
                    if (mCurrentState == STATE_CUT) {
                        handleOp(OP_CANCEL, 0, 0);
                        if (mWorkProgressDialog != null && mWorkProgressDialog.isAdded()) {
                            mWorkProgressDialog.dismiss();
                        }
                        mBtnPlay.setImageResource(R.drawable.ic_pause);
                    } else {
                        handleOp(OP_PAUSE, 0, 0);
                        mBtnPlay.setImageResource(mCurrentState == STATE_RESUME ? R.drawable.ic_pause : R.drawable.ic_play);
                    }
                    mTvDone.setClickable(true);
                    mTvDone.setEnabled(true);
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (mTXVideoEditer != null)
                        handleOp(OP_PLAY, mEditPannel.getSegmentFrom(), mEditPannel.getSegmentTo());
                    break;
            }
        }
    };
}
