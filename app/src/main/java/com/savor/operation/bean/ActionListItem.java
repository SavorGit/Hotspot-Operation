package com.savor.operation.bean;

import com.savor.operation.activity.SavorMainActivity;
import com.savor.operation.enums.FunctionType;

import java.io.Serializable;

/**
 * Created by hezd on 2017/11/2.
 */

public class ActionListItem implements Serializable {
    private FunctionType type;
    private int num;

    public ActionListItem () {}

    public ActionListItem (FunctionType type,int num) {
        this.type = type;
        this.num = num;
    }
    @Override
    public String toString() {
        return "ActionListItem{" +
                "type=" + type +
                ", num=" + num +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActionListItem that = (ActionListItem) o;

        if (num != that.num) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + num;
        return result;
    }

    public FunctionType getType() {
        return type;
    }

    public void setType(FunctionType type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
