package com.hxwl.newwlf.home.home.intent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.CenterProgressWebView;
import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.DuiZhengPLBean;
import com.hxwl.newwlf.modlebean.DuizhengXQBean;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.DateUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.NetWorkUtils;
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
public class DuizhengVideoActivity extends BaseActivity {
    private String newsId;
    private View ic_net_warn;
    private Button btn1;

    public static Intent getIntent(Context context,String newsId){
        Intent intent=new Intent(context,DuizhengVideoActivity.class);
        intent.putExtra("newsId",newsId);
        return intent;
    }
    CenterProgressWebView mWebView;
    private View header;
    private CusListView expandableList;
    private TextView tvName;
    private TextView tvTime;
    private TextView tv_coutent;
    private TextView tv_author;
    private TextView tvTitle;
    private ImageView ivTouxiang;
    private LinearLayout lv_picture;
    private ShowDialog showDialog;

    private List<DuiZhengPLBean.DataBean> listData = new ArrayList<>();

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
    private DuiZhengPLBean.DataBean.ReplyListBean huiFuBean;
    private DuizhengXQBean.DataBean bean;
    private DuizhengVideoDetilsAdapter adapter;
    private DuiZhengPLBean.DataBean info;

    private RelativeLayout videoContainer;
    private IMediaDataVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videodetail_activity);
        StatusBarUtil.setColor(DuizhengVideoActivity.this,getResources().getColor(R.color.tab_color),0);
        newsId = getIntent().getStringExtra("newsId");
        TXMediaManager.instance(DuizhengVideoActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
        initView();
        initHData();
        initListData(page);
    }


    private void initHData() {
        OkHttpUtils.post()
                .url(URLS.AGAINST_AGAINSTINFO)
                .addParams("againstId",newsId)
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
                                DuizhengXQBean zixunXQBean = gson.fromJson(response, DuizhengXQBean.class);
                                if (zixunXQBean.getCode().equals("1000")) {
                                    bean=zixunXQBean.getData();
                                    initHView();
                                }else if (zixunXQBean.getCode().equals("2002")||zixunXQBean.getCode().equals("2003")){
                                    UIUtils.showToast(zixunXQBean.getMessage());
                                    startActivity(LoginActivity.getIntent(DuizhengVideoActivity.this));

                                }
                            } catch (Exception e) {
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
//        //发帖人的名字
//        tvName.setText(bean.getVideoName().getNickname());
        tvTitle.setText(bean.getAgainstName());
        tv_coutent.setText(bean.getRemark());
        tv_author.setText("");
        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        tvTime.setText(DateUtils.timet(bean.getUpdateTime()));
//        int nType = Integer.parseInt(bean.getVideos().getType().trim());
        if (!NetWorkUtils.isWifiConnected(DuizhengVideoActivity.this)) {
            ic_net_warn.setVisibility(View.VISIBLE);
        }else {
            ic_net_warn.setVisibility(View.GONE);
            String url = bean.getVideoUrl();
            if (url.indexOf("letv")!=-1){
                loadAppUrl(url);
            }else {
                TXMediaManager.instance(DuizhengVideoActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
                TXMediaManager.instance(DuizhengVideoActivity.this).TXVodPlayAndView(url ,videoContainer
                        ,3,bean.getAgainstName()) ;
            }

        }


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

        OkHttpUtils.post()
                .url(URLS.AGAINST_AGAINSTCOMMENTLIST)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("againstId",newsId)
                .addParams("pageNumber",page+"")
                .addParams("pageSize","10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        expandableList.onRefreshComplete();
                        expandableList.onLoadMoreComplete();
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                DuiZhengPLBean plZixunListBean = gson.fromJson(response, DuiZhengPLBean.class);
                                if (plZixunListBean.getCode().equals("1000")) {
                                    if (isRefresh) {
                                        listData.clear();
                                        listData.addAll(plZixunListBean.getData());
                                    } else {
                                        listData.addAll(plZixunListBean.getData());
                                    }
                                    for (DuiZhengPLBean.DataBean info : listData) {
                                        String zhuID = info.getCommentId();
                                        for (DuiZhengPLBean.DataBean.ReplyListBean huifu : info.getReplyList()) {
                                            if (TextUtils.isEmpty(huifu.getUserId())) {
                                                huifu.setUserId(zhuID);
                                            }
                                        }
                                    }
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    //默认展开全部
                                    notiAdapter() ;
                                }else if (plZixunListBean != null &&plZixunListBean.getData().size()<10) {
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    if(page != 1){
                                        UIUtils.showToast("没有更多了...");
                                    }
                                } else if (plZixunListBean.getCode().equals("2002")||plZixunListBean.getCode().equals("2003")){
                                    UIUtils.showToast(plZixunListBean.getMessage());
                                    startActivity(LoginActivity.getIntent(DuizhengVideoActivity.this));
                                    DuizhengVideoActivity.this.finish();
                                }else {
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    UIUtils.showToast("服务器异常");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initView() {
        showDialog = new ShowDialog(DuizhengVideoActivity.this);
        header = View.inflate(DuizhengVideoActivity.this, R.layout.video_detail_header, null);
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
        tv_coutent = (TextView) header.findViewById(R.id.tv_coutent);
        tv_author = (TextView) header.findViewById(R.id.tv_author);
        ivTouxiang = (ImageView) header.findViewById(R.id.iv_touxiang);
        lv_picture = (LinearLayout) header.findViewById(R.id.lv_Picture);
        videoContainer = (RelativeLayout) header.findViewById(R.id.videoContainer);
        ic_net_warn = findViewById(R.id.ic_net_warn);
        btn1 = (Button) ic_net_warn.findViewById(R.id.btn1);


        //点击继续播放，非WIFI才出现
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ic_net_warn.setVisibility(View.GONE);
                String url = bean.getVideoUrl();
                TXMediaManager.instance(DuizhengVideoActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
                TXMediaManager.instance(DuizhengVideoActivity.this).TXVodPlayAndView(url ,videoContainer
                        ,3,bean.getAgainstName()) ;

            }
        });
        adapter = new DuizhengVideoDetilsAdapter(DuizhengVideoActivity.this, listData, replyToCommentListener, newsId);
        expandableList.setAdapter(adapter);
        initGaiLouData();

        adapter.setOnPinlun1Clicklistener(new DuizhengVideoDetilsAdapter.OnZanClicklistener() {
            @Override
            public void onPinlun1Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final DuiZhengPLBean.DataBean.ReplyListBean itemNoteComment = listData.get(groupPosition).getReplyList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    huifuState = 1;
                    huiFuBean = new DuiZhengPLBean.DataBean.ReplyListBean();
                    huiFuBean.setReferUserName(itemNoteComment.getUserName());
                    huiFuBean.setReferUserId(itemNoteComment.getUserId());
                    huiFuBean.setId(itemNoteComment.getId());
                    huiFuBean.setUserId(MakerApplication.instance.getUid());
                    huiFuBean.setUserName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getUserId()+"", "2");
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getUserName()) + ":");

                }
            }
        });

        adapter.setOnPinlun2Clicklistener(new DuizhengVideoDetilsAdapter.OnZanClicklistener2() {
            @Override
            public void onPinlun2Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final DuiZhengPLBean.DataBean.ReplyListBean itemNoteComment = listData.get(groupPosition).getReplyList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    huifuState = 1;
                    huiFuBean = new DuiZhengPLBean.DataBean.ReplyListBean();
                    huiFuBean.setReferUserName(itemNoteComment.getReferUserName());
                    huiFuBean.setReferUserId(itemNoteComment.getReferUserId());
                    huiFuBean.setPid(itemNoteComment.getPid());
                    huiFuBean.setUserId(MakerApplication.instance.getUid());
                    huiFuBean.setUserName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getReferUserId()+"", "2");
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getReferUserName()) + ":");
                }
            }
        });

        showDialog = new ShowDialog(DuizhengVideoActivity.this);
        // 评论对话框
        frame_msg_ll = (RelativeLayout) findViewById(R.id.frame_msg_ll);

        //评论发送按钮
        chat_btn_create_card = (TextView) findViewById(R.id.chat_btn_create_card);
        chat_btn_create_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                String content = chat_et_create_context.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(DuizhengVideoActivity.this, "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (0 == huifuState) {//回复现有的帖子
                    if (info == null) {
                        return;
                    }
                    AppUtils.setEvent(DuizhengVideoActivity.this,"VedioPublish","视频-跟帖");
                    sendCommentNoteZ(zhuId, toUid, content, info);
                } else {//盖楼
                    if (huiFuBean == null) {
                        return;
                    }
                    AppUtils.setEvent(DuizhengVideoActivity.this,"VedioReply","视频-回复");
                    sendCommentNote(zhuId, toUid, content, huiFuBean);
                }
                chat_et_create_context.setText("");
                chat_et_create_context.setHint("我也说两句...");
                InputMethodManager imm = (InputMethodManager) DuizhengVideoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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

                StatService.onEvent(DuizhengVideoActivity.this,"VedioInput","视频评论输入框",1);
                TCAgent.onEvent(DuizhengVideoActivity.this,"VedioInput","视频评论输入框");
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();

                    InputMethodManager imm = (InputMethodManager) DuizhengVideoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(chat_et_create_context, 0);

            }
        });
        //滑动隐藏布局
        expandableList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                chat_et_create_context.setHint("我也说两句...");

                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = DuizhengVideoActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
