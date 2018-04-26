package com.hxwl.wlf3.home.xiangqing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.wlf3.bean3.Pinlin3Bean;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

public class ShijianZIAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Pinlin3Bean.DataBean.CommentListBean> listBeans;
    private int pos;

    public ShijianZIAdapter(Context context, ArrayList<Pinlin3Bean.DataBean.CommentListBean> listBeans,int pos) {
        this.context = context;
        this.listBeans = listBeans;
        this.pos = pos;
    }

    @Override
    public int getCount() {
        return listBeans.size() > 3 ? 3 : listBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.zipinlun_item, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        if (StringUtils.isBlank(listBeans.get(position).getReferUserId())){
            viewHolder.ziname1.setText(listBeans.get(position).getNickName()+"：");
            viewHolder.ziname2.setVisibility(View.GONE);
            viewHolder.zihuifu.setVisibility(View.GONE);
        }else {
            viewHolder.ziname1.setText(listBeans.get(position).getNickName());
            viewHolder.ziname2.setText(listBeans.get(position).getReferUserNickName()+"：");
            viewHolder.ziname2.setVisibility(View.VISIBLE);
            viewHolder.zihuifu.setVisibility(View.VISIBLE);
        }

        viewHolder.zicontent.setText(listBeans.get(position).getContent());

        viewHolder.ziname1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPinlun1Clicklistener.onPinlun1Clicklistener(pos,position);
            }
        });
        viewHolder.ziname2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPinlun2Clicklistener.onPinlun2Clicklistener(pos,position);
            }
        });
        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView ziname1;
        public TextView zihuifu;
        public TextView ziname2;
        public TextView zicontent;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.ziname1 = (TextView) rootView.findViewById(R.id.ziname1);
            this.zihuifu = (TextView) rootView.findViewById(R.id.zihuifu);
            this.ziname2 = (TextView) rootView.findViewById(R.id.ziname2);
            this.zicontent = (TextView) rootView.findViewById(R.id.zicontent);
        }

    }


    public OnZanClicklistener onPinlun1Clicklistener;
    public OnZanClicklistener2 onPinlun2Clicklistener;

    public void setOnPinlun1Clicklistener(OnZanClicklistener onPinlun1Clicklistener) {
        this.onPinlun1Clicklistener = onPinlun1Clicklistener;
    }

    public void setOnPinlun2Clicklistener(OnZanClicklistener2 onPinlun2Clicklistener) {
        this.onPinlun2Clicklistener = onPinlun2Clicklistener;
    }

    public interface OnZanClicklistener {
        void onPinlun1Clicklistener(int groupPosition, int childPosition);

    }
    public interface OnZanClicklistener2 {
        void onPinlun2Clicklistener(int groupPosition, int childPosition);

    }


}
