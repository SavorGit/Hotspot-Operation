package com.savor.operation.bean;

import java.io.Serializable;

/**
 * 当前任务数量
 * Created by hezd on 2017/11/7.
 */

public class TaskNum implements Serializable {
    private String nums;

    @Override
    public String toString() {
        return "TaskNum{" +
                "nums='" + nums + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskNum taskNum = (TaskNum) o;

        return nums != null ? nums.equals(taskNum.nums) : taskNum.nums == null;
    }

    @Override
    public int hashCode() {
        return nums != null ? nums.hashCode() : 0;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }
}
