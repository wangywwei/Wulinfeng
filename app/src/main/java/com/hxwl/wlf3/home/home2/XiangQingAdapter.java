package com.hxwl.wlf3.home.home2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hxwl.common.tencentplay.utils.TCUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.YueyueBean;
import com.hxwl.wlf3.bean.DuiZenXiangBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home1.Home3Adapter;
import com.hxwl.wlf3.home.linearfenlei.DuiZhenLayout;
import com.hxwl.wlf3.home.linearfenlei.PureTextLayout;
import com.hxwl.wlf3.home.linearfenlei.SaichengLayout;
import com.hxwl.wlf3.home.linearfenlei.VideoListlayout;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/4/24.
 */

public class XiangQingAdapter  extends RecyclerView.Adapter<XiangQingAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DuiZenXiangBean.DataBean> list6;



    private boolean aBoolean=true;
    private int hasSubscribed;
    private int state;

    public XiangQingAdapter(Context context, ArrayList<DuiZenXiangBean.DataBean> list) {
        this.context = context;
        this.list6 = list;
    }

    @Override
    public XiangQingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.beiyong_item, null);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        XiangQingAdapter.ViewHolder viewHolder = new XiangQingAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(XiangQingAdapter.ViewHolder holder, final int position) {










    }

    @Override
    public int getItemCount() {
        return list6.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView home_saishi_img;
        private TextView home_saishi_name;
        private TextView home_saishi_item;
        private ImageView home_saishi_yuyue;
        private ImageView home_saishi_shijianzhou;
        private RelativeLayout home_saishi_xrecycler;

        public ViewHolder(View itemView) {
            super(itemView);
            home_saishi_img= (ImageView) itemView.findViewById(R.id.home_saishi_img);
            home_saishi_name= (TextView) itemView.findViewById(R.id.home_saishi_name);
            home_saishi_item= (TextView) itemView.findViewById(R.id.home_saishi_item);
            home_saishi_yuyue= (ImageView) itemView.findViewById(R.id.home_saishi_yuyue);
            home_saishi_shijianzhou= (ImageView) itemView.findViewById(R.id.home_saishi_shijianzhou);
            home_saishi_xrecycler= (RelativeLayout) itemView.findViewById(R.id.home_saishi_xrecycler);
        }
    }


}
