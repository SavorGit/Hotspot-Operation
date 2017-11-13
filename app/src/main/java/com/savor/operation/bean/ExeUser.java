package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/11/13.
 */

public class ExeUser implements Serializable {
    private static final long serialVersionUID = -1;
    private String user_id;
    private String username;
    private List<TaskInfoBean> task_info;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<TaskInfoBean> getTask_info() {
        return task_info;
    }

    public void setTask_info(List<TaskInfoBean> task_info) {
        this.task_info = task_info;
    }

    @Override
    public String toString() {
        return "ExeUser{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", task_info=" + task_info +
                '}';
    }
}
