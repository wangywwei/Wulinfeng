package com.hxwl.wlf3.home.remenhot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

public class GenDuoActivity extends BaseActivity {

    private GenduoAdapter genduoAdapter;

    public static Intent getIntent(Context context){
        Intent intent=new Intent(context,GenDuoActivity.class);
        return intent;
    }

    private ImageView back;
    private XRecyclerView genduo_recyclerview;
    private ArrayList<String> datalist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_duo);
        AppUtils.setTitle(this);
        initView();


    }


    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        genduo_recyclerview = (XRecyclerView) findViewById(R.id.genduo_recyclerview);
        genduoAdapter = new GenduoAdapter(this,datalist);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        genduo_recyclerview.setLayoutManager(layoutManager);
        genduo_recyclerview.setNestedScrollingEnabled(false);
        datalist.add("1");
        datalist.add("1");
        genduo_recyclerview.setAdapter(genduoAdapter);

        genduo_recyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                genduo_recyclerview.refreshComplete();//刷新完成
            }

            @Override
            public void onLoadMore() {
                genduo_recyclerview.refreshComplete();//刷新完成
            }
        });
    }
}
