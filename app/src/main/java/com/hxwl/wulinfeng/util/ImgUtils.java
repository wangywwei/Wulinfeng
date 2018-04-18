/**
 * Project Name:InvoiceCADA
 * File Name:ImgUrils.java
 * Package Name:com.jzg.invoicecada.utils
 * Date:2014-4-21下午1:50:22
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.hxwl.wulinfeng.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ClassName:ImgUrils <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-4-21 下午1:50:22 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class ImgUtils
{
	/**
	 * 将byte数组转换成Bitmap Bytes2Bimap: <br/>
	 * 
	 * @author wang
	 * @param b
	 * @return
	 * @since JDK 1.6
	 */
	public static Bitmap Bytes2Bimap(byte[] b)
	{
		if (b.length != 0)
		{
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else
		{
			return null;
		}
	}

	/**
	 * 根据指定的图像路径和大小来获取缩略图
	 * 此方法有两点好处：
	 *     1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
	 *        第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
	 *     2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
	 *        用这个工具生成的图像不会被拉伸。
	 * @param imagePath 图像的路径
	 * @param width 指定输出图像的宽度
	 * @param height 指定输出图像的高度
	 * @return 生成的缩略图
	 */
	public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
// 获取这个图片的宽和高，注意此处的bitmap为null
		bitmap = BitmapFactory.decodeFile(imagePath, options);
		options.inJustDecodeBounds = false; // 设为 false
// 计算缩放比
		int h = options.outHeight;
		int w = options.outWidth;
		int beWidth = w / width;
		int beHeight = h / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
// 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
		bitmap = BitmapFactory.decodeFile(imagePath, options);
// 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
	/**
	 * 将bitmap转换成字节 Bitmap2Bytes: <br/>
	 * 
	 * @author wang
	 * @param bm
	 * @return
	 * @since JDK 1.6
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 图片按照比例大小压缩 comp: <br/>
	 * 
	 * @author wang
	 * @param image
	 * @return
	 * @since JDK 1.6
	 */
	// public static Bitmap comp(Bitmap image)
	// {
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	// if (baos.toByteArray().length / 1024 > 1024)
	// {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
	// baos.reset();// 重置baos即清空baos
	// image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//
	// 这里压缩50%，把压缩后的数据存放到baos中
	// }
	// ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
	// BitmapFactory.Options newOpts = new BitmapFactory.Options();
	// // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
	// newOpts.inJustDecodeBounds = true;
	// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
	// newOpts.inJustDecodeBounds = false;
	// int w = newOpts.outWidth;
	// int h = newOpts.outHeight;
	// // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
	// float hh = 800f;// 这里设置高度为800f
	// float ww = 480f;// 这里设置宽度为480f
	// // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
	// int be = 1;// be=1表示不缩放
	// if (w > h && w > ww)
	// {// 如果宽度大的话根据宽度固定大小缩放
	// be = (int) (newOpts.outWidth / ww);
	// } else if (w < h && h > hh)
	// {// 如果高度高的话根据宽度固定大小缩放
	// be = (int) (newOpts.outHeight / hh);
	// }
	// if (be <= 0)
	// be = 1;
	// newOpts.inSampleSize = be;// 设置缩放比例
	// // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
	// isBm = new ByteArrayInputStream(baos.toByteArray());
	// bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
	// return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	// // return bitmap;
	// }
	//
	// public static Bitmap compressImage(Bitmap image)
	// {
	//
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//
	// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
	// int options = 100;
	// while (baos.toByteArray().length / 1024 > 100)
	// { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
	// baos.reset();// 重置baos即清空baos
	// image.compress(Bitmap.CompressFormat.JPEG, options, baos);//
	// 这里压缩options%，把压缩后的数据存放到baos中
	// options -= 10;// 每次都减少10
	// }
	// ByteArrayInputStream isBm = new
	// ByteArrayInputStream(baos.toByteArray());//
	// 把压缩后的数据baos存放到ByteArrayInputStream中
	// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
	// 把ByteArrayInputStream数据生成图片
	// return bitmap;
	// }

	/**
	 * 指定目标图片大小（不是Byte单位）
	 * 
	 * @param bitmap
	 *            原图片
	 * @param maxSize
	 *            目标大小
	 * 
	 * @return 压缩好的图片
	 */
	public static Bitmap compressByQuality(Bitmap bitmap, int maxSize)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		bitmap.compress(CompressFormat.JPEG, maxSize, baos);

		return BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
				baos.toByteArray().length);
	}

	/**
	 * 指定目标图片尺寸压缩 注：操作过程中不会保持纵横比
	 * 
	 * @param oldBitmap
	 *            需要压缩的图片（bitmap）
	 * @param newWidth
	 *            目标图片宽度
	 * @param newHeight
	 *            目标图片高度
	 * 
	 * @return 压缩好的图片（bitmap）
	 */
	public static Bitmap zoomImage(Bitmap oldBitmap, double newWidth,
								   double newHeight)
	{

		Bitmap bitmap;
		// 获取这个图片的宽和高
		float width = oldBitmap.getWidth();
		float height = oldBitmap.getHeight();
		// 矩阵压缩
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		bitmap = Bitmap.createBitmap(oldBitmap, 0, 0, (int) width,
				(int) height, matrix, true);
		oldBitmap.recycle();
		return bitmap;
	}

	/**
	 * 缩放照片（比率缩放）
	 * 
	 * @param bitmap
	 * @param scaleWidth
	 * @param scaleHeight
	 * @return 缩放后的照片
	 */
	public static Bitmap zoomImageFromScale(Bitmap bitmap, float scale)
	{

		// 图片的原宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		// 缩放图片动作
		Matrix matrix = new Matrix();
		// 比率缩放
		matrix.postScale(scale, scale);

		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
				true);

		return bmp;
	}

	/**
	 * 倒影图片效果
	 * 
	 * @param originalImage
	 * @return
	 */
	public static Bitmap createMirrorImage(Bitmap originalImage)
	{

		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		// 反射倒影图片的高度
		int mirrorHeight = height >> 1;

		Matrix matrix = new Matrix();
		// 实现图片翻转90度
		matrix.preScale(1, -1);
		// 创建倒影图片（是原始图片的一半大小）
		Bitmap mirrorImage = Bitmap.createBitmap(originalImage, 0,
				mirrorHeight, width, mirrorHeight, matrix, false);
		// 创建总图片（原图片 + 倒影图片+中间间隔1）
		Bitmap finalBitmap = Bitmap.createBitmap(width, height + mirrorHeight
				+ 1, Config.ARGB_8888);
		// 创建画布
		Canvas canvas = new Canvas(finalBitmap);
		canvas.drawBitmap(originalImage, 0, 0, null);
		// 把倒影图片画到画布上 （中间间隔1感觉更真实）
		canvas.drawBitmap(mirrorImage, 0, height + 1, null);
		Paint shaderPaint = new Paint();
		// 创建线性渐变LinearGradient对象
		LinearGradient shader = new LinearGradient(0, height + 1, 0,
				finalBitmap.getHeight(), 0x7fffffff, 0x00ffffff,
				TileMode.MIRROR);

		shaderPaint.setShader(shader);

		shaderPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// 画布画出反转图片大小区域、然后把渐变效果加到其中、就出现了图片的倒影效果、
		canvas.drawRect(0, height + 1, width, finalBitmap.getHeight(),
				shaderPaint);

		return finalBitmap;
	}

	/**
	 * 获取网络图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getBitmapByUrl(String url)
	{

		Bitmap bitmap = null;

		try
		{
			URL ur = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) ur.openConnection();

			InputStream is = conn.getInputStream();

			bitmap = BitmapFactory.decodeStream(is);
		} catch (MalformedURLException e)
		{
			// LogUtils.logE("异常："+e.getMessage());
		} catch (IOException e)
		{
			// LogUtils.logE("异常："+e.getMessage());
		}

		return bitmap;
	}

	/**
	 * 压缩图片比例（依据文件路径） 再压缩图片质量返回(耗时）
	 * 
	 * @param srcPath
	 * @param compressSize
	 * @method
	 */
	public static Bitmap compressImage(String srcPath, int compressSize)
	{
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww)
		{// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh)
		{// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}

		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		newOpts.inPreferredConfig = Config.RGB_565;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;
		// return compressImage(bitmap, compressSize);// 压缩好比例大小后再进行质量压缩
	}

	/**
	 * 压缩bitmap的质量（按照指定的压缩大小来处理）如果不传，默认为100kb（耗时）
	 * 
	 * @param bitmap
	 * @param compressSize
	 * @return
	 * @method
	 */
	public static Bitmap compressImage(Bitmap bitmap, int compressSize)
	{
		if (compressSize == 0)
			compressSize = 100;// 默认压缩100kb
		System.out.println("--------图片压缩------" + compressSize);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		if (baos.toByteArray().length / 1024 < compressSize)
		{
			System.out.println("当前图片<" + compressSize);
			return bitmap;
		}
		while (baos.toByteArray().length / 1024 > compressSize)
		{ // 循环判断如果压缩后图片是否大于compressSizekb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			bitmap.compress(CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 6;// 每次都减少6
			if (options <= 0)
				break;
			System.out.println("baos.toByteArray().length=="
					+ baos.toByteArray().length);
			System.out.println("------压缩次数-------" + options
					+ "baos.toByteArray().length / 1024=="
					+ baos.toByteArray().length / 1024);
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		System.out.println("------压缩结果-------" + baos.toByteArray().length
				/ 1024);
		Bitmap bitNew = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitNew;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path)
	{
		int degree = 0;
		try
		{
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * 
	 * @param angle
	 * @param bitmap
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap)
	{
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 根据图片的uri获取图片的path
	 * 
	 * @param activity
	 * @param uri
	 * @return
	 */
	public static String getImagePath(Activity activity, Uri uri)
	{

		String[] imgs = { MediaStore.Images.Media.DATA };// 将图片URI转换成存储路径
		Cursor cursor = activity.managedQuery(uri, imgs, null, null, null);
		int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(index);
	}


	public static Bitmap decodeSampledBitmapFromFile(String filename,
													 int reqWidth, int reqHeight)
	{

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filename, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// If we're running on Honeycomb or newer, try to use inBitmap
		// if (Utils.hasHoneycomb()) {
		// addInBitmapOptions(options, cache);
		// }

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filename, options);
	}

	// =========================================
	/**
	 * 把bitmap压缩后转换成String 并设置图片旋转度
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath)
	{
		byte[] b = null;
		try
		{
			int degree = ImgUtils.readPictureDegree(filePath);
			Bitmap bm = getSmallBitmap(filePath, 480, 800);
			Bitmap bitmap = rotaingImageView(degree, bm);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 60, baos);
			b = baos.toByteArray();
			baos.close();
			return Base64.encodeToString(b, Base64.DEFAULT);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 压缩图片并把结果转换成byte数组
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] compressBitmapToByte(String filePath)
	{
		byte[] b = null;
		try
		{
			int degree = ImgUtils.readPictureDegree(filePath);
			Bitmap bm = getSmallBitmap(filePath, 480, 800);
			Bitmap bitmap = rotaingImageView(degree, bm);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.JPEG, 100, baos);
			b = baos.toByteArray();
			System.out.println("b.length is " + b.length);
			baos.close();
			return b;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从路径获取图片字节,并旋转
	 * @param filePath
	 * @return
	 */
	public synchronized static byte[] getAndCompressBitmapToByte(String filePath){
		int degree = ImgUtils.readPictureDegree(filePath);
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		if(degree != 0){
			bm = rotaingImageView(degree, bm);
		}
		byte[] b = null;
		int compressSize = getCompressSize(getBitmapSize(filePath));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, compressSize, baos);
		b = baos.toByteArray();
		try {
			if(baos != null)
				baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * string转成bitmap
	 * 
	 * @param st
	 */
	public static Bitmap stringToBitmap(String st)
	{
		Bitmap bitmap = null;
		try
		{
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
			return bitmap;
		} catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 把bitmap转换成String 并设置图片旋转度
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap toBitmap(String filePath)
	{
		Bitmap bitmap = null;
		byte[] b = null;
		try
		{
			int degree = ImgUtils.readPictureDegree(filePath);
			Bitmap bm = getSmallBitmap(filePath, 100, 100);
			Bitmap temp = rotaingImageView(degree, bm);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			temp.compress(CompressFormat.JPEG, 40, baos);
			b = baos.toByteArray();
			bitmap = Bytes2Bimap(b);
			baos.close();
			return bitmap;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取缩略图用
	 * @param filePath
	 * @return
	 */
	public synchronized static Bitmap getBitmapFromFilePath(String filePath){
		Bitmap bitmap = null;
		byte[] b = null;
		ByteArrayOutputStream baos = null;
		int degree = ImgUtils.readPictureDegree(filePath);
		Bitmap bm = getSmallBitmap(filePath, 100, 100);
		if(degree != 0){
			bm = rotaingImageView(degree, bm);
		}
		int compressSize = getCompressSize(getBitmapSize(filePath));
		if(compressSize >= 90)return bm;
		baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, compressSize, baos);
		b = baos.toByteArray();
		bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
		if(baos!= null)
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return bitmap;
	}
	private static final float mMinBitmapSize = 1.5f*1024f*1024f;
	public static int getCompressSize(int size){
		if(size == 0)return 90;
		if(size <= mMinBitmapSize)return 90;
		float m = (float)mMinBitmapSize/(float)size;
		m *=100f;
		if(m <0|| m>90)m=90;
		if(m<60)m=60;
		return (int)m;
	}
	@SuppressLint("NewApi")
	public static int getBitmapSize(String filePath){
		File file = new File(filePath);
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(file);
			try {
				int size = fileIn.available();
				return size;
			} catch (IOException e) {
				e.printStackTrace();
				return 0;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} finally {
			if(fileIn!= null)
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return 0;
	}

	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight)
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth)
		{

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath, int width, int height)
	{
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, width, height);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		options.outWidth = width;
		options.outHeight = height;
		options.inPreferredConfig = Config.ARGB_4444;
		options.inInputShareable = true;

		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
     * 获取网络图片资源
     * 
     * @param url
     * @return
     */
    public static Bitmap getHttpBitmap(String url) {
            URL myFileURL;
            Bitmap bitmap = null;
          try {
				myFileURL = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) myFileURL
                              .openConnection();
			//	conn.connect();
                conn.setConnectTimeout(5000);
                conn.setDoInput(true);
                InputStream is =  conn.getInputStream();
            //      myFileURL.openStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                    
            return bitmap;
    }

    /**
     * 保存图片到相册
     * @param context
     * @param bmp
     */
    public static void saveImage(Context context, Bitmap bmp) {
	    // 保存图片
	    File filePath = new File(Environment.getExternalStorageDirectory(), "bjxd");
	    if (!filePath.exists()) {
	    	filePath.mkdir();
	    }
	    String fileName = System.currentTimeMillis() + ".jpg";
	    File file = new File(filePath, fileName);
	    try {
	        FileOutputStream fos = new FileOutputStream(file);
	        bmp.compress(CompressFormat.JPEG, 100, fos);
	        fos.flush();
	        fos.close();
		    // 把文件插入到系统图库
//		    try {
//		        MediaStore.Images.Media.insertImage(context.getContentResolver(),
//						file.getAbsolutePath(), fileName, null);
//		    } catch (FileNotFoundException e) {
//		        e.printStackTrace();
//		    }
//		    // 通知图库更新
		    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
		    		Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		   
		    String path = Environment.getExternalStorageDirectory().getPath();
		    if(path.endsWith("0")){
		    	path = path.substring(0, path.length()-1);
		    }
		    Toast.makeText(context, "保存成功,"+path+"bjxd", Toast.LENGTH_SHORT).show();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
		}
	}
    
    
    /**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize1(options, 480, 800);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}
	
	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize1(BitmapFactory.Options options,
			int reqWidth, int reqHeight)
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth)
		{

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri) return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}
}
