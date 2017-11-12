package com.savor.operation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bushlee on 2017/11/6.
 */

public class TaskDetailExecute implements Serializable {
    private static final long serialVersionUID = -1;
    private String username ;
    private String box_name;
    private String state;
    private String remark;
    //private List<TaskDetailRepairImg> repair_img = new ArrayList<TaskDetailRepairImg>();
    private List<String> repair_img = new ArrayList<String>();

    private String repair_time;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBox_name() {
        return box_name;
    }

    public void setBox_name(String box_name) {
        this.box_name = box_name;
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

    public List<String> getRepair_img() {
        return repair_img;
    }

    public void setRepair_img(List<String> repair_img) {
        this.repair_img = repair_img;
    }

    public String getRepair_time() {
        return repair_time;
    }

    public void setRepair_time(String repair_time) {
        this.repair_time = repair_time;
    }

    @Override
    public String toString() {
        return "TaskDetailExecute{" +
                "username='" + username + '\'' +
                ", box_name='" + box_name + '\'' +
                ", state='" + state + '\'' +
                ", remark='" + remark + '\'' +
                ", repair_img=" + repair_img +
                ", repair_time='" + repair_time + '\'' +
                '}';
    }
}
