package com.savor.operation.enums;

import java.io.Serializable;

/**
 * 搜索酒楼操作类型，比如发布任务搜索酒楼还是首页搜索酒楼
 * Created by hezd on 2017/11/3.
 */

public enum SearchHotelOpType implements Serializable {
    /**普通类型搜索酒楼*/
    NORMAL,
    /**发布任务时搜索酒楼*/
    PUBLIS_TASK,
}
