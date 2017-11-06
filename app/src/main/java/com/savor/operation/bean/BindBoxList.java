package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/11/6.
 */

public class BindBoxList implements Serializable {
    private static final long serialVersionUID = -1;
    private List<BindBoxListBean> list;

    public List<BindBoxListBean> getList() {
        return list;
    }

    public void setList(List<BindBoxListBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "BindBoxList{" +
                "list=" + list +
                '}';
    }
}
