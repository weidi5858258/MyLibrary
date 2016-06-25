package com.xianglin.station.imageloader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

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
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.Base64;

/**
 * 图片处理类
 * 
 * @author huangyang
 *
 */
public class BitmapUtils {
	private final static int BITMAP_SIZE_MAX = 640 * 640;

	private BitmapUtils() {

	}

	/**
	 * Bitmap转换成ByteArray数组
	 * 
	 * @param bitmap
	 * @param needRecyle
	 * @return
	 */
	public static byte[] bitMapToByteArray(final Bitmap bitmap, final boolean needRecyle) {
		if (bitmap == null)
			return null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 100, output);
		if (needRecyle)
			bitmap.recycle();
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * byte[]数组转换成Bitmap
	 * 
	 * @param byteArray
	 * @return
	 */
	public static Bitmap byteArrayToBitmap(byte[] byteArray) {
		if (byteArray == null)
			return null;
		if (byteArray.length > 0) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			ByteArrayInputStream input = new ByteArrayInputStream(byteArray);
			return BitmapFactory.decodeStream(input, null, options);
		}
		return null;
	}

	/**
	 * 裁剪Bitmap
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap croppingBitmap(Bitmap bitmap) {
		if (bitmap == null)
			return null;
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();
		if (height == width)
			return bitmap;
		final int cropSize = Math.min(width, height);
		final Bitmap result = Bitmap.createBitmap(bitmap, (width - cropSize) >> 1, (height - cropSize) >> 1, width,
				height);
		return result;
	}

	/**
	 * 获取Bitmap的缩略图
	 * 
	 * @param srcFile
	 * @param maxSize
	 * @return
	 */
	public static Bitmap getThumbnailFromFile(String srcFile, int maxSize) {
		if (srcFile == null || srcFile.length() == 0)
			return null;
		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(srcFile, option);
		int width = option.outWidth;
		int height = option.outHeight;
		if (width * height <= maxSize) {
			Bitmap bitmapSmall = BitmapFactory.decodeFile(srcFile);
			return bitmapSmall;
		}
		int ratio = Math.round((float) Math.sqrt((float) width * (float) height / maxSize));

		option.inSampleSize = ratio;
		option.inJustDecodeBounds = false;
		Bitmap bitmapSmall = BitmapFactory.decodeFile(srcFile, option);
		return bitmapSmall;
	}

	/**
	 * 加载Bitmap
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap loadImage(String path) {
		return loadImage(path, BITMAP_SIZE_MAX, null);
	}

	/**
	 * 加载Bitmap
	 * 
	 * @param path
	 * @param bitmapSize
	 * @param bitmapConfig
	 * @return
	 */
	public static Bitmap loadImage(String path, int bitmapSize, Bitmap.Config bitmapConfig) {
		if (path == null)
			return null;
		final File file = new File(path);
		if (!file.exists())
			return null;
		InputStream is1 = null;
		InputStream is2 = null;
		try {
			is1 = new FileInputStream(file);
			final BitmapFactory.Options option = new BitmapFactory.Options();
			option.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is1, null, option);
			try {
				is1.close();
			} catch (IOException e) {
			}
			is1 = null;

			if (option.outWidth > 0 && option.outHeight > 0) {
				option.inJustDecodeBounds = false;
				option.inSampleSize = 1;
				int scaleSize = option.outHeight * option.outWidth;
				while (scaleSize > bitmapSize) {
					option.inSampleSize <<= 1;
					scaleSize >>= 2;
				}
				if (bitmapConfig != null)
					option.inPreferredConfig = bitmapConfig;
				is2 = new FileInputStream(file);
				return BitmapFactory.decodeStream(is2, null, option);
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (is1 != null) {
				try {
					is1.close();
				} catch (Exception e2) {
				}
				is1 = null;
			}
			if (is2 != null) {
				try {
					is2.close();
				} catch (Exception e2) {
				}
				is2 = null;
			}
		}
		return null;
	}

	/**
	 * 保存Bitmap
	 * 
	 * @param bitmap
	 * @param path
	 * @return
	 */
	public static boolean save(Bitmap bitmap, String path) {
		return save(bitmap, path, 100);
	}

	/**
	 * 保存Bitmap
	 * 
	 * @param bitmap
	 * @param path
	 * @param compress
	 * @return
	 */
	public static boolean save(Bitmap bitmap, String path, int compress) {
		if (bitmap == null || path == null) {
			return false;
		}

		if (!FileOperateUtils.isSdCardMounted() || FileOperateUtils.getSdCardFreeSize() < 10 * 1024) {
			return false;
		}

		final int indexFolder = path.lastIndexOf("/");
		if (indexFolder > 0) {
			final String folder = path.substring(0, indexFolder);
			final File folderFile = new File(folder);
			folderFile.mkdirs();
		}

		final File file = new File(path);
		if (file.exists()) {
			final boolean deleted = file.delete();
			if (!deleted) {
				return false;
			}
		}

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			// final boolean hasAlpha = bitmap.hasAlpha();
			return bitmap.compress(CompressFormat.JPEG, compress, fos);
		} catch (final Exception e) {
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (final IOException e) {
				}
				fos = null;
			}
		}
		return false;
	}

	/**
	 * 根据比例创建Bitmap
	 * 
	 * @param bitmap
	 * @param ratio
	 * @return
	 */
	public static Bitmap scaleBitmap(Bitmap bitmap, float ratio) {
		if (bitmap == null || ratio <= 0.0f)
			return null;
		final Matrix matrix = new Matrix();
		matrix.setScale(ratio, ratio);
		final Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return result;
	}

	/**
	 * 缩放图片
	 * 
	 * @param bitmap
	 * @param ps
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, float ps) {
		if (bitmap == null)
			return null;
		final Matrix matrix = new Matrix();
		matrix.postScale(ps, ps);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	/**
	 * 图片四角处理
	 * 
	 * @param bitmap
	 * @param roundPX
	 * @return
	 */
	public static Bitmap getRoundRectangle(Bitmap bitmap, float roundPX) {
		Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(dstbmp);
		final int color = 0Xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return dstbmp;
	}

	/**
	 * 获得带倒影的图片方法
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
				+ reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
		return bitmapWithReflection;
	}

	/**
	 * String 类型转换成Bitmap
	 * 
	 * @param imageInfo
	 * @return
	 */
	public static Bitmap convertStringToBitmap(String imageInfo) {
		if (imageInfo == null)
			return null;
		Bitmap bitmap = null;
		byte[] buffer;
		ByteArrayInputStream bais = null;
		SoftReference<Bitmap> softRef = null;
		try {
			buffer = Base64.decode(imageInfo, Base64.DEFAULT);
			BitmapFactory.Options options = new BitmapFactory.Options();

			options.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);
			options.inSampleSize = calculateSampleSize(options, 600, 800);

			options.inJustDecodeBounds = false;
			bais = new ByteArrayInputStream(buffer);
			softRef = new SoftReference<Bitmap>(BitmapFactory.decodeStream(bais, null, options));
			bitmap = softRef.get();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			imageInfo = null;
			buffer = null;
			if (softRef != null) {
				softRef.clear();
				softRef = null;
			}
			try {
				if (bais != null) {
					bais.close();
					bais = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}

	/**
	 * 计算sampleSize 缩放图片
	 * 
	 * @param options
	 * @param reqHeight
	 * @param reqWidth
	 * @return
	 */
	private static int calculateSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}

	/**
	 * 质量压缩
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap compressByQuality(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) {
			baos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
			options -= 10;
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		Bitmap newBitmap = BitmapFactory.decodeStream(isBm);
		return newBitmap;
	}
}
