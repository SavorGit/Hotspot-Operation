package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2018/2/6.
 */

public class MyHotel implements Serializable {
    private static final long serialVersionUID = -1;
    private MytaskHotel list;

    public MytaskHotel getList() {
        return list;
    }

    public void setList(MytaskHotel list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "MyHotel{" +
                "list=" + list +
                '}';
    }
}
