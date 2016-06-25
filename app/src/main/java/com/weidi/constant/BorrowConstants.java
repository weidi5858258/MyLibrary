package com.xianglin.station.constants;

/**
 * 系统常量
 * 
 * @author songdiyuan
 * @version $Id: BorrowConstants.java, v 1.0.0 2015-8-7 下午4:17:56 xl Exp $
 */
public interface BorrowConstants {

	/** 网络状态参数 */
	int RESULT_STATUS_NORMAL = 1000;
	int RESULT_STATUS_SUCCESS = 2000;
	int RESULT_STATUS_ERROR = 5000;
	int RESULT_STATUS_HEAD_PARAMETER_ERROR = 5013;

	String INNER_DBNAME = "xlxz.db";

	/**
	 * 实名认证资料审核模式，默认状态
	 */
	int IDENTIFY_RESULT_DEFUALT = 0;
	/**
	 * 实名认证资料审核完成
	 */
	int IDENTIFY_RESULT_CHECKED = 1;
	/**
	 * 实名认证资料审核中
	 */
	int IDENTIFY_RESULT_CHECKING = 2;
	/**
	 * 未实名认证或者实名认证未通过，需要进行实名认证
	 */
	int IDENTIFY_RESULT_NEED_ADD_BANK = 3;
	/**
	 * 已申请一笔贷款
	 */
	int IDENTIFY_RESULT_NOT_LOAN = 4;

	/**
	 * 从申请贷款页面进去，没有实名认证
	 */
	int IDENTIFY_RESULT_NEED_IDENTIFY = 5;

	/**
	 * 已经实名认证过，但不是本网点用户
	 */
	int IDENTIFY_RESULT_NOT_LOCAL = 6;

	/**
	 * 默认（从实名认证那里过来）
	 */
	int DEFAULT_VALUE = 100;

	/**
	 * 是否从贷款页面进入身份证读取界面
	 */
	int IS_FROM_CREDIT = 101;

	/**
	 * 是否从还款那里进入身份证读取界面
	 */
	int IS_FROM_RAYMENT = 102;

	/**
	 * 是否从预约办卡那里进入身份证读取界面
	 */
	int IS_FROM_BANK = 103;

	/**
	 * 每隔一段时间自动去登录、签到（现在是五分钟）
	 */
	int AUTO_LOGIN_DOWNLOAD_SIGN_TIME = 5 * 60 * 1000;

	/**
	 * 未办理该类银行卡
	 */
	int IDENTIFY_RESULT_NOT_ID_CARD = 7;

	/**
	 * 已办理该类银行卡
	 */
	int IDENTIFY_RESULT_PASS_ID_CARD = 8;

	/**
	 * IDENTIFICATION_CERTIFY=9001 身份证明 HOMESTEAD_CERTIFY=9002 宅基地证明
	 * FAMILY_CERTIFY=9003 家庭证明 WORK_CERTIFY=9004 工作证明 EDUCATION_CERTIFY=9005
	 * 教育证明 MARRIAGE_CERTIFY=9006 结婚证明, VEHICLE_PROPERTY_CERTIFY=9007 车辆产权证明
	 * OTHER_CERTIFY=9100 其他证明
	 */

	/**
	 * 身份证明
	 */
	String IDENTIFICATION_CERTIFY = "9001";

	/**
	 * 结婚证明
	 */
	String MARRIAGE_CERTIFY = "9002";

	/**
	 * 家庭证明
	 */
	String FAMILY_CERTIFY = "9003";

	/**
	 * 宅基地证明
	 */
	String HOMESTEAD_CERTIFY = "9004";

	/**
	 * 车辆产权证明
	 */
	String VEHICLE_PROPERTY_CERTIFY = "9005";

	/**
	 * 教育证明
	 */
	String EDUCATION_CERTIFY = "9006";

	/**
	 * 工作证明
	 */
	String WORK_CERTIFY = "9007";

	/**
	 * 其他证明
	 */
	String OTHER_CERTIFY = "9100";

	/**
	 * 创建新业务
	 */
	String BUSINESS_TYPE_KEY = "business_type";

