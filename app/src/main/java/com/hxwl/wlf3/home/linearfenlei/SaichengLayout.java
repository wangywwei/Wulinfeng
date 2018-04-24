package com.hxwl.wlf3.home.linearfenlei;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.YueyueBean;
import com.hxwl.wlf3.bean.DynamicBean;
import com.hxwl.wlf3.home.home1.Home3Adapter;
import com.hxwl.wlf3.home.home1.ShiPin1Adapter;
import com.hxwl.wlf3.home.home1.ShiPin1Fragment;
import com.hxwl.wulinfeng.MainActivity;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.hxwl.wulinfeng.adapter.SaiChengAdapter;
import com.hxwl.wulinfeng.adapter.ShiPinAdapter;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.bumptech.glide.Glide;
import com.hxwl.newwlf.URLS;
import com.hxwl.wlf3.bean.Home3Bean;
import com.hxwl.wulinfeng.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/4/13.
 */
/*
* 赛程界面
* */
public class SaichengLayout extends LinearLayout {
    private Context context;
    private View view;
    private Home3Bean.DataBean.SchedulesBean dataBean;

    private ImageView saicheng_bofang;
    private LinearLayout saicheng_ditu;
    private ImageView saicheng_img;
    private TextView saicheng_position;
    private ImageView saicheng_yuyuetupian;
    private TextView saicheng_name;
    private XRecyclerView saicheng_xrecycler;
    private RelativeLayout saicheng_relative;
    private HuoDongLayout huoDongLayout;
    private RelativeLayout saicheng_img_layout;
    private ShiPin1Adapter beiyongadapter;
    private ShiPin1Fragment shiPin1Fragment;


   private ArrayList<Home3Bean.DataBean.SchedulesBean.EventBean.AgainstListBean> arrayList = new ArrayList();
    private ShiPin1Adapter shiPinAdapter;
    private int state;
    private int hasSubscribed;


    public void setBean(final Home3Bean.DataBean.SchedulesBean bean) {
        this.dataBean = bean;
        state = dataBean.getState();
        hasSubscribed = dataBean.getHasSubscribed();

        try {
            Glide.with(context).load(URLS.IMG + dataBean.getEvent().getCoverImage()).into(saicheng_img);
            saicheng_name.setText(dataBean.getEvent().getTitle());
            saicheng_position.setText(dataBean.getEvent().getEventAddress());
        } catch (Exception e) {
        }

        try {

            if (state == 1) {

                if (hasSubscribed == 0) {
                    saicheng_yuyuetupian.setImageResource(R.drawable.yuyue3);//预约
                } else {
                    saicheng_yuyuetupian.setImageResource(R.drawable.yiyuyue3);//已预约
                }
            } else if (state == 2) {
                saicheng_yuyuetupian.setImageResource(R.drawable.zhibozhong3);//进行中--直播中
            } else if (state == 3) {
                saicheng_yuyuetupian.setImageResource(R.drawable.quanchenghuigu1);//结束--全程回顾
            }
        } catch (Exception e) {
        }

        arrayList.clear();
        arrayList.addAll(dataBean.getEvent().getAgainstListBeans());
        beiyongadapter.notifyDataSetChanged();

/*
* 活动
* */

        try {
            List<Home3Bean.DataBean.SchedulesBean.ActivityListBean> activityList = dataBean.getActivityList();
            if (null == activityList || activityList.size() == 0) {
                //为空的情况
                return;
            } else {
                saicheng_relative.removeAllViews();//清空布局
                for (int i = 0; i <activityList.size() ; i++) {
                    huoDongLayout = new HuoDongLayout(context);
                    saicheng_relative.addView(huoDongLayout);
                    huoDongLayout.setBean(activityList.get(i));
                }
            }
        } catch (Exception e) {
        }




        if (state==3){
            try {
                saicheng_img_layout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(ZixunVideoActivity.getIntent(context,dataBean.getId()+""));
                    }
                });
            } catch (Exception e) {
            }


            try {
                saicheng_name.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(ZixunVideoActivity.getIntent(context,dataBean.getId()+""));
                    }
                });
            } catch (Exception e) {
            }
            try {
                saicheng_yuyuetupian.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(ZixunVideoActivity.getIntent(context,dataBean.getId()+""));
                    }
                });
            }catch (Exception e){}

        }else if (state==2){
            try {
                saicheng_img_layout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(HuiGuDetailActivity.getIntent(context,dataBean.getId()+"",0));
                    }
                });
            } catch (Exception e) {
            }


            try {
                saicheng_name.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(HuiGuDetailActivity.getIntent(context,dataBean.getId()+"",0));
                    }
                });
            } catch (Exception e) {
            }
            try {
                saicheng_yuyuetupian.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(HuiGuDetailActivity.getIntent(context,dataBean.getId()+"",0));
                    }
                });
            }catch (Exception e){}

        }else if (state==1){
            if (hasSubscribed == 0) {




                saicheng_yuyuetupian.setImageResource(R.drawable.yuyue3);//预约



                try {
                    saicheng_img_layout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            initGuanzhu(position,URLS.SCHEDULE_USERSUBSCRIBE);
                        }
                    });
                } catch (Exception e) {
                }


                try {
                    saicheng_name.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(HuiGuDetailActivity.getIntent(context,dataBean.getId()+"",0));
                        }
                    });
                } catch (Exception e) {
                }
                try {
                    saicheng_yuyuetupian.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(HuiGuDetailActivity.getIntent(context,dataBean.getId()+"",0));
                        }
                    });
                }catch (Exception e){}







            } else {





            }




        }




        try {
            saicheng_ditu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "点击了地图", Toast.LENGTH_SHORT).show();

                    getPopupWindow();

                }
            });
        } catch (Exception e) {
        }



        
    }

    public SaichengLayout(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.caicheng_item, this);
        saicheng_bofang = (ImageView) view.findViewById(R.id.saicheng_bofang);
        saicheng_ditu = (LinearLayout) view.findViewById(R.id.saicheng_ditu);
        saicheng_img = (ImageView) view.findViewById(R.id.saicheng_img);
        saicheng_name = (TextView) view.findViewById(R.id.saicheng_name);
        saicheng_position = (TextView) view.findViewById(R.id.saicheng_position);
        saicheng_yuyuetupian = (ImageView) view.findViewById(R.id.saicheng_yuyuetupian);
        saicheng_xrecycler = (XRecyclerView) view.findViewById(R.id.saicheng_xrecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        saicheng_xrecycler.setLayoutManager(layoutManager);
        saicheng_xrecycler.setNestedScrollingEnabled(false);

            try {
                try {

                    //对阵的适配器
                    shiPinAdapter = new ShiPin1Adapter(context, arrayList);
                    saicheng_xrecycler.setAdapter(shiPinAdapter);
            }catch (Exception e){}


        }catch (Exception e){
        }
    }


