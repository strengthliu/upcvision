package com.surpass.vision.appCfg;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.surpass.vision.domain.PointGroupData;

public class GlobalConsts {
	
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
	public static final String Key_UserInfo_Pre = "Key_UserInfo_Pre"; // 用户key前缀
    public final static String Key_Graph_pre_ = "Key_Graph_pre_"; // 图形key前缀
	public static final String Key_HistoryData_pre_ = "Key_HistoryData_pre_"; // 历史数据组key前缀
	public static final String Key_RealTimeData_pre_ = "Key_RealTimeData_pre_";
	
	public static final String Key_Point_pre = "Key_Point_pre";
	public static final String Key_Device_pre_ = "Key_Device_pre_";
	public static final String Key_Server_pre_ = "Key_Server_pre_";

    
    /**
     * 很多ID拼接成字符串时，分隔字符
     */
    public final static String Key_splitChar = ",";

    /**
     * PointGroupData表中，type的值的类型，没有写枚举。
     */
	public static final String Type_xygraph_ = "xygraph";
	public static final String Type_realtimedata_ = "realtimedata";
	public static final String Type_alertdata_ = "alertdata";
	public static final String Type_historydata_ = "historydata";
	public static final String Type_linealertdata_ = "linealertdata";
	public static final String Type_graph_ = "graph";

	/**
	 * 解析图形文件
	 */
	public static final String PointTag = "text";
	public static final String PointID = "id";
	public static final String GraphElement = "svg";

	// 设备中文注释的长度
	public static final int DeviceNoteLength = 80;
	// token加解密时的加长前缀
	public static final String SecKey_Pre = "SecKey_Pre";

	
	/**
	 * 权限功能名称
	 */
	public static final String Operation_createOrUpdateRealTimeData = "Operation_createOrUpdateRealTimeData";
	public static final String Operation_createOrUpdateLineAlertData = "Operation_createOrUpdateLineAlertData";
	public static final String Operation_getRealTimeDataList = "Operation_getRealTimeDataList";
	
	
	public static final String KeyAggrandizement = "KeyAggrandizement"; // 增加
	public static final String KeyDecrement = "KeyDecrement"; // 减少



}
