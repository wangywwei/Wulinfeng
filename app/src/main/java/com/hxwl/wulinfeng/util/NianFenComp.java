package com.hxwl.wulinfeng.util;

import com.hxwl.wulinfeng.bean.NianFenYueBean;

import java.util.Comparator;

/**
 * Created by Allen on 2017/6/22.
 */

public class NianFenComp implements Comparator<NianFenYueBean> {
    @Override
    public int compare(NianFenYueBean nianFenYueBean1, NianFenYueBean nianFenYueBean2) {
        return Integer.parseInt(nianFenYueBean2.getYear()) - (Integer.parseInt(nianFenYueBean1.getYear()));
    }
}
