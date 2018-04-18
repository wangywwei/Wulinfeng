package com.hxwl.common.adfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;

import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


/**
 * 功能:不加载广告的webview
 * 作者：zjn on 2017/2/4 09:59
 */

public class NoAdWebViewClient extends WebViewClient {
    private String homeurl;
    private Context context;

    RelativeLayout mProgressBar;

    public NoAdWebViewClient(Context context, String homeurl,View view) {
        this.context = context;
        this.homeurl = homeurl;
        this.mProgressBar = (RelativeLayout) view;
    }
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        url = url.toLowerCase();
        if(!url.contains(homeurl)){
            if (!ADFilterTool.hasAd(context, url)) {
                return super.shouldInterceptRequest(view, url);
            }else{
                return new WebResourceResponse(null,null,null);
            }
        }else{
            return super.shouldInterceptRequest(view, url);
        }


    }

    // url拦截
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    // 页面开始加载
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    // 页面加载完成
    @Override
    public void onPageFinished(WebView view, String url) {
        mProgressBar.setVisibility(View.GONE);
    }

    // WebView加载的所有资源url
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }
}
