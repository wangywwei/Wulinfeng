package com.hxwl.newwlf.home.home.intent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.wulin.CusListView;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

public class GodsActivity extends BaseActivity {
    public static Intent getIntent(Context context){
        Intent intent=new Intent(context,GodsActivity.class);
        return intent;
    }
    private CusListView expandableList;
    public boolean isRefresh = true;
    private String lastNewsId = "0";
    private com.hxwl.common.utils.ShowDialog showDialog;


    // 评论
    private RelativeLayout frame_msg_ll;
    private TextView chat_btn_create_card;//评论确认按钮
    private EditText chat_et_create_context;//评论输入框

    //回复传递参数
    private String zhuId = "";//心得id
    private String newsId;
    private String toUid = "";//回复谁
    private String type = "1";
    private PLZixunListBean.DataBean.ReplyListBean bean = null; //回复
    private PLZixunListBean.DataBean info = null;//盖楼

    private int page = 1;
    private List<PLZixunListBean.DataBean> zixunListBean = new ArrayList<>();
    private GodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gods);
        AppUtils.setTitle(GodsActivity.this);

        initView();
        initData(page);
    }


    private void initData(final int page) { //zixun_tiezidetail
        OkHttpUtils.post()
                .url(URLS.NEWS_GODCOMMENTLIST)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("pageNumber",page+"")
                .addParams("token", MakerApplication.instance.getToken())
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
                                    startActivity(LoginActivity.getIntent(GodsActivity.this));
                                    GodsActivity.this.finish();
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

        expandableList = (CusListView) findViewById(R.id.cusListView1);

        adapter = new GodsAdapter(GodsActivity.this, zixunListBean, replyToCommentListener);
        expandableList.setAdapter(adapter);
        initLouZhuInfo();

        adapter.setOnPinlun1Clicklistener(new GodsAdapter.OnZanClicklistener() {
            @Override
            public void onPinlun1Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final PLZixunListBean.DataBean.ReplyListBean itemNoteComment = zixunListBean.get(groupPosition).getReplyList().get(childPosition);
                if (itemNoteComment == null) {

                } else {
                    bean = new PLZixunListBean.DataBean.ReplyListBean();
                    bean.setReferUserName(itemNoteComment.getUserName());
                    bean.setReferUserId(itemNoteComment.getUserId());
                    bean.setId(itemNoteComment.getId());
                    bean.setUserId(MakerApplication.instance.getUid());
                    bean.setUserName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getUserId()+"", "2",zixunListBean.get(groupPosition).getNewsId());
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getUserName()) + ":");
                }
            }
        });

        adapter.setOnPinlun2Clicklistener(new GodsAdapter.OnZanClicklistener2() {
            @Override
            public void onPinlun2Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final PLZixunListBean.DataBean.ReplyListBean itemNoteComment = zixunListBean.get(groupPosition).getReplyList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    bean = new PLZixunListBean.DataBean.ReplyListBean();
                    bean.setReferUserName(itemNoteComment.getReferUserName());
                    bean.setReferUserId(itemNoteComment.getReferUserId());
                    bean.setPid(itemNoteComment.getPid());
                    bean.setUserId(MakerApplication.instance.getUid());
                    bean.setUserName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getReferUserId()+"", "2",zixunListBean.get(groupPosition).getNewsId());
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getReferUserName()) + ":");
                }
            }
        });

        showDialog = new ShowDialog(GodsActivity.this);
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
                    Toast.makeText(GodsActivity.this, "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                chat_et_create_context.setText("");
                chat_et_create_context.setHint("我也说两句...");

                if (bean == null) {
                    return;
                }
                AppUtils.setEvent(GodsActivity.this,"InfoReply","资讯-回复");
                sendCommentNote(zhuId, toUid, content, bean);
                InputMethodManager imm = (InputMethodManager) GodsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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

                InputMethodManager imm = (InputMethodManager) GodsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(chat_et_create_context, 0);

            }
        });
        //滑动隐藏布局
        expandableList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                chat_et_create_context.setHint("我也说两句...");

                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = GodsActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
//                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) GodsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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
            bean = new PLZixunListBean.DataBean.ReplyListBean();
            bean.setUserName(MakerApplication.instance().getNickName());
            bean.setUserId(MakerApplication.instance().getUserid());
            bean.setReferUserName("");
            bean.setReferUserId("");
            bean.setPid(itemNotes.getCommentId());
            bean.setReferUserName("");
            showKeyBoard(itemNotes.getCommentId()+"", "", "1",itemNotes.getNewsId());
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
    public void showKeyBoard(String zhuId, String toUid, String type,String newsId) {
        frame_msg_ll.setVisibility(View.VISIBLE);
        chat_et_create_context.setFocusable(true);
        chat_et_create_context.setFocusableInTouchMode(true);
        chat_et_create_context.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) chat_et_create_context.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(chat_et_create_context, 0);
        this.newsId = newsId;
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
        map.put("token", MakerApplication.instance.getToken());
        map.put("content",msg);
        OkHttpUtils.post()
                .url(URLS.NEWS_NEWSCOMMENTREPLY)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(GodsActivity.this, "评论失败",
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
                                    bean.setPid(zhuId);
                                    bean.setContent(msg);
                                    for (int i = 0; i <zixunListBean.size() ; i++) {
                                        if (zixunListBean.get(i).getCommentId().equals(bean.getPid())) {
                                            zixunListBean.get(i).getReplyList().add(0,bean);
                                            break;
                                        }
                                    }
                                    notiAdapter();
                                    Toast.makeText(GodsActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(GodsActivity.this));
                                    GodsActivity.this.finish();
                                }else {
                                    Toast.makeText(GodsActivity.this, homeOneBean.getMessage(),
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



    @Override
    protected void onDestroy() {
        super.onDestroy();

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
