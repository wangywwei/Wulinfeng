package com.hxwl.wulinfeng.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.BaseZuiXinAdapter;
import com.hxwl.wulinfeng.adapter.ImpZuiXinAdapter;
import com.hxwl.wulinfeng.adapter.WuLinZuiXinAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.AllHomeHunHeBean;
import com.hxwl.wulinfeng.bean.HomeBannerImageBean;
import com.hxwl.wulinfeng.bean.HomeHunHeBean;
import com.hxwl.wulinfeng.bean.HomeTuijianZhiboBean;
import com.hxwl.wulinfeng.bean.HomeZhuanTiBean;
import com.hxwl.wulinfeng.bean.HomeZuixinHuifangBean;
import com.hxwl.wulinfeng.bean.MainPageTuiJianBean;
import com.hxwl.wulinfeng.bean.WuLinZuiXinBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.LetvPlayUtils;
import com.hxwl.wulinfeng.util.LocalUrlParseUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.SpaceItemDecoration;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.lecloud.skin.videoview.vod.UIVodVideoView;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Allen on 2017/5/26.
 * 最新栏目fragment
 */

public class WuLinZuiXinFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.ll_pb)
    RelativeLayout ll_pb;
    @Bind(R.id.tv_send)
    TextView tvSend;

    private Activity activity;
    private View frag_wulinzuixin;
    private CommonSwipeRefreshLayout common_layout;
    private ListView lv_zuixin;
    //数据
    private List<WuLinZuiXinBean> zuixinListData = new ArrayList<>();

    public boolean isRefresh = true;
    private String lastNewsId = "0";
    private int page = 1;
    private WuLinZuiXinAdapter wulinZuiXinAdapter;

    //回复主贴还是跟帖的状态
    private int huifustate = 0;
    private String typeGentie = "1"; // 1跟帖 2回复
    private String Gentieid = ""; // 跟帖id：回复主贴时默认为空，回复跟帖需要传跟帖的id
    private String huifurenid = ""; // 回复人的id：回复主贴时默认为空，回复跟帖需要传回复人的id
    private static final int ONE_COMMENT_CODE = -1;
    //获取屏幕宽度
    private int m_ScreenWidth;
    private String contente;
    private String picture;
    private EditText edt_msg;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 设置窗口透明，可避免播放器SurfaceView初始化时的黑屏现象
        activity.getWindow().setFormat(PixelFormat.TRANSLUCENT);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        frag_wulinzuixin = inflater.inflate(R.layout.wulin_zuixin, container, false);
        ButterKnife.bind(activity);
        initView();
        initData(page);

        return frag_wulinzuixin;
    }

    private void initView() {
        edt_msg = (EditText) frag_wulinzuixin.findViewById(R.id.edt_msg);
        common_layout = (CommonSwipeRefreshLayout) frag_wulinzuixin.findViewById(R.id.common_layout);
        lv_zuixin = (ListView) frag_wulinzuixin.findViewById(R.id.lv_zuixin);
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
        wulinZuiXinAdapter = new WuLinZuiXinAdapter(zuixinListData, getActivity(), replyToCommentListener, replyToReplyListener);
        lv_zuixin.setAdapter(wulinZuiXinAdapter);
        wulinZuiXinAdapter.setOnZanClicklistener(new WuLinZuiXinAdapter.OnZanClicklistener() {
            @Override
            public void onZanClicklistener(int position, View view) {
//                if (userId != null && userId.equals("-1")) {
//
//                } else {
//                    if (!zuixinListData.contains(position + "")) {
//                        genTieZan(commentItems.get(position).getId(), position);
//                    } else {
//                        UIUtils.showToast("您已经赞过！");
//                    }
//                }
            }
        });
        edt_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (huifustate != 1) {
                    onCreateDialogs(ONE_COMMENT_CODE, ONE_COMMENT_CODE);
                }
            }
        });
    }

    private void initData(int page) {
        if (!SystemHelper.isConnected(activity)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    wulinZuiXinAdapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {
                    common_layout.setRefreshing(false);
                    UIUtils.showToast("服务器异常");
                }
            }
        }, "method=" + NetUrlUtils.wulin_list + page + MakerApplication.instance().getUid());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page + "");
        map.put("num", "30");
        map.put("uid", MakerApplication.instance().getUid());
        map.put("loginKey", MakerApplication.instance().getLoginKey());
        map.put("method", NetUrlUtils.wulin_list);
        tasker.execute(map);

