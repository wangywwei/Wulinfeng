package com.hxwl.wulinfeng.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.mobstat.StatService;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.BiSaiShiPinActivity;
import com.hxwl.wulinfeng.activity.PlayerActivity;
import com.hxwl.wulinfeng.activity.WuZhanLMActivity;
import com.hxwl.wulinfeng.adapter.GridViewListAdapter;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.BiSaiShiPinBean;
import com.hxwl.wulinfeng.bean.FaXianBean;
import com.hxwl.wulinfeng.bean.SaiChengType;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.MyGrideview;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;

import static com.hxwl.wulinfeng.R.layout.frag_saicheng;

/**
 * Created by Allen on 2017/5/26.
 */

public class FaXianFragment extends BaseFragment {

    private View frag_faxian;
    private List<FaXianBean> faXianBeanList = new ArrayList<>();
    private MyGrideview gv_list;
    private GridViewListAdapter gridViewListAdapter;
    private ImageView iv_titleimg;
    private TextView tv_title;
    private SharedPreferences sp;
    private ImageView iv_dian;
    private BiSaiShiPinBean bean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_faxian == null) {
            frag_faxian = inflater.inflate(R.layout.frag_faxian, container, false);
            AppUtils.setTitle(getActivity());
            initView();
            initHData();
            initHListData();
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_faxian.getParent();
            if (parent != null) {
                parent.removeView(frag_faxian);
            }
        }
        AppUtils.setTitle(getActivity());
        return frag_faxian;
    }

    private void initHData() {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    bean = JSON.parseArray(result.getData(), BiSaiShiPinBean.class) != null && JSON.parseArray(result.getData(), BiSaiShiPinBean.class).size() > 0 ? JSON.parseArray(result.getData(), BiSaiShiPinBean.class).get(0) : null;
                    initHView(bean);
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {

                }
            }
        }, "method=" + NetUrlUtils.faxian_bisaishipin);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("type", "faxian");
        map.put("method", NetUrlUtils.faxian_bisaishipin);
        tasker.execute(map);

    }

    private void initHView(BiSaiShiPinBean bean) {
        if (bean == null) {
            return;
        }
        ImageUtils.getPic(bean.getIcon(), iv_titleimg, getActivity());
        tv_title.setText(bean.getName() + "更新至" + TimeUtiles.getTimeed_(bean.getLastVideoTime()));
        sp = getActivity().getSharedPreferences("MSG", Context.MODE_PRIVATE);
        if(sp.getString("faxianLastId","").equals(bean.getLastVideoId())){
            iv_dian.setVisibility(View.GONE);
        }else{
            iv_dian.setVisibility(View.VISIBLE);

        }
    }

    private void initData() {
        if (!SystemHelper.isConnected(getActivity())) {
            UIUtils.showToast("请检查网络");
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                getActivity(), true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    faXianBeanList.clear();
                    initHListData();
                    faXianBeanList.addAll(JSON.parseArray(result.getData(), FaXianBean.class));

                    gridViewListAdapter.notifyDataSetChanged();
                } else if (result != null && result.getStatus().equals("empty")) {

                } else {
                    initHListData();
                    gridViewListAdapter.notifyDataSetChanged();
                    UIUtils.showToast("获取赛事列表失败...");
                }
            }
        }, "method=" + NetUrlUtils.faxian_huodong);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("method", NetUrlUtils.faxian_huodong);
        tasker.execute(map);

    }

    private void initView() {
        iv_titleimg = (ImageView) frag_faxian.findViewById(R.id.iv_titleimg);
        tv_title = (TextView) frag_faxian.findViewById(R.id.tv_title);
        iv_dian = (ImageView) frag_faxian.findViewById(R.id.iv_dian);

        //初始化数据
//        initHListData();
        RelativeLayout rl_jump = (RelativeLayout) frag_faxian.findViewById(R.id.rl_jump);
        rl_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp != null){
                    sp.edit().putString("faxianLastId",bean.getLastVideoId()).commit() ;
                }
                Intent intent = new Intent(getActivity(), BiSaiShiPinActivity.class);
                startActivity(intent);
            }
        });
        gv_list = (MyGrideview) frag_faxian.findViewById(R.id.gv_list);
//        gv_list.setSelector(new ColorDrawable(Color.TRANSPARENT));
        gridViewListAdapter = new GridViewListAdapter(getActivity(), faXianBeanList);
        gv_list.setAdapter(gridViewListAdapter);
        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FaXianBean bean = null;
                if (view instanceof TextView) {
                    bean = (FaXianBean) view.getTag();
                } else {
                    bean = (FaXianBean) view.findViewById(R.id.tv_title).getTag();
                }
                if (bean == null) {
                    return;
                }
                String name = bean.getName();
                if ("选手".equals(name)) {
                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
                    startActivity(intent);
                } else if ("俱乐部".equals(name)) {
                    Intent intent = new Intent(getActivity(), WuZhanLMActivity.class);
                    startActivity(intent);
                } else {

                }

            }
        });

    }

    private void initHListData() {
        FaXianBean bean = null;
        if (faXianBeanList.size() == 0) {
            bean = new FaXianBean();
            bean.setId("xuanshou");
            bean.setName("选手");
            faXianBeanList.add(bean);
            bean = new FaXianBean();
            bean.setId("wuzhan");
            bean.setName("俱乐部");
            faXianBeanList.add(bean);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "发现");
        TCAgent.onPageStart(getActivity(), "发现");
        if(bean != null){
            if(sp.getString("faxianLastId","").equals(bean.getLastVideoId())){
                iv_dian.setVisibility(View.GONE);
            }else{
                iv_dian.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(),"发现");
        TCAgent.onPageEnd(getActivity(),"发现");
    }
}
