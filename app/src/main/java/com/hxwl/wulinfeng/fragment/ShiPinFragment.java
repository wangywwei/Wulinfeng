package com.hxwl.wulinfeng.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudViewViewImp;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.CenterProgressWebView;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.BiSaiShiPinActivity;
import com.hxwl.wulinfeng.activity.BiSaiShiPinTypeActivity;
import com.hxwl.wulinfeng.activity.VideoDetailActivity;
import com.hxwl.wulinfeng.adapter.HorizAdapter;
import com.hxwl.wulinfeng.adapter.ShiPinAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.ShiPinAboveList;
import com.hxwl.wulinfeng.bean.ShiPinBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.OnClickEventUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.width;
import static com.hxwl.common.tencentplay.tencentCloud.TXCloudViewViewA.CURRENT_STATE_PLAYING;
import static com.hxwl.common.tencentplay.tencentCloud.TXMediaManager.ctx;
import static com.hxwl.wulinfeng.R.id.view;

/**
 * Created by Allen on 2017/5/26.
 * 首页 -- 视频  界面
 */

public class ShiPinFragment extends BaseFragment implements ShiPinAdapter.VideoListener, View.OnClickListener {

    private View frag_shipin; //总试图
    //=======横向listview和数据
    private RecyclerView rlv_hor_recy;
    private HorizAdapter horizAdapter;
    private List<ShiPinAboveList> beanDataList = new ArrayList<>();

    //========竖向listview和数据====
    private ListView listView;
    private ShiPinAdapter shipinAdapter;
    private List<ShiPinBean> listData = new ArrayList<>();

    //============
    private int page = 1;
    private boolean isRefresh = true;
    private String num;

    private IMediaDataVideoView videoView;
    private int m_Position = 0;
    private View header;
    private ImageView iv_video_bg;
    private ImageView iv_start;
    private RelativeLayout rlyt_video_bg;
    private RelativeLayout videoContainer;
    private View ic_net_warn;
    private Button btn1;
    private CommonSwipeRefreshLayout common_layout;
    private Activity activity;

    private ShiPinAboveList beanData = null;
    private TextView tv_huifang;
    private TextView tv_jijin;
    private TextView tv_huaxu;
    private RelativeLayout rl_tableyout;
    private TextView tv_canyuyue;
    private EmptyViewHelper emptyViewHelper;

