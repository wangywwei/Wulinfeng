package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.SearchActivity;
import com.hxwl.wulinfeng.bean.BiSaiShiPinBean;
import com.hxwl.wulinfeng.util.ImageUtils;

import java.util.List;

/**
 * Created by Allen on 2017/6/27.
 */
public class AboveHistAdapter extends BaseAdapter{
    private Activity activity ;
    private List<String> listDataUp ;
    public AboveHistAdapter(Activity activity, List<String> listDataUp) {
        this.activity = activity ;
        this.listDataUp = listDataUp ;
    }

    @Override
    public int getCount() {
        if(listDataUp != null){
            return listDataUp.size() > 10 ? 10 : listDataUp.size() ;
        }
        return 0;
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
    public View getView(int paramInt, View convertView, ViewGroup viewGroup) {
        String text = listDataUp.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_hotsearch,
                    null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(text);
        holder.text.setTag(text);
        return convertView;
    }

    class ViewHolder{
        public TextView text;
    }
}
