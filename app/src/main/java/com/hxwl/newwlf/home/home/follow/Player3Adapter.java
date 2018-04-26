package com.hxwl.newwlf.home.home.follow;

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
import com.hxwl.newwlf.modlebean.Quanshou3Bean;
import com.hxwl.newwlf.modlebean.QuanshouBean;
import com.hxwl.newwlf.modlebean.YueyueBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class Player3Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Quanshou3Bean.DataBean> mList;


    public Player3Adapter(Context context, ArrayList<Quanshou3Bean.DataBean> mList) {
        this.context = context;
        this.mList = mList;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.player3_item,
                    null);
        }
        final ViewHolder holder = new ViewHolder(convertView);
        final Quanshou3Bean.DataBean friend = mList.get(position);
        if (position > 0) {

            String lastLetter = mList.get(position - 1).getFirstLetter().charAt(0)
                    + "";

            String currentLetter = mList.get(position).getFirstLetter().charAt(0)
                    + "";
            // 当前条目对应的字母和上一个条目中的字母一样,就隐藏当前字母对应的条目
            if (lastLetter.equals(currentLetter)) {

                holder.getViewHolder(convertView).tv_item_letter
                        .setVisibility(View.GONE);
            } else {
                // 为了防止被隐藏的黑色条目复用，出现字母对应条目显示不全问题，所以就将隐藏的黑色字母条目重新显示
                holder.getViewHolder(convertView).tv_item_letter
                        .setVisibility(View.GONE);
                holder.getViewHolder(convertView).tv_item_letter.setText(friend
                        .getFirstLetter().charAt(0) + "");
            }

        } else {
            // 说明是第0个黑色A条目 显示
            holder.getViewHolder(convertView).tv_item_letter
                    .setVisibility(View.GONE);
            holder.getViewHolder(convertView).tv_item_letter.setText(friend
                    .getFirstLetter().charAt(0) + "");

        }



        holder.player3_name.setText(friend.getPlayerName());
        if (!StringUtils.isBlank(friend.getClubName())&&!StringUtils.isBlank(friend.getWeightLevel()+"")){
            holder.player3_title.setText(friend.getClubName()+" / "+friend.getWeightLevel()+"KG");
        }else if (StringUtils.isBlank(friend.getClubName())&&!StringUtils.isBlank(friend.getWeightLevel()+"")){
            holder.player3_title.setText(friend.getWeightLevel()+"KG");
        } else if (!StringUtils.isBlank(friend.getClubName())&&StringUtils.isBlank(friend.getWeightLevel()+"")) {
            holder.player3_title.setText(friend.getClubName());
        }else if (StringUtils.isBlank(friend.getClubName())&&StringUtils.isBlank(friend.getWeightLevel()+"")){
            holder.player3_title.setText("");
        }

        GlidUtils.setGrid2(context, URLS.IMG+friend.getHeadImg(),holder.player3_touxiang);

        if (friend.getIsAttention()==1){
            holder.player3_guanzhu.setImageResource(R.drawable.yiguanzhu3);
        }else {
            holder.player3_guanzhu.setImageResource(R.drawable.guanzhu3);
        }

        holder.player3_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (friend.getIsAttention()==1){
                    //取消关注
                    gunazhu(URLS.SCHEDULE_USERCANCELPLAYERATTENTION,friend.getPlayerId());
                }else {
                    //关注
                    gunazhu(URLS.SCHEDULE_USERPLAYERATTENTION,friend.getPlayerId());
                }
            }
            private void gunazhu(String url,String playerId) {
                OkHttpUtils.post()
                        .url(url)
                        .addParams("userId", MakerApplication.instance.getUid())
                        .addParams("playerId",playerId)
                        .addParams("token", MakerApplication.instance.getToken())
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
                                            ToastUtils.showToast(context,bean.getMessage());
                                            if (friend.getIsAttention()==1){
                                                friend.setIsAttention(0);
                                                holder.player3_guanzhu.setImageResource(R.drawable.guanzhu3);
                                            }else {
                                                friend.setIsAttention(1);
                                                holder.player3_guanzhu.setImageResource(R.drawable.yiguanzhu3);
                                            }


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
        });


        return convertView;
    }





    static class ViewHolder {
        TextView tv_item_letter;
        private ImageView player3_touxiang;
        private TextView player3_name;
        private TextView player3_title;
        private ImageView player3_guanzhu;

        public ViewHolder(View itemView) {
            tv_item_letter = (TextView) itemView.findViewById(R.id.tv_letter);
            player3_touxiang= (ImageView) itemView.findViewById(R.id.player3_touxiang);
            player3_guanzhu= (ImageView) itemView.findViewById(R.id.player3_guanzhu);
            player3_name= (TextView) itemView.findViewById(R.id.player3_name);
            player3_title= (TextView) itemView.findViewById(R.id.player3_title);

        }

        public ViewHolder getViewHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }


}
