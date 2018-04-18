package com.hxwl.common.utils;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hxwl.wulinfeng.R;


/**
 * 显示加载框
 * @author Allen
 *
 */
public class ShowDialog2 extends Activity{
	public static Dialog progressDialog;
	public static TextView dialogMyTextView;

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
		public static void showProgressDialog(Context context,final String title, final String msg,
											  final boolean cancelable) {
			progressDialog = new Dialog(context, R.style.Dialog);
			View dialogView = LayoutInflater.from(context).inflate(
					R.layout.progress_dialog, null);
			progressDialog.setContentView(dialogView);
			dialogMyTextView = (TextView) dialogView
					.findViewById(R.id.progress_dialog_msgTextView);

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

	public static void showProgressDialog(Context context,final String title, final String msg,
				final boolean cancelable, boolean isShow) {
		progressDialog = new Dialog(context, R.style.Dialog);
		View dialogView = LayoutInflater.from(context).inflate(
				R.layout.progress_dialog, null);
		progressDialog.setContentView(dialogView);
		dialogMyTextView = (TextView) dialogView
				.findViewById(R.id.progress_dialog_msgTextView);

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

	public static void dismissProgressDialog() {
			if (ShowDialog2.progressDialog!=null){
				ShowDialog2.progressDialog.dismiss();
			}
		}
}
