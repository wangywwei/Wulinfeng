package com.hxwl.common.adfilter;

import android.content.Context;
import android.content.res.Resources;

import com.hxwl.wulinfeng.R;


/**
 * 功能:广告过滤的工具类
 * 作者：zjn on 2017/2/4 10:00
 */
public class ADFilterTool {

    public static boolean hasAd(Context context, String url) {
        Resources res = context.getResources();
        String[] adUrls = res.getStringArray(R.array.adBlockUrl);
        for (String adUrl : adUrls) {
            if (url.contains(adUrl)) {
                return true;
            }
        }
        return false;
    }
}
