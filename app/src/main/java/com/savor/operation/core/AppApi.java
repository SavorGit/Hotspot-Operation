package com.savor.operation.core;

import android.content.Context;
import android.text.TextUtils;

import com.common.api.utils.AppUtils;

import java.io.File;
import java.util.HashMap;

public class AppApi {
    public static final String APK_DOWNLOAD_FILENAME = "NewApp.apk";

    /**云平台php接口*/
    public static final String CLOUND_PLATFORM_PHP_URL = "http://devp.mobile.littlehotspot.com/";
//  public static final String CLOUND_PLATFORM_PHP_URL = "http://mobile.littlehotspot.com/";

    /**
     * 常用的一些key值 ,签名、时间戳、token、params
     */
    public static final String SIGN = "sign";
    public static final String TIME = "time";
    public static final String TOKEN = "token";
    public static final String PARAMS = "params";


    /**这是一个临时值，以请求时传入的值为准*/
    public static String tvBoxUrl;
    public static int hotelid;
    public static int roomid;

    /**
     * Action-自定义行为 注意：自定义后缀必须为以下结束 _FORM:该请求是Form表单请求方式 _JSON:该请求是Json字符串
     * _XML:该请求是XML请求描述文件 _GOODS_DESCRIPTION:图文详情 __NOSIGN:参数不需要进行加密
     */
    public static enum Action {
        TEST_POST_JSON,
        TEST_GET_JSON,
        /**登录*/
        POST_LOGIN_JSON,
        /**首页*/
        POST_INDEX_JSON,
        /**获取所有维修用户*/
        POST_REPAIR_USER_JSON,
        /**搜索酒楼*/
        POST_SEARCH_HOTEL_JSON,
        /**外包搜索酒楼*/
        POST_SINGLESEARCH_HOTEL_JSON,
        /**异常报告列表*/
        POST_ERROR_REPORT_LIST_JSON,
        /**异常报告详情页*/
        POST_ERROR_REPORT_DETAIL_JSON,
        /**获取用户维修记录*/
        POST_REPAIR_RECORD_LIST_JSON,
        /**维修记录*/
        POST_FIX_HISTORY_JSON,
        /**获取酒楼损坏配置表*/
        POST_DAMAGE_CONFIG_JSON,
        /**获取单机版*/
        POST_SINGLE_DAMAGE_CONFIG_JSON,
        /**提交保修记录*/
        POST_SUBMIT_DAMAGE_JSON,
        /**外包维修或者签到*/
        POST_SINGLE_SUBMIT_DAMAGE_JSON,
        TEST_DOWNLOAD_JSON,
        POST_UPGRADE_JSON,
        /**获取酒店版位详细信息（mac地址等）*/
        POST_HOTEL_MACINFO_JSON,
        /**任务类型*/
        POST_TASK_TYPE_JSON,
        /**发布*/
        POST_PUBLISH_JSON,
        /**版位列表*/
        POST_BOX_LIST_JSON,
        /**获取任务数量*/
        POST_TASK_NUM_JSON,
        /**查看者任务列表*/
        POST_VIEW_TASK_LIST_JSON,
        /**指派者任务列表*/
        POST_APPOINT_TASK_LIST_JSON,
        /**执行者任务列表*/
        POST_EXE_TASK_LIST_JSON,
        /**发布者任务列表*/
        POST_PUB_TASK_LIST_JSON,
        /**获取需要绑定到包间的版位*/
        POST_ROOM_BOX_JSON,
        /**绑定机顶盒*/
        POST_BIND_BOX_JSON,
        /**任务详情*/
        POST_TASK_DETAIL_JSON,
        /**拒绝任务*/
        POST_REFUSE_TASK_JSON,
        /**获取执行者按钮点击信息*/
        POST_EXE_INFO_JSON,
        /**执行者提交任务*/
        POST_REPORT_MISSION_JSON,
        /**获取任务的执行者列表*/
        POST_EXE_USER_LIST_JSON,
        /**指派任务*/
        POST_APPOIN_TASK_JSON,
        /**请求getip接口*/
        GET_IP_JSON,
        /**维修记录*/
        POST_POSITION_LIST_JSON,
        /**机顶盒状态列表*/
        POST_STATE_CONF_JSON,
        /**获取巡视列表*/
        POST_INSPECTOR_JSON,
        /**获取机顶盒详情*/
        POST_BOX_DETAIL_JSON,
        /**获取正在下载的节目单列表*/
        POST_LOADING_PRO_JSON,
        /**机顶盒状态 正常冻结损坏*/
        POST_BOX_STATECONFIG_JSON,
        /**一键测试*/
        POST_ONEKEY_TEST_JSON,
    }

