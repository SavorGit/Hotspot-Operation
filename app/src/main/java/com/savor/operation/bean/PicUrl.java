package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/22.
 */

public class PicUrl implements Serializable {
    private static final long serialVersionUID = -1;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "PicUrl{" +
                "img='" + img + '\'' +
                '}';
    }
}
