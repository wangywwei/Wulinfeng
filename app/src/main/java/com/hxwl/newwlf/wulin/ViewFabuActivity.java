package com.hxwl.newwlf.wulin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.hxwl.common.tencentplay.utils.TCConstants;
import com.hxwl.common.tencentplay.widget.VideoWorkProgressFragment;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.WLF;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.HuatiListBean;
import com.hxwl.newwlf.modlebean.TOkenBean;
import com.hxwl.newwlf.wulin.arts.HuatiRecyclerAdapter;
import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.activity.HomeActivity;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.library.flowlayout.FlowLayoutManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.lang.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class ViewFabuActivity extends BaseActivity implements View.OnClickListener {
    private ProgressDialog dialog;
    private TextView tv_cancel;
    private TextView tv_commit;
    private RelativeLayout rl_title;
    private EditText edt_input_content;
    private TextView tv_textcount;
    private LinearLayout rlyt_center;
    private ImageView iv_thumbnail;
    private ProgressBar head_progressBar;
    private RelativeLayout rlyt_fabu2;
    private String accessKeySecret;
    private String accessKeyId;
    private String securityToken;
    private String userId;
    private String loginKey;
    private String username;
    public static String videoPath;
    public static String strThumbnailPath;
    private PopupWindow popupWindow;
    private String huatiID;//话题ID
    private String input;
    private String IMgFile;
    private TextView huatiatext;
    private ImageView huatiaimg2;
    private TextView huatiatext2;
    private LinearLayout addhuati2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_share2);
        AppUtils.setTitle(ViewFabuActivity.this);
        AppManager.getAppManager().addActivity(this);
        userId = (String) SPUtils.get(this, Constants.USER_ID, "-1");
        loginKey = (String) SPUtils.get(this, Constants.USER_LOGIN_KEY, "-1");
        username = (String) SPUtils.get(this, Constants.USER_Name, "-1");
        videoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
        strThumbnailPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);


        initView();
        initWorkProgressPopWin();

    }
    private String textContext = "";//editText的内容
    private void initView() {
        huatiatext = (TextView) findViewById(R.id.huatiatext);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        edt_input_content = (EditText) findViewById(R.id.edt_input_content);
        tv_textcount = (TextView) findViewById(R.id.tv_textcount);
        rlyt_center = (LinearLayout) findViewById(R.id.rlyt_center);
        iv_thumbnail = (ImageView) findViewById(R.id.iv_thumbnail);
        head_progressBar = (ProgressBar) findViewById(R.id.head_progressBar);

        rlyt_fabu2 = (RelativeLayout) findViewById(R.id.rlyt_fabu2);
        addhuati2= (LinearLayout) findViewById(R.id.addhuati2);
        huatiaimg2= (ImageView)findViewById(R.id.huatiaimg2);
        huatiatext2 = (TextView) findViewById(R.id.huatiatext2);
        addhuati2.setVisibility(View.GONE);

        huatiaimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlyt_center.setVisibility(View.VISIBLE);
                addhuati2.setVisibility(View.GONE);
            }
        });
        tv_cancel.setOnClickListener(this);
        tv_commit.setOnClickListener(this);
        rlyt_center.setOnClickListener(this);

        edt_input_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textContext = charSequence.toString();
                int len = textContext.length();
                if (len > 120) {
                    len = 120;
                }
                tv_textcount.setText(len + " /120");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Bitmap bm = BitmapUtils.getBitmapByPath(strThumbnailPath);
        if (bm == null) {
            iv_thumbnail.setImageResource(R.drawable.wulinnull_icon);
        } else {
            iv_thumbnail.setImageBitmap(bm);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_commit:
                showDialog(ViewFabuActivity.this, "正在上传请稍后", false);
                sendVideo();
                break;
            case R.id.rlyt_center:
                addPopo();
                break;
        }
    }

    public void showDialog(Context context, String title,
                           boolean canCancel) {
        dialog = new ProgressDialog(context);
        dialog.setCancelable(canCancel);
        dialog.setMessage(title);
        dialog.show();
    }
    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private HuatiRecyclerAdapter huatiRecyclerAdapter;
    private TranslateAnimation animation;
    private ArrayList<HuatiListBean.DataBean> list=new ArrayList<>();
    private void addPopo() {
        View view = getLayoutInflater().inflate(R.layout.addpopo, null);
        popupWindow = new PopupWindow(view);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setOutsideTouchable(true);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(200);
        popupWindow.showAtLocation(rlyt_center, Gravity.BOTTOM, 0, 0);
        view.startAnimation(animation);

        addpohuati(view);

    }

    private void addpohuati(View view) {
        ImageView add_guan= (ImageView) view.findViewById(R.id.add_guan);
        RecyclerView addrecyclerview= (RecyclerView) view.findViewById(R.id.addrecyclerview);
        add_guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        addrecyclerview.setLayoutManager(flowLayoutManager);
        huatiRecyclerAdapter = new HuatiRecyclerAdapter(list,this);
        addrecyclerview.setAdapter(huatiRecyclerAdapter);
        huatiRecyclerAdapter.setOnItemclickLinter(new HuatiRecyclerAdapter.OnItemclickLinter() {
            @Override
            public void onItemClicj(int position) {
                huatiID=list.get(position).getId()+"";
                huatiatext2.setText(list.get(position).getTopicName());
                rlyt_center.setVisibility(View.GONE);
                addhuati2.setVisibility(View.VISIBLE);
                popupWindow.dismiss();


            }
        });
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_TOPICLIST)
                .addParams("pageNumber","1")
                .addParams("pageSize","10")
                .addParams("more","more")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        UIUtils.showToast("服务器异常");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                HuatiListBean bean = gson.fromJson(response, HuatiListBean.class);
                                if (bean.getCode().equals("1000")){
                                    list.clear();
                                    list.addAll(bean.getData());
                                    huatiRecyclerAdapter.notifyDataSetChanged();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });





    }


    //发布视频
    private void sendVideo() {
        input = edt_input_content.getText().toString();
//        if(StringUtils.isEmpty(input)){
//            ToastUtils.showToast(ViewFabuActivity.this,"请为视频加上一个有趣的标题");
//
//            return;
//        }
        if (!SystemHelper.isConnected(this)) {
            UIUtils.showToast("请检查网络");
            return;
        }

        OkHttpUtils.post()
                .url(URLS.SCHEDULE_GETOSSTOKEN)
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
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                TOkenBean bean = gson.fromJson(response, TOkenBean.class);
                                if (bean.getCode().equals("1000")){
                                    accessKeySecret = bean.getData().getAccessKeySecret();
                                    accessKeyId = bean.getData().getAccessKeyId();
                                    securityToken = bean.getData().getSecurityToken();

                                    initImg();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    private void initImg() {

            if (StringUtils.isBlank(accessKeyId)&&StringUtils.isBlank(videoPath)){
                return;
            }

            String endpoint = "oss-cn-beijing.aliyuncs.com";
            // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考访问控制章节
            // 也可查看sample 中 sts 使用方式了解更多(https://github.com/aliyun/aliyun-oss-android-sdk/tree/master/app/src/main/java/com/alibaba/sdk/android/oss/app)
            OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(
                    accessKeyId,
                    accessKeySecret,
                    securityToken);
            //该配置类如果不设置，会有默认配置，具体可看该类
            ClientConfiguration conf = new ClientConfiguration();
            conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
            conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
            conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
            conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
            OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);

            String originalFilename=".jpg";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            //重命名文件名称
            String fileName = sdf.format(new Date());
            //随机5位数
            String random  = RandomStringUtils.randomNumeric(5);
            if (originalFilename.lastIndexOf(".") != -1){
                fileName = fileName+random+originalFilename.substring(originalFilename.lastIndexOf("."));
            }
             Log.e("TAG", "mages:"+fileName);
            PutObjectRequest put = new PutObjectRequest(WLF.HEIXIONGWLF, "images/"+fileName,strThumbnailPath );
            // 异步上传时可以设置进度回调
            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.e("TAG--PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);

                }
            });
        IMgFile = fileName;
            OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    Log.e("TAG--PutObject", "UploadSuccess");
                    Log.e("TAG--ETag", result.getETag());
                    Log.e("TAG--RequestId", result.getRequestId());
                    initVideo();

                }
                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    // 请求异常
                    UIUtils.showToast("服务器异常");
                    if (clientExcepion != null) {
                        // 本地异常如网络异常等
                        clientExcepion.printStackTrace();
                    }
                    if (serviceException != null) {
                        // 服务异常
                        Log.e("TAG--ErrorCode", serviceException.getErrorCode());
                        Log.e("TAG--RequestId", serviceException.getRequestId());
                        Log.e("TAG--HostId", serviceException.getHostId());
                        Log.e("TAG--RawMessage", serviceException.getRawMessage());
                    }
                }
            });


    }


    private void initVideo() {
        if (StringUtils.isBlank(accessKeyId)&&StringUtils.isBlank(videoPath)){
            return;
        }

        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考访问控制章节
        // 也可查看sample 中 sts 使用方式了解更多(https://github.com/aliyun/aliyun-oss-android-sdk/tree/master/app/src/main/java/com/alibaba/sdk/android/oss/app)
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(
                accessKeyId,
                accessKeySecret,
                securityToken);
        //该配置类如果不设置，会有默认配置，具体可看该类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);

        String originalFilename=videoPath.substring(videoPath.indexOf("."));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        //重命名文件名称
        String fileName = sdf.format(new Date());
        //随机5位数
        String random  = RandomStringUtils.randomNumeric(5);
        if (originalFilename.lastIndexOf(".") != -1){
            fileName = fileName+random+originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        Log.e("TAG", "videos:"+fileName);
        PutObjectRequest put = new PutObjectRequest(WLF.HEIXIONGWLF, "videos/"+fileName, videoPath);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.e("TAG--PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);

            }
        });
        final String finalFileName = fileName;
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.e("TAG--PutObject", "UploadSuccess");
                Log.e("TAG--ETag", result.getETag());
                Log.e("TAG--RequestId", result.getRequestId());
                Map<String,String> map=new HashMap<>();
                map.put("userId",SPUtils.get(ViewFabuActivity.this,"id","-1")+"");
                map.put("content",input);
                if (!StringUtils.isBlank(huatiID)){
                    map.put("topicId",huatiID);
                }
                map.put("videoName",finalFileName);
                map.put("videoPreviewImage",IMgFile);
                map.put("token", MakerApplication.instance.getToken());
                OkHttpUtils.post()
                        .url(URLS.SCHEDULE_PUBLISHVIDEOTEXT)
                        .params(map)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                UIUtils.showToast("服务器异常");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                JsonValidator jsonValidator = new JsonValidator();
                                if (jsonValidator.validate(response)){
                                    Gson gson = new Gson();
                                    try {
                                        HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                        dismissDialog();
                                        if (bean.getCode().equals("1000")){
                                            UIUtils.showToast(bean.getMessage());
                                            //将自己关闭
                                            Intent intent=new Intent(ViewFabuActivity.this,HomeActivity.class);
                                            intent.putExtra("signTab","wulin");
                                            startActivity(intent);
                                        }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                            UIUtils.showToast(bean.getMessage());
                                            startActivity(LoginActivity.getIntent(ViewFabuActivity.this));
                                            ViewFabuActivity.this.finish();
                                        }else {
                                            UIUtils.showToast(bean.getMessage());
                                        }



                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }

                            }
                        });

            }
            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                dismissDialog();
                UIUtils.showToast("服务器异常");
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("TAG--ErrorCode", serviceException.getErrorCode());
                    Log.e("TAG--RequestId", serviceException.getRequestId());
                    Log.e("TAG--HostId", serviceException.getHostId());
                    Log.e("TAG--RawMessage", serviceException.getRawMessage());
                }
            }
        });




    }

    private VideoWorkProgressFragment mWorkProgressDialog;
    private final int STATE_NONE = 0;
    private int mCurrentState = STATE_NONE;
    private void initWorkProgressPopWin() {
        if (mWorkProgressDialog == null) {
            mWorkProgressDialog = new VideoWorkProgressFragment();
            mWorkProgressDialog.setOnClickStopListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWorkProgressDialog.dismiss();
                    Toast.makeText(ViewFabuActivity.this, "取消视频生成", Toast.LENGTH_SHORT).show();
                    mWorkProgressDialog.setProgress(0);
                    mCurrentState = STATE_NONE;
                }
            });
        }
        mWorkProgressDialog.setProgress(0);
    }
}
