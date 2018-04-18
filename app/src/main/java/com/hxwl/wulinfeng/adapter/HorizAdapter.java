package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.ShiPinAboveList;
import com.hxwl.wulinfeng.bean.ShiPinHorBean;
import com.hxwl.wulinfeng.bean.ZiXunBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.ToastUtils;

import java.util.List;

import static com.hxwl.wulinfeng.R.id.iv_start;

/**
 * Created by Allen on 2017/6/2.
 * 视频横向listviewadapter
 */
public class HorizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShiPinAboveList>  hlistData;
    private Activity activity;
    private View.OnClickListener PlayVideoClick ;

    public HorizAdapter(Activity activity, List<ShiPinAboveList> hlistData ,View.OnClickListener PlayVideoClick) {
        this.activity = activity;
        this.hlistData = hlistData ;
        this.PlayVideoClick = PlayVideoClick ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view1 = inflate.inflate(R.layout.hlv_item, null);
        return new ShiPinHorListViewHolder(view1,activity);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(hlistData.size() <= position){
            return ;
        }
        ShiPinHorListViewHolder myViewHolder = (ShiPinHorListViewHolder) holder;
        ShiPinAboveList dataBean = hlistData.get(position);
        if(dataBean != null && dataBean.getImg() != null){
            ImageUtils.getPic(dataBean.getImg(), myViewHolder.iv_hor_pic, activity);
        }else{
            myViewHolder.iv_hor_pic.setImageResource(R.drawable.wlf_deimg);
        }
        myViewHolder.tv_title.setText(dataBean.getTitle());
        myViewHolder.ll_layout.setTag(dataBean);
        if(PlayVideoClick != null){
            myViewHolder.ll_layout.setOnClickListener(PlayVideoClick);
        }else{
            ToastUtils.showToast(activity ,"视频错误 ");
        }
        if(dataBean.isPlay()){
            myViewHolder.iv_start.setVisibility(View.GONE);
            myViewHolder.tv_title.setTextColor(activity.getResources().getColor(R.color.red));
        }else{
            myViewHolder.iv_start.setVisibility(View.VISIBLE);
            myViewHolder.tv_title.setTextColor(activity.getResources().getColor(R.color.black));
        }
//        myViewHolder.tv_time.setText();
    }

    @Override
    public int getItemCount() {
        return hlistData == null ? 0 : hlistData.size();
    }

}
