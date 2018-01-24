package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2018/1/23.
 */

public class TotalStatus implements Serializable {

    /**
     * update_time : 2018-01-23 10:38
     * hotel_online : 3
     * hotel_not_onlie : 0
     * hotel_10_72_not_onlie : (离线小于72小时:0 离线大于72小时:0)
     * small_plat_normal_num : 3
     * small_plat_not_normal_num : 34
     * box_normal_num : 0
     * box_not_normal_num : 502
     * black_box_num : 3
     * remark : 在线指10分钟以内;离线指大于十分钟;异常指大于72小时
     */

    private String update_time;
    private int hotel_online;
    private int hotel_not_onlie;
    private String hotel_10_72_not_onlie;
    private int small_plat_normal_num;
    private int small_plat_not_normal_num;
    private int box_normal_num;
    private int box_not_normal_num;
    private String black_box_num;
    private String remark;

    @Override
    public String toString() {
        return "TotalStatus{" +
                "update_time='" + update_time + '\'' +
                ", hotel_online=" + hotel_online +
                ", hotel_not_onlie=" + hotel_not_onlie +
                ", hotel_10_72_not_onlie='" + hotel_10_72_not_onlie + '\'' +
                ", small_plat_normal_num=" + small_plat_normal_num +
                ", small_plat_not_normal_num=" + small_plat_not_normal_num +
                ", box_normal_num=" + box_normal_num +
                ", box_not_normal_num=" + box_not_normal_num +
                ", black_box_num='" + black_box_num + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TotalStatus that = (TotalStatus) o;

        if (hotel_online != that.hotel_online) return false;
        if (hotel_not_onlie != that.hotel_not_onlie) return false;
        if (small_plat_normal_num != that.small_plat_normal_num) return false;
        if (small_plat_not_normal_num != that.small_plat_not_normal_num) return false;
        if (box_normal_num != that.box_normal_num) return false;
        if (box_not_normal_num != that.box_not_normal_num) return false;
        if (update_time != null ? !update_time.equals(that.update_time) : that.update_time != null)
            return false;
        if (hotel_10_72_not_onlie != null ? !hotel_10_72_not_onlie.equals(that.hotel_10_72_not_onlie) : that.hotel_10_72_not_onlie != null)
            return false;
        if (black_box_num != null ? !black_box_num.equals(that.black_box_num) : that.black_box_num != null)
            return false;
        return remark != null ? remark.equals(that.remark) : that.remark == null;
    }

    @Override
    public int hashCode() {
        int result = update_time != null ? update_time.hashCode() : 0;
        result = 31 * result + hotel_online;
        result = 31 * result + hotel_not_onlie;
        result = 31 * result + (hotel_10_72_not_onlie != null ? hotel_10_72_not_onlie.hashCode() : 0);
        result = 31 * result + small_plat_normal_num;
        result = 31 * result + small_plat_not_normal_num;
        result = 31 * result + box_normal_num;
        result = 31 * result + box_not_normal_num;
        result = 31 * result + (black_box_num != null ? black_box_num.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getHotel_online() {
        return hotel_online;
    }

    public void setHotel_online(int hotel_online) {
        this.hotel_online = hotel_online;
    }

    public int getHotel_not_onlie() {
        return hotel_not_onlie;
    }

    public void setHotel_not_onlie(int hotel_not_onlie) {
        this.hotel_not_onlie = hotel_not_onlie;
    }

    public String getHotel_10_72_not_onlie() {
        return hotel_10_72_not_onlie;
    }

    public void setHotel_10_72_not_onlie(String hotel_10_72_not_onlie) {
        this.hotel_10_72_not_onlie = hotel_10_72_not_onlie;
    }

    public int getSmall_plat_normal_num() {
        return small_plat_normal_num;
    }

    public void setSmall_plat_normal_num(int small_plat_normal_num) {
        this.small_plat_normal_num = small_plat_normal_num;
    }

    public int getSmall_plat_not_normal_num() {
        return small_plat_not_normal_num;
    }

    public void setSmall_plat_not_normal_num(int small_plat_not_normal_num) {
        this.small_plat_not_normal_num = small_plat_not_normal_num;
    }

    public int getBox_normal_num() {
        return box_normal_num;
    }

    public void setBox_normal_num(int box_normal_num) {
        this.box_normal_num = box_normal_num;
    }

    public int getBox_not_normal_num() {
        return box_not_normal_num;
    }

    public void setBox_not_normal_num(int box_not_normal_num) {
        this.box_not_normal_num = box_not_normal_num;
    }

    public String getBlack_box_num() {
        return black_box_num;
    }

    public void setBlack_box_num(String black_box_num) {
        this.black_box_num = black_box_num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
