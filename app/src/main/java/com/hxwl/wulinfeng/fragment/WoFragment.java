package com.hxwl.wulinfeng.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.hxwl.common.utils.GlidUtils;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.AboutWLFActivity;
import com.hxwl.wulinfeng.activity.HelpAndReturnActivity;
import com.hxwl.wulinfeng.activity.MyMartialActivity;
import com.hxwl.wulinfeng.activity.NormalWebviewActivity;
import com.hxwl.wulinfeng.activity.PersonalDataActivity;
import com.hxwl.wulinfeng.activity.SaiShiYuYueActivity;
import com.hxwl.wulinfeng.activity.ShouCangActivity;
import com.hxwl.wulinfeng.activity.SystemMessageActivity;
import com.hxwl.wulinfeng.base.BaseFragment;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;

/**
 * Created by Allen on 2017/5/26.
 */

public class WoFragment extends BaseFragment implements View.OnClickListener {

    private View frag_wo;
    private ImageView iv_head_img;//头像
    private TextView tv_login;//登录
    private TextView tv_logout;//登出按钮
    private RelativeLayout rl_lay1;//赛事预约
    private RelativeLayout rl_lay2;//系统消息
    private RelativeLayout rl_lay3;//我的武林
    private RelativeLayout rl_lay4;//帮助与反馈
    private RelativeLayout rl_lay5;//关于武林风
    private RelativeLayout rl_shangcheng;//商城
    private RelativeLayout rl_haixuan;//海选赛报名
    private RelativeLayout rl_shoucang;
    private RelativeLayout rl_logout;
    private PopupWindow pop;
    private ImageView img_point,img_point2;//分别是帮助反馈和系统消息的哄对方i安
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (frag_wo == null) {
            frag_wo = inflater.inflate(R.layout.frag_wo, container, false);
            AppUtils.setTitle(getActivity());
            initView() ;
            initData();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) frag_wo.getParent();
            if (parent != null) {
                parent.removeView(frag_wo);
            }
        }
        AppUtils.setTitle(getActivity());
        return frag_wo;
    }

    private void initData() {
        sp = getActivity().getSharedPreferences("MSG", Context.MODE_PRIVATE);
        //系统消息红点
        int systemMessageCount = sp.getInt("systemMessageCount",0);
        if(0 != systemMessageCount){
            img_point2.setVisibility(View.VISIBLE);
        }else{
            img_point2.setVisibility(View.GONE);
        }
        //反馈消息红点
        int fankuicount = sp.getInt("fankuicount",0);
        if(0 != fankuicount){
            img_point.setVisibility(View.VISIBLE);
        }else{
            img_point.setVisibility(View.GONE);
        }

    }

    private void initView() {
        img_point = (ImageView) frag_wo.findViewById(R.id.img_point);//红点
        img_point2 = (ImageView) frag_wo.findViewById(R.id.img_point2);//红点
        iv_head_img = (ImageView) frag_wo.findViewById(R.id.iv_head_img);
        iv_head_img.setOnClickListener(this);
        tv_login = (TextView) frag_wo.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
        tv_logout = (TextView) frag_wo.findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(this);
        rl_logout = (RelativeLayout) frag_wo.findViewById(R.id.rl_logout);
        rl_lay1 = (RelativeLayout) frag_wo.findViewById(R.id.rl_lay1);
        rl_lay1.setOnClickListener(this);
        rl_lay2 = (RelativeLayout) frag_wo.findViewById(R.id.rl_lay2);
        rl_lay2.setOnClickListener(this);
        rl_haixuan = (RelativeLayout) frag_wo.findViewById(R.id.rl_haixuan);
        rl_haixuan.setOnClickListener(this);
        rl_shoucang = (RelativeLayout) frag_wo.findViewById(R.id.rl_shoucang);
        rl_shoucang.setOnClickListener(this);
        rl_lay3 = (RelativeLayout) frag_wo.findViewById(R.id.rl_lay3);
        rl_lay3.setOnClickListener(this);
        rl_lay4 = (RelativeLayout) frag_wo.findViewById(R.id.rl_lay4);
        rl_lay4.setOnClickListener(this);
        rl_lay5 = (RelativeLayout) frag_wo.findViewById(R.id.rl_lay5);
        rl_lay5.setOnClickListener(this);
        rl_shangcheng = (RelativeLayout) frag_wo.findViewById(R.id.rl_shangcheng);
        rl_shangcheng.setOnClickListener(this);
        initUserInfo();
    }

    private void initUserInfo() {
        GlidUtils.setGrid2(getActivity(),MakerApplication.instance.getHeadImg(),iv_head_img);

        if(!TextUtils.isEmpty(MakerApplication.instance.getNickName())){
            tv_login.setText(Photos.stringPhoto(MakerApplication.instance.getNickName()));
        }else {
            tv_login.setText("");
        }
        rl_logout.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View view) {
        Intent intent = null ;
        switch(view.getId()){
            case R.id.iv_head_img:
                intent = new Intent(getActivity(),PersonalDataActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login:
                if(MakerApplication.instance.getLoginState().equals("login")){

                }else{
                    intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_shoucang://收藏的活动



                intent = new Intent(getActivity(),SystemMessageActivity.class);
                startActivity(intent);



                intent = new Intent(getActivity(),ShouCangActivity.class);
                startActivity(intent);

                break;
            case R.id.tv_logout://退出登录
                StatService.onEvent(getActivity(),"MyLogout","我的-退出登录",1);
                TCAgent.onEvent(getActivity(),"MyLogout","我的-退出登录");
//                showNormalDialog();
                MakerApplication.instance().setLoginState(getActivity() ,MakerApplication.LOGOUT);
                MakerApplication.instance().clearUserInfo();//清楚用户信息

                startActivity(LoginActivity.getIntent(getActivity()));
                getActivity().finish();
                break;
            case R.id.rl_lay1://赛事预约
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                StatService.onEvent(getActivity(),"MyAppointment","我的-赛事预约",1);
                TCAgent.onEvent(getActivity(),"MyAppointment","我的-赛事预约");
                intent = new Intent(getActivity(),SaiShiYuYueActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_lay2://系统消息
                sp.edit().putInt("systemMessageCount", 0).commit();
                img_point2.setVisibility(View.GONE);
                intent = new Intent(getActivity(),SystemMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_lay3://我的武林
                if(StringUtils.isBlank(MakerApplication.instance.getMobile())){
                    UIUtils.showToast("请您点击我的头像去绑定手机号");
                    return;
                }
                intent = new Intent(getActivity(),MyMartialActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_lay4://帮助与反馈
                img_point.setVisibility(View.GONE);
                StatService.onEvent(getActivity(),"MyHelpFeedback","我的-帮助与反馈",1);
                TCAgent.onEvent(getActivity(),"MyHelpFeedback","我的-帮助与反馈");
                intent = new Intent(getActivity(),HelpAndReturnActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_lay5://关于武林风
                StatService.onEvent(getActivity(),"MyAboutWLF","我的-关于武林风",1);
                TCAgent.onEvent(getActivity(),"MyAboutWLF","我的-关于武林风");
                intent = new Intent(getActivity(),AboutWLFActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_shangcheng://商城
                intent=new Intent(getActivity(), NormalWebviewActivity.class);
                intent.putExtra("url",(String) SPUtils.get(getActivity(), "shoppingUrl",""));
                intent.putExtra("title","搏击商城");
                startActivity(intent);

                break;
            case R.id.rl_haixuan:
                intent=new Intent(getActivity(), NormalWebviewActivity.class);
                intent.putExtra("url",(String) SPUtils.get(getActivity(), "competitionUrl",""));
                intent.putExtra("title","海选赛报名");
                startActivity(intent);
                break;
           default:
                break;

        }

    }

    private void showNormalDialog(){
        View view = View.inflate(getActivity(), R.layout.dialog_diy,
                null);
        TextView title= (TextView) view
                .findViewById(R.id.quxiao);//设置标题
        TextView queding= (TextView) view
                .findViewById(R.id.queding);//设置标题
        pop = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 获取点击view的坐标
        pop.showAsDropDown(view);
        // 透明度渐变

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakerApplication.instance().setLoginState(getActivity() ,MakerApplication.LOGOUT);
                MakerApplication.instance().clearUserInfo();//清楚用户信息
                initUserInfo();
                pop.dismiss();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(getActivity(), "我的");
        TCAgent.onPageStart(getActivity(), "我的");
        initUserInfo();
        initData() ;
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(getActivity(),"我的");
        TCAgent.onPageEnd(getActivity(),"我的");
    }
}
