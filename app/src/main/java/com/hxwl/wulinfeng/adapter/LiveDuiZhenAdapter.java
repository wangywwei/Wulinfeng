package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.Duizhen;
import com.hxwl.wulinfeng.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Allen on 2017/8/7.
 */

public class LiveDuiZhenAdapter extends RecyclerView.Adapter<LiveDuiZhenAdapter.MyViewHolder> {
    private Activity activity;
    private List<Duizhen> listData = new ArrayList<>();

    public LiveDuiZhenAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.live_guess_item, null);
        LiveDuiZhenAdapter.MyViewHolder viewHolder = new LiveDuiZhenAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String bluePhoto = listData.get(position).getBlue_player_photo();
        String redPhoto = listData.get(position).getRed_player_photo();
        String blueflag = listData.get(position).getBlue_player_guoqi_img();
        String redflag = listData.get(position).getRed_player_guoqi_img();

        String blueName = listData.get(position).getBlue_player_name();
        String redName = listData.get(position).getRed_player_name();

        holder.tv_title.setText(listData.get(position).getTitle());

        String changci = listData.get(position).getVs_order();

        ImageUtils.getCirclePic(bluePhoto, holder.player_icon_right, activity);
        ImageUtils.getCirclePic(redPhoto, holder.player_icon_left, activity);
        ImageUtils.getPic(blueflag, holder.country_right, activity);
        ImageUtils.getPic(redflag, holder.country_left, activity);

        holder.playerA_name.setText(redName);
        holder.playerB_name.setText(blueName);

        holder.changci.setText("[第" + changci + "场]");
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setData(List<Duizhen> data) {
        this.listData.clear();
        this.listData.addAll(data);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends BaseRecyclerViewHolder {

        public LinearLayout ll_title;
        public ImageView player_icon_left;
        public ImageView player_icon_right;
        public ImageView country_left;
        public ImageView country_right;
        public TextView playerA_name;
        public TextView playerB_name;
        public TextView changci;
        public TextView tv_title;
        public ImageView iv_vs;
        public TextView tv_changci;
        public ImageView user_icon1;
        public ImageView user_icon2;
        public ImageView ivShengLeft;
        public ImageView ivShengRight;

        public MyViewHolder(View convertView) {
            super(convertView);
            player_icon_left = (ImageView) convertView.findViewById(R.id.iv_player_icon_left);
            player_icon_right = (ImageView) convertView.findViewById(R.id.iv_player_icon_right);
            country_left = (ImageView) convertView.findViewById(R.id.iv_country_left);
            country_right = (ImageView) convertView.findViewById(R.id.iv_country_right);

            playerA_name = (TextView) convertView.findViewById(R.id.playerA_name);
            playerB_name = (TextView) convertView.findViewById(R.id.tv_playerB_name);
            changci = (TextView) convertView.findViewById(R.id.changci);
            tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            iv_vs = (ImageView) convertView.findViewById(R.id.iv_vs);
            ivShengLeft = (ImageView) convertView.findViewById(R.id.iv_sheng_left);
            ivShengRight = (ImageView) convertView.findViewById(R.id.iv_sheng_right);
        }
    }
}
