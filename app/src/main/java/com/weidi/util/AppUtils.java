package com.xianglin.station.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.widget.Toast;

import com.xianglin.mobile.common.logging.LogCatLog;
import com.xianglin.station.R;


/**
 * @Description 一些配置和方法
 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
 * @date 2014-4-16 10:21:31
 * @update 2014-6-28 17:12:57
 */
public class AppUtils {
	private final static String TAG = "APP";
	/** 图片加载失败时，展示的图片 **/
//	public static int IMG_LOAD_FAILD = R.drawable.img_load_failed;
//	public static int IMG_LOAD_FAILD_SHORT = R.drawable.img_load_failed_short;
//	/** 图片加载中，显示的占位图片 **/
//	public static int IMG_LOAD_HOLDER = R.drawable.img_load_holder;
//	public static int IMG_LOAD_HOLDER_SHORT = R.drawable.img_load_holder_short;

	public static String formatSqlValue(String value) {
		return value.replaceAll("'", "''");
	}

	public static String sImplode(Object ids) {
		return "'" + implode(ids, "','") + "'";
	}

	public static void copyto(Object one, Object two) throws Exception {
		Field[] fields = two.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				if (!Modifier.isFinal(field.getModifiers()))
					FieldUtil.setValueToFiled(field, two, FieldUtil.getFieldValue(field, one).toString());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Description 根据图片尺寸，屏幕边距计算出 填充控件时该图片需要的高度
	 * @param imgW
	 *            图片宽
	 * @param imgH
	 *            图片高
	 * @param subtractW
	 *            除去距离（dp）
	 */
	public static int getImgSimpleHeight(Context context, int imgW, int imgH, int subtractW) {
		return (int) (Sys.getScreenWidth(context) - Sys.Dp2Px(context, subtractW)) * imgH / imgW;
	}

	/** 得到一个 max 以内的随机数 */
	public static int getOneRandomNum(int max) {
		int num = 0;
		int a[] = new int[max];
		for (int i = 0; i < max; i++) {
			a[i] = (int) (Math.random() * max);
		}
		num = a[a.length - 1];
//		CustomLog.i(TAG,"num = " + num);
		return num;
	}

	/**
	 * 产生一个最大为max随机数
	 */
	public static String getRandomNum(int max) {
		Random rad = new Random();
		return String.valueOf(rad.nextInt(max + 1));
	}


	/**
	 * 数组转为字符串
	 */
	@SuppressWarnings("unchecked")
	public static String implode(Object data, String separator) {
		if (data == null) {
			return "";
		}
		StringBuffer out = new StringBuffer();
		if (data instanceof Object[]) {
			boolean flag = false;
			for (Object obj : (Object[]) data) {
				if (flag) {
					out.append(separator);
				} else {
					flag = true;
				}
				out.append(obj);
			}
		} else if (data instanceof Map) {
			Map<Object, ?> temp = (Map<Object, ?>) data;
			Set<Object> keys = temp.keySet();
			boolean flag = false;
			for (Object key : keys) {
				if (flag) {
					out.append(separator);
				} else {
					flag = true;
				}
				out.append(temp.get(key));
			}
		} else if (data instanceof Collection) {
			boolean flag = false;
			for (Object obj : (Collection) data) {
				if (flag) {
					out.append(separator);
				} else {
					flag = true;
				}
				out.append(obj);
			}
		} else {
			return data.toString();
		}
		return out.toString();
	}

	//其实也用不着对返回的json字符串做这样的处理
	public static JSONObject checkReturnData(Context context, String jsonString) {
		return checkReturnData(context, jsonString, true);
	}
	public static JSONObject checkReturnData(Context context, String jsonString, boolean tip) {
		JSONObject object = null;
		if (!AppUtils.empty(jsonString)) {
			try {
				object = new JSONObject(jsonString);
				if (object.has("status") && object.getInt("status") == -1) {
					Toast.makeText(context, "网络状态不佳，请稍后再试", Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				try {
					new JSONArray(jsonString);
				} catch (Exception e2) {
					if (context != null && tip) {
						// 返回的结果不是json
					    //APP.tip(context, "数据异常");
					}
				}
			}
		} else {
			if (context != null && tip) {
				 Toast.makeText(context, "请检查您的数据是否合理", Toast.LENGTH_LONG).show();
			}
		}
		return object;

	}

	/** 对常见类型判断是否为空 */
	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && (obj.equals("") || obj.equals("0"))) {
			return true;
		} else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)) {
			return true;
		} else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 通过 url 下载 bmp图片 <br>
	 * 获取图片失败时，默认用 logo
	 */
	public static Bitmap getBmpFromUrl(Context context, String imageUri) {
		Bitmap bitmap = null;
		try {
			URL myFileUrl = new URL(imageUri);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
		}
		return bitmap;
	}

	/**
	 * @Description 把输入流转化成字符串
	 */
	public static String InputStream2String(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toString();
	}

	/**
	 * @Description 校验是否是手机号（13、14、15、18 xxxx）
	 */
	public static boolean isPhoneNO(String no) {
		Pattern p = Pattern.compile("^1[3|4|5|8]\\d{9}$");
		Matcher m = p.matcher(no);
		return m.matches();
	}

