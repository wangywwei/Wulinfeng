package com.hxwl.wulinfeng.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.ClubDetailsBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.ClubRecordAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.ClubListBean;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
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

import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;


/**
 * Created by Allen on 2017/6/13.
 * 俱乐部详情
 */
public class ClubDetailsActivity extends BaseActivity {
    public static Intent getIntent(Context context,String clubId) {
        Intent intent = new Intent(context, ClubDetailsActivity.class);
        intent.putExtra("clubId",clubId);
        return intent;
    }
    private String clubId;
    private RelativeLayout rl_layout, title_back;
    private ScrollViewX rv_layout;
    private TextView tv_name;//title上边显示的名字
    private TextView player_name;

    private ExpandableTextView tv_jianjietext;//简介

    private ClubDetailsBean clubDetailsBean;

    private CommonSwipeRefreshLayout common_layout;
    private boolean isRefresh;

    private int maxDescripLine = 3; //TextView默认最大展示行数
    private ImageView iv_usericon;
    private CircleProgressBar solidProgress1;
    private CircleProgressBar solidProgress2;
    private CircleProgressBar solidProgress3;
    private TextView tv_zongjitext;
    private TextView tv_wintext;
    private TextView tv_kotext;
    private TextView tv_losetext;
    private int page = 1;
    private List<ClubListBean> playListBean = new ArrayList<>();
    private ClubRecordAdapter mdapter;
    private MyListView lvCansiaRecord;
    private TextView tv_mingxing;
    private TextView tv_xueyuan;
    private int etvWidth;
    private int statusBarHeight1;
    private LinearLayout club_gone;
    private ImageView beijing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clubdetails_activity);
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
        StatusBarUtil.setTranslucentForImageView(ClubDetailsActivity.this, 100, rl_layout);
        clubId = getIntent().getStringExtra("clubId");
        initView();
        initData();
    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.btn_back);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        club_gone= (LinearLayout) findViewById(R.id.club_gone);
        club_gone.setVisibility(View.GONE);
        iv_usericon = (ImageView) findViewById(R.id.iv_usericon);
        rv_layout = (ScrollViewX) findViewById(R.id.rv_layout);
        rl_layout = (RelativeLayout) findViewById(R.id.rl_layout);
        beijing= (ImageView) findViewById(R.id.beijing);
        title_back = (RelativeLayout) findViewById(R.id.title_back);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtils.dip2px(ClubDetailsActivity.this, 50));
        layoutParams.setMargins(0, statusBarHeight1, 0, 0);//4个参数按顺序分别是左上右下
        title_back.setLayoutParams(layoutParams);

        tv_name = (TextView) findViewById(R.id.tv_name);
        player_name = (TextView) findViewById(R.id.player_name);
        tv_jianjietext = (ExpandableTextView) findViewById(R.id.tv_jianjietext);
        tv_jianjietext.setVisibility(View.VISIBLE);


        solidProgress1 = (CircleProgressBar) findViewById(R.id.solid_progress1);
        solidProgress2 = (CircleProgressBar) findViewById(R.id.solid_progress2);
        solidProgress3 = (CircleProgressBar) findViewById(R.id.solid_progress3);

        tv_mingxing = (TextView) findViewById(R.id.tv_mingxing);
        tv_xueyuan = (TextView) findViewById(R.id.tv_xueyuan);

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

            }
        });

        lvCansiaRecord = (MyListView) findViewById(R.id.lv_cansia_record);
        lvCansiaRecord.setVisibility(View.GONE);
        lvCansiaRecord.setFocusable(false);
        mdapter = new ClubRecordAdapter(ClubDetailsActivity.this, playListBean);
        lvCansiaRecord.setAdapter(mdapter);


        rv_layout.setOnScrollListener(new ScrollViewX.OnScrollListener() {
            @Override
            public void onScrollChanged(int x, int y, int oldX, int oldY) {
                boolean b = rv_layout.isChildVisible(rl_layout);
                if (b) {
                    title_back.setBackgroundColor(getResources().getColor(R.color.transparent));
                    StatusBarUtil.setTranslucentForImageView(ClubDetailsActivity.this, 80, rl_layout);
                    tv_name.setVisibility(View.GONE);
                } else {
                    title_back.setBackgroundColor(getResources().getColor(R.color.shouye_tab));
                    AppUtils.setTitle(ClubDetailsActivity.this);
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
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_CULBRINFO)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("clubId",clubId)
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
                                ClubDetailsBean bean = gson.fromJson(response, ClubDetailsBean.class);
                                if (bean.getCode().equals("1000")){
                                    clubDetailsBean=bean;
                                    initDetails();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(ClubDetailsActivity.this));
                                    ClubDetailsActivity.this.finish();
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
        if (clubDetailsBean == null) {
            return;
        }

        if (StringUtils.isBlank(clubDetailsBean.getData().getBackgroundImage())){
            Glide.with(this)
                    .load(URLS.IMG+clubDetailsBean.getData().getClubLogo())
                    .placeholder(R.drawable.backgrougimg)
                    .error(R.drawable.backgrougimg)
                    .crossFade(1000)
                    .bitmapTransform(new BlurTransformation(this,23,1))  // “23”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                    .into(beijing);
        }else {
            Glide.with(this)
                    .load(URLS.IMG+clubDetailsBean.getData().getBackgroundImage())
                    .placeholder(R.drawable.backgrougimg)
                    .error(R.drawable.backgrougimg)
                    .crossFade(1000)
                    .into(beijing);
        }

        tv_jianjietext.resetTextView();
        try {
            tv_jianjietext.setText(clubDetailsBean.getData().getIntro());
        }catch (Exception o){

        }

        player_name.setText(clubDetailsBean.getData().getClubName());

        try {
            ImageUtils.getPic(URLS.IMG+clubDetailsBean.getData().getClubLogo(), iv_usericon, ClubDetailsActivity.this);
        }catch (Exception o){
        }

        //明星学员
        tv_mingxing.setText(clubDetailsBean.getData().getStartStudent());

        //计算
        String win = clubDetailsBean.getData().getWinSum()+"";
        String ko = clubDetailsBean.getData().getKoSum()+"";
        String lose = clubDetailsBean.getData().getFailSum()+"";

        if (win != null && ko != null && lose != null) {
            int zongji = clubDetailsBean.getData().getTotal();
            tv_zongjitext.setText(zongji + "");//总场数
            tv_wintext.setText(win);//胜场数
            tv_kotext.setText(ko);//KO场数
            tv_losetext.setText(lose);//败场数
            //胜率
            a_simulateProgress(clubDetailsBean.getData().getWinPercent());
            //KO率
            b_simulateProgress(clubDetailsBean.getData().getKoPercent());
            //失败率
            c_simulateProgress(clubDetailsBean.getData().getFailPercent());

        }

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
        StatService.onPageStart(this, "俱乐部详情");
        TCAgent.onPageStart(this, "俱乐部详情");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "俱乐部详情");
        TCAgent.onPageEnd(this, "俱乐部详情");
    }



}
