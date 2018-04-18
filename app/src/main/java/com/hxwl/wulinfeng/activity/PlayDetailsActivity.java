package com.hxwl.wulinfeng.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.QuanshouduizhengBean;
import com.hxwl.newwlf.modlebean.QuanshouxiangqingBean;
import com.hxwl.newwlf.modlebean.YueyueBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.BisaiRecordAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.CircleProgressBar;
import com.hxwl.wulinfeng.view.ExpandableTextView;
import com.hxwl.wulinfeng.view.MyListView;
import com.hxwl.wulinfeng.view.ScrollViewX;
import com.jaeger.library.StatusBarUtil;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/13.
 * 选手详情页面
 */
public class PlayDetailsActivity extends BaseActivity implements View.OnClickListener {
    public static Intent getIntent(Context context, String play_id) {
        Intent intent = new Intent(context, PlayDetailsActivity.class);
        intent.putExtra("play_id",play_id);
        return intent;
    }

    private String play_id;
    private RelativeLayout rl_layout, title_back;
    private ScrollViewX rv_layout;
    private TextView tv_name;//title上边显示的名字
    private TextView player_name;
    private ExpandableTextView tv_jianjietext;//简介

    private QuanshouxiangqingBean.DataBean playDetailsBean; //选手详情

    private CommonSwipeRefreshLayout common_layout;
    private boolean isRefresh;

