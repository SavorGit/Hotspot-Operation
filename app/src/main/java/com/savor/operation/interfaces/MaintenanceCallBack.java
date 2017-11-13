package com.savor.operation.interfaces;

import java.util.List;

/**
 * 提交任务
 *
 * Created by bushlee on 2017/2/7.
 */

public interface MaintenanceCallBack {
    /**
     * 提交维修任务
     */
    void toMaintenance(String box_id,String remark,String state, List<String> urls);
    /**
     * 提交安装任务
     */
    void toInstallation();
    /**
     * 提交改造任务
     */
    void toTransform();
    /**
     * 提交检测任务
     */
    void toDetect();

   ;
}
