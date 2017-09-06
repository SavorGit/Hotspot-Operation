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

    @Override
    public String toString() {
        return "ErrorDetail{" +
                "info='" + info + '\'' +
                ", date='" + date + '\'' +
                ", list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorDetail)) return false;

        ErrorDetail that = (ErrorDetail) o;

        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return list != null ? list.equals(that.list) : that.list == null;

    }

    @Override
    public int hashCode() {
        int result = info != null ? info.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }
}
