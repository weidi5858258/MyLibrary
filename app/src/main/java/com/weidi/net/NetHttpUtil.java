package com.c1tech.dress.net;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import com.c1tech.dress.activity.BaseActivity;
import com.c1tech.dress.util.Sys;

import android.text.TextUtils;
import android.util.Log;


public class NetHttpUtil {

	public static String getContentType(String fileName) {
		return null;
	}

	public static String getContentType(File file) {
		return null;
	}

	/**
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @return param list
	 */
	public static List<NetParam> getQueryParameters(String queryString) {
		if (queryString.startsWith("?")) {
			queryString = queryString.substring(1);
		}

		List<NetParam> result = new ArrayList<NetParam>();

		if (queryString != null && !queryString.equals("")) {
			String[] p = queryString.split("&");
			for (String s : p) {
				if (s != null && !s.equals("")) {
					if (s.indexOf('=') > -1) {
						String[] temp = s.split("=");
						if (temp.length == 2) {
							result.add(new NetParam(temp[0], temp[1]));
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Convert %XX
	 * 
	 * @param value
	 * @return
	 */
	public static String formParamDecode(String value) {
		int nCount = 0;
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == '%') {
				i += 2;
			}
			nCount++;
		}

		byte[] sb = new byte[nCount];

		for (int i = 0, index = 0; i < value.length(); i++) {
			if (value.charAt(i) != '%') {
				sb[index++] = (byte) value.charAt(i);
			} else {
				StringBuilder sChar = new StringBuilder();
				sChar.append(value.charAt(i + 1));
				sChar.append(value.charAt(i + 2));
				sb[index++] = Integer.valueOf(sChar.toString(), 16).byteValue();
				i += 2;
			}
		}
		String decode = "";
		try {
			decode = new String(sb, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return decode;
	}

	public static boolean isEmpty(String str) {
		if (null == str || str.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static Map<String, String> splitResponse(String response) {
		Map<String, String> map = new HashMap<String, String>();
		if (!NetHttpUtil.isEmpty(response)) {
			String[] array = response.split("&");
			if (array.length > 2) {
				String tokenStr = array[0]; // oauth_token=xxxxx
				String secretStr = array[1];// oauth_token_secret=xxxxxxx
				String[] token = tokenStr.split("=");
				if (token.length == 2) {
					map.put("oauth_token", token[1]);
				}
				String[] secret = secretStr.split("=");
				if (secret.length == 2) {
					map.put("oauth_token_secret", secret[1]);
				}
			}
		}
		return map;
	}

	public static String download(String stringurl) {
		String imageURl = null;
		Log.i("url:", stringurl);
		try {
			URL url = new URL(stringurl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");
			con.setDoInput(true);
			con.connect();
			String content_encode = con.getContentEncoding();
			BufferedOutputStream bos = null;
			imageURl = BaseActivity.FINAL_USER_IMAGE_PATH + Sys.md5(url.toString()) + ".png";
			bos = new BufferedOutputStream(new FileOutputStream(imageURl));
			byte[] buf = new byte[1024];
			int len = 0;
			int count = 0;
//			if (con.getContentLength() > 0)
//				BaseActivity.Log_info("image size:" + con.getContentLength());
			InputStream in = con.getInputStream();
			if (!TextUtils.isEmpty(content_encode) && content_encode.equals("gzip")) {
				GZIPInputStream zipin = new GZIPInputStream(in);
				if (zipin != null) {
					while ((len = zipin.read(buf)) > 0) {
						bos.write(buf, 0, len);
						count += len;
					}
					bos.flush();
					bos.close();
					zipin.close();
					return imageURl;
				}
			}
			
			while ((len = in.read(buf)) > 0) {
				bos.write(buf, 0, len);
				count += len;
			}
			bos.flush();
			bos.close();
			in.close();
		} catch (Exception e) {
			return null;
		}
		return imageURl;
	}

	public static byte[] download2(String stringurl) {
		try {
			URL url = new URL(stringurl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate");
			con.setDoInput(true);
			con.connect();
			String content_encode = con.getContentEncoding();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len = 0;
			int count = 0;
//			if (con.getContentLength() > 0)
//				BaseActivity.Log_info("image size:" + con.getContentLength());

			if (!TextUtils.isEmpty(content_encode) && content_encode.equals("gzip")) {
				GZIPInputStream zipin = new GZIPInputStream(con.getInputStream());
				if (zipin != null) {
					while ((len = zipin.read(buf)) > 0) {
						bos.write(buf, 0, len);
						count += len;
					}
					bos.flush();
					bos.close();
					zipin.close();
					return bos.toByteArray();
				}
			}
			InputStream in = con.getInputStream();
			while ((len = in.read(buf)) > 0) {
				bos.write(buf, 0, len);
				count += len;
			}
			bos.flush();
			bos.close();
			in.close();
			return bos.toByteArray();
		} catch (Exception e) {
			return null;
		}
	}

}
