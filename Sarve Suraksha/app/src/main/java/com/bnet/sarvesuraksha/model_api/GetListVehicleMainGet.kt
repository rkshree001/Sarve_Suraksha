package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetListVehicleMainGet {
    @SerializedName("vehicleList")
    @Expose
    var vehicleList: List<GetListVehicleMainRes>? = null
}
