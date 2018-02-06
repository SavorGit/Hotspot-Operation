package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by bushlee on 2018/2/6.
 */

public class PubUserBean implements Serializable {
    private static final long serialVersionUID = -1;
    private String publish_user_id;
    private String remark;

    public String getPublish_user_id() {
        return publish_user_id;
    }

    public void setPublish_user_id(String publish_user_id) {
        this.publish_user_id = publish_user_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "PubUserBean{" +
                "publish_user_id='" + publish_user_id + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
