package com.hxwl.wulinfeng.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.hxwl.common.utils.ShowDialog2;
import com.hxwl.common.utils.StringUtils;
import com.hxwl.newwlf.URLS;
import com.hxwl.newwlf.WLF;
import com.hxwl.newwlf.login.LoginActivity;
import com.hxwl.newwlf.modlebean.LoginBean;
import com.hxwl.newwlf.modlebean.TOkenBean;
import com.hxwl.utils.Photos;
import com.hxwl.wulinfeng.MakerApplication;
import com.hxwl.wulinfeng.R;
import com.hxwl.wulinfeng.base.BaseActivity;
import com.hxwl.wulinfeng.bean.ImageItem;
import com.hxwl.wulinfeng.util.AppUtils;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.DialogUtil;
import com.hxwl.wulinfeng.util.ImageUtils;
import com.hxwl.wulinfeng.util.ImgUtils;
import com.hxwl.wulinfeng.util.JsonValidator;
import com.hxwl.wulinfeng.util.ScreenUtils;
import com.hxwl.wulinfeng.util.SelectPopupWindow;
import com.hxwl.wulinfeng.util.SystemHelper;
import com.hxwl.wulinfeng.util.UIUtils;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tendcloud.tenddata.TCAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.apache.commons.lang.RandomStringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import okhttp3.Call;
import rx.functions.Action1;

/**
 * Created by Allen on 2017/6/20.
 * 个人资料界面
 */
