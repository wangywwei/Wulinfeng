package com.hxwl.newwlf.home.home.Recommend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.GlidUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.HomeRecommendBean;
import com.hxwl.newwlf.modlebean.YueyueBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.DateUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/1/11.
 */

public class RecommendLiveAdapter extends RecyclerView.Adapter<RecommendLiveAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HomeRecommendBean.DataBean.ScheduleListBean> list;

    private OnItemclickLinter onItemclickLinter;

    public void setOnItemclickLinter(OnItemclickLinter onItemclickLinter) {
        this.onItemclickLinter = onItemclickLinter;
    }

    public interface OnItemclickLinter{
        public void onItemClicj(int position);
    }

    public RecommendLiveAdapter(Context context,
                                ArrayList<HomeRecommendBean.DataBean.ScheduleListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_recommend_live, null);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder viewHolder = new ViewHolder(view);




        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onItemclickLinter){
                    onItemclickLinter.onItemClicj(position);
                }
            }
        });
        holder.live_title.setText(list.get(position).getScheduleName());
        holder.live_time.setText(DateUtils.timet(list.get(position).getScheduleBeginTime()));
        GlidUtils.setGrid2(context, URLS.IMG+list.get(position).getRedPlayerHeadImage(),holder.live_touxiang);
        GlidUtils.setGrid2(context, URLS.IMG+list.get(position).getBluePlayerHeadImage(),holder.live_touxiang2);
        holder.live_name.setText(list.get(position).getRedPlayerName());
        holder.live_julebu.setText(list.get(position).getRedPlayerClubName());
        holder.live_name2.setText(list.get(position).getBluePlayerName());
        holder.live_julebu2.setText(list.get(position).getBluePlayerClubName());


        switch (list.get(position).getScheduleState()){
            case 0:
                holder.live_boolean.setImageResource(R.drawable.yuyue1);
                break;
            case 1:
                holder.live_boolean.setImageResource(R.drawable.yiyuyue);
                break;
            case 2:
                holder.live_boolean.setImageResource(R.drawable.zhibozhong1);
                break;
            case 3:
                holder.live_boolean.setImageResource(R.drawable.quanchenghuigu1);
                break;
        }

        holder.live_boolean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getScheduleState()==0){
                    initGuanzhu(position,URLS.SCHEDULE_USERSUBSCRIBE);
                }
            }
        });

    }

    private void initGuanzhu(final int position, String scheduleUsersubscribe) {
        OkHttpUtils.post()
                .url(scheduleUsersubscribe)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("scheduleId",list.get(position).getScheduleId()+"")
                .addParams("token",MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                YueyueBean bean = gson.fromJson(response, YueyueBean.class);
                                if (bean.getCode().equals("1000")){
                                    list.get(position).setScheduleState(1);
                                    RecommendLiveAdapter.this.notifyDataSetChanged();
                                    ToastUtils.showToast(context,bean.getMessage()+"");

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });

    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView live_title;
        private TextView live_time;
        private ImageView live_touxiang;
        private TextView live_name;
        private TextView live_julebu;
        private ImageView live_boolean;
        private ImageView live_touxiang2;
        private TextView live_name2;
        private TextView live_julebu2;

        public ViewHolder(View itemView) {
            super(itemView);
            live_title= (TextView) itemView.findViewById(R.id.live_title);
            live_time= (TextView) itemView.findViewById(R.id.live_time);
            live_touxiang= (ImageView) itemView.findViewById(R.id.live_touxiang);
            live_name= (TextView) itemView.findViewById(R.id.live_name);
            live_julebu= (TextView) itemView.findViewById(R.id.live_julebu);
            live_boolean= (ImageView) itemView.findViewById(R.id.live_boolean);
            live_touxiang2= (ImageView) itemView.findViewById(R.id.live_touxiang2);
            live_name2= (TextView) itemView.findViewById(R.id.live_name2);
            live_julebu2= (TextView) itemView.findViewById(R.id.live_julebu2);
        }
    }

}

