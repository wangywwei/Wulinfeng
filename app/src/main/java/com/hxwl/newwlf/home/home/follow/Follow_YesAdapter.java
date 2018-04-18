package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.GuanzhuQuanshouBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/17.
 */

public class Follow_YesAdapter extends RecyclerView.Adapter<Follow_YesAdapter.ViewHolder> {

    private ArrayList<GuanzhuQuanshouBean.DataBean> list;
    private Context context;


    public Follow_YesAdapter(ArrayList<GuanzhuQuanshouBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public interface OnItemclickLinter{
        public void onItemClicj(int position);
    }

    private OnItemclickLinter onItemclickLinter;

    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.follow_yes_item, null);

        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GuanzhuQuanshouBean.DataBean dataBean=list.get(position);
        if (dataBean!=null){
            try {
                if (StringUtils.isBlank(dataBean.getPlayerHeadImage())){
                    holder.leaving_img.setImageResource(R.drawable.xuanshoutouxiang);
                }else {
                    GlidUtils.setGrid2(context, URLS.IMG+dataBean.getPlayerHeadImage(),holder.leaving_img);
                }

                if (dataBean.isXuanzhong()){
                    holder.leaving_img2.setVisibility(View.VISIBLE);
                }else {
                    holder.leaving_img2.setVisibility(View.GONE);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null!=onItemclickLinter){
                            onItemclickLinter.onItemClicj(position);
                        }
                    }
                });
            }catch (Exception o){

            }

        }





    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView leaving_img2;
        private ImageView leaving_img;
        public ViewHolder(View itemView) {
            super(itemView);
            leaving_img2= (ImageView) itemView.findViewById(R.id.leaving_img2);
            leaving_img= (ImageView) itemView.findViewById(R.id.leaving_img);
        }
    }

}