//        try {
//            Map<String, Object> map = new HashMap<>();
//            map.put("page", page + "");
//            map.put("num", "30");
//            map.put("uid", MakerApplication.instance().getUid());
//            map.put("loginKey", MakerApplication.instance().getLoginKey());
//            //        map.put("keyword" ,num );
//            //        map.put("saishiId" ,page); // 赛事编号 做过滤用的
//            AppClient.okhttp_post_Asyn(NetUrlUtils.wulin_list, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    getActivity().runOnUiThread(new Runnable() {
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
////                            zuixinListData.clear();
////                            zuixinListData.addAll(JSON.parseArray(result.getData(), WuLinZuiXinBean.class));
//                        } else {
////                            zuixinListData.addAll(JSON.parseArray(result.getData(), WuLinZuiXinBean.class));
//                        }
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                common_layout.setRefreshing(false);
//                                common_layout.setLoadMore(false);
//                                wulinZuiXinAdapter.notifyDataSetChanged();
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

    /**
     * 回复评论的监听（回复楼主）
     */
    private View.OnClickListener replyToCommentListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            huifustate = 1;
            //弹起软件盘
            edt_msg.setFocusableInTouchMode(true);
            edt_msg.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) edt_msg.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(edt_msg, 0);

            int position = (Integer) v.getTag();
            onCreateDialogs(ONE_COMMENT_CODE, position);
        }
    };

    /**
     * 互相回复的监听（楼中楼）
     */
    private View.OnClickListener replyToReplyListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            huifustate = 1;
            //弹起软件盘
            edt_msg.setFocusableInTouchMode(true);
            edt_msg.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) edt_msg.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(edt_msg, 0);

            int parentPosition = (Integer) v.getTag(R.id.tag_first);
            int childPosition = (Integer) v.getTag(R.id.tag_second);
            onCreateDialogs(parentPosition, childPosition);
        }


    };

    @Override
    @OnClick({R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send://发送消息
                sendCoument();
                edt_msg.getText().clear();
                //发送完消息隐藏键盘
                //发送完消息隐藏键盘
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edt_msg.getWindowToken(), 0);
                break;
            default:

                break;
        }
    }

    /**
     * 发送评论
     */
    private void sendCoument() {
//        if (userId.equals("-1") || loginKey.equals("-1")) {//评论 去登陆
//            Intent intent = new Intent(this, LoginHomePageActivity.class);
//            intent.putExtra("LoginFlag", "shequ_dashang");
//            startActivity(intent);
//        } else {
//            Log.d("top", "zhuId=" + id + " uid=" + userId + " loginKey=" + loginKey + " type=" + typeGentie + " genId" + Gentieid + " toUid=" + huifurenid);
//            if (!TextUtils.isEmpty(edt_msg.getText().toString())) {
//                MakerApplication.okHttpUtilsPost.url(NetUrlUtils.QUANZI_HUIFU_Url)
//                        .addParams("zhuId", id)
//                        .addParams("uid", userId)
//                        .addParams("loginKey", loginKey)
//                        .addParams("type", typeGentie)
//                        .addParams("genId", Gentieid)
//                        .addParams("toUid", huifurenid)
//                        .addParams("msg", edt_msg.getText().toString())
//                        .build().execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        UIUtils.showToast("服务器异常");
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.d("100", response);
//                        SendCommentBean sendCommentBean = new Gson().fromJson(response, SendCommentBean.class);
//                        if (sendCommentBean != null && sendCommentBean.getStatus() != null && sendCommentBean.getStatus().equals("ok")) {
//                            edt_msg.setText("");
//                            //评论成功状态恢复默认
//                            huifustate = 0;
//                            gedt_msgDetails();//重新获取评论内容
//                        } else {
//                            UIUtils.showToast(sendCommentBean.getMsg());
//                        }
//                    }
//                });
//            } else {
//                UIUtils.showToast("请输入评论内容");
//            }
//        }
    }

    /**
     * 弹出评论的对话框
     *
     * @param parentPositon 父节点的position
     * @param childPostion  子节点的position
     * @return
     */
    private void onCreateDialogs(final int parentPositon, final int childPostion) {
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (childPostion) {
                    case ONE_COMMENT_CODE:
                        if (TextUtils.isEmpty(edt_msg.getText().toString())) {
                            UIUtils.showToast("请输入评论内容");
                        } else {
                            typeGentie = "1";
                            Gentieid = "";
                            huifurenid = "";
                            sendCoument();
                            edt_msg.getText().clear();
                            //发送完消息隐藏键盘
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edt_msg.getWindowToken(), 0);
                        }

                        break;
                    default:
                        if (TextUtils.isEmpty(edt_msg.getText().toString())) {
                            UIUtils.showToast("请输入评论内容");
                        } else {
                            if (parentPositon != -1) {//楼中楼
                                Gentieid = zuixinListData.get(parentPositon).getId();
//                                huifurenid = zuixinListData.get(parentPositon).getHuifu().get(childPostion).getFrom_uid();
                            } else {
                                Gentieid = zuixinListData.get(childPostion).getId();
                                huifurenid = zuixinListData.get(childPostion).getUid();
                            }
                            typeGentie = "2";
                            sendCoument();
                            edt_msg.getText().clear();
                            //发送完消息隐藏键盘
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(edt_msg.getWindowToken(), 0);

                        }
                        break;
                }
            }
        });

    }

}
