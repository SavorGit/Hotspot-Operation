package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2017/11/6.
 */

public class TaskDetailRepair implements Serializable {
    private static final long serialVersionUID = -1;
    private String box_name ;
    private String box_id;
    private String fault_desc;
    private String fault_img_url;

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

    @Override
    public String toString() {
        return "TaskDetailRepair{" +
                "box_name='" + box_name + '\'' +
                ", box_id='" + box_id + '\'' +
                ", fault_desc='" + fault_desc + '\'' +
                ", fault_img_url='" + fault_img_url + '\'' +
                '}';
    }
}
