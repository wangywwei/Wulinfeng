package com.hxwl.newwlf.home.home.Recommend;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.view.HoRecyclerView;

import java.util.ArrayList;


public class TabFragment extends BaseFragment {
    private View view;
    private HoRecyclerView tab_recyclerview;
    private HomeRecommendBean.DataBean bean;
    private TabRecyclerAdapter adapter;
    private ArrayList<HomeRecommendBean.DataBean.PlayBackVideoListBean> list = new ArrayList<>();

    public void setBean(HomeRecommendBean.DataBean bean) {
        this.bean = bean;
        if (bean!=null){
            list.clear();
            list.addAll(bean.getPlayBackVideoList());
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tab2, container, false);
            initView(view);

        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }

        return view;

    }


    private void initView(View view) {
        tab_recyclerview= (HoRecyclerView) view.findViewById(R.id.tab_recyclerview);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tab_recyclerview.setLayoutManager(linearLayoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(tab_recyclerview);
        adapter=new TabRecyclerAdapter(getActivity(),list);
        tab_recyclerview.setAdapter(adapter);

        adapter.setOnItemclickLinter(new TabRecyclerAdapter.OnItemclickLinter() {
            @Override
            public void onItemClicj(int position) {
                getActivity().startActivity(HuiGuDetailActivity.getIntent(getActivity(),bean.getPlayBackVideoList().get(position).getScheduleId()+"",0));
            }
        });
    }
}
