package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.SousuoXuanshouBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.PlayBean;
import com.hxwl.wulinfeng.util.ImageUtils;

import java.util.List;

/**
 * Created by Allen on 2017/6/12.
 */
public class SearchPlayerAdapter extends BaseAdapter {
    private Activity context;
    private List<SousuoXuanshouBean.DataBean> listData;

    public SearchPlayerAdapter(Activity playerActivity, List<SousuoXuanshouBean.DataBean> listData) {
        this.context = playerActivity;
        this.listData = listData;
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
        if (listData.size() <= position) {
            return null;
        }
        final SousuoXuanshouBean.DataBean playData = listData.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_searchxuanshou, null);
            holder = new ViewHolder();
            holder.tv_addstate = (TextView) convertView.findViewById(R.id.tv_addstate);
            holder.tvName = (TextView) convertView.findViewById(R.id.txt_user_name);
            holder.user_head = (ImageView) convertView.findViewById(R.id.user_head);
            holder.iv_guoqi = (ImageView) convertView.findViewById(R.id.iv_guoqi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(playData.getPlayerName());
        holder.tvName.setTag(playData);

        if(!TextUtils.isEmpty(playData.getWeight())){
            holder.tv_addstate.setText(playData.getWeight()+"KG");
        }

        ImageUtils.getCirclePic(playData.getHeadImg(), holder.user_head, context);
        if(TextUtils.isEmpty(playData.getCountryImg())){
            holder.iv_guoqi.setVisibility(View.GONE);
        }else{
            holder.iv_guoqi.setVisibility(View.VISIBLE);
            ImageUtils.getPic(playData.getCountryImg(), holder.iv_guoqi, context);
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tv_addstate;
        TextView tvName;
        ImageView user_head;
        ImageView iv_guoqi;

    }
}
