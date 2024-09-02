package com.bnet.sarvesuraksha.model_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostVehicleBasicDetail1 {
    @SerializedName("registrationNumber")
    @Expose
    private String registrationNumber;
    @SerializedName("registrationDate")
    @Expose
    private String registrationDate;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

}
