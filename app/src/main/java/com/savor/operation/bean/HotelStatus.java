package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hezd on 2018/1/23.
 */

public class HotelStatus implements Serializable {

    /**
     * hotel_all_nums : 79
     * hotel_all_normal_nums : 75
     * hotel_all_freeze_nums : 4
     * list : [{"name":"一代","hotel_all_nums":"9","hotel_all_normal_nums":"8","hotel_all_freeze_nums":"1"},{"name":"二代","hotel_all_nums":"28","hotel_all_normal_nums":"28","hotel_all_freeze_nums":"1"},{"name":"5G","hotel_all_nums":"48","hotel_all_normal_nums":"45","hotel_all_freeze_nums":"1"},{"name":"三代","hotel_all_nums":"12","hotel_all_normal_nums":"12","hotel_all_freeze_nums":"1"}]
     */

    private String hotel_all_nums;
    private String hotel_all_normal_nums;
    private String hotel_all_freeze_nums;
    private List<ListBean> list;

    @Override
    public String toString() {
        return "HotelStatus{" +
                "hotel_all_nums='" + hotel_all_nums + '\'' +
                ", hotel_all_normal_nums='" + hotel_all_normal_nums + '\'' +
                ", hotel_all_freeze_nums='" + hotel_all_freeze_nums + '\'' +
                ", list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotelStatus that = (HotelStatus) o;

        if (hotel_all_nums != null ? !hotel_all_nums.equals(that.hotel_all_nums) : that.hotel_all_nums != null)
            return false;
        if (hotel_all_normal_nums != null ? !hotel_all_normal_nums.equals(that.hotel_all_normal_nums) : that.hotel_all_normal_nums != null)
            return false;
        if (hotel_all_freeze_nums != null ? !hotel_all_freeze_nums.equals(that.hotel_all_freeze_nums) : that.hotel_all_freeze_nums != null)
            return false;
        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        int result = hotel_all_nums != null ? hotel_all_nums.hashCode() : 0;
        result = 31 * result + (hotel_all_normal_nums != null ? hotel_all_normal_nums.hashCode() : 0);
        result = 31 * result + (hotel_all_freeze_nums != null ? hotel_all_freeze_nums.hashCode() : 0);
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
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
}
