package com.xianglin.station.imageloader;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;

import com.xianglin.mobile.common.logging.LogCatLog;

import android.os.Environment;
import android.os.StatFs;

/**
 * 文件操作
 * 
 * @author huangyang
 *
 */
public class FileOperateUtils {
	// 应用程序目录
	private static final String APPPATH = Environment.getExternalStorageDirectory() + "/";
	private static final String SAVE_IMAGE_FOLDER = "XiangLin/downloadimagedemo/image01/";
	private static final String SAVE_PDF_FOLDER = "XiangLin/pdf/";
	private static final String ZHENG_JIAN_FOLDER = "XiangLin/CertificatImg/";

	// sd卡用于存储图片缓存
	private static String appImageCachePath;
	private static String sdcardPath;

	/**
	 * 创建缓存目录
	 */
	public static void createAppFolder() {
		// 判断sdcard是否挂在可用
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return;
		}

		final File fileSdCardDir = Environment.getExternalStorageDirectory();
		sdcardPath = fileSdCardDir.getParent() + File.separator + fileSdCardDir.getName() + File.separator;
		appImageCachePath = sdcardPath + SAVE_IMAGE_FOLDER;
		final File appImageCacheFilePath = new File(appImageCachePath);
		if (!appImageCacheFilePath.exists()) {
			appImageCacheFilePath.mkdirs();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 */
	public static void deleteFile(String filePath) {
		if (filePath == null)
			return;
		final File file = new File(filePath);
		if (!file.exists())
			return;
		if (file.isFile())
			file.delete();
	}

	/**
	 * 删除文件夹
	 * 
	 * @param dirPath
	 */
	public static void deleteFolder(String dirPath) {
		// 删除文件夹中的文件
		deleteFolderFile(dirPath);
		// 删除文件夹
		File file = new File(dirPath);
		file.delete();
	}

	/**
	 * 删除文件夹中的文件
	 * 
	 * @param dirPath
	 */
	public static void deleteFolderFile(String dirPath) {
		if (dirPath == null)
			return;
		final File file = new File(dirPath);
		if (!file.exists())
			return;
		if (!file.isDirectory())
			return;
		final String[] tempList = file.list();
		File tempFile = null;
		for (int i = 0; i < tempList.length; i++) {
			if (dirPath.endsWith(File.separator))
				tempFile = new File(dirPath + tempList[i]);
			else
				tempFile = new File(dirPath + File.separator + tempList[i]);
			if (tempFile.isFile())
				tempFile.delete();
			if (tempFile.isDirectory())
				deleteFolder(dirPath.endsWith(File.separator) ? dirPath + tempList[i] : dirPath + File.separator + tempList[i]);
		}
	}

	/**
	 * 获取图片缓存路径
	 * 
	 * @return
	 */
	public static String getAppImageCachePath() {
		if (appImageCachePath == null)
			createAppFolder();
		return appImageCachePath;
	}

	/**
	 * 获取sdcard路径
	 * 
	 * @return
	 */
	public static String getSdCardPath() {
		if (sdcardPath == null)
			createAppFolder();
		return sdcardPath;
	}

	/**
	 * 获取引用程序路径
	 * 
	 * @return
	 */
	public static String getAppPath() {
		return APPPATH;
	}

	/**
	 * 获取pdf文件路径
	 * 
	 * @return
	 */
	public static String getPdfFolder() {
		return getAppPath() + File.separator + SAVE_PDF_FOLDER;
	}

	/**
	 * 获取证件路径
	 * 
	 * @return
	 */
	public static String getCertificateImg() {
		return getAppPath() + File.separator + ZHENG_JIAN_FOLDER;
	}

	/**
	 * 获取sdcard可用空间大小
	 * 
	 * @return
	 */
	public static long getSdCardFreeSize() {
		if (!isSdCardMounted())
			return 0;
		// 创建文件夹
		createAppFolder();
		final File sdcardDir = Environment.getExternalStorageDirectory();
		final StatFs sf = new StatFs(sdcardDir.getPath());
		final int availableBlocks = sf.getAvailableBlocks();
		final int blockSize = sf.getBlockSize();
		return availableBlocks * blockSize / 1024;
	}

	/**
	 * 判断sdcard是否挂载可用
	 * 
	 * @return
	 */
	public static boolean isSdCardMounted() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}

	/**
	 * 获取文件名称
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		if (path == null)
			return null;
		// String tempName = path.substring(path.lastIndexOf("\\") + 1);
		final File file = new File(path);
		return file.getName();
	}

	/**
	 * 检测磁盘中是否存在文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isExist(String filePath) {
		if (filePath == null)
			return false;
		final File file = new File(filePath);
		if (!file.exists())
			return false;
		return true;
	}

	/**
	 * 将字节数组保存为文件
	 * 
	 * @param byteArray
	 */
	public static boolean saveByteToFile(byte[] byteArray, String path, String fileName) {
		if (byteArray == null)
			return false;
		if (byteArray.length > 0) {
			File file = null;
			BufferedOutputStream bos = null;
			FileOutputStream fos = null;
			try {
				File dir = new File(path + File.separator);
				if (!dir.exists()) {
					dir.mkdir();
				}
				file = new File(path + fileName);
				if (file.exists())
					file.delete();
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos);
				bos.write(byteArray);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return true;
	}

	/**
	 * 将文件转换成byte[]数组
	 * 
	 * @param path
	 * @return
	 */
	public static byte[] fileToByte(String path) {
		if (null == path || path == "")
			return null;
		final File file = new File(path);
		if (file.isDirectory() || !file.exists())
			return null;
		FileInputStream fs = null;
		ByteArrayOutputStream bos = null;
		byte[] buffer = new byte[10 * 1000];
		byte[] buffResult = null;
		int len;
		try {
			fs = new FileInputStream(file);
			bos = new ByteArrayOutputStream(buffer.length);
			while ((len = fs.read(buffer)) != -1) {
				bos.write(buffer, 0, len);
			}
			buffResult = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return buffResult;
	}
}
