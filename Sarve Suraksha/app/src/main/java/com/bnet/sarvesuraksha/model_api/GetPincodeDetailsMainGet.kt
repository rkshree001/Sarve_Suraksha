package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetPincodeDetailsMainGet {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("data")
    @Expose
    var data: GetPincodeDetailsMainRes? = null
}
