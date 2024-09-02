package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class KYCVerificationDocDetail {
    @SerializedName("docType")
    @Expose
    var docType: String? = null

    @SerializedName("docNumber")
    @Expose
    var docNumber: String? = null

    @SerializedName("DOB")
    @Expose
    var dob: String? = null

    @SerializedName("ownerType")
    @Expose
    var ownerType: String? = null

    @SerializedName("DOI")
    @Expose
    var doi: Any? = null

    @SerializedName("docStatus")
    @Expose
    var docStatus: Boolean? = null
}
