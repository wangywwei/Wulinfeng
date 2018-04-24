package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;

public class Player3_0Activity extends BaseActivity {

    private XRecyclerView player3_0_xrecyclerview;
    private Player3Adapter adapter;
    private ArrayList<QuanshouBean.DataBean> list = new ArrayList<>();
    private QuickIndexBar quickIndexBar;
    private TextView center_view;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, Player3_0Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player3_0);
        StatusBarUtil.setColor(this, this.getResources().getColor(R.color.rgb_BA2B2C));

        initView();
        initData();
    }

    private void initData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pageNumber", "1");
        map.put("pageSize", "50");
        map.put("userId", MakerApplication.instance.getUid());
        map.put("token", MakerApplication.instance.getToken());
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_PLAYERLIST)
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
                                QuanshouBean bean = gson.fromJson(response, QuanshouBean.class);
                                if (bean.getCode().equals("1000")){
                                    list.clear();
//                                    for (int i = 0; i <bean.getData().size() ; i++) {
//                                        Friend friend=new Friend(
//                                                bean.getData().get(i).getPlayerId(),
//                                                bean.getData().get(i).getPlayerImage(),
//                                                bean.getData().get(i).getPlayerName(),
//                                                bean.getData().get(i).getPlayerClub(),
//                                                bean.getData().get(i).getWeight(),
//                                                bean.getData().get(i).getUserIsFollow()
//                                        );
//
//                                    }
                                    list.addAll(bean.getData());
                                    adapter.notifyDataSetChanged();
                                } else if (bean.getData() != null && bean.getData().size()>0) {

                                    UIUtils.showToast("没有更多了");

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(Player3_0Activity.this));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });


    }


    private void initView() {
        player3_0_xrecyclerview = (XRecyclerView) findViewById(R.id.player3_0_xrecyclerview);
        quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);
        center_view = (TextView) findViewById(R.id.center_view);


        adapter = new Player3Adapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        player3_0_xrecyclerview.setLayoutManager(layoutManager);
        player3_0_xrecyclerview.setNestedScrollingEnabled(false);
        player3_0_xrecyclerview.setAdapter(adapter);

        quickIndexBar.setOnTouchLetterListenner(new QuickIndexBar.onTouchLetterListenner() {

            @Override
            public void onTouchLetter(String letter) {

                // 屏幕中间显示带字母的View
                showCenterAnimView(letter);
            }

        });


        player3_0_xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                player3_0_xrecyclerview.refresh();
            }

            @Override
            public void onLoadMore() {
                player3_0_xrecyclerview.refresh();
            }
        });

    }
    private Handler mHandler = new Handler();
    private boolean mIsScale;
    protected void showCenterAnimView(String letter) {
        center_view.setText(letter);
        if (!mIsScale) {
            mIsScale = true;
            ViewPropertyAnimator.animate(center_view).scaleX(1.0F)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(400).start();
            ViewPropertyAnimator.animate(center_view).scaleY(1.0F)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(400).start();

        }
        mHandler.removeCallbacksAndMessages(null);

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                ViewPropertyAnimator.animate(center_view).scaleX(0F)
                        .setInterpolator(new OvershootInterpolator())
                        .setDuration(450).start();
                ViewPropertyAnimator.animate(center_view).scaleY(0F)
                        .setInterpolator(new OvershootInterpolator())
                        .setDuration(450).start();

                mIsScale = false;
            }
        }, 1500);
    }
}
