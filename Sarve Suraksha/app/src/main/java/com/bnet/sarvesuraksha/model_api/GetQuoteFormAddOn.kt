package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetQuoteFormAddOn {
    @SerializedName("addOnName")
    @Expose
    var addOnName: String? = null

    @SerializedName("addOnPremium")
    @Expose
    var addOnPremium: Int? = null

    @SerializedName("declaration")
    @Expose
    var declaration: String? = null

    @SerializedName("insId")
    @Expose
    var insId: String? = null
}
