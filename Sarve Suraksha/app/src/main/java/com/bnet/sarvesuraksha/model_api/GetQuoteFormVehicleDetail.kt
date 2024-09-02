package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetQuoteFormVehicleDetail {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("vehicleType")
    @Expose
    var vehicleType: String? = null

    @SerializedName("registrationNumber")
    @Expose
    var registrationNumber: String? = null

    @SerializedName("makerName")
    @Expose
    var makerName: String? = null

    @SerializedName("modelName")
    @Expose
    var modelName: String? = null

    @SerializedName("variantName")
    @Expose
    var variantName: String? = null

    @SerializedName("rtoCode")
    @Expose
    var rtoCode: String? = null

    @SerializedName("isClaimed")
    @Expose
    var isClaimed: Boolean? = null

    @SerializedName("deliveryDate")
    @Expose
    var deliveryDate: String? = null
}
