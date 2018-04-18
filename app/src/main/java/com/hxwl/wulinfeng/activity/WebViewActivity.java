package com.hxwl.wulinfeng.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * WebView
 * 建议缓存策略为，判断是否有网络
 * 有网络:使用LOAD_DEFAULT，
 * 无网络时:使用LOAD_CACHE_ELSE_NETWORK。
 * /data/data/com.fmpt.runner/files/webcache
 * @author Qch
 */
public class WebViewActivity extends Activity {

    @Bind(R.id.online_webview)
    WebView onlineWebview;
    @Bind(R.id.title)
    TextView tvtitle;
    @Bind(R.id.title_back)
    RelativeLayout title_back;
    protected static final String TAG = "WebViewActivity";
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
//        String title_txt = intent.getStringExtra("title_txt");
        tvtitle.setText(title);
        WebSettings webSet  = onlineWebview.getSettings();
        webSet.setJavaScriptEnabled(true);//支持js
        webSet.setDefaultTextEncodingName("UTF-8");//设置字符编码


        /****缓存******/


//        webSet.setDomStorageEnabled(true);
//        //开启 database storage API 功能
//        webSet.setDatabaseEnabled(true);
//        String cacheDirPath = getFilesDir().getAbsolutePath()+APP_CACAHE_DIRNAME;
//        Log.i(TAG, "cacheDirPath=" + cacheDirPath);
//        //设置数据库缓存路径
//        webSet.setDatabasePath(cacheDirPath);
//        //设置  Application Caches 缓存目录
//        webSet.setAppCachePath(cacheDirPath);
//        //开启 Application Caches 功能
//        webSet.setAppCacheEnabled(true);

//        onlineWebview.setScrollBarStyle(0);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上

        onlineWebview.setWebViewClient(new XWebViewClient());
        if (url != null) {
            onlineWebview.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        }

        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    final class XWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG,"onPageStarted");
            super.onPageStarted(view, url, favicon);
        }
        public void onPageFinished(WebView view, String url) {
            Log.d(TAG,"onPageFinished ");
            super.onPageFinished(view, url);
        }
    }
    final class XWebChromeClient extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
            if (progress == 100) {// 如果全部载入,隐藏进度对话框
            }
            super.onProgressChanged(view, progress);
        }
    }


}
