package com.savor.operation.bean;

import java.io.Serializable;

/**
 * 机顶盒状态
 * Created by hezd on 2018/1/23.
 */

public class BoxState implements Serializable {

    /**
     * id : 2
     * name : 冻结
     */

    private int id;
    private String name;

    @Override
    public String toString() {
        return "BoxState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxState boxState = (BoxState) o;

        if (id != boxState.id) return false;
        return name != null ? name.equals(boxState.name) : boxState.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
