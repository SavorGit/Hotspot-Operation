package com.savor.operation.bean;

import java.io.Serializable;

/**
 * 系统状态条目
 * Created by hezd on 2018/1/23.
 */

public class SystemStatusItem implements Serializable {

    /**
     * name : 一代
     * hotel_all_nums : 9
     * hotel_all_normal_nums : 8
     * hotel_all_freeze_nums : 1
     */

    private String name;
    private String hotel_all_nums;
    private String hotel_all_normal_nums;
    private String hotel_all_freeze_nums;

    @Override
    public String toString() {
        return "SystemStatusItem{" +
                "name='" + name + '\'' +
                ", hotel_all_nums='" + hotel_all_nums + '\'' +
                ", hotel_all_normal_nums='" + hotel_all_normal_nums + '\'' +
                ", hotel_all_freeze_nums='" + hotel_all_freeze_nums + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemStatusItem that = (SystemStatusItem) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (hotel_all_nums != null ? !hotel_all_nums.equals(that.hotel_all_nums) : that.hotel_all_nums != null)
            return false;
        if (hotel_all_normal_nums != null ? !hotel_all_normal_nums.equals(that.hotel_all_normal_nums) : that.hotel_all_normal_nums != null)
            return false;
        return hotel_all_freeze_nums != null ? hotel_all_freeze_nums.equals(that.hotel_all_freeze_nums) : that.hotel_all_freeze_nums == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (hotel_all_nums != null ? hotel_all_nums.hashCode() : 0);
        result = 31 * result + (hotel_all_normal_nums != null ? hotel_all_normal_nums.hashCode() : 0);
        result = 31 * result + (hotel_all_freeze_nums != null ? hotel_all_freeze_nums.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotel_all_nums() {
        return hotel_all_nums;
    }

    public void setHotel_all_nums(String hotel_all_nums) {
        this.hotel_all_nums = hotel_all_nums;
    }

    public String getHotel_all_normal_nums() {
        return hotel_all_normal_nums;
    }

    public void setHotel_all_normal_nums(String hotel_all_normal_nums) {
        this.hotel_all_normal_nums = hotel_all_normal_nums;
    }

    public String getHotel_all_freeze_nums() {
        return hotel_all_freeze_nums;
    }

    public void setHotel_all_freeze_nums(String hotel_all_freeze_nums) {
        this.hotel_all_freeze_nums = hotel_all_freeze_nums;
    }
}
