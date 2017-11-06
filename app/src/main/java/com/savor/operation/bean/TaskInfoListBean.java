package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/6.
 */

public class TaskInfoListBean implements Serializable {
    private static final long serialVersionUID = -1;
    private String task_type;
    private String hotel_name;
    private String task_type_desc;

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getTask_type_desc() {
        return task_type_desc;
    }

    public void setTask_type_desc(String task_type_desc) {
        this.task_type_desc = task_type_desc;
    }

    @Override
    public String toString() {
        return "TaskInfoListBean{" +
                "task_type='" + task_type + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                ", task_type_desc='" + task_type_desc + '\'' +
                '}';
    }
}
