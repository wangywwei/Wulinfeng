package com.hxwl.wulinfeng.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hxwl.wulinfeng.MakerApplication;

/**
 * Created by Administrator on 2017/5/22.
 */

public abstract class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        MakerApplication.activity=this;
        initView();
        if (savedInstanceState==null){
            initData();
        }
        initListener();
    }

    public abstract int getLayoutResID();

    public abstract void initView();

    public abstract void initData();

    /**
     * 初始化监听：点击监听、设置适配器、设置条目点击监听
     */
    public abstract void initListener();

    /**
     * 提示语言
     */
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
