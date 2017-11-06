package com.savor.operation.bean;

import java.io.Serializable;

/**
 * 维修任务
 * Created by hezd on 2017/11/3.
 */

public class FixTask implements Serializable {
    private String boxName;
    private String exceptioinDes;
    private String url;

    @Override
    public String toString() {
        return "FixTask{" +
                "boxName='" + boxName + '\'' +
                ", exceptioinDes='" + exceptioinDes + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixTask fixTask = (FixTask) o;

        if (boxName != null ? !boxName.equals(fixTask.boxName) : fixTask.boxName != null)
            return false;
        if (exceptioinDes != null ? !exceptioinDes.equals(fixTask.exceptioinDes) : fixTask.exceptioinDes != null)
            return false;
        return url != null ? url.equals(fixTask.url) : fixTask.url == null;
    }

    @Override
    public int hashCode() {
        int result = boxName != null ? boxName.hashCode() : 0;
        result = 31 * result + (exceptioinDes != null ? exceptioinDes.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public String getExceptioinDes() {
        return exceptioinDes;
    }

    public void setExceptioinDes(String exceptioinDes) {
        this.exceptioinDes = exceptioinDes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
