package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.DongtaiBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.BiSaiShiPinActivity;
import com.hxwl.wulinfeng.activity.LoginforCodeActivity;
import com.hxwl.wulinfeng.activity.TieZiDetailsActivity;
import com.hxwl.wulinfeng.bean.HuiFuBean;
import com.hxwl.wulinfeng.bean.MyMartialBean;
import com.hxwl.wulinfeng.bean.WuLinZuiXinBean;
import com.hxwl.wulinfeng.bean.ZanBean;
import com.hxwl.wulinfeng.net.AppClient;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.ICallback;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.NetWorkUtils;
import com.hxwl.wulinfeng.util.PreciseCompute;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.wulin.ImageListGridViewAdapter;
import com.hxwl.wulinfeng.wulin.ImageViewPageActivity;
import com.hxwl.wulinfeng.wulin.MyGridView;
import com.hxwl.wulinfeng.wulin.SeeHighDefinitionPictureActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hxwl.wulinfeng.R.id.rlyt_video_bg;

public class MyMartialAdapter extends BaseExpandableListAdapter {
    private Activity context;
    //数据
    private List<DongtaiBean.DataBean> listData;
    private OnClickListener replyToCommentListener;// 评论监听

    private final static String SECCLICK = "点赞成功";
    private final static String FAILCLICK = "取消点赞";
    public static int replay_to_comment = 1000;
    private LayoutInflater inflater;

    private int m_ScreenWidth;

    // 剪切板 控制器
    private ClipboardManager mClipboard = null;

    private int oldposition = -1;

    public MyMartialAdapter(Activity context, List<DongtaiBean.DataBean> listData, OnClickListener replyToCommentListener) {
        this.context = context;
        this.listData = listData;
        this.replyToCommentListener = replyToCommentListener;
        mClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        inflater = LayoutInflater.from(context);

        //获取屏幕的宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        m_ScreenWidth = wm.getDefaultDisplay().getWidth() - ScreenUtils.dip2px(context, 20);
    }

    private VideoListener m_VideoListener;

    public interface VideoListener {
        //当前页面播放
        void playVideo(String url, RelativeLayout videoContainer, int position, int oldposition, String title);

        //跳转详情页
        void openVideoDetails(String id);

        void loadWebUrl(String url, int position, int oldposition, RelativeLayout videoContainer);

