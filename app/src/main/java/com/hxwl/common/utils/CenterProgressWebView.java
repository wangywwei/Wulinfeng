package com.hxwl.common.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hxwl.common.adfilter.NoAdWebViewClient;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.hxwl.wulinfeng.R;

/**
 * 功能:顶部带有进度条的自定义webview
 */
public class CenterProgressWebView extends LinearLayout {

	WebView mWebView;

	RelativeLayout mProgressBar;

	private String url;
	private Context mContext;


	public CenterProgressWebView(Context context) {
		this(context, null);
	}


	public CenterProgressWebView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CenterProgressWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initView(context);
	}

	private void initView(Context context) {
		View.inflate(context, R.layout.view_web_progress, this);
		mWebView = (WebView)findViewById(R.id.web_view);
		mProgressBar = (RelativeLayout) findViewById(R.id.progress_bar);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void loadUrl(String url) {
		if(url == null) {
			url = "https://www.heixiongboji.com/";
		}

		initWebview(url);
	}

	public void invalidate(){
		mWebView.reload();
		mWebView.invalidate();
	}


	private void initWebview(String url) {
//		mWebView.removeAllViews();
//		mWebView.addJavascriptInterface(this, "android");

		WebSettings webSettings = mWebView.getSettings();
		// 设置默认缩放方式尺寸是far
//		webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		// 设置出现缩放工具
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDefaultFontSize(16);
		webSettings.setAppCacheEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setUseWideViewPort(true);//关键点

		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

		webSettings.setDisplayZoomControls(false);
		webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
		webSettings.setAllowFileAccess(true); // 允许访问文件
		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
		webSettings.setSupportZoom(true); // 支持缩放



		webSettings.setLoadWithOverviewMode(true);

//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
		// 开启 DOM storage API 功能
		webSettings.setDomStorageEnabled(true);
		if(mWebView.getX5WebViewExtension() != null) {
			Bundle data = new Bundle();
			data.putBoolean("standardFullScreen", false);
			//true表示标准全屏，false表示X5全屏；不设置默认false，
			data.putBoolean("supportLiteWnd", false);
			//false：关闭小窗；true：开启小窗；不设置默认true，
//			data.putInt("DefaultVideoScreen", 2);
			//1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
			mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
		}
		mWebView.setWebViewClient(new NoAdWebViewClient(mContext,url,mProgressBar));
		// 设置WebViewClient
//		mWebView.setWebViewClient(new WebViewClient() {
//			@Override
//			public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
////				sslErrorHandler.proceed();
//			}
//
//			// url拦截
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//				return true;
//			}
//
//			// 页面开始加载
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				mProgressBar.setVisibility(View.VISIBLE);
//			}
//
//			// 页面加载完成
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				mProgressBar.setVisibility(View.GONE);
//			}
//
//			// WebView加载的所有资源url
//			@Override
//			public void onLoadResource(WebView view, String url) {
//				super.onLoadResource(view, url);
//			}
//
//			@Override
//			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//				super.onReceivedError(view, errorCode, description, failingUrl);
//			}
//
//
//		});

		// 设置WebChromeClient
		mWebView.setWebChromeClient(new WebChromeClient() {
			// 设置网页加载的进度条
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
//				mProgressBar.setProgress(newProgress);
				super.onProgressChanged(view, newProgress);
			}

			// 设置程序的Title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}
		});
		mWebView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) { // 表示按返回键
						mWebView.goBack(); // 后退
						return true; // 已处理
					}
				}
				return false;
			}
		});
		mWebView.loadUrl(url);
	}
//
//	public void addJavascriptInterface(Object object, String name) {
//		mWebView.addJavascriptInterface(object,name);
//	}

	/**
	 * Gets whether this WebView has a back history item.
	 *
	 * @return true iff this WebView has a back history item
	 */
	public boolean canGoBack() {
		return mWebView.canGoBack();
	}

	/**
	 * Goes back in the history of this WebView.
	 */
	public void goBack() {
		mWebView.goBack();
	}

	//停止播放
	public void onStop(){
		mWebView.onPause();
		mWebView.stopLoading(); // 暂停网页中正在播放的视频
		mWebView.clearHistory();
		mWebView.destroy();
	}

	public void onPause(){
		mWebView.onPause();
	}

	public void onResume(){
		mWebView.onResume();
	}
}
