package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleDetailRcMainGet {
    @SerializedName("rcDetail")
    @Expose
    var rcDetail: GetVehicleDetailRcMainRes? = null
}
