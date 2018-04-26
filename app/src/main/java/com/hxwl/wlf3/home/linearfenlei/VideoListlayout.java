package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.ShiPin1Fragment;
import com.hxwl.wlf3.home.xiangqing.ShijianActivity;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.TuJiDetailsActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * *视频
 *
 * 图集
 *
 * 图片
 * Created by Administrator on 2018/4/16.
 */

public class VideoListlayout extends LinearLayout {

    private Context context;
    private View view;
    private Home3Bean.DataBean.SchedulesBean dataBean;
    private ImageView vll_img;
    private ImageView vll_bofang;
    private TextView vll_text;
    private TextView vll_name;
    private TextView vll_content;
    private ImageView cll_position1;
    private TextView vll_position;
    private TextView vll_bujidashuju;
    private HuoDongLayout huoDongLayout;
    private RelativeLayout video_relative,video_relative1;
    private RelativeLayout vll_img_layout;
    private LinearLayout cll_ditu;
    private ShiPin1Fragment shiPin1Fragment;
    private int showType1;
    private int inn;
    private LinearLayout cll_layout;

    public void setBean(final Home3Bean.DataBean.SchedulesBean bean, int in) {
        this.dataBean = bean;


        this.inn=in;




/*VIDEO(1,"视频"),RICH(2,"图文"),GATHER(3,"图集"),WORD(4,"纯文本"),SCHEDULE(5,"赛程"),AGAINST(6,"对阵"); */
        try {


            showType1 = dataBean.getEvent().getShowType();
            if (showType1 ==1){
                try {//视频
                    vll_bofang.setVisibility(View.VISIBLE);
                    vll_position.setVisibility(View.VISIBLE);
                    cll_position1.setVisibility(View.VISIBLE);


                    try {
                        Glide.with(context).load(URLS.IMG+dataBean.getEvent().getCoverImage() ).into(vll_img);
                    }catch (Exception e){
                    }

                    try {
                        vll_position.setText(dataBean.getEvent().getEventAddress());//地址
                    }catch (Exception e){
                    }

                    try {
                        vll_name.setText(dataBean.getEvent().getTitle());//名称
                    }catch (Exception e){
                    }

                    try {
                        vll_bujidashuju.setVisibility(View.VISIBLE);
                        vll_bujidashuju.setText(dataBean.getEvent().getAuthor());
                    }catch (Exception e){
                    }

                    try {
                        vll_content.setText(dataBean.getEvent().getIntro());
                    }catch (Exception e){
                    }

                }catch (Exception e){
                }
            }else if (showType1 ==2){
                try {//图文

                    cll_position1.setVisibility(View.VISIBLE);
                    try {
                        Glide.with(context).load(URLS.IMG+dataBean.getEvent().getCoverImage() ).into(vll_img);
                    }catch (Exception e){
                    }

                    try {
                        vll_position.setVisibility(View.VISIBLE);
                        vll_position.setText(dataBean.getEvent().getEventAddress());//地址
                    }catch (Exception e){
                    }

                    try {
                        vll_name.setText(dataBean.getEvent().getTitle());//名称
                    }catch (Exception e){
                    }

                    try {
                        vll_content.setText(dataBean.getEvent().getIntro());
                    }catch (Exception e){
                    }

                    try {
                        vll_content.setText(dataBean.getEvent().getIntro());
                    }catch (Exception e){
                    }
                }catch (Exception e){
                }
            }else if (showType1 ==3){
                try {//图文
                    vll_bujidashuju.setVisibility(View.VISIBLE);
                    try {
                        Glide.with(context).load(URLS.IMG+dataBean.getEvent().getCoverImage() ).into(vll_img);
                    }catch (Exception e){
                    }

                    try {
                        vll_position.setText(dataBean.getEvent().getEventAddress());//地址
                    }catch (Exception e){
                    }
                    try {
                        vll_name.setText(dataBean.getEvent().getTitle());//名称
                    }catch (Exception e){
                    }
                    try {
                        vll_content.setText(dataBean.getEvent().getIntro());//内容
                    }catch (Exception e){
                    }
                    try {
                        vll_text.setVisibility(View.VISIBLE);
                        vll_text.setText(dataBean.getEvent().getImageNumber());//imageNumber
                    }catch (Exception e){
                    }
                }catch (Exception e){
                }
            }
        }catch (Exception e){
        }

//        try {
//
//            int showType = dataBean.getEvent().getShowType();
//            int scheduleState = dataBean.getEvent().getScheduleState();
//            //showType=5并且scheduleState=3时显示
//            if (showType==5||scheduleState==3){
//                video_relative1.setVisibility(View.VISIBLE);
//                video_relative1.removeAllViews();
//                shiPin1Fragment = new ShiPin1Fragment(context);
//                video_relative1.addView(shiPin1Fragment);
//                shiPin1Fragment.setBean(dataBean.getEvent());
//            }
//
//
//        }catch (Exception e){
//        }

        // 活动
        try {
            List<Home3Bean.DataBean.SchedulesBean.ActivityListBean> activityList = dataBean.getActivityList();
            if(null == activityList || activityList.size() ==0 ){
                //为空的情况
                return;
            }else{

                activityList.clear();
                activityList.addAll( dataBean.getActivityList());
                video_relative.removeAllViews();//清空布局
//                for (int i = 0; i < activityList.size(); i++) {
                    huoDongLayout = new HuoDongLayout(context);
                    video_relative.addView(huoDongLayout);
                    huoDongLayout.setBean(dataBean,inn);

//                }
            }
        }catch (Exception e){
        }


/*
*
*               VIDEO(1,"视频"),RICH(2,"图文"),GATHER(3,"图集"),WORD(4,"纯文本"),SCHEDULE(5,"赛程"),AGAINST(6,"对阵");
* 点击事件
* */

try {
    cll_ditu.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "您暂时没有安装地图", Toast.LENGTH_SHORT).show();

        }
    });
}catch (Exception e){}


