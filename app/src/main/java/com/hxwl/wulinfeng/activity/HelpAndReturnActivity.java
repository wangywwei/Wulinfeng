package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.ChangjianWentBean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.adapter.HelpAReturnAdapter;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Allen on 2017/6/20.
 * 帮助与反馈界面
 */
public class HelpAndReturnActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv_listview;
    private List<ChangjianWentBean.DataBean> listData = new ArrayList<>();
    private HelpAReturnAdapter adapter;
    private TextView tv_myfankuicount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpandreturn_activity);
        AppUtils.setTitle(HelpAndReturnActivity.this);
        initView();
        initHData();
        initData();
    }

    /**
     * 加载头部数据
     */
    private void initHData() {
        sp = getSharedPreferences("MSG", MODE_PRIVATE);
        int fankuicount = sp.getInt("fankuicount", 0);
        if (0 == fankuicount) {
            tv_myfankuicount.setVisibility(View.GONE);
            return;
        } else {
            tv_myfankuicount.setVisibility(View.VISIBLE);
            tv_myfankuicount.setText(fankuicount + "");
        }

    }

    private void initData() {
        if (!SystemHelper.isConnected(HelpAndReturnActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }

        OkHttpUtils.post()
                .url(URLS.HOTQUESTION_ALL)
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
                                ChangjianWentBean bean = gson.fromJson(response, ChangjianWentBean.class);
                                if (bean.getCode().equals("1000")) {
                                    listData.clear();
                                    listData.addAll(bean.getData());
                                    adapter.notifyDataSetChanged();

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(HelpAndReturnActivity.this));
                                    HelpAndReturnActivity.this.finish();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initView() {
        ImageView user_icon = (ImageView) findViewById(R.id.user_icon);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageView iv_myfankui = (ImageView) findViewById(R.id.iv_myfankui);
        iv_myfankui.setOnClickListener(this);
        tv_myfankuicount = (TextView) findViewById(R.id.tv_myfankuicount);

        ImageView iv_myyijian = (ImageView) findViewById(R.id.iv_myyijian);
        iv_myyijian.setOnClickListener(this);


        lv_listview = (ListView) findViewById(R.id.lv_listview);
        adapter = new HelpAReturnAdapter(HelpAndReturnActivity.this, listData);
        lv_listview.setAdapter(adapter);
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChangjianWentBean.DataBean bean = null;
                if (view instanceof TextView) {
                    bean = (ChangjianWentBean.DataBean) view.getTag();
                } else {
                    bean = (ChangjianWentBean.DataBean) view.findViewById(R.id.tv_title).getTag();
                }
                if (bean == null) {
                    return;
                }
                Intent intent = new Intent(HelpAndReturnActivity.this, ChangJianQuestActivity.class);
                intent.putExtra("id", bean.getId());
                intent.putExtra("title", bean.getUrl());
                startActivity(intent);
            }
        });
    }

    private SharedPreferences sp;

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_myfankui:
                StatService.onEvent(HelpAndReturnActivity.this, "MyFeedback", "我的反馈", 1);
                TCAgent.onEvent(HelpAndReturnActivity.this, "MyFeedback", "我的反馈");
                sp.edit().putInt("fankuicount", 0).commit();//覆盖我的数据
                sp.edit().putString("fankuimsg", "").commit();//覆盖我的数据
                intent = new Intent(HelpAndReturnActivity.this, MyFanKuiActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_myyijian:
                intent = new Intent(HelpAndReturnActivity.this, SubmitProposalActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "帮助与反馈");
        TCAgent.onPageStart(this, "帮助与反馈");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "帮助与反馈");
        TCAgent.onPageEnd(this, "帮助与反馈");
    }
}
