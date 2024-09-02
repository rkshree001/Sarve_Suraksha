package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class VerifyDocTypeKycTLPerposerDetail {
    @SerializedName("memberType")
    @Expose
    var memberType: String? = null

    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null
}