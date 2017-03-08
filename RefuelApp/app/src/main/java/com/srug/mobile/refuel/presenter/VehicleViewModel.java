package com.srug.mobile.refuel.presenter;

public class VehicleViewModel {

    private Long mId;
    private Long mUserId;
    private String mBrand;
    private String mModel;
    private String mPlate;
    private boolean mIsSelected;

    public VehicleViewModel(Long id, Long userId) {
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

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setIsSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    @Override
    public String toString() {
        if (mBrand == null || mBrand.isEmpty()) {
            return "Default vehicle";
        }
        String vehicleName = mBrand;
        if (mModel != null && !mModel.isEmpty()) {
            vehicleName += " " + mModel;
        }
        if (mPlate != null && !mPlate.isEmpty()) {
            vehicleName += " (" + mPlate + ")";
        }
        return vehicleName;
    }
}
