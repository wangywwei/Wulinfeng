package com.hxwl.wulinfeng.wulin;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.DongtaiBean;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.TieZiDetailsActivity;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.NetWorkUtils;
import com.hxwl.wulinfeng.util.PreciseCompute;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import static com.hxwl.wulinfeng.R.id.rlyt_video_bg;

public class ExpandListViewAdapter extends BaseExpandableListAdapter {
    private Activity context;
    //数据
    private List<DongtaiBean.DataBean> listData;
    private OnClickListener replyToCommentListener;// 评论监听

    private final static String SECCLICK = "点赞成功";
    private final static String FAILCLICK = "取消点赞";
    public static int replay_to_comment = 1000;
    private LayoutInflater inflater;
    private int m_ScreenWidth;
    private int oldposition = -1;

    // 剪切板 控制器
    private ClipboardManager mClipboard = null;

    private VideoListener m_VideoListener;

    public interface VideoListener {
        //当前页面播放
        void playVideo(String url, RelativeLayout videoContainer, int position, int oldposition, String title ,String fengmiantuUrl);

        //跳转详情页
        void openVideoDetails(String id);

        void loadWebUrl(String url, int position, int oldposition, RelativeLayout videoContainer);

        //是否正在播放
        boolean isPlaying();

    }

    public void setVideoListener(VideoListener m_VideoListener) {
        this.m_VideoListener = m_VideoListener;
    }

    public ExpandListViewAdapter(Activity context, List<DongtaiBean.DataBean> listData, OnClickListener replyToCommentListener) {
        this.context = context;
        this.listData = listData;
        this.replyToCommentListener = replyToCommentListener;
        mClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        inflater = LayoutInflater.from(context);
        //获取屏幕的宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        m_ScreenWidth = wm.getDefaultDisplay().getWidth() - ScreenUtils.dip2px(context, 0);
    }

    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listData.get(groupPosition).getCommentList().size() > 3 ? 3 : listData.get(groupPosition).getCommentList().size();
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
        if (convertView == null ) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.pinglunandvideo_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.user_icon);
            holder.nickName = (TextView) convertView.findViewById(R.id.user_name);
            holder.time = (TextView) convertView.findViewById(R.id.fatie_time);
            holder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
            holder.msg = (TextView) convertView.findViewById(R.id.pinglun_content);
            holder.zan_num = (TextView) convertView.findViewById(R.id.zan_num);
            holder.pinlun_num = (TextView) convertView.findViewById(R.id.pinlun_num);
            holder.iv_zan = (ImageView) convertView.findViewById(R.id.iv_zan);
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
        if (!StringUtils.isBlank(itemNotes.getMessageUserName())){
            holder.nickName.setText(Photos.stringPhoto(itemNotes.getMessageUserName()));
        }

        if (itemNotes.getUpdateTime() != null && !itemNotes.getUpdateTime().isEmpty()) {
            holder.time.setText(itemNotes.getUpdateTime());
        } else {
//            viewHolder.time.setText(bean.getTime());
        }
        if (itemNotes.getContent() != null && !StringUtils.isEmpty(itemNotes.getContent())) {
            holder.msg.setVisibility(View.VISIBLE);
            holder.msg.setText(itemNotes.getContent());
        } else {
            holder.msg.setVisibility(View.GONE);
        }

        holder.zan_num.setText(itemNotes.getFavourNum()+"");
        holder.pinlun_num.setText(itemNotes.getCommentNum()+"");
        int isHavaVideo = itemNotes.getMessageType();
        if(isHavaVideo==2){
            if (!itemNotes.isSelect()) {
                holder.videoContainer.setVisibility(View.GONE);
                holder.videoContainer.removeAllViews();
                holder.rlyt_video_bg.setVisibility(View.VISIBLE);
                //视频图片背景.
                if(!TextUtils.isEmpty(itemNotes.getVideoPreviewImage())){
                    ImageUtils.getPic(URLS.IMG+itemNotes.getVideoPreviewImage(),holder.iv_video_bg ,context );
                }else{
                    holder.iv_video_bg.setImageResource(R.drawable.wlf_deimg);
                }
            } else {
                //true
                holder.rlyt_video_bg.setVisibility(View.GONE);
                holder.videoContainer.setVisibility(View.VISIBLE);
            }
        }else{

        }


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

