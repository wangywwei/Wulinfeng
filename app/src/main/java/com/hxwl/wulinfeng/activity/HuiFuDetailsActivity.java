package com.hxwl.wulinfeng.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.GenduoHuifuBean;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.PLZixunListBean;
import com.hxwl.utils.Photos;
import com.hxwl.wlf3.bean3.Pinlin3Bean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.HuiFuAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/21.
 * 回复详情页
 */
public class HuiFuDetailsActivity extends BaseActivity{
    public static Intent getIntent(Context context, String commentId, Pinlin3Bean.DataBean itemNotes, String url, String id, int usertype){
        Intent intent=new Intent(context,HuiFuDetailsActivity.class);
        intent.putExtra("bean", itemNotes);
        intent.putExtra("commentId",commentId);
        intent.putExtra("url",url);
        intent.putExtra("id",id);
        intent.putExtra("usertype",usertype);
        return intent;
    }
    private String id;
    private int usertype;
    private String url;
    private String commentId;
    private Pinlin3Bean.DataBean itemNotes = null ;
    private ListView lv_listview;
    private List<GenduoHuifuBean.DataBean.ReplyListBean> listData = new ArrayList<>() ;
    private View headView;
    private ImageView imageView;
    private TextView nickName;
    private TextView time;
    private TextView tv_zancount;
    private TextView tv_zancount2;
    private TextView msg;
    private ImageView iv_zan;
    private ImageView btn_comment_reply;
    //回复传递参数
    private String zhuId  = "";//心得id
    private String toUid  = "";//回复谁
    private String type = "1" ;
    private PLZixunListBean.DataBean.ReplyListBean bean = null ;

    private com.hxwl.common.utils.ShowDialog showDialog;

