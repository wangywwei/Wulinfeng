package com.hxwl.common.swiperefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Allen on 2017/6/13.
 * 解决滑动问题
 */

public class SimpleSwipeRefreshLayout  extends CommonSwipeRefreshLayout {
    private View view;
    public SimpleSwipeRefreshLayout(Context context) {
        super(context);
    }

    public SimpleSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setViewGroup(View view) {
        this.view = view;
    }

    @Override
    public boolean isChildScrollToTop() {
        if (view != null && view instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) view;
            return absListView.getChildCount() > 0
                    && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                    .getTop() < absListView.getPaddingTop());
        }
        return super.isChildScrollToTop();
    }
}
