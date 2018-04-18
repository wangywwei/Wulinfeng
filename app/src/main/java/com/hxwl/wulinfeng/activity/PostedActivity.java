package com.hxwl.wulinfeng.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.baidu.mobstat.StatService;
import com.google.gson.Gson;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.WLF;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.HomeOneBean;
import com.hxwl.newwlf.modlebean.HuatiListBean;
import com.hxwl.newwlf.modlebean.TOkenBean;
import com.hxwl.newwlf.wulin.arts.HuatiRecyclerAdapter;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.ImageBean;
import com.hxwl.wulinfeng.bean.ImageItem;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.ImgUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.SPUtils;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.library.flowlayout.FlowLayoutManager;
import com.tendcloud.tenddata.TCAgent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.lang.RandomStringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.iwf.photopicker.widget.MultiPickResultView;
import okhttp3.Call;

import static com.hxwl.wulinfeng.R.id.edit_content;

/**
 * 发帖页面
 */
public class PostedActivity extends BaseActivity implements View.OnClickListener {

    private HuatiRecyclerAdapter huatiRecyclerAdapter;
    private String accessKeySecret;
    private String accessKeyId;
    private String securityToken;
    private ImageView huatiaimg;
    private TextView huatiatext;
    private ImageView huatiaimg2;
    private TextView huatiatext2;
    public static Intent getIntent(Context context){
        Intent intent=new Intent(context,PostedActivity.class);
        return intent;
    }
    @Bind(R.id.tv_back)
    TextView tv_back;
    @Bind(R.id.ll_pb)
    RelativeLayout ll_pb;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(edit_content)
    EditText editContent;
    @Bind(R.id.recycler_view)
    MultiPickResultView recyclerView;
    @Bind(R.id.tv_commit)
    TextView tvCommit;
    private ArrayList<ImageItem> imagePhotos = new ArrayList<>();
    private String photoPath;
    private String thumPath;
    private ArrayList<String> photos;
    private String huatiID;//话题ID
    private String userId;
    private String loginKey;
    private ImageBean imageBean;
    private ProgressDialog dialog;
    private TextView tv_textcount;
    private LinearLayout addhuati;
    private LinearLayout addhuati2;
    private PopupWindow popupWindow;