//================================================================================================
        if (isHavaVideo==2) {
            holder.videoContainer.setVisibility(View.VISIBLE);
            holder.rl_note_images.setVisibility(View.GONE);
            holder.iv_single_image.setVisibility(View.GONE);
            holder.rv_note_image.setVisibility(View.GONE);
            holder.rlyt_video_bg.setVisibility(View.VISIBLE);
            holder.ic_net_warn.setVisibility(View.GONE);

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
                        //隐藏该容器，显示播放容器
                        holder.ic_net_warn.setVisibility(View.GONE);
                        holder.rlyt_video_bg.setVisibility(View.GONE);
                        holder.videoContainer.setVisibility(View.VISIBLE);
                        m_VideoListener.playVideo(URLS.VIDEO+itemNotes.getVideoName(),
                                holder.videoContainer, groupPosition, oldposition,itemNotes.getContent() ,URLS.IMG+itemNotes.getVideoPreviewImage());
//                        }
                    }
                }
            });
            holder.btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.ic_net_warn.setVisibility(View.GONE);
                    //隐藏该容器，显示播放容器
                    holder.rlyt_video_bg.setVisibility(View.GONE);
                    holder.videoContainer.setVisibility(View.VISIBLE);
                    m_VideoListener.playVideo(URLS.VIDEO+itemNotes.getVideoName(),
                            holder.videoContainer, groupPosition, oldposition,itemNotes.getContent(),URLS.IMG+itemNotes.getVideoPreviewImage());
                    oldposition = groupPosition;
                }
            });


        } else if (listImageItems != null && listImageItems.size() > 1) {
            // 加载图片--9张图片
            holder.rlyt_video_bg.setVisibility(View.GONE);
            holder.rl_note_images.setVisibility(View.VISIBLE);
            holder.iv_single_image.setVisibility(View.GONE);
            holder.rv_note_image.setVisibility(View.VISIBLE);
            holder.ic_net_warn.setVisibility(View.GONE);
            holder.videoContainer.removeAllViews();
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
            holder.videoContainer.removeAllViews();
            holder.videoContainer.setVisibility(View.GONE);
            final String url = listImageItems.get(0);
//            ImageUtils.getPic(url,holder.iv_single_image,context);
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
                        double scale = PreciseCompute.div(picWidth, picHeight, 0);
                        int scaleHeight = (int) PreciseCompute.div(m_ScreenWidth, scale, 0);
                        params.width = m_ScreenWidth ;
                        params.height = scaleHeight;
//                        holder.iv_single_image.setLayoutParams(params);
//
                        Bitmap bm = BitmapUtils.zoomBitmap(bitmap, m_ScreenWidth, scaleHeight);
                        holder.iv_single_image.setImageBitmap(bm);
                    }

                });
            }
            holder.iv_single_image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //单张图片点击效果
                    if (!StringUtils.isEmpty(listImageItems.get(0))) {
                        Intent intent = new Intent(
                                context,
                                SeeHighDefinitionPictureActivity.class);
                        intent.putExtra("highDefinitionUrl",
                                url);
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
            holder.videoContainer.removeAllViews();
            holder.videoContainer.setVisibility(View.GONE);
        }


        ImageUtils.getCirclePic(URLS.IMG+itemNotes.getHeadImage(), holder.imageView, context);
        holder.iv_zan.setTag(itemNotes);
        if (itemNotes.getUserIsFavourMessage()==2) {//没有点过
            holder.iv_zan.setImageResource(R.drawable.zan_icon);
        } else {//点过赞
            holder.iv_zan.setImageResource(R.drawable.yizan_icon);
        }
        // 这个字符串是一个数组,里面的对象是点过赞的人
        try {
            List<DongtaiBean.DataBean.FavourListBean> listNotePraise = itemNotes
                    .getFavourList();
            if (listNotePraise != null && !listNotePraise.isEmpty()) {
                holder.rl_relayout.setVisibility(View.VISIBLE);
                holder.tv_zan.setVisibility(View.VISIBLE);
                StringBuffer name = new StringBuffer();
                for (int i = 0; i < listNotePraise.size(); i++) {
                    if (i == listNotePraise.size() - 1) {
                        name.append(Photos.stringPhoto(listNotePraise.get(i).getNickName()));
                    } else {
                        name.append(Photos.stringPhoto(listNotePraise.get(i).getNickName()) + "、");
                    }
                }
                holder.tv_zan.setText(name.toString());
            } else {
                holder.rl_relayout.setVisibility(View.GONE);
                holder.tv_zan.setVisibility(View.GONE);
            }
        }catch (Exception i){

        }
        final int dianzannum=itemNotes.getUserIsFavourMessage();
        holder.iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                if (itemNotes.getUserIsFavourMessage()==1) {//没有点过 holder.iv_zan.setImageResource(R.drawable.zanblue_icon);
                    quxiaoDianZan();
                } else {//没点过赞
                    dianZan();
                }
            }

            private void setZanImg() {
                List<DongtaiBean.DataBean.FavourListBean> listNotePraise = itemNotes
                        .getFavourList();
                if (listNotePraise != null && listNotePraise.size() > 0) {
                    holder.rl_relayout.setVisibility(View.VISIBLE);
                    holder.tv_zan.setVisibility(View.VISIBLE);
                    StringBuffer name = new StringBuffer();
                    for (int i = 0; i < listNotePraise.size(); i++) {
                        if (i == listNotePraise.size() - 1) {
                            name.append(Photos.stringPhoto(listNotePraise.get(i).getNickName()));
                        } else {
                            name.append(Photos.stringPhoto(listNotePraise.get(i).getNickName()) + "、");
                        }
                    }
                    holder.tv_zan.setText(name);
                } else {
                    holder.rl_relayout.setVisibility(View.GONE);
                    holder.tv_zan.setVisibility(View.GONE);
                }
            }

            //点赞
            public void dianZan() {
                OkHttpUtils.post()
                        .url(URLS.SCHEDULE_ADDDYNAMICMESSAGEFAVOUR)
                        .addParams("targetId",itemNotes.getMessageId())
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("userId",MakerApplication.instance().getUserid())
                        .addParams("favourType","1")    //赞类型 1动态的赞 2资讯评论的赞 3选手留言的赞
                        .build().execute(new StringCallback() {
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
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")){
                                    itemNotes.setUserIsFavourMessage(1);
                                    holder.iv_zan.setImageResource(R.drawable.yizan_icon);
                                    DongtaiBean.DataBean.FavourListBean favourListBean = new DongtaiBean.DataBean.FavourListBean();
                                    favourListBean.setNickName(MakerApplication.instance().getNickName());
                                    favourListBean.setUserId(MakerApplication.instance().getUserid());
                                    itemNotes.getFavourList().add(0, favourListBean);
                                    if (dianzannum==1){
                                        holder.zan_num.setText(itemNotes.getFavourNum()+"");//点赞数量
                                    }else {
                                        holder.zan_num.setText(itemNotes.getFavourNum()+1+"");//点赞数量
                                    }
                                    setZanImg();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    context.startActivity(LoginActivity.getIntent(context));
                                    context.finish();
                                }else {
                                    UIUtils.showToast(bean.getMessage());
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

            }

            //取消点赞
            public void quxiaoDianZan() {
                OkHttpUtils.post()
                        .url(URLS.SCHEDULE_CANCELDYNAMICMESSAGEFAVOUR)
                        .addParams("targetId",itemNotes.getMessageId())
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("userId",MakerApplication.instance().getUserid())
                        .addParams("favourType","1")    //赞类型 1动态的赞 2资讯评论的赞 3选手留言的赞
                        .build().execute(new StringCallback() {
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
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                if (bean.getCode().equals("1000")){
                                    itemNotes.setUserIsFavourMessage(2);
                                    holder.iv_zan.setImageResource(R.drawable.zan_icon);
                                    for (DongtaiBean.DataBean.FavourListBean info : itemNotes.getFavourList()) {
                                        if (MakerApplication.instance().getUserid().equals(info.getUserId())) {
                                            itemNotes.getFavourList().remove(info);
                                            break;
                                        }
                                    }
                                    if (dianzannum==1){
                                        holder.zan_num.setText(itemNotes.getFavourNum()-1+"");//点赞数量
                                    }else {
                                        holder.zan_num.setText(itemNotes.getFavourNum()+"");//点赞数量
                                    }
                                    setZanImg();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    context.startActivity(LoginActivity.getIntent(context));
                                    context.finish();
                                }else {
                                    UIUtils.showToast(bean.getMessage());
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

            }


        });



        //把数据背在 holder.nickName身上
        holder.nickName.setTag(itemNotes);

        holder.btn_comment_reply.setTag(itemNotes);
        holder.btn_comment_reply.setOnClickListener(replyToCommentListener);
        return convertView;
    }

    /**
     * 儿子getview
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final DongtaiBean.DataBean.CommentListBean noteComment = listData.get(groupPosition)
                .getCommentList().get(childPosition);
        ViewHolder holder = new ViewHolder();
        convertView = View.inflate(context, R.layout.item_comment_reply,
                null);
        //回复的内容
        holder.tv_comment_reply_text = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_text);
        //用户名字
        holder.tv_comment_reply_writer = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_writer);
        holder.tv_comment_reply_writer2 = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_writer2);
        holder.tv_comment_reply_writer3 = (TextView) convertView
                .findViewById(R.id.tv_comment_reply_writer3);
        holder.ll_layout = (LinearLayout) convertView
                .findViewById(R.id.ll_layout);

        holder.view = (ImageView) convertView
                .findViewById(R.id.view);
        //c查看全部按钮textview
        holder.tv_all = (TextView) convertView
                .findViewById(R.id.tv_all);
        if (!isLastChild) {
            holder.tv_all.setVisibility(View.GONE);
        } else {
            holder.tv_all.setVisibility(View.VISIBLE);
            //点击查看全部
            if (listData.get(groupPosition).getCommentList().size() > 3) {
                holder.ll_layout.setPadding(10, 10, 10, 0);
                holder.tv_all.setVisibility(View.VISIBLE);
            } else {
                holder.tv_all.setVisibility(View.GONE);
            }
            holder.tv_all.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    context.startActivity(TieZiDetailsActivity.getIntent(context,noteComment.getMessageId()+"",listData.get(groupPosition)));
                }
            });
        }

        holder.tv_comment_reply_writer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onPinlun1Clicklistener){
                    onPinlun1Clicklistener.onPinlun1Clicklistener(groupPosition,childPosition);
                }
            }
        });

        holder.tv_comment_reply_writer3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onPinlun2Clicklistener){
                    onPinlun2Clicklistener.onPinlun2Clicklistener(groupPosition,childPosition);
                }
            }
        });


        if (StringUtils.isBlank(noteComment.getReferUserName())) {
            holder.tv_comment_reply_writer.setText(noteComment.getUserName()+":");
            holder.tv_comment_reply_writer2.setVisibility(View.GONE);
            holder.tv_comment_reply_writer3.setVisibility(View.GONE);
            holder.tv_comment_reply_text.setText(noteComment.getContent());
        } else {
            holder.tv_comment_reply_writer.setText(noteComment.getUserName());
            holder.tv_comment_reply_writer2.setVisibility(View.VISIBLE);
            holder.tv_comment_reply_writer3.setVisibility(View.VISIBLE);
            holder.tv_comment_reply_writer3.setText(noteComment.getReferUserName()+":");

            holder.tv_comment_reply_text.setText(noteComment.getContent());
        }
        convertView.setTag(R.id.tag_first, groupPosition);
        convertView.setTag(R.id.tag_second, childPosition);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class ViewHolder {
        ImageView imageView;
        ImageView iv_zan;
        TextView nickName;
        TextView time;
        TextView msg;
        ImageView btn_comment_reply; //二级评论按钮
        private TextView tv_comment_reply_writer2; // 评论者昵称
        private TextView tv_comment_reply_writer3; // 评论者昵称
        public ImageView iv_single_image;// 单个照片
        public MyGridView rv_note_image;// 多个图片的GridView
        public RelativeLayout rl_note_images;
        public TextView tv_zan; // 点赞人名
        public TextView zan_num; // 点赞人名
        public TextView pinlun_num; // 点赞人名

        private TextView tv_comment_reply_writer; // 评论者昵称
        private TextView tv_comment_reply_text; // 评论 内容
        private TextView tv_all; // 查看全部的
        private ImageView view;
        public RelativeLayout rl_relayout;
        public LinearLayout ll_layout;
        public RelativeLayout videoContainer;//视频播放布局
        public View ic_net_warn;//移动网络提醒
        public ImageView iv_start;//开始按钮
        public RelativeLayout rlyt_video_bg;//封面图布局视频
        public Button btn1;//移动网络继续播放按钮
        public ImageView iv_video_bg;
    }



    public OnZanClicklistener onPinlun1Clicklistener;
    public OnZanClicklistener2 onPinlun2Clicklistener;

    public void setOnPinlun1Clicklistener(OnZanClicklistener onPinlun1Clicklistener) {
        this.onPinlun1Clicklistener = onPinlun1Clicklistener;
    }

    public void setOnPinlun2Clicklistener(OnZanClicklistener2 onPinlun2Clicklistener) {
        this.onPinlun2Clicklistener = onPinlun2Clicklistener;
    }

    public interface OnZanClicklistener {
        void onPinlun1Clicklistener(int groupPosition, int childPosition);

    }
    public interface OnZanClicklistener2 {
        void onPinlun2Clicklistener(int groupPosition, int childPosition);

    }



}
