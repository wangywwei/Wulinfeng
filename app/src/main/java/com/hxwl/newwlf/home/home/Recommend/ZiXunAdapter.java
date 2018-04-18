package com.hxwl.newwlf.home.home.Recommend;

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
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.TuJiDetailsActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12.
 */

public class ZiXunAdapter extends RecyclerView.Adapter<ZiXunAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HomeRecommendBean.DataBean.HotNewsBean> list;

    public ZiXunAdapter(Context context, ArrayList<HomeRecommendBean.DataBean.HotNewsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommen_zixun_item, null);
        ViewGroup.LayoutParams params=new RecyclerView.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GlidUtils.setGrid(context, URLS.IMG+list.get(position).getCoverImage(), holder.grid_image);

        holder.grid_title.setText(list.get(position).getTitle());
        if (StringUtils.isBlank(list.get(position).getSubjectName())){
            holder.grid_title1.setVisibility(View.GONE);
        }else {
            holder.grid_title1.setVisibility(View.VISIBLE);
            holder.grid_title.setText(list.get(position).getSubjectName());
        }
        switch (list.get(position).getNewsType()){
            case 1:
                holder.grid_image2.setVisibility(View.GONE);
                holder.grid_image3.setVisibility(View.GONE);
                break;
            case 2:
                holder.grid_image2.setText(list.get(position).getImageGatherNum()+"");
                holder.grid_image2.setVisibility(View.VISIBLE);
                holder.grid_image3.setVisibility(View.GONE);
                break;
            case 3:
                holder.grid_image2.setVisibility(View.GONE);
                holder.grid_image3.setVisibility(View.VISIBLE);
                break;
            case 4:

                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (list.get(position).getNewsType()){
                    case 1:
                        context.startActivity(ZiXunDetailsActivity.getIntent(context,list.get(position).getId()+""));
                        break;
                    case 2:
                        context.startActivity(TuJiDetailsActivity.getIntent(context,list.get(position).getId()+""));
                        break;
                    case 3:
                        context.startActivity(ZixunVideoActivity.getIntent(context,list.get(position).getId()+""));
                        break;
                    case 4:
                        context.startActivity(ZhuanTiActivity.getIntent(context,list.get(position).getSubjectId()+""));
                        break;
                }


            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
         ImageView grid_image;
        TextView grid_image2;
        ImageView grid_image3;
         TextView grid_title;
         TextView grid_title1;
        public ViewHolder(View itemView) {
            super(itemView);
            grid_image = (ImageView) itemView.findViewById(R.id.grid_image);
            grid_image2 = (TextView) itemView.findViewById(R.id.grid_image2);
            grid_image3= (ImageView) itemView.findViewById(R.id.grid_image3);
            grid_title = (TextView) itemView.findViewById(R.id.grid_title);
            grid_title1 = (TextView) itemView.findViewById(R.id.grid_title1);
        }
    }

    private OnItemclickLinter onItemclickLinter;

    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }

    public interface OnItemclickLinter{
        public void onItemClicj(int position);
    }
}
