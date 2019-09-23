class User{

}

class UserInfo extends User{

}

class UserRight{

}


class UserSpace {
    constructor(height, width) {
        this.height = height;
        this.width = width;
    }
}
/**
 * 用户可见的图
 */
UserSpace.prototype.graphs = new Map();
UserSpace.prototype.alertData = new Map();
UserSpace.prototype.historyData = new Map();
UserSpace.prototype.lineAlertData = new Map();
UserSpace.prototype.realTimeData = new Map();
UserSpace.prototype.xyGraph = new Map();


    /**
	 * 用户权限信息
	 */
    //UserRight right;
UserSpace.prototype.right = new UserRight();
    /**
	 * 
	 */
UserSpace.prototype.token = "";
// Integer userID;

    /**
	 * 用户信息
	 */
UserSpace.prototype.user = new UserInfo();

class Graph{
    changed=false;
    children = new Map();
    creater = new User();
    id=0;
    isFile = false;
    isSVG = false;
    name="";
    otherrule1="";
    otherrule2="";
    // 权限信息
    owner = new User(); // 拥有者，默认为创建者
    path="";
    // 点位信息
    points=new List();
    shared=new List(); // 共享者
}

class PointGroup {
//    Integer id;
//    String name;
//    List points;
//    String type;
//
//    // 权限信息
//    User owner; // 拥有者，默认为创建者
//    // 点位信息
//    User creater; // 创建者
//
//
//    List shared; // 共享者
//

}

class Point {

}

class AlertData extends PointGroup{

}

class HistoryData extends PointGroup{

}

class LineAlertData extends PointGroup{

}

class RealTimeData extends PointGroup{

}

class XYGraph  extends PointGroup{

}



class GlobalConsts {
	static a = "adfs";
    /**
	 * WebSocket的定义
	 */
	static TOPIC = "/topic/greetings";
    static ENDPOINT = "/gs-guide-websocket";
    static APP_PREFIX = "/app";
    static HELLO_MAPPING = "/hello";

    /**
	 * 返回值状态码
	 */
    static ResultCode_SUCCESS = "000"; // 成功
    static ResultCode_FAIL = "001"; // 失败
    static ResultCode_NO_LOGIN = "003"; // 未登录
    static ResultCode_NO_PRIVILEGE = "004"; // 权限不对
    static ResultCode_INVALIDATION = "005"; // 无效
    static ResultCode_AuthericationError = "006"; // token错

    /**
	 * Redis中Key的定义 String Key_UserSpace_pre_ = "Key_UserSpace_pre_"; //
	 * 用户空间key前缀 public static final String Key_UserInfo_Pre =
	 * "Key_UserInfo_Pre"; // 用户key前缀 public final static String Key_Graph_pre_ =
	 * "Key_Graph_pre_"; // 图形key前缀 public static final String
	 * Key_HistoryData_pre_ = "Key_HistoryData_pre_"; // 历史数据组key前缀 public
	 * static final String Key_RealTimeData_pre_ = "Key_RealTimeData_pre_";
	 * 
	 * public static final String Key_Point_pre = "Key_Point_pre"; public static
	 * final String Key_Device_pre_ = "Key_Device_pre_"; public static final
	 * String Key_Server_pre_ = "Key_Server_pre_";
	 */


    /**
	 * 很多ID拼接成字符串时，分隔字符
	 */
    static Key_splitChar = ",";

    /**
	 * PointGroupData表中，type的值的类型，没有写枚举。
	 */
    static Type_xygraph_ = "xygraph";
    static Type_realtimedata_ = "realtimedata";
    static Type_alertdata_ = "alertdata";
    static Type_historydata_ = "historydata";
    static Type_linealertdata_ = "linealertdata";
    static Type_graph_ = "graph";

    /**
	 * 解析图形文件 public static final String PointTag = "text"; public static final
	 * String PointID = "id"; public static final String GraphElement = "svg";
	 *  // 设备中文注释的长度 public static final int DeviceNoteLength = 80;
	 * 
	 */

    /**
	 * 权限功能名称
	 */
    static Operation_createOrUpdateRealTimeData = "Operation_createOrUpdateRealTimeData";
    static Operation_getRealTimeDataList = "Operation_getRealTimeDataList";


}

/**
 * 全局变量
 */
var userSpace ;
var user;
var token;

var _galleryKey = null;

