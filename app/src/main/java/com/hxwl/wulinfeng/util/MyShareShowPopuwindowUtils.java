package com.hxwl.wulinfeng.util;

import android.app.Activity;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.hxwl.wulinfeng.AppContext;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.tendcloud.tenddata.TCAgent;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.sina.weibo.SinaWeibo.ShareParams;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class MyShareShowPopuwindowUtils {
	/**
	 * 
	 * @param context
	 * @param sharetype
	 * @param title
	 */
	public static void ShowPopuWin(final Activity context ,final String id ,final String sharetype
			 ,final String title  ,final String imageUrl){
		StatService.onEvent(context,"share","分享",1);
		TCAgent.onEvent(context,"share","分享");
//		ShareSDK.initSDK(context);
		final String url ;
		if ("zhibo".equals(sharetype)) {//直播 saicheng_id
			url = NetUrlUtils.share_zhibo + "saicheng_id/" + id ;
		}else if ("zixun".equals(sharetype)) {//其他 id
			url = NetUrlUtils.share_zixun + "id/" + id ;
		}else if("tuji".equals(sharetype)){
			url = NetUrlUtils.share_tuji + "id/" + id ;
		}else if("shipin".equals(sharetype)){
			url = NetUrlUtils.share_shipin + "id/" + id ;
		}else{
			ToastUtils.showToast(context,"未识别类型");
			return ;
		}
		/**
		 * 测量控件大小
		 */
		View view = View.inflate(context, R.layout.popuwindow_sharesdk,
				null);
		final PopupWindow pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// 获取点击view的坐标
		pop.showAsDropDown(view);
		// 透明度渐变
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(300);
		TextView cancle = (TextView) view.findViewById(R.id.cancle);
		cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss() ;
			}
		});

		//复制链接
		ImageView iv_copy = (ImageView) view.findViewById(R.id.iv_copy);
		iv_copy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StatService.onEvent(context,"CopyLink","复制链接",1);
				TCAgent.onEvent(context,"CopyLink","复制链接");
				 ClipboardManager c= (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
		    	  c.setText(url);
				ToastUtils.showToast(context, "复制到剪切板~");
				pop.dismiss();
			}
		});
		//微信好友
		ImageView iv_weixin = (ImageView) view.findViewById(R.id.iv_weixin);
		iv_weixin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StatService.onEvent(context,"FriendsShare","微信朋友分享",1);
				TCAgent.onEvent(context,"FriendsShare","微信朋友分享");
				 final Platform weixin = ShareSDK.getPlatform(context, Wechat.NAME);
			        Wechat.ShareParams sp = new Wechat.ShareParams();
			        sp.setShareType(Platform.SHARE_WEBPAGE);
			        sp.setUrl(url);
			        sp.setText("您的好友"+ MakerApplication.instance().getUsername()+"分享给你一条数据");
			        sp.setTitle(title);
			        sp.setImageUrl(imageUrl);
			        weixin.share(sp);
			}
		});
		//朋友圈
		ImageView iv_pengyou = (ImageView) view.findViewById(R.id.iv_pengyou);
		iv_pengyou.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StatService.onEvent(context,"MomentsShare","朋友圈分享",1);
				TCAgent.onEvent(context,"MomentsShare","朋友圈分享");
				final Platform pengyou = ShareSDK.getPlatform(context, WechatMoments.NAME);
				WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
		        sp.setShareType(Platform.SHARE_WEBPAGE);
		        sp.setUrl(url);
		        sp.setText("您的好友"+AppContext.instance.getUsername());
		        sp.setTitle(title);
		        sp.setImageUrl(imageUrl);
		        pengyou.share(sp);
			}
		});
		//手机qq
		ImageView iv_qq = (ImageView) view.findViewById(R.id.iv_qq);
		iv_qq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StatService.onEvent(context,"QQShare","QQ分享",1);
				TCAgent.onEvent(context,"QQShare","QQ分享");
				Platform qq = ShareSDK.getPlatform(context, QQ.NAME);
		         QQ.ShareParams sp = new QQ.ShareParams();
		         sp.setTitle(title);
		         sp.setTitleUrl(url);
		         sp.setText("您的好友"+AppContext.instance.getUsername()+"分享给你一条数据");
		         sp.setImageUrl(imageUrl);
		         qq.share(sp);
			}
		});
		//qq空间
		ImageView iv_qqzone = (ImageView) view.findViewById(R.id.iv_qqzone);
		iv_qqzone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StatService.onEvent(context,"QzoneShare","QQ空间分享",1);
				TCAgent.onEvent(context,"QzoneShare","QQ空间分享");
				ShareParams sp = new ShareParams();
				sp.setTitle(title);
				sp.setTitleUrl(url); // 标题的超链接
				sp.setText("您的好友"+AppContext.instance.getUsername()+"共享给你一条数据");
				sp.setImageUrl(imageUrl);
				sp.setSite("武林风");
				sp.setSiteUrl(url);
				Platform qzone = ShareSDK.getPlatform (QZone.NAME);
//				qzone. setPlatformActionListener (paListener); // 设置分享事件回调
				// 执行图文分享
				qzone.share(sp);
			}
		});
		//新浪微博 
		ImageView iv_weibo = (ImageView) view.findViewById(R.id.iv_weibo);
		iv_weibo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StatService.onEvent(context,"WeiboShare","微博分享",1);
				TCAgent.onEvent(context,"WeiboShare","微博分享");
				ShareParams sp = new ShareParams();
				sp.setText(url);
				sp.setImagePath(imageUrl);
				Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//				weibo.setPlatformActionListener(null);
				// 执行图文分享
				weibo.share(sp);
			}
		});
		// 大小渐变
		ScaleAnimation sa = new ScaleAnimation(0.3f, 1, 0.3f, 1,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(500);

		AnimationSet as = new AnimationSet(true);
		as.addAnimation(aa);
		as.addAnimation(sa);
		view.startAnimation(as);
	}
}
