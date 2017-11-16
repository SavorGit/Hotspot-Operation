package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/11/16.
 */

public class BindBoxResponse implements Serializable {

    /**
     * type : 2
     * err_msg : MAC已经存在于永峰写字楼(测试)的大会议室的SONY
     */

    private int type;
    private String err_msg;

    @Override
    public String toString() {
        return "BindBoxResponse{" +
                "type=" + type +
                ", err_msg='" + err_msg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BindBoxResponse that = (BindBoxResponse) o;

        if (type != that.type) return false;
        return err_msg != null ? err_msg.equals(that.err_msg) : that.err_msg == null;
    }

    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + (err_msg != null ? err_msg.hashCode() : 0);
        return result;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }
}
