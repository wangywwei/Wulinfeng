package com.hxwl.wlf3.home.home1;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.linearfenlei.SYHuoDongAdapter;
import com.hxwl.wulinfeng.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/22.
 */

public class ShiPin1Fragment extends LinearLayout {
    private Context context;
    private View view;
    private Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean dataBean;
    private XRecyclerView syhuodong_xrecycler;
    private ArrayList<Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean> arrayList = new ArrayList();
    private SYHuoDongAdapter beiyongadapter1;
    private ShiPin1Adapter shiPin1Adapter;


    public void setBean(final Home3Bean.DataBean.SchedulesBean.EventBean.againstListBean bean) {
        this.dataBean = bean;
        arrayList.clear();
        arrayList.add(dataBean);
        shiPin1Adapter.notifyDataSetChanged();

        for (int i = 0; i < arrayList.size(); i++) {
            String winner = arrayList.get(i).getWinner();
            Toast.makeText(context, "这是名字"+winner, Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(context, arrayList.get(0).getWinner(), Toast.LENGTH_SHORT).show();

//        beiyongadapter1.notifyDataSetChanged();
    }

    public ShiPin1Fragment(Context context) {
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

            shiPin1Adapter = new ShiPin1Adapter(context,arrayList);
            syhuodong_xrecycler.setAdapter(beiyongadapter1);
        }catch (Exception e){
        }

    }

}
