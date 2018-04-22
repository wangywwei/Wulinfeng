package com.hxwl.wlf3.home.home2.chongfu;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.home.home1.ShiPin1Adapter;
import com.hxwl.wulinfeng.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public class Saicheng1Layout extends LinearLayout {

    private Context context;
    private View view;
    private DynamicBean.DataBean dataBean;

    ArrayList<DynamicBean.DataBean.EventBean.AgainstListBean> arrayList = new ArrayList();

    private ImageView saicheng_bofang;
    private LinearLayout saicheng_ditu;
    private ImageView saicheng_img;
    private TextView saicheng_position;
    private ImageView saicheng_yuyuetupian;
    private TextView saicheng_name;
    private XRecyclerView saicheng_xrecycler;
//    private Home3SaiCheng1Adapter beiyongadapter;
    private RelativeLayout saicheng_relative;
    private HuoDong1Layout huoDongLayout;
    private RelativeLayout saicheng_img_layout;
    private ShiPin2Adapter shiPin1Adapter;

    public void setBean(final DynamicBean.DataBean bean) {
        this.dataBean = bean;



        try {
            Glide.with(context).load(URLS.IMG + dataBean.getEvent().getCoverImage()).into(saicheng_img);
            saicheng_name.setText(dataBean.getEvent().getTitle());
            saicheng_position.setText(dataBean.getEvent().getEventAddress());
        } catch (Exception e) {
        }

        try {
            int state = dataBean.getEvent().getTimeState();
            if (state == 1) {
                int hasSubscribed = dataBean.getEvent().getHasSubscribed();
                if (hasSubscribed == 0) {
                    saicheng_yuyuetupian.setImageResource(R.drawable.yuyue3);//预约
                } else {
                    saicheng_yuyuetupian.setImageResource(R.drawable.yiyuyue3);//已预约
                }
            } else if (state == 2) {
                saicheng_yuyuetupian.setImageResource(R.drawable.zhibozhong3);//进行中--直播中
            } else if (state == 3) {
                saicheng_yuyuetupian.setImageResource(R.drawable.quanchenghuigu1);//结束--全程回顾
            }
        } catch (Exception e) {
        }


        try {

            int state = dataBean.getEvent().getTimeState();

            if (state == 3) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                saicheng_xrecycler.setLayoutManager(layoutManager);
                saicheng_xrecycler.setNestedScrollingEnabled(false);
                try {
//                    arrayList.add(dataBean.getEvent().getAgainstList());


                    shiPin1Adapter = new ShiPin2Adapter(context,arrayList);

//                    beiyongadapter = new Home3SaiCheng1Adapter(context, arrayList);
                    saicheng_xrecycler.setAdapter(shiPin1Adapter);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }

/*
* 活动
* */

        try {

            List<DynamicBean.DataBean.ActivityListBean> activityList = dataBean.getActivityList();
            if (null == activityList || activityList.size() == 0) {
                //为空的情况
                return;
            } else {
                saicheng_relative.removeAllViews();//清空布局
                huoDongLayout = new HuoDong1Layout(context);
                saicheng_relative.addView(huoDongLayout);
                huoDongLayout.setBean(dataBean.getActivityList().get(0));
            }
        } catch (Exception e) {

        }


        try {
            saicheng_img_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "播放图片", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
        }


        try {
            saicheng_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "" + dataBean.getEvent().getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
        }


        try {
            saicheng_ditu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了地图", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
        }


        try {
            saicheng_yuyuetupian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "预约", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}


    }

    public Saicheng1Layout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.caicheng_item, this);
        saicheng_bofang = (ImageView) view.findViewById(R.id.saicheng_bofang);
        saicheng_ditu = (LinearLayout) view.findViewById(R.id.saicheng_ditu);
        saicheng_img = (ImageView) view.findViewById(R.id.saicheng_img);
        saicheng_name = (TextView) view.findViewById(R.id.saicheng_name);
        saicheng_position = (TextView) view.findViewById(R.id.saicheng_position);
        saicheng_yuyuetupian = (ImageView) view.findViewById(R.id.saicheng_yuyuetupian);
        saicheng_xrecycler = (XRecyclerView) view.findViewById(R.id.saicheng_xrecycler);

        saicheng_img_layout = (RelativeLayout) view.findViewById(R.id.saicheng_img_layout);

    }
}
