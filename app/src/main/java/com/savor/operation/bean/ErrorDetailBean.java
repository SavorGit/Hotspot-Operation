package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2017/9/6.
 */

public class ErrorDetailBean implements Serializable {
    private static final long serialVersionUID = -1;

    private String detail_id;
    private String hotel_id;
    private String hotel_info;
    private String small_palt_info;
    private String box_info;
    private String hotel_name;

    public String getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(String detail_id) {
        this.detail_id = detail_id;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_info() {
        return hotel_info;
    }

    public void setHotel_info(String hotel_info) {
        this.hotel_info = hotel_info;
    }

    public String getSmall_palt_info() {
        return small_palt_info;
    }

    public void setSmall_palt_info(String small_palt_info) {
        this.small_palt_info = small_palt_info;
    }

    public String getBox_info() {
        return box_info;
    }

    public void setBox_info(String box_info) {
        this.box_info = box_info;
    }


    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    @Override
    public String toString() {
        return "ErrorDetailBean{" +
                "detail_id='" + detail_id + '\'' +
                ", hotel_id='" + hotel_id + '\'' +
                ", hotel_info='" + hotel_info + '\'' +
                ", small_palt_info='" + small_palt_info + '\'' +
                ", box_info='" + box_info + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                '}';
    }
}
