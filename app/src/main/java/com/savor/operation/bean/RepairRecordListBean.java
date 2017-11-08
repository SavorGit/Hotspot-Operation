package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2017/9/6.
 */

public class RepairRecordListBean implements Serializable {
    private static final long serialVersionUID = -1;
    private String nickname;
    private String datetime;
    private String hotel_name;
    private List<RepairListBean> repair_list;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public List<RepairListBean> getRepair_list() {
        return repair_list;
    }

    public void setRepair_list(List<RepairListBean> repair_list) {
        this.repair_list = repair_list;
    }

    @Override
    public String toString() {
        return "RepairRecordListBean{" +
                "nickname='" + nickname + '\'' +
                ", datetime='" + datetime + '\'' +
                ", hotel_name='" + hotel_name + '\'' +
                ", repair_list=" + repair_list +
                '}';
    }
}
