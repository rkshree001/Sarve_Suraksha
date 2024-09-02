package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCartDetailsMainData {
    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: GetCartDetailsMainGet? = null
}
