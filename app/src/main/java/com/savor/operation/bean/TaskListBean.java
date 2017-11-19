package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2017/11/6.
 */

public class TaskListBean implements Serializable {
    private static final long serialVersionUID = -1;
    private  String id;
    private  String task_type;
    private  String state;
    private  String region_name;
    private  String task_emerge;
    private  String tv_nums;
    private  String hotel_name;
    private  String create_time;
    private  String publish_user_id;
    private  String hotel_address;
    private  String publish_user;
    private  String state_id;
    private  String task_emerge_id;
    private  String task_type_id;
    private  String task_type_desc;
    private  String refuse_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getTask_emerge() {
        return task_emerge;
    }

    public void setTask_emerge(String task_emerge) {
        this.task_emerge = task_emerge;
    }

    public String getTv_nums() {
        return tv_nums;
    }

    public void setTv_nums(String tv_nums) {
        this.tv_nums = tv_nums;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPublish_user_id() {
        return publish_user_id;
    }

    public void setPublish_user_id(String publish_user_id) {
        this.publish_user_id = publish_user_id;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getPublish_user() {
        return publish_user;
    }

    public void setPublish_user(String publish_user) {
        this.publish_user = publish_user;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getTask_emerge_id() {
        return task_emerge_id;
    }

    public void setTask_emerge_id(String task_emerge_id) {
        this.task_emerge_id = task_emerge_id;
    }

    public String getTask_type_id() {
        return task_type_id;
    }

    public void setTask_type_id(String task_type_id) {
        this.task_type_id = task_type_id;
    }

    public String getTask_type_desc() {
        return task_type_desc;
    }

    public void setTask_type_desc(String task_type_desc) {
        this.task_type_desc = task_type_desc;
    }

    public String getRefuse_time() {
        return refuse_time;
    }

    public void setRefuse_time(String refuse_time) {
        this.refuse_time = refuse_time;
    }

    @Override
    public String toString() {
        return "TaskListBean{" +
                "id='" + id + '\'' +
                ", task_type='" + task_type + '\'' +
                ", state='" + state + '\'' +
                ", region_name='" + region_name + '\'' +
                ", task_emerge='" + task_emerge + '\'' +
                ", tv_nums='" + tv_nums + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                ", create_time='" + create_time + '\'' +
                ", publish_user_id='" + publish_user_id + '\'' +
                ", hotel_address='" + hotel_address + '\'' +
                ", publish_user='" + publish_user + '\'' +
                ", state_id='" + state_id + '\'' +
                ", task_emerge_id='" + task_emerge_id + '\'' +
                ", task_type_id='" + task_type_id + '\'' +
                ", task_type_desc='" + task_type_desc + '\'' +
                ", refuse_time='" + refuse_time + '\'' +
                '}';
    }
}
