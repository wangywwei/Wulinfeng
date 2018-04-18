package com.hxwl.wlf3.home.saicheng;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
/*
* 首页详情页
* */
public class SCDetailsActivity extends BaseActivity {

    private ImageView scdetails_icon;
    private TextView scdetails_zhinan;
    private TextView scdetails_pinglun;
    private TextView scdetails_dongtai;
    private ViewPager scdetails_frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scdetails);

        initView();

    }

    private void initView() {
        scdetails_icon = (ImageView) findViewById(R.id.scdetails_icon);
        scdetails_zhinan = (TextView) findViewById(R.id.scdetails_zhinan);
        scdetails_dongtai = (TextView) findViewById(R.id.scdetails_dongtai);
        scdetails_pinglun = (TextView) findViewById(R.id.scdetails_pinglun);
        scdetails_frame = (ViewPager) findViewById(R.id.scdetails_frame);

    }
}
