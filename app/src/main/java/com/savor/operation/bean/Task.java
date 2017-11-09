package com.savor.operation.bean;

import java.io.Serializable;

/**
 * 任务类型，例如：安装 验收
 * Created by hezd on 2017/11/6.
 */

public class Task implements Serializable {

    /**
     * bref : 信
     * type_id : 3
     * type_name : 信息检测
     */

    private String bref;
    /**1，信息监测 8，网络改造 2，安装与验收 4，维修*/
    private int type_id;
    private String type_name;

    @Override
    public String toString() {
        return "Task{" +
                "bref='" + bref + '\'' +
                ", type_id=" + type_id +
                ", type_name='" + type_name + '\'' +
                '}';
    }

    public String getBref() {
        return bref;
    }

    public void setBref(String bref) {
        this.bref = bref;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task taskType = (Task) o;

        if (type_id != taskType.type_id) return false;
        if (bref != null ? !bref.equals(taskType.bref) : taskType.bref != null) return false;
        return type_name != null ? type_name.equals(taskType.type_name) : taskType.type_name == null;
    }

    @Override
    public int hashCode() {
        int result = bref != null ? bref.hashCode() : 0;
        result = 31 * result + type_id;
        result = 31 * result + (type_name != null ? type_name.hashCode() : 0);
        return result;
    }
}
