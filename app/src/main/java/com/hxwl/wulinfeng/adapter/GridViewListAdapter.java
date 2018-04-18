package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hxwl.common.utils.CircleImageView;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.FaXianBean;
import com.hxwl.wulinfeng.util.ImageUtils;

import java.util.List;

/**
 * Created by Allen on 2017/6/14.
 */
public class GridViewListAdapter extends BaseAdapter {
    private Activity activity;
    private List<FaXianBean> faXianBeanList;
    public GridViewListAdapter(Activity activity, List<FaXianBean> faXianBeanList) {
        this.activity =activity;
        this.faXianBeanList = faXianBeanList ;
    }

    @Override
    public int getCount() {
        return faXianBeanList == null ? 0 : faXianBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHower viewHower;
        FaXianBean bean = faXianBeanList.get(position) ;
        if(convertView == null){
            convertView = View.inflate(activity, R.layout.item_faxian_grid ,null) ;
            viewHower = new ViewHower();
            viewHower.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHower.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHower.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(viewHower);
        }else{
            viewHower = (ViewHower)convertView.getTag();
        }
        viewHower.tv_title.setText(bean.getName());
        Bitmap bitmap = null;
        if (0 == position){
            bitmap = BitmapFactory.decodeResource(activity.getResources(),R.drawable.xie_player);
            viewHower.iv_icon.setImageBitmap(ImageUtils.toRoundBitmap(bitmap));
        }else if(1 == position){
            bitmap = BitmapFactory.decodeResource(activity.getResources(),R.drawable.wuzhan_icon);
            viewHower.iv_icon.setImageBitmap(ImageUtils.toRoundBitmap(bitmap));
        }else{
            if (!StringUtils.isEmpty(bean.getIcon())) {
                ImageUtils.getPic(bean.getIcon(),viewHower.iv_icon,activity);
//                if (bean.getIcon().contains(".gif") || bean.getIcon().contains(".GIF")) {
//                    Glide.with(activity).load(bean.getIcon()).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<GifDrawable>() {
//                        @Override
//                        public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) {
//                            Bitmap bit = resource.getFirstFrame();
//                            viewHower.iv_icon.setImageBitmap(ImageUtils.convertToBlur(bit));
//                        }
//                    });
//                } else {
//                    Glide.with(activity)
//                            .load(bean.getIcon())
//                            .asBitmap()
//                            .into(new SimpleTarget<Bitmap>() {
//                                @Override
//                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                    viewHower.iv_icon.setImageBitmap(ImageUtils.convertToBlur(resource));
//                                }
//                            });
//                }
            }
        }
        final Bitmap bit ;
        if (0 == position){
            bit = BitmapFactory.decodeResource(activity.getResources(),R.drawable.player);
            viewHower.iv_img.setImageBitmap(bit);
        }else if(1 == position){
            bit = BitmapFactory.decodeResource(activity.getResources(),R.drawable.wuzhan);
            viewHower.iv_img.setImageBitmap(bit);
        }else{
            if (!StringUtils.isEmpty(bean.getIcon())) {
                if (bean.getIcon().contains(".gif") || bean.getIcon().contains(".GIF")) {
                    Glide.with(activity).load(bean.getIcon()).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<GifDrawable>() {
                        @Override
                        public void onResourceReady(GifDrawable resource, GlideAnimation<? super GifDrawable> glideAnimation) {
                            Bitmap bit = resource.getFirstFrame();
                            viewHower.iv_img.setImageBitmap(ImageUtils.convertToBlur(bit));
                        }
                    });
                } else {
                    Glide.with(activity)
                            .load(bean.getIcon())
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    viewHower.iv_img.setImageBitmap(ImageUtils.convertToBlur(resource));
                                }
                            });
                }
            }
        }
        viewHower.tv_title.setTag(bean);
        return convertView;
    }
    class ViewHower{
        TextView tv_title;
        ImageView iv_icon;
        ImageView iv_img;
    }
}
