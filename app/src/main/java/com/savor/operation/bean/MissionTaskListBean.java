package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2017/11/6.
 */

public class MissionTaskListBean extends TaskListBean implements Serializable {
    private static final long serialVersionUID = -1;
    private  String appoint_time;
    private  String appoint_user_id;
    private  String appoint_user;
    private  String appoint_exe_time;
    private  String exe_user_id;
    private  String exeuser;
    private  String complete_time;

    public String getAppoint_time() {
        return appoint_time;
    }

    public void setAppoint_time(String appoint_time) {
        this.appoint_time = appoint_time;
    }

    public String getAppoint_user_id() {
        return appoint_user_id;
    }

    public void setAppoint_user_id(String appoint_user_id) {
        this.appoint_user_id = appoint_user_id;
    }

    public String getAppoint_user() {
        return appoint_user;
    }

    public void setAppoint_user(String appoint_user) {
        this.appoint_user = appoint_user;
    }

    public String getAppoint_exe_time() {
        return appoint_exe_time;
    }

    public void setAppoint_exe_time(String appoint_exe_time) {
        this.appoint_exe_time = appoint_exe_time;
    }

    public String getExe_user_id() {
        return exe_user_id;
    }

    public void setExe_user_id(String exe_user_id) {
        this.exe_user_id = exe_user_id;
    }

    public String getExeuser() {
        return exeuser;
    }

    public void setExeuser(String exeuser) {
        this.exeuser = exeuser;
    }

    public String getComplete_time() {
        return complete_time;
    }

    public void setComplete_time(String complete_time) {
        this.complete_time = complete_time;
    }

    @Override
    public String toString() {
        return "ExeTaskListBean{" +
                "appoint_time='" + appoint_time + '\'' +
                ", appoint_user_id='" + appoint_user_id + '\'' +
                ", appoint_user='" + appoint_user + '\'' +
                ", appoint_exe_time='" + appoint_exe_time + '\'' +
                ", exe_user_id='" + exe_user_id + '\'' +
                ", exeuser='" + exeuser + '\'' +
                ", complete_time='" + complete_time + '\'' +
                '}';
    }
}
