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
import com.hxwl.common.utils.HncNotifier;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.SearchSaichengBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.adapter.SaiChengSearchAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.SaichengBean;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;


/**
 * Created by Allen on 2017/6/28.
 * 搜索结果 -- 赛程
 */
@SuppressLint("ValidFragment")
public class SearchSaiChengFragment extends BaseFragment {
    private String keyword; //搜索关键字

    public SearchSaiChengFragment(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private ListView list_view;
    private View saichengsearch_frament;
    private CommonSwipeRefreshLayout common_layout;
    private int page = 1;
    private boolean isRefresh;
    private String num;
    private ArrayList<SearchSaichengBean.DataBean> listData = new ArrayList<>();
    private SaiChengSearchAdapter saiChengAdapter;
    private EmptyViewHelper emptyViewHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (saichengsearch_frament == null)
        {
            saichengsearch_frament = inflater.inflate(R.layout.searchsaicheng_frament, container, false);
            initView();
            initData(page);
        }else{
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) saichengsearch_frament.getParent();
            if (parent != null)
            {
                parent.removeView(saichengsearch_frament);
            }
        }
        return saichengsearch_frament ;

    }

    public void initData(final int page) {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SEARCH_SCHEDULE)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("searchText", keyword)
                .addParams("page", page+"")
                .addParams("pageSize", "10")
                .addParams("userId", MakerApplication.instance.getUid())
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
                                SearchSaichengBean bean = gson.fromJson(response, SearchSaichengBean.class);
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
                                    saiChengAdapter.notifyDataSetChanged();
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

    private void initView() {
        list_view = (ListView) saichengsearch_frament.findViewById(R.id.list_view);
        common_layout = (CommonSwipeRefreshLayout) saichengsearch_frament.findViewById(R.id.common_layout);
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
        saiChengAdapter = new SaiChengSearchAdapter(listData,getActivity());
        list_view.setAdapter(saiChengAdapter);
        emptyViewHelper = new EmptyViewHelper(list_view,(FrameLayout)saichengsearch_frament.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initData(page);
            }
        });
//        recycl_view.setDetalisCallBack(this);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(HuiGuDetailActivity.getIntent(getActivity(),listData.get(i).getScheduleId()+"",1));

            }
        });
    }


    /**
     * 添加提醒
     *
     * @param bean
     */
    private void initTiXing(final SaichengBean bean) {

        ToastUtils.diyToast(getActivity(), "直播开始前10分钟提醒！");
        bean.setYuyue_state("0");
        saiChengAdapter.notifyDataSetChanged();
        MakerApplication.instance.makeSaicheng(getActivity(), bean);

        if (listData != null) {
            String start_time = bean.getStart_time();
            long value = Long.parseLong(start_time) * 1000;
            long value2 = System.currentTimeMillis();
            if (value <= value2) {
                SPUtils.clearYuYueInfo(getActivity(), bean);
            } else {
                HncNotifier.getHncNotifier().shownotifyItemNote(getActivity(), bean);
            }
        }
    }
}
