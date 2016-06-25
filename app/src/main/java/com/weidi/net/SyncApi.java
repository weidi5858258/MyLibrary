package com.c1tech.dress.net;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.c1tech.dress.activity.BaseActivity;
import com.c1tech.dress.bean.BankCardBean;
import com.c1tech.dress.bean.GoodsDetailBean;
import com.c1tech.dress.bean.HotMarketBean;
import com.c1tech.dress.bean.LocalUser;
import com.c1tech.dress.sign.tencent.weixin.WeixinConstants;
import com.c1tech.dress.util.APP;
import com.c1tech.dress.util.APPCode;
import com.c1tech.dress.util.Constant;

/**
 * 同步方法 返回的结果都是json格式的字符串
 * @Description
 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
 * @date 2014-5-27 10:53:58
 * @update 2014-9-15 16:17:01 新增微信接口
 *         <hr>
 *         2014-8-25 18:06:21 更新地图
 *         <hr>
 *         2014-8-14 16:16:21 新增修改个人账户信息接口
 *         <hr>
 *         2014-8-8 17:20:33 新增未付款订单界面可以重新付款
 *         <hr>
 *         2014-7-17 11:18:02 更新首页广告城市读取为本地
 *         <hr>
 *         <Strong>Nobody likes to work on Sundays.
 */
public class SyncApi {

