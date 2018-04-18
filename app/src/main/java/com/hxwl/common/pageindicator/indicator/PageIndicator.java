package com.hxwl.common.pageindicator.indicator;

/**
 * 功能:页面指示器
 * 作者：zjn on 2017/4/24 10:59
 */
import android.support.v4.view.ViewPager;

public interface PageIndicator extends ViewPager.OnPageChangeListener {
    /** bind ViewPager */
    void setViewPager(ViewPager vp);

    /** for special viewpager,such as LooperViewPager */
    void setViewPager(ViewPager vp, int realCount);

    void setCurrentItem(int item);
}
