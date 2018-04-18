package com.hxwl.wulinfeng.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hxwl.common.addialog.AdManager;
import com.hxwl.common.addialog.bean.AdBean;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.ACache;
import com.hxwl.common.utils.CenterProgressWebView;
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
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.MediaUtils;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * 直播详情页面
 */
public class LiveDetailActivity extends BaseActivity implements View.OnClickListener {
    public static Intent getIntent(Context context,String scheduleId,int reliao) {
        Intent intent = new Intent(context, LiveDetailActivity.class);
        intent.putExtra("scheduleId",scheduleId);
        intent.putExtra("reliao",reliao);
        return intent;
    }
    private String scheduleId;
    private int reliao;
    //微信分享回调请求
    private static final int WEIXIN_SHARE_CALLBACK = 0;
    private View rootView;
    private static final String TAG = "LiveDetailActivity";
    public String saichengId;
    private ImageView iv_picture;
    @Bind(R.id.llyt_webview_container)
    LinearLayout llyt_webview_container;
    @Bind(R.id.rlyt_top)
    RelativeLayout rlyt_top;
    @Bind(R.id.full_screen)
    FrameLayout full_screen;
    //预告的跳转URL
    private String yugaoUrl;
    private String zhiboUrl;
    private List<DuizhenEntity> duizhen;
    private String type;
    private RelativeLayout rl_nostart_pic;
    private ImageView iv_tioaozhuan;
    private TextView tvData;
    private TextView tvChat;
    private TextView tvGuess;
    private View chatLine;
    private View guessLine;
    private View dataLine;
    private String zhiBostate;
    private String strYuGaoImg;
    private TextView title;
    private TextView tv_wangqi;
    private View wangqi_line;
    private TextView iv_yuyue;
    private TextView iv_shoupiao;
    public String saishiId;
    private FrameLayout fl_content;
    private RelativeLayout videoContainer;
    private IMediaDataVideoView videoView;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;//用来存储当前的Fragment
    //当前fragment的tag
    private String currentFragTag;
    //缓存框架
    private ACache mAcache;
    //对话框view
    private RelativeLayout rlyt_view;
    //关闭
    private TextView tv_close;
    public LiveChatFragment fragment1;
    private LiveDuizhenFragment fragment2;
    private boolean isDuiZhen = true;
    RelativeLayout iv_share;
    //直播是否已开始 默认未开始
    private boolean isStart = false;
    HashMap<String, String> mapa = new HashMap<>();

    //判断是否显示弹幕 false不显示 true显示
    public boolean isdanmu = false;
    //直播详情
    //当前vediobean对象
    private WQShiPinBean currentVideoListBean;

