package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hezd on 2017/11/6.
 */

public class SkillList implements Serializable {
    private RoleInfo role_info;
    private List<City> manage_city;

    @Override
    public String toString() {
        return "SkillList{" +
                "role_info=" + role_info +
                ", manage_city=" + manage_city +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkillList skillList = (SkillList) o;

        if (role_info != null ? !role_info.equals(skillList.role_info) : skillList.role_info != null)
            return false;
        return manage_city != null ? manage_city.equals(skillList.manage_city) : skillList.manage_city == null;
    }

    @Override
    public int hashCode() {
        int result = role_info != null ? role_info.hashCode() : 0;
        result = 31 * result + (manage_city != null ? manage_city.hashCode() : 0);
        return result;
    }

    public RoleInfo getRole_info() {
        return role_info;
    }

    public void setRole_info(RoleInfo role_info) {
        this.role_info = role_info;
    }

    public List<City> getManage_city() {
        return manage_city;
    }

    public void setManage_city(List<City> manage_city) {
        this.manage_city = manage_city;
    }
}