        //是否正在播放
        boolean isPlaying();
    }
    public void setVideoListener(VideoListener m_VideoListener) {
        this.m_VideoListener = m_VideoListener;
    }

    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listData.get(groupPosition).getCommentList().size() > 6 ? 6 : listData.get(groupPosition).getCommentList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listData.get(groupPosition).getCommentList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 分组getview
     */
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final DongtaiBean.DataBean itemNotes = listData.get(groupPosition);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.mymartial_item_group, null);
            holder.time = (TextView) convertView.findViewById(R.id.fatie_time);
            holder.time2 = (TextView) convertView.findViewById(R.id.fatie_time2);
            holder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
            holder.tv_zan_text = (TextView) convertView.findViewById(R.id.tv_zan_text);
            holder.msg = (TextView) convertView.findViewById(R.id.pinglun_content);
            holder.iv_zan = (ImageView) convertView.findViewById(R.id.iv_zan);
            holder.iv_line_cu = (ImageView) convertView.findViewById(R.id.iv_line_cu);
            holder.iv_line_xi = (ImageView) convertView.findViewById(R.id.iv_line_xi);
            holder.iv_xiahuaxian = (ImageView) convertView.findViewById(R.id.iv_xiahuaxian);
            holder.btn_comment_reply = (ImageView) convertView.findViewById(R.id.btn_comment_reply);

            //新增
            holder.videoContainer = (RelativeLayout) convertView.findViewById(R.id.videoContainer);
            holder.ic_net_warn = convertView.findViewById(R.id.ic_net_warn);
            holder.iv_start = (ImageView) convertView.findViewById(R.id.iv_start);
            holder.rlyt_video_bg = (RelativeLayout) convertView.findViewById(rlyt_video_bg);
            holder.btn1 = (Button) holder.ic_net_warn.findViewById(R.id.btn1);
            holder.iv_video_bg = (ImageView) convertView
                    .findViewById(R.id.iv_video_bg);

            holder.iv_single_image = (ImageView) convertView
                    .findViewById(R.id.iv_single_image);
            holder.rv_note_image = (MyGridView) convertView
                    .findViewById(R.id.rv_note_image);
            holder.rl_note_images = (RelativeLayout) convertView
                    .findViewById(R.id.rl_note_images);
            holder.rl_relayout = (RelativeLayout) convertView
                    .findViewById(R.id.rl_relayout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (itemNotes.getUpdateTime() != null && !itemNotes.getUpdateTime().isEmpty()) {
            holder.time.setText(itemNotes.getUpdateTime());
        } else {
//            viewHolder.time.setText(bean.getTime());
        }

        if (!itemNotes.isSelect()) {
            holder.videoContainer.setVisibility(View.GONE);
            holder.rlyt_video_bg.setVisibility(View.VISIBLE);
            //视频图片背景
        } else {
            //true
            holder.rlyt_video_bg.setVisibility(View.GONE);
            holder.videoContainer.setVisibility(View.VISIBLE);
        }

        //设置上边显示的字母
        String section = getSectionForPosition(groupPosition);
        if (groupPosition == getPositionForSection(section)) {
            holder.time.setVisibility(View.VISIBLE);
            holder.time2.setVisibility(View.VISIBLE);
            holder.iv_line_cu.setVisibility(View.VISIBLE);
            holder.iv_line_xi.setVisibility(View.GONE);
            holder.iv_xiahuaxian.setVisibility(View.VISIBLE);
        } else {
            holder.time.setVisibility(View.GONE);
            holder.time2.setVisibility(View.GONE);
            holder.iv_line_cu.setVisibility(View.VISIBLE);
            holder.iv_line_xi.setVisibility(View.GONE);
            holder.iv_xiahuaxian.setVisibility(View.GONE);
        }
        if (itemNotes.getContent() != null && !StringUtils.isEmpty(itemNotes.getContent())) {
            holder.msg.setVisibility(View.VISIBLE);
            holder.msg.setText(itemNotes.getContent());
        } else {
            holder.msg.setVisibility(View.GONE);
        }
        holder.iv_zan.setTag(itemNotes);
        int is_zan = itemNotes.getFavourNum();
        if (0 == is_zan) {//没有点过
            holder.iv_zan.setImageResource(R.drawable.zan_icon);
        } else {//点过赞
            holder.iv_zan.setImageResource(R.drawable.yizan_icon);
        }


        // 这个字符串是一个数组,里面的对象是点过赞的人
        final List<DongtaiBean.DataBean.FavourListBean> listNotePraise = itemNotes
                .getFavourList();
        if (listNotePraise != null && listNotePraise.size() > 0) {
            holder.tv_zan_text.setVisibility(View.VISIBLE);
            holder.rl_relayout.setVisibility(View.VISIBLE);
            holder.tv_zan.setVisibility(View.VISIBLE);
            StringBuffer name = new StringBuffer();
            for (int i = 0; i < listNotePraise.size(); i++) {
                if (i == listNotePraise.size() - 1) {
                    name.append(listNotePraise.get(i).getNickName());
                } else {
                    name.append(listNotePraise.get(i).getNickName() + "、");
                }
            }
            holder.tv_zan.setText(name);
            holder.tv_zan_text.setText(listNotePraise.size() + "人点过赞");
        } else {
            holder.rl_relayout.setVisibility(View.GONE);
            holder.tv_zan.setVisibility(View.GONE);
            holder.tv_zan_text.setVisibility(View.GONE);
        }
        holder.iv_zan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                int is_zan = itemNotes.getFavourNum();
                if (0 == is_zan) {//没有点过 holder.iv_zan.setImageResource(R.drawable.zanblue_icon);
                    dianZan(holder.iv_zan, itemNotes);
                } else {//点过赞
                    quxiaoDianZan(holder.iv_zan, itemNotes);
                }
            }

            private void setZanImg() {
                // 这个字符串是一个数组,里面的对象是点过赞的人
                final List<DongtaiBean.DataBean.FavourListBean> listNotePraise = itemNotes
                        .getFavourList();
                if (listNotePraise != null && listNotePraise.size() > 0) {
                    holder.rl_relayout.setVisibility(View.VISIBLE);
                    holder.tv_zan.setVisibility(View.VISIBLE);
                    holder.tv_zan_text.setVisibility(View.VISIBLE);
                    StringBuffer name = new StringBuffer();
                    for (int i = 0; i < listNotePraise.size(); i++) {
                        if (i == listNotePraise.size() - 1) {
                            name.append(listNotePraise.get(i).getNickName());
                        } else {
                            name.append(listNotePraise.get(i).getNickName() + "、");
                        }
                    }
                    holder.tv_zan.setText(name);
                    holder.tv_zan_text.setText(listNotePraise.size() + "人点过赞");
                } else {
                    holder.rl_relayout.setVisibility(View.GONE);
                    holder.tv_zan.setVisibility(View.GONE);
                    holder.tv_zan_text.setVisibility(View.GONE);
                }
            }

            //点赞
            public void dianZan(final View view, final DongtaiBean.DataBean itemNotes) {
                JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                        context, true, new ExcuteJSONObjectUpdate() {
                    @Override
                    public void excute(ResultPacket result) {
                        if (result != null && result.getStatus().equals("ok")) {
                            itemNotes.setFavourNum(1);
                            if (view instanceof ImageView) {
                                ((ImageView) view).setImageResource(R.drawable.yizan_icon);
                            } else {
                                ((ImageView) view.findViewById(R.id.iv_zan)).setImageResource(R.drawable.yizan_icon);
                            }
                            DongtaiBean.DataBean.FavourListBean bean = new DongtaiBean.DataBean.FavourListBean();
                            bean.setNickName(MakerApplication.instance().getUsername());
                            bean.setUserId(MakerApplication.instance().getUid());
                            itemNotes.getFavourList().add(0, bean);
                            setZanImg();
                        } else if (result != null && result.getStatus().equals("empty")) {

                        } else {
                            UIUtils.showToast("服务器异常");
                        }
                    }
                });
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("zhuId", itemNotes.getMessageId());
                map.put("uid", MakerApplication.instance().getUid());
                map.put("loginKey", MakerApplication.instance().getLoginKey());
                map.put("method", NetUrlUtils.wulin_dianzan);
                tasker.execute(map);
            }
            //取消点赞
            public void quxiaoDianZan(final View view, final DongtaiBean.DataBean itemNotes) {
                JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                        context, true, new ExcuteJSONObjectUpdate() {
                    @Override
                    public void excute(ResultPacket result) {
                        if (result != null && result.getStatus().equals("ok")) {
                            itemNotes.setFavourNum(0);
                            if (view instanceof ImageView) {
                                ((ImageView) view).setImageResource(R.drawable.zan_icon);
                            } else {
                                ((ImageView) view.findViewById(R.id.iv_zan)).setImageResource(R.drawable.zan_icon);
                            }
                            for (DongtaiBean.DataBean.FavourListBean info : itemNotes.getFavourList()) {
                                if (MakerApplication.instance().getUid().equals(info.getUserId())) {
                                    itemNotes.getFavourList().remove(info);
                                    break;
                                }
                            }
                            setZanImg();
                        } else if (result != null && result.getStatus().equals("empty")) {

                        } else {
                            UIUtils.showToast("服务器异常");
                        }
                    }
                });
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("zhuId", itemNotes.getMessageId());
                map.put("uid", MakerApplication.instance().getUid());
                map.put("loginKey", MakerApplication.instance().getLoginKey());
                map.put("method", NetUrlUtils.wulin_quxiaodianzan);
                tasker.execute(map);

            }
        });

        List<String> listImageItems2 = new ArrayList<>();
        if (!StringUtils.isBlank(itemNotes.getImageList())&&itemNotes.getImageList().indexOf(",")!=-1){
            String [] temp = null;
            temp = itemNotes.getImageList().split(",");
            for (int i = 0; i <temp.length ; i++) {
                listImageItems2.add(URLS.IMG+temp[i]);
            }
        }else if (!StringUtils.isBlank(itemNotes.getImageList())){
            listImageItems2.add(URLS.IMG+itemNotes.getImageList());
        } else {

        }
        final List<String> listImageItems = listImageItems2;
        int isHavaVideo = itemNotes.getMessageType();
