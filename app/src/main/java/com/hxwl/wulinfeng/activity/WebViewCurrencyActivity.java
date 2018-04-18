package com.hxwl.wulinfeng.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;

/**
 * Created by Allen on 2017/7/10.
 * H5通用界面
 */
public class WebViewCurrencyActivity extends BaseActivity {

    private String url;
    private WebView webView;
    private ImageView user_icon;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_activity);
        AppUtils.setTitle(WebViewCurrencyActivity.this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        if(TextUtils.isEmpty(title)){
            tv_title.setText("武林风");
        }else{
            tv_title.setText(title);
        }
        webView = (WebView) findViewById(R.id.webview);
        user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initWebData(url);
    }

    private void initWebData(String html) {
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(html);

    }
}