    private CenterProgressWebView mWebView;
    private int width;
    private int height;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_shipin == null) {
            frag_shipin = inflater.inflate(R.layout.frag_shipin, container, false);
            activity = getActivity();
            initView();
            initData(page);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_shipin.getParent();
            if (parent != null) {
                parent.removeView(frag_shipin);
            }
        }
        return frag_shipin;
    }

    //共外部调用
    public void resetData() {
        if (frag_shipin != null) {
            page = 1;
            isRefresh = true;
            initData(page);
        }
    }

    public void initData(int page) {
        if (!SystemHelper.isConnected(activity)) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }
        if (frag_shipin != null) {
            initHdata(page);
        }
    }

    /**
     * 初始化竖向listview的数据
     */
    private void initHdata(final int page) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if (isRefresh && page == 1) {//刷新
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(), ShiPinBean.class));
                    } else {//加载
                        listData.addAll(JSON.parseArray(result.getData(), ShiPinBean.class));
                    }
                    if (activity != null) {
                        common_layout.setRefreshing(false);
                        common_layout.setLoadMore(false);
                        shipinAdapter.notifyDataSetChanged();
                    }
                } else if (result != null && result.getStatus().equals("empty")) {
                    if (isRefresh && page == 1) {
                        listData.clear();
                        shipinAdapter.notifyDataSetChanged();
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    UIUtils.showToast("没有更多了");
                } else {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    emptyViewHelper.setType(1);
                }
            }
        }, "method=" + NetUrlUtils.ziXun_bel_shipin + page + getType());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page + "");
        map.put("num", num);
        map.put("saishiId", getType()); // 赛事编号 做过滤用的
        map.put("method", NetUrlUtils.ziXun_bel_shipin);
        tasker.execute(map);

    }

    private void initView() {
        common_layout = (CommonSwipeRefreshLayout) frag_shipin.findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                initHeadVideoData();
                initHdata(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initHeadVideoData();
                initHdata(page);
            }
        });
        listView = (ListView) frag_shipin.findViewById(R.id.xlv_shipin_list);
        header = LayoutInflater.from(activity).inflate(R.layout.shipin_head, (ViewGroup) frag_shipin.findViewById(android.R.id.content), false);
        initHeadVideoView();
        initHeadVideoData();
        rlv_hor_recy = (RecyclerView) header.findViewById(R.id.rlv_hor_recy);
        horizAdapter = new HorizAdapter(activity, beanDataList, PlayVideoClick);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlv_hor_recy.setLayoutManager(layout);
        rlv_hor_recy.setAdapter(horizAdapter);
        shipinAdapter = new ShiPinAdapter(activity, listData);
        listView.setAdapter(shipinAdapter);
        emptyViewHelper = new EmptyViewHelper(listView, (FrameLayout) frag_shipin.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initHeadVideoData();
                initData(page);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //ll_item  ShiPinBean
                ShiPinBean bean = null;
                if (view instanceof FrameLayout) {
                    bean = (ShiPinBean) view.getTag();
                } else {
                    bean = (ShiPinBean) view.findViewById(R.id.fl_item).getTag();
                }
                if (bean == null) {
                    return;
                }
                Intent intent = new Intent(activity, VideoDetailActivity.class);
                intent.putExtra("id", bean.getId());
                startActivity(intent);
            }
        });
        shipinAdapter.setVideoListener(this);

        listView.addHeaderView(header);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (TXCloudPlayerManager.getCurrentJcvd() != null) {
                    TXCloudViewViewImp videoPlayer = (TXCloudViewViewImp) TXCloudPlayerManager.getCurrentJcvd();
                    if (videoPlayer.currentState == CURRENT_STATE_PLAYING) {
                        //如果正在播放
                        int firstVisibleItemPosition = listView.getFirstVisiblePosition();
                        int lastVisibleItemPosition = listView.getLastVisiblePosition();

                        if (firstVisibleItemPosition > videoPlayer.getPosition() || lastVisibleItemPosition < videoPlayer.getPosition()) {
                            //如果第一个可见的条目位置大于当前播放videoPlayer的位置
                            //或最后一个可见的条目位置小于当前播放videoPlayer的位置，释放资源
                            TXMediaManager.instance(getActivity()).videoDestroy();
                            for (int i = 0; i < listData.size(); i++) {
                                listData.get(i).setSelect(false);
                            }
                            shipinAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private View.OnClickListener PlayVideoClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (ShiPinBean info : listData) {
                if (info.isSelect()) {
                    info.setSelect(false);
                    shipinAdapter.notifyDataSetChanged();
                    break;
                }
            }
            ShiPinAboveList dataBean = null;
            if (view instanceof LinearLayout) {
                dataBean = (ShiPinAboveList) view.getTag();
            } else if (view instanceof TextView) {
                dataBean = (ShiPinAboveList) view.findViewById(R.id.ll_layout).getTag();
            } else {
                dataBean = (ShiPinAboveList) view.findViewById(R.id.ll_layout).getTag();
            }

            if (dataBean == null) {
                return;
            }
            String img = dataBean.getImg();
            if (img != null) {
                ImageUtils.getPic(img, iv_video_bg, activity);
            }
            for (ShiPinAboveList data : beanDataList) {
                if (data.getId().equals(dataBean.getId())) {
                    if (data.isPlay()) {
                        ToastUtils.showToast(getActivity(), "视频正在播放中");
                        return;
                    }
                }
            }
            for (ShiPinAboveList data : beanDataList) {
                data.setPlay(false);
            }
            dataBean.setPlay(true);
            StatService.onEvent(getActivity(), "VedioPlayTimes", "视频播放次数", 1);
            TCAgent.onEvent(getActivity(), "VedioPlayTimes", "视频播放次数");
            rlyt_video_bg.setVisibility(View.GONE);
            videoContainer.setVisibility(View.VISIBLE);
            beanData = dataBean;
            initHorPlay(dataBean);
            horizAdapter.notifyDataSetChanged();
        }
    };

    /**
     * 初始化上边播放视频
     *
     * @param dataBean
     */
    private void initHorPlay(ShiPinAboveList dataBean) {
        try {
        if (dataBean.getVideos().getUrl() == null) {
            return;
        }
        if (TXMediaManager.instance(getActivity()).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
            TXMediaManager.instance(getActivity()).videoDestroy();
        }
        if (dataBean.getVideos().getType().equals("1")) {
            if (mWebView == null) {
                mWebView = new CenterProgressWebView(getActivity());
            }
            videoContainer.removeAllViews();
            if (mWebView.getParent() != null) {
                ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            if(width == 0 || height == 0){
                WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
                width = wm.getDefaultDisplay().getWidth();
                height = width * 9 / 16;
            }
            params.width = width;
            params.height = height;

            mWebView.setLayoutParams(params);

            videoContainer.addView(mWebView);
            mWebView.loadUrl(dataBean.getVideos().getUrl());
        } else {

                //添加腾讯点播
                TXMediaManager.instance(getActivity()).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
                TXMediaManager.instance(getActivity()).TXVodPlayAndView(dataBean.getVideos().getUrl(), videoContainer, -1, dataBean.getTitle());
        }
             } catch (Exception e) {

        }
    }

    /**
     * 初始化头布局的信息
     */
    private void initHeadVideoData() {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    header.setVisibility(View.VISIBLE);
                    beanDataList.clear();
                    beanDataList.addAll(JSON.parseArray(result.getData(), ShiPinAboveList.class));
                    if (beanDataList.size() > 0) {
                        beanData = beanDataList.get(0);
                    }
//                    if (beanDataList != null && beanDataList.size() > 0) {
//                        beanDataList.get(0).setPlay(true);
//                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    if (beanDataList != null && beanDataList.size() > 0 && iv_video_bg != null) {
                        ImageUtils.getPic(beanDataList.get(0).getImg(), iv_video_bg, activity);
                    }
                    if (!TextUtils.isEmpty(result.getNum())) {
                        rl_tableyout.setVisibility(View.VISIBLE);
                        tv_canyuyue.setText("0".equals(result.getNum())?"查看所有比赛回放":"本周更新" + result.getNum() + "场比赛回放");
                    } else {
                        rl_tableyout.setVisibility(View.GONE);
                    }
                    horizAdapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {
                    header.setVisibility(View.GONE);
                } else {
                    header.setVisibility(View.GONE);
//                    UIUtils.showToast("服务器返回失败。");
                }
            }
        }, "method=" + NetUrlUtils.ziXun_abo_shipin);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("method", NetUrlUtils.ziXun_abo_shipin);
        tasker.execute(map);

    }

    /**
     * 初始化头布局的界面
     */
    private void initHeadVideoView() {
        iv_video_bg = (ImageView) header.findViewById(R.id.iv_video_bg);
        iv_start = (ImageView) header.findViewById(R.id.iv_start);
        iv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatService.onEvent(getActivity(), "VedioPlayTimes", "视频播放次数", 1);
                TCAgent.onEvent(getActivity(), "VedioPlayTimes", "视频播放次数");
                if (beanData == null) {
                    return;
                }
                rlyt_video_bg.setVisibility(View.GONE);
                videoContainer.setVisibility(View.VISIBLE);
                initHorPlay(beanData);
            }
        });
        rlyt_video_bg = (RelativeLayout) header.findViewById(R.id.rlyt_video_bg);
        videoContainer = (RelativeLayout) header.findViewById(R.id.videoContainer);
        ic_net_warn = header.findViewById(R.id.ic_net_warn);
        btn1 = (Button) ic_net_warn.findViewById(R.id.btn1);

        tv_huifang = (TextView) header.findViewById(R.id.tv_huifang);
        tv_huifang.setOnClickListener(this);
        tv_jijin = (TextView) header.findViewById(R.id.tv_jijin);
        tv_jijin.setOnClickListener(this);
        tv_huaxu = (TextView) header.findViewById(R.id.tv_huaxu);
        tv_huaxu.setOnClickListener(this);
        rl_tableyout = (RelativeLayout) header.findViewById(R.id.rl_tableyout);
        rl_tableyout.setOnClickListener(this);
        tv_canyuyue = (TextView) header.findViewById(R.id.tv_canyuyue);

    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(activity, "视频");
        TCAgent.onPageStart(activity, "视频");
        TXMediaManager.instance(getActivity()).resumeplayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(activity, "视频");
        TCAgent.onPageEnd(activity, "视频");
        if (TXMediaManager.instance(getActivity()).mLivePlayer != null && TXMediaManager.instance(getActivity()).mLivePlayer.isPlaying()) {
            TXMediaManager.instance(getActivity()).pauseplayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.onDestroy();
        }
    }

    public void handleReleaseRes() {
        if (videoView != null) {
            videoView.stopAndRelease();
            videoView.onDestroy();
            videoView = null;
        }
        try {
            //初始化所有数据
            for (int i = 0; i < listData.size(); i++) {
                listData.get(i).setSelect(false);
            }
            shipinAdapter.notifyDataSetChanged();
            for (int i = 0; i < beanDataList.size(); i++) {
                beanDataList.get(i).setPlay(false);
            }
            if (beanDataList.size() > 0) {
                beanDataList.get(0).setPlay(true);
                initHorPlay(beanDataList.get(0));
            }
            rlyt_video_bg.setVisibility(View.VISIBLE);
            videoContainer.setVisibility(View.GONE);

        } catch (Exception e) {

        }
    }

    @Override
    public void playVideo(String url, final RelativeLayout videoContainerAdapter, int position, final int oldposition, String title) {
        if (position == -1) {
            return;
        }
        for (ShiPinAboveList data : beanDataList) {
            data.setPlay(false);
        }
        StatService.onEvent(getActivity(), "VedioPlayTimes", "视频播放次数", 1);
        TCAgent.onEvent(getActivity(), "VedioPlayTimes", "视频播放次数");
        rlyt_video_bg.setVisibility(View.VISIBLE);
        videoContainer.setVisibility(View.GONE);
        horizAdapter.notifyDataSetChanged();

        try {
            m_Position = position;
            //初始化所有数据
            for (int i = 0; i < listData.size(); i++) {
                listData.get(i).setSelect(false);
            }
            listData.get(position).setSelect(true);
            shipinAdapter.notifyDataSetChanged();
            //添加乐视点播
            if (TXMediaManager.instance(getActivity()).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
                TXMediaManager.instance(getActivity()).videoDestroy();
            }
            TXMediaManager.instance(getActivity()).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
            TXMediaManager.instance(getActivity()).TXVodPlayAndView(url, videoContainerAdapter, -1, title, position);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openVideoDetails(String id) {
        if (TextUtils.isEmpty(id)) {
            ToastUtils.showToast(getActivity(),"暂无视频详情");
            return ;
        }
        Intent intent = new Intent(activity, VideoDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void loadWebUrl(String url, int position, int oldposition, RelativeLayout videoContainerAdapter) {
        for (ShiPinAboveList data : beanDataList) {
            data.setPlay(false);
        }
        //添加乐视点播
        if (TXMediaManager.instance(getActivity()).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
            TXMediaManager.instance(getActivity()).videoDestroy();
        }
        StatService.onEvent(getActivity(), "VedioPlayTimes", "视频播放次数", 1);
        TCAgent.onEvent(getActivity(), "VedioPlayTimes", "视频播放次数");
        rlyt_video_bg.setVisibility(View.VISIBLE);
        videoContainer.setVisibility(View.GONE);
        horizAdapter.notifyDataSetChanged();

        if (mWebView == null) {
            mWebView = new CenterProgressWebView(getActivity());
        }
        videoContainerAdapter.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (mWebView.getParent() != null) {
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
        }
        mWebView.setLayoutParams(params);

        //初始化所有数据
        for (int i = 0; i < listData.size(); i++) {
            listData.get(i).setSelect(false);
        }
        listData.get(position).setSelect(true);
        shipinAdapter.notifyDataSetChanged();

        videoContainerAdapter.addView(mWebView);
        mWebView.loadUrl(url);
    }

    @Override
    public boolean isPlaying() {
        if (videoView != null) {
            return videoView.isPlaying();
        }
        return false;
    }

    @Override
    public void resetPlayer() {
        if (videoView != null) {
            videoView.resetPlayer();
        }
    }

    @Override
    public void startResume() {
        if (videoView != null) {
            videoView.onResume();
        }
    }

    /**
     * 屏幕旋转时调用此方法
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (videoView != null) {
            videoView.onConfigurationChanged(newConfig);
        }
    }

    ///
    private static String TYPE_JIJIN = "1";//集锦
    private static String TYPE_HUAXU = "2";//花絮

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.tv_huifang:
                intent = new Intent(getActivity(), BiSaiShiPinActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_jijin:
                intent = new Intent(getActivity(), BiSaiShiPinTypeActivity.class);
                intent.putExtra("type", TYPE_JIJIN);
                startActivity(intent);
                break;
            case R.id.tv_huaxu:
                intent = new Intent(getActivity(), BiSaiShiPinTypeActivity.class);
                intent.putExtra("type", TYPE_HUAXU);
                startActivity(intent);
                break;
            case R.id.rl_tableyout:
                StatService.onEvent(getActivity(), "IndexVideoMatchvideo", "首页视频下比赛回放", 1);
                TCAgent.onEvent(getActivity(), "IndexVideoMatchvideo", "首页视频下比赛回放");
                intent = new Intent(getActivity(), BiSaiShiPinActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
