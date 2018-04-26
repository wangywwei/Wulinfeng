package com.hxwl.wlf3.home.home2.gengduo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hxwl.wlf3.home.remenhot.GenduoAdapter;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.NormalWebviewActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/26.
 */

public class Genduo1Adapter  extends RecyclerView.Adapter<Genduo1Adapter.ViewHolder> {
    private Context context;
    private ArrayList<GengDuoBean.DataBean> list;

    public Genduo1Adapter(Context context, ArrayList<GengDuoBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Genduo1Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.genduo_adapter,null);
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        Genduo1Adapter.ViewHolder viewHolder=new Genduo1Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Genduo1Adapter.ViewHolder holder,final int position) {


        String image = list.get(position).getImage();

        Glide.with(context)
                .load(image)
                .placeholder(R.drawable.aboutwlflogo)//图片加载出来前，显示的图片
                .error(R.drawable.aboutwlflogo)//图片加载失败后，显示的图片
                .into(holder.gengduo1_img);

        String name = list.get(position).getName();
        holder.gengduo1_name.setText(name);

        holder.gengduo1_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = list.get(position).getUrl();
                Intent intent=new Intent(context, NormalWebviewActivity.class);
                intent.putExtra("url",url+"");
                intent.putExtra("title","活动");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

private LinearLayout gengduo1_layout;
        private ImageView gengduo1_img;
        private TextView gengduo1_name;
        public ViewHolder(View itemView) {
            super(itemView);
            gengduo1_layout= (LinearLayout) itemView.findViewById(R.id.gengduo1_layout);
            gengduo1_img= (ImageView) itemView.findViewById(R.id.gengduo1_img);
            gengduo1_name= (TextView) itemView.findViewById(R.id.gengduo1_name);
        }
    }
}
