package com.hxwl.newwlf.home.home.follow;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.LeavingBean;
import com.hxwl.newwlf.modlebean.PLZixunListBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiFuDetailsActivity;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.OnClickEventUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2018/1/17.
 */

public class LeavingAdapter extends BaseExpandableListAdapter { private Activity context;
    private String id;
    //数据
    private List<LeavingBean.DataBean> listData;
    private View.OnClickListener replyToCommentListener;// 评论监听

    private final static String SECCLICK = "点赞成功";
    private final static String FAILCLICK = "取消点赞";
    public static int replay_to_comment = 1000;
    private LayoutInflater inflater;

    // 剪切板 控制器
    private ClipboardManager mClipboard = null;
    private boolean zannnn;

    public void setId(String id) {
        this.id = id;
    }

    public LeavingAdapter(Activity context, List<LeavingBean.DataBean> listData, View.OnClickListener replyToCommentListener, String id) {
        this.context = context;
        this.listData = listData;
        this.id = id;
        this.replyToCommentListener = replyToCommentListener;
        mClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listData.get(groupPosition).getReplyList() != null) {
            return listData.get(groupPosition).getReplyList().size() > 3 ? 3 : listData.get(groupPosition).getReplyList().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (listData.get(groupPosition).getReplyList() != null) {
            return listData.get(groupPosition).getReplyList().get(childPosition);
        } else {
            return null;
        }

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
        final LeavingBean.DataBean itemNotes = listData.get(groupPosition);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.zixun_pinglun_item2, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.user_icon);
            holder.nickName = (TextView) convertView.findViewById(R.id.user_name);
            holder.time = (TextView) convertView.findViewById(R.id.fatie_time);
            holder.tv_zancount = (TextView) convertView.findViewById(R.id.tv_zancount);
            holder.tv_zancount2 = (TextView) convertView.findViewById(R.id.tv_zancount2);
            holder.msg = (TextView) convertView.findViewById(R.id.pinglun_content);
            holder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
            holder.iv_zan = (ImageView) convertView.findViewById(R.id.iv_zan);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.btn_comment_reply = (ImageView) convertView.findViewById(R.id.btn_comment_reply);
            holder.rl_relayout= (RelativeLayout) convertView.findViewById(R.id.rl_relayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nickName.setText(Photos.stringPhoto(itemNotes.getUserName()));

        holder.tv_zancount.setText(itemNotes.getFavourNum()+"");//点赞数量
        holder.tv_zancount2.setText(itemNotes.getReplyNum()+"");//点赞数量

        holder.time.setText(itemNotes.getCommentTime());

        if (itemNotes.getContent() != null && !StringUtils.isEmpty(itemNotes.getContent())) {
            holder.msg.setVisibility(View.VISIBLE);
            holder.msg.setText(itemNotes.getContent());
        } else {
            holder.msg.setVisibility(View.GONE);
        }
        ImageUtils.getCirclePic(URLS.IMG+itemNotes.getHeadImg(), holder.imageView, context);
        holder.iv_zan.setTag(itemNotes);
        if (itemNotes.getUserIsFavour()==1) {//点过赞
            holder.tv_zancount.setTextColor(context.getResources().getColor(R.color.rgb_888888));
            holder.iv_zan.setImageResource(R.drawable.yizan_icon);
        } else {//没有点过
            holder.tv_zancount.setTextColor(context.getResources().getColor(R.color.rgb_888888));
            holder.iv_zan.setImageResource(R.drawable.zan_icon);
        }

        try {
            List<LeavingBean.DataBean.FavourListBean> listNotePraise = itemNotes
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
                holder.tv_zan.setText(name.toString());
            } else {
                holder.rl_relayout.setVisibility(View.GONE);
                holder.tv_zan.setVisibility(View.GONE);

            }
        }catch (Exception i){

        }

