package com.hxwl.wulinfeng.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.SearchHuatiBean;
import com.hxwl.newwlf.sousuo.HuatiRecycAdapter2;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.library.flowlayout.FlowLayoutManager;
import com.library.flowlayout.SpaceItemDecoration;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;


/**
 * Created by Allen on 2017/6/28.
 * 搜索结果 -- 话题
 */
@SuppressLint("ValidFragment")
public class SearchTuJiFragment extends BaseFragment {

    private View frag_tujisearch;
    private String keyword;
    private XRecyclerView huati_recycler;
    private ArrayList<SearchHuatiBean.DataBean> listData = new ArrayList<>();
    private int page = 1;
    private boolean isRefresh = true;
    private HuatiRecycAdapter2 huatiRecyclerAdapter;

    private TextView parent;

    public SearchTuJiFragment(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_tujisearch == null) {
            frag_tujisearch = inflater.inflate(R.layout.frag_tujisearch, container, false);
            initView();
            initData(page);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_tujisearch.getParent();
            if (parent != null) {
                parent.removeView(frag_tujisearch);
            }
        }
        return frag_tujisearch;
    }


    private void initView() {
        parent = (TextView) frag_tujisearch.findViewById(R.id.parent);
        huati_recycler = (XRecyclerView) frag_tujisearch.findViewById(R.id.huati_recycler);
        //流式布局
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        huati_recycler.setLayoutManager(flowLayoutManager);
        huati_recycler.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        huatiRecyclerAdapter = new HuatiRecycAdapter2(getActivity(), listData);
        huati_recycler.setAdapter(huatiRecyclerAdapter);

        huati_recycler.setPullRefreshEnabled(false);
        huati_recycler.setLoadingMoreEnabled(false);

    }

    public void initData(final int page) {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            parent.setVisibility(View.VISIBLE);
            huati_recycler.setVisibility(View.GONE);
            return;
        }
        if (StringUtils.isBlank(keyword)) {
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SEARCH_TOPIC)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("searchText", keyword)
                .addParams("page", page + "")
                .addParams("pageSize", "10")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        parent.setVisibility(View.VISIBLE);
                        huati_recycler.setVisibility(View.GONE);
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                SearchHuatiBean bean = gson.fromJson(response, SearchHuatiBean.class);
                                if (bean.getCode().equals("1000")) {
                                    if (isRefresh) {//是刷新
                                        listData.clear();
                                        listData.addAll(bean.getData());
                                    } else {//不是刷新
                                        listData.addAll(bean.getData());
                                    }
                                    huatiRecyclerAdapter.notifyDataSetChanged();
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

    private int dp2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

}
