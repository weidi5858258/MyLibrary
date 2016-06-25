package com.c1tech.dress.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.c1tech.dress.activity.BaseActivity;
import com.c1tech.dress.net.NetHttpClient.OnProgressListener;

public class NetRequest {

	public NetRequest() {}

	private long totalsiaze;

	public long getTotalsiaze() {
		return totalsiaze;
	}

	/**
	 * 
	 * @Description 执行请求
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @param url
	 * @param httpMethod
	 *            (POST,GET,PUT, etc)
	 * @param listParam
	 *            parameters
	 * @param listFile
	 *            要post 的文件
	 */

	public String syncRequest(String url, String httpMethod, List<NetParam> listParam, List<NetParam> listFile) {
		return syncRequest(url, httpMethod, listParam, listFile, 0);
	}

	public String syncRequest(String url, 
							  String httpMethod, 
							  List<NetParam> listParam, 
							  List<NetParam> listFile,
							  int timeout, 
							  OnProgressListener listener) {
		if (url == null || "".equals(url)) {
			Log.i("url", "error url:null");
			return null;
		}
		String reValue = null;
		try {
			StringBuffer sbQueryString = new StringBuffer();
			String queryString = formEncodeParameters(listParam);
			NetHttpClient http = new NetHttpClient();
			if ("GET".equals(httpMethod)) {
				reValue = http.httpGet(url, queryString, timeout);
			} else if ((listFile == null) || (listFile.size() == 0)) {
				reValue = http.httpPost(url, queryString, timeout);
			} else {
				reValue = http.httpPostWithFile(url, queryString, listFile, timeout, listener);
				totalsiaze = http.getTotalsiaze();
			}
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			try {
				json.put("status", "-1");
				reValue = json.toString();
			} catch (JSONException e1) {
			}
			e.printStackTrace();
		}
		return reValue;
	}

	public String syncRequest(String url, 
							  String httpMethod, 
							  List<NetParam> listParam, 
							  List<NetParam> listFile,
							  int timeout) {
		if (url == null || "".equals(url)) {
			System.out.println("NetRequest-syncRequest()-error url:null");
			return null;
		}
		String reValue = null;
		try {
			StringBuffer sbQueryString = new StringBuffer();
			String queryString = formEncodeParameters(listParam);
			NetHttpClient http = new NetHttpClient();
			if ("GET".equals(httpMethod)) {
				reValue = http.httpGet(url, queryString, timeout);
			} else if ((listFile == null) || (listFile.size() == 0)) {
				reValue = http.httpPost(url, queryString, timeout);
			} else {
				reValue = http.httpPostWithFile(url, queryString, listFile, timeout);
			}
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			try {
				json.put("status", "-1");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			reValue = json.toString();
			e.printStackTrace();
		}
		return reValue;
	}

	/**
	 * Do async request
	 * 
	 * @param url
	 *            The full url that needs to be signed including its non OAuth
	 *            url parameters
	 * @param httpMethod
	 *            The http method used. Must be a valid HTTP method verb
	 *            (POST,GET,PUT, etc)
	 * @param key
	 *            OAuth key
	 * @param listParam
	 *            Query parameters
	 * @param listFile
	 *            Files for post
	 * @param asyncHandler
	 *            The async handler
	 * @param cookie
	 *            Cookie response to handler
	 * @return
	 */
	public boolean asyncRequest(Context ctx, 
								String url, 
								String httpMethod, 
								List<NetParam> listParam,
								List<NetParam> listFile, 
								NetAsyncHandler asyncHandler, 
								Object cookie) {
		boolean reValue = false;
		try {
			String queryString = formEncodeParameters(listParam);
			NetAsyncHttpClient asyncHttp = new NetAsyncHttpClient();
			if ("GET".equals(httpMethod)) {
				reValue = asyncHttp.httpGet(url, queryString, asyncHandler, cookie);
			} else if ((listFile == null) || (listFile.size() == 0)) {
				reValue = asyncHttp.httpPost(url, queryString, asyncHandler, cookie);
			} else {
				reValue = asyncHttp.httpPostWithFile(url, queryString, listFile, asyncHandler, cookie);
			}
		} catch (Exception e) {
//			BaseActivity.Tip("数据异常");
		}
		return reValue;
	}

	/**
	 * 把每个value的值编码成"UTF-8"格式（为什么不在生成NetParam对象时就把它编码成"UTF-8"格式呢？？？）
	 * Encode each parameters in list.
	 * 
	 * @param parameters
	 *            List of parameters
	 * @return Encoded parameters
	 */
	private String formEncodeParameters(List<NetParam> parameters) {
		List<NetParam> encodeParams = new ArrayList<NetParam>();
		for (NetParam a : parameters) {
			if (a.mValue == null) {
				System.out.println(a.mName + " value null");
			}
			encodeParams.add(new NetParam(a.mName, encode(a.mValue)));
		}
		return normalizeRequestParameters(encodeParams);
	}

	private static String encode(String value) {
		String encoded = null;
		try {
			encoded = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException ignore) {
		}
		return encoded;
	}

	/**
	 * 字符拼接（拼接成GET请求的那种方式）
	 * @param parameters
	 * @return
	 */
	private String normalizeRequestParameters(List<NetParam> parameters) {
		StringBuffer sb = new StringBuffer();
		NetParam p = null;
		for (int i = 0, size = parameters.size(); i < size; i++) {
			p = parameters.get(i);
			sb.append(p.mName);
			sb.append("=");
			sb.append(p.mValue);

			if (i < size - 1) {
				sb.append("&");
			}
		}

		return sb.toString();
	}
}
