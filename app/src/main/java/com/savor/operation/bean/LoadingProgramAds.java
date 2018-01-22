package com.savor.operation.bean;

import java.io.Serializable;

/**
 * 节目
 * Created by hezd on 2018/1/22.
 */

public class LoadingProgramAds implements Serializable {

    /**
     * ads_name : 广告位1
     * location_id : 1
     * sort_num : 2
     * type : 1
     * flag : 0
     */

    private String ads_name;
    private String location_id;
    private String sort_num;
    private String type;
    private int flag;

    @Override
    public String toString() {
        return "LoadingProgramAds{" +
                "ads_name='" + ads_name + '\'' +
                ", location_id='" + location_id + '\'' +
                ", sort_num='" + sort_num + '\'' +
                ", type='" + type + '\'' +
                ", flag=" + flag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadingProgramAds that = (LoadingProgramAds) o;

        if (flag != that.flag) return false;
        if (ads_name != null ? !ads_name.equals(that.ads_name) : that.ads_name != null)
            return false;
        if (location_id != null ? !location_id.equals(that.location_id) : that.location_id != null)
            return false;
        if (sort_num != null ? !sort_num.equals(that.sort_num) : that.sort_num != null)
            return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = ads_name != null ? ads_name.hashCode() : 0;
        result = 31 * result + (location_id != null ? location_id.hashCode() : 0);
        result = 31 * result + (sort_num != null ? sort_num.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + flag;
        return result;
    }

    public String getAds_name() {
        return ads_name;
    }

    public void setAds_name(String ads_name) {
        this.ads_name = ads_name;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getSort_num() {
        return sort_num;
    }

    public void setSort_num(String sort_num) {
        this.sort_num = sort_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
