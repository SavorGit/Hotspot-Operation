package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 发布内容列表
 * Created by hezd on 2018/1/31.
 */

public class ContentListResponse implements Serializable {
    private List<PubProgram> program_list;

    private String menu_num;
    private String ads_menu_num;

    @Override
    public String toString() {
        return "ContentListResponse{" +
                "program_list=" + program_list +
                ", menu_num='" + menu_num + '\'' +
                ", ads_menu_num='" + ads_menu_num + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContentListResponse that = (ContentListResponse) o;

        if (program_list != null ? !program_list.equals(that.program_list) : that.program_list != null)
            return false;
        if (menu_num != null ? !menu_num.equals(that.menu_num) : that.menu_num != null)
            return false;
        return ads_menu_num != null ? ads_menu_num.equals(that.ads_menu_num) : that.ads_menu_num == null;
    }

    @Override
    public int hashCode() {
        int result = program_list != null ? program_list.hashCode() : 0;
        result = 31 * result + (menu_num != null ? menu_num.hashCode() : 0);
        result = 31 * result + (ads_menu_num != null ? ads_menu_num.hashCode() : 0);
        return result;
    }

    public List<PubProgram> getProgram_list() {
        return program_list;
    }

    public void setProgram_list(List<PubProgram> program_list) {
        this.program_list = program_list;
    }

    public String getMenu_num() {
        return menu_num;
    }

    public void setMenu_num(String menu_num) {
        this.menu_num = menu_num;
    }

    public String getAds_menu_num() {
        return ads_menu_num;
    }

    public void setAds_menu_num(String ads_menu_num) {
        this.ads_menu_num = ads_menu_num;
    }
}
