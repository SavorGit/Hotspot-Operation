package com.savor.operation.bean;

import java.io.Serializable;

/**
 * 节目
 * Created by hezd on 2018/1/22.
 */

public class Program implements Serializable {

    /**
     * ads_name : 广告位1
     * location_id : 1
     * sort_num : 2
     * type : 1
     * flag : 0
     */

    private String name;
    private String location_id;
    private String sort_num;
    private String type;
    private int flag;

    @Override
    public String toString() {
        return "Program{" +
                "name='" + name + '\'' +
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

        Program program = (Program) o;

        if (flag != program.flag) return false;
        if (name != null ? !name.equals(program.name) : program.name != null) return false;
        if (location_id != null ? !location_id.equals(program.location_id) : program.location_id != null)
            return false;
        if (sort_num != null ? !sort_num.equals(program.sort_num) : program.sort_num != null)
            return false;
        return type != null ? type.equals(program.type) : program.type == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (location_id != null ? location_id.hashCode() : 0);
        result = 31 * result + (sort_num != null ? sort_num.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + flag;
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
