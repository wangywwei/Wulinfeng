package com.hxwl.wulinfeng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
/**
 * Created by Allen on 2017/6/15.
 */

public class ScrollViewX extends ScrollView {
    private static final long DELAY = 100;

    private int currentScroll;

    private Runnable scrollCheckTask;
    public ScrollViewX(Context context) {
        super(context);
    }

    public ScrollViewX(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewX(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init() {
        scrollCheckTask = new Runnable() {
            @Override
            public void run() {
                int newScroll = getScrollY();
                if (currentScroll == newScroll) {
                    if (onScrollListener != null) {
                        onScrollListener.onScrollStopped();
                    }
                } else {
                    if (onScrollListener != null) {
                        onScrollListener.onScrolling();
                    }
                    currentScroll = getScrollY();
                    postDelayed(scrollCheckTask, DELAY);
                }
            }
        };
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    currentScroll = getScrollY();
                    postDelayed(scrollCheckTask, DELAY);
                }
                return false;
            }
        });
    }

    public interface OnScrollListener {
        public void onScrollChanged(int x, int y, int oldX, int oldY);

        public void onScrollStopped();

        public void onScrolling();
    }

    private OnScrollListener onScrollListener;

    /**
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (onScrollListener != null) {
            onScrollListener.onScrollChanged(x, y, oldX, oldY);
        }
    }

    /**
     * @param child
     * @return
     */
    public boolean isChildVisible(View child) {
        if (child == null) {
            return false;
        }
        Rect scrollBounds = new Rect();
        getHitRect(scrollBounds);
        return child.getLocalVisibleRect(scrollBounds);
    }

    /**
     * @return
     */
    public boolean isAtTop() {
        return getScrollY() <= 0;
    }

    /**
     * @return
     */
    public boolean isAtBottom() {
        return getChildAt(getChildCount() - 1).getBottom() + getPaddingBottom() == getHeight() + getScrollY();
    }
}
