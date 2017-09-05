package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hezd on 2017/9/5.
 */

public class IndexInfo implements Serializable {

    /**
     * list : ["当前在线酒楼(10分钟内在线):3","当前离线酒楼(离线超过10分钟)1 (其中0个酒楼离线15小时以内，1个酒楼离线大于15小时)","酒楼总数:55","正常酒楼:1","异常酒楼:54","异常小平台:52","异常机顶盒:603","更新时间:2017-09-04 22:08:53"]
     * remark : 注:异常为心跳失联超过15小时以上
     */

    private String remark;
    private List<String> list;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexInfo indexInfo = (IndexInfo) o;

        if (remark != null ? !remark.equals(indexInfo.remark) : indexInfo.remark != null)
            return false;
        return list != null ? list.equals(indexInfo.list) : indexInfo.list == null;

    }

    @Override
    public int hashCode() {
        int result = remark != null ? remark.hashCode() : 0;
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IndexInfo{" +
                "remark='" + remark + '\'' +
                ", list=" + list +
                '}';
    }
}