	String CREATE_BUSINESS_VALUE = "create";
	/**
	 * 编辑新业务
	 */

	String EDIT_BUSINESS_VALUE = "edit";

	String SERVICE_SETTING = "servicesetting";
	
	String LOG_SETTINGS_LIST = "LogSettingsList";

	/**
	 * 业务实体对象
	 */
	String BUSINESS_OBJECT_KEY = "business_object";

	/**
	 * householdid
	 */
	String HOUSE_HOLD_ID = "house_hold_id";
	/**
	 * 酷银数据接口
	 */
	int TYPE_KOOLYIN_LOGIN = 0x10; // 向酷银服务器登录
	int TYPE_KOOLYIN_DOWNLOAD_PAYMENT = 0x11; // 下载证书
	int TYPE_KOOLYIN_LOGOUT = 0x12; // 登出
	int TYPE_KOOLYIN_SIGNIN = 0x13; // 到银行签到
	int TYPE_KOOLYIN_TRANSACTION = 0x14; // 交易回调数据
	int TYPE_KOOLYIN_SWIPECARD = 0x15; // 设备回调，纯磁条卡刷卡状态
	int TYPE_KOOLYIN_SWIPECARD_ERROR = 0x16; // 提示信息
	int TYPE_KOOLYIN_GETCARDDATA_SWIPE = 0x17; // 刷卡时，查询余额或者转账时的数据回调
	int TYPE_KOOLYIN_GETCARDDATA_INSERT = 0x18; // 插卡时，查询余额或者转账时的数据回调

	String KOOLYIN_CUSTOMERID = "115521001000071"; // 客户号
	String KOOLYIN_USER_NAME = "01"; // 用户名
	String KOOLYIN_PASSWORD = "111111"; // 密码

	int REQUSET_REPAYMENT = 1; // 贷款查询

	/** 接口参数 命名如：INTERFACE_URL_XXX_XXX_XXX_XXX */
	/* 系统登出 */
	String INTERFACE_URL_LOGOUT = "xianglin.nodemanager.logout";

	/* 登录短信验证 */
	String INTERFACE_URL_SMSFACADE_LOGINSENDSMS = "com.xianglin.xlStation.common.service.facade.userFacade.SmsFacade.loginSendSms";

	/* 其他短信验证 */
	String INTERFACE_URL_SMSFACADE_OTHERSENDSMS = "com.xianglin.xlStation.common.service.facade.userFacade.SmsFacade.otherBSSendSms";

	/* 短信验证码验证 */
	String INTERFACE_URL_SMSFACADE_GETSMSINFO = "com.xianglin.xlStation.common.service.facade.userFacade.SmsFacade.getSmsInfo";

	/* 获取实名认证状态 */
	String INTERFACE_URL_GET_AGREEMENT = "com.xl.mobile.rpc.xloan.LoanService.getXLLoanAccount";
										
	/* 获取实名认证状态 */
	String INTERFACE_URL_GET_STATION_INFO_BY_CARD_NUM = "com.xianglin.xlStation.common.service.facade.userFacade.AuthenticationFacade.getStationInfoByIdNo";

	/* 判断是否是本网点用户 */
	String INTERFACE_URL_IF_IN_NODE = "com.xianglin.xlStation.common.service.facade.userFacade.AuthenticationFacade.ifInNode";

	/* 判断某村民是否是本网点用户 */
	String INTERFACE_URL_IFINNODE = "com.xianglin.xlStation.common.service.facade.userFacade.AuthenticationFacade.ifInNode";

	/* 卡认证,身份证认证 */
	String INTERFACE_URL_POS_REAL_NAME_AUTH_INFO = "com.xianglin.xlStation.common.service.facade.userFacade.AuthenticationFacade.posRealNameAuthInfo";

	/* 查询实名认证列表 */
	String INTERFACE_URL_GET_REAL_NAME_AUTH_LIST_BY_NODE = "com.xianglin.xlStation.common.service.facade.userFacade.AuthenticationFacade.getRealNameAuthListByNode";

	/* 获取网点初始化信息 */
	String INTERFACE_URL_GET_NODE_MESSAGE = "com.xianglin.xlStation.common.service.facade.userFacade.UserFacade.getNodeMessage";

