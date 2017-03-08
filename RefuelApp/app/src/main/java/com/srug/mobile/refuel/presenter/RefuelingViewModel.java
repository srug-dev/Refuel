package com.srug.mobile.refuel.presenter;

import java.util.Date;

public class RefuelingViewModel {

    private long mId;
    private long mVehicleId;
    private Date mDate;
    private double mDistance;
    private double mPrice;
    private double mAmount;
    private boolean mIsSelected;

    public RefuelingViewModel(long id, long vehicleId) {
        mId = id;
        mVehicleId = vehicleId;
    }

    public long getId() {
        return mId;
    }

    public long getVehicleId() {
        return mVehicleId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(double distance) {
        mDistance = distance;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        mAmount = amount;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setIsSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    public double getConsumption() {
        double amount = getAmount();
        double price = getPrice();
        if (!(amount > 0 && price > 0)) {
            return 0.0;
        }
        double consumption = amount / price;
        return (double) Math.round(consumption * 1000) / 1000;
    }

    public double getConsumptionAvg() {
        double consumption = getConsumption();
        double distance = getDistance();
        if (!(consumption > 0 && distance > 0)) {
            return 0.0;
        }
        double average = (consumption * 100) / distance;
        return (double) Math.round(average * 1000) / 1000;
    }
}
