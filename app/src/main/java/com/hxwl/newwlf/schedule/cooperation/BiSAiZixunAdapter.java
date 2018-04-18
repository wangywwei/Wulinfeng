package com.hxwl.newwlf.schedule.cooperation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.newwlf.modlebean.SengdaiVideoBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/17.
 */

public class BiSAiZixunAdapter extends RecyclerView.Adapter<BiSAiZixunAdapter.ViewHolder> {
    private ArrayList<SengdaiVideoBean.DataBean> list;
    private Context context;


    public BiSAiZixunAdapter(ArrayList<SengdaiVideoBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.information_item, null);
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.information_content.setText(list.get(position).getScheduleName());
        GlidUtils.setGrid(context, URLS.IMG+list.get(position).getPublicityImg(),holder.information_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(HuiGuDetailActivity.getIntent(context,list.get(position).getScheduleId()+"",list.get(position).getPlayType()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tuji;
        private TextView information_content;
        private TextView information_title;
        private TextView information_look;
        private ImageView information_img;
        private ImageView information_img2;
        public ViewHolder(View itemView) {
            super(itemView);
            tuji= (TextView) itemView.findViewById(R.id.tuji);
            information_content= (TextView) itemView.findViewById(R.id.information_content);
            information_title= (TextView) itemView.findViewById(R.id.information_title);
            information_look= (TextView) itemView.findViewById(R.id.information_look);
            information_img= (ImageView) itemView.findViewById(R.id.information_img);
            information_img2= (ImageView) itemView.findViewById(R.id.information_img2);
        }
    }
}