	/**
	 * 保存bmp到本地（分享到微信时用）
	 */
	public static String saveBmp2SDCard(Bitmap bitmap, String timestamp) throws IOException {
		String path = FileUtils.IMG_SHARE_PATH + "/share_" + timestamp + ".jpg";
		File file = new File(path);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 保存bmp到本地指定目录
	 */
	public static void saveBmp2SDPath(Bitmap bitmap, String path) throws IOException {
		File file = new File(path);
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据文件路径保存bmp
	 */
	public static Bitmap saveBmp2SDByPath(String filePath, int w, int h) {
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(filePath, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int destWidth = 0;
			int destHeight = 0;
			double ratio = 0.0;
			if (srcWidth < w || srcHeight < h) {
				ratio = 0.0;
				destWidth = srcWidth;
				destHeight = srcHeight;
			} else if (srcWidth > srcHeight) {
				ratio = (double) srcWidth / w;
				destWidth = w;
				destHeight = (int) (srcHeight / ratio);
			} else {
				ratio = (double) srcHeight / h;
				destHeight = h;
				destWidth = (int) (srcWidth / ratio);
			}
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inSampleSize = (int) ratio + 1;
			newOpts.inJustDecodeBounds = false;
			newOpts.outHeight = destHeight;
			newOpts.outWidth = destWidth;
			return BitmapFactory.decodeFile(filePath, newOpts);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 拍照后图片的保存路径
	 */
	public static String getPicTempPath() {
		String saveDir = FileUtils.IMG_SAVE_PATH_CAP_TEMP;
		try {
			File dir = new File(saveDir);
			if (!dir.exists())
				dir.mkdir();
		} catch (Exception e) {
		}
		return saveDir + "/" + MyDate.getTimestamp() + ".png";
	}

	/**
	 * 裁剪后的图片路径
	 */
	public static String getPicCutPath() {
		String saveDir = FileUtils.IMG_SAVE_PATH_CAP_CUT;
		try {
			File dir = new File(saveDir);
			if (!dir.exists())
				dir.mkdir();
		} catch (Exception e) {
		}
		return saveDir + "/" + "pic_" + MyDate.getTimestamp() + ".png";
	}

	/**
	 * 读取图片的旋转的角度 (某些机型拍照后图片是旋转过的)
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return 图片的旋转角度
	 */
	public static int getBmpDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 将图片按照某个角度进行旋转
	 * 
	 * @param bm
	 *            需要旋转的图片
	 * @param degree
	 *            旋转角度
	 * @return 旋转后的图片
	 */
	public static Bitmap rotateBmpByDegree(Bitmap bitmap, int degree) {
		Bitmap bmp = null;
		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);
		try {
			// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
			bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		} catch (OutOfMemoryError e) {
		}
		if (bmp == null) {
			bmp = bitmap;
		}
		if (bitmap != bmp) {
			bitmap.recycle();
		}
		return bmp;
	}

	/** -----------------------服务器 路径------------------------- */
	// TODO 服务器路径配置
	public static String host = "http://192.168.1.4:8080/Dress";
	static String imgholder = "http://192.168.1.4:8080/Dress/upload/failed/img_load_holder.png";
	static String imgholder_short = "http://192.168.1.4:8080/Dress/upload/failed/img_load_holder_short.png";

	/** 服务器根地址 */
	public static String domains(String relativeurl) {
		return host + "/html/" + relativeurl;
	}

	/**
	 * @Description 购物车地址
	 */
	public static String cart = host + "/widget.html";

	/** 用户头像地址 */
	public static String head_path(String img) {
		LogCatLog.i(TAG,"用户头像 = " + img);//
		if (img.isEmpty() || img.equals("null"))
			return host + "/img/spdet_30.gif";// 默认头像
		if (checkUrl(img)) {
			return img;
		}
		return img_path(img);
	}

	/** 幻灯片广告图片地址 */
	public static String adv_path(String img) {
		if (img.isEmpty() || img.equals("null"))
			return imgholder;
		if (checkUrl(img)) {
			return img;
		}
		return host + "/upload/adv/" + img;
	}

	public static String women_street_path(String storeID, String img) {
		if (img.isEmpty() || img.equals("null"))
			return imgholder;
		if (checkUrl(img)) {
			return img;
		}
		// return host + "/merchants/" + storeID + "/" + img;
		return host + "/merchants/" + img;
	}

	public static String goods_path(String img) {
		if (img.isEmpty() || img.equals("null"))
			return imgholder;
		if (checkUrl(img)) {
			return img;
		}
		return host + "/upload/goods/" + img;
	}

	public static String beauty_proclaim_path(String img) {
		if (img.isEmpty() || img.equals("null"))
			return imgholder;
		if (checkUrl(img)) {
			return img;
		}
		return host + "/upload/issue/" + img;
	}

	/** 缺省图片地址 */
	public static String img_path(String img) {
		if (checkUrl(img)) {
			return img;
		}
		return host + "/upload/head/pic_1435821460.png";
	}

	public static Boolean checkUrl(String fukingUrl) {
		if (fukingUrl.length() > 8 && fukingUrl.substring(0, 7).equals("http://"))
			return true;
		return false;
	}

}
