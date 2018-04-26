package com.hxwl.wlf3.home.xiangqing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.wulinfeng.R;

public class RichtextWeb extends LinearLayout {

    private TextView web_title;
    private TextView web_time;
    private TextView web_action;
    private WebView web_web;

    private Context context;
    private Shijian3Bean.DataBean data;

    public void setData(final Shijian3Bean.DataBean data) {
        this.data = data;
        if (data!=null){
            web_title.setText(data.getTitle());
            web_time.setText(data.getEventTime());
            web_action.setText(data.getAuthor());
            WebSettings setTing = web_web.getSettings();
            if(setTing != null){
                setTing.setJavaScriptEnabled(true);
                setTing.setDefaultTextEncodingName("utf-8");
            }
//            web_web.loadUrl(URLS.HTML+data.getNewsUrl());
//            web_web.setWebChromeClient(new WebChromeClient());
//            web_web.setWebViewClient(new WebViewClient() {
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    if (!StringUtils.isBlank(data.getNewsUrl())){
//
//                    }
//                }
//            });

        }

    }

    public RichtextWeb(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        View richtext = LayoutInflater.from(context).inflate(R.layout.richtext, this);
        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        richtext.setTag(layoutParams);
        web_title= (TextView) richtext.findViewById(R.id.web_title);
        web_time= (TextView) richtext.findViewById(R.id.web_time);
        web_action= (TextView) richtext.findViewById(R.id.web_action);
        web_web= (WebView) richtext.findViewById(R.id.web_web);


    }


}
