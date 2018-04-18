package com.hxwl.wulinfeng.util.json;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;




/**
 * 功能:fastjson 开源类库再封装
 *
 * zjn 2015-11-6
 */
public class JSONUtils {

	/**
	 * 功能:本程序专用解析方法，封装第一级字段
	 *
	 * @param ctx
	 * @param Result
	 * @return
	 */
	public static String getResultData(Activity ctx, String Result) {
		String strData = "";
		JSONResult jsonResult = JSONUtils.getJsonResult(Result);
		if (jsonResult.status.equals("200")) {
			strData = jsonResult.data;
		} else if (jsonResult.status.equals("500")) {
			strData = "";
			showToast(ctx, "服务器异常");
		} else {
			strData = "";
		}
		return strData;
	}

//	/**
//	 * 判断token是否有效
//	 *
//	 * @return true 为正常，false 为无效
//	 */
//	public static boolean isTokenEfficacious(String Result) {
//		JSONResult jsonResult = JSONUtils.getJsonResult(Result);
//		if ("0".equals(jsonResult.isUse)) {
//			return false;
//		}
//		return true;
//	}

	/**
	 *
	 * @param ctx
	 * @param msg
	 */
	private static void showToast(final Activity ctx, final String msg) {
		if ("main".equals(Thread.currentThread().getName())) {
			Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
		} else {
			ctx.runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	/**
	 * 按章节点得到相应的内容
	 *
	 * @param jsonString
	 *            json字符串
	 * @param note
	 *            节点
	 * @return 节点对应的内容
	 */
	public static String getNoteJson(String jsonString, String note) {
		if (!TextUtils.isEmpty(jsonString) && !TextUtils.isEmpty(note)) {
			JSONObject object = JSONObject.parseObject(jsonString);
			return object.getString(note);
		}
		return null;
	}

//	/**
//	 * 把相对应节点的内容封装为对象
//	 *
//	 * @param jsonString
//	 *            json字符串
//	 * @param beanClazz
//	 *            要封装成的目标对象
//	 * @return 目标对象
//	 */
//	public static <T> T parserObject(String jsonString, String note,
//									 Class<T> beanClazz) {
//		if (jsonString != null) {
//			String noteJson = getNoteJson(jsonString, note);
//			T object = JSON.parseObject(noteJson, beanClazz);
//			return object;
//		}
//		return null;
//	}

//	/**
//	 * 按照节点得到节点内容，转化为一个数组
//	 *
//	 * @param jsonString
//	 *            json字符串
//	 * @param beanClazz
//	 *            集合里存入的数据对象
//	 * @return 含有目标对象的集合
//	 */
//	public static <T> List<T> parserArray(String jsonString, String note,
//										  Class<T> beanClazz) {
//		if (!TextUtils.isEmpty(jsonString) && !TextUtils.isEmpty(note)) {
//			List<T> objects = new ArrayList<T>();
//			String noteJson = getNoteJson(jsonString, note);
//			if (!TextUtils.isEmpty(noteJson)) {
//				objects = JSON.parseArray(noteJson, beanClazz);
//			} else {
//				return null;
//			}
//
//			return objects;
//		}
//		return null;
//	}

	/**
	 * 把对象转化为json字符串
	 *
	 * @param obj
	 *            要转化的对象
	 * @return 返回json字符串
	 */
	public static String toJsonString(Object obj) {
		if (obj != null) {
			return GsonUtil.GsonString(obj) ;
		} else {
			throw new RuntimeException("object is not null");
		}
	}

	public static <T> T jsonString2Bean(String jsonString, Class<T> beanClazz) {
		T object = null;
		if (jsonString == null) {
			return null;
		}
		object = GsonUtil.GsonToBean(jsonString, beanClazz) ;
		return object;
	}

//	/**
//	 * 把jsonString转化为多个bean对象数组
//	 *
//	 * @param jsonString
//	 * @param beanClazz
//	 * @return
//	 */
//	public static <T> List<T> jsonString2Beans(String jsonString,
//											   Class<T> beanClazz) {
//		if (jsonString == null) {
//			return null;
//		}
//		List<T> objects = new ArrayList<T>();
//		objects = JSON.parseArray(jsonString, beanClazz);
//		return objects;
//	}

	/**
	 * 获取jsonResult信息
	 *
	 * @param jsonString
	 * @return
	 */
	public static JSONResult getJsonResult(String jsonString) {
		if (jsonString.equals("")) {
			return null;
		}
		JSONResult jsonResult = jsonString2Bean(jsonString,JSONResult.class);
		return jsonResult;
	}

//	/**
//	 * 加密
//	 *
//	 * @param jsonString
//	 * @return
//	 */
//	public static String encryptDES(String jsonString) {
//		return jsonString;
//	}
//
//	/**
//	 * 解密
//	 *
//	 * @param jsonString
//	 * @return
//	 */
//	public static String decryptDES(String jsonString) {
//
//		try {
//			return DESTools.decode(jsonString);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}
}
