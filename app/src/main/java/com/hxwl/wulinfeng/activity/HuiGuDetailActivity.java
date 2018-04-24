package com.hxwl.wulinfeng.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.CenterProgressWebView;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeLiveInfoBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.DuizhenEntity;
import com.hxwl.wulinfeng.bean.WQShiPinBean;
import com.hxwl.wulinfeng.fragment.LiveChatFragment;
import com.hxwl.wulinfeng.fragment.LiveDuizhenFragment;
import com.hxwl.wulinfeng.fragment.LiveWangqiFragment;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.NetWorkUtils;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.lecloud.sdk.constant.PlayerEvent;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.lecloud.sdk.videoview.VideoViewListener;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Allen on 2017/6/30.
 * 赛程详情页面
 */
public class HuiGuDetailActivity extends BaseActivity implements View.OnClickListener {

    private FragmentTransaction transaction;

    public static Intent getIntent(Context context, String scheduleId, int reliao) {
        Intent intent = new Intent(context, HuiGuDetailActivity.class);
        intent.putExtra("scheduleId",scheduleId);
        intent.putExtra("reliao",reliao);
        return intent;
    }
    private String scheduleId;
    private int reliao;
    private static final String TAG = "LiveDetailActivity";
    @Bind(R.id.title_back)
    RelativeLayout titleBack;
    @Bind(R.id.tv_chat)
    TextView tvChat;
    @Bind(R.id.chat_line)
    View chatLine;
    @Bind(R.id.rl_chat)
    RelativeLayout rlChat;
    @Bind(R.id.tv_guess)
    TextView tvGuess;
    @Bind(R.id.guess_line)
    View guessLine;
    @Bind(R.id.rl_guess)
    RelativeLayout rlGuess;

    RelativeLayout rlData;
    //相关回顾
    @Bind(R.id.tv_huigu)
    TextView tvHuigu;
    @Bind(R.id.huigu_line)
    View huiguLine;
    LinearLayout llyt_webview_container;
    RelativeLayout rlyt_top;
    private HomeLiveInfoBean bean;

    private String saichengId;
    private ImageView iv_picture;
    private String zhibo;
    private List<DuizhenEntity> duizhen;

    private WQShiPinBean videoBean = null;
    private RelativeLayout rl_nostart_pic;
    private ImageView iv_tioaozhuan;

    private LiveChatFragment fragment1;
    private LiveDuizhenFragment fragment2;
    private LiveWangqiFragment fragment4;

    private RelativeLayout videoContainer;
    private IMediaDataVideoView videoView;

    VideoViewListener videoViewListener = new VideoViewListener() {
        @Override
        public void onStateResult(int event, Bundle bundle) {
            handleVideoInfoEvent(event, bundle);// 处理播放器事件
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

    private String url;
    private TextView title;
    private View ic_net_warn;
    private Button btn1;
    private String saishiId;
    private String pageType;
    private String name;
    private ImageView iv_empty;
    private RelativeLayout rl_empty;
    private TextView tv_empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huigudetail_activity);
        // 设置窗口透明，可避免播放器SurfaceView初始化时的黑屏现象
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        // 视频播放界面，保持屏幕常亮利于视频观看体验
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        ButterKnife.bind(this);
        //StatusBarUtil.setTransparent(this);
        StatusBarUtil.setColor(HuiGuDetailActivity.this, getResources().getColor(R.color.tab_color), 0);
        setSwipeBackEnable(false); //设置本页是不是能左滑返回
        scheduleId= getIntent().getStringExtra("scheduleId");
        saichengId = getIntent().getStringExtra("saichengId");
        saishiId = getIntent().getStringExtra("saishiId");
        reliao=getIntent().getIntExtra("reliao",0);
        name = getIntent().getStringExtra("name");
        //初始化数据源

        transaction = getSupportFragmentManager().beginTransaction();
//        if (reliao==1){
            tvChat.setTextColor(getResources().getColor(R.color.shouye_tab));
            chatLine.setVisibility(View.VISIBLE);
            tvGuess.setTextColor(getResources().getColor(R.color.editTextcolor));
            guessLine.setVisibility(View.GONE);
            if (fragment1 == null) {
                fragment1 = new LiveChatFragment();
                fragment1.setScheduleId(scheduleId);
                transaction.add(R.id.fl_content, fragment1);
            } else {
                transaction.show(fragment1);
            }
//        }else {
//            tvChat.setTextColor(getResources().getColor(R.color.editTextcolor));
//            chatLine.setVisibility(View.GONE);
//            tvGuess.setTextColor(getResources().getColor(R.color.shouye_tab));
//            guessLine.setVisibility(View.VISIBLE);
//            if (fragment2 == null) {
//                fragment2 = new LiveDuizhenFragment();
//                fragment2.setCurrentSaichengId(scheduleId);
//                transaction.add(R.id.fl_content, fragment2);
//            } else {
//                transaction.show(fragment2);
//            }
//        }
        transaction.commit();
        initView();
        initData();

    }
    private void hide(FragmentTransaction transaction) {
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
    }

