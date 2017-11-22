package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/22.
 */

public class ExeTask implements Serializable {
    private static final long serialVersionUID = -1;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "ExeTask{" +
                "state=" + state +
                '}';
    }
}
