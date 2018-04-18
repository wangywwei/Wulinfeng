package com.hxwl.wulinfeng.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.hxwl.wulinfeng.adapter.ImpZuiXinAdapter;
import com.hxwl.wulinfeng.adapter.ZiXunAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.HomeBannerImageBean;
import com.hxwl.wulinfeng.bean.ZiXunBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.util.json.GsonUtil;
import com.hxwl.wulinfeng.view.SpaceItemDecoration;
import com.tendcloud.tenddata.TCAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

import static com.hxwl.wulinfeng.R.layout.frag_saicheng;
import static com.hxwl.wulinfeng.R.layout.frag_tuji;

/**
 * Created by Allen on 2017/5/26.
 * 资讯fragment界面
 */

public class ZiXunFragment extends BaseFragment {
    private Activity activity;
    private ListView recycl_view;
    private View frag_zixun;
    private RelativeLayout ll_pb;
    private CommonSwipeRefreshLayout common_layout;
    private int page = 1;
    private boolean isRefresh = true;
    private String num;
    private List<ZiXunBean> listData = new ArrayList<>();
    private ZiXunAdapter ziXunAdapter;

    private List<HomeBannerImageBean> data2EntityList = new ArrayList<>();
    private EmptyViewHelper emptyViewHelper;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    //共外部调用
    public void resetData() {
        if (frag_zixun != null){
            page = 1;
            isRefresh = true;
            initData(page);
        }
    }

    public void initData(int page) {
        if (frag_zixun != null){
            getZixunData(page);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_zixun == null) {
            frag_zixun = inflater.inflate(R.layout.frag_zixun, container, false);
            initView();
            initData(page);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_zixun.getParent();
            if (parent != null) {
                parent.removeView(frag_zixun);
            }
        }
        return frag_zixun;
    }

    public void getZixunData(final int page) {
        if (!SystemHelper.isConnected(activity)) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    if (isRefresh && page == 1) {//是加载更多
                        listData.clear();
                        listData.addAll(JSON.parseArray(result.getData(), ZiXunBean.class));
                    } else {//刷新或是第一次进来
                        listData.addAll(JSON.parseArray(result.getData(), ZiXunBean.class));
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    ziXunAdapter.notifyDataSetChanged();
                } else if (result.getStatus() != null && result.getStatus().equals("empty")) {
                    if (isRefresh && page == 1) {
                        listData.clear();
                        ziXunAdapter.notifyDataSetChanged();
                    }
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    UIUtils.showToast("没有更多了");
                } else {
                    common_layout.setRefreshing(false);
                    common_layout.setLoadMore(false);
                    emptyViewHelper.setType(1);
                }
            }
        }, "method=" + NetUrlUtils.ziXun_news + page + getType());
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("page", page + "");
        map.put("num", num);
        map.put("saishiId", getType()); // 赛事编号 做过滤用的
        map.put("method", NetUrlUtils.ziXun_news);
        tasker.execute(map);

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
        emptyViewHelper = new EmptyViewHelper(recycl_view,(FrameLayout)frag_zixun.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                getZixunData(page);
            }
        });
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
                Intent intent = new Intent(getActivity(), ZiXunDetailsActivity.class);
                intent.putExtra("id", dataBean.getId());
                startActivity(intent);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(activity, "资讯");
        TCAgent.onPageStart(activity, "资讯");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(activity,"资讯");
        TCAgent.onPageEnd(activity,"资讯");
    }


}
