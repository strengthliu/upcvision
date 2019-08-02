
class UserSpace {
    constructor(height, width) {
        this.height = height;
        this.width = width;
    }
    /**
     * 用户可见的图
     */
    Map graphs;
    Map alertData;
    Map historyData;
    Map lineAlertData;
    Map realTimeData;
    Map xyGraph;

    /**
     * 用户权限信息
     */
    UserRight right;

    /**
     *
     */
    String token;
//	Integer userID;

    /**
     * 用户信息
     */
    UserInfo user;

}

class Graph{
    int changed;
    Map children;
    User creater; // 创建者
    Integer id;
    boolean isFile;
    boolean isSVG;

    String name;

    String otherrule1;

    String otherrule2;

    // 权限信息
    User owner; // 拥有者，默认为创建者

    String path;

    //ArrayList<String >pointIDs;
    //ret.setChildren(fl.getChildren());

    // 点位信息
    List points;

    List shared; // 共享者

}

class PointGroup {
    Integer id;
    String name;
    List points;
    String type;

    // 权限信息
    User owner; // 拥有者，默认为创建者
    // 点位信息
    User creater; // 创建者


    List shared; // 共享者


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

class UserRight{

}

class UserInfo extends User{

}

class User{

}

class GlobalConsts {
    /**
     * WebSocket的定义
     */
    String TOPIC = "/topic/greetings";
    String ENDPOINT = "/gs-guide-websocket";
    String APP_PREFIX = "/app";
    String HELLO_MAPPING = "/hello";

    /**
     * 返回值状态码
     */
    String ResultCode_SUCCESS = "000"; // 成功
    String ResultCode_FAIL = "001"; // 失败
    String ResultCode_NO_LOGIN = "003"; // 未登录
    String ResultCode_NO_PRIVILEGE = "004"; // 权限不对
    String ResultCode_INVALIDATION = "005"; // 无效
    String ResultCode_AuthericationError = "006"; // token错

    /**
     * Redis中Key的定义
    String Key_UserSpace_pre_ = "Key_UserSpace_pre_"; // 用户空间key前缀
    public static final String Key_UserInfo_Pre = "Key_UserInfo_Pre"; // 用户key前缀
    public final static String Key_Graph_pre_ = "Key_Graph_pre_"; // 图形key前缀
    public static final String Key_HistoryData_pre_ = "Key_HistoryData_pre_"; // 历史数据组key前缀
    public static final String Key_RealTimeData_pre_ = "Key_RealTimeData_pre_";

    public static final String Key_Point_pre = "Key_Point_pre";
    public static final String Key_Device_pre_ = "Key_Device_pre_";
    public static final String Key_Server_pre_ = "Key_Server_pre_";
     */


    /**
     * 很多ID拼接成字符串时，分隔字符
     */
    public final static String Key_splitChar = ",";

    /**
     * PointGroupData表中，type的值的类型，没有写枚举。
     */
    String Type_xygraph_ = "xygraph";
    String Type_realtimedata_ = "realtimedata";
    String Type_alertdata_ = "alertdata";
    String Type_historydata_ = "historydata";
    String Type_linealertdata_ = "linealertdata";
    String Type_graph_ = "graph";

    /**
     * 解析图形文件
    public static final String PointTag = "text";
    public static final String PointID = "id";
    public static final String GraphElement = "svg";

    // 设备中文注释的长度
    public static final int DeviceNoteLength = 80;

     */

    /**
     * 权限功能名称
     */
    public static final String Operation_createOrUpdateRealTimeData = "Operation_createOrUpdateRealTimeData";
    public static final String Operation_getRealTimeDataList = "Operation_getRealTimeDataList";


}
