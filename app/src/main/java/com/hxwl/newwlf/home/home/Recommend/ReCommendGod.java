package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.newwlf.home.home.intent.GodsActivity;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.TuJiDetailsActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.hxwl.wulinfeng.view.HoRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ReCommendGod extends LinearLayout implements View.OnClickListener {
    private Context context;
    private TextView god_loding;
    private HoRecyclerView god_recycler;
    private GodRecyclerAdapter adapter;
    private ArrayList<HomeRecommendBean.DataBean.GodListBean> list=new ArrayList<>();
    public ReCommendGod(Context context) {
        this(context, null);
    }

    private HomeRecommendBean.DataBean bean;

    public void setBean(HomeRecommendBean.DataBean bean) {
        this.bean = bean;
        list.clear();
        list.addAll(bean.getGodList());
        adapter.notifyDataSetChanged();

    }

    public ReCommendGod(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReCommendGod(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(final Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.recommen_god, this);
        god_loding= (TextView) view.findViewById(R.id.god_loding);
        god_recycler= (HoRecyclerView) view.findViewById(R.id.god_recycler);

        god_loding.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        god_recycler.setLayoutManager(linearLayoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(god_recycler);
        adapter=new GodRecyclerAdapter(context,list);
        god_recycler.setAdapter(adapter);


        adapter.setOnItemclickLinter(new GodRecyclerAdapter.OnItemclickLinter() {
            @Override
            public void onItemClicj(int position) {
                switch (list.get(position).getNewsType()){
                    case "1":
                        context.startActivity(ZiXunDetailsActivity.getIntent(context,list.get(position).getNewsId()+""));
                        break;
                    case "2":
                        context.startActivity(TuJiDetailsActivity.getIntent(context,list.get(position).getNewsId()+""));
                        break;
                    case "3":
                        context.startActivity(ZixunVideoActivity.getIntent(context,list.get(position).getNewsId()+""));
                        break;
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.god_loding:
                context.startActivity(GodsActivity.getIntent(context));
                break;

        }
    }
}
