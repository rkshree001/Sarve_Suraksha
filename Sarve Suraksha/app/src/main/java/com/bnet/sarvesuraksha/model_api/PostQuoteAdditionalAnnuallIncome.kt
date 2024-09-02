package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PostQuoteAdditionalAnnuallIncome {
    @SerializedName("memberType")
    @Expose
    var memberType: String? = null

    @SerializedName("max")
    @Expose
    var max: String? = null

    @SerializedName("min")
    @Expose
    var min: String? = null
}
