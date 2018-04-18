package com.hxwl.wulinfeng.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.CenterProgressWebView;
import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.VideosaichenBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.VideoDetilsAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.HuiFuBean;
import com.hxwl.wulinfeng.bean.VideoListDatilsBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.wulin.CusListView;
import com.jaeger.library.StatusBarUtil;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/29.
 * 视频详情  -- 首页
 */
public class VideoDetailActivity extends BaseActivity {
    private String id;

    public static Intent getIntent(Context context,String id){
        Intent intent=new Intent(context,VideoDetailActivity.class);
        intent.putExtra("id",id);
        return intent;
    }
    CenterProgressWebView mWebView;
    private View header;
    private CusListView expandableList;
    private TextView tvName;
    private TextView tvTime;
    private TextView tvTitle;
    private ImageView ivTouxiang;
    private LinearLayout lv_picture;
    private com.hxwl.common.utils.ShowDialog showDialog;

    private List<VideoListDatilsBean> listData = new ArrayList<VideoListDatilsBean>();

    int picHeight = 0;
    int picWidth = 0;
    //获取屏幕宽度
    private int m_ScreenWidth;

    private int page = 1;
    public boolean isRefresh = true;
    private int huifuState = 0; //默认是盖楼-0！ 1是回复

    //回复传递参数
    private String zhuId = "";//心得id
    private String toUid = "";//回复谁
    private String type = "1";

    private RelativeLayout frame_msg_ll;
    private TextView chat_btn_create_card;
    private EditText chat_et_create_context;
    private HuiFuBean huiFuBean;
    private VideosaichenBean.DataBean bean;
    private VideoDetilsAdapter adapter;
    private VideoListDatilsBean info;

