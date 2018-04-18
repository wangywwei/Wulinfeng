package com.hxwl.wulinfeng.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mobstat.StatService;
import com.google.gson.JsonArray;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.tendcloud.tenddata.TCAgent;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import static android.R.attr.type;

/**
 * Created by Allen on 2017/7/12.
 */
public class ChangJianQuestActivity extends BaseActivity{

    private WebView webView;
    private String title;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changjianquest);
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        AppUtils.setTitle(ChangJianQuestActivity.this);
        initView();
        initData();
    }

    private void initData() {//sys_changjianquest
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                ChangJianQuestActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    String contents = ""
;                    try {
                        org.json.JSONObject json = new org.json.JSONObject(result.getData());
                        contents = json.getString("contents");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                        initWebData(contents);
                }else if(result != null && result.getStatus().equals("empty")){

                }else{

                }
            }
        },"method="+NetUrlUtils.sys_changjianquest+"id"+id);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("method", NetUrlUtils.sys_changjianquest);
        tasker.execute(params);
    }

    private void initView() {
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
//        tv_title.setText(title);
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView = (WebView) findViewById(R.id.webview);
    }

    private void initWebData(String html) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        if (StringUtils.isBlank(title)){
            webView.loadUrl(title);
        }
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "常见问题详情");
        TCAgent.onPageStart(this, "常见问题详情");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"常见问题详情");
        TCAgent.onPageEnd(this,"常见问题详情");
    }
}
