package com.xianglin.station.util;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xianglin.station.XLApplication;
import com.xianglin.station.activity.BaseActivity;
import com.xianglin.station.dialog.ConfirmDialog;
import com.xianglin.station.dialog.ConfirmDialog.OnDismissListener;

/**
 * @Description
 * @author <a href=
 *         "http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=ceychen%40foxmail.com&from=url&k=7QJWxu1k&subject=from_eclipse&nogetbtn=1"
 *         >ceychen</a>
 * @update 2014-6-3 10:55:07
 */
public class Sys {

	private static Object curNetWorkLock = new Object();

	public static int getMemorySize() {
		return (int) (Runtime.getRuntime().maxMemory() / 1024) / 1024;
	}

	public static String getAppPath() {
		return Environment.getDataDirectory().getPath();
	}

	public static String getRootPath() {
		return Environment.getRootDirectory().getPath();
	}

	public static String getSDCardPath() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return Environment.getExternalStorageDirectory().getPath();
		else
			return null;
	}

	public static boolean fileExist(String file) {
		File f = new File(file);
		if (f.exists())
			return true;
		return false;
	}

	/**
	 * 得到设备屏幕的宽度
	 */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 得到设备屏幕的高度
	 */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}
	
	/**
	 * @Description 每一个Tab的宽（90dp）
	 */
	public static int getItemWidth(Context context) {
		return Dp2Px(context, 250);
	}

	/**
	 * @Description Slide line img 的宽（80dp）
	 */
	public static int getSlideWidth(Context context) {
		return Dp2Px(context, 80);
	}

	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		return (int) (spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
	}

	/**
	 * @Description 复制文本到剪贴板
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public static void copy(String text, Context context) {
		ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
		cmb.setPrimaryClip(ClipData.newPlainText("hehe", text));
		Toast.makeText(context, "复制成功", Toast.LENGTH_LONG).show();
	}

	/**
	 * 检测SDcard是否存在
	 */
	public static boolean isSDcardExist() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return true;
		return false;
	}

	/**
	 * 判断文件是否已经存在
	 */
	public static boolean checkFileExists(String filepath) {
		File file = new File(FileUtils.SDPATH + filepath);
		return file.exists();
	}

	/**
	 * 在SD卡创建个目录，输入文件夹名即可，<br>
	 * Only the folder name, not full path. like： ceychen/save/
	 */
	public static File createDir(String dirpath) {
		File dir = new File(FileUtils.SDPATH + dirpath);
		dir.mkdir();
		Log.i("Sys", "正在创建目录..." + FileUtils.SDPATH + dirpath);
		return dir;
	}

	/**
	 * 在SD卡创建个目录，输入完整路径<br>
	 * full path. like：/ <em>sdcard</em> /ceychen/save/<br>
	 * 
	 */
	public static File createDirWithOutSDPath(String dirpath_full) {
		File dir = new File(dirpath_full);
		dir.mkdir();
		Log.i("Sys", "正在创建目录..." + dirpath_full);
		return dir;
	}

	/**
	 * 获取文件夹大小(返回 KB)
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0; // byte
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size * 1024 / 1048576;
	}

	/**
	 * 删除指定目录下文件及目录
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) throws IOException {
		if (!TextUtils.isEmpty(filePath)) {
			File file = new File(filePath);

			if (file.isDirectory()) {// 处理目录
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFolderFile(files[i].getAbsolutePath(), true);
				}
			}
			if (deleteThisPath) {
				if (!file.isDirectory()) {// 如果是文件，删除
					file.delete();
				} else {// 目录
					if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
						file.delete();
					}
				}
			}
		}
	}

	/**
	 * @Description 删除某文件
	 */
	public static void delFile(String file) {
		File delFile = new File(file);
		delFile.delete();
	}

	/**
	 * @Description 删除某目录下所有文件
	 */
	public static void deleteAllFilesOfDir(String path, boolean flag) {
		try {
			File file = new File(path);
			deleteAllFilesOfDir(file, flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description 删除某目录下所有文件
	 */
	public static void deleteAllFilesOfDir(File path, boolean flag) {
		if (!path.exists())
			return;
		if (path.isFile()) {
			path.delete();
			return;
		}
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			deleteAllFilesOfDir(files[i], true);
		}
		if (flag)
			path.delete();
	}

	public static String getFileName(String path) {
		if (path.indexOf("/") < 0) {
			return path;
		}
		return path.trim().substring(path.lastIndexOf("/") + 1);
	}

	public static String md5(String str) {
		return MD5Util.encode(str);
	}

	/**
	 * 网络检测
	 * 
	 * @date 2014-6-3 10:54:23
	 * @author <a href=
	 *         "http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=ceychen%40foxmail.com&from=url&k=7QJWxu1k&subject=from_eclipse&nogetbtn=1"
	 *         >ceychen</a>
	 */
	public static boolean CheckNetworkState(Context context, boolean showTip) {
		synchronized (curNetWorkLock) {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo typeMobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			State mobile = null;
			if (typeMobile != null) {
				mobile = typeMobile.getState();
			}
			NetworkInfo typeWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			State wifi = null;
			if (typeWifi != null) {
				wifi = typeWifi.getState();
			}
			// 如果3G、wifi、2G等网络状态是连接的，则退出，否则显示提示信息进入网络设置界面
			if ((mobile == State.CONNECTED) || mobile == State.CONNECTING || wifi == State.CONNECTED
					|| wifi == State.CONNECTING)
				return true;
			else {
				if (showTip)
					Toast.makeText(context, "当前网络不可用", Toast.LENGTH_LONG).show();
				return false;
			}
		}
	}

	/**
	 * 询问是否设置网络
	 * 
	 * @date 2014-6-3 15:30:01
	 * @author <a href=
	 *         "http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&email=ceychen%40foxmail.com&from=url&k=7QJWxu1k&subject=from_eclipse&nogetbtn=1"
	 *         >ceychen</a>
	 */
	public static void confirmSetNetwork(final Context context) {
		ConfirmDialog dialog = new ConfirmDialog(context, "设置网络", "当前网络不可用，需要去设置吗？", "设置");
		dialog.setOnDismissListener(new OnDismissListener() {
			@Override
			public void OnConfirmed(Boolean confirmed) {
				if (confirmed) {
					context.startActivity(new Intent(Settings.ACTION_SETTINGS));
				} else {
					APPManager.getInstance().exit();
				}
			}
		});
		dialog.show();
	}
}
