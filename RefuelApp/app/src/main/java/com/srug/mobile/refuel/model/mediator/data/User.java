package com.srug.mobile.refuel.model.mediator.data;

public class User {

    private Long mId;
    private String mEmail;

    public User(Long id) {
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
}
