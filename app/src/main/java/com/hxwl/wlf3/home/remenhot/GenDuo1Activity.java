package com.hxwl.wlf3.home.remenhot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.hxwl.wulinfeng.R;

public class GenDuo1Activity extends AppCompatActivity {
    private ImageView gengduo_fanhui;
    private TextView gengduo_processing;
    private TextView gengduo_end;
    private XRecyclerView gengduo_xrecycler1;
    private XRecyclerView gengduo_xrecycler2;

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, GenDuo1Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_duo1);
        initView();
          getevent();

        gengduo_xrecycler1.setVisibility(View.VISIBLE);
        gengduo_xrecycler2.setVisibility(View.GONE);



        getdata();



    }

    private void initView() {
        gengduo_fanhui = (ImageView) findViewById(R.id.gengduo_fanhui);
        gengduo_processing = (TextView) findViewById(R.id.gengduo_processing);
        gengduo_end = (TextView) findViewById(R.id.gengduo_end);
        gengduo_xrecycler1 = (XRecyclerView) findViewById(R.id.gengduo_xrecycler1);
        gengduo_xrecycler2 = (XRecyclerView) findViewById(R.id.gengduo_xrecycler2);
    }



    public void getevent() {
        gengduo_fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gengduo_processing.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                gengduo_xrecycler1.setVisibility(View.VISIBLE);
                gengduo_xrecycler2.setVisibility(View.GONE);

                gengduo_processing.setBackgroundResource(Color.parseColor("#FFF"));//Color.parseColor("#FF6600")
                gengduo_end.setBackgroundResource(R.drawable.coxrners_bg2);
            }
        });

        gengduo_end.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                gengduo_xrecycler2.setVisibility(View.VISIBLE);
                gengduo_xrecycler1.setVisibility(View.GONE);

                gengduo_end.setBackgroundResource(Color.parseColor("#FFF"));//Color.parseColor("#FF6600")
                gengduo_processing.setBackgroundResource(R.drawable.coxrners_bg2);

            }
        });

    }

    public void getdata() {







    }




}
