package com.hxwl.newwlf.wulin.wulin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.newwlf.modlebean.HotHuatiBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HotHuatiBean.DataBean> list;

    private OnItemclickLinter onItemclickLinter;


    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }

    public interface OnItemclickLinter {
        public void onItemClicj(int view);
    }

    public TopicAdapter(Context context, ArrayList<HotHuatiBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommen_topic_item, null);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GlidUtils.setGrid(context, URLS.IMG+list.get(position).getImage(),holder.topic_image);
        holder.topic_name.setText(list.get(position).getTopicName());
        holder.topic_nub.setText(list.get(position).getJoinNum()+"次参与");

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
        private ImageView topic_image;
        private TextView topic_name;
        private TextView topic_nub;
        public ViewHolder(View itemView) {
            super(itemView);
            topic_image= (ImageView) itemView.findViewById(R.id.topic_image);
            topic_name= (TextView) itemView.findViewById(R.id.topic_name);
            topic_nub= (TextView) itemView.findViewById(R.id.topic_nub);
        }
    }


}
