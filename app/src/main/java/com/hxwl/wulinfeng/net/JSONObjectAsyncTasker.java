package com.hxwl.wulinfeng.net;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.LoginforCodeActivity;
import com.hxwl.wulinfeng.util.NetUrlUtils;

import java.io.Serializable;
import java.util.HashMap;

import static com.hxwl.wulinfeng.util.NetUrlUtils.isneed_relogin;

public class JSONObjectAsyncTasker extends
        AsyncTask<HashMap<String, Object>, Integer, ResultPacket> {
    private ExcuteJSONObjectUpdate action;
    private boolean isShowProgress;
    private Activity context;
    private ShowDialog showDialog;
    private String cacheKey;//userid+method+参数
    private View loadView = null;
    private View listView = null;

    public JSONObjectAsyncTasker(Activity activity, boolean isShowProgress,
                                 ExcuteJSONObjectUpdate paramExcuteJSONObjectUpdate) {
        this.context = activity;
        this.action = paramExcuteJSONObjectUpdate;
        this.isShowProgress = isShowProgress;
//		if(isShowProgress){
//			showDialog = new ShowDialog(context);
//		}
    }

    public JSONObjectAsyncTasker(Activity activity, boolean isShowProgress,
                                 ExcuteJSONObjectUpdate paramExcuteJSONObjectUpdate, String cacheKey) {
        this.context = activity;
        this.action = paramExcuteJSONObjectUpdate;
        this.isShowProgress = isShowProgress;
        this.cacheKey = cacheKey.replace("/", ".");
//		if(isShowProgress){
//			showDialog = new ShowDialog(context);
//		}
    }

    public JSONObjectAsyncTasker(Activity activity, boolean isShowProgress, View listView,
                                 ExcuteJSONObjectUpdate paramExcuteJSONObjectUpdate, String cacheKey) {
        this.context = activity;
        this.action = paramExcuteJSONObjectUpdate;
        this.isShowProgress = isShowProgress;
        this.cacheKey = cacheKey.replace("/", ".");
        this.listView = listView;
        loadView = View.inflate(activity, R.layout.progress_dialog2, null);
        ImageView imageView = (ImageView) loadView.findViewById(R.id.dialog_pg);
        Glide.with(activity).load(R.drawable.loadinggif).into(imageView);
        if (listView instanceof AbsListView) {
            ((ViewGroup) listView.getParent()).addView(loadView);
            ((AbsListView) listView).setEmptyView(loadView);
        } else if (listView instanceof RecyclerView) {

        } else {

        }
//		if(isShowProgress){
//			showDialog = new ShowDialog(context);
//		}
    }

    protected ResultPacket doInBackground(
            HashMap<String, Object>... paramVarArgs) {
        try {
            ResultPacket resultPacket = AppClient.okhttp_post_syn(context, paramVarArgs[0]);

            //缓存机制 -- 需求屏蔽
//			if(cacheKey!=null){
//				if(resultPacket.getStatus()!=null && resultPacket.getStatus().equals("ok")){
//					//保存对象
//					CacheManager.saveObject(context, resultPacket, cacheKey);
//				}
//			}
//			if(cacheKey!=null && resultPacket.getStatus().equals("error")){
//				boolean isExistDataCache = CacheManager.isExistDataCache(context, cacheKey);
//				if(isExistDataCache){//如果存在缓存，加载缓存
//					Serializable seri = CacheManager.readObject(context,
//							cacheKey);
//					return (ResultPacket) seri;
//				}
//			}
            //重新登录机制 -- 完善
            if (resultPacket.getStatus() != null && resultPacket.getStatus().equals("error") && resultPacket.getMsg() != null && resultPacket.getMsg().equals("relogin")) {
                if (paramVarArgs[0] != null || paramVarArgs[0].size() > 0) {
                    if (paramVarArgs[0].get("method") != null) {
                        if (paramVarArgs[0].get("method").equals(NetUrlUtils.wo_message_system) ||
                                paramVarArgs[0].get("method").equals(NetUrlUtils.wo_myfankui) || paramVarArgs[0].get("method").equals(NetUrlUtils.zuiXin_tuijianzhibo) ) {//不需要登录的几个relogin接口

                        } else if(paramVarArgs[0].get("method").equals(NetUrlUtils.isneed_relogin)){//判断是否需要重新登录
                            MakerApplication.instance().setLoginState(context ,MakerApplication.LOGOUT);
                            MakerApplication.instance().clearUserInfo();//清楚用户信息
                        }else {
                            resultPacket.setMsg("登陆已失效，请重新登陆");
                            if (AppManager.getAppManager().currentActivity() instanceof LoginforCodeActivity) {
                            } else {
                                Intent intent = new Intent(context, LoginforCodeActivity.class);
                                context.startActivity(intent);
                            }
                        }
                    }
                }
            }
            return resultPacket;
        } catch (Exception localException) {
            ResultPacket localResultPacket1 = new ResultPacket();
            localResultPacket1.setStatus("error");
            localResultPacket1.setMsg("访问失败，请返回重试");
            localException.printStackTrace();
            //缓存机制报错的时候读取缓存 -- 需求不要了
//			if(cacheKey!=null){
//				boolean isExistDataCache = CacheManager.isExistDataCache(context, cacheKey);
//				if(isExistDataCache){//如果存在缓存，加载缓存
//					Serializable seri = CacheManager.readObject(context,
//							cacheKey);
//					return (ResultPacket) seri;
//				}
//			}
            return localResultPacket1;
        }
    }

    protected void onCancelled() {
        super.onCancelled();
    }

    protected void onPostExecute(ResultPacket result) {
        if (result.getStatus() != null) {
            action.excute(result);
        } else {
//			if(isShowProgress){
//				ToastUtils.showToast(context, "获取信息异常，没有正常返回值");
//			}
//		}
//		if (isShowProgress) {
//			showDialog.dismissProgressDialog();
//		}
        }
    }

    protected void onPreExecute() {
//		if (isShowProgress) {
//			if (showDialog != null) {
//				showDialog.showProgressDialog ("提示", "请稍候...", true);
//			}
//		}
    }

    protected void onProgressUpdate(Integer... paramVarArgs) {
        super.onProgressUpdate(paramVarArgs);
    }

    public void setShowProgress(boolean isShowProgress) {
        this.isShowProgress = isShowProgress;
    }
}
