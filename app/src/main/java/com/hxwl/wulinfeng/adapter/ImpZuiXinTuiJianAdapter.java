package com.hxwl.wulinfeng.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hxwl.common.utils.DensityUtil;
import com.hxwl.common.utils.HncNotifier;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HuiGuDetailActivity;
import com.hxwl.wulinfeng.activity.LiveDetailActivity;
import com.hxwl.wulinfeng.activity.LoginforCodeActivity;
import com.hxwl.wulinfeng.activity.ZiXunDetailsActivity;
import com.hxwl.wulinfeng.bean.HomeTuijianZhiboBean;
import com.hxwl.wulinfeng.bean.MainPageTuiJianBean;
import com.hxwl.wulinfeng.bean.SaichengBean;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.TimeUtiles;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.lecloud.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.value;
import static com.hxwl.wulinfeng.R.id.llyt_toutiao_zhibo;
import static com.hxwl.wulinfeng.R.id.view_line;

/**
 * Created by Allen on 2017/5/27.
 */

public class ImpZuiXinTuiJianAdapter extends BaseZuiXinTuiJianAdapter {
    private List<SaichengBean> tuijianZhiboBeen ;
    private Activity m_Context;
    private int screenWidth;
    private long value2;

    public ImpZuiXinTuiJianAdapter(List<SaichengBean> tuijianZhiboBeen, Activity context){
        try{
//            if(mainPageTuiJianBean != null){
//                this.zhiboBeanList = mainPageTuiJianBean.getData().getZhibo();
//                this.duizhenBeanList = mainPageTuiJianBean.getData().getDuizhen();
//            }
            this.m_Context = context;
            this.tuijianZhiboBeen = tuijianZhiboBeen ;
            screenWidth = ScreenUtils.getScreenWidth(m_Context);
            value2 = System.currentTimeMillis();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 推荐直播界面
     * @param parent
     * @param position
     * @return
     */
    @Override
    public ZuiXinTuijianZhiBoViewHolder onCreateZhiBoViewHolder(ViewGroup parent, int position) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view1 = inflate.inflate(R.layout.item_zuixin_tuijian_zhibo, null);
        return new ZuiXinTuijianZhiBoViewHolder(view1,m_Context);
    }

    @Override
    public int getZhiBoViewCount() {
        try{
            return tuijianZhiboBeen.size();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public void onBindZhiBoViewHolder(ZuiXinTuijianZhiBoViewHolder holder, final int position) {
        final SaichengBean zhiboBean = tuijianZhiboBeen.get(position);

        ImageUtils.getPic(zhiboBean.getImage(),holder.iv_zhibo_thumbnail,m_Context);

        if (zhiboBean.getState()!=null && zhiboBean.getState().equals("2")){ //zhibo
            holder.iv_zhibo_state.setBackgroundResource(R.drawable.live_btn);
        }else if (zhiboBean.getState()!=null && zhiboBean.getState().equals("3")){ //已结束
            holder.iv_zhibo_state.setBackgroundResource(R.drawable.huikan);
        }else {
            if (zhiboBean.getYugao_url()!=null && !TextUtils.isEmpty(zhiboBean.getYugao_url())){
                holder.iv_zhibo_state.setBackgroundResource(R.drawable.yugao_btn);
            }else{
                holder.iv_zhibo_state.setBackgroundResource(R.drawable.wekaishi_btn);
            }
        }
        if(tuijianZhiboBeen.size() == 1){
//            holder.view_line.setVisibility(View.GONE);
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holder.rl_width
                    .getLayoutParams();
            int width = ScreenUtils.getScreenWidth(m_Context);
            linearParams.width = width - DensityUtil.dip2px(m_Context,0);
            linearParams.height = (int)m_Context.getResources().getDimension(R.dimen.px_870);
            holder.rl_width.setLayoutParams(linearParams);
        }else{
//            holder.view_line.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) holder.rl_width
                    .getLayoutParams();
            int width = ScreenUtils.getScreenWidth(m_Context);
            linearParams.width = width - DensityUtil.dip2px(m_Context,15);
            linearParams.height = (int)m_Context.getResources().getDimension(R.dimen.px_870);
            holder.rl_width.setLayoutParams(linearParams);
        }
        holder.tv_time.setText(TimeUtiles.getTimeeForTuiJ(zhiboBean.getStart_time()));
        holder.tv_title.setText(zhiboBean.getTitle());
//        holder.tv_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        holder.tv_title2.setText(zhiboBean.getChangguan_addr());
        holder.flag.setText(zhiboBean.getSaishi_name());
        holder.flag_back.setText(zhiboBean.getSaishi_name());

        //TODO 判断 video_type 上边图标
        if("1".equals(zhiboBean.getVideo_type())){
            holder.flag2.setImageResource(R.drawable.live_icon);
        }else if("2".equals(zhiboBean.getVideo_type())){
            holder.flag2.setImageResource(R.drawable.tv);
        }else{
            holder.flag2.setVisibility(View.GONE);
        }

        String yuyue_state = zhiboBean.getYuyue_state();
        String state = zhiboBean.getState();//0不可用 1未开始 2进行中
        String video_type = zhiboBean.getVideo_type();
        String start_time = zhiboBean.getStart_time();
        long value_Start = Long.parseLong(start_time)*1000 +600000;
        long value_End = Long.parseLong(zhiboBean.getEnd_time())*1000 +600000;
        holder.iv_img.setVisibility(View.VISIBLE);

        //直播中判断
        if(video_type.equals("1") && state.equals("2")){
            holder.iv_img.setVisibility(View.VISIBLE);
            holder.rl_click.setBackgroundResource(R.drawable.send_red);
            holder.iv_button.setText("直播中");
            holder.iv_img.setImageResource(R.drawable.zhibozhong);
        }else if(video_type.equals("1") && state.equals("1")){
            holder.iv_img.setVisibility(View.VISIBLE);
            if("empty".equals(yuyue_state)){
                if(value_Start > value2){
                    holder.iv_button.setText("预约");
                    holder.rl_click.setBackgroundResource(R.drawable.send_red);
                    holder.iv_img.setImageResource(R.drawable.time_icon);
                }else{
                    holder.iv_button.setText("不可预约");
                    holder.rl_click.setBackgroundResource(R.drawable.send_gray);
                    holder.iv_img.setImageResource(R.drawable.time_icon);
                }
            }else if("0".equals(yuyue_state)){
                holder.rl_click.setBackgroundResource(R.drawable.send_gray);
                holder.iv_button.setText("已预约");
                holder.iv_img.setVisibility(View.GONE);
            }else if("1".equals(yuyue_state)){
                holder.rl_click.setBackgroundResource(R.drawable.send_gray);
                holder.iv_button.setText("已预约");
                holder.iv_img.setVisibility(View.GONE);
            }
        }else if(video_type.equals("1") && state.equals("3")){
            holder.rl_click.setBackgroundResource(R.drawable.send_red);
            holder.iv_img.setVisibility(View.VISIBLE);
            holder.iv_button.setText("已结束");
            holder.iv_img.setImageResource(R.drawable.zhibozhong);
        }else if(video_type.equals("2") && value_Start < value2 && value2 <= value_End){
            holder.rl_click.setBackgroundResource(R.drawable.send_red);
            holder.iv_img.setVisibility(View.VISIBLE);
            holder.iv_button.setText("直播中");
            holder.iv_img.setImageResource(R.drawable.zhibozhong);
        }else if(video_type.equals("2") && value2 <value_Start){
            holder.iv_img.setVisibility(View.VISIBLE);
            if("empty".equals(yuyue_state)){
                if(value_Start > value2){
                    holder.rl_click.setBackgroundResource(R.drawable.send_red);
                    holder.iv_button.setText("预约");
                    holder.iv_img.setImageResource(R.drawable.time_icon);
                }else{
                    holder.iv_button.setText("不可预约");
                    holder.rl_click.setBackgroundResource(R.drawable.send_gray);
                    holder.iv_img.setImageResource(R.drawable.time_icon);
                }
            }else if("0".equals(yuyue_state)){
                holder.iv_button.setText("已预约");
                holder.rl_click.setBackgroundResource(R.drawable.send_gray);
                holder.iv_img.setVisibility(View.GONE);
            }else if("1".equals(yuyue_state)){
                holder.iv_button.setText("已预约");
                holder.rl_click.setBackgroundResource(R.drawable.send_gray);
                holder.iv_img.setVisibility(View.GONE);
            }else{
                holder.iv_button.setText("不可预约");
                holder.rl_click.setBackgroundResource(R.drawable.send_gray);
                holder.iv_img.setImageResource(R.drawable.time_icon);
            }
        }else if(video_type.equals("2") && value2 >value_End){
            holder.rl_click.setBackgroundResource(R.drawable.send_red);
            holder.iv_img.setVisibility(View.VISIBLE);
            holder.iv_button.setText("已结束");
            holder.iv_img.setImageResource(R.drawable.zhibozhong);
        }else{ //不能做任何处理
            holder.rl_click.setBackgroundResource(R.drawable.send_red);
            holder.iv_button.setText("未识别类型");
            holder.iv_img.setVisibility(View.GONE);
        }
        holder.rl_click.setPadding(24,14,24,14);
        //售票中 -- 判断
        if((video_type.equals("1") && state.equals("1"))  && !TextUtils.isEmpty(zhiboBean.getShoupiao_url())){
            holder.iv_shoupiao.setVisibility(View.VISIBLE);
        } else{
            holder.iv_shoupiao.setVisibility(View.GONE);
        }
        if((video_type.equals("2") && value > value2) && !TextUtils.isEmpty(zhiboBean.getShoupiao_url())){
            holder.iv_shoupiao.setVisibility(View.VISIBLE);
        } else{
            holder.iv_shoupiao.setVisibility(View.GONE);
        }
        holder.rl_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null ;
                String state = zhiboBean.getState();//0不可用 1未开始 2进行中 3 已结束
                if("0".equals(state)){
                    ToastUtils.showToast(m_Context,"类型不可用。");
                }else if("1".equals(state)){//预约
                        String start_time = zhiboBean.getStart_time();
                        long value = Long.parseLong(start_time)*1000 ;
                        long value2 = System.currentTimeMillis();
                        if(value <= value2){
                            intent = new Intent(m_Context, HuiGuDetailActivity.class) ;
                            intent.putExtra("saichengId",zhiboBean.getSaicheng_id());
                            intent.putExtra("saishiId",zhiboBean.getSaishi_id());
                            intent.putExtra("name",zhiboBean.getTitle());
                            intent.putExtra("pageType","bisaishipin");
                            m_Context.startActivity(intent);
                        }else{
                            intent = new Intent(m_Context, LiveDetailActivity.class) ;
                            intent.putExtra("saichengId",zhiboBean.getSaicheng_id());
                            intent.putExtra("saishiId",zhiboBean.getSaishi_id());
                            intent.putExtra("name",zhiboBean.getTitle());
                            m_Context.startActivity(intent);
                        }
                }else if("2".equals(state)){//直播
                    intent = new Intent(m_Context, LiveDetailActivity.class) ;
                    intent.putExtra("saichengId",zhiboBean.getSaicheng_id());
                    intent.putExtra("saishiId",zhiboBean.getSaishi_id());
                    intent.putExtra("name",zhiboBean.getTitle());
                    m_Context.startActivity(intent);
                }else if("3".equals(state)){//回放
                    intent = new Intent(m_Context, HuiGuDetailActivity.class) ;
                    intent.putExtra("saichengId",zhiboBean.getSaicheng_id());
                    intent.putExtra("saishiId",zhiboBean.getSaishi_id());
                    intent.putExtra("name",zhiboBean.getTitle());
                    intent.putExtra("pageType","bisaishipin");
                    m_Context.startActivity(intent);
                }else{
                    ToastUtils.showToast(m_Context,"未识别类型。");
                }
            }
        });

        holder.rl_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null ;
                String state = zhiboBean.getState();//0不可用 1未开始 2进行中 3 已结束
                if("0".equals(state)){
                    ToastUtils.showToast(m_Context,"类型不可用。");
                }else if("1".equals(state)){//预约
                    if("empty".equals(zhiboBean.getYuyue_state())){
                        String start_time = zhiboBean.getStart_time();
                        long value = Long.parseLong(start_time)*1000 ;
                        long value2 = System.currentTimeMillis();
                        if(value <= value2){
                            intent = new Intent(m_Context, HuiGuDetailActivity.class) ;
                            intent.putExtra("saichengId",zhiboBean.getSaicheng_id());
                            intent.putExtra("saishiId",zhiboBean.getSaishi_id());
                            intent.putExtra("name",zhiboBean.getTitle());
                            intent.putExtra("pageType","bisaishipin");
                            m_Context.startActivity(intent);
                        }else{
                            postYuYueState(zhiboBean);
                        }
                    }else if("0".equals(zhiboBean.getYuyue_state())){
                        postUnYuYueState(zhiboBean);
                    }else{
                        ToastUtils.showToast(m_Context,"已经预约过");
                    }

                }else if("2".equals(state)){//直播
                    intent = new Intent(m_Context, LiveDetailActivity.class) ;
                    intent.putExtra("saichengId",zhiboBean.getSaicheng_id());
                    intent.putExtra("saishiId",zhiboBean.getSaishi_id());
                    intent.putExtra("name",zhiboBean.getTitle());
                    m_Context.startActivity(intent);
                }else if("3".equals(state)){//回放
                    intent = new Intent(m_Context, HuiGuDetailActivity.class) ;
                    intent.putExtra("saichengId",zhiboBean.getSaicheng_id());
                    intent.putExtra("saishiId",zhiboBean.getSaishi_id());
                    intent.putExtra("name",zhiboBean.getTitle());
                    intent.putExtra("pageType","bisaishipin");
                    m_Context.startActivity(intent);
                }else{
                    ToastUtils.showToast(m_Context,"未识别类型。");
                }
            }
        });
    }
    //提交取消预约
    private void postUnYuYueState(final SaichengBean dataBean) {
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                m_Context, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    dataBean.setYuyue_state("empty");
                    notifyDataSetChanged();
                    SPUtils.clearYuYueInfo(m_Context, dataBean);
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
     //提交预约
    private void postYuYueState(final SaichengBean dataBean) {
        AppUtils.setEvent(m_Context,"Appointment","点击预约");
        String uid = MakerApplication.instance().getUid();
        String loginKey = MakerApplication.instance().getLoginKey();
        if(TextUtils.isEmpty(uid) || TextUtils.isEmpty(loginKey)){
            m_Context.startActivity(new Intent(m_Context,LoginforCodeActivity.class));
            return ;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                m_Context, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    initTiXing(dataBean);
                } else {
                    UIUtils.showToast(result.getMsg());
                }
            }
        });
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("saichengId", dataBean.getSaicheng_id());
        map.put("device", "2");
        map.put("uid", MakerApplication.instance().getUid());
        map.put("method", NetUrlUtils.wulin_yuyuetongzhi);
        tasker.execute(map);
    }
    private void initTiXing(SaichengBean bean) {
        ToastUtils.diyToast(m_Context, "直播开始前10分钟提醒！");
        bean.setYuyue_state("0");
        notifyDataSetChanged();
        MakerApplication.instance().makeSaicheng(m_Context, bean);
        if (bean != null) {
            String start_time = bean.getStart_time();
            long value = Long.parseLong(start_time) * 1000;
            long value2 = System.currentTimeMillis();
            if (value <= value2) {
                SPUtils.clearYuYueInfo(m_Context, bean);
            } else {
                HncNotifier.getHncNotifier().shownotifyItemNote(m_Context, bean);
            }
        }
    }

    @Override
    public ZuiXinTuijianDuiZhenViewHolder onCreateDuiZhenViewHolder(ViewGroup parent, int position) {
        LayoutInflater inflate = LayoutInflater.from(parent.getContext());
        View view1 = inflate.inflate(R.layout.item_zuixin_tuijian_duizhen, null);
        return new ZuiXinTuijianDuiZhenViewHolder(view1,m_Context);
    }

    @Override
    public int getDuiZhenViewCount() {
            return 0;
    }

    @Override
    public void onBindDuiZhenViewHolder(ZuiXinTuijianDuiZhenViewHolder holder, final int position) {
//        if (duizhenBeanList !=null && duizhenBeanList.size() > 0) { //保证对阵信息有数据
//            MainPageTuiJianBean.zhiboBean.DuizhenBean mbean = duizhenBeanList.get(position);
//            //选手a和b的旗帜图片
//            String a_Flag_photo = mbean.getRed_player_guoqi_img();
//            String b_Flag_photo = mbean.getBlue_player_guoqi_img();
//            //选手a和b的头像
//            String a_photo = mbean.getRed_player_photo();
//            String b_photo = mbean.getBlue_player_photo();
//            String count = mbean.getVs_order();
//            //参加竞猜的人数
//            String peopleNum = mbean.getBet_renshu()+"";
//            if (a_photo != null && !TextUtils.isEmpty(a_photo)) {
//                ImageUtils.getPic(a_Flag_photo, holder.country_left, m_Context);
//            } else {
//                holder.country_left.setVisibility(View.GONE);
//            }
//
//            if (b_photo != null && !TextUtils.isEmpty(b_photo)) {
//                ImageUtils.getPic(b_Flag_photo, holder.country_right, m_Context);
//            } else {
//                holder.country_right.setVisibility(View.GONE);
//            }
//            ImageUtils.getCirclePic(a_photo, holder.player_icon_left, m_Context);
//            ImageUtils.getCirclePic(b_photo, holder.player_icon_right, m_Context);
//            holder.tv_toutiao_duizhen_title.setText(mbean.getTitle()+"");
//            holder.changci.setText("-第" + count + "场-");
//            holder.guessing_num.setText(peopleNum + "人竞猜");
//            holder.playerA_name.setText(mbean.getRed_player_name());
//            holder.playerB_name.setText(mbean.getBlue_player_name());
//            Log.d("666", mbean.getState());
//            //竞猜的状态
//            if (mbean.getState().equals("3")) {
//                holder.guessing.setText("竞猜结束");
////                holder.iv_vs.setImageResource(R.drawable.vsafter);
//            } else if (mbean.getState().equals("2")) {
//                holder.guessing.setText("正在比赛");
////                holder.iv_vs.setImageResource(R.drawable.vsafter);
//            } else if (mbean.getState().equals("1")) {
//                holder.guessing.setText("正在竞猜");
////                holder.iv_vs.setImageResource(R.drawable.vs);
//            } else if (mbean.getState().equals("0")) {
//                holder.guessing.setText("已退赛");
////                holder.iv_vs.setImageResource(R.drawable.vsafter);
//            }
//
//            //红 蓝方的id
//            String buleIds = mbean.getBlue_player_id();
//            String redIds = mbean.getRed_player_id();
//            if (mbean.getState().equals("3")) { //已结束
//                String result = mbean.getVs_res();
//                if (Integer.valueOf(result) > 0) { //左红右蓝
//                    if (result.equals(buleIds)) {
//                        holder.iv_sheng_right.setVisibility(View.VISIBLE);
//                        holder.iv_sheng_left.setVisibility(View.GONE);
//                    } else {
//                        holder.iv_sheng_left.setVisibility(View.VISIBLE);
//                        holder.iv_sheng_right.setVisibility(View.GONE);
//                    }
//                }
//            } else {
//                holder.iv_sheng_right.setVisibility(View.GONE);
//                holder.iv_sheng_left.setVisibility(View.GONE);
//            }
//
//            //推荐对阵的标签显示
//            if (mbean.getLabel()!=null && !mbean.getLabel().isEmpty()){
//                holder.lableView.setVisibility(View.VISIBLE);
//                holder.lableView.setText(mbean.getLabel());
//            }else{
//                holder.lableView.setVisibility(View.GONE);
//            }
//
//            holder.llyt_toutiao_duizhen.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    detalisCallBack.getDetalisData(TYPE_DUIZHEN,position);
//                }
//            });
//        }
    }
}
