package com.savor.operation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/11/6.
 */

public class TaskDetailRepair implements Serializable {
    private static final long serialVersionUID = -1;
    private String  repair_id;
    private String box_name ;
    private String box_id;
    private String fault_desc;
    private String fault_img_url;
    private String username;
    private String state;
    private String remark;
    private String repair_time;
    private List<TaskDetailRepairImg> repair_img = new ArrayList<TaskDetailRepairImg>();

    public String getRepair_id() {
        return repair_id;
    }

    public void setRepair_id(String repair_id) {
        this.repair_id = repair_id;
    }

    public String getBox_name() {
        return box_name;
    }

    public void setBox_name(String box_name) {
        this.box_name = box_name;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getFault_desc() {
        return fault_desc;
    }

    public void setFault_desc(String fault_desc) {
        this.fault_desc = fault_desc;
    }

    public String getFault_img_url() {
        return fault_img_url;
    }

    public void setFault_img_url(String fault_img_url) {
        this.fault_img_url = fault_img_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRepair_time() {
        return repair_time;
    }

    public void setRepair_time(String repair_time) {
        this.repair_time = repair_time;
    }

    public List<TaskDetailRepairImg> getRepair_img() {
        return repair_img;
    }

    public void setRepair_img(List<TaskDetailRepairImg> repair_img) {
        this.repair_img = repair_img;
    }

    @Override
    public String toString() {
        return "TaskDetailRepair{" +
                "repair_id='" + repair_id + '\'' +
                ", box_name='" + box_name + '\'' +
                ", box_id='" + box_id + '\'' +
                ", fault_desc='" + fault_desc + '\'' +
                ", fault_img_url='" + fault_img_url + '\'' +
                ", username='" + username + '\'' +
                ", state='" + state + '\'' +
                ", remark='" + remark + '\'' +
                ", repair_time='" + repair_time + '\'' +
                ", repair_img=" + repair_img +
                '}';
    }
}
