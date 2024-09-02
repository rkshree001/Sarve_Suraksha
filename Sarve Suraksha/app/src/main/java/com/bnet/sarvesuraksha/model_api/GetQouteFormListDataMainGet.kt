package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetQouteFormListDataMainGet {
    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: GetQouteFormListVehicleDetail? = null

    @SerializedName("vehicleBasicDetail")
    @Expose
    var vehicleBasicDetail: GetQouteFormVehicleBasicDetail? = null

    @SerializedName("ownerDetail")
    @Expose
    var ownerDetail: GetQouteFormListOwnerDetail? = null
}
