package com.hxwl.common.tencentplay.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxwl.common.tencentplay.choose.TCVideoEditerMgr;
import com.hxwl.common.tencentplay.choose.TCVideoFileInfo;
import com.hxwl.common.tencentplay.utils.TCConstants;
import com.hxwl.common.tencentplay.widget.BeautySettingPannel;
import com.hxwl.common.tencentplay.widget.TCAudioControl;
import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXUGCRecord;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * UGC小视频录制界面
 */
public class TCVideoRecordActivity extends BaseActivity implements View.OnClickListener, BeautySettingPannel.IOnBeautyParamsChangeListener
        , TXRecordCommon.ITXVideoRecordListener{

    private static final String TAG = "TCVideoRecordActivity";
    private WeakReference<Activity> mReference = new WeakReference(TCVideoRecordActivity.this);
    private boolean mRecording = false;
    private boolean mStartPreview = false;
    private boolean mFront = false;
    private long mStartRecordTimeStamp = 0;
    private TXUGCRecord mTXCameraRecord;
    private TXRecordCommon.TXRecordResult mTXRecordResult;

    private BeautySettingPannel.BeautyParams mBeautyParams = new BeautySettingPannel.BeautyParams();
    private TXCloudVideoView mVideoView;
    private ProgressBar mRecordProgress;
    private TextView mProgressTime;

    private BeautySettingPannel mBeautyPannelView;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusListener;
    private boolean mPause=false;
    private TCAudioControl mAudioCtrl;

    @Bind(R.id.rlyt_close)
    RelativeLayout rlyt_close;
    @Bind(R.id.flash_button)
    CheckedTextView flash_button;
    @Bind(R.id.switch_camera_button)
    CheckedTextView switch_camera_button;
    @Bind(R.id.beaut_button)
    CheckedTextView beaut_button;
    @Bind(R.id.next_done)
    CheckedTextView next_done;
    @Bind(R.id.record_delete)
    CheckedTextView record_delete;
    @Bind(R.id.record)
    ImageView btn_pause;
    @Bind(R.id.llyt_beauty_pannel)
    LinearLayout llyt_beauty_pannel;
    //闪光灯是否打开成功,默认失败
    private boolean isFlashSuccess = false;
    //视频时长（ms）
    private long mVideoDuration = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
        setContentView(R.layout.activity_video_record);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(mReference.get());
        initViews();
    }

    //真正的沉浸式状态栏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && Build.VERSION.SDK_INT >= 19){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    private void startCameraPreview() {
        if (mStartPreview) return;
        mStartPreview = true;

        TXRecordCommon.TXUGCSimpleConfig param = new TXRecordCommon.TXUGCSimpleConfig();
        param.videoQuality = TXRecordCommon.VIDEO_RESOLUTION_540_960;
        param.isFront = mFront;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            param.mHomeOriention = TXLiveConstants.VIDEO_ANGLE_HOME_DOWN;
        } else {
            param.mHomeOriention = TXLiveConstants.VIDEO_ANGLE_HOME_RIGHT;
        }
        mTXCameraRecord = TXUGCRecord.getInstance(this.getApplicationContext());
//        mTXCameraRecord.startCameraCustomPreview(param,mVideoView);
        mTXCameraRecord.startCameraSimplePreview(param, mVideoView);
        mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel);
        mTXCameraRecord.setFaceScaleLevel(mBeautyParams.mFaceSlimLevel);
        mTXCameraRecord.setEyeScaleLevel(mBeautyParams.mBigEyeLevel);
        mTXCameraRecord.setFilter(mBeautyParams.mFilterBmp);
        mTXCameraRecord.setGreenScreenFile(mBeautyParams.mGreenFile, true);
        mTXCameraRecord.setMotionTmpl(mBeautyParams.mMotionTmplPath);
