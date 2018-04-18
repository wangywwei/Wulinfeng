package com.hxwl.wulinfeng.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.downloadapk.CheckVersionRunnable;
import com.hxwl.common.utils.ThreadPoolUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.CheckVersionBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/19.
 */
public class AboutWLFActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_gengxin;
    private ImageView iv_dian;
    private RelativeLayout rl_banben;

    // 要申请的权限
    String alertTitle = "存储权限申请";
    private String[] permissions = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private AlertDialog dialog;
    private String downUrl;
    private String qiangzhiUpdate;
    private CheckVersionBean.DataBean bean;
    private int versionCode;//本地版本
    private String newVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutwlf_activity);
        AppUtils.setTitle(AboutWLFActivity.this);
        initView();
        checkVersion();
    }
    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(this);
        RelativeLayout rl_banben =  (RelativeLayout) findViewById(R.id.rl_banben);
        rl_banben.setOnClickListener(this);
        RelativeLayout rl_tiaokuan = (RelativeLayout) findViewById(R.id.rl_tiaokuan);
        rl_tiaokuan.setOnClickListener(this);
        RelativeLayout rl_zhengce = (RelativeLayout) findViewById(R.id.rl_zhengce);
        rl_zhengce.setOnClickListener(this);
        tv_gengxin = (TextView) findViewById(R.id.tv_gengxin);
        TextView tv_verison = (TextView) findViewById(R.id.tv_verison);
        iv_dian = (ImageView) findViewById(R.id.iv_dian);
        String versionName = AppUtils.getVersionName(AboutWLFActivity.this);
        tv_verison.setText("武林风 V"+versionName);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null ;
        switch(view.getId()){
            case R.id.rl_banben://检查新版本 判断是不是能点击
                JumpToUpdate();
//                shouPopuWin();
                break ;
            case R.id.rl_tiaokuan://条款
                intent = new Intent(AboutWLFActivity.this,AgreementActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
                break ;
            case R.id.rl_zhengce://政策
                intent = new Intent(AboutWLFActivity.this,AgreementActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break ;
            case R.id.user_icon:
                finish();
                break ;
             default:
                 break;

        }
    }

    private void JumpToUpdate() {
        if (newVersion != null && versionCode < Integer.valueOf(newVersion)) { //大于本地版本
            //检查本地是否已下载
            //如果已下载,提示用户安装
            //启动通知，去下载
            ThreadPoolUtils.newInstance().execute(new CheckVersionRunnable(AboutWLFActivity.this, bean));
        }else{
            iv_dian.setVisibility(View.GONE);
            ToastUtils.showToast(AboutWLFActivity.this,"已是最新版本");
        }
    }

    private void checkVersion() {
        // 检查该权限是否已经获取
        int i = ContextCompat.checkSelfPermission(this, permissions[0]);
        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
        if (i != PackageManager.PERMISSION_GRANTED) {
            //没有获取权限时，结束该方法
            return;
        }
        OkHttpUtils.post()
                .url(URLS.COMMON_SYSTEMINFO)
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
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                CheckVersionBean bean = gson.fromJson(response, CheckVersionBean.class);
                                if (bean.getCode().equals("1000")) {
                                    //服务器返回的版本
                                    newVersion = bean.getData().getAndroidMaxVersion();
                                    //下载地址
                                    downUrl = bean.getData().getAndroidDownloadUrl();
                                    //强制更新的标志
                                    qiangzhiUpdate =  bean.getData().getAndroidMinVersion();
                                    //本地的版本
                                    versionCode = AppUtils.getVersionCode(AboutWLFActivity.this);

                                    if (newVersion != null && versionCode < Integer.valueOf(newVersion)) { //大于本地版本
                                        iv_dian.setVisibility(View.VISIBLE);
                                        tv_gengxin.setVisibility(View.VISIBLE);
                                    }else{
                                        iv_dian.setVisibility(View.GONE);
                                        tv_gengxin.setVisibility(View.GONE);
                                    }
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(AboutWLFActivity.this));
                                    AboutWLFActivity.this.finish();
                                }else {
                                    iv_dian.setVisibility(View.GONE);
                                    tv_gengxin.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle(alertTitle)
                .setMessage(getResources().getString(R.string.app_name)+"需要使用存储权限，您是否允许？"+getResources().getString(R.string.app_name))
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSettting();
                    } else
                        finish();
                } else {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {
        dialog = new AlertDialog.Builder(this)
                .setTitle(alertTitle)
                .setMessage("请在-应用设置-权限-中，允许"+getResources().getString(R.string.app_name)+"使用存储权限来保存用户数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void shouPopuWin() {
            /**
             * 测量控件大小
             */
            View view = View.inflate(AboutWLFActivity.this, R.layout.popu_currency,
                    null);
            //取消按钮
            TextView iv_char_cancale = (TextView) view.findViewById(R.id.iv_char_cancle);
                //分局type判断显示什么按钮
            TextView iv_char_ok = (TextView) view.findViewById(R.id.iv_char_ok);
           final PopupWindow pop = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            pop.setOutsideTouchable(true);
            pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 获取点击view的坐标
            pop.showAsDropDown(view);
            // 透明度渐变
            AlphaAnimation aa = new AlphaAnimation(0, 1);
            aa.setDuration(300);

            // 大小渐变
            ScaleAnimation sa = new ScaleAnimation(0.3f, 1, 0.3f, 1,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
            sa.setDuration(500);

            AnimationSet as = new AnimationSet(true);
            as.addAnimation(aa);
            as.addAnimation(sa);
            view.startAnimation(as);
            //保存成功
            TextView tv_title1 = (TextView) view.findViewById(R.id.tv_title1);
//            tv_title1.setText(title1);
            //去数据中心
            TextView tv_title2 = (TextView) view.findViewById(R.id.tv_title2);
//            tv_title2.setText(title2);
            iv_char_cancale.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pop.dismiss();
//                    icallBack.onCancleClick(pop);
                }
            });
            iv_char_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    icallBack.onOkClick(pop);
                    pop.dismiss();
                }
            });

    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(this, "关于武林风");
        TCAgent.onPageStart(this, "关于武林风");
        checkVersion();
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"关于武林风");
        TCAgent.onPageEnd(this,"关于武林风");
    }
}
