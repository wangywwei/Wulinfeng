package com.hxwl.newwlf.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxwl.common.utils.ShowDialog;
import com.hxwl.common.utils.ShowDialog2;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
    private TextView arts_mima;
    private TextView arts_yanzhengma;
    private MimaLoginFragment mimaLoginFragment;
    private YanzhenmaLogianFragment yanzhenmaLogianFragment;
    private FragmentManager fragmentManager;
    private Fragment currentFragment;
    private ShowDialog showDialog;
    private LinearLayout weixinglog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppUtils.setTitle(LoginActivity.this);
        MakerApplication.instance().setLoginState(this ,MakerApplication.LOGOUT);
        MakerApplication.instance().clearUserInfo();//清楚用户信息
        initView();
        showDialog=new ShowDialog(this);

        initweixing();

    }

    private String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private void initweixing() {
//        String sdk=android.os.Build.VERSION.SDK; // SDK号
        String model=android.os.Build.BRAND;// 手机品牌
//        String release=android.os.Build.VERSION.RELEASE; // android系统版本号
//        Log.e("TAG", sdk+"---"+model+"----"+release );
//        UIUtils.showToast(sdk+"---"+model+"----"+release);
        try {
            Properties prop= new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if(prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null
                    ||model.equals("nubia")){
                weixinglog.setVisibility(View.GONE);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager
                    .getPackageInfo(context.getPackageName(), 0);
            int versionCode = Integer.valueOf(info.versionCode);
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void initView() {
        arts_mima = (TextView) findViewById(R.id.arts_mima);
        arts_yanzhengma = (TextView) findViewById(R.id.arts_yanzhengma);
        arts_mima.setOnClickListener(this);
        arts_yanzhengma.setOnClickListener(this);
        weixinglog = (LinearLayout) findViewById(R.id.weixinglog);
        weixinglog.setOnClickListener(this);
        mimaLoginFragment=new MimaLoginFragment();
        yanzhenmaLogianFragment=new YanzhenmaLogianFragment();
        fragmentManager = getSupportFragmentManager();
        arts_yanzhengma.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
        arts_yanzhengma.setBackground(getResources().getDrawable(R.drawable.shap_huati2));
        arts_mima.setTextColor(getResources().getColor(R.color.white));
        arts_mima.setBackground(getResources().getDrawable(R.drawable.shap_wulin));
        fragmentManager.beginTransaction().replace(R.id.login_frame, mimaLoginFragment, MimaLoginFragment.class.getSimpleName()).commit();
        currentFragment = mimaLoginFragment;
    }

    //切换fragment
    public void switchContent(Fragment from, Fragment to) {
        if (currentFragment != to) {
            currentFragment = to;
            if (to.equals(mimaLoginFragment)){
                arts_yanzhengma.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
                arts_yanzhengma.setBackground(getResources().getDrawable(R.drawable.shap_huati2));
                arts_mima.setTextColor(getResources().getColor(R.color.white));
                arts_mima.setBackground(getResources().getDrawable(R.drawable.shap_wulin));
            }else if (to.equals(yanzhenmaLogianFragment)){
                arts_yanzhengma.setTextColor(getResources().getColor(R.color.white));
                arts_yanzhengma.setBackground(getResources().getDrawable(R.drawable.shap_huati));
                arts_mima.setTextColor(getResources().getColor(R.color.rgb_BA2B2C));
                arts_mima.setBackground(getResources().getDrawable(R.drawable.shap_wulin2));
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.login_frame, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arts_mima:
                switchContent(currentFragment, mimaLoginFragment);
                break;
            case R.id.arts_yanzhengma:
                switchContent(currentFragment, yanzhenmaLogianFragment);
                break;
            case R.id.weixinglog://微信
                if (!SystemHelper.isConnected(this)) {
                    UIUtils.showToast("请检查网络");
                    return;
                }
                ShowDialog2.showProgressDialog(this,"","",true);
                if (!MakerApplication.api.isWXAppInstalled()) {
                    UIUtils.showToast("您还未安装微信客户端");
                    ShowDialog2.dismissProgressDialog();
                }else {
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    MakerApplication.api.sendReq(req);
                }

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        showDialog.dismissProgressDialog();
    }

}
