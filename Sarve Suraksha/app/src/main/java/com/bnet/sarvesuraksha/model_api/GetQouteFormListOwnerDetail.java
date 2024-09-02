package com.bnet.sarvesuraksha.model_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQouteFormListOwnerDetail {
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("email")
    @Expose
    private String email;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