try {
    cll_layout.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            context.startActivity(ShijianActivity.getIntent(context,dataBean.getEvent().getId()+""));
        }
    });
}catch (Exception e){}

        
//        if (showType1==1){
//
//            try {
//                vll_img_layout.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.startActivity(ZixunVideoActivity.getIntent(context,dataBean.getEvent().getId()+""));
//                    }
//                });
//            }catch (Exception e){}
//
//
//
//            try {
//                vll_name.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.startActivity(ZixunVideoActivity.getIntent(context,dataBean.getEvent().getId()+""));
//                    }
//                });
//            }catch (Exception e){}
//
//
//
//            try {
//                vll_content.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.startActivity(ZixunVideoActivity.getIntent(context,dataBean.getEvent().getId()+""));
//                    }
//                });
//            }catch (Exception e){}
//
//
//            try {
//                cll_ditu.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                    Toast.makeText(context, "打开地图", Toast.LENGTH_SHORT).show();
//                        getPopupWindow();
//                    }
//                });
//            }catch (Exception e){}
//
//
//        }else  if (showType1==2){
//
//            try {
//                vll_img_layout.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.startActivity(ZiXunDetailsActivity.getIntent(context,dataBean.getEvent().getId()+""));
//                    }
//                });
//            }catch (Exception e){}
//
//
//
//            try {
//                vll_name.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.startActivity(ZiXunDetailsActivity.getIntent(context,dataBean.getEvent().getId()+""));
//                    }
//                });
//            }catch (Exception e){}
//
//
//
//            try {
//                vll_content.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.startActivity(ZiXunDetailsActivity.getIntent(context,dataBean.getEvent().getId()+""));
//                    }
//                });
//            }catch (Exception e){}
//
//
//            try {
//                cll_ditu.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                    Toast.makeText(context, "打开地图", Toast.LENGTH_SHORT).show();
//                        getPopupWindow();
//                    }
//                });
//            }catch (Exception e){}
//
//        }else  if (showType1==3){
//            try {
//                vll_img_layout.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.startActivity(TuJiDetailsActivity.getIntent(context,dataBean.getEvent().getId()+""));
//                    }
//                });
//            }catch (Exception e){}
//
//            try {
//                vll_name.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.startActivity(TuJiDetailsActivity.getIntent(context,dataBean.getEvent().getId()+""));
//                    }
//                });
//            }catch (Exception e){}
//
//            try {
//                vll_content.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        context.startActivity(TuJiDetailsActivity.getIntent(context,dataBean.getEvent().getId()+""));
//                    }
//                });
//            }catch (Exception e){}
//
//            try {
//                cll_ditu.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                    Toast.makeText(context, "打开地图", Toast.LENGTH_SHORT).show();
//                        getPopupWindow();
//                    }
//                });
//            }catch (Exception e){}
//        }

    }

    public VideoListlayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.beiyong1, this);
        vll_img= (ImageView) view.findViewById(R.id.vll_img);
        vll_bofang= (ImageView) view.findViewById(R.id.vll_bofang);
        vll_text= (TextView) view.findViewById(R.id.vll_text);
        vll_name= (TextView) view.findViewById(R.id.vll_name);
        vll_content= (TextView) view.findViewById(R.id.vll_content);
        cll_position1= (ImageView) view.findViewById(R.id.cll_position1);
        vll_position= (TextView) view.findViewById(R.id.vll_position);
        vll_bujidashuju= (TextView) view.findViewById(R.id.vll_bujidashuju);
        video_relative = (RelativeLayout) view.findViewById(R.id.video_relative);
        vll_img_layout = (RelativeLayout) view.findViewById(R.id.vll_img_layout);
        cll_ditu = (LinearLayout) view.findViewById(R.id.cll_ditu);
        video_relative1 = (RelativeLayout) view.findViewById(R.id.video_relative1);

        cll_layout = (LinearLayout) view.findViewById(R.id.cll_layout);  
        
        
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


                        if (AMapUtil.isInstallByRead("com.autonavi.minimap")){//


                            AMapUtil.goToNaviActivity(getContext(),"test",null,"34.264642646862","108.95108518068","1","2");
                        }else {
                            Toast.makeText(getContext(), "请先安装高德地图", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case R.id.btn_camera_pop_album:
//                        Toast.makeText(getContext(), "请先安装百度地图", Toast.LENGTH_SHORT).show();

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
