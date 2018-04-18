package com.hxwl.wulinfeng.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.bean.SaichengBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.key;
import static android.os.Build.ID;

/**
 * Created by Administrator on 2016/8/2.
 * sharep- 工具类 控制读取sp文件
 */
public class SPUtils {
        /**
         * 保存在手机里面的文件名
         */
        public static final String SP_NAME = "config";
        public static final String SP_MAKE_SAICHENG = "yuyue";
        public static final String SP_MAKE_YUYUELIST = MakerApplication.instance().getUid()+"yuyue_list";
        public static final int SP_MODE = Context.MODE_PRIVATE;
        private static SharedPreferences sp;
        private static Editor editor;


        /**
         * 预约专用sp
         * @return 是否添加成功（可以使用apply提交）
         */
        public static boolean putYuYueInfo(Context context, SaichengBean bean) {
            SharedPreferences sp  = context.getSharedPreferences(SP_MAKE_SAICHENG, SP_MODE);
            String spString =  sp.getString(SP_MAKE_YUYUELIST, "");
            if(TextUtils.isEmpty(bean.getSaicheng_id())){
                bean.setSaicheng_id(bean.getId());
            }
            if(TextUtils.isEmpty(spString)){
                List<SaichengBean> listData = new ArrayList<>();
                listData.add(bean) ;
                String mapListString = JSON.toJSONString(listData);
//                ToastUtils.showToast(context,"保存预约信息成功");
                return sp.edit().putString(SP_MAKE_YUYUELIST,mapListString).commit() ;
            }else{
                List<SaichengBean> listData = JSON.parseArray(spString,SaichengBean.class) ;
                listData.add(bean) ;
                String mapListString = JSON.toJSONString(listData);
//                ToastUtils.showToast(context,"保存预约信息成功");
                return sp.edit().putString(SP_MAKE_YUYUELIST,mapListString).commit() ;
            }
        }
    public static List<SaichengBean> getYuYueInfo(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(SP_MAKE_SAICHENG,
                SP_MODE);
         String spString =  sp.getString(SP_MAKE_YUYUELIST, "");
        if(TextUtils.isEmpty(spString)){
            return null ;
        }
        List<SaichengBean> listData = JSON.parseArray(spString,SaichengBean.class) ;
        return listData ;
    }
    public static void clearYuYueInfo(Context context ,SaichengBean bean)
    {
        SharedPreferences sp = context.getSharedPreferences(SP_MAKE_SAICHENG,
                SP_MODE);
         String spString =  sp.getString(SP_MAKE_YUYUELIST, "");
        if(TextUtils.isEmpty(spString)){
            return ;
        }
        List<SaichengBean> listData = JSON.parseArray(spString,SaichengBean.class) ;
        for (SaichengBean info : listData ){
            if(info != null){
                if(info.getSaicheng_id().equals(bean.getSaicheng_id())){
                    listData.remove(info);
                    break ;
                }
            }
        }
        String mapListString = JSON.toJSONString(listData);
        sp.edit().putString(SP_MAKE_YUYUELIST,mapListString).commit() ;
    }
        /**
         * 根据类型调用不同的保存方法
         *
         * @param context 上下文
         * @param key     添加的键
         * @param value   添加的值
         * @return 是否添加成功（可以使用apply提交）
         */
        public static boolean put(Context context, String key, Object value) {
            if (sp == null) {
                sp = context.getSharedPreferences(SP_NAME, SP_MODE);
            }
            editor = sp.edit();
            if (value == null) {
                editor.putString(key, null);
            } else if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);
            } else if (value instanceof Float) {
                editor.putFloat(key, (Float) value);
            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);
            } else {
                editor.putString(key, value.toString());
            }
            return editor.commit();
        }


    public static Object get(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME,
                SP_MODE);

        if (defaultObject instanceof String)
        {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer)
        {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean)
        {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float)
        {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long)
        {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }


        public static Map<String, ?> getAll(Context context) {
            if (sp == null) {
                sp = context.getSharedPreferences(SP_NAME, SP_MODE);
            }
            Map<String, ?> all = sp.getAll();
            return all;
        }

    /**
     * 清除数据
     *
     * @param context
     * @return 是否清除成功(如果不关注结果，可以使用apply)
     */
    public static boolean clear(Context context) {
        return false;
    }


    /**

     * 删除信息
     * @throws Exception
     */

    public static  void deleteAll() throws Exception {
        editor.clear();
        editor.commit();
    }

    public static void put(String isFirst, String s) {
    }
}