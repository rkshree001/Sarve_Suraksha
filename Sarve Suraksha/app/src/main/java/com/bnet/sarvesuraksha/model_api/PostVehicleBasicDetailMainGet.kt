package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostVehicleBasicDetailMainGet {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: PostVehicleDetailPropsal? = null

    @SerializedName("vehicleBasicDetail")
    @Expose
    var vehicleBasicDetail: PostVehicleBasicDetail? = null

    @SerializedName("ownerDetail")
    @Expose
    var ownerDetail: PostVehicleOwnerDetail? = null

    @SerializedName("insuranceDetail")
    @Expose
    var insuranceDetail: List<GetVehicleInsuranceDetail>? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null
}
