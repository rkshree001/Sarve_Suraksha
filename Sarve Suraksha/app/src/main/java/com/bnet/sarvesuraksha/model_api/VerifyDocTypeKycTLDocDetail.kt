package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VerifyDocTypeKycTLDocDetail {
    @SerializedName("docType")
    @Expose
    var docType: String? = null

    @SerializedName("docNumber")
    @Expose
    var docNumber: String? = null

    @SerializedName("DOB")
    @Expose
    var dob: String? = null

    @SerializedName("docStatus")
    @Expose
    var docStatus: Boolean? = null
}
