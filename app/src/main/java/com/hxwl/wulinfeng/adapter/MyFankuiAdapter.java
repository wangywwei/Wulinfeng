package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.newwlf.modlebean.MyFankuiBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.MyFanKuiBean;
import com.hxwl.wulinfeng.bean.SystemMessageBean;

import java.util.List;

/**
 * Created by Allen on 2017/6/14.
 */
public class MyFankuiAdapter extends BaseAdapter {
    private Activity activity;
    private List<MyFankuiBean.DataBean> listData;

    public MyFankuiAdapter(Activity activity, List<MyFankuiBean.DataBean> listData) {
        this.activity = activity;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData != null ? listData.size() : 0;
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
        MyFankuiBean.DataBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_myfankui,
                    null);
            holder = new ViewHolder();
            holder.tv_da = ((TextView) convertView
                    .findViewById(R.id.tv_da));

            holder.tv_wen = ((TextView) convertView
                    .findViewById(R.id.tv_wen));

            holder.rl_da = ((RelativeLayout) convertView
                    .findViewById(R.id.rl_da));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        if ("0".equals(dataBean.getIs_read()) && !TextUtils.isEmpty(dataBean.getHuifu())) { //未读
//            holder.tv_wen.setText(Html.fromHtml("·问 : " + changeTextColor(dataBean.getMsg(), "#111111")));
//        } else {//已读
            holder.tv_wen.setText(Html.fromHtml("问 : " + changeTextColor(dataBean.getContent(), "#111111")));
//        }
        if(TextUtils.isEmpty(dataBean.getAnswer())){
            holder.rl_da.setVisibility(View.GONE);
        }else{
            holder.rl_da.setVisibility(View.VISIBLE);
            holder.tv_da.setText(Html.fromHtml("答 : " + changeTextColor(dataBean.getAnswer(), "#898989")));
        }
        return convertView;
    }

    class ViewHolder {
        public TextView tv_da;
        public TextView tv_wen;
        public RelativeLayout rl_da;
    }
    /**
     * 改变文本部分颜色
     */
    public String changeTextColor(String text, String color) {
        return "<font color=\"" + color + "\">" + text + "</font>";
    }
}
