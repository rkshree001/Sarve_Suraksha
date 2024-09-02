package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserAadharCard {

    @SerializedName("idNumber")
    @Expose
    private var idNumber: String? = null

    @SerializedName("status")
    @Expose
    private var status: String? = null

    fun getIdNumber(): String? {
        return idNumber
    }

    fun setIdNumber(idNumber: String?) {
        this.idNumber = idNumber
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }

}