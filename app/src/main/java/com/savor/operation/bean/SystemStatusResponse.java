package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2018/1/23.
 */

public class SystemStatusResponse implements Serializable {
    private SystemStatus list;

    @Override
    public String toString() {
        return "SystemStatusResponse{" +
                "list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemStatusResponse that = (SystemStatusResponse) o;

        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        return list != null ? list.hashCode() : 0;
    }

    public SystemStatus getList() {
        return list;
    }

    public void setList(SystemStatus list) {
        this.list = list;
    }
}
