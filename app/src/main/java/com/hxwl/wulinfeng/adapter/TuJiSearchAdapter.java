package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.bean.TujiImgs;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

/**
 * Created by Allen on 2017/6/2.
 */
public class TuJiSearchAdapter extends BaseAdapter{
    private Activity activity ;
    private List<TuJiBean> listData ;
    public TuJiSearchAdapter(Activity activity, List<TuJiBean> listData) {
        this.activity = activity ;
        this.listData = listData ;
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
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
        TuJiBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_tujisearch,null);
            holder = new ViewHolder();
            holder.tv_title = ((TextView) convertView
            .findViewById(R.id.tv_title));

            holder.tv_time = ((TextView) convertView
                    .findViewById(R.id.tv_time));

            holder.tv_count = ((TextView) convertView
                    .findViewById(R.id.tv_count));

            holder.tv_seecount = ((TextView) convertView
                    .findViewById(R.id.tv_seecount));

            holder.iv_img = ((ImageView) convertView
                    .findViewById(R.id.iv_img));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setTag(dataBean);
        holder.tv_title.setText(dataBean.getTitle());
        List<TujiImgs> imgList = dataBean.getImgs();
        if(imgList != null && imgList.size() > 0){
            for(int i = 0 ;i < imgList.size() ; i ++){
                if(imgList.get(i).getUrl() != null){
                    ImageUtils.getPic(imgList.get(i).getUrl() !=null ? imgList.get(i).getUrl().trim() : "" , holder.iv_img ,activity);
                }
            }
        }

        holder.tv_time.setText(dataBean.getTime() == null ? "" : TimeUtiles.getTimeeForTuiJ(dataBean.getTime()));
        holder.tv_count.setText(dataBean.getImgs() != null ? dataBean.getImgs().size()+"" : 0+"");
        holder.tv_seecount.setText(dataBean.getOpen_times()+"人看过");
        return convertView;
    }
    class ViewHolder{
        public TextView tv_title;
        public TextView tv_time;//姓名
        public TextView tv_count;//日期时间
        public TextView tv_seecount;//日期时间
        public ImageView iv_img;//资讯图片
    }
}
