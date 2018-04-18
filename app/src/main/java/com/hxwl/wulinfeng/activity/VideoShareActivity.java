package com.hxwl.wulinfeng.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
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
import com.hxwl.common.tencentplay.widget.PictureDialog;
import com.hxwl.common.tencentplay.widget.VideoWorkProgressFragment;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.WLF;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HuatiListBean;
import com.hxwl.newwlf.modlebean.TOkenBean;
import com.hxwl.newwlf.wulin.arts.HuatiRecyclerAdapter;
import com.hxwl.wulinfeng.AppManager;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.net.ExcuteJSONObjectUpdate;
import com.hxwl.wulinfeng.net.JSONObjectAsyncTasker;
import com.hxwl.wulinfeng.net.ResultPacket;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.NetUrlUtils;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.ToastUtils;
import com.hxwl.wulinfeng.util.UIUtils;
import com.library.flowlayout.FlowLayoutManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.lang.RandomStringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 功能:视频分享页面—— 发布视频
 * 作者：zjn on 2017/6/15 16:43
 */

public class VideoShareActivity extends BaseActivity {
    private final int STATE_NONE = 0;
    private final int STATE_RESUME = 1;
    private final int STATE_PAUSE = 2;
    private final int STATE_CUT = 3;
    private String accessKeySecret;
    private String accessKeyId;
    private String securityToken;
    private DecimalFormat mDecimalFormat = new DecimalFormat("###");
    private TextView tv_cancel;
    private ImageView iv_thumbnail;
    private EditText edt_input_content;
    public static String videoPath;
    private String userId;
    private String loginKey;
    //缩略图路径
    public static String strThumbnailPath;
    private TextView tv_commit;
    //分享平台的标志 1.朋友圈  2.好友
    public static int sharestatue = 0;
    private String username;
    protected PictureDialog dialog;
    private LinearLayout addhuati;
    private VideoWorkProgressFragment mWorkProgressDialog;
    private int mCurrentState = STATE_NONE;
    private String input;
    private PopupWindow popupWindow;
    private String huatiID;//话题ID
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_share2);
        AppUtils.setTitle(VideoShareActivity.this);
        AppManager.getAppManager().addActivity(this);
        initView();
        initWorkProgressPopWin();
    }

    private void initView() {
        userId = (String) SPUtils.get(this, Constants.USER_ID, "-1");
        loginKey = (String) SPUtils.get(this, Constants.USER_LOGIN_KEY, "-1");
        username = (String) SPUtils.get(this, Constants.USER_Name, "-1");
        videoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
        strThumbnailPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);

        addhuati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopo();
            }
        });

        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        iv_thumbnail = (ImageView) findViewById(R.id.iv_thumbnail);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        edt_input_content = (EditText) findViewById(R.id.edt_input_content);


        Bitmap bm = BitmapUtils.getBitmapByPath(strThumbnailPath);
        if (bm == null) {
            iv_thumbnail.setImageResource(R.drawable.wulinnull_icon);
        } else {
            iv_thumbnail.setImageBitmap(bm);
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发布
        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoadingDialog();
                sendVideo();
            }
        });



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
        popupWindow.showAtLocation(addhuati, Gravity.BOTTOM, 0, 0);
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
            }
        });
        OkHttpUtils.post()
                .url(URLS.SCHEDULE_TOPICLIST)
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
                                HuatiListBean bean = gson.fromJson(response, HuatiListBean.class);
                                if (bean.getCode().equals("1000")){
                                    list.clear();
                                    list.addAll(bean.getData());
                                    huatiRecyclerAdapter.notifyDataSetChanged();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(VideoShareActivity.this));
                                    VideoShareActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }

                    }
                });





    }


    private void initWorkProgressPopWin() {
        if (mWorkProgressDialog == null) {
            mWorkProgressDialog = new VideoWorkProgressFragment();
            mWorkProgressDialog.setOnClickStopListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mWorkProgressDialog.dismiss();
                    Toast.makeText(VideoShareActivity.this, "取消视频生成", Toast.LENGTH_SHORT).show();
                    mWorkProgressDialog.setProgress(0);
                    mCurrentState = STATE_NONE;
                }
            });
        }
        mWorkProgressDialog.setProgress(0);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i("TAG", "onResume: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("TAG", "onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //发布视频
    private void sendVideo() {
        input = edt_input_content.getText().toString();
        if(StringUtils.isEmpty(input)){
            ToastUtils.showToast(VideoShareActivity.this,"请为视频加上一个有趣的标题");
            return;
        }
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
                                    initVideo();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(VideoShareActivity.this));
                                    VideoShareActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

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
            PutObjectRequest put = new PutObjectRequest(WLF.HEIXIONGWLF, "wyw/"+fileName, videoPath);
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
                    map.put("userId",SPUtils.get(VideoShareActivity.this,"id","-1")+"");
                    map.put("content",input);
                    map.put("token", MakerApplication.instance.getToken());
                    if (!StringUtils.isBlank(huatiID)){
                        map.put("topicId",huatiID);
                    }
                    map.put("imageList",finalFileName);
                    OkHttpUtils.post()
                            .url(URLS.SCHEDULE_PUBLISHIMAGESTEXT)
                            .params(map)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    UIUtils.showToast("服务器异常");
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    //将自己关闭
                                    dismissDialog();
                                    UIUtils.showToast("发布成功");
                                    finish();
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

    /**
     * loading dialog
     */
    protected void showLoadingDialog() {
        if (!isFinishing()) {
            dismissDialog();
            dialog = new PictureDialog(this);
            dialog.show();
        }
    }

    /**
     * dismiss dialog
     */
    protected void dismissDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void replyService(String qcloudId,final String input,String url,String coverURL) {
        if (!SystemHelper.isConnected(this)) {
            UIUtils.showToast("请检查网络");
            return;
        }
        JSONObjectAsyncTasker tasker = new JSONObjectAsyncTasker(
                VideoShareActivity.this, true, new ExcuteJSONObjectUpdate() {
            @Override
            public void excute(ResultPacket result) {
                if (result != null && result.getStatus().equals("ok")) {
                    Intent intent = new Intent (VideoShareActivity.this,HomeActivity.class);
                    intent.putExtra("fragId",3);
                    AppManager.getAppManager().finishActivityforName("HomeActivity");
                    startActivity(intent);
                    finish();
                }else{
                    ToastUtils.showToast(VideoShareActivity.this,result.getMsg());
                }
            }
        } );
        HashMap<String, Object> map= new HashMap<String, Object>();
        map.put("uid", MakerApplication.instance.getUid());
        map.put("loginKey", MakerApplication.instance.getLoginKey());

        map.put("qcloudFileid", qcloudId + "");
        map.put("qcloudVideoUrl", url + "");
        map.put("qcloudVideoImg", coverURL);
        map.put("contents", input);

        map.put("method", NetUrlUtils.wulin_publisVideo);
        tasker.execute(map);



    }
}
