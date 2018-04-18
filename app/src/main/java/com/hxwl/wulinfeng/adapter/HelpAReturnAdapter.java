package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.newwlf.modlebean.ChangjianWentBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HelpAndReturnActivity;
import com.hxwl.wulinfeng.bean.HelpAndReturnBean;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.bean.TujiImgs;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

/**
 * Created by Allen on 2017/6/20.
 * 帮助与反馈界面适配器
 */
public class HelpAReturnAdapter extends BaseAdapter{
    private Activity activity ;
    private List<ChangjianWentBean.DataBean> listData ;
    public HelpAReturnAdapter(Activity activity, List<ChangjianWentBean.DataBean> listData) {
        this.activity = activity ;
        this.listData = listData ;
    }

    @Override
    public int getCount() {
        return listData != null ? listData.size() :  0;
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
        ChangjianWentBean.DataBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_helpandreturn,null);
            holder = new ViewHolder();
            holder.tv_title = ((TextView) convertView
                    .findViewById(R.id.tv_title));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setTag(dataBean);
        holder.tv_title.setText(dataBean.getQuestioncol());
        return convertView;
    }
    class ViewHolder{
        public TextView tv_title;
    }
}
