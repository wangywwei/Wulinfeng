package com.hxwl.wulinfeng.util;

import android.text.TextUtils;
import android.util.Log;

public class LogUtils {

	private static final boolean VERBOSE = true;
	private static final boolean DEBUG = true;
	private static final boolean INFO = true;
	private static final boolean WARN = true;
	private static final boolean ERROR = true;
	private static final String SEPARATOR = ",";

	public static void v(String message) {
		if (VERBOSE) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.v(tag, message);
		}
	}

	public static void v(String tag, String message) {
		if (VERBOSE) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			Log.v(tag, message);
		}
	}

	public static void d(String message) {
		if (DEBUG) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.d(tag, message);
		}
	}

	public static void d(String tag, String message) {
		if (DEBUG) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			Log.d(tag, message);
		}
	}

	public static void i(String message) {
		if (INFO) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.i(tag, message);
		}
	}

	public static void i(String tag, String message) {
		if (INFO) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			Log.i(tag, message);
		}
	}

	public static void w(String message) {
		if (WARN) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.w(tag, message);
		}
	}

	public static void w(String tag, String message) {
		if (WARN) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			Log.w(tag, message);
		}
	}

	public static void e(String message) {
		if (ERROR) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			String tag = getDefaultTag(stackTraceElement);
			Log.e(tag, message);
		}
	}

	public static void e(String tag, String message) {
		if (ERROR) {
			StackTraceElement stackTraceElement = Thread.currentThread()
					.getStackTrace()[3];
			if (TextUtils.isEmpty(tag)) {
				tag = getDefaultTag(stackTraceElement);
			}
			Log.e(tag, message);
		}
	}

	/**
	 * 获取默认的TAG名称. 比如在MainActivity.java中调用了日志输出. 则TAG为MainActivity
	 */
	public static String getDefaultTag(StackTraceElement stackTraceElement) {
		String fileName = stackTraceElement.getFileName();
		String stringArray[] = fileName.split("\\.");
		String tag = stringArray[0];
		return tag;
	}

}
