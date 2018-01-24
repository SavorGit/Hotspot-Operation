package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hezd on 2018/1/23.
 */

public class OneKeyTestResponse implements Serializable {

    /**
     * small_device_name : 小平台
     * small_device_state : 离线
     * box_device_name : xiemeng_5g
     * box_device_state : 离线 无法投屏点播
     * remark : ["离线原因(进攻参考)","1、机顶盒没开机","2、局域网拥堵","3、外网网络断开等等"]
     */

    private String small_device_name;
    private String small_device_state;
    private String box_device_name;
    private String box_device_state;
    private List<String> remark;

    @Override
    public String toString() {
        return "OneKeyTestResponse{" +
                "small_device_name='" + small_device_name + '\'' +
                ", small_device_state='" + small_device_state + '\'' +
                ", box_device_name='" + box_device_name + '\'' +
                ", box_device_state='" + box_device_state + '\'' +
                ", remark=" + remark +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OneKeyTestResponse that = (OneKeyTestResponse) o;

        if (small_device_name != null ? !small_device_name.equals(that.small_device_name) : that.small_device_name != null)
            return false;
        if (small_device_state != null ? !small_device_state.equals(that.small_device_state) : that.small_device_state != null)
            return false;
        if (box_device_name != null ? !box_device_name.equals(that.box_device_name) : that.box_device_name != null)
            return false;
        if (box_device_state != null ? !box_device_state.equals(that.box_device_state) : that.box_device_state != null)
            return false;
        return remark != null ? remark.equals(that.remark) : that.remark == null;
    }

    @Override
    public int hashCode() {
        int result = small_device_name != null ? small_device_name.hashCode() : 0;
        result = 31 * result + (small_device_state != null ? small_device_state.hashCode() : 0);
        result = 31 * result + (box_device_name != null ? box_device_name.hashCode() : 0);
        result = 31 * result + (box_device_state != null ? box_device_state.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }

    public String getSmall_device_name() {
        return small_device_name;
    }

    public void setSmall_device_name(String small_device_name) {
        this.small_device_name = small_device_name;
    }

    public String getSmall_device_state() {
        return small_device_state;
    }

    public void setSmall_device_state(String small_device_state) {
        this.small_device_state = small_device_state;
    }

    public String getBox_device_name() {
        return box_device_name;
    }

    public void setBox_device_name(String box_device_name) {
        this.box_device_name = box_device_name;
    }

    public String getBox_device_state() {
        return box_device_state;
    }

    public void setBox_device_state(String box_device_state) {
        this.box_device_state = box_device_state;
    }

    public List<String> getRemark() {
        return remark;
    }

    public void setRemark(List<String> remark) {
        this.remark = remark;
    }
}
