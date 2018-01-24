package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2018/1/23.
 */

public class SystemStatusCity implements Serializable {

    /**
     * id : 0
     * region_name : 全国
     */

    private String id;
    private String region_name;

    @Override
    public String toString() {
        return "SystemStatusCity{" +
                "id='" + id + '\'' +
                ", region_name='" + region_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemStatusCity that = (SystemStatusCity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return region_name != null ? region_name.equals(that.region_name) : that.region_name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (region_name != null ? region_name.hashCode() : 0);
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
}
