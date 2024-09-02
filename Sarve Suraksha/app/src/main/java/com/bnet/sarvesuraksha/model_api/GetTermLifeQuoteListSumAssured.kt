package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetTermLifeQuoteListSumAssured {
    @SerializedName("min")
    @Expose
    var min: Int? = null

    @SerializedName("max")
    @Expose
    var max: Int? = null
}
