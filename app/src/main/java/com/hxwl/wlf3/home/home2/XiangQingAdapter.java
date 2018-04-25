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
    private ArrayList<DuiZenXiangBean.DataBean.AgainstListBean> list6;

    public XiangQingAdapter(Context context, ArrayList<DuiZenXiangBean.DataBean.AgainstListBean> list) {
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

        String blueClub = list6.get(position).getBlueClub();//俱乐部1
        String blueHeadImg = list6.get(position).getBlueHeadImg();//图片
        String blueName = list6.get(position).getBlueName();//名字
        Glide.with(context).load(URLS.IMG+blueHeadImg).into(holder.duizhen_img1);
        holder.duizhen_address1.setText(blueClub);
        holder.duizhen_name1.setText(blueName);

        String redClub = list6.get(position).getRedClub();//俱乐部2
        String redHeadImg = list6.get(position).getRedHeadImg();//图片
        String redName = list6.get(position).getRedName();//名字
        Glide.with(context).load(URLS.IMG+redHeadImg).into(holder.duizhen_img2);
        holder.duizhen_address2.setText(redClub);
        holder.duizhen_name2.setText(redName);

    }

    @Override
    public int getItemCount() {
        return list6.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView duizhen_img1;
        public TextView duizhen_name1;
        public TextView duizhen_address1;

        public ImageView duizhen_img2;
        public TextView duizhen_name2;
        public TextView duizhen_address2;
        public ViewHolder(View itemView) {
            super(itemView);

            duizhen_img1 = (ImageView) itemView.findViewById(R.id.duizhen_img1);
            duizhen_name1= (TextView) itemView.findViewById(R.id.duizhen_name1);
            duizhen_address1= (TextView) itemView.findViewById(R.id.duizhen_address1);

            duizhen_img2 = (ImageView) itemView.findViewById(R.id.duizhen_img2);
            duizhen_name2= (TextView) itemView.findViewById(R.id.duizhen_name2);
            duizhen_address2= (TextView) itemView.findViewById(R.id.duizhen_address2);

        }
    }


}
