/**
 * Project Name:JZGPingGuShi
 * File Name:AppManager.java
 * Package Name:com.gc.jzgpinggushi.app
 * Date:2014-9-1上午10:18:48
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 */

package com.hxwl.wulinfeng;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 */
public class AppManager {
    private static Stack<Activity> activityStack;

    private static AppManager instance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public synchronized void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity(Class<?> cls) {
        List<Activity> targetActivityList = new ArrayList<>();
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                targetActivityList.add(activity);
                activity.finish();
            }
        }
        if (!targetActivityList.isEmpty())
            activityStack.removeAll(targetActivityList);
    }
    public void finishActivityforName(String className) {
        List<Activity> targetActivityList = new ArrayList<>();
        for (Activity activity : activityStack) {
            if (activity.getClass().getName().equals(className)) {
                targetActivityList.add(activity);
                activity.finish();
            }
        }
        if (!targetActivityList.isEmpty())
            activityStack.removeAll(targetActivityList);
    }
    public boolean isInActivityStack(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     *
     * @param className 不需要结束的activity的名字
     */
    public void finishAllActivitys(String className) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)&&!activityStack.get(i).getClass().getName().equals(className)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
