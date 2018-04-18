package com.hxwl.newwlf.schedule.cooperation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.QuanshendaiBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.PlayDetailsActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/1.
 */

public class QuanshouAdapter extends RecyclerView.Adapter<QuanshouAdapter.ViewHolder> {
    private ArrayList<QuanshendaiBean.DataBean> list;
    private Context context;


    public QuanshouAdapter(ArrayList<QuanshendaiBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_xuanshou, null);
        RecyclerView.LayoutParams params=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GlidUtils.setGrid2(context, URLS.IMG+list.get(position).getHeadImage(),holder.user_head);

        holder.txt_user_name.setText(list.get(position).getPlayerName());
        holder.tv_addstate.setText(list.get(position).getWeightName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(PlayDetailsActivity.getIntent(context,list.get(position).getPlayerId()+""));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_catalog;
        private ImageView user_head;
        private TextView txt_user_name;
        private TextView tv_addstate;
        private TextView txt_user_id;
        private LinearLayout ll_bg;
        public ViewHolder(View itemView) {
            super(itemView);
            txt_catalog= (TextView) itemView.findViewById(R.id.txt_catalog);
            user_head= (ImageView) itemView.findViewById(R.id.user_head);
            txt_user_name= (TextView) itemView.findViewById(R.id.txt_user_name);
            tv_addstate= (TextView) itemView.findViewById(R.id.tv_addstate);
            txt_user_id= (TextView) itemView.findViewById(R.id.txt_user_id);
            ll_bg= (LinearLayout) itemView.findViewById(R.id.ll_bg);
        }
    }
}
