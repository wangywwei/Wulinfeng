package com.hxwl.wulinfeng.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.OnClickEventUtils;
import com.hxwl.wulinfeng.util.TopProgressWebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 功能:正常的webview载体界面
 *
 * @author zjn
 */
public class NormalWebviewActivity extends BaseActivity{

    private TopProgressWebView webVi_message_details;
    private String title;

    private String strUrl;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.rlyt_title)
    RelativeLayout rlyt_title;
    @Bind(R.id.title_back)
    RelativeLayout title_back;
    //是否显示title
    private boolean isShowTitle = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_webview);
        AppUtils.setTitle(NormalWebviewActivity.this);
        ButterKnife.bind(this);
        webVi_message_details = (TopProgressWebView) findViewById(R.id.webVi_message_details);

        strUrl = getIntent().getStringExtra("url");
//        strUrl = "http://live.wasu.cn/show/id/17659";
        title = getIntent().getStringExtra("title");
        isShowTitle = getIntent().getBooleanExtra("isShowTitle",true);
        if(isShowTitle){
            rlyt_title.setVisibility(View.VISIBLE);
            tv_title.setText(title+"");
        }else{
            rlyt_title.setVisibility(View.GONE);
        }
        webVi_message_details.loadUrl(strUrl);
    }

    @OnClick({R.id.title_back})
    public void onClick(View view){
        if(OnClickEventUtils.isFastClick()){
            return;
        }
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webVi_message_details != null){
            webVi_message_details.onStop();
        }
    }
}
