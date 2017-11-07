package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/11/7.
 */

public class BaseBoxInfo implements Serializable {
    private String box_id;
    private String fault_desc;
    private String fault_img_url;

    @Override
    public String toString() {
        return "BaseBoxInfo{" +
                "box_id='" + box_id + '\'' +
                ", fault_desc='" + fault_desc + '\'' +
                ", fault_img_url='" + fault_img_url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseBoxInfo that = (BaseBoxInfo) o;

        if (box_id != null ? !box_id.equals(that.box_id) : that.box_id != null) return false;
        if (fault_desc != null ? !fault_desc.equals(that.fault_desc) : that.fault_desc != null)
            return false;
        return fault_img_url != null ? fault_img_url.equals(that.fault_img_url) : that.fault_img_url == null;
    }

    @Override
    public int hashCode() {
        int result = box_id != null ? box_id.hashCode() : 0;
        result = 31 * result + (fault_desc != null ? fault_desc.hashCode() : 0);
        result = 31 * result + (fault_img_url != null ? fault_img_url.hashCode() : 0);
        return result;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
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
}
