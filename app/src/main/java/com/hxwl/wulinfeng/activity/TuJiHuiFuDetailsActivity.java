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

import com.alibaba.fastjson.JSON;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.HuiFuAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.GengHuiFuBean;
import com.hxwl.wulinfeng.bean.TuJiListDatilsBean;
import com.hxwl.wulinfeng.bean.ZanBean;
import com.hxwl.wulinfeng.bean.ZiXunListDatilsBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/6/26.
 */
public class TuJiHuiFuDetailsActivity extends BaseActivity {
    private TuJiListDatilsBean itemNotes = null;
    private String id;

    private ListView lv_listview;
    private List<GengHuiFuBean> listData = new ArrayList<>();
    private View headView;
    private ImageView imageView;
    private TextView nickName;
    private TextView time;
    private TextView tv_zancount;
    private TextView msg;
    private ImageView iv_zan;
    private ImageView btn_comment_reply;

    //回复传递参数
    private String zhuId = "";//心得id
    private String toUid = "";//回复谁
    private String type = "1";
    private GengHuiFuBean bean = null;

    private com.hxwl.common.utils.ShowDialog showDialog;

    private CommonSwipeRefreshLayout common_layout;
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
        AppUtils.setTitle(TuJiHuiFuDetailsActivity.this);
        itemNotes = (TuJiListDatilsBean) getIntent().getSerializableExtra("bean");
        id = getIntent().getStringExtra("id");
        if (itemNotes == null) {
            return;
        }
        initView();
        initHData();
        initData(page);
    }

    private void initHData() {
        nickName.setText(itemNotes.getNickname());
        tv_zancount.setText(itemNotes.getZan_times());
        if (itemNotes.getTime() != null && !itemNotes.getTime().isEmpty()) {
            time.setText(TimeUtiles.getTimeed(itemNotes.getTime()));
        } else {
//            viewtime.setText(bean.getTime());
        }
        if (itemNotes.getMsg() != null && !StringUtils.isEmpty(itemNotes.getMsg())) {
            msg.setVisibility(View.VISIBLE);
            msg.setText(itemNotes.getMsg());
        } else {
            msg.setVisibility(View.GONE);
        }
        ImageUtils.getCirclePic(itemNotes.getHead_url(), imageView, TuJiHuiFuDetailsActivity.this);
        iv_zan.setTag(itemNotes);
        String is_zan = itemNotes.getIs_zan();
        if ("0".equals(is_zan)) {//没有点过
            iv_zan.setImageResource(R.drawable.zan_icon);
        } else {//点过赞
            iv_zan.setImageResource(R.drawable.yizan_icon);
        }
        iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    startActivity(new Intent(TuJiHuiFuDetailsActivity.this,LoginforCodeActivity.class));
                    return ;
                }
                String is_zan = itemNotes.getIs_zan();
                if ("0".equals(is_zan)) {//没有点过 iv_zan.setImageResource(R.drawable.zanblue_icon);
                    dianZan(iv_zan, itemNotes);
                } else {//点过赞
                    ToastUtils.showToast(TuJiHuiFuDetailsActivity.this, "已经点过赞了");
                }
            }

            //点赞
            public void dianZan(final View view, final TuJiListDatilsBean itemNotes) {
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    startActivity(new Intent(TuJiHuiFuDetailsActivity.this,LoginforCodeActivity.class));
                    return ;
                }
                JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                        TuJiHuiFuDetailsActivity.this, true, new ExcuteJSONObjectUpdate() {
                    @Override
                    public void excute(ResultPacket result) {
                        if (result != null && result.getStatus().equals("ok")) {
                            itemNotes.setIs_zan("1");
                            if (view instanceof ImageView) {
                                ((ImageView) view).setImageResource(R.drawable.yizan_icon);
                            } else {
                                ((ImageView) view.findViewById(R.id.iv_zan)).setImageResource(R.drawable.yizan_icon);
                            }
                            ZanBean bean = new ZanBean();
                            bean.setNickname(MakerApplication.instance().getUsername());
                            bean.setHead_url(MakerApplication.instance().getHeadPic());
                            bean.setUid(MakerApplication.instance().getUid());
                        } else if (result != null && result.getStatus().equals("empty")) {

                        } else {

                        }
                    }
                });
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("id", itemNotes.getId());
                map.put("type", "11");
                map.put("gentieId", itemNotes.getId());
                map.put("uid", MakerApplication.instance().getUid());
                map.put("loginKey", MakerApplication.instance().getLoginKey());
                map.put("method", NetUrlUtils.zixun_gentiezan);
                tasker.execute(map);

