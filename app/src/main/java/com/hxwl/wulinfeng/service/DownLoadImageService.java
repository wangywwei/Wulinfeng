package com.hxwl.wulinfeng.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.hxwl.wulinfeng.util.BitmapUtils;
import com.hxwl.wulinfeng.util.Constants;
import com.hxwl.wulinfeng.util.config.SystemDir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 功能:下载图片服务
 */

public class DownLoadImageService implements Runnable {
    private Context context;
    private File currentFile;
    private List<String> list;

    public DownLoadImageService(Context context, List<String> urlList) {
        this.list = urlList;
        this.context = context;
    }

    @Override
    public void run() {
        Bitmap bitmap = null;
        File appDir = null;
        String url = "";
        try {
            for(int i=0;i<list.size();i++){
                //判断该图片是否存在,存在的话继续下一个循环
                appDir = new File(SystemDir.DIR_IMAGE);
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }
                url = list.get(i);
                //混淆文件名 判断混淆文件名的文件是否存在
                String fileName = url.substring(url.lastIndexOf("/")+1)+""+ Constants.BITMAP_SUFFIX;
                currentFile = new File(appDir, fileName);
                if(currentFile.exists()){
                    continue;
                }
                bitmap = Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                if (bitmap != null){
                    // 在这里执行图片保存方法
                    String filePath = currentFile.getPath().substring(0,currentFile.getPath().lastIndexOf("."));
                    BitmapUtils.saveEncryptBitmapToSD(bitmap,filePath,60);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveImageToGallery(Bitmap bmp,String fileName) {
        // 首先保存图片
        File appDir = new File(SystemDir.DIR_IMAGE);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        currentFile = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}