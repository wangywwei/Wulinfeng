package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home2.chongfu.HuoDong1Layout;
import com.hxwl.wulinfeng.R;

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

private HuoDong1Layout  huoDongLayout1;

    private DynamicBean.DataBean dataxqBean;



/*    public void setxqBean(final DynamicBean.DataBean bean) {
        this.dataxqBean = bean;

        try {
            chun_name.setText(dataxqBean.getEvent().getTitle());
        }catch (Exception e){
        }

        try {
            chun_content.setText(dataxqBean.getEvent().getIntro());

        }catch (Exception e){
        }

        try {
            chun_position.setText(dataxqBean.getEvent().getEventAddress());
        }catch (Exception e){
        }

*//*
* 活动
* *//*

        try {

//            private Home3Bean.DataBean.SchedulesBean dataBean;

            List<DynamicBean.DataBean.ActivityListBean> activityList = dataxqBean.getActivityList();
            if(null == activityList || activityList.size() ==0 ){
                //为空的情况
                return;
            }else{
                chun_relative.removeAllViews();//清空布局
                huoDongLayout1 = new HuoDong1Layout(context);
                chun_relative.addView(huoDongLayout);
                huoDongLayout.setBean(dataBean);
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
    }*/



    public void setBean(final Home3Bean.DataBean.SchedulesBean bean) {
        this.dataBean = bean;

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
            List<Home3Bean.DataBean.SchedulesBean.ActivityListBean> activityList = dataBean.getActivityList();
            if(null == activityList || activityList.size() ==0 ){
                //为空的情况
                return;
            }else{
                chun_relative.removeAllViews();//清空布局
                for (int i = 0; i < activityList.size(); i++) {
                    huoDongLayout = new HuoDongLayout(context);
                    chun_relative.addView(huoDongLayout);
                    huoDongLayout.setBean(activityList.get(i));
                }



            }
        }catch (Exception e){
        }



        try {
            chun_ditu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "点击了地图", Toast.LENGTH_SHORT).show();
                    getPopupWindow();
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

    public void getPopupWindow() {

        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(getContext(), R.layout.camera_pop_menu, null);
        Button btnCamera = (Button) popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnAlbum = (Button) popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels/2;
        final PopupWindow popWindow = new PopupWindow(popView,width,height, true);

        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置同意在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_camera_pop_camera:


                        if (AMapUtil.isInstallByRead("com.autonavi.minimap")){
                            AMapUtil.goToNaviActivity(getContext(),"test",null,"34.264642646862","108.95108518068","1","2");
                        }else {
                            Toast.makeText(getContext(), "请先安装高德地图", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case R.id.btn_camera_pop_album:
                        Toast.makeText(getContext(), "请先安装腾讯地图", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_camera_pop_cancel:
//                        Toast.makeText(getContext(), "点击了3", Toast.LENGTH_SHORT).show();
                        break;
                }
                popWindow.dismiss();
            }
        };

        btnCamera.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
