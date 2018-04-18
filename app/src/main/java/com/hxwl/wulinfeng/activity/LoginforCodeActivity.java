package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.LoginUser;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
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

import java.util.HashMap;

/**
 * Created by Allen on 2017/6/19.
 * 登陆界面--验证码登陆
 */
public class LoginforCodeActivity extends BaseActivity implements View.OnClickListener {
    protected MakerApplication appContext = null;
    private TextView tv_psw_login;
    private EditText phoneNum;
    private TextView tv_send;
    private EditText code_sms;
    private ImageView iv_clear;
    private TextView tv_text;
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        AppUtils.setTitle(LoginforCodeActivity.this);
        appContext = (MakerApplication)getApplicationContext() ;
        initView();
    }

    private void initData() {
        if (!SystemHelper.isConnected(LoginforCodeActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String phone = phoneNum.getText().toString();
        String code = code_sms.getText().toString();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            ToastUtils.showToast(LoginforCodeActivity.this,"手机号或验证码不能为空");
            return ;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                LoginforCodeActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                        /**
                         * 保存登陆者信息
                         */
                    LoginUser user = JSON.parseObject(result.getData(),LoginUser.class) ;
                    appContext.saveLoginInfo(user,LoginforCodeActivity.this);
                    appContext.setLoginState(LoginforCodeActivity.this ,MakerApplication.instance.LOGIN);
                    if("1".equals(user.getPassword())){
                        finish();
//                        Intent intent = new Intent(LoginforCodeActivity.this,SetPwdActivity.class) ;
////                        Intent intent = new Intent(LoginforCodeActivity.this,SetPwdActivity.class) ;
//                        startActivity(intent);
                    }else{
                        finish();
                    }
                    UIUtils.showToast("登录成功");
                }else if(result != null && result.getStatus().equals("empty")){

                }else{
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile",phone);
        params.put("code",code);
        params.put("device",2);
        params.put("qudao", Constants.getChannelName(LoginforCodeActivity.this));
        params.put("deviceVersion",android.os.Build.MODEL+","+android.os.Build.VERSION.RELEASE+",wulinfeng"+ AppUtils.getVersionCode(this));
        params.put("method", NetUrlUtils.wo_codeLogin);
        tasker.execute(params);
    }

    private void initView() {
        findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
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

        tv_text = (TextView) findViewById(R.id.tv_text);
        SpannableString spanableInfo = new SpannableString("温馨提示：未注册武林风账号的手机号，登录时将自动注册，且代表您已同意"+""+"《注册协议》");
        spanableInfo.setSpan(new Clickable(clickListener),35,39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_text.setText(spanableInfo);
        tv_text.setMovementMethod(LinkMovementMethod.getInstance());
//        tv_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        tv_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);

        tv_psw_login = (TextView) findViewById(R.id.tv_psw_login);
        tv_psw_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginforCodeActivity.this,LoginforPwdActivity.class);
                startActivity(intent);
            }
        });
    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginforCodeActivity.this,AgreementActivity.class);
            startActivity(intent);
        }
    };

    class Clickable extends ClickableSpan {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }

        /**
         * 重写父类点击事件
         */
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        /**
         * 重写父类updateDrawState方法  我们可以给TextView设置字体颜色,背景颜色等等...
         */
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.black));
            ds.setUnderlineText(true);//是否有下划线
        }
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
            case R.id.tv_login://登录
                StatService.onEvent(this,"login","登录",1);
                TCAgent.onEvent(this,"login","登录");
                initData();
                break ;
             default:
                 break;

        }
    }
    private void sendCode() {
        if (!SystemHelper.isConnected(LoginforCodeActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String mobile = phoneNum.getText().toString() ;
        if(TextUtils.isEmpty(mobile)){
            ToastUtils.showToast(LoginforCodeActivity.this,"手机号不能为空");
            return;
        }
        if(!AppUtils.isMobileNO(mobile)){
            ToastUtils.showToast(LoginforCodeActivity.this,"请输入正确格式的手机号");
            return ;
        }
        TimeCountUtil timeCountUtil = new TimeCountUtil(LoginforCodeActivity.this, 60000, 1000,
                tv_send);
        timeCountUtil.start();

        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                LoginforCodeActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    UIUtils.showToast(result.getMsg());
                }else if(result != null && result.getStatus().equals("empty")){
                    UIUtils.showToast(result.getMsg());
                }else{
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> map= new HashMap<String, Object>();
        map.put("biaoshi",appContext.WLF_Flag);
        map.put("mobile",mobile);
        map.put("type", "1");
        map.put("device","2");
        map.put("method", NetUrlUtils.wo_getverificationcode);
        tasker.execute(map);

    }
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "验证码登录");
        TCAgent.onPageStart(this, "验证码登录");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"验证码登录");
        TCAgent.onPageEnd(this,"验证码登录");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this) ;
    }
}
