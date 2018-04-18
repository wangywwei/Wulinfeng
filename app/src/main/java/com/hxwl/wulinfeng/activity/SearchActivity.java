package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.RemenSearchBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.AboveHistAdapter;
import com.hxwl.wulinfeng.adapter.BelowHistAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.swipebacklayout.lib.SwipeBackLayout;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.MyGrideview;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/27.
 */

public class SearchActivity extends BaseActivity{

    private static final String HIST_RECORD = "histrecord";
    private MyGrideview gr_above;
    private MyGrideview gr_below;
    private SharedPreferences sp;
    private String hisjson;
    private List<String> listDataUp = new ArrayList<>();
    private List<RemenSearchBean.DataBean> listDatabelow = new ArrayList<>();
    private AboveHistAdapter aboveHistAdapter;
    private BelowHistAdapter belowHistAdapter;
    private EditText et_search;
    private String searchText;
    private RelativeLayout rl_above;
    private TextView remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        AppUtils.setTitle(SearchActivity.this);
        sp = getSharedPreferences(HIST_RECORD,MODE_PRIVATE);
        SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
                    if(state == SwipeBackLayout.EDGE_LEFT){
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        if(inputMethodManager.isActive()){
                            inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
                        }
                    }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
            }

            @Override
            public void onScrollOverThreshold() {
            }
        });
        initView();
        initLocaData();
        initData();
    }
    //加载网络热搜
    private void initData() {
        if (!SystemHelper.isConnected(SearchActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SEARCH_HOT)
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
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                RemenSearchBean bean = gson.fromJson(response, RemenSearchBean.class);
                                if (bean.getCode().equals("1000")) {
                                    listDatabelow.addAll(bean.getData());
                                    belowHistAdapter.notifyDataSetChanged();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(SearchActivity.this));
                                    SearchActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    //加载本地历史记录文件
    private void initLocaData() {
        hisjson = sp.getString("histjson","");
        if(TextUtils.isEmpty(hisjson)){
            findViewById(R.id.zhongjian).setVisibility(View.GONE);
            rl_above.setVisibility(View.GONE);
            gr_above.setVisibility(View.GONE);
            return ;
        }
        findViewById(R.id.zhongjian).setVisibility(View.VISIBLE);
        rl_above.setVisibility(View.VISIBLE);
        gr_above.setVisibility(View.VISIBLE);
        listDataUp.addAll(JSON.parseArray(hisjson,String.class));
        aboveHistAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private void initView() {
        TextView cancle = (TextView) findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(inputMethodManager.isActive()){
                    inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
                }
                searchText = et_search.getText().toString();
                if(TextUtils.isEmpty(searchText)){
                    return ;
                }
                listDataUp.add(searchText) ;
                SaveTextToSp(listDataUp) ;
                Intent intent = new Intent(SearchActivity.this ,SearchResultActivity.class);
                intent.putExtra("keyword", searchText);
                startActivity(intent);

            }
        });
        remove = (TextView) findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp != null){
                    sp.edit().putString("histjson","").commit() ;
                }
                initLocaData();
            }
        });
        rl_above = (RelativeLayout) findViewById(R.id.rl_above);
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
		            /*隐藏软键盘*/
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if(inputMethodManager.isActive()){
                        inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }
                    searchText = et_search.getText().toString();
                    if(TextUtils.isEmpty(searchText)){
                        return false;
                    }
                    listDataUp.add(searchText) ;
                    SaveTextToSp(listDataUp) ;
                    Intent intent = new Intent(SearchActivity.this ,SearchResultActivity.class);
                    intent.putExtra("keyword", searchText);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        gr_above = (MyGrideview) findViewById(R.id.gr_above);
        gr_above.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text ;
                if(view instanceof TextView){
                    text = (String)view.getTag();
                }else{
                    text = (String)view.findViewById(R.id.text).getTag();
                }
                if(TextUtils.isEmpty(text)){return ;}
                Intent intent = new Intent(SearchActivity.this ,SearchResultActivity.class);
                intent.putExtra("keyword", text);
                startActivity(intent);
            }
        });
        aboveHistAdapter = new AboveHistAdapter(SearchActivity.this,listDataUp);
        gr_above.setAdapter(aboveHistAdapter);

        gr_below = (MyGrideview) findViewById(R.id.gr_below);
        belowHistAdapter = new BelowHistAdapter(SearchActivity.this,listDatabelow);
        gr_below.setAdapter(belowHistAdapter);
        gr_below.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RemenSearchBean.DataBean text ;
                if(view instanceof TextView){
                    text = (RemenSearchBean.DataBean)view.getTag();
                }else{
                    text = (RemenSearchBean.DataBean)view.findViewById(R.id.text).getTag();
                }
                if(text == null){return ;}
                Intent intent = new Intent(SearchActivity.this ,SearchResultActivity.class);
                intent.putExtra("keyword", text.getWord());
                startActivity(intent);
            }
        });

    }

    //保存sp历史记录
    private void SaveTextToSp(List<String> listdata) {
        if(sp != null ){
            sp.edit().putString("histjson",JSON.toJSONString(listdata)).commit() ;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "搜索");
        TCAgent.onPageStart(this, "搜索");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"搜索");
        TCAgent.onPageEnd(this,"搜索");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
