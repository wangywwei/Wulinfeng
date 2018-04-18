package com.hxwl.wulinfeng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.TuJiDetailsActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.hxwl.wulinfeng.adapter.TuJiAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.bean.ZiXunBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

import static com.hxwl.wulinfeng.R.id.listview;
import static com.hxwl.wulinfeng.R.layout.frag_saicheng;
import static com.hxwl.wulinfeng.R.string.phone;


/**
 * Created by Allen on 2017/5/26.
 * 首页 -- 图集fragment
 */

public class TuJiFragment extends BaseFragment {

    private View frag_tuji;
    private ListView lv_listview;
    private List<TuJiBean> listData = new ArrayList<>();
    private TuJiAdapter tujiAdapter;
    private CommonSwipeRefreshLayout common_layout;
    private int page = 1;
    private boolean isRefresh = true;
    private String num;
    private EmptyViewHelper emptyViewHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_tuji == null) {
            frag_tuji = inflater.inflate(R.layout.frag_tuji, container, false);
            initView();
            initData(page);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_tuji.getParent();
            if (parent != null) {
                parent.removeView(frag_tuji);
            }
        }
        return frag_tuji;

//        frag_tuji = inflater.inflate(R.layout.frag_tuji, container, false);
//        initView();
//        initData(page);
//        return frag_tuji;
    }

    //共外部调用
    public void resetData() {
        if (frag_tuji != null) {
            page = 1;
            isRefresh = true;
            initData(page);
        }
    }

    public void initData(final int page) { //ziXun_tuji
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }
        if (frag_tuji == null) {
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if (isRefresh && page == 1) {//是刷新
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(), TuJiBean.class));
                    } else {//不是刷新
                        listData.addAll(JSON.parseArray(result.getData(), TuJiBean.class));
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    tujiAdapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    ToastUtils.showToast(getActivity(), "没有更多了");
                } else {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                }
            }
        }, "method=Home.imgList" + page);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        params.put("num", num);
        params.put("saishiId", getType());
        params.put("method", "Home/imgList");
        tasker.execute(params);

    }

    private void initView() {
        common_layout = (CommonSwipeRefreshLayout) frag_tuji.findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                initData(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initData(page);
            }
        });
        lv_listview = (ListView) frag_tuji.findViewById(R.id.lv_listview);
        tujiAdapter = new TuJiAdapter(getActivity(), listData);
        lv_listview.setAdapter(tujiAdapter);

        emptyViewHelper = new EmptyViewHelper(lv_listview,(FrameLayout)frag_tuji.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initData(page);
            }
        });

        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TuJiBean dataBean = null;
                if (view instanceof TextView) {
                    dataBean = (TuJiBean) view.getTag();
                } else {
                    dataBean = (TuJiBean) view.findViewById(R.id.tv_title).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), TuJiDetailsActivity.class);
                intent.putExtra("id", dataBean.getId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "图集");
        TCAgent.onPageStart(getActivity(), "图集");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(), "图集");
        TCAgent.onPageEnd(getActivity(), "图集");
    }
}
