package com.hxwl.newwlf.home.home.follow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

public class Player3Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<QuanshouBean.DataBean> mList;


    public Player3Adapter(Context context, ArrayList<QuanshouBean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.player3_item,
                    null);
        }
        ViewHolder holder = new ViewHolder(convertView);

//        if (position > 0) {
//
//            String lastLetter = mList.get(position - 1).getmPinYin().charAt(0)
//                    + "";
//
//            String currentLetter = mList.get(position).getmPinYin().charAt(0)
//                    + "";
//            // 当前条目对应的字母和上一个条目中的字母一样,就隐藏当前字母对应的条目
//            if (lastLetter.equals(currentLetter)) {
//
//                holder.getViewHolder(convertView).tv_item_letter
//                        .setVisibility(View.GONE);
//            } else {
//                // 为了防止被隐藏的黑色条目复用，出现字母对应条目显示不全问题，所以就将隐藏的黑色字母条目重新显示
//                holder.getViewHolder(convertView).tv_item_letter
//                        .setVisibility(View.VISIBLE);
//                holder.getViewHolder(convertView).tv_item_letter.setText(friend
//                        .getmPinYin().charAt(0) + "");
//            }
//
//        } else {
//            // 说明是第0个黑色A条目 显示
//            holder.getViewHolder(convertView).tv_item_letter
//                    .setVisibility(View.VISIBLE);
//            holder.getViewHolder(convertView).tv_item_letter.setText(friend
//                    .getmPinYin().charAt(0) + "");
//
//        }

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

        return convertView;
    }


    static class ViewHolder {
        private ImageView player3_touxiang;
        private TextView player3_name;
        private TextView player3_title;
        private ImageView player3_guanzhu;

        public ViewHolder(View itemView) {
            player3_touxiang= (ImageView) itemView.findViewById(R.id.player3_touxiang);
            player3_guanzhu= (ImageView) itemView.findViewById(R.id.player3_guanzhu);
            player3_name= (TextView) itemView.findViewById(R.id.player3_name);
            player3_title= (TextView) itemView.findViewById(R.id.player3_title);

        }

        public ViewHolder getViewHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }


}