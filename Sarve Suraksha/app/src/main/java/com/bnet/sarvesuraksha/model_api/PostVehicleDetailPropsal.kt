package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostVehicleDetailPropsal {
    @SerializedName("makerName")
    @Expose
    var makerName: String? = null

    @SerializedName("modelName")
    @Expose
    var modelName: String? = null

    @SerializedName("variantName")
    @Expose
    var variantName: String? = null

    @SerializedName("fuelType")
    @Expose
    var fuelType: String? = null

    @SerializedName("rtoDetail")
    @Expose
    var rtoDetail: GetVehicleRtoDetail? = null

    @SerializedName("vehicleSegment")
    @Expose
    var vehicleSegment: String? = null

    @SerializedName("typeOfVehicle")
    @Expose
    var typeOfVehicle: String? = null

    @SerializedName("vehicleType")
    @Expose
    var vehicleType: String? = null
}
