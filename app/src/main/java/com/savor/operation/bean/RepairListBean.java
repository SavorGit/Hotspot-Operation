package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2017/9/6.
 */

public class RepairListBean implements Serializable {
    private static final long serialVersionUID = -1;

    private String create_time;
    private String state;
    private String repair_error;
    private String remark;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepair_error() {
        return repair_error;
    }

    public void setRepair_error(String repair_error) {
        this.repair_error = repair_error;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "RepairListBean{" +
                "create_time='" + create_time + '\'' +
                ", state='" + state + '\'' +
                ", repair_error='" + repair_error + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