//                try {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("id", itemNotes.getId());
//                    map.put("type", "11");
//                    map.put("gentieId", itemNotes.getId());
//                    map.put("uid", MakerApplication.instance().getUid());
//                    map.put("loginKey", MakerApplication.instance().getLoginKey());
//                    AppClient.okhttp_post_Asyn(NetUrlUtils.zixun_gentiezan, map, new ICallback() {
//                        @Override
//                        public void error(IOException e) {
//                            TuJiHuiFuDetailsActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    UIUtils.showToast("服务器异常");
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void success(ResultPacket result) {
//                            if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                                itemNotes.setIs_zan("1");
//                                TuJiHuiFuDetailsActivity.this.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (view instanceof ImageView) {
//                                            ((ImageView) view).setImageResource(R.drawable.zanblue_icon);
//                                        } else {
//                                            ((ImageView) view.findViewById(R.id.iv_zan)).setImageResource(R.drawable.zanblue_icon);
//                                        }
//                                        ZanBean bean = new ZanBean();
//                                        bean.setNickname("Allen");
//                                        bean.setHead_url("http:\\/\\/wx.qlogo.cn\\/mmopen\\/PiajxSqBRaEKK7mASCM4bLibpzAkXfib5hgX8tiafmicxjnVFtt8CE6l18qaCUJFaZ2oxdJhc88KK5r01FtDKpHiahmg\\/0");
//                                        bean.setUid(MakerApplication.instance().getUid());
//                                    }
//                                });
//                            } else {
//                            }
//                        }
//                    });
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
            }
        });
        btn_comment_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    startActivity(new Intent(TuJiHuiFuDetailsActivity.this,LoginforCodeActivity.class));
                    return ;
                }
                bean = new GengHuiFuBean();
                bean.setFrom_head_url(MakerApplication.instance().getHeadPic());
                bean.setFrom_nickname(MakerApplication.instance().getUsername());
                bean.setFrom_uid(MakerApplication.instance().getUid());
                bean.setZhu_id(id);
                bean.setGen_id(itemNotes.getId());
                bean.setTo_uid(itemNotes.getUid());
                showKeyBoard(itemNotes.getId(), itemNotes.getUid(), "1");
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

    //TODO  时间：6-27 9：16
    private void initData(int page) {
        if (!SystemHelper.isConnected(TuJiHuiFuDetailsActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                TuJiHuiFuDetailsActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if (isRefresh) {
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(), GengHuiFuBean.class));
                    } else {
                        listData.addAll(JSON.parseArray(result.getData(), GengHuiFuBean.class));
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    adapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {
                    Toast.makeText(TuJiHuiFuDetailsActivity.this, "没有更多了",
                            Toast.LENGTH_SHORT).show();
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    showDialog.dismissProgressDialog();
                } else {
                    common_layout.setRefreshing(false);
                    UIUtils.showToast("服务器异常");
                }
            }
        }, "method=" + NetUrlUtils.tuji_gengduohuifu + page + itemNotes.getId());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", itemNotes.getId());
        map.put("page", page);
        map.put("method", NetUrlUtils.tuji_gengduohuifu);
        tasker.execute(map);

//        try {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", itemNotes.getId());
//            map.put("page", page);
//            AppClient.okhttp_post_Asyn(NetUrlUtils.tuji_gengduohuifu, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    TuJiHuiFuDetailsActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            common_layout.setRefreshing(false);
//                            UIUtils.showToast("服务器异常");
//                        }
//                    });
//                }
//
//                @Override
//                public void success(ResultPacket result) {
//                    if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                        if (isRefresh) {
//                            listData.clear();
//                            listData.addAll(JSON.parseArray(result.getData(), GengHuiFuBean.class));
//                        } else {
//                            listData.addAll(JSON.parseArray(result.getData(), GengHuiFuBean.class));
//                        }
//                        TuJiHuiFuDetailsActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                common_layout.setRefreshing(false);
//                                common_layout.setLoadMore(false);
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                    } else if (result.getStatus() != null && result.getStatus().equals("empty")) {
//                        TuJiHuiFuDetailsActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(TuJiHuiFuDetailsActivity.this, "没有更多了",
//                                        Toast.LENGTH_SHORT).show();
//                                common_layout.setRefreshing(false);
//                                common_layout.setLoadMore(false);
//                                showDialog.dismissProgressDialog();
//                            }
//                        });
//                    } else {
//
//                    }
//                }
//            });
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.btn_back);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_listview = (ListView) findViewById(R.id.lv_listview);
        headView = View.inflate(TuJiHuiFuDetailsActivity.this, R.layout.huifudetails_head_activity, null);
        imageView = (ImageView) headView.findViewById(R.id.user_icon);
        nickName = (TextView) headView.findViewById(R.id.user_name);
        time = (TextView) headView.findViewById(R.id.fatie_time);
        tv_zancount = (TextView) headView.findViewById(R.id.tv_zancount);
        msg = (TextView) headView.findViewById(R.id.pinglun_content);
        iv_zan = (ImageView) headView.findViewById(R.id.iv_zan);
        btn_comment_reply = (ImageView) headView.findViewById(R.id.btn_comment_reply);
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

        showDialog = new ShowDialog(TuJiHuiFuDetailsActivity.this);
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
                    startActivity(new Intent(TuJiHuiFuDetailsActivity.this,LoginforCodeActivity.class));
                    return ;
                }
                String content = chat_et_create_context.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(TuJiHuiFuDetailsActivity.this, "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                chat_et_create_context.setText("");
                if (bean == null) {
                    return;
                }
                sendCommentNote(zhuId, toUid, content, bean);

                InputMethodManager imm = (InputMethodManager) TuJiHuiFuDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
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

                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    startActivity(new Intent(TuJiHuiFuDetailsActivity.this,LoginforCodeActivity.class));
                    return ;
                }
            }
        });
        //滑动隐藏布局
        lv_listview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = TuJiHuiFuDetailsActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) TuJiHuiFuDetailsActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
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
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    startActivity(new Intent(TuJiHuiFuDetailsActivity.this,LoginforCodeActivity.class));
                    return ;
                }
                GengHuiFuBean info = null;
                if (view instanceof TextView) {
                    info = (GengHuiFuBean) view.getTag();
                } else {
                    info = (GengHuiFuBean) view.findViewById(R.id.user_name).getTag();
                }
                if (info == null) {
                    return;
                }
                bean = new GengHuiFuBean();
                bean.setFrom_head_url(MakerApplication.instance().getHeadPic());
                bean.setFrom_nickname(MakerApplication.instance().getUsername());
                bean.setTo_nickname(info.getFrom_nickname());
                bean.setFrom_uid(MakerApplication.instance().getUid());
                bean.setZhu_id(id);
                bean.setGen_id(itemNotes.getId());
                bean.setTo_uid(info.getFrom_uid());
                showKeyBoard(itemNotes.getId(), info.getFrom_uid(), "2");
            }
        });
        lv_listview.addHeaderView(headView);