	private static void wrapAuth(List<NetParam> parameters) {
		LocalUser localUser = BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context);
		parameters.add(new NetParam("huiyuanid", localUser.uid));
		parameters.add(new NetParam("key", APPCode.VERSION));
	}

	/**
	 * @Description 检测新版本$
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @date 2014-7-11 09:27:34
	 */
	public static String getVer() {
		String url = APP.host+"/css/app.css";// 写绝对地址，避免局域网测试时误发布后无法更新
		List<NetParam> parameters = new ArrayList<NetParam>();
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getVer():检测新版本 result = " + result);
		return result;
		//82-4974858-下载送好吃的、好玩的、下不下？？？
	}

	/**
	 * @Description 登录
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-3 11:05:11
	 */
	public static String signin(String acct, String psw) {
		String url = APP.domains("huiyuanlog/login");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("name", acct));
		parameters.add(new NetParam("psw", psw));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "POST", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-signin():登录 result = " + result);
		return result;
		/**严中路388弄31号301室
		 *{"status":0,"data":{"accout":"weidi5858258","uid":"174","name":"伟弟","txImage":"http://192.168.1.9:8080/Dress/upload/head/pic_1435900318.png","jf":0}}
		 */
	}

	/**
	 * @Description 注册
	 */
	public static String signup(String acct, String psw, String tel, String checkCode, String code) {
		String url = APP.domains("huiyuanlog/reg");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("name", acct));//用户名
		parameters.add(new NetParam("psw", psw));//密码
		parameters.add(new NetParam("phone", tel));//手机号
		parameters.add(new NetParam("code", checkCode));//短信验证码
		parameters.add(new NetParam("gsm", code));//邀请码
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "POST", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-signup():注册 result = " + result);
		return result;
	}

	/**
	 * @Description 注册时，获取短信验证码
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-9-18 上午11:14:28
	 */
	public static String getSMSCode(String tel) {
		String url = APP.domains("huiyuanlog/validPhone");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("phone", tel));//手机号
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getSMSCode():获取短信验证码 result = " + result);
		return result;
		//{"status":0,"data":"709990"}
	}

	/**
	 * @Description 城市列表
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-6 16:08:36
	 * @update 2014-6-17 15:25:37
	 */
	public static String getCities() {
		String url = APP.domains("aarea/list");
		List<NetParam> parameters = new ArrayList<NetParam>();
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getCities():城市列表 result = " + result);
		return result;
		/**
		 * {"status":0,"data":[{"id":"510100","name":"成都市","status":"1","isDefault":"1","ps":"null","m_id":"1"},
		 * {"id":"330100","name":"杭州市","status":"1","isDefault":"1","ps":"null","m_id":"90"},
		 * {"id":"330200","name":"宁波市","status":"1","isDefault":"1","ps":"null","m_id":"91"},
		 * {"id":"330300","name":"温州市","status":"1","isDefault":"1","ps":"null","m_id":"92"},
		 * {"id":"330400","name":"嘉兴市","status":"1","isDefault":"1","ps":"null","m_id":"93"},
		 * {"id":"330500","name":"湖州市","status":"1","isDefault":"1","ps":"null","m_id":"94"},
		 * {"id":"330600","name":"绍兴市","status":"1","isDefault":"1","ps":"null","m_id":"95"},
		 * {"id":"330700","name":"金华市","status":"1","isDefault":"1","ps":"null","m_id":"96"},
		 * {"id":"330800","name":"衢州市","status":"1","isDefault":"1","ps":"null","m_id":"97"},
		 * {"id":"330900","name":"舟山市","status":"1","isDefault":"1","ps":"null","m_id":"98"},
		 * {"id":"331000","name":"台州市","status":"1","isDefault":"1","ps":"null","m_id":"99"},
		 * {"id":"331100","name":"丽水市","status":"1","isDefault":"1","ps":"null","m_id":"100"}]}
		 */
	}

	/**
	 * @Description 首页广告，顶部和底部的产品广告
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-13 11:48:13
	 * @update 2014-6-25 13:39:40
	 * @update 2014-7-29 添加参数城市ID
	 */
	public static String getIndexAdv(int position, String cityId) {
		String url = APP.domains("aindex/adv");
		System.out.println("SyncApi-getIndexAdv()-url:"+url);
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("wzid", String.valueOf(position)));
		parameters.add(new NetParam("quyu", cityId));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getIndexAdv():首页广告顶部和底部 result = " + result);
		return result;
		/**
		 * {"status":"0","data":[{"aadv_id":"12","name":"玉兰油保湿品","img":"http://192.168.1.29:8080/Dress/upload/adv/1434225691213.jpg"}]}
		 */
	}

	/**
	 * @Description 首页广告，中间部分长期不变的菜单
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-28 11:13:37
	 */
	public static String getIndexMenu(int position) {
		String url = APP.domains("aindex/menu");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("wzid", String.valueOf(position)));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getIndexMenu():首页广告中间部分 result = " + result);
		return result;
		/**
		 * {"status":"0","data":[
									{"amenu_id":"5",
									 "name":"附近宝贝",
									 "img":"http://192.168.1.29:8080/Dress/upload/adv/1404104902132.png"}
								]}
		 */
	}

	/**
	 * @Description 首页广告点击后列表
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-28 15:09:25
	 * @update 2014-7-17 11:16:34
	 */
	public static String getAdvGoodsList(Context context, String adv_id, int page) {
		String url = APP.domains("aindex/goodslist");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", adv_id));
		parameters.add(new NetParam("p", String.valueOf(page)));
		parameters.add(new NetParam("city", context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND).getString(APPCode.CHOICE_CITY_ID, "")));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getAdvGoodsList():首页广告点击后列表 result = " + result);
		return result;
		//{"status":-1}
	}

	/**
	 * @Description 女人街
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-13 13:54:06
	 * @update 2014-7-8 14:15:54 取不到城市时默认全国
	 */
	public static String getWomenStreet(Context context, int page) {
		String url = APP.domains("anrj/list");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND).getString(APPCode.CHOICE_CITY_ID, "")));
		parameters.add(new NetParam("pageno", page + ""));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getWomenStreet():女人街 result = " + result);
		return result;
	}

	/**
	 * @Description 地图 poi 结果(没有选择或者定位到城市时，使用本地存储的城市 id )
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-8-25 下午4:19:34
	 */
	public static String getPOIList(Context context, Boolean showAll, String city) {
		String url = APP.domains("ashop/mapshop");
		List<NetParam> parameters = new ArrayList<NetParam>();
		String id = "";
		if (!showAll) {
			id = city.isEmpty() ? context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND).getString(APPCode.CHOICE_CITY_ID, "") : "";
		}
		parameters.add(new NetParam("id", id));
		parameters.add(new NetParam("name", city));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getPOIList():地图获取附近商家信息 result = " + result);
		return result;
	}

	/**
	 * 
	 * @Description 搜索商品
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-8-27 下午4:06:18
	 */
	public static String getQuery(Context context, String key, int page, int type) {
		String url = APP.domains("agoods/search");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND).getString(APPCode.CHOICE_CITY_ID, "")));
		parameters.add(new NetParam("key", key));
		parameters.add(new NetParam("pageno", String.valueOf(page)));
		parameters.add(new NetParam("serchType", String.valueOf(type)));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getQuery():搜索商品 result = " + result);
		return result;
	}

	/**
	 * @Description 测试地址，各种临时测试
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-8-29 下午3:27:16
	 */
	public static String TEST() {
		String url = "http://192.168.1.127:8080/xpzc/test.txt";
		List<NetParam> parameters = new ArrayList<NetParam>();
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-TEST()-result = " + result);
		return result;
	}

	/**
	 * @Description 女人街点击后列表
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-29 14:26:10
	 */
	public static String getStoreGoodsList(String storeID, int page) {
		String url = APP.domains("ashop/nrjgoods");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", storeID));
		parameters.add(new NetParam("pageno", String.valueOf(page)));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getStoreGoodsList():女人街指向的商品列表 result = " + result);
		return result;
	}

	/**
	 * @Description 美丽宣言列表
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-17 16:20:05
	 */
	public static String getBeautyProclaim(int page) {
		String url = APP.domains("abbs/list");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("pageno", page + ""));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getBeautyProclaim():美丽宣言列表 result = " + result);
		return result;
	}

	/**
	 * @Description 美丽宣言详情
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-18 11:17:42
	 */
	public static String getBeautyProclaimDetails(long id) {
		String url = APP.domains("abbs/detail");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("bbsid", id + ""));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// BaseActivity.Log_info("美丽宣言详情 json = " + result);
		System.out.println("SyncApi-getBeautyProclaimDetails():美丽宣言详情 result = " + result);
		return result;
		/**
		 * {"status":0,"data":[{"huiyuanname":"xiao祁祁","content":"null","txImage":"http://192.168.1.29:8080/Dress/upload/1403081537410.png","bbsnum":1,"time":"2014-06-10 16:39:24","imageSrc":"1402389348230.jpg;1402389453630.jpg;1402389453634.jpg;1402389505325.jpg;"}]}
		 */
	}

	/**
	 * @Description 获取美丽宣言评论列表
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-18 11:17:42
	 */
	public static String getBeautyProclaimDiscusses(long id, int page) {
		String url = APP.domains("abbs/pinglun");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("bbsid", id + ""));
		parameters.add(new NetParam("pageno", page + ""));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getBeautyProclaimDiscusses():美丽宣言评论列表 result = " + result);
		return result;
		/**
		 * {"status":0,"data":[{"txImage":"","content":"看上去很好啊"},
		 * {"txImage":"1403070587355.jpg","content":"åµåµ"},
		 * {"txImage":"1403070587355.jpg","content":"çè§£gddfgh"},
		 * {"txImage":"1403070587355.jpg","content":"Hello.world!"},
		 * {"txImage":"1403070587355.jpg","content":"来自星星的你，和来自月亮的我，是失散多年的。。"},
		 * {"txImage":"1403070587355.jpg","content":"a"},
		 * {"txImage":"1403087301064.jpg","content":"你好琪琪"},
		 * {"txImage":"1403081537410.png","content":" 看起来不错嘛。。好用不呢？"}]}
		 */
	}

	/**
	 * @Description 发布美丽宣言评论
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-18 16:06:31
	 */
	public static String postBeautyProclaimDiscusses(long id, String msg) {
		String url = APP.domains("abbs/submit");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("bbsid", id + ""));
		parameters.add(new NetParam("content", msg));
		wrapAuth(parameters);
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-postBeautyProclaimDiscusses():发布美丽宣言评论 result = " + result);
		return result;
		//{"status":0}发表成功
	}

	/**
	 * @Description 晒单?
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-15 10:57:50
	 * @update 2014-6-20 17:00:02
	 * @param orderNO
	 * @param pics
	 */
	public static String postOrderCommentary(String orderNO, String stars, String comTxt, String picOrderby,
			List<String> pics) {
		String url = APP.domains("aorder/commentBaskSingle");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("orderId", orderNO));
		parameters.add(new NetParam("leve", stars));
		parameters.add(new NetParam("saycontent", comTxt));
		parameters.add(new NetParam("imgOrder", picOrderby));
		wrapAuth(parameters);
		List<NetParam> files = null;
		if (pics != null && pics.size() != 0) {
			files = new ArrayList<NetParam>();
			int num = 1;
			for (String pic : pics) {
				files.add(new NetParam("pic_" + num, pic));
				File file = new File(pic);
				System.out.println("SyncApi-postOrderCommentary()-晒单-晒单图片：pic_" + num + "，路径" + pic + "，大小：" + file.length() + "");
				num++;
			}
		}
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "POST", parameters, files);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-postOrderCommentary():晒单  result = " + result);
		return result;
	}

	/**
	 * @Description 订单列表?
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @update 2014-6-19 15:24:50
	 */
	public static String getOrderList(int statusCode, int page) {
		String url = APP.domains("aorder/orderlist");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("status", String.valueOf(statusCode)));
		parameters.add(new NetParam("pageNo", String.valueOf(page)));
		wrapAuth(parameters);
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getOrderList():订单列表 result = " + result);
		return result;
	}

	/**
	 * @Description 订单详情?
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-20 13:40:23
	 * @update 2014-6-21 10:15:42
	 */
	public static String getOrderDetails(String orderNO) {
		String url = APP.domains("aorder/order");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("orderId", orderNO));
		wrapAuth(parameters);
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getOrderDetails():订单详情 result = " + result);
		return result;
	}

	/**
	 * @Description 确认收货?
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-20 16:49:18
	 */
	public static String confirmOrderReceipt(String orderNO) {
		String url = APP.domains("aorder/cinfirmReceipt");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("orderId", orderNO));
		wrapAuth(parameters);
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-confirmOrderReceipt():确认收货 result = " + result);
		return result;
	}

	/**
	 * @Description 收货地址
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-21 14:48:18
	 */
	public static String getAddressList(String uid) {
		String url = APP.domains("aadress/list");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("huiYuan_id", uid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getAddressList():收货地址  result = " + result);
		return result;
		/**
		 * {"status":0,"data":[{"address_id":"172","receiveName":"王力伟","receivePhone":"18565603244","address":"上海数字产业园","province":"上海市","city":"浦东新区","county":""},
		 * 					   {"address_id":"173","receiveName":"王力伟","receivePhone":"18565603244","address":"新昌大佛寺景区","province":"浙江省","city":"绍兴市","county":"新昌县"}]}
		 */
	}

	/**
	 * @Description 新增收货地址
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-21 15:57:56
	 */
	public static String addAddress(String uid, String province, String city, String area, String address, String name,
			String tel) {
		String url = APP.domains("aadress/add");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("huiYuan_id", uid));
		parameters.add(new NetParam("province", province));
		parameters.add(new NetParam("city", city));
		parameters.add(new NetParam("county", area));
		parameters.add(new NetParam("address", address));
		parameters.add(new NetParam("receiveName", name));
		parameters.add(new NetParam("receivePhone", tel));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-addAddress():新增收货地址  result = " + result);
		return result;
		//{"status":0}表示成功
	}

	/**
	 * @Description 更新收货地址
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-21 16:11:22
	 */
	public static String updateAddress(String uid, String address_id, String province, String city, String area,
			String address, String name, String tel) {
		String url = APP.domains("aadress/save");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("huiYuan_id", uid));
		parameters.add(new NetParam("address_id", address_id));
		parameters.add(new NetParam("province", province));
		parameters.add(new NetParam("city", city));
		parameters.add(new NetParam("county", area));
		parameters.add(new NetParam("address", address));
		parameters.add(new NetParam("receiveName", name));
		parameters.add(new NetParam("receivePhone", tel));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-updateAddress():更新收货地址  result = " + result);
		return result;
		//{"status":0}表示成功
	}

	/**
	 * @Description 删除收货地址
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-22 12:52:29
	 */
	public static String delAddress(String address_id) {
		String url = APP.domains("aadress/del");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("address_id", address_id));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-delAddress():删除收货地址 result = " + result);
		return result;
		//{"status":0}表示成功
	}

	/**
	 * @Description 社会化登录?
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 */
	public static String SocialSignin(String openid, String head, String nickName) {
		String url = APP.domains("aqqlogin/login");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("openid", openid));
		parameters.add(new NetParam("img", head));
		parameters.add(new NetParam("name", nickName));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-SocialSignin():社会化登录 result = " + result);
		return result;
	}

	/**
	 * @Description 获取微信Token?
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-9-15 下午1:47:07
	 * @param code
	 *            授权后会返回，用这个code来获取token
	 */
	public static String getWeixinToken(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
		// appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("appid", WeixinConstants.APP_ID));
		parameters.add(new NetParam("secret", WeixinConstants.APP_KEY));
		parameters.add(new NetParam("grant_type", "authorization_code"));
		parameters.add(new NetParam("code", code));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getWeixinToken():获取微信Token result = " + result);
		return result;
	}

	/**
	 * @Description 获取微信账户资料?
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-9-15 下午1:47:07
	 * @param access_token
	 *            token
	 * @param openid
	 *            获取 token 时一起返回的
	 */
	public static String getWeixinAcInfo(String token, String openid) {
		String url = "https://api.weixin.qq.com/sns/userinfo";
		// access_token=ACCESS_TOKEN&openid=OPENID
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("access_token", token));
		parameters.add(new NetParam("openid", openid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getWeixinAcInfo():获取微信账户资料 result = " + result);
		return result;
	}

	/**
	 * @Description 拉取推送消息
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @param lastDate
	 *            likes 2014-05-18
	 */
	public static String pullNotices(String lastID, String lastDate) {
		String url = APP.domains("amessage/send");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", lastID));
		parameters.add(new NetParam("data", lastDate));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-pullNotices():拉取推送消息 result = " + result);
		return result;
		//{"status":1}表示失败
	}

	/**
	 * @Description 炫品公告
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-22 13:52:40
	 */
	public static String getNotices(String uid, int page) {
		String url = APP.domains("amessage/getxpgg");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("pageno", String.valueOf(page)));
		parameters.add(new NetParam("uid", uid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getNotices():炫品公告 result = " + result);
		return result;
	}

	/**
	 * @Description 打折促销
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-22 13:52:40
	 */
	public static String getDiscount(String uid, int page) {
		String url = APP.domains("amessage/getdzcx");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("pageno", String.valueOf(page)));
		parameters.add(new NetParam("uid", uid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaseActivity.Log_info("打折促销 json = " + result);
		System.out.println("SyncApi-getDiscount():打折促销 result = " + result);
		return result;
	}

	/**
	 * @Description 删除打折促销、炫品公告
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-6-22 16:22:21
	 */
	public static String delNotices(String msgID, String uid) {
		String url = APP.domains("amessage/del");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("messagesend_id", msgID));
		parameters.add(new NetParam("uid", uid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-delNotices():拉取推送消息 result = " + result);
		return result;
	}

	/**
	 * @Description 根据活动商品 id 获取游戏类型（是点还是转）
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-11 16:05:10
	 * @param actid
	 *            活动id
	 */
	public static String getGameType(String actid) {
		String url = APP.domains("aactive/game");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("actid", actid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// result = "{\"status\":0,\"data\":[{\"gamename\":\"diandian\"}]}";
		System.out.println("SyncApi-getGameType():获取游戏类型 result = " + result);
		return result;
	}

	/**
	 * @Description 获取短信验证码
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-10 18:51:32
	 */
	public static String getSmsCode(String phone) {
		String url = APP.cart;
		// ../widget.html?widget=loginBar&changePassSendCode=1&phone=18288989898
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("widget", "loginBar"));
		parameters.add(new NetParam("changePassSendCode", "1"));
		parameters.add(new NetParam("phone", phone));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getSmsCode():获取短信验证码 result = " + result);
		return result;
	}

	/**
	 * @Description 重置密码
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-10 19:03:43
	 */
	public static String resetPsw(String phone, String checkCode, String psw) {
		String url = APP.cart;
		// ../widget.html?widget=loginBar&changePass=1&phone=18288989898&valide=ys12&newpwd=123123123
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("widget", "loginBar"));
		parameters.add(new NetParam("changePass", "1"));
		parameters.add(new NetParam("phone", phone));
		parameters.add(new NetParam("newpwd", psw));
		parameters.add(new NetParam("valide", checkCode));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-resetPsw():重置密码 result = " + result);
		return result;
	}

	/**
	 * 活动商品列表
	 * 
	 * @param context
	 * @param page
	 * @return
	 */
	public static String getPromotionSales(Context context, int page) {
		String url = APP.domains("aactive/list");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND).getString(APPCode.CHOICE_CITY_ID, "")));
		parameters.add(new NetParam("pageno", String.valueOf(page)));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getPromotionSales():游戏优惠(活动商品)列表 result = " + result);
		return result;
	}

	/**
	 * @Description 活动商品详情
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-9 10:14:27
	 * @return String
	 * @param goodId
	 *            :商品id
	 * @param actid
	 *            :商品游戏类型
	 */
	public static String getPromotionDetails(int goodID, int actionID, String memberId) {
		String url = APP.domains("aactive/detail");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("goodId", String.valueOf(goodID)));
		parameters.add(new NetParam("actid", String.valueOf(actionID)));
		parameters.add(new NetParam("memberId", String.valueOf(memberId)));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getPromotionDetails():活动商品详情 result = " + result);
		return result;
	}

	/**
	 * @Description 搜索时推荐的商品列表
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-3 14:29:24
	 * @update 2014-7-8 14:15:54 取不到城市时默认全国
	 */
	public static String getRecomList(Context context) {
		String url = APP.domains("agoods/recommend");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND).getString(APPCode.CHOICE_CITY_ID, "")));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getRecomList():搜索时推荐的商品列表 result = " + result);
		return result;
		//{"name":"梦幻水彩唇膏","imgUrl":"http://192.168.1.29:8080/Dress/upload/goods/1393485399468.png","discount":11.7,"shopid":"18","shopname":"心兰旗舰店","goods_id":"3","salesMoney":700.0,"bazaarMoney":600.0},
	}

	/**
	 * @Description 摇一摇
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-3 11:05:21
	 * @update 2014-7-8 14:15:54 取不到城市时默认全国
	 */
	public static String getShakeList(Context context) {

		String url = APP.domains("aactive/yaoyiyao");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND).getString(
				APPCode.CHOICE_CITY_ID, "")));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getShakeList():摇一摇 result = " + result);
		return result;
	}

	/**
	 * 爆品
	 * 
	 * @update 2014-7-8 14:15:54 取不到城市时默认全国
	 */
	public static String getBoomData(Context context, int page) {

		String url = APP.domains("aactive/baoping");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND).getString(APPCode.CHOICE_CITY_ID, "")));
		parameters.add(new NetParam("pageno", page + ""));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		BaseActivity.Log_info("爆品 json = " + result);
		System.out.println("SyncApi-getBoomData():爆品 result = " + result);
		return result;
	}

	/**
	 * 附近宝贝
	 * 
	 * @param context
	 * @param page
	 * @return
	 */
	public static String getNearData(int page) {
		String url = APP.domains("ashop/neargoods");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("lon", Constant.lng + ""));
		parameters.add(new NetParam("lat", Constant.lat + ""));
		parameters.add(new NetParam("pageno", page + ""));
		
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getNearData():附近宝贝 result = " + result);
		return result;
	}

	/**
	 * 炫品热销（会员专区）
	 * 
	 * @update 2014-7-8 14:15:54 取不到城市时默认全国
	 */
	public static String getHotData(Context context, int page) {
		String url = APP.domains("agoods/hotlist");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND)
									      .getString(APPCode.CHOICE_CITY_ID, "")));
		parameters.add(new NetParam("pageno", page + ""));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getHotData():会员专区 result = " + result);
		return result;
		/**
		 * {"status":0,"data":[
								{"goodId":"1",
								 "image":"http://192.168.1.29:8080/Dress/upload/goods/1394420282671.jpg",
								 "goodName":"欧莱雅葡萄籽抗氧化套装洗面奶+膜力水爽肤水+特润日霜女士护肤",
								 "bazaarMoney":496.0,
								 "shopid":1,
								 "shopname":1,
								 "salesMoney":319.0,
								 "discount":6.4}
							  ]}
		 */
	}

	/**
	 * 
	 * @Description 美容项目
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @updated by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-8-28 10:09:22
	 */
	public static String getBeautifulProjectData(Context context, int page) {

		String url = APP.domains("ashop/nearmrxm");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("cityid", 
									context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND)
										   .getString(APPCode.CHOICE_CITY_ID, "")));
		parameters.add(new NetParam("pageno", page + ""));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getBeautifulProjectData():美容项目 result = " + result);
		return result;
	}

	/**
	 * @Description 其他人还买了
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-4 15:33:32
	 * @update 2014-7-8 14:15:54 取不到城市时默认全国
	 */
	public static String getOtherPeopleBuy(Context context) {
		String url = APP.domains("agoods/otherbuy");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", 
				context.getSharedPreferences(APPCode.CITY, Context.MODE_APPEND)
					   .getString(APPCode.CHOICE_CITY_ID, "")));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getOtherPeopleBuy():其他人还买了 result = " + result);
		return result;
	}

	/**
	 * 身边美容院
	 * 
	 * @param context
	 * @param page
	 * @return
	 */
	public static String getBeautifulData(int page) {
		String url = APP.domains("ashop/nearshop");
		List<NetParam> parameters = new ArrayList<NetParam>();

		parameters.add(new NetParam("lon", Constant.lng + ""));
		parameters.add(new NetParam("lat", Constant.lat + ""));
		parameters.add(new NetParam("pageno", page + ""));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaseActivity.Log_info("身边美容院  json = " + result);
		System.out.println("SyncApi-getBeautifulData():身边美容院 json = " + result);
		return result;
	}

	/**
	 * 服务器那边的地址：ConfirmOrderWidget
	 * @Description 确认订单（查询订单内商品金额）
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-9-17 下午4:44:41
	 */
	public static String checkCartOrderGoods(String buffer) {
		String url = APP.cart;//ConfirmOrderWidget-display(Map pamtr)
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("event", "getShopGoods"));// 动作是获取要结算的商品信息
		parameters.add(new NetParam("action", "phone"));
		parameters.add(new NetParam("widget", "confirmOrder"));
		parameters.add(new NetParam("dataType", "json"));
		parameters.add(new NetParam("memberId", BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context).uid));
		// 其他的设计导致，只能这样奇葩的写法
		url += "?" + buffer;
		//最终的url = http://192.168.1.29:8080/Dress/widget.html?actId=1&actId=2...&action=phone&widget=confirmOrder&dataType=json&event=getShopGoods&memberId=用户uid
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-checkCartOrderGoods():确认订单（查询订单内商品金额）result = " + result);
		return result;
		/**
		 * {"jf":"1000","jf_money":"1.0","account":"0.0","address":[{"id":"172","name":"王力伟","phone":"18565603244","address":"上海市浦东新区杭州市上海数字产业园"},
																 	{"id":"173","name":"王力伟","phone":"18565603244","address":"浙江省绍兴市新昌县新昌高中"},
																 	{"id":"174","name":"王力伟","phone":"18565603244","address":"上海市嘉定区杭州市捷敏电子有限公司"}],
		 * "shopGoods":[{"shopId":"18","shopName":"心兰旗舰店是是","goods":[{"goodsId":"3","goodsName":"梦幻水彩唇膏","actMoney":"0.0","specMoney":"0.0","price":"700.0","num":"1","actId":"","id":"2bfddcb518d344d78961899ea5566a59"}]}]}
		 */
	}

	/**
	 * @Description 查询购物车内所要结算的商品的配送方式?
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-9-17 17:35:02
	 */
	public static String getShippingForOrder(String buffer) {
		String url = APP.cart;
		// action=phone&widget=confirmOrder&dataType=json&event=queryYF&memberId=
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("event", "queryYF"));// 查询运费
		parameters.add(new NetParam("action", "phone"));
		parameters.add(new NetParam("widget", "confirmOrder"));
		parameters.add(new NetParam("dataType", "json"));
		parameters.add(new NetParam("memberId", BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context).uid));
		// 其他的设计导致，只能这样奇葩的写法
		url += "?" + buffer;
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getShippingForOrder():查询订单配送方式 result = " + result);
		return result;
	}

	/**
	 * @Description 提交订单（服务器计算价格，然后使用支付宝支付）
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-9-17 17:35:02
	 */
	public static String checkOrder(String buffer) {
		String url = APP.cart;
		// action=phone&widget=confirmOrder&dataType=json&event=create&memberId=
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("event", "create"));// 动作是创建
		parameters.add(new NetParam("action", "phone"));
		parameters.add(new NetParam("widget", "confirmOrder"));
		parameters.add(new NetParam("dataType", "json"));
		parameters.add(new NetParam("memberId", BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context).uid));
		// 其他的设计导致，只能这样奇葩的写法
		url += "?" + buffer;
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-checkOrder():提交订单 result = " + result);
		return result;
		//{"create":true,"tradeNo":"2015070805221657","totale":638.0,"orderMoney":638.0,"dlyMoney":0.0}
	}

	/**
	 * @Description 获取购物车列表
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-7 18:16:33
	 */
	public static String getCartList(String uid) {
		String url = APP.domains("acar/goodlist");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("huiYuan_id", uid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getCartList():获取购物车列表 result = " + result);
		return result;
		/**
		 * {"status":0,"data":[
								{"goodsname":"Olay官方旗舰玉兰油多效修护凝乳50g",
								 "cars_box_id":"812776b7f5884b128420a17feef0537a",
								 "image":"http://192.168.1.29:8080/Dress/upload/goods/1395712160119.jpg",
								 "money":110.0,
								 "num":2,
								 "shopid":"38",
								 "shopname":"国际名妆",
								 "good_id":"53",
								 "specVal":"",
								 "actInfo":"|",
								 "type":"sl"}
							  ]}
		 */
	}

	/**
	 * @Description 添加到购物车（普通商品）
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-8 14:15:00
	 */
	public static String addToCartList(String goodsID, int count, String sizeCode, String uid) {
		String url = APP.cart;
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("widget", "carsWidget"));
		parameters.add(new NetParam("dataType", "json"));
		parameters.add(new NetParam("n", String.valueOf(count)));
		parameters.add(new NetParam("action", "add"));
		parameters.add(new NetParam("1", "1"));
		parameters.add(new NetParam("i", "0.9020185777330575"));
		parameters.add(new NetParam("spec_val_id", sizeCode));
		parameters.add(new NetParam("g_id", goodsID));
		parameters.add(new NetParam("huiYuan_id", uid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-addToCartList():添加到购物车（普通商品）result = " + result);
		return result;
		//{"status":1,"msg":"对不起！您购买的商品没找到或已经下架！"}
		//{"status":0}表示成功，服务器那边改了一下状态


	}

	/**
	 * @Description 添加到购物车（活动商品，限购一件） 
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-11 18:40:10
	 * @updated 2014-8-1 18:32:46 by <a
	 *          href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 */
	public static String addToCartList(String goodsID, String sizeCode, String actID, String uid, String agmId) {
		String url = APP.domains("acar/actadd");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("action", "Actadd"));
		parameters.add(new NetParam("num", "1"));// 活动商品限购1件
		parameters.add(new NetParam("g_id", goodsID));
		parameters.add(new NetParam("a_id", actID));
		parameters.add(new NetParam("huiYuan_id", uid));
		parameters.add(new NetParam("spec_val_id", sizeCode));
		parameters.add(new NetParam("agmId", agmId));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-addToCartList():添加到购物车（活动商品，限购一件）result = " + result);
		return result;
	}

	/**
	 * @Description 修改购物车数量
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-8 14:48:13
	 * @param cartGoodsId
	 *            类似：d4934d8e1e96465682370d7d5ffbd50d
	 */
	public static String editCartList(String cartGoodsId, int count) {
		String url = APP.domains("acar/modify");

		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("cars_box_id", cartGoodsId));
		parameters.add(new NetParam("num", String.valueOf(count)));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-editCartList():修改购物车数量 result = " + result);
		return result;
		//{"status":0}表示成功
	}

	/**
	 * @Description 删除购物车中某件商品
	 * @author <a href="http://t.cn/RvIApP5">ceychen</a>
	 * @date 2014-7-7 18:21:29
	 * @param cartGoodsId
	 *            类似：d4934d8e1e96465682370d7d5ffbd50d
	 */
	public static String delCartGood(String cartGoodsId) {
		String url = APP.cart;
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("widget", "carsWidget"));
		parameters.add(new NetParam("dataType", "json"));
		parameters.add(new NetParam("action", "del"));
		parameters.add(new NetParam("carInfo", cartGoodsId + ":::|"));
		parameters.add(new NetParam("huiYuan_id", BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context).uid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-delCartGood():删除购物车中某件商品 result = " + result);
		return result;
		//{"status":0}表示成功
	}

	/**
	 * 身边美容院 详情
	 * 
	 * @param context
	 * @param page
	 * @return
	 */
	public static String getAbsaData(int id, int page) {

		String url = APP.domains("ashop/shoplist");
		List<NetParam> parameters = new ArrayList<NetParam>();

		parameters.add(new NetParam("id", id + ""));
		parameters.add(new NetParam("pageno", page + ""));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getAbsaData():身边美容院详情 result = " + result);
		return result;
	}

	/**
	 * @Description 根据商品 id 获取归属店铺 名和店铺id
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-7-31 下午3:46:58
	 */
	public static String getStoreByGoods(String id) {
		String url = APP.domains("ashop/getshop");
		
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("id", id));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getStoreByGoods():商品归属店铺 result = " + result);
		return result;
	}

	/**
	 * @Description 根据商品 id 获取该商品 尺寸、颜色等属性
	 * @created by <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a> on
	 *          2014-8-1 17:49:53
	 */
	public static String getGoodsSize(String id) {
		String url = APP.domains("agoods/spec");
		List<NetParam> parameters = new ArrayList<NetParam>();

		parameters.add(new NetParam("id", id));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getGoodsSize():商品属性 result = " + result);
		return result;
		//{"status":2}

	}

