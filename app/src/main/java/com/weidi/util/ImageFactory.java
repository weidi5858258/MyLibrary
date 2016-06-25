package com.xianglin.station.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;

/**
 * 
 * 
 * @author longfeng
 * @version $Id: ImageFactory.java, v 1.0.0 2015年12月1日 上午10:32:18 xl Exp $
 */
public class ImageFactory {

	/**
	 * 分辨率压缩 在质量压缩
	 * 
	 * @param 路劲
	 * @return </br> 处理之后的路劲
	 * @throws IOException
	 */
	public static byte[] perUploadPic(String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		try {
			BitmapFactory.decodeFile(path, newOpts);// 打开空图片获取分辨率
			newOpts.inSampleSize = 4;// 设置缩放倍数
			newOpts.inJustDecodeBounds = false;
			Bitmap bitmap1 = BitmapFactory.decodeFile(path, newOpts);
			bitmap1.compress(CompressFormat.JPEG, 80, baos);
		} catch (OutOfMemoryError e) {
			Log.e("OutOfMemoryError", "图片上传压缩初次分辨率失败");
			newOpts.inSampleSize = newOpts.inSampleSize + 2;
			Bitmap bitmap1 = BitmapFactory.decodeFile(path, newOpts);
			bitmap1.compress(CompressFormat.JPEG, 80, baos);
		}
		Log.e("压缩后分辨率：", newOpts.outWidth + "*" + newOpts.outHeight);
		Log.e("分辨率压缩后的大小:", (baos.toByteArray().length / 1024) + "");

		Bitmap bitmap = null;
		int options = 80;
		while (baos.toByteArray().length / 1024 > 90) { //
			if (bitmap == null)
				bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
			else
				baos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//
			options -= 5;//
			Log.e("baos.toByteArray().length:", +baos.toByteArray().length + "");
		}
		Log.e("质量压缩后的大小:", (baos.toByteArray().length / 1024) + "");
		byte[] bb = baos.toByteArray();
		return bb;
	}
}
