package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.common.swiperefresh.CommonSwipeRefreshLayout;
import com.hxwl.common.utils.EmptyViewHelper;
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.newwlf.modlebean.QuanshouWeiBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.PlayerAdapter;
import com.hxwl.wulinfeng.adapter.PopAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.LevelBean;
import com.hxwl.wulinfeng.bean.PlayBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.PinYinKit;
import com.hxwl.wulinfeng.util.PinyinComparator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.widget.SideBar;
import com.tendcloud.tenddata.TCAgent;
import com.yyydjk.library.DropDownMenu;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hxwl.wulinfeng.R.id.icon_search;
import static com.hxwl.wulinfeng.R.id.user_icon;
import static com.hxwl.wulinfeng.R.layout.frag_tuji;

/**
 * Created by Allen on 2017/6/8.
 * 选手页面
 */
public class PlayerActivity extends BaseActivity implements View.OnClickListener {

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
    private RelativeLayout rl_layout ,rl_title;
    private EmptyViewHelper emptyViewHelper;
    private TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        AppUtils.setTitle(PlayerActivity.this);
        initView();
        initLevel();
        initData(page, weightLevel);
    }

    private void initLevel() {
        if (!SystemHelper.isConnected(this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                PlayerActivity.this, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    levelData.clear();
//                    initLevelQ();
//                    levelData.addAll(JSON.parseArray(result.getData(), LevelBean.class));
//                    popAdapter.notifyDataSetChanged();
//                } else if (result != null && result.getStatus().equals("empty")) {
//
//                } else {
//
//                }
//            }
//        }, "method=" + NetUrlUtils.wulin_level);
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("method", NetUrlUtils.wulin_level);
//        tasker.execute(map);

    }

    public void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);
        rl_layout = (RelativeLayout) findViewById(R.id.rl_layout);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        rl_layout.setOnClickListener(this);
        rl_title.setOnClickListener(this);
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        jiantou = (ImageView) findViewById(R.id.jiantou);
        ImageView icon_search = (ImageView) findViewById(R.id.icon_search);
        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatService.onEvent(PlayerActivity.this, "PlayerSearch", "选手搜索", 1);
                TCAgent.onEvent(PlayerActivity.this, "PlayerSearch", "选手搜索");
                //点击搜索按钮
                Intent intent = new Intent(PlayerActivity.this, SearchActivity.class);
                intent.putExtra("type",3) ;
                startActivity(intent);
            }
        });
        initLevelQ();
        common_layout = (CommonSwipeRefreshLayout) findViewById(R.id.common_layout);
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
        listview = (ListView) findViewById(R.id.listview);

        lv_level = (ListView) findViewById(R.id.lv_level);
        popAdapter = new PopAdapter(levelData, PlayerActivity.this);
        lv_level.setAdapter(popAdapter);
        tv_text = (TextView) findViewById(R.id.tv_text);
        tv_text.setOnClickListener(this);
        lv_level.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LevelBean bean = null;
                if (view instanceof TextView) {
                    bean = (LevelBean) view.getTag();
                } else {
                    bean = (LevelBean) view.findViewById(R.id.text).getTag();
                }
                if (bean == null) {
                    return;
                }
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
                weightLevel = bean.getId();
                page = 1;
                isRefresh = true;
                initData(page, weightLevel);
                popAdapter.setCheckItem(i);
            }
        });
        rl_click = (RelativeLayout) findViewById(R.id.rl_click);
        rl_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_click.setVisibility(View.GONE);//隐藏
                jiantou.setImageResource(R.drawable.triangle_down);
                isShow = false;
            }
        });
        listview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        playerAdapter = new PlayerAdapter(PlayerActivity.this, listData);
        listview.setAdapter(playerAdapter);
        emptyViewHelper = new EmptyViewHelper(listview,(FrameLayout)findViewById(R.id.parent));
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
                PlayBean info = null;
                if (view instanceof TextView) {
                    info = (PlayBean) view.getTag();
                } else {
                    info = (PlayBean) view.findViewById(R.id.txt_user_name).getTag();
                }
                if (info == null) {
                    return;
                }
                ToastUtils.showToast(PlayerActivity.this, info.getName());
                Intent intent = new Intent(PlayerActivity.this, PlayDetailsActivity.class);
                intent.putExtra("play_id", info.getPlayer_id());
                startActivity(intent);
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

    private void initLevelQ() {
//        LevelBean bean = new LevelBean();
//        bean.setChecked(true);
//        bean.setName("全部选手");
//        bean.setId("");
//        if(levelData != null && levelData.size() == 0){
//            levelData.add(bean) ;
//        }
    }

    private void initData(int page, String weightLevel) {
        if (!SystemHelper.isConnected(this)) {
            UIUtils.showToast("请检查网络");
            emptyViewHelper.setType(1);
            return;
        }
//        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
//                PlayerActivity.this, true, new ExcuteJSONObjectUpdate() {
//            @Override
//            public void excute(ResultPacket result) {
//                if (result != null && result.getStatus().equals("ok")) {
//                    if (isRefresh) {//是加载更多
//                        listData.clear();
//                        listData.addAll(JSON.parseArray(result.getData(), PlayBean.class));
//                    } else {//刷新或是第一次进来
//                        listData.addAll(JSON.parseArray(result.getData(), PlayBean.class));
//                    }
//                    common_layout.setRefreshing(false);
//                    common_layout.setLoadMore(false);
//                    playerAdapter.notifyDataSetChanged();
//                } else if (result != null && result.getStatus().equals("empty")) {
//                    common_layout.setRefreshing(false);
//                    common_layout.setLoadMore(false);
//                    UIUtils.showToast("没有更多了");
//                } else {
//                    common_layout.setRefreshing(false);
//                    common_layout.setLoadMore(false);
//                    emptyViewHelper.setType(1);
//                    UIUtils.showToast("服务器异常");
//                }
//            }
//        }, "method=" + NetUrlUtils.wulin_play + page + weightLevel);
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("page", page + "");
//        if (!TextUtils.isEmpty(weightLevel)) {
//            map.put("weightLevel", weightLevel);
//        }
//        map.put("method", NetUrlUtils.wulin_play);
//        tasker.execute(map);

    }

    /**
     * 对list数据进行处理 添加首字母
     * @param listData
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    private void filledData(List<PlayBean> listData)
            throws BadHanyuPinyinOutputFormatCombination {
        /**
         * 添加首字母
         */
        for (int i = 0; i < listData.size(); i++) {
//			 汉字转换成拼音
            String pinyin = PinYinKit
                    .getPingYin(listData.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if (sortString.matches("[A-Z]")) {
                listData.get(i).setSortLetters(sortString.toUpperCase());
            } else {
                listData.get(i).setSortLetters("#");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "选手");
        TCAgent.onPageStart(this, "选手");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "选手");
        TCAgent.onPageEnd(this, "选手");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_title:
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
