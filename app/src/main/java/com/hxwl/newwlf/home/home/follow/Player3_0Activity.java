package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.PlayDetailsActivity;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import okhttp3.Call;

public class Player3_0Activity extends BaseActivity {

    private ListView mLv;
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
        mLv = (ListView) findViewById(R.id.lv);
        quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);
        center_view = (TextView) findViewById(R.id.center_view);

        Collections.sort(list);
        adapter = new Player3Adapter(this, list);

        mLv.setAdapter(adapter);

        quickIndexBar.setOnTouchLetterListenner(new QuickIndexBar.onTouchLetterListenner() {

            @Override
            public void onTouchLetter(String letter) {
                for (int i = 0; i < list.size(); i++) {
                    String itemLetter = list.get(i).getmPinYin().charAt(0)
                            + "";
                    if (itemLetter.equals(letter)) {
                        // 将ListView里面一样的对应的字母设置到屏幕
                        mLv.setSelection(i);
                        // 因为ListView里面有两个或者多个相同的对应的字母
                        break;
                    }
                }
                // 屏幕中间显示带字母的View
                showCenterAnimView(letter);
            }

        });

        ViewHelper.setScaleX(center_view, 0);
        ViewHelper.setScaleY(center_view, 0);

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(PlayDetailsActivity.getIntent(Player3_0Activity.this,list.get(position).getPlayerId()+""));

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
