package com.hxwl.wulinfeng.util;

import com.hxwl.wulinfeng.bean.NianFenYueBean;

import java.util.Comparator;

/**
 * Created by Allen on 2017/6/22.
 */

public class YueFenComp implements Comparator<String> {
    @Override
    public int compare(String yuefen1, String yuefen2) {
        return Integer.parseInt(yuefen2) - (Integer.parseInt(yuefen1));
    }
}
