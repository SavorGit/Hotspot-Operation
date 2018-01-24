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
    private int type;
    private String location_id;

    @Override
    public String toString() {
        return "PubProgram{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", location_id='" + location_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PubProgram that = (PubProgram) o;

        if (type != that.type) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return location_id != null ? location_id.equals(that.location_id) : that.location_id == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + type;
        result = 31 * result + (location_id != null ? location_id.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }
}
