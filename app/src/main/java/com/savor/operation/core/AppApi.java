package com.savor.operation.core;

import android.content.Context;

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
        /**异常报告列表*/
        POST_ERROR_REPORT_LIST_JSON,
        /**异常报告详情页*/
        POST_ERROR_REPORT_DETAIL_JSON,
        /**获取用户维修记录*/
        POST_REPAIR_RECORD_LIST_JSON,
        /**维修记录*/
        POST_FIX_HISTORY_JSON,
    }

    /**
     * API_URLS:(URL集合)
     */
    public static final HashMap<Action, String> API_URLS = new HashMap<Action, String>() {
        private static final long serialVersionUID = -8469661978245513712L;

        {
            put(Action.TEST_GET_JSON, "https://www.baidu.com/");
            put(Action.TEST_GET_JSON, "https://www.baidu.com/");
            put(Action.POST_LOGIN_JSON, formatPhpUrl("Opclient/login/doLogin"));
            put(Action.POST_INDEX_JSON, formatPhpUrl("Opclient/index/index"));
            put(Action.POST_REPAIR_USER_JSON, formatPhpUrl("Opclient/Box/getAllRepairUser"));
            put(Action.POST_SEARCH_HOTEL_JSON, formatPhpUrl("Opclient/hotel/searchHotel"));
            put(Action.POST_ERROR_REPORT_LIST_JSON, formatPhpUrl("Opclient/ErrorReport/getList"));
            put(Action.POST_ERROR_REPORT_DETAIL_JSON, formatPhpUrl("Opclient/ErrorReport/getErrorDetail"));
            put(Action.POST_REPAIR_RECORD_LIST_JSON, formatPhpUrl("Opclient/Box/getRepairRecordListByUserid"));
            put(Action.POST_FIX_HISTORY_JSON, formatPhpUrl("Opclient/Hotel/getHotelVersionById"));

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

    public static void testGet(Context context, ApiRequestListener handler) {
//        SmallPlatInfoBySSDP smallPlatInfoBySSDP = Session.get(context).getSmallPlatInfoBySSDP();
//        API_URLS.put(Action.TEST_GET_JSON,"http://"+ smallPlatInfoBySSDP.getServerIp()+":"+ smallPlatInfoBySSDP.getCommandPort()+"/small-platform-1.0.0.0.1-SNAPSHOT/com/execute/call-tdc");
//        final HashMap<String, Object> params = new HashMap<String, Object>();
//        new AppServiceOk(context, Action.TEST_GET_JSON, handler, params).get();
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
    public static void searchHotel(Context context, String hotel_name,ApiRequestListener handler) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("hotel_name",hotel_name);
        new AppServiceOk(context, Action.POST_SEARCH_HOTEL_JSON, handler, params).post();
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
