package com.savor.operation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/11/6.
 */

public class TaskDetail implements Serializable {
    private static final long serialVersionUID = -1;
    private String create_time;
    private String hotel_name;
    private String hotel_linkman;
    private String hotel_linkman_tel;
    private String hotel_id;
    private String hotel_address;
    private String appoint_time;
    private String appoint_exe_time;
    private String task_emerge;
    private String task_type;
    private String tv_nums;
    private String region_name;
    private String state;
    private String task_emerge_id;
    private String task_type_id;
    private String task_type_desc;
    private String state_id;
    private List<TaskDetailRepair> repair_list = new ArrayList<TaskDetailRepair>();

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_linkman() {
        return hotel_linkman;
    }

    public void setHotel_linkman(String hotel_linkman) {
        this.hotel_linkman = hotel_linkman;
    }

    public String getHotel_linkman_tel() {
        return hotel_linkman_tel;
    }

    public void setHotel_linkman_tel(String hotel_linkman_tel) {
        this.hotel_linkman_tel = hotel_linkman_tel;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getAppoint_time() {
        return appoint_time;
    }

    public void setAppoint_time(String appoint_time) {
        this.appoint_time = appoint_time;
    }

    public String getAppoint_exe_time() {
        return appoint_exe_time;
    }

    public void setAppoint_exe_time(String appoint_exe_time) {
        this.appoint_exe_time = appoint_exe_time;
    }

    public String getTask_emerge() {
        return task_emerge;
    }

    public void setTask_emerge(String task_emerge) {
        this.task_emerge = task_emerge;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }

    public String getTv_nums() {
        return tv_nums;
    }

    public void setTv_nums(String tv_nums) {
        this.tv_nums = tv_nums;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public List<TaskDetailRepair> getRepair_list() {
        return repair_list;
    }

    public void setRepair_list(List<TaskDetailRepair> repair_list) {
        this.repair_list = repair_list;
    }

    @Override
    public String toString() {
        return "TaskDetail{" +
                "create_time='" + create_time + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                ", hotel_linkman='" + hotel_linkman + '\'' +
                ", hotel_linkman_tel='" + hotel_linkman_tel + '\'' +
                ", hotel_id='" + hotel_id + '\'' +
                ", hotel_address='" + hotel_address + '\'' +
                ", appoint_time='" + appoint_time + '\'' +
                ", appoint_exe_time='" + appoint_exe_time + '\'' +
                ", task_emerge='" + task_emerge + '\'' +
                ", task_type='" + task_type + '\'' +
                ", tv_nums='" + tv_nums + '\'' +
                ", region_name='" + region_name + '\'' +
                ", state='" + state + '\'' +
                ", task_emerge_id='" + task_emerge_id + '\'' +
                ", task_type_id='" + task_type_id + '\'' +
                ", task_type_desc='" + task_type_desc + '\'' +
                ", state_id='" + state_id + '\'' +
                ", repair_list=" + repair_list +
                '}';
    }
}
