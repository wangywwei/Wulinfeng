package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;

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
    private DynamicBean.DataBean dataxqBean;

    private ImageView vll_img;
    private ImageView vll_bofang;
    private TextView vll_text;
    private TextView vll_name;
    private TextView vll_content;
    private ImageView cll_position1;
    private TextView vll_position;
    private TextView vll_bujidashuju;
    private HuoDongLayout huoDongLayout;
    private RelativeLayout video_relative;
    private RelativeLayout vll_img_layout;
    private LinearLayout cll_ditu;


    public void setBean(final Home3Bean.DataBean.SchedulesBean bean) {
        this.dataBean = bean;
        dataBean.getEvent().getId();


/*VIDEO(1,"视频"),RICH(2,"图文"),GATHER(3,"图集"),WORD(4,"纯文本"),SCHEDULE(5,"赛程"),AGAINST(6,"对阵"); */
        try {
            int showType1 = dataBean.getEvent().getShowType();
            if (showType1==1){
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
            }else if (showType1==2){
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
            }else if (showType1==3){
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






        try {

            int showType = dataBean.getEvent().getShowType();
            int scheduleState = dataBean.getEvent().getScheduleState();



            if (showType==5||scheduleState==3){






            }





        }catch (Exception e){
        }






        // 活动
        try {
            List<Home3Bean.DataBean.SchedulesBean.ActivityListBean> activityList = dataBean.getActivityList();
            if(null == activityList || activityList.size() ==0 ){
                //为空的情况
                return;
            }else{
                video_relative.removeAllViews();//清空布局
                huoDongLayout = new HuoDongLayout(context);
                video_relative.addView(huoDongLayout);
                huoDongLayout.setBean(dataBean.getActivityList().get(0));
            }
        }catch (Exception e){
        }



        try {
            vll_img_layout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "点击了播放图片", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}



        try {
            vll_name.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"点击了："+ dataBean.getEvent().getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}



        try {
            vll_content.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"点击了："+ dataBean.getEvent().getIntro(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}


        try {
            cll_ditu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "打开地图", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){}

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

    }

}
