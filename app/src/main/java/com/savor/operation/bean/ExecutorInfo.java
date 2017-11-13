package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2017/11/13.
 */

public class ExecutorInfo implements Serializable {
    private static final long serialVersionUID = -1;
    private List<ExecutorInfoBean> list;

    public List<ExecutorInfoBean> getList() {
        return list;
    }

    public void setList(List<ExecutorInfoBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ExecutorInfo{" +
                "list=" + list +
                '}';
    }
}
