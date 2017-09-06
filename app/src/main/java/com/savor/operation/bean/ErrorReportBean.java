package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/9/6.
 */

public class ErrorReportBean implements Serializable {
    private static final long serialVersionUID = -1;

    private String id;
    private String info;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "ErrorReportBean{" +
                "id='" + id + '\'' +
                ", info='" + info + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorReportBean)) return false;

        ErrorReportBean that = (ErrorReportBean) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
