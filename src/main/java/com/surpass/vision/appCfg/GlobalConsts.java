package com.surpass.vision.appCfg;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.surpass.vision.domain.PointGroupData;

public final class GlobalConsts {
	
	/**
	 * WebSocket的定义
	 */
    public static final String TOPIC = "/topic/greetings";
    public static final String ENDPOINT = "/gs-guide-websocket";
    public static final String APP_PREFIX = "/app";
    public static final String HELLO_MAPPING = "/hello";

    /**
     * 返回值状态码
     */
    public final static String ResultCode_SUCCESS = "000"; // 成功
    public final static String ResultCode_FAIL = "001"; // 失败
    public final static String ResultCode_NO_LOGIN = "003"; // 未登录
    public final static String ResultCode_NO_PRIVILEGE = "004"; // 权限不对
    public final static String ResultCode_INVALIDATION = "005"; // 无效
    public final static String ResultCode_AuthericationError = "006"; // token错
    
//    public final static String ResultMessage
    
    /**
     * Redis中Key的定义
     */
	public static final String Key_UserSpace_pre_ = "Key_UserSpace_pre_"; // 用户空间key前缀
	public static final String Key_UserSpaceFlash_pre_ = "Key_UserSpaceFlash_pre_"; // 用户空间key前缀
	public static final String Key_UserInfo_Pre_ = "Key_UserInfo_Pre_"; // 用户key前缀
	public static final String Key_DepartInfo_Pre_ = "Key_DepartInfo_Pre_";
    public final static String Key_Graph_pre_ = "Key_Graph_pre_"; // 图形key前缀
	public static final String Key_HistoryData_pre_ = "Key_HistoryData_pre_"; // 历史数据组key前缀
	public static final String Key_RealTimeData_pre_ = "Key_RealTimeData_pre_";
	public static final String Key_AlertData_pre_ = "Key_AlertData_pre_";
	public static final String Key_LineAlertData_pre_ = "Key_LineAlertData_pre_";
	public static final String Key_XYGraph_pre_ = "Key_XYGraph_pre_";

    /**
     * PointGroupData表中，type的值的类型，没有写枚举。
     */
	public static final String Type_xygraph_ = "Key_XYGraph_pre_";
	public static final String Type_realtimedata_ = "Key_RealTimeData_pre_";
	public static final String Type_alertdata_ = "Key_AlertData_pre_";
	public static final String Type_historydata_ = "Key_HistoryData_pre_";
	public static final String Type_linealertdata_ = "Key_LineAlertData_pre_";
	public static final String Type_graph_ = "Key_Graph_pre_";

//	public static final String Key_AlertData_pre_ = null;

	public static final String Key_Point_pre = "Key_Point_pre";
	public static final String Key_Device_pre_ = "Key_Device_pre_";
	public static final String Key_Server_pre_ = "Key_Server_pre_";

	public static final String Key_ControlMessage_pre = "Key_ControlMessage_pre";

    
    /**
     * 很多ID拼接成字符串时，分隔字符
     */
    public final static String Key_splitChar = ",";
	/**
	 * 服务器和tagName连接的符号
	 * ---------------------------------------
	 * @author 刘强 2019年8月29日 上午9:18:36 
	 */
	public static final String Key_splitCharServerPoint = "\\";
	public static final String Key_ServerNamePre = "\\\\";

	/**
	 * 解析图形文件
	 */
	public static final String PointTag = "text";
	public static final String RectTag = "rect";
	public static final String PointID = "id";
	public static final String GPointID = "PBD:PtTagName";
	public static final String PBMultiStateTag = "PB:MultiState";
	public static final String PBMSStateTag = "PB:MSState";
	public static final String GIsMultiState = "PB:IsMultiState";
	public static final String GType = "PB:Type";
	public static final String GNumberFormat = "PB:NumberFormat";
	public static final String PBDProperty = "PBD:Property";

	public static final String GraphElement = "svg";
	public static final String ScriptElement = "script";
	public static final String GPointTag = "g";


	// 设备中文注释的长度
	public static final int DeviceNoteLength = 80;
	// token加解密时的加长前缀
	public static final String SecKey_Pre = "SecKey_Pre";

	
	/**
	 * 权限功能名称
	 */
	public static final String Operation_createRealTimeData = "Operation_createRealTimeData";
	public static final String Operation_updateRealTimeData = "Operation_updateRealTimeData";
	public static final String Operation_createLineAlertData = "Operation_createLineAlertData";
	public static final String Operation_updateLineAlertData = "Operation_updateLineAlertData";
	public static final String Operation_getRealTimeDataList = "Operation_getRealTimeDataList";
	public static final String Operation_getAlertDataList = "Operation_getAlertDataList";
	public static final String Operation_createAlertData = "Operation_createAlertData";
	public static final String Operation_updateAlertData = "Operation_updateAlertData";
	public static final String Operation_getHistoryDataList = "Operation_getHistoryDataList";
	public static final String Operation_createHistoryData = "Operation_createHistoryData";
	public static final String Operation_updateHistoryData = "Operation_updateHistoryData";
	public static final String Operation_getLineAlertDataList = "Operation_getLineAlertDataList";
	public static final String Operation_getXYGraphList = "Operation_getXYGraphList";
	public static final String Operation_createXYGraph = "Operation_createXYGraph";
	public static final String Operation_updateXYGraph = "Operation_updateXYGraph";
	public static final String Operation_getGraphList = "Operation_getGraphList";
	public static final String Operation_shareGraph = "Operation_shareGraph";
	public static final String Operation_getHistoryData = "Operation_getHistoryData";
	public static final String Operation_uploadGraphFile = "Operation_uploadGraphFile";
	public static final String Operation_updateGraphFile = "Operation_updateGraphFile";
	public static final String Operation_deleteGraphFile = "Operation_deleteGraphFile";
	public static final String Operation_createUser = "Operation_createUser";
	public static final String Operation_updateUserInfo = "Operation_updateUserInfo";
	public static final String Operation_getUserInfo = "Operation_getUserInfo";
	public static final String Operation_createDepartment = "Operation_createDepartment";

	
	public static final String KeyAggrandizement = "KeyAggrandizement"; // 增加
	public static final String KeyDecrement = "KeyDecrement"; // 减少
	
	
	public static final String AJAX_REQUEST_HEADER = "XMLHttpRequest";     //Ajax请求的Header
	public static final String SESSION_USER = "SESSION_USER";
	
	
	public static final String UserAdminID = "2";
	public static final Integer UserRoleAdmin = 1;
	public static final Integer UserRoleConfiguration = 2;
	public static final Integer UserRoleUser = 3;
	public static final Integer UserRoleGuest = 4;
	
	public static final String Default_ServerName = "demo";




}
