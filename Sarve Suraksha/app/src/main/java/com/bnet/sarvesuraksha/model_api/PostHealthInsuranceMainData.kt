package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostHealthInsuranceMainData {
    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: PostHealthInsuranceMainGet? = null
}
