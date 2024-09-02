package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostVehicleOwnerDetail {
    @SerializedName("ownerName")
    @Expose
    var ownerName: String? = null

    @SerializedName("mobileNumber")
    @Expose
    var mobileNumber: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null
}
