package com.hxwl.utils;

import android.content.Context;

import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.SPUtils;

/**
 * Created by Administrator on 2018/3/6.
 */

public class SPchucunUtils {

    public static void setSousuoType(Context context,String string){
        SPUtils.put(context, "SousuoType",string);
    }
    public static String getSousuoType(Context context){
        return (String) SPUtils.get(context, "SousuoType","");
    }

}
