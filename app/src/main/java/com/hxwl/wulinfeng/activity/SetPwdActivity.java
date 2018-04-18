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
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/19.
 * 设置密码界面
 */
public class SetPwdActivity extends BaseActivity implements View.OnClickListener{
    protected MakerApplication appContext = null;
    private TextView tv_login;
    private EditText tv_setpwd2;
    private EditText tv_setpwd;
    private boolean isChecked;//密码1是否可见
    private boolean isChecked2;//密码2是否可见

    private ImageView iv_seepwd1;
    private ImageView iv_seepwd2;
    private TextView tv_pass;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpwd_activity);
        AppUtils.setTitle(SetPwdActivity.this);
        appContext = (MakerApplication)getApplicationContext() ;
        type = getIntent().getIntExtra("type" ,0);
        initView();
    }

    private void initView() {
        findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_setpwd = (EditText) findViewById(R.id.tv_setpwd);
        tv_setpwd2 = (EditText) findViewById(R.id.tv_setpwd2);
        iv_seepwd1 = (ImageView) findViewById(R.id.iv_seepwd1);
        iv_seepwd1.setOnClickListener(this);
        iv_seepwd2 = (ImageView) findViewById(R.id.iv_seepwd2);
        iv_seepwd2.setOnClickListener(this);
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
        tv_login.setOnClickListener(this);
        tv_pass = (TextView) findViewById(R.id.tv_pass);
        tv_pass.setOnClickListener(this);
        if(1 == type){
            tv_pass.setVisibility(View.GONE);
        }else{
            tv_pass.setVisibility(View.VISIBLE);
        }

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
            case R.id.tv_login://确定
                initData();
                break ;
            case R.id.tv_pass://跳过
                finish();
                break ;
            default:
                break;

        }
    }

    private void initData() {
        if (!SystemHelper.isConnected(SetPwdActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        final String password = tv_setpwd.getText().toString() ;
        final String password2 = tv_setpwd2.getText().toString() ;
        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)){
            ToastUtils.showToast(SetPwdActivity.this,"请输入密码");
            return ;
        }else if(!password.equals(password2)){
            ToastUtils.showToast(SetPwdActivity.this,"两次输入密码不相同");
            return ;
        }else if( 6 > password.length() || password.length() > 20){
            ToastUtils.showToast(SetPwdActivity.this,"密码格式不正确，6-20位");
            return ;
        }
        final LoginUser user = null ;
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                SetPwdActivity.this, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    UIUtils.showToast(result.getMsg());
//                    MakerApplication.instance().setPassWord("0");
//                    finish();
//                } else{
//                    UIUtils.showToast(result.getMsg());
//                }
//            }
//        });
//        HashMap<String, Object> map= new HashMap<String, Object>();
//        map.put("uid", MakerApplication.instance().getUid());
//        map.put("loginKey", MakerApplication.instance().getLoginKey());
//        map.put("password",password);
//        map.put("method", NetUrlUtils.wo_setpwd);
//        tasker.execute(map);

        try {
            OkHttpUtils.post()
                    .url(URLS.USER_UPDATE)
                    .addParams("token", MakerApplication.instance.getToken())
                    .addParams("id",MakerApplication.instance.getUid())
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
                                        MakerApplication.instance().setPassWord("0");
                                        finish();
                                    }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                        UIUtils.showToast(bean.getMessage());
                                        startActivity(LoginActivity.getIntent(SetPwdActivity.this));
                                        SetPwdActivity.this.finish();
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
}
