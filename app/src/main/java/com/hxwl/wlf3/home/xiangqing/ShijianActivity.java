package com.hxwl.wlf3.home.xiangqing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.home.home.zixun.ZixunVideoActivity;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.wlf3.bean3.Pinlin3Bean;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.UIUtils;
import com.jaeger.library.StatusBarUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

public class ShijianActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_line_view;
    private TextView chat_btn_create_card;
    private EditText chat_et_create_context;
    private RelativeLayout frame_msg_ll;
    private XRecyclerView shijian_recycler;
    private ArrayList<Pinlin3Bean.DataBean> list=new ArrayList<>();
    private ShijianAdapter adapter;
    public static Intent getIntent(Context context, String targetId) {
        Intent intent = new Intent(context, ShijianActivity.class);
        intent.putExtra("targetId", targetId);
        return intent;
    }

    private String targetId;
    private int page=1;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shijian);
        StatusBarUtil.setColor(ShijianActivity.this, getResources().getColor(R.color.tab_color), 0);
        targetId = getIntent().getStringExtra("targetId");
        initView();
        initData();

    }

    private void initData() {
        OkHttpUtils.post()
                .url(URLS.COMMENTLIST)
                .addParams("userId", MakerApplication.instance.getUid())
                .addParams("targetId",targetId)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("pageNumber",page+"")
                .addParams("pageSize","10")
                .addParams("type","1")
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
                                Pinlin3Bean plZixunListBean = gson.fromJson(response, Pinlin3Bean.class);
                                if (plZixunListBean.getCode().equals("1000")) {

                                    list.addAll(plZixunListBean.getData());
                                    adapter.notifyDataSetChanged();

                                }else if (plZixunListBean.getCode().equals("2002")||plZixunListBean.getCode().equals("2003")){
                                    UIUtils.showToast(plZixunListBean.getMessage());
                                    startActivity(LoginActivity.getIntent(ShijianActivity.this));
                                    ShijianActivity.this.finish();
                                } else {
                                    UIUtils.showToast("服务器异常");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }

    private void initView() {
        iv_line_view = (ImageView) findViewById(R.id.iv_line_view);
        chat_btn_create_card = (TextView) findViewById(R.id.chat_btn_create_card);
        chat_et_create_context = (EditText) findViewById(R.id.chat_et_create_context);
        frame_msg_ll = (RelativeLayout) findViewById(R.id.frame_msg_ll);
        shijian_recycler = (XRecyclerView) findViewById(R.id.shijian_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shijian_recycler.setLayoutManager(layoutManager);
        shijian_recycler.setNestedScrollingEnabled(false);

        adapter=new ShijianAdapter(this,list);
        shijian_recycler.setAdapter(adapter);
        
        chat_et_create_context.setOnClickListener(this);


        shijian_recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                list.clear();
                shijian_recycler.refresh();
            }

            @Override
            public void onLoadMore() {
                shijian_recycler.refresh();
            }
        });
        

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_et_create_context:

                break;
        }
    }

}
