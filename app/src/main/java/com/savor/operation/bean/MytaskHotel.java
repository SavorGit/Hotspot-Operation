package com.savor.operation.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bushlee on 2018/2/6.
 */

public class MytaskHotel implements Serializable {
    private static final long serialVersionUID = -1;
    private HotelHeart heart;
    private List<MyHotelBean> hotel;
    private int isNextPage;

    public HotelHeart getHeart() {
        return heart;
    }

    public void setHeart(HotelHeart heart) {
        this.heart = heart;
    }

    public List<MyHotelBean> getHotel() {
        return hotel;
    }

    public void setHotel(List<MyHotelBean> hotel) {
        this.hotel = hotel;
    }

    public int getIsNextPage() {
        return isNextPage;
    }

    public void setIsNextPage(int isNextPage) {
        this.isNextPage = isNextPage;
    }

    @Override
    public String toString() {
        return "MytaskHotel{" +
                "heart=" + heart +
                ", hotel=" + hotel +
                ", isNextPage=" + isNextPage +
                '}';
    }
}
