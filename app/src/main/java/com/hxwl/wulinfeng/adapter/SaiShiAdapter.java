package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.LiveDetailActivity;
import com.hxwl.wulinfeng.activity.LoginforCodeActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.hxwl.wulinfeng.bean.SaiShiYuYueBean;
import com.hxwl.wulinfeng.bean.SaichengBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.UIUtils;

import java.util.HashMap;
import java.util.List;

import static com.hxwl.wulinfeng.R.id.iv_yuyue;
import static com.hxwl.wulinfeng.R.id.start;

/**
 * Created by Allen on 2017/6/14.
 * 我-- 赛事预约
 */
public class SaiShiAdapter extends BaseAdapter {
    private Activity activity;
    private List<SaiShiYuYueBean> listData;

    public SaiShiAdapter(Activity activity, List<SaiShiYuYueBean> listData) {
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
        final SaiShiYuYueBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.item_saichengyuyue,
                    null);
            holder = new ViewHolder();
            holder.iv_zhibo_thumbnail = (ImageView) convertView.findViewById(R.id.iv_zhibo_thumbnail);
            holder.llyt_toutiao_zhibo = (RelativeLayout) convertView.findViewById(R.id.llyt_toutiao_zhibo);
            holder.iv_zhibo_state = (ImageView) convertView.findViewById(R.id.iv_zhibo_state);

            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_title2 = (TextView) convertView.findViewById(R.id.tv_title2);
            holder.flag = (TextView) convertView.findViewById(R.id.flag);
            holder.flag2 = (ImageView) convertView.findViewById(R.id.flag2);
            holder.iv_button = (TextView) convertView.findViewById(R.id.iv_button);
            holder.flag_back = (TextView) convertView.findViewById(R.id.flag_back);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (dataBean.getSaicheng_info().getSaicheng_img() == null) {
            holder.iv_zhibo_thumbnail.setImageResource(R.drawable.wlf_deimg);
        } else {
            ImageUtils.getPic(dataBean.getSaicheng_info().getSaicheng_img(), holder.iv_zhibo_thumbnail, activity);
        }
//        if (dataBean.getState() != null && dataBean.getState().equals("2")) { //zhibo
//            holder.iv_zhibo_state.setBackgroundResource(R.drawable.live_btn);
//        } else if (dataBean.getState() != null && dataBean.getState().equals("3")) { //已结束
//            holder.iv_zhibo_state.setBackgroundResource(R.drawable.huikan);
//        } else {
////            if (dataBean.getYugao_url()!=null && !TextUtils.isEmpty(dataBean.getYugao_url())){
////                holder.iv_zhibo_state.setBackgroundResource(R.drawable.yugao_btn);
////            }else{
////                holder.iv_zhibo_state.setBackgroundResource(R.drawable.wekaishi_btn);
////            }
//        }
        holder.flag.setText(dataBean.getSaicheng_info().getSaishi_name());
        holder.flag_back.setText(dataBean.getSaicheng_info().getSaishi_name());

        holder.tv_time.setText(TimeUtiles.getTimeeForTuiJ(dataBean.getSaicheng_info().getStart_time()));
        holder.tv_title.setText(dataBean.getSaicheng_info().getTitle());
        holder.tv_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        holder.tv_title2.setText(dataBean.getSaicheng_info().getChangguan_addr());
        String state = dataBean.getState();//0不可用 1未开始 2进行中 3 已结束
        final String yuyue_state = dataBean.getState();
        if ("empty".equals(yuyue_state)) {
            holder.iv_button.setText("预约");
        } else if ("0".equals(yuyue_state)) {
            holder.iv_button.setText("已预约");
        } else {
            holder.iv_button.setText("已预约");
        }
        holder.iv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = MakerApplication.instance().getUid();
                String loginKey = MakerApplication.instance().getLoginKey();
                if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
                    activity.startActivity(new Intent(activity,LoginforCodeActivity.class));
                    return ;
                }
                if(yuyue_state != null && "0".equals(yuyue_state)){
                    SaichengBean bean = new SaichengBean();
                    bean.setId(dataBean.getId());
                    bean.setSaicheng_id(TextUtils.isEmpty(dataBean.getSaicheng_id())?dataBean.getId() : dataBean.getSaicheng_id());
                    bean.setStart_time(dataBean.getSaicheng_info().getStart_time());
                    bean.setName(dataBean.getSaicheng_info().getSaishi_name());
                    bean.setTitle(dataBean.getSaicheng_info().getTitle());
                    postUnYuYueState(bean);
                }
            }
        });
        holder.tv_time.setTag(dataBean);

        //TODO 判断 video_type
//        if("1".equals(dataBean.getVideo_type())){
//            holder.flag2.setVisibility(View.VISIBLE);
//            holder.flag2.setImageResource(R.drawable.live_icon);
//        }else if("2".equals(dataBean.getVideo_type())){
//            holder.flag2.setVisibility(View.VISIBLE);
//            holder.flag2.setImageResource(R.drawable.tv);
//        }else{
//            holder.flag2.setVisibility(View.GONE);
//        }

        return convertView;
    }
    //提交取消预约
    private void postUnYuYueState(final SaichengBean dataBean) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                activity, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    for(SaiShiYuYueBean info: listData){
                        if(info.getSaicheng_id().equals(dataBean.getSaicheng_id())){
                            listData.remove(info) ;
                            break ;
                        }
                    }
                    notifyDataSetChanged();
                    SPUtils.clearYuYueInfo(activity, dataBean);
                } else{
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> map= new HashMap<String, Object>();
        map.put("saichengId", dataBean.getSaicheng_id());
        map.put("uid", MakerApplication.instance().getUid());
        map.put("method", NetUrlUtils.wulin_quxiaoyuyuetongzhi);
        tasker.execute(map);
    }


    class ViewHolder {
        public ImageView iv_zhibo_thumbnail;
        public ImageView iv_zhibo_state;
        public RelativeLayout llyt_toutiao_zhibo;

        public TextView tv_time;
        public TextView tv_title;
        public TextView tv_title2;
        public TextView flag;
        public ImageView flag2;
        public TextView iv_button;
        public TextView flag_back;
    }
}
