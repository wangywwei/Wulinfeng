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
import com.hxwl.wulinfeng.bean.NianFenYueBean;
import com.hxwl.wulinfeng.util.ToastUtils;

import java.util.List;


/**
 * Created by Allen on 2017/6/22.
 */
public class NianFenListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Activity activity ;
    private List<NianBean.DataBean> listData ;
    private View.OnClickListener nianClickListener ;

    public NianFenListAdapter(Activity activity, List<NianBean.DataBean> listData ,View.OnClickListener nianClickListener ) {
        this.activity = activity ;
        this.listData = listData ;
        this.nianClickListener = nianClickListener ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view1 = inflate.inflate(R.layout.item_nianfen, null);
        return new MyViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        final NianBean.DataBean strTime;
        try {
            strTime = listData.get(position);
            int white=activity.getResources().getColor(R.color.white);
            if(strTime.isSelect()){
                myViewHolder.tv_nianfen.setBackgroundColor(white);
                myViewHolder.tv_nianfen.setTextColor(activity.getResources().getColor(R.color.shouye_tab));
            }else{
                myViewHolder.tv_nianfen.setBackgroundColor(white);
                myViewHolder.tv_nianfen.setTextColor(activity.getResources().getColor(R.color.text_black));
            }
            myViewHolder.tv_nianfen.setText(strTime.getYear());
            myViewHolder.tv_nianfen.setTag(strTime);
            myViewHolder.rlyt_nianfen.setOnClickListener(nianClickListener);
        }  catch (Exception e){
            ToastUtils.showToast(activity,"出现异常");
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
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
