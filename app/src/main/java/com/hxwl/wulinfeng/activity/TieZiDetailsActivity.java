package com.hxwl.wulinfeng.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.DongTaiHuifuBean;
import com.hxwl.newwlf.modlebean.DongtaiBean;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.TieZiDetailsAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.NetWorkUtils;
import com.hxwl.wulinfeng.util.PreciseCompute;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.wulin.ImageListGridViewAdapter;
import com.hxwl.wulinfeng.wulin.ImageViewPageActivity;
import com.hxwl.wulinfeng.wulin.MyGridView;
import com.hxwl.wulinfeng.wulin.SeeHighDefinitionPictureActivity;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/12.
 * 帖子详情页
 */
public class TieZiDetailsActivity extends BaseActivity {
    public static Intent getIntent(Context context, String zhu_id, DongtaiBean.DataBean dataBean) {
        Intent intent = new Intent(context, TieZiDetailsActivity.class);
        intent.putExtra("zhu_id", zhu_id);
        intent.putExtra("dataBean", dataBean);
        return intent;
    }

    private DongtaiBean.DataBean dataBean;
    private ListView lv_listview;
    private View headview;
    private TieZiDetailsAdapter tieZiDetailsAdapter;
    private List<DongTaiHuifuBean.DataBean.CommentListBean> huiFuBeanList = new ArrayList<>();
    private int m_ScreenWidth;

    private ImageView imageView;
    private TextView nickName;
    private TextView time;
    private TextView tv_zan;
    private TextView tv_zan_text;
    private TextView msg;
    private ImageView iv_zan;
    private ImageView btn_comment_reply;
    private ImageView iv_single_image;
    private MyGridView rv_note_image;
    private RelativeLayout rl_note_images;
    private RelativeLayout rl_relayout;
    private ShowDialog showDialog;
    private EditText chat_et_create_context;
    private TextView chat_btn_create_card;
    private TextView pinlun_num;
    private TextView zan_num;
    private RelativeLayout frame_msg_ll;

    //回复传递参数
    private String zhu_id;
    private String zhuId = "";//心得id
    private String toUid = "";//回复谁
    private String type = "1";
    private DongTaiHuifuBean.DataBean.CommentListBean bean = null;
//    private WuLinZuiXinBean wuLinZuiXinBean;