    private RelativeLayout videoContainer;
    private IMediaDataVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videodetail_activity);
        StatusBarUtil.setColor(VideoDetailActivity.this,getResources().getColor(R.color.tab_color),0);
        id = getIntent().getStringExtra("id");
        TXMediaManager.instance(VideoDetailActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
        initView();
        initHData();
        initListData(page);
    }


    private void initHData() {
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_GETSCHEDULEPLAYBACKINFO)
                .addParams("scheduleId",id)
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
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                VideosaichenBean videosaichenBean = gson.fromJson(response, VideosaichenBean.class);
                                if (videosaichenBean.getCode().equals("1000")){
                                    bean=videosaichenBean.getData();
                                    initHView();

                                }else if (videosaichenBean.getCode().equals("2002")||videosaichenBean.getCode().equals("2003")){
                                    UIUtils.showToast(videosaichenBean.getMessage());
                                    startActivity(LoginActivity.getIntent(VideoDetailActivity.this));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });


    }

    private void initHView() {
        if (bean == null) {
            return;
        }
//        ImageUtils.getCirclePic(bean.getHuser().getHead_url(), ivTouxiang, this);
        //发帖人的名字
//        tvName.setText(bean.getVideoName().getNickname());
        tvTitle.setText(bean.getVideoName());
        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
//        tvTime.setText(TimeUtiles.getTimes(bean.getTime()));
//        int nType = Integer.parseInt(bean.getVideos().getType().trim());
        String url = bean.getVideoUrl();
        TXMediaManager.instance(VideoDetailActivity.this).TXVodPlayForDP(url ,videoContainer
                ,3,bean.getVideoName()) ;
//        switch (nType){
//            case 1:
//                loadAppUrl(url);
//                break;
//            default:
//                //添加腾讯点播
//                TXMediaManager.instance(VideoDetailActivity.this).TXVodPlayForDP(url ,videoContainer
//                        ,3,bean.getVideoName()) ;
//                break;
//        }
    }
    /**
     * 加载URL
     *
     * @param url
     */
    private void loadAppUrl(String url) {
        if (mWebView == null) {
            mWebView = new CenterProgressWebView(this);
        }
        videoContainer.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mWebView.setLayoutParams(params);
        videoContainer.addView(mWebView);
        mWebView.loadUrl(url);
    }

    private void notiAdapter() {
        adapter.notifyDataSetChanged();
        if (adapter != null && adapter.getGroupCount() > 0) {
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                expandableList.expandGroup(i);
            }
        }
    }


    private void initListData(final int page) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                VideoDetailActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if (isRefresh) {
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(), VideoListDatilsBean.class));
                    } else {
                        listData.addAll(JSON.parseArray(result.getData(), VideoListDatilsBean.class));
                    }
                    for (VideoListDatilsBean info : listData) {
                        String zhuID = info.getId();
                        for (HuiFuBean huifu : info.getHuifu()) {
                            if (TextUtils.isEmpty(huifu.getZhu_id())) {
                                huifu.setZhu_id(zhuID);
                            }
                        }
                    }
                    expandableList.onRefreshComplete();
                    expandableList.onLoadMoreComplete();
                    //默认展开全部
                    notiAdapter() ;
                } else if (result != null && result.getStatus().equals("empty")) {
                    expandableList.onRefreshComplete();
                    expandableList.onLoadMoreComplete();
                    if(page != 1){
                        UIUtils.showToast("没有更多了...");
                    }
                } else {
                    expandableList.onRefreshComplete();
                    expandableList.onLoadMoreComplete();
                    UIUtils.showToast("服务器异常");
                }
            }
        }, "method=" + NetUrlUtils.video_detail_list + page + id);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("page", page);
        map.put("method", NetUrlUtils.video_detail_list);
        tasker.execute(map);

    }

    private void initView() {
        showDialog = new ShowDialog(VideoDetailActivity.this);
        header = View.inflate(VideoDetailActivity.this, R.layout.video_detail_header, null);
        ImageView user_icon = (ImageView) header.findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        expandableList = (CusListView) findViewById(R.id.cusListView1);
        expandableList.addHeaderView(header);
        tvName = (TextView) header.findViewById(R.id.tv_name);
        tvTime = (TextView) header.findViewById(R.id.tv_time);
        tvTitle = (TextView) header.findViewById(R.id.tv_title);
        ivTouxiang = (ImageView) header.findViewById(R.id.iv_touxiang);
        lv_picture = (LinearLayout) header.findViewById(R.id.lv_Picture);
        videoContainer = (RelativeLayout) header.findViewById(R.id.videoContainer);

        adapter = new VideoDetilsAdapter(VideoDetailActivity.this, listData, replyToCommentListener, id);
        expandableList.setAdapter(adapter);
        initGaiLouData();

        showDialog = new ShowDialog(VideoDetailActivity.this);
        // 评论对话框
        frame_msg_ll = (RelativeLayout) findViewById(R.id.frame_msg_ll);

        //评论发送按钮
        chat_btn_create_card = (TextView) findViewById(R.id.chat_btn_create_card);
        chat_btn_create_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    startActivity(new Intent(VideoDetailActivity.this,LoginforCodeActivity.class));
                    return ;
                }
                String content = chat_et_create_context.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(VideoDetailActivity.this, "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (0 == huifuState) {//盖楼
                    if (info == null) {
                        return;
                    }
                    AppUtils.setEvent(VideoDetailActivity.this,"VedioPublish","视频-跟帖");
                    sendCommentNoteZ(zhuId, toUid, content, info);
                } else {//回复现有的帖子
                    if (huiFuBean == null) {
                        return;
                    }
                    AppUtils.setEvent(VideoDetailActivity.this,"VedioReply","视频-回复");
                    sendCommentNote(zhuId, toUid, content, huiFuBean);
                }
                chat_et_create_context.setText("");
                chat_et_create_context.setHint("我也说两句...");
                InputMethodManager imm = (InputMethodManager) VideoDetailActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        // 评论输入框
        chat_et_create_context = (EditText) findViewById(R.id.chat_et_create_context);
        chat_et_create_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat_et_create_context.setFocusable(true);
                chat_et_create_context.setFocusableInTouchMode(true);
                chat_et_create_context.requestFocus();
                chat_et_create_context.requestFocusFromTouch();

                StatService.onEvent(VideoDetailActivity.this,"VedioInput","视频评论输入框",1);
                TCAgent.onEvent(VideoDetailActivity.this,"VedioInput","视频评论输入框");
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    startActivity(new Intent(VideoDetailActivity.this,LoginforCodeActivity.class));
                    return ;
                }else{
                    InputMethodManager imm = (InputMethodManager) VideoDetailActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(chat_et_create_context, 0);
                }
            }
        });
        //滑动隐藏布局
        expandableList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                chat_et_create_context.setHint("我也说两句...");

                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = VideoDetailActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
