package com.hxwl.newwlf.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.common.utils.ShowDialog2;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.LoginBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HomeActivity;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.MD5Encoder;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/*
*
* 密码登录
*
* */
public class MimaLoginActivity extends AppCompatActivity {

    protected MakerApplication appContext = null;
    private EditText phoneNum;
    private EditText et_pwd;
    private ImageView iv_seepwd;
    private TextView tv_text;
    private TextView tv_login;
    private LinearLayout activity_img_layout3;
    private Object event;
    private boolean isChecked;//密码是否可见
    private boolean ischeck = true;// 标记是否同意了法律文件
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mima_login);
        appContext = (MakerApplication) this.getApplicationContext();
        initView();
        getevent();
    }


    private void initView() {
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        iv_seepwd = (ImageView) findViewById(R.id.iv_seepwd);
        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_login = (TextView) findViewById(R.id.tv_login);
        activity_img_layout3 = (LinearLayout) findViewById(R.id.activity_img_layout3);
    }


    public void getevent() {
        //显示或隐藏密码
        iv_seepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });



        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatatrue();
            }
        });




        activity_img_layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!SystemHelper.isConnected(MimaLoginActivity.this)) {
                    UIUtils.showToast("请检查网络");
                    return;
                }
                ShowDialog2.showProgressDialog(MimaLoginActivity.this,"","",true);
                if (!MakerApplication.api.isWXAppInstalled()) {
                    UIUtils.showToast("您还未安装微信客户端");
                    ShowDialog2.dismissProgressDialog();
                }else {
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    MakerApplication.api.sendReq(req);
                }


            }
        });



    }



    private boolean isCanReg = true; //是否可以注册
    private void initDatatrue() {
        if (!SystemHelper.isConnected(this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String phone = phoneNum.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)) {
            ToastUtils.showToast(this, "手机号或密码不能为空");
            return;
        }

        if (!Photos.isMobile(phone)){
            ToastUtils.showToast(this, "您输入的手机号不正确");
            return;
        }

        ShowDialog2.showProgressDialog(this,"","",true);
        OkHttpUtils.post()
                .url(URLS.USER_ISEXIST)
                .addParams("phone",phone)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ShowDialog2.dismissProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {
                                    isCanReg = true ;
                                    initData();
                                }else {
                                    UIUtils.showToast("手机号还未注册");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ShowDialog2.dismissProgressDialog();
                    }
                });
    }

    private void initData() {
        if (!SystemHelper.isConnected(this)) {
            UIUtils.showToast("请检查网络");
            ShowDialog2.dismissProgressDialog();
            return;
        }
        if(!ischeck){
            ShowDialog2.dismissProgressDialog();
            UIUtils.showToast("请同意协议");
        }
        String phone = phoneNum.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)) {
            ToastUtils.showToast(this, "手机号或密码不能为空");
            ShowDialog2.dismissProgressDialog();
            return;
        }

        if (pwd.length()<6){
            UIUtils.showToast("密码长度不能小于六位");
            return;
        }

        try {
            OkHttpUtils.post()
                    .url(URLS.USER_LOGIN)
                    .addParams("phone",phone)
                    .addParams("password", MD5Encoder.encode(pwd))
                    .addParams("deviceType","1")
                    .addParams("pushToken", MakerApplication.instance.getPushToken())
                    .addParams("currVersion",LoginActivity.getVersion(this)+"")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            UIUtils.showToast("服务器异常");
                            ShowDialog2.dismissProgressDialog();

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            JsonValidator jsonValidator = new JsonValidator();
                            if (jsonValidator.validate(response)){
                                Gson gson = new Gson();
                                try {
                                    LoginBean bean = gson.fromJson(response, LoginBean.class);
                                    if (bean.getCode().equals("1000")){
                                        ShowDialog2.dismissProgressDialog();
                                        appContext.saveLogin(bean.getData(),MimaLoginActivity.this);
                                        appContext.setLoginState(MimaLoginActivity.this ,MakerApplication.instance().LOGIN);
                                        appContext.setPassWord("0");
                                        //跳转到主页
                                        Intent intent = new Intent(MimaLoginActivity.this,HomeActivity.class) ;
                                        startActivity(intent);
                                        finish();
                                        UIUtils.showToast("登录成功");

                                    }else{
                                        ToastUtils.showToast(MimaLoginActivity.this,bean.getMessage());
                                    }

                                }catch (Exception e){
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
