package com.hxwl.wulinfeng.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.zixun.TujiLayout;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.PLZixunListBean;
import com.hxwl.newwlf.modlebean.ZixunXQBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.TuJiDetilsAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.DateUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.MyShareShowPopuwindowUtils;
import com.hxwl.wulinfeng.util.PreciseCompute;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.wulin.CusListView;
import com.hxwl.wulinfeng.wulin.ImageViewPageActivity;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;


/**
 * Created by Allen on 2017/6/26.
 */
public class TuJiDetailsActivity extends BaseActivity {

    private TujiLayout tujiLayout;

    public static Intent getIntent(Context context, String newsId){
        Intent intent=new Intent(context,TuJiDetailsActivity.class);
        intent.putExtra("newsId",newsId);
        return intent;
    }
    private String newsId;
    private String id;
    private View header;
    private CusListView expandableList;
    private List<PLZixunListBean.DataBean> listData = new ArrayList<>();
    private TextView tvName;
    private TextView tvTime;
    private TextView tvTitle;
    private ImageView ivTouxiang;
    private TextView tv_coutent;
    private TextView tv_author;
    private LinearLayout lv_picture;
    private com.hxwl.common.utils.ShowDialog showDialog;

    private ZixunXQBean.DataBean tujiBean;
    //加载下一张
    private static final int LOAD_NEXT_PIC = 0;
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

    private List<String> imaginrList = new ArrayList<>();
    Iterator<String> imgIterator;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOAD_NEXT_PIC:
                    if (imgIterator != null && imgIterator.hasNext()) {
                        orderLoadImageList(imgIterator.next());
                    }
                    break;
            }
        }
    };
    private TuJiDetilsAdapter adapter;
    private RelativeLayout frame_msg_ll;
    private PLZixunListBean.DataBean info;
    private TextView chat_btn_create_card;
    private EditText chat_et_create_context;
    private PLZixunListBean.DataBean.ReplyListBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuji_details_activity);
        AppUtils.setTitle(TuJiDetailsActivity.this);
//        StatusBarUtil.setTranslucentForImageView(this, 100, expandableList);
        id = getIntent().getStringExtra("id");
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
                                    tujiBean=bean.getData();
                                    initDetails();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(TuJiDetailsActivity.this));

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
        if (tujiBean == null) {
            return;
        }
