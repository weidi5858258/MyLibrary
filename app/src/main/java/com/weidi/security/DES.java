package com.xianglin.mobile.common.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;

/**
 * 
 * @author alex
 *
 */
public class DES {
	
	/**
	 *  String result = DES.doFinal(Cipher.ENCRYPT_MODE, "weidi", "rDIwjx1Q");//加密
		//result = Wz8wWSc2T2c=
		System.out.println("result = "+result);
		String resource = DES.doFinal(Cipher.DECRYPT_MODE, "Wz8wWSc2T2c=", "rDIwjx1Q");//解密
		System.out.println("resource = "+resource);
	 * 
	 */
    public static String encrypt(String content, String strkey) {
        if (content.equals("")) {
            return "";
        } else
            return doFinal(Cipher.ENCRYPT_MODE, content, strkey);
    }
    
    public static byte[] encrypt(byte[] content, String strkey) {
    	if (null == content || content.length == 0) {
    		return null;
    	} else
    		return doFinal(Cipher.ENCRYPT_MODE, content, strkey);
    }

    public static String decrypt(String content, String strkey) {
        if (content.equals("")) {
            return "";
        } else
            return doFinal(Cipher.DECRYPT_MODE, content, strkey);
    }
    
    public static byte[] decrypt(byte[] content, String strkey) {
        if (null == content || content.length == 0) {
            return null;
        } else
            return doFinal(Cipher.DECRYPT_MODE, content, strkey);
    }

    public static String doFinal(int opmode, String content, String strkey) {
        try {
            Key key = new SecretKeySpec(strkey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(opmode, key);

            // make input
            byte plaintext[] = null;
            if (opmode == Cipher.DECRYPT_MODE)
                plaintext = Base64.decode(content, Base64.DEFAULT);
            else
                plaintext = content.getBytes("UTF-8");

            byte[] output = cipher.doFinal(plaintext);

            // make output
            String Ciphertext = null;
            if (opmode == Cipher.DECRYPT_MODE)
                Ciphertext = new String(output);
            else
                Ciphertext = Base64.encodeToString(output, Base64.DEFAULT);

            return Ciphertext;

        } catch (Exception e) {
            Log.w("Des", "opmode=" + opmode, e);

            return null;
        }
    }
    
    public static byte[] doFinal(int opmode, byte[] content, String strkey) {
        try {
            Key key = new SecretKeySpec(strkey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(opmode, key);

            // make input
            byte plaintext[] = null;
            if (opmode == Cipher.DECRYPT_MODE)
                plaintext = Base64.decode(content, Base64.DEFAULT);
            else
                plaintext = content;

            byte[] output = cipher.doFinal(plaintext);

            // make output
            byte[] Ciphertext = null;
            if (opmode == Cipher.DECRYPT_MODE)
                Ciphertext = output;
            else
                Ciphertext = Base64.encode(output, Base64.DEFAULT);

            return Ciphertext;

        } catch (Exception e) {
            Log.w("Des", "opmode=" + opmode, e);
            
            return null;
        }
    }
}

