package com.hxwl.newwlf.sousuo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;

public class SousuoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        AppUtils.setTitle(this);



    }
}
