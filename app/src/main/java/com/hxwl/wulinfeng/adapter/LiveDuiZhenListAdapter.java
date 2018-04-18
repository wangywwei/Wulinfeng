package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.modlebean.HomeDuizhengBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiFuDetailsActivity;
import com.hxwl.wulinfeng.bean.Duizhen;
import com.hxwl.wulinfeng.bean.GengHuiFuBean;
import com.hxwl.wulinfeng.bean.TuJiBean;
import com.hxwl.wulinfeng.bean.TujiImgs;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;

import java.util.List;

/**
 * Created by Allen on 2017/6/21.
 */
public class LiveDuiZhenListAdapter extends BaseAdapter{
    private Activity activity ;
    private  List<HomeDuizhengBean.DataBean> listData ;
    public LiveDuiZhenListAdapter(Activity activity, List<HomeDuizhengBean.DataBean> listData) {
        this.activity = activity ;
        this.listData = listData ;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        HomeDuizhengBean.DataBean dataBean = listData.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.live_guess_item,null);
            holder = new ViewHolder();
            holder.player_icon_left = (ImageView) convertView.findViewById(R.id.iv_player_icon_left);
            holder.player_icon_right = (ImageView) convertView.findViewById(R.id.iv_player_icon_right);
            holder.country_left = (ImageView) convertView.findViewById(R.id.iv_country_left);
            holder.country_right = (ImageView) convertView.findViewById(R.id.iv_country_right);
            holder.julebu1= (TextView) convertView.findViewById(R.id.julebu1);
            holder.julebu2= (TextView) convertView.findViewById(R.id.julebu2);
            holder.playerA_name = (TextView) convertView.findViewById(R.id.playerA_name);
            holder.playerB_name = (TextView) convertView.findViewById(R.id.tv_playerB_name);
            holder.changci = (TextView) convertView.findViewById(R.id.changci);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.iv_vs = (ImageView) convertView.findViewById(R.id.iv_vs);
            holder.ivShengLeft = (ImageView) convertView.findViewById(R.id.iv_sheng_left);
            holder.ivShengRight = (ImageView) convertView.findViewById(R.id.iv_sheng_right);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_title.setText(dataBean.getAgainstName());

        String bluePhoto = URLS.IMG+ dataBean.getBluePlayerHeadImage();
        String redPhoto = URLS.IMG+ dataBean.getRedPlayerHeadImage();
        ImageUtils.getCirclePic(bluePhoto, holder.player_icon_right, activity);
        ImageUtils.getCirclePic(redPhoto, holder.player_icon_left, activity);

        //左边红色，右边蓝色
        String blueName = dataBean.getBluePlayerName();
        String redName = dataBean.getRedPlayerName();
        holder.playerA_name.setText(redName);
        holder.playerB_name.setText(blueName);
        holder.julebu2.setText(dataBean.getBluePlayerClubName());
        holder.julebu1.setText(dataBean.getRedPlayerClubName());

        String changci = dataBean.getSessionNum()+"";
        holder.changci.setText("[第" + changci + "场]");
        return convertView;
    }
    class ViewHolder{
        public LinearLayout ll_title;
        public ImageView player_icon_left;
        public ImageView player_icon_right;
        public ImageView country_left;
        public ImageView country_right;
        public TextView playerA_name;
        public TextView playerB_name;
        public TextView changci;
        public TextView tv_title;
        public ImageView iv_vs;
        public TextView tv_changci;
        public ImageView user_icon1;
        public ImageView user_icon2;
        public ImageView ivShengLeft;
        public ImageView ivShengRight;
        public TextView julebu1;
        public TextView julebu2;
    }
}