        final int dianzannum=itemNotes.getUserIsFavour();
        holder.iv_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                if(OnClickEventUtils.isFastClick()){
                    return;
                }
                if (itemNotes.getUserIsFavour()==1) {//没有点过 holder.iv_zan.setImageResource(R.drawable.zanblue_icon);
                    dianZan2(holder.iv_zan, itemNotes);
                } else {//没点过赞
                    dianZan(holder.iv_zan, itemNotes);
                }

            }
            private void setZanImg() {
                List<LeavingBean.DataBean.FavourListBean> listNotePraise = itemNotes
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
                    holder.tv_zan.setText(name.toString());
//                    holder.tv_zan_text.setText(listNotePraise.size() + "人点过赞");
                } else {
                    holder.rl_relayout.setVisibility(View.GONE);
                    holder.tv_zan.setVisibility(View.GONE);
                }
            }

            private void dianZan2(final ImageView view, final LeavingBean.DataBean itemNotes) {
                OkHttpUtils.post()
                        .url(URLS.SCHEDULE_CANCELDYNAMICMESSAGEFAVOUR)
                        .addParams("targetId",itemNotes.getCommentId())
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("userId",MakerApplication.instance().getUserid())
                        .addParams("favourType","3")    //赞类型 1动态的赞 2资讯评论的赞 3选手留言的赞
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
                                    itemNotes.setUserIsFavour(2);
                                    view.setImageResource(R.drawable.zan_icon);
                                    if (dianzannum==1){
                                        holder.tv_zancount.setText(itemNotes.getFavourNum()-1+"");//点赞数量
                                    }else {
                                        holder.tv_zancount.setText(itemNotes.getFavourNum()+"");//点赞数量
                                    }
                                    for (LeavingBean.DataBean.FavourListBean info : itemNotes.getFavourList()) {
                                        if (MakerApplication.instance().getUserid().equals(info.getUserId())) {
                                            itemNotes.getFavourList().remove(info);
                                            break;
                                        }
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
            //点赞
            public void dianZan(final ImageView view, final LeavingBean.DataBean itemNotes) {
                OkHttpUtils.post()
                        .url(URLS.SCHEDULE_ADDDYNAMICMESSAGEFAVOUR)
                        .addParams("token", MakerApplication.instance.getToken())
                        .addParams("targetId",itemNotes.getCommentId())
                        .addParams("userId", MakerApplication.instance().getUserid())
                        .addParams("favourType","3")    //赞类型 1动态的赞 2资讯评论的赞 3选手留言的赞
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
                                    itemNotes.setUserIsFavour(1);
                                    view.setImageResource(R.drawable.yizan_icon);
                                    if (dianzannum==1){
                                        holder.tv_zancount.setText(itemNotes.getFavourNum()+"");//点赞数量
                                    }else {
                                        holder.tv_zancount.setText(itemNotes.getFavourNum()+1+"");//点赞数量
                                    }
                                    LeavingBean.DataBean.FavourListBean favourListBean = new LeavingBean.DataBean.FavourListBean();
                                    favourListBean.setNickName(MakerApplication.instance().getNickName());
                                    favourListBean.setHeadImg(MakerApplication.instance().getHeadImg());
                                    favourListBean.setUserId(MakerApplication.instance().getUserid());
                                    itemNotes.getFavourList().add(0, favourListBean);
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
        final LeavingBean.DataBean.ReplyListBean noteComment = listData.get(groupPosition)
                .getReplyList().get(childPosition);
        ViewHolder holder = new ViewHolder();
        convertView = View.inflate(context, R.layout.item_zixun_comment_reply2,
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
        holder.iv_jiange = (ImageView) convertView
                .findViewById(R.id.iv_jiange);

        //c查看全部按钮textview
        holder.tv_all = (TextView) convertView
                .findViewById(R.id.tv_all);
        if (!isLastChild) {
            holder.tv_all.setVisibility(View.GONE);
        } else {
            holder.tv_all.setVisibility(View.VISIBLE);

            if (listData.get(groupPosition).getReplyList().size() > 3) {
                holder.tv_all.setVisibility(View.VISIBLE);
            } else {
                holder.tv_all.setVisibility(View.GONE);
            }
            holder.tv_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//查看详情


                    PLZixunListBean.DataBean itemNotes=new PLZixunListBean.DataBean();
                    itemNotes.setCommentId(listData.get(groupPosition).getCommentId());
                    itemNotes.setUserName(listData.get(groupPosition).getUserName());
                    itemNotes.setFavourNum(listData.get(groupPosition).getFavourNum());
                    itemNotes.setReplyNum(listData.get(groupPosition).getReplyNum());
                    itemNotes.setCommentTime(listData.get(groupPosition).getCommentTime());
                    itemNotes.setContent(listData.get(groupPosition).getContent());
                    itemNotes.setHeadImg(listData.get(groupPosition).getHeadImg());
                    itemNotes.setUserIsFavour(listData.get(groupPosition).getUserIsFavour());
                    ArrayList<PLZixunListBean.DataBean.ReplyListBean> arrayList=new ArrayList<>();
                    for (int i = 0; i <listData.get(groupPosition).getReplyList().size() ; i++) {
                        PLZixunListBean.DataBean.ReplyListBean replyListBean=new PLZixunListBean.DataBean.ReplyListBean();
                        replyListBean.setReferUserName(listData.get(groupPosition).getReplyList().get(i).getReferUserName());
                        replyListBean.setReferUserId(listData.get(groupPosition).getReplyList().get(i).getReferUserId());
                        replyListBean.setId(listData.get(groupPosition).getReplyList().get(i).getId());
                        replyListBean.setUserId(listData.get(groupPosition).getReplyList().get(i).getUserId());
                        replyListBean.setUserName(listData.get(groupPosition).getReplyList().get(i).getUserName());
                        replyListBean.setContent(listData.get(groupPosition).getReplyList().get(i).getContent());
                        replyListBean.setPid(listData.get(groupPosition).getReplyList().get(i).getId());
                        arrayList.add(replyListBean);
                    }
                    itemNotes.setReplyList(arrayList);
                    context.startActivity(HuiFuDetailsActivity.getIntent(context,
                            listData.get(groupPosition).getCommentId(),
                            itemNotes,
                            URLS.PLAYER_COMMENTREPLYLIST,id,3));
                }
            });
        }

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
        if(isLastChild){
            holder.iv_jiange.setVisibility(View.VISIBLE);
        }else{
            holder.iv_jiange.setVisibility(View.GONE);
        }

        holder.tv_comment_reply_writer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onPinlun1Clicklistener){
                    onPinlun1Clicklistener.onPinlun1Clicklistener(groupPosition,childPosition);
                }
            }
        });

        holder.tv_comment_reply_writer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=onPinlun2Clicklistener){
                    onPinlun2Clicklistener.onPinlun2Clicklistener(groupPosition,childPosition);
                }
            }
        });
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
        TextView tv_zan;
        ImageView btn_comment_reply; //二级评论按钮
        ImageView iv_img;
        private TextView tv_comment_reply_writer2; // 评论者昵称
        private TextView tv_comment_reply_writer3; // 评论者昵称
        private TextView tv_comment_reply_writer; // 评论者昵称
        private TextView tv_comment_reply_text; // 评论 内容
        private TextView tv_all; // 查看全部的
        public RelativeLayout rl_relayout;
        public TextView tv_zancount;
        public TextView tv_zancount2;
        public ImageView iv_jiange;
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
