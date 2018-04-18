package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import com.hxwl.newwlf.modlebean.LoginBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.LoginUser;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.MD5Encoder;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.TimeCountUtil;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/19.
 * 修改密码界面
 */
public class ChangePwdActivity extends BaseActivity implements View.OnClickListener{
    protected MakerApplication appContext = null;
    private TextView tv_psw_login;
    private EditText phoneNum;
    private TextView tv_send;
    private EditText code_sms;
    private ImageView iv_clear;
    private TextView tv_login;
    private EditText tv_setpwd2;
    private EditText tv_setpwd;
    private boolean isChecked;//密码1是否可见
    private boolean isChecked2;//密码2是否可见

    private ImageView iv_seepwd1;
    private ImageView iv_seepwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd_activity);
        AppUtils.setTitle(ChangePwdActivity.this);
        appContext = (MakerApplication)getApplicationContext() ;
        initView();
    }

    private void initView() {
        findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("修改密码");
        tv_setpwd = (EditText) findViewById(R.id.tv_setpwd);
        tv_setpwd2 = (EditText) findViewById(R.id.tv_setpwd2);
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
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_send.setOnClickListener(this);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        iv_seepwd1 = (ImageView) findViewById(R.id.iv_seepwd1);
        iv_seepwd1.setOnClickListener(this);
        iv_seepwd2 = (ImageView) findViewById(R.id.iv_seepwd2);
        iv_seepwd2.setOnClickListener(this);
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
        tv_setpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s1 = charSequence.toString();
                if (!TextUtils.isEmpty(s1)) {
                    iv_seepwd1.setVisibility(View.VISIBLE);
                } else {
                    iv_seepwd1.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        tv_setpwd2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s1 = charSequence.toString();
                if (!TextUtils.isEmpty(s1)) {
                    iv_seepwd2.setVisibility(View.VISIBLE);
                } else {
                    iv_seepwd2.setVisibility(View.GONE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login.setText("确认修改");
        tv_login.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent = null ;
        switch(view.getId()){
            case R.id.iv_seepwd1:
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    tv_setpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    tv_setpwd.setSelection(tv_setpwd.getText().toString().length());
                    iv_seepwd1.setImageResource(R.drawable.codevisible);
                    isChecked = false;
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    tv_setpwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    tv_setpwd.setSelection(tv_setpwd.getText().toString().length());
                    iv_seepwd1.setImageResource(R.drawable.invisible_icon);
                    isChecked = true;
                }
                break;
            case R.id.iv_seepwd2:
                if (isChecked2) {
                    //选择状态 显示明文--设置为可见的密码
                    tv_setpwd2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    tv_setpwd2.setSelection(tv_setpwd2.getText().toString().length());
                    iv_seepwd1.setImageResource(R.drawable.codevisible);
                    isChecked2 = false;
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    tv_setpwd2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    tv_setpwd2.setSelection(tv_setpwd2.getText().toString().length());
                    iv_seepwd1.setImageResource(R.drawable.invisible_icon);
                    isChecked2 = true;
                }
                break;
            case R.id.tv_send://发送验证码
                sendCode();
                break ;
            case R.id.iv_clear://文本清除
                code_sms.setText("");
                break ;
            case R.id.tv_login://登录
                initData();
                break ;
            default:
                break;

        }
    }

    private void initData() {
        if (!SystemHelper.isConnected(ChangePwdActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String phone = phoneNum.getText().toString();
        String code = code_sms.getText().toString();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            ToastUtils.showToast(ChangePwdActivity.this,"手机号或验证码不能为空");
            return ;
        }
        final String password = tv_setpwd.getText().toString() ;
        final String password2 = tv_setpwd2.getText().toString() ;
        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)){
            ToastUtils.showToast(ChangePwdActivity.this,"请输入密码");
            return ;
        }else if(!password.equals(password2)){
            ToastUtils.showToast(ChangePwdActivity.this,"两次输入密码不相同");
            return ;
        }else if( 6 > password.length() || password.length() > 20){
            ToastUtils.showToast(ChangePwdActivity.this,"密码格式不正确，6-20位");
            return ;
        }
        final LoginUser user = null ;
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                ChangePwdActivity.this, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    UIUtils.showToast(result.getMsg());
//                } else{
//                    UIUtils.showToast(result.getMsg());
//                }
//            }
//        } );
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("mobile",phone);
//        params.put("code",code);
//        params.put("password",password);
//        params.put("method", NetUrlUtils.wo_forgetpwd);
//        tasker.execute(params);

        try {
            OkHttpUtils.post()
                    .url(URLS.USER_UPDATE)
                    .addParams("id",MakerApplication.instance.getUid())
                    .addParams("phone",phone)
                    .addParams("verifyCode",code)
                    .addParams("token", MakerApplication.instance.getToken())
                    .addParams("password", MD5Encoder.encode(password))
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
                                        UIUtils.showToast(bean.getMessage());
                                        MakerApplication.instance.setPassWord("0");
                                        finish();
                                    }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                        UIUtils.showToast(bean.getMessage());
                                        startActivity(LoginActivity.getIntent(ChangePwdActivity.this));
                                        ChangePwdActivity.this.finish();
                                    }else {
                                        UIUtils.showToast(bean.getMessage());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendCode() {
        if (!SystemHelper.isConnected(ChangePwdActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String mobile = phoneNum.getText().toString() ;
        if(TextUtils.isEmpty(mobile)){
            ToastUtils.showToast(ChangePwdActivity.this,"手机号不能为空");
            return;
        }
        if(!AppUtils.isMobileNO(mobile)){
            ToastUtils.showToast(ChangePwdActivity.this,"请输入正确格式的手机号");
            return ;
        }
        TimeCountUtil timeCountUtil = new TimeCountUtil(ChangePwdActivity.this, 60000, 1000,
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
                                LoginBean bean = gson.fromJson(response, LoginBean.class);
                                if (bean.getCode().equals("1000")) {
                                    UIUtils.showToast(bean.getMessage());

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(ChangePwdActivity.this));
                                    ChangePwdActivity.this.finish();
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
