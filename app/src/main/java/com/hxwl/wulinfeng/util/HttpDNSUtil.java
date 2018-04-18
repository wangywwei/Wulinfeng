package com.hxwl.wulinfeng.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 功能:httpdns 工具类
 * 作者：zjn on 2017/1/14 11:00
 */

public class HttpDNSUtil {
    /**
     * 转换url 主机头为ip地址
     *
     * @param url  原url
     * @param host 主机头
     * @param ip   服务器ip
     * @return
     */
    public static String getIpUrl(String url, String host, String ip) {
        if (url == null) {
            Log.e("TAG", "URL NULL");
        }
        if (host == null) {
            Log.e("TAG", "host NULL");
        }
        if (ip == null) {
            Log.e("TAG", "ip NULL");
        }
        if (url == null || host == null || ip == null) return url;
        String ipUrl = url.replaceFirst(host, ip);
        return ipUrl;
    }
    /**
     * 根据url获得ip,此方法只是最简单的模拟,实际情况很复杂,需要做缓存处理
     *
     * @param host
     * @return
     */
    public static String getIPByHost(String host) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("203.107.1.1")
                .addPathSegment("d")
                .addQueryParameter("host", host)
                .build();
        //与我们正式请求独立，所以这里新建一个OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();
        try {
            String result = null;
            /**
             * 子线程中同步去获取
             */
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String body = response.body().string();
                JSONObject jsonObject = new JSONObject(body);
                JSONArray ips = jsonObject.optJSONArray("ips");
                if (ips != null) {
                    result = ips.optString(0);
                }
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
