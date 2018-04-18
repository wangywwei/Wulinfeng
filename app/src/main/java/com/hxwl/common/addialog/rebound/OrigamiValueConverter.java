package com.hxwl.common.addialog.rebound;

/**
 * 功能:
 * 作者：zjn on 2017/4/24 11:38
 */

/**
 * Helper math util to convert tension & friction values from the Origami design tool to values that
 * the spring system needs.
 */
public class OrigamiValueConverter {

    public static double tensionFromOrigamiValue(double oValue) {
        return oValue == 0 ? 0 : (oValue - 30.0) * 3.62 + 194.0;
    }

    public static double origamiValueFromTension(double tension) {
        return tension == 0 ? 0 : (tension - 194.0) / 3.62 + 30.0;
    }

    public static double frictionFromOrigamiValue(double oValue) {
        return oValue == 0 ? 0 : (oValue - 8.0) * 3.0 + 25.0;
    }

    public static double origamiValueFromFriction(double friction) {
        return friction == 0 ? 0 : (friction - 25.0) / 3.0 + 8.0;
    }

}

