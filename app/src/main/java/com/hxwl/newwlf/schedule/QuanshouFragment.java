package com.hxwl.newwlf.schedule;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.newwlf.modlebean.QuanshouWeiBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.PlayDetailsActivity;
import com.hxwl.wulinfeng.activity.SearchActivity;
import com.hxwl.wulinfeng.adapter.PlayerAdapter;
import com.hxwl.wulinfeng.adapter.PopAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.PinyinComparator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.widget.SideBar;
import com.tendcloud.tenddata.TCAgent;
import com.yyydjk.library.DropDownMenu;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/1/23.
 */

public class QuanshouFragment extends BaseFragment implements View.OnClickListener {
    private ListView lv_level;
    private ListView listview;
    private String weightLevel;
    private boolean isShow;
    private CommonSwipeRefreshLayout common_layout;
    private List<QuanshouBean.DataBean> listData = new ArrayList<>();
    private List<QuanshouWeiBean.DataBean> levelData = new ArrayList<>();
    private List<View> list = new ArrayList<>();//存放下拉列表

    private int page = 1;
    private boolean isRefresh;
    private PlayerAdapter playerAdapter;
    private SideBar sideBar;
    private TextView dialogTxt;
    private int lastItem;
    private int totalItem;
    public PinyinComparator comparator = new PinyinComparator();//排序
    private DropDownMenu dropDownMenu;
    private String[] lev = new String[]{"全部选手"};
    private ArrayAdapter arr_adapter;
    private PopAdapter popAdapter;
    private TextView tv_text;
    private RelativeLayout rl_click;
    private ImageView jiantou;
    private RelativeLayout rl_layout ,rl_title,rl_title2,rl_title_layout;
    private EmptyViewHelper emptyViewHelper;
    private TextView tv_title;
    private int pageNumber=1;
    private View view;
    private TextView quanbu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.activity_playlist, container, false);

            initView(view);
            initLevel();
            initData(page, weightLevel);
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }


        return view;
    }

    private void initLevelQ() {
        QuanshouWeiBean.DataBean bean = new QuanshouWeiBean.DataBean();

        bean.setName("全部选手");
        bean.setId(0);
        if(levelData != null && levelData.size() > 0){
            levelData.add(bean) ;
        }
    }

    private void initData(int page, String weightLevel){
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }

        HashMap<String, String> map = new HashMap<String, String>();
        if (!TextUtils.isEmpty(weightLevel)) {
            if (!weightLevel.equals("0")){
                map.put("weightLevel", weightLevel);
            }
        }
        map.put("pageNumber", page + "");
        map.put("pageSize", "10");
        map.put("userId", MakerApplication.instance.getUid());
        map.put("token", MakerApplication.instance.getToken());
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_PLAYERLIST)
                .params(map)
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
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                QuanshouBean bean = gson.fromJson(response, QuanshouBean.class);
                                if (bean.getCode().equals("1000")){
                                    if (isRefresh) {//是加载更多
                                        listData.clear();
                                        listData.addAll(bean.getData());
                                    } else {//刷新或是第一次进来
                                        listData.addAll(bean.getData());
                                    }

                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    playerAdapter.notifyDataSetChanged();
                                } else if (bean.getData() != null && bean.getData().size()>0) {
                                    common_layout.setRefreshing(false);
                                    common_layout.setLoadMore(false);
                                    UIUtils.showToast("没有更多了");
//                }

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });




    }

    private void initLevel(){
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            return;
        }

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_WEIGHTLEVELLIST)
                .addParams("token", MakerApplication.instance.getToken())
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
                                QuanshouWeiBean bean = gson.fromJson(response, QuanshouWeiBean.class);
                                if (bean.getCode().equals("1000")){
                                    levelData.clear();
                                    QuanshouWeiBean.DataBean bean2 = new QuanshouWeiBean.DataBean();

                                    bean2.setName("全部选手");
                                    bean2.setId(0);
                                    levelData.add(bean2) ;

                                    levelData.addAll(bean.getData());
                                    popAdapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });


    }

    private void initView(View view) {
        quanbu= (TextView) view.findViewById(R.id.quanbu);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        rl_title2= (RelativeLayout) view.findViewById(R.id.tv_title2);
        rl_title2.setOnClickListener(this);
        tv_title.setOnClickListener(this);
        rl_layout = (RelativeLayout) view.findViewById(R.id.rl_layout);
        rl_title_layout= (RelativeLayout) view.findViewById(R.id.rl_title_layout);
        rl_title_layout.setVisibility(View.GONE);
        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        rl_layout.setOnClickListener(this);
        rl_title.setOnClickListener(this);
        ImageView user_icon = (ImageView) view.findViewById(R.id.user_icon);


        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        jiantou = (ImageView) view.findViewById(R.id.jiantou);
        ImageView icon_search = (ImageView) view.findViewById(R.id.icon_search);
        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatService.onEvent(getActivity(), "PlayerSearch", "选手搜索", 1);
                TCAgent.onEvent(getActivity(), "PlayerSearch", "选手搜索");
                //点击搜索按钮
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("type",3) ;
                startActivity(intent);
            }
        });

        common_layout = (CommonSwipeRefreshLayout) view.findViewById(R.id.common_layout);
        common_layout.setTargetScrollWithLayout(true);
        common_layout.setPullRefreshEnabled(true);
        common_layout.setLoadingMoreEnabled(true);
        common_layout.setLoadingListener(new CommonSwipeRefreshLayout.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                initData(page, weightLevel);
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initData(page, weightLevel);
            }
        });
        listview = (ListView) view.findViewById(R.id.listview);

        lv_level = (ListView) view.findViewById(R.id.lv_level);
        popAdapter = new PopAdapter(levelData, getActivity());
        lv_level.setAdapter(popAdapter);
        tv_text = (TextView) view.findViewById(R.id.tv_text);
        tv_text.setOnClickListener(this);
        lv_level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                QuanshouWeiBean.DataBean bean = null;
                if (view instanceof TextView) {
                    bean = (QuanshouWeiBean.DataBean) view.getTag();
                } else {
                    bean = (QuanshouWeiBean.DataBean) view.findViewById(R.id.text).getTag();
                }
                if (bean == null) {
                    return;
                }
                quanbu.setText(bean.getName());
                if (isShow) {
                    rl_click.setVisibility(View.GONE);//隐藏
                    jiantou.setImageResource(R.drawable.triangle_down);
                    isShow = false;
                } else {
                    rl_click.setVisibility(View.VISIBLE);//显示
                    jiantou.setImageResource(R.drawable.triangle_up);
                    isShow = true;
                }
                tv_title.setText(bean.getName());
                weightLevel = bean.getId()+"";
                page = 1;
                isRefresh = true;
                initData(page, weightLevel);
                popAdapter.setCheckItem(i);
            }
        });
        rl_click = (RelativeLayout) view.findViewById(R.id.rl_click);
        rl_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_click.setVisibility(View.GONE);//隐藏
                jiantou.setImageResource(R.drawable.triangle_down);
                isShow = false;
            }
        });
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        playerAdapter = new PlayerAdapter(getActivity(), listData);
        listview.setAdapter(playerAdapter);
        emptyViewHelper = new EmptyViewHelper(listview,(FrameLayout) view.findViewById(R.id.parent));
        emptyViewHelper.setCallBackListener(new EmptyViewHelper.OnClickCallBackListener() {
            @Override
            public void OnClickButton(View v) {
                initLevel();
                initData(page, weightLevel);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                startActivity(PlayDetailsActivity.getIntent(getActivity(),listData.get(i).getPlayerId()+""));
            }
        });
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (listview != null && listview.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listview.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listview.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                    if (!enable) {
                        boolean endItemVisible = listview.getLastVisiblePosition() == (listview.getCount() - 1);
                        enable = endItemVisible;
                    }
                }
                common_layout.setEnabled(enable);
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "选手");
        TCAgent.onPageStart(getActivity(), "选手");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(), "选手");
        TCAgent.onPageEnd(getActivity(), "选手");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_title2:
            case R.id.rl_title:
                if (isShow) {
                    rl_click.setVisibility(View.GONE);//隐藏
                    jiantou.setImageResource(R.drawable.triangle_down);
                    isShow = false;
                } else {
                    rl_click.setVisibility(View.VISIBLE);//显示
                    jiantou.setImageResource(R.drawable.triangle_up);
                    isShow = true;
                }
                break;
            default:
                break;
        }

    }

}
