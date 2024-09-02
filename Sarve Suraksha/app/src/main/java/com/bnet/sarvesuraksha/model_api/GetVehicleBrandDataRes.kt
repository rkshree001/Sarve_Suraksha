package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetVehicleBrandDataRes {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("brandImg")
    @Expose
    var brandImg: String? = null

    @SerializedName("makerName")
    @Expose
    var makerName: String? = null

    @SerializedName("active")
    @Expose
    var active: Boolean? = null

    @SerializedName("isTopModel")
    @Expose
    var isTopModel: Boolean? = null
}
