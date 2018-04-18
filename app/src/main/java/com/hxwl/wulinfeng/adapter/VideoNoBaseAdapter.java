package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.bean.HomeZuixinHuifangBean;
import com.hxwl.wulinfeng.util.NetWorkUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.lecloud.sdk.videoview.IMediaDataVideoView;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * 功能:首页视频适配器
 */
public class VideoNoBaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private VideoListener m_VideoListener;
    private Activity m_Context;
    private Context Context;
    private LayoutInflater m_Inflater;
    private List<HomeZuixinHuifangBean> huifangBeen ;

    public VideoNoBaseAdapter(List<HomeZuixinHuifangBean> huifangBeen ,Context context) {
        this.huifangBeen = huifangBeen ;
        this.Context = context;
        if (m_Inflater == null) {
            m_Inflater = LayoutInflater.from(context);
        }
    }
    public void appendToList(List<HomeZuixinHuifangBean> list) {
        if (list == null) {
            return;
        }
        huifangBeen.addAll(list);
        notifyDataSetChanged();
    }

    public void setVideoListener(VideoListener m_VideoListener) {
        this.m_VideoListener = m_VideoListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = m_Inflater.inflate(R.layout.video_item,
                null, false);
        RecyclerView.ViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        try {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.ic_net_warn.setVisibility(View.GONE);
//            if (!huifangBeen.get(position).isSelect()) {
//                myViewHolder.videoContainer.setVisibility(View.GONE);
//                myViewHolder.rlyt_video_bg.setVisibility(View.VISIBLE);
//                myViewHolder.llyt_title.setVisibility(View.VISIBLE);
//                //视频图片背景
//                Glide.with(m_Context).load(huifangBeen.get(position).getImg()).into(myViewHolder.iv_video_bg);
//                myViewHolder.tv_title.setText(huifangBeen.get(position).getTitle());
//            } else {
//                //true
//                myViewHolder.rlyt_video_bg.setVisibility(View.GONE);
//                myViewHolder.llyt_title.setVisibility(View.GONE);
//                myViewHolder.videoContainer.setVisibility(View.VISIBLE);
//            }
            //用户头像
//            myViewHolder.tv_pinglun.setText(huifangBeen.get(position).getHuifu_times()+"");
//            myViewHolder.tv_dianzan.setText(huifangBeen.get(position).getOpen_times()+"");

            myViewHolder.iv_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatService.onEvent(m_Context,"VedioPlayTimes","视频播放次数",1);
                    TCAgent.onEvent(m_Context,"VedioPlayTimes","视频播放次数");
                    if(!NetWorkUtils.isWifiConnected(m_Context)){
                        myViewHolder.ic_net_warn.setVisibility(View.VISIBLE);
                    }else {
                        //隐藏该容器，显示播放容器
                        myViewHolder.rlyt_video_bg.setVisibility(View.GONE);
                        myViewHolder.llyt_title.setVisibility(View.GONE);
                        myViewHolder.videoContainer.setVisibility(View.VISIBLE);
                        m_VideoListener.playVideo(huifangBeen.get(position).getUrl(),
                                myViewHolder.videoContainer, position);
                    }
                }
            });
            myViewHolder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatService.onEvent(m_Context,"VedioPlayTimes","视频播放次数",1);
                    TCAgent.onEvent(m_Context,"VedioPlayTimes","视频播放次数");
                    myViewHolder.ic_net_warn.setVisibility(View.GONE);
                    //隐藏该容器，显示播放容器
                    myViewHolder.rlyt_video_bg.setVisibility(View.GONE);
                    myViewHolder.llyt_title.setVisibility(View.GONE);
                    myViewHolder.videoContainer.setVisibility(View.VISIBLE);
                    m_VideoListener.playVideo(huifangBeen.get(position).getUrl(),
                            myViewHolder.videoContainer, position);
                }
            });
            myViewHolder.llyt_empty_area.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeZuixinHuifangBean videosBean = huifangBeen.get(position);
                    if (videosBean == null) {
                        ToastUtils.showToast(m_Context, "该视频没有详情");
                        return;
                    }
                    m_VideoListener.openVideoDetails(huifangBeen.get(position).getId());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setIsRecyclable(false);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        //从新判断
        try {
            HomeZuixinHuifangBean dataBean;
            if (huifangBeen != null) {
                dataBean = huifangBeen.get(holder.getAdapterPosition());
                if (dataBean.isSelect()) {
                    //是否选中播放
                    if (m_VideoListener.getVideoView() != null) {
                        if (!m_VideoListener.isPlaying()) {
//                            Log.e("VideoBaseAdapter", "To 重新播放");
                            ((MyViewHolder)holder).videoContainer.refreshDrawableState();
                            m_VideoListener.startResume();
                        }
                    }
                }else{
//                    Log.e("VideoBaseAdapter", "To 刷新数据");
                    //初始化所有数据
                    for(int i=0;i<huifangBeen.size();i++){
                        huifangBeen.get(i).setSelect(false);
                    }
                    notifyDataSetChanged();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        //从新判断
        try {
            HomeZuixinHuifangBean dataBean;
            if (huifangBeen != null) {
                dataBean = huifangBeen.get(holder.getAdapterPosition());
                if (dataBean.isSelect()) {
                    //是否选中播放
                    if (m_VideoListener.getVideoView() != null) {
                        if (!m_VideoListener.isPlaying()) {
//                            Log.e("VideoBaseAdapter", "To 重新播放")
                            m_VideoListener.startResume();
//                            MyViewHolder myViewHolder = ((MyViewHolder)holder);
//                            //隐藏该容器，显示播放容器
//                            myViewHolder.rlyt_video_bg.setVisibility(View.GONE);
//                            myViewHolder.llyt_title.setVisibility(View.GONE);
//                            myViewHolder.videoContainer.setVisibility(View.VISIBLE);
//                            m_VideoListener.playVideo(huifangBeen.get(holder.getAdapterPosition()).getVideos().getUrl(),
//                                    myViewHolder.videoContainer, holder.getAdapterPosition());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return huifangBeen.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_video_bg;
        public TextView tv_title;
        public TextView tv_time;
        public TextView tv_userName;

        public TextView tv_pinglun;
        public TextView tv_dianzan;
        public LinearLayout ll_item;
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

        public MyViewHolder(View view) {
            super(view);
            iv_video_bg = (ImageView) view.findViewById(R.id.iv_video_bg);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_userName = (TextView) view.findViewById(R.id.tv_userName);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_pinglun = (TextView) view.findViewById(R.id.tv_pinglun);
            tv_dianzan = (TextView) view.findViewById(R.id.tv_dianzan);
            ll_item = (LinearLayout) view.findViewById(R.id.ll_item);
            llyt_empty_area = (LinearLayout) view.findViewById(R.id.llyt_empty_area);
            llyt_title = (RelativeLayout) view.findViewById(R.id.llyt_title);
            iv_start = (ImageView) view.findViewById(R.id.iv_start);
            rlyt_video_bg = (RelativeLayout) view.findViewById(R.id.rlyt_video_bg);
            videoContainer = (RelativeLayout) view.findViewById(R.id.videoContainer);
            ic_net_warn =  view.findViewById(R.id.ic_net_warn);
            btn1 = (Button) ic_net_warn.findViewById(R.id.btn1);
        }
    }

    public interface VideoListener {
        //当前页面播放
        void playVideo(String url, RelativeLayout videoContainer, int position);

        //跳转详情页
        void openVideoDetails(String id);

        //是否正在播放
        boolean isPlaying();

        //重新播放
        void resetPlayer();

        //续播
        void startResume();

        //获取播放对象
        IMediaDataVideoView getVideoView();

        //添加播放view
        void addPlayView(RelativeLayout videoContainer);
    }

    /**
     * 清除List
     */
    public void clear() {
        if (huifangBeen != null) {
            huifangBeen.clear();
        }

        notifyDataSetChanged();
    }
}
