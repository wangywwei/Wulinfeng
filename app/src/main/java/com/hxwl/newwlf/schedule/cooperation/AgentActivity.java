package com.hxwl.newwlf.schedule.cooperation;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.MyPagerAdapter;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.AgentBean;
import com.hxwl.newwlf.sousuo.CustomViewPager;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.DialogUtil;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.ExpandableTextView;
import com.jaeger.library.StatusBarUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/*
* 省代详情
* */
public class AgentActivity extends BaseActivity implements View.OnClickListener {
    private ImageView btn_back;
    private TextView tv_name;
    private ImageView iv_usericon;
    private TextView player_name;
    private ExpandableTextView tv_jianjietext;
    private ImageView agent_img;
    private RelativeLayout agent_relative;
    private ImageView agent_img2;
    private RelativeLayout agent_relative2;
    private TextView tv_chat;
    private View chat_line;
    private RelativeLayout rl_chat,title_back;
    private TextView tv_guess;
    private View guess_line;
    private RelativeLayout rl_guess;
    private TextView tv_huigu;
    private View huigu_line;
    private RelativeLayout rl_huigu;

    public static Intent getIntent(Context context, String agentId) {
        Intent intent = new Intent(context, AgentActivity.class);
        intent.putExtra("agentId", agentId);
        return intent;
    }

