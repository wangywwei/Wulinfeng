package com.hxwl.newwlf.schedule.recent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.RecentJinqiBean;
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
 * Created by Administrator on 2018/1/29.
 */

public class RecentHeaderAdapter2 extends BaseAdapter {
    private ArrayList<RecentJinqiBean.DataBean.NotBeginScheduleListBean> list;
    private Context context;

    public RecentHeaderAdapter2(ArrayList<RecentJinqiBean.DataBean.NotBeginScheduleListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder v;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.recent_item, null);
            v=new ViewHolder(convertView);
            convertView.setTag(v);
        } else {
            v= (ViewHolder) convertView.getTag();
        }
        v.recent_zhuangtai.setVisibility(View.GONE);
        v.recent_zhuangtai2.setVisibility(View.GONE);
        v.recent_zhuangtai3.setVisibility(View.VISIBLE);
        v.recent_list.setVisibility(View.GONE);
        v.recent_tiem.setText(DateUtils.timet(list.get(position).getScheduleBeginTime()));
        v.recent_title.setText(list.get(position).getCompetitionName());
        GlidUtils.setGrid(context, URLS.IMG+list.get(position).getPublicityImg(),v.recent_img);
        v.recent_content.setText(list.get(position).getScheduleName());
        v.recent_address.setText(list.get(position).getHoldAddress());
        switch (list.get(position).getUserIsSubscribe()){
            case "0":
                v.recent_zhuangtai3.setImageResource(R.drawable.yuyue1);
                break;
            case "1":
                v.recent_zhuangtai3.setImageResource(R.drawable.yiyuyue);

                break;
        }

        v.recent_zhuangtai3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                switch (list.get(position).getUserIsSubscribe()){
                    case "0":
                        initGuanzhu(position,URLS.SCHEDULE_USERSUBSCRIBE);
                        break;
                    case "1":
                        initGuanzhu(position,URLS.SCHEDULE_USERCANCELSUBSCRIBE);
                        break;
                }


            }
        });

        return convertView;
    }


    private void initGuanzhu(final int position,String url) {
        OkHttpUtils.post()
                .url(url)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("scheduleId",list.get(position).getScheduleId()+"")
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
                                    switch (list.get(position).getUserIsSubscribe()){
                                        case "0":
                                            list.get(position).setUserIsSubscribe("1");
                                            break;
                                        case "1":
                                            list.get(position).setUserIsSubscribe("0");
                                            break;
                                    }

                                    RecentHeaderAdapter2.this.notifyDataSetChanged();
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

    public static class ViewHolder {
        public View rootView;
        public TextView recent_tiem;
        public TextView recent_title;
        public ImageView recent_img;
        public TextView recent_content;
        public TextView recent_address;
        public ImageView recent_zhuangtai;
        public ImageView recent_zhuangtai2;
        public ImageView recent_zhuangtai3;
        public RecyclerView recent_list;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.recent_tiem = (TextView) rootView.findViewById(R.id.recent_tiem);
            this.recent_title = (TextView) rootView.findViewById(R.id.recent_title);
            this.recent_img = (ImageView) rootView.findViewById(R.id.recent_img);
            this.recent_content = (TextView) rootView.findViewById(R.id.recent_content);
            this.recent_address = (TextView) rootView.findViewById(R.id.recent_address);
            this.recent_zhuangtai = (ImageView) rootView.findViewById(R.id.recent_zhuangtai);
            this.recent_zhuangtai2 = (ImageView) rootView.findViewById(R.id.recent_zhuangtai2);
            this.recent_zhuangtai3 = (ImageView) rootView.findViewById(R.id.recent_zhuangtai3);
            this.recent_list = (RecyclerView) rootView.findViewById(R.id.recent_recycler);
        }

    }
}
