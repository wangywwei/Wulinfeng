package com.hxwl.wlf3.home.home2;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.linearfenlei.SYHuoDongAdapter;
import com.hxwl.wulinfeng.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/20.
 */

public class HuoDong1Layout extends LinearLayout {
    private Context context;
    private View view;
    private DynamicBean.DataBean.ActivityListBean dataBean;
    private XRecyclerView syhuodong_xrecycler;
    private ArrayList<DynamicBean.DataBean.ActivityListBean> arrayList = new ArrayList();
    private SYHuoDong1Adapter beiyongadapter1;


    public void setBean(final DynamicBean.DataBean.ActivityListBean bean) {
        this.dataBean = bean;
        arrayList.clear();
        arrayList.add(dataBean);
        beiyongadapter1.notifyDataSetChanged();
    }

    public HuoDong1Layout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.syhuodong_item, this);
        syhuodong_xrecycler = (XRecyclerView) view.findViewById(R.id.syhuodong_xrecycler);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        syhuodong_xrecycler.setLayoutManager(layoutManager);
        syhuodong_xrecycler.setNestedScrollingEnabled(false);
        try {
            //对阵的适配器
            beiyongadapter1 = new SYHuoDong1Adapter(context, arrayList);
            syhuodong_xrecycler.setAdapter(beiyongadapter1);
        }catch (Exception e){
        }

    }

}
