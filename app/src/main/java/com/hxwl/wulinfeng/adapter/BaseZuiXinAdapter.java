package com.hxwl.wulinfeng.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hxwl.wulinfeng.bean.AllHomeHunHeBean;
import com.hxwl.wulinfeng.bean.HomeBannerImageBean;
import com.hxwl.wulinfeng.bean.HomeHunHeBean;
import com.hxwl.wulinfeng.bean.HomeTuijianZhiboBean;
import com.hxwl.wulinfeng.bean.HomeZhuanTiBean;
import com.hxwl.wulinfeng.bean.HomeZuixinHuifangBean;
import com.hxwl.wulinfeng.bean.MainPageTuiJianBean;
import com.hxwl.wulinfeng.bean.SaichengBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/5/26.
 * 最新adapter的基类用来创建方法 ，返回响应布局
 */

public abstract class BaseZuiXinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_BANNER = 0;//最上面顶部
    //头条推荐
    public static final int TYPE_ZUIXIN_TUIJIAN = 1;
    //专题
    public static final int TYPE_ZUIXIN_ZHUANTI = 2;
    //视频
    public static final int TYPE_ZUIXIN_VIDEO = 3;
    //图集
    public static final int TYPE_TUJI = 4;
    //新闻
    public static final int TYPE_NEWS = 5;
    //混合布局video
    public static final int TYPE_VIDEO = 6;
