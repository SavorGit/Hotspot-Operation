package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2018/1/18.
 */

public class MyInspectResult implements Serializable {
    private static final long serialVersionUID = -1;
    private List<MyInspect> list;
    private int count;
    private int isNextPage;

    public List<MyInspect> getList() {
        return list;
    }

    public void setList(List<MyInspect> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIsNextPage() {
        return isNextPage;
    }

    public void setIsNextPage(int isNextPage) {
        this.isNextPage = isNextPage;
    }

    @Override
    public String toString() {
        return "MyInspectResult{" +
                "list=" + list +
                ", count=" + count +
                ", isNextPage=" + isNextPage +
                '}';
    }
}
