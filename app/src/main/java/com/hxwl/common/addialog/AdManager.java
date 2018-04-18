package com.hxwl.common.addialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hxwl.common.addialog.bean.AdBean;
import com.hxwl.common.pageindicator.FlycoPageIndicator;
import com.hxwl.common.utils.DensityUtil;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.ImageUtils;

import java.util.List;

/**
 * 功能:广告功能的管理类
 * 作者：zjn on 2017/4/24 10:29
 */

public class AdManager {
    /**
     * 广告弹窗距离两侧的距离-单位(dp)
     */
    private int padding = 44;
    /**
     * 广告弹窗的宽高比
     */
    private float widthPerHeight = 0.75f;
    //对话框view
    private View contentView;
    //广告rootview
    private RelativeLayout adRootContent;
    //viewpager
    private ViewPager viewPager;
    //指示器
    private FlycoPageIndicator mIndicator;
    // viewPager滑动动画效果
    private ViewPager.PageTransformer pageTransformer = null;
    private AnimDialogUtils animDialogUtils;
    //广告信息
    private List<AdBean> mAdBeanList;
    //上下文对象
    private Activity mActivity;
    private AdAdapter mAdadapter;
    // 弹窗背景是否透明
    private boolean isAnimBackViewTransparent = false;
    // 弹窗是否可关闭
    private boolean isDialogCloseable = true;
    // 设置弹窗背景颜色
    private int backViewColor = Color.parseColor("#bf000000");
    // 弹性动画弹性参数
    private double bounciness = AdConstant.BOUNCINESS;
    // 是否覆盖全屏幕
    private boolean isOverScreen = true;
    // 弹窗关闭点击事件
    private View.OnClickListener onCloseClickListener = null;
    // 弹性动画速度参数
    private double speed = AdConstant.SPEED;
    //图片的点击事件监听
    private OnImageClickListener onImageClickListener = null;

    private DisplayMetrics displayMetrics = new DisplayMetrics();

    private AdBean mAdvInfo;

    public AdManager(Activity activity,List<AdBean> adBeanList){
        this.mActivity = activity;
        this.mAdBeanList = adBeanList;
    }

