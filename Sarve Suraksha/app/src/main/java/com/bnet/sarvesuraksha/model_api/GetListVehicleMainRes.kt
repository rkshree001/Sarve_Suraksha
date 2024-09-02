package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetListVehicleMainRes {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("registrationNumber")
    @Expose
    var registrationNumber: String? = null

    @SerializedName("makerName")
    @Expose
    var makerName: String? = null

    @SerializedName("variantName")
    @Expose
    var variantName: String? = null

    @SerializedName("modelName")
    @Expose
    var modelName: String? = null

    @SerializedName("vehiclePictures")
    @Expose
    var vehiclePictures: GetVehiclePicture? = null
}