    private TextView tv_guojitext;
    private TextView tv_shengaotext;
    private TextView tv_nianlingtv;
    private TextView tv_jibietext;
    private TextView tv_tizhongtext;
    private TextView tv_julebutext;
    private ImageView iv_usericon;
    private CircleProgressBar solidProgress1;
    private CircleProgressBar solidProgress2;
    private CircleProgressBar solidProgress3;
    private TextView tv_zongjitext;
    private TextView tv_wintext;
    private TextView tv_kotext;
    private TextView tv_losetext;
    private ImageView guanzhu;
    private int page = 1;
    private List<QuanshouduizhengBean.DataBean> playListBean = new ArrayList<>();
    private BisaiRecordAdapter mdapter;
    private MyListView lvCansiaRecord;
    private ImageView iv_guoqi;
    private int statusBarHeight1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playdetails_activity);
        play_id = getIntent().getStringExtra("play_id");
        /**
         * 获取状态栏高度——方法1
         * */
        statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        StatusBarUtil.setTranslucentForImageView(PlayDetailsActivity.this, 100, rl_layout);

        initView();
        initData();
        initListData(page);
    }


    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.btn_back);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        guanzhu= (ImageView) findViewById(R.id.guanzhu);
        guanzhu.setOnClickListener(this);
        iv_usericon = (ImageView) findViewById(R.id.iv_usericon);
        rv_layout = (ScrollViewX) findViewById(R.id.rv_layout);
        rl_layout = (RelativeLayout) findViewById(R.id.rl_layout);
        title_back = (RelativeLayout) findViewById(R.id.title_back);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtils.dip2px(PlayDetailsActivity.this,50));
        layoutParams.setMargins(0, statusBarHeight1, 0, 0);//4个参数按顺序分别是左上右下
        title_back.setLayoutParams(layoutParams);

        tv_name = (TextView) findViewById(R.id.tv_name);
        player_name = (TextView) findViewById(R.id.player_name);
        tv_jianjietext = (ExpandableTextView) findViewById(R.id.tv_jianjietext);
        tv_jianjietext.setVisibility(View.VISIBLE);

        iv_guoqi = (ImageView) findViewById(R.id.iv_guoqi);

        solidProgress1 = (CircleProgressBar) findViewById(R.id.solid_progress1);
        solidProgress2 = (CircleProgressBar) findViewById(R.id.solid_progress2);
        solidProgress3 = (CircleProgressBar) findViewById(R.id.solid_progress3);

        tv_guojitext = (TextView) findViewById(R.id.tv_guojitext);
        tv_shengaotext = (TextView) findViewById(R.id.tv_shengaotext);
        tv_nianlingtv = (TextView) findViewById(R.id.tv_nianlingtv);
        tv_jibietext = (TextView) findViewById(R.id.tv_jibietext);
        tv_tizhongtext = (TextView) findViewById(R.id.tv_tizhongtext);
        tv_julebutext = (TextView) findViewById(R.id.tv_julebutext);

        tv_zongjitext = (TextView) findViewById(R.id.tv_zongjitext);
        tv_wintext = (TextView) findViewById(R.id.tv_wintext);
        tv_kotext = (TextView) findViewById(R.id.tv_kotext);
        tv_losetext = (TextView) findViewById(R.id.tv_losetext);

        common_layout = (CommonSwipeRefreshLayout) findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(false);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initListData(page);
            }
        });

        lvCansiaRecord = (MyListView) findViewById(R.id.lv_cansia_record);
        lvCansiaRecord.setFocusable(false);
        mdapter = new BisaiRecordAdapter(PlayDetailsActivity.this, playListBean);
        lvCansiaRecord.setAdapter(mdapter);

        rv_layout.setOnScrollListener(new ScrollViewX.OnScrollListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldX, int oldY) {
                boolean b = rv_layout.isChildVisible(rl_layout);
                if (b) {
                    title_back.setBackgroundColor(getResources().getColor(R.color.transparent));
                    StatusBarUtil.setTranslucentForImageView(PlayDetailsActivity.this, 100, rl_layout);
                    tv_name.setVisibility(View.GONE);
                } else {
                    title_back.setBackgroundColor(getResources().getColor(R.color.shouye_tab));
                   AppUtils.setTitle(PlayDetailsActivity.this);
                    tv_name.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrollStopped() {

            }

            @Override
            public void onScrolling() {

            }
        });
    }

    private void initData() {
        if(StringUtils.isBlank(play_id)){
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_GETPLAYERINFO)
                .addParams("userId",MakerApplication.instance.getUid())
                .addParams("playerId",play_id)
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
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                QuanshouxiangqingBean bean = gson.fromJson(response, QuanshouxiangqingBean.class);
                                if (bean.getCode().equals("1000")){
                                    playDetailsBean = bean.getData();
                                    initDetails();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(PlayDetailsActivity.this));
                                    PlayDetailsActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

    }

    private void initListData(final int page) {
        if(StringUtils.isBlank(play_id)){
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_GETPLAYERAGAINSTLIST)
                .addParams("playerId",play_id)
                .addParams("pageNumber",page+"")
                .addParams("pageSize","10")
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
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                QuanshouduizhengBean bean = gson.fromJson(response, QuanshouduizhengBean.class);
                                if (bean.getCode().equals("1000")){
                                    playListBean.addAll(bean.getData());
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    mdapter.notifyDataSetChanged();
                                } else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(PlayDetailsActivity.this));
                                    PlayDetailsActivity.this.finish();
                                }else if (bean.getData() == null || bean.getData().size()==0) {
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    ToastUtils.showToast(PlayDetailsActivity.this, "没有更多了");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });


    }

    /**
     * 初始化选手数据
     */
    private void initDetails() {
        if (playDetailsBean == null) {
            return;
        }

        switch (playDetailsBean.getUserIsFollow()){
            case "0":
                guanzhu.setImageResource(R.drawable.guanzhu1);
                break;
            case "1":
                guanzhu.setImageResource(R.drawable.guanzhu0);
                break;

        }

        tv_jianjietext.resetTextView();
        try {
            tv_jianjietext.setText(playDetailsBean.getIntroduction());
        }catch (Exception o){

        }

        tv_name.setText(playDetailsBean.getPlayerName());
        player_name.setText(playDetailsBean.getPlayerName());
        if (StringUtils.isBlank(playDetailsBean.getHeadImg())){
            iv_usericon.setImageResource(R.drawable.xuanshoutouxiang);
        }else {
            GlidUtils.setGrid2(this,URLS.IMG+playDetailsBean.getHeadImg(),iv_usericon);
        }

        if (!TextUtils.isEmpty(playDetailsBean.getCountryImg())) {
            iv_guoqi.setVisibility(View.VISIBLE);
            ImageUtils.getPic(playDetailsBean.getCountryImg(), iv_guoqi, PlayDetailsActivity.this);
        } else {
            iv_guoqi.setVisibility(View.GONE);
        }

        tv_guojitext.setText(playDetailsBean.getCountryName()+"");
        if (playDetailsBean.getHeight()!=0){
            tv_shengaotext.setText(playDetailsBean.getHeight() + "cm");
        }else {
            tv_shengaotext.setText("暂无");
        }
        if (playDetailsBean.getWeight()!=0){
            tv_tizhongtext.setText(playDetailsBean.getWeight()+"kg");
        }else {
            tv_tizhongtext.setText("暂无");
        }
        tv_jibietext.setText(playDetailsBean.getWeightLevelName());

        tv_julebutext.setText(playDetailsBean.getClubName());

        tv_nianlingtv.setText(playDetailsBean.getBirthday());
        //计算
        String win = playDetailsBean.getWinNum()+"";
        String ko = playDetailsBean.getKoNum()+"";
        String lose = playDetailsBean.getFailNum()+"";
        tv_zongjitext.setText(playDetailsBean.getTotal()+"");
        tv_wintext.setText(win);
        tv_kotext.setText(ko);
        tv_losetext.setText(lose);

        a_simulateProgress(playDetailsBean.getWinPercent());
        b_simulateProgress(playDetailsBean.getKoPercent());
        c_simulateProgress(playDetailsBean.getFailPercent());


    }

    private void a_simulateProgress(int A_num) {
        ValueAnimator a_animator = ValueAnimator.ofInt(0, A_num);

        a_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                solidProgress1.setProgress(progress);
            }
        });
        a_animator.setRepeatCount(0);
        a_animator.setDuration(3000);
        a_animator.start();
    }

    private void b_simulateProgress(int B_num) {
        ValueAnimator b_animator = ValueAnimator.ofInt(0, B_num);
        b_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                solidProgress2.setProgress(progress);
            }
        });
        b_animator.setRepeatCount(0);
        b_animator.setDuration(3000);
        b_animator.start();

    }

    private void c_simulateProgress(int c_num) {
        ValueAnimator c_animator = ValueAnimator.ofInt(0, c_num);
        c_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                solidProgress3.setProgress(progress);
            }
        });
        c_animator.setRepeatCount(0);
        c_animator.setDuration(3000);
        c_animator.start();

    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "选手详情");
        TCAgent.onPageStart(this, "选手详情");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "选手详情");
        TCAgent.onPageEnd(this, "选手详情");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.guanzhu:
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                switch (playDetailsBean.getUserIsFollow()){
                    case "0":
                        initGuanzhu(URLS.SCHEDULE_USERPLAYERATTENTION);
                        break;
                    case "1":
                        initGuanzhu(URLS.SCHEDULE_USERCANCELPLAYERATTENTION);
                        break;

                }

                break;
        }
    }

    private void initGuanzhu(String url) {
        if(StringUtils.isBlank(play_id)){
            return;
        }
        OkHttpUtils.post()
                .url(url)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("playerId",play_id)
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
                                YueyueBean bean = gson.fromJson(response, YueyueBean.class);
                                if (bean.getCode().equals("1000")){
                                    switch (playDetailsBean.getUserIsFollow()){
                                        case "0":
                                            guanzhu.setImageResource(R.drawable.guanzhu0);
                                            playDetailsBean.setUserIsFollow("1");
                                            break;
                                        case "1":
                                            guanzhu.setImageResource(R.drawable.guanzhu1);
                                            playDetailsBean.setUserIsFollow("0");
                                            break;

                                    }

                                    ToastUtils.showToast(PlayDetailsActivity.this,bean.getMessage());

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(PlayDetailsActivity.this));
                                    PlayDetailsActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });
    }
}
