package com.hxwl.wulinfeng.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.hxwl.common.utils.ShowDialog2;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.LoginBean;
import com.hxwl.newwlf.modlebean.WXUserInfoBean;
import com.hxwl.newwlf.modlebean.WeiXingBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.activity.HomeActivity;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


/** 微信客户端回调activity示例 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private static final int RETURN_MSG_TYPE_LOGIN = 1;
	private static final int RETURN_MSG_TYPE_SHARE = 2;
	private static final String TAG ="TAG" ;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//如果没回调onResp，八成是这句没有写
		MakerApplication.api.handleIntent(getIntent(), this);
	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req) {
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	//app发送消息给微信，处理返回消息的回调
	@Override
	public void onResp(BaseResp resp) {
		Log.e("TAG", "onResp: "+resp.errCode + "" );
		switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				if (RETURN_MSG_TYPE_SHARE == resp.getType()) UIUtils.showToast("分享失败");
				else UIUtils.showToast("登录失败");
				ShowDialog2.dismissProgressDialog();
				break;
			case BaseResp.ErrCode.ERR_OK:
				switch (resp.getType()) {
					case RETURN_MSG_TYPE_LOGIN:
						//拿到了微信返回的code,立马再去请求access_token
						final String code = ((SendAuth.Resp) resp).code;
						Log.e("TAG", "code = " + code );
						//就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
						getAccessToken(code);
						break;

					case RETURN_MSG_TYPE_SHARE:
						UIUtils.showToast("微信分享成功");
						finish();
						break;
				}
				break;
		}
		ShowDialog2.dismissProgressDialog();
	}

	/**
	 * 获取access_token：
	 *
	 * @param code 用户或取access_token的code，仅在ErrCode为0时有效
	 */
	private void getAccessToken(final String code) {
		Map<String, String> params = new HashMap();
		params.put("appid", Constants.APP_ID);
		params.put("secret", Constants.APPSECRET);
		params.put("code", code);
		params.put("grant_type", "authorization_code");
		OkHttpUtils.get()
				.url(URLS.URL_WX_BASE)
				.params(params)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {
						UIUtils.showToast("登录失败");
						ShowDialog2.dismissProgressDialog();
						Log.e(TAG, "onError: " +e.toString());
					}

					@Override
					public void onResponse(String response, int id) {
						Log.e(TAG, "onResponse: " );
						JsonValidator jsonValidator = new JsonValidator();
						if (jsonValidator.validate(response)) {
							Gson gson = new Gson();
							try {
								WeiXingBean bean = gson.fromJson(response, WeiXingBean.class);
								if (StringUtils.isBlank(bean.getErrcode())){
									getWXUserInfo(bean.getAccess_token(),bean.getOpenid(),bean.getUnionid());
								}else {
									UIUtils.showToast("登录失败");
									ShowDialog2.dismissProgressDialog();
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
		ShowDialog2.dismissProgressDialog();
	}

	/**
	 * 获取微信登录，用户授权后的个人信息
	 *
	 * @param access_token
	 * @param openid
	 * @param unionid
	 */
	private void getWXUserInfo(final String access_token, final String openid, final String unionid) {
		Map<String, String> params = new HashMap();
		params.put("access_token", access_token);
		params.put("openid", openid);
		OkHttpUtils.get()
				.url(URLS.URL_USERINFO)
				.params(params)
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {
						Log.e(TAG, "onError: " );
						UIUtils.showToast("登录失败");
						ShowDialog2.dismissProgressDialog();
					}

					@Override
					public void onResponse(String response, int id) {
						JsonValidator jsonValidator = new JsonValidator();
						if (jsonValidator.validate(response)) {
							Gson gson = new Gson();
							try {
								WXUserInfoBean bean = gson.fromJson(response, WXUserInfoBean.class);
								if (!StringUtils.isBlank(bean.getNickname())){
//									String country = bean.getCountry(); //国家
//									String province = bean.getProvince(); //省
//									String city = bean.getCity(); //市
//									int sex = bean.getSex(); //性别
									String nickname = bean.getNickname(); //用户名
									String headimgurl = bean.getHeadimgurl(); //头像url

									if (MakerApplication.instance.getLoginState().equals("login")){
										weixingbangding(unionid,nickname,headimgurl);
									}else {
										weixinglonging(unionid,nickname,headimgurl);
									}


								}else {
									UIUtils.showToast("错误");
									ShowDialog2.dismissProgressDialog();
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
		ShowDialog2.dismissProgressDialog();
	}

	private void weixingbangding(String unionid, final String nickname, String headimgurl) {
		try {
			OkHttpUtils.post()
					.url(URLS.USER_UPDATE)
					.addParams("id", MakerApplication.instance.getUid())
					.addParams("weixinUnionId", unionid)
					.addParams("token", MakerApplication.instance.getToken())
					.addParams("nickName", nickname)
					.addParams("headImg", headimgurl)
					.build()
					.execute(new StringCallback() {
						@Override
						public void onError(Call call, Exception e, int id) {
							UIUtils.showToast("绑定失败");
							ShowDialog2.dismissProgressDialog();
						}

						@Override
						public void onResponse(String response, int id) {
							JsonValidator jsonValidator = new JsonValidator();
							if (jsonValidator.validate(response)){
								Gson gson = new Gson();
								try {
									LoginBean bean = gson.fromJson(response, LoginBean.class);
									if (bean.getCode().equals("1000")){
										ShowDialog2.dismissProgressDialog();
										Log.e(TAG, "onResponse: 登录成功" );
										MakerApplication.instance.saveLogin(bean.getData(),WXEntryActivity.this);
										MakerApplication.instance.setLoginState(WXEntryActivity.this ,MakerApplication.instance.LOGIN);
										MakerApplication.instance.setLoginWX(WXEntryActivity.this ,nickname);
										MakerApplication.instance.setPassWord("0");


										UIUtils.showToast("绑定成功");
									}else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
										UIUtils.showToast(bean.getMessage());
										startActivity(LoginActivity.getIntent(WXEntryActivity.this));
										WXEntryActivity.this.finish();
									}else{
										ToastUtils.showToast(WXEntryActivity.this,"绑定失败");
									}

								}catch (Exception e){
									e.printStackTrace();
								}

							}

							ShowDialog2.dismissProgressDialog();

						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void weixinglonging(String unionid, final String nickname, final String headimgurl) {
		try {
			OkHttpUtils.post()
					.url(URLS.USER_LOGIN)
					.addParams("token", MakerApplication.instance.getToken())
					.addParams("deviceType","1")
					.addParams("pushToken",MakerApplication.instance.getPushToken())
					.addParams("currVersion", LoginActivity.getVersion(WXEntryActivity.this)+"")
					.addParams("weixinUnionId", unionid)
					.addParams("nickName", nickname)
					.addParams("headImg", headimgurl)
					.build()
					.execute(new StringCallback() {
						@Override
						public void onError(Call call, Exception e, int id) {
							UIUtils.showToast("登录失败");
							ShowDialog2.dismissProgressDialog();
						}

						@Override
						public void onResponse(String response, int id) {
							JsonValidator jsonValidator = new JsonValidator();
							if (jsonValidator.validate(response)){
								Gson gson = new Gson();
								try {
									LoginBean bean = gson.fromJson(response, LoginBean.class);
									if (bean.getCode().equals("1000")){
										ShowDialog2.dismissProgressDialog();
										Log.e(TAG, "onResponse: 登录成功" );
										MakerApplication.instance.saveLogin(bean.getData(),WXEntryActivity.this);
										MakerApplication.instance.setLoginState(WXEntryActivity.this ,MakerApplication.instance.LOGIN);
										MakerApplication.instance.setLoginWX(WXEntryActivity.this ,nickname);
										MakerApplication.instance.setPassWord("0");

										//跳转到主页
										Intent intent = new Intent(WXEntryActivity.this,HomeActivity.class) ;
										startActivity(intent);
										UIUtils.showToast("登录成功");
									}else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
										UIUtils.showToast(bean.getMessage());
										startActivity(LoginActivity.getIntent(WXEntryActivity.this));
										WXEntryActivity.this.finish();
									}else{
										ToastUtils.showToast(WXEntryActivity.this,bean.getMessage());
									}

								}catch (Exception e){
									e.printStackTrace();
								}

							}

							ShowDialog2.dismissProgressDialog();

						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}
