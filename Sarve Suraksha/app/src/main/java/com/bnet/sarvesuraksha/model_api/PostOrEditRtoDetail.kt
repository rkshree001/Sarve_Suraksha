package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostOrEditRtoDetail {
    @SerializedName("rtoCode")
    @Expose
    var rtoCode: String? = null

    @SerializedName("cityName")
    @Expose
    var cityName: String? = null
}
