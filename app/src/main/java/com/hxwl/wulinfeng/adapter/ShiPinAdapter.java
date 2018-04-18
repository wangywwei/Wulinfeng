package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.HomeZuixinHuifangBean;
import com.hxwl.wulinfeng.bean.ShiPinBean;
import com.hxwl.wulinfeng.bean.ShiPinHorBean;
import com.hxwl.wulinfeng.bean.Videos;
import com.hxwl.wulinfeng.util.NetWorkUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * Created by Allen on 2017/6/2.
 */
public class ShiPinAdapter extends BaseAdapter{
    private List<ShiPinBean> listData;
    private Activity activity;
    private int oldposition  = -1;

    private VideoListener m_VideoListener;
    public ShiPinAdapter(Activity activity, List<ShiPinBean> listData) {
        this.activity = activity;
        this.listData = listData ;
    }
    public interface VideoListener {
        //当前页面播放
        void playVideo(String url, RelativeLayout videoContainer, int position, int oldposition ,String title);

        //跳转详情页
        void openVideoDetails(String id);

        void loadWebUrl(String url , int position, int oldposition ,RelativeLayout videoContainer);

        //是否正在播放
        boolean isPlaying();

        //重新播放
        void resetPlayer();

        //续播
        void startResume();

    }

    public void setVideoListener(VideoListener m_VideoListener) {
        this.m_VideoListener = m_VideoListener;
    }
    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
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
    public View getView(final int paramInt, View view, ViewGroup viewGroup) {
        if(paramInt >= listData.size()){
            return null;
        }
        final ShiPinBean dataBean = listData.get(paramInt);
        final viewHolder holder;
        if (view == null) {
            view = View.inflate(activity, R.layout.item_shipin_video,
                    null);
            holder = new viewHolder();
            holder.iv_video_bg = (ImageView) view.findViewById(R.id.iv_video_bg);
            holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            holder.tv_label = (TextView) view.findViewById(R.id.tv_label);
            holder.user_icon = (ImageView) view.findViewById(R.id.user_icon);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            holder.tv_pinglun = (TextView) view.findViewById(R.id.tv_pinglun);
            holder.tv_dianzan = (TextView) view.findViewById(R.id.tv_dianzan);
            holder.fl_item = (FrameLayout) view.findViewById(R.id.fl_item);
            holder.llyt_empty_area = (LinearLayout) view.findViewById(R.id.llyt_empty_area);
            holder.llyt_title = (RelativeLayout) view.findViewById(R.id.llyt_title);
            holder.iv_start = (ImageView) view.findViewById(R.id.iv_start);
            holder.rlyt_video_bg = (RelativeLayout) view.findViewById(R.id.rlyt_video_bg);
            holder.videoContainer = (RelativeLayout) view.findViewById(R.id.videoContainer);
            holder.ic_net_warn =  view.findViewById(R.id.ic_net_warn);
            holder.btn1 = (Button) holder.ic_net_warn.findViewById(R.id.btn1);
            view.setTag(holder);
        } else {
            holder = (viewHolder) view.getTag();
        }
        holder.fl_item.setTag(dataBean);
        try {
            //用户姓名
            holder.ic_net_warn.setVisibility(View.GONE);
            if (!dataBean.isSelect()) {
                holder.videoContainer.setVisibility(View.GONE);
                holder.rlyt_video_bg.setVisibility(View.VISIBLE);
                holder.llyt_title.setVisibility(View.VISIBLE);
                //视频图片背景
                Glide.with(activity).load(dataBean.getImg()).into(holder.iv_video_bg);
                holder.tv_title.setText(dataBean.getTitle());
            } else {
                //true
                holder.rlyt_video_bg.setVisibility(View.GONE);
                holder.llyt_title.setVisibility(View.GONE);
                holder.videoContainer.setVisibility(View.VISIBLE);
            }
            holder.tv_dianzan.setText(dataBean.getOpen_times()+"人看过");
            if(TextUtils.isEmpty(dataBean.getType_name())){
                holder.tv_label.setVisibility(View.INVISIBLE);
            }else{
                holder.tv_label.setVisibility(View.VISIBLE);
                holder.tv_label.setText(dataBean.getType_name());
            }
            holder.iv_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!NetWorkUtils.isWifiConnected(activity)){
                        holder.ic_net_warn.setVisibility(View.VISIBLE);
                    }else {
                        if(dataBean.getVideos().getType().equals("1")){
                            holder.rlyt_video_bg.setVisibility(View.GONE);
                            holder.llyt_title.setVisibility(View.GONE);
                            holder.videoContainer.setVisibility(View.VISIBLE);
                            m_VideoListener.loadWebUrl(dataBean.getVideos().getUrl() ,paramInt ,oldposition ,holder.videoContainer);
                        }else{
                            //隐藏该容器，显示播放容器
                            holder.rlyt_video_bg.setVisibility(View.GONE);
                            holder.llyt_title.setVisibility(View.GONE);
                            holder.videoContainer.setVisibility(View.VISIBLE);
                            m_VideoListener.playVideo(dataBean.getVideos().getUrl(),
                                    holder.videoContainer, paramInt ,oldposition ,dataBean.getTitle());
                        }
                        oldposition = paramInt ;
                    }
                }
            });
            holder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatService.onEvent(activity,"VedioPlayTimes","视频播放次数",1);
                    TCAgent.onEvent(activity,"VedioPlayTimes","视频播放次数");

                    if(dataBean.getVideos().getType().equals("1")){
                        holder.rlyt_video_bg.setVisibility(View.GONE);
                        holder.llyt_title.setVisibility(View.GONE);
                        holder.videoContainer.setVisibility(View.VISIBLE);
                        m_VideoListener.loadWebUrl(dataBean.getVideos().getUrl() ,paramInt ,oldposition , holder.videoContainer);
                    }else{
                        holder.ic_net_warn.setVisibility(View.GONE);
                        //隐藏该容器，显示播放容器
                        holder.rlyt_video_bg.setVisibility(View.GONE);
                        holder.llyt_title.setVisibility(View.GONE);
                        holder.videoContainer.setVisibility(View.VISIBLE);
                        m_VideoListener.playVideo(dataBean.getVideos().getUrl(),
                                holder.videoContainer, paramInt ,oldposition ,dataBean.getTitle());
                    }
                    oldposition = paramInt ;
                }
            });
            holder.llyt_empty_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Videos videosBean = dataBean.getVideos();
                    if (videosBean == null) {
                        ToastUtils.showToast(activity, "该视频没有详情");
                        return;
                    }
                    m_VideoListener.openVideoDetails(dataBean.getId());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tv_time.setText(TimeUtiles.getTimeeForTuiJ(dataBean.getTime()));
        return view;
    }
    class viewHolder{
        public ImageView iv_video_bg;
        public TextView tv_title;
        public ImageView user_icon;
        public TextView tv_time;
        public TextView tv_label;

        public TextView tv_pinglun;
        public TextView tv_dianzan;
        public FrameLayout fl_item;
        //空白区域
        public LinearLayout llyt_empty_area;
        //开始按钮
        public ImageView iv_start;
        //播放背景图片容器
        public RelativeLayout rlyt_video_bg;
        //播放容器
        public RelativeLayout videoContainer;
        //标题
        public RelativeLayout llyt_title;
        public View ic_net_warn;
        public Button btn1;
    }
}
