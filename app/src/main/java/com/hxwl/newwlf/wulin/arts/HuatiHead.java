package com.hxwl.newwlf.wulin.arts;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.wulinfeng.R;

/**
 * Created by Administrator on 2018/2/8.
 */

public class HuatiHead extends LinearLayout {
    private Context context;
    private ImageView huati_back;
    private ImageView huati_img;
    private TextView huati_title;
    private TextView huati_number;
    private RelativeLayout rl_layout;
    private TextView huati_title2;


    public HuatiHead(Context context) {
        this(context, null);
    }

    public HuatiHead(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HuatiHead(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    public void setData(String topicName,String content,String image,String joinNum){

        GlidUtils.setGrid(context, URLS.IMG+image,huati_img);
        huati_title.setText(topicName);
        huati_title2.setText(content);
        huati_number.setText(joinNum+"次参与");

        huati_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onItemclickLinter){
                    onItemclickLinter.onItemClicj();
                }
            }
        });
    }

    private void initView(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.activity_huati_xq_head, this);
        huati_back= (ImageView) view.findViewById(R.id.huati_back);
        huati_img= (ImageView) view.findViewById(R.id.huati_img);
        huati_title= (TextView) view.findViewById(R.id.huati_title);
        huati_number= (TextView) view.findViewById(R.id.huati_number);
        rl_layout= (RelativeLayout) view.findViewById(R.id.rl_layout);
        huati_title2= (TextView) view.findViewById(R.id.huati_title2);

    }

    public interface OnItemclickLinter{
        public void onItemClicj();
    }

    private OnItemclickLinter onItemclickLinter;

    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }
}
