package com.hxwl.wulinfeng.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;


/**
 * 功能：fragment的程序顶层父类，不允许修改 zjn 暂时没用到
 */
@SuppressLint("NewApi")
public class BaseFragment extends Fragment {

    protected View m_View;
    private AlertDialog m_AlertDialog;
    private boolean isRefresh = false ;
    //记录首页是不是要刷新
    public void setIsRefresh(boolean b){
        isRefresh = b ;
    }
    public boolean getIsRefresh(){
        return isRefresh ;
    }

    /**
     * 过滤赛事
     */
    protected String type = "" ;

    public void setType(String type){
        this.type = type ;
    }

    public String getType(){
        return type ;
    }

    /**
     * 搜索关键字
     */
    private String keyword = "" ;

    public void setKeyword(String keyword){
        this.keyword = keyword ;
    }

    public String getKeyword(){
        return keyword ;
    }

    /**
     * 判断本地网络是否连接
     *
     * @param paramContext
     * @return
     */
    public static boolean isConnect(Context paramContext) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) MakerApplication.instance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (localConnectivityManager != null) {
            NetworkInfo localNetworkInfo = localConnectivityManager
                    .getActiveNetworkInfo();
            if ((localNetworkInfo != null)
                    && (localNetworkInfo.isConnected())
                    && (localNetworkInfo.getState() == NetworkInfo.State.CONNECTED))
                return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
//        TCAgent.onPageStart(getActivity(),getActivity().getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
//        TCAgent.onPageEnd(getActivity(),getActivity().getClass().getSimpleName());
    }

    /**
     * 取消对话框
     */
    public void cancelAlertDialog() {
        if ((this.m_AlertDialog != null) && (this.m_AlertDialog.isShowing())) {
            this.m_AlertDialog.dismiss();
            this.m_AlertDialog = null;
        }
    }

    Toast m_toast;

    /**
     * 创建toast
     *
     * @param paramString
     */
    public void createToast(String paramString) {
        // TODO Auto-generated method stub
        if (m_toast != null) {
            m_toast.cancel();
            m_toast = null;
        }
        m_toast = Toast.makeText(MakerApplication.instance(),
                paramString, Toast.LENGTH_SHORT);
        View localView = LayoutInflater.from(MakerApplication.instance())
                .inflate(R.layout.toast, null);
        ((TextView) localView.findViewById(R.id.tv_toast))
                .setText(paramString);
        m_toast.setView(localView);
        m_toast.setGravity(81, 0, 100);
        m_toast.show();
//		Toast.makeText(TheApplication.instance, paramString, Toast.LENGTH_SHORT).show();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (this.m_View != null) {
            ViewGroup localViewGroup = (ViewGroup) this.m_View.getParent();
            if (localViewGroup != null) {
                localViewGroup.removeView(this.m_View);
            }
            return this.m_View;
        }
        return null;
    }

}