//========banner=========
    //创建banner的viewHolder
    public ZuiXinBannerViewHolder onCreateBannerViewHolder(ViewGroup parent, int position) {
        return null;
    }

    //当前类型的item数目
    public int getBannerViewCount() {
        return 0;
    }

    //赋值方法
    public void onBindBannerViewHolder(ZuiXinBannerViewHolder holder, int position) {

    }
    //========推荐=========
    //创建头条推荐的viewHolder
    public ZuiXinTuijianViewHolder onCreateZuiXinTuiJianViewHolder(ViewGroup parent, int position) {
    return null;
    }

    //当前类型的item数目
    public int getZuiXinTuiJianViewCount() {
        return 0;
    }

    //赋值方法
    public void onBindZuiXinTuiJianViewHolder(ZuiXinTuijianViewHolder holder, int position) {

    }
    //========专题=========
    //创建专题
    public ZuiXinZhuanTiViewHolder onCreateZuiXinZhuanTiViewHolder(ViewGroup parent, int position) {
        return null;
    }

    //当前类型的item数目
    public int getZuiXinZhuanTiViewCount() {
        return 0;
    }

    //赋值方法
    public void onBindZuiXinZhuanTiViewHolder(ZuiXinZhuanTiViewHolder holder, int position) {

    }
    //========新闻=========
    //创建专题
    public ZuiXinNewsViewHolder onCreateZuiXinNewsViewHolder(ViewGroup parent, int position) {
        return null;
    }


    public int getHunHeViewCount() {
        return 0;
    }

    //赋值方法
    public void onBindZuiXinNewsViewHolder(ZuiXinNewsViewHolder holder, int position) {

    }
    //========视频=========
    //创建头条视频
    public ZuiXinVedioViewHolder onCreateZuiXinVedioViewHolder(ViewGroup parent, int position) {
        return null;
    }

    //当前类型的item数目
    public int getZuiXinVedioViewCount() {
        return 0;
    }

    //赋值方法
    public void onBindZuiXinVedioViewHolder(ZuiXinVedioViewHolder holder, int position) {

    }
    //========图集=========
    //创建头条图集
    public ZuiXinPicGalleryViewHolder onCreateZuiXinPicGalleryViewHolder(ViewGroup parent, int position) {
        return null;
    }

    //当前类型的item数目
    public int getZuiXinPicGalleryViewCount() {
        return 0;
    }

    //赋值方法
    public void onBindZuiXinPicGalleryViewHolder(ZuiXinPicGalleryViewHolder holder, int position) {

    }
    //========news视频=========
    //创建头条图集
    public ZuiXinNewsVideoViewHolder onCreateZuiXinNewsVideoViewHolder(ViewGroup parent, int position) {
        return null;
    }

    //当前类型的item数目
    public int getZuiXinNewsVideoCount() {
        return 0;
    }

    //赋值方法
    public void onBindZuiXinNewsVideoViewHolder(ZuiXinNewsVideoViewHolder holder, int position) {

    }

    //创建viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return onCreateBannerViewHolder(parent, viewType);
            case 1:
                return onCreateZuiXinTuiJianViewHolder(parent, viewType);
            case 2:
                return onCreateZuiXinZhuanTiViewHolder(parent, viewType);
            case 3:
                return onCreateZuiXinVedioViewHolder(parent, viewType);
            case 4:
                return onCreateZuiXinPicGalleryViewHolder(parent, viewType);
            case 5:
                return onCreateZuiXinNewsViewHolder(parent, viewType);
            case 6:
                return onCreateZuiXinNewsVideoViewHolder(parent, viewType);
            default:
                return onCreateZuiXinNewsViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int bannerViewCount = getBannerViewCount();
        int zuixintuijianViewCount = getZuiXinTuiJianViewCount();
        int zhuantiViewCount = getZuiXinZhuanTiViewCount();
        int zuixinVideoCount = getZuiXinVedioViewCount() ;
        int viewCount = getItemCount();
        int itemType = getItemViewType(position);
        switch (itemType) {
            case 0:
                onBindBannerViewHolder((ZuiXinBannerViewHolder) holder, position);
                break;
            case 1:
                onBindZuiXinTuiJianViewHolder((ZuiXinTuijianViewHolder) holder, position - bannerViewCount);
                break;
            case 2:
                onBindZuiXinZhuanTiViewHolder((ZuiXinZhuanTiViewHolder) holder, position - bannerViewCount - zuixintuijianViewCount );
                break;
            case 3:
                onBindZuiXinVedioViewHolder((ZuiXinVedioViewHolder) holder, position - bannerViewCount - zuixintuijianViewCount - zhuantiViewCount);
                break;
            case 4:
                onBindZuiXinPicGalleryViewHolder((ZuiXinPicGalleryViewHolder) holder, position - bannerViewCount - zuixintuijianViewCount - zhuantiViewCount - zuixinVideoCount);
                break;
            case 5:
                onBindZuiXinNewsViewHolder((ZuiXinNewsViewHolder) holder, position - bannerViewCount - zuixintuijianViewCount - zhuantiViewCount- zuixinVideoCount);
                break;
            case 6:
                onBindZuiXinNewsVideoViewHolder((ZuiXinNewsVideoViewHolder) holder, position - bannerViewCount - zuixintuijianViewCount - zhuantiViewCount- zuixinVideoCount);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int bannerViewCount = getBannerViewCount();
        int tuijianViewCount = getZuiXinTuiJianViewCount();
        int zhuantiViewCount = getZuiXinZhuanTiViewCount();
        int videoCount= getZuiXinVedioViewCount() ;
        if (position < bannerViewCount) {
            return TYPE_BANNER;
        }
        if (position >= bannerViewCount && position < (tuijianViewCount + bannerViewCount)) {
            return TYPE_ZUIXIN_TUIJIAN;
        }
        if (position >= bannerViewCount + tuijianViewCount && position < (tuijianViewCount + bannerViewCount + zhuantiViewCount)) {
            return TYPE_ZUIXIN_ZHUANTI;
        }
        if (position >= bannerViewCount + tuijianViewCount + zhuantiViewCount && position < (tuijianViewCount + bannerViewCount + videoCount + zhuantiViewCount)) {
            return TYPE_ZUIXIN_VIDEO;
        }
        try {
        List<AllHomeHunHeBean> list = getNewsData() ;
            AllHomeHunHeBean typeBean = list.get(position - (tuijianViewCount + bannerViewCount + zhuantiViewCount + videoCount));
            String type = typeBean.getType_();
        if ("news".equals (type)) {
            return TYPE_NEWS;
        } else if ("tuji".equals(type)) {
            return TYPE_TUJI;
        } else if ("video".equals(type)) {
            return TYPE_VIDEO;
            }
        } catch (Exception e) {
            return TYPE_BANNER;
        }
        return TYPE_BANNER;
    }



    @Override
    public int getItemCount() {
        return getBannerViewCount() + getZuiXinTuiJianViewCount() +getZuiXinVedioViewCount()+ getZuiXinZhuanTiViewCount()
                + getHunHeViewCount();
    }

    //第三部分和第二部分的接口回调函数
    public interface DetalisCallBack {
        void getDetalisData(int viewType, int secondViewType, int position);
    }

    public DetalisCallBack detalisCallBack;

    public void setDetalisCallBack(DetalisCallBack detalisCallBack) {
        this.detalisCallBack = detalisCallBack;
    }

    //更新数据
    public abstract void setData(List<AllHomeHunHeBean> tuijianHunHeBean);

    public abstract void setTuijianData(List<SaichengBean> list, String num);

    public abstract void setBannerData(List<HomeBannerImageBean> data);

    public abstract void setHuifangData(List<HomeZuixinHuifangBean> list);

    public abstract void setZhuanTiData(List<HomeZhuanTiBean> list);

    //添加数据
    public abstract void loadNextPageData(List<AllHomeHunHeBean> tuijianHunHeBean);

    public abstract MainPageTuiJianBean getData();

    //获取到新闻数据
    public abstract List<AllHomeHunHeBean> getNewsData();
    public abstract List<HomeZuixinHuifangBean> getHuiFangData() ;

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
