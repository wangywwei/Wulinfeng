package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.LoginBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.LoginUser;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.TimeCountUtil;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/20.
 * 更换绑定手机号
 */
public class ChangePhoneActivity extends BaseActivity implements View.OnClickListener {

    protected MakerApplication appContext = null;
    private EditText phoneNum;
    private TextView tv_send;
    private EditText code_sms;
    private ImageView iv_clear;
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changephone_activity);
        AppUtils.setTitle(ChangePhoneActivity.this);
        appContext = (MakerApplication)getApplicationContext() ;
        initView();
    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if(!TextUtils.isEmpty(s1)){
                    tv_send.setVisibility(View.VISIBLE);
                }else{
                    tv_send.setVisibility(View.GONE);
                }
                if(s1.length() == 11 ){
                    if (!AppUtils.isMobileNO(s1)) {
                        ToastUtils.showToast(ChangePhoneActivity.this, "请输入正确格式的手机号");
                        return;
                    }

                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_send.setOnClickListener(this);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        code_sms = (EditText) findViewById(R.id.code_sms);
        code_sms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s =  charSequence.toString();
                if(!TextUtils.isEmpty(s)){
                    iv_clear.setVisibility(View.VISIBLE);
                }else{
                    iv_clear.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null ;
        switch(view.getId()){
            case R.id.tv_send://发送验证码
                sendCode();
                break ;
            case R.id.iv_clear://文本清除
                code_sms.setText("");
                break ;
            case R.id.tv_login://绑定
                initData();
                break ;
            default:
                break;

        }
    }


    private void sendCode() {
        if (!SystemHelper.isConnected(ChangePhoneActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        final String mobile = phoneNum.getText().toString() ;
        if(TextUtils.isEmpty(mobile)){
            ToastUtils.showToast(ChangePhoneActivity.this,"手机号不能为空");
            return;
        }
        if(!AppUtils.isMobileNO(mobile)){
            ToastUtils.showToast(ChangePhoneActivity.this,"请输入正确格式的手机号");
            return ;
        }
        TimeCountUtil timeCountUtil = new TimeCountUtil(ChangePhoneActivity.this, 60000, 1000,
                tv_send);
        timeCountUtil.start();
        tv_send.setBackground(getResources().getDrawable(R.drawable.send_gray));
        OkHttpUtils.post()
                .url(URLS.HOME_GETCODE)
                .addParams("phone",mobile)
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {

                                    UIUtils.showToast(bean.getMessage());

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(ChangePhoneActivity.this));
                                    ChangePhoneActivity.this.finish();
                                }else {
                                    UIUtils.showToast(bean.getMessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void initData() {
        if (!SystemHelper.isConnected(ChangePhoneActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        final String phone = phoneNum.getText().toString();
        String code = code_sms.getText().toString();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            ToastUtils.showToast(ChangePhoneActivity.this,"手机号或验证码不能为空");
            return ;
        }

        final LoginUser user = null ;

        OkHttpUtils.post()
                .url(URLS.USER_UPDATE)
                .addParams("id",MakerApplication.instance.getUid())
                .addParams("phone",phone)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("verifyCode",code)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                LoginBean bean = gson.fromJson(response, LoginBean.class);
                                if (bean.getCode().equals("1000")) {
                                    MakerApplication.instance().setPhone(phone ,ChangePhoneActivity.this);
                                    UIUtils.showToast(bean.getMessage());
                                    Intent intent = new Intent();
                                    intent.putExtra("phone",phone) ;
                                    setResult(101 ,intent);
                                    finish();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(ChangePhoneActivity.this));
                                    ChangePhoneActivity.this.finish();
                                }else {
                                    UIUtils.showToast(bean.getMessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }
}
