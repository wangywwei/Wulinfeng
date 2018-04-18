package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.newwlf.schedule.bisairili.NianBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.Month;
import com.hxwl.wulinfeng.bean.NianFenYueBean;
import com.hxwl.wulinfeng.util.ToastUtils;

import java.util.List;

/**
 * Created by Allen on 2017/6/22.
 */
public class YueFenListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Activity activity ;
    private List<Month> yueFenList ;
    private View.OnClickListener yueClickListener ;

    public YueFenListAdapter(Activity activity, List<Month> yueFenList ,View.OnClickListener yueClickListener) {
        this.activity = activity ;
        this.yueFenList = yueFenList ;
        this.yueClickListener = yueClickListener ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view1 = inflate.inflate(R.layout.item_yuefen, null);
        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        final Month strTime;
        try {
            strTime = yueFenList.get(position);
            int white=activity.getResources().getColor(R.color.white);
            if(strTime.isselect()){
                myViewHolder.tv_nianfen.setBackgroundResource(R.drawable.rili);
                myViewHolder.tv_nianfen.setTextColor(activity.getResources().getColor(R.color.white));
            }else{
                myViewHolder.tv_nianfen.setBackgroundColor(white);
                myViewHolder.tv_nianfen.setTextColor(activity.getResources().getColor(R.color.black));
            }

            myViewHolder.tv_nianfen.setText(strTime.getMonth()+"");

            myViewHolder.tv_nianfen.setTag(strTime);
            myViewHolder.rlyt_nianfen.setOnClickListener(yueClickListener);
        }  catch (Exception e){
            ToastUtils.showToast(activity,"出现异常");
        }
    }

    @Override
    public int getItemCount() {
        return yueFenList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout rlyt_nianfen;
        public TextView tv_nianfen;

        public MyViewHolder(View itemView) {
            super(itemView);
            rlyt_nianfen=(RelativeLayout)itemView.findViewById(R.id.rlyt_nianfen);
            tv_nianfen=(TextView)itemView.findViewById(R.id.tv_nianfen);
        }
    }
}
