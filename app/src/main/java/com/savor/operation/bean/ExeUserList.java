package com.savor.operation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/11/6.
 */

public class ExeUserList implements Serializable {
    private static final long serialVersionUID = -1;
    private  String  user_id;
    private  String  username;
    private List<TaskInfoListBean> task_info = new ArrayList<TaskInfoListBean>();

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

    public List<TaskInfoListBean> getTask_info() {
        return task_info;
    }

    public void setTask_info(List<TaskInfoListBean> task_info) {
        this.task_info = task_info;
    }

    @Override
    public String toString() {
        return "ExeUserList{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", task_info=" + task_info +
                '}';
    }
}
