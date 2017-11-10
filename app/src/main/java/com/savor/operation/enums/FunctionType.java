package com.savor.operation.enums;

import java.io.Serializable;

/**
 * 首页功能分类类型
 * Created by hezd on 2017/11/3.
 */

public enum FunctionType implements Serializable {
    /**发布任务*/
    PUBLISH_TASK,
    /**观察者任务列表*/
    TASK_LIST,
    /**系统状态*/
    SYSTEM_STATUS,
    /**搜索酒楼*/
    SEARCH_HOTEL,
    /**异常报告*/
    EXCEPTION_REPORT,
    /**维修记录*/
    FIX_HISTORY,
    /**我的任务*/
    MY_TASK,
    /**绑定版位*/
    BIND_BOX,
    /**指派者任务列表*/
    APPOINT_TASK_LIST,
    /**发布者任务列表*/
    PUB_TASK_LIST,
}