//================================================================================================
        if (isHavaVideo==2) {
            holder.rl_note_images.setVisibility(View.GONE);
            holder.iv_single_image.setVisibility(View.GONE);
            holder.rv_note_image.setVisibility(View.GONE);
            holder.rlyt_video_bg.setVisibility(View.VISIBLE);
            holder.ic_net_warn.setVisibility(View.GONE);
            holder.videoContainer.setVisibility(View.VISIBLE);
            //视频图片背景.
            if(!TextUtils.isEmpty(itemNotes.getVideoPreviewImage())){
                ImageUtils.getPic(URLS.IMG+itemNotes.getVideoPreviewImage(),holder.iv_video_bg ,context );
            }else{
                holder.iv_video_bg.setImageResource(R.drawable.wlf_deimg);
            }
            holder.iv_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!NetWorkUtils.isWifiConnected(context)) {
                        holder.ic_net_warn.setVisibility(View.VISIBLE);
                    } else {
//                        if(dataBean.getVideos().getType().equals("1")){
//                            holder.rlyt_video_bg.setVisibility(View.GONE);
//                            holder.videoContainer.setVisibility(View.VISIBLE);
//                            m_VideoListener.loadWebUrl(dataBean.getVideos().getUrl() ,paramInt ,oldposition ,holder.videoContainer);
//                        }else{
                        //隐藏该容器，显示播放容器
                        holder.ic_net_warn.setVisibility(View.GONE);
                        holder.rlyt_video_bg.setVisibility(View.GONE);
                        holder.videoContainer.setVisibility(View.VISIBLE);
                        m_VideoListener.playVideo(URLS.VIDEO+itemNotes.getVideoName(),
                                holder.videoContainer, groupPosition, oldposition,itemNotes.getContent());
//                        }
                        oldposition = groupPosition;
                    }
                }
            });
            holder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if(dataBean.getVideos().getType().equals("1")){
//                        holder.rlyt_video_bg.setVisibility(View.GONE);
//                        holder.videoContainer.setVisibility(View.VISIBLE);
//                        m_VideoListener.loadWebUrl(dataBean.getVideos().getUrl() ,paramInt ,oldposition , holder.videoContainer);
//                    }else{
                    holder.ic_net_warn.setVisibility(View.GONE);
                    //隐藏该容器，显示播放容器
                    holder.rlyt_video_bg.setVisibility(View.GONE);
                    holder.videoContainer.setVisibility(View.VISIBLE);
                    m_VideoListener.playVideo(URLS.VIDEO+itemNotes.getVideoName(),
                            holder.videoContainer, groupPosition, oldposition,itemNotes.getContent());
//                    }
                    oldposition = groupPosition;
                }
            });
