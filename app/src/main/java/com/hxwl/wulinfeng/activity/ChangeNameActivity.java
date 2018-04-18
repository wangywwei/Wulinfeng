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
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.LoginBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/20.
 * 改名
 */
public class ChangeNameActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_clear;
    private EditText et_name;
    private TextView tv_save;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changename_activity);
        AppUtils.setTitle(ChangeNameActivity.this);
        name = getIntent().getStringExtra("name");
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
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_name.setText(name);
        et_name.setSelection(TextUtils.isEmpty(name) ? 0 : name.length());
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String s = charSequence.toString() ;
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
        tv_save = (TextView) findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.iv_clear:
                et_name.setText("");
                break ;
            case R.id.tv_save:
                saveName();
                break ;
            default:
                break ;
        }
    }

    private void saveName() {
        if (!SystemHelper.isConnected(ChangeNameActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        final String name = et_name.getText().toString() ;
        if(TextUtils.isEmpty(name)){
            ToastUtils.showToast(ChangeNameActivity.this,"昵称不能为空");
            return;
        }
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                ChangeNameActivity.this, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    UIUtils.showToast(result.getMsg());
//                    MakerApplication.instance().setUserName(name,ChangeNameActivity.this);
//                    Intent intent = new Intent();
//                    intent.putExtra("username",name) ;
//                    setResult(100 ,intent);
//                    finish();
//                } else{
//                    UIUtils.showToast(result.getMsg());
//                }
//            }
//        } );
//        HashMap<String, Object> params = new HashMap<String, Object>();
//        params.put("uid", MakerApplication.instance().getUid());
//        params.put("loginKey",  MakerApplication.instance().getLoginKey());
//        params.put("nickname", name);
//        params.put("method",NetUrlUtils.wo_changename);
//        tasker.execute(params);

        OkHttpUtils.post()
                .url(URLS.USER_UPDATE)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("id",MakerApplication.instance.getUid())
                .addParams("nickName",name)
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
                                    MakerApplication.instance().setUserName(name,ChangeNameActivity.this);
                                    Intent intent = new Intent();
                                    intent.putExtra("username",name) ;
                                    setResult(100 ,intent);
                                    finish();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(ChangeNameActivity.this));
                                    ChangeNameActivity.this.finish();
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