//    private void initGuanzhu(final int position,String url) {
//        OkHttpUtils.post()
//                .url(url)
//                .addParams("token", MakerApplication.instance.getToken())
//                .addParams("userId", MakerApplication.instance.getUid())
//                .addParams("scheduleId",list6.get(position).getEvent().getId()+"")
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        UIUtils.showToast("服务器异常");
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        JsonValidator jsonValidator = new JsonValidator();
//                        if (jsonValidator.validate(response)){
//                            Gson gson = new Gson();
//                            try {
//                                YueyueBean bean = gson.fromJson(response, YueyueBean.class);
//                                if (bean.getCode().equals("1000")){
//                                    switch (list6.get(position).getHasSubscribed()){
//                                        case 0:
//
//                                            list6.get(position).setHasSubscribed(1);
//                                            break;
//
//                                        case 1:
//                                            list6.get(position).setHasSubscribed(0);
//                                            break;
//                                    }
//
//
//                                    Home3Adapter.this.
//                                            notifyDataSetChanged();
//
//
//
////                                    switch (list.get(position).getUserIsSubscribe()){
////                                        case "0":
////                                            list.get(position).setUserIsSubscribe("1");
////                                            break;
////                                        case "1":
////                                            list.get(position).setUserIsSubscribe("0");
////                                            break;
////                                    }
////
////                                    RecentHeaderAdapter2.this.notifyDataSetChanged();
//                                    ToastUtils.showToast(context,bean.getMessage()+"");
//
//                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
//                                    UIUtils.showToast(bean.getMessage());
//                                    context.startActivity(LoginActivity.getIntent(context));
//                                }
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }
//
//                        }
//
//                    }
//                });
//
//    }

    public void getPopupWindow() {

        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(getContext(), R.layout.camera_pop_menu, null);
        Button btnCamera = (Button) popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnAlbum = (Button) popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels/2;
        final PopupWindow popWindow = new PopupWindow(popView,width,height, true);

        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(false);// 设置同意在外点击消失

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_camera_pop_camera:


                        if (AMapUtil.isInstallByRead("com.autonavi.minimap")){
                            AMapUtil.goToNaviActivity(getContext(),"test",null,"34.264642646862","108.95108518068","1","2");
                        }else {
                            Toast.makeText(getContext(), "请先安装高德地图", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case R.id.btn_camera_pop_album:
                        Toast.makeText(getContext(), "请先安装腾讯地图", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_camera_pop_cancel:
//                        Toast.makeText(getContext(), "点击了3", Toast.LENGTH_SHORT).show();
                        break;
                }
                popWindow.dismiss();
            }
        };

        btnCamera.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}

