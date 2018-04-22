package com.hxwl.wlf3.home.home2;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.home.home2.chongfu.DuiZhen1Layout;
import com.hxwl.wlf3.home.home2.chongfu.Saicheng1Layout;
import com.hxwl.wlf3.home.home2.chongfu.Video1Listlayout;
import com.hxwl.wlf3.home.linearfenlei.PureTextLayout;
import com.hxwl.wulinfeng.R;

import java.util.ArrayList;

/**
 ** 赛事动态
 *
 * Created by Administrator on 2018/4/20.
 */

public class DynamicActivity extends RecyclerView.Adapter<DynamicActivity.ViewHolder> {
    private Context context;
    private ArrayList<DynamicBean.DataBean> list6;
    private DuiZhen1Layout duiZhenLayout;
    private Video1Listlayout videoListlayout;
    private PureTextLayout pureTextLayout;
    private Saicheng1Layout saicheng1Layout;


    public DynamicActivity(Context context, ArrayList<DynamicBean.DataBean> list) {
        this.context = context;
        this.list6 = list;
    }

    @Override
    public DynamicActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dongtai_activity, null);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        DynamicActivity.ViewHolder viewHolder = new DynamicActivity.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DynamicActivity.ViewHolder holder, final int position) {


        try {
            int timeState = list6.get(position).getEvent().getTimeState();
            if (timeState==1){
                holder.dongtai_img.setImageResource(R.drawable.yuanhuise);
                holder.dongtai_view.setBackgroundColor(Color.parseColor("#999999"));
                holder.dongtai_duizhen.setImageResource(R.drawable.biaoqianhui);
                holder.dongtai_time.setTextColor(Color.parseColor("#999999"));
            }else  if (timeState==2){
                holder.dongtai_img.setImageResource(R.drawable.yuanhongse);
                holder.dongtai_view.setBackgroundColor(Color.parseColor("#C52720"));
                holder.dongtai_duizhen.setImageResource(R.drawable.biaoqianhong);
                holder.dongtai_time.setTextColor(Color.parseColor("#C52720"));
            }else if (timeState==3){
                holder.dongtai_img.setImageResource(R.drawable.yuanlanse);
                holder.dongtai_view.setBackgroundColor(Color.parseColor("#007AFF"));
                holder.dongtai_duizhen.setImageResource(R.drawable.biaoqianlan);
                holder.dongtai_time.setTextColor(Color.parseColor("#007AFF"));
            }

        }catch (Exception e){
        }


        try {

            holder.dongtai_time.setText(list6.get(position).getEvent().getEventTime());
        }catch (Exception e){
        }




   /*VIDEO(1,"视频"),RICH(2,"图文"),GATHER(3,"图集"),WORD(4,"纯文本"),SCHEDULE(5,"赛程"),AGAINST(6,"对阵"); */
        try {
            int showType = list6.get(position).getEvent().getShowType();
            if (showType==1){
                try {
                    holder.dongtai_relative.removeAllViews();//清空布局
                    videoListlayout = new Video1Listlayout(context);
                    holder.dongtai_relative.addView(videoListlayout);
                    videoListlayout.setxqBean(list6.get(position));
                }catch (Exception e){
                }

            }else if (showType==2){
                try {
                    holder.dongtai_relative.removeAllViews();//清空布局
                    videoListlayout = new Video1Listlayout(context);
                    holder.dongtai_relative.addView(videoListlayout);
                    videoListlayout.setxqBean(list6.get(position));
                }catch (Exception e){
                }
            }else if (showType==3){
                try {
                    holder.dongtai_relative.removeAllViews();//清空布局
                    videoListlayout = new Video1Listlayout(context);
                    holder.dongtai_relative.addView(videoListlayout);
                    videoListlayout.setxqBean(list6.get(position));
                }catch (Exception e){
                }

            }else if (showType==4){
                try {
//                    holder.dongtai_relative.removeAllViews();//清空布局
//                    pureTextLayout = new PureTextLayout(context);
//                    holder.dongtai_relative.addView(pureTextLayout);
//                    pureTextLayout.setxqBean(list6.get(position));
                }catch (Exception e){
                }

            }else if (showType==5){
                try {
                    holder.dongtai_relative.removeAllViews();//清空布局
                    saicheng1Layout = new Saicheng1Layout(context);
                    holder.dongtai_relative.addView(saicheng1Layout);
                    saicheng1Layout.setBean(list6.get(position));
                }catch (Exception e){
                }
            }else if (showType==6){
                try {
                    holder.dongtai_relative.removeAllViews();//清空布局
                    duiZhenLayout=new DuiZhen1Layout(context);
                    holder.dongtai_relative.addView(duiZhenLayout);
                    duiZhenLayout.setBean(list6.get(position));
                }catch (Exception e){
                }
            }
        }catch (Exception e){
        }




    }

    @Override
    public int getItemCount() {
        return list6.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView dongtai_duizhen;
        private TextView dongtai_time;
        private ImageView dongtai_img;
        private View dongtai_view;
        private RelativeLayout dongtai_relative;

        public ViewHolder(View itemView) {
            super(itemView);

            dongtai_relative= (RelativeLayout) itemView.findViewById(R.id.dongtai_relative);
            dongtai_view= (View) itemView.findViewById(R.id.dongtai_view);
            dongtai_img= (ImageView) itemView.findViewById(R.id.dongtai_img);
            dongtai_duizhen= (ImageView) itemView.findViewById(R.id.dongtai_duizhen);
            dongtai_time= (TextView) itemView.findViewById(R.id.dongtai_time);

        }
    }
}
