package com.hxwl.wulinfeng.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudViewViewImp;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.CenterProgressWebView;
import com.hxwl.common.utils.EmptyRecyclerView;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.BaseZuiXinAdapter;
import com.hxwl.wulinfeng.adapter.ImpZuiXinAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.AllHomeHunHeBean;
import com.hxwl.wulinfeng.bean.HomeBannerImageBean;
import com.hxwl.wulinfeng.bean.HomeHunHeBean;
import com.hxwl.wulinfeng.bean.HomeZhuanTiBean;
import com.hxwl.wulinfeng.bean.HomeZuixinHuifangBean;
import com.hxwl.wulinfeng.bean.MainPageTuiJianBean;
import com.hxwl.wulinfeng.bean.SaichengBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.LetvPlayUtils;
import com.hxwl.wulinfeng.util.LocalUrlParseUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.lecloud.skin.videoview.vod.UIVodVideoView;
import com.tendcloud.tenddata.TCAgent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hxwl.common.tencentplay.tencentCloud.TXCloudViewViewA.CURRENT_STATE_PLAYING;

/**
 * Created by Allen on 2017/5/26.
 * 最新栏目fragment
 */

public class ZuiXinFragment extends BaseFragment implements BaseZuiXinAdapter.DetalisCallBack, ImpZuiXinAdapter.VideoListener {
    private ArrayList<HomeBannerImageBean> bannerList = new ArrayList<>();//banner数据
    private WeakReference<Activity> m_WeakReference;
    private Activity activity;
    private View frag_faxian;
    private EmptyRecyclerView xlv_toutiao_content;
    private CommonSwipeRefreshLayout common_layout;

    //头条数据
    private MainPageTuiJianBean m_MainPageTuiJianBean;
    private List<MainPageTuiJianBean.DataBean.NewsBean> m_NewsBeanList = new ArrayList<>();
    private List<SaichengBean> tuijianZhiboBeen = new ArrayList<>();
    private List<HomeZuixinHuifangBean> tuijianHuifangBean = new ArrayList<>();
    private List<HomeZhuanTiBean> tuijianZhuanTiBean = new ArrayList<>();
    private List<AllHomeHunHeBean> tuijianHunHeBean = new ArrayList<>();

    private boolean isRefresh = true;
    private String lastNewsId = "0";
    private int page = 1;
    private BaseZuiXinAdapter m_baseZuiXinAdapter;
    private EmptyViewHelper emptyViewHelper;

