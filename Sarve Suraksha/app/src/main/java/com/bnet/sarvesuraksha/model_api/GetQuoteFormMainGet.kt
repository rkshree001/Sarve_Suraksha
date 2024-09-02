package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetQuoteFormMainGet {
    @SerializedName("vehicleDetail")
    @Expose
    var vehicleDetail: GetQuoteFormVehicleDetail? = null

    @SerializedName("quoteList")
    @Expose
    var quoteList: List<GetQuoteFormMainRes>? = null
}
