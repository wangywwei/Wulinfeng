package com.hxwl.newwlf.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.ClubBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.ClubDetailsActivity;
import com.hxwl.wulinfeng.adapter.WuZhanAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/1/24.
 */

public class WIFFragment extends BaseFragment {
    private View view;
    private ListView lv_listview;
    private CommonSwipeRefreshLayout common_layout;
    private List<ClubBean.DataBean> listData = new ArrayList<>();

    private int page = 1;
    private boolean isRefresh;
    private WuZhanAdapter wuZhanAdapter;
    private EmptyViewHelper emptyViewHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.wuzhanlm_activity, null);
            initView(view);
            initData(page);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    private void initData(int page) {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_CULBR)
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        common_layout.setRefreshing(false);
                        common_layout.setLoadMore(false);
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                ClubBean bean = gson.fromJson(response, ClubBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if (isRefresh) {//是加载更多
                                        listData.clear();
                                        listData.addAll(bean.getData());
                                    } else {//刷新或是第一次进来
                                        listData.addAll(bean.getData());
                                    }
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    wuZhanAdapter.notifyDataSetChanged();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });


    }

    private void initView(View view) {

        lv_listview = (ListView) view.findViewById(R.id.lv_listview);
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                startActivity(ClubDetailsActivity.getIntent(getActivity(), listData.get(i).getId() + ""));
            }
        });
        wuZhanAdapter = new WuZhanAdapter(getActivity(), listData);
        lv_listview.setAdapter(wuZhanAdapter);
        emptyViewHelper = new EmptyViewHelper(lv_listview, (FrameLayout) view.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initData(page);
            }
        });

        common_layout = (CommonSwipeRefreshLayout) view.findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
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
    }
}
