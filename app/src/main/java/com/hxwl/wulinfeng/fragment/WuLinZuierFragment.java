package com.hxwl.wulinfeng.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.ZiXunAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.HomeBannerImageBean;
import com.hxwl.wulinfeng.bean.ZiXunBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Allen on 2017/5/26.
 * 资讯fragment界面
 */

public class WuLinZuierFragment extends BaseFragment {
    private Activity activity;
    private ListView recycl_view;
    private View frag_zixun;
    private RelativeLayout ll_pb;
    private CommonSwipeRefreshLayout common_layout;
    private int page = 1;
    private boolean isRefresh;
    private String num;
    private List<ZiXunBean> listData = new ArrayList<>();
    private ZiXunAdapter ziXunAdapter;

    private List<HomeBannerImageBean> data2EntityList = new ArrayList<>();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        frag_zixun = inflater.inflate(R.layout.frag_zixun, container, false);
        initView();
        initData();
        return frag_zixun;
    }

    private void initData() {
        getZixunData(page);

    }

    public void getZixunData(int page) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if (isRefresh) {//是加载更多
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(), ZiXunBean.class));
                    } else {//刷新或是第一次进来
                        listData.addAll(JSON.parseArray(result.getData(), ZiXunBean.class));
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    ziXunAdapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {
                    common_layout.setRefreshing(false);
                    UIUtils.showToast("服务器异常");
                }
            }
        }, "method=" + NetUrlUtils.ziXun_news + page);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page + "");
        map.put("num", num);
//        map.put("keyword" ,num );
//        map.put("saishiId" ,page); // 赛事编号 做过滤用的
        map.put("method", NetUrlUtils.ziXun_news);
        tasker.execute(map);

//        Map<String, Object> map = new HashMap<>();
//        map.put("page", page + "");
//        map.put("num", num);
////        map.put("keyword" ,num );
////        map.put("saishiId" ,page); // 赛事编号 做过滤用的
//        try {
//            AppClient.okhttp_post_Asyn(NetUrlUtils.ziXun_news, map, new ICallback() {
//                @Override
//                public void error(IOException e) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            common_layout.setRefreshing(false);
//                            UIUtils.showToast("服务器异常");
//                        }
//                    });
//                }
//
//                @Override
//                public void success(ResultPacket result) {
//                    if (result.getStatus() != null && result.getStatus().equals("ok")) {
//                        if (isRefresh) {//是加载更多
//                            listData.clear();
//                            listData.addAll(JSON.parseArray(result.getData(), ZiXunBean.class));
//                        } else {//刷新或是第一次进来
//                            listData.addAll(JSON.parseArray(result.getData(), ZiXunBean.class));
//                        }
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                common_layout.setRefreshing(false);
//                                common_layout.setLoadMore(false);
//                                ziXunAdapter.notifyDataSetChanged();
//                            }
//                        });
//                    } else {
//
//                    }
//                }
//            });
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
    }

    private void initView() {
        recycl_view = (ListView) frag_zixun.findViewById(R.id.xlv_toutiao_content);

        ll_pb = (RelativeLayout) frag_zixun.findViewById(R.id.ll_pb);
        common_layout = (CommonSwipeRefreshLayout) frag_zixun.findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                getZixunData(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                getZixunData(page);
            }
        });
        ziXunAdapter = new ZiXunAdapter(getActivity(), listData);
        recycl_view.setAdapter(ziXunAdapter);
//        recycl_view.setDetalisCallBack(this);
        recycl_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ZiXunBean dataBean = null;
                if (view instanceof TextView) {
                    dataBean = (ZiXunBean) view.getTag();
                } else {
                    dataBean = (ZiXunBean) view.findViewById(R.id.tv_title).getTag();
                }
                if (dataBean == null) {
                    return;
                }

                ToastUtils.showToast(activity, dataBean.getTitle());
            }
        });
    }

}
