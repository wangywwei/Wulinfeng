package com.hxwl.wulinfeng.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.GuanyuBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/19.
 * 协议界面
 */
public class AgreementActivity extends BaseActivity{

    private WebView webView;
    private int type;//条款通用界面 0 默认注册协议 2条款协议 1 隐私政策

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agreement_activity);
        AppUtils.setTitle(AgreementActivity.this);
        type = getIntent().getIntExtra("type",0);
        initView();
        if(1 == type){
            initData(NetUrlUtils.shiyong_xieyi);
        }else if(2 == type){
            initData(NetUrlUtils.yinsi_zhengce);
        }else{
            initData(NetUrlUtils.zhuce_xieyi);
        }
    }
    private void initData(String method) {
        OkHttpUtils.post()
                .url(URLS.STATICSOURCE_GET)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("id", type+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                GuanyuBean bean = gson.fromJson(response, GuanyuBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if(!TextUtils.isEmpty(bean.getData().getContent())){
                                        initWebData(bean.getData().getContent());
                                    }

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(AgreementActivity.this));
                                    AgreementActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


    }

    private void initWebData(String html) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        //条款通用界面 0 默认注册协议 2条款协议 1 隐私政策
        if(2 == type){
            tv_title.setText("条款协议");
        }else if(1 == type){
            tv_title.setText("隐私政策");
        }else{
            tv_title.setText("注册协议");
        }
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView = (WebView) findViewById(R.id.webview);

    }
    @Override
    public void onResume() {
        super.onResume();
        if(1 == type){
            StatService.onPageStart(this, "使用条款");
            TCAgent.onPageStart(this, "使用条款");
        }else if(2 == type){
            StatService.onPageStart(this, "使用条款");
            TCAgent.onPageStart(this, "使用条款");
        }else{
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(1 == type){
            StatService.onPageEnd(this,"隐私政策");
        }else if(2 == type){
            StatService.onPageEnd(this,"隐私政策");
        }else{
        }
    }
}
