package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2017/11/17.
 */

public class ExecuteRepair implements Serializable {
    private static final long serialVersionUID = -1;
    private List<TaskDetailRepairImg> repair_img;
    private String username;
    private String repair_time;


    public List<TaskDetailRepairImg> getRepair_img() {
        return repair_img;
    }

    public void setRepair_img(List<TaskDetailRepairImg> repair_img) {
        this.repair_img = repair_img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepair_time() {
        return repair_time;
    }

    public void setRepair_time(String repair_time) {
        this.repair_time = repair_time;
    }

    @Override
    public String toString() {
        return "ExecuteRepair{" +
                "repair_img=" + repair_img +
                ", username='" + username + '\'' +
                ", repair_time='" + repair_time + '\'' +
                '}';
    }
}
