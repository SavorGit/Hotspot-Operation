package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2017/9/6.
 */

public class ErrorDetail implements Serializable {
    private static final long serialVersionUID = -1;
    private String info;
    private String date;
    private List<ErrorDetailBean> list;
    private int isNextPage;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ErrorDetailBean> getList() {
        return list;
    }

    public void setList(List<ErrorDetailBean> list) {
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
        return "ErrorDetail{" +
                "info='" + info + '\'' +
                ", date='" + date + '\'' +
                ", list=" + list +
                ", isNextPage=" + isNextPage +
                '}';
    }
}
