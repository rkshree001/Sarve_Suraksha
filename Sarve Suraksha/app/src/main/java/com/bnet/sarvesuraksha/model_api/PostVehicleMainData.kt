package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostVehicleMainData {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("data")
    @Expose
    var data: PostVehicleMainGet? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}
