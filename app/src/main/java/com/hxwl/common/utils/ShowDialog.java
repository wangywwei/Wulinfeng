package com.hxwl.common.utils;
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;


/**
 * 显示加载框
 * @author Allen
 *
 */
public class ShowDialog extends Activity{
	private  Activity context;
	public ShowDialog(){

	}
	public ShowDialog(Activity context){
		this.context = context;
		progressDialog = new Dialog(context, R.style.Dialog);
		View dialogView = LayoutInflater.from(context).inflate(
				R.layout.progress_dialog, null);
		progressDialog.setContentView(dialogView);
		dialogMyTextView = (TextView) dialogView
				.findViewById(R.id.progress_dialog_msgTextView);
	}
	// 界面提示对话框
		protected   Dialog progressDialog;
		protected   TextView dialogMyTextView;

		// 显示
		/**
		 * 显示�?��ProgressDialog
		 * 
		 * @param title
		 *            标题
		 * @param msg
		 *            内容
		 * @param cancelable
		 *            是否可以取消
		 */
		public void showProgressDialog(final String title, final String msg,
				final boolean cancelable) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					progressDialog.setCancelable(cancelable);
					dialogMyTextView.setText(msg);
					if (title != null) {
						progressDialog.setTitle(title);
					}
					try {
						progressDialog.show();
					} catch (Exception e) {
					}
					
				}
			});
		}

		public void showProgressDialog(final String title, final String msg,
				final boolean cancelable, boolean isShow) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					dialogMyTextView.setText(msg);
					progressDialog.setCancelable(cancelable);
					if (title != null) {
						progressDialog.setTitle(title);
					}
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
						progressDialog.show();
					} else {
						progressDialog.show();
					}
				}
			});
		}

		public void dismissProgressDialog() {
			if (progressDialog.isShowing()) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							progressDialog.dismiss();
						} catch (Exception e) {
						}
						
					}
				});
			}
		}
}
