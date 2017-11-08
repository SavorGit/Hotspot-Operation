package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 维修任务
 * Created by hezd on 2017/11/6.
 */

public class RepairInfo extends BaseBoxInfo {
    private String box_name;
    private List<BoxInfo> boxInfoList;

    @Override
    public String toString() {
        return "RepairInfo{" +
                "box_name='" + box_name + '\'' +
                ", boxInfoList=" + boxInfoList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RepairInfo that = (RepairInfo) o;

        if (box_name != null ? !box_name.equals(that.box_name) : that.box_name != null)
            return false;
        return boxInfoList != null ? boxInfoList.equals(that.boxInfoList) : that.boxInfoList == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (box_name != null ? box_name.hashCode() : 0);
        result = 31 * result + (boxInfoList != null ? boxInfoList.hashCode() : 0);
        return result;
    }

    public String getBox_name() {
        return box_name;
    }

    public void setBox_name(String box_name) {
        this.box_name = box_name;
    }

    public List<BoxInfo> getBoxInfoList() {
        return boxInfoList;
    }

    public void setBoxInfoList(List<BoxInfo> boxInfoList) {
        this.boxInfoList = boxInfoList;
    }
}
