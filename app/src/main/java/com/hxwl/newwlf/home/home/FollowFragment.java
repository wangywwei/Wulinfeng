package com.hxwl.newwlf.home.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.MyPagerAdapter;
import com.hxwl.newwlf.home.home.follow.DataFragment;
import com.hxwl.newwlf.home.home.follow.FollowPlayerActivity;
import com.hxwl.newwlf.home.home.follow.Follow_YesAdapter;
import com.hxwl.newwlf.home.home.follow.InformationFragment;
import com.hxwl.newwlf.home.home.follow.LeavingMessageFragment;
import com.hxwl.newwlf.home.home.follow.PlayFragment;
import com.hxwl.newwlf.home.home.follow.Player3_0Activity;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.GuanzhuQuanshouBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.CacheUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/*
* 关注页面
* */
public class FollowFragment extends BaseFragment implements View.OnClickListener {
    public static final String FOLLOW="FollowFragment";
    private View view;
    private LinearLayout nofollow;//点击未关注 跳转关注页面
    private RelativeLayout nofollow_no;//未关注——用于隐藏显示
    private RecyclerView follow_yes;//关注头像列表
    private ImageView follow_bianji;//点击加号 跳转关注页面
    private SlidingTabLayout tl_SlidingTabLayout;
    private ViewPager follow_viewpager;

    private ArrayList<Fragment> fragments=new ArrayList<>();
    private ArrayList<String> tabString=new ArrayList<>();
    private MyPagerAdapter adapter;
    private Follow_YesAdapter follow_yesAdapter;
    private ArrayList<GuanzhuQuanshouBean.DataBean> urls=new ArrayList<>();
    private LeavingMessageFragment leavingMessageFragment;
    private InformationFragment informationFragment;
    private PlayFragment playFragment;
    private DataFragment dataFragment;
    private GuanzhuQuanshouBean bean;

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_follow, null);
            initView(view);

        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }
    private int dijige=0;

    private void initView(View view) {
        nofollow = (LinearLayout) view.findViewById(R.id.nofollow);
        nofollow_no = (RelativeLayout) view.findViewById(R.id.nofollow_no);
        follow_yes = (RecyclerView) view.findViewById(R.id.follow_yes);
        follow_bianji = (ImageView) view.findViewById(R.id.follow_bianji);
        tl_SlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.tl_SlidingTabLayout);
        follow_viewpager = (ViewPager) view.findViewById(R.id.follow_viewpager);


        follow_bianji.setOnClickListener(this);
        nofollow.setOnClickListener(this);

        tabString.add("留言");
        leavingMessageFragment = new LeavingMessageFragment();
        fragments.add(leavingMessageFragment);
        tabString.add("资讯");
        informationFragment = new InformationFragment();
        fragments.add(informationFragment);
        tabString.add("比赛");
        playFragment = new PlayFragment();
        fragments.add(playFragment);
        tabString.add("资料");
        dataFragment = new DataFragment();
        fragments.add(dataFragment);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        follow_yes.setLayoutManager(linearLayoutManager);

        follow_yesAdapter=new Follow_YesAdapter(urls,getActivity());
        follow_yes.setAdapter(follow_yesAdapter);

        adapter=new MyPagerAdapter(getChildFragmentManager(),fragments,tabString);
        follow_viewpager.setAdapter(adapter);
        tl_SlidingTabLayout.setViewPager(follow_viewpager);


        follow_yesAdapter.setOnItemclickLinter(new Follow_YesAdapter.OnItemclickLinter() {
            @Override
            public void onItemClicj(int position) {
                for (int i = 0; i <urls.size() ; i++) {
                    if (urls.get(position).isXuanzhong()){
                        return;
                    }
                    try {
                        urls.get(i).setXuanzhong(false);
                    }catch (Exception o){

                    }
                }
                dijige=position;
                urls.get(position).setXuanzhong(true);
                follow_yesAdapter.notifyDataSetChanged();
                leavingMessageFragment.setNewsId(urls.get(position).getPlayerId()+"");
                playFragment.setAgentId(urls.get(position).getPlayerId()+"");
                dataFragment.setAgentId(urls.get(position).getPlayerId()+"");
                informationFragment.setPlayerId(urls.get(position).getPlayerId()+"");
            }
        });

    }

    private void initData() {
        if (!SystemHelper.isConnected(getActivity())) {
            Gson gson = new Gson();
            List<String> loding = CacheUtils.readJson(getActivity(), FOLLOW);
            try {
                GuanzhuQuanshouBean bean = gson.fromJson(loding.get(0), GuanzhuQuanshouBean.class);
                if (bean.getCode().equals("1000")){
                    if (bean.getData()!=null&&bean.getData().size()>0){
                        nofollow_no.setVisibility(View.GONE);
                        urls.clear();
                        urls.addAll(bean.getData());
                        if (dijige>=urls.size()){
                            dijige=0;
                        }
                        urls.get(dijige).setXuanzhong(true);
                        follow_yesAdapter.notifyDataSetChanged();
                        leavingMessageFragment.setNewsId(urls.get(dijige).getPlayerId()+"");
                        playFragment.setAgentId(urls.get(dijige).getPlayerId()+"");
                        dataFragment.setAgentId(urls.get(dijige).getPlayerId()+"");
                        informationFragment.setPlayerId(urls.get(dijige).getPlayerId()+"");
                    }else {
                        nofollow_no.setVisibility(View.VISIBLE);
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }



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
                                bean = gson.fromJson(response, GuanzhuQuanshouBean.class);
                                if (bean.getCode().equals("1000")){
                                    if (bean.getData()!=null&& bean.getData().size()>0){
                                        CacheUtils.writeJson(getActivity(),response,FOLLOW,false);
                                        nofollow_no.setVisibility(View.GONE);
                                        urls.clear();
                                        urls.addAll(bean.getData());
                                        urls.get(0).setXuanzhong(true);
                                        follow_yesAdapter.notifyDataSetChanged();
                                        leavingMessageFragment.setNewsId(urls.get(0).getPlayerId()+"");
                                        playFragment.setAgentId(urls.get(0).getPlayerId()+"");
                                        dataFragment.setAgentId(urls.get(0).getPlayerId()+"");
                                        informationFragment.setPlayerId(urls.get(0).getPlayerId()+"");
                                    }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                        UIUtils.showToast(bean.getMessage());
                                        startActivity(LoginActivity.getIntent(getActivity()));
                                        getActivity().finish();
                                    }else {
                                        nofollow_no.setVisibility(View.VISIBLE);
                                    }

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
            case R.id.follow_bianji:
                getActivity().startActivity(Player3_0Activity.getIntent(getActivity()));

                break;
            case R.id.nofollow:
                if (StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                getActivity().startActivity(Player3_0Activity.getIntent(getActivity()));
                break;



        }
    }
}
