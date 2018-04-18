package com.hxwl.wulinfeng.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.PLZixunListBean;
import com.hxwl.newwlf.modlebean.ZixunXQBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.DateUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.wulin.CusListView;
import com.hxwl.wulinfeng.wulin.SeeHighDefinitionPictureActivity;
import com.hxwl.wulinfeng.wulin.ZiXunDetilsAdapter;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

import static com.hxwl.wulinfeng.R.id.webview;

/**
 * Created by Allen on 2017/6/20.
 * 资讯详情界面
 */
public class ZiXunDetailsActivity extends BaseActivity {
    public static Intent getIntent(Context context, String newsId){
        Intent intent=new Intent(context,ZiXunDetailsActivity.class);
        intent.putExtra("newsId",newsId);
        return intent;
    }

    private String newsId;
    private CusListView expandableList;
    private View header;
    private ZixunXQBean.DataBean zixunBean;
    private TextView tvName;
    private TextView tv_author;
    private ImageView ivTouxiang;
    private TextView tvTitle;
    private TextView tvTime;
    private WebView webView;

    public boolean isRefresh = true;
    private String lastNewsId = "0";
    private com.hxwl.common.utils.ShowDialog showDialog;

    private int huifuState = 0; //默认是盖楼-0！ 1是回复

    // 评论
    private RelativeLayout frame_msg_ll;
    private TextView chat_btn_create_card;//评论确认按钮
    private EditText chat_et_create_context;//评论输入框

    //回复传递参数
    private String zhuId = "";//心得id
    private String toUid = "";//回复谁
    private String type = "1";
    private PLZixunListBean.DataBean.ReplyListBean bean = null; //回复
    private PLZixunListBean.DataBean info = null;//盖楼

    private int page = 1;
    private List<PLZixunListBean.DataBean> zixunListBean = new ArrayList<>();
    private ZiXunDetilsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zixun_details_activity);
        AppUtils.setTitle(ZiXunDetailsActivity.this);
        newsId=getIntent().getStringExtra("newsId");

        initView();
        initHData();
        initData(page);
    }

    private void initHData() {
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_NEWSINFO)
                .addParams("newsId",newsId)
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
                                ZixunXQBean bean = gson.fromJson(response, ZixunXQBean.class);
                                if (bean.getCode().equals("1000")) {
                                    zixunBean=bean.getData();
                                    initDetails();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(ZiXunDetailsActivity.this));
                                    ZiXunDetailsActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    /**
     * 渲染头布局
     */
    private void initDetails() {
        if (zixunBean == null) {
            return;
        }
//        ImageUtils.getCirclePic(URLS.IMG+zixunBean.getCoverImages(), ivTouxiang, this);
        //发帖人的名字
        tvName.setText(zixunBean.getAuthor());
        tvTitle.setText(zixunBean.getTitle());
        tv_author.setText(zixunBean.getAuthor());
        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        tvTime.setText(DateUtils.timet(zixunBean.getCreateTime()));
        String strContents = "<html><body>"+zixunBean.getContent()+"</body></html>";
//        strContents = strContents.replaceFirst("282828","ffffff");
//        strContents = strContents.replaceFirst("959595","222222");
        //使用webview加载标签

        WebSettings setTing = webView.getSettings();
        if(setTing != null){
            setTing.setJavaScriptEnabled(true);
            setTing.setDefaultTextEncodingName("utf-8");
        }
//        webView.loadDataWithBaseURL(null, strContents, "text/html", "utf-8", null);
        webView.loadUrl(URLS.HTML+zixunBean.getNewsUrl());
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!StringUtils.isBlank(zixunBean.getNewsUrl())){

                }
            }
        });
