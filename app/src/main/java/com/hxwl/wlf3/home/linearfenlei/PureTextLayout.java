package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.LiveAdapter;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 纯文本
 * Created by Administrator on 2018/4/16.
 */

public class PureTextLayout extends LinearLayout {
    private Context context;
    private View view;
    private Home3Bean.DataBean.SchedulesBean dataBean;

    private TextView chun_position;
    private TextView chun_name;
    private TextView chun_content;
    private LinearLayout chun_ditu1;
    private ImageView chun_position1;
    private RelativeLayout chun_relative;
    private HuoDongLayout huoDongLayout;
    private LinearLayout chun_ditu;

    public void setBean(final Home3Bean.DataBean.SchedulesBean bean) {
        this.dataBean = bean;

        try {
            dataBean.getEvent().getId();
        }catch (Exception e){
        }

        try {
            chun_name.setText(dataBean.getEvent().getTitle());
        }catch (Exception e){
        }

        try {
            chun_content.setText(dataBean.getEvent().getIntro());

        }catch (Exception e){
        }

        try {
            chun_position.setText(dataBean.getEvent().getEventAddress());
        }catch (Exception e){
        }

/*
* 活动
* */

        try {

//            private Home3Bean.DataBean.SchedulesBean dataBean;

            List<Home3Bean.DataBean.SchedulesBean.ActivityListBean> activityList = dataBean.getActivityList();
            if(null == activityList || activityList.size() ==0 ){
                //为空的情况
                return;
            }else{
                chun_relative.removeAllViews();//清空布局
                huoDongLayout = new HuoDongLayout(context);
                chun_relative.addView(huoDongLayout);
                huoDongLayout.setBean(dataBean.getActivityList().get(0));
            }
        }catch (Exception e){

        }



        try {
            chun_ditu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了地图", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}



        try {
            chun_name.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了文字", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}



        try {
            chun_content.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了文字", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}
    }

    public PureTextLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.chun_text_item, this);
        chun_name = (TextView) view.findViewById(R.id.chun_name);
        chun_content = (TextView) view.findViewById(R.id.chun_content);
        chun_ditu1 = (LinearLayout) view.findViewById(R.id.chun_ditu);
        chun_position = (TextView) view.findViewById(R.id.chun_position);
        chun_position1 = (ImageView) view.findViewById(R.id.chun_position1);

        chun_relative = (RelativeLayout) view.findViewById(R.id.chun_relative);

        chun_ditu = (LinearLayout) view.findViewById(R.id.chun_ditu);
    }
}
