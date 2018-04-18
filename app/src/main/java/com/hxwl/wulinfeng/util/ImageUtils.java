package com.hxwl.wulinfeng.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.view.Displayer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Administrator on 2016/7/26.
 */
public class ImageUtils {

    //将url转换成图片,支持GIF或者jpg等
    public static void getPic(String url, ImageView view, Context context) {
        if (!StringUtils.isEmpty(url)) {
            if (url.contains(".gif") || url.contains(".GIF")) {
                Glide.with(context)
                        .load(url)
                        .asGif()
                        .placeholder(R.drawable.wlf_deimg)
                        .error(R.drawable.wlf_deimg)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(view);
            } else {
                Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .placeholder(R.drawable.wlf_deimg)
                        .error(R.drawable.wlf_deimg)
                        .into(view);
            }
        } else {
            view.setImageResource(R.drawable.wlf_deimg);
        }
    }

    /**
     * 让Gallery上能马上看到该图片
     */
    public static void scanPhoto(Context ctx, String imgFileName) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }

    /**
     * 把Bitmap这个图片对象存在sd卡上的saveFile这个路径下
     *
     * @param saveFile
     * @param bitmap
     * @return
     */
    public static boolean saveBitmapTOSd(String saveFile, Bitmap bitmap) {
        try {
            File file = new File(saveFile);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream out = new FileOutputStream(saveFile);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    public static Bitmap getBitmapByPath(String filePath,
                                         BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
//			String thumbpath=ImageUtilsXh.getThumbnailImage(filePath, filePath);

            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    /**
     * 获取bitmap
     *
     * @param file
     * @return
     */
    public static Bitmap getBitmapByFile(File file) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }

    //将url转换成图片,jpg
    public static void getBitmapPic(String url, int resId, Context context, SimpleTarget<Bitmap> target) {
        if (!StringUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .placeholder(resId)
                    .into(target);
        }
    }

    public static void getBitmapPic(String url, Context context, SimpleTarget<Bitmap> target) {
        if (!StringUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(target);
        }
    }

    //将url转换成图片,jpg 需要设置回调
    public static void getGifPic(String url, int resId, Context context, SimpleTarget<GifDrawable> target) {
        if (!StringUtils.isEmpty(url)) {
            Glide.with(context)
                    .load(url)
                    .asGif()
                    .placeholder(resId)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(target);
        }
    }

    //将url转换成图片
    public static void getPic(String url, View view, Context context) {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(null)
                .delayBeforeLoading(100)//设置延时多少时间后开始下载
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的资源是否缓存在SD卡中
                .considerExifParams(true).build();// 是否考虑JPEG图像EXIF参数（旋转，翻转）

        ImageLoader.getInstance().displayImage(url, (ImageView) view, options);
    }

    /**
     * 将URl转换成圆形图片
     *
     * @param url
     * @param view
     * @param context
     */
    public static void getCirclePic(String url, View view, Context context) {
        try {
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
            DisplayImageOptions option = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.denglu)
                    .showImageForEmptyUri(R.drawable.denglu)
                    .showImageOnFail(R.drawable.denglu)
                    .cacheInMemory(true)
                    .cacheOnDisc(true)
                    .bitmapConfig(Bitmap.Config.ARGB_8888)   //设置图片的解码类型
                    .displayer(new Displayer(0))
                    .build();

            ImageLoader.getInstance().displayImage(url, (ImageView) view, option);
        }catch (Exception o){

        }

    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    /**
     * 图片成比例缩放后的高度
     *
     * @param imageUri
     * @param context
     * @return
     */
    public static int getimageHeight(URL imageUri, Context context) {

        Bitmap bitmap = null;
        int imageheight = 0;
        Log.d("gggggggggggg", "fff");
        try {
            HttpURLConnection hp = (HttpURLConnection) imageUri
                    .openConnection();
            bitmap = BitmapFactory.decodeStream(hp.getInputStream());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            int picheight = options.outHeight;
            int picwidth = options.outWidth;
            Log.d("gggggggggggg", picheight + "fff" + picwidth);
            WindowManager wm = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);

            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();
            int dix = picwidth / width;
            imageheight = picheight / dix;

            hp.disconnect();// 关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageheight;
    }

    /**
     * @param urlpath
     * @return Bitmap
     * 根据图片url获取图片对象
     */
    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取网络图片
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmapByUrl(String url) {

        Bitmap bitmap = null;

        try {
            URL ur = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) ur.openConnection();

            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            // LogUtils.logE("异常："+e.getMessage());
        } catch (IOException e) {
            // LogUtils.logE("异常："+e.getMessage());
        }

        return bitmap;
    }

    /**
     * 高斯模糊
     *
     * @param bmp
     * @return
     */
    public static Bitmap convertToBlur(Bitmap bmp) {
        if (bmp == null) {
            return null;
        }
        long start = System.currentTimeMillis();
        // 高斯矩阵
        int[] gauss = new int[]{1, 2, 1, 2, 4, 2, 1, 2, 1};

        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);

        int pixR = 0;
        int pixG = 0;
        int pixB = 0;

        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;

        int delta = 28; // 值越小图片会越亮，越大则越暗

        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + (int) (pixR * gauss[idx]);
                        newG = newG + (int) (pixG * gauss[idx]);
                        newB = newB + (int) (pixB * gauss[idx]);
                        idx++;
                    }
                }

                newR /= delta;
                newG /= delta;
                newB /= delta;

                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));

                pixels[i * width + k] = Color.argb(255, newR, newG, newB);

                newR = 0;
                newG = 0;
                newB = 0;
            }
        }

        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        long end = System.currentTimeMillis();
        return bitmap;
    }


}
