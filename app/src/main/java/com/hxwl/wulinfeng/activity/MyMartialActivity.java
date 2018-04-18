package com.hxwl.wulinfeng.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudPlayerManager;
import com.hxwl.common.tencentplay.tencentCloud.TXCloudViewViewImp;
import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.DongtaiBean;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.wulin.wulin.WulinTopic;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.KeyboardChangeListener;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.wulin.CusListView;
import com.hxwl.wulinfeng.wulin.ExpandListViewAdapter;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.hxwl.common.tencentplay.tencentCloud.TXCloudViewViewA.CURRENT_STATE_PLAYING;

/**
 * 我的武林界面
 */
@SuppressLint("InlinedApi")
public class MyMartialActivity extends BaseActivity implements ExpandListViewAdapter.VideoListener, KeyboardChangeListener.KeyBoardListener {
    //数据
    private List<DongtaiBean.DataBean> listData = new ArrayList<>();
    private com.hxwl.common.utils.ShowDialog showDialog;
    //	=================心得数据=================
    private CusListView expandableList;//expand listview
    public boolean isRefresh = true;
    private int page = 1;
    private ExpandListViewAdapter expandAdapter;

    //回复传递参数
    private String zhuId = "";//心得id
    private String toUid = "";//回复谁
    private String type = "1";
    private DongtaiBean.DataBean.CommentListBean bean = null;
    private EmptyViewHelper emptyViewHelper;
    private EditText et_keywored;
    private TextView tv_send;
    private TextView tv_textcount;
    private LinearLayout ll_sendmsg;
    private View keyBordView;
    private KeyboardChangeListener mKeyboardChangeListener;
    private WulinTopic wulinTopic;
    private ImageView user_icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail_expandlistview);
        AppUtils.setTitle(MyMartialActivity.this);
        setView();
    }


    private void loadItemNotePage(final int page, boolean b) {
        if (!SystemHelper.isConnected(MyMartialActivity.this)) {
            UIUtils.showToast("请检查网络");
            expandableList.onRefreshComplete();
            expandableList.onLoadMoreComplete();
            emptyViewHelper.setType(1);
            return;
        }

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_DYNAMICLIST)
                .addParams("dataType","2")
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId",MakerApplication.instance.getUid())
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
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                DongtaiBean bean = gson.fromJson(response, DongtaiBean.class);
                                if (bean.getCode().equals("1000")){
                                    if (isRefresh) {
                                        listData.clear();
                                        listData.addAll(bean.getData());
                                    } else {
                                        listData.addAll(bean.getData());
                                    }
                                    expandAdapter.notifyDataSetChanged();
                                    expandableList.onRefreshComplete();
                                    expandableList.onLoadMoreComplete();
                                    //默认展开全部
                                    if (expandAdapter != null && expandAdapter.getGroupCount() > 0) {
                                        for (int i = 0; i < expandAdapter.getGroupCount(); i++) {
                                            expandableList.expandGroup(i);
                                        }
                                    }
                                    if (listData.size()<=0){
                                        emptyViewHelper.setType(3);
                                    }
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(MyMartialActivity.this));
                                    MyMartialActivity.this.finish();
                                }else {
                                    UIUtils.showToast(bean.getMessage());
                                    emptyViewHelper.setType(1);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });


    }
    private String model;

    private void setView() {
        model = android.os.Build.MODEL;
        mKeyboardChangeListener = new KeyboardChangeListener(MyMartialActivity.this);
        mKeyboardChangeListener.setKeyBoardListener(this);
        user_icon= (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showDialog = new ShowDialog(MyMartialActivity.this);
        ll_sendmsg = (LinearLayout) findViewById(R.id.ll_sendmsg);
        TextView edt_msg = (TextView) findViewById(R.id.edt_msg);
        expandableList = (CusListView) findViewById(R.id.cusListView1);

        expandableList.setDivider(null);
        expandableList.setGroupIndicator(null);
        expandAdapter = new ExpandListViewAdapter(MyMartialActivity.this, listData
                , replyToCommentListener);
        expandableList.setAdapter(expandAdapter);
        emptyViewHelper = new EmptyViewHelper(expandableList, (FrameLayout)findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                loadItemNotePage(page, true);
            }
        });

        expandAdapter.setOnPinlun1Clicklistener(new ExpandListViewAdapter.OnZanClicklistener() {
            @Override
            public void onPinlun1Clicklistener(int groupPosition, int childPosition) {
                final DongtaiBean.DataBean.CommentListBean itemNoteComment = listData.get(groupPosition).getCommentList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    bean = new DongtaiBean.DataBean.CommentListBean();
                    bean.setReferUserName(itemNoteComment.getUserName());
                    bean.setReferUserId(itemNoteComment.getUserId());
                    bean.setId(itemNoteComment.getId());
                    bean.setUserId(MakerApplication.instance.getUid());
                    bean.setUserName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getMessageId()+"", itemNoteComment.getUserId()+"", "2");
                    et_keywored.setHint("回复" + Photos.stringPhoto(itemNoteComment.getUserName()) + ":");

                }
            }
        });

        expandAdapter.setOnPinlun2Clicklistener(new ExpandListViewAdapter.OnZanClicklistener2() {
            @Override
            public void onPinlun2Clicklistener(int groupPosition, int childPosition) {
                final DongtaiBean.DataBean.CommentListBean itemNoteComment = listData.get(groupPosition).getCommentList().get(childPosition);
                if (itemNoteComment == null) {
                } else {
                    bean = new DongtaiBean.DataBean.CommentListBean();
                    bean.setReferUserName(itemNoteComment.getReferUserName());
                    bean.setReferUserId(itemNoteComment.getReferUserId());
                    bean.setMessageId(itemNoteComment.getMessageId());
                    bean.setUserId(MakerApplication.instance.getUid());
                    bean.setUserName(MakerApplication.instance.getNickName());
                    showKeyBoard(itemNoteComment.getMessageId()+"", itemNoteComment.getReferUserId()+"", "2");
                    et_keywored.setHint("回复" + Photos.stringPhoto(itemNoteComment.getReferUserName()) + ":");
                }
            }
        });

        //滑动隐藏布局
        expandableList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (TXCloudPlayerManager.getCurrentJcvd() != null) {
                    TXCloudViewViewImp videoPlayer = (TXCloudViewViewImp) TXCloudPlayerManager.getCurrentJcvd();
                    if (videoPlayer.currentState == CURRENT_STATE_PLAYING) {
                        //如果正在播放
                        int firstVisibleItemPosition = expandableList.getFirstVisiblePosition() == 0 ? 0 : expandableList.getFirstVisiblePosition() - 1;
                        int lastVisibleItemPosition = expandableList.getLastVisiblePosition() == 0 ? 0 : expandableList.getLastVisiblePosition();
                        int pos = videoPlayer.getPosition();
                        if (firstVisibleItemPosition == 0 ? (firstVisibleItemPosition + 1 > videoPlayer.getPosition()) : (firstVisibleItemPosition > videoPlayer.getPosition() + 2)
                                || lastVisibleItemPosition < videoPlayer.getPosition() + 2) {
                            //如果第一个可见的条目位置大于当前播放videoPlayer的位置
                            //或最后一个可见的条目位置小于当前播放videoPlayer的位置，释放资源
                            TXMediaManager.instance(MyMartialActivity.this).videoDestroy();
                            for (int i = 0; i < listData.size(); i++) {
                                listData.get(i).setSelect(false);
                            }
                            expandAdapter.notifyDataSetChanged();
                        }
                    }
                }

                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
                    View currentFocus = MyMartialActivity.this.getCurrentFocus();
                    if (currentFocus != null) {
                        currentFocus.clearFocus();
                    }
                }
                if(dialog != null){
                    dialog.dismiss();
                }
                InputMethodManager imm = (InputMethodManager) MyMartialActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(ll_sendmsg, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(ll_sendmsg.getWindowToken(), 0); //强制隐藏键盘
                isShow = false ;

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
                page = 1;
                isRefresh = true;
                loadItemNotePage(page, true);


            }
        });
        expandableList.setonLoadMoreListener(new CusListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                loadItemNotePage(page, true);
            }
        });
        expandAdapter.setVideoListener(this);
    }

    private Dialog dialog = null;
    private boolean isShow;

    //点击别的区域的时候,设置输入法选项
    public void showEditDialog(final Context context) {
        isShow = true;
        if (dialog == null) {
            dialog = new Dialog(context, R.style.dialog);
        }
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        keyBordView = inflater.inflate(R.layout.chat_view_edittext, null);
        et_keywored = (EditText) keyBordView.findViewById(R.id.et_keywored);
        tv_send = (TextView) keyBordView.findViewById(R.id.tv_send);
        tv_textcount = (TextView) keyBordView.findViewById(R.id.tv_textcount);

        tv_textcount.setVisibility(View.GONE);
        //设置展示范围
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().
                setAttributes(params);

        dialog.setContentView(keyBordView);

        dialog.show();

        et_keywored.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                                 @Override
                                                 public void onFocusChange(View v, boolean hasFocus) {
                                                     if (hasFocus) {
                                                         et_keywored.post(new Runnable() {
                                                             @Override
                                                             public void run() {
                                                                 InputMethodManager imm = (InputMethodManager) MyMartialActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                                 imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                                                                 //自动弹出
                                                                 try {
                                                                     Thread.sleep(40);
                                                                 } catch (InterruptedException e) {

                                                                 }
                                                             }
                                                         });
                                                     }

                                                 }
                                             }
        );

        //发送消息
        tv_send.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           String content = et_keywored.getText().toString();
                                           if (TextUtils.isEmpty(content)) {
                                               Toast.makeText(MyMartialActivity.this, "请输入内容~~~~~",
                                                       Toast.LENGTH_SHORT).show();
                                               return;
                                           }
                                           et_keywored.setText("");
                                           et_keywored.setHint("我也说两句...");
                                           if (bean == null) {
                                               return;
                                           }
                                           sendCommentNote(zhuId, toUid, content, bean);

                                           InputMethodManager imm = (InputMethodManager) MyMartialActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                           imm.showSoftInput(ll_sendmsg, InputMethodManager.SHOW_FORCED);
                                           imm.hideSoftInputFromWindow(ll_sendmsg.getWindowToken(), 0); //强制隐藏键盘

                                           et_keywored.getText().clear();
                                           if(dialog != null){
                                               dialog.dismiss();
                                           }
                                           ll_sendmsg.setVisibility(View.GONE);
                                           hideKeyBoard();
                                       }
                                   }
        );
        //隐藏dialog的方法
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                       @Override
                                       public void onCancel(final DialogInterface dialog) {
                                           //小米手机
                                           if (model.contains("MI 3")) {
                                               et_keywored.post(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       InputMethodManager imm = (InputMethodManager) MyMartialActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                                       imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);

                                                       try {
                                                       } catch (Exception e) {

                                                       }

                                                   }
                                               });
                                           } else {
                                               //普通手机
                                               InputMethodManager imm = (InputMethodManager) MyMartialActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                                               imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                                           }
                                           ll_sendmsg.setVisibility(View.GONE);
                                           isShow = false;
                                       }
                                   }
        );
        et_keywored.setFocusable(true);
    }

    //隐藏键盘和输入框
    public void hideKeyBoard() {
        isShow = false;
        if (model.contains("MI 3")) {
            //小米手机
            et_keywored.post(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) MyMartialActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });
        } else {
            //普通手机
            InputMethodManager imm = (InputMethodManager) MyMartialActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    /**
     * /**
     * 回复评论的监听（回复楼主） 点击左边的气泡的时候触发（评论）
     */
    private OnClickListener replyToCommentListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                UIUtils.showToast("请您点击我的头像去绑定手机号");
                return;
            }

            DongtaiBean.DataBean itemNotes = null;
            if (v instanceof ImageView) {
                itemNotes = (DongtaiBean.DataBean) v.getTag();
            } else {
                itemNotes = (DongtaiBean.DataBean) v.findViewById(R.id.btn_comment_reply).getTag();
            }
            if (itemNotes == null) {
                return;
            }
            bean = new DongtaiBean.DataBean.CommentListBean();
            bean.setUserName(MakerApplication.instance().getNickName());
            bean.setUserId(MakerApplication.instance().getUserid());
            bean.setMessageId(itemNotes.getMessageId());
            bean.setReferUserName("");
            bean.setReferUserId("");
            showKeyBoard(itemNotes.getMessageId()+"", "", "1");
            et_keywored.setHint("我也说两句...");
        }
    };

    @Override
    public void playVideo(final String url, final RelativeLayout videoContainer, final int position, int oldposition, final String title, String ImgUrl) {
        if (position == -1) {
            return;
        }
        try {
            //初始化所有数据
            for (int i = 0; i < listData.size(); i++) {
                listData.get(i).setSelect(false);
            }
            listData.get(position).setSelect(true);
            expandAdapter.notifyDataSetChanged();

            if (TXMediaManager.instance(MyMartialActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
                TXMediaManager.instance(MyMartialActivity.this).videoDestroy();
            }
            TXMediaManager.instance(MyMartialActivity.this).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
            TXMediaManager.instance(MyMartialActivity.this).TXVodPlayAndViewWL(url, videoContainer, -1, title, position);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openVideoDetails(String id) {

    }

    @Override
    public void loadWebUrl(String url, int position, int oldposition, RelativeLayout videoContainer) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void onKeyboardChange(boolean isShow, int keyboardHeight) {
        if (isShow) {
        } else {
            if (dialog != null) {
                ll_sendmsg.setVisibility(View.GONE);
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        }
    }



    /**
     * 为ExpandableListView编写监听器
     *
     * @author Allen
     */
    class expandableListListener implements OnChildClickListener {
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
        isShow = true ;
        ll_sendmsg.setVisibility(View.VISIBLE);
        showEditDialog(MyMartialActivity.this);


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

    private void sendCommentNote(final String zhuId, String toUid, final String msg, final DongtaiBean.DataBean.CommentListBean bean) {
        showDialog.showProgressDialog("正在评论", "请稍后。", true);
        Map<String,String> map=new HashMap<>();
        map.put("messageId",zhuId);
        map.put("userId", SPUtils.get(MyMartialActivity.this,"id","-1")+"");
        map.put("content",msg);
        map.put("token", MakerApplication.instance.getToken());
        if (!StringUtils.isBlank(toUid)){
            map.put("referUserId",toUid);
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
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                HomeOneBean bean2 = gson.fromJson(response, HomeOneBean.class);
                                if (bean2.getCode().equals("1000")){
                                    Toast.makeText(MyMartialActivity.this, "评论成功",
                                            Toast.LENGTH_SHORT).show();
                                    ll_sendmsg.setVisibility(View.GONE);
                                    bean.setMessageId(zhuId);
                                    bean.setContent(msg);
                                    if ("1".equals(type)) {//跟帖
                                        for (int i = 0; i <listData.size() ; i++) {
                                            if (listData.get(i).getMessageId().equals(bean.getMessageId())) {
                                                listData.get(i).getCommentList().add(0,bean);
                                                listData.get(i).setCommentNum(listData.get(i).getCommentNum()+1);
                                                break;
                                            }
                                        }

                                    } else if ("2".equals(type)) {//评论
                                        for (int i = 0; i <listData.size() ; i++) {
                                            if (listData.get(i).getMessageId().equals(bean.getMessageId())) {
                                                listData.get(i).getCommentList().add(0,bean);
                                                listData.get(i).setCommentNum(listData.get(i).getCommentNum()+1);
                                                break;
                                            }
                                        }

                                    }
                                    showDialog.dismissProgressDialog();
                                    notiAdapter();
                                }else if (bean2.getCode().equals("2002")||bean2.getCode().equals("2003")){
                                    UIUtils.showToast(bean2.getMessage());
                                    startActivity(LoginActivity.getIntent(MyMartialActivity.this));
                                    MyMartialActivity.this.finish();
                                } else {
                                    Toast.makeText(MyMartialActivity.this, bean2.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                    ll_sendmsg.setVisibility(View.GONE);
                                    showDialog.dismissProgressDialog();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });

    }

    private void notiAdapter() {
        expandAdapter.notifyDataSetChanged();
        if (expandAdapter != null && expandAdapter.getGroupCount() > 0) {
            for (int i = 0; i < expandAdapter.getGroupCount(); i++) {
                expandableList.expandGroup(i);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(MyMartialActivity.this, "武林最新");
        TCAgent.onPageStart(MyMartialActivity.this, "武林最新");
        page = 1;
        isRefresh = true;
        loadItemNotePage(page, true);

    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(MyMartialActivity.this, "武林最新");
        TCAgent.onPageEnd(MyMartialActivity.this, "武林最新");
        if (TXMediaManager.instance(MyMartialActivity.this).mLivePlayer != null || TXCloudPlayerManager.getCurrentJcvd() != null) {
            TXMediaManager.instance(MyMartialActivity.this).videoDestroy();
        }
    }
}
