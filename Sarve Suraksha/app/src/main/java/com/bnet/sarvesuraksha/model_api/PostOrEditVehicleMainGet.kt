package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostOrEditVehicleMainGet {
    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: PostOrEditVehicleDetail? = null

    @SerializedName("vehicleBasicDetail")
    @Expose
    var vehicleBasicDetail: PostOrEditVehicleBasicDetail? = null

    @SerializedName("vehicleType")
    @Expose
    var vehicleType: String? = null

    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("insuranceDetail")
    @Expose
    var insuranceDetail: List<Any>? = null

    @SerializedName("__v")
    @Expose
    var v: Int? = null
}