//result:<html><head><script>location.replace('http://weidi5858258.nat123.net/Dress/html/agoods/spec?id=1000&nat123code2Ver=2785&nat123YunDun=true');</script><title></title></head><body><div><center>nat123/505.1</center></div></body></html>

	
	/**
	 * @Description 获取转盘游戏参数
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @date 2014-7-11 09:27:34
	 */
	public static String getGameParam(String goodsID, String actid) {
		String url = APP.domains("aactive/gameParam");
		List<NetParam> parameters = new ArrayList<NetParam>();
		LocalUser localUser = BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context);

		parameters.add(new NetParam("memberId", localUser.uid));
		parameters.add(new NetParam("goodId", goodsID));
		parameters.add(new NetParam("actid", actid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getGameParam():获取转盘游戏参数 result = " + result);
		return result;
	}

	/**
	 * @Description 未付款订单，点击付款时获取订单总价
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @created 2014-8-8 17:19:09
	 */
	public static String getOrderMoney(String orderID) {
		String url = APP.domains("aorder/topay");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("huiyuanid", BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context).uid));
		parameters.add(new NetParam("orderid", orderID));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getOrderMoney():未付款订单，点击付款时获取订单总价 result = " + result);
		return result;
	}

	/**
	 * @Description 上传图片
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @created 2014-8-13 16:53:26
	 */
	public static String uploadImg(List<String> pics) {//集合中放的是每张图片的地址
		String url = APP.domains("ahuiyuan/uploadimg");
		List<NetParam> parameters = new ArrayList<NetParam>();
		wrapAuth(parameters);
		List<NetParam> files = null;
		if (pics != null && pics.size() != 0) {//至少有一张图片
			files = new ArrayList<NetParam>();
			int num = 1;
			for (String pic : pics) {
				files.add(new NetParam("pic_" + num, pic));//("pic_1", "客户端本地图片地址")
				num++;
//				File file = new File(pic);
//				System.out.println("上传图片-图片：pic_" + num + "，路径" + pic + "，大小：" + file.length() + "");
				//上传图片-图片：pic_2，路径/storage/sdcard0/XuanPin/images/save/capture/xuanpin_cut/pic_1435735086.png，大小：216495
			}
		}
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "POST", parameters, files);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-uploadImg():上传图片 result = " + result);
		return result;
		//SyncApi-uploadImg():上传图片 result = {"status": 0,"fileName": "http://192.168.1.9:8080/Dress/upload/head/pic_1435890333.png"}
	}
	
	/**
	 * @Description 上传我的图片（可以上传其他任何文件）
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @created 2014-8-13 16:53:26
	 */
	public static void uploadImages(List<String> pics) {
		String url = APP.domains("ahuiyuan/uploadImages");
		List<NetParam> parameters = new ArrayList<NetParam>();
		List<NetParam> files = null;
		if (pics != null && pics.size() != 0) {
			files = new ArrayList<NetParam>();
			int num = 1;
			for (String pic : pics) {
				files.add(new NetParam("pic_" + num, pic));
				num++;
			}
		}
		try {
			NetRequest request = new NetRequest();
			request.syncRequest(url, "POST", parameters, files);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description 修改头像（主要是为了修改用户在服务器端的信息）
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @created 2014-8-13 17:37:07
	 */
	public static String updateHead(String imgPath) {
		String url = APP.domains("ahuiyuan/edittx");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("uid", BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context).uid));
		parameters.add(new NetParam("imgname", imgPath));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-updateHead():修改头像 result = " + result);
		//SyncApi-updateHead():修改头像 result = {"status":0}表示修改成功
		return result;
	}

	/**
	 * @Description 修改昵称
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @created 2014-8-13 15:46:31
	 */
	public static String updateNick(String txt) {
		String url = APP.domains("ahuiyuan/editname");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("uid", BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context).uid));
		parameters.add(new NetParam("userName", txt));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "POST", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-updateNick():修改昵称 result = " + result);
		return result;
	}

	/**
	 * @Description 修改密码
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @created 2014-8-13 16:33:52
	 */
	public static String updatePsw(String old, String now) {
		String url = APP.domains("ahuiyuan/editpsw");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("uid", BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context).uid));
		parameters.add(new NetParam("oldpsw", old));
		parameters.add(new NetParam("newpsw", now));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "POST", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-updatePsw():修改密码 result = " + result);
		return result;
	}

	/**
	 * @Description 获取账户昵称头像和积分
	 * @author <a href="http://t.cn/RvIApP5">ceychen@foxmail.com</a>
	 * @created 2014-8-13 18:00:21
	 */
	public static String getAccInfo() {
		String url = APP.domains("ahuiyuan/gethData");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("uid", BaseActivity.getLastLocalUserInfoInstance(BaseActivity.context).uid));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getAccInfo():获取账户信息 result = " + result);
		return result;
		//{"status":0,"data":[{"jf":"0","username":"伟弟","imgtx":"http://192.168.1.29:8080/Dress/upload/null"}]}
	}

	/**
	 * 提交银行卡信息
	 * @param bankCardBean
	 * @return
	 */
	public static String submitBankCard(BankCardBean bankCardBean){
		String url = APP.domains("ahuiyuan/bankcard");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("cardholder", bankCardBean.getCardholder()));
		parameters.add(new NetParam("bankcard", bankCardBean.getBankcard()));
		parameters.add(new NetParam("belongscard", bankCardBean.getBelongscard()));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "POST", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-submitBankCard():银行卡信息 result = " + result);
		return result;
	}
	
	/**
	 * 传送实体到服务器进行保存，是个收藏的商品实体
	 * @param hotMarketBean
	 * @return
	 */
	public static String sendGoodsDetailBean(GoodsDetailBean goodsDetailBean){
		String url = APP.domains("agoods/favorite");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("actId", String.valueOf(goodsDetailBean.getActId())));
		parameters.add(new NetParam("goodId", String.valueOf(goodsDetailBean.getGoodId())));
		parameters.add(new NetParam("name", goodsDetailBean.getHotName()));
		parameters.add(new NetParam("image", goodsDetailBean.getHotImg()));
		parameters.add(new NetParam("nowPrice", String.valueOf(goodsDetailBean.getNowPrice())));
		parameters.add(new NetParam("oldPrice", String.valueOf(goodsDetailBean.getOldPrice())));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-sendGoodsDetailBean():收藏的商品实体 result = " + result);
		return result;
	}
	
	/**
	 * 取收藏的商品实体
	 * @return
	 */
	public static String getGoodsDetailBean(){
		String url = APP.domains("agoods/getfavorite");
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getGoodsDetailBean()-result = " + result);
		return result;
	}
	
	/**
	 * 根据商品的Id去查询得到这个商品的详细信息，其信息包括：
	 * actId,goodsId,name,img,nowPrice,oldPrice
	 * @param goodsId
	 * @return
	 */
	public static String getGoodsDetail(int goodsId){
		String url = APP.domains("agoods/getgoodsdetail");
		List<NetParam> parameters = new ArrayList<NetParam>();
		parameters.add(new NetParam("goodsId", String.valueOf(goodsId)));
		NetRequest request = new NetRequest();
		String result = null;
		try {
			result = request.syncRequest(url, "GET", parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SyncApi-getGoodsDetail()-result = " + result);
		return result;
	}
	
}
