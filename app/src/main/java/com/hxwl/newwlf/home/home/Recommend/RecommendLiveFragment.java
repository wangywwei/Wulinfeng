package com.hxwl.newwlf.home.home.Recommend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;


public class RecommendLiveFragment extends BaseFragment {


    private TextView live_title;
    private TextView live_time;
    private ImageView live_touxiang;
    private TextView live_name;
    private TextView live_julebu;
    private ImageView live_boolean;
    private ImageView live_touxiang2;
    private TextView live_name2;
    private TextView live_julebu2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_live, null);
        initView(view);

        return view;
    }


    private void initView(View view) {
        live_title = (TextView) view.findViewById(R.id.live_title);
        live_time = (TextView) view.findViewById(R.id.live_time);
        live_touxiang = (ImageView) view.findViewById(R.id.live_touxiang);
        live_name = (TextView) view.findViewById(R.id.live_name);
        live_julebu = (TextView) view.findViewById(R.id.live_julebu);
        live_boolean = (ImageView) view.findViewById(R.id.live_boolean);
        live_touxiang2 = (ImageView) view.findViewById(R.id.live_touxiang2);
        live_name2 = (TextView) view.findViewById(R.id.live_name2);
        live_julebu2 = (TextView) view.findViewById(R.id.live_julebu2);
    }
}
