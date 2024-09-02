package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleDetailUserDetail {
    @SerializedName("fullName")
    @Expose
    var fullName: String? = null

    @SerializedName("mobileNumber")
    @Expose
    var mobileNumber: Long? = null

    @SerializedName("pincode")
    @Expose
    var pincode: String? = null
}
