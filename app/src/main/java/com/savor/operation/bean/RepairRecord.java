package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2017/9/7.
 */

public class RepairRecord implements Serializable {
    private static final long serialVersionUID = -1;
    private RepairRecordList list;

    public RepairRecordList getList() {
        return list;
    }

    public void setList(RepairRecordList list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "RepairRecord{" +
                "list=" + list +
                '}';
    }
}