    private CommonSwipeRefreshLayout common_layout ;
    public boolean isRefresh = true;
    private int page = 1;
    private RelativeLayout frame_msg_ll;
    private TextView chat_btn_create_card;
    private EditText chat_et_create_context;
    private HuiFuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.huifudetails_activity);
        AppUtils.setTitle(HuiFuDetailsActivity.this);
        itemNotes = (Pinlin3Bean.DataBean) getIntent().getSerializableExtra("bean");
        commentId=getIntent().getStringExtra("commentId");
        url=getIntent().getStringExtra("url");
        id=getIntent().getStringExtra("id");
        usertype=getIntent().getIntExtra("usertype",0);
        if(itemNotes == null){
            return ;
        }
        initView();
        initHData();
        initData(page);
    }

    private void initHData() {
        if (itemNotes==null){
            return;
        }
        nickName.setText(Photos.stringPhoto(itemNotes.getNickName()));


        time.setText(itemNotes.getCommentTime());

        if (itemNotes.getContent() != null && !StringUtils.isEmpty(itemNotes.getContent())) {
            msg.setVisibility(View.VISIBLE);
            msg.setText(itemNotes.getContent());
        } else {
            msg.setVisibility(View.GONE);
        }
        ImageUtils.getCirclePic(URLS.IMG+itemNotes.getHeadImg(), imageView, HuiFuDetailsActivity.this);
        iv_zan.setTag(itemNotes);
        final int dianzannum=itemNotes.getIsFavour();
        if (itemNotes.getIsFavour()==1) {//点过赞
            tv_zancount.setTextColor(this.getResources().getColor(R.color.rgb_888888));
            iv_zan.setImageResource(R.drawable.yizan_icon);
        } else {//没有点过
            tv_zancount.setTextColor(this.getResources().getColor(R.color.rgb_888888));
            iv_zan.setImageResource(R.drawable.zan_icon);
        }

        iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                if (itemNotes.getIsFavour()==1) {//没有点过 holder.iv_zan.setImageResource(R.drawable.zanblue_icon);
                    dianZan2(iv_zan, itemNotes);
                } else {//没点过赞
                    dianZan(iv_zan, itemNotes);
                }
            }


            private void dianZan2(final ImageView view, final Pinlin3Bean.DataBean itemNotes) {
                OkHttpUtils.post()
                        .url(URLS.CANCELFAVOUR)
                        .addParams("targetId",itemNotes.getTargetId())
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("userId",MakerApplication.instance.getUid())
                        .addParams("type",usertype+"")    //赞类型 1动态的赞 2资讯评论的赞 3选手留言的赞
                        .build().execute(new StringCallback() {
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
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")){
                                    itemNotes.setIsFavour(2);
                                    view.setImageResource(R.drawable.zan_icon);
                                    if (dianzannum==1){
                                        tv_zancount.setText(itemNotes.getFavourNum()-1+"");//点赞数量
                                    }else {
                                        tv_zancount.setText(itemNotes.getFavourNum()+"");//点赞数量
                                    }

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(HuiFuDetailsActivity.this));
                                    HuiFuDetailsActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });


            }

            //点赞
            public void dianZan(final ImageView view, final Pinlin3Bean.DataBean itemNotes) {
                OkHttpUtils.post()
                        .url(URLS.ADDFAVOUR)
                        .addParams("targetId",itemNotes.getTargetId())
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("userId",MakerApplication.instance().getUserid())
                        .addParams("type",usertype+"")    //赞类型 1动态的赞 2资讯评论的赞 3选手留言的赞
                        .build().execute(new StringCallback() {
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
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")){
                                    itemNotes.setIsFavour(1);
                                    view.setImageResource(R.drawable.yizan_icon);
                                    if (dianzannum==1){
                                        tv_zancount.setText(itemNotes.getFavourNum()+"");//点赞数量
                                    }else {
                                        tv_zancount.setText(itemNotes.getFavourNum()+1+"");//点赞数量
                                    }


                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(HuiFuDetailsActivity.this));
                                    HuiFuDetailsActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });


            }
        });


        btn_comment_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                bean = new PLZixunListBean.DataBean.ReplyListBean();
                bean.setReferUserName(MakerApplication.instance.getNickName());
                bean.setReferUserId(MakerApplication.instance().getUserid());

                bean.setId(itemNotes.getId());
                bean.setUserId(itemNotes.getUserId());
                showKeyBoard(itemNotes.getId() ,"" ,"1") ;
            }
        });
    }

    /**
     *
     * @param zhuId 帖子的id
     * @param toUid 给谁发的 id
     * @param type  1跟帖 2回复
     */
    public void showKeyBoard(String zhuId ,String toUid ,String type ) {
        frame_msg_ll.setVisibility(View.VISIBLE);
        chat_et_create_context.setFocusable(true);
        chat_et_create_context.setFocusableInTouchMode(true);
        chat_et_create_context.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager)chat_et_create_context.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(chat_et_create_context, 0);

        this.zhuId = zhuId;
        this.toUid = toUid;
        this.type = type ;
    }

    private void initData(int page) {
        if (StringUtils.isBlank(url)){
            return;
        }
        OkHttpUtils.post()
                .url(url)
                .addParams("commentId",commentId)
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("pageNumber",page+"")
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("pageSize","10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                GenduoHuifuBean bean = gson.fromJson(response, GenduoHuifuBean.class);
                                if (bean.getCode().equals("1000")){
                                    tv_zancount.setText(bean.getData().getFavourNum()+"");
                                    tv_zancount2.setText(bean.getData().getReplyNum()+"");
                                    if (isRefresh) {
                                        listData.clear();
                                        listData.addAll(bean.getData().getReplyList());
                                    } else {
                                        listData.addAll(bean.getData().getReplyList());
                                    }
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    adapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(HuiFuDetailsActivity.this));
                                    HuiFuDetailsActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });


    }

    private void initView() {
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        headView = View.inflate(HuiFuDetailsActivity.this, R.layout.huifudetails_head_activity,null);
        imageView = (ImageView) headView.findViewById(R.id.user_icon);
        nickName = (TextView) headView.findViewById(R.id.user_name);
        time = (TextView) headView.findViewById(R.id.fatie_time);
        tv_zancount = (TextView) headView.findViewById(R.id.tv_zancount);
        tv_zancount2 = (TextView) headView.findViewById(R.id.tv_zancount2);
        msg = (TextView) headView.findViewById(R.id.pinglun_content);
        iv_zan = (ImageView) headView.findViewById(R.id.iv_zan);
        btn_comment_reply = (ImageView) headView.findViewById(R.id.btn_comment_reply);
        common_layout = (CommonSwipeRefreshLayout)findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1 ;
                isRefresh = true;
                initData(page);
            }

            @Override
            public void onLoadMore() {
                page ++ ;
                isRefresh = false;
                initData(page);
            }
        });

        showDialog = new ShowDialog(HuiFuDetailsActivity.this);
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
                    Toast.makeText(HuiFuDetailsActivity.this, "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                chat_et_create_context.setText("");
                if(bean == null){
                    return ;
                }
                sendCommentNote(zhuId , toUid, content ,bean);

                InputMethodManager imm = (InputMethodManager)HuiFuDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(frame_msg_ll,InputMethodManager.SHOW_FORCED);
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

            }
        });
        //滑动隐藏布局
        lv_listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = HuiFuDetailsActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager)HuiFuDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(frame_msg_ll,InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                }
        });
        lv_listview.addHeaderView(headView);
        adapter = new HuiFuAdapter(HuiFuDetailsActivity.this,listData);
        lv_listview.setAdapter(adapter);

        adapter.setOnPinlun1Clicklistener(new HuiFuAdapter.OnZanClicklistener() {
            @Override
            public void onPinlun1Clicklistener(int groupPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                Pinlin3Bean.DataBean.ReplyListBean info = itemNotes.getReplyList().get(groupPosition);
                bean = new PLZixunListBean.DataBean.ReplyListBean();
                bean.setReferUserName(MakerApplication.instance.getNickName());
                bean.setUserName(info.getReferUserNickName());
                bean.setReferUserId(MakerApplication.instance.getUid());
                bean.setId(itemNotes.getId());
                bean.setUserId(info.getReferUserId());
                chat_et_create_context.setHint("回复"+info.getNickName()+":");
                showKeyBoard(itemNotes.getId() ,info.getUserId() ,"2");

            }
        });

        adapter.setOnPinlun2Clicklistener(new HuiFuAdapter.OnZanClicklistener2() {
            @Override
            public void onPinlun2Clicklistener(int groupPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                Pinlin3Bean.DataBean.ReplyListBean info = itemNotes.getReplyList().get(groupPosition);
                bean = new PLZixunListBean.DataBean.ReplyListBean();
                bean.setReferUserName(MakerApplication.instance().getNickName());
                bean.setUserName(info.getReferUserNickName());
                bean.setReferUserId(MakerApplication.instance().getUserid());
                bean.setId(itemNotes.getId());
                bean.setUserId(info.getReferUserId());
                chat_et_create_context.setHint("回复"+info.getReferUserNickName()+":");
                showKeyBoard(itemNotes.getId() ,info.getReferUserId() ,"2");

            }
        });
    }


    /**
     * 发表评论方法  即时刷新一条数据
     * @param zhuId 帖子id
     * @param toUid 给谁评论
     * @param msg 发送信息
     */
    private String pinlunurl;
    private void sendCommentNote(final String zhuId , String toUid , final String msg , final PLZixunListBean.DataBean.ReplyListBean bean) {
        if (url.equals(URLS.PLAYER_COMMENTREPLYLIST)){
            pinlunurl=URLS.HOME_PLAYERCOMMENTREPLY;
        }else {
            pinlunurl=URLS.NEWS_NEWSCOMMENTREPLY;
        }
        HashMap<String,String> map=new HashMap<>();
        map.put("newsId",commentId);
        map.put("userId",MakerApplication.instance().getUserid());
        if (!StringUtils.isBlank(toUid)){
            map.put("referUserId",toUid);
        }
        if (!StringUtils.isBlank(id)){
            map.put("playerId",id);
        }
        map.put("commentId",zhuId);
        map.put("content",msg);
        map.put("token",MakerApplication.instance.getToken());
        OkHttpUtils.post()
                .url(pinlunurl)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(HuiFuDetailsActivity.this, "评论失败",
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
                                    isRefresh=true;
                                    initData(page);
                                    Toast.makeText(HuiFuDetailsActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(HuiFuDetailsActivity.this));
                                    HuiFuDetailsActivity.this.finish();
                                }else {
                                    Toast.makeText(HuiFuDetailsActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });
    }
}
