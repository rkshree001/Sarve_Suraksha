package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddVehicleDataGet {
    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: AddVehicleDetail? = null

    @SerializedName("vehicleBasicDetail")
    @Expose
    var vehicleBasicDetail: AddVehicleBasicDetail? = null

    @SerializedName("ownerDetail")
    @Expose
    var ownerDetail: GetQouteFormListOwnerDetail? = null
}