//                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) VideoDetailActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                expandableList.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });

        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        //设置子条目点击事件
        expandableList.setOnChildClickListener(new VideoDetailActivity.expandableListListener());
        expandableList.setonRefreshListener(new CusListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新操作
                page = 1;
                isRefresh = true;
                initListData(page);


            }
        });
        expandableList.setonLoadMoreListener(new CusListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initListData(page);
            }
        });
    }


    private void initGaiLouData() {
        info = new VideoListDatilsBean();
        info.setHead_url(MakerApplication.instance().getHeadPic());
        info.setUid(MakerApplication.instance().getUid());
        info.setNickname(MakerApplication.instance().getUsername());
    }

    /**
     * /**
     * 回复评论的监听（回复楼主） 点击左边的气泡的时候触发（评论）
     */
    private View.OnClickListener replyToCommentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String uid = MakerApplication.instance().getUid();
            String loginKey = MakerApplication.instance().getLoginKey();
            if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                startActivity(new Intent(VideoDetailActivity.this,LoginforCodeActivity.class));
                return ;
            }
            chat_et_create_context.setHint("我也说两句...");
            VideoListDatilsBean itemNotes = null;
            if (v instanceof ImageView) {
                itemNotes = (VideoListDatilsBean) v.getTag();
            } else {
                itemNotes = (VideoListDatilsBean) v.findViewById(R.id.btn_comment_reply).getTag();
            }
            if (itemNotes == null) {
                return;
            }
            huiFuBean = new HuiFuBean();
            huiFuBean.setFrom_head_url(MakerApplication.instance().getHeadPic());
            huiFuBean.setFrom_nickname(MakerApplication.instance().getUsername());
            huiFuBean.setFrom_uid(MakerApplication.instance().getUid());
            huiFuBean.setZhu_id(itemNotes.getId());
            huiFuBean.setTo_uid(itemNotes.getUid());
            huifuState = 1;
            showKeyBoard(itemNotes.getId(), itemNotes.getUid(), "1");
        }
    };

    /**
     * 为ExpandableListView编写监听器
     *
     * @author Allen
     */
    class expandableListListener implements ExpandableListView.OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            String uid = MakerApplication.instance().getUid();
            String loginKey = MakerApplication.instance().getLoginKey();
            if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                startActivity(new Intent(VideoDetailActivity.this,LoginforCodeActivity.class));
                return false;
            }
            final HuiFuBean itemNoteComment = listData.get(groupPosition).getHuifu().get(childPosition);
            if (itemNoteComment == null) {
            } else {
                chat_et_create_context.setHint("回复"+itemNoteComment.getFrom_nickname()+":");
                huiFuBean = new HuiFuBean();
                huiFuBean.setFrom_head_url(MakerApplication.instance().getHeadPic());
                huiFuBean.setFrom_nickname(MakerApplication.instance().getUsername());
                huiFuBean.setTo_nickname(itemNoteComment.getFrom_nickname());
                huiFuBean.setFrom_uid(MakerApplication.instance().getUid());
                huiFuBean.setZhu_id(itemNoteComment.getZhu_id());
                huiFuBean.setTo_uid(itemNoteComment.getFrom_uid());
                huifuState = 1;
                showKeyBoard(itemNoteComment.getZhu_id(), itemNoteComment.getFrom_uid(), "2");
            }
            return false;
        }
    }

    /**
     * @param zhuId 帖子的id
     * @param toUid 给谁发的 id
     * @param type  1跟帖 2回复
     */
    public void showKeyBoard(String zhuId, String toUid, String type) {
        frame_msg_ll.setVisibility(View.VISIBLE);
        chat_et_create_context.setFocusable(true);
        chat_et_create_context.setFocusableInTouchMode(true);
        chat_et_create_context.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) chat_et_create_context.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(chat_et_create_context, 0);

        this.zhuId = zhuId;
        this.toUid = toUid;
        this.type = type;
    }

    /**
     * 发表评论方法  即时刷新一条数据
     *
     * @param zhuId 帖子id
     * @param toUid 给谁评论
     * @param msg   发送信息
     */
    private void sendCommentNote(final String zhuId, String toUid, final String msg, final HuiFuBean bean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                VideoDetailActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    String id = result.getInsertid();
                    if (StringUtils.isEmpty(id)) {
                        return;
                    }
                    bean.setId(id);
                    bean.setMsg(msg);
                    for (VideoListDatilsBean itemNotesdata : listData) {
                        if (itemNotesdata.getId().equals(bean.getZhu_id())) {
                            if (itemNotesdata.getHuifu() != null) {
                                itemNotesdata.getHuifu().add(bean);
                            } else {
                                List<HuiFuBean> huifu = new ArrayList<HuiFuBean>();
                                huifu.add(bean);
                                itemNotesdata.setHuifu(huifu);
                            }
                        }
                    }
                    huifuState = 0;
                    Toast.makeText(VideoDetailActivity.this, result.getMsg(),
                            Toast.LENGTH_SHORT).show();
                    showDialog.dismissProgressDialog();
                    notiAdapter() ;
                } else {
                    Toast.makeText(VideoDetailActivity.this, "评论失败",
                            Toast.LENGTH_SHORT).show();
                    showDialog.dismissProgressDialog();
                }
            }
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("uid", MakerApplication.instance().getUid());
        map.put("loginKey", MakerApplication.instance().getLoginKey());
        map.put("typeGentie", "2");//回复
        map.put("type", "3");
        map.put("id", id);//主贴ID
        map.put("genId", zhuId);//跟帖ID
        map.put("toUid", toUid);
        map.put("msg", msg);
        map.put("method", NetUrlUtils.zixun_gentiehuifu);
        tasker.execute(map);


    }

    private void sendCommentNoteZ(final String zhuId, String toUid, final String msg, final VideoListDatilsBean info) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                VideoDetailActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    String id = result.getInsertid();
                    if (StringUtils.isEmpty(id)) {
                        return;
                    }
                    info.setId(id);
                    info.setMsg(msg);
                    info.setTime(result.getTime());
                    listData.add(0, info);
                    huifuState = 0;
                    Toast.makeText(VideoDetailActivity.this, "评论成功",
                            Toast.LENGTH_SHORT).show();
                    showDialog.dismissProgressDialog();
                    notiAdapter() ;
                    initGaiLouData();
                } else {
                    Toast.makeText(VideoDetailActivity.this, "评论失败",
                            Toast.LENGTH_SHORT).show();
                    showDialog.dismissProgressDialog();
                }
            }
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("uid", MakerApplication.instance().getUid());
        map.put("loginKey", MakerApplication.instance().getLoginKey());
        map.put("typeGentie", "1");//盖楼
        map.put("type", "3");//
        map.put("id", id);//主贴ID
        map.put("msg", msg);
        map.put("method", NetUrlUtils.zixun_gentiehuifu);
        tasker.execute(map);

    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtils.setEvent(this,"VedioPageOpen","视频详情打开");
        StatService.onPageStart(this, "视频详情");
        TCAgent.onPageStart(this, "视频详情");
        if (mWebView != null) {
            mWebView.onResume(); // 暂停网页中正在播放的视频
        }

        TXMediaManager.instance(VideoDetailActivity.this).resumeplayer();
    }

    @Override
    protected void onDestroy() {
        if(TXMediaManager.instance(VideoDetailActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null){
            TXMediaManager.instance(VideoDetailActivity.this).videoDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"视频详情");
        TCAgent.onPageEnd(this,"视频详情");
        if (mWebView != null) {
            mWebView.onPause(); // 暂停网页中正在播放的视频
        }
        if(TXMediaManager.instance(VideoDetailActivity.this).mLivePlayer != null && TXMediaManager.instance(VideoDetailActivity.this).mLivePlayer.isPlaying()){
            TXMediaManager.instance(VideoDetailActivity.this).pauseplayer();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (ScreenUtils.isLandScape(this)) {
                TXMediaManager.instance(VideoDetailActivity.this).mPlayerView1.backPress() ;
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }else{
                finish();
            }
        }
        return false;
    }

}
