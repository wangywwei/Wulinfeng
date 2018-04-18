package com.hxwl.wulinfeng.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hxwl.wulinfeng.MakerApplication;


/**
 * Created by Administrator on 2016/8/10.
 */
public class ListUtils {
    /** 动态改变listView的高度 */
    public static void setListViewHeightBasedOnChildren(ExpandableListView listView) {
        // 获取ListView对应的Adapter
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            // pre -condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getGroupCount(); i++) { // listAdapter.getCount()返回数据项的数目
            View listgroupItem = listAdapter .getGroupView(i, true, null, listView );
            listgroupItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listgroupItem .getMeasuredHeight(); // 统计所有子项的总高度
            for (int j = 0; j < listAdapter.getChildrenCount( i); j++) {
                View listchildItem = listAdapter .getChildView(i, j, false , null, listView);
                listchildItem.measure(0, 0); // 计算子项View 的宽高
                totalHeight += listchildItem.getMeasuredHeight(); // 统计所有子项的总高度
                System. out.println("height :" +"group:" +i +" child:"+j+"次"+ totalHeight);

            }
        }

        ViewGroup.LayoutParams params = listView .getLayoutParams();
        params.height = totalHeight + ( listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params );

    }
    /**
     * 可扩展listview展开时调用
     *
     * @param listView
     * @param groupPosition
     */
    public static void setExpandedListViewHeightBasedOnChildren(
            ExpandableListView listView, int groupPosition) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            return;
        }
        View listItem = listAdapter.getChildView(groupPosition, 0, true, null,
                listView);
        listItem.measure(0, 0);
        int appendHeight = 0;
        for (int i = 0; i < listAdapter.getChildrenCount(groupPosition); i++) {
            appendHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        Log.d(TAG, "Expand params.height" + params.height);
        params.height += appendHeight;
        listView.setLayoutParams(params);
    }

    /**
     * 可扩展listview收起时调用
     *
     * @param listView
     * @param groupPosition
     */
    public static void setCollapseListViewHeightBasedOnChildren(
            ExpandableListView listView, int groupPosition) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        if (listAdapter == null) {
            return;
        }
        View listItem = listAdapter.getChildView(groupPosition, 0, true, null,
                listView);
        listItem.measure(0, 0);
        int appendHeight = 0;
        for (int i = 0; i < listAdapter.getChildrenCount(groupPosition); i++) {
            appendHeight += listItem.getMeasuredHeight();
        }
        /*Log.d(TAG,
                "Collapse childCount="
                        + listAdapter.getChildrenCount(groupPosition));*/
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height -= appendHeight;
        listView.setLayoutParams(params);
    }

    public static void setListViewHeight(ExpandableListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        int count = listAdapter.getCount();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        // listAdapter.getCount()返回数据项的数目
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
            if(i==listAdapter.getCount()-1){
                totalHeight += ScreenUtils.dip2px(MakerApplication.instance(),50);
            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 计算GridView宽高
     * @param gridView
     */
    public static void calGridViewWidthAndHeigh(int numColumns ,GridView gridView) {

        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高

            if ((i+1)%numColumns == 0) {
                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }

            if ((i+1) == len && (i+1)%numColumns != 0) {
                totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
            }
        }

        totalHeight += 40;

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }
}
