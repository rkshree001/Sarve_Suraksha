package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleRcDetailGet {
    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: GetVehicleDetailMainRes? = null

    @SerializedName("userDetail")
    @Expose
    var userDetail: GetVehicleDetailUserDetail? = null
}