	/* 执行登录、首次登陆/修改密码/忘记密码/ */
	String INTERFACE_URL_NOMAL_LOGIN = "com.xianglin.xlStation.common.service.facade.userFacade.UserFacade.nomalLogin";

	/* 首次登录方法 */
	String INTERFACE_URL_FIRST_LOGIN = "com.xianglin.xlStation.common.service.facade.userFacade.UserFacade.firstLogin";

	/* 用户修改密码登录接口 */
	String INTERFACE_URL_CHANGE_PWD_LOGIN = "com.xianglin.xlStation.common.service.facade.userFacade.UserFacade.changePWDlogin";

	/* 提交预申请 */
	String INTERFACE_URL_PRE_APPLY = "com.xl.mobile.rpc.xloan.LoanService.preApply";

	/* 下载合同 */
	String INTERFACE_URL_GET_CONTRACT_FILE = "com.xl.mobile.rpc.xloan.LoanService.getContractFile";

	/* html预览合同下载 */
	String INTERFACE_URL_GET_CONTRACT_FILE_PREVIEW = "com.xl.mobile.rpc.xloan.LoanService.getContractFilePreView";

	/* 提交还款请求 */
	String INTERFACE_URL_REPAY = "com.xl.mobile.rpc.xloan.LoanService.repay";

	/* 网点经理同意放贷， 合同提交 */
	String INTERFACE_URL_APPLY = "com.xl.mobile.rpc.xloan.LoanService.apply";

	/* 查询产品列表 */
	String INTERFACE_URL_GET_PRODUCT_LIST = "com.xl.mobile.rpc.xloan.LoanService.getProductList";

	/* 查询产品详情 */
	String INTERFACE_URL_GET_PRODUCT_DETAIL = "com.xl.mobile.rpc.xloan.LoanService.getProductDetail";

	/* 信用评估 */
	String INTERFACE_URL_CREDIT_REVIEW = "com.xl.mobile.rpc.xloan.LoanService.creditReview";

	/* 查询贷款申请列表 */
	String INTERFACE_URL_GET_APPLY_LIST = "com.xl.mobile.rpc.xloan.LoanService.getApplyList";

	/* 查询贷款申请详情 */
	String INTERFACE_URL_GET_APPLY_DETAIL = "com.xl.mobile.rpc.xloan.LoanService.getApplyDetail";

	/* 根据村民ID 查询贷款和贷款状态 */
	String INTERFACE_URL_GET_APPLY_INFO_BY_CUST_ID = "com.xl.mobile.rpc.xloan.LoanService.getApplyInfoByCustID";

	/* 网点经理否决预申请接口 */
	String INTERFACE_URL_SET_PRE_APPLY_STATUS = "com.xl.mobile.rpc.xloan.LoanService.setPreApplyStatus";

	/* 网点经理同意打劣后款，并认购。 */
	String INTERFACE_URL_PURCHASE = "com.xl.mobile.rpc.xloan.LoanService.purchase";

	/* 网点经理打劣后款，记录资金流水， 不管失败还是成功都要记录， 状态为LoanStateEnum中定义的状态。 */
	String INTERFACE_URL_PURCHASE_RECORD = "com.xl.mobile.rpc.xloan.LoanService.purchaseRecord";

	/* 上传合同接口 */
	String INTERFACE_URL_UPLOAD_CONTRACT = "com.xl.mobile.rpc.xloan.LoanService.uploadContract";

	// 图片地址下载json
	String INTERFACE_URL_IMAGE_URL = "com.xianglin.mmgw.service.ArchUriService.getArchUriList";

	
	
	/*还款账单查询*/
	String GET_REPAYMENT_BILLS = "com.xl.mobile.rpc.xloan.LoanService.getRepaymentBills";
	
	/*当期还款账单查询*/
	String GET_REPAYMENT_CURRENT_BILLS = "com.xl.mobile.rpc.xloan.LoanService.getCurrentPeriodRepaymentBill";
	
	//图片下载地址byte[]
	String INTERFACE_URL_IMAGE_LOAD_URL = "com.xianglin.mmgw.service.ArchUriService.getArch";