//        webView.addJavascriptInterface(new JavascriptInterface(), "imageListener");
    }
    public class JavascriptInterface {
        @android.webkit.JavascriptInterface
        public void startPhotoActivity(String imageUrl) {
            if(!TextUtils.isEmpty(imageUrl)){
                Intent intent = new Intent(ZiXunDetailsActivity.this, SeeHighDefinitionPictureActivity.class);
                intent.putExtra("highDefinitionUrl", imageUrl);
                startActivity(intent);
            }
        }
    }

    private String readJS() {
        try {
            InputStream inStream = getAssets().open("js.html");
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inStream.read(bytes)) > 0) {
                outStream.write(bytes, 0, len);
            }
            return outStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void initData(final int page) { //zixun_tiezidetail
        OkHttpUtils.post()
                .url(URLS.NEWS_NEWSCOMMENTLIST)
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("newsId",newsId)
                .addParams("token", MakerApplication.instance.getToken())
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
                                PLZixunListBean plZixunListBean = gson.fromJson(response, PLZixunListBean.class);
                                if (plZixunListBean.getCode().equals("1000")) {
                                    if (isRefresh) {
                                        zixunListBean.clear();
                                        zixunListBean.addAll(plZixunListBean.getData());
                                    } else {
                                        zixunListBean.addAll(plZixunListBean.getData());
                                    }
                                    for (PLZixunListBean.DataBean info : zixunListBean) {
                                        String zhuID = info.getCommentId();
                                        for (PLZixunListBean.DataBean.ReplyListBean huifu : info.getReplyList()) {
                                            if (TextUtils.isEmpty(huifu.getUserId()+"")) {
                                                huifu.setUserId(zhuID);
                                            }
                                        }
                                    }
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    notiAdapter();
                                    showDialog.dismissProgressDialog();
                                }else if (plZixunListBean.getCode().equals("2002")||plZixunListBean.getCode().equals("2003")){
                                    UIUtils.showToast(plZixunListBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ZiXunDetailsActivity.this));
                                    ZiXunDetailsActivity.this.finish();
                                }else if (plZixunListBean != null && plZixunListBean.getData().size()<10) {
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    if(page != 1){
                                        UIUtils.showToast("没有更多了...");
                                    }
                                }else {
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    UIUtils.showToast(plZixunListBean.getMessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });




    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        header = View.inflate(ZiXunDetailsActivity.this, R.layout.zixun_webview_header, null);
        expandableList = (CusListView) findViewById(R.id.cusListView1);
        expandableList.addHeaderView(header);
        tv_author= (TextView) header.findViewById(R.id.tv_author);
        tvName = (TextView) header.findViewById(R.id.tv_name);
        tvTime = (TextView) header.findViewById(R.id.tv_time);
        tvTitle = (TextView) header.findViewById(R.id.tv_title);
        ivTouxiang = (ImageView) header.findViewById(R.id.iv_touxiang);
        webView = (WebView) header.findViewById(webview);

        adapter = new ZiXunDetilsAdapter(ZiXunDetailsActivity.this, zixunListBean, replyToCommentListener, newsId);
        expandableList.setAdapter(adapter);
        adapter.setOnPinlun1Clicklistener(new ZiXunDetilsAdapter.OnZanClicklistener() {
            @Override
            public void onPinlun1Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final PLZixunListBean.DataBean.ReplyListBean itemNoteComment = zixunListBean.get(groupPosition).getReplyList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    huifuState = 1;
                    bean = new PLZixunListBean.DataBean.ReplyListBean();
                    bean.setReferUserName(itemNoteComment.getUserName());
                    bean.setReferUserId(itemNoteComment.getUserId());
                    bean.setId(itemNoteComment.getId());
                    bean.setUserId(MakerApplication.instance.getUid());
                    bean.setUserName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getUserId()+"", "2");
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getUserName()) + ":");
                }
            }
        });

        adapter.setOnPinlun2Clicklistener(new ZiXunDetilsAdapter.OnZanClicklistener2() {
            @Override
            public void onPinlun2Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final PLZixunListBean.DataBean.ReplyListBean itemNoteComment = zixunListBean.get(groupPosition).getReplyList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    huifuState = 1;
                    bean = new PLZixunListBean.DataBean.ReplyListBean();
                    bean.setReferUserName(itemNoteComment.getReferUserName());
                    bean.setReferUserId(itemNoteComment.getReferUserId());
                    bean.setPid(itemNoteComment.getPid());
                    bean.setUserId(MakerApplication.instance.getUid());
                    bean.setUserName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getReferUserId()+"", "2");
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getReferUserName()) + ":");
                }
            }
        });


        initLouZhuInfo();

        showDialog = new ShowDialog(ZiXunDetailsActivity.this);
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
                    Toast.makeText(ZiXunDetailsActivity.this, "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                chat_et_create_context.setText("");
                chat_et_create_context.setHint("我也说两句...");
                if (0 == huifuState) {//盖楼
                    if (info == null) {
                        return;
                    }
                    sendCommentNoteZ(zhuId, toUid, content, info);
                } else {//回复现有的帖子
                    if (bean == null) {
                        return;
                    }
                    sendCommentNote(zhuId, toUid, content, bean);

                }

                InputMethodManager imm = (InputMethodManager) ZiXunDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
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

                InputMethodManager imm = (InputMethodManager) ZiXunDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(chat_et_create_context, 0);

            }
        });
        //滑动隐藏布局
        expandableList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                chat_et_create_context.setHint("我也说两句...");

                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = ZiXunDetailsActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
