package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.SaichengBean;
import com.hxwl.wulinfeng.bean.ZiXunBean;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.ToastUtils;

import org.w3c.dom.Text;

import java.util.List;

import static android.R.attr.value;
import static com.hxwl.wulinfeng.R.id.flag2;
import static com.hxwl.wulinfeng.R.id.iv_img;

/**
 * Created by Allen on 2017/6/7.
 */
public class SaiChengAdapter extends BaseAdapter{
    private List<SaichengBean> listData ;
    private Activity context ;
    private View.OnClickListener onclickListener ;
    private boolean isshowLable = true ;
    private final long value2;

    public SaiChengAdapter(Activity activity, List<SaichengBean> listData , View.OnClickListener onclickListener) {
            this.context = activity ;
            this.listData =  listData;
        this.onclickListener = onclickListener ;
        value2 = System.currentTimeMillis();
    }

    @Override
    public int getCount() {
        return listData == null ? 0:listData.size();
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
        SaichengBean dataBean = listData.get(paramInt);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_saicheng,
                    null);
            holder = new ViewHolder();
            holder.iv_zhibo_thumbnail = (ImageView) convertView.findViewById(R.id.iv_zhibo_thumbnail);
            holder.llyt_toutiao_zhibo = (RelativeLayout) convertView.findViewById(R.id.llyt_toutiao_zhibo);
            holder.rl_click = (RelativeLayout) convertView.findViewById(R.id.rl_click);
            holder.iv_zhibo_state = (ImageView) convertView.findViewById(R.id.iv_zhibo_state);

            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_title2 = (TextView) convertView.findViewById(R.id.tv_title2);
            holder.flag = (TextView) convertView.findViewById(R.id.flag);
            holder.flag2 = (ImageView) convertView.findViewById(R.id.flag2);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.iv_button = (TextView) convertView.findViewById(R.id.iv_button);
            holder.flag_back = (TextView) convertView.findViewById(R.id.flag_back);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(dataBean.getSaicheng_img() == null){
            holder.iv_zhibo_thumbnail.setImageResource(R.drawable.wlf_deimg);
        }else{
            ImageUtils.getPic(dataBean.getSaicheng_img(),holder.iv_zhibo_thumbnail,context);
        }
        if (dataBean.getState()!=null && dataBean.getState().equals("2")){ //zhibo
            holder.iv_zhibo_state.setBackgroundResource(R.drawable.live_btn);
        }else if (dataBean.getState()!=null && dataBean.getState().equals("3")){ //已结束
            holder.iv_zhibo_state.setBackgroundResource(R.drawable.huikan);
        }else {
//            if (dataBean.getYugao_url()!=null && !TextUtils.isEmpty(dataBean.getYugao_url())){
//                holder.iv_zhibo_state.setBackgroundResource(R.drawable.yugao_btn);
//            }else{
//                holder.iv_zhibo_state.setBackgroundResource(R.drawable.wekaishi_btn);
//            }
        }
        if(isshowLable){
            holder.flag.setVisibility(View.VISIBLE);
            holder.flag_back.setVisibility(View.VISIBLE);
            holder.flag.setText(dataBean.getSaishi_name());
            holder.flag_back.setText(dataBean.getSaishi_name());
        }else{
            holder.flag.setVisibility(View.GONE);
            holder.flag_back.setVisibility(View.GONE);
        }
        holder.flag.setText(dataBean.getSaishi_name());
        holder.flag_back.setText(dataBean.getSaishi_name());
        holder.tv_time.setText(TimeUtiles.getTimeeForTuiJ(dataBean.getStart_time()));
        holder.tv_title.setText(dataBean.getTitle());
//        holder.tv_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        holder.tv_title2.setText(dataBean.getChangguan_addr());
        String state = dataBean.getState();//0不可用 1未开始 2进行中 3 已结束
        if("0".equals(state)){
            holder.iv_button.setText("类型不可用");
            holder.rl_click.setBackgroundResource(R.drawable.send_red);
            holder.iv_img.setVisibility(View.GONE);
        }else if("1".equals(state)){
            String yuyue_state = dataBean.getYuyue_state();
            holder.iv_img.setVisibility(View.VISIBLE);
            if("empty".equals(yuyue_state)){
                holder.iv_button.setText("预约");
                holder.rl_click.setBackgroundResource(R.drawable.send_red);
                holder.iv_img.setImageResource(R.drawable.time_icon);
            }else if("0".equals(yuyue_state) || "1".equals(yuyue_state)){
                holder.rl_click.setBackgroundResource(R.drawable.send_gray);
                holder.iv_button.setText("已预约");
                holder.iv_img.setVisibility(View.GONE);
            }else{
                holder.iv_button.setText("");
                holder.rl_click.setBackgroundResource(R.drawable.send_red);
                holder.iv_img.setVisibility(View.GONE);
            }
        }else if("2".equals(state)){
            holder.rl_click.setBackgroundResource(R.drawable.send_red);
            holder.iv_img.setVisibility(View.VISIBLE);
            holder.iv_button.setText("进行中");
            holder.iv_img.setImageResource(R.drawable.zhibozhong);
        }else if("3".equals(state)){
            holder.rl_click.setBackgroundResource(R.drawable.send_red);
            holder.iv_img.setVisibility(View.VISIBLE);
            holder.iv_button.setText("回放");
            holder.iv_img.setImageResource(R.drawable.replay_icon);
        }else{
            holder.rl_click.setBackgroundResource(R.drawable.send_red);
            holder.iv_button.setText("未识别类型");
        }

        if(!TextUtils.isEmpty(dataBean.getEnd_time())){
            String end_time = dataBean.getEnd_time();
            long value = Long.parseLong(end_time)*1000 ;
            if(value <= value2){
                holder.rl_click.setBackgroundResource(R.drawable.send_red);
                holder.iv_img.setVisibility(View.VISIBLE);
                holder.iv_button.setText("回放");
                holder.iv_img.setImageResource(R.drawable.replay_icon);
            }
        }
        holder.rl_click.setPadding(24,14,24,14);

//        //TODO 判断 video_type
        if("live".equals(dataBean.getVideo_type())){
            holder.flag2.setVisibility(View.VISIBLE);
            holder.flag2.setImageResource(R.drawable.live_icon);
        }else if("tv".equals(dataBean.getVideo_type())){
            holder.flag2.setVisibility(View.VISIBLE);
            holder.flag2.setImageResource(R.drawable.tv);
        }else{
            holder.flag2.setVisibility(View.GONE);
        }
        holder.rl_click.setTag(dataBean);
        holder.rl_click.setOnClickListener(onclickListener);
        return convertView;
    }

    public void setIsShowLable(boolean b) {
        this.isshowLable = b ;
    }

    class ViewHolder{
        public ImageView iv_zhibo_thumbnail;
        public ImageView iv_zhibo_state;
        public RelativeLayout llyt_toutiao_zhibo;
        public RelativeLayout rl_click;

        public TextView tv_time ;
        public TextView tv_title ;
        public TextView tv_title2 ;
        public TextView flag ;
        public ImageView flag2 ;
        public ImageView iv_img ;
        public TextView iv_button ;
        public TextView flag_back;
    }
}
