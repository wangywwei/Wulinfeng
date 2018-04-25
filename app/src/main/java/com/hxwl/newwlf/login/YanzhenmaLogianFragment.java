package com.hxwl.newwlf.login;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.utils.ShowDialog2;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.LoginBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.AgreementActivity;
import com.hxwl.wulinfeng.activity.HomeActivity;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.TimeCountUtil;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/2/7.
 */
/*
* 验证码登录
* */
public class YanzhenmaLogianFragment extends BaseFragment implements View.OnClickListener {
    protected MakerApplication appContext = null;
    private View view;
    private TextView tv_psw_login;
    private EditText phoneNum;
    private TextView tv_send;
    private EditText code_sms;
    private ImageView iv_clear;
    private TextView tv_text;
    private TextView tv_login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.login_activity, null);
            appContext = (MakerApplication) getActivity().getApplicationContext();
            initView(view);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        return view;
    }

    private void initData() {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            ShowDialog2.dismissProgressDialog();
            return;
        }
        String phone = phoneNum.getText().toString();
        String code = code_sms.getText().toString();
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            ToastUtils.showToast(getActivity(),"手机号或验证码不能为空");
            ShowDialog2.dismissProgressDialog();
            return ;
        }
        if (!Photos.isMobile(phone)){
            ToastUtils.showToast(getActivity(), "您输入的手机号不正确");
            return;
        }

        ShowDialog2.showProgressDialog(getActivity(),"","",true);
        OkHttpUtils.post()
                .url(URLS.USER_LOGIN)
                .addParams("deviceType","1")
                .addParams("pushToken",MakerApplication.instance.getPushToken())
                .addParams("currVersion",LoginActivity.getVersion(getActivity())+"")
                .addParams("phone",phone)
                .addParams("verifyCode",code)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ShowDialog2.dismissProgressDialog();
                        UIUtils.showToast("请检查网络");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                LoginBean bean = gson.fromJson(response, LoginBean.class);
                                if (bean.getCode().equals("1000")){
                                    ShowDialog2.dismissProgressDialog();
                                    appContext.saveLogin(bean.getData(),getActivity());
                                    appContext.setLoginState(getActivity() ,MakerApplication.LOGIN);
                                    appContext.setPassWord("1");
                                    //跳转到主页
                                    Intent intent = new Intent(getActivity(),HomeActivity.class) ;
                                    startActivity(intent);
                                    UIUtils.showToast("登录成功");
                                    getActivity().finish();
                                }else{
                                    ToastUtils.showToast(getActivity(),bean.getMessage());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ShowDialog2.dismissProgressDialog();
                    }
                });

    }

    private void initView(View view) {
        phoneNum = (EditText) view.findViewById(R.id.phoneNum);
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
        tv_send = (TextView) view.findViewById(R.id.tv_send);
        tv_send.setOnClickListener(this);
        iv_clear = (ImageView) view.findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        code_sms = (EditText) view.findViewById(R.id.code_sms);
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

        tv_text = (TextView) view.findViewById(R.id.tv_text);
        SpannableString spanableInfo = new SpannableString("温馨提示：未注册武林风账号的手机号，登录时将自动注册，且代表您已同意"+""+"《注册协议》");
        spanableInfo.setSpan(new Clickable(clickListener),35,39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_text.setText(spanableInfo);
        tv_text.setMovementMethod(LinkMovementMethod.getInstance());

        tv_login = (TextView) view.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);

        tv_psw_login = (TextView) view.findViewById(R.id.tv_psw_login);

    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),AgreementActivity.class);
            intent.putExtra("type",3);
            startActivity(intent);
        }
    };

    static class Clickable extends ClickableSpan {
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
//            ds.setColor(getResources().getColor(R.color.black));//如果使用当前界面这行需要解开
            ds.setUnderlineText(true);//是否有下划线
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_send://发送验证码
                sendCode();
                break ;
            case R.id.iv_clear://文本清除
                code_sms.setText("");
                break ;
            case R.id.tv_login://登录

                StatService.onEvent(getActivity(),"login","登录",1);
                TCAgent.onEvent(getActivity(),"login","登录");
                initData();
                break ;
        }
    }

    private void sendCode() {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String mobile = phoneNum.getText().toString() ;
        if(TextUtils.isEmpty(mobile)){
            ToastUtils.showToast(getActivity(),"手机号不能为空");
            return;
        }
        if(!AppUtils.isMobileNO(mobile)){
            ToastUtils.showToast(getActivity(),"请输入正确格式的手机号");
            return ;
        }
        TimeCountUtil timeCountUtil = new TimeCountUtil(getActivity(), 60000, 1000,
                tv_send);

        timeCountUtil.start();
        tv_send.setBackground(getResources().getDrawable(R.drawable.send_gray));
        OkHttpUtils.post()
                .url(URLS.HOME_GETCODE)
                .addParams("phone",mobile)
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

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "验证码登录");
        TCAgent.onPageStart(getActivity(), "验证码登录");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(),"验证码登录");
        TCAgent.onPageEnd(getActivity(),"验证码登录");
    }

}
