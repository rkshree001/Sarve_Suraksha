package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetIndividualVehicleMainGet {
    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: GetIndividualVehicleMainRes? = null

    @SerializedName("docDetail")
    @Expose
    var docDetail: List<Any>? = null
}
