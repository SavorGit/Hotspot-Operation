package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2017/9/5.
 */

public class UserBean implements Serializable {
    private static final long serialVersionUID = -1;
    private String userid;
    private String username;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