//                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) ZiXunDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                expandableList.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        });
        //设置子条目点击事件
        expandableList.setOnChildClickListener(new expandableListListener());

        expandableList.setonRefreshListener(new CusListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新操作
                page = 1;
                isRefresh = true;
                initData(page);


            }
        });
        expandableList.setonLoadMoreListener(new CusListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initData(page);
            }
        });
    }

    private void initLouZhuInfo() {
        info = new PLZixunListBean.DataBean();
        info.setHeadImg(MakerApplication.instance().getHeadImg());
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
            PLZixunListBean.DataBean itemNotes = null;
            if (v instanceof ImageView) {
                itemNotes = (PLZixunListBean.DataBean) v.getTag();
            } else {
                itemNotes = (PLZixunListBean.DataBean) v.findViewById(R.id.btn_comment_reply).getTag();
            }
            if (itemNotes == null) {
                return;
            }
            huifuState = 1;
            bean = new PLZixunListBean.DataBean.ReplyListBean();
            bean.setUserName(MakerApplication.instance().getNickName());
            bean.setUserId(MakerApplication.instance().getUserid());
            bean.setReferUserName("");
            bean.setReferUserId("");
            bean.setPid(itemNotes.getCommentId());
            showKeyBoard(itemNotes.getCommentId()+"", "", "1");
        }
    };

    /**
     * 为ExpandableListView编写监听器
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
     * @param zhu 帖子id
     * @param to 给谁评论
     * @param msg   发送信息
     */
    private void sendCommentNote(final String zhu, final String to, final String msg, final PLZixunListBean.DataBean.ReplyListBean bean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        HashMap<String,String> map=new HashMap<>();
        map.put("newsId",newsId);
        map.put("userId",MakerApplication.instance().getUserid());
        if (!StringUtils.isBlank(to)){
            map.put("referUserId",to);
        }
        map.put("commentId",zhu);
        map.put("content",msg);
        map.put("token",MakerApplication.instance.getToken());
        OkHttpUtils.post()
                .url(URLS.NEWS_NEWSCOMMENTREPLY)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ZiXunDetailsActivity.this, "评论失败",
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
                                    bean.setPid(zhuId);
                                    bean.setContent(msg);
                                    for (int i = 0; i <zixunListBean.size() ; i++) {
                                        if (zixunListBean.get(i).getCommentId().equals(bean.getPid())) {
                                            zixunListBean.get(i).getReplyList().add(0,bean);
                                            zixunListBean.get(i).setReplyNum(zixunListBean.get(i).getReplyNum()+1);
                                            break;
                                        }
                                    }
                                    notiAdapter();
                                    Toast.makeText(ZiXunDetailsActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ZiXunDetailsActivity.this));
                                    ZiXunDetailsActivity.this.finish();
                                }else {
                                    Toast.makeText(ZiXunDetailsActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        }

                });



    }

    private void notiAdapter() {
        adapter.notifyDataSetChanged();
        if (adapter != null && adapter.getGroupCount() > 0) {
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                expandableList.expandGroup(i);
            }
        }
    }


    private void sendCommentNoteZ(final String zhuId, String toUid, final String msg, final PLZixunListBean.DataBean replyListBean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        OkHttpUtils.post()
                .url(URLS.NEWS_NEWSCOMMENT)
                .addParams("newsId",newsId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId",MakerApplication.instance().getUserid())
                .addParams("content",msg)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ZiXunDetailsActivity.this, "评论失败",
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
                                    isRefresh=true;
                                    huifuState=0;
                                    initData(page);
                                    notiAdapter();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ZiXunDetailsActivity.this));
                                    ZiXunDetailsActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        AppUtils.setEvent(this,"InfoPageOpen","资讯详情打开");
        StatService.onPageStart(this, "资讯详情");
        TCAgent.onPageStart(this, "资讯详情");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"资讯详情");
        TCAgent.onPageEnd(this,"资讯详情");
    }
}
