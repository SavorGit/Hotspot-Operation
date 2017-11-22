package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/13.
 */

public class ExecutorInfoBean implements Serializable {
    private static final long serialVersionUID = -1;
    private String box_id;
    private String box_name;
    private String repair_img;

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getBox_name() {
        return box_name;
    }

    public void setBox_name(String box_name) {
        this.box_name = box_name;
    }

    public String getRepair_img() {
        return repair_img;
    }

    public void setRepair_img(String repair_img) {
        this.repair_img = repair_img;
    }

    @Override
    public String toString() {
        return "ExecutorInfoBean{" +
                "box_id='" + box_id + '\'' +
                ", box_name='" + box_name + '\'' +
                ", repair_img='" + repair_img + '\'' +
                '}';
    }
}
