package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetPincodeDetailsMainRes {
    @SerializedName("pincode")
    @Expose
    var pincode: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("district")
    @Expose
    var district: String? = null

    @SerializedName("POName")
    @Expose
    var pOName: List<String>? = null
}
