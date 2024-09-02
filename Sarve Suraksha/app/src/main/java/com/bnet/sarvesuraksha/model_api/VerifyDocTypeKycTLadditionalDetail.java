package com.bnet.sarvesuraksha.model_api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyDocTypeKycTLadditionalDetail {

    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("annuallIncome")
    @Expose
    private GetTLAdditionalAnnuallIncome annuallIncome;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public GetTLAdditionalAnnuallIncome getAnnuallIncome() {
        return annuallIncome;
    }

    public void setAnnuallIncome(GetTLAdditionalAnnuallIncome annuallIncome) {
        this.annuallIncome = annuallIncome;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