    private CommonSwipeRefreshLayout common_layout;
    private int page = 1;
    private boolean isRefresh;
    //新添加的布局
    private FrameLayout fl_item;
    private RelativeLayout rlyt_video_bg;
    private ImageView iv_video_bg;
    private ImageView iv_start;
    private View ic_net_warn;
    private RelativeLayout videoContainer;
    public Button btn1;//移动网络继续播放按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiezidetails_activity);
        AppUtils.setTitle(TieZiDetailsActivity.this);
        //获取屏幕的宽高
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        m_ScreenWidth = wm.getDefaultDisplay().getWidth() - ScreenUtils.dip2px(TieZiDetailsActivity.this, 20);
        zhu_id = getIntent().getStringExtra("zhu_id");
        dataBean = (DongtaiBean.DataBean) getIntent().getSerializableExtra("dataBean");
        initView();
        initHData();
        initData(page);
    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        common_layout = (CommonSwipeRefreshLayout) findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                initData(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initData(page);
            }
        });
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        headview = View.inflate(TieZiDetailsActivity.this, R.layout.tiezidetails_head_activity, null);
        lv_listview.addHeaderView(headview);

        imageView = (ImageView) headview.findViewById(R.id.user_icon);
        nickName = (TextView) headview.findViewById(R.id.user_name);
        time = (TextView) headview.findViewById(R.id.fatie_time);
        tv_zan = (TextView) headview.findViewById(R.id.tv_zan);
        tv_zan_text = (TextView) headview.findViewById(R.id.tv_zan_text);
        msg = (TextView) headview.findViewById(R.id.pinglun_content);
        zan_num= (TextView) headview.findViewById(R.id.zan_num);
        pinlun_num= (TextView) headview.findViewById(R.id.pinlun_num);
        iv_zan = (ImageView) headview.findViewById(R.id.iv_zan);
        btn_comment_reply = (ImageView) headview.findViewById(R.id.btn_comment_reply);

        iv_single_image = (ImageView) headview
                .findViewById(R.id.iv_single_image);
        rv_note_image = (MyGridView) headview
                .findViewById(R.id.rv_note_image);
        rl_note_images = (RelativeLayout) headview
                .findViewById(R.id.rl_note_images);
        rl_relayout = (RelativeLayout) headview
                .findViewById(R.id.rl_relayout);
        //新增功能 视频模块
        fl_item = (FrameLayout) headview.findViewById(R.id.fl_item);
        rlyt_video_bg = (RelativeLayout) headview.findViewById(R.id.rlyt_video_bg);
        iv_video_bg = (ImageView) headview.findViewById(R.id.iv_video_bg);
        iv_start = (ImageView) headview.findViewById(R.id.iv_start);
        ic_net_warn = headview.findViewById(R.id.ic_net_warn);
        videoContainer = (RelativeLayout) headview.findViewById(R.id.videoContainer);
        btn1 = (Button) ic_net_warn.findViewById(R.id.btn1);


        btn_comment_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isBlank(MakerApplication.instance.getMobile())) {
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                bean = new DongTaiHuifuBean.DataBean.CommentListBean();
                bean.setReferUserName(MakerApplication.instance().getNickName());
                bean.setReferUserId(MakerApplication.instance().getUserid());
                bean.setId(zhu_id);
                bean.setUserId("");
                showKeyBoard(zhu_id, "", "1");
            }
        });

        showDialog = new ShowDialog(TieZiDetailsActivity.this);
        // 评论对话框
        frame_msg_ll = (RelativeLayout) findViewById(R.id.frame_msg_ll);
        //评论发送按钮
        chat_btn_create_card = (TextView) findViewById(R.id.chat_btn_create_card);
        chat_btn_create_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (StringUtils.isBlank(MakerApplication.instance.getMobile())) {
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                String content = chat_et_create_context.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(TieZiDetailsActivity.this, "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(zhu_id)) {
                    Toast.makeText(TieZiDetailsActivity.this, "参数错误",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                chat_et_create_context.setText("");
                if (bean == null) {
                    return;
                }
                sendCommentNote(zhuId, toUid, content, bean);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
            }
        });
        // 评论输入框
        chat_et_create_context = (EditText) findViewById(R.id.chat_et_create_context);

        lv_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = TieZiDetailsActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        tieZiDetailsAdapter = new TieZiDetailsAdapter(TieZiDetailsActivity.this, huiFuBeanList);
        lv_listview.setAdapter(tieZiDetailsAdapter);

        tieZiDetailsAdapter.setOnPinlun1Clicklistener(new TieZiDetailsAdapter.OnZanClicklistener() {
            @Override
            public void onPinlun1Clicklistener(int groupPosition) {
                if (StringUtils.isBlank(MakerApplication.instance.getMobile())) {
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                DongTaiHuifuBean.DataBean.CommentListBean info = huiFuBeanList.get(groupPosition);
                bean = new DongTaiHuifuBean.DataBean.CommentListBean();
                bean.setReferUserName(MakerApplication.instance().getNickName());
                bean.setUserName(info.getReferUserName());
                bean.setReferUserId(MakerApplication.instance().getUserid());
                bean.setId(dataBean.getMessageId());
                bean.setUserId(info.getUserId());
                chat_et_create_context.setHint("回复" + info.getUserName() + ":");
                showKeyBoard(dataBean.getMessageId(), info.getUserId(), "2");
            }
        });

        tieZiDetailsAdapter.setOnPinlun2Clicklistener(new TieZiDetailsAdapter.OnZanClicklistener2() {
            @Override
            public void onPinlun2Clicklistener(int groupPosition) {
                if (StringUtils.isBlank(MakerApplication.instance.getMobile())) {
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                DongTaiHuifuBean.DataBean.CommentListBean info = huiFuBeanList.get(groupPosition - 1);
                bean = new DongTaiHuifuBean.DataBean.CommentListBean();
                bean.setReferUserName(MakerApplication.instance().getNickName());
                bean.setUserName(info.getReferUserName());
                bean.setReferUserId(MakerApplication.instance().getUserid());
                bean.setId(dataBean.getMessageId());
                bean.setUserId(info.getUserId());
                chat_et_create_context.setHint("回复" + info.getReferUserName() + ":");
                showKeyBoard(dataBean.getMessageId(), info.getReferUserId(), "2");
            }
        });
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

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

    private void initData(int page) {
        if (!SystemHelper.isConnected(TieZiDetailsActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }

        OkHttpUtils.post()
                .url(URLS.DYNAMIC_DYNAMICCOMMENTREPLYLIST)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("commendId", zhu_id)
                .addParams("pageNumber", page + "")
                .addParams("pageSize", "10")
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                DongTaiHuifuBean bean = gson.fromJson(response, DongTaiHuifuBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if (isRefresh) {
                                        huiFuBeanList.clear();
                                        huiFuBeanList.addAll(bean.getData().getCommentList());
                                    } else {
                                        huiFuBeanList.addAll(bean.getData().getCommentList());
                                    }
                                    zan_num.setText(bean.getData().getFavourNum()+"");
                                    pinlun_num.setText(bean.getData().getCommentNum()+"");
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    tieZiDetailsAdapter.notifyDataSetChanged();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initHData() {
        if (!SystemHelper.isConnected(TieZiDetailsActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        initHeadData();
    }

    private void initHeadData() {
        nickName.setText(dataBean.getMessageUserName());
        zan_num.setText(dataBean.getFavourNum()+"");
        pinlun_num.setText(dataBean.getCommentNum()+"");
        if (dataBean.getUpdateTime() != null && dataBean.getUpdateTime().isEmpty()) {
            time.setText(dataBean.getUpdateTime());
        } else {
            time.setVisibility(View.GONE);
//            viewtime.setText(bean.getTime());
        }
        if (dataBean.getContent() != null && !StringUtils.isEmpty(dataBean.getContent())) {
            msg.setVisibility(View.VISIBLE);
            msg.setText(dataBean.getContent());
        } else {
            msg.setVisibility(View.GONE);
        }
        ImageUtils.getCirclePic(URLS.IMG + dataBean.getHeadImage(), imageView, TieZiDetailsActivity.this);
        iv_zan.setTag(dataBean);
        if (dataBean.getUserIsFavourMessage() == 2) {//没有点过
            iv_zan.setImageResource(R.drawable.zan_icon);
        } else {//点过赞
            iv_zan.setImageResource(R.drawable.yizan_icon);
        }
        try {
            List<DongtaiBean.DataBean.FavourListBean> listNotePraise = dataBean
                    .getFavourList();
            if (listNotePraise != null && listNotePraise.size() > 0) {
                tv_zan_text.setVisibility(View.VISIBLE);
                rl_relayout.setVisibility(View.VISIBLE);
                tv_zan.setVisibility(View.VISIBLE);
                StringBuffer name = new StringBuffer();
                for (int i = 0; i < listNotePraise.size(); i++) {
                    if (i == listNotePraise.size() - 1) {
                        name.append(listNotePraise.get(i).getNickName());
                    } else {
                        name.append(listNotePraise.get(i).getNickName() + "、");
                    }
                }
                tv_zan.setText(name);
//                holder.tv_zan_text.setText(listNotePraise.size() + "人点过赞");
            } else {
                rl_relayout.setVisibility(View.GONE);
                tv_zan.setVisibility(View.GONE);
                tv_zan_text.setVisibility(View.GONE);
            }
        } catch (Exception i) {

        }
        final int dianzannum=dataBean.getUserIsFavourMessage();
        iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isBlank(MakerApplication.instance.getMobile())) {
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                if (dataBean.getUserIsFavourMessage() == 1) {//没有点过 holder.iv_zan.setImageResource(R.drawable.zanblue_icon);
                    quxiaoDianZan();
                } else {//没点过赞
                    dianZan();
                }
            }

            private void setZanImg() {
                List<DongtaiBean.DataBean.FavourListBean> listNotePraise = dataBean
                        .getFavourList();
                if (listNotePraise != null && listNotePraise.size() > 0) {
                    rl_relayout.setVisibility(View.VISIBLE);
                    tv_zan.setVisibility(View.VISIBLE);
                    tv_zan_text.setVisibility(View.VISIBLE);
                    StringBuffer name = new StringBuffer();
                    for (int i = 0; i < listNotePraise.size(); i++) {
                        if (i == listNotePraise.size() - 1) {
                            name.append(listNotePraise.get(i).getNickName());
                        } else {
                            name.append(listNotePraise.get(i).getNickName() + "、");
                        }
                    }
                    tv_zan.setText(name);
//                    holder.tv_zan_text.setText(listNotePraise.size() + "人点过赞");
                } else {
                    rl_relayout.setVisibility(View.GONE);
                    tv_zan.setVisibility(View.GONE);
                    tv_zan_text.setVisibility(View.GONE);
                }
            }

            //点赞
            public void dianZan() {
                OkHttpUtils.post()
                        .url(URLS.SCHEDULE_ADDDYNAMICMESSAGEFAVOUR)
                        .addParams("targetId", dataBean.getMessageId())
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("userId", MakerApplication.instance().getUserid())
                        .addParams("favourType", "1")    //赞类型 1动态的赞 2资讯评论的赞 3选手留言的赞
                        .build().execute(new StringCallback() {
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
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {
                                    dataBean.setUserIsFavourMessage(1);
                                    iv_zan.setImageResource(R.drawable.yizan_icon);
                                    DongtaiBean.DataBean.FavourListBean favourListBean = new DongtaiBean.DataBean.FavourListBean();
                                    favourListBean.setNickName(MakerApplication.instance().getNickName());
                                    favourListBean.setUserId(MakerApplication.instance().getUserid());
                                    dataBean.getFavourList().add(0, favourListBean);
                                    if (dianzannum==1){
                                        zan_num.setText(dataBean.getFavourNum()+"");//点赞数量
                                    }else {
                                        zan_num.setText(dataBean.getFavourNum()+1+"");//点赞数量
                                    }

                                    setZanImg();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(TieZiDetailsActivity.this));
                                    TieZiDetailsActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

            }

            //取消点赞
            public void quxiaoDianZan() {
                OkHttpUtils.post()
                        .url(URLS.SCHEDULE_CANCELDYNAMICMESSAGEFAVOUR)
                        .addParams("targetId", dataBean.getMessageId())
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("userId", MakerApplication.instance().getUserid())
                        .addParams("favourType", "1")    //赞类型 1动态的赞 2资讯评论的赞 3选手留言的赞
                        .build().execute(new StringCallback() {
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
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {
                                    dataBean.setUserIsFavourMessage(2);
                                    iv_zan.setImageResource(R.drawable.zan_icon);
                                    for (DongtaiBean.DataBean.FavourListBean info : dataBean.getFavourList()) {
                                        if (MakerApplication.instance().getUserid().equals(info.getUserId())) {
                                            dataBean.getFavourList().remove(info);
                                            break;
                                        }
                                    }

                                    if (dianzannum==1){
                                        zan_num.setText(dataBean.getFavourNum()-1+"");//点赞数量
                                    }else {
                                        zan_num.setText(dataBean.getFavourNum()+"");//点赞数量
                                    }
                                    setZanImg();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(TieZiDetailsActivity.this));
                                    TieZiDetailsActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

            }
        });


        List<String> listImageItems2 = new ArrayList<>();
        if (!StringUtils.isBlank(dataBean.getImageList()) && dataBean.getImageList().indexOf(",") != -1) {
            String[] temp = null;
            temp = dataBean.getImageList().split(",");
            for (int i = 0; i < temp.length; i++) {
                listImageItems2.add(URLS.IMG + temp[i]);
            }
        } else if (!StringUtils.isBlank(dataBean.getImageList())) {
            listImageItems2.add(URLS.IMG + dataBean.getImageList());
        } else {

        }
        final List<String> listImageItems = listImageItems2;
        int isHavaVideo = dataBean.getMessageType();
//================================================================================================
        if (isHavaVideo == 2) {
            rl_note_images.setVisibility(View.GONE);
            iv_single_image.setVisibility(View.GONE);
            rv_note_image.setVisibility(View.GONE);
            rlyt_video_bg.setVisibility(View.VISIBLE);
            ic_net_warn.setVisibility(View.GONE);
            videoContainer.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(dataBean.getVideoPreviewImage())) {
                ImageUtils.getPic(URLS.IMG + dataBean.getVideoPreviewImage(), iv_video_bg, TieZiDetailsActivity.this);
            }
            iv_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!NetWorkUtils.isWifiConnected(TieZiDetailsActivity.this)) {
                        ic_net_warn.setVisibility(View.VISIBLE);
                    } else {
                        //隐藏该容器，显示播放容器
                        ic_net_warn.setVisibility(View.GONE);
                        rlyt_video_bg.setVisibility(View.GONE);
                        videoContainer.setVisibility(View.VISIBLE);
                        playVideo(URLS.VIDEO + dataBean.getVideoName(),
                                videoContainer, 0, 0, dataBean.getContent());
//                        }
                    }
                }
            });
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ic_net_warn.setVisibility(View.GONE);
                    //隐藏该容器，显示播放容器
                    rlyt_video_bg.setVisibility(View.GONE);
                    videoContainer.setVisibility(View.VISIBLE);
                    playVideo(URLS.VIDEO + dataBean.getVideoName(),
                            videoContainer, 0, 0, dataBean.getContent());
                }
            });


        } else if (listImageItems != null && listImageItems.size() > 1) {
            // 加载图片--9张图片
            rlyt_video_bg.setVisibility(View.GONE);
            rl_note_images.setVisibility(View.VISIBLE);
            iv_single_image.setVisibility(View.GONE);
            rv_note_image.setVisibility(View.VISIBLE);
            ic_net_warn.setVisibility(View.GONE);
            videoContainer.setVisibility(View.GONE);

            ImageListGridViewAdapter adapter = new ImageListGridViewAdapter(
                    TieZiDetailsActivity.this, listImageItems);
            rv_note_image.setAdapter(adapter);
            rv_note_image
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            //多张图片点击效果
                            if (listImageItems != null) {
                                Intent intent = new Intent(TieZiDetailsActivity.this,
                                        ImageViewPageActivity.class);
                                intent.putExtra("position", position);
                                intent.putExtra("jsonArray",
                                        JSON.toJSONString(listImageItems));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });
        } else if (listImageItems != null && listImageItems.size() == 1) {
            rlyt_video_bg.setVisibility(View.GONE);
            rl_note_images.setVisibility(View.VISIBLE);
            iv_single_image.setVisibility(View.VISIBLE);
            rv_note_image.setVisibility(View.GONE);
            ic_net_warn.setVisibility(View.GONE);
            videoContainer.setVisibility(View.GONE);
//            ImageUtils.getPic(listImageItems.get(0), iv_single_image, TieZiDetailsActivity.this);
            final String url = listImageItems.get(0);
//            ImageUtils.getPic(url,iv_single_image,context);
            if (url.contains(".gif") || url.contains(".GIF")) {
                ImageUtils.getGifPic(url, R.drawable.icon_pic_default, TieZiDetailsActivity.this, new SimpleTarget<GifDrawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(GifDrawable gifDrawable, GlideAnimation glideAnimation) {
                        iv_single_image.setImageDrawable(gifDrawable);
                    }

                });
            } else {
                ImageUtils.getBitmapPic(url, R.drawable.icon_pic_default, TieZiDetailsActivity.this, new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        int picHeight = bitmap.getHeight();
                        int picWidth = bitmap.getWidth();
                        ViewGroup.LayoutParams params = iv_single_image.getLayoutParams();
                        double scale = PreciseCompute.div(picWidth, picHeight, 2);
                        int scaleHeight = (int) PreciseCompute.div(m_ScreenWidth, scale, 2);
                        params.width = m_ScreenWidth / 2;
                        params.height = scaleHeight / 2;
                        iv_single_image.setLayoutParams(params);

                        Bitmap bm = BitmapUtils.zoomBitmap(bitmap, m_ScreenWidth, scaleHeight);
                        iv_single_image.setImageBitmap(bitmap);
                    }

                });
            }

            iv_single_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //单张图片点击效果
                    if (!StringUtils.isEmpty(listImageItems.get(0))) {
                        Intent intent = new Intent(
                                TieZiDetailsActivity.this,
                                SeeHighDefinitionPictureActivity.class);
                        intent.putExtra("highDefinitionUrl",
                                listImageItems.get(0));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                }
            });
        } else {
            rl_note_images.setVisibility(View.GONE);
            iv_single_image.setVisibility(View.GONE);
            rv_note_image.setVisibility(View.GONE);
            rlyt_video_bg.setVisibility(View.GONE);
            ic_net_warn.setVisibility(View.GONE);
            videoContainer.setVisibility(View.GONE);
        }
    }

    public void playVideo(String url, RelativeLayout videoContainer, int position, int oldposition, String title) {
        if (position == -1) {
            return;
        }
        try {
            //添加乐视点播
            if (TXMediaManager.instance(TieZiDetailsActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
                TXMediaManager.instance(TieZiDetailsActivity.this).videoDestroy();
            }
            TXMediaManager.instance(TieZiDetailsActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
            TXMediaManager.instance(TieZiDetailsActivity.this).TXVodPlayAndView(url, videoContainer, -1, title);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发表评论方法  即时刷新一条数据
     *
     * @param zhuId 帖子id
     * @param toUid 给谁评论
     * @param msg   发送信息
     */
    private void sendCommentNote(final String zhuId, String toUid, final String msg, final DongTaiHuifuBean.DataBean.CommentListBean bean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        Map<String, String> map = new HashMap<>();
        map.put("messageId", zhuId);
        map.put("userId", SPUtils.get(TieZiDetailsActivity.this, "id", "-1") + "");
        map.put("content", msg);
        map.put("token", MakerApplication.instance.getToken());
        if (!StringUtils.isBlank(toUid)) {
            map.put("referUserId", toUid);
        }

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_DYNAMICCOMMENT)
                .params(map)
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
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {
                                    Toast.makeText(TieZiDetailsActivity.this, "评论成功",
                                            Toast.LENGTH_SHORT).show();
                                    isRefresh = true;
                                    initData(page);
                                    frame_msg_ll.setVisibility(View.GONE);
                                    showDialog.dismissProgressDialog();
                                } else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(TieZiDetailsActivity.this));
                                    TieZiDetailsActivity.this.finish();
                                }else {
                                    Toast.makeText(TieZiDetailsActivity.this, bean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    frame_msg_ll.setVisibility(View.GONE);
                                    showDialog.dismissProgressDialog();
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
        StatService.onPageStart(this, "武林详情");
        TCAgent.onPageStart(this, "武林详情");
        TXMediaManager.instance(TieZiDetailsActivity.this).resumeplayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "武林详情");
        TCAgent.onPageEnd(this, "武林详情");
        if (TXMediaManager.instance(TieZiDetailsActivity.this).mLivePlayer != null && TXMediaManager.instance(TieZiDetailsActivity.this).mLivePlayer.isPlaying()) {
            TXMediaManager.instance(TieZiDetailsActivity.this).pauseplayer();
        }
    }

    @Override
    protected void onDestroy() {
        if (TXMediaManager.instance(TieZiDetailsActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
            TXMediaManager.instance(TieZiDetailsActivity.this).videoDestroy();
        }
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ScreenUtils.isLandScape(this)) {
                TXMediaManager.instance(TieZiDetailsActivity.this).mPlayerView1.backPress();
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                return true;
            } else {
                finish();
            }
        }
        return false;
    }
}
