package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.GuanzhuQuanshouBean;
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.newwlf.modlebean.QuanshouWeiBean;
import com.hxwl.newwlf.modlebean.YueyueBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;


/**
 * Created by Allen on 2017/6/8.
 * 选手页面
 */
public class FollowPlayerActivity extends BaseActivity implements View.OnClickListener {

    private String remolist;
    private String addlist;
    private GuanzhuQuanshouBean guanzhuQuanshouBean;
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, FollowPlayerActivity.class);
        return intent;
    }

    private ListView follow_fu;
    private XRecyclerView follow_zi;
    private TextView quxiao;
    private TextView wanchen;
    private FollowFuAdapter followFuAdapter;
    private FollowZiAdapter followZiAdapter;
    private ArrayList<QuanshouWeiBean.DataBean> fulist = new ArrayList<>();
    private ArrayList<QuanshouBean.DataBean> zilist = new ArrayList<>();
    private int page = 1;
    private boolean isRefresh=true;
    private String weightLevel;
    private ArrayList<String> yiguanzhu=new ArrayList<>();
    private ArrayList<String> yiguanzhucope=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_player);
//        AppUtils.setTitle(this);
        StatusBarUtil.setColor(this,this.getResources().getColor(R.color.rgb_BA2B2C));
        initView();
        initData();
        yiguanzhuadd();
    }

    private void initDataY(int page, String weightLevel) {

        HashMap<String, String> map = new HashMap<String, String>();
        if (!TextUtils.isEmpty(weightLevel)) {
            if (!weightLevel.equals("0")){
                map.put("weightLevel", weightLevel);
            }
        }
        map.put("token", MakerApplication.instance.getToken());
        map.put("pageNumber", page + "");
        map.put("pageSize", "50");
        map.put("userId", MakerApplication.instance.getUid());
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_PLAYERLIST)
                .params(map)
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
                                QuanshouBean bean = gson.fromJson(response, QuanshouBean.class);
                                if (bean.getCode().equals("1000")){
                                    if (isRefresh) {//是加载更多
                                        zilist.clear();
                                        zilist.addAll(bean.getData());
                                    } else {//刷新或是第一次进来
                                        zilist.addAll(bean.getData());
                                    }

                                    followZiAdapter.notifyDataSetChanged();
                                } else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(FollowPlayerActivity.this));
                                    FollowPlayerActivity.this.finish();
                                }else if (bean.getData() != null && bean.getData().size()>0) {

                                    UIUtils.showToast("没有更多了");
//                }

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });
    }

    private void initData() {
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
                                    fulist.clear();
                                    QuanshouWeiBean.DataBean bean2 = new QuanshouWeiBean.DataBean();
                                    bean2.setName("已关注");
                                    bean2.setId(0);
                                    fulist.add(bean2) ;
                                    QuanshouWeiBean.DataBean bean3 = new QuanshouWeiBean.DataBean();
                                    bean3.setName("全部选手");
                                    bean3.setId(0);
                                    fulist.add(bean3) ;
                                    fulist.addAll(bean.getData());
                                    followFuAdapter.setSelectedPosition(1);
                                    followFuAdapter.notifyDataSetChanged();
                                    initDataY(page,null);
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(FollowPlayerActivity.this));
                                    FollowPlayerActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });

    }

    private ArrayList<String> addguanzu=new ArrayList<>();
    private ArrayList<String> remueguanzu=new ArrayList<>();
    private boolean yiguanzhujibei=false;
    private void initView() {
        follow_fu = (ListView) findViewById(R.id.follow_fu);
        follow_zi = (XRecyclerView) findViewById(R.id.follow_zi);
        quxiao = (TextView) findViewById(R.id.quxiao);
        quxiao.setOnClickListener(this);
        wanchen = (TextView) findViewById(R.id.wanchen);
        wanchen.setOnClickListener(this);

        followFuAdapter = new FollowFuAdapter(fulist, this);
        follow_fu.setAdapter(followFuAdapter);
        followZiAdapter = new FollowZiAdapter(zilist, this);
        follow_zi.setLayoutManager(new GridLayoutManager(this, 3));
        follow_zi.setAdapter(followZiAdapter);

        followZiAdapter.setOnItemclickLinter(new FollowZiAdapter.OnItemclickLinter() {
            @Override
            public void onItemClicj(int position) {
                if ( zilist.get(position).getUserIsFollow()==1){
                    if (yiguanzhujibei){
                        guanzhuQuanshouBean.getData().get(position).setUserIsFollow(0);
                    }
                    zilist.get(position).setUserIsFollow(0);
                    for (int i = 0; i < yiguanzhucope.size(); i++) {
                        if (yiguanzhucope.get(i).equals(zilist.get(position).getPlayerId())){
                            yiguanzhucope.remove(yiguanzhucope.get(i));
                        }
                    }

                }else {
                    if (yiguanzhujibei){
                        guanzhuQuanshouBean.getData().get(position).setUserIsFollow(1);
                    }
                    zilist.get(position).setUserIsFollow(1);
                    yiguanzhucope.add(zilist.get(position).getPlayerId());
                }

                followZiAdapter.notifyDataSetChanged();
            }
        });

        follow_zi.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isRefresh = true;
                initDataY(page, weightLevel);
                follow_zi.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                page++;
                isRefresh = false;
                initDataY(page, weightLevel);
                follow_zi.refreshComplete();
            }
        });

        follow_fu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                followFuAdapter.setSelectedPosition(position);
                followFuAdapter.notifyDataSetInvalidated();
                page = 1;
                isRefresh = true;
                if (position==0){
                    yiguanzhujibei=true;
                    yiguanzhuadd();
                }else {
                    yiguanzhujibei=false;
                    try {
                        initDataY(page,fulist.get(position).getId()+"");
                    }catch (Exception o){
                    }
                }

            }
        });


    }
    /*
    * 添加已关注
    * */
    private void yiguanzhuadd() {
        OkHttpUtils.post()
                .url(URLS.HOME_USERPLAYERATTENTIONLIST)
                .addParams("userId", MakerApplication.instance.getUid())
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
                                guanzhuQuanshouBean = gson.fromJson(response, GuanzhuQuanshouBean.class);
                                if (guanzhuQuanshouBean.getCode().equals("1000")){
                                    if (guanzhuQuanshouBean.getData()!=null&& guanzhuQuanshouBean.getData().size()>0){
                                        zilist.clear();
                                        for (int i = 0; i <guanzhuQuanshouBean.getData().size() ; i++) {
                                            yiguanzhu.add(guanzhuQuanshouBean.getData().get(i).getPlayerId()+"");
                                            QuanshouBean.DataBean dataBean=new QuanshouBean.DataBean();
                                            dataBean.setPlayerImage(guanzhuQuanshouBean.getData().get(i).getPlayerHeadImage());
                                            dataBean.setPlayerName(guanzhuQuanshouBean.getData().get(i).getPlayerName());
                                            dataBean.setPlayerId(guanzhuQuanshouBean.getData().get(i).getPlayerId());
                                            dataBean.setUserIsFollow(guanzhuQuanshouBean.getData().get(i).getUserIsFollow());
                                            zilist.add(dataBean);
                                        }
                                        yiguanzhucope.addAll(yiguanzhu);
                                        followZiAdapter.notifyDataSetChanged();

                                    }

                                }else if (guanzhuQuanshouBean.getCode().equals("2002")||guanzhuQuanshouBean.getCode().equals("2003")){
                                    UIUtils.showToast(guanzhuQuanshouBean.getMessage());
                                    startActivity(LoginActivity.getIntent(FollowPlayerActivity.this));
                                    FollowPlayerActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quxiao:
                finish();

                break;
            case R.id.wanchen:
                addguanzu.addAll(yiguanzhu);
                remueguanzu.addAll(yiguanzhucope);

                remueguanzu.remove(yiguanzhu);
                addguanzu.remove(yiguanzhucope);

                StringBuffer adddd=new StringBuffer();
                if (addguanzu!=null&&addguanzu.size()>0){
                    for (int i = 0; i <addguanzu.size() ; i++) {
                        adddd.append(addguanzu.get(i)).append(",");
                    }
                }
                if (!StringUtils.isBlank(adddd.toString())){
                    addlist = adddd.substring(0,adddd.length()-1);
                }
                StringBuffer remo=new StringBuffer();
                if (remueguanzu!=null&&remueguanzu.size()>0){
                    for (int i = 0; i <remueguanzu.size(); i++) {
                        remo.append(remueguanzu.get(i)).append(",");
                    }
                }
                if (!StringUtils.isBlank(remo.toString())){
                    remolist = remo.substring(0,remo.length()-1);
                }
/*
                * 这写反了add是删除的
                *
                * Remo是添加的
                * */
                if (StringUtils.isBlank(addlist)){
                    initAdd(remolist);
                }else {
                    initRemo(remolist, addlist);
                }

                break;
        }
    }

    private void initRemo(final String addlist, String remolist) {
        if (StringUtils.isBlank(MakerApplication.instance.getMobile())){
            UIUtils.showToast("请您点击我的头像去绑定手机号");
            return;
        }

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_USERCANCELPLAYERATTENTION)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("playerId",remolist)
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
                                YueyueBean bean = gson.fromJson(response, YueyueBean.class);
                                if (bean.getCode().equals("1000")){

                                    initAdd(addlist);

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(FollowPlayerActivity.this));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });


    }

    private void initAdd(String addlist) {
        if (StringUtils.isBlank(addlist)){
            finish();
            return;
        }
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_USERPLAYERATTENTION)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("playerId",addlist)
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
                                YueyueBean bean = gson.fromJson(response, YueyueBean.class);
                                if (bean.getCode().equals("1000")){
                                    ToastUtils.showToast(FollowPlayerActivity.this,bean.getMessage());
                                    finish();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(FollowPlayerActivity.this));

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });
    }
}
