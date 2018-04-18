package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/13.
 */

public class SaichengLayout extends LinearLayout {

    private Context context;
    private View view;
    private Home3Bean.DataBean.SchedulesBean dataBean;
    private ImageView saicheng_bofang;
    private LinearLayout saicheng_ditu;
    private ImageView saicheng_img;
    private TextView saicheng_position;
    private ImageView saicheng_yuyuetupian;
    private TextView saicheng_name;

    public void setBean(final Home3Bean.DataBean.SchedulesBean bean) {
        this.dataBean = bean;

        try {
            Glide.with(context).load(URLS.IMG+dataBean.getEvent().getCoverImage() ).into(saicheng_img);
            saicheng_name.setText(dataBean.getEvent().getTitle());
            saicheng_position.setText(dataBean.getEvent().getEventAddress());
        }catch (Exception e){
        }

        try {
            int state = dataBean.getState();
            if (state==1){
                int hasSubscribed = dataBean.getHasSubscribed();
                if (hasSubscribed==0){
                    saicheng_yuyuetupian.setImageResource(R.drawable.yuyue3);//预约
                }else{
                    saicheng_yuyuetupian.setImageResource(R.drawable.yiyuyue3);//已预约
                }
            }else if (state==2){
                saicheng_yuyuetupian.setImageResource(R.drawable.zhibozhong3);//进行中--直播中
            }else if (state==3){
                saicheng_yuyuetupian.setImageResource(R.drawable.quanchenghuigu1);//结束--全程回顾
            }
        }catch (Exception e){
        }
    }

    public SaichengLayout(Context context) {
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
    }
}