//                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) DuizhengVideoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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
        expandableList.setOnChildClickListener(new DuizhengVideoActivity.expandableListListener());
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
        info = new DuiZhengPLBean.DataBean();
        info.setHeadImg(MakerApplication.instance().getHeadPic());
        info.setUserId(MakerApplication.instance().getUserid());
        info.setUserName(MakerApplication.instance().getNickName());
    }

    /**
     * /**
     * 回复评论的监听（回复楼主） 点击左边的气泡的时候触发（评论）
     */
    private View.OnClickListener replyToCommentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                UIUtils.showToast("请您点击我的头像去绑定手机号");
                return;
            }
            chat_et_create_context.setHint("我也说两句...");
            DuiZhengPLBean.DataBean itemNotes = null;
            if (v instanceof ImageView) {
                itemNotes = (DuiZhengPLBean.DataBean ) v.getTag();
            } else {
                itemNotes = (DuiZhengPLBean.DataBean ) v.findViewById(R.id.btn_comment_reply).getTag();
            }
            if (itemNotes == null) {
                return;
            }
            huifuState = 1;
            huiFuBean = new DuiZhengPLBean.DataBean.ReplyListBean();
            huiFuBean.setUserName(MakerApplication.instance().getNickName());
            huiFuBean.setUserId(MakerApplication.instance().getUserid());
            huiFuBean.setReferUserName("");
            huiFuBean.setReferUserId("");
            huiFuBean.setPid(itemNotes.getCommentId());
            showKeyBoard(itemNotes.getCommentId()+"", "", "1");

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
    private void sendCommentNote(final String zhuId, String toUid, final String msg, final DuiZhengPLBean.DataBean.ReplyListBean huiFuBean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);

        HashMap<String,String> map=new HashMap<>();
        map.put("againstId",newsId);
        map.put("userId",MakerApplication.instance().getUserid());
        if (!StringUtils.isBlank(toUid)){
            map.put("referUserId",toUid);
        }
        map.put("commentId",zhuId);
        map.put("content",msg);
        map.put("token", MakerApplication.instance.getToken());
        OkHttpUtils.post()
                .url(URLS.AGAINST_AGAINSTCOMMENTREPLY)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(DuizhengVideoActivity.this, "评论失败",
                                Toast.LENGTH_SHORT).show();
                        showDialog.dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                showDialog.dismissProgressDialog();
                                HomeOneBean homeOneBean = gson.fromJson(response, HomeOneBean.class);
                                if (homeOneBean.getCode().equals("1000")) {
                                    page=1;
                                    huifuState=0;
                                    huiFuBean.setPid(zhuId);
                                    huiFuBean.setContent(msg);
                                    for (int i = 0; i <listData.size() ; i++) {
                                        if (listData.get(i).getCommentId().equals(huiFuBean.getPid())) {
                                            listData.get(i).getReplyList().add(0,huiFuBean);
                                            break;
                                        }
                                    }
                                    notiAdapter();
                                    Toast.makeText(DuizhengVideoActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(DuizhengVideoActivity.this));
                                    DuizhengVideoActivity.this.finish();
                                }else {
                                    Toast.makeText(DuizhengVideoActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });



    }

    private void sendCommentNoteZ(final String zhuId, String toUid, final String msg, final DuiZhengPLBean.DataBean bean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        OkHttpUtils.post()
                .url(URLS.GAINST_AGAINSTCOMMENT)
                .addParams("againstId",newsId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId",MakerApplication.instance().getUserid())
                .addParams("content",msg)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(DuizhengVideoActivity.this, "评论失败",
                                Toast.LENGTH_SHORT).show();
                        showDialog.dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                showDialog.dismissProgressDialog();
                                HomeOneBean homeOneBean = gson.fromJson(response, HomeOneBean.class);
                                if (homeOneBean.getCode().equals("1000")) {
                                    page=1;
                                    huifuState=0;
                                    isRefresh=true;
                                    initListData(page);

                                    notiAdapter();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(DuizhengVideoActivity.this));
                                    DuizhengVideoActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


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

        TXMediaManager.instance(DuizhengVideoActivity.this).resumeplayer();
    }

    @Override
    protected void onDestroy() {
        if(TXMediaManager.instance(DuizhengVideoActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null){
            TXMediaManager.instance(DuizhengVideoActivity.this).videoDestroy();
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
        if(TXMediaManager.instance(DuizhengVideoActivity.this).mLivePlayer != null && TXMediaManager.instance(DuizhengVideoActivity.this).mLivePlayer.isPlaying()){
            TXMediaManager.instance(DuizhengVideoActivity.this).pauseplayer();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (ScreenUtils.isLandScape(this)) {
                TXMediaManager.instance(DuizhengVideoActivity.this).mPlayerView1.backPress() ;
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }else{
                finish();
            }
        }
        return false;
    }

}
