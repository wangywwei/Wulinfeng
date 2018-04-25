package com.hxwl.wlf3.home.home1;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hxwl.common.tencentplay.utils.TCUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.YueyueBean;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wlf3.home.home2.EventActivity;
import com.hxwl.wlf3.home.linearfenlei.SaichengLayout;
import com.hxwl.wlf3.home.linearfenlei.PureTextLayout;
import com.hxwl.wlf3.home.linearfenlei.VideoListlayout;
import com.hxwl.wlf3.home.linearfenlei.DuiZhenLayout;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import okhttp3.Call;

public class Home3Adapter extends RecyclerView.Adapter<Home3Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Home3Bean.DataBean.SchedulesBean> list6;
    private DuiZhenLayout duiZhenLayout;
    private VideoListlayout videoListlayout;
    private PureTextLayout pureTextLayout;
    private SaichengLayout saicheng1Layout;


    private boolean aBoolean=true;
    private int hasSubscribed;
    private int state;

    public Home3Adapter(Context context, ArrayList<Home3Bean.DataBean.SchedulesBean> list) {
        this.context = context;
        this.list6 = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_saicheng, null);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        hasSubscribed = list6.get(position).getHasSubscribed();
        state = list6.get(position).getState();
        try {
            Glide.with(context).load(URLS.IMG+ list6.get(position).getPublicityImg()).into(holder.home_saishi_img);
        }catch (Exception e){
        }

        try {
            holder.home_saishi_name.setText(list6.get(position).getScheduleName()+"");
        }catch (Exception e){
        }

        try {
            long beginTime = list6.get(position).getBeginTime();
            String s = TCUtils.asTwoDigit(beginTime);
            holder.home_saishi_item.setText(s+"");//时间
        }catch (Exception e){
        }



        try {
            holder.home_saishi_yuyue.setVisibility(View.GONE);
        }catch (Exception e){

        }



//        try {
//
//            if (state ==1){
//
//                if (hasSubscribed ==0){
//                    holder.home_saishi_yuyue.setImageResource(R.drawable.yuyue3);//预约
//                }else{
//                    holder.home_saishi_yuyue.setImageResource(R.drawable.yiyuyue3);//已预约
//                }
//            }else if (state ==2){
//                holder.home_saishi_yuyue.setImageResource(R.drawable.zhibozhong3);//进行中--直播中
//            }else if (state ==3){
//                holder.home_saishi_yuyue.setImageResource(R.drawable.quanchenghuigu1);//结束--全程回顾
//            }
//        }catch (Exception e){
//        }

        try {
            int timelineState = list6.get(position).getTimelineState();
            if (timelineState==0){
                holder.home_saishi_shijianzhou.setImageResource(R.drawable.shijianzhou);
            }else if (timelineState==1){
                holder.home_saishi_shijianzhou.setImageResource(R.drawable.shijianzhou2);
            }else if (timelineState==2){
                holder.home_saishi_shijianzhou.setImageResource(R.drawable.shijianzhou3);
            }else if (timelineState==3){
                holder.home_saishi_shijianzhou.setImageResource(R.drawable.shijianzhou4);
            }else if (timelineState==4){
                holder.home_saishi_shijianzhou.setImageResource(R.drawable.shijianzhou5);
            }else if (timelineState==5){
                holder.home_saishi_shijianzhou.setImageResource(R.drawable.shijianzhou6);
            }
        }catch (Exception e){
        }