public class PersonalDataActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_phone;
    private TextView tv_username;
    private ImageView iv_headpic;
    private RelativeLayout rl_changehead;
    private TextView tv_setphone;
    private TextView tv_setpwd;
    private TextView tv_weixing;
    private TextView tv_wx;
    private RelativeLayout rl_weixing;
    private RelativeLayout rl_changepwd;
    private TextView data_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persondata_activity);
        AppUtils.setTitle(PersonalDataActivity.this);
        initView();
        initPersonData();
    }

    private void initPersonData() {
        if (!StringUtils.isBlank(MakerApplication.instance.getLoginWX())&&StringUtils.isBlank(MakerApplication.instance.getMobile())){
            rl_changepwd.setVisibility(View.GONE);
        }else {
            rl_changepwd.setVisibility(View.VISIBLE);
        }
        if(MakerApplication.LOGIN.equals(MakerApplication.instance.getLoginState())){ // 登陆成功
            if(TextUtils.isEmpty(MakerApplication.instance.getMobile())){
                tv_phone.setText("");
                tv_setphone.setText("绑定手机号");
            }else{
                tv_phone.setText(Photos.Mobile2(MakerApplication.instance.getMobile()));
                tv_setphone.setText("修改手机号");
            }
            if(MakerApplication.instance.getPassWord().equals("0")){
                tv_setpwd.setText("修改密码");
            }else if (MakerApplication.instance.getPassWord().equals("1")){
                tv_setpwd.setText("设置密码");
            }
            tv_username.setText(Photos.stringPhoto(MakerApplication.instance.getNickName()));

            if(!TextUtils.isEmpty(MakerApplication.instance.getHeadImg())){
                ImageUtils.getCirclePic(MakerApplication.instance.getHeadImg(),iv_headpic,PersonalDataActivity.this);
            }
        }
    }

    private void initView() {
        findViewById(R.id.user_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_weixing= (RelativeLayout) findViewById(R.id.rl_weixing);
        rl_weixing.setOnClickListener(this);
        initweixing();

        rl_changehead = (RelativeLayout) findViewById(R.id.rl_changehead);
        rl_changehead.setOnClickListener(this);
        RelativeLayout rl_username = (RelativeLayout) findViewById(R.id.rl_username);
        rl_username.setOnClickListener(this);
        RelativeLayout rl_changephone = (RelativeLayout) findViewById(R.id.rl_changephone);
        rl_changephone.setOnClickListener(this);
        rl_changepwd = (RelativeLayout) findViewById(R.id.rl_changepwd);
        rl_changepwd.setOnClickListener(this);

        data_logout = (TextView) findViewById(R.id.data_logout);
        data_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatService.onEvent(PersonalDataActivity.this,"MyLogout","我的-退出登录",1);
                TCAgent.onEvent(PersonalDataActivity.this,"MyLogout","我的-退出登录");
//                showNormalDialog();
                MakerApplication.instance().setLoginState(PersonalDataActivity.this ,MakerApplication.LOGOUT);
                MakerApplication.instance().clearUserInfo();//清楚用户信息
                startActivity(LoginActivity.getIntent(PersonalDataActivity.this));
              finish();
            }
        });
        tv_setphone = (TextView) findViewById(R.id.tv_setphone);
        tv_setpwd = (TextView) findViewById(R.id.tv_setpwd);

        tv_weixing = (TextView) findViewById(R.id.tv_weixing);
        tv_wx = (TextView) findViewById(R.id.tv_wx);
        if (!StringUtils.isBlank(MakerApplication.instance.getLoginWX())){
            tv_wx.setText(MakerApplication.instance.getLoginWX());
            tv_weixing.setText("解绑微信");
        }else {
            tv_wx.setText("");
            tv_weixing.setText("绑定微信");
        }

        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_username = (TextView) findViewById(R.id.tv_username);
        iv_headpic = (ImageView) findViewById(R.id.iv_headpic);

        //大图路径
        photoPath = Constants.USER_PHOTO_PATH;
        //缩略图路径
        thumPath =Environment.getExternalStorageDirectory().getAbsolutePath() + "/black/photo_tub/";
        tempImgPath =   initTempPic("/tempPic/", Constants.USER_PHOTO_NAME);
    }
    private String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private void initweixing() {
        String model=android.os.Build.BRAND;// 手机品牌
        try {
            Properties prop= new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if(prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null
                    ||model.equals("nubia")){
                rl_weixing.setVisibility(View.GONE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String initTempPic(String path, String fileName){
        File dirFile = new File(Environment.getExternalStorageDirectory().getPath() + path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(dirFile.getPath() + File.separator + fileName + ".jpg");

        if (file.exists()) {//如果存在，就先删除再创建
            file.delete();
        }
        return file.getAbsolutePath();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null ;
        switch(view.getId()){
            case R.id.rl_changehead :
                changeHead();
                break ;
            case R.id.rl_username :
                intent = new Intent(PersonalDataActivity.this,ChangeNameActivity.class) ;
                intent.putExtra("name",MakerApplication.instance.getUsername()) ;
                startActivityForResult(intent ,REQUEST_FOR_NAME);
                break ;
            case R.id.rl_changephone :
                intent = new Intent(PersonalDataActivity.this,ChangePhoneActivity.class) ;
                startActivityForResult(intent,REQUEST_FOR_PHONE);
                break ;
            case R.id.rl_changepwd :
                if(MakerApplication.instance.getPassWord().equals("0")){
                    intent = new Intent(PersonalDataActivity.this,ChangePwdActivity.class) ;
                    startActivity(intent);
                }else if (MakerApplication.instance.getPassWord().equals("1")){
                    intent = new Intent(PersonalDataActivity.this,SetPwdActivity.class) ;
                    intent.putExtra("type",1);
                    startActivity(intent);
                }else{
                    UIUtils.showToast("您还没有登录");
                }
                break ;
            case R.id.rl_weixing :
                if ("解绑微信".equals(tv_weixing.getText().toString().trim())){
                    if (StringUtils.isBlank(MakerApplication.instance.getMobile())){
                        UIUtils.showToast("您还没有绑定手机号");
                    }else {
                        DialogUtil.showConfirm(this, "", "是否解绑微信？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                okhttpjiebang();//解绑
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }


                }else {
                    ShowDialog2.showProgressDialog(this,"","",true);
                    if (!MakerApplication.api.isWXAppInstalled()) {
                        UIUtils.showToast("您还未安装微信客户端");
                        ShowDialog2.dismissProgressDialog();
                    }else {
                        SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_demo_test";
                        MakerApplication.api.sendReq(req);
                    }

                }
                break ;
            default:
                break ;
        }
    }

    private void okhttpjiebang() {
        OkHttpUtils.post()
                .url(URLS.USER_UPDATE)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("id",MakerApplication.instance.getUid())
                .addParams("weixinUnionId","解绑微信")
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
                                LoginBean bean = gson.fromJson(response, LoginBean.class);
                                if (bean.getCode().equals("1000")){
                                    MakerApplication.instance.setLoginWX(PersonalDataActivity.this ,"");
                                    MakerApplication.instance.saveLogin(bean.getData(),PersonalDataActivity.this);
                                    UIUtils.showToast("解绑成功");
                                    tv_wx.setText("");
                                    tv_weixing.setText("绑定微信");
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(PersonalDataActivity.this));
                                    PersonalDataActivity.this.finish();
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

    private String[] poptitle=new String[]{"拍照", "手机相册"};
    public static final int REQUEST_FOR_IMAGE = 0x00000401;
    public static final int REQUEST_FOR_PHONE = 50; //修改手机号
    public static final int REQUEST_FOR_NAME = 40; //修改姓名
    public static final int REQUEST_FOR_CAMERA = 30;
    private String tempImgPath;//照片临时文件
    private String bigPath;
    private String mthumPath;
    private String photoPath;
    private String thumPath;

    private void changeHead() {
        SelectPopupWindow photoWindow=new SelectPopupWindow(this,poptitle);
        //设置弹窗位置
        photoWindow.showAtLocation(this.findViewById(R.id.rl_changehead), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,0);
        photoWindow.setOnPopupWindowClickListener(new SelectPopupWindow.OnPopupWindowClickListener() {
            @Override
            public void onPopupWindowItemClick(int position) {
                switch (position){
                    case 0:  //拍照
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                            toCamera();
                        } else {
                            RxPermissions rxPermissions = new RxPermissions(PersonalDataActivity.this);
                            rxPermissions
                                    .request(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .subscribe(new Action1<Boolean>() {
                                        @Override
                                        public void call(Boolean granted) {
                                            if (granted) {
                                                toCamera();
                                            } else {
                                                UIUtils.showToast("此功能需要开启相机和读写SD卡授权");
                                            }
                                        }
                                    });
                        }

                        break;
                    case 1:  //图库
                        String status = Environment.getExternalStorageState();
                        if (status.equals(Environment.MEDIA_MOUNTED)) {
                            Intent in = new Intent(Intent.ACTION_PICK, null);
                            in.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                            File tmpPath = new File(tempImgPath);
                            Uri imageUri = Uri.fromFile(tmpPath);
                            in.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(in, REQUEST_FOR_IMAGE);
                        } else {
                            UIUtils.showToast("没有储存卡");

                        }
                        break;

                }
            }
        });
    }
    public void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(tempImgPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_FOR_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                // 图库选择
                case REQUEST_FOR_IMAGE:
                    Uri uri = data.getData();
                    if (uri != null) {
                        tempImgPath = ImgUtils.getRealFilePath(this,uri);
                        File file = new File(tempImgPath);
                        if (file.exists() && file.canRead() && file.length() > 0) {
                            camera(tempImgPath);
                        }
                    }
                    break;
                // 拍照
                case REQUEST_FOR_CAMERA:
                    if(TextUtils.isEmpty(tempImgPath))
                        return ;
                    File file = new File(tempImgPath);
                    if(!file.exists()){
                        UIUtils.showToast("拍摄的图片不存在，请重新拍摄");
                        return;
                    }
                    camera(tempImgPath);
                    break;
                case REQUEST_FOR_NAME:  //修改姓名
                    String username=data.getStringExtra("username");
                    tv_username.setText(username);
                    break;
                case REQUEST_FOR_PHONE:  //修改手机号
                    String phone=data.getStringExtra("phone");
                    tv_phone.setText(Photos.Mobile2(phone));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 拍照
     */
    private ArrayList<ImageItem> imagePhotos = new ArrayList<>();
    private int anInt;
    private void camera(String path) {
        imagePhotos.clear();
        BitmapUtils utils = new BitmapUtils();
        Random rand = new Random();
        anInt = rand.nextInt(1000);
        //创建大图文件
        bigPath = createPicPath(photoPath, anInt);
        //创建缩略图文件
        mthumPath = createPicPath(thumPath, anInt);

        Boolean isBigPicSave = utils.saveImage( path, bigPath, 95);
        boolean isTubPicSave = utils.saveImage(ImgUtils.getImageThumbnail(bigPath,
                ScreenUtils.dip2px(this, 60), ScreenUtils.dip2px(this, 40)), mthumPath);
        imagePhotos.add(new ImageItem(bigPath,    mthumPath,anInt+".jpg"));

        Bitmap bm = BitmapFactory.decodeFile(bigPath);
        Bitmap bitmap = ImageUtils.toRoundBitmap(bm);
        iv_headpic.setImageBitmap(bitmap);
        upLoadImage();

    }
    /**
     * 创建图片存放地址
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
    private String accessKeySecret;
    private String accessKeyId;
    private String securityToken;
    //上传头像
    private void upLoadImage() {
        if (!SystemHelper.isConnected(PersonalDataActivity.this)) {
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
                                    String s = imagePhotos.get(0).getImagePath() ;
                                    init(s);
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(PersonalDataActivity.this));
                                    PersonalDataActivity.this.finish();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    }
                });

    }


    private void init(String s) {
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
                Log.e("TAG--PutObject", "UploadSuccess");
                Log.e("TAG--ETag", result.getETag());
                Log.e("TAG--RequestId", result.getRequestId());
                doUserInfo(finalFileName);
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

    private void doUserInfo(String finalFileName) {
        if (!SystemHelper.isConnected(PersonalDataActivity.this)) {
            UIUtils.showToast("请检查网络");
            return;
        }

        OkHttpUtils.post()
                .url(URLS.USER_UPDATE)
                .addParams("token", MakerApplication.instance.getToken())
                .addParams("id",MakerApplication.instance.getUid())
                .addParams("headImg",finalFileName)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        JsonValidator jsonValidator = new JsonValidator();
                        if (jsonValidator.validate(response)) {
                            Gson gson = new Gson();
                            try {
                                LoginBean bean = gson.fromJson(response, LoginBean.class);
                                if (bean.getCode().equals("1000")) {
                                    MakerApplication.instance.setHeadPic(bean.getData().getHeadImg());
                                    //是不是要马上更新头像
                                    ImageUtils.getCirclePic(URLS.IMG+bean.getData().getHeadImg(),iv_headpic,PersonalDataActivity.this);
                                    UIUtils.showToast("头像更换成功");
                                }else if (bean.getCode().equals("2002")||bean.getCode().equals("2003")){
                                    UIUtils.showToast(bean.getMessage());
                                    startActivity(LoginActivity.getIntent(PersonalDataActivity.this));
                                    PersonalDataActivity.this.finish();
                                }else {
                                    UIUtils.showToast(bean.getMessage());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onPageStart(this, "个人资料");
        TCAgent.onPageStart(this, "个人资料");
        initPersonData();
    }
    @Override
    public void onPause() {
        super.onPause();
        StatService.onPageEnd(this,"个人资料");
        TCAgent.onPageEnd(this,"个人资料");
    }
}
