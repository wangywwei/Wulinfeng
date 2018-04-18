package com.hxwl.newwlf.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
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
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.MD5Encoder;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/2/7.
 */
/*
* 密码登录
* */
public class MimaLoginFragment extends BaseFragment implements View.OnClickListener {
    protected MakerApplication appContext = null;
    private View view;
    private EditText phoneNum;
    private EditText et_pwd;
    private ImageView iv_seepwd;
    private TextView tv_text;
    private TextView tv_login;
    private TextView tv_regiest;
    private TextView tv_forgetpwd;


    private boolean isChecked;//密码是否可见
    private TextView yanzhengmalog;
    private boolean ischeck = true;// 标记是否同意了法律文件
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.login_pwd_activity, null);
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
        if(!ischeck){
            ShowDialog2.dismissProgressDialog();
            UIUtils.showToast("请同意协议");
        }
        String phone = phoneNum.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)) {
            ToastUtils.showToast(getActivity(), "手机号或密码不能为空");
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
                    .addParams("pushToken",MakerApplication.instance.getPushToken())
                    .addParams("currVersion",LoginActivity.getVersion(getActivity())+"")
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
                                        appContext.saveLogin(bean.getData(),getActivity());
                                        appContext.setLoginState(getActivity() ,MakerApplication.instance().LOGIN);
                                        appContext.setPassWord("0");
                                        //跳转到主页
                                        Intent intent = new Intent(getActivity(),HomeActivity.class) ;
                                        startActivity(intent);
                                        getActivity().finish();
                                        UIUtils.showToast("登录成功");

                                    }else{
                                        ToastUtils.showToast(getActivity(),bean.getMessage());
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



    private void initView(View view) {
        phoneNum = (EditText) view.findViewById(R.id.phoneNum);
        et_pwd = (EditText) view.findViewById(R.id.et_pwd);
        iv_seepwd = (ImageView) view.findViewById(R.id.iv_seepwd);
        iv_seepwd.setOnClickListener(this);
        yanzhengmalog= (TextView) view.findViewById(R.id.yanzhengmalog);

//        et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
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

        tv_text = (TextView) view.findViewById(R.id.tv_text);

        SpannableString spanableInfo = new SpannableString("温馨提示：未注册武林风账号的手机号，登录时将自动注册，且代表您已同意"+""+"《注册协议》");
        spanableInfo.setSpan(new Clickable(clickListener),35,39, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_text.setText(spanableInfo);
        tv_text.setMovementMethod(LinkMovementMethod.getInstance());

        tv_text.setOnClickListener(this);

        tv_login = (TextView) view.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);

    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),AgreementActivity.class);
            intent.putExtra("type",3);
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
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
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

                initDatatrue();

                break;


        }
    }

    private boolean isCanReg = true; //是否可以注册
    private void initDatatrue() {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String phone = phoneNum.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)) {
            ToastUtils.showToast(getActivity(), "手机号或密码不能为空");
            return;
        }

        if (!Photos.isMobile(phone)){
            ToastUtils.showToast(getActivity(), "您输入的手机号不正确");
            return;
        }

        ShowDialog2.showProgressDialog(getActivity(),"","",true);
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


}
