package com.hxwl.wlf3.home.xiangqing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;

public class Tuji3Layout extends RelativeLayout {
    private Context context;
    private Shijian3Bean.DataBean data;
    private TextView video_title;
    private TextView video_time;
    private TextView video_action;
    private TextView video_content;

    public void setData(Shijian3Bean.DataBean data) {
        this.data = data;
        video_title.setText(data.getTitle());
        video_action.setText(data.getAuthor());
        video_time.setText(data.getEventTime());
        if (StringUtils.isBlank(data.getContent())){
            video_content.setVisibility(GONE);
        }else {
            video_content.setVisibility(VISIBLE);
            video_content.setText(data.getContent());
        }

    }

    public Tuji3Layout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        View tujilayout = LayoutInflater.from(context).inflate(R.layout.tujilayout, this);
        video_title= (TextView) tujilayout.findViewById(R.id.video_title);
        video_time= (TextView) tujilayout.findViewById(R.id.video_time);
        video_action= (TextView) tujilayout.findViewById(R.id.video_action);
        video_content= (TextView) tujilayout.findViewById(R.id.video_content);
    }
}
