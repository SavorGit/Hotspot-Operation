package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2018/2/6.
 */

public class PubUser implements Serializable {
    private static final long serialVersionUID = -1;
    private List<PubUserBean> list;

    public List<PubUserBean> getList() {
        return list;
    }

    public void setList(List<PubUserBean> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PubUser{" +
                "list=" + list +
                '}';
    }
}
