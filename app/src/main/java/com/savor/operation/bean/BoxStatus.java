package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hezd on 2018/1/23.
 */

public class BoxStatus implements Serializable {

    /**
     * all_num : 950
     * normal_all_num : 925
     * break_all_num : 5
     * freeze_all_num : 20
     * list : [{"name":"一代","box_all_num":"105","box_normal_all_num":"102","box_freeze_all_num":"2","freeze_all_num":"0"},{"name":"二代","box_all_num":"462","box_normal_all_num":"455","box_freeze_all_num":"3","freeze_all_num":"0"},{"name":"5G","box_all_num":"485","box_normal_all_num":"468","box_freeze_all_num":"1","freeze_all_num":"0"},{"name":"三代","box_all_num":"157","box_normal_all_num":"147","box_freeze_all_num":"2","freeze_all_num":"0"}]
     */

    private String all_num;
    private String normal_all_num;
    private String break_all_num;
    private String freeze_all_num;
    private List<ListBean> list;

    @Override
    public String toString() {
        return "BoxStatus{" +
                "all_num='" + all_num + '\'' +
                ", normal_all_num='" + normal_all_num + '\'' +
                ", break_all_num='" + break_all_num + '\'' +
                ", freeze_all_num='" + freeze_all_num + '\'' +
                ", list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxStatus boxStatus = (BoxStatus) o;

        if (all_num != null ? !all_num.equals(boxStatus.all_num) : boxStatus.all_num != null)
            return false;
        if (normal_all_num != null ? !normal_all_num.equals(boxStatus.normal_all_num) : boxStatus.normal_all_num != null)
            return false;
        if (break_all_num != null ? !break_all_num.equals(boxStatus.break_all_num) : boxStatus.break_all_num != null)
            return false;
        if (freeze_all_num != null ? !freeze_all_num.equals(boxStatus.freeze_all_num) : boxStatus.freeze_all_num != null)
            return false;
        return list != null ? list.equals(boxStatus.list) : boxStatus.list == null;
    }

    @Override
    public int hashCode() {
        int result = all_num != null ? all_num.hashCode() : 0;
        result = 31 * result + (normal_all_num != null ? normal_all_num.hashCode() : 0);
        result = 31 * result + (break_all_num != null ? break_all_num.hashCode() : 0);
        result = 31 * result + (freeze_all_num != null ? freeze_all_num.hashCode() : 0);
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }

    public String getAll_num() {
        return all_num;
    }

    public void setAll_num(String all_num) {
        this.all_num = all_num;
    }

    public String getNormal_all_num() {
        return normal_all_num;
    }

    public void setNormal_all_num(String normal_all_num) {
        this.normal_all_num = normal_all_num;
    }

    public String getBreak_all_num() {
        return break_all_num;
    }

    public void setBreak_all_num(String break_all_num) {
        this.break_all_num = break_all_num;
    }

    public String getFreeze_all_num() {
        return freeze_all_num;
    }

    public void setFreeze_all_num(String freeze_all_num) {
        this.freeze_all_num = freeze_all_num;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 一代
         * box_all_num : 105
         * box_normal_all_num : 102
         * box_freeze_all_num : 2
         * freeze_all_num : 0
         */

        private String name;
        private String box_all_num;
        private String box_normal_all_num;
        private String box_break_all_num;
        private String box_freeze_all_num;

        @Override
        public String toString() {
            return "ListBean{" +
                    "name='" + name + '\'' +
                    ", box_all_num='" + box_all_num + '\'' +
                    ", box_normal_all_num='" + box_normal_all_num + '\'' +
                    ", box_break_all_num='" + box_break_all_num + '\'' +
                    ", box_freeze_all_num='" + box_freeze_all_num + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ListBean listBean = (ListBean) o;

            if (name != null ? !name.equals(listBean.name) : listBean.name != null) return false;
            if (box_all_num != null ? !box_all_num.equals(listBean.box_all_num) : listBean.box_all_num != null)
                return false;
            if (box_normal_all_num != null ? !box_normal_all_num.equals(listBean.box_normal_all_num) : listBean.box_normal_all_num != null)
                return false;
            if (box_break_all_num != null ? !box_break_all_num.equals(listBean.box_break_all_num) : listBean.box_break_all_num != null)
                return false;
            return box_freeze_all_num != null ? box_freeze_all_num.equals(listBean.box_freeze_all_num) : listBean.box_freeze_all_num == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (box_all_num != null ? box_all_num.hashCode() : 0);
            result = 31 * result + (box_normal_all_num != null ? box_normal_all_num.hashCode() : 0);
            result = 31 * result + (box_break_all_num != null ? box_break_all_num.hashCode() : 0);
            result = 31 * result + (box_freeze_all_num != null ? box_freeze_all_num.hashCode() : 0);
            return result;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBox_all_num() {
            return box_all_num;
        }

        public void setBox_all_num(String box_all_num) {
            this.box_all_num = box_all_num;
        }

        public String getBox_normal_all_num() {
            return box_normal_all_num;
        }

        public void setBox_normal_all_num(String box_normal_all_num) {
            this.box_normal_all_num = box_normal_all_num;
        }

        public String getBox_break_all_num() {
            return box_break_all_num;
        }

        public void setBox_break_all_num(String box_break_all_num) {
            this.box_break_all_num = box_break_all_num;
        }

        public String getBox_freeze_all_num() {
            return box_freeze_all_num;
        }

        public void setBox_freeze_all_num(String box_freeze_all_num) {
            this.box_freeze_all_num = box_freeze_all_num;
        }
    }
}