//            holder.llyt_empty_area.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Videos videosBean = dataBean.getVideos();
//                    if (videosBean == null) {
//                        ToastUtils.showToast(activity, "该视频没有详情");
//                        return;
//                    }
//                    m_VideoListener.openVideoDetails(dataBean.getId());
//                }
//            });

        } else if (listImageItems != null && listImageItems.size() > 1) {
            // 加载图片--9张图片
            holder.rlyt_video_bg.setVisibility(View.GONE);
            holder.rl_note_images.setVisibility(View.VISIBLE);
            holder.iv_single_image.setVisibility(View.GONE);
            holder.rv_note_image.setVisibility(View.VISIBLE);
            holder.ic_net_warn.setVisibility(View.GONE);
            holder.videoContainer.setVisibility(View.GONE);

            ImageListGridViewAdapter adapter = new ImageListGridViewAdapter(
                    context, listImageItems);
            holder.rv_note_image.setAdapter(adapter);
            holder.rv_note_image
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            //多张图片点击效果
                            if (listImageItems != null) {
                                Intent intent = new Intent(context,
                                        ImageViewPageActivity.class);
                                intent.putExtra("position", position);
                                intent.putExtra("jsonArray",
                                        JSON.toJSONString(listImageItems));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        }
                    });
        } else if (listImageItems != null && listImageItems.size() == 1) {
            holder.rlyt_video_bg.setVisibility(View.GONE);
            holder.rl_note_images.setVisibility(View.VISIBLE);
            holder.iv_single_image.setVisibility(View.VISIBLE);
            holder.rv_note_image.setVisibility(View.GONE);
            holder.ic_net_warn.setVisibility(View.GONE);
            holder.videoContainer.setVisibility(View.GONE);
            final String url = listImageItems.get(0);
            if (url.contains(".gif") || url.contains(".GIF")) {
                ImageUtils.getGifPic(url, R.drawable.icon_pic_default, context, new SimpleTarget<GifDrawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(GifDrawable gifDrawable, GlideAnimation glideAnimation) {
                        holder.iv_single_image.setImageDrawable(gifDrawable);
                    }

                });
            } else {
                ImageUtils.getBitmapPic(url, R.drawable.icon_pic_default, context, new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        int picHeight = bitmap.getHeight();
                        int picWidth = bitmap.getWidth();
                        ViewGroup.LayoutParams params = holder.iv_single_image.getLayoutParams();
                        double scale = PreciseCompute.div(picWidth, picHeight, 2);
                        int scaleHeight = (int) PreciseCompute.div(m_ScreenWidth, scale, 2);
                        params.width = m_ScreenWidth / 2;
                        params.height = scaleHeight / 2;
                        holder.iv_single_image.setLayoutParams(params);

                        Bitmap bm = BitmapUtils.zoomBitmap(bitmap, m_ScreenWidth, scaleHeight);
                        holder.iv_single_image.setImageBitmap(bitmap);
                    }

                });
            }
