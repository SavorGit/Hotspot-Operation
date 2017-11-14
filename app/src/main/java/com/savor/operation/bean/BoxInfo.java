package com.savor.operation.bean;

import java.io.Serializable;

/**
 * 版位信息
 * Created by hezd on 2017/11/6.
 */

public class BoxInfo implements Serializable {

    /**
     * box_id : 15
     * mac : FCD5D9200024
     * box_name : SONY
     */

    private String box_id;
    private String mac;
    private String box_name;
    private String fault_desc;

    @Override
    public String toString() {
        return "BoxInfo{" +
                "box_id='" + box_id + '\'' +
                ", mac='" + mac + '\'' +
                ", box_name='" + box_name + '\'' +
                ", fault_desc='" + fault_desc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxInfo boxInfo = (BoxInfo) o;

        if (box_id != null ? !box_id.equals(boxInfo.box_id) : boxInfo.box_id != null) return false;
        if (mac != null ? !mac.equals(boxInfo.mac) : boxInfo.mac != null) return false;
        if (box_name != null ? !box_name.equals(boxInfo.box_name) : boxInfo.box_name != null)
            return false;
        return fault_desc != null ? fault_desc.equals(boxInfo.fault_desc) : boxInfo.fault_desc == null;
    }

    @Override
    public int hashCode() {
        int result = box_id != null ? box_id.hashCode() : 0;
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        result = 31 * result + (box_name != null ? box_name.hashCode() : 0);
        result = 31 * result + (fault_desc != null ? fault_desc.hashCode() : 0);
        return result;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBox_name() {
        return box_name;
    }

    public void setBox_name(String box_name) {
        this.box_name = box_name;
    }

    public String getFault_desc() {
        return fault_desc;
    }

    public void setFault_desc(String fault_desc) {
        this.fault_desc = fault_desc;
    }
}
