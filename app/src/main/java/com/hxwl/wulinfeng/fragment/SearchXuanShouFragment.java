package com.hxwl.wulinfeng.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.SousuoXuanshouBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.PlayDetailsActivity;
import com.hxwl.wulinfeng.adapter.SearchPlayerAdapter;
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
 * 搜索结果 -- 选手
 */
@SuppressLint("ValidFragment")
public class SearchXuanShouFragment extends BaseFragment {

    private View frag_xuanshousearch;
    private String keyword;
    private ListView lv_listview;
    private List<SousuoXuanshouBean.DataBean> listData = new ArrayList<>();
    private SearchPlayerAdapter suxnahouAdapter;
    private CommonSwipeRefreshLayout common_layout;
    private int page = 1;
    private boolean isRefresh = true;
    private EmptyViewHelper emptyViewHelper;

    public SearchXuanShouFragment(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_xuanshousearch == null) {
            frag_xuanshousearch = inflater.inflate(R.layout.frag_xuanshousearch, container, false);
            initView();
            initData(page);
        }else{
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_xuanshousearch.getParent();
            if (parent != null)
            {
                parent.removeView(frag_xuanshousearch);
            }
        }
        return frag_xuanshousearch ;
    }

    public void initData(final int page) {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }

        OkHttpUtils.post()
                .url(URLS.SEARCH_PLAYER)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("searchText", keyword)
                .addParams("page", page+"")
                .addParams("pageSize", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        emptyViewHelper.setType(1);
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                SousuoXuanshouBean bean = gson.fromJson(response, SousuoXuanshouBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if (bean.getData()==null||bean.getData().size()<=0){
                                        emptyViewHelper.setType(2);
                                        return;
                                    }
                                    if (isRefresh) {//是加载更多
                                        listData.clear();
                                        listData.addAll(bean.getData());
                                    } else {//刷新或是第一次进来
                                        listData.addAll(bean.getData());
                                    }
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    suxnahouAdapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initView() {
        common_layout = (CommonSwipeRefreshLayout) frag_xuanshousearch.findViewById(R.id.common_layout);
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
        lv_listview = (ListView) frag_xuanshousearch.findViewById(R.id.lv_listview);
        suxnahouAdapter = new SearchPlayerAdapter(getActivity(), listData);
        lv_listview.setAdapter(suxnahouAdapter);
        emptyViewHelper = new EmptyViewHelper(lv_listview,(FrameLayout)frag_xuanshousearch.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initData(page);
            }
        });

        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SousuoXuanshouBean.DataBean dataBean = null;
                if (view instanceof TextView) {
                    dataBean = (SousuoXuanshouBean.DataBean) view.getTag();
                } else {
                    dataBean = (SousuoXuanshouBean.DataBean) view.findViewById(R.id.txt_user_name).getTag();
                }
                if (dataBean == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), PlayDetailsActivity.class);
                intent.putExtra("play_id", dataBean.getId());
                startActivity(intent);
            }
        });
    }
}
