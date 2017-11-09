package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/11/6.
 */

public class BindBoxList implements Serializable {
    private List<BindBoxListBean> list;
    private String hotel_name;

    @Override
    public String toString() {
        return "BindBoxList{" +
                "list=" + list +
                ", hotel_name='" + hotel_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BindBoxList that = (BindBoxList) o;

        if (list != null ? !list.equals(that.list) : that.list != null) return false;
        return hotel_name != null ? hotel_name.equals(that.hotel_name) : that.hotel_name == null;
    }

    @Override
    public int hashCode() {
        int result = list != null ? list.hashCode() : 0;
        result = 31 * result + (hotel_name != null ? hotel_name.hashCode() : 0);
        return result;
    }

    public List<BindBoxListBean> getList() {
        return list;
    }

    public void setList(List<BindBoxListBean> list) {
        this.list = list;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }
}