//        mTXCameraRecord.setVideoProcessListener(this);
    }

    private void initViews() {
        mBeautyPannelView = (BeautySettingPannel) findViewById(R.id.beauty_pannel);
        mBeautyPannelView.setBeautyParamsChangeListener(this);
        mBeautyPannelView.setViewVisibility(R.id.exposure_ll, View.GONE);

        mAudioCtrl = (TCAudioControl) findViewById(R.id.layoutAudioControl);
        mVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
        mVideoView.enableHardwareDecode(true);

        mProgressTime = (TextView) findViewById(R.id.progress_time);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasPermission()) {
            startCameraPreview();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopCameraPreview();
            mStartPreview = false;
        }
        stopRecord(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopBGM();
            mTXCameraRecord.stopCameraPreview();
            mTXCameraRecord.setVideoRecordListener(null);
            mTXCameraRecord = null;
            mStartPreview = false;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mRecording) {
            mTXCameraRecord.stopCameraPreview();
            stopRecord(false);
            mStartPreview = false;
            startCameraPreview();
        } else {
            mTXCameraRecord.stopCameraPreview();
            mStartPreview = false;
            startCameraPreview();
        }
    }

    @OnClick({R.id.rlyt_close,R.id.flash_button,
            R.id.switch_camera_button,R.id.beaut_button,
            R.id.record,R.id.next_done,R.id.record_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlyt_close:
                if (mRecording && mTXCameraRecord != null) {
                    mTXCameraRecord.stopRecord();
                    mTXCameraRecord.setVideoRecordListener(null);
                }
                finish();
                break;
            case R.id.flash_button:
                if(!mFront){
                    if(!isFlashSuccess){
                        isFlashSuccess = true;
                        mTXCameraRecord.toggleTorch(true);
                        flash_button.setChecked(true);
                    }else{
                        isFlashSuccess = false;
                        mTXCameraRecord.toggleTorch(false);
                        flash_button.setChecked(false);
                    }
                }
                break;
            case R.id.beaut_button:
                llyt_beauty_pannel.setVisibility(llyt_beauty_pannel.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                beaut_button.setChecked(llyt_beauty_pannel.getVisibility() == View.VISIBLE);
                break;
            case R.id.switch_camera_button:
                mFront = !mFront;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.switchCamera(mFront);
                }
                switch_camera_button.setChecked(mFront);
                break;
            case R.id.record:

                switchRecord();
                break;
//            case R.id.btn_music_pannel:
//                mAudioCtrl.setVisibility(View.VISIBLE);
//                break;
            case R.id.next_done: //下一步
                stopRecord(true);
                break;
            case R.id.record_delete:
                Intent intent = new Intent(TCVideoRecordActivity.this,PictureSelectorActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                if (mBeautyPannelView.isShown()) {

                    llyt_beauty_pannel.setVisibility(View.GONE);
                }
                if (mAudioCtrl.isShown()) {
                    mAudioCtrl.setVisibility(View.GONE);
                }
                beaut_button.setChecked(llyt_beauty_pannel.getVisibility() == View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        next_done.setVisibility(View.GONE);
    }

    private void switchRecord() {
        if (mRecording) {
            if (mPause){
                resumeRecord();
            } else {
                pauseRecord();
            }
        } else {
            startRecord();
        }
    }

    private void resumeRecord() {
        ImageView liveRecord = (ImageView) findViewById(R.id.record);
        if (liveRecord != null) liveRecord.setBackgroundResource(R.drawable.dp_video_stop);
        mPause = false;
        if (mTXCameraRecord != null) {
            mTXCameraRecord.resumeRecord();
        }
        requestAudioFocus();
    }

    private void pauseRecord() {
        ImageView liveRecord = (ImageView) findViewById(R.id.record);
        if (liveRecord != null) liveRecord.setBackgroundResource(R.drawable.start_record);
        mPause = true;
        if (mTXCameraRecord != null) {
            mTXCameraRecord.pauseRecord();
        }
        abandonAudioFocus();
    }

    // 录制时间要大于5s
    private void stopRecord(boolean showToast) {
        if (System.currentTimeMillis() <= mStartRecordTimeStamp + 5 * 1000) {
            if (showToast) {
                showTooShortToast();
                return;
            } else {
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setVideoRecordListener(null);
                }
            }
        }
        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopBGM();
            mTXCameraRecord.stopRecord();
        }
        ImageView liveRecord = (ImageView) findViewById(R.id.record);
        if (liveRecord != null) liveRecord.setBackgroundResource(R.drawable.start_record);
        mRecording = false;

        if (mRecordProgress != null) {
            mRecordProgress.setProgress(0);
        }
        if (mProgressTime != null) {
            mProgressTime.setText(String.format(Locale.CHINA, "%s", "00:00"));
        }
        abandonAudioFocus();
    }

    private void showTooShortToast() {
        if (mRecordProgress != null) {
            int statusBarHeight = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }

            int[] position = new int[2];
            mRecordProgress.getLocationOnScreen(position);
            Toast toast = Toast.makeText(this, "至少录到这里", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP | Gravity.LEFT, position[0], position[1] - statusBarHeight - 110);
            toast.show();
        }
    }

    private void startRecord() {
        record_delete.setEnabled(false);
        if (mTXCameraRecord == null) {
            mTXCameraRecord = TXUGCRecord.getInstance(this.getApplicationContext());
        }
        mRecordProgress = (ProgressBar) findViewById(R.id.record_progress);
        mTXCameraRecord.setVideoRecordListener(this);
        int result = mTXCameraRecord.startRecord();
        if (result != 0) {
            Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "录制失败，错误码：" + result, Toast.LENGTH_SHORT).show();
            mTXCameraRecord.setVideoRecordListener(null);
            mTXCameraRecord.stopRecord();
            return;
        }

        mAudioCtrl.setPusher(mTXCameraRecord);
        mRecording = true;
        mPause = false;
        ImageView liveRecord = (ImageView) findViewById(R.id.record);
        if (liveRecord != null) liveRecord.setBackgroundResource(R.drawable.dp_video_stop);
        mStartRecordTimeStamp = System.currentTimeMillis();
        requestAudioFocus();
    }


    //开始编辑
    private void startEditor(){
        if (mTXRecordResult != null && mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK) {

            File file = new File(mTXRecordResult.videoPath);
            if (file.exists()) {
                try {
                    File newFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath() + File.separator + file.getName());
                    if (!newFile.exists()) {
                        newFile = new File(getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + File.separator + file.getName());
                    }
                    file.renameTo(newFile);
                    String mVideoPath = newFile.getName();
                    ContentValues values = initCommonContentValues(newFile);
                    values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, System.currentTimeMillis());
                    values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
                    values.put(MediaStore.Video.VideoColumns.DURATION, mVideoDuration);//时长
                    this.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
                    insertVideoThumb(newFile.getPath(), mTXRecordResult.coverPath);
                    Intent intent = new Intent(this, TCVideoEditerActivity.class);
                    if (!newFile.exists()) {
                        showErrorDialog("选择的文件不存在");
                        return;
                    }
                    TCVideoFileInfo fileInfo = TCVideoEditerMgr.getInstance(TCVideoRecordActivity.this).getVideo(mVideoPath).get(0);
                    if(fileInfo != null){
                        intent.putExtra(TCConstants.INTENT_KEY_SINGLE_CHOOSE, fileInfo);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showErrorDialog(String msg) {
        android.app.AlertDialog.Builder normalDialog = new android.app.AlertDialog.Builder(TCVideoRecordActivity.this, R.style.ConfirmDialogStyle);
        normalDialog.setMessage(msg);
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("知道了", null);
        normalDialog.show();
    }

    private static ContentValues initCommonContentValues(File saveFile) {
        ContentValues values = new ContentValues();
        long currentTimeInSeconds = System.currentTimeMillis();
        values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATE_ADDED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATA, saveFile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.SIZE, saveFile.length());

        return values;
    }

    /**
     * 插入视频缩略图
     *
     * @param videoPath
     * @param coverPath
     */
    private void insertVideoThumb(String videoPath, String coverPath) {
        //以下是查询上面插入的数据库Video的id（用于绑定缩略图）
        //根据路径查询
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Thumbnails._ID},//返回id列表
                String.format("%s = ?", MediaStore.Video.Thumbnails.DATA), //根据路径查询数据库
                new String[]{videoPath}, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID));
                //查询到了Video的id
                ContentValues thumbValues = new ContentValues();
                thumbValues.put(MediaStore.Video.Thumbnails.DATA, coverPath);//缩略图路径
                thumbValues.put(MediaStore.Video.Thumbnails.VIDEO_ID, videoId);//video的id 用于绑定
                //Video的kind一般为1
                thumbValues.put(MediaStore.Video.Thumbnails.KIND,
                        MediaStore.Video.Thumbnails.MINI_KIND);
                //只返回图片大小信息，不返回图片具体内容
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(coverPath, options);
                if (bitmap != null) {
                    thumbValues.put(MediaStore.Video.Thumbnails.WIDTH, bitmap.getWidth());//缩略图宽度
                    thumbValues.put(MediaStore.Video.Thumbnails.HEIGHT, bitmap.getHeight());//缩略图高度
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
                this.getContentResolver().insert(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, //缩略图数据库
                        thumbValues);
            }
            cursor.close();
        }
    }

    @Override
    public void onRecordEvent(int event, Bundle param) {

    }

    SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    @Override
    public void onRecordProgress(long milliSecond) {
        if (mRecordProgress != null) {
            float progress = milliSecond / (60000.0f * 5);
            mRecordProgress.setProgress((int) (progress * 100));
            mProgressTime.setText(sdf.format(new Date(milliSecond)));
            if (milliSecond >= (60000.0f * 5)) {
                stopRecord(true);
            }
            mVideoDuration = milliSecond;
            if(milliSecond >= 5000){
                next_done.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /** attention to this below ,must add this**/

    }

    @Override
    public void onRecordComplete(TXRecordCommon.TXRecordResult result) {
        mTXRecordResult = result;
        if (mTXRecordResult.retCode != TXRecordCommon.RECORD_RESULT_OK) {
            ImageView liveRecord = (ImageView) findViewById(R.id.record);
            if (liveRecord != null) liveRecord.setBackgroundResource(R.drawable.start_record);
            mRecording = false;

            if (mRecordProgress != null) {
                mRecordProgress.setProgress(0);
            }
            if (mProgressTime != null) {
                mProgressTime.setText(String.format(Locale.CHINA, "%s", "00:00"));
            }
            Toast.makeText(TCVideoRecordActivity.this.getApplicationContext(), "录制失败，原因：" + mTXRecordResult.descMsg, Toast.LENGTH_SHORT).show();
        } else {

            if (mRecordProgress != null) {
                mRecordProgress.setProgress(0);
            }
            mProgressTime.setText(String.format(Locale.CHINA, "%s", "00:00"));
//            startPreview();
            //开始编辑
            startEditor();
        }
    }

    private void requestAudioFocus() {
        if (null == mAudioManager) {
            mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        }

        if (null == mOnAudioFocusListener) {
            mOnAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {

                @Override
                public void onAudioFocusChange(int focusChange) {
                    try {
                        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                            mTXCameraRecord.setVideoRecordListener(null);
                            stopRecord(false);
                        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                            mTXCameraRecord.setVideoRecordListener(null);
                            stopRecord(false);
                        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        }
        try {
            mAudioManager.requestAudioFocus(mOnAudioFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abandonAudioFocus() {
        try {
            if (null != mAudioManager && null != mOnAudioFocusListener) {
                mAudioManager.abandonAudioFocus(mOnAudioFocusListener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBeautyParamsChange(BeautySettingPannel.BeautyParams params, int key) {

        switch (key) {
            case BeautySettingPannel.BEAUTYPARAM_BEAUTY:
                mBeautyParams.mBeautyLevel = params.mBeautyLevel;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_WHITE:
                mBeautyParams.mWhiteLevel = params.mWhiteLevel;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setBeautyDepth(mBeautyParams.mBeautyLevel, mBeautyParams.mWhiteLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FACE_LIFT:
                mBeautyParams.mFaceSlimLevel = params.mFaceSlimLevel;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setFaceScaleLevel(params.mFaceSlimLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_BIG_EYE:
                mBeautyParams.mBigEyeLevel = params.mBigEyeLevel;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setEyeScaleLevel(params.mBigEyeLevel);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_FILTER:
                mBeautyParams.mFilterBmp = params.mFilterBmp;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setFilter(params.mFilterBmp);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_MOTION_TMPL:
                mBeautyParams.mMotionTmplPath = params.mMotionTmplPath;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setMotionTmpl(params.mMotionTmplPath);
                }
                break;
            case BeautySettingPannel.BEAUTYPARAM_GREEN:
                mBeautyParams.mGreenFile = params.mGreenFile;
                if (mTXCameraRecord != null) {
                    mTXCameraRecord.setGreenScreenFile(params.mGreenFile, true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                startCameraPreview();
                break;
            default:
                break;
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        permissions.toArray(new String[0]),
                        100);
                return false;
            }
        }

        return true;
    }
}
