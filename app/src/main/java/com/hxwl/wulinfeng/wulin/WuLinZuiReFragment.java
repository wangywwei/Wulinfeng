//package com.hxwl.wulinfeng.wulin;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.PixelFormat;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.AbsListView;
//import android.widget.EditText;
//import android.widget.ExpandableListView;
//import android.widget.ExpandableListView.OnChildClickListener;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.alibaba.fastjson.JSON;
//import com.baidu.mobstat.StatService;
//import com.hxwl.common.utils.EmptyViewHelper;
//import com.hxwl.common.utils.ShowDialog;
//import com.hxwl.common.utils.StringUtils;
//import com.hxwl.wulinfeng.MakerApplication;
//import com.hxwl.wulinfeng.R;
//import com.hxwl.wulinfeng.activity.LoginforCodeActivity;
//import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
//import com.hxwl.wulinfeng.base.BaseFragment;
//import com.hxwl.wulinfeng.bean.HuiFuBean;
//import com.hxwl.wulinfeng.bean.WuLinZuiXinBean;
//import com.hxwl.wulinfeng.net.AppClient;
//import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
//import com.hxwl.wulinfeng.net.ICallback;
//import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
//import com.hxwl.wulinfeng.net.ResultPacket;
//import com.hxwl.wulinfeng.util.AppUtils;
//import com.hxwl.wulinfeng.util.NetUrlUtils;
//import com.hxwl.wulinfeng.util.SystemHelper;
//import com.hxwl.wulinfeng.util.ToastUtils;
//import com.hxwl.wulinfeng.util.UIUtils;
//import com.tendcloud.tenddata.TCAgent;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
//import static com.hxwl.wulinfeng.R.layout.frag_wulin;
//
///**
// * 武林页面 -- 最热
// */
//@SuppressLint("InlinedApi")
//public class WuLinZuiReFragment extends BaseFragment {
//    @Bind(R.id.ll_pb)
//    RelativeLayout ll_pb;
//    @Bind(R.id.tv_send)
//    TextView tvSend;
//    private Activity activity;
//    private View frag_wulinzuixin;
//    //数据
//    private List<WuLinZuiXinBean> listData = new ArrayList<>();
//    //	=================心得数据=================
//    private CusListView expandableList;//expand listview
//    public boolean isRefresh = true;
//    private int page = 1;
//    private String lastNewsId = "0";
//    private ExpandListViewAdapter expandAdapter;
//
//    // 评论
//    private RelativeLayout frame_msg_ll;
//    private TextView chat_btn_create_card;//评论确认按钮
//    private EditText chat_et_create_context;//评论输入框
//    //回复传递参数
//    private String zhuId = "";//心得id
//    private String toUid = "";//回复谁
//    private String type = "1";
//    private HuiFuBean bean = null;
//    private EmptyViewHelper emptyViewHelper;
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = activity;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // 设置窗口透明，可避免播放器SurfaceView初始化时的黑屏现象
//        activity.getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
//                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
//        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//
//        if (frag_wulinzuixin == null)
//        {
//            frag_wulinzuixin = inflater.inflate(R.layout.activity_item_detail_expandlistview, container, false);
//            ButterKnife.bind(activity);
//            setView();
//            loadItemNotePage(page, true);
//        }else{
//            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
//            ViewGroup parent = (ViewGroup) frag_wulinzuixin.getParent();
//            if (parent != null)
//            {
//                parent.removeView(frag_wulinzuixin);
//            }
//        }
//        return frag_wulinzuixin;
//    }
//
//    public void restRefresh(){
//        if(frag_wulinzuixin != null && expandableList != null){
//            expandableList.onRefreshComplete();
//            expandableList.onLoadMoreComplete();
//        }
//    }
//
//    private void loadItemNotePage(final int page, boolean b) {
//        if (!SystemHelper.isConnected(activity)) {
//            UIUtils.showToast("请检查网络");
//            expandableList.onRefreshComplete();
//            expandableList.onLoadMoreComplete();
//            emptyViewHelper.setType(1);
//            return;
//        }
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                activity, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    if (isRefresh) {
//                        listData.clear();
//                        listData.addAll(JSON.parseArray(result.getData(), WuLinZuiXinBean.class));
//                    } else {
//                        listData.addAll(JSON.parseArray(result.getData(), WuLinZuiXinBean.class));
//                    }
//                    expandAdapter.notifyDataSetChanged();
//                    expandableList.onRefreshComplete();
//                    expandableList.onLoadMoreComplete();
//                    //默认展开全部
//                    if (expandAdapter != null && expandAdapter.getGroupCount() > 0) {
//                        for (int i = 0; i < expandAdapter.getGroupCount(); i++) {
//                            expandableList.expandGroup(i);
//                        }
//                    }
//                } else if (result != null && result.getStatus().equals("empty")) {
//                    expandableList.onRefreshComplete();
//                    expandableList.onLoadMoreComplete();
//                    UIUtils.showToast("没有更多了");
//                } else {
//                    expandableList.onRefreshComplete();
//                    expandableList.onLoadMoreComplete();
//                    emptyViewHelper.setType(1);
//                }
//            }
//        }, "method=" + NetUrlUtils.wulin_list + page + MakerApplication.instance().getUid() + "sort=2");
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("page", page + "");
//        map.put("num", "30");
//        map.put("sort ", "2");
//        map.put("uid", MakerApplication.instance().getUid());
//        map.put("loginKey", MakerApplication.instance().getLoginKey());
//        map.put("method", NetUrlUtils.wulin_list);
//        tasker.execute(map);
//
////		try {
////			Map<String, Object> map = new HashMap<>();
////			map.put("page", page + "");
////			map.put("num","30");
////			map.put("sort ","2");
////			map.put("uid", MakerApplication.instance().getUid());
////			map.put("loginKey", MakerApplication.instance().getLoginKey());
////			AppClient.okhttp_post_Asyn(NetUrlUtils.wulin_list, map, new ICallback() {
////				@Override
////				public void error(IOException e) {
////					getActivity().runOnUiThread(new Runnable() {
////						@Override
////						public void run() {
////							expandableList.onRefreshComplete();
////							UIUtils.showToast("服务器异常");
////						}
////					});
////				}
////				@Override
////				public void success(ResultPacket result) {
////					if (result.getStatus() != null && result.getStatus().equals("ok")) {
////						if (isRefresh) {
////							listData.clear();
////							listData.addAll(JSON.parseArray(result.getData() ,WuLinZuiXinBean.class));
////						}else{
////							listData.addAll(JSON.parseArray(result.getData() ,WuLinZuiXinBean.class));
////						}
////						getActivity().runOnUiThread(new Runnable() {
////							@Override
////							public void run() {
////								expandAdapter.notifyDataSetChanged();
////								expandableList.onRefreshComplete();
////								//默认展开全部
////								if (expandAdapter != null && expandAdapter.getGroupCount() > 0) {
////									for(int i = 0; i < expandAdapter.getGroupCount(); i++){
////										expandableList.expandGroup(i);
////									}
////								}
////							}
////						});
////					} else {
////
////					}
////				}
////			});
////		} catch (Throwable throwable) {
////			throwable.printStackTrace();
////		}
//    }
//
//    private ShowDialog showDialog;
//
//    private void setView() {
//        showDialog = new ShowDialog(getActivity());
//        // 评论对话框
//        frame_msg_ll = (RelativeLayout) frag_wulinzuixin.findViewById(R.id.frame_msg_ll);
//        //评论发送按钮
//        chat_btn_create_card = (TextView) frag_wulinzuixin.findViewById(R.id.chat_btn_create_card);
//        chat_btn_create_card.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                String uid = MakerApplication.instance().getUid();
//                String loginKey = MakerApplication.instance().getLoginKey();
//                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
//                    startActivity(new Intent(getActivity(),LoginforCodeActivity.class));
//                    return ;
//                }
//                String content = chat_et_create_context.getText().toString();
//                if (TextUtils.isEmpty(content)) {
//                    Toast.makeText(getActivity(), "请输入内容~~~~~",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                chat_et_create_context.setText("");
//                chat_et_create_context.setHint("我也说两句...");
//                if (bean == null) {
//                    return;
//                }
//                sendCommentNote(zhuId, toUid, content, bean);
//
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
//                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
//            }
//        });
//        // 评论输入框
//        chat_et_create_context = (EditText) frag_wulinzuixin.findViewById(R.id.chat_et_create_context);
//        chat_et_create_context.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                chat_et_create_context.setFocusable(true);
//                chat_et_create_context.setFocusableInTouchMode(true);
//                chat_et_create_context.requestFocus();
//                chat_et_create_context.requestFocusFromTouch();
//
//                String uid = MakerApplication.instance().getUid();
//                String loginKey = MakerApplication.instance().getLoginKey();
//                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
//                    startActivity(new Intent(getActivity(),LoginforCodeActivity.class));
//                    return ;
//                }
//            }
//        });
//        //监听edittext文本改变
//        chat_et_create_context.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String text = s.toString();
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//        expandableList = (CusListView) frag_wulinzuixin.findViewById(R.id.cusListView1);
//        expandableList.setDivider(null);
//        expandableList.setGroupIndicator(null);
//        expandAdapter = new ExpandListViewAdapter(getActivity(), listData
//                , replyToCommentListener);
//        expandableList.setAdapter(expandAdapter);
//
//        emptyViewHelper = new EmptyViewHelper(expandableList,(FrameLayout)frag_wulinzuixin.findViewById(R.id.parent));
//        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
//            @Override
//            public void OnClickButton(View v) {
//                loadItemNotePage(page,true);
//            }
//        });
//        //滑动隐藏布局
//        expandableList.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
//                    View currentFocus = getActivity().getCurrentFocus();
//                    if (currentFocus != null) {
//                        currentFocus.clearFocus();
//                    }
//                }
//                chat_et_create_context.setHint("我也说两句...");
//                frame_msg_ll.setVisibility(View.GONE);
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(frame_msg_ll, InputMethodManager.SHOW_FORCED);
//                imm.hideSoftInputFromWindow(frame_msg_ll.getWindowToken(), 0); //强制隐藏键盘
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem,
//                                 int visibleItemCount, int totalItemCount) {
//                expandableList.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
//            }
//        });
//        //设置子条目点击事件
//        expandableList.setOnChildClickListener(new expandableListListener());
//        expandableList.setonRefreshListener(new CusListView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // 刷新操作
//                page = 1;
//                isRefresh = true;
//                loadItemNotePage(page, true);
//            }
//        });
//        expandableList.setonLoadMoreListener(new CusListView.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                page++;
//                isRefresh = false;
//                loadItemNotePage(page, true);
//            }
//        });
//
//    }
//
//    /**
//     * /**
//     * 回复评论的监听（回复楼主） 点击左边的气泡的时候触发（评论）
//     */
//    private OnClickListener replyToCommentListener = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            String uid = MakerApplication.instance().getUid();
//            String loginKey = MakerApplication.instance().getLoginKey();
//            if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
//                startActivity(new Intent(getActivity(),LoginforCodeActivity.class));
//                return ;
//            }
//            chat_et_create_context.setHint("我也说两句...");
//            WuLinZuiXinBean itemNotes = null;
//            if (v instanceof ImageView) {
//                itemNotes = (WuLinZuiXinBean) v.getTag();
//            } else {
//                itemNotes = (WuLinZuiXinBean) v.findViewById(R.id.btn_comment_reply).getTag();
//            }
//            if (itemNotes == null) {
//                return;
//            }
//            StatService.onEvent(getActivity(),"WulinReply","武林回复",1);
//            TCAgent.onEvent(getActivity(),"WulinReply","武林回复");
//            bean = new HuiFuBean();
//            bean.setFrom_head_url(MakerApplication.instance().getHeadPic());
//            bean.setFrom_nickname(MakerApplication.instance().getUsername());
//            bean.setFrom_uid(MakerApplication.instance().getUid());
//            bean.setZhu_id(itemNotes.getId());
//            bean.setTo_uid("");
//            showKeyBoard(itemNotes.getId(), "", "1");
//        }
//    };
//
//    /**
//     * 为ExpandableListView编写监听器
//     *
//     * @author Allen
//     */
//    class expandableListListener implements OnChildClickListener {
//        @Override
//        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//            String uid = MakerApplication.instance().getUid();
//            String loginKey = MakerApplication.instance().getLoginKey();
//            if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
//                startActivity(new Intent(getActivity(),LoginforCodeActivity.class));
//                return false;
//            }
//            final HuiFuBean itemNoteComment = listData.get(groupPosition).getHuifu().get(childPosition);
//            if (itemNoteComment == null) {
//            } else {
//                chat_et_create_context.setHint("回复"+itemNoteComment.getFrom_nickname()+":");
//                bean = new HuiFuBean();
//                bean.setFrom_head_url(MakerApplication.instance().getHeadPic());
//                bean.setFrom_nickname(MakerApplication.instance().getUsername());
//                bean.setTo_nickname(itemNoteComment.getFrom_nickname());
//                bean.setFrom_uid(MakerApplication.instance().getUid());
//                bean.setZhu_id(itemNoteComment.getZhu_id());
//                bean.setTo_uid(itemNoteComment.getFrom_uid());
//                showKeyBoard(itemNoteComment.getZhu_id(), itemNoteComment.getFrom_uid(), "2");
//            }
//            return false;
//        }
//    }
//
//    /**
//     * @param zhuId 帖子的id
//     * @param toUid 给谁发的 id
//     * @param type  1跟帖 2回复
//     */
//    public void showKeyBoard(String zhuId, String toUid, String type) {
//        frame_msg_ll.setVisibility(View.VISIBLE);
//        chat_et_create_context.setFocusable(true);
//        chat_et_create_context.setFocusableInTouchMode(true);
//        chat_et_create_context.requestFocus();
//        InputMethodManager inputManager =
//                (InputMethodManager) chat_et_create_context.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.showSoftInput(chat_et_create_context, 0);
//
//        this.zhuId = zhuId;
//        this.toUid = toUid;
//        this.type = type;
//    }
//
//    /**
//     * 发表评论方法  即时刷新一条数据
//     *
//     * @param zhuId 帖子id
//     * @param toUid 给谁评论
//     * @param msg   发送信息
//     */
//    private void sendCommentNote(final String zhuId, String toUid, final String msg, final HuiFuBean bean) {
//        showDialog.showProgressDialog("正在评论", "请稍后。", true);
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                activity, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    String id = result.getInsertid();
//                    if (StringUtils.isEmpty(id)) {
//                        return;
//                    }
//                    bean.setId(id);
//                    bean.setMsg(msg);
//                    if ("1".equals(type)) {//跟帖
//                        for (WuLinZuiXinBean itemNotesdata : listData) {
//                            if (itemNotesdata.getId().equals(bean.getZhu_id())) {
//                                itemNotesdata.getHuifu().add(bean);
//                            }
//                        }
//                    } else if ("2".equals(type)) {//评论
//                        for (WuLinZuiXinBean itemNotesdata : listData) {
//                            if (itemNotesdata.getId().equals(bean.getZhu_id())) {
//                                itemNotesdata.getHuifu().add(bean);
//                            }
//                        }
//                    } else {
//
//                    }
//                    Toast.makeText(getActivity(), "评论成功",
//                            Toast.LENGTH_SHORT).show();
//                    frame_msg_ll.setVisibility(View.GONE);
//                    showDialog.dismissProgressDialog();
//                    expandAdapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(getActivity(), "评论失败",
//                            Toast.LENGTH_SHORT).show();
//                    frame_msg_ll.setVisibility(View.GONE);
//                    showDialog.dismissProgressDialog();
//                }
//            }
//        });
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("uid", MakerApplication.instance().getUid());
//        map.put("loginKey", MakerApplication.instance().getLoginKey());
//        map.put("type", type);
//        map.put("zhuId", zhuId);
//        map.put("toUid", toUid);
//        map.put("msg", msg);
//        map.put("method", NetUrlUtils.wulin_huifu);
//        tasker.execute(map);
//
////        Map<String, Object> map = new HashMap<>();
////        map.put("uid", MakerApplication.instance().getUid());
////        map.put("loginKey", MakerApplication.instance().getLoginKey());
////        map.put("type", type);
////        map.put("zhuId", zhuId);
////        map.put("toUid", toUid);
////        map.put("msg", msg);
////        try {
////            AppClient.okhttp_post_Asyn(NetUrlUtils.wulin_huifu, map, new ICallback() {
////                @Override
////                public void error(IOException e) {
////                    getActivity().runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            Toast.makeText(getActivity(), "评论失败",
////                                    Toast.LENGTH_SHORT).show();
////                            frame_msg_ll.setVisibility(View.GONE);
////                            showDialog.dismissProgressDialog();
////                        }
////                    });
////                }
////
////                @Override
////                public void success(ResultPacket result) {
////                    String id = result.getInsertid();
////                    if (StringUtils.isEmpty(id)) {
////                        return;
////                    }
////                    bean.setId(id);
////                    bean.setMsg(msg);
////                    if ("1".equals(type)) {//跟帖
////                        for (WuLinZuiXinBean itemNotesdata : listData) {
////                            if (itemNotesdata.getId().equals(bean.getZhu_id())) {
////                                itemNotesdata.getHuifu().add(bean);
////                            }
////                        }
////                    } else if ("2".equals(type)) {//评论
////                        for (WuLinZuiXinBean itemNotesdata : listData) {
////                            if (itemNotesdata.getId().equals(bean.getZhu_id())) {
////                                itemNotesdata.getHuifu().add(bean);
////                            }
////                        }
////                    } else {
////
////                    }
////                    getActivity().runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            Toast.makeText(getActivity(), "评论成功",
////                                    Toast.LENGTH_SHORT).show();
////                            frame_msg_ll.setVisibility(View.GONE);
////                            showDialog.dismissProgressDialog();
////                            expandAdapter.notifyDataSetChanged();
////                        }
////                    });
////
////                }
////            });
////        } catch (Throwable throwable) {
////            throwable.printStackTrace();
////        }
//    }
//
////	/**
////	 * 界面重现 加载数据
////	 */
////	@Override
////	protected void onResume() {
////		// TODO Auto-generated method stub
////		super.onResume();
////		loadItemNotePage(itemnotepage);
////	}
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        StatService.onPageStart(activity, "武林最热");
//        TCAgent.onPageStart(activity, "武林最热");
//        page = 1 ;
//        isRefresh = true;
//        loadItemNotePage(page ,true);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        StatService.onPageEnd(activity,"武林最热");
//        TCAgent.onPageEnd(activity,"武林最热");
//    }
//}
