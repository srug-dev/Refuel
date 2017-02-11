package com.srug.mobile.refuel.model.mediator.data;

public class Vehicle {

    private Long mId;
    private Long mUserId;
    private String mBrand;
    private String mModel;
    private String mPlate;

    public Vehicle(Long id, Long userId) {
        mId = id;
        mUserId = userId;
    }

    public Long getId() {
        return mId;
    }

    public Long getUserId() {
        return mUserId;
    }

    public String getBrand() {
        return mBrand;
    }

    public void setBrand(String brand) {
        mBrand = brand;
    }

    public String getModel() {
        return mModel;
    }

    public void setModel(String model) {
        mModel = model;
    }

    public String getPlate() {
        return mPlate;
    }

    public void setPlate(String plate) {
        mPlate = plate;
    }
}