    private CenterProgressWebView mWebView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        m_WeakReference = new WeakReference<Activity>(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 设置窗口透明，可避免播放器SurfaceView初始化时的黑屏现象
        activity.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        // 视频播放界面，保持屏幕常亮利于视频观看体验
//        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (frag_faxian == null) {
            frag_faxian = inflater.inflate(R.layout.frag_zuixin, container, false);
            initView();
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_faxian.getParent();
            if (parent != null) {
                parent.removeView(frag_faxian);
            }
        }
        return frag_faxian;
    }

    //共外部调用
    public void resetData() {
        if (frag_faxian != null) {
            page = 1;
            isRefresh = true;
            initData(page);
        }
    }

    public void initData(int page) {
        if (frag_faxian != null) {
            initHunheData(page);
        }
    }

    /**
     * 初始化混合列表
     */
    private void initHunheData(final int page) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    HomeHunHeBean bean = JSON.parseObject(result.getData(), HomeHunHeBean.class);
                    lastNewsId = bean.getLastNewsId();
                    if (isRefresh && page == 1) {
                        tuijianHunHeBean.clear();
                        tuijianHunHeBean.addAll(bean.getNews());
                    } else {
                        tuijianHunHeBean.addAll(bean.getNews());
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    m_baseZuiXinAdapter.setData(tuijianHunHeBean);
                } else if (result != null && result.getStatus().equals("empty")) {
                    if (isRefresh && page == 1) {
                        tuijianHunHeBean.clear();
                        m_baseZuiXinAdapter.setData(tuijianHunHeBean);
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    UIUtils.showToast("没有更多了");
                } else {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                }
            }
        }, "method=" + NetUrlUtils.faxian_bisaishipin + page + getType() + lastNewsId);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page + "");
        map.put("lastNewsId", lastNewsId);
        map.put("saishiId", getType()); // 赛事编号 做过滤用的
        map.put("method", NetUrlUtils.zuiXin_news);
        tasker.execute(map);

    }

    /**
     * 初始化专题列表
     */
    private void initZhuantiData() {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    tuijianZhuanTiBean.clear();
                    tuijianZhuanTiBean.addAll(JSON.parseArray(result.getData(), HomeZhuanTiBean.class));
                    m_baseZuiXinAdapter.setZhuanTiData(tuijianZhuanTiBean);
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {

                }
            }
        }, "method=" + NetUrlUtils.zuiXin_zhuanti + MakerApplication.instance().getUid());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("uid", MakerApplication.instance().getUid());
        map.put("loginKey", MakerApplication.instance().getLoginKey());
        map.put("method", NetUrlUtils.zuiXin_zhuanti);
        tasker.execute(map);

    }

    /**
     * 初始化回放列表
     */
    private void initHuifangData() {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    tuijianHuifangBean.clear();
                    tuijianHuifangBean.addAll(JSON.parseArray(result.getData(), HomeZuixinHuifangBean.class));
                    m_baseZuiXinAdapter.setHuifangData(tuijianHuifangBean);
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {

                }
            }
        }, "method=" + NetUrlUtils.zuiXin_huifang);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("method", NetUrlUtils.zuiXin_huifang);
        tasker.execute(map);

    }

    /**
     * 初始化推荐列表
     */
    private void initTuijianData() {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    tuijianZhiboBeen.clear();
                    tuijianZhiboBeen.addAll(JSON.parseArray(result.getData(), SaichengBean.class));
                    m_baseZuiXinAdapter.setTuijianData(tuijianZhiboBeen, result.getNum());
                } else if (result != null && result.getStatus().equals("empty")) {
                    tuijianZhiboBeen.clear();
                    m_baseZuiXinAdapter.setTuijianData(tuijianZhiboBeen, result.getNum());
                } else {

                }
            }
        }, "method=" + NetUrlUtils.zuiXin_tuijianzhibo + MakerApplication.instance().getUid());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("uid", MakerApplication.instance().getUid());
        map.put("loginKey", MakerApplication.instance().getLoginKey());
        map.put("method", NetUrlUtils.zuiXin_tuijianzhibo);
        tasker.execute(map);

    }

    /**
     * 初始化banner列表
     */
    private List<HomeBannerImageBean> data2EntityList = new ArrayList<>();
    private void initBannerData() {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                activity, false, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    data2EntityList.clear();
                    data2EntityList.addAll(JSON.parseArray(result.getData(), HomeBannerImageBean.class));
                    m_baseZuiXinAdapter.setBannerData(data2EntityList);
                } else if (result != null && result.getStatus().equals("empty")) {

                } else{

                }
            }
        }, "method=Home.getTopImg" + "|banner");
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("method", "Home/getTopImg");
        tasker.execute(params);
    }

    private void initData() {
        if (!SystemHelper.isConnected(activity)) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }
        initBannerData();
        initTuijianData();
        initHuifangData();
        initZhuantiData();
        initHunheData(page);
    }

    private void initView() {
        xlv_toutiao_content = (EmptyRecyclerView) frag_faxian.findViewById(R.id.xlv_toutiao_content);
//        int spacingInPixels = 10;
//        xlv_toutiao_content.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        common_layout = (CommonSwipeRefreshLayout) frag_faxian.findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                initData();
//                initHunheData(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initHunheData(page);
            }
        });

        //请求数据
