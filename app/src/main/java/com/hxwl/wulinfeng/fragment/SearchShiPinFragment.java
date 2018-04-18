package com.hxwl.wulinfeng.fragment;

import android.annotation.SuppressLint;
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
import com.hxwl.newwlf.home.home.intent.DuizhengVideoActivity;
import com.hxwl.newwlf.modlebean.SearchduizhenBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.ShiPinSearchAdapter;
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
 * Created by Allen on 2017/6/28.
 * 搜索结果 -- 视频
 */
@SuppressLint("ValidFragment")
public class SearchShiPinFragment extends BaseFragment {

    private View searchshipin_frament;
    private String keyword;

    public SearchShiPinFragment(String keyword) {
        this.keyword = keyword;
    }

    private ListView listView;
    private ShiPinSearchAdapter shipinAdapter;
    private List<SearchduizhenBean.DataBean> listData = new ArrayList<>();

    //============
    private int page = 1;
    private boolean isRefresh = true;

    private CommonSwipeRefreshLayout common_layout;
    private EmptyViewHelper emptyViewHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (searchshipin_frament == null) {
            searchshipin_frament = inflater.inflate(R.layout.searchshipin_frament, container, false);

            initView();
            initData(page);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) searchshipin_frament.getParent();
            if (parent != null) {
                parent.removeView(searchshipin_frament);
            }
        }
        return searchshipin_frament;
    }



    public void initData(final int page) {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SEARCH_AGAINST)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("searchText", keyword)
                .addParams("page", page+"")
                .addParams("pageSize","10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                SearchduizhenBean bean = gson.fromJson(response, SearchduizhenBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if (bean.getData()==null||bean.getData().size()<=0){
                                        emptyViewHelper.setType(2);
                                        return;
                                    }
                                    if (isRefresh && page == 1) {//刷新
                                        listData.clear();
                                        listData.addAll(bean.getData());
                                    } else {//加载
                                        listData.addAll(bean.getData());
                                    }
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    shipinAdapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initView() {
        common_layout = (CommonSwipeRefreshLayout) searchshipin_frament.findViewById(R.id.common_layout);
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
        listView = (ListView) searchshipin_frament.findViewById(R.id.xlv_shipin_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchduizhenBean.DataBean dataBean =listData.get(position);
                startActivity(DuizhengVideoActivity.getIntent(getActivity(),dataBean.getId()));

            }
        });

        shipinAdapter = new ShiPinSearchAdapter(getActivity(), listData);
        listView.setAdapter(shipinAdapter);
        emptyViewHelper = new EmptyViewHelper(listView,(FrameLayout)searchshipin_frament.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initData(page);
            }
        });

//        shipinAdapter.setVideoListener(this);
    }
}
