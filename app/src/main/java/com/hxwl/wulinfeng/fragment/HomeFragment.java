package com.hxwl.wulinfeng.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;

/**
 * Created by Allen on 2017/5/26.
 */

public class HomeFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mhomepage = inflater.inflate(R.layout.frag_homepage, container, false);
        initView() ;
        initData();
        return mhomepage ;
    }

    private void initData() {

    }

    private void initView() {

    }
}
