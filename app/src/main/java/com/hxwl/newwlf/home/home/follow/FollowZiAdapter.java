package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.wulinfeng.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/18.
 */

public class FollowZiAdapter extends XRecyclerView.Adapter<FollowZiAdapter.ViewHolder> {
    private ArrayList<QuanshouBean.DataBean> list;
    private Context context;

    public FollowZiAdapter(ArrayList<QuanshouBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.follow_zi_item, null);
        ViewGroup.LayoutParams params=new RecyclerView.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder v=new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (StringUtils.isBlank(list.get(position).getPlayerImage())){
            holder.follow_touxiang.setImageResource(R.drawable.xuanshoutouxiang);
        }else {
            GlidUtils.setGrid2(context, URLS.IMG+list.get(position).getPlayerImage(),holder.follow_touxiang);
        }
        holder.follow_zi_name.setText(list.get(position).getPlayerName());
        if (list.get(position).getUserIsFollow()==1){
            holder.follow_touxiang1.setVisibility(View.VISIBLE);
            holder.follow_touxiang2.setVisibility(View.VISIBLE);
        }else {
            holder.follow_touxiang1.setVisibility(View.GONE);
            holder.follow_touxiang2.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onItemclickLinter){
                    onItemclickLinter.onItemClicj(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView follow_touxiang;
        public ImageView follow_touxiang1;
        public ImageView follow_touxiang2;
        public TextView follow_zi_name;


        public ViewHolder(View itemView) {
            super(itemView);
            this.follow_touxiang = (ImageView) itemView.findViewById(R.id.follow_touxiang);
            this.follow_touxiang1 = (ImageView) itemView.findViewById(R.id.follow_touxiang1);
            this.follow_touxiang2 = (ImageView) itemView.findViewById(R.id.follow_touxiang2);
            this.follow_zi_name = (TextView) itemView.findViewById(R.id.follow_zi_name);
        }
    }

    public interface OnItemclickLinter{
        public void onItemClicj(int position);
    }

    private OnItemclickLinter onItemclickLinter;

    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }

}
