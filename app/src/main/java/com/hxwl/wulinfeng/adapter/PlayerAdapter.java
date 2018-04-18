package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.ImageUtils;

import java.util.List;

/**
 * Created by Allen on 2017/6/12.
 */
public class PlayerAdapter extends BaseAdapter implements SectionIndexer {
    private Activity context ;
    private List<QuanshouBean.DataBean> listData ;
    public PlayerAdapter(Activity playerActivity, List<QuanshouBean.DataBean> listData) {
        this.context = playerActivity ;
        this.listData = listData ;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final QuanshouBean.DataBean playData = listData.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_xuanshou,null);
            holder = new ViewHolder();
            holder.tv_addstate=(TextView)  convertView.findViewById(R.id.tv_addstate);
            holder.tvLetter=(TextView)  convertView.findViewById(R.id.txt_catalog);
            holder.tvName=(TextView)  convertView.findViewById(R.id.txt_user_name);
            holder.user_head=(ImageView)  convertView.findViewById(R.id.user_head);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置上边显示的字母
        int section = getSectionForPosition(position);
            holder.tvName.setText(playData.getPlayerName());
            holder.tvName.setTag(playData);

            if(TextUtils.isEmpty(playData.getPlayerClub()) && !TextUtils.isEmpty(playData.getWeight())){
                holder.tv_addstate.setText(playData.getWeight()+"KG");
            }else if(TextUtils.isEmpty(playData.getPlayerClub()) && TextUtils.isEmpty(playData.getWeight())){
                holder.tv_addstate.setText("");
            }else if(TextUtils.isEmpty(playData.getWeight()) && !TextUtils.isEmpty(playData.getPlayerClub())){
                holder.tv_addstate.setText(playData.getPlayerClub());
            }else if(!TextUtils.isEmpty(playData.getWeight()) && !TextUtils.isEmpty(playData.getPlayerClub())){
                holder.tv_addstate.setText(playData.getPlayerClub()+"/"+playData.getWeight()+"KG");
            }else{
                holder.tv_addstate.setText("");
            }
            if (StringUtils.isBlank(playData.getPlayerImage())){
                holder.user_head.setImageResource(R.drawable.xuanshoutouxiang);
            }else {
                ImageUtils.getCirclePic(URLS.IMG+playData.getPlayerImage(),holder.user_head,context);
            }

        return convertView;
    }
    public  class ViewHolder
    {
        TextView tv_addstate;
        TextView tvLetter;
        TextView tvName;
        ImageView user_head;

    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int section) {
//        for (int i = 0; i < getCount(); i++)
//        {
//            String sortStr = listData.get(i).getSortLetters();
//            if(TextUtils.isEmpty(sortStr)){
//                return -1 ;
//            }
//            char firstChar = sortStr.toUpperCase().charAt(0);
//            if (firstChar == section)
//                return i;
//        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int i) {
//        if(listData.get(i).getSortLetters() != null){
//            return this.listData.get(i).getSortLetters().charAt(0);
//        }
        return 0 ;
    }
    private String getAlpha(String str)
    {
        String  sortStr = str.trim().substring(0, 1).toUpperCase();
        if (sortStr.matches("[A-Z]"))
            return sortStr;
        else
            return "#";
    }
}
