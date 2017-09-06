package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2017/9/6.
 */

public class ErrorReport implements Serializable {
    private static final long serialVersionUID = -1;

    private List<ErrorReportBean> list;

    public List<ErrorReportBean> getList() {
        return list;
    }

    public void setList(List<ErrorReportBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ErrorReport{" +
                "list=" + list +
                '}';
    }
}
