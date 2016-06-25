
package com.xianglin.station.constants;

/**
 * 程序发布 各开关控制接口类
 * 
 * @author songdiyuan
 * @version $Id: ReleaseSwitch.java, v 1.0.0 2015-08-08 下午3:48:32 songdy Exp $
 */
public interface ReleaseSwitch {
	
	
	/**
	 * 在debug 情况下的环境
	 *  
	 * test：
	 * 		ENVController.ENV_DEV 开发
	 * 		ENVController.ENV_TEST 测试
	 * 		ENVController.ENV_LT 联调
	 * 
	 * product:
	 * 		ENVController.ENV_PRODUCT
	 */

	String XL_ENV_DEBUG_VALUE = ENVController.ENV_TEST;
	 

	
	/**
	 * 在debug 情况下的db 版本号
	 * 
	 */
	int DB_DEBUG_VER = 1;
	
	/**
	 *  在 product 情况下的umeng的appkey值
	 *  
	 */	
	String UM_APPKEY_VALUE = "560114b367e58e0e1a000ba6";  
	
	/**
	 *  在 debug 情况下的umeng的appkey值
	 *  
	 */
	String UM_APPKEY_DEBUG_VALUE = "560114ede0f55aa80900264f";
	
	/**
	 * 在debug 情况下的渠道名
	 */
	String UM_CHANNEL_NAME = "XiangLinXiaoZhan";
}