    /**
     * API_URLS:(URL集合)
     */
    public static final HashMap<Action, String> API_URLS = new HashMap<Action, String>() {
        private static final long serialVersionUID = -8469661978245513712L;

        {
            put(Action.TEST_GET_JSON, "https://www.baidu.com/");
            put(Action.TEST_GET_JSON, "https://www.baidu.com/");
            put(Action.POST_LOGIN_JSON, formatPhpUrl("Opclient11/login/doLogin"));
            put(Action.POST_INDEX_JSON, formatPhpUrl("Opclient/index/index"));
            put(Action.POST_REPAIR_USER_JSON, formatPhpUrl("Opclient/Box/getAllRepairUser"));
            put(Action.POST_SEARCH_HOTEL_JSON, formatPhpUrl("Opclient11/Hotel/searchHotel"));
            put(Action.POST_SINGLESEARCH_HOTEL_JSON, formatPhpUrl("Tasksubcontract/hotel/searchHotel"));
            put(Action.POST_ERROR_REPORT_LIST_JSON, formatPhpUrl("Opclient/ErrorReport/getList"));
            put(Action.POST_ERROR_REPORT_DETAIL_JSON, formatPhpUrl("Opclient/ErrorReport/getErrorDetail"));
            put(Action.POST_REPAIR_RECORD_LIST_JSON, formatPhpUrl("Opclient/Box/getRepairRecordListByUserid"));
            put(Action.POST_FIX_HISTORY_JSON, formatPhpUrl("Opclient/Hotel/getHotelVersionById"));
            put(Action.POST_DAMAGE_CONFIG_JSON, formatPhpUrl("Opclient/Box/getHotelBoxDamageConfig"));
            put(Action.POST_SUBMIT_DAMAGE_JSON, formatPhpUrl("Opclient/Box/InsertBoxDamage"));
            put(Action.POST_UPGRADE_JSON, formatPhpUrl("Opclient/Version/index"));
            put(Action.POST_HOTEL_MACINFO_JSON, formatPhpUrl("Opclient/Hotel/getHotelMacInfoById"));
            put(Action.POST_TASK_TYPE_JSON, formatPhpUrl("Opclient11/Basedata/getTaskTypeList"));
            put(Action.POST_PUBLISH_JSON, formatPhpUrl("Opclient11/Task/pubTask"));
            put(Action.POST_BOX_LIST_JSON, formatPhpUrl("Opclient11/Box/getBoxList"));
            put(Action.POST_TASK_NUM_JSON, formatPhpUrl("Opclient11/Task/countTaskNums"));

            put(Action.POST_VIEW_TASK_LIST_JSON, formatPhpUrl("Opclient11/Task/viewTaskList"));
            put(Action.POST_APPOINT_TASK_LIST_JSON, formatPhpUrl("Opclient11/task/appointTaskList"));
            put(Action.POST_EXE_TASK_LIST_JSON, formatPhpUrl("Opclient11/Task/exeTaskList"));
            put(Action.POST_PUB_TASK_LIST_JSON, formatPhpUrl("Opclient11/task/pubTaskList"));
            put(Action.POST_ROOM_BOX_JSON, formatPhpUrl("Opclient11/Bindbox/getBoxList"));
            put(Action.POST_BIND_BOX_JSON, formatPhpUrl("Opclient11/Bindbox/bindBox"));
            put(Action.POST_TASK_DETAIL_JSON, formatPhpUrl("Opclient11/task/taskDetail"));
            put(Action.POST_REFUSE_TASK_JSON, formatPhpUrl("Opclient11/task/refuseTask"));
            put(Action.POST_EXE_INFO_JSON, formatPhpUrl("Opclient11/Mission/getexecutorInfo"));
            put(Action.POST_REPORT_MISSION_JSON, formatPhpUrl("Opclient11/Mission/reportMission"));
            put(Action.POST_EXE_USER_LIST_JSON, formatPhpUrl("Opclient11/Task/getExeUserList"));
            put(Action.POST_APPOIN_TASK_JSON, formatPhpUrl("Opclient11/Task/appointTask"));
            put(Action.GET_IP_JSON, formatPhpUrl("basedata/Ip/getIp"));
            put(Action.POST_POSITION_LIST_JSON, formatPhpUrl("Tasksubcontract/Hotel/getSingleHotelVersionById"));

            put(Action.POST_SINGLE_SUBMIT_DAMAGE_JSON, formatPhpUrl("Tasksubcontract/Box/insertSingleBoxDamage"));
            put(Action.POST_SINGLE_DAMAGE_CONFIG_JSON, formatPhpUrl("Tasksubcontract/Box/getHotelBoxDamageConfig"));
            put(Action.POST_STATE_CONF_JSON, formatPhpUrl("Opclient20/Box/stateConf"));
            put(Action.POST_INSPECTOR_JSON, formatPhpUrl("Opclient20/Inspector/getMyInspect"));
            put(Action.POST_BOX_DETAIL_JSON, formatPhpUrl("Opclient20/Box/contentDetail"));
            put(Action.POST_LOADING_PRO_JSON, formatPhpUrl("Opclient20/Box/getDownloadPro"));
            put(Action.POST_BOX_STATECONFIG_JSON, formatPhpUrl("Opclient20/Box/stateConf"));
            put(Action.POST_ONEKEY_TEST_JSON, formatPhpUrl("Opclient20/Box/oneKeyCheck"));
        }
    };


