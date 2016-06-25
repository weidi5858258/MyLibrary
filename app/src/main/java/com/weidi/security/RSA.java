/**
 * created since 2009-7-20
 */
package com.xianglin.mobile.common.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.xianglin.mobile.common.logging.LogCatLog;

import android.util.Base64;
import android.util.Log;

/**
 * 
 * @author alex
 * 
 */
public class RSA {

	private final static String TAG = "RSA";
	private static final String ALGORITHM = "RSA";

	/**
	 * @param algorithm
	 * @param ins
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws AlipayException
	 */
	private static PublicKey getPublicKeyFromX509(String algorithm, String bysKey) {
		try {
			byte[] decodedKey = Base64.decode(bysKey, Base64.NO_WRAP);
			X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			return keyFactory.generatePublic(x509);
		} catch (Exception e) {
			return null;
		}
	}

	public static String encrypt(String content, String key) {
		if ("".equals(content)) {
			return "";
		}

		try {
			PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, key);
			LogCatLog.i(TAG,"pubkey = "+pubkey);
			if (pubkey == null) {
				return null;
			}
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubkey);
			byte plaintext[] = content.getBytes("UTF-8");
			byte[] output = cipher.doFinal(plaintext);
			String s = new String(Base64.encode(output, Base64.NO_WRAP));
			return s;
		} catch (Exception e) {
			Log.e("RSAHelper", "", e);
			return null;
		}
	}
}
