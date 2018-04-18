package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.ZiXunBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

import me.iwf.photopicker.PhotoPickUtils;

/**
 * Created by Allen on 2017/6/1.
 * 首页资讯listview adapter
 */
public class ZiXunAdapter extends BaseAdapter{
    private List<ZiXunBean> listData ;
    private Activity context ;
    public ZiXunAdapter(Activity activity , List<ZiXunBean> listData){
        this.context = activity ;
        this.listData =  listData;
    }
    @Override
    public int getCount() {
        return listData == null ? 0:listData.size();
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
        if(paramInt >= listData.size()){
            return null;
        }
        ZiXunBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_zixun,
                    null);
            holder = new ViewHolder();
            holder.tv_title = ((TextView) convertView
                    .findViewById(R.id.tv_title));

            holder.tv_time = ((TextView) convertView
                    .findViewById(R.id.tv_time));

            holder.tv_kanguo = ((TextView) convertView
                    .findViewById(R.id.tv_kanguo));

            holder.iv_news_img = ((ImageView) convertView
                    .findViewById(R.id.iv_news_img));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setTag(dataBean);
        holder.tv_title.setText(dataBean.getTitle());
        ImageUtils.getPic(dataBean.getFengmiantu() !=null ? dataBean.getFengmiantu().trim() : "" , holder.iv_news_img ,context);
        holder.tv_time.setText(dataBean.getTime() == null ? "" : TimeUtiles.getTimeeForTuiJ(dataBean.getTime()));
        holder.tv_kanguo.setText(dataBean.getOpen_times()+"人看过");
        return convertView;

    }
    class ViewHolder{
        public TextView tv_title;
        public TextView tv_time;//姓名
        public TextView tv_kanguo;//日期时间
        public ImageView iv_news_img;//资讯图片
    }
}
