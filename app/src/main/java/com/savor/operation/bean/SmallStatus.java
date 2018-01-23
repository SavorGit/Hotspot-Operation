package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2018/1/23.
 */

public class SmallStatus implements Serializable {

    /**
     * all_nums : 64
     * normal_nums : 61
     * freeze_nums : 3
     */

    private String all_nums;
    private String normal_nums;
    private String freeze_nums;

    @Override
    public String toString() {
        return "SmallStatus{" +
                "all_nums='" + all_nums + '\'' +
                ", normal_nums='" + normal_nums + '\'' +
                ", freeze_nums='" + freeze_nums + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SmallStatus that = (SmallStatus) o;

        if (all_nums != null ? !all_nums.equals(that.all_nums) : that.all_nums != null)
            return false;
        if (normal_nums != null ? !normal_nums.equals(that.normal_nums) : that.normal_nums != null)
            return false;
        return freeze_nums != null ? freeze_nums.equals(that.freeze_nums) : that.freeze_nums == null;
    }

    @Override
    public int hashCode() {
        int result = all_nums != null ? all_nums.hashCode() : 0;
        result = 31 * result + (normal_nums != null ? normal_nums.hashCode() : 0);
        result = 31 * result + (freeze_nums != null ? freeze_nums.hashCode() : 0);
        return result;
    }

    public String getAll_nums() {
        return all_nums;
    }

    public void setAll_nums(String all_nums) {
        this.all_nums = all_nums;
    }

    public String getNormal_nums() {
        return normal_nums;
    }

    public void setNormal_nums(String normal_nums) {
        this.normal_nums = normal_nums;
    }

    public String getFreeze_nums() {
        return freeze_nums;
    }

    public void setFreeze_nums(String freeze_nums) {
        this.freeze_nums = freeze_nums;
    }
}
