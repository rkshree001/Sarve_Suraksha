package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostOrEditVehicleBasicDetail {
    @SerializedName("registrationNumber")
    @Expose
    var registrationNumber: String? = null

    @SerializedName("registrationDate")
    @Expose
    var registrationDate: String? = null
}
