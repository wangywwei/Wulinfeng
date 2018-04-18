package com.hxwl.wulinfeng.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;

/**
 * Created by Allen on 2017/7/10.
 * 售票界面 AND H5通用界面
 */
public class TicketActivity extends BaseActivity {

    private String url;
    private WebView webView;
    private ImageView user_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_activity);
        AppUtils.setTitle(TicketActivity.this);
        url = getIntent().getStringExtra("url");
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

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//        });
    }
}
