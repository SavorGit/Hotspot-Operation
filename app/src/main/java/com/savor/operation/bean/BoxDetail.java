package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 机顶盒详情
 * Created by hezd on 2018/1/22.
 */

public class BoxDetail implements Serializable {
    private String box_name;
    private String box_mac;
    private String log_upload_time;
    private List<FixHistoryResponse.PositionInfo.BoxInfoBean.RepaireInfo> repair_record;
    private String last_heart_time;
    private String loss_hours;
    private String pro_period_state;
    private String ads_period_state;
    private String pro_period;
    private String ads_period;
    private List<Program> program_list;

    @Override
    public String toString() {
        return "BoxDetail{" +
                "box_name='" + box_name + '\'' +
                ", box_mac='" + box_mac + '\'' +
                ", log_upload_time='" + log_upload_time + '\'' +
                ", repair_record=" + repair_record +
                ", last_heart_time='" + last_heart_time + '\'' +
                ", loss_hours='" + loss_hours + '\'' +
                ", pro_period_state='" + pro_period_state + '\'' +
                ", ads_period_state='" + ads_period_state + '\'' +
                ", pro_period='" + pro_period + '\'' +
                ", ads_period='" + ads_period + '\'' +
                ", program_list=" + program_list +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxDetail boxDetail = (BoxDetail) o;

        if (box_name != null ? !box_name.equals(boxDetail.box_name) : boxDetail.box_name != null)
            return false;
        if (box_mac != null ? !box_mac.equals(boxDetail.box_mac) : boxDetail.box_mac != null)
            return false;
        if (log_upload_time != null ? !log_upload_time.equals(boxDetail.log_upload_time) : boxDetail.log_upload_time != null)
            return false;
        if (repair_record != null ? !repair_record.equals(boxDetail.repair_record) : boxDetail.repair_record != null)
            return false;
        if (last_heart_time != null ? !last_heart_time.equals(boxDetail.last_heart_time) : boxDetail.last_heart_time != null)
            return false;
        if (loss_hours != null ? !loss_hours.equals(boxDetail.loss_hours) : boxDetail.loss_hours != null)
            return false;
        if (pro_period_state != null ? !pro_period_state.equals(boxDetail.pro_period_state) : boxDetail.pro_period_state != null)
            return false;
        if (ads_period_state != null ? !ads_period_state.equals(boxDetail.ads_period_state) : boxDetail.ads_period_state != null)
            return false;
        if (pro_period != null ? !pro_period.equals(boxDetail.pro_period) : boxDetail.pro_period != null)
            return false;
        if (ads_period != null ? !ads_period.equals(boxDetail.ads_period) : boxDetail.ads_period != null)
            return false;
        return program_list != null ? program_list.equals(boxDetail.program_list) : boxDetail.program_list == null;
    }

    @Override
    public int hashCode() {
        int result = box_name != null ? box_name.hashCode() : 0;
        result = 31 * result + (box_mac != null ? box_mac.hashCode() : 0);
        result = 31 * result + (log_upload_time != null ? log_upload_time.hashCode() : 0);
        result = 31 * result + (repair_record != null ? repair_record.hashCode() : 0);
        result = 31 * result + (last_heart_time != null ? last_heart_time.hashCode() : 0);
        result = 31 * result + (loss_hours != null ? loss_hours.hashCode() : 0);
        result = 31 * result + (pro_period_state != null ? pro_period_state.hashCode() : 0);
        result = 31 * result + (ads_period_state != null ? ads_period_state.hashCode() : 0);
        result = 31 * result + (pro_period != null ? pro_period.hashCode() : 0);
        result = 31 * result + (ads_period != null ? ads_period.hashCode() : 0);
        result = 31 * result + (program_list != null ? program_list.hashCode() : 0);
        return result;
    }

    public String getBox_name() {
        return box_name;
    }

    public void setBox_name(String box_name) {
        this.box_name = box_name;
    }

    public String getBox_mac() {
        return box_mac;
    }

    public void setBox_mac(String box_mac) {
        this.box_mac = box_mac;
    }

    public String getLog_upload_time() {
        return log_upload_time;
    }

    public void setLog_upload_time(String log_upload_time) {
        this.log_upload_time = log_upload_time;
    }

    public List<FixHistoryResponse.PositionInfo.BoxInfoBean.RepaireInfo> getRepair_record() {
        return repair_record;
    }

    public void setRepair_record(List<FixHistoryResponse.PositionInfo.BoxInfoBean.RepaireInfo> repair_record) {
        this.repair_record = repair_record;
    }

    public String getLast_heart_time() {
        return last_heart_time;
    }

    public void setLast_heart_time(String last_heart_time) {
        this.last_heart_time = last_heart_time;
    }

    public String getLoss_hours() {
        return loss_hours;
    }

    public void setLoss_hours(String loss_hours) {
        this.loss_hours = loss_hours;
    }

    public String getPro_period_state() {
        return pro_period_state;
    }

    public void setPro_period_state(String pro_period_state) {
        this.pro_period_state = pro_period_state;
    }

    public String getAds_period_state() {
        return ads_period_state;
    }

    public void setAds_period_state(String ads_period_state) {
        this.ads_period_state = ads_period_state;
    }

    public String getPro_period() {
        return pro_period;
    }

    public void setPro_period(String pro_period) {
        this.pro_period = pro_period;
    }

    public String getAds_period() {
        return ads_period;
    }

    public void setAds_period(String ads_period) {
        this.ads_period = ads_period;
    }

    public List<Program> getProgram_list() {
        return program_list;
    }

    public void setProgram_list(List<Program> program_list) {
        this.program_list = program_list;
    }
}
