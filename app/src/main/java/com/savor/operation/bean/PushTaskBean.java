package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2018/2/7.
 */

public class PushTaskBean implements Serializable {
    private String task_id;

    @Override
    public String toString() {
        return "PushTaskBean{" +
                "task_id='" + task_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PushTaskBean that = (PushTaskBean) o;

        return task_id != null ? task_id.equals(that.task_id) : that.task_id == null;
    }

    @Override
    public int hashCode() {
        return task_id != null ? task_id.hashCode() : 0;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }
}
