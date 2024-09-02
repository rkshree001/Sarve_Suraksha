package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class UserOccupation {

    @SerializedName("employeType")
    @Expose
    private var employeType: String? = null

    @SerializedName("annualIncome")
    @Expose
    private var annualIncome: Int? = null

    fun getEmployeType(): String? {
        return employeType
    }

    fun setEmployeType(employeType: String?) {
        this.employeType = employeType
    }

    fun getAnnualIncome(): Int? {
        return annualIncome
    }

    fun setAnnualIncome(annualIncome: Int?) {
        this.annualIncome = annualIncome
    }

}