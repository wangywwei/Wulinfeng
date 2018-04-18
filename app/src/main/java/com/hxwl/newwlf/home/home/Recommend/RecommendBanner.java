package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.intent.DuizhengVideoActivity;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.TuJiDetailsActivity;
import com.hxwl.wulinfeng.activity.WebViewCurrencyActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.hxwl.wulinfeng.bean.HomeBannerImageBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.json.GsonUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 *
 * 首页--推荐--banner
 */

public class RecommendBanner extends LinearLayout implements OnBannerListener {
    private View view;
    private Context context;
    private Home3Bean.DataBean bean;
    private List<HomeBannerImageBean> data2EntityList = new ArrayList<>();
    public void setBean(final Home3Bean.DataBean bean) {
        this.bean = bean;
        data2EntityList.clear();
        if (bean!=null){
            HomeBannerImageBean homeBannerImageBean=null;


            
            
            for (int i = 0; i <bean.getBanners().size(); i++) {
                homeBannerImageBean=new HomeBannerImageBean();
                homeBannerImageBean.setId(bean.getBanners().get(i).getId()+"");
                homeBannerImageBean.setImg(URLS.IMG+bean.getBanners().get(i).getImage());
                homeBannerImageBean.setLabel(bean.getBanners().get(i).getLabel1()+"");
                homeBannerImageBean.setLabel2(bean.getBanners().get(i).getLabel2()+"");
                homeBannerImageBean.setTitle(bean.getBanners().get(i).getId()+"");
                homeBannerImageBean.setAndroid_url(bean.getBanners().get(i).getUrl()+"");
                data2EntityList.add(homeBannerImageBean);
            }
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            //设置图片加载器
            banner.setImageLoader(new GlideImageLoader());

            //设置图片集合
            banner.setImages(data2EntityList);
            List<String> titleList = getTitleList(data2EntityList);
            banner.setBannerTitles(titleList) ;

            List<String> lableList = getLableList(data2EntityList);
            banner.setBannerLables(lableList) ;

            List<String> lableList2 = getLableList2(data2EntityList);
            banner.setBannerLables2(lableList2) ;
            //设置banner动画效果
            banner.setBannerAnimation(Transformer.Default);
            //设置自动轮播，默认为true
            banner.isAutoPlay(true);
            //设置轮播时间
            banner.setDelayTime(3000);
            //设置指示器位置（当banner模式中有指示器时）
            //                        banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
            //banner设置方法全部调用完毕时最后调用
            banner.start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    //TODO
                    Home3Bean.DataBean.BannersBean dataBean = bean.getBanners().get(position);

                        switch (dataBean.getBannerType()){
                            case 1:
                                context.startActivity(HuiGuDetailActivity.getIntent(context,dataBean.getTargetId()+"",2));
                                break;
                            case 2:
                                context.startActivity(DuizhengVideoActivity.getIntent(context,dataBean.getTargetId()+""));
                                break;
                            case 3:

//                                Toast.makeText(context, "这里容易出问题", Toast.LENGTH_SHORT).show();


                                try {
                                    int bannerType = dataBean.getBannerType();
                                    switch (bannerType){
                                    case 1:
                                        context.startActivity(ZiXunDetailsActivity.getIntent(context,dataBean.getTargetId()+""));
                                        break;
                                    case 2:
                                        context.startActivity(TuJiDetailsActivity.getIntent(context,dataBean.getTargetId()+""));
                                        break;
                                    case 3:
                                        context.startActivity(ZixunVideoActivity.getIntent(context,dataBean.getTargetId()+""));
                                        break;
                                }
                                }catch (Exception e){

                                }

                                break;
                            case 4:
                                Intent intent = new Intent(context, WebViewCurrencyActivity.class);
                                intent.putExtra("url", dataBean.getUrl());
                                intent.putExtra("title", "武林风");
                                context.startActivity(intent);
                                break;
                        }

                }
            });


        }
    }

    private List<String> getTitleList(List<HomeBannerImageBean> list) {
        List<String> titleList = new ArrayList<>();
        if (list == null) {
            return titleList;
        }
        for (int i = 0; i < list.size(); i++) {
            titleList.add(list.get(i).getTitle());
        }
        return titleList;
    }
    private List<String> getLableList(List<HomeBannerImageBean> list) {
        List<String> titleList = new ArrayList<>();
        if (list == null) {
            return titleList;
        }
        for (int i = 0; i < list.size(); i++) {
            titleList.add(list.get(i).getLabel());
        }
        return titleList;
    }

    private List<String> getLableList2(List<HomeBannerImageBean> list) {
        List<String> titleList = new ArrayList<>();
        if (list == null) {
            return titleList;
        }
        for (int i = 0; i < list.size(); i++) {
            titleList.add(list.get(i).getLabel2());
        }
        return titleList;
    }

    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //Glide 加载图片简单用法
            HomeBannerImageBean pathbean = GsonUtil.GsonToBean(new Gson().toJson(path), HomeBannerImageBean.class);
            if (pathbean != null && pathbean.getImg() != null) {
                ImageUtils.getPic(pathbean.getImg(), imageView, context);
            }
        }
    }

    public RecommendBanner(Context context) {
        this(context,null);
    }

    public RecommendBanner(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RecommendBanner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private Banner banner;

    private ArrayList<String> list_path;

    public void setList_path(ArrayList<String> list_path) {
        this.list_path = list_path;
    }

    private void initView(Context context) {
            this.context = context;
        view= LayoutInflater.from(context).inflate(R.layout.recommenbanner,this);
        banner= (Banner) view.findViewById(R.id.banner);
    }

    @Override
    public void OnBannerClick(int position) {


    }




}