	// 还款时获取收款方名称及卡号
	String INTERFACE_URL_GET_XLLOAN_ACCOUNT_RESPONSE = "com.xl.mobile.rpc.xloan.LoanService.getXLLoanAccount";

	// 还款资金流水
	String INTERFACE_URL_REPAYMENT_RECORD = "com.xl.mobile.rpc.xloan.LoanService.repaymentRecord";
	// 打印接口
	String INTERFACE_URL_PRINT_COMMON = "com.xl.mobile.rpc.xloan.LoanService.commonPrint";
	
	// 提前还款协议
	String INTERFACE_URL_AGREEMENT_EARLY = "com.xl.mobile.rpc.xloan.LoanService.getAgreement";
	
	// 提前还款详情展示
	String INTERFACE_URL_DETAIL_EARLY = "com.xl.mobile.rpc.xloan.LoanService.getPrepaymentDetail";
	
	//补打小票
	String INTERFACE_URL_MAKE_UP_RECEIPT="com.xl.mobile.rpc.xloan.LoanService.getReceipt";

	String INTENT_PREAPPLY_DETAIL = "preApply_detail";
	String INTENT_PRODUCT_DETAIL = "product_detail";
	String INTENT_CARD_NUMBER = "card_number";
	String INTENT_RESULT_SUCCESS = "result_status";
	String INTENT_FROM_CREDIT = "from_credit";
	String INTENT_FROM_ID_CARD = "from_id_card";
	String INTENT_IDENTIFY_RESULT = "identify_result";
	String BUNDLE_MANAGER_VO_DATA = "manager_vo_data";

	/** 网点信息记录接口 **/

	// 当前网点交易查询列表
	String NODETRANSACTION_NODEDETAIL_LIST = "com.xianglin.xlnodecore.common.service.facade.NodeTransactionRecordService.getNodeDealList4POS";

	// 当前网点交易单条查询详情
	String NODETRANSACTION_NODEDETAIL = "com.xianglin.xlnodecore.common.service.facade.NodeTransactionRecordService.getNodeDealDetails";

	// 当前网点交易信息新增
	String NODETRANSACTION_NODEDETAIL_DETAIL = "com.xianglin.xlnodecore.common.service.facade.NodeTransactionRecordService.addNodeDeal4POS";

	// 当前网点交易信息更新
	String NODETRANSACTION_UPDATE_NODEDETAIL_DETAIL = "com.xianglin.xlnodecore.common.service.facade.NodeTransactionRecordService.updateNodeDeal";

	// 当前网点交易信息删除,软删除
	String NODETRANSACTION_DELETE_NODEDETAIL_DETAIL = "com.xianglin.xlnodecore.common.service.facade.NodeTransactionRecordService.deleteNodeDeal";

	// 根据日期统计当前网点的交易笔数
	String NODETRANSACTION_NODE_RECORD_COUNT = "com.xianglin.xlnodecore.common.service.facade.NodeTransactionRecordService.getNodeRecordCount";

	// 根据网点经理id获取网点经理信息
	String ACCOUNT_NODE_MANAGER = "com.xianglin.xlnodecore.common.service.facade.AccountNodeManagerService.queryNodeManagerByNodePartyId4POS";

	// 添加村民
	String HOUSE_HOLD_ADDXL_INFO = "com.xianglin.xlnodecore.common.service.facade.HouseholdInfoService.addXlcHouseholdInfo";

	// 修改村民信息
	String HOUSE_HOLD_UPDATEXL_HOUSE_INFO = "com.xianglin.xlnodecore.common.service.facade.HouseholdInfoService.updateXlcHouseholdInfo";

	// 查询村民列表
	String HOUSE_HOLD_HOUSE_INFO_LIST = "com.xianglin.xlnodecore.common.service.facade.HouseholdInfoService.getHouseholdInfoList4POS";

	// POS端网点经理查询村民列表
	String HOUSE_HOLD_HOUSE_INFO_4POS = "com.xianglin.xlnodecore.common.service.facade.HouseholdInfoService.getXlcHouseholdInfoListInOneNode";

