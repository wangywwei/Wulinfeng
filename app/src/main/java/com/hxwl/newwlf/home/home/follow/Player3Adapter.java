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
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

public class Player3Adapter extends RecyclerView.Adapter<Player3Adapter.ViewHolder> {
    private Context context;
    private ArrayList<QuanshouBean.DataBean> mList;


    public Player3Adapter(Context context, ArrayList<QuanshouBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.player3_item, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QuanshouBean.DataBean friend = mList.get(position);

        holder.player3_name.setText(friend.getPlayerName());
        if (!StringUtils.isBlank(friend.getPlayerClub())&&!StringUtils.isBlank(friend.getWeightLevel())){
            holder.player3_title.setText(friend.getPlayerClub()+" / "+friend.getWeightLevel()+"KG");
        }else if (StringUtils.isBlank(friend.getPlayerClub())&&!StringUtils.isBlank(friend.getWeightLevel())){
            holder.player3_title.setText(friend.getWeightLevel()+"KG");
        } else if (!StringUtils.isBlank(friend.getPlayerClub())&&StringUtils.isBlank(friend.getWeightLevel())) {
            holder.player3_title.setText(friend.getPlayerClub());
        }else if (StringUtils.isBlank(friend.getPlayerClub())&&StringUtils.isBlank(friend.getWeightLevel())){
            holder.player3_title.setText("");
        }

        GlidUtils.setGrid2(context, URLS.IMG+friend.getPlayerImage(),holder.player3_touxiang);

        if (friend.getUserIsFollow()==0){
            holder.player3_guanzhu.setImageResource(R.drawable.guanzhu3);
        }else {
            holder.player3_guanzhu.setImageResource(R.drawable.yiguanzhu3);
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView player3_touxiang;
        private TextView player3_name;
        private TextView player3_title;
        private ImageView player3_guanzhu;

        public ViewHolder(View itemView) {
            super(itemView);
            player3_touxiang= (ImageView) itemView.findViewById(R.id.player3_touxiang);
            player3_guanzhu= (ImageView) itemView.findViewById(R.id.player3_guanzhu);
            player3_name= (TextView) itemView.findViewById(R.id.player3_name);
            player3_title= (TextView) itemView.findViewById(R.id.player3_title);

        }
    }


}
