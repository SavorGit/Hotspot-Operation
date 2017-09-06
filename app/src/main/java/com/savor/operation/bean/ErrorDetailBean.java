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

    @Override
    public String toString() {
        return "ErrorDetailBean{" +
                "detail_id='" + detail_id + '\'' +
                ", hotel_id='" + hotel_id + '\'' +
                ", hotel_info='" + hotel_info + '\'' +
                ", small_palt_info='" + small_palt_info + '\'' +
                ", box_info='" + box_info + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorDetailBean)) return false;

        ErrorDetailBean that = (ErrorDetailBean) o;

        if (detail_id != null ? !detail_id.equals(that.detail_id) : that.detail_id != null)
            return false;
        if (hotel_id != null ? !hotel_id.equals(that.hotel_id) : that.hotel_id != null)
            return false;
        if (hotel_info != null ? !hotel_info.equals(that.hotel_info) : that.hotel_info != null)
            return false;
        if (small_palt_info != null ? !small_palt_info.equals(that.small_palt_info) : that.small_palt_info != null)
            return false;
        return box_info != null ? box_info.equals(that.box_info) : that.box_info == null;

    }

    @Override
    public int hashCode() {
        int result = detail_id != null ? detail_id.hashCode() : 0;
        result = 31 * result + (hotel_id != null ? hotel_id.hashCode() : 0);
        result = 31 * result + (hotel_info != null ? hotel_info.hashCode() : 0);
        result = 31 * result + (small_palt_info != null ? small_palt_info.hashCode() : 0);
        result = 31 * result + (box_info != null ? box_info.hashCode() : 0);
        return result;
    }
}
