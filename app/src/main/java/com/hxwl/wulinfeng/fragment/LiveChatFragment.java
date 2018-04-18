package com.hxwl.wulinfeng.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.emoji.Emoji;
import com.hxwl.newwlf.emoji.EmojiUtil;
import com.hxwl.newwlf.emoji.FaceFragment;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.bean.ChatBean;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 直播模块的热聊
 */

public class LiveChatFragment extends BaseFragment{
    private String tag="TAG";
    private View mchat;
    private TextView tv_empty;
    private ListView lvChat;
    private EditText edt_msg;
    private TextView tv_send;
    private LinearLayout ll_sendmsg;
    private String scheduleId;
    private TIMConversation conversation;
    List<ChatBean> allDataList = new ArrayList<ChatBean>();
    private ChatAdapter chatadapter;
    private String username;
    private ImageButton btnEmoticon;
    private FrameLayout container;

    public void setScheduleId(String scheduleId) {
        this.scheduleId = "schedule_"+scheduleId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mchat == null) {
            mchat = inflater.inflate(R.layout.chat_layout, container, false);
            initView(mchat);
            initdata();
        } else {
            ViewGroup parent = (ViewGroup) mchat.getParent();
            if (parent != null) {
                parent.removeView(mchat);
            }
        }
        return mchat;
    }

    private void initdata() {
        mineId = MakerApplication.instance.getUid();
        username = MakerApplication.instance.getNickName();

        edt_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                edt_msg.setFocusable(true);
                edt_msg.setFocusableInTouchMode(true);
                edt_msg.requestFocus();
                edt_msg.requestFocusFromTouch();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edt_msg, 0);
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edt_msg, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(edt_msg.getWindowToken(), 0); //强制隐藏键盘

                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }

                final String et_keyworedmsg=edt_msg.getText().toString().trim();
                if (!TextUtils.isEmpty(et_keyworedmsg)) {
                    //构造一条消息
                    TIMMessage msg = new TIMMessage();
                    //添加文本内容
                    TIMTextElem elem = new TIMTextElem();
                    elem.setText(et_keyworedmsg);
                    //将elem添加到消息
                    if(msg.addElement(elem) != 0) {
                        Log.e(tag, "addElement failed");
                        return;
                    }
                    //发送消息
                    conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                        @Override
                        public void onError(int code, String desc) {//发送消息失败
                            //错误码code和错误描述desc，可用于定位请求失败原因
                            //错误码code含义请参见错误码表
                            Log.e(tag, "send message failed. code: " + code + " errmsg: " + desc);
                        }

                        @Override
                        public void onSuccess(TIMMessage msg) {//发送消息成功
                            Log.e(tag, "SendMsg ok");
                            ChatBean data = new ChatBean();
                            data.setMsg(et_keyworedmsg);
                            data.setSaicheng_id(scheduleId);
                            data.setNickname(MakerApplication.instance.getNickName());
                            data.setHead_url(MakerApplication.instance.getHeadPic());
                            data.setUid(MakerApplication.instance.getUid());
                            allDataList.add(data);
                            if (chatadapter == null) {
                                chatadapter = new ChatAdapter();
                                lvChat.setAdapter(chatadapter);
                                //显示最后一条信息
                                lvChat.setSelection(chatadapter.getCount());
                            } else {
                                chatadapter.notifyDataSetChanged();
                                lvChat.smoothScrollToPosition(lvChat.getCount() - 1);//移动到尾部
                            }
                        }
                    });
                    edt_msg.getText().clear();


                } else {
                    Toast.makeText(getActivity(), "内容不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }


    private void initView(View mchat) {
        btnEmoticon = (ImageButton) mchat.findViewById(R.id.btnEmoticon);
        container = (FrameLayout) mchat.findViewById(R.id.Container);
        tv_empty = (TextView) mchat.findViewById(R.id.tv_empty);
        lvChat = (ListView) mchat.findViewById(R.id.lv_chat);
        edt_msg = (EditText) mchat.findViewById(R.id.edt_msg);
        tv_send = (TextView) mchat.findViewById(R.id.tv_send);
        ll_sendmsg = (LinearLayout) mchat.findViewById(R.id.ll_sendmsg);
        FaceFragment faceFragment = FaceFragment.Instance();
        faceFragment.setListener(emojiclicklistener);
        getChildFragmentManager().beginTransaction().add(R.id.Container,faceFragment).commit();

        btnEmoticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edt_msg, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(edt_msg.getWindowToken(), 0); //强制隐藏键盘
                if (container.getVisibility()==View.VISIBLE){
                    container.setVisibility(View.GONE);
                }else {
                    container.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    FaceFragment.OnEmojiClickListener emojiclicklistener=new FaceFragment.OnEmojiClickListener() {
        @Override
        public void onEmojiDelete() {
            String text = edt_msg.getText().toString();
            if (text.isEmpty()) {
                return;
            }
            if ("]".equals(text.substring(text.length() - 1, text.length()))) {
                int index = text.lastIndexOf("[");
                if (index == -1) {
                    int action = KeyEvent.ACTION_DOWN;
                    int code = KeyEvent.KEYCODE_DEL;
                    KeyEvent event = new KeyEvent(action, code);
                    edt_msg.onKeyDown(KeyEvent.KEYCODE_DEL, event);
                    displayTextView();
                    return;
                }
                edt_msg.getText().delete(index, text.length());
                displayTextView();
                return;
            }
            int action = KeyEvent.ACTION_DOWN;
            int code = KeyEvent.KEYCODE_DEL;
            KeyEvent event = new KeyEvent(action, code);
            edt_msg.onKeyDown(KeyEvent.KEYCODE_DEL, event);
            displayTextView();
        }

        @Override
        public void onEmojiClick(Emoji emoji) {
            if (emoji != null) {
                int index = edt_msg.getSelectionStart();
                Editable editable = edt_msg.getEditableText();
                if (index < 0) {
                    editable.append(emoji.getContent());
                } else {
                    editable.insert(index, emoji.getContent());
                }
            }
            displayTextView();

        }
    };

    private void displayTextView() {
        try {
            EmojiUtil.handlerEmojiText(edt_msg, edt_msg.getText().toString(), getActivity());
            edt_msg.setSelection(edt_msg.getText().length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void doChat() {
        if (StringUtils.isEmpty(scheduleId)) {
            return;
        }
        /*
        * 聊天监听
        * */
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                TIMMessage timMessage=list.get(0);
                for(int i = 0; i < timMessage.getElementCount(); ++i) {
                    TIMElem elem = timMessage.getElement(i);
                    //获取当前元素的类型
                    TIMElemType elemType = elem.getType();
                    if (elemType == TIMElemType.Text) {
                        Log.e(tag, ((TIMTextElem)elem).getText());
                        ChatBean data = new ChatBean();
                        data.setMsg(((TIMTextElem)elem).getText());
                        data.setSaicheng_id(scheduleId);
                        if (timMessage.isSelf()){
                            data.setHead_url(MakerApplication.instance.getHeadPic());
                            data.setNickname(MakerApplication.instance.getNickName());
                            data.setUid(MakerApplication.instance.getUid());
                        }else {
                            data.setHead_url(timMessage.getSenderProfile().getFaceUrl());
                            data.setUid(timMessage.getMsgId());
                            data.setNickname(timMessage.getSenderProfile().getNickName());
                        }

                        allDataList.add(data);
                        if (chatadapter == null) {
                            chatadapter = new ChatAdapter();
                            lvChat.setAdapter(chatadapter);
                            //显示最后一条信息
                            lvChat.setSelection(chatadapter.getCount());
                        } else {
                            chatadapter.notifyDataSetChanged();
                            lvChat.smoothScrollToPosition(lvChat.getCount() - 1);//移动到尾部
                        }
                    }
                }

                return false;
            }//消息监听器
        });



    }

    @Override
    public void onResume() {
        doChat();
        if (!StringUtils.isBlank(MakerApplication.instance.getWeixinUnionId())&&StringUtils.isBlank(MakerApplication.instance.getMobile())){
            loginTIM(MakerApplication.instance.getWeixinUnionId());//登录腾讯云
        }else {
            loginTIM(MakerApplication.instance.getMobile());//登录腾讯云
        }
        super.onResume();
    }

    private void loginTIM(String username) {
        TIMManager.getInstance().login(username, MakerApplication.instance.getSignature(), new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.d("tag", "login failed. code: " + code + " errmsg: " + desc);
            }
            @Override
            public void onSuccess() {
                Log.d("tag", "login succ");
                initgerenziliaoIM();//给腾讯IM设置昵称和头像
                initIM();//加入聊天室
            }
        });
    }
    private void initgerenziliaoIM() {
        //初始化参数，修改昵称为“cat”
        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
        param.setNickname(MakerApplication.instance.getNickName());
        param.setFaceUrl( MakerApplication.instance.getHeadPic());
        TIMFriendshipManager.getInstance().modifyProfile(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e(tag, "modifyProfile failed: " + code + " desc" + desc);
            }

            @Override
            public void onSuccess() {
                Log.e(tag, "modifyProfile succ");
            }
        });
    }
    private void initIM() {
        TIMGroupManager.getInstance().applyJoinGroup(scheduleId, "some reason", new TIMCallBack() {
            @java.lang.Override
            public void onError(int code, String desc) {
                //接口返回了错误码code和错误描述desc，可用于原因
                //错误码code列表请参见错误码表
                Log.e(tag, code+"disconnected"+desc);
            }

            @java.lang.Override
            public void onSuccess() {
                Log.e(tag, "join group");
            }
        });
        //会话类型：群组
        conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,      //会话类型：群组
                scheduleId);

    }
    private String mineId;//我自己的id
    private ViewHolder vh2;




    /**
     * 热聊
     */
    public class ChatAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return allDataList.size() == 0 ? 0 : allDataList.size();
        }

        @Override
        public Object getItem(int i) {
            return allDataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                vh2 = new ViewHolder();
                convertView = getActivity().getLayoutInflater().inflate(R.layout.live_chat_item, null);

                vh2.rl_layout_left = (RelativeLayout) convertView.findViewById(R.id.rl_layout_left);
                vh2.rl_layout_right = (LinearLayout) convertView.findViewById(R.id.rl_layout_right);

                vh2.user_icon1 = (ImageView) convertView.findViewById(R.id.user_icon1);
                vh2.user_icon2 = (ImageView) convertView.findViewById(R.id.user_icon2);
                vh2.name_left = (TextView) convertView.findViewById(R.id.name_left);
                vh2.name_right = (TextView) convertView.findViewById(R.id.name_right);
                vh2.content_left = (TextView) convertView.findViewById(R.id.content_left);
                vh2.content_right = (TextView) convertView.findViewById(R.id.content_right);
                convertView.setTag(vh2);
            } else {
                vh2 = (ViewHolder) convertView.getTag();
            }


            String imgurl = allDataList.get(i).getHead_url();
            String name = allDataList.get(i).getNickname();
            String msg = allDataList.get(i).getMsg();
            String userId = allDataList.get(i).getUid();
            mineId = MakerApplication.instance.getUid();

            if (userId.equals(mineId)) {
                vh2.rl_layout_left.setVisibility(View.GONE);
                vh2.rl_layout_right.setVisibility(View.VISIBLE);
                GlidUtils.setGrid2(getActivity(),imgurl,vh2.user_icon2);

                try {
                    EmojiUtil.handlerEmojiText(vh2.content_right, msg, getActivity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (name != null && !TextUtils.isEmpty(name)) {
                    if (name.length() > 6) {
                        vh2.name_right.setText(name.substring(0, 6) + "***");

                    } else {
                        vh2.name_right.setText(name);
                    }
                } else {
                    vh2.name_right.setText("游客");
                }


            } else {

                vh2.rl_layout_left.setVisibility(View.VISIBLE);
                vh2.rl_layout_right.setVisibility(View.GONE);

                GlidUtils.setGrid2(getActivity(),imgurl,vh2.user_icon1);

                try {
                    EmojiUtil.handlerEmojiText(vh2.content_left, msg, getActivity());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (name != null && !TextUtils.isEmpty(name)) {
                    if (name.length() > 6) {
                        vh2.name_left.setText(name.substring(0, 6) + "***");

                    } else {
                        vh2.name_left.setText(name);
                    }
                } else {
                    vh2.name_left.setText("游客");
                }

            }
            return convertView;
        }
    }
    private static class ViewHolder {
        public TextView changci;
        public TextView guessing;
        public ImageView iv_vs;
        public TextView tv_changci;
        public ImageView user_icon1;
        public ImageView user_icon2;
        public TextView name_left;
        public TextView name_right;
        public TextView content_left;
        public TextView content_right;
        public RelativeLayout rl_layout_left;
        public LinearLayout rl_layout_right;

    }

    @Override
    public void onStop() {
        //退出群组
        TIMGroupManager.getInstance().quitGroup(
                scheduleId,  //群组Id
                new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        Log.e(tag, "=onError");
                    }

                    @Override
                    public void onSuccess() {
                        Log.e(tag, "quit group succ");
                    }
                });      //回调
        super.onStop();
    }
}