//        ll_pb.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        xlv_toutiao_content.setLayoutManager(linearLayoutManager);
        m_baseZuiXinAdapter = new ImpZuiXinAdapter(m_WeakReference.get(), getActivity());
        ((ImpZuiXinAdapter) m_baseZuiXinAdapter).setVideoListener(this);
        xlv_toutiao_content.setAdapter(m_baseZuiXinAdapter);
        emptyViewHelper = new EmptyViewHelper(xlv_toutiao_content, (FrameLayout) frag_faxian.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initData();
            }
        });
        m_baseZuiXinAdapter.setDetalisCallBack(this);
        xlv_toutiao_content.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (TXCloudPlayerManager.getCurrentJcvd() != null) {
                    TXCloudViewViewImp videoPlayer = (TXCloudViewViewImp) TXCloudPlayerManager.getCurrentJcvd();
                    if (videoPlayer.currentState == CURRENT_STATE_PLAYING) {
                        //如果正在播放
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) xlv_toutiao_content.getLayoutManager();
                        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();

                        if (firstVisibleItemPosition > videoPlayer.getPosition() || lastVisibleItemPosition < videoPlayer.getPosition()) {
                            //如果第一个可见的条目位置大于当前播放videoPlayer的位置
                            //或最后一个可见的条目位置小于当前播放videoPlayer的位置，释放资源
                            TXMediaManager.instance(getActivity()).videoDestroy();
                            for (int i = 0; i < tuijianHunHeBean.size(); i++) {
                                tuijianHunHeBean.get(i).setSelect(false);
                            }
                            for (int i = 0; i < tuijianHuifangBean.size(); i++) {
                                tuijianHuifangBean.get(i).setSelect(false);
                            }
                            m_baseZuiXinAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }


    @Override
    public void playVideo(String url, RelativeLayout videoContainer, int position, int oldposition, String title) {
        if(position == -1){
            return ;
        }
        for (int i = 0; i < tuijianHunHeBean.size(); i++) {
            tuijianHunHeBean.get(i).setSelect(false);
        }
        tuijianHunHeBean.get(oldposition).setSelect(true);
        m_baseZuiXinAdapter.notifyDataSetChanged();
        if(TXMediaManager.instance(getActivity()).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null){
            TXMediaManager.instance(getActivity()).videoDestroy();
        }
        TXMediaManager.instance(getActivity()).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
        TXMediaManager.instance(getActivity()).TXVodPlayAndView(url,videoContainer,-1 ,title ,position) ;
    }

    @Override
    public void playVideoForHG(String url, RelativeLayout videoContainer, int position, int oldposition, String title) {
        if(position == -1){
            return ;
        }
        for (int i = 0; i < tuijianHuifangBean.size(); i++) {
            tuijianHuifangBean.get(i).setSelect(false);
        }
        tuijianHuifangBean.get(oldposition).setSelect(true);
        m_baseZuiXinAdapter.notifyDataSetChanged();
        if(TXMediaManager.instance(getActivity()).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null){
            TXMediaManager.instance(getActivity()).videoDestroy();
        }
        TXMediaManager.instance(getActivity()).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
        TXMediaManager.instance(getActivity()).TXVodPlayAndView(url,videoContainer,-1 ,title ,position) ;
    }

    @Override
    public void openVideoDetails(String id) {

    }

    @Override
    public void loadWebUrl(String url, int position, int oldposition, RelativeLayout videoContainer) {
        if (mWebView == null) {
            mWebView = new CenterProgressWebView(getActivity());
        }
        videoContainer.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if(mWebView.getParent() != null){
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
        }
        mWebView.setLayoutParams(params);

        for (int i = 0; i < tuijianHunHeBean.size(); i++) {
            tuijianHunHeBean.get(i).setSelect(false);
        }
        tuijianHunHeBean.get(position).setSelect(true);
        m_baseZuiXinAdapter.notifyDataSetChanged();

        videoContainer.addView(mWebView);
        mWebView.loadUrl(url);
    }

    @Override
    public void loadWebUrlForHG(String url, int position, int oldposition, RelativeLayout videoContainer) {
        if (mWebView == null) {
            mWebView = new CenterProgressWebView(getActivity());
        }
        videoContainer.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if(mWebView.getParent() != null){
            ((ViewGroup)mWebView.getParent()).removeView(mWebView);
        }
        mWebView.setLayoutParams(params);

        for (int i = 0; i < tuijianHuifangBean.size(); i++) {
            tuijianHuifangBean.get(i).setSelect(false);
        }
        tuijianHuifangBean.get(position).setSelect(true);
        m_baseZuiXinAdapter.notifyDataSetChanged();

        videoContainer.addView(mWebView);
        mWebView.loadUrl(url);
    }

    @Override
    public void getDetalisData(int viewType, int secondViewType, int position) {

    }

    public void handleReleaseRes() {
        try {
            //初始化所有数据
            for (int i = 0; i < tuijianHuifangBean.size(); i++) {
                tuijianHuifangBean.get(i).setSelect(false);
            }
            //初始化所有数据
            for (int i = 0; i < tuijianHunHeBean.size(); i++) {
                tuijianHunHeBean.get(i).setSelect(false);
            }
            m_baseZuiXinAdapter.notifyDataSetChanged();
        } catch (Exception e) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(activity, "首页最新");
        TCAgent.onPageStart(activity, "首页最新");
        TXMediaManager.instance(getActivity()).resumeplayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(activity, "首页最新");
        TCAgent.onPageEnd(activity, "首页最新");
        if(TXMediaManager.instance(getActivity()).mLivePlayer != null && TXMediaManager.instance(getActivity()).mLivePlayer.isPlaying()){
            TXMediaManager.instance(getActivity()).pauseplayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
