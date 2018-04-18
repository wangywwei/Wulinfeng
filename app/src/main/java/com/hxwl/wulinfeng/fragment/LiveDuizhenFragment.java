package com.hxwl.wulinfeng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeDuizhengBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.LiveDuiZhenListAdapter;
import com.hxwl.wulinfeng.bean.DuizhenEntity;
import com.hxwl.wulinfeng.bean.DuizhenListBean;
import com.hxwl.wulinfeng.util.DateUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.hxwl.wulinfeng.view.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 直播模块的对阵
 */

public class LiveDuizhenFragment extends Fragment {
    LinearLayout iv_nodata;
    private View mduizhen ;
    private String currentSaichengId;
    private List<HomeDuizhengBean.DataBean> listData = new ArrayList<>();
    private DuizhenListBean duizhenBean;
    private List<DuizhenEntity> duizhenList;
    //开始时间
    private long startTime = 0;
    private TextView time;
    private LiveDuiZhenListAdapter liveDuiZhenAdapter;
    private TextView tv_empty;
    private MyListView lvDuizhen;
    private HomeDuizhengBean bean;

    public void setCurrentSaichengId(String currentSaichengId) {
        this.currentSaichengId = currentSaichengId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mduizhen == null) {
            mduizhen = inflater.inflate(R.layout.live_duizhen_layout, container, false);
            initView();
            initdata();
        } else {
            // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) mduizhen.getParent();
            if (parent != null) {
                parent.removeView(mduizhen);
            }
        }
        return mduizhen;

    }
    public void initView(){
        iv_nodata = (LinearLayout)mduizhen.findViewById(R.id.iv_nodata);
        lvDuizhen = (MyListView)mduizhen.findViewById(R.id.lv_duizhen);

        tv_empty = (TextView) mduizhen.findViewById(R.id.tv_empty);

        time = (TextView) mduizhen.findViewById(R.id.time);
        liveDuiZhenAdapter = new LiveDuiZhenListAdapter(getActivity(),listData);
        lvDuizhen.setAdapter(liveDuiZhenAdapter);
    }


    private void initdata() {
        if (StringUtils.isBlank(currentSaichengId)){
            return;
        }
        OkHttpUtils.post()
                .url(URLS.HOME_GETSCHEDULEAGAINSTLIST)
                .addParams("scheduleId",currentSaichengId)
                .addParams("token", MakerApplication.instance.getToken())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                bean = gson.fromJson(response, HomeDuizhengBean.class);

                                if (bean.getCode().equals("1000")){
                                    listData.clear();
                                    listData.addAll(bean.getData());
                                    liveDuiZhenAdapter.notifyDataSetChanged();
                                    time.setText(DateUtils.timet(bean.getData().get(0).getScheduleBeginTime()));
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(getActivity()));
                                    getActivity().finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

    }


}
