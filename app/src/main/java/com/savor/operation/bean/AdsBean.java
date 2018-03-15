package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2018/3/14.
 */

public class AdsBean implements Serializable {

    /**
     * media_id : 4832
     * order : 1
     * state : 1
     * name : 阿斯彭寓所的全景展示3 6秒
     * type : 广告
     */

    private String media_id;
    private String order;
    private int state;
    private String name;
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdsBean adsBean = (AdsBean) o;

        if (state != adsBean.state) return false;
        if (media_id != null ? !media_id.equals(adsBean.media_id) : adsBean.media_id != null)
            return false;
        if (order != null ? !order.equals(adsBean.order) : adsBean.order != null) return false;
        if (name != null ? !name.equals(adsBean.name) : adsBean.name != null) return false;
        return type != null ? type.equals(adsBean.type) : adsBean.type == null;
    }

    @Override
    public int hashCode() {
        int result = media_id != null ? media_id.hashCode() : 0;
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + state;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