    //开始弹出广告对话框
    public void showAdDialog(final int animType){
        contentView = LayoutInflater.from(mActivity).inflate(R.layout.ad_dialog_content_layout,null);
        adRootContent = (RelativeLayout) contentView.findViewById(R.id.ad_root_content);
        viewPager = (ViewPager) contentView.findViewById(R.id.viewPager);
        mIndicator = (FlycoPageIndicator) contentView.findViewById(R.id.indicator);

        mAdadapter = new AdAdapter();
        viewPager.setAdapter(mAdadapter);

        if (pageTransformer != null) {
            viewPager.setPageTransformer(true, pageTransformer);
        }

        mIndicator.setViewPager(viewPager);
        isShowIndicator();

        animDialogUtils = AnimDialogUtils.getInstance(mActivity)
                .setAnimBackViewTransparent(isAnimBackViewTransparent)
                .setDialogCloseable(isDialogCloseable)
                .setDialogBackViewColor(backViewColor)
                .setOnCloseClickListener(onCloseClickListener)
                .setOverScreen(isOverScreen)
                .initView(contentView);
        setRootContainerHeight();

        // 延迟1s展示，为了避免ImageLoader还为加载完缓存图片时就展示了弹窗的情况
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animDialogUtils.show(animType, bounciness, speed);
            }
        }, 1000);
    }


    class AdAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mAdBeanList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final AdBean advInfo = mAdBeanList.get(position);

            View rootView = mActivity.getLayoutInflater().inflate(R.layout.ad_viewpager_item, null);
            final ImageView iv_ad_pic = (ImageView) rootView.findViewById(R.id.iv_ad_pic);
            final RelativeLayout ll_pb = (RelativeLayout) rootView.findViewById(R.id.ll_pb);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(rootView, params);
            iv_ad_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (advInfo != null && onImageClickListener != null) {
                        onImageClickListener.onImageClick(iv_ad_pic, advInfo);
                    }
                }
            });
            ImageUtils.getPic(advInfo.getImgUrl(),iv_ad_pic,mActivity);
            ImageUtils.getBitmapPic(advInfo.getImgUrl(), mActivity, new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ll_pb.setVisibility(View.GONE);
                    iv_ad_pic.setImageBitmap(resource);
                }

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    ll_pb.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    ll_pb.setVisibility(View.GONE);
                }
            });
            return rootView;
        }
    }

    /**
     * 根据页面数量，判断是否显示Indicator
     */
    private void isShowIndicator() {
        if (mAdBeanList.size() > 1) {
            mIndicator.setVisibility(View.VISIBLE);
        } else {
            mIndicator.setVisibility(View.INVISIBLE);
        }
    }

    private void setRootContainerHeight() {
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int totalPadding = DensityUtil.dip2px(mActivity, padding * 2);
        int width = widthPixels - totalPadding;
        final int height = (int) (width / widthPerHeight);
        ViewGroup.LayoutParams params = adRootContent.getLayoutParams();
        params.height = height;
    }

    /**
     * 开始执行销毁弹窗的操作
     */
    public void dismissAdDialog() {
        animDialogUtils.dismiss(AdConstant.ANIM_STOP_DEFAULT);
    }


    /**
     * ViewPager每一项的单击事件
     */
    public interface OnImageClickListener {

        public void onImageClick(View view, AdBean advInfo);

    }

    /**
     * 设置弹窗距离屏幕左右两侧的距离
     * @param padding
     * @return
     */
    public AdManager setPadding(int padding) {
        this.padding = padding;

        return this;
    }

    /**
     * 设置弹窗宽高比
     * @param widthPerHeight
     * @return
     */
    public AdManager setWidthPerHeight(float widthPerHeight) {
        this.widthPerHeight = widthPerHeight;

        return this;
    }

    /**
     * 设置ViewPager Item点击事件
     * @param onImageClickListener
     * @return
     */
    public AdManager setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;

        return this;
    }

    /**
     * 设置背景是否透明
     * @param animBackViewTransparent
     * @return
     */
    public AdManager setAnimBackViewTransparent(boolean animBackViewTransparent) {
        isAnimBackViewTransparent = animBackViewTransparent;

        return this;
    }

    /**
     * 设置弹窗关闭按钮是否可见
     * @param dialogCloseable
     * @return
     */
    public AdManager setDialogCloseable(boolean dialogCloseable) {
        isDialogCloseable = dialogCloseable;

        return this;
    }

    /**
     * 设置弹窗关闭按钮点击事件
     * @param onCloseClickListener
     * @return
     */
    public AdManager setOnCloseClickListener(View.OnClickListener onCloseClickListener) {
        this.onCloseClickListener = onCloseClickListener;

        return this;
    }

    /**
     * 设置弹窗背景颜色
     * @param backViewColor
     * @return
     */
    public AdManager setBackViewColor(int backViewColor) {
        this.backViewColor = backViewColor;

        return this;
    }

    /**
     * 设置弹窗弹性动画弹性参数
     * @param bounciness
     * @return
     */
    public AdManager setBounciness(double bounciness) {
        this.bounciness = bounciness;

        return this;
    }

    /**
     * 设置弹窗弹性动画速度参数
     * @param speed
     * @return
     */
    public AdManager setSpeed(double speed) {
        this.speed = speed;

        return this;
    }

    /**
     * 设置ViewPager滑动动画效果
     * @param pageTransformer
     */
    public AdManager setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this.pageTransformer = pageTransformer;

        return this;
    }

    /**
     * 设置弹窗背景是否覆盖全屏幕
     * @param overScreen
     * @return
     */
    public AdManager setOverScreen(boolean overScreen) {
        isOverScreen = overScreen;

        return this;
    }
}
