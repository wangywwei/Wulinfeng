package com.hxwl.wulinfeng.net;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.alibaba.fastjson.JSON;
import com.hxwl.newwlf.URLS;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.photoView.PhotoView;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.json.JSONUtils;
import com.hxwl.wulinfeng.wulin.SeeHighDefinitionPictureActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class AppClient {

    /**
     * 同步请求数据方法post
     * @param paramMap
     * @return
     * @throws Throwable
     */
    public static ResultPacket okhttp_post_syn(Activity activity ,Map<String, Object> paramMap){
        FormBody.Builder builder = new FormBody.Builder();
        String Baseurl =NetUrlUtils.BaseUrl;
        if(paramMap != null || paramMap.size()>0){
            for (String key : paramMap.keySet()) {
                if("method".equals(key)){
                    Baseurl = Baseurl+paramMap.get(key) ;
                }else{
                    builder.add(key,String.valueOf(paramMap.get(key)));
                }
            }
        }
        Request request = new Request.Builder().url(Baseurl).post(builder.build()).build();
        Response response = null;
        try {
            response = MakerApplication.okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResultPacket localResultPacket = null;
        try {
            localResultPacket = JSON.parseObject(response.body().string(),ResultPacket.class);
            response.body().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return localResultPacket;
    }

    /**
     * 异步加载数据 -- post
     * @param url 访问地址
     * @param paramMap 封装map参数
     * @param callback 接口回调
     * @return 返回
     * @throws Throwable
     */
    public static void okhttp_post_Asyn(final Activity activity , String url,
                                        Map<String, Object> paramMap, final ICallback callback) throws Throwable {
        FormBody.Builder builder = new FormBody.Builder();
        if(paramMap != null && paramMap.size()>0){
            for (String key : paramMap.keySet()) {
                builder.add(key,String.valueOf(paramMap.get(key)));
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = MakerApplication.okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.error(e);
                    }
                });

            }
            @Override
            public void onResponse(Call call, final Response response){
                if (response.isSuccessful()){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                callback.success(JSON.parseObject(response.body().string(),ResultPacket.class));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }else{

                }
            }
        });
    }
    /**
     * 异步加载数据 -- post
     * @param url 访问地址
     * @param paramMap 封装map参数
     * @param callback 接口回调
     * @return 返回
     * @throws Throwable
     */
    public static void okhttp_post_Asyn(String url,
                                        Map<String, Object> paramMap, final ICallback callback) throws Throwable {
        FormBody.Builder builder = new FormBody.Builder();
        if(paramMap != null && paramMap.size()>0){
            for (String key : paramMap.keySet()) {
                builder.add(key,String.valueOf(paramMap.get(key)));
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = MakerApplication.okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.error(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String s = response.body().string() ;
                    callback.success(JSON.parseObject(s,ResultPacket.class));
                }else{

                }
            }
        });
    }
    /**
     * 异步加载数据 -- post(带有上传文件)
     * @param url 访问地址
     * @param paramMap 封装map参数
     * @param callback 接口回调
     * @return 返回
     * @throws Throwable
     */
    public static void okhttp_post_Asyn(String url,
                                        Map<String, Object> paramMap, String file , final ICallback callback) throws Throwable {
         final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg; charset=utf-8");

//        FormBody.Builder builder = new FormBody.Builder();
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(paramMap != null && paramMap.size()>0){
            for (String key : paramMap.keySet()) {
                builder.addFormDataPart(key,String.valueOf(paramMap.get(key)));
            }
        }
        File files = new File(file);
        builder.addFormDataPart("img",files.getName(), RequestBody.create(MEDIA_TYPE_PNG,files));

        Request request = new Request.Builder().url(url).addHeader("Connection", "close").post(builder.build()).build();
        Call call = MakerApplication.okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.error(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String s = response.body().string() ;
                    callback.success(JSON.parseObject(s,ResultPacket.class));
                }else{

                }
            }
        });
    }

    /**
     * 显示网络图片
     * @param touchImageView
     * @param url
     * @param activity
     */
    public static void getImageForView(PhotoView touchImageView, String url, Activity activity) {
        Request request = new Request.Builder().url(url).build();
        try {
            ResponseBody body = MakerApplication.okHttpClient.newCall(request).execute().body();
            //获取流
            InputStream in = body.byteStream();
            //转化为bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            touchImageView.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


