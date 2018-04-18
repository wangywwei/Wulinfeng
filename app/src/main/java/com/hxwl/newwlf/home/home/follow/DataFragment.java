package com.hxwl.newwlf.home.home.follow;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.QuanshouxiangqingBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.CircleProgressBar;
import com.hxwl.wulinfeng.view.ExpandableTextView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/*
* 关注--资料
* */
public class DataFragment extends BaseFragment {
    private View view;
    private TextView tv_guoji;//国籍
    private ImageView iv_guoqi;
    private TextView tv_guojitext;
    private TextView tv_shengao;
    private TextView tv_shengaotext;
    private TextView tv_nianling;
    private TextView tv_nianlingtv;
    private TextView tv_jibie;
    private TextView tv_jibietext;
    private TextView tv_tizhong;
    private TextView tv_tizhongtext;
    private TextView tv_julebu;
    private TextView tv_julebutext;
    private TextView tv_jianjie;
    private ExpandableTextView tv_jianjietext;
    private CircleProgressBar solid_progress1;
    private CircleProgressBar solid_progress2;
    private CircleProgressBar solid_progress3;
    private TextView tv_zongjitext;
    private TextView tv_shengtext;
    private TextView tv_zkotext;
    private TextView tv_futext;
    private QuanshouxiangqingBean.DataBean playDetailsBean; //选手详情
    private String agentId;
    private TextView tv_name;
    private TextView tv_nametext;

    public void setAgentId(String agentId) {
        this.agentId = agentId;
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_data, null);
            initView(view);
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        initView(view);
        return view;

    }

    private void initData() {
        if (StringUtils.isBlank(agentId)) {
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_GETPLAYERINFO)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId", MakerApplication.instance.getUid() )
                .addParams("playerId", agentId)
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
                                QuanshouxiangqingBean bean = gson.fromJson(response, QuanshouxiangqingBean.class);
                                if (bean.getCode().equals("1000")) {
                                    playDetailsBean = bean.getData();
                                    initDetails();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });

    }

    private void initDetails() {
        if (playDetailsBean == null) {
            return;
        }

        tv_jianjietext.resetTextView();
        tv_jianjietext.setText(playDetailsBean.getIntroduction());
        tv_nametext.setText(playDetailsBean.getPlayerName());
        tv_nametext.setText(playDetailsBean.getPlayerName());

//        if (!TextUtils.isEmpty(playDetailsBean.getGuoqi_img())) {
//            iv_guoqi.setVisibility(View.VISIBLE);
//            ImageUtils.getPic(playDetailsBean.getGuoqi_img(), iv_guoqi, PlayDetailsActivity.this);
//        } else {
//            iv_guoqi.setVisibility(View.GONE);
//        }

        tv_guojitext.setText(playDetailsBean.getCountryName() + "");
        if (playDetailsBean.getHeight()!=0){
            tv_shengaotext.setText(playDetailsBean.getHeight() + "cm");
        }else {
            tv_shengaotext.setText("暂无");
        }

        tv_jibietext.setText(playDetailsBean.getWeightLevelName());
        if (playDetailsBean.getWeight()!=0) {
            tv_tizhongtext.setText(playDetailsBean.getWeight() + "kg");
        }else {
            tv_tizhongtext.setText("暂无");
        }

        tv_julebutext.setText(playDetailsBean.getClubName());

        tv_nianlingtv.setText(playDetailsBean.getBirthday());
        //计算
        String win = playDetailsBean.getWinNum() + "";
        String ko = playDetailsBean.getKoNum() + "";
        String lose = playDetailsBean.getFailNum() + "";
        tv_zongjitext.setText("总计 "+playDetailsBean.getTotal() + "");
        tv_shengtext.setText("胜 "+win);
        tv_zkotext.setText("KO "+ko);
        tv_futext.setText("负 "+lose);

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
                solid_progress1.setProgress(progress);
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
                solid_progress2.setProgress(progress);
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
                solid_progress3.setProgress(progress);
            }
        });
        c_animator.setRepeatCount(0);
        c_animator.setDuration(3000);
        c_animator.start();

    }

    private void initView(View view) {
        tv_guoji = (TextView) view.findViewById(R.id.tv_guoji);
        iv_guoqi = (ImageView) view.findViewById(R.id.iv_guoqi);
        tv_guojitext = (TextView) view.findViewById(R.id.tv_guojitext);
        tv_shengao = (TextView) view.findViewById(R.id.tv_shengao);
        tv_shengaotext = (TextView) view.findViewById(R.id.tv_shengaotext);
        tv_nianling = (TextView) view.findViewById(R.id.tv_nianling);
        tv_nianlingtv = (TextView) view.findViewById(R.id.tv_nianlingtv);
        tv_jibie = (TextView) view.findViewById(R.id.tv_jibie);
        tv_jibietext = (TextView) view.findViewById(R.id.tv_jibietext);
        tv_tizhong = (TextView) view.findViewById(R.id.tv_tizhong);
        tv_tizhongtext = (TextView) view.findViewById(R.id.tv_tizhongtext);
        tv_julebu = (TextView) view.findViewById(R.id.tv_julebu);
        tv_julebutext = (TextView) view.findViewById(R.id.tv_julebutext);
        tv_jianjie = (TextView) view.findViewById(R.id.tv_jianjie);
        tv_jianjietext = (ExpandableTextView) view.findViewById(R.id.tv_jianjietext);
        solid_progress1 = (CircleProgressBar) view.findViewById(R.id.solid_progress1);
        solid_progress2 = (CircleProgressBar) view.findViewById(R.id.solid_progress2);
        solid_progress3 = (CircleProgressBar) view.findViewById(R.id.solid_progress3);
        tv_zongjitext = (TextView) view.findViewById(R.id.tv_zongjitext);
        tv_shengtext = (TextView) view.findViewById(R.id.tv_shengtext);
        tv_zkotext = (TextView) view.findViewById(R.id.tv_zkotext);
        tv_futext = (TextView) view.findViewById(R.id.tv_futext);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_nametext = (TextView) view.findViewById(R.id.tv_nametext);

    }
}
