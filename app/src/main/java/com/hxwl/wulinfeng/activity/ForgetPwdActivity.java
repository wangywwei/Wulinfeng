package com.hxwl.wulinfeng.activity;

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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.LoginUser;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.TimeCountUtil;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.hxwl.wulinfeng.R.id.common_layout;

/**
 * Created by Allen on 2017/6/19.
 * 忘记密码界面
 */
public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{
    protected MakerApplication appContext = null;
    private TextView tv_psw_login;
    private EditText phoneNum;
    private TextView tv_send;
    private EditText code_sms;
    private ImageView iv_clear;
    private TextView tv_login;
    private EditText et_newpwd2;
    private EditText et_newpwd;
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
        AppUtils.setTitle(ForgetPwdActivity.this);
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
        tv_title.setText("找回密码");
        tv_setpwd = (EditText) findViewById(R.id.tv_setpwd);
        tv_setpwd2 = (EditText) findViewById(R.id.tv_setpwd2);
        iv_seepwd1 = (ImageView) findViewById(R.id.iv_seepwd1);
        iv_seepwd1.setOnClickListener(this);
        iv_seepwd2 = (ImageView) findViewById(R.id.iv_seepwd2);
        iv_seepwd2.setOnClickListener(this);
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
        tv_login.setText("确认");
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
        if (!SystemHelper.isConnected(ForgetPwdActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String phone = phoneNum.getText().toString();
        String code = code_sms.getText().toString();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            ToastUtils.showToast(ForgetPwdActivity.this,"手机号或验证码不能为空");
            return ;
        }
        final String password = tv_setpwd.getText().toString() ;
        final String password2 = tv_setpwd2.getText().toString() ;
        if(TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)){
            ToastUtils.showToast(ForgetPwdActivity.this,"请输入密码");
            return ;
        }else if(!password.equals(password2)){
            ToastUtils.showToast(ForgetPwdActivity.this,"两次输入密码不相同");
            return ;
        }else if( 6 > password.length() || password.length() > 20){
            ToastUtils.showToast(ForgetPwdActivity.this,"密码格式不正确，6-20位");
            return ;
        }
        final LoginUser user = null ;
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                ForgetPwdActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    finish();
                } else{
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile",phone);
        params.put("code",code);
        params.put("password",password);
        params.put("method",NetUrlUtils.wo_forgetpwd);
        tasker.execute(params);

//        try {
//            Map<String, Object> map = new HashMap<>();
//            map.put("mobile",phone);
//            map.put("code",code);
//            map.put("password",password);
//            AppClient.okhttp_post_Asyn(NetUrlUtils.wo_forgetpwd, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    ForgetPwdActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            UIUtils.showToast("服务器异常");
//                        }
//                    });
//                }
//                @Override
//                public void success(final ResultPacket result) {
//                    if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                        /**
//                         * 登录
//                         */
//                        finish();
////                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
////                        intent.putExtra("fragId",6);
////                        startActivity(intent);
//                        ForgetPwdActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                UIUtils.showToast(result.getMsg());
//                            }
//                        });
//                    } else{
//                        ForgetPwdActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                UIUtils.showToast(result.getMsg());
//                            }
//                        });
//                    }
//                }
//            });
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

    private void sendCode() {
        if (!SystemHelper.isConnected(ForgetPwdActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String mobile = phoneNum.getText().toString() ;
        if(TextUtils.isEmpty(mobile)){
            ToastUtils.showToast(ForgetPwdActivity.this,"手机号不能为空");
            return;
        }
        if(!AppUtils.isMobileNO(mobile)){
            ToastUtils.showToast(ForgetPwdActivity.this,"请输入正确格式的手机号");
            return ;
        }
        TimeCountUtil timeCountUtil = new TimeCountUtil(ForgetPwdActivity.this, 60000, 1000,
                tv_send);
        timeCountUtil.start();
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                ForgetPwdActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    UIUtils.showToast(result.getMsg());
                } else{
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("biaoshi",appContext.WLF_Flag);
        params.put("mobile",mobile);
        params.put("type", "3");
        params.put("device","2");
        params.put("method", NetUrlUtils.wo_getverificationcode);
        tasker.execute(params);

//        try {
//            Map<String, Object> map = new HashMap<>();
//            map.put("biaoshi",appContext.WLF_Flag);
//            map.put("mobile",mobile);
//            map.put("type", "1");
//            map.put("device","2");
//            AppClient.okhttp_post_Asyn(NetUrlUtils.wo_getverificationcode, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    ForgetPwdActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            UIUtils.showToast("服务器异常");
//                        }
//                    });
//                }
//                @Override
//                public void success(final ResultPacket result) {
//                    if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                        ForgetPwdActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
//                    }else{
//                        ForgetPwdActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                UIUtils.showToast(result.getMsg());
//                            }
//                        });
//                    }
//                }
//            });
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }
}
