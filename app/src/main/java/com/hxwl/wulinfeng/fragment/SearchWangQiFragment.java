package com.hxwl.wulinfeng.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.SearchZixunBean;
import com.hxwl.newwlf.sousuo.SearchZixunAdapter;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/28.
 * 搜索结果 -- 资讯
 */
@SuppressLint("ValidFragment")
public class SearchWangQiFragment extends BaseFragment {

    private View searchwangqi_frament;
    private String keyword;
    private XRecyclerView information_recycle;
    private TextView no_information;
    private SearchZixunAdapter informationAdapter;
    private ArrayList<SearchZixunBean.DataBean> list=new ArrayList<>();
    private int pageNumber=1;
    public void restData(){
        if(searchwangqi_frament != null){
            pageNumber = 1 ;
            initData();
        }
    }
    public SearchWangQiFragment(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (searchwangqi_frament == null) {
            searchwangqi_frament = inflater.inflate(R.layout.searchwangqi_frament, container, false);

            initView();
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) searchwangqi_frament.getParent();
            if (parent != null) {
                parent.removeView(searchwangqi_frament);
            }
        }
        return searchwangqi_frament;
    }


    public void initData() {
        if (StringUtils.isBlank(keyword)){
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SEARCH_NEWS)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("searchText",keyword)
                .addParams("pageNumber",pageNumber+"")
                .addParams("pageSize",10+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                SearchZixunBean bean = gson.fromJson(response, SearchZixunBean.class);
                                if (bean.getCode().equals("1000")){
                                    if (bean.getData()==null||bean.getData().size()<=0){
                                        information_recycle.setVisibility(View.GONE);
                                        no_information.setVisibility(View.VISIBLE);
                                        return;
                                    }
                                    list.addAll(bean.getData());
                                    informationAdapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });



    }

    private void initView() {
        information_recycle = (XRecyclerView) searchwangqi_frament.findViewById(R.id.information_recycle);
        no_information= (TextView) searchwangqi_frament.findViewById(R.id.no_information);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        information_recycle.setLayoutManager(linearLayoutManager);
        informationAdapter=new SearchZixunAdapter(list,getActivity());
        information_recycle.setAdapter(informationAdapter);

        information_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNumber=1;
                initData();
                information_recycle.refreshComplete();//刷新完成
            }

            @Override
            public void onLoadMore() {
                pageNumber++;
                initData();
                information_recycle.refreshComplete();//刷新完成
            }
        });
    }
}
