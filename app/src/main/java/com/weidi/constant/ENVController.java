package com.xianglin.station.constants;

import android.content.Context;
import com.xianglin.station.util.NativeEncrypt;

/**
 * URL控制
 * 
 * @author songdiyuan
 * @version $Id: ENVController.java, v 1.0.0 2015-8-8 下午7:12:26 xl Exp $
 */
public class ENVController {

	// ENV key
	/** 本地 */
	public static final String ENV_LOCAL = "ENV_LOCAL";
	/** 开发 */
	public static final String ENV_DEV = "ENV_DEV";
	/** 测试 */
	public static final String ENV_TEST = "ENV_TEST";
	/** 测试2 */
	public static final String ENV_TEST_2 = "ENV_TEST_2";
	/** 联调 */
	public static final String ENV_LT = "ENV_LT";
	/** 生产 */
	public static final String ENV_PRODUCT = "ENV_PRODUCT";
	/** 预生产 */
	public static final String ENV_PP_PRODUCT = "ENV_PP_PRODUCT";

	/** 接口服务URL地址 */
	public static String URL = "";
	/** 接口服务URL地址 */
	public static String MMGW_URL = "";
	/** 接口服务待办贷款列表URL地址 */
	public static String MMGW_LOAN_URL = "";

	/** 用户协议地址 */
	public static String USER_AGREEMENT_URL = "https://www.baidu.com";

	/** 合同预览地址 */
	public static String LOAN_CONTRACT_URL = "http://www.qq.com";
	
	/** 二维码MD5加密key */
	public static String MMGWSECRET_FOR_MD5="";
	
	/** 二维码AES加密key */
	public static String MMGWSECRET_FOR_AES="";


	/**
	 * 
	 * @param context
	 * @param env
	 */
	public static void initEnv(Context context, String env) {
		MMGWSECRET_FOR_MD5= NativeEncrypt.nativeEncryptInstance(context).getRsaByKey("MMGWSECRET_FOR_MD5_KEY");
		MMGWSECRET_FOR_AES = NativeEncrypt.nativeEncryptInstance(context).getRsaByKey("MMGWSECRET_FOR_AES_KEY");
		if (ENV_PRODUCT.equals(env)) {
			URL = NativeEncrypt.nativeEncryptInstance(context).getUrlByKey(
					"PRODUCT_URL"); // 生产
			MMGW_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("PRODUCT_MMGW_URL");
			MMGW_LOAN_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("PRODUCT_MMGW_LOAN_URL");

		} else if (ENV_PP_PRODUCT.equals(env)) {
			URL = NativeEncrypt.nativeEncryptInstance(context).getUrlByKey(
					"PP_PRODUCT_URL"); // 预生产
			MMGW_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("PP_PRODUCT_MMGW_URL");
			MMGW_LOAN_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("PP_PRODUCT_MMGW_LOAN_URL");

		} else if (ENV_LOCAL.equals(env)) {
			URL = "http://127.0.0.1:8080/ggw/mgw.htm";// 本地
			MMGW_URL = "";
			MMGW_LOAN_URL = "";

		} else if (ENV_DEV.equals(env)) {
			URL = NativeEncrypt.nativeEncryptInstance(context).getUrlByKey(
					"ENV_DEV_URL"); // 开发
			MMGW_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("ENV_DEV_MMGW_URL");
			MMGW_LOAN_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("ENV_DEV_MMGW_LOAN_URL");

		} else if (ENV_TEST.equals(env)) {
			URL = NativeEncrypt.nativeEncryptInstance(context).getUrlByKey(
					"ENV_TEST_URL"); // 测试
			MMGW_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("ENV_TEST_MMGW_URL");
			MMGW_LOAN_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("ENV_TEST_MMGW_LOAN_URL");

		} else if (ENV_TEST_2.equals(env)) {
			URL = NativeEncrypt.nativeEncryptInstance(context).getUrlByKey(
					"ENV_TEST_2_URL"); // 测试2
			MMGW_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("ENV_TEST_2_MMGW_URL");
			MMGW_LOAN_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("ENV_TEST_2_MMGW_LOAN_URL");

		} else if (ENV_LT.equals(env)) {
			URL = NativeEncrypt.nativeEncryptInstance(context).getUrlByKey(
					"ENV_LT_URL"); // 联调
			MMGW_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("ENV_LT_MMGW_URL");
			MMGW_LOAN_URL = NativeEncrypt.nativeEncryptInstance(context)
					.getUrlByKey("ENV_LT_MMGW_LOAN_URL");
		}

	}

}
