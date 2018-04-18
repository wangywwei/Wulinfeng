package com.hxwl.common.tencentplay.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxwl.common.tencentplay.choose.TCVideoEditerListAdapter;
import com.hxwl.common.tencentplay.choose.TCVideoEditerMgr;
import com.hxwl.common.tencentplay.choose.TCVideoFileInfo;
import com.hxwl.common.tencentplay.utils.TCConstants;
import com.hxwl.common.tencentplay.widget.PictureDialog;
import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.OnClickEventUtils;
import com.tencent.liteav.basic.log.TXCLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 功能:图片视频选择页面
 * 作者：zjn on 2017/6/16 11:02
 */

public class PictureSelectorActivity extends BaseActivity implements View.OnClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback{
    private static final String TAG = PictureSelectorActivity.class.getSimpleName();
    public static final int TYPE_SINGLE_CHOOSE = 0;
    public static final int TYPE_MULTI_CHOOSE = 1;
    public static Intent getIntent(Context context){
        Intent intent=new Intent(context,PictureSelectorActivity.class);
        return intent;
    }
    protected PictureDialog dialog;
    protected Context mContext;
    private RelativeLayout rlyt_fanhui;
    private TextView tv_next_step;
    private RecyclerView picture_recycler;
    //选择视频
    private RelativeLayout rlyt_selector_video;
    private TextView tv_selector_video;
    //选择照片
    private RelativeLayout rlyt_selector_pictor;
    private TextView tv_selector_pictor;

    private TCVideoEditerListAdapter mAdapter;
    private TCVideoEditerMgr mTCVideoEditerMgr;

