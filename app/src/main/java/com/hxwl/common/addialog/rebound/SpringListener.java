package com.hxwl.common.addialog.rebound;

/**
 * 功能:
 * 作者：zjn on 2017/4/24 11:31
 */

public interface SpringListener {

    /**
     * called whenever the spring is updated
     * @param spring the Spring sending the update
     */
    void onSpringUpdate(Spring spring);

    /**
     * called whenever the spring achieves a resting state
     * @param spring the spring that's now resting
     */
    void onSpringAtRest(Spring spring);

    /**
     * called whenever the spring leaves its resting state
     * @param spring the spring that has left its resting state
     */
    void onSpringActivate(Spring spring);

    /**
     * called whenever the spring notifies of displacement state changes
     * @param spring the spring whose end state has changed
     */
    void onSpringEndStateChange(Spring spring);
}