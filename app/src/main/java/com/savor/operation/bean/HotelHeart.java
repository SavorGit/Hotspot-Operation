package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2018/2/6.
 */

public class HotelHeart implements Serializable {

    private static final long serialVersionUID = -1;
    private int hotel_all_nums;
    private int hotel_all_normal_nums;
    private int hotel_all_freeze_nums;
    private int box_normal_num;
    private int box_not_normal_num;
    private int black_box_num;
    private String remark;


    public int getHotel_all_nums() {
        return hotel_all_nums;
    }

    public void setHotel_all_nums(int hotel_all_nums) {
        this.hotel_all_nums = hotel_all_nums;
    }

    public int getHotel_all_normal_nums() {
        return hotel_all_normal_nums;
    }

    public void setHotel_all_normal_nums(int hotel_all_normal_nums) {
        this.hotel_all_normal_nums = hotel_all_normal_nums;
    }

    public int getHotel_all_freeze_nums() {
        return hotel_all_freeze_nums;
    }

    public void setHotel_all_freeze_nums(int hotel_all_freeze_nums) {
        this.hotel_all_freeze_nums = hotel_all_freeze_nums;
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

    public int getBlack_box_num() {
        return black_box_num;
    }

    public void setBlack_box_num(int black_box_num) {
        this.black_box_num = black_box_num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "HotelHeart{" +
                "hotel_all_nums=" + hotel_all_nums +
                ", hotel_all_normal_nums=" + hotel_all_normal_nums +
                ", hotel_all_freeze_nums=" + hotel_all_freeze_nums +
                ", box_normal_num=" + box_normal_num +
                ", box_not_normal_num=" + box_not_normal_num +
                ", black_box_num=" + black_box_num +
                ", remark='" + remark + '\'' +
                '}';
    }
}
