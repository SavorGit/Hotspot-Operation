package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/9/5.
 */

public class Hotel implements Serializable {

    /**
     * id : 53
     * name : 永峰写字楼(茶室)
     */

    private String id;
    private String name;
    private String contractor;
    private String mobile;
    private String addr;
    private String area_id;

    @Override
    public String toString() {
        return "Hotel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contractor='" + contractor + '\'' +
                ", mobile='" + mobile + '\'' +
                ", addr='" + addr + '\'' +
                ", area_id='" + area_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        if (id != null ? !id.equals(hotel.id) : hotel.id != null) return false;
        if (name != null ? !name.equals(hotel.name) : hotel.name != null) return false;
        if (contractor != null ? !contractor.equals(hotel.contractor) : hotel.contractor != null)
            return false;
        if (mobile != null ? !mobile.equals(hotel.mobile) : hotel.mobile != null) return false;
        if (addr != null ? !addr.equals(hotel.addr) : hotel.addr != null) return false;
        return area_id != null ? area_id.equals(hotel.area_id) : hotel.area_id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (contractor != null ? contractor.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (addr != null ? addr.hashCode() : 0);
        result = 31 * result + (area_id != null ? area_id.hashCode() : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }
}
