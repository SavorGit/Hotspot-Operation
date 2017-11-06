package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/11/6.
 */

public class City implements Serializable {
    private String id;
    private String region_name;
    private boolean isSelect;

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", region_name='" + region_name + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (isSelect != city.isSelect) return false;
        if (id != null ? !id.equals(city.id) : city.id != null) return false;
        return region_name != null ? region_name.equals(city.region_name) : city.region_name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (region_name != null ? region_name.hashCode() : 0);
        result = 31 * result + (isSelect ? 1 : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