/*VIDEO(1,"视频"),RICH(2,"图文"),GATHER(3,"图集"),WORD(4,"纯文本"),SCHEDULE(5,"赛程"),AGAINST(6,"对阵"); */
        try {
            int showType = list6.get(position).getEvent().getShowType();
            if (showType==1){
                try {
                    holder.home_saishi_xrecycler.removeAllViews();//清空布局
                    videoListlayout = new VideoListlayout(context);
                    holder.home_saishi_xrecycler.addView(videoListlayout);
                    videoListlayout.setBean(list6.get(position),position);
                }catch (Exception e){
                }

            }else if (showType==2){
                try {
                    holder.home_saishi_xrecycler.removeAllViews();//清空布局
                    videoListlayout = new VideoListlayout(context);
                    holder.home_saishi_xrecycler.addView(videoListlayout);
                    videoListlayout.setBean(list6.get(position),position);
                }catch (Exception e){
                }

            }else if (showType==3){
                try {
                    holder.home_saishi_xrecycler.removeAllViews();//清空布局
                    videoListlayout = new VideoListlayout(context);
                    holder.home_saishi_xrecycler.addView(videoListlayout);
                    videoListlayout.setBean(list6.get(position),position);
                }catch (Exception e){
                }

            }else if (showType==4){
                try {
                    holder.home_saishi_xrecycler.removeAllViews();//清空布局
                    pureTextLayout = new PureTextLayout(context);
                    holder.home_saishi_xrecycler.addView(pureTextLayout);
                    pureTextLayout.setBean(list6.get(position),position);
                }catch (Exception e){
                }

            }else if (showType==5){
                try {
                    holder.home_saishi_yuyue.setVisibility(View.GONE);
                    holder.home_saishi_xrecycler.removeAllViews();//清空布局
                    saicheng1Layout = new SaichengLayout(context);
                    holder.home_saishi_xrecycler.addView(saicheng1Layout);
                    saicheng1Layout.setBean(list6.get(position),position);
                }catch (Exception e){
                }
            }else if (showType==6){
                    try {
                        holder.home_saishi_xrecycler.removeAllViews();//清空布局
                    duiZhenLayout=new DuiZhenLayout(context);
                    holder.home_saishi_xrecycler.addView(duiZhenLayout);
                    duiZhenLayout.setBean(list6.get(position),position);
                }catch (Exception e){
                }
            }
        }catch (Exception e){
        }




//        try {//          预约
//            holder.home_saishi_yuyue.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                            if (hasSubscribed==0){
//                                initGuanzhu(position,URLS.SCHEDULE_USERSUBSCRIBE);
//
//
//                            }else if (hasSubscribed==1){
////                                initGuanzhu(position,URLS.SCHEDULE_USERCANCELSUBSCRIBE);
//                            }
//
//                }
//            });
//        }catch (Exception e){}


        try {//     赛事
            holder.home_saishi_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.startActivity(EventActivity.getIntent(context));

                }
            });

        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return list6.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView home_saishi_img;
        private TextView home_saishi_name;
        private TextView home_saishi_item;
        private ImageView home_saishi_yuyue;
        private ImageView home_saishi_shijianzhou;
        private RelativeLayout home_saishi_xrecycler;

        public ViewHolder(View itemView) {
            super(itemView);
            home_saishi_img= (ImageView) itemView.findViewById(R.id.home_saishi_img);
            home_saishi_name= (TextView) itemView.findViewById(R.id.home_saishi_name);
            home_saishi_item= (TextView) itemView.findViewById(R.id.home_saishi_item);
            home_saishi_yuyue= (ImageView) itemView.findViewById(R.id.home_saishi_yuyue);
            home_saishi_shijianzhou= (ImageView) itemView.findViewById(R.id.home_saishi_shijianzhou);
            home_saishi_xrecycler= (RelativeLayout) itemView.findViewById(R.id.home_saishi_xrecycler);
        }
    }


    private void initGuanzhu(final int position,String url) {
        OkHttpUtils.post()
                .url(url)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("scheduleId",list6.get(position).getEvent().getId()+"")
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
                                    switch (list6.get(position).getHasSubscribed()){
                                        case 0:

                                            list6.get(position).setHasSubscribed(1);
                                        break;

                                        case 1:
                                            list6.get(position).setHasSubscribed(0);
                                            break;
                                    }


                                    Home3Adapter.this.notifyDataSetChanged();



//                                    switch (list.get(position).getUserIsSubscribe()){
//                                        case "0":
//                                            list.get(position).setUserIsSubscribe("1");
//                                            break;
//                                        case "1":
//                                            list.get(position).setUserIsSubscribe("0");
//                                            break;
//                                    }
//
//                                    RecentHeaderAdapter2.this.notifyDataSetChanged();
                                    ToastUtils.showToast(context,bean.getMessage()+"");

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    context.startActivity(LoginActivity.getIntent(context));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });

    }


}
