package com.hxwl.wulinfeng.adapter;

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
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.SearchSaichengBean;
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
 * Created by Allen on 2017/6/7.
 */
public class SaiChengSearchAdapter extends BaseAdapter{
    private ArrayList<SearchSaichengBean.DataBean> list;
    private Context context;

    public SaiChengSearchAdapter(ArrayList<SearchSaichengBean.DataBean> list, Context context) {
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

        v.recent_list.setVisibility(View.GONE);
        final SearchSaichengBean.DataBean liveScheduleBean=list.get(position);
        if (liveScheduleBean==null){
            v.recent_tiem.setText(DateUtils.timet(liveScheduleBean.getScheduleBeginTime()));
            v.recent_title.setText(liveScheduleBean.getScheduleName());
            GlidUtils.setGrid(context, URLS.IMG+liveScheduleBean.getPublicityImg(),v.recent_img);
            v.recent_content.setText(liveScheduleBean.getScheduleName());
            v.recent_address.setText(liveScheduleBean.getHoldAddress());

            if (liveScheduleBean.getState().equals("2")){
                v.recent_zhuangtai.setVisibility(View.VISIBLE);
                v.recent_zhuangtai2.setVisibility(View.GONE);
            }else {
                if (liveScheduleBean.isHasSubscribe()){
                    v.recent_zhuangtai2.setImageResource(R.drawable.yiyuyue1);
                    v.recent_zhuangtai2.setVisibility(View.VISIBLE);
                    v.recent_zhuangtai.setVisibility(View.GONE);
                }else {
                    v.recent_zhuangtai2.setImageResource(R.drawable.yuyue1);
                    v.recent_zhuangtai2.setVisibility(View.VISIBLE);
                    v.recent_zhuangtai.setVisibility(View.GONE);
                }

            }
        }

        v.recent_zhuangtai2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liveScheduleBean.isHasSubscribe()){
                    initGuanzhu(position,URLS.SCHEDULE_USERCANCELSUBSCRIBE);
                }else {
                    initGuanzhu(position,URLS.SCHEDULE_USERSUBSCRIBE);
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
                                    if (list.get(position).isHasSubscribe()){
                                        list.get(position).setHasSubscribe(false);
                                    }else {
                                        list.get(position).setHasSubscribe(true);
                                    }
                                    notifyDataSetChanged();
                                    ToastUtils.showToast(context,bean.getMessage()+"");
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    context.startActivity(LoginActivity.getIntent(context));

                                }else {
                                    notifyDataSetChanged();
                                    ToastUtils.showToast(context,bean.getMessage()+"");
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
            this.recent_list = (RecyclerView) rootView.findViewById(R.id.recent_recycler);
        }

    }
}
