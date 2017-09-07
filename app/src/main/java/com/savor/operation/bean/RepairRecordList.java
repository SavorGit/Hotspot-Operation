package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/9/6.
 */

public class RepairRecordList implements Serializable {
    private static final long serialVersionUID = -1;
    private List<RepairRecordListBean> repair_info;
    private int isNextPage;


    public List<RepairRecordListBean> getList() {
        return repair_info;
    }

    public void setList(List<RepairRecordListBean> list) {
        this.repair_info = list;
    }

    public List<RepairRecordListBean> getRepair_info() {
        return repair_info;
    }

    public void setRepair_info(List<RepairRecordListBean> repair_info) {
        this.repair_info = repair_info;
    }

    public int getIsNextPage() {
        return isNextPage;
    }

    public void setIsNextPage(int isNextPage) {
        this.isNextPage = isNextPage;
    }

    @Override
    public String toString() {
        return "RepairRecordList{" +
                "repair_info=" + repair_info +
                ", isNextPage=" + isNextPage +
                '}';
    }
}
