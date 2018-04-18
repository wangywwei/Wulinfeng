package com.hxwl.wulinfeng.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.LoginBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.LoginUser;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/19.
 * 密码登陆
 */
public class LoginforPwdActivity extends BaseActivity implements View.OnClickListener {
    protected MakerApplication appContext = null;
    private EditText phoneNum;
    private EditText et_pwd;
    private ImageView iv_seepwd;
    private TextView tv_text;
    private TextView tv_login;
    private TextView tv_regiest;
    private TextView tv_forgetpwd;
    private LinearLayout weixinglog;

    private boolean isChecked;//密码是否可见
    private TextView yanzhengmalog;
    private boolean ischeck = true;// 标记是否同意了法律文件


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_pwd_activity);
        AppUtils.setTitle(LoginforPwdActivity.this);
        appContext = (MakerApplication) getApplicationContext();
        initView();
    }

    private void initData() {
        if (!SystemHelper.isConnected(LoginforPwdActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        if(!ischeck){
            UIUtils.showToast("请同意协议");
        }
        String phone = phoneNum.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)) {
            ToastUtils.showToast(LoginforPwdActivity.this, "手机号或密码不能为空");
            return;
        }
        final LoginUser user = null;
        OkHttpUtils.post()
                .url(URLS.LOGIN)
                .addParams("phone",phone)
                .addParams("password",pwd)
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
                                LoginBean bean = gson.fromJson(response, LoginBean.class);
                                if (bean.getCode().equals("1000")){
                                    appContext.saveLogin(bean.getData(),LoginforPwdActivity.this);
                                    //跳转到主页
                                    Intent intent = new Intent(LoginforPwdActivity.this,HomeActivity.class) ;
                                    startActivity(intent);
                                    finish();
                                    UIUtils.showToast("登录成功");

                                }else if (bean.getCode().equals("1001")){
                                    ToastUtils.showToast(LoginforPwdActivity.this,bean.getMessage());
                                }else if (bean.getCode().equals("1001")){
                                    ToastUtils.showToast(LoginforPwdActivity.this,bean.getMessage());
                                }else if (bean.getCode().equals("1002")){
                                    ToastUtils.showToast(LoginforPwdActivity.this,bean.getMessage());
                                }else if (bean.getCode().equals("1003")){
                                    ToastUtils.showToast(LoginforPwdActivity.this,bean.getMessage());
                                }else if (bean.getCode().equals("1004")){
                                    ToastUtils.showToast(LoginforPwdActivity.this,bean.getMessage());
                                }else if (bean.getCode().equals("2000")){
                                    ToastUtils.showToast(LoginforPwdActivity.this,bean.getMessage());
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }



                    }
                });


    }

    @SuppressLint("WrongViewCast")
    private void initView() {
        findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        iv_seepwd = (ImageView) findViewById(R.id.iv_seepwd);
        iv_seepwd.setOnClickListener(this);
        yanzhengmalog= (TextView) findViewById(R.id.yanzhengmalog);


        yanzhengmalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();
                if (!TextUtils.isEmpty(s)) {
                    iv_seepwd.setVisibility(View.VISIBLE);
                } else {
                    iv_seepwd.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_text.setOnClickListener(this);
        tv_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);

        weixinglog = (LinearLayout) findViewById(R.id.weixinglog);
        weixinglog.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_seepwd:
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    et_pwd.setSelection(et_pwd.getText().toString().length());
                    iv_seepwd.setImageResource(R.drawable.codevisible);
                    isChecked = false;
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_pwd.setSelection(et_pwd.getText().toString().length());
                    iv_seepwd.setImageResource(R.drawable.invisible_icon);
                    isChecked = true;
                }
                break;

            case R.id.tv_login://登录
                initData();
                break;
//            case R.id.tv_regiest://注册
//                intent = new Intent(LoginforPwdActivity.this, RegisterActivity.class);
//                startActivity(intent);
//                break;
//            case R.id.tv_forgetpwd://忘记密码
//                intent = new Intent(LoginforPwdActivity.this, ForgetPwdActivity.class);
//                startActivity(intent);
//                break;
            case R.id.tv_text://查看协议
                intent = new Intent(LoginforPwdActivity.this, AgreementActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}
