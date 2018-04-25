package com.hxwl.newwlf.home.home.zixun;

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
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.PLZixunListBean;
import com.hxwl.newwlf.modlebean.ZixunXQBean;
import com.hxwl.utils.Photos;
import com.hxwl.wlf3.bean3.Pinlin3Bean;
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
public class ZixunVideoActivity extends BaseActivity {
    private String newsId;
    private View ic_net_warn;
    private Button btn1;

    public static Intent getIntent(Context context,String newsId){
        Intent intent=new Intent(context,ZixunVideoActivity.class);
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

    private List<Pinlin3Bean.DataBean> listData = new ArrayList<>();

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
    private Pinlin3Bean.DataBean.ReplyListBean huiFuBean;
    private ZixunXQBean.DataBean bean;
    private ZixunVideoDetilsAdapter adapter;
    private Pinlin3Bean.DataBean info;

    private RelativeLayout videoContainer;
    private IMediaDataVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videodetail_activity);
        StatusBarUtil.setColor(ZixunVideoActivity.this,getResources().getColor(R.color.tab_color),0);
        newsId = getIntent().getStringExtra("newsId");
        TXMediaManager.instance(ZixunVideoActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
        initView();
        initHData();
        initListData(page);
    }


    private void initHData() {
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_NEWSINFO)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("newsId",newsId)
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
                                ZixunXQBean zixunXQBean = gson.fromJson(response, ZixunXQBean.class);
                                if (zixunXQBean.getCode().equals("1000")) {
                                    bean=zixunXQBean.getData();
                                    initHView();
                                }else if (zixunXQBean.getCode().equals("2002")||zixunXQBean.getCode().equals("2003")){
                                    UIUtils.showToast(zixunXQBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ZixunVideoActivity.this));
                                    ZixunVideoActivity.this.finish();
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
        tvTitle.setText(bean.getTitle());
        tv_coutent.setText(bean.getTextContent());
        tv_author.setText(bean.getAuthor());
        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        tvTime.setText(DateUtils.timet(bean.getCreateTime()));
//        int nType = Integer.parseInt(bean.getVideos().getType().trim());
        if (!NetWorkUtils.isWifiConnected(ZixunVideoActivity.this)) {
            ic_net_warn.setVisibility(View.VISIBLE);
        }else {
            ic_net_warn.setVisibility(View.GONE);
            String url = bean.getVideoUrl();
//            loadAppUrl(url);
            if (url.indexOf("letv")!=-1){
                loadAppUrl(url);
            }else {
                TXMediaManager.instance(ZixunVideoActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
                TXMediaManager.instance(ZixunVideoActivity.this).TXVodPlayAndView(url ,videoContainer
                        ,3,bean.getTitle()) ;
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
                .url(URLS.COMMENTLIST)
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("targetId",newsId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("pageNumber",page+"")
                .addParams("pageSize","10")
                .addParams("type","1")
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
                                Pinlin3Bean plZixunListBean = gson.fromJson(response, Pinlin3Bean.class);
                                if (plZixunListBean.getCode().equals("1000")) {
                                    if (isRefresh) {
                                        listData.clear();
                                        listData.addAll(plZixunListBean.getData());
                                    } else {
                                        listData.addAll(plZixunListBean.getData());
                                    }
                                    for (Pinlin3Bean.DataBean info : listData) {
                                        String zhuID = info.getId();
                                        for (Pinlin3Bean.DataBean.ReplyListBean huifu : info.getReplyList()) {
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
                                }else if (plZixunListBean.getCode().equals("2002")||plZixunListBean.getCode().equals("2003")){
                                    UIUtils.showToast(plZixunListBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ZixunVideoActivity.this));
                                    ZixunVideoActivity.this.finish();
                                } else {
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
        showDialog = new ShowDialog(ZixunVideoActivity.this);
        header = View.inflate(ZixunVideoActivity.this, R.layout.video_detail_header, null);
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
                TXMediaManager.instance(ZixunVideoActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
                TXMediaManager.instance(ZixunVideoActivity.this).TXVodPlayAndView(url ,videoContainer
                        ,3,bean.getTitle()) ;

            }
        });
        adapter = new ZixunVideoDetilsAdapter(ZixunVideoActivity.this, listData, replyToCommentListener, newsId);
        expandableList.setAdapter(adapter);
        initGaiLouData();

        adapter.setOnPinlun1Clicklistener(new ZixunVideoDetilsAdapter.OnZanClicklistener() {
            @Override
            public void onPinlun1Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final Pinlin3Bean.DataBean.ReplyListBean itemNoteComment = listData.get(groupPosition).getReplyList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    huifuState = 1;
                    huiFuBean = new Pinlin3Bean.DataBean.ReplyListBean();
                    huiFuBean.setReferUserNickName(itemNoteComment.getNickName());
                    huiFuBean.setReferUserId(itemNoteComment.getUserId());
                    huiFuBean.setId(itemNoteComment.getId());
                    huiFuBean.setUserId(MakerApplication.instance.getUid());
                    huiFuBean.setNickName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getUserId()+"", "1");
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getNickName()) + ":");
                }
            }
        });

        adapter.setOnPinlun2Clicklistener(new ZixunVideoDetilsAdapter.OnZanClicklistener2() {
            @Override
            public void onPinlun2Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final Pinlin3Bean.DataBean.ReplyListBean itemNoteComment = listData.get(groupPosition).getReplyList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    huifuState = 1;
                    huiFuBean = new Pinlin3Bean.DataBean.ReplyListBean();
                    huiFuBean.setReferUserNickName(itemNoteComment.getReferUserNickName());
                    huiFuBean.setReferUserId(itemNoteComment.getReferUserId());
                    huiFuBean.setPid(itemNoteComment.getPid());
                    huiFuBean.setUserId(MakerApplication.instance.getUid());
                    huiFuBean.setNickName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getReferUserId()+"", "2");
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getReferUserNickName()) + ":");
                }
            }
        });

        showDialog = new ShowDialog(ZixunVideoActivity.this);
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
                    Toast.makeText(ZixunVideoActivity.this, "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (0 == huifuState) {//回复现有的帖子
                    if (info == null) {
                        return;
                    }
                    AppUtils.setEvent(ZixunVideoActivity.this,"VedioPublish","视频-跟帖");
                    sendCommentNoteZ(zhuId, toUid, content, info);
                } else {//盖楼
                    if (huiFuBean == null) {
                        return;
                    }
                    AppUtils.setEvent(ZixunVideoActivity.this,"VedioReply","视频-回复");
                    sendCommentNote(zhuId, toUid, content, huiFuBean);
                }
                chat_et_create_context.setText("");
                chat_et_create_context.setHint("我也说两句...");
                InputMethodManager imm = (InputMethodManager) ZixunVideoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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

                InputMethodManager imm = (InputMethodManager) ZixunVideoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(chat_et_create_context, 0);

            }
        });
        //滑动隐藏布局
        expandableList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                chat_et_create_context.setHint("我也说两句...");

                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = ZixunVideoActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
