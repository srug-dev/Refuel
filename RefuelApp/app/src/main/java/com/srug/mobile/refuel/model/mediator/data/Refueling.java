package com.srug.mobile.refuel.model.mediator.data;

import java.util.Date;

public class Refueling implements Comparable {

    private Long mId;
    private Long mVehicleId;
    private Date mDate;
    private Double mDistance;
    private Double mPrice;
    private Double mAmount;

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

    public Double getDistance() {
        return mDistance;
    }

    public void setDistance(Double distance) {
        mDistance = distance;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }

    public Double getAmount() {
        return mAmount;
    }

    public void setAmount(Double amount) {
        mAmount = amount;
    }

    @Override
    public int compareTo(Object another) {
        return getDate().compareTo(((Refueling)another).getDate());
    }
}
