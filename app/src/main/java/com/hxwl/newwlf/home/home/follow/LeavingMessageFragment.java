package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.LeavingBean;
import com.hxwl.utils.Photos;
import com.hxwl.wlf3.bean3.Pinlin3Bean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.KeyboardChangeListener;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.wulin.CusListView;
import com.jaeger.library.StatusBarUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
/*
* 关注——留言
* */

public class LeavingMessageFragment extends BaseFragment implements  KeyboardChangeListener.KeyBoardListener{
    private View view;
    private String newsId;
    private String type;
    public void setNewsId(String newsId,String type) {
        this.newsId = newsId;
        this.type = type;
        page=1;
        initData(page);
        if (!StringUtils.isBlank(newsId)){
            adapter.setId(newsId);
        }

    }
    private CusListView expandableList;
    public boolean isRefresh = true;
    private com.hxwl.common.utils.ShowDialog showDialog;

    private int huifuState = 0; //默认是盖楼-0！ 1是回复

    // 评论
    private RelativeLayout frame_msg_ll;
    private TextView chat_btn_create_card;//评论确认按钮
    private EditText chat_et_create_context;//评论输入框

    //回复传递参数
    private String zhuId = "";//心得id
    private String toUid = "";//回复谁

    private Pinlin3Bean.DataBean.CommentListBean bean = null; //回复
    private Pinlin3Bean.DataBean info = null;//盖楼
    private KeyboardChangeListener mKeyboardChangeListener;
    private int page = 1;
    private List<Pinlin3Bean.DataBean> zixunListBean = new ArrayList<>();
    private LeavingAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_leaving_message, null);
            initView(view);
            initData(page);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.shouye_tab), 0);

        return view;
    }

    private void initData(final int page) { //zixun_tiezidetail
        if (StringUtils.isBlank(newsId)){
            return;
        }
        OkHttpUtils.post()
                .url(URLS.COMMENTLIST)
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("targetId",newsId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("pageNumber",page+"")
                .addParams("pageSize","10")
                .addParams("type",type)
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
                                        zixunListBean.clear();
                                        zixunListBean.addAll(plZixunListBean.getData());
                                    } else {
                                        zixunListBean.addAll(plZixunListBean.getData());
                                    }
                                    for (Pinlin3Bean.DataBean info : zixunListBean) {
                                        String zhuID = info.getId()+"";
                                        for (Pinlin3Bean.DataBean.CommentListBean huifu : info.getCommentList()) {
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
                                    startActivity(LoginActivity.getIntent(getActivity()));

                                }else if (plZixunListBean != null && plZixunListBean.getData().size()<10) {
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    if(page != 1){
                                        UIUtils.showToast("没有更多了...");
                                    }
                                }else {
                                    UIUtils.showToast(plZixunListBean.getMessage());
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


    private void initView(View view) {
        mKeyboardChangeListener = new KeyboardChangeListener(getActivity());
        mKeyboardChangeListener.setKeyBoardListener(this);

        expandableList = (CusListView) view.findViewById(R.id.cusListView1);
        adapter = new LeavingAdapter(getActivity(), zixunListBean, replyToCommentListener, newsId,type);
        expandableList.setDivider(null);
        expandableList.setGroupIndicator(null);
        expandableList.setAdapter(adapter);
        initLouZhuInfo();
        adapter.setOnPinlun1Clicklistener(new LeavingAdapter.OnZanClicklistener() {
            @Override
            public void onPinlun1Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final Pinlin3Bean.DataBean.CommentListBean itemNoteComment = zixunListBean.get(groupPosition).getCommentList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    huifuState = 1;
                    bean = new Pinlin3Bean.DataBean.CommentListBean();
                    bean.setReferUserNickName(itemNoteComment.getNickName());
                    bean.setReferUserId(itemNoteComment.getUserId());
                    bean.setId(itemNoteComment.getId());
                    bean.setUserId(MakerApplication.instance.getUid());
                    bean.setNickName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getUserId()+"", "2");
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getNickName()) + ":");

                }
            }
        });

        adapter.setOnPinlun2Clicklistener(new LeavingAdapter.OnZanClicklistener2() {
            @Override
            public void onPinlun2Clicklistener(int groupPosition, int childPosition) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                final Pinlin3Bean.DataBean.CommentListBean itemNoteComment = zixunListBean.get(groupPosition).getCommentList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    huifuState = 1;
                    bean = new Pinlin3Bean.DataBean.CommentListBean();
                    bean.setReferUserNickName(itemNoteComment.getReferUserNickName());
                    bean.setReferUserId(itemNoteComment.getReferUserId());
                    bean.setPid(itemNoteComment.getPid());
                    bean.setUserId(MakerApplication.instance.getUid());
                    bean.setNickName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getPid()+"", itemNoteComment.getReferUserId()+"", "2");
                    chat_et_create_context.setHint("回复" + Photos.stringPhoto(itemNoteComment.getReferUserNickName()) + ":");
                }
            }
        });
        showDialog = new ShowDialog(getActivity());
        // 评论对话框
        frame_msg_ll = (RelativeLayout) view.findViewById(R.id.frame_msg_ll);
        //评论发送按钮
        chat_btn_create_card = (TextView) view.findViewById(R.id.chat_btn_create_card);
        chat_btn_create_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                String content = chat_et_create_context.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(getActivity(), "请输入内容~~~~~",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                chat_et_create_context.setText("");
                chat_et_create_context.setHint("我也说两句...");
                if (0 == huifuState) {//盖楼
                    if (info == null) {
                        return;
                    }
                    AppUtils.setEvent(getActivity(),"InfoPublish","资讯-跟帖");
                    sendCommentNoteZ(zhuId, toUid, content, info);
                } else {//回复现有的帖子
                    if (bean == null) {
                        return;
                    }
                    AppUtils.setEvent(getActivity(),"InfoReply","资讯-回复");

                    sendCommentNote(zhuId, toUid, content, bean);

                }

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
            }
        });

        // 评论输入框
        chat_et_create_context = (EditText) view.findViewById(R.id.chat_et_create_context);
        chat_et_create_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chat_et_create_context.setFocusable(true);
                chat_et_create_context.setFocusableInTouchMode(true);
                chat_et_create_context.requestFocus();
                chat_et_create_context.requestFocusFromTouch();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(chat_et_create_context, 0);

            }
        });
        //滑动隐藏布局
        expandableList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                chat_et_create_context.setHint("我也说两句...");

                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = getActivity().getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