	// 根据partId查询村民详情信息
	String HOUSE_HOLD_GET_HOUSE_INFO_BYID = "com.xianglin.xlnodecore.common.service.facade.HouseholdInfoService.getHouseholdInfo4POS";

	// 多条件查询列表
	String HOUSE_HOLD_SELECT_HOUSEHOLD_BY_FACTOR = "com.xianglin.xlnodecore.common.service.facade.HouseholdInfoService.selectXlcHouseholdInfoByDifferntFactor4POS";

	// 获取网点业务列表
	String SELECT_NODE_BUSINESS_LIST = "com.xianglin.xlnodecore.common.service.facade.NodeBusinessService.selectNodeBusinessList";

	/** 日志操作对外服务 **/

	// 添加新日志
	String LOG_SAVE = "com.xianglin.xlnodecore.common.service.facade.LogService.save";

	// 获取操作日志列表
	String LOG_GET_LIST = "com.xianglin.xlnodecore.common.service.facade.LogService.getOperationLog";

	// 网点经理银行卡号
	String NODE_MANAGER_BANKCARD_NUMBER = "node_manager_bankcard_Number";

	/** 功能接口控制服务 **/
	String QUERY_ACCESS_CONTROLS = "com.xianglin.xlnodecore.common.service.facade.AccessControlService.queryAccessControls4POS";

	/** 预约办卡服务 **/

	// 根据网点ID获取网点签约的银行
	String LOAD_NODE_BUSINESS_BANK_ID = "com.xianglin.xlnodecore.common.service.facade.NodeBusinessService.loadNodeBusinessBankByNodeid";

	// 村民是否重复申请
	String BOOKING_CARD_REPEATAPPLY = "com.xianglin.xlnodecore.common.service.facade.BankcardApplicationService.isRepeatApply4POS";

	// 预约手机号是否已重复存在
	String BOOKING_CARD_MOBILE_REPEATED = "com.xianglin.xlnodecore.common.service.facade.BankcardApplicationService.mobileIsRepeated4POS";

	// 预约登记
	String BOOKING_CARD_INFO = "com.xianglin.xlnodecore.common.service.facade.BankcardApplicationService.addBookingCardInfo4POS";

	// 办卡进度列表查询
	String BOOKING_CARD_BY_PAGE = "com.xianglin.xlnodecore.common.service.facade.BankcardApplicationService.getBookingCardByPage4POS";

	// 获取办卡详情
	String BOOKING_CARD_INFO_BY_ID = "com.xianglin.xlnodecore.common.service.facade.BankcardApplicationService.getBookingCardInfoById4POS";

	// 更新办卡进度
	String BOOKING_CARD_UPD_INFO = "com.xianglin.xlnodecore.common.service.facade.BankcardApplicationService.updBookingCardInfo4POS";
	//获取图片
	String BANK_CARD_IMAGE="com.xianglin.xlnodecore.common.service.facade.BankcardApplicationService.getBankcardImageData4POS";

	// 生成pdf
	String GENERRAPDFBY_NODEID_AND_CREDNTNUM = "com.xianglin.xlnodecore.common.service.facade.BankcardApplicationService.generaPdfByNodeidAndCredentNum4POS";

	// 校验验证码
	String VALIDATE_PHONE_CODE = "com.xianglin.xlnodecore.common.service.facade.BankcardApplicationService.validatePhoneCode4POS";

	/** 站长信息设置 **/

	// 更新站长信息
	String NODE_MANAGER_UPDATE = "com.xianglin.xlnodecore.common.service.facade.AccountNodeManagerService.update4POS";

	// xlStation的nodecode 获取nodepartyid
	String QUERY_NODE_PARTY_ID_BY_NODE_CODE = "com.xianglin.xlnodecore.common.service.facade.NodeBusinessService.queryNodepartyidByNodecode4POS";

	//查询异常log开关设置
	String QUERY_POS_LOG_SETTING = "com.xianglin.xlnodecore.common.service.facade.LogService.queryAllPosLogSetting4POS";
	
	//上传异常log开关设置
	String ADD_POS_LOG = "com.xianglin.xlnodecore.common.service.facade.LogService.addPosLog4POS";
	
}
