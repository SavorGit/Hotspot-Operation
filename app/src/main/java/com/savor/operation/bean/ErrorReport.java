package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2017/9/6.
 */

public class ErrorReport implements Serializable {
    private static final long serialVersionUID = -1;

    private List<ErrorReportBean> list;
    private int isNextPage;

    public List<ErrorReportBean> getList() {
        return list;
    }

    public void setList(List<ErrorReportBean> list) {
        this.list = list;
    }

    public int getIsNextPage() {
        return isNextPage;
    }

    public void setIsNextPage(int isNextPage) {
        this.isNextPage = isNextPage;
    }


    @Override
    public String toString() {
        return "ErrorReport{" +
                "list=" + list +
                ", isNextPage=" + isNextPage +
                '}';
    }
}