    private ArrayList<HuatiListBean.DataBean> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fatie_layout);
        AppUtils.setTitle(PostedActivity.this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private String textContext = "";//editText的内容

    private void initData() {
        //大图路径
        photoPath = Environment.getExternalStorageDirectory() + "/BlackBears/photo_temp/";
        //缩略图路径
        thumPath = Environment.getExternalStorageDirectory() + "/BlackBears/photo_tub/";
    }

    private StringBuffer stringBuffer=new StringBuffer();
    /**
     * 上传帖子的图片
     *
     * @param index
     */
    private void init(String s, final int index){
        if (StringUtils.isBlank(accessKeyId)){
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

        PutObjectRequest put = new PutObjectRequest(WLF.HEIXIONGWLF, "images/"+fileName, s);
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
//                Log.e("TAG--PutObject", "UploadSuccess");
//                Log.e("TAG--ETag", result.getETag());
//                Log.e("TAG--RequestId", result.getRequestId());
                    if (index == imagePhotos.size() - 1) {
                        if (StringUtils.isBlank(stringBuffer.toString())){
                            stringBuffer.append(finalFileName);
                        }else {
                            stringBuffer.append(",").append(finalFileName);
                        }

                        PostedActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initshangchuan();

                            }
                        });

                    } else {
                        init(photos.get(index + 1), index + 1);
                        if (StringUtils.isBlank(stringBuffer.toString())){
                            stringBuffer.append(finalFileName);
                        }else {
                            stringBuffer.append(",").append(finalFileName);
                        }
                    }
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

    private void initshangchuan() {
        Map<String,String> map=new HashMap<>();
        map.put("userId",SPUtils.get(PostedActivity.this,"id","-1")+"");
        map.put("content",textContext);
        if (!StringUtils.isBlank(huatiID)){
            map.put("topicId",huatiID);
        }
        if (!StringUtils.isBlank(stringBuffer.toString())){
            map.put("imageList",stringBuffer.toString());
        }
        map.put("token",MakerApplication.instance.getToken());
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
                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)){
                            Gson gson = new Gson();
                            try {
                                HomeOneBean bean = gson.fromJson(response, HomeOneBean.class);
                                deleteFile();
                                dismissDialog();
                                if (bean.getCode().equals("1000")){
                                    UIUtils.showToast(bean.getMessage());
                                    //将自己关闭
                                    Intent intent=new Intent(PostedActivity.this,HomeActivity.class);
                                    intent.putExtra("signTab","wulin");
                                    startActivity(intent);

                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(PostedActivity.this));
                                    PostedActivity.this.finish();
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

    public void initView() {
        TextView user_icon = (TextView) findViewById(R.id.tv_back);
        user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView.init(this, MultiPickResultView.ACTION_SELECT, null);
        tv_textcount = (TextView) findViewById(R.id.tv_textcount);
        addhuati= (LinearLayout) findViewById(R.id.addhuati);
        huatiaimg = (ImageView)findViewById(R.id.huatiaimg);
        huatiatext = (TextView) findViewById(R.id.huatiatext);
        addhuati2= (LinearLayout) findViewById(R.id.addhuati2);
        huatiaimg2= (ImageView)findViewById(R.id.huatiaimg2);
        huatiatext2 = (TextView) findViewById(R.id.huatiatext2);
        addhuati2.setVisibility(View.GONE);

        huatiaimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addhuati.setVisibility(View.VISIBLE);
                addhuati2.setVisibility(View.GONE);
            }
        });


        addhuati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPopo();
            }
        });

        editContent.addTextChangedListener(new TextWatcher() {
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
        tvCommit.setOnClickListener(this);
        tv_back.setOnClickListener(this);

        /* 上传图片6.0权限*/
        AndPermission.with(this)
                .requestCode(101)
                .permission(Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(mRationaleListener)
                .send();
    }
    private TranslateAnimation animation;
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
                huatiatext2.setText(list.get(position).getTopicName());
                addhuati.setVisibility(View.GONE);
                addhuati2.setVisibility(View.VISIBLE);
                popupWindow.dismiss();


            }
        });
            OkHttpUtils.post()
                    .url(URLS.SCHEDULE_TOPICLIST)
                    .addParams("pageNumber","1")
                    .addParams("pageSize","10")
                    .addParams("more","more")
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
                                        startActivity(LoginActivity.getIntent(PostedActivity.this));
                                        PostedActivity.this.finish();
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }

                        }
                    });





    }


    private RationaleListener mRationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(PostedActivity.this)
                    .setTitle("友好提醒")
                    .setMessage("没有读取SD卡权限将不能发图，请读取SD卡权限赐给我吧！")
                    .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();// 用户同意继续申请。
                        }
                    })
                    .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel(); // 用户拒绝申请。
                        }
                    }).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StatService.onEvent(this, "WulinReleasealbum", "武林相册选择", 1);
        TCAgent.onEvent(this, "WulinReleasealbum", "武林相册选择");
        recyclerView.onActivityResult(requestCode, resultCode, data);

    }

    private boolean checkoutParams() {
        if (TextUtils.isEmpty(editContent.getText().toString().trim()) && recyclerView.getPhotos().size() == 0) {
            Toast.makeText(this, "内容和图片不能同时为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    /**
     * 创建图片存放地址
     *
     * @param dir
     * @return
     */
    public String createPicPath(String dir, int pos) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(dirFile.getPath() + File.separator + pos + ".jpg");
        if (file.exists()) {
            file.delete();
        }

        return dirFile.getPath() + File.separator + pos + ".jpg";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_commit: //发帖

                if (checkoutParams()) {

                    showDialog(PostedActivity.this, "正在上传请稍后", false);
//                    ll_pb.setVisibility(View.VISIBLE);
                    upContent();
                }

                break;
            default:

                break;
        }
    }

    private void initPic() {
        //临时获得的图片位置
        photos = recyclerView.getPhotos();
        imagePhotos.clear();
        if (photos == null || photos.size() == 0) {
            initshangchuan();

            return;
        }
        BitmapUtils utils = new BitmapUtils();
        for (int i = 0; i < photos.size(); i++) {
            Random rand = new Random();
            int anInt = rand.nextInt(1000);
            String bigPath = createPicPath(photoPath, anInt);//创建大图文件
            String mthumPath = createPicPath(thumPath, anInt);//创建缩略图文件
            Boolean isBigPicSave = utils.saveImage(photos.get(i), bigPath, 95);
            boolean isTubPicSave = utils.saveImage(ImgUtils.getImageThumbnail(bigPath,
                    ScreenUtils.dip2px(this, 60), ScreenUtils.dip2px(this, 40)), mthumPath);
            imagePhotos.add(new ImageItem(bigPath, mthumPath, anInt + ".jpg"));
        }
        if (imagePhotos != null && imagePhotos.size() > 0) {
            init(photos.get(0), 0);
        }else {

        }


    }


    /**
     * 发帖
     */
    private void upContent() {
        if (!SystemHelper.isConnected(PostedActivity.this)) {
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
                                    initPic();
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(PostedActivity.this));
                                    PostedActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });


    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
//        dismissDialog();
    }

    private void deleteFile() {
        try {
            for (int i = 0; i < imagePhotos.size(); i++) {
                String str = imagePhotos.get(i).getImagePath();
                if (!TextUtils.isEmpty(str)) {
                    File file1 = new File(str);
                    file1.delete();
                }
                if (!TextUtils.isEmpty(imagePhotos.get(i).getImageTub())) {
                    File file2 = new File(imagePhotos.get(i).getImageTub());
                    file2.delete();
                }

            }


        } catch (Exception e) {
            Toast.makeText(this, "文件删除失败", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResume() {
        super.onResume();
        StatService.onPageStart(this, "发布");
        TCAgent.onPageStart(this, "发布");
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this, "发布");
        TCAgent.onPageEnd(this, "发布");
    }




}
