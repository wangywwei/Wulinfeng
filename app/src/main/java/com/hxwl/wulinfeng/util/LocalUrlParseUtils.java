package com.hxwl.wulinfeng.util;

import com.hxwl.common.utils.StringUtils;

import java.util.HashMap;

/**
 * 功能:本地URL解析工具类
 * 作者：zjn on 2017/3/16 14:09
 */

public class LocalUrlParseUtils {

    /**
     * 解析URL
     * @param params
     * @return
     */
    public static HashMap<String, String> parseUrlParams(String params) {
        if (StringUtils.isEmpty(params)) {
            return null;
        }
        HashMap hasmap = new HashMap();
        String key = "";
        if (params.contains("http") || params.contains("https")) {
            if (params.contains("?")) {
                String str = params.substring(params.indexOf("?") + 1);
                String[] strs = str.split("&");
                for (int i = 0; i < strs.length; i++) {
                    if (strs[i].contains("=")) {
                        key = strs[i].substring(0, strs[i].indexOf("="));
                        String value = strs[i].substring(strs[i].indexOf("=") + 1);
                        hasmap.put(key, value);
                    } else {
                        key = strs[i];
                        hasmap.put(key, "");
                    }
                }
            } else {
                String[] strs = params.split("&");
                for (int i = 0; i < strs.length; i++) {
                    if (strs[i].contains("=")) {
                        key = strs[i].substring(0, strs[i].indexOf("="));
                        String value = strs[i].substring(strs[i].indexOf("=") + 1);
                        hasmap.put(key, value);
                    } else {
                        key = strs[i];
                        hasmap.put(key, "");
                    }
                }
            }
        } else {
            String[] strs = params.split("&");
            for (int i = 0; i < strs.length; i++) {
                if (strs[i].contains("=")) {
                    key = strs[i].substring(0, strs[i].indexOf("="));
                    String value = strs[i].substring(strs[i].indexOf("=") + 1);
                    hasmap.put(key, value);
                } else {
                    key = strs[i];
                    hasmap.put(key, "");
                }
            }
        }
        return hasmap;
    }
}
