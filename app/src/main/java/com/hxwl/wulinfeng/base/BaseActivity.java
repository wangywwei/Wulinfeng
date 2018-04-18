package com.hxwl.wulinfeng.base;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.swipebacklayout.lib.SwipeBackLayout;
import com.hxwl.wulinfeng.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * Created by Allen on 2017/5/22.
 * 全部activity界面的基类
 */

public abstract class BaseActivity extends SwipeBackActivity  {
    private SwipeBackLayout mSwipeBackLayout;// 左滑返回的实例
    private ImageView main_bt_goback;
    public Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT); //设置成左滑
        //创建Activity时候，添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

//        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.init();
        // mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM); //设置成从下往上拉
    }

//    private void initBack() {
//        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
//        if(user_icon != null && user_icon instanceof ImageView){
//            findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mImmersionBar != null)
//            mImmersionBar.destroy();
    }


    /**
     * 设置返回方法 可以直接调用 id设置成 main_bt_goback
     */
//    public void setBackFunction() {
//        main_bt_goback = (ImageView) findViewById(R.id.main_bt_goback);
//        main_bt_goback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }
    /**
     * 对网络连接状态进行判断
     *
     * @return true, 可用； false， 不可用
     */
    protected boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
     /*   if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;*/
        return connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable();
    }

    /**
     * 网络可用就调用下一步需要进行的方法， 网络不可用则需设置 跳转设置网络
     */
    private void initIntener() {

        // 判断网络是否可用
        if (isOpenNetwork() == true) {
            // 网络可用，则开始加载。
//             initPross();//这里是我个人程序要进行网络加载的方法，根据自己的程序而定，灵活运用。
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    getApplication());
            builder.setTitle("没有可用的网络").setMessage("是否对网络进行设置?");

            builder.setPositiveButton("是",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = null;

                            try {
                                String sdkVersion = android.os.Build.VERSION.SDK;
                                if (Integer.valueOf(sdkVersion) > 10) {
                                    intent = new Intent(
                                            android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                } else {
                                    intent = new Intent();
                                    ComponentName comp = new ComponentName(
                                            "com.android.settings",
                                            "com.android.settings.WirelessSettings");
                                    intent.setComponent(comp);
                                    intent.setAction("android.intent.action.VIEW");
                                }
                                getApplication().startActivity(intent);
                            } catch (Exception e) {
                                // Log.w(TAG,
                                // "open network settings failed, please check...");
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("否",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                    // finish();//因为网络不可用的状态，也是不让自己的程序结束运行，
                                    // 这是根据个人需要设置。
                                    // initAll();//这里是没有网络的时候，又不需要手动设置，则显示出来的一个静态页面，根据个人需要。
                                }
                            }).show();

        }
    }


    /**
     * 通过类名启动Activity
     * 不带参数
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        if (!isFinishing()) {
            openActivity(pClass, null);
        }

    }
    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        if (!isFinishing()) {
            Intent intent = new Intent(this, pClass);
            if (pBundle != null) {
                intent.putExtras(pBundle);
            }
            startActivity(intent);
        }
    }

    /**
     * 通过类名启动带返回值Activity，并且含有Bundle数据
     *
     * * @param reback 请求吗
     */
    protected void openForResultsActivity(Class<?> pClass, int reback) {
        if (!isFinishing()) {
            Intent intent = new Intent(this, pClass);

            startActivityForResult(intent, reback);
        }
    }
    /**
     * 通过类名启动带返回值Activity，并且含有Bundle数据
     *
     * @param reback 请求吗
     * @param pBundle bundle数据
     */
    protected void openForResultsActivity(Class<?> pClass, int reback, Bundle pBundle) {
        if (!isFinishing()) {
            Intent intent = new Intent(this, pClass);
            if (pBundle != null) {
                intent.putExtras(pBundle);
            }
            startActivityForResult(intent, reback);
        }
    }

    /**
     *  弹窗提示文字（准备通用 布局需要修改）
     */
    public void showPopu(String texs) {
        /**
         * 测量控件大小
         */
        View view = View.inflate(this, R.layout.popu_tuiquan,
                null);
        ImageView iv_char_ok = (ImageView) view.findViewById(R.id.iv_but1);
        TextView tv_title2 = (TextView) view.findViewById(R.id.tv_title2);
        tv_title2.setText(texs);
        final PopupWindow pop= new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
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
        iv_char_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss() ;
            }


        });
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            InputMethodManager imm = null;
            if (getCurrentFocus() != null)
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }
}
