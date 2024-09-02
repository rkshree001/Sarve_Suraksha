package com.bnet.sarvesuraksha.model_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetQuoteFormMainRes {
    @SerializedName("_id")
    @Expose
    var id: String? = null

    @SerializedName("insurerName")
    @Expose
    var insurerName: String? = null

    @SerializedName("insurerImg")
    @Expose
    var insurerImg: String? = null

    @SerializedName("planName")
    @Expose
    var planName: String? = null

    @SerializedName("premium")
    @Expose
    var premium: Int? = null

    @SerializedName("insuranceType")
    @Expose
    var insuranceType: String? = null

    @SerializedName("IDVCover")
    @Expose
    var iDVCover: Int? = null

    @SerializedName("addOn")
    @Expose
    var addOn: List<GetQuoteFormAddOn>? = null
}
