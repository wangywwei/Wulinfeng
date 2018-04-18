package com.hxwl.newwlf.wulin.arts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.GenHuatiBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/26.
 */

public class HuatiGridAdapter extends RecyclerView.Adapter<HuatiGridAdapter.ViewHolder> {
    private ArrayList<GenHuatiBean.DataBean.HotTopicListBean> list;
    private Context context;


    public HuatiGridAdapter(ArrayList<GenHuatiBean.DataBean.HotTopicListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private OnItemclickLinter onItemclickLinter;

    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }

    public interface OnItemclickLinter {
        public void onItemClicj(int view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.huati_grid_item, null);
        ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GlidUtils.setGrid(context, URLS.IMG+list.get(position).getImage(),holder.huati_img);
        holder.huati_title.setText(list.get(position).getTopicName());
        holder.huati_number.setText(list.get(position).getJoinNum()+"次参与");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemclickLinter) {
                    onItemclickLinter.onItemClicj(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView huati_img;
        private TextView huati_title;
        private TextView huati_number;
        public ViewHolder(View itemView) {
            super(itemView);
            huati_img= (ImageView) itemView.findViewById(R.id.huati_img);
            huati_title= (TextView) itemView.findViewById(R.id.huati_title);
            huati_number= (TextView) itemView.findViewById(R.id.huati_number);
        }
    }

}
