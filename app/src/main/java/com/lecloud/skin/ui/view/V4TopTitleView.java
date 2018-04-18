package com.lecloud.skin.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.lecloud.skin.ui.ILetv;
import com.lecloud.skin.ui.LetvUIListener;
import com.lecloud.skin.ui.utils.ReUtils;
import com.lecloud.skin.ui.utils.StatusUtils;

import java.util.HashMap;

import okhttp3.Call;

/**
 * 顶部浮层
 * @author pys
 */
public class V4TopTitleView extends LinearLayout{
	private Context context;
	protected LetvUIListener mLetvUIListener;
    private ILetv mILetv;
    private TextView textView;
    private ImageView netStateView;
    private ImageView batteryView;
    private TextView timeView;
    //弹幕
    private ImageView ivDanmu;
    //互动
    private ImageView ivHudong;
    //分享
    private ImageView ivShare;

    private String title;
    //弹幕是否打开，默认打开
    private boolean isOpenDanmu = true;
    //互动是否打开，默认打开
    private boolean isOpenHudong = true;

//    private ShareSjcBean shareSjcBean;
    //微信分享回调请求
    private static final int WEIXIN_SHARE_CALLBACK = 0;

    public ILetv getmILetv() {
        return mILetv;
    }

    public void setmILetv(ILetv mILetv) {
        this.mILetv = mILetv;
    }

    public V4TopTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

	public V4TopTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public V4TopTitleView(Context context) {
        super(context);
    }


    /**
     * 设置状态栏
     */
    public void setState() {
            /**
             * 设置标题
             */
            if (title != null) {
                textView.setText(title);
            }

            /**
             * 获取网络状态
             */
            netStateView.setImageLevel(StatusUtils.getWiFistate(context));
            /**
             * 电池
             */
            batteryView.setImageLevel(StatusUtils.getBatteryStatus(context));
            /**
             * 时间
             */
            timeView.setText(StatusUtils.getCurrentTime(context));
    }


    
    @Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		context = getContext();
		initView(context);
	}

    
    protected void initView(final Context context) {
        /**
         * 返回键
         */
        findViewById(ReUtils.getId(context, "full_back")).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((Activity)getContext()).getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    if (mLetvUIListener != null) {
                        mLetvUIListener.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        //点击返回键关闭弹幕
                        mILetv.isOpenDanmu(false);
                    }
                }else{
                    ((Activity)getContext()).finish();
                }
            }
        });
        
        textView = (TextView) findViewById(R.id.full_title);
        netStateView = (ImageView) findViewById(R.id.full_net);
        batteryView = (ImageView) findViewById(R.id.full_battery);
        timeView = (TextView) findViewById(R.id.full_time);
        ivDanmu = (ImageView) findViewById(R.id.iv_danmu_btn);
        ivHudong = (ImageView) findViewById(R.id.iv_hudong);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivDanmu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpenDanmu){
                    isOpenDanmu = false;
                    //关闭弹幕
                    ivDanmu.setBackgroundResource(R.drawable.danmu_off);
                    ToastUtils.showToast(context,"关闭弹幕");
                    mILetv.isOpenDanmu(false);
                }else{
                    isOpenDanmu = true;
                    //打开弹幕
                    ivDanmu.setBackgroundResource(R.drawable.danmu_on);
                    ToastUtils.showToast(context,"打开弹幕");
                    mILetv.isOpenDanmu(true);
                }
            }
        });

        ivHudong.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpenHudong){
                    isOpenHudong = false;
                    //关闭互动
                    ivHudong.setBackgroundResource(R.drawable.hudong_off);
                    ToastUtils.showToast(context,"关闭互动");
                    mILetv.isOpenHudong(false);
                }else{
                    isOpenHudong = true;
                    //打开互动
                    ivHudong.setBackgroundResource(R.drawable.hudong_on);
                    ToastUtils.showToast(context,"打开互动");
                    mILetv.isOpenHudong(true);
                }
            }
        });

        ivShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast(context,"点击分享");
//                getShareCsj();

            }
        });

        setState();

    }

    
    public void setLetvUIListener(LetvUIListener mLetvUIListener) {
     this.mLetvUIListener = mLetvUIListener;
    }

    public void setTitle(String title){
    	if(!TextUtils.isEmpty(title)){
    		textView.setText(title);
    	}
    }
    
    /**
     * whether show topview's right views, including screen lock, time etc.
     * @param isShow
     */
    public void showTopRightView(boolean isShow){
    	int visibility;
    	if(isShow){
    		visibility = View.VISIBLE;
    	}else{
    		visibility = View.GONE;
    	}
		if(netStateView != null){
			netStateView.setVisibility(visibility);
		}
		if(batteryView != null){
			batteryView.setVisibility(visibility);
		}
		if(timeView != null){
			timeView.setVisibility(visibility);
		}
    }
}