    private Handler mHandler;
    private HandlerThread mHandlerThread;
    //默认为视频剪辑
    private int mType = TYPE_SINGLE_CHOOSE;
    private Handler mMainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ArrayList<TCVideoFileInfo> fileInfoArrayList = (ArrayList<TCVideoFileInfo>) msg.obj;
            mAdapter.addAll(fileInfoArrayList,true);
        }
    };
    //点击拍摄按钮
    private View.OnClickListener onClickCamera = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PictureSelectorActivity.this,TCVideoRecordActivity.class) ;
            startActivity(intent);
            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this) ;
        setContentView(R.layout.activity_picture_selector);
        AppUtils.setTitle(this);
        mTCVideoEditerMgr = TCVideoEditerMgr.getInstance(this);
        mHandlerThread = new HandlerThread("LoadList");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mContext = this;
        initView();
        loadVideoList();
    }

    private void initView() {

        rlyt_fanhui = (RelativeLayout)findViewById(R.id.rlyt_fanhui);
        rlyt_fanhui.setOnClickListener(this);
        rlyt_selector_pictor = (RelativeLayout)findViewById(R.id.rlyt_selector_pictor);
        rlyt_selector_pictor.setOnClickListener(this);
        rlyt_selector_video = (RelativeLayout)findViewById(R.id.rlyt_selector_video);
        rlyt_selector_video.setOnClickListener(this);
        tv_selector_pictor = (TextView)findViewById(R.id.tv_selector_pictor);
        tv_selector_video = (TextView)findViewById(R.id.tv_selector_video);
        picture_recycler = (RecyclerView) findViewById(R.id.picture_recycler);
        picture_recycler.setHasFixedSize(true);
//        picture_recycler.addItemDecoration(new GridSpacingItemDecoration(1,
//                2, false));
        picture_recycler.setLayoutManager(new GridLayoutManager(this, 3));
        // 解决调用 notifyItemChanged 闪烁问题,取消默认动画
//        ((SimpleItemAnimator) picture_recycler.getItemAnimator())
//                .setSupportsChangeAnimations(false);
        picture_recycler.setItemAnimator(null);
        mAdapter = new TCVideoEditerListAdapter(this,onClickCamera);
        picture_recycler.setAdapter(mAdapter);
        resetSelectorType();
        tv_next_step = (TextView)findViewById(R.id.tv_next_step);
        tv_next_step.setOnClickListener(this);
    }

    //重置选择类型
    private void resetSelectorType() {
        if (mType == TYPE_SINGLE_CHOOSE) {
            mAdapter.setMultiplePick(false);
        } else {
            mAdapter.setMultiplePick(true);
        }
    }

    /**
     * loading dialog
     */
    protected void showPleaseDialog() {
        if (!isFinishing()) {
            dismissDialog();
            dialog = new PictureDialog(this);
            dialog.show();
        }
    }

    /**
     * dismiss dialog
     */
    protected void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    /*
    * 获取所有视频
    * */
    private void loadVideoList() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    ArrayList<TCVideoFileInfo> fileInfoArrayList = mTCVideoEditerMgr.getAllVideo();

                    Message msg = new Message();
                    msg.obj = fileInfoArrayList;
                    mMainHandler.sendMessage(msg);
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadVideoList();
        }
    }

    @Override
    protected void onDestroy() {
        mHandlerThread.getLooper().quit();
        mHandlerThread.quit();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Glide.with(this).pauseRequests();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).resumeRequests();
    }

    @Override
    public void onClick(View v) {
        //不能同时选中图片和视频
        if(OnClickEventUtils.isFastClick()){
            return;
        }
        switch (v.getId()){
            case R.id.rlyt_fanhui:
                finish();
                break;
            case R.id.tv_next_step:

                //下一步
                doSelect();
                break;
            case R.id.rlyt_selector_video:
                mAdapter.resetSelectItem();
                tv_selector_video.setTextColor(Color.parseColor("#ffffff"));
                tv_selector_pictor.setTextColor(Color.parseColor("#222222"));
                rlyt_selector_video.setBackgroundColor(Color.parseColor("#fbc700"));
                rlyt_selector_pictor.setBackgroundColor(Color.parseColor("#ffffff"));
                //点击视频编辑
                mType = TYPE_SINGLE_CHOOSE;
                resetSelectorType();
                break;
            case R.id.rlyt_selector_pictor:
                mAdapter.resetSelectItem();
                tv_selector_video.setTextColor(Color.parseColor("#222222"));
                tv_selector_pictor.setTextColor(Color.parseColor("#ffffff"));
                rlyt_selector_video.setBackgroundColor(Color.parseColor("#ffffff"));
                rlyt_selector_pictor.setBackgroundColor(Color.parseColor("#fbc700"));
                //点击视频合成
                mType = TYPE_MULTI_CHOOSE;
                resetSelectorType();
                break;
        }
    }

    private void doSelect() {
        if (mType == TYPE_SINGLE_CHOOSE) {

            if (mAdapter.getSingleSelected().getDuration()>120000){
                Toast.makeText(this, "视频时间不能大于2分钟", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, TCVideoEditerActivity.class);
            TCVideoFileInfo fileInfo = mAdapter.getSingleSelected();
            if (fileInfo == null) {
                Toast.makeText(this, "必须选择视频文件", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isVideoDamaged(fileInfo)) {
                showErrorDialog("该视频文件已经损坏");
                return;
            }
            File file = new File(fileInfo.getFilePath());
            if (!file.exists()) {
                showErrorDialog("选择的文件不存在");
                return;
            }
            intent.putExtra(TCConstants.INTENT_KEY_SINGLE_CHOOSE, fileInfo);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, TCVideoJoinerActivity.class);
            ArrayList<TCVideoFileInfo> videoFileInfos = mAdapter.getMultiSelected();
            if (videoFileInfos == null || videoFileInfos.size() == 0) {
                TXCLog.d(TAG, "select file null");
                return;
            }
            if (videoFileInfos.size() < 2) {
                Toast.makeText(this, "必须选择两个以上视频文件", Toast.LENGTH_SHORT).show();
                return;
            }
            if (isVideoDamaged(videoFileInfos)) {
                showErrorDialog("包含已经损坏的视频文件");
                return;
            }
            for (TCVideoFileInfo info : videoFileInfos) {
                File file = new File(info.getFilePath());
                if (!file.exists()) {
                    showErrorDialog("选择的文件不存在");
                    return;
                }
            }
            intent.putExtra(TCConstants.INTENT_KEY_MULTI_CHOOSE, videoFileInfos);
            startActivity(intent);
        }
        finish();
    }

    /**
     * 检测视频是否损坏
     *
     * @param info
     * @return
     */
    private boolean isVideoDamaged(TCVideoFileInfo info) {
        if (info.getDuration() == 0) {
            //数据库获取到的时间为0，使用Retriever再次确认是否损坏
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                retriever.setDataSource(info.getFilePath());
            } catch (Exception e) {
                return true;//无法正常打开，也是错误
            }
            String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            if (TextUtils.isEmpty(duration))
                return true;
            return Integer.valueOf(duration) == 0;
        }
        return false;
    }

    private boolean isVideoDamaged(List<TCVideoFileInfo> list) {
        for (TCVideoFileInfo info : list) {
            if (isVideoDamaged(info)) {
                return true;
            }
        }
        return false;
    }

    private void showErrorDialog(String msg) {
        android.app.AlertDialog.Builder normalDialog = new android.app.AlertDialog.Builder(PictureSelectorActivity.this, R.style.ConfirmDialogStyle);
        normalDialog.setMessage(msg);
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("知道了", null);
        normalDialog.show();
    }
}
