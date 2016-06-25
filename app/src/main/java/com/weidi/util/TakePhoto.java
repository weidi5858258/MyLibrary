package com.xianglin.station.util;
/*
 * 拍照
 * @ author 刘昊亮 2015/7/14
 * */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

public class TakePhoto extends Activity{

	private Camera mCamera;
	private Bitmap mBitmap;
	private int bitmapWidth;
	private int bitmapHeight;

	// 准备一个保存图片的pictureCallback对象
	public Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub

			if (camera != null) {
				Toast.makeText(getApplicationContext(), "正在保存...",
						Toast.LENGTH_LONG).show();
				// 用BitmapFactory.decodeByteArray()方法可以把相机传回的裸数据转换成Bitmap对象
				// 这里通过BitmapFactory.Options类指定解码方法
				BitmapFactory.Options options = new BitmapFactory.Options();
				// 在解码图片的时候设置inJustDecodeBounds属性为true，可以避免内存分配
				// options.inJustDecodeBounds = true; 这句话已开启就会死机
				mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
						options);
				bitmapWidth = options.outWidth;
				bitmapHeight = options.outHeight;
				// 把bitmap保存成一个存储卡中的文件
				File file = new File("/sdcard/YY"
						+ new DateFormat().format("yyyyMMdd_hhmmss",
								Calendar.getInstance(Locale.CHINA)) + ".png");
				try {
					file.createNewFile();
					BufferedOutputStream os = new BufferedOutputStream(
							new FileOutputStream(file));
					mBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
					os.flush();
					os.close();
					Toast.makeText(
							getApplicationContext(),
							"图片 " + bitmapWidth + "X" + bitmapHeight
									+ " 保存完毕，在存储卡的根目录", Toast.LENGTH_LONG)
							.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	};

	/**
	 * 拍摄按钮的OnClickListener，在这里执行拍照。
	 */
	class TakePictureBtnOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (mCamera != null) {
				mCamera.takePicture(null, null, mPictureCallback);
			} else {
				Toast.makeText(getApplicationContext(), "Camera对象为空！",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
