package com.xianglin.station.util;

/**
 */
public class APPCode {

	// public static String VERSION = "jx_xpzc_roid_140922"; // 版本号，测试用
	public static int TIMEOUT = 10 * 60 * 1000;// 连接超时时间

	public static final long TIME = 2000;// 在2秒内双击“返回键”后退出应用
	public static final int CHECK_NOCHECK = 0;// 还未进行过实名认证
	public static final int CHECK_ING = 1;// 实名认证还在审核
	public static final int CHECK_PASS = 2;// 实名认证已经通过审核
	public static final int CHECK_FAILURE = 3;// 实名认证失败
	public static final boolean CHECK_BANK_SUCCED = true;
	public static final boolean CHECK_BANK_FAILURE = false;
	/** ----------------------- Handler 代码 --------------------- */

	/** ----------------------- SharedPreferences 代码 --------------------- */

	/** -------------------------- Dialog 提示用代码 -------------------------- */

	/** -------------------------- Intent 跳转用代码 -------------------------- */

	/** -------------------------- 数据库表名代码 -------------------------- */

	/** -------------------------- 手机号码校验 -------------------------- */
	public static final String PHONERULE = "^1[0-9]{10}$";
	/** -------------------------- 孩子数量校验 -------------------------- */
	public static final String CHILDREN = "^[1-9]$";
	/** ---------------------------只允许带一位小数正数校验-------------------------- */
	public static final String CHECKNUMBER = "^[0-9]+(.[0-9]{1})?$";
	/** ---------------------------email校验-------------------------- */
	public static final String CHECKEMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	/** ---------------------------QQ号校验----------------------------------- */
	public static final String CHECKQQ = "^[1-9][0-9]{4,}$";
	/**
	 * ---------------------------weixin号校验-----------------------------------
	 */
	public static final String CHECKWEIXIN = "^[a-zA-Z0-9][a-zA-Z0-9_]{4,}$";
	/** ---------------------------身份证格式校验----------------------------------- */
	public static final String ISIDCARD2 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$";
	/** ---------------------------纯中文校验----------------------------------- */
	public static final String CHINESENAME = "^[\\u4e00-\\u9fa5]{0,}$";
	/** ---------------------------纯中文校验----------------------------------- */
	public static final String CHINESENAMEPOINT = "^[[\\u4e00-\\u9fa5]\\.]{0,}$";
	/** -----------------------------固定电话校验---------------------------------- */
	public static final String TELEPHONE = "^(0[0-9]{2,4}\\-?)?([0-9]+)?$";
	/** ------------------银行卡号校验------------------ **/
	public static final String BANKCODE = "^[0-9]{16,19}$";
	/** ---------站长信息-微信号--------- */
	public static final String WECHAT = "^([a-zA-Z])+[a-zA-Z0-9\\-\\_]*$";
	/** -----地址----- **/
	public static final String ADDRESS = "^[a-zA-Z0-9[\\u4e00-\\u9fa5]]+$";
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	
	/** ---------------------------调用后台接口时----------------------------------- */
	/** 从后台返回对象为null时 */
	public static final int OBJECT_IS_NULL = 0;
	/** 默认的异常 */
	public static final int DEFAULT_EXCEPTION = 1;
	/** 除此之外的异常 */
	public static final int OTHER_EXCEPTION = 2;

}
