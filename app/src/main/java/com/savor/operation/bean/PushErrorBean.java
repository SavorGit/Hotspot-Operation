package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/9/7.
 */

public class PushErrorBean implements Serializable {
    private String error_id;

    @Override
    public String toString() {
        return "PushErrorBean{" +
                "error_id='" + error_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PushErrorBean that = (PushErrorBean) o;

        return error_id != null ? error_id.equals(that.error_id) : that.error_id == null;

    }

    @Override
    public int hashCode() {
        return error_id != null ? error_id.hashCode() : 0;
    }

    public String getError_id() {
        return error_id;
    }

    public void setError_id(String error_id) {
        this.error_id = error_id;
    }
}
