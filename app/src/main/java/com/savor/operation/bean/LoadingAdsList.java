package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 下载中的广告列表
 * Created by hezd on 2018/3/14.
 */

public class LoadingAdsList implements Serializable {
    private String period;
    private String download_percent;
    private List<AdsBean> list;

    @Override
    public String toString() {
        return "LoadingAdsList{" +
                "period='" + period + '\'' +
                ", download_percent='" + download_percent + '\'' +
                ", list=" + list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoadingAdsList that = (LoadingAdsList) o;

        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (download_percent != null ? !download_percent.equals(that.download_percent) : that.download_percent != null)
            return false;
        return list != null ? list.equals(that.list) : that.list == null;
    }

    @Override
    public int hashCode() {
        int result = period != null ? period.hashCode() : 0;
        result = 31 * result + (download_percent != null ? download_percent.hashCode() : 0);
        result = 31 * result + (list != null ? list.hashCode() : 0);
        return result;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDownload_percent() {
        return download_percent;
    }

    public void setDownload_percent(String download_percent) {
        this.download_percent = download_percent;
    }

    public List<AdsBean> getList() {
        return list;
    }

    public void setList(List<AdsBean> list) {
        this.list = list;
    }
}
