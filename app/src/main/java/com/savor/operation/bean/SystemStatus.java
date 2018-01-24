package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2018/1/23.
 */

public class SystemStatus implements Serializable {
    private TotalStatus heart;
    private HotelStatus hotel;
    private SmallStatus small;
    private BoxStatus box;

    @Override
    public String toString() {
        return "SystemStatus{" +
                "heart=" + heart +
                ", hotel=" + hotel +
                ", small=" + small +
                ", box=" + box +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemStatus that = (SystemStatus) o;

        if (heart != null ? !heart.equals(that.heart) : that.heart != null) return false;
        if (hotel != null ? !hotel.equals(that.hotel) : that.hotel != null) return false;
        if (small != null ? !small.equals(that.small) : that.small != null) return false;
        return box != null ? box.equals(that.box) : that.box == null;
    }

    @Override
    public int hashCode() {
        int result = heart != null ? heart.hashCode() : 0;
        result = 31 * result + (hotel != null ? hotel.hashCode() : 0);
        result = 31 * result + (small != null ? small.hashCode() : 0);
        result = 31 * result + (box != null ? box.hashCode() : 0);
        return result;
    }

    public TotalStatus getHeart() {
        return heart;
    }

    public void setHeart(TotalStatus heart) {
        this.heart = heart;
    }

    public HotelStatus getHotel() {
        return hotel;
    }

    public void setHotel(HotelStatus hotel) {
        this.hotel = hotel;
    }

    public SmallStatus getSmall() {
        return small;
    }

    public void setSmall(SmallStatus small) {
        this.small = small;
    }

    public BoxStatus getBox() {
        return box;
    }

    public void setBox(BoxStatus box) {
        this.box = box;
    }
}
