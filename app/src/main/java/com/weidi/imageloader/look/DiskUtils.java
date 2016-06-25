package com.xianglin.station.imageloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 磁盘文件操作
 * 
 * @author huangyang
 *
 */
public class DiskUtils {
	/**
	 * 创建folder
	 * 
	 * @param path
	 */
	public static void createFolders(String path) {
		if (path == null)
			return;
		int index = path.lastIndexOf("/");
		if (index > 0) {
			String folders = path.substring(0, index);
			final File file = new File(folders);
			file.mkdir();
		}
	}

	/**
	 *移动文件
	 * 
	 * @param srcPath
	 * @param dstPath
	 * @return
	 */
	public static boolean moveFile(String srcPath, String dstPath) {
		final File file = new File(srcPath);
		if (!file.exists())
			return false;
		createFolders(dstPath);
		File newFile = new File(dstPath);
		if (newFile.exists())
			newFile.delete();
		return file.renameTo(newFile);
	}

	/**
	 * 复制文件
	 * 
	 * @param srcPath
	 * @param dstPath
	 * @return
	 */
	public static boolean copyFile(String srcPath, String dstPath) {
		final File file = new File(srcPath);
		if (!file.exists())
			return false;
		createFolders(dstPath);
		File newFile = new File(dstPath);
		if (newFile.exists())
			newFile.delete();
		try {
			InputStream is = new FileInputStream(file);
			OutputStream os = new FileOutputStream(newFile);
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) > 0) {
				os.write(buffer);
			}
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
