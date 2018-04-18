package com.hxwl.common.downloadapk;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat.Builder;

import com.hxwl.common.utils.ThreadPoolUtils;
import com.hxwl.wulinfeng.MainActivity;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.util.config.SystemDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 类名：UpdateAPKServer 说明：更新apk程序的服务
 * 
 * @author zhang
 * 
 */
@TargetApi(Build.VERSION_CODES.ECLAIR)
public class UpdateAPKServer extends Service {

	public static final int DOWNLOAD_COMPLETE = 0;
	public static final int DOWNLOAD_FAIL = 1;

	// 标题
	private int TitleID = 0;
	/** 文件存储路径 */
	private File m_FileSaveDir = null;
	/** 文件对象 */
	private File m_File = null;
	// 通知栏
	private NotificationManager m_UpdateNotificationManager;
	private Builder m_Builder;
	// 通知栏跳转
	private Intent m_UpdateIntent;
	private PendingIntent m_UpdatePendingIntent;

	private Handler updateHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case DOWNLOAD_COMPLETE:
				// /点击安装PendingIntent
				Uri uri = Uri.fromFile(m_File);
				Intent installIntent = new Intent(Intent.ACTION_VIEW);
				installIntent.setDataAndType(uri,
						"application/vnd.android.package-archive");
				m_UpdatePendingIntent = PendingIntent.getActivity(
						UpdateAPKServer.this, 0, installIntent, 0);
				m_Builder
						.setContentTitle(
								getResources().getString(R.string.app_name))
						.setTicker("下载完成,请点击安装").setContentText("下载完成,请点击安装")
						.setDefaults(Notification.DEFAULT_SOUND)
						.setProgress(0, 0, false);
				UpdateAPKServer.this.sendBroadcast(new Intent(
						"action.update.download.icon"));
				m_Builder.setContentIntent(m_UpdatePendingIntent);
				m_UpdateNotificationManager.notify(0, m_Builder.build());
				stopService(m_UpdateIntent);
				// 获取已下载的文件 72370 29680
				File updateFile = new File(SystemDir.DIR_UPDATE_APK, getApplicationContext()
						.getResources().getString(R.string.app_name) + ".apk");
				installapk(updateFile);
				break;
			case DOWNLOAD_FAIL:
				m_Builder
						.setContentTitle(
								getResources().getString(R.string.app_name))
						.setContentText("下载失败").setProgress(0, 0, false);
				m_Builder.build().contentIntent = m_UpdatePendingIntent;
				m_UpdateNotificationManager.notify(0, m_Builder.build());
				break;

			default:
				break;
			}

		};
	};

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
		startActivity(intent);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if(intent == null){
			return super.onStartCommand(intent, flags, startId);
		}
		String strApkUrl = intent.getStringExtra("strUrl");
		// 获取传值
		TitleID = intent.getIntExtra("TitleID", 0);
		// 创建文件
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			m_FileSaveDir = new File(SystemDir.DIR_UPDATE_APK);
			m_File = new File(m_FileSaveDir.getPath(), getResources()
					.getString(TitleID) + ".apk");
		}
		// 创建通知管理器
		m_UpdateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		m_Builder = new Builder(this);
		m_Builder.setSmallIcon(R.mipmap.ic_launcher);

		// 设置点击通知栏跳转到主界面
		m_UpdateIntent = new Intent(this, MainActivity.class);
		m_UpdatePendingIntent = PendingIntent.getActivity(this, 0,
				m_UpdateIntent, 0);
		m_Builder.setTicker("开始下载").setContentTitle("正在下载")
				.setContentIntent(m_UpdatePendingIntent)
				.setProgress(100, 0, false);
		// 发出通知
		m_UpdateNotificationManager.notify(0, m_Builder.build());
		// 开启新线程进行下载
		ThreadPoolUtils.newInstance().execute(new UpdateAPKRunnable(strApkUrl));
		return super.onStartCommand(intent, flags, startId);
	}

	class UpdateAPKRunnable implements Runnable {

		private Message msg = updateHandler.obtainMessage();
		private String m_StrApkUrl = "";

		public UpdateAPKRunnable(String strUrl) {
			this.m_StrApkUrl = strUrl;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			msg.what = DOWNLOAD_COMPLETE;
			try {

				if (!m_FileSaveDir.exists()) {
					m_FileSaveDir.mkdirs();
				}
				if (!m_File.exists()) {
					m_File.createNewFile();
				}
				boolean isComplete = downloadUpdateFile(m_StrApkUrl, m_File);
				if (isComplete) {
					// 下载成功
					updateHandler.sendMessage(msg);
				} else {
					// 下载失败
					msg.what = DOWNLOAD_FAIL;
					updateHandler.sendMessage(msg);
				}
			} catch (Exception e) {
				// TODO: handle exception
				// 下载失败
				msg.what = DOWNLOAD_FAIL;
				updateHandler.sendMessage(msg);
			}
		}

	}

	public boolean downloadUpdateFile(String downloadUrl, File saveFile) {
		// TODO Auto-generated method stub
		int downloadCount = 0;
		int currentSize = 0;
		// 下载大小
		long totalSize = 0;
		long updateTotalSize = 0;
		// 是否下载完成
		boolean isComplete = false;
		HttpURLConnection urlConnection = null;
		InputStream in = null;
		FileOutputStream fos = null;
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
			String Lowerurl = downloadUrl.trim().toLowerCase();
			if(Lowerurl.contains("http")){
				URL url = new URL(downloadUrl);
				urlConnection = (HttpURLConnection) url.openConnection();
			}else if(Lowerurl.contains("https")){
				urlConnection = (HttpsURLConnection)new URL(downloadUrl).openConnection();
			}
			urlConnection.setRequestProperty("User-Agent", "PacificHttpClient");
			if (currentSize > 0) {
				urlConnection.setRequestProperty("RANGE", "bytes="
						+ currentSize + "-");
			}
			urlConnection.setConnectTimeout(100000);
			urlConnection.setReadTimeout(20000);
			updateTotalSize = urlConnection.getContentLength();
			if (urlConnection.getResponseCode() == 404) {
				throw new Exception("fail!");
			}
			in = urlConnection.getInputStream();
			fos = new FileOutputStream(saveFile, false);
			byte[] buffer = new byte[4096];
			int readSize = 0;
			while ((readSize = in.read(buffer)) > 0) {
				fos.write(buffer, 0, readSize);
				totalSize += readSize;
				if ((downloadCount == 0)
						|| (int) (totalSize * 100 / updateTotalSize) - 1 > downloadCount) {
					downloadCount += 1;
					m_Builder.setProgress(100,
							(int) (totalSize * 100 / updateTotalSize), false);
					m_Builder.setContentText((int) totalSize * 100
							/ updateTotalSize + "%");
					m_UpdateNotificationManager.notify(0, m_Builder.build());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (urlConnection != null) {
					urlConnection.disconnect();
				}
				if (in != null) {
					in.close();
				}
				if (fos != null) {
					fos.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (totalSize == updateTotalSize) {
			isComplete = true;
		}
		return isComplete;
	}

	private class MyHostnameVerifier implements HostnameVerifier {

		@Override
		public boolean verify(String hostname, SSLSession session) {
			// TODO Auto-generated method stub
			return true;
		}
	}

	private class MyTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(updateHandler != null){
			updateHandler.removeCallbacksAndMessages(null);
		}
	}
}
