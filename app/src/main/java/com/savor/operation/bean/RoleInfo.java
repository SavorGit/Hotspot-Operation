package com.savor.operation.bean;

import java.io.Serializable;

/**
 * 角色信息
 * Created by hezd on 2017/11/6.
 */

public class RoleInfo implements Serializable {
    private String id;
    private String name;

    @Override
    public String toString() {
        return "RoleInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleInfo roleInfo = (RoleInfo) o;

        if (id != null ? !id.equals(roleInfo.id) : roleInfo.id != null) return false;
        return name != null ? name.equals(roleInfo.name) : roleInfo.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
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
}
