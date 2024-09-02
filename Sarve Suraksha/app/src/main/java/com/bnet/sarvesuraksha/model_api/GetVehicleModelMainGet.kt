package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleModelMainGet {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("brandId")
    @Expose
    var brandId: String? = null

    @SerializedName("modelName")
    @Expose
    var modelName: String? = null

    @SerializedName("fuelType")
    @Expose
    var fuelType: List<String>? = null

    @SerializedName("active")
    @Expose
    var active: Boolean? = null

    @SerializedName("isTopModel")
    @Expose
    var isTopModel: Boolean? = null
}