    public String getSaichengId() {
        return scheduleId;
    }

    public String getSaishiId() {
        return scheduleId;
    }


    public List<DuizhenEntity> getDuizhen() {
        return duizhen;
    }

    private void initData() {
        title.setText(TextUtils.isEmpty(name) ? "比赛视频" : name);
        pageType = getIntent().getStringExtra("pageType");
        //直播间信息
        doHuiGuList();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.title);
        iv_picture = (ImageView) findViewById(R.id.iv_picture);
        //未开始显示的图片布局
        rl_nostart_pic = (RelativeLayout) findViewById(R.id.rl_nostart_pic);
        //视频跳转按钮
        iv_tioaozhuan = (ImageView) findViewById(R.id.iv_tioaozhuan);
        iv_tioaozhuan.setOnClickListener(this);
        title = (TextView) findViewById(R.id.title);
        videoContainer = (RelativeLayout) findViewById(R.id.videoContainer);
        rlyt_top = (RelativeLayout) findViewById(R.id.rlyt_top);
        llyt_webview_container = (LinearLayout) findViewById(R.id.llyt_webview_container);
        ic_net_warn = findViewById(R.id.ic_net_warn);
        btn1 = (Button) ic_net_warn.findViewById(R.id.btn1);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ic_net_warn.setVisibility(View.GONE);
                if (reliao==1){
                    topVedioHuiGuNotTiaoZhuanDispose(5,
                            bean.getData().getLiveUrl(),
                            bean.getData().getScheduleName());
                }else {
                    topVedioHuiGuNotTiaoZhuanDispose(3,
                            bean.getData().getLiveUrl(),
                            bean.getData().getScheduleName());
                }
            }
        });
        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty);
        iv_empty = (ImageView) findViewById(R.id.iv_empty);
        tv_empty = (TextView) findViewById(R.id.tv_empty);

        TXMediaManager.instance(HuiGuDetailActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_LIVE_PLAY);
    }

    @OnClick({R.id.title_back, R.id.rl_chat, R.id.rl_guess, R.id.rl_huigu, R.id.iv_tioaozhuan})
    public void onClick(View view) {
        transaction = getSupportFragmentManager().beginTransaction();
        hide(transaction);
        switch (view.getId()) {
            case R.id.title_back:
                if (ScreenUtils.isLandScape(this)) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    if (TXMediaManager.instance(HuiGuDetailActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
                        TXMediaManager.instance(HuiGuDetailActivity.this).videoDestroy();
                    }
                    finish();
                }
                break;
            case R.id.rl_chat://热聊
                if (fragment1 == null) {
                    fragment1 = new LiveChatFragment();
                    fragment1.setScheduleId(scheduleId);
                    transaction.add(R.id.fl_content, fragment1);
                } else {
                    transaction.show(fragment1);
                }
                tvChat.setTextColor(getResources().getColor(R.color.shouye_tab));
                chatLine.setVisibility(View.VISIBLE);
                tvGuess.setTextColor(getResources().getColor(R.color.editTextcolor));
                guessLine.setVisibility(View.GONE);
                break;
            case R.id.rl_guess://对阵
                if (fragment2 == null) {
                    fragment2 = new LiveDuizhenFragment();
                    fragment2.setCurrentSaichengId(scheduleId);
                    transaction.add(R.id.fl_content, fragment2);
                } else {
                    transaction.show(fragment2);
                }
                tvChat.setTextColor(getResources().getColor(R.color.editTextcolor));
                chatLine.setVisibility(View.GONE);
                tvGuess.setTextColor(getResources().getColor(R.color.shouye_tab));
                guessLine.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_tioaozhuan:
                Intent intent1 = new Intent(HuiGuDetailActivity.this, NormalWebviewActivity.class);
                intent1.putExtra("title", "武林风");
                intent1.putExtra("isShowTitle", true);
                intent1.putExtra("url", zhibo);
                startActivity(intent1);
                break;
        }
        transaction.commit();
    }
    /**
     * 回顾
     */
    private void doHuiGuList() {
        OkHttpUtils.post()
                .url(URLS.HOME_LIVEINFO)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("scheduleId",scheduleId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                bean = gson.fromJson(response, HomeLiveInfoBean.class);
                                if (bean.getCode().equals("1000")){
                                    title.setText(bean.getData().getScheduleName());
                                    if (!NetWorkUtils.isWifiConnected(HuiGuDetailActivity.this)) {
                                        ic_net_warn.setVisibility(View.VISIBLE);

                                    }else {
                                        ic_net_warn.setVisibility(View.GONE);
                                        transaction = getSupportFragmentManager().beginTransaction();
                                        hide(transaction);
                                        if (bean.getData().getState() == 2) {
                                            if (fragment1 == null) {
                                                fragment1 = new LiveChatFragment();
                                                fragment1.setScheduleId(scheduleId);
                                                transaction.add(R.id.fl_content, fragment1);
                                            } else {
                                                transaction.show(fragment1);
                                            }
                                            tvChat.setTextColor(getResources().getColor(R.color.shouye_tab));
                                            chatLine.setVisibility(View.VISIBLE);
                                            tvGuess.setTextColor(getResources().getColor(R.color.editTextcolor));
                                            guessLine.setVisibility(View.GONE);
                                            transaction.commit();
                                            topVedioHuiGuNotTiaoZhuanDispose(5,
                                                    bean.getData().getLiveUrl(),
                                                    bean.getData().getScheduleName());
                                        } else {
                                            if (fragment2 == null) {
                                                fragment2 = new LiveDuizhenFragment();
                                                fragment2.setCurrentSaichengId(scheduleId);
                                                transaction.add(R.id.fl_content, fragment2);
                                            } else {
                                                transaction.show(fragment2);
                                            }
                                            tvChat.setTextColor(getResources().getColor(R.color.editTextcolor));
                                            chatLine.setVisibility(View.GONE);
                                            tvGuess.setTextColor(getResources().getColor(R.color.shouye_tab));
                                            guessLine.setVisibility(View.VISIBLE);
                                            transaction.commit();
                                            if (StringUtils.isBlank(bean.getData().getPrevueUrl())) {
                                                topVedioHuiGuNotTiaoZhuanDispose(3,
                                                        bean.getData().getLiveUrl(),
                                                        bean.getData().getScheduleName());
                                            } else {
                                                topVedioHuiGuNotTiaoZhuanDispose(3,
                                                        bean.getData().getPrevueUrl(),
                                                        bean.getData().getScheduleName());
                                            }
                                        }
                                    }
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(HuiGuDetailActivity.this));

                                }else {
                                    zhiboEmpty();
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });

    }

    /**
     * 会看信息错误
     */
    private void zhiboEmpty() {
        rl_nostart_pic.setVisibility(View.GONE);
        iv_tioaozhuan.setVisibility(View.GONE);
        videoContainer.setVisibility(View.GONE);
        rl_empty.setVisibility(View.VISIBLE);
        iv_empty.setImageResource(R.drawable.shipinzhizuozhong);
        tv_empty.setText("回放视频制作中，敬请期待...");
    }


    private void vedioViewPageShow(WQShiPinBean videoBean) {
        TXMediaManager.instance(HuiGuDetailActivity.this).videoDestroy();
        try {
            if (reliao==1){
                topVedioHuiGuNotTiaoZhuanDispose(5,
                        bean.getData().getLiveUrl(),
                        bean.getData().getScheduleName());
            }else {
                topVedioHuiGuNotTiaoZhuanDispose(3,
                        bean.getData().getLiveUrl(),
                        bean.getData().getScheduleName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 顶部直播跳转处理
     *
     * @param urlFengMian
     */
    private void topVedioLiveTiaoZhuanDispose(String urlFengMian) {
        llyt_webview_container.setVisibility(View.GONE);
        videoContainer.setVisibility(View.GONE);
        rl_nostart_pic.setVisibility(View.VISIBLE);
        iv_tioaozhuan.setVisibility(View.VISIBLE);
        rl_empty.setVisibility(View.GONE);
        //加载背景图片
        Glide.with(HuiGuDetailActivity.this).load(urlFengMian).into(iv_picture);
    }

    /**
     * 功能:顶部回顾不跳转展现
     *
     * @param type   类型
     * @param params 参数
     */
    private void topVedioHuiGuNotTiaoZhuanDispose(int type, String params, String title) {
        try {
            //int nType = Integer.parseInt(type.trim());

            //1网页地址2乐视移动直播3乐视标准直播4视频文件5乐视点播
            switch (type) {
                case 1://网页地址
                case 2://乐视点播 --- 网页地址
                    //调用本地webview
                    rl_nostart_pic.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.VISIBLE);
                    rl_empty.setVisibility(View.GONE);
                    break;
                case 3://视频文件 mp4
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    rl_empty.setVisibility(View.GONE);
                    TXMediaManager.instance(HuiGuDetailActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
                    TXMediaManager.instance(HuiGuDetailActivity.this).TXVodPlayAndView(params, videoContainer, -1, title);
                    break;
                case 4://4 腾讯flv
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    rl_empty.setVisibility(View.GONE);
                    //测试数据
                    TXMediaManager.instance(HuiGuDetailActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_LIVE_PLAY);
                    TXMediaManager.instance(HuiGuDetailActivity.this).TXVodPlayAndView(params, videoContainer, 4, title);
                    break;
                case 5:// 5腾讯hls地址
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    rl_empty.setVisibility(View.GONE);
                    //测试数据
                    TXMediaManager.instance(HuiGuDetailActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_LIVE_PLAY);
                    TXMediaManager.instance(HuiGuDetailActivity.this).TXVodPlayAndView(params, videoContainer, -1, title);
                    break;
            }
        } catch (NumberFormatException e) {
            //视频类型出现异常
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    CenterProgressWebView mWebView;


    /**
     * 往期条目点击播放视频
     */
    public void playVideo(WQShiPinBean videoListBean) {
        if (videoView != null) {
            videoView.onDestroy();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mWebView != null) {
                mWebView.onStop();
                mWebView = null;
            }
        }
//        currentVideoListBean = videoListBean;
        title.setText(videoListBean.getTitle());
        vedioViewPageShow(videoListBean);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUtils.setEvent(this, "PlaybackPageOpen", "比赛回放视频详情打开");
        StatService.onPageStart(this, name + "比赛视频");
        TCAgent.onPageStart(this, name + "比赛视频");
        TXMediaManager.instance(HuiGuDetailActivity.this).resumeplayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPageEnd(this, name + "比赛视频");
        TCAgent.onPageEnd(this, name + "比赛视频");
        if (TXMediaManager.instance(HuiGuDetailActivity.this).mLivePlayer != null && TXMediaManager.instance(HuiGuDetailActivity.this).mLivePlayer.isPlaying()) {
            TXMediaManager.instance(HuiGuDetailActivity.this).pauseplayer();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mWebView != null) {
                mWebView.onPause();
            } // 暂停网页中正在播放的视频
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ScreenUtils.isLandScape(this)) {
                TXMediaManager.instance(HuiGuDetailActivity.this).mPlayerView1.backPress();
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            } else {
                if (TXMediaManager.instance(HuiGuDetailActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
                    TXMediaManager.instance(HuiGuDetailActivity.this).videoDestroy();
                }
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if(TXMediaManager.instance(HuiGuDetailActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null){
            TXMediaManager.instance(HuiGuDetailActivity.this).videoDestroy();
        }
        super.onDestroy();
    }

    /**
     * 处理直播类事件
     */
    private void handleLiveEvent(int state, Bundle bundle) {
    }

    /**
     * 处理视频信息类事件
     */
    private void handleVideoInfoEvent(int state, Bundle bundle) {
    }

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
     * 屏幕旋转时调用此方法
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rlyt_top.setVisibility(View.VISIBLE);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            AppUtils.setEvent(HuiGuDetailActivity.this, "PlaybackFullScreen", "比赛回放视频全屏");
            rlyt_top.setVisibility(View.GONE);
        }
        if (videoView != null) {
            videoView.onConfigurationChanged(newConfig);
        }
    }
}
