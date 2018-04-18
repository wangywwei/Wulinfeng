package com.hxwl.newwlf.home.home.zixun;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/23.
 */

public class TujiLayout extends LinearLayout {
    private Context context;
    private View view;
    private ViewPager viewpager;
    private TextView tujinum;
    private ArrayList<View> list;
    private ArrayList<String> tujilist=new ArrayList<>();
    private String tujis;

    public void setTujis(Context context,String tujis) {
        this.context = context;
        this.tujis = tujis;
        if (StringUtils.isBlank(tujis)){
            return;
        }
        tujilist.clear();
        if (tujis.indexOf(",")!=-1){
            String[] all=tujis.split(",");
            for (int i = 0; i <all.length ; i++) {
                tujilist.add(URLS.IMG+all[i]);
            }
        }else {
            tujilist.add(URLS.IMG+tujis);
        }
        initView(context);
    }

    public TujiLayout(Context context) {
        this(context, null);
    }

    public TujiLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TujiLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.tujiviewpage, this);
        viewpager= (ViewPager) view.findViewById(R.id.viewpager);
        tujinum= (TextView) view.findViewById(R.id.tujinum);
        list=new ArrayList<>();
        for (int i = 0; i <tujilist.size() ; i++) {
            View tujiview=LayoutInflater.from(context).inflate(R.layout.tujiview,null);
            ImageView tujiimage= (ImageView) tujiview.findViewById(R.id.tujiimage);
            GlidUtils.setGrid(context,tujilist.get(i),tujiimage);
            list.add(tujiview);
        }
        tujinum.setText(list.size()+"");
        PagerAdapter adapter=new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0==arg1;
            }
            @Override
            public int getCount() {
                return list.size();
            }

            //对超出范围的资源进行销毁
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }
            //对显示的资源进行初始化
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(list.get(position));
                return list.get(position);
            }

        };

        viewpager.setAdapter(adapter);



    }






}
