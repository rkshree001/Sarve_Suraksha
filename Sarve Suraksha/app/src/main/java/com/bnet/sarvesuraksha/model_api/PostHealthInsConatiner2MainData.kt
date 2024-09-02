package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostHealthInsConatiner2MainData {
    @SerializedName("statusCode")
    @Expose
    var statusCode: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("data")
    @Expose
    var data: PostHealthInsConatiner2MainGet? = null
}