//                frame_msg_ll.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
        expandableList.setOnChildClickListener(new expandableListListener());

        expandableList.setonRefreshListener(new CusListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新操作
                expandableList.onRefreshComplete();
                expandableList.onLoadMoreComplete();
                page = 1;
                isRefresh = true;
                initData(page);

            }
        });
        expandableList.setonLoadMoreListener(new CusListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                expandableList.onRefreshComplete();
                expandableList.onLoadMoreComplete();
                page++;
                isRefresh = false;
                initData(page);
            }
        });
    }

    private void initLouZhuInfo() {
        info = new Pinlin3Bean.DataBean();
        info.setHeadImg(MakerApplication.instance().getHeadImg());
        info.setUserId(MakerApplication.instance().getUserid());
        info.setNickName(MakerApplication.instance().getNickName());
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
                itemNotes = (Pinlin3Bean.DataBean) v.getTag();
            } else {
                itemNotes = (Pinlin3Bean.DataBean) v.findViewById(R.id.btn_comment_reply).getTag();
            }
            if (itemNotes == null) {
                return;
            }
            huifuState = 1;
            bean = new Pinlin3Bean.DataBean.CommentListBean();
            bean.setNickName(MakerApplication.instance().getNickName());
            bean.setUserId(MakerApplication.instance().getUserid());
            bean.setReferUserNickName("");
            bean.setReferUserId("");
            bean.setPid(itemNotes.getId());
            showKeyBoard(itemNotes.getId()+"", "", "1");
        }
    };

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

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {

    }

    /**
     * 为ExpandableListView编写监听器
     * @author Allen
     */
    class expandableListListener implements OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

            return false;
        }

    }

    /**
     * 发表评论方法  即时刷新一条数据
     *
     * @param zhu 帖子id
     * @param to 给谁评论
     * @param msg   发送信息
     */
    private void sendCommentNote(final String zhu, final String to, final String msg, final Pinlin3Bean.DataBean.CommentListBean bean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        Log.e("TAG", "sendCommentNote: "+newsId+"sendCommentNote: "+zhu+"sendCommentNote: "+to+"sendCommentNote: "+msg+"sendCommentNote: "+MakerApplication.instance().getUserid());

        HashMap<String,String> map=new HashMap<>();
        map.put("targetId",newsId);
        map.put("userId",MakerApplication.instance().getUserid());
        if (!StringUtils.isBlank(to)){
            map.put("referUserId",to);
        }
        map.put("pid",zhu);
        map.put("content",msg);
        map.put("type",type);
        map.put("token",MakerApplication.instance.getToken());
        OkHttpUtils.post()
                .url(URLS.COMMENTREPLY)
                .params(map)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "评论失败",
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
                                    huifuState=0;
                                    bean.setPid(zhuId);
                                    bean.setContent(msg);
                                    for (int i = 0; i <zixunListBean.size() ; i++) {
                                        if (zixunListBean.get(i).getId().equals(bean.getPid())) {
                                            zixunListBean.get(i).getCommentList().add(0,bean);
                                            zixunListBean.get(i).setFavourNum(zixunListBean.get(i).getFavourNum()+1);
                                            break;
                                        }
                                    }
                                    notiAdapter();
                                    Toast.makeText(getActivity(), homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));

                                }else {
                                    Toast.makeText(getActivity(), homeOneBean.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                });



    }

    private void sendCommentNoteZ(final String zhuId, String toUid, final String msg, final Pinlin3Bean.DataBean replyListBean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        OkHttpUtils.post()
                .url(URLS.COMMENT)
                .addParams("targetId",newsId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("content",msg)
                .addParams("type",type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getActivity(), "评论失败",
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
                                    initData(page);
                                    notiAdapter();
                                }else if (homeOneBean.getCode().equals("2002")||homeOneBean.getCode().equals("2003")){
                                    UIUtils.showToast(homeOneBean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


    }


}
