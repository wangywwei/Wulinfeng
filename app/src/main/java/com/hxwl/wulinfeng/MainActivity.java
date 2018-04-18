package com.hxwl.wulinfeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hxwl.common.addialog.AdManager;
import com.hxwl.wulinfeng.activity.HomeActivity;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.tbruyelle.rxpermissions.RxPermissions;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
        setContentView(R.layout.activity_main);
        setSwipeBackEnable(true); //设置本页是不是能左滑返回
    }
    //真正的沉浸式状态栏
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && Build.VERSION.SDK_INT >= 19){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public void initView() {
        ImageView but_img = (ImageView)findViewById(R.id.but_img);
    }

    public void jump(View v){
        Intent intent = new Intent(MainActivity.this , HomeActivity.class) ;
        startActivity(intent);
    }
}
