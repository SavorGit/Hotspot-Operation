package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/9/6.
 */

public class RepairRecordList implements Serializable {
    private static final long serialVersionUID = -1;
    private List<RepairRecordListBean> list;

    public List<RepairRecordListBean> getList() {
        return list;
    }

    public void setList(List<RepairRecordListBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RepairRecordList{" +
                "list=" + list +
                '}';
    }
}
