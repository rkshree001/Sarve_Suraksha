package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleDetailRcMainRes {
    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: GetVehicleDetailRc? = null

    @SerializedName("ownerdetail")
    @Expose
    var ownerdetail: GetVehicleOwnerDetail? = null

    @SerializedName("insuranceDetail")
    @Expose
    var insuranceDetail: List<GetVehicleInsuranceDetail>? = null
}