    private boolean isLive = true;
    View ic_net_warn;
    Button btn1;
    //strplayType 、strUrl 只在判断WiFi提醒时使用
    private String strPlayType;
    private String strUrl;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WEIXIN_SHARE_CALLBACK:
//                    mAcache.put(videoBean.getSaicheng_id(), "1");
//                    shareCompleteCallback();//微信回调
                    break;
            }
        }
    };

    private String vedioType;
    private String name;
    private TextView tv_title1;
    private TextView tv_time;
    private RelativeLayout rl_empty;
    private ImageView iv_empty;
    private TextView tv_empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detail);
        // 设置窗口透明，可避免播放器SurfaceView初始化时的黑屏现象
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        // 视频播放界面，保持屏幕常亮利于视频观看体验
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //开启硬件加速
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        mAcache = ACache.get(this);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(LiveDetailActivity.this, getResources().getColor(R.color.tab_color), 0);
        setSwipeBackEnable(false); //设置本页是不是能左滑返回

        //初始化数据源
        fragmentManager = getSupportFragmentManager();
        fragment1 = new LiveChatFragment();
        fragment2 = new LiveDuizhenFragment();
        saichengId = getIntent().getStringExtra("saichengId");
        saishiId = getIntent().getStringExtra("saishiId");

        scheduleId= getIntent().getStringExtra("scheduleId");
        name = getIntent().getStringExtra("name");
        reliao=getIntent().getIntExtra("reliao",0);
        initView();
        if (reliao==1){
            tvChat.setTextColor(getResources().getColor(R.color.shouye_tab));
            chatLine.setVisibility(View.VISIBLE);
            tvGuess.setTextColor(getResources().getColor(R.color.editTextcolor));
            guessLine.setVisibility(View.GONE);
            fragmentManager.beginTransaction().replace(R.id.fl_content, fragment1, LiveChatFragment.class.getSimpleName()).commit();
            currentFragment = fragment1;
        }else {
            tvChat.setTextColor(getResources().getColor(R.color.editTextcolor));
            chatLine.setVisibility(View.GONE);
            tvGuess.setTextColor(getResources().getColor(R.color.shouye_tab));
            guessLine.setVisibility(View.VISIBLE);
            fragmentManager.beginTransaction().replace(R.id.fl_content, fragment2, LiveChatFragment.class.getSimpleName()).commit();
            currentFragment = fragment2;
        }

        initData();
    }


    public void initView() {
        rootView = getLayoutInflater().from(this).inflate(R.layout.activity_live_detail, null);
        chatLine = (View) findViewById(R.id.chat_line);
        guessLine = (View) findViewById(R.id.guess_line);
        wangqi_line = (View) findViewById(R.id.wangqi_line);
        tvChat = (TextView) findViewById(R.id.tv_chat);
        tvGuess = (TextView) findViewById(R.id.tv_guess);
        tv_wangqi = (TextView) findViewById(R.id.tv_wangqi);
        title = (TextView) findViewById(R.id.title);
        iv_share = (RelativeLayout) findViewById(R.id.iv_share);
        //未开始显示的图片
        iv_picture = (ImageView) findViewById(R.id.iv_picture);
        iv_shoupiao = (TextView) findViewById(R.id.iv_shoupiao);
        iv_yuyue = (TextView) findViewById(R.id.iv_yuyue);
        tv_title1 = (TextView) findViewById(R.id.tv_title1);
        tv_time = (TextView) findViewById(R.id.tv_time);

        rl_empty = (RelativeLayout) findViewById(R.id.rl_empty);
        iv_empty = (ImageView) findViewById(R.id.iv_empty);
        tv_empty = (TextView) findViewById(R.id.tv_empty);

        //未开始显示的图片布局
        rl_nostart_pic = (RelativeLayout) findViewById(R.id.rl_nostart_pic);
        //视频跳转按钮
        iv_tioaozhuan = (ImageView) findViewById(R.id.iv_tioaozhuan);

        fl_content = (FrameLayout) findViewById(R.id.fl_content);

        videoContainer = (RelativeLayout) findViewById(R.id.videoContainer);
        // videoContainer.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        ic_net_warn = findViewById(R.id.ic_net_warn);
        btn1 = (Button) ic_net_warn.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ic_net_warn.setVisibility(View.GONE);
                topVedioLiveNotTiaoZhuanDispose(strPlayType, strUrl);
            }
        });

    }

    //切换fragment
    private void switchContent(Fragment from, Fragment fragment) {
        if (currentFragment != fragment) {
            currentFragment = fragment;
        }
        if (fragment.equals(fragment1)) {
            tvChat.setTextColor(getResources().getColor(R.color.shouye_tab));
            chatLine.setVisibility(View.VISIBLE);
            tv_wangqi.setTextColor(getResources().getColor(R.color.editTextcolor));
            wangqi_line.setVisibility(View.GONE);
            tvGuess.setTextColor(getResources().getColor(R.color.editTextcolor));
            guessLine.setVisibility(View.GONE);
        } else if (fragment.equals(fragment2)) {
            tvChat.setTextColor(getResources().getColor(R.color.editTextcolor));
            chatLine.setVisibility(View.GONE);
            tvGuess.setTextColor(getResources().getColor(R.color.shouye_tab));
            guessLine.setVisibility(View.VISIBLE);
            tv_wangqi.setTextColor(getResources().getColor(R.color.editTextcolor));
            wangqi_line.setVisibility(View.GONE);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!fragment.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(R.id.fl_content, fragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(fragment).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

    public void initData() {
        String titlea = getIntent().getStringExtra("title");
        saichengId = getIntent().getStringExtra("saichengId");
        saishiId = getIntent().getStringExtra("saishiId");
        title.setText(titlea == null ? "武林风" : titlea);
        //获取直播信息
        doLive();

    }

    public String getSaichengId() {
        return saichengId;
    }

    public String getSaishiId() {
        return saishiId;
    }


    public List<DuizhenEntity> getDuizhen() {
        return duizhen;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppUtils.setEvent(this, "LivePageOpen", "直播详情打开");
        StatService.onPageStart(this, "直播详情页");
        TCAgent.onPageStart(this, "直播详情页");
        if (TXMediaManager.instance(LiveDetailActivity.this).textureView != null && TXCloudPlayerManager.getCurrentJcvd() != null) {
            TXMediaManager.instance(LiveDetailActivity.this).resumeplayer();
        } else {
            if (bean != null) {
                doLive();
            }
        }
        /**demo的内容，暂停系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(this, false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "直播详情页");
        TCAgent.onPageEnd(this, "直播详情页");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mWebView != null) {
                mWebView.onPause(); // 暂停网页中正在播放的视频
            }
        }
        if (TXMediaManager.instance(LiveDetailActivity.this).mLivePlayer != null && TXMediaManager.instance(LiveDetailActivity.this).mLivePlayer.isPlaying()) {
            TXMediaManager.instance(LiveDetailActivity.this).pauseplayer();
        }
        /**demo的内容，恢复系统其它媒体的状态*/
        MediaUtils.muteAudioFocus(this, true);
    }

    @Override
    protected void onDestroy() {
        if (TXMediaManager.instance(LiveDetailActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
            TXMediaManager.instance(LiveDetailActivity.this).videoDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private HomeLiveInfoBean bean;
    /**
     * 获取直播信息
     */
    private void doLive() {
        OkHttpUtils.post()
                .url(URLS.HOME_LIVEINFO)
                .addParams("scheduleId",scheduleId)
                .addParams("token", MakerApplication.instance.getToken())
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
                                    topVedioLiveNotTiaoZhuanDispose("4",
                                            bean.getData().getLiveUrl());
//                                    topVedioLiveNotTiaoZhuanDispose("4",
//                                            "rtmp://live.hkstv.hk.lxdns.com/live/hks");
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(LiveDetailActivity.this));
                                    LiveDetailActivity.this.finish();
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });

    }

    /**
     * 直播信息错误
     */
    private void zhiboError() {
        rl_nostart_pic.setVisibility(View.VISIBLE);
        iv_tioaozhuan.setVisibility(View.GONE);
        videoContainer.setVisibility(View.GONE);
        rl_empty.setVisibility(View.VISIBLE);
        iv_empty.setImageResource(R.drawable.bofangxinxicuowu);
    }


    /**
     * 顶部直播跳转处理
     *
     * @param urlFengMian
     */
    private void topVedioLiveTiaoZhuanDispose(String urlFengMian) {
        iv_shoupiao.setVisibility(View.GONE);
        iv_yuyue.setVisibility(View.GONE);
        tv_title1.setVisibility(View.GONE);
        tv_time.setVisibility(View.GONE);
        llyt_webview_container.setVisibility(View.GONE);
        videoContainer.setVisibility(View.GONE);
        rl_nostart_pic.setVisibility(View.VISIBLE);
        iv_tioaozhuan.setVisibility(View.VISIBLE);
        //加载背景图片
        Glide.with(LiveDetailActivity.this).load(urlFengMian).into(iv_picture);
    }

    /**
     * 功能:顶部直播不跳转展现
     *
     * @param type   类型
     * @param params 参数
     */
    private void topVedioLiveNotTiaoZhuanDispose(String type, String params) {

        try {
            int nType = Integer.parseInt(type.trim());
            HashMap<String, String> hashMap;
            //1网页地址 2乐视移动直播 3乐视标准直播 4视频文件 5乐视点播
            switch (nType) {
                case 1://网页地址
                    //调用本地webview
                    isdanmu = false;
                    rl_nostart_pic.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.VISIBLE);
                    loadAppUrl(params);
                    break;
                case 4://视频文件 也调用乐视点播
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    TXMediaManager.instance(LiveDetailActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_LIVE_PLAY);
                    TXMediaManager.instance(LiveDetailActivity.this).TXVodPlayAndView(params, videoContainer, 5, bean.getData().getScheduleName());
                    break;
                case 2://乐视移动直播
                case 6:// (华数TV)乐视移动直播
                case 3://乐视标准直播
                case 5://乐视点播
                case 7://腾讯Hls
                    isdanmu = true;
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    TXMediaManager.instance(LiveDetailActivity.this).TXVodPlayAndView(params, videoContainer, -1, bean.getData().getScheduleName());
                    break;
                case 8://腾讯RTMP   TXVodPlayAndView
                    isdanmu = true;
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    TXMediaManager.instance(LiveDetailActivity.this).TXVodPlayAndView(params, videoContainer, -1, bean.getData().getScheduleName());
                    break;
                case 9://腾讯FLV
                    isdanmu = true;
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    TXMediaManager.instance(LiveDetailActivity.this).TXVodPlayAndView(params, videoContainer, -1, bean.getData().getScheduleName());
                    break;

            }
        } catch (NumberFormatException e) {
            //视频类型出现异常
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能:顶部回顾不跳转展现
     *
     * @param type   类型
     * @param params 参数
     */
    private void topVedioHuiGuNotTiaoZhuanDispose(String type, String params) {

        try {
            int nType = Integer.parseInt(type.trim());
//            nType = 2;
            HashMap<String, String> hashMap;
            //1网页地址2乐视移动直播3乐视标准直播4视频文件5乐视点播
            switch (nType) {
                case 1://网页地址
                case 2://乐视点播
                    rl_nostart_pic.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.VISIBLE);
                    loadAppUrl(params);
                    break;
                case 3://视频文件
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    TXMediaManager.instance(LiveDetailActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
                    TXMediaManager.instance(LiveDetailActivity.this).TXVodPlayAndView(params, videoContainer, 3, bean.getData().getScheduleName());
                    break;
                case 4://腾讯flv
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    //测试数据
                    TXMediaManager.instance(LiveDetailActivity.this).TXVodPlayAndView(params, videoContainer, 4, bean.getData().getScheduleName());
                    break;
                case 5:// 腾讯hls地址
                    rl_nostart_pic.setVisibility(View.GONE);
                    llyt_webview_container.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    //测试数据
                    TXMediaManager.instance(LiveDetailActivity.this).TXVodPlayAndView(params, videoContainer, 5, bean.getData().getScheduleName());
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
     * 加载URL
     *
     * @param url
     */
    private void loadAppUrl(String url) {
        if (mWebView == null) {
            mWebView = new CenterProgressWebView(this);
        }
        llyt_webview_container.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);
        llyt_webview_container.addView(mWebView);
        mWebView.loadUrl(url);
    }


    @OnClick({R.id.title_back, R.id.rl_chat, R.id.rl_guess, R.id.rl_wangqi, R.id.iv_tioaozhuan, R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.rl_chat://热聊
                AppUtils.setEvent(this, "LiveChat", "直播-点击热聊按钮次数");

                switchContent(currentFragment, fragment1);
                break;
            case R.id.rl_guess://对阵
                if (currentVideoListBean != null) {
                    fragment2.setCurrentSaichengId(currentVideoListBean.getSaicheng_id());
                } else {
                    fragment2.setCurrentSaichengId(saichengId);
                }
                switchContent(currentFragment, fragment2);
                break;
            case R.id.iv_tioaozhuan://跳转
                Intent intent1 = new Intent(LiveDetailActivity.this, NormalWebviewActivity.class);
                intent1.putExtra("title", "武林风");
                intent1.putExtra("isShowTitle", true);
                if (vedioType.equals("2")) {
                    //栏目
                    if (isStart) {
                        //已开始
                        intent1.putExtra("url", zhiboUrl);
                    } else {
                        //未开始
                        intent1.putExtra("url", yugaoUrl);
                    }
                } else {
                    if ((zhiBostate != null && zhiBostate.equals("2")) || (zhiBostate != null && zhiBostate.equals("4"))) { //直播进行中
                        if (type != null && type.equals("2")) { //乐视
//                            if (bean.getZhibo_info().getIs_letv_biaozhun().equals("1")) { //标准直播
//                                intent1.putExtra("url", "http://live.lecloud.com/live/playerPage/getView?activityId=" + zhiboUrl);
//                            }
                        } else {
                            intent1.putExtra("url", zhiboUrl);
                        }
                    } else {
                        intent1.putExtra("url", yugaoUrl);
                    }

                }
                startActivity(intent1);
                break;


        }
    }

    /**
     * 往期条目点击播放视频
     */
    public void playVideo(WQShiPinBean videoListBean) {
        ic_net_warn.setVisibility(View.GONE);
        isLive = false;
        if (videoView != null) {
            videoView.onDestroy();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mWebView != null) {
                mWebView.onStop();
                mWebView = null;
            }
        }
        currentVideoListBean = videoListBean;
        title.setText(videoListBean.getTitle());
        vedioViewPageShow(videoListBean);
    }

    private void vedioViewPageShow(WQShiPinBean videoBean) {
        TXMediaManager.instance(LiveDetailActivity.this).videoDestroy();
        if (videoBean.getIs_tiaozhuan() != null && videoBean.getIs_tiaozhuan().equals("1")) { //跳转
            zhiBostate = "2";
            type = "3";
            zhiboUrl = videoBean.getUrl();
            topVedioLiveTiaoZhuanDispose(videoBean.getImg());
        } else { //不跳转
            topVedioHuiGuNotTiaoZhuanDispose(videoBean.getUrl_type(), videoBean.getUrl());
        }
    }

    private void initAdDialog(String img, final String title, final String url) {
        List<AdBean> advList = new ArrayList<>();
        AdBean adInfo = new AdBean();
        adInfo.setImgUrl(img);
        advList.add(adInfo);

        AdManager adManager = new AdManager(this, advList);
        adManager.setOnImageClickListener(new AdManager.OnImageClickListener() {
            @Override
            public void onImageClick(View view, AdBean advInfo) {

                Intent intent = new Intent(LiveDetailActivity.this, WebViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

        adManager.showAdDialog(Integer.parseInt("90"));
    }

    /**
     * 屏幕旋转时调用此方法
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            rlyt_top.setVisibility(View.VISIBLE);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            AppUtils.setEvent(LiveDetailActivity.this, "LiveFullScreen", "直播全屏");
//            rlyt_top.setVisibility(View.GONE);
        }
//        full_screen.setVisibility(View.GONE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ScreenUtils.isLandScape(this)) {
                TXMediaManager.instance(LiveDetailActivity.this).mPlayerView1.backPress();
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            } else {
                if (currentFragment != null) {
                    fragmentManager.beginTransaction().remove(currentFragment).commit();
                    currentFragment = null;
                }
                finish();
            }
        }
        return false;

    }
}