//            ImageUtils.getPic(listImageItems.get(0), holder.iv_single_image, context);
            holder.iv_single_image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //单张图片点击效果
                    if (!StringUtils.isEmpty(listImageItems.get(0))) {
                        Intent intent = new Intent(
                                context,
                                SeeHighDefinitionPictureActivity.class);
                        intent.putExtra("highDefinitionUrl",
                                listImageItems.get(0));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }

                }
            });
        } else {
            holder.rl_note_images.setVisibility(View.GONE);
            holder.iv_single_image.setVisibility(View.GONE);
            holder.rv_note_image.setVisibility(View.GONE);
            holder.rlyt_video_bg.setVisibility(View.GONE);
            holder.ic_net_warn.setVisibility(View.GONE);
            holder.videoContainer.setVisibility(View.GONE);
        }
        //把数据背在 holder.nickName身上
        holder.time.setTag(itemNotes);

        holder.btn_comment_reply.setTag(itemNotes);
        holder.btn_comment_reply.setOnClickListener(replyToCommentListener);
        return convertView;
    }

    /**
     * 儿子getview
     */
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final DongtaiBean.DataBean.CommentListBean noteComment = listData.get(groupPosition)
                .getCommentList().get(childPosition);
        ViewHolder holder = new ViewHolder();
        convertView = View.inflate(context, R.layout.mymartial_item_child,
                null);
        //回复的内容
        holder.tv_comment_reply_text = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_text);
        holder.ll_layout = (LinearLayout) convertView
                .findViewById(R.id.ll_layout);
        holder.view = (ImageView) convertView
                .findViewById(R.id.view);
        //用户名字
        holder.tv_comment_reply_writer = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_writer);
        //c查看全部按钮textview
        holder.tv_all = (TextView) convertView
                .findViewById(R.id.tv_all);
        if (!isLastChild) {
            holder.tv_all.setVisibility(View.GONE);
        } else {
            holder.tv_all.setVisibility(View.VISIBLE);
//			if(){//还有下一页
//
//			}else{
//				holder.tv_all.setVisibility(View.GONE);
//			}
            //点击查看全部
            if (listData.get(groupPosition).getCommentList().size() > 6) {
                holder.tv_all.setVisibility(View.VISIBLE);
            } else {
                holder.tv_all.setVisibility(View.GONE);
            }
            holder.tv_all.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TieZiDetailsActivity.class);
                    intent.putExtra("zhu_id", noteComment.getMessageId());
                    context.startActivity(intent);
                }
            });
        }
        int end = noteComment.getContent().length();
        int content = noteComment.getUserName() == null ? 0 : noteComment.getUserName().length();
        String text;
        if (StringUtils.isBlank(noteComment.getReferUserName())) {
            holder.tv_comment_reply_writer.setText(noteComment.getUserName() + ":");
            holder.tv_comment_reply_text.setText(noteComment.getContent());
        } else {
            text = noteComment.getReferUserName() + "回复" + noteComment.getUserName() + ":" + noteComment.getContent();
            SpannableStringBuilder ss = new SpannableStringBuilder(text);

            // 设置指定位置文字的背景颜色（将“回复”设置成灰色）
            if (!StringUtils.isBlank(noteComment.getReferUserName())){

                int start = noteComment.getReferUserName().length();
                ss.setSpan(new ForegroundColorSpan(Color.parseColor("#5A5A5A")),
                        start, start + 2, Spannable
                                .SPAN_EXCLUSIVE_INCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(Color.parseColor("#5A5A5A")),
                        start + 2 + content + 1, start + 3 + end + content, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
//		holder.tv_comment_reply_writer.setText(noteComment.getFrom_nickname());
        convertView.setTag(R.id.tag_first, groupPosition);
        convertView.setTag(R.id.tag_second, childPosition);
//		convertView.setOnClickListener(replyToCommentListener);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public Object[] getSections() {
        return null;
    }

    public int getPositionForSection(String section) {
        for (int i = 0; i < getGroupCount(); i++) {
            String sortStr = listData.get(i).getUpdateTime();
            if (TextUtils.isEmpty(sortStr)) {
                return -1;
            }
            String firstChar = listData.get(i).getUpdateTime();
            if (firstChar.equals(section))
                return i;
        }
        return -1;
    }

    public String getSectionForPosition(int i) {
        if (listData.get(i).getUpdateTime() != null) {
            return this.listData.get(i).getUpdateTime();
        }
        return null;
    }

    public class ViewHolder {
        ImageView iv_zan;
        TextView time;
        TextView msg;
        ImageView btn_comment_reply; //二级评论按钮

        public ImageView iv_single_image;// 单个照片
        public MyGridView rv_note_image;// 多个图片的GridView
        public RelativeLayout rl_note_images;
        public TextView tv_zan; // 点赞人名
        public TextView tv_zan_text;//点赞数量提醒

        private TextView tv_comment_reply_writer; // 评论者昵称
        private TextView tv_comment_reply_text; // 评论 内容
        private TextView tv_all; // 查看全部的
        public RelativeLayout rl_relayout;
        public ImageView iv_line_cu;
        public ImageView iv_line_xi;
        public ImageView iv_xiahuaxian;//下划线
        public LinearLayout ll_layout;
        public ImageView view;
        public TextView time2;

        public RelativeLayout videoContainer;//视频播放布局
        public View ic_net_warn;//移动网络提醒
        public ImageView iv_start;//开始按钮
        public RelativeLayout rlyt_video_bg;//封面图布局视频
        public Button btn1;//移动网络继续播放按钮
        public ImageView iv_video_bg;
    }

    public void setOnZanClicklistener(OnZanClicklistener onZanClicklistener) {
        this.onZanClicklistener = onZanClicklistener;
    }

    public OnZanClicklistener onZanClicklistener;

    public interface OnZanClicklistener {
        void onZanClicklistener(int position, View view);
    }

}
