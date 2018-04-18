package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.LoginUser;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.TimeCountUtil;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 2017/6/19.
 * 注册界面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    protected MakerApplication appContext = null;
    private TextView tv_psw_login;
    private EditText phoneNum;
    private TextView tv_send;
    private EditText code_sms;
    private ImageView iv_clear;
    private ImageView iv_chexkbox;
    private TextView tv_detail;
    private TextView tv_text;
    private TextView tv_login;
    private EditText tv_setpwd2;
    private EditText tv_setpwd;

    private boolean isChecked;//密码1是否可见
    private boolean isChecked2;//密码2是否可见
    private boolean isCanReg = true; //是否可以注册

    private boolean ischeck = false;// 标记是否同意了法律文件
    private ImageView iv_seepwd1;
    private ImageView iv_seepwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        AppUtils.setTitle(RegisterActivity.this);
        appContext = (MakerApplication) getApplicationContext();
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

        phoneNum = (EditText) findViewById(R.id.phoneNum);
        phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString();
                if (!TextUtils.isEmpty(s1)) {
                    tv_send.setVisibility(View.VISIBLE);
                } else {
                    tv_send.setVisibility(View.GONE);
                }
                if (s1.length() == 11) {
                    if (!AppUtils.isMobileNO(s1)) {
                        ToastUtils.showToast(RegisterActivity.this, "请输入正确格式的手机号");
                        return;
                    }
                    checkIsReg(s1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        iv_seepwd1 = (ImageView) findViewById(R.id.iv_seepwd1);
        iv_seepwd1.setOnClickListener(this);
        iv_seepwd2 = (ImageView) findViewById(R.id.iv_seepwd2);
        iv_seepwd2.setOnClickListener(this);

        iv_chexkbox = (ImageView) findViewById(R.id.iv_chexkbox);
        iv_chexkbox.setOnClickListener(this);

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
                String s = charSequence.toString();
                if (!TextUtils.isEmpty(s)) {
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }


        });
        tv_detail = (TextView) findViewById(R.id.tv_detail);
        tv_detail.setOnClickListener(this);
        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_text.setOnClickListener(this);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
    }

    //检测是否已经注册过了这个手机号
    private void checkIsReg(String phone) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                RegisterActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if ("1".equals(result.getMsg())) {//已占用
                        ToastUtils.showToast(RegisterActivity.this, "手机号已经注册过");
                        isCanReg = false;
                    } else if ("0".equals(result.getMsg())) {
                        ToastUtils.showToast(RegisterActivity.this, "手机号可以注册");
                        isCanReg = true;
                    } else {

                    }
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("biaoshi", appContext.WLF_Flag);
        map.put("mobile", phone);
        map.put("method", NetUrlUtils.wo_cheakisreg);
        tasker.execute(map);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
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
            case R.id.iv_chexkbox://是否同意了协议
                /**
                 * 同意法律文件
                 */
                if (ischeck) {
                    iv_chexkbox.setImageResource(R.drawable.unchecked);
                    ischeck = false;
                } else {
                    iv_chexkbox.setImageResource(R.drawable.selected);
                    ischeck = true;
                }
                break;
            case R.id.tv_psw_login://登录
                intent = new Intent(RegisterActivity.this, LoginforPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_send://发送验证码
                sendCode();
                break;
            case R.id.iv_clear://文本清除
                code_sms.setText("");
                break;
            case R.id.tv_login://登录
                StatService.onEvent(this,"register","注册",1);
                TCAgent.onEvent(this,"register","注册");
                initData();
                break;
            case R.id.tv_text://详情
            case R.id.tv_detail://详情
                intent = new Intent(RegisterActivity.this, AgreementActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                break;
            default:
                break;

        }
    }

    private void initData() {
        if (!SystemHelper.isConnected(RegisterActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String phone = phoneNum.getText().toString();
        String code = code_sms.getText().toString();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            ToastUtils.showToast(RegisterActivity.this, "手机号或验证码不能为空");
            return;
        }
        final String password = tv_setpwd.getText().toString();
        final String password2 = tv_setpwd2.getText().toString();
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            ToastUtils.showToast(RegisterActivity.this, "请输入密码");
            return;
        } else if (!password.equals(password2)) {
            ToastUtils.showToast(RegisterActivity.this, "两次输入密码不相同");
            return;
        } else if (6 > password.length() || password.length() > 20) {
            ToastUtils.showToast(RegisterActivity.this, "密码格式不正确，6-20位");
            return;
        }
        final LoginUser user = null;
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                RegisterActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                        /**
                         * 保存登陆者信息
                         */
                    LoginUser user = JSON.parseObject(result.getData(), LoginUser.class);
                    appContext.saveLoginInfo(user, RegisterActivity.this);
                    appContext.setLoginState(RegisterActivity.this, MakerApplication.instance().LOGIN);
                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    UIUtils.showToast(result.getMsg());
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("mobile", phone);
        map.put("code", code);
        map.put("password", password);
        map.put("device", 2);
        map.put("qudao", Constants.getChannelName(RegisterActivity.this));
        map.put("deviceVersion", android.os.Build.MODEL + "," + android.os.Build.VERSION.RELEASE + ",wulinfeng" + AppUtils.getVersionCode(this));
        map.put("method", NetUrlUtils.wo_register);
        tasker.execute(map);

//        try {
//            Map<String, Object> map = new HashMap<>();
//            map.put("mobile", phone);
//            map.put("code", code);
//            map.put("password", password);
//            map.put("device", 2);
//            map.put("deviceVersion", android.os.Build.MODEL + "," + android.os.Build.VERSION.RELEASE + ",wulinfeng" + AppUtils.getVersionCode(this));
//            AppClient.okhttp_post_Asyn(NetUrlUtils.wo_register, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    RegisterActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            UIUtils.showToast("服务器异常");
//                        }
//                    });
//                }
//
//                @Override
//                public void success(final ResultPacket result) {
//                    if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                        /**
//                         * 保存登陆者信息
//                         */
//                        LoginUser user = JSON.parseObject(result.getData(), LoginUser.class);
//                        appContext.saveLoginInfo(user, RegisterActivity.this);
//                        appContext.setLoginState(RegisterActivity.this, MakerApplication.instance().LOGIN);
//                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        finish();
////                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
////                        intent.putExtra("fragId",6);
////                        startActivity(intent);
//                        RegisterActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                UIUtils.showToast(result.getMsg());
//                            }
//                        });
//                    } else {
//                        RegisterActivity.this.runOnUiThread(new Runnable() {
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
        if (!SystemHelper.isConnected(RegisterActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String mobile = phoneNum.getText().toString();
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.showToast(RegisterActivity.this, "手机号不能为空");
            return;
        }
        if (!AppUtils.isMobileNO(mobile)) {
            ToastUtils.showToast(RegisterActivity.this, "请输入正确格式的手机号");
            return;
        }
        if (!isCanReg) {
            return;
        }
        TimeCountUtil timeCountUtil = new TimeCountUtil(RegisterActivity.this, 60000, 1000,
                tv_send);
        timeCountUtil.start();
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                RegisterActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {

                }else if(result != null && result.getStatus().equals("empty")){

                }else{
                    UIUtils.showToast(result.getMsg());
                }
            }
        } );
        HashMap<String, Object> map= new HashMap<String, Object>();
        map.put("biaoshi", appContext.WLF_Flag);
        map.put("mobile", mobile);
        map.put("type", "1");
        map.put("device", "2");
        map.put("method", NetUrlUtils.wo_getverificationcode);
        tasker.execute(map);

//        try {
//            Map<String, Object> map = new HashMap<>();
//            map.put("biaoshi", appContext.WLF_Flag);
//            map.put("mobile", mobile);
//            map.put("type", "1");
//            map.put("device", "2");
//            AppClient.okhttp_post_Asyn(NetUrlUtils.wo_getverificationcode, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    RegisterActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            UIUtils.showToast("服务器异常");
//                        }
//                    });
//                }
//
//                @Override
//                public void success(final ResultPacket result) {
//                    if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                        RegisterActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
//                    } else {
//                        RegisterActivity.this.runOnUiThread(new Runnable() {
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
