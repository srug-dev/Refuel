package com.srug.mobile.refuel.presenter;

public class UserViewModel {

    private Long mId;
    private String mEmail;
    private boolean mIsSelected;

    public UserViewModel(Long id) {
        mId = id;
    }

    public Long getId() {
        return mId;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setIsSelected(boolean isSelected) {
        mIsSelected = isSelected;
    }

    @Override
    public String toString() {
        return mEmail == null || mEmail.isEmpty() ? "Default user" : mEmail;
    }
}
