package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetTLAdditionalPersonalDetail {
    @SerializedName("DOB")
    @Expose
    var dob: String? = null

    @SerializedName("pincode")
    @Expose
    var pincode: String? = null

    @SerializedName("gender")
    @Expose
    var gender: String? = null
}