    private CustomViewPager agent_content;
    private String agentId;
    private BiSAIZixunFragment informationFragment;
    private CooZixunFragment zixunFragment;
    private QuanshendaiFragment quanshouFragment;
    private AgentBean.DataBean agentbean;
    private RelativeLayout rl_layout;
    private TextView address;
    private TextView tet;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int statusBarHeight1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);

        statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        StatusBarUtil.setTranslucentForImageView(AgentActivity.this, 100, rl_layout);
        agentId = getIntent().getStringExtra("agentId");
        informationFragment = new BiSAIZixunFragment();
        zixunFragment = new CooZixunFragment();
        quanshouFragment = new QuanshendaiFragment();

        initView();
        fragments.add(zixunFragment);
        fragments.add(quanshouFragment);
        fragments.add(informationFragment);
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, null);
        agent_content.setAdapter(mAdapter);

        agent_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        agent_content.setCurrentItem(0);
                        tv_chat.setTextColor(getResources().getColor(R.color.shouye_tab));
                        chat_line.setVisibility(View.VISIBLE);
                        tv_guess.setTextColor(getResources().getColor(R.color.editTextcolor));
                        guess_line.setVisibility(View.GONE);
                        tv_huigu.setTextColor(getResources().getColor(R.color.editTextcolor));
                        huigu_line.setVisibility(View.GONE);
                        break;
                    case 1:
                        agent_content.setCurrentItem(1);
                        tv_chat.setTextColor(getResources().getColor(R.color.editTextcolor));
                        chat_line.setVisibility(View.GONE);
                        tv_guess.setTextColor(getResources().getColor(R.color.shouye_tab));
                        guess_line.setVisibility(View.VISIBLE);
                        tv_huigu.setTextColor(getResources().getColor(R.color.editTextcolor));
                        huigu_line.setVisibility(View.GONE);
                        break;
                    case 2:
                        agent_content.setCurrentItem(2);
                        tv_chat.setTextColor(getResources().getColor(R.color.editTextcolor));
                        chat_line.setVisibility(View.GONE);
                        tv_guess.setTextColor(getResources().getColor(R.color.editTextcolor));
                        guess_line.setVisibility(View.GONE);
                        tv_huigu.setTextColor(getResources().getColor(R.color.shouye_tab));
                        huigu_line.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initData();

    }

    private void initData() {
        if (StringUtils.isBlank(agentId)) {
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_AGENTINFO)
                .addParams("agentId", agentId)
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
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                AgentBean bean = gson.fromJson(response, AgentBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if (bean.getData() == null) {

                                        UIUtils.showToast("暂无数据");
                                    }
                                    agentbean = bean.getData();
                                    quanshouFragment.setAgentId(bean.getData().getId() + "");
                                    informationFragment.setAgentId(bean.getData().getId() + "");
                                    zixunFragment.setAgentId(bean.getData().getId() + "");
                                    initDataY();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(AgentActivity.this));
                                    AgentActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    private void initDataY() {
        if (agentbean == null) {
            return;
        }
        tv_jianjietext.resetTextView();
        tv_jianjietext.setText(agentbean.getIntro());

        tv_name.setText(agentbean.getAgentName());

        GlidUtils.setGrid2(this, URLS.IMG + agentbean.getLogoImage(), iv_usericon);
        player_name.setText(agentbean.getProvinceName());
        address.setText(agentbean.getAddress());
        tet.setText(agentbean.getUpdatetime() + "");


        if (!StringUtils.isBlank(agentbean.getBackImage())) {
            GlidUtils.setBackground(this, URLS.IMG + agentbean.getBackImage(), rl_layout);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        agent_content = (CustomViewPager) findViewById(R.id.agent_content);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_back.setFocusable(true);
        btn_back.setFocusableInTouchMode(true);
        btn_back.requestFocus();
        address = (TextView) findViewById(R.id.address);
        tet = (TextView) findViewById(R.id.tet);
        rl_layout = (RelativeLayout) findViewById(R.id.rl_layout);
        tv_name = (TextView) findViewById(R.id.tv_name);
        iv_usericon = (ImageView) findViewById(R.id.iv_usericon);
        player_name = (TextView) findViewById(R.id.player_name);
        tv_jianjietext = (ExpandableTextView) findViewById(R.id.tv_jianjietext);
        agent_img = (ImageView) findViewById(R.id.agent_img);
        agent_relative = (RelativeLayout) findViewById(R.id.agent_relative);
        agent_img2 = (ImageView) findViewById(R.id.agent_img2);
        agent_relative2 = (RelativeLayout) findViewById(R.id.agent_relative2);
        tv_chat = (TextView) findViewById(R.id.tv_chat);
        chat_line = (View) findViewById(R.id.chat_line);
        rl_chat = (RelativeLayout) findViewById(R.id.rl_chat);
        tv_guess = (TextView) findViewById(R.id.tv_guess);
        guess_line = (View) findViewById(R.id.guess_line);
        rl_guess = (RelativeLayout) findViewById(R.id.rl_guess);
        tv_huigu = (TextView) findViewById(R.id.tv_huigu);
        huigu_line = (View) findViewById(R.id.huigu_line);
        rl_huigu = (RelativeLayout) findViewById(R.id.rl_huigu);

        title_back= (RelativeLayout) findViewById(R.id.title_back);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtils.dip2px(AgentActivity.this, 50));
        layoutParams.setMargins(0, statusBarHeight1, 0, 0);//4个参数按顺序分别是左上右下
        title_back.setLayoutParams(layoutParams);


        rl_chat.setOnClickListener(this);
        rl_huigu.setOnClickListener(this);
        rl_guess.setOnClickListener(this);
        agent_relative2.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_chat:
                agent_content.setCurrentItem(0);
                tv_chat.setTextColor(getResources().getColor(R.color.shouye_tab));
                chat_line.setVisibility(View.VISIBLE);
                tv_guess.setTextColor(getResources().getColor(R.color.editTextcolor));
                guess_line.setVisibility(View.GONE);
                tv_huigu.setTextColor(getResources().getColor(R.color.editTextcolor));
                huigu_line.setVisibility(View.GONE);
                break;
            case R.id.rl_guess:
                agent_content.setCurrentItem(1);
                tv_chat.setTextColor(getResources().getColor(R.color.editTextcolor));
                chat_line.setVisibility(View.GONE);
                tv_guess.setTextColor(getResources().getColor(R.color.shouye_tab));
                guess_line.setVisibility(View.VISIBLE);
                tv_huigu.setTextColor(getResources().getColor(R.color.editTextcolor));
                huigu_line.setVisibility(View.GONE);
                break;
            case R.id.rl_huigu:
                agent_content.setCurrentItem(2);
                tv_chat.setTextColor(getResources().getColor(R.color.editTextcolor));
                chat_line.setVisibility(View.GONE);
                tv_guess.setTextColor(getResources().getColor(R.color.editTextcolor));
                guess_line.setVisibility(View.GONE);
                tv_huigu.setTextColor(getResources().getColor(R.color.shouye_tab));
                huigu_line.setVisibility(View.VISIBLE);
                break;
            case R.id.agent_relative2:
                //先new出一个监听器，设置好监听
                DialogInterface.OnClickListener dialogOnclicListener = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case Dialog.BUTTON_POSITIVE:
                                requestPermission();
                                break;
                            case Dialog.BUTTON_NEGATIVE:
                                break;
                            case Dialog.BUTTON_NEUTRAL:
                                break;
                        }
                    }
                };

                //弹窗让用户选择，是否允许申请权限
                DialogUtil.showConfirm(AgentActivity.this, "申请权限", "是否允许获取打电话权限？", dialogOnclicListener, dialogOnclicListener);

                break;

        }
    }

    private void requestPermission() {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(AgentActivity.this, Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                        100);
                return;
            } else {
                callPhone();
            }
        } else {
            callPhone();
        }
    }

    /**
     * 拨号方法
     */
    private void callPhone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + tet.getText().toString().trim());
        intent.setData(data);
        startActivity(intent);
    }

}
