package com.srug.mobile.refuel.model.mediator.data;

import java.util.Date;

public class Refueling {

    private Long mId;
    private Long mVehicleId;
    private Date mDate;
    private Long mDistance;
    private Long mPrice;
    private Long mAmount;

    public Refueling(Long id, Long vehicleId) {
        mId = id;
        mVehicleId = vehicleId;
    }

    public Long getId() {
        return mId;
    }

    public Long getVehicleId() {
        return mVehicleId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Long getDistance() {
        return mDistance;
    }

    public void setDistance(long distance) {
        mDistance = distance;
    }

    public Long getPrice() {
        return mPrice;
    }

    public void setPrice(long price) {
        mPrice = price;
    }

    public Long getAmount() {
        return mAmount;
    }

    public void setAmount(long amount) {
        mAmount = amount;
    }
}
