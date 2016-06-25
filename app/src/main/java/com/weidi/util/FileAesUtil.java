package com.xianglin.station.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.content.Context;

public class FileAesUtil {

	public static String encrypeString(Context context,String str) throws Exception {
		byte[] b = decryptOrEncrypt(Cipher.ENCRYPT_MODE, str.getBytes("utf-8"), NativeEncrypt.nativeEncryptInstance(context).getAesKEY());
		return toHex(b);
	}

	public static String decryptString(Context context,String str) throws Exception {
		byte[] b = decryptOrEncrypt(Cipher.DECRYPT_MODE, toByte(str), NativeEncrypt.nativeEncryptInstance(context).getAesKEY());
		return new String(b);
	}
	public static boolean encryptFile(Context context, String path, boolean isDelete) {
		// 如果已经是加密文件即后缀名是.pa不再操作
		if (path == null || path.endsWith(".pa") || !FileUtils.getInstance().isExists(path)) {
			return false;
		}
		String pwd = NativeEncrypt.nativeEncryptInstance(context).getAesKEY();
		// 应用认证失败
		if (pwd.equals("app fail")) {
			return false;
		}
		String outPath = path + ".pa";
		byte[] bytOut;
		try {
			byte[] bytIn = fileToByte(path, isDelete);
			bytOut = encrype(bytIn, pwd);
			byteToFile(outPath, bytOut);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean decryptFile(Context context, String path,
			boolean isDelete) {
		// 如果已经是加密文件即后缀名是.pa不再操作
		if (path == null || path.endsWith(".pa") || !FileUtils.getInstance().isExists(path)) {
			return false;
		}
		String pwd = NativeEncrypt.nativeEncryptInstance(context).getAesKEY();
		// 应用认证失败
		if (pwd.equals("app fail")) {
			return false;
		}
		String outPath = path + ".pa";
		byte[] bytIn;
		try {
			bytIn = fileToByte(path, isDelete);
			byte[] b = decrypt(bytIn, pwd);
			byteToFile(outPath, b);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	private static byte[] fileToByte(String path, boolean isDelete)
			throws Exception {
		File file1 = new File(path);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file1));
		byte[] bytIn = new byte[(int) file1.length()];
		bis.read(bytIn);
		bis.close();
		if (isDelete)
			file1.delete();
		return bytIn;
	}

	private static void byteToFile(String path, byte[] bytOut) throws Exception {
		File file1 = new File(path);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(file1));
		bos.write(bytOut);
		bos.close();
	}

	private static void encryptfile(Context context, String inputPath,
			String outPath, boolean isDelete) throws Exception {
		byte[] bytIn = fileToByte(inputPath, isDelete);
		String pwd = NativeEncrypt.nativeEncryptInstance(context).getAesKEY();
		byte[] bytOut = encrype(bytIn, pwd);
		byteToFile(outPath, bytOut);
	}

	private static void decryptFile(Context context, String inputPath,
			String outPath, boolean isDelete) throws Exception {
		byte[] bytIn = fileToByte(inputPath, isDelete);
		String pwd = NativeEncrypt.nativeEncryptInstance(context).getAesKEY();
		byte[] b = decrypt(bytIn, pwd);
		byteToFile(outPath, b);
	}

	private static void encryptfile(String pwd, String inputPath,
			String outPath, boolean isDelete) throws Exception {
		byte[] bytIn = fileToByte(inputPath, isDelete);
		byte[] bytOut = encrype(bytIn, pwd);
		byteToFile(outPath, bytOut);
	}

	private static void decryptFile(String pwd, String inputPath,
			String outPath, boolean isDelete) throws Exception {
		byte[] bytIn = fileToByte(inputPath, isDelete);
		byte[] b = decrypt(bytIn, pwd);
		byteToFile(outPath, b);
	}


	private static byte[] encrype(byte[] bytIn, String pwd) throws Exception {
		return decryptOrEncrypt(Cipher.ENCRYPT_MODE, bytIn, pwd);
	}

	private static byte[] decrypt(byte[] bytIn, String pwd) throws Exception {
		return decryptOrEncrypt(Cipher.DECRYPT_MODE, bytIn, pwd);
	}

	private static byte[] decryptOrEncrypt(int mode, byte[] content,
			String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			//SecureRandom sr = new SecureRandom(password.getBytes()); 
	        //4.1以上
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");  
			sr.setSeed(password.getBytes());
			kgen.init(128,sr);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			//4.3
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			cipher.init(mode, key);// 初始化
			cipher.init(mode, key, new IvParameterSpec(
					new byte[cipher.getBlockSize()]));
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}
	private static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private static final String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
}
