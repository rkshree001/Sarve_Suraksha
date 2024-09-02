package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostVehicleMainGet {
    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: PostVehicleMainRes? = null
}
