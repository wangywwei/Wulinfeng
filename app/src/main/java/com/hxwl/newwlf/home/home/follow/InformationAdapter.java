package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.newwlf.modlebean.GuanzhuZixunBean;
import com.hxwl.newwlf.modlebean.SengdaiVideoBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.TuJiDetailsActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/17.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    private ArrayList<GuanzhuZixunBean.DataBean> list;
    private Context context;


    public InformationAdapter(ArrayList<GuanzhuZixunBean.DataBean> list, Context context) {
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
        holder.information_content.setText(list.get(position).getTitle());
        GlidUtils.setGrid(context, URLS.IMG+list.get(position).getCoverImage(),holder.information_img);
        holder.information_look.setText(list.get(position).getViewsNum()+"看过");
        if (StringUtils.isBlank(list.get(position).getColumnName())){
            holder.information_look3.setVisibility(View.GONE);
            holder.information_look3.setText(list.get(position).getColumnName());
        }else {
            holder.information_look3.setVisibility(View.VISIBLE);
            holder.information_look3.setText(list.get(position).getColumnName());
        }
        holder.information_title.setText(list.get(position).getAuthor());
        holder.tuji.setText(list.get(position).getImageGatherNum()+"");
        switch (list.get(position).getNewsType()){
            case 1:
                holder.information_img2.setVisibility(View.GONE);
                holder.tuji.setVisibility(View.GONE);
                break;
            case 2:
                holder.information_img2.setVisibility(View.GONE);
                holder.tuji.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.information_img2.setVisibility(View.VISIBLE);
                holder.tuji.setVisibility(View.GONE);
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
                }


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
        private TextView information_look3;
        private ImageView information_img;
        private ImageView information_img2;
        public ViewHolder(View itemView) {
            super(itemView);
            tuji= (TextView) itemView.findViewById(R.id.tuji);
            information_content= (TextView) itemView.findViewById(R.id.information_content);
            information_title= (TextView) itemView.findViewById(R.id.information_title);
            information_look= (TextView) itemView.findViewById(R.id.information_look);
            information_look3= (TextView) itemView.findViewById(R.id.information_look3);
            information_img= (ImageView) itemView.findViewById(R.id.information_img);
            information_img2= (ImageView) itemView.findViewById(R.id.information_img2);
        }
    }
}
