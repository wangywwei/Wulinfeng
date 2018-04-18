package com.hxwl.wulinfeng.swipebacklayout.lib.app;


import com.hxwl.wulinfeng.swipebacklayout.lib.SwipeBackLayout;

/**
 * @author yukun 时间：5-22 11：01
 */
public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();

}
