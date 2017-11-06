package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 维修任务
 * Created by hezd on 2017/11/6.
 */

public class RepairInfo implements Serializable {
    private String box_id;
    private String box_name;
    private String fault_desc;
    private String fault_img_url;
    private List<BoxInfo> boxInfoList;

    @Override
    public String toString() {
        return "RepairInfo{" +
                "box_id='" + box_id + '\'' +
                ", box_name='" + box_name + '\'' +
                ", fault_desc='" + fault_desc + '\'' +
                ", fault_img_url='" + fault_img_url + '\'' +
                ", boxInfoList=" + boxInfoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairInfo that = (RepairInfo) o;

        if (box_id != null ? !box_id.equals(that.box_id) : that.box_id != null) return false;
        if (box_name != null ? !box_name.equals(that.box_name) : that.box_name != null)
            return false;
        if (fault_desc != null ? !fault_desc.equals(that.fault_desc) : that.fault_desc != null)
            return false;
        if (fault_img_url != null ? !fault_img_url.equals(that.fault_img_url) : that.fault_img_url != null)
            return false;
        return boxInfoList != null ? boxInfoList.equals(that.boxInfoList) : that.boxInfoList == null;
    }

    @Override
    public int hashCode() {
        int result = box_id != null ? box_id.hashCode() : 0;
        result = 31 * result + (box_name != null ? box_name.hashCode() : 0);
        result = 31 * result + (fault_desc != null ? fault_desc.hashCode() : 0);
        result = 31 * result + (fault_img_url != null ? fault_img_url.hashCode() : 0);
        result = 31 * result + (boxInfoList != null ? boxInfoList.hashCode() : 0);
        return result;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
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

    public String getFault_img_url() {
        return fault_img_url;
    }

    public void setFault_img_url(String fault_img_url) {
        this.fault_img_url = fault_img_url;
    }

    public List<BoxInfo> getBoxInfoList() {
        return boxInfoList;
    }

    public void setBoxInfoList(List<BoxInfo> boxInfoList) {
        this.boxInfoList = boxInfoList;
    }
}
