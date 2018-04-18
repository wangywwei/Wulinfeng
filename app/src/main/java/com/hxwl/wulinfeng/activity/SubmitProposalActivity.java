package com.hxwl.wulinfeng.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/20.
 * 提交建议界面
 */
public class SubmitProposalActivity extends BaseActivity {

    private EditText et_text;
    private EditText et_lianxi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitjianyi_activity);
        AppUtils.setTitle(SubmitProposalActivity.this);
        initView();
    }

    private void initData() {
        if (!SystemHelper.isConnected(SubmitProposalActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        String text = et_text.getText().toString();
        String listxi = et_lianxi.getText().toString();
        if (TextUtils.isEmpty(text)) {
            UIUtils.showToast("请填写您的建议");
            return;
        } else if (TextUtils.isEmpty(listxi)) {
            UIUtils.showToast("请填写您的联系方式");
            return;
        }
        if (!Photos.isMobile(listxi)){
            UIUtils.showToast("请填写您正确的联系方式");
            return;
        }

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_ADDFEEDBACK)
                .addParams("content", text)
                .addParams("contact", listxi)
                .addParams("userId", MakerApplication.instance.getUid())
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
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")) {
                                    et_text.setText("");
                                    et_lianxi.setText("");
                                    UIUtils.showToast(bean.getMessage());
                                    finish();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(SubmitProposalActivity.this));
                                    SubmitProposalActivity.this.finish();
                                }else{
                                    UIUtils.showToast(bean.getMessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        if(user_icon != null && user_icon instanceof ImageView){
            findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        et_text = (EditText) findViewById(R.id.et_text);
        et_lianxi = (EditText) findViewById(R.id.et_lianxi);

        TextView tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "反馈建议");
        TCAgent.onPageStart(this, "反馈建议");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"反馈建议");
        TCAgent.onPageEnd(this,"反馈建议");
    }
}
