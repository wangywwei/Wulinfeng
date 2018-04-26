package com.hxwl.wlf3.home.xiangqing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.common.tencentplay.tencentCloud.TXMediaManager;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.wulinfeng.R;

public class VideoLayout extends LinearLayout {
    private Context context;
    private Shijian3Bean.DataBean data;
    private TextView tv1;
    private Button btn1;
    private RelativeLayout videoContainer;
    private TextView video_title;
    private TextView video_time;
    private TextView video_action;
    private TextView video_content;
    private View ic_net_warn;


    public void setData(Shijian3Bean.DataBean data) {
        this.data = data;
        TXMediaManager.instance(context).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
        TXMediaManager.instance(context).TXVodPlayAndView(data.getVideoUrl() ,videoContainer
                ,3,data.getTitle()) ;
        video_title.setText(data.getTitle());
        video_action.setText(data.getAuthor());
        if (StringUtils.isBlank(data.getContent())){
            video_content.setVisibility(GONE);
        }else {
            video_content.setVisibility(VISIBLE);
            video_content.setText(data.getContent());
        }

        video_time.setText(data.getEventTime());

    }

    public VideoLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        View videolayout = LayoutInflater.from(context).inflate(R.layout.video3layout, this);
        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        videolayout.setTag(layoutParams);
        TXMediaManager.instance(context).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
        videoContainer= (RelativeLayout) videolayout.findViewById(R.id.videoContainer);

        video_title= (TextView) videolayout.findViewById(R.id.video_title);
        video_time= (TextView) videolayout.findViewById(R.id.video_time);
        video_action= (TextView) videolayout.findViewById(R.id.video_action);
        video_content= (TextView) videolayout.findViewById(R.id.video_content);
        ic_net_warn = findViewById(R.id.ic_net_warn);
        btn1= (Button) ic_net_warn.findViewById(R.id.btn1);

        //点击继续播放，非WIFI才出现
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ic_net_warn.setVisibility(View.GONE);
                String url = data.getVideoUrl();
                TXMediaManager.instance(context).setActivityType(TXMediaManager.ACTIVITY_TYPE_VOD_PLAY);
                TXMediaManager.instance(context).TXVodPlayAndView(url ,videoContainer
                        ,3,data.getTitle()) ;
            }
        });

    }
}
