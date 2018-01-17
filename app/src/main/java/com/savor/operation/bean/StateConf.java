package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2018/1/17.
 */

public class StateConf implements Serializable {
    private static final long serialVersionUID = -1;
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StateConf{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
