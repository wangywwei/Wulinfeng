package com.hxwl.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hxwl.newwlf.URLS;
import com.hxwl.wulinfeng.R;

/**
 * Created by Administrator on 2018/1/12.
 */

public class GlidUtils {


    //普通的图片
    public static void setGrid(Context context, String url, ImageView view){
        Glide
                .with(context)
                .load(url)
                .placeholder(R.drawable.wlf_deimg)
                .error(R.drawable.wlf_deimg)
                .crossFade(1000)
                .into(view);
    }

    //圆形图片
    public static void setGrid2(Context context, String url, ImageView view){
        Glide
                .with(context)
                .load(url)
                .error(R.drawable.denglu)
                .bitmapTransform(new GlideCircleTransform(context))
                .crossFade(1000)
                .into(view);
    }

    //设置背景图
    public static void setBackground(Context context, String url, final View view){
        Glide.with(context)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(180,180) {//设置宽高
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Drawable drawable = new BitmapDrawable(resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(drawable);//设置背景
                        }
                    }
                });
    }




}
