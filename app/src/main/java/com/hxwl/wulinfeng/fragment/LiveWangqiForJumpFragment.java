package com.hxwl.wulinfeng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.LiveDetailActivity;
import com.hxwl.wulinfeng.activity.WangQiShipinActivity;
import com.hxwl.wulinfeng.adapter.VideoWangQiAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.WQShiPinBean;
import com.hxwl.wulinfeng.bean.WangQiSPBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 直播模块的往期
 */
public class LiveWangqiForJumpFragment extends BaseFragment {

    private View mwangqi;
    private ListView lv_listview;
    private List<WQShiPinBean> listData = new ArrayList<>();
    private CommonSwipeRefreshLayout common_layout;
    private int page = 1;
    private boolean isRefresh = true;
    private String num;
    private String saichengId;
    private String saishiId;
    private WangQiSPBean bean;
    private VideoWangQiAdapter zhiboAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mwangqi == null) {
            mwangqi = inflater.inflate(R.layout.live_wangqi_layout, container, false);
            initView();
            initData(page);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mwangqi.getParent();
            if (parent != null) {
                parent.removeView(mwangqi);
            }
        }
        return mwangqi;

//        mwangqi = inflater.inflate(R.layout.live_wangqi_layout, container, false);
//        initView();
//        initData(page);
//        return mwangqi;
    }

    private void initView() {
        common_layout = (CommonSwipeRefreshLayout) mwangqi.findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(false);
        common_layout.setLoadingMoreEnabled(false);
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
        lv_listview = (ListView) mwangqi.findViewById(R.id.lv_listview);
        zhiboAdapter = new VideoWangQiAdapter(getActivity(), listData);
        lv_listview.setAdapter(zhiboAdapter);
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WQShiPinBean dataBean = null;
                if (view instanceof TextView) {
                    dataBean = (WQShiPinBean) view.getTag();
                } else {
                    dataBean = (WQShiPinBean) view.findViewById(R.id.tv_title).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                initSelectView();
                dataBean.setSelect(true);
                zhiboAdapter.notifyDataSetChanged();
//                if (getActivity() instanceof LiveDetailActivity) {
//                    ((LiveDetailActivity) getActivity()).playVideo(dataBean);
//                } else if (getActivity() instanceof HuiGuDetailActivity) {
//                    ((HuiGuDetailActivity) getActivity()).playVideo(dataBean);
//                }
                StatService.onEvent(getActivity(),"VedioPlayTimes","视频播放次数",1);
                TCAgent.onEvent(getActivity(),"VedioPlayTimes","视频播放次数");
                Intent intent = new Intent(getActivity(), HuiGuDetailActivity.class);
                intent.putExtra("saichengId", dataBean.getSaicheng_id());
                intent.putExtra("saishiId", dataBean.getSaishi_id());
                intent.putExtra("name", dataBean.getName());
                intent.putExtra("pageType", "bisaishipin");
                startActivity(intent);
            }
        });
    }

    private void initData(int page) {
        if (getActivity() instanceof HuiGuDetailActivity) {
            saichengId = ((HuiGuDetailActivity) getActivity()).getSaichengId();
            saishiId = ((HuiGuDetailActivity) getActivity()).getSaishiId();
        } else if (getActivity() instanceof LiveDetailActivity) {
            saichengId = ((LiveDetailActivity) getActivity()).getSaichengId();
            saishiId = ((LiveDetailActivity) getActivity()).getSaishiId();
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    bean = JSON.parseObject(result.getData(), WangQiSPBean.class);
                    if (isRefresh) {
                        listData.clear();
                        listData.addAll(bean.getHuifang());
                    } else {
                        listData.addAll(bean.getHuifang());
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    zhiboAdapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    UIUtils.showToast("没有更多了");
                } else {
                    common_layout.setRefreshing(false);
                    UIUtils.showToast("服务器异常");
                }
            }
        }, "method=" + NetUrlUtils.video_wangqi + page + saishiId);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("saishiId", saishiId);
        map.put("page", page);
        map.put("method", NetUrlUtils.video_wangqi);
        tasker.execute(map);

    }

    private void initSelectView() {
        for (int i = 0; i < listData.size(); i++) {
            listData.get(i).setSelect(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "比赛视频详情-往期");
        TCAgent.onPageStart(getActivity(), "比赛视频详情-往期");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(),"比赛视频详情-往期");
        TCAgent.onPageEnd(getActivity(),"比赛视频详情-往期");
    }
}
