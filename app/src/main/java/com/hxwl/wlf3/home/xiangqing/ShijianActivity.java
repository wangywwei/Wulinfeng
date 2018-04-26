package com.hxwl.wlf3.home.xiangqing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.zixun.TujiLayout;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.ZixunXQBean;
import com.hxwl.wlf3.bean3.Pinlin3Bean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;

public class ShijianActivity extends BaseActivity implements View.OnClickListener, ShijianAdapter.OnshijianClicklistener,
        ShijianAdapter.OnZanClicklistener,ShijianAdapter.OnZanClicklistener2{


    private TextView chat_btn_create_card;
    private EditText chat_et_create_context;
    private RelativeLayout frame_msg_ll;
    private XRecyclerView shijian_recycler;
    private ArrayList<Pinlin3Bean.DataBean> list = new ArrayList<>();
    private ShijianAdapter adapter;
    private ImageView user_icon;
    private ImageView share_icon;
    private boolean ispinlun;

    public static Intent getIntent(Context context, String targetId) {
        Intent intent = new Intent(context, ShijianActivity.class);
        intent.putExtra("targetId", targetId);
        return intent;
    }

    private String targetId;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shijian);
        StatusBarUtil.setColor(ShijianActivity.this, getResources().getColor(R.color.tab_color), 0);
        targetId = getIntent().getStringExtra("targetId");
        initView();
        initHData();
        initData();

    }

    private void initHData() {
        OkHttpUtils.get()
                .url(URLS.EVENT + targetId)
                .addParams("userId", MakerApplication.instance.getUid())
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
                                Shijian3Bean zixunXQBean = gson.fromJson(response, Shijian3Bean.class);
                                if (zixunXQBean.getCode().equals("1000")) {

                                    initHView(zixunXQBean.getData());
                                } else if (zixunXQBean.getCode().equals("2002") || zixunXQBean.getCode().equals("2003")) {
                                    UIUtils.showToast(zixunXQBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ShijianActivity.this));
                                    ShijianActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


    }

    private void initHView(Shijian3Bean.DataBean data) {
        //1视频 2图文 3图集 4纯文本 5赛程 6对阵,7直播
        if (data.getShowType() == 1) {
            VideoLayout videoLayout=new VideoLayout(this);
            videoLayout.setData(data);
            shijian_recycler.addHeaderView(videoLayout);

        } else if (data.getShowType() == 3) {
            TujiLayout tujiLayout=new TujiLayout(this);
            Tuji3Layout tuji3Layout=new Tuji3Layout(this);
            tuji3Layout.setData(data);
            shijian_recycler.addHeaderView(tujiLayout);
            shijian_recycler.addHeaderView(tuji3Layout);

        } else if (data.getShowType() == 2||data.getShowType() == 4) {
            RichtextWeb richtextWeb=new RichtextWeb(this);
            richtextWeb.setData(data);
            shijian_recycler.addHeaderView(richtextWeb);
        }

    }

    private void initData() {
        OkHttpUtils.post()
                .url(URLS.COMMENTLIST)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("targetId", targetId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("pageNumber", page + "")
                .addParams("pageSize", "10")
                .addParams("type", "1")
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
                                    list.addAll(plZixunListBean.getData());
                                    adapter.notifyDataSetChanged();

                                } else if (plZixunListBean.getCode().equals("2002") || plZixunListBean.getCode().equals("2003")) {
                                    UIUtils.showToast(plZixunListBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ShijianActivity.this));
                                    ShijianActivity.this.finish();
                                } else {
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
        share_icon = (ImageView) findViewById(R.id.share_icon);
        user_icon = (ImageView) findViewById(R.id.user_icon);
        chat_btn_create_card = (TextView) findViewById(R.id.chat_btn_create_card);
        chat_et_create_context = (EditText) findViewById(R.id.chat_et_create_context);
        frame_msg_ll = (RelativeLayout) findViewById(R.id.frame_msg_ll);
        shijian_recycler = (XRecyclerView) findViewById(R.id.shijian_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shijian_recycler.setLayoutManager(layoutManager);
        shijian_recycler.setNestedScrollingEnabled(false);

        adapter = new ShijianAdapter(this, list);
        shijian_recycler.setAdapter(adapter);
        adapter.setOnshijianClicklistener(this);
        adapter.setOnPinlun1Clicklistener(this);
        adapter.setOnPinlun2Clicklistener(this);

        chat_btn_create_card.setOnClickListener(this);
        user_icon.setOnClickListener(this);
        share_icon.setOnClickListener(this);

        shijian_recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                shijian_recycler.refreshComplete();
                list.clear();
                page = 1;
                initData();
            }

            @Override
            public void onLoadMore() {
                page++;
                initData();
                shijian_recycler.refreshComplete();
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //发送
            case R.id.chat_btn_create_card:
                if (ispinlun){
                    sendCommentNote(chat_et_create_context.getText().toString().trim());
                }else {
                    sendCommentNoteZ(chat_et_create_context.getText().toString().trim());
                }
                chat_et_create_context.setText("");
                ispinlun=false;
                this.referUserId="";
                this.referUserNickName="";
                this.pid="";
                break;
            case R.id.user_icon:
                finish();
                break;
            case R.id.share_icon:

                break;
        }
    }
    private String referUserId;
    private String pid;
    private String referUserNickName;

    //点击条目，评论当前评论
    @Override
    public void onshijianClicklistener(int position) {
        chat_et_create_context.setFocusable(true);
        chat_et_create_context.setFocusableInTouchMode(true);
        chat_et_create_context.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(chat_et_create_context,0);
        ispinlun=true;
        this.referUserId="";
        this.referUserNickName="";
        this.pid=list.get(position).getId();

    }

    @Override
    protected void onDestroy() {
        if(TXMediaManager.instance(ShijianActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null){
            TXMediaManager.instance(ShijianActivity.this).videoDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"视频详情");
        TCAgent.onPageEnd(this,"视频详情");

        if(TXMediaManager.instance(ShijianActivity.this).mLivePlayer != null && TXMediaManager.instance(ShijianActivity.this).mLivePlayer.isPlaying()){
            TXMediaManager.instance(ShijianActivity.this).pauseplayer();
        }
    }

    @Override
    public void onPinlun1Clicklistener(int groupPosition, int childPosition) {
        chat_et_create_context.setFocusable(true);
        chat_et_create_context.setFocusableInTouchMode(true);
        chat_et_create_context.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(chat_et_create_context,0);
        ispinlun=true;
        //点击评论的评论前面
        this.referUserId=list.get(groupPosition).getCommentList().get(childPosition).getUserId();
        this.referUserNickName=list.get(groupPosition).getCommentList().get(childPosition).getNickName();
        this.pid=list.get(groupPosition).getId();

    }

    @Override
    public void onPinlun2Clicklistener(int groupPosition, int childPosition) {
        chat_et_create_context.setFocusable(true);
        chat_et_create_context.setFocusableInTouchMode(true);
        chat_et_create_context.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(chat_et_create_context,0);
        ispinlun=true;
        //点击评论的评论后面
        this.referUserId=list.get(groupPosition).getCommentList().get(childPosition).getReferUserId();
        this.referUserNickName=list.get(groupPosition).getCommentList().get(childPosition).getReferUserNickName();
        this.pid=list.get(groupPosition).getId();

    }

    //点击条目，评论当前事件
    private void sendCommentNoteZ(final String msg) {
        OkHttpUtils.post()
                .url(URLS.COMMENT)
                .addParams("targetId",targetId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("content",msg)
                .addParams("type","1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ShijianActivity.this, "评论失败",
                                Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                HomeOneBean homeOneBean = gson.fromJson(response, HomeOneBean.class);
                                if (homeOneBean.getCode().equals("1000")) {
                                    page=1;
                                    list.clear();
                                    initData();

                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ShijianActivity.this));
                                    ShijianActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void sendCommentNote(final String msg) {
        HashMap<String,String> map=new HashMap<>();
        map.put("targetId",targetId);
        map.put("userId",MakerApplication.instance.getUid());
        if (!StringUtils.isBlank(referUserId)){
            map.put("referUserId",referUserId);
        }
        map.put("pid",pid);
        map.put("content",msg);
        map.put("type","1");
        map.put("token", MakerApplication.instance.getToken());
        OkHttpUtils.post()
                .url(URLS.COMMENTREPLY)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(ShijianActivity.this, "评论失败",
                                Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                HomeOneBean homeOneBean = gson.fromJson(response, HomeOneBean.class);
                                if (homeOneBean.getCode().equals("1000")) {
                                    page=1;
                                    Pinlin3Bean.DataBean.CommentListBean huiFuBean=new Pinlin3Bean.DataBean.CommentListBean();

                                    huiFuBean.setPid(pid);
                                    huiFuBean.setContent(msg);
                                    huiFuBean.setNickName(MakerApplication.instance.getNickName());
                                    huiFuBean.setUserId(MakerApplication.instance.getUid());
                                    huiFuBean.setReferUserNickName(referUserNickName);
                                    huiFuBean.setReferUserId(referUserId);

                                    for (int i = 0; i <list.size() ; i++) {
                                        if (list.get(i).getId().equals(huiFuBean.getPid())) {
                                            list.get(i).getCommentList().add(0,huiFuBean);
                                            list.get(i).setFavourNum(list.get(i).getFavourNum()+1);
                                            break;
                                        }
                                    }
                                   adapter.notifyDataSetChanged();
                                    Toast.makeText(ShijianActivity.this, homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ShijianActivity.this));
                                    ShijianActivity.this.finish();
                                }else {
                                    Toast.makeText(ShijianActivity.this, homeOneBean.getMessage(),
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
