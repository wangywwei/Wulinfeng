package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;

public class ShouCangActivity extends BaseActivity implements View.OnClickListener {
    private ImageView collection_icon;
    //    onCreate

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_cang);
initview();


    }

    private void initview() {


        collection_icon = (ImageView) findViewById(R.id.collection_icon);

        collection_icon.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent intent = null ;
        switch(v.getId()){
            case R.id.collection_icon:

                finish();

                break;



        }


    }
}
