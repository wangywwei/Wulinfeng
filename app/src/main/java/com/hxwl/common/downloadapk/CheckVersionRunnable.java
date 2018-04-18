package com.hxwl.common.downloadapk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hxwl.newwlf.modlebean.CheckVersionBean;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.AboutWLFActivity;
import com.hxwl.wulinfeng.bean.VersionUpdateBean;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.config.SystemDir;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.hxwl.wulinfeng.R.style.dialog;


/**
 * 类名:CheckVersionRunnable 说明:检查版本号线程
 *
 * @author zhang
 */
public class CheckVersionRunnable implements Runnable {

    /**
     * 检查更新
     */
    private static final int CHECK_UPDATE = 1;
    /**
     * 安装文件
     */
    private static final int INSTALL_FILE = 2;
    /**
     * 弹出信息
     */
    private static final int TOAST_MESSAGE = 3;
    private Activity m_ctx;
    private CheckVersionBean.DataBean versionInfo;

    /**
     * 文件下载路径
     */
    private String m_StrApkFileUrl;
    private Handler m_Handler = new Handler() {
        public void handleMessage(Message msg) {
            String strInfo = "";
            File apkPath = null;
            switch (msg.what) {
                case CHECK_UPDATE:
                    strInfo = (String) msg.obj;
                    startDownloadApkService(strInfo);
                    break;
                case INSTALL_FILE:
                    apkPath = (File) msg.obj;
                    installapk(apkPath);
                    break;
                case TOAST_MESSAGE:
                    strInfo = (String) msg.obj;
                    ToastUtils.showToast(m_ctx, strInfo) ;
                    break;
                default:
                    break;
            }
        }

        ;

    };

    public CheckVersionRunnable(Activity ctx,
                                CheckVersionBean.DataBean versionInfo) {
        this.m_ctx = ctx;
        this.versionInfo = versionInfo;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        updateAPKServer();
    }

    /**
     * 功能:更新程序
     */
    private synchronized void updateAPKServer() {
        // TODO Auto-generated method stub
        final Message msg = Message.obtain();

        try {
            m_StrApkFileUrl = versionInfo.getAndroidDownloadUrl();
            // 获取已下载的文件 72370 29680
            File updateFile = new File(SystemDir.DIR_UPDATE_APK, m_ctx
                    .getResources().getString(R.string.app_name) + ".apk");
            if (getVersion() < Double.valueOf(versionInfo.getAndroidMaxVersion())) {
                long fileLength = getWebFileLength(m_StrApkFileUrl);
                if (updateFile.exists() && updateFile.length() == fileLength) {
                    msg.obj = updateFile;
                    msg.what = INSTALL_FILE;
                    m_Handler.sendMessage(msg);
                    return;
                }
                // 生成更新的内容描述
                msg.what = CHECK_UPDATE;
                m_Handler.sendMessage(msg);
            } else {
                // // 初始化程序下载是否成功的标记
                // UserInfo.setDownLoadSucess("0", m_ctx);
                // 清理更新的文件
                clearUpdateAPKFile(updateFile);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public  int getVersion() {
        try {
            PackageManager manager = m_ctx.getPackageManager();
            PackageInfo info = manager
                    .getPackageInfo(m_ctx.getPackageName(), 0);
            int versionCode = Integer.valueOf(info.versionCode);
            return versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 清理更新过的文件
     */
    private void clearUpdateAPKFile(File updateFile) {
        // TODO Auto-generated method stub
        if (updateFile.exists()) {
            // 当不需要的时候，清除之前的下载文件，避免浪费用户空间
            updateFile.delete();
        }
    }

    /**
     * 获取web端文件长度
     *
     * @param strWebFileUrl
     * @return
     */
    private long getWebFileLength(String strWebFileUrl) {
        // TODO Auto-generated method stub
        long updateTotalSize = 0;
        try {
            URL url = new URL(strWebFileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();
            urlConnection.setRequestProperty("User-Agent", "PacificHttpClient");
            urlConnection.setConnectTimeout(100000);
            urlConnection.setReadTimeout(20000);
            updateTotalSize = urlConnection.getContentLength();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return updateTotalSize;
    }

    /**
     * 安装apk文件
     *
     * @param apkPath
     */
    private void installapk(File apkPath) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(apkPath), type);
        m_ctx.startActivity(intent);
    }

    /**
     * 启动下载apk的服务程序
     */
    protected void startDownloadApkService(String dialogContent) {
        // TODO Auto-generated method stub
        try {
            shouPopuWin(dialogContent);
        }catch (NumberFormatException e){
            ToastUtils.showToast(m_ctx,"数字转换异常");
        }catch (Exception e) {
            // TODO Auto-generated catch block
            // 防止activity销毁时，仍然弹出对话框
            e.printStackTrace();
        }
    }
    private void shouPopuWin(String dialogContent) {
        /**
         * 测量控件大小
         */
        View view = View.inflate(m_ctx, R.layout.popu_currency,
                null);
        //取消按钮
        TextView iv_char_cancale = (TextView) view.findViewById(R.id.iv_char_cancle);
        if(Double.valueOf(versionInfo.getAndroidMaxVersion()) < 0){
            iv_char_cancale.setVisibility(View.GONE);
        }
        //分局type判断显示什么按钮
        TextView iv_char_ok = (TextView) view.findViewById(R.id.iv_char_ok);
        TextView tv_title2 = (TextView) view.findViewById(R.id.tv_title2);
        tv_title2.setText("请更新最新版本");
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
        iv_char_cancale.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AppUtils.setEvent(m_ctx,"CloseUpdateTip","关闭升级提示");
                if(Double.valueOf(versionInfo.getAndroidMinVersion()) < 0){
                    return;
                }
                //不在提醒
                SPUtils.put(m_ctx, Constants.IS_CHECK_VERSION,"1");
                pop.dismiss();
            }
        });
        iv_char_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.setEvent(m_ctx,"Update","进行版本升级");
                //启动服务去下载
                Intent intent = new Intent(m_ctx,
                        UpdateAPKServer.class);
                intent.putExtra("strUrl", m_StrApkFileUrl);
                intent.putExtra("TitleID",
                        R.string.app_name);
                // 开启更新服务
                m_ctx.startService(intent);
                pop.dismiss();
            }
        });

    }
}
