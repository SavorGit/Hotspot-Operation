package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2017/11/6.
 */

public class BindBoxListBean implements Serializable {
    private static final long serialVersionUID = -1;
    private  String tv_brand;
    private  String box_id;
    private  String room_name;
    private  String box_name;
    private  String box_mac;

    public String getTv_brand() {
        return tv_brand;
    }

    public void setTv_brand(String tv_brand) {
        this.tv_brand = tv_brand;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getBox_name() {
        return box_name;
    }

    public void setBox_name(String box_name) {
        this.box_name = box_name;
    }

    public String getBox_mac() {
        return box_mac;
    }

    public void setBox_mac(String box_mac) {
        this.box_mac = box_mac;
    }

    @Override
    public String toString() {
        return "BindBoxListBean{" +
                "tv_brand='" + tv_brand + '\'' +
                ", box_id='" + box_id + '\'' +
                ", room_name='" + room_name + '\'' +
                ", box_name='" + box_name + '\'' +
                ", box_mac='" + box_mac + '\'' +
                '}';
    }
}