    /**
     * php后台接口
     * @param url
     * @return
     */
    private static String formatPhpUrl(String url) {
        return CLOUND_PLATFORM_PHP_URL +url;
    }

    public static void testPost(Context context, String orderNo, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("loginfield", "15901559579");
        params.put("password", "123456");
        params.put("dr_rg_cd", "86");
        params.put("version_code", 19 + "");
        new AppServiceOk(context, Action.TEST_POST_JSON, handler, params).post(false, false, true, true);

    }

    /**升级*/
    public static void Upgrade(Context context,ApiRequestListener handler,int versionCode) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("versionCode", versionCode);
        params.put("deviceType", 3);
        new AppServiceOk(context,Action.POST_UPGRADE_JSON,handler,params).post();
    }
    public static void testGet(Context context, ApiRequestListener handler) {
//        SmallPlatInfoBySSDP smallPlatInfoBySSDP = Session.get(context).getSmallPlatInfoBySSDP();
//        API_URLS.put(Action.TEST_GET_JSON,"http://"+ smallPlatInfoBySSDP.getServerIp()+":"+ smallPlatInfoBySSDP.getCommandPort()+"/small-platform-1.0.0.0.1-SNAPSHOT/com/execute/call-tdc");
//        final HashMap<String, Object> params = new HashMap<String, Object>();
//        new AppServiceOk(context, Action.TEST_GET_JSON, handler, params).get();
    }

    public static void downApp(Context context, String url, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        String target = AppUtils.getPath(context, AppUtils.StorageFile.file);

        String targetApk = target + APK_DOWNLOAD_FILENAME;
        File tarFile = new File(targetApk);
        if (tarFile.exists()) {
            tarFile.delete();
        }
        new AppServiceOk(context, Action.TEST_DOWNLOAD_JSON, handler, params).downLoad(url, targetApk);

    }
    /**
     * 登录
     * @param context 上下文
     * @param username 用户名
     * @param password 密码
     * @param handler 接口回调
     */
    public static void login(Context context, String username,String password, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("username", username);
        params.put("password", password);
        new AppServiceOk(context, Action.POST_LOGIN_JSON, handler, params).post();
    }

    /**
     * 获取运维端首页信息
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getIndexInfo(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        new AppServiceOk(context, Action.POST_INDEX_JSON, handler, params).post();
    }

    public static void getAllRepairUser(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        new AppServiceOk(context, Action.POST_REPAIR_USER_JSON, handler, params).post();
    }

    /**
     * 获取运维端首页信息
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void searchHotel(Context context, String hotel_name,String area_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("hotel_name",hotel_name);
        params.put("area_id",area_id);
        new AppServiceOk(context, Action.POST_SEARCH_HOTEL_JSON, handler, params).post();
    }

    /**
     * 外包搜索酒楼
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void searchSingleHotel(Context context, String hotel_name, String area_id, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("hotel_name",hotel_name);
        params.put("area_id",area_id);
        new AppServiceOk(context, Action.POST_SINGLESEARCH_HOTEL_JSON, handler, params).post();
    }

    /**
     * 获取版位信息
     * @param context 上下文
     * @param handler 接口回调
     * @param hotelId 酒店id
     */
    public static void getPositionList(Context context, String hotelId,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("hotel_id",hotelId);
        new AppServiceOk(context, Action.POST_POSITION_LIST_JSON, handler, params).post();
    }

    /**
     * 异常报告列表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getErrorReportList(Context context, String id,int pageSize,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("id",id);
        params.put("pageSize",pageSize);
        new AppServiceOk(context, Action.POST_ERROR_REPORT_LIST_JSON, handler, params).post();
    }

    /**
     * 异常报告详情页
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getErrorDetail(Context context, String detail_id, String error_id,int pageSize,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("detail_id",detail_id);
        params.put("error_id",error_id);
        params.put("pageSize",pageSize);
        new AppServiceOk(context, Action.POST_ERROR_REPORT_DETAIL_JSON, handler, params).post();
    }

    /**
     * 获取用户维修记录
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getRepairRecordList(Context context, String userid,int page_num,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("userid",userid);
        params.put("page_num",page_num);
        new AppServiceOk(context, Action.POST_REPAIR_RECORD_LIST_JSON, handler, params).post();
    }

    /**
     * 获取版位信息和维修记录
     * @param context 上下文
     * @param handler 接口回调
     * @param hotelId 酒店id
     */
    public static void getFixHistory(Context context, String hotelId,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("hotel_id",hotelId);
        new AppServiceOk(context, Action.POST_FIX_HISTORY_JSON, handler, params).post();
    }

    /**
     * 酒店损坏配置表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getDamageConfig(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        new AppServiceOk(context, Action.POST_DAMAGE_CONFIG_JSON, handler, params).post();
    }

    /**
     * 酒店损坏配置表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getSingleDamageConfig(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        new AppServiceOk(context, Action.POST_SINGLE_DAMAGE_CONFIG_JSON, handler, params).post();
    }

    /**
     * 提交保修记录
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void submitDamage(Context context,String box_mac,String hotel_id,String box_state,
                                    String remark,String repair_num_str,String state,
                                    String type,String userid, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("box_mac",box_mac);
        params.put("hotel_id",hotel_id);
        params.put("box_state",box_state);
        params.put("remark",remark);
        params.put("repair_num_str",repair_num_str);
        params.put("state",state);
        params.put("type",type);
        params.put("userid",userid);
        new AppServiceOk(context, Action.POST_SUBMIT_DAMAGE_JSON, handler, params).post();
    }

    /**
     * 提交保修记录
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void submitSingleDamage(Context context, String bid, String hotel_id,
                                          String remark, String repair_img, String repair_type, String srtype, String state,
                                          String userid, String current_location, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("bid",bid);
        params.put("hotel_id",hotel_id);
        params.put("remark",remark);
        params.put("repair_img",repair_img);
        params.put("repair_type",repair_type);
        if(!TextUtils.isEmpty(current_location)) {
            params.put("current_location",current_location);
        }
        params.put("srtype",srtype);
        params.put("userid",userid);
        new AppServiceOk(context, Action.POST_SINGLE_SUBMIT_DAMAGE_JSON, handler, params).post();
    }

    /**
     * 提交保修记录
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getHotelMacInfo(Context context,String hotel_id, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("hotel_id",hotel_id);
        new AppServiceOk(context, Action.POST_HOTEL_MACINFO_JSON, handler, params).post();
    }

    /**
     * 提交保修记录
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getTaskList(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        new AppServiceOk(context, Action.POST_TASK_TYPE_JSON, handler, params).post();
    }

    /**
     * 发布任务
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void publishTask(Context context, String addr,String contractor,String hotel_id,
                                   String mobile,String publish_user_id,String repair_info,
                                   String task_emerge,String task_type,
                                   String tv_nums,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("addr",addr);
        params.put("contractor",contractor);
        params.put("hotel_id",hotel_id);
        params.put("mobile",mobile);
        params.put("publish_user_id",publish_user_id);
        params.put("repair_info",repair_info);
        params.put("task_emerge",task_emerge);
        params.put("task_type",task_type);
        params.put("tv_nums",tv_nums);
        new AppServiceOk(context, Action.POST_PUBLISH_JSON, handler, params).post();
    }

    /**
     * 查看者任务列表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getviewTaskList(Context context, int page,int state,String user_id,String city_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("page",page);
        params.put("state",state);
        params.put("user_id",user_id);
        params.put("city_id",city_id);
        new AppServiceOk(context, Action.POST_VIEW_TASK_LIST_JSON, handler, params).post();
    }

    /**
     * 指派者任务列表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getAppointTaskList(Context context, int page,int state,String user_id,String city_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("page",page);
        params.put("state",state);
        params.put("user_id",user_id);
        params.put("city_id",city_id);
        new AppServiceOk(context, Action.POST_APPOINT_TASK_LIST_JSON, handler, params).post();
    }
    /**
     * 执行者任务列表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getExeTaskList(Context context, int page,int state,String user_id,String city_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("page",page);
        params.put("state",state);
        params.put("user_id",user_id);
        params.put("city_id",city_id);
        new AppServiceOk(context, Action.POST_EXE_TASK_LIST_JSON, handler, params).post();
    }
    /**
     * 发布者任务列表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getPubTaskList(Context context, int page,int state,String user_id,String city_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("page",page);
        params.put("state",state);
        params.put("user_id",user_id);
        params.put("city_id",city_id);
        new AppServiceOk(context, Action.POST_PUB_TASK_LIST_JSON, handler, params).post();
    }
    /**
     * 提交保修记录
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getBoxList(Context context, String hotel_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("hotel_id",hotel_id);
        new AppServiceOk(context, Action.POST_BOX_LIST_JSON, handler, params).post();
    }

    /**
     * 提交保修记录
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getTaskNum(Context context, String area_id,String user_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("area_id",area_id);
        params.put("user_id",user_id);
        new AppServiceOk(context, Action.POST_TASK_NUM_JSON, handler, params).post();
    }


    /**
     * 获取需要绑定到包间的版位
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getRoomBoxList(Context context, String hotel_id,String room_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("hotel_id",hotel_id);
        params.put("room_id",room_id);
        new AppServiceOk(context, Action.POST_ROOM_BOX_JSON, handler, params).post();
    }

    /**
     * 提交绑定机顶盒
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void bindBox(Context context, String hotel_id,String box_id,String new_mac,String room_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("hotel_id",hotel_id);
        params.put("room_id",room_id);
        params.put("box_id",box_id);
        params.put("new_mac",new_mac);
        new AppServiceOk(context, Action.POST_BIND_BOX_JSON, handler, params).post();
    }

    /**
     * 任务详情
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void taskDetail(Context context, String task_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("task_id",task_id);
        new AppServiceOk(context, Action.POST_TASK_DETAIL_JSON, handler, params).post();
    }


    /**
     * 拒绝任务
     * @param context 上下文
     * @param handler 接口回调
     *
     */
    public static void refuseTask(Context context, String refuse_desc ,String user_id ,String task_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("refuse_desc",refuse_desc);
        params.put("task_id",task_id);
        params.put("user_id",user_id);
        new AppServiceOk(context, Action.POST_REFUSE_TASK_JSON, handler, params).post();
    }

    /**
     * 获取执行者按钮点击信息
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getExecutorInfo(Context context, String task_type ,String task_id,String user_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("task_type",task_type);
        params.put("task_id",task_id);
        params.put("user_id",user_id);
        new AppServiceOk(context, Action.POST_EXE_INFO_JSON, handler, params).post();
    }

    /**
     * 执行者提交任务
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void reportMission(Context context, String box_id ,String remark,String repair_img,String state
            ,String task_id,String task_type,String user_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("box_id",box_id);
        params.put("remark",remark);
        params.put("repair_img",repair_img);
        params.put("state",state);
        params.put("task_id",task_id);
        params.put("task_type",task_type);
        params.put("user_id",user_id);
        new AppServiceOk(context, Action.POST_REPORT_MISSION_JSON, handler, params).post();
    }

    /**
     * 获取任务的执行者列表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getExeUserList(Context context, String exe_date ,String is_lead_install,String task_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("exe_date",exe_date);
        params.put("is_lead_install",is_lead_install);
        params.put("task_id",task_id);
        new AppServiceOk(context, Action.POST_EXE_USER_LIST_JSON, handler, params).post();
    }

    /**
     * 指派任务
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void appointTask(Context context, String appoint_exe_time ,String appoint_user_id,String exe_user_id,String task_id,String is_lead_install,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("appoint_exe_time",appoint_exe_time);
        params.put("appoint_user_id",appoint_user_id);
        params.put("exe_user_id",exe_user_id);
        params.put("task_id",task_id);
        params.put("is_lead_install",is_lead_install);
        new AppServiceOk(context, Action.POST_APPOIN_TASK_JSON, handler, params).post();
    }

    /**获取小平台地址*/
    public static void getSmallPlatformIp(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        new AppServiceOk(context,Action.GET_IP_JSON,handler,params).get();
    }

    /**机顶盒状态列表*/
    public static void stateConf(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        new AppServiceOk(context,Action.POST_STATE_CONF_JSON,handler,params).post();
    }

    /**
     * 获取巡视列表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getMyInspect(Context context, String pageNum ,String pageSize,String user_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("pageNum",pageNum);
        params.put("pageSize",pageSize);
        params.put("user_id",user_id);
        new AppServiceOk(context, Action.POST_INSPECTOR_JSON, handler, params).post();
    }

    /**
     * 获取机顶盒详情
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getBoxDetail(Context context, String box_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("box_id",box_id);
        new AppServiceOk(context, Action.POST_BOX_DETAIL_JSON, handler, params).post();
    }

    /**
     * 获取正在下载节目单列表
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getLoadingProList(Context context, String pro_download_period,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("pro_download_period",pro_download_period);
        new AppServiceOk(context, Action.POST_LOADING_PRO_JSON, handler, params).post();
    }

    /**
     * 获取机顶盒状态（正常、冻结、报损）
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void getStateConfig(Context context, ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        new AppServiceOk(context, Action.POST_BOX_STATECONFIG_JSON, handler, params).post();
    }

    /**
     * 一键测试
     * @param context 上下文
     * @param handler 接口回调
     */
    public static void oneKeyTest(Context context, String box_id,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("box_id",box_id);
        new AppServiceOk(context, Action.POST_ONEKEY_TEST_JSON, handler, params).post();
    }

    // 超时（网络）异常
    public static final String ERROR_TIMEOUT = "3001";
    // 业务异常
    public static final String ERROR_BUSSINESS = "3002";
    // 网络断开
    public static final String ERROR_NETWORK_FAILED = "3003";

    public static final String RESPONSE_CACHE = "3004";

    /**
     * 从这里定义业务的错误码
     */
    public static final int CMS_RESPONSE_STATE_SUCCESS = 1001;
    public static final int CLOUND_RESPONSE_STATE_SUCCESS = 10000;

    /**机顶盒返回响应码*/
    public static final int TVBOX_RESPONSE_STATE_SUCCESS = 0;
    public static final int TVBOX_RESPONSE_STATE_ERROR = -1;
    public static final int TVBOX_RESPONSE_STATE_FORCE = 4;
    /**大小图不匹配失败*/
    public static final int TVBOX_RESPONSE_NOT_MATCH = 10002;
    public static final int TVBOX_RESPONSE_VIDEO_COMPLETE = 10003;

    /**
     * 数据返回错误
     */
    public static final int HTTP_RESPONSE_STATE_ERROR = 101;
    /**没有更多数据响应码*/
    public static final int HTTP_RESPONSE_CODE_NO_DATA = 10060;
}
