package com.hxwl.newwlf.schedule.recent;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.newwlf.modlebean.RecentJinqiBean;
import com.hxwl.newwlf.modlebean.SaichengHuifaBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.VideoDetailActivity;
import com.hxwl.wulinfeng.util.DateUtils;
import com.hxwl.wulinfeng.view.MyListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/23.
 */

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private ArrayList<SaichengHuifaBean.DataBean> list;
    private ArrayList<SaichengHuifaBean.DataBean.AgainstInfoListBean> againstInfos=new ArrayList<>();
    private Context context;
    private RecentListAdapter recentListAdapter;


    public RecentAdapter(ArrayList<SaichengHuifaBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_item, null);
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //全程回顾才显示
        SaichengHuifaBean.DataBean bean=list.get(position);
        holder.recent_tiem.setText(DateUtils.timet(bean.getScheduleBeginTime()));
        holder.recent_title.setText(bean.getCompetitionName());
        GlidUtils.setGrid(context, URLS.IMG+bean.getPublicityImg(),holder.recent_img);
        holder.recent_content.setText(bean.getScheduleName());
        holder.recent_address.setText(bean.getHoldAddress());
        holder.recent_zhuangtai.setVisibility(View.GONE);
        holder.recent_zhuangtai3.setVisibility(View.GONE);
        holder.recent_zhuangtai2.setVisibility(View.VISIBLE);

        holder.recent_list.setVisibility(View.VISIBLE);
        againstInfos.clear();
        againstInfos.addAll(bean.getAgainstInfoList());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recent_list.setLayoutManager(layoutManager);
        recentListAdapter=new RecentListAdapter(againstInfos,context);
        holder.recent_list.setAdapter(recentListAdapter);
        
        holder.huigu_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(HuiGuDetailActivity.getIntent(context,list.get(position).getScheduleId()+"",0));
            }
        });



    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout huigu_linear;
        private TextView recent_tiem;
        private TextView recent_title;
        private ImageView recent_img;
        private TextView recent_content;
        private TextView recent_address;
        private ImageView recent_zhuangtai;
        private ImageView recent_zhuangtai2;
        private ImageView recent_zhuangtai3;
        private RecyclerView recent_list;
        public ViewHolder(View itemView) {
            super(itemView);
            huigu_linear= (LinearLayout) itemView.findViewById(R.id.huigu_linear);
            recent_tiem= (TextView) itemView.findViewById(R.id.recent_tiem);
            recent_title= (TextView) itemView.findViewById(R.id.recent_title);
            recent_img= (ImageView) itemView.findViewById(R.id.recent_img);
            recent_content= (TextView) itemView.findViewById(R.id.recent_content);
            recent_address= (TextView) itemView.findViewById(R.id.recent_address);
            recent_zhuangtai= (ImageView) itemView.findViewById(R.id.recent_zhuangtai);
            recent_zhuangtai2= (ImageView) itemView.findViewById(R.id.recent_zhuangtai2);
            recent_zhuangtai3= (ImageView) itemView.findViewById(R.id.recent_zhuangtai3);
            recent_list= (RecyclerView) itemView.findViewById(R.id.recent_recycler);
        }
    }
}