//                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) ZixunVideoActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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
        expandableList.setOnChildClickListener(new ZixunVideoActivity.expandableListListener());
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
        info = new Pinlin3Bean.DataBean();
        info.setHeadImg(MakerApplication.instance.getHeadPic());
        info.setUserId(MakerApplication.instance.getUid());
        info.setNickName(MakerApplication.instance.getNickName());
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
            Pinlin3Bean.DataBean itemNotes = null;
            if (v instanceof ImageView) {
                itemNotes = (Pinlin3Bean.DataBean ) v.getTag();
            } else {
                itemNotes = (Pinlin3Bean.DataBean ) v.findViewById(R.id.btn_comment_reply).getTag();
            }
            if (itemNotes == null) {
                return;
            }
            huifuState = 1;
            huiFuBean = new Pinlin3Bean.DataBean.ReplyListBean();
            huiFuBean.setNickName(MakerApplication.instance.getNickName());
            huiFuBean.setUserId(MakerApplication.instance.getUid());
            huiFuBean.setReferUserNickName("");
            huiFuBean.setReferUserId("");
            huiFuBean.setPid(itemNotes.getId());
            showKeyBoard(itemNotes.getId()+"", "", "1");
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
    private void sendCommentNote(final String zhuId, String toUid, final String msg, final Pinlin3Bean.DataBean.ReplyListBean huiFuBean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);

        HashMap<String,String> map=new HashMap<>();
        map.put("targetId",newsId);
        map.put("userId",MakerApplication.instance.getUid());
        if (!StringUtils.isBlank(toUid)){
            map.put("referUserId",toUid);
        }
        map.put("pid",zhuId);
        map.put("content",msg);
        map.put("type","1");
        map.put("token", MakerApplication.instance.getToken());
        OkHttpUtils.post()
                .url(URLS.NEWS_NEWSCOMMENTREPLY)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ZixunVideoActivity.this, "评论失败",
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
                                        if (listData.get(i).getId().equals(huiFuBean.getPid())) {
                                            listData.get(i).getReplyList().add(0,huiFuBean);
                                            listData.get(i).setReplyNum(listData.get(i).getReplyNum()+1);
                                            break;
                                        }
                                    }
                                    notiAdapter();
                                    Toast.makeText(ZixunVideoActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ZixunVideoActivity.this));
                                    ZixunVideoActivity.this.finish();
                                }else {
                                    Toast.makeText(ZixunVideoActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });



    }

    private void sendCommentNoteZ(final String zhuId, String toUid, final String msg, final Pinlin3Bean.DataBean info) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        OkHttpUtils.post()
                .url(URLS.COMMENT)
                .addParams("targetId",newsId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("content",msg)
                .addParams("type","1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ZixunVideoActivity.this, "评论失败",
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
                                    startActivity(LoginActivity.getIntent(ZixunVideoActivity.this));
                                    ZixunVideoActivity.this.finish();
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

        TXMediaManager.instance(ZixunVideoActivity.this).resumeplayer();
    }

    @Override
    protected void onDestroy() {
        if(TXMediaManager.instance(ZixunVideoActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null){
            TXMediaManager.instance(ZixunVideoActivity.this).videoDestroy();
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
        if(TXMediaManager.instance(ZixunVideoActivity.this).mLivePlayer != null && TXMediaManager.instance(ZixunVideoActivity.this).mLivePlayer.isPlaying()){
            TXMediaManager.instance(ZixunVideoActivity.this).pauseplayer();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (ScreenUtils.isLandScape(this)) {
                TXMediaManager.instance(ZixunVideoActivity.this).mPlayerView1.backPress() ;
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            }else{
                finish();
            }
        }
        return false;
    }

}
