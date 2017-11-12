package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2017/11/6.
 */

public class TaskDetailRepairImg implements Serializable {
    private static final long serialVersionUID = -1;
    private String type ;
    private String img;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "TaskDetailRepairImg{" +
                "type='" + type + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