//        adapter = new HuiFuAdapter(TuJiHuiFuDetailsActivity.this, listData);
        lv_listview.setAdapter(adapter);
    }


    /**
     * 发表评论方法  即时刷新一条数据
     *
     * @param zhuId 帖子id
     * @param toUid 给谁评论
     * @param msg   发送信息
     */
    private void sendCommentNote(final String zhuId, String toUid, final String msg, final GengHuiFuBean bean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                TuJiHuiFuDetailsActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    String id = result.getInsertid();
                    if (StringUtils.isEmpty(id)) {
                        return;
                    }
                    bean.setId(id);
                    bean.setMsg(msg);
                    listData.add(0, bean);
                    Toast.makeText(TuJiHuiFuDetailsActivity.this, "评论成功",
                            Toast.LENGTH_SHORT).show();
                    frame_msg_ll.setVisibility(View.GONE);
                    showDialog.dismissProgressDialog();
                    adapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {
                    Toast.makeText(TuJiHuiFuDetailsActivity.this, "评论失败",
                            Toast.LENGTH_SHORT).show();
                    frame_msg_ll.setVisibility(View.GONE);
                    showDialog.dismissProgressDialog();
                }
            }
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("uid", MakerApplication.instance().getUid());
        map.put("loginKey", MakerApplication.instance().getLoginKey());
        map.put("typeGentie", "2");
        map.put("type", "2");
        map.put("id", id);
        map.put("genId", zhuId);
        map.put("toUid", toUid);
        map.put("msg", msg);
        map.put("method", NetUrlUtils.zixun_gentiehuifu);
        tasker.execute(map);

//        Map<String, Object> map = new HashMap<>();
//        map.put("uid", MakerApplication.instance().getUid());
//        map.put("loginKey", MakerApplication.instance().getLoginKey());
//        map.put("typeGentie", "2");
//        map.put("type", "2");
//        map.put("id", id);
//        map.put("genId", zhuId);
//        map.put("toUid", toUid);
//        map.put("msg", msg);
//        try {
//            AppClient.okhttp_post_Asyn(NetUrlUtils.zixun_gentiehuifu, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    TuJiHuiFuDetailsActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(TuJiHuiFuDetailsActivity.this, "评论失败",
//                                    Toast.LENGTH_SHORT).show();
//                            frame_msg_ll.setVisibility(View.GONE);
//                            showDialog.dismissProgressDialog();
//                        }
//                    });
//                }
//
//                @Override
//                public void success(ResultPacket result) {
//                    if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                        String id = result.getInsertid();
//                        if (StringUtils.isEmpty(id)) {
//                            return;
//                        }
//                        bean.setId(id);
//                        bean.setMsg(msg);
//                        listData.add(0, bean);
//                        TuJiHuiFuDetailsActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(TuJiHuiFuDetailsActivity.this, "评论成功",
//                                        Toast.LENGTH_SHORT).show();
//                                frame_msg_ll.setVisibility(View.GONE);
//                                showDialog.dismissProgressDialog();
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                    }
//                }
//            });
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }
}
