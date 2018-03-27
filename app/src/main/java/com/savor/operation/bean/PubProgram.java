package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2018/1/23.
 */

public class PubProgram implements Serializable {

    /**
     * name : A类-明星6次
     * type : 1
     * location_id : 1
     */

    private String name;
    private String type;
    private String sort_num;
    private String location_id;
    private int state;

    @Override
    public String toString() {
        return "PubProgram{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", sort_num='" + sort_num + '\'' +
                ", location_id='" + location_id + '\'' +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PubProgram that = (PubProgram) o;

        if (state != that.state) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (sort_num != null ? !sort_num.equals(that.sort_num) : that.sort_num != null)
            return false;
        return location_id != null ? location_id.equals(that.location_id) : that.location_id == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (sort_num != null ? sort_num.hashCode() : 0);
        result = 31 * result + (location_id != null ? location_id.hashCode() : 0);
        result = 31 * result + state;
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort_num() {
        return sort_num;
    }

    public void setSort_num(String sort_num) {
        this.sort_num = sort_num;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