//        ImageUtils.getCirclePic(URLS.IMG+tujiBean.getCoverImages(), ivTouxiang, this);
        //发帖人的名字
        tvName.setText(tujiBean.getAuthor());
        tvTitle.setText(tujiBean.getTitle());
        tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        tvTime.setText(DateUtils.timet(tujiBean.getCreateTime()));
        tv_coutent.setText(tujiBean.getTextContent());
        tv_author.setText(tujiBean.getAuthor());
        imaginrList.clear();
        if (tujiBean.getImageGather().indexOf(",")!=-1){
            String[] all=tujiBean.getImageGather().split(",");
            for (int i = 0; i <all.length ; i++) {
                imaginrList.add(URLS.IMG+all[i]);
            }
        }else {
            imaginrList.add(URLS.IMG+tujiBean.getImageGather());
        }
        imgIterator = imaginrList.iterator();
        tujiLayout.setTujis(this,tujiBean.getImageGather());
        //orderLoadImageList(imgIterator.next());
    }
    private void notiAdapter() {
        adapter.notifyDataSetChanged();
        if (adapter != null && adapter.getGroupCount() > 0) {
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                expandableList.expandGroup(i);
            }
        }
    }
    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        if (user_icon != null && user_icon instanceof ImageView) {
            findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        ImageView tv_share = (ImageView) findViewById(R.id.tv_share);
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tujiBean == null) {
                    return;
                }
                MyShareShowPopuwindowUtils.ShowPopuWin(TuJiDetailsActivity.this, tujiBean.getId()+"",
                        "tuji", tujiBean.getTitle(), imaginrList != null ? imaginrList.get(0) : "");
            }
        });

        showDialog = new ShowDialog(TuJiDetailsActivity.this);
        header = View.inflate(TuJiDetailsActivity.this, R.layout.tuji_webview_header, null);
        expandableList = (CusListView) findViewById(R.id.cusListView1);
        tujiLayout = new TujiLayout(this);
        expandableList.addHeaderView(tujiLayout);
        expandableList.addHeaderView(header);
        tv_coutent = (TextView) header.findViewById(R.id.tv_coutent);
        tv_author = (TextView) header.findViewById(R.id.tv_author);
        tvName = (TextView) header.findViewById(R.id.tv_name);
        tvTime = (TextView) header.findViewById(R.id.tv_time);
        tvTitle = (TextView) header.findViewById(R.id.tv_title);
        ivTouxiang = (ImageView) header.findViewById(R.id.iv_touxiang);
        lv_picture = (LinearLayout) header.findViewById(R.id.lv_Picture);

        adapter = new TuJiDetilsAdapter(TuJiDetailsActivity.this, listData, replyToCommentListener, id);
        expandableList.setAdapter(adapter);
        initGaiLouInfo() ;

        adapter.setOnPinlun1Clicklistener(new TuJiDetilsAdapter.OnZanClicklistener() {
            @Override
            public void onPinlun1Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final PLZixunListBean.DataBean.ReplyListBean itemNoteComment = listData.get(groupPosition).getReplyList().get(childPosition);
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

        adapter.setOnPinlun2Clicklistener(new TuJiDetilsAdapter.OnZanClicklistener2() {
            @Override
            public void onPinlun2Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final PLZixunListBean.DataBean.ReplyListBean itemNoteComment = listData.get(groupPosition).getReplyList().get(childPosition);
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

        showDialog = new ShowDialog(TuJiDetailsActivity.this);
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
                    Toast.makeText(TuJiDetailsActivity.this, "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                chat_et_create_context.setText("");
                chat_et_create_context.setHint("我也说两句...");
                if (0 == huifuState) {//盖楼
                    if (info == null) {
                        return;
                    }
                    AppUtils.setEvent(TuJiDetailsActivity.this,"ImgPublish","图集-跟帖");
                    sendCommentNoteZ(zhuId, toUid, content, info);
                } else {//回复现有的帖子
                    if (bean == null) {
                        return;
                    }
                    AppUtils.setEvent(TuJiDetailsActivity.this,"ImgReply","图集-回复");
                    sendCommentNote(zhuId, toUid, content, bean);
                }

                InputMethodManager imm = (InputMethodManager) TuJiDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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

                InputMethodManager imm = (InputMethodManager) TuJiDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(chat_et_create_context, 0);


            }
        });
        //滑动隐藏布局
        expandableList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                chat_et_create_context.setHint("我也说两句...");

                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = TuJiDetailsActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
//                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) TuJiDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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
        expandableList.setOnChildClickListener(new TuJiDetailsActivity.expandableListListener());
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

    private void initGaiLouInfo() {
        info = new PLZixunListBean.DataBean();
        info.setHeadImg(MakerApplication.instance().getHeadImg());
        info.setUserId(MakerApplication.instance().getUserid());
        info.setUserName(MakerApplication.instance().getNickName());
    }

    private void initData(final int page) { //tuji_tiezidetail
        OkHttpUtils.post()
                .url(URLS.NEWS_NEWSCOMMENTLIST)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("newsId",newsId)
                .addParams("pageNumber",page+"")
                .addParams("pageSize","10")
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
                                PLZixunListBean zixunListBean = gson.fromJson(response, PLZixunListBean.class);
                                if (zixunListBean.getCode().equals("1000")) {
                                    if (isRefresh) {
                                        listData.clear();
                                        listData.addAll(zixunListBean.getData());
                                    } else {
                                        listData.addAll(zixunListBean.getData());
                                    }
                                    for (PLZixunListBean.DataBean info : listData) {
                                        String zhuID = info.getCommentId();
                                        for (PLZixunListBean.DataBean.ReplyListBean huifu : info.getReplyList()) {
                                            if (TextUtils.isEmpty(huifu.getUserId())) {
                                                huifu.setUserId(zhuID);
                                            }
                                        }
                                    }
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    notiAdapter() ;
                                }else if (zixunListBean.getCode().equals("2002")||zixunListBean.getCode().equals("2003")){
                                    UIUtils.showToast(zixunListBean.getMessage());
                                    startActivity(LoginActivity.getIntent(TuJiDetailsActivity.this));

                                }else if (zixunListBean != null && zixunListBean.getData().size()<10) {
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    if(page != 1){
                                        UIUtils.showToast("没有更多了...");
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }
    /**
     * 功能:按顺序加载图片列表
     */
    private void orderLoadImageList(final String picUrl) {
        if (!StringUtils.isEmpty(picUrl)) {
            if (picUrl.contains(".gif") || picUrl.contains(".GIF")) {
                ImageUtils.getGifPic(picUrl, R.drawable.icon_pic_default, this, new SimpleTarget<GifDrawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(GifDrawable gifDrawable, GlideAnimation glideAnimation) {
                        View convertView = View.inflate(TuJiDetailsActivity.this, R.layout.fatie_details_pic, null);
                        ImageView fatie_img = (ImageView) convertView.findViewById(R.id.fatie_img);
                        fatie_img.setImageDrawable(gifDrawable);
                        lv_picture.addView(convertView);
                        mHandler.sendEmptyMessage(LOAD_NEXT_PIC);
                        fatie_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(TuJiDetailsActivity.this,
                                        ImageViewPageActivity.class);
                                intent.putExtra("url", picUrl);
                                intent.putExtra("jsonArray",
                                        JSON.toJSONString(imaginrList));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                    }

                });
            } else {
                ImageUtils.getBitmapPic(picUrl, R.drawable.icon_pic_default, this, new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        picHeight = bitmap.getHeight();
                        picWidth = bitmap.getWidth();
                        View convertView = View.inflate(TuJiDetailsActivity.this, R.layout.fatie_details_pic, null);
                        ImageView fatie_img = (ImageView) convertView.findViewById(R.id.fatie_img);
                        ViewGroup.LayoutParams params = fatie_img.getLayoutParams();
                        double scale = PreciseCompute.div(picWidth, picHeight, 2);
                        int scaleHeight = (int) PreciseCompute.div(m_ScreenWidth, scale, 2);
                        params.width = m_ScreenWidth;
                        params.height = scaleHeight;
                        fatie_img.setLayoutParams(params);

                        Bitmap bm = BitmapUtils.zoomBitmap(bitmap, m_ScreenWidth, scaleHeight);
                        fatie_img.setImageBitmap(bm);
                        lv_picture.addView(convertView);
                        mHandler.sendEmptyMessage(LOAD_NEXT_PIC);
                        fatie_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(TuJiDetailsActivity.this,
                                        ImageViewPageActivity.class);
                                intent.putExtra("url", picUrl);
                                intent.putExtra("jsonArray",
                                        JSON.toJSONString(imaginrList));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                    }

                });
            }
        }

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
    private void sendCommentNote(final String zhuId, String toUid, final String msg, final PLZixunListBean.DataBean.ReplyListBean bean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);

        HashMap<String,String> map=new HashMap<>();
        map.put("newsId",newsId);
        map.put("userId",MakerApplication.instance().getUserid());
        if (!StringUtils.isBlank(toUid)){
            map.put("referUserId",toUid);
        }
        map.put("commentId",zhuId);
        map.put("content",msg);
        map.put("token", MakerApplication.instance.getToken());
        OkHttpUtils.post()
                .url(URLS.NEWS_NEWSCOMMENTREPLY)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(TuJiDetailsActivity.this, "评论失败",
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
                                    for (int i = 0; i <listData.size() ; i++) {
                                        if (listData.get(i).getCommentId().equals(bean.getPid())) {
                                            listData.get(i).getReplyList().add(0,bean);
                                            listData.get(i).setReplyNum(listData.get(i).getReplyNum()+1);
                                            break;
                                        }
                                    }
                                    notiAdapter();
                                    Toast.makeText(TuJiDetailsActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(TuJiDetailsActivity.this));

                                }else {

                                    Toast.makeText(TuJiDetailsActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });



    }

    private void sendCommentNoteZ(final String zhuId, String toUid, final String msg, final PLZixunListBean.DataBean info) {
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
                        Toast.makeText(TuJiDetailsActivity.this, "评论失败",
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
                                    initData(page);

                                    notiAdapter();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(TuJiDetailsActivity.this));

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
        AppUtils.setEvent(this,"ImgPageOpen","图集详情打开");
        StatService.onPageStart(this, "图集详情");
        TCAgent.onPageStart(this, "图集详情");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"图集详情");
        TCAgent.onPageEnd(this,"图集详情");
    }
}
